<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%--
  Created by IntelliJ IDEA.
  User: xuxiaowei
  Date: 10/30/17
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>字典详情</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="system_dict"/>
<%@include file="../common/content_nav.jsp" %>
<c:if test="${dict eq null}">
    <script>
        alert("字典不存在");
        window.history.back();
    </script>
</c:if>
<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">编辑字典</h1>
            <a href="${backUrl}" class="btn btn-default pull-right">返回</a>
            <button id="formSubmit" class="btn btn-info pull-right" style="margin-right: 5px"
                    onclick="submitForm()">
                保存
            </button>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form name="form" class="form-horizontal">
                    <div class="form-group">
                        <input name="id" type="hidden" value="${dict.id}"/>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="name" type="text" class="form-control" value="${dict.name}"/>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label required">键：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="key" type="text" class="form-control" value="${dict.key}"/>
                        </div>
                    </div>
                </form>
                <form class="form-horizontal">
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <a href="#" onclick="showCreateModal();return false"
                               class="btn btn-success pull-left">新增</a>
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>值</th>
                            <th>描述</th>
                            <th>锁定</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${dictDataList.size() eq 0}">
                            <tr>
                                <td colspan="4">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${dictDataList}" var="item">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.value}</td>
                                <td>${item.desc}</td>
                                <td><xs:dictDesc key="dictDataLock" value="${item.lock}"/></td>
                                <td>
                                    <c:if test="${item.lock eq 0}">
                                        <a href="#" class="btn btn-success btn-xs"
                                           onclick="simpleUpdateListItem('${item.id}',1);return false">
                                            锁定
                                        </a>
                                    </c:if>
                                    <c:if test="${item.lock eq 1}">
                                        <a href="#" class="btn btn-danger btn-xs"
                                           onclick="simpleUpdateListItem('${item.id}',0);return false">
                                            解锁
                                        </a>
                                    </c:if>
                                    <a href="#" onclick="updateListItem('${item.id}');return false;"
                                       class="btn btn-info btn-xs">
                                        编辑
                                    </a>
                                    <a href="#" class="btn btn-danger btn-xs"
                                       onclick="deleteListItem('${item.id}');return false">
                                        删除
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var $form = $("form[name=form]");
    var $formSubmit = $("#formSubmit");
    function submitForm() {
        $formSubmit.attr("disabled", true);
        doPost('<%=request.getContextPath()%>/admin/system/dict/update',
            $form.serialize(),
            function (data) {
                if (data.status) {
                    bootoast({message: "更新成功！"});
                    window.location.reload(true);
                } else {
                    alert(data.msg);
                    $formSubmit.attr("disabled", false);
                }
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
                    <input name="dict.id" value="${dict.id}" type="hidden" data-ignore="true">
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">值：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="value" type="text" maxlength="191" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">描述：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="desc" type="text" maxlength="255" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">锁定：</label>
                        </div>
                        <div class="col-xs-9">
                            <select name="lock" class="form-control">
                                <xs:dictOptions key="dictDataLock"/>
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
            value: {
                required: true,
                notEmpty: true,
                maxlength: 191
            },
            desc: {
                required: true,
                notEmpty: true,
                maxlength: 255
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            value: {
                required: "值不能为空",
                notEmpty: "值不能为空",
                maxlength: "值最多191个字"
            },
            desc: {
                required: "描述不能为空",
                notEmpty: "描述不能为空",
                maxlength: "描述最多255个字"
            }
        },
        submitHandler: function () {
            $createSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/system/dictData/save",
                $createForm.serialize(),
                function (data) {
                    $createSubmit.attr("disabled", false);
                    if (data.status) {
                        $("#createModel").modal("hide");
                        setTimeout(function () {
                            bootoast({message: "新增成功！"});
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
                            <label class="control-label required">值：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="value" type="text" maxlength="191" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-3 text-right">
                            <label class="control-label required">描述：</label>
                        </div>
                        <div class="col-xs-9">
                            <input name="desc" type="text" maxlength="255" class="form-control">
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
            value: {
                required: true,
                notEmpty: true,
                maxlength: 191
            },
            desc: {
                required: true,
                notEmpty: true,
                maxlength: 255
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空",
                maxlength: "名称最多255个字"
            },
            value: {
                required: "值不能为空",
                notEmpty: "值不能为空",
                maxlength: "值最多191个字"
            },
            desc: {
                required: "描述不能为空",
                notEmpty: "描述不能为空",
                maxlength: "描述最多255个字"
            }
        },
        submitHandler: function () {
            $updateSubmit.attr("disabled", true);
            doPost("<%=request.getContextPath()%>/admin/system/dictData/update",
                $updateForm.serialize(),
                function (data) {
                    $updateSubmit.attr("disabled", false);
                    if (data.status) {
                        $("#updateModel").modal("hide");
                        setTimeout(function () {
                            bootoast({message: "更新成功！"});
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
        doPost("<%=request.getContextPath()%>/admin/system/dictData/get", {id: id}, function (data) {
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
            $updateForm[0].reset();
            updateValidator.resetForm();
        })
    });

    function simpleUpdateListItem(id, status) {
        doPost('<%=request.getContextPath()%>/admin/system/dictData/lock/update',
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
</script>


<%@include file="../common/deleteConfirm.jsp" %>
<script>
    function deleteListItem(id) {
        showDeleteModel(null, function () {
            doPost("<%=request.getContextPath()%>/admin/system/dictData/remove", {id: id}, function (data) {
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
</script>
</body>
</html>
