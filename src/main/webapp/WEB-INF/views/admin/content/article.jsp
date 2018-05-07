<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
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
    <title>文章管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/treeview.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
</head>
<body>

<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="content_article"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">文章管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12 col-md-5 col-lg-3">
                <h4>文章分类</h4>
                <div id="tree">
                    <p style="text-align: center">加载中...</p>
                </div>
            </div>
            <div class="col-xs-12 col-md-7 col-lg-9">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">标题：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[title]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.title}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">类型：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="type" class="form-control" data-value="${search.type}">
                                <option value="">全部</option>
                                <xs:dictOptions key="articleType"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">创建时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="dynamic[createTimeStart]" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">至：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="dynamic[createTimeEnd]" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">修改时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="dynamic[updateTimeStart]" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">至：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="dynamic[updateTimeEnd]" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">关键词：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[content]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.content}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">状态：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="display" class="form-control" data-value="${search.display}">
                                <option value="">全部</option>
                                <xs:dictOptions key="articleDisplay"/>
                            </select>
                        </div>
                        <%--data-ignore设为true,执行$(form).xsClean()会排除该项--%>
                        <input type="hidden" name="category.id" value="${search.category.id}" data-ignore="true">
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article_create')})">
                            <a href="<%=request.getContextPath()%>/admin/content/article/article/create"
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
                            <th>标题</th>
                            <th>图片</th>
                            <th>顺序</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="5">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.title}</td>
                                <td><img style="max-width:100px;max-height: 50px;" src="${item.image}"></td>
                                <td>${item.seq}</td>
                                <td><xs:dictDesc key="articleDisplay" value="${item.display}"/></td>
                                <td>
                                    <a class="btn btn-primary btn-xs" target="_blank" href="<c:url value='${item.url}'/>">预览</a>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article_update')})">
                                    <c:if test="${item.display eq 0}">
                                        <button class="btn btn-success btn-xs"
                                                onclick="simpleUpdateListItem('${item.id}',1)">
                                            <xs:dictDesc key="articleDisplay" value="1"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${item.display eq 1}">
                                        <button class="btn btn-danger btn-xs"
                                                onclick="simpleUpdateListItem('${item.id}',0)">
                                            <xs:dictDesc key="articleDisplay" value="0"/>
                                        </button>
                                    </c:if>
                                    <a href="<%=request.getContextPath()%>/admin/content/article/article/update?id=${item.id}"
                                       class="btn btn-info btn-xs">
                                        编辑
                                    </a>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article_delete')})">
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
<script id="treeData" type="text/plain">
[{
    "name":"全部",
    "id":"",
    "children":${categoryTree}
}]
</script>

<script>
    var $searchForm = $("#searchForm");
    $(function () {
        var treeData = JSON.parse($("#treeData").text());
        $('#tree').treeview({
            data: treeData,
            onNodeSelected: function (event, node) {
                if (node.id !== '${search.category.id}') {
                    $("#searchForm input[name='category.id']").val(node.id);
                    $searchForm.submit();
                }
            },
            toggle: false
        });
        $("#tree").treeview('expandAll');
        $("#tree").treeview('selectNode', '${search.category.id}');
    });

    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article_update')})">
    function simpleUpdateListItem(id, status) {
        doPost('<%=request.getContextPath()%>/admin/content/article/article/update',
            {
                id: id,
                display: status
            }, function (data) {
                if (data.status) {
                    window.location.reload(true);
                } else {
                    alert(data.msg);
                }
            });
    }
    </sec:authorize>
    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_article_delete')})">
    function deleteListItem(id) {
        showDeleteModel(null, function () {
            doPost("<%=request.getContextPath()%>/admin/content/article/article/remove", {id: id}, function (data) {
                if (data.status) {
                    setTimeout(function () {
                        bootoast({message: "删除成功！"});
                        window.location.reload(true);
                    }, 380);
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
