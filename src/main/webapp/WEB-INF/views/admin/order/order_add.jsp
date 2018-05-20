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
    <%@include file="../common/imgGlass.jsp" %>
    <style>
        .display-none {
            visibility: hidden;
        }
        .xs-table td, .xs-table th {
            width: 18%;
            overflow: hidden;
            padding: 10px;
            text-align: center;
        }
        .xs-table td:nth-child(2), .xs-table th:nth-child(2) {
            width: 17%;
        }
        .xs-table td:nth-child(3), .xs-table th:nth-child(3) {
            width: 17%;
        }
        .xs-table td:nth-child(4), .xs-table th:nth-child(4) {
            width: 17%;
        }
        .xs-table td:nth-child(5), .xs-table th:nth-child(5) {
            width: 30%;
        }
        .xs-table td:last-child, .xs-table th:last-child {
            width: 50px;
            overflow: hidden;
            padding: 0;
            text-align: center;
        }
        .xs-table {
            table-layout:fixed;
            width: 100%;
            border:1px solid #eaeff0;
            border-collapse: collapse;
        }
        .xs-table td {
            border:1px solid #eaeff0;
            height: 40px;
        }

        .new-item {
            background: #aeec88;
            max-height: 0;
            opacity: 0;
            transform: scale(0);
            animation: growHeight 0.5s ease forwards;
        }
        @keyframes growHeight {
            to {
                max-height: 50px;
                opacity: 1;
                transform: scale(1);
            }
        }
        .del-item {
            -webkit-animation: removed-item-animation .8s cubic-bezier(.65,-0.02,.72,.29);
            -o-animation: removed-item-animation .8s cubic-bezier(.65,-0.02,.72,.29);
            animation: removed-item-animation .8s cubic-bezier(.65,-0.02,.72,.29)
        }
        @-webkit-keyframes removed-item-animation {
            0%{
                opacity:1;
                -webkit-transform:translateX(0);
                -ms-transform:translateX(0);
                -o-transform:translateX(0);
                transform:translateX(0);
            }
            100%{
                opacity:0;
                -webkit-transform:translateX(-500px);
                -ms-transform:translateX(-500px);
                -o-transform:translateX(-500px);
                transform:translateX(-500px);
            }
        }
        @keyframes removed-item-animation1 {
            0% {
                opacity: 1;
                -webkit-transform: translateX(0);
                -ms-transform: translateX(0);
                -o-transform: translateX(0);
                transform: translateX(0)
            }
            30% {
                opacity: 1;
                -webkit-transform: translateX(50px);
                -ms-transform: translateX(50px);
                -o-transform: translateX(50px);
                transform: translateX(50px)
            }
            80% {
                opacity: 1;
                -webkit-transform: translateX(-800px);
                -ms-transform: translateX(-800px);
                -o-transform: translateX(-800px);
                transform: translateX(-800px)
            }
            100% {
                opacity: 0;
                -webkit-transform: translateX(-800px);
                -ms-transform: translateX(-800px);
                -o-transform: translateX(-800px);
                transform: translateX(-800px)
            }
        }

    </style>
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
                        <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                            <label class="control-label required">客户名：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="merchantName" type="text" class="form-control" onclick="showMerchant();" readonly>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">交货日期：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input type="text" name="deliveryDate" class="form-control datepicker" readonly>
                        </div>
                    </div>
                    <div class="m-lg bg-white" style="padding: 50px; background: #f6f8f8;">
                        <div class="wrapper-md bg-white" style="box-shadow: 0 0 60px rgba(0,0,0,0.1);height: 600px;">
                            <div class="col-xs-8">
                                <div class="col-xs-6">
                                    <h3>订单项明细</h3>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group m-t-md m-b-xs">
                                        <input type="text" class="form-control" name="searchCode" placeholder="请输入款号">
                                        <span class="input-group-btn">
                                            <button class="btn btn-success" type="button" onclick="addProduct();">添加</button>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <table class="xs-table m-t-md m-b-none" >
                                        <thead>
                                        <tr>
                                            <th>款号</th>
                                            <th class="required">价格&nbsp;</th>
                                            <th class="required">数量&nbsp;</th>
                                            <th>总额</th>
                                            <th>备注</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                    </table>
                                    <div class="xs-scrollbar" style="height: 420px;overflow: auto;">
                                        <table id="itemTable" class="xs-table" style="margin-top: -1px;">
                                            <tbody>
                                            <tr class="no-data-item">
                                                <td colspan="6" class="text-muted">
                                                    请输入款号添加产品
                                                </td>
                                            </tr>
                                                <%--<tr style="background-color: #d9edf7;">
                                                    <td>BG8362</td>
                                                    <td><input type="number" class="form-control" placeholder="元"></td>
                                                    <td><input type="number" class="form-control" placeholder="元"></td>
                                                    <td>￥12,000</td>
                                                    <td><input type="text" class="form-control"></td>
                                                    <td>
                                                        <button type="button" class="btn btn-xs btn-default">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </td>
                                                </tr>--%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <div style="box-shadow: -12px 1px 23px -12px rgba(0,0,0,0.1);margin-top:30px;padding-left:30px;height: 510px;border-left: 1px solid #eee">
                                    <div class="product-detail display-none">
                                        <div class="form-group">
                                            <input type="hidden" name="merchantId">
                                            <div class="col-xs-3  no-padder text-right">
                                                <label class="control-label">产品款号：</label>
                                            </div>
                                            <div class="col-xs-8 ">
                                                <label id="productCode" class="control-label"></label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-3 no-padder text-right">
                                                <label class="control-label">产品图片：</label>
                                            </div>
                                            <div class="col-xs-8">
                                                <img id="productImage" class="img-container" style="width: 200px;height: 200px;object-fit: cover;" alt="产品图片">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-3  no-padder text-right">
                                                <label class="control-label">总库存：</label>
                                            </div>
                                            <div class="col-xs-9 ">
                                                <label id="totalStock" class="control-label text-left">件</label>
                                                <span class="help-block"><span class="text-muted">（档口：<span id="shopStock"></span>件 &nbsp;&nbsp; 工厂：<span id="factoryStock"></span>件）</span></span>
                                            </div>
                                        </div>
                                        <div class="line line-dashed b-b line-lg"></div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-3  no-padder text-right">
                                            <label class="control-label" style="vertical-align: -webkit-baseline-middle;">总金额：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label" id="originAmount" style="font-size: 18px;font-weight: bold;color:#f00;">￥0</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-3 no-padder text-right">
                                            <label class="control-label">总件数：</label>
                                        </div>
                                        <div class="col-xs-3 ">
                                            <label class="control-label" id="originQuantity" style="font-size: 16px;font-weight: bold;color:#00b7ee;">0件</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-3  no-padder text-right">
                                            <label class="control-label">实际金额：</label>
                                        </div>
                                        <div class="col-xs-8 ">
                                            <div class="input-group">
                                                <span class="input-group-addon">￥</span>
                                                <input type="number" name="amount" class="form-control" value="19000">
                                            </div>
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
<script id="itemTemplate" type="text/html">
    <tr class="new-item" id="{{code}}">
        <td>{{code}}</td>
        <td><input type="number" name="price" class="form-control" placeholder="元" onchange="completeItemResult(event);"></td>
        <td><input type="number" name="quantity" class="form-control" placeholder="件" onchange="completeItemResult(event);"></td>
        <td class="itemTotal">￥0</td>
        <td><input type="text" name="remark" class="form-control"></td>
        <td>
            <button type="button" class="btn btn-xs btn-default" onclick="removeItem('{{code}}');">
                <i class="fa fa-times"></i>
            </button>
        </td>
    </tr>
</script>
<script>
    <%--订单项操作start--%>
    function addProduct() {
        var code = $("input[name=searchCode]").val();
        if(typeof code !== 'undefined' && code != ""){
            //检查是否已经添加
            if($("#"+code).length>0) return;
            $createSubmit.attr("disabled", false);
            doGet('<%=request.getContextPath()%>/admin/product/product/getByCode', {code:code}, function (data) {
                    $createSubmit.attr("disabled", true);
                    if (data.status) {
                        var template = $("#itemTemplate").html();
                        //是否第一个
                        if ($("#itemTable .no-data-item").length>0){
                            $("#itemTable tbody").html(renderTemplate(template, data.data));
                        } else {
                            $("#itemTable tbody").append(renderTemplate(template, data.data));
                        }
                        $("input[name=searchCode]").val("");
                        //右侧产品详情栏显示
                        showProduct(data.data);
                    } else {
                        bootoast({message: data.msg, timeout:4});
                    }
                }
            );
        }
    }

    function removeItem(id) {
        var _this = $("#"+id);
        _this.addClass('del-item')
            .one('webkitAnimationEnd oanimationend msAnimationEnd animationend', function(e) {
                _this.remove();
            });
    }

    function showProduct(product) {
        $(".product-detail").removeClass("display-none");
        $("#productCode").html(product.code);
        $("#productImage").attr("src",product.image);
        $("#totalStock").html(product.shopStock+product.factoryStock);
        $("#shopStock").html(product.shopStock);
        $("#factoryStock").html(product.factoryStock);
        completeResult();
    }

    function completeItemResult(e) {
        var _this = $(e.target);
        var count = Number(_this.parents("tr").find("input[name=quantity]").val());
        var price = Number(_this.parents("tr").find("input[name=price]").val());
        _this.parents("tr").find(".itemTotal").html("￥"+(count*price).toFixed(2));
        completeResult();
    }

    function completeResult() {
        var count = 0;
        var amount = Number(0);
        $("#itemTable tbody tr").each(function () {
            var b = Number($(this).find("input[name=quantity]").val());
            count += b;
            var c = Number($(this).find("input[name=price]").val());
            amount += c*b;
        });
        $("#originQuantity").html(count);
        $("#originAmount").html(amount.toFixed(2)+"&nbsp;元");
        $("input[name=amount]").val(amount.toFixed(2));
    }
    <%--订单项操作end--%>


    var $createForm = $("form[name=createForm]");
    var $createSubmit = $("#createSubmit");
    //初始化图片放大镜
    PostbirdImgGlass.init({
        domSelector: ".img-container",
        animation: true
    });

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
