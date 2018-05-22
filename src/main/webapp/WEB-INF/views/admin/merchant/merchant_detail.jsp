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
    <title>客户详情</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/page.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="merchant_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a class="xs-nav text-base xs-active">基本信息</a>
            <a class="xs-nav text-base">还款记录</a>
            <a class="xs-nav text-base">订货记录</a>
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right m-sm">返回</a>
            <a class="btn btn-primary pull-right m-sm m-r-n-xs" id="createSubmit">
                保存
            </a>
        </div>
        <div class="wrapper-md">
            <div class="js-panel">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <input type="hidden" name="id" value="${merchant.id}">
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                            <label class="control-label required">姓名：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="name" type="text" class="form-control" value="${merchant.name}"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">性别：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <select name="sex" class="form-control" data-value="${merchant.sex}">
                                <xs:dictOptions key="sex"/>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">联系电话：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="mobile" type="text" class="form-control" value="${merchant.mobile}"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                            <label class="control-label">名片：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3">
                            <xs:imageUploader identifier="productImage" name="businessCard" folder="yq/merchant" width="250" height="150"/>
                            <c:if test="${merchant.businessCard ne null && merchant.businessCard ne ''}">
                                <script>
                                    $(function () {
                                        putImageIntoImageUploader("productImage","${merchant.businessCard}");
                                    });
                                </script>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">国籍：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="country" type="text" class="form-control" value="${merchant.country}"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label required">欠款：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="debt" type="number" class="form-control" value="${merchant.debt}" placeholder="￥">
                        </div>
                    </div>
                </form>
            </div>
            <div class="js-panel display-none">
                <div class="panel panel-default m-b-none">
                    <table id="paymentPageTable" class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>还款金额</th>
                            <th>还款时间</th>
                            <th>备注</th>
                            <th>记录人</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div id="paymentPagination" class="xs-pagination"></div>
                <div class="clearfix"></div>
            </div>
            <div class="js-panel display-none">
                <div class="panel panel-default m-b-none">
                    <table id="orderPageTable" class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>订单金额</th>
                            <th>创建时间</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div id="orderPagination" class="xs-pagination"></div>
            </div>
        </div>
    </div>
</div>

<%--还款记录模板--%>
<script id="paymentTableTemplate" type="text/html">
    <tr>
        <td>{{id}}</td>
        <td>{{amount}}</td>
        <td>{{refundTime}}</td>
        <td>{{remark}}</td>
        <td>{{creator}}</td>
    </tr>
</script>

<%--订单模板--%>
<script id="orderTableTemplate" type="text/html">
    <tr>
        <td>{{orderNo}}</td>
        <td>{{amount}}</td>
        <td>{{createTime}}</td>
        <td>{{status}}</td>
    </tr>
</script>
<script>
    $(function () {
        $("#paymentPagination").pagination({
            callback: function (current) {
                var params = {};
                params.merchantId = ${merchant.id};
                params.page = current;
                doGet("<%=request.getContextPath()%>/admin/order/payment/list", params, function (data) {
                    if (data.status) {
                        $("#paymentPagination").pagination("setPage", current, Math.ceil( data.data.total/10));
                        var opt = {
                            "amount": function (val) {
                                return "￥"+val;
                            }
                        }
                        renderData("#paymentPageTable", data.data.list, "#paymentTableTemplate" , opt);
                    } else {
                        alert(data.msg);
                    }
                })
            }
        }).pagination("trigger");

        $("#orderPagination").pagination({
            callback: function (current) {
                var params = {};
                params.merchantId = ${merchant.id};
                params.page = current;
                doGet("<%=request.getContextPath()%>/admin/order/order/list", params, function (data) {
                    if (data.status) {
                        $("#orderPagination").pagination("setPage", current, Math.ceil( data.data.total/10));
                        var opt = {
                            "amount": function (val) {
                                return "￥"+val;
                            },
                            "status": function (val) {
                                var html = ""
                                if(val == '0'){
                                    html = "<span class=\"text-info\">交易中</span>";
                                }else if(val == '1'){
                                    html = "<span class=\"text-success\">已完成</span>";
                                }else if(val == '2'){
                                    html = "<span class=\"text-danger\">已关闭</span>";
                                }
                                return html;
                            }
                        }
                        renderData("#orderPageTable", data.data.list, "#orderTableTemplate" , opt);
                    } else {
                        alert(data.msg);
                    }
                })
            }
        }).pagination("trigger");

        $(".xs-nav").on("click", function () {
            var index = $(this).index();
            index == 0 ? $("#createSubmit").removeClass("display-none"):$("#createSubmit").addClass("display-none");
            $(".xs-nav").removeClass("xs-active");
            $(this).addClass("xs-active");
            $(".js-panel").addClass("display-none");
            $(".js-panel").eq(index).removeClass("display-none");
        })
    })

    var $createForm = $("form[name=createForm]");
    var $createSubmit = $("#createSubmit");
    $createSubmit.on("click", function () {
        $createForm.submit();
    })
    var createValidator = $createForm.validate({
        rules: {
            name: {
                required: true,
                notEmpty: true
            },
            mobile: {
                required: true,
                notEmpty: true
            },
            debt: {
                required: true,
                notEmpty: true
            }
        },
        messages: {
            name: {
                required: "姓名不能为空",
                notEmpty: "姓名不能为空"
            },
            mobile: {
                required: "联系电话不能为空",
                notEmpty: "联系电话不能为空"
            },
            debt: {
                required: "欠款不能为空",
                notEmpty: "欠款不能为空"
            }
        },
        submitHandler: function () {
            var params = $createForm.xsJson();
            $createSubmit.attr("disabled", true);
            doPost('<%=request.getContextPath()%>/admin/merchant/merchant/update', $createForm.serialize(), function (data) {
                    $createSubmit.attr("disabled", true);
                    if (data.status) {
                        history.go(-1);
                    } else {
                        alert(data.msg);
                    }
                }
            );
        }
    });
</script>
</body>
</html>
