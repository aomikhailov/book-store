<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:useBean id="userCartList" scope="request" type="java.util.List"/>

<c:choose>
    <c:when test="${not empty userCartList}">
        <c:set var="totalSum" value="0"/>
        <table>
            <tr>
                <th class="text-center w-50px">№</th>
                <th class="text-left">Название</th>
                <th class="text-left">Автор</th>
                <th class="text-center w-150px">Количество</th>
                <th class="text-center w-150px">Сумма</th>
                <th class="text-center w-200px" colspan="2">Действие</th>
            </tr>

            <c:forEach var="item" items="${userCartList}" varStatus="status">

                <c:set var="rowSum" value="${item.book.price * item.bookQuantity}"/>
                <c:set var="totalSum" value="${totalSum + rowSum}"/>

                <tr>
                    <td class="text-center w-50px">${status.index + 1}</td>
                    <td class="text-left">${item.book.title}</td>
                    <td class="text-left">${item.book.author.fio}</td>
                    <td class="text-center">${item.bookQuantity}</td>
                    <td class="text-center w-150px"><fmt:formatNumber value="${rowSum}" type="number" minFractionDigits="2" maxFractionDigits="2" /> руб.</td>
                    <td class="text-center w-100px">Добавить</td>
                    <td class="text-center w-100px">Удалить</td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="4" class="text-right font-weight-bold">Итого:</td>
                <td class="text-center w-150px font-weight-bold">${totalSum} руб.</td>
            </tr>

        </table>
    </c:when>
    <c:otherwise>
        <p>Ваша корзина пуста.</p>
    </c:otherwise>
</c:choose>