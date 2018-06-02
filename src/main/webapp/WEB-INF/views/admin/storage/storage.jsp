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
                                    总库存：<span id="totalStock">${statistics.totalStock}</span>
                                </c:if>
                                <c:if test="${type eq 1}">
                                    档口总库存：<span id="totalStock">${statistics.shopStock}</span>
                                </c:if>
                                <c:if test="${type eq 2}">
                                    工厂总库存：<span id="totalStock">${statistics.factoryStock}</span>
                                </c:if>
                            </label>
                        </div>
                        <div class="col-xs-2 text-right">
                            <label class="control-label font-bold text-danger text-md">
                                有库存的产品款数：<span id="totalCount">${statistics.totalCount}</span>
                            </label>
                        </div>
                        <div class="col-xs-3  pull-right">
                            <div class="input-group">
                                <input name="name" type="text" class="form-control" placeholder="产品款号" value="${search.name}">
                                <span class="input-group-btn">
                                    <button class="btn btn-info" type="submit">搜索</button>
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
                <a class="pull-left m-md text-info" onclick="showLog();">查看今日库存日志</a>
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
            <div class="js-stock-input tl-content well m-n display-none">
                <div class="form-group">
                    <div class="col-xs-3 text-right close-padder">
                        <label class="stock-title control-label m-n"></label>
                    </div>
                    <div class="col-xs-9 no-padder">
                        <div class="input-group">
                            <input name="stock" type="number" class="form-control" placeholder="请输入数字">
                            <span class="input-group-btn">
                                <a class="btn btn-primary b-r-n" type="button" onclick="updateStock();">确定</a>
                            </span>
                            <a type="button" class="js-input-close close close-padder">&times;</a>
                        </div>
                    </div>
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
        $(".js-stock-btn").removeClass("display-none");
        _this.parents("tr").find(".js-stock-btn").addClass("display-none");
        $(".js-stock-input").addClass("display-none");
        /*清空输入框*/
        $(".js-stock-input input[name=stock]").val("");
        /*显示标题*/
        _this.parents("tr").find(".stock-title").html(_this.text()+"：");
        /*获取焦点*/
        _this.parents("tr").find(".js-stock-input input[name=stock]").focus();
        /*显示输入框*/
        _this.parents("tr").find(".js-stock-input").removeClass("display-none");
    }

    $(document).on("click", ".js-input-close", function () {
        $(".js-stock-input input[name=stock]").val("");
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

</script>

<div class="modal fade xs-scrollbar" id="merModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" style="width: 950px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">今日库存日志</h4>
            </div>
            <div class="modal-body" style="min-height: 560px">
                <form class="form-horizontal" id="merSearchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">操作：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="action" class="form-control">
                                <option value="">全部</option>
                                <option value="0">入库</option>
                                <option value="1">出库</option>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">仓库：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="type" class="form-control">
                                <option value="">全部</option>
                                <option value="0">档口</option>
                                <option value="1">工厂</option>
                            </select>
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
                            <th>产品款号</th>
                            <th>仓库</th>
                            <th>库存变化</th>
                            <th>时间</th>
                            <th>操作</th>
                            <th>操作人</th>
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
        <td>{{productCode}}</td>
        <td>{{type}}</td>
        <td>{{stock}}</td>
        <td>{{createTime}}</td>
        <td>{{descn}}</td>
        <td>{{staffName}}</td>
    </tr>
</script>
<script>
    function showLog() {
        $("#pagination1").pagination({
            isTogo: false,
            callback: function (current) {
                var params = $("#merSearchForm").xsJson();
                params.page = current;
                params.dynamic = {startTime: new Date().format("yyyy-MM-dd")};
                doGet("<%=request.getContextPath()%>/admin/storage/log/list", params, function (data) {
                    if (data.status) {
                        $("#pagination1").pagination("setPage", current, Math.ceil( data.data.total/10));
                        var opt = {
                            "type": function (val) {
                                return val == '0'?"档口":"工厂";
                            },
                            "stock": function (val) {
                                var html = "";
                                if(val>0){
                                    html+="<span class='text-success'>+"+val+"</span>"
                                }else{
                                    html+="<span class='text-danger'>"+val+"</span>"
                                }
                                return html;
                            }
                        }
                        renderData("#MerTable", data.data.list, "#merTableTemplate", opt);
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

</script>
</body>
</html>
