<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
<%--    <meta charset="utf-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="${pageContext.request.contextPath}/"/>

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a>
        </h2>
<%-- toDo переделать pageContext на c:out (везде где выводится текст) во всех jsp--%>
    </c:if>

</div>
<div>
    <a href="documents/create">Add document</a>
</div>

<div class="table-responsive">
    <table class="table table-bordered table-striped table-hover">
        <thead >
        <tr>
            <th><c:out value="name"/></th>
            <th><c:out value="description"/></th>
            <th><c:out value="author"/></th>
            <th><c:out value="created"/></th>
            <th><c:out value="sharing"/></th>
            <th><c:out value="update"/></th>
            <th><c:out value="delete"/></th>
        </tr>
        </thead>
        <c:forEach items="${documents}" var="document">
<%--            <jsp:useBean id="document" scope="page" type="org.zhezlov.documentarchive.model.Document"/>--%>
            <tr>
                <td>${document.name} </td>
                <td>${document.description}</td>
                <td>${document.authorId}</td>
                <td>${document.dateTimeCreated}</td>
                <td><a href="documents/sharing?id=${document.id}">sharing</a></td>
                <td><a href="documents/update?id=${document.id}">update</a></td>
                <td><a href="documents/delete?id=${document.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>