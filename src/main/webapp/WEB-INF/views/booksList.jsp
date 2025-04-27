<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:useBean id="bookCatalogList" scope="request" type="java.util.List"/>

<c:choose>
    <c:when test="${not empty bookCatalogList}">
        <table>
            <tr>
                <th class="text-center w-50px">№</th>
                <th class="text-left">Название</th>
                <th class="text-left">Автор</th>
                <th class="text-center w-150px">Цена</th>
                <th class="text-center w-150px">Действие</th>
            </tr>

            <c:forEach var="book" items="${bookCatalogList}" varStatus="status">
                <tr>
                    <td class="text-center w-50px">${status.index + 1}</td>
                    <td class="text-left">${book.title}</td>
                    <td class="text-left">${book.author.fio}</td>
                    <td class="text-center w-150px"><fmt:formatNumber value="${book.price}" type="number" minFractionDigits="2" maxFractionDigits="2" /> руб.</td>
                    <td class="text-center w-150px">
                        <form action="${pageContext.request.contextPath}/user/cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="add" />
                            <input type="hidden" name="id" value="${book.bookId}" />
                            <button type="submit">Купить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>Список книг пуст. </p>
    </c:otherwise>
</c:choose>