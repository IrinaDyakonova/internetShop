<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Hello! Please provide your user details</h1>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/admin/products/create">
    Enter product name: <input type="text" name="name">
    <br>
    Enter product cost: <input type="number" name="price" step="0.01" min ="0">
    <br>
    <button type="submit">Register</button>
</form>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
