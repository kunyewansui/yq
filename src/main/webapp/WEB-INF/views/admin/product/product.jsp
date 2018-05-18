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
    <title>产品管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/treeview.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="product_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">产品管理</a>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12 col-md-5 col-lg-3">
                <h5>产品分类</h5>
                <div id="tree">
                    <p style="text-align: center">加载中...</p>
                </div>
            </div>
            <div class="col-xs-12 col-md-7 col-lg-9">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">产品名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="name" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.name}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">款号：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="code" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.code}">
                        </div>
                        <%--data-ignore设为true,执行$(form).xsClean()会排除该项--%>
                        <input type="hidden" name="cateId" value="${search.cateId}" data-ignore="true">
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage_create')})">
                            <a href="<%=request.getContextPath()%>/admin/product/product/toadd"
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
                            <th>款号</th>
                            <th>图片</th>
                            <th>名称</th>
                            <th>类型</th>
                            <th>档口库存</th>
                            <th>工厂库存</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="8">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.code}</td>
                                <td><img style="max-width:100px;max-height: 50px;" src="${item.image}"></td>
                                <td>${item.name}</td>
                                <td>${item.cateName}</td>
                                <td>${item.shopStock}</td>
                                <td>${item.factoryStock}</td>
                                <td><fmt:formatDate pattern="yyyy/MM/dd hh:mm:ss" value="${item.createTime}"/></td>
                                <td>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage_update')})">
                                    <a href="<%=request.getContextPath()%>/admin/product/product/detail?id=${item.id}"
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
<script id="treeData" type="text/plain">${cateTree}</script>
<script>
    var $searchForm = $("#searchForm");
    $(function () {
        var treeData = JSON.parse($("#treeData").text());
        $('#tree').treeview({
            data: [{
                "name": "全部",
                "id": "0",
                "children": treeData
            }],
            onNodeSelected: function (event, node) {
                if (node.id != '${search.cateId}') {
                    $("#searchForm input[name='cateId']").val(node.id);
                    $searchForm.submit();
                }
            },
            toggle: false
        });
        $("#tree").treeview('expandAll');
        $("#tree").treeview('selectNode', '${search.cateId}');
    });

    <sec:authorize access="hasAnyRole(${xs:getPermissions('product_manage_remove')})">
    function deleteListItem(id) {
        showDeleteModel(null, function () {
            doPost("<%=request.getContextPath()%>/admin/product/product/remove", {id: id}, function (data) {
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
    </sec:authorize>
</script>
</body>
</html>
