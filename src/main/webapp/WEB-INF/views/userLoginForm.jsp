<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="userLoginResult" scope="request" type="java.lang.String"/>
<jsp:useBean id="userLoginError" scope="request" type="java.lang.Integer"/>


<jsp:include page="menu.jsp"/>


<c:if test="${not empty userLoginResult}">
    <p class="${not empty userLoginError and userLoginError == '1' ? 'text-danger' : 'text-success'} text-large">
            ${userLoginResult}
    </p>
</c:if>

<h2>Вход в учетную запись</h2>

<p>Заполните имя пользователя и пароль и нажмите кнопку "Войти".</p>

<!-- Форма для входа -->
<form action="${pageContext.request.contextPath}/user/login" method="post">
    <div class="mt-10 mb-10">
        <label for="login">Логин:</label>
        <input type="text" class="w-200px" id="login" name="login" required>
    </div>
    <div class="mt-10 mb-10">
        <label for="password">Пароль:</label>
        <input type="password" class="w-200px" id="password" name="password" required>
    </div>
    <div class="mt-10 mb-10">
        <button type="submit">Войти</button>
    </div>
</form>

<p class="text-bold">Данные для входа:</p>
<ul>
    <li>Логин: user1 или user2 </li>
    <li>Пароль: 1234567890</li>
</ul>




