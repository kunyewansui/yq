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
                    <li class="${index eq "index"?"active":""}">
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
                                    <li class="${index eq "content_article"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/content/article/article">
                                            <span>文章管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_articleCategory')})">
                                    <li class="${index eq "content_articleCategory"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/content/article/category">
                                            <span>文章分类</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_image')})">
                                    <li class="${index eq "content_image"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/content/image">
                                            <span>图片管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory')})">
                                    <li class="${index eq "content_imageCategory"?"active":""}">
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
                                <i class="fa fa-assistive-listening-systems text-danger"></i>
                                <span>营销</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing_feedback')})">
                                    <li class="${index eq "marketing_feedback"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/marketing/feedback">
                                            <span>意见反馈</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('system')})" >
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-wrench text-warning-lter"></i>
                                <span>系统</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_dict')})">
                                    <li class="${index eq "system_dict"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/dict">
                                            <span>字典管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_resource')})">
                                    <li class="${index eq "system_resource"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/secResource">
                                            <span>资源管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_role')})">
                                    <li class="${index eq "system_role"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/secRole">
                                            <span>角色管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_organization')})">
                                    <li class="${index eq "system_organization"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/secOrganization">
                                            <span>组织管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_staff')})">
                                    <li class="${index eq "system_staff"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/secStaff">
                                            <span>员工管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country')})">
                                    <li class="${index eq "system_country"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/country">
                                            <span>国家管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_log')})">
                                    <li class="${index eq "system_log"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/secStaffLog">
                                            <span>系统日志</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_config')})">
                                    <li class="${index eq "system_config"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/systemConfig">
                                            <span>配置管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('system_version')})">
                                    <li class="${index eq "system_version"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/system/version">
                                            <span>版本管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('storage')})">
                        <li>
                            <a class="auto">
                                <i class="fa fa-truck text-info-lter"></i>
                                <span>仓库</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('storage_manage')})">
                                    <li class="${index eq "storage_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/storage/storage">
                                            <span>库存管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('order')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-file text-success-lter"></i>
                                <span>订单</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('order_manage')})">
                                    <li class="${index eq "order_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/order/order">
                                            <span>订单管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment')})">
                                    <li class="${index eq "order_payment"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/order/payment">
                                            <span>还款管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('report')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-list-alt text-info-dker"></i>
                                <span>报表</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('report_manage')})">
                                    <li class="${index eq "report_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/report/report">
                                            <span>报表管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('merchant')})">
                        <li>
                            <a class="auto">
                                <i class="glyphicon glyphicon-user text-warning-dker"></i>
                                <span>客户</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('merchant_manage')})">
                                    <li class="${index eq "merchant_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/merchant/merchant">
                                            <span>客户管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('product')})">
                        <li>
                            <a class="auto">
                                <i class="fa fa-futbol-o text-primary-lter"></i>
                                <span>产品</span>
                            </a>
                            <ul class="nav nav-sub">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage')})">
                                    <li class="${index eq "product_manage"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/product/product">
                                            <span>产品管理</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('product_category')})">
                                    <li class="${index eq "product_category"?"active":""}">
                                        <a href="<%=request.getContextPath()%>/admin/product/category">
                                            <span>产品类型</span>
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
            </nav>
        </div>
    </div>
</div>