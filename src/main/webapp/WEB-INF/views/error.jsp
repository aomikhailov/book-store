<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<jsp:useBean id="errorTitle" scope="request" type="java.lang.String"/>--%>
<%--<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>--%>
<%--<jsp:useBean id="errorCode" scope="request" type="java.lang.Integer"/>--%>

<%--Код ошибки по-умолчанию--%>
<c:if test="${empty errorCode}"><c:set var="errorCode" value="500"/></c:if>

<%--Заколовок по коду, если не задан --%>
<c:if test="${empty errorTitle}">
    <c:choose>
        <c:when test="${errorCode == 400}"><c:set var="errorTitle" value="Плохой запрос"/></c:when>
        <c:when test="${errorCode == 401}"><c:set var="errorTitle" value="Требуется авторизация"/></c:when>
        <c:when test="${errorCode == 403}"><c:set var="errorTitle" value="Доступ запрещён"/></c:when>
        <c:when test="${errorCode == 404}"><c:set var="errorTitle" value="Документ не найден"/></c:when>
        <c:when test="${errorCode == 405}"><c:set var="errorTitle" value="Запрещенный метод запроса"/></c:when>
        <c:otherwise><c:set var="errorTitle" value="Что-то пошло нет так"/></c:otherwise>
    </c:choose>
</c:if>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv=Content-Type content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><c:out value="${errorTitle}"/></title>
    <style type="text/css">
        body {
            background-color: #f5f5f5;
            margin-top: 8%;
            color: #5d5d5d;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
            text-shadow: 0px 1px 1px rgba(255, 255, 255, 0.75);
            text-align: center !important;
        }

        h1 {
            font-size: 2.45em;
            font-weight: 700;
            color: #5d5d5d;
            letter-spacing: -0.02em;
            margin-bottom: 30px;
            margin-top: 30px;
        }

        .container {
            width: 100%;
            margin-right: auto;
            margin-left: auto;
        }

        .animated {
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
        }

        .fadeIn {
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }

        .error {
            color: #c92127;
            fill: #c92127;
        }

        .icon-large {
            height: 132px;
            width: 132px;
        }

        .description-text {
            color: #707070;
            letter-spacing: -0.01em;
            font-size: 1.25em;
            line-height: 20px;
        }

        .footer {
            margin-top: 40px;
            font-size: 0.7em;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
    </style>
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col">
            <div class="animated fadeIn">
                <svg class="error icon-large" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <path d="M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8zm121.6 313.1c4.7 4.7 4.7 12.3 0 17L338 377.6c-4.7 4.7-12.3 4.7-17 0L256 312l-65.1 65.6c-4.7 4.7-12.3 4.7-17 0L134.4 338c-4.7-4.7-4.7-12.3 0-17l65.6-65-65.6-65.1c-4.7-4.7-4.7-12.3 0-17l39.6-39.6c4.7-4.7 12.3-4.7 17 0l65 65.7 65.1-65.6c4.7-4.7 12.3-4.7 17 0l39.6 39.6c4.7 4.7 4.7 12.3 0 17L312 256l65.6 65.1z"></path>
                </svg>
            </div>
            <h1 class="animated fadeIn"><c:out value="${errorTitle}"/></h1>
            <div class="description-text animated fadeIn">
                <c:choose>
                    <c:when test="${empty errorMessage}">
                        <%--Текст ошибки по коду, если не задан --%>
                        <c:choose>
                            <c:when test="${errorCode == 400}">
                                <p>Вы отправили запрос, который наш сервер не может обработать.</p>
                                <p>Если вы продолжите отправлять нам такие запросы, мы заблокируем вам доступ к нашему
                                    серверу.</p>
                            </c:when>
                            <c:when test="${errorCode == 401}">
                                <p>Доступ к запрошенному ресурсу предоставляется только авторизованным
                                    пользователям.</p>
                                <p>Получите имя пользователя и пароль для доступа к интересующему вас ресурсу.</p>
                            </c:when>
                            <c:when test="${errorCode == 403}">
                                <p>У вас нет прав на просмотр запрошенного ресурса.</p>
                                <p>Проверьте своё имя пользователя и пароль.</p>
                            </c:when>
                            <c:when test="${errorCode == 404}">
                                <p>Запрошенный вами документ не найден.</p>
                                <p>Убедитесь, что вы используете правильную ссылку.</p>
                            </c:when>
                            <c:when test="${errorCode == 405}">
                                <p>Вы используете метода запроса, который наш сервер не поддерживает.</p>
                                <p>Если вы продолжите отправлять нам такие запросы, мы заблокируем вам доступ к нашему
                                    серверу.</p>
                            </c:when>
                            <c:otherwise>
                                <p>Во время обработки вашего запроса возникла ошибка.</p>
                                <p>Наши специалисты уже уведомлены об этой проблеме.</p>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <%--Текст ошибки, если задан (допускается HTML)--%>
                    <c:otherwise>${errorMessage}</c:otherwise>
                </c:choose>
                <section class="footer"><strong>Код ошибки:</strong> <c:out value="${errorCode}"/></section>
            </div>
        </div>
    </div>
</div>
</body>
</html>