<%--
  Created by IntelliJ IDEA.
  User: gustinlau
  Date: 26/10/2017
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="xs" uri="http://code.xiaosuokeji.com/tags/jsp/xs" %>
<div class="app-header-fixed ">
<div class="app-header navbar">
    <div class="navbar-header bg-black" style="max-height: 50px;height:50px;">
        <button class="pull-right visible-xs dk" data-toggle-class="show" data-toggle-target=".navbar-collapse">
            <i class="glyphicon glyphicon-cog"></i>
        </button>
        <button class="pull-right visible-xs" data-toggle-class="off-screen" data-toggle-target=".app-aside">
            <i class="glyphicon glyphicon-align-justify"></i>
        </button>
        <a href="<%=request.getContextPath()%>/" class="navbar-brand text-lt hidden-xs" style="display: block;margin: 0 auto">
            <img style="display: block;margin: 8px auto 0 auto;max-height: 36px;max-width: 150px;"
                 src="<%=request.getContextPath()%>/assets/admin/img/logo.png"/>
        </a>
    </div>
    <div class="collapse pos-rlt navbar-collapse box-shadow bg-white-only">
        <div id="t_nav" class="nav navbar-nav">
                <sec:authorize access="hasAnyRole(${xs:getPermissions('content')})">
                <a href="<%=request.getContextPath()%>/admin/content" class="btn no-shadow navbar-btn">
                    图文
                </a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole(${xs:getPermissions('marketing')})">
                <a href="<%=request.getContextPath()%>/admin/marketing" class="btn no-shadow navbar-btn">
                    营销
                </a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole(${xs:getPermissions('system')})">
                <a href="<%=request.getContextPath()%>/admin/system" class="btn no-shadow navbar-btn">
                    系统
                </a>
                </sec:authorize>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle clear">
                    <span class="hidden-sm hidden-md"><sec:authentication property="principal.name"/></span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu animated fadeInRight w">
                    <sec:authorize access="hasAnyRole(${xs:getPermissions('staff_update')})">
                    <li>
                        <a href="javascript:editPersonalInfo()">修改信息</a>
                    </li>
                    </sec:authorize>
                    <li>
                        <a href="javascript:logout()">登出</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</div>
<script>
    function logout() {
        doPost("<%=request.getContextPath()%>/admin/logout", {}, function (data) {
            if (data.status) {
                window.location.href = "<%=request.getContextPath()%>/admin/login";
            }
        })
    }
</script>
<sec:authorize access="hasAnyRole(${xs:getPermissions('staff_update')})">
    <%--修改个人信息--%>
<%@include file="../common/validate.jsp" %>
    <div class="modal fade" id="editPersonalInfo" data-backdrop="static" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改个人信息</h4>
                </div>
                <div class="modal-body">
                    <form name="editPersonalInfoForm" class="form-horizontal">
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">用户名</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="text" name="username"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label">密码</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="password" style="display: none"/>
                                <input class="form-control" type="password" name="password" maxlength="20"
                                       placeholder="留空则不修改"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">姓名</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="text" name="name"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label required">手机</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="text" name="mobile"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label">email</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="email" name="email"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-xs-3 text-right">
                                <label class="control-label">QQ</label>
                            </div>
                            <div class="col-xs-9">
                                <input class="form-control" type="text" name="qq"/>
                            </div>
                        </div>
                    </form>
                    <div class="clearfix"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-info" onclick="submitEditPersonalInfoForm()">确定</button>
                </div>
            </div>
        </div>
    </div>
    <script>

        var $editPersonalInfoForm = $("form[name='editPersonalInfoForm']");
        var editPersonalInfoValidate = $editPersonalInfoForm.validate({
            rules: {
                username: {
                    required: true,
                    notEmpty: true
                },
                password: {
                    minlength: 6,
                    maxlength: 20
                },
                mobile: {
                    required: true,
                    notEmpty: true,
                    mobile: true
                },
                name: {
                    required: true,
                    notEmpty: true
                },
                status: {
                    required: true
                },
                email: {
                    email: true
                }
            },
            messages: {
                username: {
                    required: "用户名不能为空",
                    notEmpty: "用户名不能为空"
                },
                password: {
                    minlength: "密码至少为6位",
                    maxlength: "密码至多位20位"
                },
                mobile: {
                    required: "手机不能为空",
                    notEmpty: "手机不能为空",
                    mobile: "手机格式不对"
                },
                name: {
                    required: "名称不能为空",
                    notEmpty: "名称不能为空"
                },
                status: {
                    required: "请选择状态"
                },
                email: {
                    email: "email格式不对"
                }
            },
            submitHandler: function (form) {
                var params = $(form).xsJson();
                if (params.password == "") params.password = undefined;
                doPost("<%=request.getContextPath()%>/admin/security/secStaff/info/update", params, function (data) {
                    if (data.status) {
                        bootoast({message: "更新成功！"});
                        window.location.reload(true);
                    } else {
                        alert(data.msg);
                    }
                });
            }
        });

        $('#editPersonalInfo').on('hide.bs.modal', function (e) {
            editPersonalInfoValidate.resetForm();
            $("#editPersonalInfo").find(".text-danger").removeClass("text-danger");
        });

        function editPersonalInfo() {
            doPost("<%=request.getContextPath()%>/admin/security/secStaff/info/get", {id: <sec:authentication property="principal.id"/>}, function (data) {
                if (data.status) {
                    var _data = data.data;
                    $editPersonalInfoForm.find("input[name='id']").val(_data.id);
                    $editPersonalInfoForm.find("input[name='name']").val(_data.name);
                    $editPersonalInfoForm.find("input[name='username']").val(_data.username);
                    $editPersonalInfoForm.find("input[name='mobile']").val(_data.mobile);
                    $editPersonalInfoForm.find("input[name='qq']").val(_data.qq);
                    $editPersonalInfoForm.find("input[name='email']").val(_data.email);
                    $("#editPersonalInfo").modal('show');
                } else {
                    alert(data.msg);
                }
            });
        }

        function submitEditPersonalInfoForm() {
            $editPersonalInfoForm.submit();
        }
    </script>
</sec:authorize>