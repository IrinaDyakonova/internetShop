<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<h4 style="color: red">${errorMsg}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    Please provide your login: <input type="text" name="login" value="${oldLogin}" placeholder="Enter your login" required>
    <br>
    Please provide your password: <input type="password" name="pwd" placeholder="Enter your password" required>
    <br>
    <button type="submit" class="btn btn-primary">Login</button>
</form>
</body>
</html>
