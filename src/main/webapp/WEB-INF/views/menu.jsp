<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div id="menu">
    <div id="navigation">
        <strong>Навигация:</strong>
        <a href="${pageContext.request.contextPath}/">Главная</a> |
        <a href="${pageContext.request.contextPath}/">Авторы</a> |
        <a href="${pageContext.request.contextPath}/">Корзина</a>
    </div>
    <div id="session">
        <strong>Вы вошли как:</strong> пользователь
    </div>
</div>
