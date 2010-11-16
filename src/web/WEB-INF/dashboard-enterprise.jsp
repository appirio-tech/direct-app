<%--
  - Author: isv, TCSASSEMBLER
  -
  - Version: 1.0.1 (Direct Enterprise Dashboard Assembly 1.0)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Enterprise Dashboard view.
  -
  - Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
  - Show project status color in enterprise health table.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<ui:dashboardPageType tab="overview"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/datepicker.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/dashboard-view.css" media="all" type="text/css"/>

    <!-- load the data for Charts -->
    <script type="text/javascript">
        var chartData =
        {
            contest:{
                column:["date", "Customer Avg Contest Duration", "Market Avg Contest Duration"],
                week:[
                <c:forEach items="${viewData.durationStats['WEEK']}" var="stat" varStatus="loop">
                    <c:if test="${loop.index > 0}">,</c:if>
                    {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                </c:forEach>
                ],
                month:[
                    <c:forEach items="${viewData.durationStats['MONTH']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                quarter:[
                    <c:forEach items="${viewData.durationStats['QUARTER']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                year:[
                    <c:forEach items="${viewData.durationStats['YEAR']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ]
            },
            cost:{
                column:["date", "Customer Avg Cost", "Market Avg Cost"],
                week:[
                    <c:forEach items="${viewData.costStats['WEEK']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                month:[
                    <c:forEach items="${viewData.costStats['MONTH']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                quarter:[
                    <c:forEach items="${viewData.costStats['QUARTER']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                year:[
                    <c:forEach items="${viewData.costStats['YEAR']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ]
            },
            fulfill:{
                column:["date", "Customer Avg Fulfillment", "Market Avg Fulfillment"],
                week:[
                    <c:forEach items="${viewData.fulfillmentStats['WEEK']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                month:[
                    <c:forEach items="${viewData.fulfillmentStats['MONTH']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                quarter:[
                    <c:forEach items="${viewData.fulfillmentStats['QUARTER']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ],
                year:[
                    <c:forEach items="${viewData.fulfillmentStats['YEAR']}" var="stat" varStatus="loop">
                        <c:if test="${loop.index > 0}">,</c:if>
                        {date:"<c:out value='${stat.timePeriodLabel}'/>",customer:<fmt:formatNumber value="${stat.clientValue}" pattern="0.##" />,tc:<fmt:formatNumber value="${stat.overallValue}" pattern="0.##" />}
                    </c:forEach>
                ]
            }
        }
    </script>
    <!-- google visualization -->
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript" src="/scripts/dashboard-chart.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js"></script>
    <script type="text/javascript" src="/scripts/jquery.dataTables.js"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">

                            <div class="spaceWhite"></div>
                            <div class="dashboardTable">
                                <dl>
                                    <dt>
                                        <a href="javascript:void(0)" class="expand">Enterprise Health</a>
                                    </dt>
                                    <dd>
										<table cellpadding="0" cellspacing="0" id="enterpriseHealthTable">
											<div class="dashboardTableHeader">
                                                <colgroup>
                                                    <col width="32%"/>
                                                    <col width="17%"/>
                                                    <col width="17%"/>
                                                    <col width="17%"/>
                                                    <col width="17%"/>
                                                    <col/>
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th class="first">Project</th>
                                                    <th>Average Contest Duration (Hours)</th>
                                                    <th>Average Cost per Contest</th>
                                                    <th>Total Project Cost</th>
                                                    <th>Average Fulfillment</th>
                                                </tr>
                                                </thead>
											</div>
											<div>
                                                <tbody>
                                                <c:forEach items="${viewData.projects}" var="project">
                                                    <tr>
                                                        <td class="first">
                                                            <a class="longWordsBreak ${project.projectStatusColor.name}" href="projectOverview?formData.projectId=${project.project.id}">
                                                                <c:out value="${project.project.name}"/></a>
                                                        </td>
                                                        <td><span class="">
                                                            <fmt:formatNumber value="${project.averageContestDuration}"
                                                                              pattern="##0.##"/></span></td>
                                                        <td><span class="">
                                                            <fmt:formatNumber value="${project.averageCostPerContest}"
                                                                              pattern="$#,##0.00"/></span></td>
                                                        <td><span class="">
                                                            <fmt:formatNumber value="${project.totalProjectCost}"
                                                                              pattern="$#,##0.00"/></span></td>
                                                        <td><span class="">
                                                            <fmt:formatNumber value="${project.averageFulfillment}"
                                                                              pattern="##0.##"/>%</span></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
											</div>
										</table>
                                    </dd>
                                </dl>
                            </div>
                            <!-- End .dashboardTable -->

                            <div class="chartCollapse">
                                <a class="expand" href="javascript:void(0)"></a>
                            </div>
                            <div class="visualization">
                                <s:form action="dashboardEnterprise" namespace="/" id="EnterpriseDashboardForm"
                                        method="get">
                                    <div class="top">
                                        <div class="projectNameSelect">
                                            <s:select list="viewData.projectsLookupMap" name="formData.projectId"/>
                                        </div>
                                        <div class="displaying">
                                            <span>Displaying :</span>
                                            <input type="radio" name="displaying" value="contest"/>
                                            <span>Contest Duration&nbsp;</span>
                                            <input type="radio" name="displaying" checked="checked" value="cost"/>
                                            <span>Cost&nbsp;</span>
                                            <input type="radio" name="displaying" value="fulfill"/>
                                            <span>Fulfillment</span>
                                        </div>
                                    </div>
                                    <!-- top -->

                                    <div class="chart"></div>
                                    <!-- chart container-->

                                    <div class="bottom">
                                        <div class="leftPanel">
                                            <div>
                                                <label>Start: </label>
                                                <s:textfield name="formData.startDate" readonly="true" id="startDateEnterprise"
                                                             cssClass="text date-pick"/>
                                            </div>
                                            <div>
                                                <label>End: </label>
                                                <s:textfield name="formData.endDate" readonly="true" id="endDateEnterprise"
                                                             cssClass="text date-pick"/>
                                            </div>

                                            <select id="timeDimension">
                                                <option value="">Select Time Dimension</option>
                                                <option value="week">Week</option>
                                                <option value="month" selected="selected">Month</option>
                                                <option value="quarter">Quarter</option>
                                                <option value="year">Year</option>
                                            </select>

                                        </div>
                                        <div class="rightPanel">
                                            <s:select list="viewData.projectCategories" multiple="true"
                                                      name="formData.projectCategoryIds" size="5"/>
                                            <a href="javascript:" id="enterpriseDashboardSubmit">Apply</a>
                                        </div>
                                        <div class="clear"></div>
                                        <div id="validationErrors"></div>
                                    </div>
                                    <!-- bottom -->
                                </s:form>
                            </div>
                            <!-- End .visualization -->
                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
