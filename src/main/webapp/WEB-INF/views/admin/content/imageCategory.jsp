<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>Title</title>
    <%@include file="../common/head.jsp"%>
    <%@include file="../common/treeview.jsp"%>
</head>
<body>
<%@include file="../common/header.jsp"%>
<%@include file="./content_nav.jsp"%>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3">图片分类</h1>
        </div>
        <div class="wrapper-md">
            <div class="row">
                <div class="col-xs-12 col-md-5 col-lg-3">
                    <div id="tree">
                        <p style="text-align: center">加载中...</p>
                    </div>
                </div>
                <div class="col-xs-12 col-md-7 col-lg-9">
                    <form name="saveForm" class="form-horizontal" style="max-width: 800px">
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required ">编号：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="id" type="text" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">名称：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="name" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">KEY：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="key" type="text" class="form-control" >
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">顺序：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="seq" type="text" class="form-control" >
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">是否显示：</label>
                            </div>
                            <div class="col-xs-9">
                                <select name="hot" class="form-control">
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">上级分类：</label>
                            </div>
                            <div class="col-xs-9">
                                <input name="seq" type="text" class="form-control" readonly>
                            </div>
                        </div>
                    </form>
                    <div class="clearfix"></div>
                </div>
            </div>

        </div>
    </div>
</div>

<script>

    $(function () {
        disableForm($("form[name=saveForm]"));
        doPost("<%=request.getContextPath()%>/app/v1/image/category/tree",{},function (data) {
            if(data.status){
                $('#tree').treeview({
                    data: data.data,
                    onNodeSelected:function (event, data) {
                        console.log(data)
                    }
                });
            }else{
                alert(data.msg);
            }
        })
    });
</script>
</body>
</html>
