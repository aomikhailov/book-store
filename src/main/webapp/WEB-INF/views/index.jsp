<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<jsp:useBean id="bookCatalogList" scope="request" type="java.util.List"/>--%>

<%--<jsp:useBean id="bookCatalogList" scope="request" type="java.lang.String"/>--%>

<c:choose>
    <c:when test="${not empty bookCatalogList}">
        <p>Список не пустой.</p>
        <ul>
            <c:forEach var="book" items="${bookCatalogList}">
                <li>${book.title}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <p>Список книг пуст.</p>
    </c:otherwise>
</c:choose>



