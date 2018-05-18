<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title>还款管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
    <%@include file="../common/page.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="order_payment"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">还款管理</a>
        </div>
        <div class="wrapper-md">
            <div class="form-horizontal">
                <form id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">商户名：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="merchantName" type="text" class="form-control" placeholder="模糊查询">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1 no-padder m-b-md text-right">
                            <label class="control-label">还款时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-7 m-b-md">
                            <div class="row">
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[startTime]" class="form-control datepicker" readonly>
                                </div>
                                <label class="pull-left control-label" style="width: 15px">至</label>
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[endTime]" class="form-control datepicker" readonly>
                                </div>
                                <a class="pull-left control-label text-info js-date-quick" data-days="6">近7天</a>
                                <a class="pull-left control-label m-l-sm text-info js-date-quick"
                                   data-days="29">近30天</a>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="form-group m-t-n-md">
                    <div class="col-xs-12">
                        <sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_create')})">
                            <a class="btn btn-success pull-left" onclick="showCreateModal();">新增</a>
                        </sec:authorize>
                        <input class="btn btn-info pull-right" value="搜索" type="button" onclick="refreshList();">
                        <input class="btn btn-default pull-right  m-r-sm" value="重置" type="button"
                               onclick="$('#searchForm').xsClean();refreshList();">
                    </div>
                </div>
            </div>
            <div class="panel panel-default m-b-none">
                <table id="pageTable" class="table text-center table-bordered table-striped m-b-none">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>商户名称</th>
                        <th>还款金额</th>
                        <th>备注</th>
                        <th>还款时间</th>
                        <th>记录人</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div id="pagination" class="xs-pagination"></div>
        </div>
    </div>
</div>

<script id="tableTemplate" type="text/html">
    <tr>
        <td>{{id}}</td>
        <td>{{merchantName}}</td>
        <td>{{amount}}</td>
        <td>{{remark}}</td>
        <td>{{refundTime}}</td>
        <td>{{creator}}</td>
        <td>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_update')})">
                <a class="btn btn-warning btn-xs" onclick="updateListItem('{{id}}');">
                    编辑
                </a>
            </sec:authorize>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_remove')})">
                <button class="btn btn-danger btn-xs"
                        onclick="deleteListItem('{{id}}')">
                    删除
                </button>
            </sec:authorize>
        </td>
    </tr>
</script>

<%@include file="../common/deleteConfirm.jsp" %>

<sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_create')})">
    <div class="modal fade xs-scrollbar" id="createModel" data-backdrop="static" role="dialog">
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
                            <input type="hidden" name="merchantId">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">商户名：</label>
                            </div>
                            <div class="col-xs-8">
                                <input name="merchantName" type="text" class="form-control" onclick="showMerchant(0);" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">还款金额：</label>
                            </div>
                            <div class="col-xs-8">
                                <input name="amount" type="number" class="form-control" placeholder="元">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">还款时间：</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" name="refundTime" class="form-control datepicker" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label">备注说明：</label>
                            </div>
                            <div class="col-xs-8">
                                <textarea name="remark" type="text" class="form-control" rows="4"></textarea>
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
                merchantName: {
                    required: true,
                    notEmpty: true
                },
                amount: {
                    required: true,
                    notEmpty: true
                },
                refundTime: {
                    required: true,
                    notEmpty: true
                }
            },
            messages: {
                merchantName: {
                    required: "商户名不能为空",
                    notEmpty: "商户名不能为空"
                },
                amount: {
                    required: "还款金额不能为空",
                    notEmpty: "还款金额不能为空"
                },
                refundTime: {
                    required: "还款时间不能为空",
                    notEmpty: "还款时间不能为空"
                }
            },
            submitHandler: function () {
                $createSubmit.attr("disabled", true);
                doPost("<%=request.getContextPath()%>/admin/order/payment/save",
                    $createForm.serialize(),
                    function (data) {
                        $createSubmit.attr("disabled", false);
                        if (data.status) {
                            $("#createModel").modal("hide");
                            bootoast({message: "新增成功！"});
                            setTimeout(function () {
                                window.location.reload(true);
                            }, 680);
                        } else {
                            alert(data.msg);
                        }
                    }
                )
            }
        });

        function showCreateModal() {
            //默认当前日期
            $("input[name=refundTime]").datepicker("setDate", new Date());
            $("#createModel").modal("show");
        }

        $("#createModel").on('hide.bs.modal', function () {
            $createForm[0].reset();
            createValidator.resetForm();
        })
    </script>
</sec:authorize>

<sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_update')})">
    <div class="modal fade xs-scrollbar" id="updateModel" data-backdrop="static" role="dialog">
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
                        <div class="form-group row">
                            <input type="hidden" name="id">
                            <input type="hidden" name="merchantId">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">商户名：</label>
                            </div>
                            <div class="col-xs-8">
                                <input name="merchantName" type="text" class="form-control" onclick="showMerchant(1);" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">还款金额：</label>
                            </div>
                            <div class="col-xs-8">
                                <input name="amount" type="number" class="form-control" placeholder="元">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">还款时间：</label>
                            </div>
                            <div class="col-xs-8">
                                <input type="text" name="refundTime" class="form-control datepicker" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label">备注说明：</label>
                            </div>
                            <div class="col-xs-8">
                                <textarea name="remark" type="text" class="form-control" rows="4"></textarea>
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
                merchantName: {
                    required: true,
                    notEmpty: true
                },
                amount: {
                    required: true,
                    notEmpty: true
                },
                refundTime: {
                    required: true,
                    notEmpty: true
                }
            },
            messages: {
                merchantName: {
                    required: "商户名不能为空",
                    notEmpty: "商户名不能为空"
                },
                amount: {
                    required: "还款金额不能为空",
                    notEmpty: "还款金额不能为空"
                },
                refundTime: {
                    required: "还款时间不能为空",
                    notEmpty: "还款时间不能为空"
                }
            },
            submitHandler: function () {
                $updateSubmit.attr("disabled", true);
                doPost("<%=request.getContextPath()%>/admin/order/payment/update",
                    $updateForm.serialize(),
                    function (data) {
                        $updateSubmit.attr("disabled", false);
                        if (data.status) {
                            $("#updateModel").modal("hide");
                            bootoast({message: "更新成功！"});
                            setTimeout(function () {
                                window.location.reload(true);
                            }, 680);
                        } else {
                            alert(data.msg);
                        }
                    }
                )
            }
        });

        function updateListItem(id) {
            doPost("<%=request.getContextPath()%>/admin/order/payment/get", {id: id}, function (data) {
                if (data.status) {
                    $updateForm.xsSetForm(data.data);
                    $("#updateModel").modal("show");
                } else {
                    alert(data.msg);
                }
            });
        }

        $("#updateModel").on('hide.bs.modal', function () {
            $updateForm[0].reset();
            updateValidator.resetForm();
        })
    </script>
</sec:authorize>

<%--选择商户--%>
<div class="modal fade xs-scrollbar" id="merModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" style="width: 650px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">商户列表</h4>
            </div>
            <div class="modal-body" style="min-height: 560px">
                <form class="form-horizontal" id="merSearchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-2 text-right">
                            <label class="control-label">商户名：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-6 ">
                            <input name="dynamic[name]" type="text" class="form-control" placeholder="模糊查询">
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-4 ">
                            <input class="btn btn-info" value="搜索" type="button" onclick="refreshList1();">
                            <input class="btn btn-default m-r-sm" value="重置" type="button"
                                   onclick="$('#merSearchForm').xsClean();refreshList1();">
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table id="MerTable" class="table text-center table-bordered m-b-none">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>商户名</th>
                            <th>国籍</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div style="display: flex;justify-content: center;">
                    <div id="pagination1" class="xs-pagination"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="merTableTemplate" type="text/html">
    <tr>
        <td>{{id}}</td>
        <td>{{name}}</td>
        <td>{{country}}</td>
    </tr>
</script>
<script>
    var merCallBacktype;//0-新增，1-更新
    function showMerchant(type) {
        merCallBacktype=type;
        $("#pagination1").pagination({
            isTogo: false,
            callback: function (current) {
                var params = $("#merSearchForm").xsJson();
                params.page = current;
                doGet("<%=request.getContextPath()%>/admin/merchant/merchant/list", params, function (data) {
                    if (data.status) {
                        $("#pagination1").pagination("setPage", current, Math.ceil( data.data.total/10));
                        renderData("#MerTable", data.data.list, "#merTableTemplate");
                    } else {
                        alert(data.msg);
                    }
                })
            }
        }).pagination("trigger");
        $("#merModel").modal('show');
    }

    function refreshList1() {
        $("#pagination1").pagination("trigger");
    }

    $("#merModel").on('hide.bs.modal', function () {
        $("#merSearchForm")[0].reset();
    })


    $(document).on("click", "#MerTable tbody tr", function () {
        var _id = $(this).find("td").eq(0).text();
        var _name = $(this).find("td").eq(1).text();
        console.log(_name)
        var $model;
        if(merCallBacktype == 0){
            $model = $("#createModel");
        }else{
            $model = $("#updateModel");
        }
        $model.find("input[name=merchantId]").val(_id);
        $model.find("input[name=merchantName]").val(_name);
        $("#merModel").modal("hide");
    })

    $(document).on("mouseover", "#MerTable tbody tr", function () {
        $(this).addClass("info");
    })
    $(document).on("mouseout", "#MerTable tbody tr", function () {
        $(this).removeClass("info");
    })
</script>

<script>
    //快速选时间
    $(".js-date-quick").on("click", function () {
        var day = $(this).attr("data-days");
        var t = new Date();
        var inputs = $(this).parent().find("input.datepicker");
        inputs.eq(1).val(t.format("yyyy-MM-dd"));
        var t_s = t.getTime();
        t.setTime(t_s - 24 * 60 * 60 * 1000 * day);
        inputs.eq(0).val(t.format("yyyy-MM-dd"));
    });

    $(function () {
        $("#pagination").pagination({
            callback: function (current) {
                var params = $("#searchForm").xsJson();
                params.page = current;
                doGet("<%=request.getContextPath()%>/admin/order/payment/list", params, function (data) {
                    if (data.status) {
                        $("#pagination").pagination("setPage", current, Math.ceil( data.data.total/10));
                        var opt = {
                            "amount": function (val) {
                                return "￥"+val;
                            },
                            "createTime": function (val) {
                               return val.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg, '$1-$2-$3'); //日期格式转换
                            }
                        }
                        renderData("#pageTable", data.data.list, "#tableTemplate" , opt);
                    } else {
                        alert(data.msg);
                    }
                })
            }
        }).pagination("trigger");
    })

    function refreshList() {
        $("#pagination").pagination("trigger");
    }

    <sec:authorize access="hasAnyRole(${xs:getPermissions('order_payment_remove')})">
        function deleteListItem(id) {
            showDeleteModel(null, function () {
                doPost("<%=request.getContextPath()%>/admin/order/payment/remove", {id: id}, function (data) {
                    if (data.status) {
                        bootoast({message: "删除成功！",timeout: 2});
                        setTimeout(function () {
                            refreshList();
                        }, 680);
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
