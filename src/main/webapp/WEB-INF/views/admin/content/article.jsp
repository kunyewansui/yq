<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@include file="./content_nav.jsp" %>

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
                            <input name="dynamic[name]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.name}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">类型：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="type" class="form-control" data-value="${search.type}">
                                <option value="">全部</option>
                                <option value="0">富文本</option>
                                <option value="1">链接</option>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">创建时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">至：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">修改时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">至：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" class="form-control datepicker" readonly>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">关键词：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[content]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.content}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">是否上架：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="display" class="form-control" data-value="${search.display}">
                                <option value="">全部</option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                        <%--data-ignore设为true,执行$(form).xsClean()会排除该项--%>
                        <input type="hidden" name="category.id" value="${search.category.id}" data-ignore="true">
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <a href="<%=request.getContextPath()%>/admin/content/article/article/create"
                               class="btn btn-success pull-left">新增</a>
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
                            <th>是否上架</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="5">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="article">
                            <tr>
                                <td>${article.title}</td>

                                <td><img src="${article.image}"></td>

                                <td>${article.seq}</td>


                                <td>${article.display eq 0?"下架":"上架"}</td>
                                <td>
                                    <c:if test="${article.display eq 0}">
                                        <button class="btn btn-success btn-xs"
                                                onclick="updateArticle('${article.id}',1)">
                                            上架
                                        </button>
                                    </c:if>
                                    <c:if test="${article.display eq 1}">
                                        <button class="btn btn-danger btn-xs"
                                                onclick="updateArticle('${article.id}',0)">
                                            下架
                                        </button>
                                    </c:if>
                                    <a href="<%=request.getContextPath()%>/admin/content/article/article/update?id=${article.id}" class="btn btn-info btn-xs">
                                        编辑
                                    </a>
                                    <button class="btn btn-danger btn-xs"
                                            onclick="deleteArticle('${article.id}')">
                                        删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                ${__pagination__}
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
            onNodeSelected: function (event, data) {
                $("#searchForm input[name='category.id']").val(data.id);
                $searchForm.submit();
            },
            selectedId: '${search.category.id}'
        });
    });

    function updateArticle(id,status){
        doPost('<%=request.getContextPath()%>/admin/content/article/article/update',
            {
                id:id,
                display: status
            }, function (data) {
                if (data.status) {
                    window.location.reload(true);
                } else {
                    alert(data.msg);
                }
            });
    }

    function deleteArticle(id) {
        showDeleteModel("确认删除该文章？", function () {
            doPost("<%=request.getContextPath()%>/admin/content/article/article/remove", {id: id}, function (data) {
                if (data.status) {
                    setTimeout(function () {
                        alert("删除成功");
                        window.location.reload(true);
                    }, 380);
                } else {
                    alert(data.msg);
                }
            })
        })
    }
</script>
</body>
</html>