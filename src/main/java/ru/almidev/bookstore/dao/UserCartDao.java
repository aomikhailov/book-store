package ru.almidev.bookstore.dao;

import ru.almidev.bookstore.models.AppUser;
import ru.almidev.bookstore.models.BookCatalog;
import ru.almidev.bookstore.models.UserCart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO класс для управления сущностями {@link UserCart}.
 * Выполняет операции создания, чтения, обновления и удаления для таблицы `user_cart`.
 */
public class UserCartDao extends BaseDao<UserCart, Integer> {

    @Override
    public UserCart findById(Integer id) throws SQLException {
        String query = "SELECT * FROM user_cart WHERE item_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, id);
        if (results.isEmpty()) {
            return null;
        }
        return mapRowToUserCart(results.getFirst());
    }

    @Override
    public List<UserCart> findAll() throws SQLException {
        String query = "SELECT * FROM user_cart";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query);
        List<UserCart> userCartList = new ArrayList<>();
        for (Map<String, Object> row : results) {
            userCartList.add(mapRowToUserCart(row));
        }
        return userCartList;
    }

    public List<UserCart> findAllByAppUser(Integer userId) throws SQLException {
        String query = "SELECT * FROM user_cart WHERE user_id = ?";
        List<Map<String, Object>> results = databaseHelper.executeQuery(query, userId);
        List<UserCart> userCartList = new ArrayList<>();
        for (Map<String, Object> row : results) {
            userCartList.add(mapRowToUserCart(row));
        }
        return userCartList;
    }

    public List<UserCart> findAllByAppUser(AppUser appUser) throws SQLException {
         return findAllByAppUser(appUser.getUserId());
    }

    @Override
    public void save(UserCart entity) throws SQLException {
        String query = "INSERT INTO user_cart (user_id, book_id, book_quantity) VALUES (?, ?, ?)";
        databaseHelper.executeUpdate(query, entity.getUser().getUserId(), entity.getBook().getBookId(), entity.getBookQuantity());
    }

    @Override
    public void update(UserCart entity) throws SQLException {
        String query = "UPDATE user_cart SET user_id = ?, book_id = ?, book_quantity = ? WHERE item_id = ?";
        databaseHelper.executeUpdate(query, entity.getUser().getUserId(), entity.getBook().getBookId(), entity.getBookQuantity(), entity.getItemId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM user_cart WHERE item_id = ?";
        databaseHelper.executeUpdate(query, id);
    }

    private UserCart mapRowToUserCart(Map<String, Object> row) throws SQLException {
        UserCart userCart = new UserCart();
        userCart.setItemId((Integer) row.get("item_id"));
        AppUser user = new AppUserDao().findById((Integer) row.get("user_id"));
        userCart.setUser(user);
        BookCatalog book = new BookCatalogDao().findById((Integer) row.get("book_id"));
        userCart.setBook(book);
        userCart.setBookQuantity((Integer) row.get("book_quantity"));
        return userCart;
    }
}