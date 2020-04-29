<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 28.04.2020
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
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
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
