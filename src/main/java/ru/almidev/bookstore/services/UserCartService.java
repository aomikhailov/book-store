package ru.almidev.bookstore.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.almidev.bookstore.dao.BookCatalogDao;
import ru.almidev.bookstore.dao.UserCartDao;
import ru.almidev.bookstore.enums.UserCartActionEnum;
import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.models.UserCart;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static ru.almidev.bookstore.attributes.AttributeNames.USER_CART_ACTION_ERROR;
import static ru.almidev.bookstore.attributes.AttributeNames.USER_CART_ACTION_RESULT;

public class UserCartService {

    private final BookCatalogDao bookCatalogDao = new BookCatalogDao();
    private final UserCartDao userCartDao = new UserCartDao();
    private final HttpServletRequest httpServletRequest;
    private final AppUser appUser;

    public UserCartService(HttpServletRequest httpServletRequest, AppUser appUser) {
        this.httpServletRequest = httpServletRequest;
        this.appUser = appUser;
    }

    /**
     * Получает список всех записей корзины для указанного пользователя приложения.
     *
     * @param appUser Пользователь приложения, для которого необходимо найти записи корзины.
     * @return Список объектов {@code UserCart}, соответствующих пользователю,
     * либо пустой список, если произошла ошибка или записи отсутствуют.
     */
    public List<UserCart> getUserCartList(AppUser appUser) {
        try {
            return userCartDao.findAllByAppUser(appUser);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Извлекает запись каталога книг на основе параметра "id" из HTTP-запроса.
     *
     * @return Объект {@code BookCatalog}, соответствующий указанному идентификатору,
     * или {@code null}, если идентификатор некорректен, отсутствует,
     * либо произошла ошибка при запросе к базе данных.
     */
    public BookCatalog getBookCatalogFromHttpRequest() {

        int bookId;

        try {
            bookId = Integer.parseInt(httpServletRequest.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }

        try {
            return bookCatalogDao.findById(bookId);
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * Определяет действие пользователя с корзиной на основе параметра "action" из HTTP-запроса.
     *
     * @return Экземпляр {@code UserCartActionEnum}, представляющий действие пользователя
     * (например, {@code ADD} для добавления или {@code DEL} для удаления),
     * либо {@code null}, если параметр отсутствует, пуст или некорректен.
     */
    public UserCartActionEnum getUserCartActionFromHttpRequest() {
        String action = httpServletRequest.getParameter("action");
        if (action == null || action.isEmpty()) {
            return null;
        }
        if ("add".equalsIgnoreCase(action)) {
            return UserCartActionEnum.ADD;
        } else if ("del".equalsIgnoreCase(action)) {
            return UserCartActionEnum.DEL;
        } else {
            return null;
        }
    }

    /**
     * Проверяет, находится ли данная книга в корзине пользователя.
     *
     * @param book Объект {@code BookCatalog}, представляющий книгу, для которой необходимо выполнить проверку.
     * @return {@code true}, если книга находится в корзине пользователя, иначе {@code false}.
     * @throws RuntimeException Если возникает ошибка при доступе к базе данных.
     */
    public boolean isBookInUserCart(BookCatalog book) {

        if (book == null) {
            throw new RuntimeException("book не может быть null!");
        }

        try {
            return 0 != userCartDao.countByAppUserAndBookCatalog(appUser, book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавляет книгу в корзину пользователя. Если книга уже есть в корзине, увеличивает количество.
     *
     * @param book Объект {@code BookCatalog}, представляющий книгу, которую нужно добавить.
     */
    public void addBookToUserCart(BookCatalog book) {
        if (book == null) {
            httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, "Ошибка: книга для добавления в корзину не указана.");
            httpServletRequest.setAttribute(USER_CART_ACTION_ERROR, 1);
            return;
        }

        try {
            UserCart userCart = userCartDao.findByAppUserAndBookCatalog(appUser, book);
            if (userCart != null) {
                userCart.setBookQuantity(userCart.getBookQuantity() + 1);
                userCartDao.update(userCart);
            } else {
                userCart = new UserCart();
                userCart.setUser(appUser);
                userCart.setBook(book);
                userCart.setBookQuantity(1);
                userCartDao.save(userCart);
            }
            httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, String.format("Книга \"%s\" была успешно добавлена в корзину.", book.getTitle()));
        } catch (SQLException e) {
            httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, String.format("Ошибка: книгу \"%s\" не удалось добавить в корзину. Текст ошибки: %s.", book.getTitle(), e.getMessage()));
            httpServletRequest.setAttribute(USER_CART_ACTION_ERROR, 1);
        }
    }

    /**
     * Удаляет указанную книгу из корзины пользователя. Если книга отсутствует в корзине,
     * устанавливает сообщение об ошибке в HTTP-запросе.
     *
     * @param book Объект {@code BookCatalog}, представляющий книгу, которую необходимо удалить из корзины.
     *             Если значение {@code null}, операция не выполняется.
     */
    public void delBookFromUserCart(BookCatalog book) {
        if (book == null) {
            httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, "Ошибка: книга для удаления из корзины не указана.");
            httpServletRequest.setAttribute(USER_CART_ACTION_ERROR, 1);
            return;
        }

        try {
            UserCart userCart = userCartDao.findByAppUserAndBookCatalog(appUser, book);
            if (userCart != null) {
                if (userCart.getBookQuantity() > 1) {
                    userCart.setBookQuantity(userCart.getBookQuantity() - 1);
                    userCartDao.update(userCart);
                }
                else {
                    userCartDao.deleteById(userCart.getItemId());
                }
                httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, String.format("Книга  \"%s\" была успешно удалена  их корзины.", book.getTitle()));

            } else {
                httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, String.format("Ошибка: книгу \"%s\" не удалить из корзины, так как ее там не было.", book.getTitle()));
                httpServletRequest.setAttribute(USER_CART_ACTION_ERROR, 1);
            }
        } catch (SQLException e) {
            httpServletRequest.setAttribute(USER_CART_ACTION_RESULT, String.format("Ошибка: книгу \"%s\" не удалось удалить из корзины. Текст ошибки: %s.", book.getTitle(), e.getMessage()));
            httpServletRequest.setAttribute(USER_CART_ACTION_ERROR, 1);
        }
    }

}
