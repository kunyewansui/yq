<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@include file="./common/content_nav.jsp" %>

<div class="app-content ">
    <div class="app-content-body">
        <div class="wrapper-md">
            <div class="col-xs-3 no-padder">
                <div class="col-xs-12">
                    <div class="panel padder-v m-b text-center">
                        <div class="h1 text-info font-thin h1">521</div>
                        <span class="text-muted text-xs">New items</span>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="hbox bg-primary text-center m-b">
                        <div class="col wrapper">
                            <div class="font-thin text-info h1">129</div>
                            <span class="text-muted text-xs">Feeds</span>
                        </div>
                        <div class="col wrapper bg-info">
                            <div class="font-thin text-warning h1">2,560</div>
                            <span class="text-muted text-xs">VIP</span>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="block panel padder-v bg-info m-b text-center">
                        <span class="text-white font-thin h1 block">432</span>
                        <span class="text-muted text-xs">Comments</span>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="panel padder-v m-b text-center">
                        <div class="font-thin h1">129</div>
                        <span class="text-muted text-xs">Feeds</span>
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
                    <div class="panel-heading pos-rlt b-b b-light">
                        公告栏
                    </div>
                    <div class="panel-body xs-scrollbar" style="height: 284px;overflow: auto;">
                        <p class="text-muted text-center">暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                        <p>暂无任何公告</p>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 no-padder">
                <div class="panel">
                    <div class="panel-heading b-b b-light">
                        <span class="badge bg-danger pull-right">4</span>
                        <span>消息栏</span>
                    </div>
                    <div class="panel-body text-muted text-center">暂无信息</div>
                    <ul class="list-group list-group-lg no-bg auto xs-scrollbar" style="height: 284px;overflow: auto;">
                        <li class="list-group-item clearfix">
                            <span class="pull-left thumb-sm avatar m-r">
                              <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="...">
                              <i class="on b-white bottom"></i>
                            </span>
                            <span class="clear">
                              <span>Chris Fox</span>
                              <small class="text-muted clear text-ellipsis">What's up, buddy</small>
                            </span>
                        </li>
                        <li class="list-group-item clearfix">
                            <span class="pull-left thumb-sm avatar m-r">
                              <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="...">
                              <i class="on b-white bottom"></i>
                            </span>
                            <span class="clear">
                              <span>Amanda Conlan</span>
                              <small class="text-muted clear text-ellipsis">Come online and we need talk about the plans that we have discussed</small>
                            </span>
                        </li>
                        <li class="list-group-item clearfix">
                            <span class="pull-left thumb-sm avatar m-r">
                              <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="...">
                              <i class="on b-white bottom"></i>
                            </span>
                            <span class="clear">
                              <span>Amanda Conlan</span>
                              <small class="text-muted clear text-ellipsis">Come online and we need talk about the plans that we have discussed</small>
                            </span>
                        </li>
                        <li class="list-group-item clearfix">
                            <span class="pull-left thumb-sm avatar m-r">
                              <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="...">
                              <i class="busy b-white bottom"></i>
                            </span>
                            <span class="clear">
                              <span>Dan Doorack</span>
                              <small class="text-muted clear text-ellipsis">Hey, Some good news</small>
                            </span>
                        </li>
                        <li class="list-group-item clearfix">
                            <span class="pull-left thumb-sm avatar m-r">
                              <img src="<%=request.getContextPath()%>/assets/admin/img/a1.png" alt="...">
                              <i class="away b-white bottom"></i>
                            </span>
                            <span class="clear">
                              <span>Lauren Taylor</span>
                              <small class="text-muted clear text-ellipsis">Nice to talk with you.</small>
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var myChart = echarts.init(document.getElementById('main'));

    option = {
        color: ['#7266ba','#23b7e5'],
        title : {
            text: '月销售流水情况',
            textStyle: {
                fontWeight: 400,
                color: '#23b7e5'
            }
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
            selectedMode: 'multiple',
            data:['实际贷款流水','申请贷款流水']
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: [1,2,3,4,5,6,7]
        },
        yAxis: {
            type: 'value',
            name: '流水',
            axisLabel: {
                formatter: '{value} 万元'
            }
        },
        series: [
            {
                type: 'line',
                name: '实际贷款流水',
                areaStyle: {normal: {}},
                data: [4,5,1,12,9,3,2],
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            },
            {
                type: 'line',
                name: '申请贷款流水',
                areaStyle: {normal: {}},
                data: [1,2,3,4,5,6,7],
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

