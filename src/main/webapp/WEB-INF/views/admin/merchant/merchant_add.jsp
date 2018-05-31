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
    <title>新增客户</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%-- index参数的设置要在content_nav.jsp包含之前。jsp:include不可用，具体原因：未解之谜 --%>
<c:set var="index" value="merchant_manage"/>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="nav bg-light lter b-b padder-md">
            <a href="javascript:location.reload();" class="btn navbar-btn xs-nav text-base">新增客户</a>
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right m-sm">返回</a>
            <a class="btn btn-success pull-right m-sm m-r-n-xs" id="createSubmit">
                确定
            </a>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                            <label class="control-label required">姓名：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="name" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">性别：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <select name="sex" class="form-control">
                                <xs:dictOptions key="sex" value="1"/>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">联系电话：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="mobile" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                            <label class="control-label">名片：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3">
                            <xs:imageUploader identifier="productImage" name="businessCard" folder="yq/merchant" width="250" height="150"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">国籍：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="country" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label required">欠款：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="debt" type="number" class="form-control" value="0" placeholder="￥">
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
                    $createSubmit.attr("disabled", false);
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
