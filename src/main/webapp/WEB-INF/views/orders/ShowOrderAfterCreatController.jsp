<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 29.04.2020
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>orderIsProcessed</title>
</head>
<body>
<h1>orderIsProcessed</h1>

<br>
<h3><c:out value="${order.id}"/></h3>

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
