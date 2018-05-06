<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<div class="app-aside hidden-xs bg-black">
    <div class="aside-wrap ">
        <div class="navi-wrap">
            <nav class="navi">
                <ul id="a_nav" class="nav">
                    <li>
                        <a href="<%=request.getContextPath()%>/admin">
                            <i class="glyphicon glyphicon-home text-dark"></i>
                            <span>首页</span>
                        </a>
                    </li>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('content')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-picture text-primary"></i>
                                <span>图文</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article')})">
                                    <li class="${param.index eq "content_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/content/article/article">
                                            <span>文章管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_articleCategory')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/content/article/category">
                                            <span>文章分类</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_image')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/content/image">
                                            <span>图片管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/content/imageCategory">
                                            <span>图片分类</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-briefcase text-danger"></i>
                                <span>营销</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                            <span>意见反馈</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('system')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-wrench text-info-lter"></i>
                                <span>系统</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_dict')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/dict">
                                            <span>字典管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_resource')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/secResource">
                                            <span>资源管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_role')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/secRole">
                                            <span>角色管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_organization')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/secOrganization">
                                            <span>组织管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_staff')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/secStaff">
                                            <span>员工管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_log')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/secStaffLog">
                                            <span>系统日志</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_config')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/systemConfig">
                                            <span>配置管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_version')})">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/admin/system/version">
                                            <span>版本管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>

                    <li>
                        <a class="auto">
                            <i class="glyphicon glyphicon-file text-warning"></i>
                            <span>订单</span>
                        </a>
                        <ul class="nav nav-sub">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                <li>
                                    <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                        <span>订单管理</span>
                                    </a>
                                </li>
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                <li>
                                    <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                        <span>还款管理</span>
                                    </a>
                                </li>
                            </sec:authorize>
                        </ul>
                    </li>
                    <li>
                        <a class="auto">
                            <i class="glyphicon glyphicon-user text-success"></i>
                            <span>客户</span>
                        </a>
                        <ul class="nav nav-sub">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                <li>
                                    <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                        <span>客户管理</span>
                                    </a>
                                </li>
                            </sec:authorize>
                        </ul>
                    </li>
                    <li>
                        <a class="auto">
                            <i class="glyphicon glyphicon-hdd text-info-dker"></i>
                            <span>产品</span>
                        </a>
                        <ul class="nav nav-sub">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                <li>
                                    <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                        <span>产品类型</span>
                                    </a>
                                </li>
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                <li>
                                    <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                        <span>产品管理</span>
                                    </a>
                                </li>
                            </sec:authorize>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>