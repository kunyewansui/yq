<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>字典管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">字典管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[name]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.name}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">键：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[key]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.key}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">锁定：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="lock" class="form-control" data-value="${search.lock}">
                                <option value="">全部</option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <a href="#" onclick="showCreateModal();return false"
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
                            <th>名称</th>
                            <th>键</th>
                            <th>锁定</th>
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
                                <td>${item.key}</td>
                                <td>${item.lock eq 0?"正常":"锁定"}</td>
                                <td>
                                    <c:if test="${item.lock eq 0}">
                                        <a href="#" class="btn btn-success btn-xs"
                                           onclick="updateLock('${item.id}',1);return false">
                                            锁定
                                        </a>
                                    </c:if>
                                    <c:if test="${item.lock eq 1}">
                                        <a href="#" class="btn btn-danger btn-xs"
                                           onclick="updateLock('${item.id}',0);return false">
                                            解锁
                                        </a>
                                    </c:if>
                                    <a href="#" onclick="editDict('${item.id}');return false;"
                                       class="btn btn-info btn-xs">
                                        编辑
                                    </a>
                                    <a href="#" class="btn btn-danger btn-xs"
                                       onclick="deleteDict('${item.id}');return false">
                                        删除
                                    </a>
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

<div class="modal fade" id="createModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增字典</h4>
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
                            <label class="control-label required">键：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="key" type="text" maxlength="191" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">锁定：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="lock" class="form-control">
                                <option value="1">是</option>
                                <option value="0">否</option>
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
            key: {
                required: true,
                notEmpty: true,
                maxlength: 191
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            key: {
                required: "键不能为空",
                notEmpty: "键不能为空",
                maxlength: "键最多191个字"
            }
        },
        submitHandler: function () {
            $createSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/system/dict/save",
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
            $createForm.xsClean();
            createValidator.resetForm();
        })
    });
</script>

<div class="modal fade" id="updateModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑字典</h4>
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
                            <label class="control-label required">键：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="key" type="text" maxlength="191" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">锁定：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="lock" class="form-control">
                                <option value="1">是</option>
                                <option value="0">否</option>
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
            key: {
                required: true,
                notEmpty: true,
                maxlength: 191
            },
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            name: {
                required: "键不能为空",
                notEmpty: "键不能为空",
                maxlength: "键最多191个字"
            }
        },
        submitHandler: function () {
            $updateSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/system/dict/update",
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


    function editVersion(id) {
        doPost("<%=request.getContextPath()%>/admin/system/dict/get", {id: id}, function (data) {
            if (data.status) {
                $updateForm.xsSetForm(data.data);
                $("#updateModel").modal("show");
            } else {
                alert(data.msg);
            }
        });
    }


    $(function () {
        $("#updateModel").on('hide.bs.modal', function () {
            $updateForm.xsClean();
            updateValidator.resetForm();
        })
    });
</script>


<%@include file="../common/deleteConfirm.jsp" %>
<script>
    function updateLock(id, status) {
        doPost('<%=request.getContextPath()%>/admin/system/dict/lock/update',
            {
                id: id
            }, function (data) {
                if (data.status) {
                    window.location.reload(true);
                } else {
                    alert(data.msg);
                }
            });
    }

    function deleteDict(id) {
        showDeleteModel("确认删除？", function () {
            doPost("<%=request.getContextPath()%>/admin/system/dict/remove", {id: id}, function (data) {
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
