<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Irina
  Date: 04.05.2020
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OrderOneUser</title>
</head>
<body>
<h1> Show all orders by one User</h1>
<h3> User name  <c:out value="${user.name}"/></h3>


<h4 style="color: red">${massage}</h4>
<table border="1">
    <tr>
        <th>ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                                    /orders/showOrdersByUser?id=${order.id}">Show</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Start page</a>
</body>
</html>
