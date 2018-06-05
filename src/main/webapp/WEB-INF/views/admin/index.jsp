<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  Staff: GustinLau
  Date: 2017-04-04
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <%@include file="./common/head.jsp" %>
    <script src="<%=request.getContextPath()%>/assets/admin/plugins/echarts/echarts.common.min.js"></script>
</head>
<body>
<%@include file="./common/header.jsp" %>
<c:set var="index" value="index"/>
<%@include file="./common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="wrapper-md">
            <div class="col-xs-3 no-padder">
                <div class="col-xs-12">
                    <div class="panel padder-v m-b text-center">
                        <span class="text-muted text-xs">本月销售额</span>
                        <div class="h1 text-info font-thin h1">
                            <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${orderAmount}"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="block panel padder-v bg-info m-b text-center">
                        <span class="text-muted text-xs">本月订单笔数</span>
                        <span class="text-white font-thin h1 block">
                            <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${orderCount}"/>
                        </span>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="hbox bg-primary text-center m-b">
                        <div class="col wrapper">
                            <span class="text-muted text-xs">总尾款</span>
                            <div class="font-thin text-info h1">
                                <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${paymentAmount}"/>
                            </div>
                        </div>
                        <div class="col wrapper bg-info">
                            <span class="text-muted text-xs">欠款客户数</span>
                            <div class="font-thin text-warning h1">
                                <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${paymentCount}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="panel padder-v m-b text-center">
                        <span class="text-muted text-xs">本月还款总额</span>
                        <div class="font-thin h1">
                            <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${repayAmount}"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-9 no-padder">
                <div class="panel wrapper-md">
                    <div id="main" style="height:367px;"></div>
                </div>
            </div>
            <div class="col-xs-6 ">
                <div class="panel">
                    <div class="panel-heading pos-rlt b-b b-light font-bold">
                        公告栏
                    </div>
                    <div class="panel-body xs-scrollbar" style="height: 284px;overflow: auto;">
                        <c:if test="${noticeBoard eq null}">
                            <p class="text-muted text-center">暂无任何公告</p>
                        </c:if>
                        <c:if test="${noticeBoard ne null}">
                            ${noticeBoard.content}
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 no-padder">
                <div class="panel">
                    <div class="panel-heading b-b b-light font-bold">
                        <span class="badge bg-danger pull-right">${messages.size() eq 0?"":messages.size()}</span>
                        <span>消息栏</span>
                    </div>
                    <c:if test="${messages.size() eq 0}">
                        <div class="panel-body text-muted text-center">暂无消息</div>
                    </c:if>
                    <div class="list-group xs-scrollbar" style="height: 284px;overflow: auto;">
                        <c:forEach items="${messages}" var="item">
                            <a href="<%=request.getContextPath()%>/admin/merchant/merchant/detail?id=${item.id}" class="list-group-item clearfix" style="border-bottom: 1px solid #edf1f2;">
                                <span class="pull-left thumb-sm avatar m-r">
                                  <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="头像">
                                  <i class="on b-white bottom"></i>
                                </span>
                                <span class="clear">
                                  <span>${item.name}</span>
                                  <small class="text-muted clear text-ellipsis">
                                      欠款
                                      <fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="0" value="${item.debt}"/>
                                      元
                                  </small>
                                </span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var myChart = echarts.init(document.getElementById('main'));

    option = {
        color: ['#23b7e5'],
        title : {
            text: '本月销售流水情况',
            textStyle: {
                fontWeight: 400,
                color: '#23b7e5'
            },
            x: 'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            selectedMode: 'single',
            data:['实际流水'],
            x: 'left'
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: "日期",
            data: ${result.labels}
        },
        yAxis: {
            type: 'value',
            name: '流水',
            axisLabel: {
                formatter: '{value} 元'
            }
        },
        series: [
            {
                type: 'line',
                name: '实际流水',
                areaStyle: {normal: {}},
                smooth: true,
                data: ${result.actualDatasets},
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</html>

