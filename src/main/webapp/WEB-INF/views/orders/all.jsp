<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 30.04.2020
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>order</title>
</head>
<body>
<h1>All orders page</h1>
<h4 style="color: red">${massage}</h4>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login User</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                                    /orders/showOrdersByUser?id=${user.id}">Show</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                                    /orders/delete?id=${order.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
