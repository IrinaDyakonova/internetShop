<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Internet shop</h1>
<br>
<h2>Users</h2>
<br>
<a href="${pageContext.request.contextPath}/users/injectData">Inject Data Users</a>
<br>
<a href="${pageContext.request.contextPath}/users/registration">Registration</a>
<br>
<a href="${pageContext.request.contextPath}/users/all">Show all users</a>
<br>
<h2>Products</h2>
<br>
<a href="${pageContext.request.contextPath}/products/addProduct">Add product</a>
<br>
<a href="${pageContext.request.contextPath}/products/injectDataProducts">Inject Data Products</a>
<br>
<a href="${pageContext.request.contextPath}/products/all">Show all products</a>
<br>
<h2>Shopping Cart</h2>
<br>
<a href="${pageContext.request.contextPath}/shoppingCarts/all">Show all shopping cart</a>
</body>
</html>
