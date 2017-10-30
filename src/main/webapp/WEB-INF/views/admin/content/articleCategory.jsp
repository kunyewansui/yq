<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>文章分类</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/treeview.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>
<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">文章分类</h1>
            <button class="btn btn-success pull-right" type="button" onclick="showCreateModal()">新增</button>
        </div>
        <div class="wrapper-md">
            <div class="row">
                <div class="col-xs-12 col-md-5 col-lg-3">
                    <h4>文章分类</h4>
                    <div id="tree">
                        <p style="text-align: center">加载中...</p>
                    </div>
                </div>
                <div class="col-xs-12 col-md-7 col-lg-9">
                    <form name="saveForm" class="form-horizontal" style="max-width: 800px">
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required ">编号：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="id" type="text" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">名称：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="name" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">KEY：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="key" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">顺序：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="seq" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">是否显示：</label>
                            </div>
                            <div class="col-xs-9">
                                <select name="display" class="form-control">
                                    <option value=""></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">上级分类：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="parent.id" type="hidden">
                                <input name="parent.name" type="text" class="form-control" readonly>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">顺序：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="seq" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-12 text-right">
                                <button class="btn btn-info" type="button" onclick="saveCategory()">保存</button>
                                <button class="btn btn-danger" type="button" onclick="deleteCategory()">删除</button>
                            </div>
                        </div>
                    </form>
                    <div class="clearfix"></div>
                </div>
            </div>

        </div>
    </div>
</div>


<script>
    var $form = $("form[name=saveForm]");
    var treeData;
    $(function () {
        updateTree();
    });

    function updateTree() {
        $form.xsClean();
        $form.xsDisable();
        doPost("<%=request.getContextPath()%>/admin/content/article/category/tree", {}, function (data) {
            if (data.status) {
                treeData = data.data;
                $('#tree').treeview({
                    data: treeData,
                    onNodeSelected: function (event, data) {
                        getCategory(data.id);
                    }
                });
            } else {
                alert(data.msg);
            }
        })
    }

    function getCategory(id) {
        doPost("<%=request.getContextPath()%>/admin/content/article/category/get", {id: id}, function (data) {
            if (data.status) {
                var category = data.data;
                $form.xsSetInput("id", category.id);
                $form.xsSetInput("name", category.name);
                $form.xsSetInput("key", category.key);
                $form.xsSetInput("seq", category.seq);
                if (category.parent !== undefined) {
                    $form.xsSetInput("parent.id", category.parent.id);
                    $form.xsSetInput("parent.name", category.parent.name);
                }
                $form.xsEnable();
            } else {
                alert(data.msg);
            }
        })
    }


    function saveCategory() {
        <%--doPost("<%=request.getContextPath()%>/image/category/save", {id: id}, function (data) {--%>
        <%--if (data.status) {--%>
        <%--var category = data.data;--%>
        <%--getElementInForm.call($form, "id").val(category.id);--%>
        <%--getElementInForm.call($form, "name").val(category.name);--%>
        <%--getElementInForm.call($form, "key").val(category.key);--%>
        <%--getElementInForm.call($form, "seq").val(category.seq);--%>
        <%--if (category.parent !== undefined) {--%>
        <%--getElementInForm.call($form, "parent.id").val(category.parent.id);--%>
        <%--getElementInForm.call($form, "parent.name").val(category.parent.name);--%>
        <%--}--%>
        <%--enableForm.call($form);--%>
        <%--} else {--%>
        <%--alert(data.msg);--%>
        <%--}--%>
        <%--});--%>
    }


</script>


<%@include file="../common/deleteConfirm.jsp" %>
<script>
    function deleteCategory() {
        showDeleteModel(null, function () {
            alert("confirm");
        });
    }
</script>

<div class="modal fade" id="createModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增文章分类</h4>
            </div>
            <form name="createForm" class="form-horizontal" style="max-width: 800px">
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">名称：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="name" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">顺序：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="seq" type="number" min="0" step="1" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">父级：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="parent.id" type="hidden">
                            <input name="parent.name" type="text" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">KEY：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="key" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">是否显示：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="display" class="form-control">
                                <option value=""></option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">图标：</label>
                        </div>
                        <div class="col-xs-9">
                            <jsp:include page="../common/imageUploader.jsp">
                                <jsp:param name="id" value="test"/>
                                <jsp:param name="name" value="url"/>
                                <jsp:param name="folder" value="article"/>
                            </jsp:include>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">预览前缀：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="prefix" type="text" class="form-control">
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-danger">确定</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $createForm = $("form[name=createForm]");
    $createForm.validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: "qin"
            }
        },
        submitHandler: function (form) {
            <%--submitBtn.attr("disabled",true);--%>
            <%--doPost("<%=request.getContextPath()%>/admin/login", $(form).serialize(),--%>
            <%--function (data) {--%>
            <%--if (data.status) {--%>
            <%--window.location.href = "<%=request.getContextPath()%>/admin/index"--%>
            <%--} else {--%>
            <%--$("#errorText").html(data.msg);--%>
            <%--}--%>
            <%--submitBtn.attr("disabled",false);--%>
            <%--}, function (XMLHttpRequest, textStatus) {--%>
            <%--submitBtn.attr("disabled",false);--%>
            <%--alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);--%>
            <%--});--%>
        }
    });
    function showCreateModal() {
        $("#createModel").modal("show");
    }
    <%--function createCategory() {--%>
    <%--doPost("<%=request.getContextPath()%>/image/category/save", $createForm.serialize(), function (data) {--%>
    <%--if (data.status) {--%>
    <%--updateTree();--%>
    <%--alert("新增成功");--%>
    <%--} else {--%>
    <%--alert(data.msg);--%>
    <%--}--%>
    <%--}, function () {--%>
    <%--$createForm.reset()();--%>
    <%--alert(123);--%>
    <%--});--%>


    <%--}--%>
</script>

</body>
</html>
