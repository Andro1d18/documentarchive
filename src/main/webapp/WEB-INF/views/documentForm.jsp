<%--
  Created by IntelliJ IDEA.
  User: zhezl_lx3d
  Date: 09.04.2021
  Time: 13:57
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

    <title>Add document</title>

    <link rel="stylesheet" href=${pageContext.request.contextPath}/resources/css/style.css?v=2">
    <link rel="stylesheet" href="webjars/bootstrap/4.6.0-1/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="webjars/datatables/1.10.24/css/dataTables.bootstrap4.min.css">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script type="text/javascript" src="webjars/jquery/3.6.0/jquery.min.js" defer></script>
    <script type="text/javascript" src="webjars/bootstrap/4.6.0-1/js/bootstrap.min.js" defer></script>
    <script type="text/javascript" src="webjars/datatables/1.10.24/js/jquery.dataTables.min.js" defer></script>
    <script type="text/javascript" src="webjars/datatables/1.10.24/js/dataTables.bootstrap4.min.js" defer></script>
</head>
<body>
<section>
    <jsp:useBean id="document" type="org.zhezlov.documentarchive.model.Document" scope="request"/>
    <h3></h3>
    <hr>
    <%--    <form method="post" action="documents/upload?${_csrf.parameterName}=${_csrf.token}" class="form-control-static" enctype="multipart/form-data">--%>
    <form method="post" action="documents/create" class="form-control-static" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${document.description}" size=40 name="description" required></dd>
        </dl>
            <dl>
                <dt>File upload:</dt>
                <dd><input type="file" name="file" required></dd>
            </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
