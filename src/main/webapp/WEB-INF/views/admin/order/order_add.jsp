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
    <title>新增订单</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
    <%@include file="../common/page.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="order_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">新增订单</a>
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right m-sm">返回</a>
            <a class="btn btn-success pull-right m-sm m-r-n-xs" id="createSubmit">
                确定
            </a>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <div class="form-group">
                        <input type="hidden" name="merchantId">
                        <div class="col-xs-2 col-md-2 col-lg-1  no-padder text-right">
                            <label class="control-label required">客户名：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="merchantName" type="text" class="form-control" onclick="showMerchant();" readonly>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-1   no-padder text-right">
                            <label class="control-label">交货日期：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input type="text" name="deliveryDate" class="form-control datepicker" readonly>
                        </div>
                    </div>
                    <div class="m-lg bg-white" style="padding: 50px; background: #f6f8f8;">
                        <div class="wrapper-md bg-white" style="box-shadow: 0 0 60px rgba(0,0,0,0.1);height: 600px;">
                            <div class="col-xs-8">
                                <h3>订单项选择</h3>
                                <div class="xs-scrollbar m-t-md" style="height: 470px;overflow: auto;">
                                    <table class="table" >
                                        <thead>
                                        <tr>
                                            <th>first name</th>
                                            <th>last name</th>
                                            <th>birth date</th>
                                            <th>balance</th>
                                            <th>email</th>
                                            <th width="50"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">LAURENT</td>
                                            <td class="ng-binding">Renard</td>
                                            <td class="ng-binding">May 21, 1987</td>
                                            <td class="ng-binding">$102.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="whatever@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:whatever@gmail.com" href="mailto:whatever@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">BLANDINE</td>
                                            <td class="ng-binding">Faivre</td>
                                            <td class="ng-binding">Apr 25, 1987</td>
                                            <td class="ng-binding">-$2,323.22</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="oufblandou@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:oufblandou@gmail.com" href="mailto:oufblandou@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr ng-repeat="row in rowCollectionBasic" class="ng-scope">
                                            <td class="ng-binding">FRANCOISE</td>
                                            <td class="ng-binding">Frere</td>
                                            <td class="ng-binding">Aug 27, 1955</td>
                                            <td class="ng-binding">$42,343.00</td>
                                            <td>
                                                <button class="btn btn-xs" data-placement="top" data-content="raymondef@gmail.com" bs-popover="" type="button">
                                                    <i class="fa fa-eye"></i>
                                                </button>
                                                <a ng-href="mailto:raymondef@gmail.com" href="mailto:raymondef@gmail.com">email</a></td>
                                            <td>
                                                <button type="button" ng-click="removeRow(row)" class="btn btn-xs btn-default">
                                                    <i class="fa fa-times">
                                                    </i>
                                                </button>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <div style="box-shadow: 0 0 60px rgba(0,0,0,0.1);margin-top:30px;padding-left:40px;height: 510px;border-left: 1px solid #dcdcdc">
                                    <div class="form-group">
                                        <input type="hidden" name="merchantId">
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">产品款号：</label>
                                        </div>
                                        <div class="col-xs-8 ">
                                            <label class="control-label">BN82651</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-2 no-padder text-right">
                                            <label class="control-label">产品图片：</label>
                                        </div>
                                        <div class="col-xs-8">
                                            <img src="<%=request.getContextPath()%>/assets/admin/img/demo_pic.png" style="width: 200px" alt="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">档口库存：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label">200</label>
                                        </div>
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">工厂库存：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label">400</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">总库存：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label">500</label>
                                        </div>
                                    </div>
                                    <div class="line line-dashed b-b line-lg"></div>
                                    <div class="form-group">
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">总金额：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label">￥20000</label>
                                        </div>
                                    </div>
                                    <div class="line line-dashed b-b line-lg"></div>
                                    <div class="form-group">
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">总件数：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label">200件</label>
                                        </div>
                                        <div class="col-xs-2  no-padder text-right">
                                            <label class="control-label">实际金额：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <input type="number" name="amount" class="form-control" value="19000">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
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
            doPost('<%=request.getContextPath()%>/admin/merchant/merchant/save', $createForm.serialize(), function (data) {
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
    function showMerchant() {
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
        var $model= $("#createForm");
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
</body>
</html>
