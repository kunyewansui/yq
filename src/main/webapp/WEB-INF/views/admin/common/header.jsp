<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="app-header-fixed ">
<div class="app-header navbar">
    <div class="navbar-header bg-black" style="max-height: 50px;height:50px;">
        <button class="pull-right visible-xs dk" data-toggle-class="show" data-toggle-target=".navbar-collapse">
            <i class="glyphicon glyphicon-cog"></i>
        </button>
        <button class="pull-right visible-xs" data-toggle-class="off-screen" data-toggle-target=".app-aside">
            <i class="glyphicon glyphicon-align-justify"></i>
        </button>
        <a href="<%=request.getContextPath()%>/" class="navbar-brand text-lt hidden-xs" style="display: block;margin: 0 auto">
            <img style="display: block;margin: 8px auto 0 auto;max-height: 36px;max-width: 150px;"
                 src="<%=request.getContextPath()%>/assets/admin/logo.png"/>
        </a>
    </div>
    <div class="collapse pos-rlt navbar-collapse box-shadow bg-white-only">
        <div id="t_nav" class="nav navbar-nav">
                <a href="<%=request.getContextPath()%>/admin/content" class="btn no-shadow navbar-btn">
                    图文
                </a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle clear">
                    <span class="hidden-sm hidden-md"></span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu animated fadeInRight w">
                    <li>
                        <a href="javascript:logout()">登出</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</div>
<script>
    function logout() {
        doPost("<%=request.getContextPath()%>/admin/logout", {}, function (data) {
            if (data.status) {
                window.location.href = "<%=request.getContextPath()%>/admin/login";
            }
        })
    }
</script>