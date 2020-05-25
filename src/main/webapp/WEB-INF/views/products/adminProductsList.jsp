<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ProductsAdmin</title>
</head>
<body>
<h1>List of products for editing</h1>
<table border="1">
    <tr>
        <th>The product's name</th>
        <th>Product cost</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/admin/products/create">Add product to DB</a>
<br>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
