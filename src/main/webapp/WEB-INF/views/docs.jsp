<%--
  Created by IntelliJ IDEA.
  User: zhezl_lx3d
  Date: 08.04.2021
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Documents</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>name</th>
        <th>description</th>
        <th>author</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${documents}" var="document">
        <jsp:useBean id="document" scope="page" type="org.zhezlov.documentarchive.model.Document"/>
        <tr>
            <td>${document.name} </td>
            <td>${document.description}</td>
            <td>${document.authorId}</td>
            <td><a href="documents/update?id=${document.id}">Изменить</a></td>
            <td><a href="documents/delete?id=${document.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
