<%--
  Created by IntelliJ IDEA.
  User: zhezl_lx3d
  Date: 25.04.2021
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="webjars/viewerjs/ViewerJS/" defer></script>
    <base href="${pageContext.request.contextPath}/"/>
</head>
<body>
<iframe src="${contextPath}/webjars/viewerjs/ViewerJS/#../../../files/${filenameForFS}" style="position:fixed; top:0; left:0; bottom:0; right:0; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;" ></iframe>
<%--<iframe src="${contextPath}/files/${document.key}.pdf" width="700" height="550" ></iframe>--%>
</body>
</html>
