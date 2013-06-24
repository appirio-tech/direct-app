<%--
  - Author: bugbuka, Ghost_141, csy2012
  - Version: 1.4 (Release Assembly - TopCoder Cockpit Direct UI layout Part 2 Bugs Termination)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (TC Cockpit Permission and Report Update One) change log:
  - - Add logics to display Checkpoint Winners, Final Winners, Total Unique Winners in Aggregation Participation Metrics Report section.
  -
  - Version 1.2 (TC Cockpit - Member Participation Metrics Report Upgrade) change log:
  - - Added a new option "Contest" to the "View By" dropdown list.
  - - Added two new columns "Checkpoint Submissions" and "Final Submissions" to the table for all view type.
  - - Split the "Aggregation Participation Metrics Report" table into multiple tables to support sorting and pagination.
  - - Show indicator when “Customer Name” or “Billing Account” has been changed.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) 
  - - Update layout to fix a layout issue.
  - - Remove the container2BottomLeft and container2BottomRight class in pagination part.
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0) 
  - - Fixed the "Aggregation Participation Metrics Report" Table header issue.
  - 
  - Description: This page renders the view for participation metrics report including form and report data.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/dashboard-participation-report.js?v=208327"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <link rel="stylesheet" href="/css/direct/dashboard-enterprise.css?v=210282" media="all" type="text/css"/>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="participationReportContent">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Participation Metrics Report"/>
</jsp:include>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="participationMetricsReports" data-intro="This report summarizes the participation metrics for your projects and competitions.  Essentially, it is a statistical view of YOUR community. Use the filter to narrow your results." data-step="1">

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
    <s:form method="get" action="dashboardGetParticipationReport" namespace="/"
            id="dashboardParticipationReportForm">
    <s:hidden name="formData.excel" id="formDataExcel" value="false"/>
    <s:hidden name="formData.viewType" id="formDataViewType" value="project"/>
    <div class="filterContainer">
        <div class="filterLeftTwoParts">
            <div class="filterColumnPart">
                <!-- .filterCustomerName -->
                <div class="filterCustomerName">
                	<div class="filterRowLeft">
                    	<label for="formData.customerId">Customer Name:</label>
                    </div>
                    <s:select list="viewData.clientAccounts" id="formData.customerId"
                        name="formData.customerId"/>
                </div>
                <!-- end .filterCustomerName -->

                <div id="datefilter" data-intro="The date filter constrains based on the completion times of competitions." data-step="2">

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

            <div class="filterColumnPart" data-intro="If you have multiple billing accounts, you can select one here or leave it as is to view all." data-step="3"> 
                <!-- .filterBillingAccount -->
                <div class="filterBillingAccount">
                	<div class="filterRowLeft">
                    	<label for="formData.billingAccountId">Billing Account:</label>
                    	<img src="/images/dots-white.gif" class="indicator" alt/>
                    </div>
                    <s:select list="viewData.clientBillingProjects"
                                      id="formData.billingAccountId"
                                      name="formData.billingAccountId"/>

                </div>
                <!-- end .filterBillingAccount -->
                <div class="multiSelectArea" data-intro="Narrow down based on competition statuses.  For this report, you probably want to include all." data-step="4">
                    <div class="multiSelectAreaInner statusFilter">
                        <label class="multiSelectAreaTitle">Status:</label>
                        <s:select list="viewData.contestStatus" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.statusIds"
                                      name="formData.statusIds" size="3"/>
                    </div>
                </div>
                <!-- end .multiSelectArea -->
            </div>
            <div class="clearFix"></div>
        </div>

        <div class="filterColumnThird">
            <!-- .filterProject -->
            <div class="filterProject2" data-intro="Select a specific project, or view All." data-step="5">
            	<div class="filterRowLeft">
                	<label for="formData.projectId">Project Name:</label>
                	<img src="/images/dots-white.gif" class="indicator" alt/>
                </div>
                <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
            </div>
            <!-- end .filterProject -->

            <div class="multiSelectArea" data-intro="We have many competition types.  If you don't want to see all of them, only select the ones you're interested in here." data-step="6">
                <div class="multiSelectAreaInner contestTypeFilter">
                    <label class="multiSelectAreaTitle">Contest Type:</label>
                    <s:select list="viewData.projectCategories" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.projectCategoryIds"
                                      name="formData.projectCategoryIds" size="5"/>
                </div>
            </div>
            <!-- end .multiSelectArea -->
            <div class="clearFix"></div>

        </div>

        <div class="applyButtonBox" data-intro="Click Apply to run the report." data-step="7">
            <a class="button6 applyButton" href="javascript:;" id="participationReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
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
<!-- participationMetricsReportsSection -->
<div id="participationMetricsReportsSection">

<table id="participationMetricsReportBasicMetrics" class="pipelineStats" cellpadding="0" cellspacing="0" data-intro="Here are your # of projects, contests, and copilots." data-step="8">
    <thead>
        <tr>
            <th class="tableTitle" colspan="3">
                <a href="javascript:void(0)" class="expand">&nbsp;</a>
                <span>Basic Metrics</span>
            </th>
        </tr>

        <tr class="subTheadRow">
            <th class="tableColumn">Projects</th>
            <th class="tableColumn">Contests</th>
            <th class="tableColumn">Copilots</th>

        </tr>
    </thead>
    <tbody>
        <tr>
            <td>${viewData.participationBasicReport.totalProjects}</td>
            <td>${viewData.participationBasicReport.totalContests}</td>
            <td>${viewData.participationBasicReport.totalCopilots}</td>
        </tr>
    </tbody>
</table>

<table id="participationMetricsReportAggregationArea" class="pipelineStats" cellpadding="0" cellspacing="0" data-intro="Here are all the details. Use the View By control at the right to pivot on different groupings of your data." data-step="9">
    <tr>
        <th class="tableTitle" colspan="7">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Participation Metrics Report</span>
        </th>
        <th class="tableTitle">&nbsp;</th>
        <th class="tableTitle viewType" colspan="6">

            <label for="aggregationParticipationReportType">View By:</label>
            <select id="aggregationParticipationReportType" class="aggregationParticipationReportType">
                <option value="project" selected="selected">Project</option>
                <option value="billing">Billing Account</option>
                <option value="type">Contest Type</option>
                <option value="status">Contest Status</option>
                <option value="contest">Contest</option>
            </select>

        </th>
    </tr>
</table>
<div class="resultTableContainer projectAggregationReport">
<table class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0" data-intro="Click on the column headers to sort." data-step="10">
    <colgroup>
        <col width="16%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
    </colgroup>
    <thead>
        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn first">Project</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Checkpoint<br />Submissions</th>
            <th class="tableColumn">Final<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Checkpoint<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn">Winner<br />Countries</th>
            <th class="tableColumn last">Total<br />Contests</th>
        </tr>
    </thead>
    <tbody>
        <%-- project aggregation report --%>
        <c:forEach items="${viewData.projectAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="projectAggregationReport ${rowStyle}">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmissions}</td>
            <td>${item.checkpointSubmissions}</td>
            <td>${item.finalSubmissions}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.checkpointWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
            <td>${item.totalContests}</td>
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
                                    <a href="javascript:getParticipationReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>



<div class="resultTableContainer billingAggregationReport hide">
<table class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="16%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
    </colgroup>
    <thead>
        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn first">Billing Account</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Checkpoint<br />Submissions</th>
            <th class="tableColumn">Final<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Checkpoint<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn">Winner<br />Countries</th>
            <th class="tableColumn last">Total<br />Contests</th>
        </tr>
    </thead>
    <tbody>
        <%-- billing account aggregation report --%>
        <c:forEach items="${viewData.billingAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="billingAggregationReport ${rowStyle}">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmissions}</td>
            <td>${item.checkpointSubmissions}</td>
            <td>${item.finalSubmissions}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.checkpointWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
            <td>${item.totalContests}</td>
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
                                    <a href="javascript:getParticipationReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>

<div class="resultTableContainer typeAggregationReport hide">
<table class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="16%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
    </colgroup>
    <thead>
        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn first">Contest Type</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Checkpoint<br />Submissions</th>
            <th class="tableColumn">Final<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Checkpoint<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn last">Winner<br />Countries</th>
            <th class="tableColumn">Total<br />Contests</th>
        </tr>
    </thead>
    <tbody>
        <%-- contest type aggregation report --%>
        <c:forEach items="${viewData.contestTypeAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="typeAggregationReport ${rowStyle}">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmissions}</td>
            <td>${item.checkpointSubmissions}</td>
            <td>${item.finalSubmissions}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.checkpointWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
            <td>${item.totalContests}</td>
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
                                    <a href="javascript:getParticipationReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>


<div class="resultTableContainer statusAggregationReport hide">
<table class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="16%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
    </colgroup>
    <thead>
        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn first">Contest Status</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Checkpoint<br />Submissions</th>
            <th class="tableColumn">Final<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Checkpoint<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn">Winner<br />Countries</th>
            <th class="tableColumn last">Total<br />Contests</th>
        </tr>
    </thead>
    <tbody>
        <%-- status aggregation cost report --%>
        <c:forEach items="${viewData.statusAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="statusAggregationReport ${rowStyle}">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmissions}</td>
            <td>${item.checkpointSubmissions}</td>
            <td>${item.finalSubmissions}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.checkpointWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
            <td>${item.totalContests}</td>
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
                                    <a href="javascript:getParticipationReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>




<div class="resultTableContainer contestAggregationReport hide">
<table class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="16%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
    </colgroup>
    <thead>
        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn first">Contest Name</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Checkpoint<br />Submissions</th>
            <th class="tableColumn">Final<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Checkpoint<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn">Winner<br />Countries</th>
            <th class="tableColumn">Total<br />Contests</th>
        </tr>
    </thead>
    <tbody>
        <%-- contest aggregation report --%>
        <c:forEach items="${viewData.contestAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="contestAggregationReport ${rowStyle}">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmissions}</td>
            <td>${item.checkpointSubmissions}</td>
            <td>${item.finalSubmissions}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.checkpointWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
            <td>&nbsp;</td>
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
                                    <a href="javascript:getParticipationReportAsExcel();" class="exportExcel">Export to
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
