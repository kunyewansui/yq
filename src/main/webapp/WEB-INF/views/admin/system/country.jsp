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
    <title>国家管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="system_country"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">国家管理</h1>
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
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">状态：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3 m-b-md">
                            <select name="status" class="form-control" data-value="${search.status}">
                                <option value="">全部</option>
                                <xs:dictOptions key="countryStatus"/>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">电话区号：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[code]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.code}">
                        </div>
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_create')})">
                                <a href="#" onclick="showCreateModal();return false"
                                   class="btn btn-success pull-left">新增</a>
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_status')})">
                                <a href="#" onclick="updateListItemStatus(undefined,1);return false"
                                   class="btn btn-info pull-left" style="margin-left: 5px">全部开通</a>
                                <a href="#" onclick="updateListItemStatus(undefined,0);return false"
                                   class="btn btn-danger pull-left" style="margin-left: 5px">全部关闭</a>
                            </sec:authorize>
                            <input class="btn btn-info pull-right" value="搜索" type="submit">
                            <input class="btn btn-default pull-right  m-r-sm" value="重置" type="button"
                                   onclick="$('#searchForm').xsClean()">
                            <input id="kunye" type="file" onclick="templateUpdate(this);"/>
                            <a class="btn btn-danger" onclick="kunyetest();return false;">kunye</a>
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>电话区号</th>
                            <th>顺序</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="7">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.name}</td>
                                <td>${item.code}</td>
                                <td>${item.seq}</td>
                                <td><xs:dictDesc key="countryStatus" value="${item.status}"/></td>
                                <td>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_status')})">
                                        <c:if test="${item.status eq 0}">
                                            <a href="#" class="btn btn-success btn-xs"
                                               onclick="updateListItemStatus('${item.id}',1);return false">
                                                开通
                                            </a>
                                        </c:if>
                                        <c:if test="${item.status eq 1}">
                                            <a href="#" class="btn btn-danger btn-xs"
                                               onclick="updateListItemStatus('${item.id}',0);return false">
                                                关闭
                                            </a>
                                        </c:if>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_update')})">
                                        <a href="#" onclick="updateListItem('${item.id}');return false;"
                                           class="btn btn-info btn-xs">
                                            编辑
                                        </a>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_delete')})">
                                        <a href="#" class="btn btn-danger btn-xs"
                                           onclick="deleteListItem('${item.id}');return false">
                                            删除
                                        </a>
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

<script>
    function templateUpdate(file) {
        var formData = new FormData();
        formData.append("myfile",file.files[0]);
        $.ajax({
            url: "<%=request.getContextPath()%>/admin/system/country/import",
            type: "POST",
            data: formData,
            dataType: "json",
            contentType : false,
            processData : false,
            cache: false,
            success: function (data) {
                if (data.status) {
                    bootoast({message: "导入成功！"});
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function kunyetest(){
        $("#kunye").click();
    }
</script>

<sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_create')})">
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
                                <label class="control-label required">编号：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="id" type="number" min="0" step="1" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">名称：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="name" type="text" maxlength="191" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">电话区号：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="code" type="text" maxlength="255" class="form-control">
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
                                <label class="control-label required">拼音：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="pinyin" type="text" maxlength="255" class="form-control">
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
                id: {
                    required: true
                },
                name: {
                    required: true,
                    notEmpty: true,
                    maxlength: 191
                },
                code: {
                    required: true,
                    notEmpty: true,
                    maxlength: 255
                },
                seq: {
                    required: true
                },
                pinyin: {
                    required: true,
                    notEmpty: true,
                    maxlength: 255
                }
            },
            messages: {
                id: {
                    required: "编号不能为空"
                },
                name: {
                    required: "名称不能为空",
                    notEmpty: "名称不能为空",
                    maxlength: "名称最多191个字"
                },
                code: {
                    required: "电话区号不能为空",
                    notEmpty: "电话区号不能为空",
                    maxlength: "电话区号最多255个字"
                },
                seq: {
                    required: "顺序不能为空"
                },
                pinyin: {
                    required: "拼音不能为空",
                    notEmpty: "拼音不能为空",
                    maxlength: "拼音最多255个字"
                }
            },
            submitHandler: function () {
                $createSubmit.attr("disabled", true);
                doPost("<%=request.getContextPath()%>/admin/system/country/save",
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
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_update')})">
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
                                <input name="name" type="text" maxlength="191" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">电话区号：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="code" type="text" maxlength="255" class="form-control">
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
                                <label class="control-label required">拼音：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="pinyin" type="text" maxlength="255" class="form-control">
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
                id: {
                    required: true
                },
                name: {
                    required: true,
                    notEmpty: true,
                    maxlength: 191
                },
                code: {
                    required: true,
                    notEmpty: true,
                    maxlength: 255
                },
                seq: {
                    required: true
                },
                pinyin: {
                    required: true,
                    notEmpty: true,
                    maxlength: 255
                }
            },
            messages: {
                id: {
                    required: "编号不能为空"
                },
                name: {
                    required: "名称不能为空",
                    notEmpty: "名称不能为空",
                    maxlength: "名称最多191个字"
                },
                code: {
                    required: "电话区号不能为空",
                    notEmpty: "电话区号不能为空",
                    maxlength: "电话区号最多255个字"
                },
                seq: {
                    required: "顺序不能为空"
                },
                pinyin: {
                    required: "拼音不能为空",
                    notEmpty: "拼音不能为空",
                    maxlength: "拼音最多255个字"
                }
            },
            submitHandler: function () {
                $updateSubmit.attr("disabled", true);
                doPost("<%=request.getContextPath()%>/admin/system/country/update",
                    $updateForm.serialize(),
                    function (data) {
                        $updateSubmit.attr("disabled", false);
                        if (data.status) {
                            $("#updateModel").modal("hide");
                            setTimeout(function () {
                                bootoast({message: "修改成功！"});
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
            doPost("<%=request.getContextPath()%>/admin/system/country/get", {id: id}, function (data) {
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
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_status')})">
    <script>
        function updateListItemStatus(selectedId,status) {
            doPost("<%=request.getContextPath()%>/admin/system/country/status/update",
                {
                    id: selectedId,
                    status: status
                },
                function (data) {
                    if (data.status) {
                        setTimeout(function () {
                            bootoast({message: "更新成功！"});
                            window.location.reload(true);
                        }, 380);
                    } else {
                        alert(data.msg);
                    }
                });
        }
    </script>
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('system_country_delete')})">
    <%@include file="../common/deleteConfirm.jsp" %>
    <script>
        function deleteListItem(id) {
            showDeleteModel(null, function () {
                doPost("<%=request.getContextPath()%>/admin/system/country/remove", {id: id}, function (data) {
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
</sec:authorize>
</body>
</html>

