<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
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
            <th>name</th>
            <th>description</th>
            <th>author</th>
            <th>cteated</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${documents}" var="document">
<%--            <jsp:useBean id="document" scope="page" type="org.zhezlov.documentarchive.model.Document"/>--%>
            <tr>
                <td>${document.name} </td>
                <td>${document.description}</td>
                <td>${document.authorId}</td>
                <td>${document.dateTimeCreated}</td>
                <td><a href="documents/update?id=${document.id}">update</a></td>
                <td><a href="documents/delete?id=${document.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<%----%>
<%--<jsp:include page="docs.jsp"/>--%>
</body>
</html>