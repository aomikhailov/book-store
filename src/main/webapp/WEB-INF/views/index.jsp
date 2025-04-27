<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="loggedUserSession" scope="request" type="ru.almidev.bookstore.models.UserSession"/>

<jsp:include page="menu.jsp"/>

<h2>Это учебный проект BookStore</h2>

<p>Используйте ссылки в меню для просмотра каталога, списка авторов и работы с корзиной.<p>

<c:choose>
    <c:when test="${not empty loggedUserSession and not empty loggedUserSession.sessionId}">
        <p>Вы (или кто-то другой до вас на этом компьютере) уже вошли в учетную запись пользователя
            <strong>${loggedUserSession.user.login}</strong>. </p>
        <p> Чтобы посмотреть корзину другого пользователя, выйдите из этой учетной записи, нажав на расположенную ниже
            кнопку,
            а затем зайдите под другой учётной записью. </p>
        <p>
            <a class="btn" href="${pageContext.request.contextPath}/user/logout">Выйти из учетной записи</a>
        </p>
    </c:when>
    <c:otherwise>
        <p>Работа с корзиной требует, чтобы вы вошли в учетную запись.
            Чтобы сделать это, нажмите на расположенную ниже кнопку.</p>
        <p>
            <a class="btn" href="${pageContext.request.contextPath}/user/login">Войти в учётную запись</a>
        </p>
    </c:otherwise>
</c:choose>


<h3>Исходный код этого проекта</h3>
<ul>
    <li><a href="https://github.com/aomikhailov/book-store"
           target="_blank">https://github.com/aomikhailov/book-store</a></li>
</ul>


