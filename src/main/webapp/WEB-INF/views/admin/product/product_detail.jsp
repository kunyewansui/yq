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
    <title>产品详情</title>
    <%@include file="../common/head.jsp" %>
    <%@include file="../common/treeview.jsp" %>
    <%@include file="../common/validate.jsp" %>
    <%@include file="../common/imgGlass.jsp" %>
</head>
<body>
<%@include file="../common/header.jsp" %>
<%@include file="../common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="bg-light lter b-b wrapper-md ">
            <h1 class="m-n font-thin h3 inline">产品详情</h1>
            <a href="javascript:history.go(-1);" class="btn btn-default pull-right">返回</a>
            <button class="btn btn-primary pull-right" style="margin-right: 5px" id="createSubmit">
                保存
            </button>
        </div>
        <div class="wrapper-md row">
            <div class="col-xs-12">
                <form class="form-horizontal" id="createForm" name="createForm">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${product.id}">
                        <div class="col-xs-2 col-md-2 col-lg-2  no-padder text-right">
                            <label class="control-label required">产品名称：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="name" type="text" class="form-control" value="${product.name}"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label required">款号：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="code" type="text" class="form-control" value="${product.code}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                            <label class="control-label required">主图片：</label>
                            <em style="display: block" class='text-muted'>（用于订单显示）</em>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3">
                            <xs:imageUploader identifier="productImage" name="image" folder="yq/product"/>
                            <c:if test="${product.image ne null && product.image ne ''}">
                                <script>
                                    $(function () {
                                        putImageIntoImageUploader("productImage","${product.image}");
                                    });
                                </script>
                            </c:if>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                            <label class="control-label required">所属类别：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input type="text" name="cateName" id="cateName" class="form-control" readonly
                                   onclick="showCategoryModel()"/>
                            <input type="hidden" name="cateId" value="${product.cateId}">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg"></div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label required">档口库存：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="shopStock" type="number" class="form-control" value="${product.shopStock}" placeholder="件">
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label required">工厂库存：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="factoryStock" type="number" class="form-control" value="${product.factoryStock}" placeholder="件">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">成本：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="cost" type="number" class="form-control" value="${product.cost}" placeholder="￥"/>
                        </div>
                        <div class="col-xs-2 col-md-2 col-lg-2   no-padder text-right">
                            <label class="control-label">出厂价：</label>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-3 ">
                            <input name="manuPrice" type="number" class="form-control" value="${product.manuPrice}" placeholder="￥"/>
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg"></div>
                    <div class="form-group">
                        <div class="col-xs-2 col-md-2 col-lg-2 no-padder text-right">
                            <label class="control-label">产品图片：</label>
                            <em style="display: block" class='text-muted'>（可传多张）</em>
                        </div>
                        <div class="col-xs-4 col-md-4 col-lg-8">
                            <div id="previewDiv" class="img-preview"></div>
                            <input id="uploadMulti" class="hidden" name="files" type="file" onchange="uploadPreview(this)"
                                   accept="image/png,image/jpg,image/jpeg,image/bmp,image/gif"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="categoryModel" data-backdrop="static" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">产品分类</h4>
            </div>
            <div class="modal-body">
                <div id="tree">
                    <p style="text-align: center">加载中...</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="treeData" type="text/plain">${cateTree}</script>
<script id="picsData" type="text/plain">${product.pics}</script>
<script>
    var $categoryModel = $("#categoryModel");
    $(function () {
        var treeData = JSON.parse($("#treeData").text());
        $('#tree').treeview({
            data: treeData,
            onNodeSelected: function (event, data) {
                $createForm.xsSetInput("cateName", data.name);
                $createForm.xsSetInput("cateId", data.id);
                $categoryModel.modal("hide");
                $createForm.validate().element($("#cateName"));
            }
        });
        $("#tree").treeview('selectNode', '${product.cateId}');
    });

    function showCategoryModel() {
        $categoryModel.modal("show");
    }

    <%--图片批量上传--%>
    var imgCount = 999;//证书最多显示张数
    var images = [];
    var $previewDiv = $("#previewDiv");
    var imglock = false;

    function updatePreviewDiv() {
        var html = "";
        $.each(images, function (index, image) {
            html += "<label style='width:120px;height:150px'>" +
                "<img src='" + image + "'/>" +
                "<a class='delete' href='javascript:deleteUpload(" + index + ")'>删除</a>" +
                "</label>";
        });
        if(images.length < imgCount){
            html += "<label style='width:120px;height:150px;vertical-align: top;'>" +
                "<a id='add' class='add' href='#' onclick='selectImg();return false;'>" +
                "<i class='fa fa-plus' style='line-height:150px'></i>" +
                "</a>" +
                "</label>";
        }
        $previewDiv.html(html);
        //初始化图片放大镜
        PostbirdImgGlass.init({
            domSelector: "#previewDiv img",
            animation: true
        });
    }

    function selectImg() {
        if(!imglock)
            $("#uploadMulti").click();
    }

    function uploadPreview(file) {
        if (file.value != "") {
            imglock = true;
            $("#add").html("<i class='fa fa-spinner fa-pulse' style='line-height:150px'></i>");
            //兼容IE
            $.ajaxFileUpload({
                url: '<%=request.getContextPath()%>/admin/common/api/file/upload2',
                secureuri: false,
                fileElementId: 'uploadMulti',
                data: {folders: 'yq/product'},
                dataType: "text", //ie低版本不支持json
                type: "post",
                success: function (str) {
                    var data = JSON.parse(str);
                    imglock = false;
                    if (data.status) {
                        images.push(data.data[0]);
                    } else {
                        alert(data.msg);
                    }
                    $("#certificates").val(images);
                    updatePreviewDiv();
                },
                error: function (res) {
                    alert("请求失败：" + res.statusText + "\n错误码：" + res.status);
                    imglock = false;
                    updatePreviewDiv();
                }

            });
            $("#uploadMulti").val("");
        }
    }

    function deleteUpload(index) {
        images.splice(index, 1);
        $("#certificates").val(JSON.stringify(images));
        updatePreviewDiv();
    }
    <%--图片批量上传结束--%>

    $(function () {

        //回显产品图片
        var picList = JSON.parse($("#picsData").text());
        if (picList.length > 0) {
            images = picList;
        }
        updatePreviewDiv();
    })

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
            code: {
                required: true,
                notEmpty: true
            },
            cateName: {
                required: true,
                notEmpty: true
            },
            shopStock: {
                required: true
            },
            factoryStock: {
                required: true
            }
        },
        messages: {
            name: {
                required: "名称不能为空",
                notEmpty: "名称不能为空"
            },
            code: {
                required: "款号不能为空",
                notEmpty: "款号不能为空"
            },
            cateName: {
                required: "类型不能为空",
                notEmpty: "类型不能为空"
            },
            shopStock: {
                required: "档口库存不能为空"
            },
            factoryStock: {
                required: "工厂库存不能为空"
            }
        },
        submitHandler: function () {
            var params = $createForm.xsJson();
            params.picList = images;
            console.log(JSON.stringify(params));
            $createSubmit.attr("disabled", true);
            $.ajax({
                url: "<%=request.getContextPath()%>/admin/product/product/update",
                type: "POST",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                cache: false,
                success: function (data) {
                    $createSubmit.attr("disabled", false);
                    if (data.status) {
                        bootoast({message: "更新成功！"});
                        history.go(-1);
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });
</script>
</body>
</html>
