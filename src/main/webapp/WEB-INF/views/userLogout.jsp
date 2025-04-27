<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 <jsp:include page="menu.jsp"/>

<h2>Выход из учетной записи</h2>

<p>Вы успешно вышли из своей учетной записи!</p>

<p>Большинство страниц требует аутентификации на сайте, поэтому начните с входа в учетную запись.</p>

 <p>
     <a class="btn"  href="${pageContext.request.contextPath}/user/login">Войти</a>
 </p>








