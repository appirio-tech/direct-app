<%--
  - Author: Blues, flexme, GreatKevin
  - Version: 1.4
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 TC Cockpit Cost Report Update Cost Breakdown Assembly Change notes:
  - - Add a popup window to support the cost breakdown data.
  - Version 1.2 Direct Improvements Assembly Release 2 Assembly Change notes:
  - - Make 'Custom name' column before the 'project name' column.
  - Version 1.2 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  - Description: This page renders the view for cost report including form and report data.
  -
  - Version 1.3 (TC Cockpit Permission and Report Update One) change log:
  - - Change parameter name from projectIds to projectId.
  - - Change parameter name from billingAccountIds to billingAccount.
  - - Change parameter name from customerIds to customerId.
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
  - - Add the search bar for cost report details table.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/filter-panel.css?v=210396" media="all" type="text/css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/filter-panel-ie7.css?v=210396"/>
    <![endif]-->
    <script type="text/javascript" src="/scripts/tableSearchBar.js?v=210396"></script>
    <script type="text/javascript" src="/scripts/jquery.multiselect.js?v=196003"></script>
    <script type="text/javascript" src="/scripts/dashboard-cost-report.js?v=210122"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <link rel="stylesheet" href="/css/dashboard-enterprise.css?v=210282" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/jquery.multiSelect.css?v=196003" media="all" type="text/css"/>

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
    <jsp:param name="reportTitle" value="Cost Report"/>
</jsp:include>

<%-- cost report form --%>
<div class="pipelineSearch">
    <s:form method="get" action="dashboardGetCostReport" namespace="/"
            id="dashboardCostReportForm">
        <s:hidden name="formData.excel" id="formDataExcel" value="false"/>
        <s:hidden name="formData.showBreakdown" id="formDataShowBreakdown" value="false"/>
        <div id="costReportFilters" class="filterLinkArea">
          <div id="costReportFilterShowControl">
            <a class="fiterButton" href="javascript:">Filters</a>
          </div>

            <div class="filterArea">

        <div class="filterCustomName" id="customerNameFilter">
                    <span class="label">Customer Name</span>
                    <s:select list="viewData.clientAccounts" id="formData.customerId"
                        name="formData.customerId" size="1"/>
                </div>

                <div class="filterProject">
                    <span class="label">Project Name </span>
                    <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
                </div>

                <div class="firstRow">
                    <div class="datePicker"> <strong>Payment Date</strong><br>
                        <div>
                            <span class="label">Start: </span>
                            <s:textfield name="formData.startDate" readonly="true"
                                         id="startDateCostReport"
                                         cssClass="text date-pick"/>
                        </div>
                        <div>
                            <span class="label">End: </span>
                            <s:textfield name="formData.endDate" readonly="true"
                                         id="endDateCostReport" cssClass="text date-pick"/>
                        </div>
                        <s:if test="hasActionErrors()">
                            <div id="validationErrors">
                                <s:actionerror/>
                            </div>
                        </s:if>
                    </div>

                    <div class="timeDimension">
                        <div class="columns contestType">
                            <strong>Contest Status</strong><br/>
                            <s:select list="viewData.contestStatus" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.statusIds"
                                      name="formData.statusIds" size="3"/>
                        </div>
                    </div>
                </div>
                <div class="spacer"></div>
                <div class="secondRow">
                    <div class="filterContest">
                        <div class="columns contestType">
                            <strong>Contest Type</strong><br/>
                            <s:select list="viewData.projectCategories" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.projectCategoryIds"
                                      name="formData.projectCategoryIds" size="5"/>
                        </div>

                        <div class="columns" id="clientBillingProjectsFilter">
                            <strong>Billing Account</strong><br/>
                            <s:select list="viewData.clientBillingProjects"
                                      id="formData.billingAccountId"
                                      name="formData.billingAccountId" size="1"/>
                        </div>

                        <div class="columns columnButton">
                            <a class="button6 applyButton" href="javascript:"
                               id="costReportSubmit">
                                <span class="left"><span class="right">Apply</span></span>
                            </a>
                        </div>

                    </div>
                </div>
            </div>
            <!-- End .filterArea -->
        </div>
    </s:form>
</div>

<c:if test="${not viewData.showJustForm}">

<%--
  Cost report area
--%>
<div id="costReportSection">

    <%-- aggregation cost report --%>
<table id="costReportAggregationArea" class="pipelineStats resultTable"
       cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th class="tableTitle" colspan="4">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Aggregation Cost Report</span>
        </th>
        <th class="tableTitle viewType">
            <label for="aggregationCostReportType">View By:</label>
            <select id="aggregationCostReportType">
                <option value="project">Project</option>
                <option value="billing">Billing Account</option>
                <option value="contestType">Contest Type</option>
                <option value="status">Contest Status</option>
            </select>
        </th>
    </tr>
    <tr class="projectAggregationCostReport scData">
        <th class="tableColumn">Project</th>
        <th class="tableColumn">Contest Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="billingAggregationCostReport scData hide">
        <th class="tableColumn">Billing Account</th>
        <th class="tableColumn">Contest Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="contestTypeAggregationCostReport scData hide">
        <th class="tableColumn">Contest Type</th>
        <th class="tableColumn">Contest Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="statusAggregationCostReport scData hide">
        <th class="tableColumn">Contest Status</th>
        <th class="tableColumn">Contest Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    </thead>
    <tbody>

        <%-- project aggregation report --%>
    <c:forEach items="${viewData.projectAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="projectAggregationCostReport scData  ${rowStyle}">
            <td><a href="<c:url value="dashboardGetCostReport?${item.drillInQuery}"/>" target="_blank"><c:out value="${item.name}"/></a></td>
            <td>
                <fmt:formatNumber value="${item.totalContestFees}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalEstimatedMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalActualMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalCosts}" pattern="$###,##0.00"/>
            </td>
        </tr>
    </c:forEach>

        <%-- billing account aggregation report --%>
    <c:forEach items="${viewData.billingAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="billingAggregationCostReport scData hide ${rowStyle}">
              <td><a href="<c:url value="dashboardGetCostReport?${item.drillInQuery}"/>" target="_blank"><c:out value="${item.name}"/></a></td>
            <td>
                <fmt:formatNumber value="${item.totalContestFees}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalEstimatedMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalActualMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalCosts}" pattern="$###,##0.00"/>
            </td>
        </tr>
    </c:forEach>

        <%-- contest type aggregation report --%>
    <c:forEach items="${viewData.contestTypeAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="contestTypeAggregationCostReport scData hide ${rowStyle}">
              <td><a href="<c:url value="dashboardGetCostReport?${item.drillInQuery}"/>" target="_blank"><c:out value="${item.name}"/></a></td>
            <td>
                <fmt:formatNumber value="${item.totalContestFees}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalEstimatedMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalActualMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalCosts}" pattern="$###,##0.00"/>
            </td>
        </tr>
    </c:forEach>

        <%-- status aggregation cost report --%>
    <c:forEach items="${viewData.statusAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="statusAggregationCostReport scData hide ${rowStyle}">
             <td><a href="<c:url value="dashboardGetCostReport?${item.drillInQuery}"/>" target="_blank"><c:out value="${item.name}"/></a></td>
            <td>
                <fmt:formatNumber value="${item.totalContestFees}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalEstimatedMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalActualMemberCosts}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.totalCosts}" pattern="$###,##0.00"/>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>

    <div class='filterPanel searchBar' id='costReportSearchBar'>
        <div class='filterHead'>
            <div class='rightSide'>
                <div class='inner'>
                    <div class='searchContainer'>
                        <span class='title'>Search</span>

                        <div class='filterSearch'>
                            <input type='text' class='searchBox'/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--end .filterHead-->
        <div class='collapseBottom'>
            <div class='rightSide'>
                <div class='inner'></div>
            </div>
        </div>
    </div>

<div class="resultTableContainer">
    <%-- Cost report Details --%>
<table id="costDetails" class="pipelineStats paginatedDataTable resultTable" cellpadding="0"
       cellspacing="0">
    <thead>
    <tr>
        <th class="tableTitle" colspan="8">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Cost Details</span>
        </th>
        <th class="tableTitle viewType" colspan="3">
            <select id="costDetailsViewType">
                <option value="default">Default View</option>
                <option value="breakdown">Cost Breakdown View</option>
            </select>
        </th>
    </tr>
    <tr>
        <th class="tableColumn">&nbsp;Customer&nbsp;</th>
        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
        <th class="tableColumn">&nbsp;Project&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest Type&nbsp;</th>
        <th class="tableColumn">&nbsp;Status&nbsp;</th>
        <th class="tableColumn">&nbsp;Completion Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest Fee&nbsp;</th>
        <th class="tableColumn">&nbsp;Estimated Member Cost&nbsp;</th>
        <th class="tableColumn">&nbsp;Actual Member Cost&nbsp;</th>
        <th class="tableColumn">&nbsp;&nbsp;&nbsp;Total&nbsp;&nbsp;&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${viewData.costDetails}" var="item" varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="pipelineDetailsRow" id="contest_${item.contest.id}">
            <td>
                <c:out value="${item.client.name}"/>
            </td>
            <td>
                <c:out value="${item.billing.name}"/>
            </td>
            <td>
                    <c:out value="${item.project.name}"/>

            <td>
                <c:out value="${item.contest.name}"/>
            </td>
            <td>
                <c:out value="${item.contestType.name}"/>
            </td>
            <td>
                <c:out value="${item.status}"/>
            </td>
            <td>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.completionDate}"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.contestFee}" pattern="$###,##0.00"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.estimatedCost}" pattern="$###,##0.00"/>
            </td>
            <td>

                 <c:set var="status" value="${fn:trim(item.status)}"/>
                <%-- only display the actual cost when the contest is finished --%>
                <c:if test="${status == 'Finished'}">
                    <fmt:formatNumber value="${item.actualCost}" pattern="$###,##0.00"/>
                </c:if>
            </td>
            <td>
                <fmt:formatNumber value="${item.total}" pattern="$###,##0.00"/>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

        <div class="container2Left">
            <div class="container2Right">
                <div class="container2Bottom">
                    <div class="container2BottomLeft">
                        <div class="container2BottomRight">

                            <div class="panel tableControlPanel">
                                <div class="exportControl">
                                    <a href="javascript:getCostReportAsExcel(false);" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

</div>
<!-- End .panel -->

</div>
</c:if>
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

<div class="popups">
    <div class="expandViewPopup hide" id="contestDViewPopup">
        <div class="close">
            <a href="javascript:void(0)" id="contestDViewClose"></a>.
        </div>
        <div class="popContent">
            <h2>Cost Details</h2>
            <div class="dashboardTable costTable nobg">
                <div id="breakdownBody" class="tableWrapper">
                    <table cellpadding="0" cellspacing="0" class="pipelineStats">
                        <thead>
                            <tr>
                                <th class="tableColumn sorting">Customer</th>
                                <th class="tableColumn sorting_desc">Billing</th>
                                <th class="tableColumn sorting">Project</th>
                                <th class="tableColumn sorting">Contest</th>
                                <th class="tableColumn sorting">Contest Type</th>
                                <th class="tableColumn sorting">Status</th>
                                <th class="tableColumn sorting">Completion Date</th>
                                <th class="tableColumn sorting">Contest Fee</th>
                                <th class="tableColumn sorting">Estimated Member Cost</th>
                                <th class="tableColumn sorting">Actual Member Cost</th>
                                <th class="tableColumn sorting">Prizes</th>
                                <th class="tableColumn sorting">Spec Review</th>
                                <th class="tableColumn sorting">Review</th>
                                <th class="tableColumn sorting">Reliability</th>
                                <th class="tableColumn sorting">Digital Run</th>
                                <th class="tableColumn sorting">Copilot</th>
                                <th class="tableColumn sorting">Build</th>
                                <th class="tableColumn sorting">Bugs</th>
                                <th class="tableColumn sorting">Misc</th>
                                <th class="tableColumn sorting">Total</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <div class="panel">
                        <div class="dataTables_info" id="breakdown_costDetails_info"></div>
                        <!-- this area contains the print, export to excel, export to pdf links -->
                        <a href="javascript:getCostReportAsExcel(true);"
                           class="exportExcel">Export to <strong>Excel</strong></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="hide handler">
        <a href="#" id="contestDViewMock"></a>
    </div>
</div>
</body>
<!-- End #page -->

</html>
