<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 10/30/17
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>订单管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="order_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">订单管理</a>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12 col-md-12 col-lg-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">订单号：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="orderNo" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.orderNo}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">商户名：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="merchantName" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.merchantName}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">状态：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="status" class="form-control" data-value="${search.status}">
                                <option value="">全部</option>
                                <xs:dictOptions key="orderStatus"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1 no-padder m-b-md text-right">
                            <label class="control-label">创建时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-7 m-b-md">
                            <div class="row">
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[startTime]" class="form-control datepicker" readonly>
                                </div>
                                <label class="pull-left control-label" style="width: 15px">至</label>
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[endTime]" class="form-control datepicker" readonly>
                                </div>
                                <a class="pull-left control-label text-info js-date-quick" data-days="6">近7天</a>
                                <a class="pull-left control-label m-l-sm text-info js-date-quick"
                                   data-days="29">近30天</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('order_manage_create')})">
                            <a href="<%=request.getContextPath()%>/admin/order/order/toadd"
                               class="btn btn-success pull-left">新增</a>
                            </sec:authorize>
                            <input class="btn btn-info pull-right" value="搜索" type="submit">
                            <input class="btn btn-default pull-right  m-r-sm" value="重置" type="button"
                                   onclick="$('#searchForm').xsClean()">
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>客户名</th>
                            <th>订单金额</th>
                            <th>创建时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="6">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.orderNo}</td>
                                <td>${item.merchantName}</td>
                                <td><fmt:formatNumber value="${item.amount}" type="currency" minFractionDigits="0"/></td>
                                <td><fmt:formatDate pattern="yyyy/MM/dd hh:mm:ss" value="${item.createTime}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.status eq '0'}">
                                            <span class="text-info"><xs:dictDesc key="orderStatus" value="${item.status}"/></span>
                                        </c:when>
                                        <c:when test="${item.status eq '1'}">
                                            <span class="text-success"><xs:dictDesc key="orderStatus" value="${item.status}"/></span>
                                        </c:when>
                                        <c:when test="${item.status eq '2' or item.status eq '3'}">
                                            <span class="text-danger"><xs:dictDesc key="orderStatus" value="${item.status}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <xs:dictDesc key="orderStatus" value="${item.status}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('order_manage_update')})">
                                    <a href="<%=request.getContextPath()%>/admin/order/order/detail?id=${item.orderNo}"
                                       class="btn btn-warning btn-xs">
                                        查看详情
                                    </a>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('order_manage_remove')})">
                                    <button class="btn btn-danger btn-xs"
                                            onclick="deleteListItem('${item.id}')">
                                        删除
                                    </button>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <xs:pagination pageModel="${pageModel}"/>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/deleteConfirm.jsp" %>
<script>
    //快速选时间
    $(".js-date-quick").on("click", function () {
        var day = $(this).attr("data-days");
        var t = new Date();
        var inputs = $(this).parent().find("input.datepicker");
        inputs.eq(1).val(t.format("yyyy-MM-dd"));
        var t_s = t.getTime();
        t.setTime(t_s - 24 * 60 * 60 * 1000 * day);
        inputs.eq(0).val(t.format("yyyy-MM-dd"));
    });
</script>

<sec:authorize access="hasAnyRole(${xs:getPermissions('order_manage_remove')})">
<script>
    function deleteListItem(id) {
        showDeleteModel(null, function () {
            doPost("<%=request.getContextPath()%>/admin/order/order/remove", {id: id}, function (data) {
                if (data.status) {
                    bootoast({message: "删除成功！"});
                    setTimeout(function () {
                        window.location.reload(true);
                    }, 680);
                } else {
                    alert(data.msg);
                }
            })
        })
    }
</script>
</sec:authorize>

</body>
</html>
