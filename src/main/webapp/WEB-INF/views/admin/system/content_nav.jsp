<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="app-aside hidden-xs bg-black">
    <div class="aside-wrap ">
        <div class="navi-wrap">
            <nav class="navi">
                <ul id="a_nav" class="nav">
                    <li>
                        <a href="<%=request.getContextPath()%>/admin/system/version">
                            <i class="fa fa-empire"></i>
                            <span>版本管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/admin/system/dict">
                            <i class="fa fa-empire"></i>
                            <span>字典管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/admin/system/secResource">
                            <i class="fa fa-empire"></i>
                            <span>资源管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/admin/system/secRole">
                            <i class="fa fa-empire"></i>
                            <span>角色管理</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>