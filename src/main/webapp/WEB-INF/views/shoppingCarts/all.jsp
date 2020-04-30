<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping carts</title>
</head>
<body>
<h1>All products shopping carts page</h1>
<table border="1">
    <tr>
        <th>The product's name</th>
        <th>Product cost</th>
    </tr>
    <c:forEach var="product" items="${allProductsShoppingCarts}">
        <tr>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                                    /products/deleteFromCart?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form method="post" action="${pageContext.request.contextPath}/orders/add">
    <input type="hidden" name="id" value="${shoppingCart.getUser().getId()}">
    <button type="submit"> add order</button>
</form>
<br>
<br>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
