<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 11/1/17
  Time: 4:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="cmn-hans">
<head>
    <title>token管理</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/validate.jsp" %>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="./content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">token管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="col-xs-12">
                <form class="form-horizontal" id="searchForm">
                    <div class="form-group">
                        <div class="col-xs-4 col-md-2 col-lg-1  no-padder m-b-md text-right">
                            <label class="control-label">版本名称：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[name]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.name}">
                        </div>
                        <div class="col-xs-4 col-md-2 col-lg-1   no-padder m-b-md text-right">
                            <label class="control-label">描述：</label>
                        </div>
                        <div class="col-xs-8 col-md-4 col-lg-3  m-b-md">
                            <input name="dynamic[desc]" type="text" class="form-control" placeholder="模糊查询"
                                   value="${search.dynamic.desc}">
                        </div>
                    </div>
                    <div class="form-group m-t-n-md">
                        <div class="col-xs-12">
                            <sec:authorize access="hasAnyRole(${xs:getPermissions('system_version_create')})">
                            <a href="#" onclick="showCreateModal();return false"
                               class="btn btn-success pull-left">新增</a>
                            </sec:authorize>
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
                            <th>ID</th>
                            <th>用户ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageModel.list.size() eq 0}">
                            <tr>
                                <td colspan="6">无数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageModel.list}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.userId}</td>
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
  function uploadApk(type) {
      var formData=new FormData();
      formData.append("folders","apk");
      if(type==0){
          formData.append("files",$("#create_file").get(0).files[0]);
          uploadFile("<%=request.getContextPath()%>/admin/common/api/file/upload",formData,function (data) {
              if(data.status){
                  bootoast({message: "上传成功！", timeout: 1});
                  $("#create_url").val(data.data[0]);
              }else{
                  alert(data.msg);
              }
          });
      }else{
          formData.append("files",$("#update_file").get(0).files[0]);
          uploadFile("<%=request.getContextPath()%>/admin/common/api/file/upload",formData,function (data) {
              if(data.status){
                  bootoast({message: "上传成功！", timeout: 1});
                  $("#update_url").val(data.data[0]);
              }else{
                  alert(data.msg);
              }
          });
      }
  }
</script>




</body>
</html>
