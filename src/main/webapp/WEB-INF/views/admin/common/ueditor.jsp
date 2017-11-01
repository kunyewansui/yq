<%--
  Created by IntelliJ IDEA.
  User: GustinLau
  Date: 2017-05-11
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<%=request.getContextPath()%>/assets/admin/plugins/ueditor/ueditor.config.js"></script>
<script>
    window.UEDITOR_CONFIG.serverUrl = "<%=request.getContextPath()%>/admin/ueditor/controller"
</script>
<!-- 编辑器源码文件 -->
<script src="<%=request.getContextPath()%>/assets/admin/plugins/ueditor/ueditor.all.min.js"></script>