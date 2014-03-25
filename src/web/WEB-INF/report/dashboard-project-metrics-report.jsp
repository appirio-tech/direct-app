<%--
  - Author: Ghost_141, csy2012, TCSASSEMBER
  - Version: 1.4
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for project metrics report including form and report data.
  -
  - Version 1.0 (TC Cockpit Project Metrics Report )
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - - Update the layout to fix a layout issue.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 2.0) changes:
  - - Update the layout to fix a layout issue.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to report
  -
  - Version 1.4 (TC Direct Rebranding Assembly Copilot and Reporting related pages)
  - - Rebranding the copilot and reporting related pages.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:reportPageType tab="reports"/>
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
<div id="content" class="projectMetricsReportContent">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent" class="newSidebarCollapse">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Project Metrics Report"/>
</jsp:include>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="projectMetricsReports" data-intro="This report summarizes your project metrics. It's helpful when you want to see all of your projects budgets, costs, # of contests, etc. in one view. Use the filter to narrow your results." data-step="1">

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

                <div id="datefilter" data-intro="The dates represent the start and completion dates of your projects.  Set the date range wide enough to include all of your projects." data-step="2">

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
                <div class="filterBillingAccount" data-intro="If you have multiple billing accounts, you can select one here." data-step="3">
                    <label for="formData.billingAccountId">Billing Account:</label>
                    <s:select list="viewData.clientBillingProjects"
                                      id="formData.billingAccountId"
                                      name="formData.billingAccountId"/>

                </div>
                <!-- end .filterBillingAccount -->
                <div class="multiSelectArea" data-intro="Include or exclude specific project statuses." data-step="4">
                     <div class="multiSelectAreaInner"> 
                        <label class="multiSelectAreaTitle1">Status:</label>
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
            <div class="filterProject2" data-intro="Select a specific project, or All." data-step="5">
                <label>Project Name:</label>
                <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
            </div>
            <!-- end .filterProject -->
            <div class="clearFix"></div>

        </div>

        <div class="applyButtonBox" data-intro="Click Apply to run the report." data-step="6">
            <a class="button6 applyButton" href="javascript:;" id="reportMetricsReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
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
<div id="projectMetricsReportsSection" data-intro="Here are your results. Click the column headers to sort. Click a project name to view the dashboard for that project." data-step="7">
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
            <th class="tableColumn">Total<br /><nobr> # of Challenges</nobr></th>
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
            <td class="lastCol">
                <fmt:formatNumber value="${item.projectFulfillment}" pattern="##0.##"/>%
            </td>
        </tr>
        </c:forEach>
    </tbody>
    <tfoot>
        <tr class="reportTotal"><td colspan="3" style="text-align: right">Total Sum:&nbsp;&nbsp;</td><td></td><td></td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
            <td></td><td></td><td></td><td class="lastCol"></td>
        </tr>
    </tfoot>
</table>

        <div class="container2Left">
            <div class="container2Right">
                <div class="container2Bottom">
                    <div>
                        <div>

                            <div class="panel tableControlPanel">
                                <div class="exportControl" data-intro="Like Excel?  Export to Excel by clicking this icon." data-step="8">
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
