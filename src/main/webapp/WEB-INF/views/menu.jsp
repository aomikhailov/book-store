<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="loggedUserSession" scope="request" type="ru.almidev.bookstore.models.UserSession"/>
<jsp:useBean id="unloggedUsersSessions" scope="request" type="java.util.List"/>

<div id="menu">
    <div id="navigation">
        <strong>Навигация:</strong>
        <a href="${pageContext.request.contextPath}/">Главная</a> |
        <a href="${pageContext.request.contextPath}/">Авторы</a> |
        <a href="${pageContext.request.contextPath}/">Корзина</a>
    </div>
    <div id="session">
          <c:choose>
              <c:when test="${not empty loggedUserSession and not empty loggedUserSession.sessionId}">
                  <strong>Вы вошли как: </strong> ${loggedUserSession.user.fullName}
            </c:when>
            <c:otherwise>
                Вы гость (у вас нет сессии аутентификации на сайте)
            </c:otherwise>

        </c:choose>

        <c:choose>
            <c:when test="${not empty unloggedUsersSessions}">
                , можете войти как:
                <c:forEach var="session" items="${unloggedUsersSessions}" varStatus="status">
                    <a href="JavaScript:void();" id="${session.sessionToken}" class="session">${session.user.fullName}</a>
                    <c:if test="${!status.last}">
                        или
                    </c:if>
                </c:forEach>
            </c:when>
        </c:choose>

    </div>
</div>