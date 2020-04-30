<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 30.04.2020
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OrderOneUser</title>
</head>
<body>
<h1>Order by one user</h1>
<br>
<h3> User name  <c:out value="${order.user.name}"/></h3>
<h3> Order number <c:out value="${order.id}"/></h3>
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

        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/" class="btn btn-sm btn-primary">Go to the main page</a>
</body>
</html>
