<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:useBean id="bookAuthorList" scope="request" type="java.util.List"/>

<jsp:include page="menu.jsp" />

<h2>Авторы, книги которых доступны в нашем магазине</h2>

<c:choose>
    <c:when test="${not empty bookAuthorList}">
        <table>
            <tr>
                <th class="text-center w-50px">№</th>
                <th class="text-left">Фамилия</th>
                <th class="text-left">Имя</th>
                <th class="text-left">Отчество</th>
                <th class="text-center">Дата рождения</th>
                <th class="text-center w-150px">Действие</th>
            </tr>

            <c:forEach var="author" items="${bookAuthorList}" varStatus="status">
                <tr>
                    <td class="text-center w-50px">${status.index + 1}</td>
                    <td class="text-left">${author.lastName}</td>
                    <td class="text-left">${author.firstName}</td>
                    <td class="text-left">${author.middleName}</td>
                    <td class="text-center">
                        <fmt:formatDate value="${author.birthDate}" pattern="dd.MM.yyyy" />
                    </td>
                    <td class="text-center w-150px"><a href="${pageContext.request.contextPath}/author/books?id=${author.authorId}">Книги автора</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>Список авторов пуст.</p>
    </c:otherwise>
</c:choose>



