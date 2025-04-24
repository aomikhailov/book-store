<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:useBean id="pageTitle" scope="request" type="java.lang.String"/>
<jsp:useBean id="pageHead" scope="request" type="java.lang.String"/>
<jsp:useBean id="contentTemplate" scope="request" type="java.lang.String"/>

<c:if test="${empty pageTitle}">
    <c:set var="pageTitle" value="Заголовок веб-страницы по умолчанию"/>
</c:if>

<c:if test="${empty pageHead}">
    <c:set var="pageHead" value="Заголовок страницы по умолчанию"/>
</c:if>

<c:if test="${empty contentTemplate}">
    <c:set var="contentTemplate" value="/WEB-INF/views/default.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle}"/></title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-container">
<h1><c:out value="${pageHead}"/></h1>
<c:if test="${not empty contentTemplate}">
    <c:catch var="contentTemplateIncludeError">
        <jsp:include page="${contentTemplate}" />
    </c:catch>
    <c:if test="${not empty contentTemplateIncludeError}">
        <p>Файл не найден, или произошла ошибка при его загрузке.</p>
        <p>Ошибка: ${contentTemplateIncludeError.message}</p>
    </c:if>
</c:if>
</body>
</html>