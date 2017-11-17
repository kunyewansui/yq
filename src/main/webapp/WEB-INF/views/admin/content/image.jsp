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
    <title>图片管理</title>
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
            <h1 class="m-n font-thin h3 inline">图片管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12 col-md-5 col-lg-3">
                <h4>图片分类</h4>
                <div id="tree">
                    <p style="text-align: center">加载中...</p>
                </div>
            </div>
            <div class="col-xs-12 col-md-7 col-lg-9">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[name]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.name}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">展示：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="display" class="form-control" data-value="${search.display}">
                                <option value="">全部</option>
                                <xs:dictOptions key="imageDisplay"/>
                            </select>
                        </div>
                        <%--data-ignore设为true,执行$(form).xsClean()会排除该项--%>
                        <input type="hidden" name="category.id" value="${search.category.id}" data-ignore="true">
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_create')})">
                            <a href="#" onclick="showCreateModal();return false"
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
                            <th>名称</th>
                            <th>图片</th>
                            <th>链接</th>
                            <th>顺序</th>
                            <th>展示</th>
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
                                <td>${item.name}</td>
                                <td><img style="max-width: 200px" src="${item.url}"></td>
                                <td><a href="${item.link}">${item.link}</a></td>
                                <td>${item.seq}</td>
                                <td><xs:dictDesc key="imageDisplay" value="${item.display}"/></td>
                                <td>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_update')})">
                                    <a href="#" onclick="updateListItem('${item.id}');return false;"
                                       class="btn btn-info btn-xs">
                                        编辑
                                    </a>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_delete')})">
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

<script id="treeData" type="text/plain">
    ${categoryTree}
</script>

<script>
    var $searchForm = $("#searchForm");
    var categoryTreeCallBackType;
    var selectedId;
    $(function () {
        var treeData = JSON.parse($("#treeData").text());
        $('#tree').treeview({
            data: [{
                "name": "全部",
                "id": "",
                "children": treeData
            }],
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

        $("#categoryTree").treeview({
            data: treeData,
            onNodeSelected: function (event, node) {
                if (categoryTreeCallBackType === 0) {//新增
                    $createForm.xsSetInput("category.name", node.name);
                    $createForm.xsSetInput("category.id", node.id);
                    $("#categoryModel").modal('hide');
                    $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                    $createForm.validate().element($("#createCategoryName"));
                } else {//修改
                    if (selectedId === node.id) {
                        alert("不能选自己作为父级");
                        $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                    } else {
                        $updateForm.xsSetInput("category.name", node.name);
                        $updateForm.xsSetInput("category.id", node.id);
                        $("#categoryModel").modal('hide');
                        $("#categoryTree").treeview("unselectNode", $("#categoryTree").treeview("getSelected")[0].id);
                        $updateForm.validate().element($("#updateCategoryName"));
                    }
                }
            }
        });
    });
    function showCategoryTree(type) {
        categoryTreeCallBackType = type;
        $("#categoryTree").treeview('expandAll');
        $("#categoryModel").modal('show');
    }
</script>

<sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_create')})">
<div class="modal fade" id="createModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增</h4>
            </div>
            <form name="createForm" class="form-horizontal" style="max-width: 800px">
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">名称：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="name" type="text" maxlength="255" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">图片：</label>
                        </div>
                        <div class="col-xs-9">
                            <xs:imageUploader identifier="create" name="url" folder="banner"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label">链接：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="link" type="text" maxlength="255" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">分类：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="category.id" type="hidden" value="">
                            <input id="createCategoryName" type="text" name="category.name" class="form-control" readonly onclick="showCategoryTree(0)">
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
                            <label class="control-label required">展示：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="display" class="form-control">
                                <xs:dictOptions key="imageDisplay" value="1"/>
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
                notEmpty: true,
                maxlength: 255
            },
            url: {
                required: true,
                notEmpty: true,
                maxlength: 255
            },
            seq: {
                required: true
            },
            "category.name": {
                required: true,
                notEmpty: true,
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            url: {
                required: "url不能为空",
                notEmpty: "url不能为空",
                maxlength: "url最多255个字"
            },
            seq: {
                required: "顺序不能为空"
            },
            "category.name": {
                required: "分类不能为空",
                notEmpty: "分类不能为空"
            }
        },
        submitHandler: function () {
            $createSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/content/image/save",
                $createForm.serialize(),
                function (data) {
                    $createSubmit.attr("disabled", false);
                    if (data.status) {
                        $("#createModel").modal("hide");
                        setTimeout(function () {
                            alert("新增成功");
                            window.location.reload(true);
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
            $createForm[0].reset();
            createValidator.resetForm();
        })
    });
</script>
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_update')})">
<div class="modal fade" id="updateModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <form name="updateForm" class="form-horizontal" style="max-width: 800px">
                <div class="modal-body">
                    <input type="hidden" name="id">
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">名称：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="name" type="text" maxlength="255" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">图片：</label>
                        </div>
                        <div class="col-xs-9">
                            <xs:imageUploader identifier="update" name="url" folder="banner"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label">链接：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="link" type="text" maxlength="255" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">分类：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="category.id" type="hidden" value="">
                            <input id="updateCategoryName" type="text" name="category.name" class="form-control" readonly
                                   onclick="showCategoryTree(1)">
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
                            <label class="control-label required">展示：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="display" class="form-control">
                                <xs:dictOptions key="imageDisplay"/>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="updateSubmit" type="submit" class="btn btn-success">确定</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    var $updateForm = $("form[name=updateForm]");
    var $updateSubmit = $("#updateSubmit");
    var updateValidator = $updateForm.validate({
        rules: {
            name: {
                required: true,
                notEmpty: true,
                maxlength: 255
            },
            url: {
                required: true,
                notEmpty: true,
                maxlength: 255
            },
            seq: {
                required: true
            },
            "category.name": {
                required: true,
                notEmpty: true,
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            url: {
                required: "url不能为空",
                notEmpty: "url不能为空",
                maxlength: "url最多255个字"
            },
            seq: {
                required: "顺序不能为空"
            },
            "category.name": {
                required: "分类不能为空",
                notEmpty: "分类不能为空"
            }
        },
        submitHandler: function () {
            $updateSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/content/image/update",
                $updateForm.serialize(),
                function (data) {
                    $updateSubmit.attr("disabled", false);
                    if (data.status) {
                        $("#updateModel").modal("hide");
                        setTimeout(function () {
                            alert("修改成功");
                            window.location.reload(true);
                        }, 380);

                    } else {
                        alert(data.msg);
                    }
                }, function (XMLHttpRequest, textStatus) {
                    $updateSubmit.attr("disabled", false);
                    alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
                });
        }
    });

    function updateListItem(id) {
        doPost("<%=request.getContextPath()%>/admin/content/image/get", {id: id}, function (data) {
            if (data.status) {
                $updateForm.xsSetForm(data.data);
                putImageIntoImageUploader("update",data.data.url);
                $updateForm.xsSetInput("category.id", data.data.category.id);
                $updateForm.xsSetInput("category.name", data.data.category.name);
                $("#updateModel").modal("show");
            } else {
                alert(data.msg);
            }
        });
    }

    $(function () {
        $("#updateModel").on('hide.bs.modal', function () {
            $updateForm[0].reset();
            updateValidator.resetForm();
        })
    });

    function simpleUpdateListItem(id, status) {
        doPost('<%=request.getContextPath()%>/admin/content/image/update',
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
</script>
</sec:authorize>
<sec:authorize access="hasAnyRole(${xs:getPermissions('content_image_delete')})">
<%@include file="../common/deleteConfirm.jsp" %>
<script>
    function deleteListItem(id) {
        showDeleteModel(null, function () {
            doPost("<%=request.getContextPath()%>/admin/content/image/remove", {id: id}, function (data) {
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
</sec:authorize>

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
</body>
</html>
