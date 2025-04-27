<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="userFullName" scope="request" type="java.lang.String"/>
<jsp:include page="menu.jsp" />

<h2>Корзина пользователя "${userFullName}"</h2>

<jsp:include page="cartList.jsp" />


