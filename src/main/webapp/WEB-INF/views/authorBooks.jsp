<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="authorFio" scope="request" type="java.lang.String"/>

<jsp:include page="menu.jsp" />

<h2>Книги автора "${authorFio}"</h2>

<jsp:include page="booksList.jsp" />


