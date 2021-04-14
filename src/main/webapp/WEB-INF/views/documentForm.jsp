<%--
  Created by IntelliJ IDEA.
  User: zhezl_lx3d
  Date: 09.04.2021
  Time: 13:57
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

    <title>Add document</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<section>
    <jsp:useBean id="document" type="org.zhezlov.documentarchive.model.Document" scope="request"/>
    <h3></h3>
    <hr>
    <form method="post" action="update" class="form-control-static">
        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${document.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${document.name}" name="name" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${document.description}" size=40 name="description" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</body>
</html>
