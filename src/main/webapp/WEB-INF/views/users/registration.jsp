<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 28.04.2020
  Time: 02:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Hello! Please provide your user details</h1>
    <h4 style="color: red">${massage}</h4>
    <form method="post" action="${pageContext.request.contextPath}/users/registration">
        Please provide your name: <input type="text" name="name">
        <br>
        Please provide your login: <input type="text" name="login">
        <br>
        Please provide your password: <input type="password" name="pwd">
        <br>
        Please repeat your password: <input type="password" name="pwd-repeat">
        <br>
        <button type="submit">Register</button>
    </form>
    <a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>