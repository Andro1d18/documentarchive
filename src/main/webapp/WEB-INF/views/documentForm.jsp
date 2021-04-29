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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<%--    <jsp:useBean id="document" type="org.zhezlov.documentarchive.model.Document" scope="request"/>--%>
    <h3></h3>
    <hr>
    <%--    <form method="post" action="documents/upload?${_csrf.parameterName}=${_csrf.token}" class="form-control-static" enctype="multipart/form-data">--%>
    <form:form cssClass="form-group-lg" method="post" action="documents/create" class="form-control-static" enctype="multipart/form-data" modelAttribute="uploadedFile">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <dl>
            <dt><c:out value="Description:"/></dt>
            <dd><input type="text" value="${document.description}" size=40 name="description" required></dd>
            <dd style="color: red; font-style: italic;">
                <form:errors path="description" /></dd>
        </dl>
            <dl>
                <dt><c:out value="File upload:"/></dt>
                <dd><input type="file" name="file" required></dd>
                <dd style="color: red; font-style: italic;">
                    <form:errors path="file" /></dd>
            </dl>
       <div class="row">
           <div>
               <button class="btn-default btn btn-lg" type="submit">Save</button>
                   <%--        <button onclick="window.history.back()" type="button">Cancel</button>--%>
               </form:form>
           </div>
           <div>
               <form method="get" action="cancel">
                   <%--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                   <button style="margin-left: 5px" class="btn btn-default btn-lg"  type="submit">Cancel</button>
               </form>
           </div>
       </div>
</section>
</body>
</html>
