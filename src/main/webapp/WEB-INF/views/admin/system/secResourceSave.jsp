<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%--
  Created by IntelliJ IDEA.
  User: xuxiaowei
  Date: 2017/11/3
  Time: 下午9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>Title</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/ztree.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">新增资源</h1>
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
                            <label class="control-label required">名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="name" type="text" class="form-control"
                                   validate-required="true|名称不能为空" validate-maxlength="255|名称最大长度为" validate-notEmpty="true|fs"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">键：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="key" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">父级：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input type="text" name="parent.name" class="form-control" readonly
                                   onclick="showParentModel()"/>
                            <input type="hidden" name="parent.id">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">类型：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="type" class="form-control" onchange="typeChange(event)">
                                <xs:dictOptions key="secResourceType"/>
                            </select>
                        </div>
                        <div id="request" style="display: none">
                            <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                                <label class="control-label required">url：</label>
                            </div>
                            <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                                <input name="url" type="text" class="form-control"/>
                            </div>
                            <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                                <label class="control-label required">请求方法：</label>
                            </div>
                            <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                                <input name="method" type="text" class="form-control"/>
                            </div>
                        </div>
                        <div id="icon">
                            <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                                <label class="control-label required">图标：</label>
                            </div>
                            <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                                <input name="icon" type="text" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">顺序：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="seq" type="number" class="form-control" value="0"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">描述：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="desc" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">可分配：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="assign" class="form-control" onchange="typeChange(event)">
                                <xs:dictOptions key="secResourceAssign"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label required">记录日志：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="log" class="form-control">
                                <xs:dictOptions key="secResourceLog"/>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="resourceTree" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">父级</h4>
            </div>
            <div class="modal-body">
                <ul id="tree" class="ztree" style="overflow:auto;height: 500px"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" onclick="selectConfirm()">确定</button>
            </div>
        </div>
    </div>
</div>
<script>
    var zTreeObj,
        setting = {
            data: {
                key: {
                    children: "sons"
                }
            },
            view: {
                selectedMulti: false
            }
        },
        zTreeNodes = [{"name": "顶级", "id": 0, "sons":${tree eq null ? "[]":tree}}];

    $(document).ready(function () {
        zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);

    });

    function selectConfirm() {
        var selectedNode = zTreeObj.getSelectedNodes()[0];
        if (treeType === P_TYPE_CREATE) {
            $("#createPid").val(selectedNode.id);
            $('#createPName').val(selectedNode.name);
        } else {
            $("#editPid").val(selectedNode.id);
            $('#editPName').val(selectedNode.name);
        }
        $('#resourceTree').modal('hide');
    }

    $('#resourceTree').on('hidden.bs.modal', function (e) {
        zTreeObj.selectNode(zTreeNodes[0]);
        zTreeObj.expandAll(false);
    });
</script>

<script>
    var $createForm = $("#createForm");
    var $parentModel = $("#parentModel");
    var $request = $("#request");
    var $icon = $("#icon");

    function showParentModel() {
        $parentModel.modal("show");
    }

    function typeChange(e) {
        if ($(e.target).val() == 2) {
            $request.css("display", "block");
            $icon.css("display", "none");
        } else {
            $request.css("display", "none");
            $icon.css("display", "block");
        }
    }

    $createForm.xsValidate(function () {
        var data = $createForm.xsJson();
        data.url = (parseInt($createForm.xsGetInput("url")) === 2) ? $createForm.xsGetInput("url") : undefined;
        data.method = (parseInt($createForm.xsGetInput("method")) === 2) ? $createForm.xsGetInput("method") : undefined;
        data.icon = (parseInt($createForm.xsGetInput("icon")) === 2) ? undefined : $createForm.xsGetInput("icon");
        doPost('<%=request.getContextPath()%>/admin/security/secResource/save',
            data,
            function (data) {
                if (data.status) {
                    alert("新增成功");
                    window.location.href = '${backUrl}';
                } else {
                    alert(data.msg);
                }
            });
    });

    function submitForm() {
        $createForm.submit();
    }
</script>
</body>
</html>
