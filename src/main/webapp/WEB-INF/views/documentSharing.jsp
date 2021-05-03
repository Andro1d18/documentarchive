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

    <title><c:out value="Sharing document "/></title>

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
<div>
    <h2><c:out value=" Sharing document ${document.name}"/></h2>
</div>

<div>
    <form action="documents/sharing" method="post">
        <input type="hidden" name="id" value="${document.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="table-striped">
            <div class="row">
                <div class="col-md-1"><c:out value="for all users"/></div>
                <div class="col-md-1"><input type="checkbox" name="forAllUsers" value="true" id="idChk"
                                             onclick="myFunction()"></div>
            </div>
            <div class="row">
                <div class="col-md-1"><c:out value="or one user"/></div>
                <div class="col-md-1">
                    <select name="userId" id="myOption">
                        <c:forEach var="item" items="${users}">
                            <c:if test="${item.username != loggedUser}">
                                <option value="${item.id}"><c:out value="${item.username}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-1"><c:out value="Can view"/></div>
                <div class="col-md-1"><input type="checkbox" checked name="canView" value="true" id="idCanView"></div>
            </div>
            <div class="row">
                <div class="col-md-1"><c:out value="Can edit"/></div>
                <div class="col-md-1"><input type="checkbox" checked name="canEdit" value="true" id="idCanEdit"></div>
            </div>
            <div class="row">
                <div class="col-md-1"><c:out value="Can delete"/></div>
                <div class="col-md-1"><input type="checkbox" name="canDelete" value="true" id="idCanDelete"></div>
            </div>
        </div>
        <button type="submit" class="btn btn-default btn-lg" ><c:out value="Enable sharing"/></button>
    </form>
    <form action="documents/unsharing" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${document.id}">
        <button type="submit" class="btn btn-default btn-lg "><c:out value="Disable sharing for all users"/></button>
        <br>
<%--        <button onclick="window.history.back()" type="button">Cancel</button>--%>
    </form>
    <form method="get" action="cancel">
        <%--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
        <button style="margin-top: 10px" class="btn btn-default btn-lg"  type="submit">Cancel</button>
    </form>
</div>

<script>
    function myFunction() {
        if (document.getElementById("idChk").checked === true) {
            document.getElementById("myOption").disabled = true;
        } else {
            document.getElementById("myOption").disabled = false;
        }
    }
</script>
</body>
</html>
