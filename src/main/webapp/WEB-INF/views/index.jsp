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
<a href="${pageContext.request.contextPath}/registration">Registration</a>
<br>
<a href="${pageContext.request.contextPath}/users/list">Show all users</a>
<br>
<h2>Products</h2>
<br>
<a href="${pageContext.request.contextPath}/admin/products/list">Work with products for admin</a>
<br>
<a href="${pageContext.request.contextPath}/products/fill">Inject Data Products</a>
<br>
<a href="${pageContext.request.contextPath}/products/list">Show all products and add their to Cart</a>
<br>
<br>
<h2>Shopping Cart</h2>
<br>
<a href="${pageContext.request.contextPath}/shopping-carts/show">Show all shopping cart by user</a>
<br>
<h2>Orders</h2>
<br>
<a href="${pageContext.request.contextPath}/admin/orders/list">Show all orders</a>
<br>
<a href="${pageContext.request.contextPath}/orders/personal/list">Show all order by user</a>
<br>
<br>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>
