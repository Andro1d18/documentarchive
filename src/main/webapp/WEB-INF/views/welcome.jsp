<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>

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

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="${pageContext.request.contextPath}/"/>

    <title>Welcome</title>


</head>
<body>
<script type="text/javascript" src="${contextPath}/resources/js/document.js" defer></script>
<script type="text/javascript" src="${contextPath}/resources/js/common.js" defer></script>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h3 class="display-4">Welcome ${pageContext.request.userPrincipal.name} | <a class="display-4"
                                                                                     onclick="document.forms['logoutForm'].submit()">Logout</a>
        </h3>
        <%-- toDo переделать pageContext на c:out (везде где выводится текст) во всех jsp--%>
    </c:if>

</div>
<div>
    <h3><a href="documents/create"><c:out value="Add document"/></a></h3>
</div>

<div class="table-responsive">
    <table class="table table-bordered table-striped" id="datatable">
        <thead>
        <tr>
            <th><c:out value="name"/></th>
            <th><c:out value="description"/></th>
            <th><c:out value="author"/></th>
            <th><c:out value="created"/></th>
            <th><c:out value="sharing"/></th>
            <th><c:out value="update"/></th>
            <th><c:out value="delete"/></th>
            <th><c:out value="preview"/></th>
        </tr>
        </thead>
        <c:forEach items="${documents}" var="document">
            <%--            <jsp:useBean id="document" scope="page" type="org.zhezlov.documentarchive.model.Document"/>--%>
            <tr>
                <td><a href="documents/downloading?id=${document.id}">${document.name}</a></td>
                <td>${document.description}</td>
                <td>${document.authorId}</td>
                <td>${document.dateTimeCreated}</td>
                <td><a href="documents/sharing?id=${document.id}"><c:out value="sharing"/></a></td>
                <td><a href="documents/update?id=${document.id}"><c:out value="update"/></a></td>
                <td><a href="documents/delete?id=${document.id}"><c:out value="delete"/></a></td>
                <td>
                    <c:choose>
                        <c:when test="${document.isCanPreview()}">
                            <a href="preview?id=${document.id}"><c:out value="preview"/></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="not available" />
                        </c:otherwise>
                    </c:choose>
                </td>
                    <%--                <td><a href="preview?id=${document.id}"><c:out value="preview"/></a></td>--%>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>