<%--
  Created by IntelliJ IDEA.
  User: zhezl_lx3d
  Date: 23.04.2021
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="${pageContext.request.contextPath}/"/>

    <title>Sharing document </title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div>
    <h2> Sharing document ${document.name}</h2>
</div>
<div>
</div>
<div>
    <form action="documents/sharing" method="post">
        <input type="hidden" name="id" value="${document.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <select name="userId">
            <c:forEach var="item" items="${users}">
                <c:if test="${item.username != loggedUser}">
                    <option value="${item.id}">${item.username}</option>
                </c:if>
            </c:forEach>
        </select>
<%--        <input type="submit" value="Save">--%>
        <button type="submit"><c:out value="Save"/></button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</div>

</body>
</html>
