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
                        <a href="<%=request.getContextPath()%>/admin/content/article/article">
                            <i class="fa fa-empire"></i>
                            <span>文章管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/admin/content/article/category">
                            <i class="fa fa-empire"></i>
                            <span>文章分类</span>
                        </a>
                    </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/image/manage">
                                <i class="fa fa-empire"></i>
                                <span>图片分类</span>
                            </a>
                        </li>
                </ul>
            </nav>
        </div>
    </div>
</div>