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
    <title>订单编辑</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
    <%@include file="../common/page.jsp" %>
    <%@include file="../common/imgGlass.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="order_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">订单编辑</a>
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right m-sm">返回</a>
            <a class="btn btn-primary pull-right m-sm m-r-n-xs" id="createSubmit">
                保存
            </a>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <div class="wrapper-md item-wrapper base">
                        <div class="padder-md">
                            <h3>基本信息</h3>
                        </div>
                        <div class="wrapper-md">
                            <div class="form-group">
                                <input type="hidden" name="id" value="${order.id}">
                                <div class="col-xs-2 col-md-2 col-lg-1  no-padder text-right">
                                    <label class="control-label">订单号：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <input type="text" class="form-control" value="${order.orderNo}" disabled>
                                </div>
                                <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                                    <label class="control-label">订单状态：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <select name="status" class="form-control">
                                        <xs:dictOptions key="orderStatus" value="${order.status}"/>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 col-md-2 col-lg-1 no-padder text-right">
                                    <label class="control-label required">客户名：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <input type="hidden" name="merchantId" value="${order.merchantId}">
                                    <input name="merchantName" type="text" class="form-control" value="${order.merchantName}" onclick="showMerchant();" readonly disabled>
                                </div>
                                <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                                    <label class="control-label">交货日期：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <input type="text" name="deliveryDate" class="form-control datepicker" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${order.deliveryDate}" />" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 col-md-2 col-lg-1  no-padder text-right">
                                    <label class="control-label required">预付款：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <input name="imprest" type="number" value="${order.imprest}" class="form-control">
                                </div>
                                <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                                    <label class="control-label">订单创建人：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-3 ">
                                    <input type="text" class="form-control" value="${order.creator}" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 col-md-2 col-lg-1  no-padder text-right">
                                    <label class="control-label">备注：</label>
                                </div>
                                <div class="col-xs-4 col-md-4 col-lg-8 ">
                                    <textarea name="remark" rows="2" class="form-control">${order.remark}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="wrapper-md item-wrapper">
                        <div class="col-xs-8">
                            <div class="col-xs-6">
                                <h3>订单明细</h3>
                            </div>
                            <div class="col-xs-6">
                                <div class="input-group m-t-md m-b-xs">
                                    <input type="text" class="form-control" name="searchCode" placeholder="请输入款号">
                                    <span class="input-group-btn">
                                        <button class="btn btn-success" type="button" onclick="addProduct(event);">添加</button>
                                    </span>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <table class="xs-table m-t-md m-b-none" >
                                    <thead>
                                    <tr>
                                        <th>款号</th>
                                        <th class="required">价格(元)&nbsp;</th>
                                        <th class="required">数量(件)&nbsp;</th>
                                        <th>总额</th>
                                        <th>备注（选填）</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="item-content xs-scrollbar">
                                    <table id="itemTable" class="xs-table xs-table-hover" style="margin-top: -1px;">
                                        <tbody>
                                            <c:if test="${order.orderItemList.size() eq 0}">
                                                <tr class="no-data-item" onclick="$('input[name=searchCode]').focus();">
                                                    <td colspan="6" class="text-muted">
                                                        请输入款号添加产品
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:forEach items="${order.orderItemList}" var="item">
                                                <tr class="new-item" id="${item.product.code}">
                                                    <td>${item.product.code}</td>
                                                    <td><input type="number" name="price" class="form-control" value="${item.price}" placeholder="元" oninput="completeItemResult(event);"></td>
                                                    <td><input type="number" name="quantity" class="form-control" value="${item.quantity}" placeholder="件" oninput="completeItemResult(event);"></td>
                                                    <td class="itemTotal">￥${item.total}</td>
                                                    <td><input type="text" name="productRemark" class="form-control" value="${item.remark}"></td>
                                                    <td>
                                                        <input type="hidden" name="productId" value="${item.product.id}">
                                                        <input type="hidden" name="productImage" value="${item.product.image}">
                                                        <input type="hidden" name="productShopStock" value="${item.product.shopStock}">
                                                        <input type="hidden" name="productFactoryStock" value="${item.product.factoryStock}">
                                                        <button type="button" class="btn btn-xs btn-default" onclick="removeItem('${item.product.code}');">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-4">
                            <div class="left-container">
                                <div class="product-detail xs-hide">
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
                                            <img id="productImage" class="img-container" alt="产品图片">
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
                                        <label class="control-label total-fee">总金额：</label>
                                    </div>
                                    <div class="col-xs-3 ">
                                        <label class="control-label text-money" id="originAmount">￥0</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-3 no-padder text-right">
                                        <label class="control-label">总件数：</label>
                                    </div>
                                    <div class="col-xs-3 ">
                                        <label class="control-label text-quantity" id="originQuantity">0件</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-3  no-padder text-right">
                                        <label class="control-label">实际金额：</label>
                                    </div>
                                    <div class="col-xs-8 ">
                                        <div class="input-group">
                                            <span class="input-group-addon">￥</span>
                                            <input type="number" name="amount" class="form-control" value="${order.amount}">
                                        </div>
                                        <span class="help-block text-muted">（订单最终以该金额为准）</span>
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
        <td><input type="number" name="price" class="form-control" placeholder="元" oninput="completeItemResult(event);"></td>
        <td><input type="number" name="quantity" class="form-control" placeholder="件" oninput="completeItemResult(event);"></td>
        <td class="itemTotal">￥0</td>
        <td><input type="text" name="productRemark" class="form-control"></td>
        <td>
            <input type="hidden" name="productId" value="{{id}}">
            <input type="hidden" name="productImage" value="{{image}}">
            <input type="hidden" name="productShopStock" value="{{shopStock}}">
            <input type="hidden" name="productFactoryStock" value="{{factoryStock}}">
            <button type="button" class="btn btn-xs btn-default" onclick="removeItem('{{code}}');">
                <i class="fa fa-times"></i>
            </button>
        </td>
    </tr>
</script>
<script>
    <%--订单项操作start--%>
    init();
    //初始化图片放大镜
    PostbirdImgGlass.init({
        domSelector: ".img-container",
        animation: true
    });
    function addProduct(e) {
        var _this = $(e.target);
        var code = $.trim($("input[name=searchCode]").val());
        if(typeof code !== 'undefined' && code != ""){
            //检查是否已经添加
            if($("#"+code).length>0){
                //锚点定位
                $("#itemTable").parent().animate(
                    {scrollTop: $("#"+code).offset().top},
                    {duration: 500,easing: "swing"}
                );
                //获取焦点
                $("#"+code+" input").eq(0).focus();
                return;
            }
            _this.attr("disabled", true);
            doGet('<%=request.getContextPath()%>/admin/product/product/getByCode', {code:code}, function (data) {
                _this.attr("disabled", false);
                    if (data.status) {
                        var template = $("#itemTemplate").html();
                        //是否第一个
                        if ($("#itemTable .no-data-item").length>0){
                            $("#itemTable tbody").html(renderTemplate(template, data.data));
                        } else {
                            $("#itemTable tbody").append(renderTemplate(template, data.data));
                        }
                        //锚点定位
                        $("#itemTable").parent().animate(
                                {scrollTop: $("#"+code).offset().top},
                                {duration: 500,easing: "swing"}
                            );
                        //获取焦点
                        $("#"+code+" input").eq(0).focus();
                        //列表项高亮
                        $("#itemTable tbody tr").removeClass("info");
                        $("#"+code).addClass("info");
                        //重置搜索框
                        $("input[name=searchCode]").val("");
                        //右侧产品详情栏显示
                        showProduct(code);
                        //重新计算总金额
                        completeResult();
                    } else {
                        bootoast({message: data.msg, timeout: 4, type: "danger"});
                    }
                }
            );
        }
    }

    $(document).on("click", "#itemTable tbody tr:not(.no-data-item)", function () {
        var code = $(this).attr("id");
        $("#itemTable tbody tr").removeClass("info");
        $(this).addClass("info");
        //右侧产品详情栏显示
        showProduct(code);
    })

    function removeItem(id) {
        var _this = $("#"+id);
        var count = $("#itemTable tbody tr").length;
        //动画效果
        _this.addClass('del-item')
            .one('webkitAnimationEnd oanimationend msAnimationEnd animationend', function(e) {
                //是否最后一个
                if (count==1){
                    var html = " <tr class=\"no-data-item\" onclick=\"$('input[name=searchCode]').focus();\">\n" +
                        "            <td colspan=\"6\" class=\"text-muted\">请输入款号添加产品</td>\n" +
                        "        </tr>";
                    $("#itemTable tbody").html(html);
                } else {
                    _this.remove();
                }
                $(".product-detail").addClass("xs-hide");
                //重新计算总金额
                completeResult();
            });
    }

    function showProduct(code) {
        $(".product-detail").removeClass("xs-hide");
        var _this = $("#"+code);

        $("#productCode").html(code);
        $("#productImage").attr("src", _this.find("input[name=productImage]").val());
        var shopStock = Number(_this.find("input[name=productShopStock]").val());
        var factoryStock = Number(_this.find("input[name=productFactoryStock]").val());
        $("#shopStock").html(shopStock);
        $("#factoryStock").html(factoryStock);
        $("#totalStock").html(shopStock + factoryStock);
    }

    function completeItemResult(e) {
        var _this = $(e.target);
        var count = Number(_this.parents("tr").find("input[name=quantity]").val());
        var price = Number(_this.parents("tr").find("input[name=price]").val());
        _this.parents("tr").find(".itemTotal").html("￥"+(count*price).toFixed(2));
        //重新计算总金额
        completeResult();
    }

    function init() {
        var count = 0;
        var amount = 0;
        $("#itemTable tbody tr:not(.no-data-item)").each(function () {
            var b = Number($(this).find("input[name=quantity]").val());
            count += b;
            var c = Number($(this).find("input[name=price]").val());
            amount += c*b;
        });
        $("#originQuantity").html(count);
        $("#originAmount").html(amount.toFixed(2)+"&nbsp;元");
    }

    function completeResult() {
        var count = 0;
        var amount = 0;
        $("#itemTable tbody tr:not(.no-data-item)").each(function () {
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

    $createSubmit.on("click", function () {
        $createForm.submit();
    })
    var createValidator = $createForm.validate({
        rules: {
            merchantName: {
                required: true,
                notEmpty: true
            },
            imprest: {
                required: true,
                notEmpty: true
            },
            amount: {
                required: true,
                notEmpty: true
            }
        },
        messages: {
            merchantName: {
                required: "客户名不能为空",
                notEmpty: "客户名不能为空"
            },
            imprest: {
                required: "预付款不能为空",
                notEmpty: "预付款不能为空"
            },
            amount: {
                required: "订单金额不能为空",
                notEmpty: "订单金额不能为空"
            }
        },
        submitHandler: function () {
            var params = $createForm.xsJson();
            var $datalist = $("#itemTable tbody tr:not(.no-data-item)");
            var orderItemList = [];
            if($datalist.length>0){
                var flag = true;
                $datalist.each(function () {
                    var _this = $(this);
                    var product = {};
                    product.productId = _this.find("input[name=productId]").val();
                    product.quantity = _this.find("input[name=quantity]").val();
                    product.price = _this.find("input[name=price]").val();
                    if(product.quantity === "" || product.price === ""){
                        alert("订单项信息请填写完整");
                        flag = !1;
                        return false;
                    }
                    product.total = (Number(product.quantity)*Number(product.price)).toFixed(2);
                    product.remark = _this.find("input[name=productRemark]").val();
                    orderItemList.push(product);
                })
                if(!flag) return;
            }
            params.orderItemList = orderItemList;
            $createSubmit.attr("disabled", true);
            $.ajax({
                url: "<%=request.getContextPath()%>/admin/order/order/update",
                type: "POST",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                cache: false,
                success: function (data) {
                    if (data.status) {
                        bootoast({message: "保存成功！"});
                        setTimeout(function () {
                            history.go(-1);
                        }, 680);
                    } else {
                        alert(data.msg);
                        $createSubmit.attr("disabled", false);
                    }
                }
            });
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
