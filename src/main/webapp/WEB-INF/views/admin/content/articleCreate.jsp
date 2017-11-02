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
    <title>Title</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/ueditor.jsp" %>
    <%@include file="../common/treeview.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">新增文章</h1>
            <a href="${backUrl}" class="btn btn-default pull-right">返回</a>
            <button class="btn btn-success pull-right" style="margin-right: 5px"
                    onclick="submitForm()">
                确定
            </button>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">标题：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="title" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">顺序：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="seq" type="number" class="form-control" value="0"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">分类：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="category.name" class="form-control" readonly
                                   onclick="showCategoryModel()"/>
                            <input type="hidden" name="category.id">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">类型：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="type" class="form-control" onchange="typeChange(event)">
                                <xs:dictOptions key="articleType"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">是否展示：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="display" class="form-control">
                                <xs:dictOptions key="articleDisplay"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1 no-padder m-b-md text-right">
                            <label class="control-label">图标：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <xs:imageUploader identifier="articleIcon" name="image" folder="article"/>
                        </div>
                    </div>
                    <div id="richText">
                        <div id="container" style="height: 500px"></div>
                    </div>
                    <div id="link" style="display: none">
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">链接：</label>
                        </div>
                        <div class="col-xs-8 col-md-10 col-lg-11 m-b-md">
                            <input name="url" type="text" class="form-control"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="categoryModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章分类</h4>
            </div>
            <div class="modal-body">
                <div id="tree">
                    <p style="text-align: center">加载中...</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="treeData" type="text/plain">
    ${categoryTree}

</script>
<script>
    var ue = UE.getEditor('container', {initialFrameWidth: null});

    var $richText = $("#richText");
    var $link = $("#link");
    var $form = $("#createForm");
    var $categoryModel = $("#categoryModel");
    $(function () {
        var treeData = JSON.parse($("#treeData").text());
        $('#tree').treeview({
            data: treeData,
            onNodeSelected: function (event, data) {
                $form.xsSetInput("category.name", data.name);
                $form.xsSetInput("category.id", data.id);
                $categoryModel.modal("hide");
            }
        });
    });

    function showCategoryModel() {
        $categoryModel.modal("show");
    }

    function typeChange(e) {
        if ($(e.target).val() == 0) {
            $link.css("display", "none");
            $richText.css("display", "block");
        } else {
            $richText.css("display", "none");
            $link.css("display", "block");
        }
    }

    function submitForm() {
        doPost('<%=request.getContextPath()%>/admin/content/article/save',
            {
                title: $form.xsGetInput("title"),
                seq: $form.xsGetInput("seq"),
                type: $form.xsGetInput("type"),
                display: $form.xsGetInput("display"),
                image: $form.xsGetInput("image"),
                "category.id": $form.xsGetInput("category.id"),
                url: (parseInt($form.xsGetInput("type")) === 0) ? undefined : $form.xsGetInput("url"),
                content: (parseInt($form.xsGetInput("type")) === 1) ? undefined : ue.getContent(),
                lock: 0
            }, function (data) {
                if (data.status) {
                    alert("新增成功");
                    window.location.href = '${backUrl}';
                } else {
                    alert(data.msg);
                }
            });
    }
</script>
</body>
</html>
