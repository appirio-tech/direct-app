<%--
  - Author: TCSASSEMBER
  - Version: 1.1 (TC Cockpit Participation Metrics Report Part One Assembly 1 )
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (TC Cockpit Permission and Report Update One) change log:
  - - Add logics to display Milestone Winners, Final Winners, Total Unique Winners in Aggregation Participation Metrics Report section.
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
<div id="content">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Participation Metrics Report"/>
</jsp:include>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="participationMetricsReports">

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
                        <s:if test="hasActionErrors()">
                            <div id="validationErrors">
                                <s:actionerror/>
                            </div>
                        </s:if>
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
            <div class="filterProject2">
                <label>Project Name:</label>
                <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
            </div>
            <!-- end .filterProject -->

            <div class="multiSelectArea">
                <div class="multiSelectAreaInner">
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

        <div class="applyButtonBox">
            <a class="button6 applyButton" href="javascript:;" id="participationReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
            <div class="clearFix"></div>
        </div>
        <!-- end .applyButtonBox -->

    </div>
    </s:form>
    <!-- End .filterContainer -->

</div>
<!-- End .pipelineFilter -->

<s:if test="not viewData.showJustForm and not hasActionErrors()">
<!-- participationMetricsReportsSection -->
<div id="participationMetricsReportsSection">

<table id="participationMetricsReportBasicMetrics" class="pipelineStats" cellpadding="0" cellspacing="0">
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

<table id="participationMetricsReportAggregationArea" class="pipelineStats" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="21%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="12%" />
    </colgroup>
    <thead>
        <tr>
            <th class="tableTitle" colspan="8">
                <a href="javascript:void(0)" class="expand">&nbsp;</a>
                <span>Aggregation Participation Metrics Report</span>
            </th>
            <th class="tableTitle viewType" colspan="4">
                <div>
                <label for="aggregationParticipationReportType">View By:</label>
                <select id="aggregationParticipationReportType">
                    <option value="project" selected="selected">Project</option>
                    <option value="billing">Billing Account</option>
                    <option value="contestType">Contest Type</option>
                    <option value="status">Contest Status</option>
                </select>
                </div>
            </th>
        </tr>

        <tr class="projectAggregationCostReport scData subTheadRow">
            <th class="tableColumn">Project</th>
            <th class="tableColumn">Total<br />Registrations</th>
            <th class="tableColumn">Unique<br />Registrants</th>
            <th class="tableColumn">Registrant<br />Countries</th>
            <th class="tableColumn">Total<br />Submissions</th>
            <th class="tableColumn">Unique<br />Submitters</th>
            <th class="tableColumn">Submitter<br />Countries</th>
            <th class="tableColumn">Milestone<br />Wins</th>
            <th class="tableColumn">Final<br />Wins</th>
            <th class="tableColumn">Total<br />Wins</th>
            <th class="tableColumn">Total<br /><nobr>Unique Winners</nobr></th>
            <th class="tableColumn">Winner<br />Countries</th>
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
            <td>${item.totalSubmitters}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.milestoneWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
        </tr>
        </c:forEach>

        <%-- billing account aggregation report --%>
        <c:forEach items="${viewData.billingAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="billingAggregationReport ${rowStyle} hide">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmitters}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.milestoneWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
        </tr>
        </c:forEach>

        <%-- contest type aggregation report --%>
        <c:forEach items="${viewData.contestTypeAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="contestTypeAggregationReport ${rowStyle} hide">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmitters}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.milestoneWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
        </tr>
        </c:forEach>

        <%-- status aggregation cost report --%>
        <c:forEach items="${viewData.statusAggregation}" var="item"
               varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'even' : ''}"/>
        <tr class="statusAggregationReport ${rowStyle} hide">
            <td>${item.groupName}</td>
            <td>${item.totalRegistrants}</td>
            <td>${item.uniqueRegistrants}</td>
            <td>${item.registrantCountries}</td>
            <td>${item.totalSubmitters}</td>
            <td>${item.uniqueSubmitters}</td>
            <td>${item.submitterContries}</td>
            <td>${item.milestoneWinners}</td>
            <td>${item.finalWinners}</td>
            <td>${item.totalWinners}</td>
            <td>${item.totalUniqueWinners}</td>
            <td>${item.winnerCountries}</td>
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
