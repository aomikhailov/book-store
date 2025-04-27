<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="userLoginResult" scope="request" type="java.lang.String"/>
<jsp:useBean id="userLoginError" scope="request" type="java.lang.Integer"/>

<jsp:include page="menu.jsp"/>

<h2>Вход в учетную запись</h2>

<p>Вы успешно вошли в свою учетную запись!</p>
<p>Подберите себе что-нибудь в нашем <a href="${pageContext.request.contextPath}/catalog">каталоге</a>. </p>




