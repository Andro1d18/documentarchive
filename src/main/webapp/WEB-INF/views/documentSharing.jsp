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
    <form action="documents/sharing" method="post">
        <input type="hidden" name="id" value="${document.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="table-striped">
            <div class="row">
                <div class="col-md-1"><c:out value="for all users"/></div>
                <div class="col-md-1"><input type="checkbox" name="forAllUsers" value="true" id="idChk"
                                             onclick="myFunction()"></div>
                <%--                <div  class="col-md-1">Customer Address</div>--%>
            </div>
            <div class="row">
                <div class="col-md-1"><c:out value="or one user"/></div>
                <div class="col-md-1">
                    <select name="userId" id="myOption">
                        <c:forEach var="item" items="${users}">
                            <c:if test="${item.username != loggedUser}">
                                <option value="${item.id}">${item.username}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <%--                <div class="col-md-1">003</div>--%>
            </div>
            <div class="row">
                <%--                <div class="col-md-1">xxx</div>--%>
                <%--                <div class="col-md-1">yyy</div>--%>
                <%--                <div class="col-md-1">www</div>--%>
            </div>
            <div class="row">
                <%--                <div class="col-md-1">ttt</div>--%>
                <%--                <div class="col-md-1">uuu</div>--%>
                <%--                <div class="col-md-1">Mkkk</div>--%>
            </div>
        </div>
        <button type="submit"><c:out value="Enable sharing"/></button>
    </form>
    <form action="documents/unsharing" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${document.id}">
        <button type="submit"><c:out value="Disable sharing for all users"/></button>
        <br>
        <button onclick="window.history.back()" type="button">Cancel</button>
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
