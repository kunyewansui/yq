<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%--
  Created by IntelliJ IDEA.
  User: xuxiaowei
  Date: 11/1/17
  Time: 4:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>图片分类管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/treeview.jsp" %>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">图片分类</h1>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_create')})">
                <button class="btn btn-success pull-right" type="button" onclick="showCreateModal()">新增</button>
            </sec:authorize>
        </div>
        <div class="wrapper-md">
            <div class="row">
                <div class="col-xs-12 col-md-5 col-lg-3">
                    <h4>图片分类</h4>
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
                                <label class="control-label required">键：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="key" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">上级分类：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="parent.id" type="hidden">
                                <input name="parent.name" type="text" class="form-control" readonly
                                       onclick="showCategoryTree(1)">
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
                                <label class="control-label required">展示：</label>
                            </div>
                            <div class="col-xs-9">
                                <select name="display" class="form-control">
                                    <xs:dictOptions key="imageCategoryDisplay"/>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-12 text-right">
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_lock')})">
                                    <a id="lockCategory" class="btn btn-danger" style="display:none" type="button"
                                       onclick="lockCategory(1)"><i class="fa fa-lock"></i>锁定
                                    </a>
                                    <a id="unlockCategory" class="btn btn-success" style="display:none" type="button"
                                       onclick="lockCategory(0)"><i class="fa fa-unlock-alt"></i>解锁
                                    </a>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_update')})">
                                    <button class="btn btn-info" type="button" onclick="updateItem()">保存</button>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_delete')})">
                                    <button class="btn btn-danger" type="button" onclick="deleteItem()">删除</button>
                                </sec:authorize>
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
    var selectedId;
    var categoryTreeCallBackType;
    $(function () {
        updateTree(true, false);
    });

    function updateTree(reset, remove) {
        if (reset) {
            $form.xsClean();
            $form.xsDisable();
        }
        if (remove) {
            $("#tree").treeview("remove");
            $("#categoryTree").treeview("remove");
            selectedId=undefined;
        }
        doPost("<%=request.getContextPath()%>/admin/content/imageCategory/tree", {}, function (data) {
            if (data.status) {
                treeData = data.data;
                $('#tree').treeview({
                    data: treeData,
                    onNodeSelected: function (event, node) {
                        if (selectedId !== node.id) {
                            selectedId = node.id;
                            getItem(selectedId);
                        }
                    },
                    toggle: false
                });

                if (selectedId !== undefined) {
                    $("#tree").treeview("selectNode", selectedId);
                }

                $("#categoryTree").treeview({
                    data: [{
                        "name": "无",
                        "id": "",
                        "children": treeData
                    }],
                    onNodeSelected: function (event, node) {
                        if (categoryTreeCallBackType === 0) {//新增
                            $createForm.xsSetInput("parent.name", node.name);
                            $createForm.xsSetInput("parent.id", node.id);
                            $("#categoryModel").modal('hide');
                            $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                        } else {//修改
                            if (selectedId === node.id) {
                                alert("不能选自己作为父级");
                                $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                            } else {
                                $form.xsSetInput("parent.name", node.name);
                                $form.xsSetInput("parent.id", node.id);
                                $("#categoryModel").modal('hide');
                                $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                            }
                        }
                    }
                });

            } else {
                alert(data.msg);
            }
        })
    }

    function getItem(id) {
        doPost("<%=request.getContextPath()%>/admin/content/imageCategory/get", {id: id}, function (data) {
            if (data.status) {
                $form.xsClean();
                var object = data.data;
                $form.xsSetForm(object);
                if (object.parent !== undefined) {
                    $form.xsSetInput("parent.id", object.parent.id);
                    $form.xsSetInput("parent.name", object.parent.name);
                } else {
                    $form.xsSetInput("parent.name", "无");
                }
                if (object.lock === 0) {
                    $("#lockCategory").css("display", '');
                    $("#unlockCategory").css("display", 'none');
                    $form.xsEnable();
                } else {
                    $("#lockCategory").css("display", 'none');
                    $("#unlockCategory").css("display", '');
                    $form.xsDisable();
                }
            } else {
                alert(data.msg);
                $form.xsEnable();
            }
        })
    }

    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_update')})">
    function updateItem() {
        doPost("<%=request.getContextPath()%>/admin/content/imageCategory/update",
            $form.serialize(),
            function (data) {
                if (data.status) {
                    setTimeout(function () {
                        updateTree(false, true);
                        alert("更新成功");
                    }, 380);
                } else {
                    alert(data.msg);
                }
            });
    }
    </sec:authorize>
    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_lock')})">
    function lockCategory(lock) {
        doPost("<%=request.getContextPath()%>/admin/content/imageCategory/lock",
            {
                id: selectedId
            },
            function (data) {
                if (data.status) {
                    getItem(selectedId);
                    alert(lock === 1 ? "锁定成功" : "解锁成功");
                } else {
                    alert(data.msg);
                }
            });
    }
    </sec:authorize>
</script>

<sec:authorize access="hasAnyRole(${xs:getPermissions('system_imageCategory_delete')})">
    <%@include file="../common/deleteConfirm.jsp" %>
    <script>
        function deleteItem() {
            showDeleteModel(null, function () {
                doPost("<%=request.getContextPath()%>/admin/content/imageCategory/remove", {id: selectedId}, function (data) {
                    if (data.status) {
                        selectedId = "";
                        updateTree(true, true);
                    } else {
                        alert(data.msg);
                    }
                });
            });
        }
    </script>
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('content_imageCategory_create')})">
    <div class="modal fade" id="createModel" data-backdrop="static" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增图片分类</h4>
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
                                <label class="control-label required">键：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="key" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">父级：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="parent.id" type="hidden" value="">
                                <input type="text" name="parent.name" class="form-control" readonly data-value="无" value="无"
                                       onclick="showCategoryTree(0)">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">顺序：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="seq" type="number" min="0" step="1" class="form-control" value="0"
                                       data-value="0">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">显示：</label>
                            </div>
                            <div class="col-xs-9">
                                <select name="display" class="form-control">
                                    <xs:dictOptions key="imageCategoryDisplay" value="1"/>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="createSubmit" type="submit" class="btn btn-success">确定</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
        var $createForm = $("form[name=createForm]");
        var $createSubmit = $("#createSubmit");
        var createValidator = $createForm.validate({
            rules: {
                name: {
                    required: true,
                    notEmpty: true
                },
                seq: {
                    required: true
                },
                key: {
                    required: true,
                    notEmpty: true
                }
            },
            messages: {
                name: {
                    required: "名称不能为空",
                    notEmpty: "名称不能为空"
                },
                seq: {
                    required: "顺序不能为空"
                },
                key: {
                    required: "Key不能为空",
                    notEmpty: "Key不能为空"
                }
            },
            submitHandler: function () {
                $createSubmit.attr("disabled", true);
                doPost("<%=request.getContextPath()%>/admin/content/imageCategory/save",
                    $createForm.serialize(),
                    function (data) {
                        $createSubmit.attr("disabled", false);
                        if (data.status) {
                            $("#createModel").modal("hide");
                            setTimeout(function () {
                                alert("新增成功");
                                updateTree(false);
                            }, 380);
                        } else {
                            alert(data.msg);
                        }
                    }, function (XMLHttpRequest, textStatus) {
                        $createSubmit.attr("disabled", false);
                        alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
                    });
            }
        });
        function showCreateModal() {
            $("#createModel").modal("show");
        }
        $(function () {
            $("#createModel").on('hide.bs.modal', function () {
                createValidator.resetForm();
                $createForm[0].reset();
            })
        });
    </script>
</sec:authorize>

<%--分类树--%>
<div class="modal fade" id="categoryModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">图片分类</h4>
            </div>
            <div class="modal-body">
                <div id="categoryTree">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function showCategoryTree(type) {
        categoryTreeCallBackType = type;
        $("#categoryTree").treeview('expandAll');
        $("#categoryModel").modal('show');
    }
</script>
</body>
</html>
