<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Staff: GustinLau
  Date: 2017-04-04
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>管理后台</title>
    <%@include file="./common/head.jsp" %>
</head>
<body>
<div class="app app-header-fixed app-aside-fixed" style="padding-top: 0">
    <%@include file="./common/header.jsp" %>
    <div class="app-aside hidden-xs bg-black">
        <div class="aside-wrap ">
        </div>
    </div>
    <div class="app-content ">
        <div class="app-content-body">
        </div>
    </div>
</div>
<script>
    var topLinks=$("#t_nav").find("a");
    if(topLinks.length>0){
        window.location.href=$(topLinks[0]).attr("href");
    }
</script>
</body>
</html>

