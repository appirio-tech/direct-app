<%--
  - Author: TCSASSEMBER, Ghost_141
  - Version: 1.1 (Release Assembly - TopCoder Cockpit Direct UI - Text and Layout Part 1 Bugs Termination)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for project metrics report including form and report data.
  -
  - Version 1.0 (TC Cockpit Project Metrics Report )
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - - Update the layout to fix a layout issue.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/dashboard-project-metrics-report.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <link rel="stylesheet" href="/css/direct/dashboard-enterprise.css?v=210282" media="all" type="text/css"/>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Project Metrics Report"/>
</jsp:include>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="projectMetricsReports">

    <!-- .filterTitle -->
    <div class="filterTitle">
        <div class="filterTitleRight">
            <div class="filterTitleCenter">
                <a href="javascript:;" class="expanded"></a>
                <h4>Filter</h4>
            </div><!-- End .filterTitleCenter -->
        </div><!-- End .filterTitleRight -->
    </div>
    <!-- End .filterTitle -->

    <!-- .filterContainer -->
    <s:form method="get" action="dashboardGetProjectMetricsReport" namespace="/"
            id="dashboardProjectMetricsReportForm">
    <s:hidden name="formData.excel" id="formDataExcel" value="false"/>
    <div class="filterContainer">
        <div class="filterLeftTwoParts">
            <div class="filterColumnPart">
                <!-- .filterCustomerName -->
                <div class="filterCustomerName">
                    <label for="formData.customerId">Customer Name:</label>
                    <s:select list="viewData.clientAccounts" id="formData.customerId"
                        name="formData.customerId"/>
                </div>
                <!-- end .filterCustomerName -->

                <div id="datefilter">

                    <div class="filterRow firstFilterRow">
                        <label for="startDate">Start:</label>
                        <s:textfield name="formData.startDate" readonly="true"
                                         id="startDateParticipationReport"
                                         cssClass="text date-pick fLeft dp-applied"/>
                        <div class="clearFix"></div>
                    </div><!-- end .filterRow -->

                    <div class="filterRow">
                        <label for="endDate">End:</label>
                        <s:textfield name="formData.endDate" readonly="true"
                                         id="endDateParticipationReport" cssClass="text date-pick fLeft dp-applied"/>
                        <div class="clearFix"></div>
                    </div><!-- end .filterRow -->

                </div>
                <!-- end .datefilter -->
            </div>

            <div class="filterColumnPart">
                <!-- .filterBillingAccount -->
                <div class="filterBillingAccount">
                    <label for="formData.billingAccountId">Billing Account:</label>
                    <s:select list="viewData.clientBillingProjects"
                                      id="formData.billingAccountId"
                                      name="formData.billingAccountId"/>

                </div>
                <!-- end .filterBillingAccount -->
                <div class="multiSelectArea">
                    <div class="multiSelectAreaInner">
                        <label class="multiSelectAreaTitle">Status:</label>
                        <s:select list="viewData.projectStatus" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.projectStatusIds"
                                      name="formData.projectStatusIds" size="4"/>
                    </div>
                </div>
                <!-- end .multiSelectArea -->
            </div>
            <div class="clearFix"></div>
        </div>

        <div class="filterColumnThird">
            <!-- .filterProject -->
            <div class="filterProject2">
                <label>Project Name:</label>
                <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
            </div>
            <!-- end .filterProject -->
            <div class="clearFix"></div>

        </div>

        <div class="applyButtonBox">
            <a class="button6 applyButton" href="javascript:;" id="reportMetricsReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
            <div class="clearFix"></div>
        </div>
        <!-- end .applyButtonBox -->
        <s:if test="hasActionErrors()">
            <div id="validationErrors">
                <s:actionerror/>
            </div>
        </s:if>
    </div>
    </s:form>
    <!-- End .filterContainer -->

</div>
<!-- End .pipelineFilter -->

<s:if test="not viewData.showJustForm and not hasActionErrors()">
<!-- projectMetricsReportsSection -->
<div id="projectMetricsReportsSection">
<div class="resultTableContainer">
<table id="costDetails" class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
    <!-- <colgroup>
        <col width="21%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="12%" />
    </colgroup>-->
    <thead>
        <tr>
            <th class="tableTitle" colspan="16">
                <a href="javascript:void(0)" class="expand">&nbsp;</a>
                <span>Project Metrics Report</span>
            </th>
        </tr>

        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn">Project Name</th>
            <th class="tableColumn">Project<br />Type</th>
            <th class="tableColumn">Project<br />Status</th>
            <th class="tableColumn">Draft</th>
            <th class="tableColumn">Scheduled</th>
            <th class="tableColumn">Active</th>
            <th class="tableColumn">Finished</th>
            <th class="tableColumn">Canceled</th>
            <th class="tableColumn">Total<br />Budget</th>
            <th class="tableColumn">Actual<br />Cost</th>
            <th class="tableColumn">Planned<br />Cost</th>
            <th class="tableColumn">Total<br />Projected Cost</th>
            <th class="tableColumn">Start<br />Date</th>
            <th class="tableColumn">Completion<br />Date</th>
            <th class="tableColumn">Total<br /><nobr> # of Contests</nobr></th>
            <th class="tableColumn">Project<br />Fulfillment</th>
        </tr>
    </thead>
    <tbody>
        <%-- project aggregation report --%>
        <c:forEach items="${viewData.entries}" var="item"
               varStatus="loop">
               
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        
        <tr class="projectAggregationReport ${rowStyle}">
            <td>
                <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId">${item.projectId}</s:param></s:url>">
                   ${item.projectName}
                </a>
            </td>
            <td>${item.projectType}</td>
            <td>
                ${item.projectStatus}
            </td>
            <td>
                ${item.numDraft}/<fmt:formatNumber value="${item.costDraft}" pattern="$#,##0.00"/>
            </td>
            <td>
                ${item.numScheduled}/<fmt:formatNumber value="${item.costScheduled}" pattern="$#,##0.00"/>
            </td>
            <td>
                ${item.numActive}/<fmt:formatNumber value="${item.costActive}" pattern="$#,##0.00"/>
            </td>
            <td>
                ${item.numFinished}/<fmt:formatNumber value="${item.costFinished}" pattern="$#,##0.00"/>
            </td>
            <td>
                ${item.numCanceled}/<fmt:formatNumber value="${item.costCanceled}" pattern="$#,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalBudget}" pattern="$#,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.actualCost}" pattern="$#,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.plannedCost}" pattern="$#,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.projectedCost}" pattern="$#,##0.00"/>
            </td>
            <td>
                <c:if test="${not (item.startDate eq null)}">
                    <fmt:formatDate value="${item.startDate}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)
                </c:if>
            </td>
            <td>
                <c:if test="${not (item.completionDate eq null)}">
                    <fmt:formatDate value="${item.completionDate}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)
                </c:if>     
            </td>
            <td>${item.totalContests}</td>
            <td>
                <fmt:formatNumber value="${item.projectFulfillment}" pattern="##0.##"/>%
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>

        <div class="container2Left">
            <div class="container2Right">
                <div class="container2Bottom">
                    <div>
                        <div>

                            <div class="panel tableControlPanel">
                                <div class="exportControl">
                                    <a href="javascript:getMetricsReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

</div>
</div>
</s:if>

<!-- End .container2Content -->
<!-- End .container2 -->
</div>
</div>

</div>
<!-- End #mainContent -->

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
