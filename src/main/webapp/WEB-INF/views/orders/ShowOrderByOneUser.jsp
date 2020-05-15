<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OrderOneUser</title>
</head>
<body>
<h1>Order by one user</h1>
<br>

<h3> Order number <c:out value="${order.id}"/></h3>
<table border="1">

    <tr>
        <th>The product's name</th>
        <th>Product cost</th>
    </tr>
    <c:forEach var="product" items="${order.getProducts()}">
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

<a href="${pageContext.request.contextPath}/" class="btn btn-sm btn-primary">Go to the main page</a>
</body>
</html>
