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
    <title>库存管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/page.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="storage_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=0" class="xs-nav text-base ${type eq 0?'xs-active':''}">全部库存</a>
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=1" class="xs-nav text-base ${type eq 1?'xs-active':''}">档口库存</a>
            <a href="<%=request.getContextPath()%>/admin/storage/storage?type=2" class="xs-nav text-base ${type eq 2?'xs-active':''}">工厂库存</a>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-2 text-right">
                            <label class="control-label font-bold text-success text-md">
                                <c:if test="${type eq 0}">
                                    总库存：${statistics.totalStock}
                                </c:if>
                                <c:if test="${type eq 1}">
                                    档口总库存：${statistics.shopStock}
                                </c:if>
                                <c:if test="${type eq 2}">
                                    工厂总库存：${statistics.factoryStock}
                                </c:if>
                            </label>
                        </div>
                        <div class="col-xs-2 text-right">
                            <label class="control-label font-bold text-danger text-md">
                                有库存的产品款数：${statistics.totalCount}
                            </label>
                        </div>
                        <div class="col-xs-3  pull-right">
                            <div class="input-group">
                                <input name="name" type="text" class="form-control" placeholder="产品名称/产品编号"
                                       value="${search.name}">
                                <span class="input-group-btn">
                                    <button class="btn btn-info" type="button" onclick="refreshList();">搜索</button>
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table id="pageTable" class="table text-center table-bordered table-striped m-b-none">
                        <thead>
                        <tr>
                            <th>款号</th>
                            <c:if test="${type eq 0 or type eq 1}">
                                <th>档口库存</th>
                            </c:if>
                            <c:if test="${type eq 0 or type eq 2}">
                                <th>工厂库存</th>
                            </c:if>
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
</div>
<script id="tableTemplate" type="text/html">
    <tr>
        <td>{{code}}</td>
        <c:if test="${type eq 0 or type eq 1}">
            <td>{{shopStock}}</td>
        </c:if>
        <c:if test="${type eq 0 or type eq 2}">
            <td>{{factoryStock}}</td>
        </c:if>
        <td>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('storage_manage_shop_update')})">
                <c:if test="${type eq 0 or type eq 1}">
                    <a class="js-stock-btn btn btn-success btn-xs" onclick="showStockInput('{{id}}', 0);">档口入库</a>
                    <a class="js-stock-btn btn btn-success btn-xs" onclick="showStockInput('{{id}}', 1);">档口出库</a>
                </c:if>
            </sec:authorize>
            <sec:authorize access="hasAnyRole(${xs:getPermissions('storage_manage_factory_update')})">
                <c:if test="${type eq 0 or type eq 2}">
                    <a class="js-stock-btn btn btn-warning btn-xs" onclick="showStockInput('{{id}}', 2);">工厂入库</a>
                    <a class="js-stock-btn btn btn-warning btn-xs" onclick="showStockInput('{{id}}', 3)">工厂出库</a>
                </c:if>
            </sec:authorize>
            <div class="js-stock-input tl-content w-xxl well m-n display-none">
                <div class="input-group">
                    <input name="stock" type="number" class="form-control" placeholder="请输入数字">
                    <span class="input-group-btn">
                        <a class="btn btn-success" type="button" onclick="updateStock();">确定</a>
                    </span>
                    <a type="button" class="js-input-close close" style="padding: 6px 0 6px 10px;">&times;</a>
                </div>
            </div>
        </td>
    </tr>
</script>
<script>
    var selectedId;
    var selectedType;
    function showStockInput(id, type) {
        var _this = $(event.target);
        console.log(_this);
        selectedId = id;
        selectedType = type;
        _this.parents("tr").find(".js-stock-btn").addClass("display-none");
        $(".js-stock-input").addClass("display-none");
        _this.parents("tr").find(".js-stock-input").removeClass("display-none");
    }

    $(document).on("click", ".js-input-close", function () {
        $(".js-stock-input").addClass("display-none");
        $(".js-stock-btn").removeClass("display-none");
    })

    function updateStock(){
        var _this = $(event.target);
        var stock = _this.parents(".js-stock-input").find("input[name=stock]").val();
        if(stock == '') return;
        if(selectedType == '0' || selectedType == '1'){
            if(selectedType == "1") stock = stock*(-1);
            doPost("<%=request.getContextPath()%>/admin/storage/storage/shop/update", {id:selectedId,stock:stock}, function (data) {
                if (data.status) {
                    bootoast({message: "库存更新成功！",timeout: 2});
                    setTimeout(function () {
                        location.reload();
                    }, 680);
                } else {
                    alert(data.msg);
                }
            })
        }else if (selectedType == '2' || selectedType == '3'){
            if(selectedType == "3") stock = stock*(-1);
            doPost("<%=request.getContextPath()%>/admin/storage/storage/factory/update", {id:selectedId,stock:stock}, function (data) {
                if (data.status) {
                    bootoast({message: "库存更新成功！",timeout: 2});
                    setTimeout(function () {
                        location.reload();
                    }, 680);
                } else {
                    alert(data.msg);
                }
            })
        }

    }

    $(function () {
        $("#pagination").pagination({
            callback: function (current) {
                var params = $("#searchForm").xsJson();
                params.page = current;
                params.dynamic = {type: ${type}};
                doGet("<%=request.getContextPath()%>/admin/storage/storage/list", params, function (data) {
                    if (data.status) {
                        $("#pagination").pagination("setPage", current, Math.ceil( data.data.total/10));
                        renderData("#pageTable", data.data.list, "#tableTemplate");
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
</script>
</body>
</html>
