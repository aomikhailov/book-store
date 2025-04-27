<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="userFullName" scope="request" type="java.lang.String"/>
<jsp:useBean id="userCartActionResult" scope="request" type="java.lang.String"/>
<jsp:useBean id="userCartActionError" scope="request" type="java.lang.Integer"/>

<jsp:include page="menu.jsp" />

<c:if test="${not empty userCartActionResult}">
    <p class="${not empty userCartActionError and userCartActionError == '1' ? 'text-danger' : 'text-success'} text-large">
            ${userCartActionResult}
    </p>
</c:if>

<h2>Корзина пользователя "${userFullName}"</h2>

<jsp:include page="cartList.jsp" />


