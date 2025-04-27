<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="loggedUserSession" scope="request" type="ru.almidev.bookstore.models.UserSession"/>

<div id="menu">
    <div id="navigation">
        <strong>Навигация:</strong>
        <a href="${pageContext.request.contextPath}/">Главная</a> |
        <a href="${pageContext.request.contextPath}/catalog">Каталог</a> |
        <a href="${pageContext.request.contextPath}/authors">Авторы</a> |
        <a href="${pageContext.request.contextPath}/user/cart">Корзина</a>

    </div>
    <div id="session">
          <c:choose>
              <c:when test="${not empty loggedUserSession and not empty loggedUserSession.sessionId}">
                  <strong>Вы вошли как: </strong> ${loggedUserSession.user.login} |  <a href="${pageContext.request.contextPath}/user/logout">Выйти</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/login">Войти</a>
            </c:otherwise>

        </c:choose>
    </div>
</div>