<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="errorCode" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ошибка ${errorCode}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/error.css">
</head>
<body>
<h1>Ошибка ${errorCode}</h1>
<p>${errorMessage}</p>
<c:if test="${errorCode == 404}">
    <p><a href="${pageContext.request.contextPath}/">Вернуться на главную</a></p>
</c:if>
</body>
</html>