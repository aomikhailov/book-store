<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
                    <td class="text-center w-150px">${book.price} руб.</td>
                    <td class="text-center w-150px">Купить</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>Список книг пуст.</p>
    </c:otherwise>
</c:choose>