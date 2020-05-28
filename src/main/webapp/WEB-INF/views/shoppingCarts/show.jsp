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
                                    /shopping-carts/products/remove?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<c:if test="${allProductsShoppingCarts.size() > 0 }" >
<form method="post" action="${pageContext.request.contextPath}/orders/complete">
    <input type="hidden" name="id" value="${shoppingCart.getUserId()}">
    <button type="submit"> add order</button>
</form>
</c:if>
<br>
<br>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
