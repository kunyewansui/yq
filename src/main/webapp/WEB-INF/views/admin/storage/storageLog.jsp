<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>库存日志</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/datepicker.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<c:set var="index" value="storage_log"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a class="xs-nav text-base ${search.type eq null?'xs-active':''}" onclick="toggleSearch();">全部日志</a>
            <a class="xs-nav text-base ${search.type eq 0?'xs-active':''}" onclick="toggleSearch(0);">档口日志</a>
            <a class="xs-nav text-base ${search.type eq 1?'xs-active':''}" onclick="toggleSearch(1);">工厂日志</a>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">操作人：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="staffName" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.staffName}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">产品款号：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="productCode" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.productCode}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">操作：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <select name="action" class="form-control" data-value="${search.action}">
                                <option value="">全部</option>
                                <option value="0">入库</option>
                                <option value="1">出库</option>
                            </select>
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1 no-padder m-b-md text-right">
                            <label class="control-label">操作时间：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-7 m-b-md">
                            <div class="row">
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[startTime]" class="form-control datepicker" value="${search.dynamic.startTime}" readonly>
                                </div>
                                <label class="pull-left control-label" style="width: 15px">至</label>
                                <div class="col-xs-10 col-md-4 col-lg-4 ">
                                    <input type="text" name="dynamic[endTime]" class="form-control datepicker" value="${search.dynamic.endTime}" readonly>
                                </div>
                                <a class="pull-left control-label text-info js-date-quick" data-days="0">今天</a>
                                <a class="pull-left control-label m-l-sm text-info js-date-quick" data-days="6">近7天</a>
                            </div>
                        </div>
                        <input type="hidden" name="type" value="${search.type}" data-ignore="true">
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <input class="btn btn-info pull-right" value="搜索" type="submit">
                            <input class="btn btn-default pull-right  m-r-sm" value="重置" type="button"
                                   onclick="$('#searchForm').xsClean()">
                        </div>
                    </div>
                </form>
                <div class="panel panel-default m-b-none">
                    <table class="table text-center table-bordered table-striped m-b-none">
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
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="7">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.productCode}</td>
                                <td>${item.type eq 0?"档口":"工厂"}</td>
                                <td class="${item.stock>0?'text-success':"text-danger"}">${item.stock>0?'+':""}${item.stock}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createTime}"/></td>
                                <td>${item.descn}</td>
                                <td>${item.staffName}</td>
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
    function toggleSearch(type) {
        if(typeof type !== 'undefined'){
            $("input[name=type]").val(type);
        }else{
            $("input[name=type]").val("");
        }
        $("#searchForm").submit();
    }
</script>

</body>
</html>

