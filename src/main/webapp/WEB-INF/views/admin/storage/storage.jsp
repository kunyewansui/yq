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
    <title>库存管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/page.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="storage_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=0" class="xs-nav text-base ${type eq 0?'xs-active':''}">全部库存</a>
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=1" class="xs-nav text-base ${type eq 1?'xs-active':''}">档口库存</a>
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=2" class="xs-nav text-base ${type eq 2?'xs-active':''}">工厂库存</a>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group ">
                        <div class="col-xs-3  pull-right">
                            <div class="input-group">
                                <input name="name" type="text" class="form-control" placeholder="产品名称/产品编号"
                                       value="${search.name}">
                                <span class="input-group-btn">
                                    <button class="btn btn-info" type="button" onclick="refreshList();">搜索</button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-2 text-right">
                            <label class="control-label">
                                总库存：${statistics.totalStock}
                            </label>
                        </div>
                        <div class="col-xs-2 text-right">
                            <label class="control-label">
                                产品款数：${statistics.totalCount}
                            </label>
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table id="pageTable" class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>款号</th>
                            <th>档口库存</th>
                            <th>工厂库存</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div id="pagination" class="xs-pagination"></div>
            </div>
        </div>
    </div>
</div>
<script id="tableTemplate" type="text/html">
    <tr>
        <td>{{code}}</td>
        <td>{{shopStock}}</td>
        <td>{{factoryStock}}</td>
        <td>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage_update')})">
                <a href="<%=request.getContextPath()%>/admin/storage/storage/update?id=${item.id}"
                   class="btn btn-warning btn-xs">
                    查看详情
                </a>
            </sec:authorize>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage_remove')})">
                <button class="btn btn-danger btn-xs"
                        onclick="deleteListItem('${item.id}')">
                    删除
                </button>
            </sec:authorize>
        </td>
    </tr>
</script>
<script>
    $(function () {
        $("#pagination").pagination({
            callback: function (current) {
                var params = $("#searchForm").xsJson();
                params.page = current;
                doGet("<%=request.getContextPath()%>/admin/storage/storage/list", params, function (data) {
                    if (data.status) {
                        $("#pagination").pagination("setPage", current, Math.ceil( data.data.total/10));
                        renderData("#pageTable", data.data.list, "#tableTemplate");
                    } else {
                        alert(data.msg);
                    }
                })
            }
        }).pagination("trigger");
    })

    function refreshList() {
        $("#pagination").pagination("trigger");
    }
</script>
</body>
</html>
