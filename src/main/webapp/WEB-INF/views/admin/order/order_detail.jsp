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
    <title>订单详情</title>
    <%@include file="../common/head.jsp" %>
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
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right m-sm">返回</a>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <div class="wrapper-md item-wrapper" style="width: 800px;margin: 0 auto;height: auto;">
                        <div class="padder-md">
                            <h3 class="text-center">订单信息</h3>
                        </div>
                        <div class="line b-b"></div>
                        <div class="wrapper-md">
                            <div class="form-group">
                                <div class="col-xs-12">
                                    <div class="col-xs-5">
                                        <label class="control-label">订单号：${order.orderNo}</label>
                                    </div>
                                    <div class="col-xs-3">
                                        <label class="control-label">客户：${order.merchantName}</label>
                                    </div>
                                    <div class="col-xs-4">
                                        <label class="control-label">手机：${order.merchantMobile}</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-12">
                                    <div class="col-xs-4">
                                        <label class="control-label">下单日期：<fmt:formatDate pattern="yyyy-MM-dd" value="${order.createTime}" /></label>
                                    </div>
                                    <div class="col-xs-4">
                                        <label class="control-label">交货日期：<fmt:formatDate pattern="yyyy-MM-dd" value="${order.deliveryDate}" /></label>
                                    </div>
                                    <div class="col-xs-4">
                                        <label class="control-label">预付款：<fmt:formatNumber value="${order.imprest}" type="currency" minFractionDigits="0"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel m-t-xl">
                                <table id="itemTable" class="table text-center m-b-none">
                                    <thead>
                                    <tr>
                                        <th style="text-align: left;">产品款号</th>
                                        <th>价格</th>
                                        <th>数量</th>
                                        <th>库存</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${order.orderItemList.size() eq 0}">
                                        <tr class="no-data-item" onclick="$('input[name=searchCode]').focus();">
                                            <td colspan="6" class="text-muted">
                                                无订单明细
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:forEach items="${order.orderItemList}" var="item">
                                        <tr class="" id="${item.product.code}">
                                            <td style="text-align: left;"><img src="${item.product.image}" class="thumb"><span class="text-md font-bold m-l-md">${item.product.code}</span></td>
                                            <td class="price" data-value="${item.price}" style="font-family: Century Gothic,arial,tahoma; font-weight: bold;color: #000;"><fmt:formatNumber value="${item.price}" type="currency" minFractionDigits="0"/></td>
                                            <td class="quantity">${item.quantity}</td>
                                            <td class="text-muted">${item.product.shopStock+item.product.factoryStock}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-xs-4 pull-right">
                                <div class="row">
                                    <div class="col-xs-6 no-padder text-right">
                                        <label class="control-label">总件数：</label>
                                    </div>
                                    <div class="col-xs-5 ">
                                        <label class="control-label text-quantity" id="originQuantity">0件</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6  no-padder text-right">
                                        <label class="control-label total-fee" style="padding-top: 12px">订单金额：</label>
                                    </div>
                                    <div class="col-xs-5 ">
                                        <label class="control-label  text-money" id="originAmount"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6  no-padder text-right">
                                        <label class="control-label" style="padding-top: 12px">实付金额：</label>
                                    </div>
                                    <div class="col-xs-5">
                                        <label class="control-label  text-money" ><fmt:formatNumber value="${order.amount}" groupingUsed="false" minFractionDigits="0"/>&nbsp;元</label>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group m-t">
                                <p class="text-muted" style="padding-left: 7px;">备注：</p>
                                <c:if test="${order.remark ne '' and order.remark ne null}">
                                    <p class="text-muted">【订单】：${order.remark}</p>
                                </c:if>
                                <c:forEach items="${order.orderItemList}" var="item">
                                    <c:if test="${item.remark ne ''}">
                                        <p class="text-muted">【${item.product.code}】：${item.remark}</p>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    //初始化图片放大镜
    PostbirdImgGlass.init({
        domSelector: ".thumb",
        animation: true
    });

    var count = 0;
    var amount = 0;
    $("#itemTable tbody tr:not(.no-data-item)").each(function () {
        var b = Number($(this).find(".quantity").text());
        count += b;
        var c = Number($(this).find(".price").data("value"));
        amount += c*b;
    });
    $("#originQuantity").html(count+"件");
    $("#originAmount").html(amount.toFixed(0)+"&nbsp;元");

</script>
</body>
</html>
