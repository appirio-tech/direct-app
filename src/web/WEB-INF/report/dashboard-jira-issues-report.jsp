<%--
  - Author: TCSASSEMBLER, Ghost_141, csy2012
  - Version: 1.0 (Module Assembly - JIRA issues loading update and report creation)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for jira issues report including form and report data.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - Update layout to fix a layout issue.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0) changes:
  - Update layout to fix a date layout issue.  
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/dashboard-jira-issues-report.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <link rel="stylesheet" href="/css/direct/dashboard-enterprise.css" media="all" type="text/css"/>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="jiraReportContent">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Jira Issues Report"/>
</jsp:include>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="jiraIssuesReports">

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
    <s:form method="get" action="dashboardGetJiraIssuesReport" namespace="/"
            id="dashboardJiraIssuesReportForm">
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
                                         id="startDateJiraIssuesReport"
                                         cssClass="text date-pick fLeft dp-applied"/>
                            <div class="clearFix"></div>
                        </div><!-- end .filterRow -->

                        <div class="filterRow">
                            <label for="endDate">End:</label>
                            <s:textfield name="formData.endDate" readonly="true"
                                         id="endDateJiraIssuesReport" cssClass="text date-pick fLeft dp-applied"/>
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
                <a class="button6 applyButton" href="javascript:;" id="jiraIssuesReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
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
    <div id="jiraIssuesReportsSection" style="width:100%;display: table">
        <div class="resultTableContainer">
            <table id="jiraIssueDetails" class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                    <th class="tableTitle" colspan="17">
                        <a href="javascript:void(0)" class="expand">&nbsp;</a>
                        <span>Jira Issues Report</span>
                    </th>
                </tr>

                <tr class="projectAggregationCostReport scData subTheadRow">
                    <th class="tableColumn">Customer</th>
                    <th class="tableColumn">Billing</th>
                    <th class="tableColumn">Project</th>                
                    <th class="tableColumn">Bug<br/>ID</th>
					<th class="tableColumn">Title</th>
                    <th class="tableColumn">Launch<br/>Date</th>
                    <th class="tableColumn">Description</th>
                    <th class="tableColumn">Amount</th>
					<th class="tableColumn">Contest<br />Name</th>
                    <th class="tableColumn">Contest<br/>ID</th>
                    <th class="tableColumn">Status</th>
                    <th class="tableColumn">Reporter</th>
                    <th class="tableColumn">Assignee</th>
                    <th class="tableColumn">TCO<br />Points</th>
                    <th class="tableColumn">Resolution<br />Date</th>
                    <th class="tableColumn">Registrants</th>
                    <th class="tableColumn">Winner</th>
                </tr>
                </thead>
                <tbody>
                <s:iterator value="viewData.entries" status="statusValue">

                    <tr class="projectAggregationReport ${rowStyle}">
                        <td>

                            <s:property value="customer"/>
                        </td>
                        <td><s:property value="billingAccount"/></td>
                        <td>
                            <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="projectId"/></s:url>">
                                <s:property value="projectName"/>
                            </a>
                        </td>
                       
                        <td>
                            <a href="https://apps.topcoder.com/bugs/browse/${ticketId}">${ticketId}</a>
                        </td>
						 <td>
                            <a href="https://apps.topcoder.com/bugs/browse/${ticketId}">${ticketTitle}</a>
                        </td>
                        <td  class="singleLineCol">
                            <s:date name="launchDate" format="yyyy-MM-dd" />
                        </td>
                       
                        <td>
                            <a href="javascript:;" title="<s:property value='ticketDescription'/>">View</a>
                        </td>
                        <td>
                            <fmt:formatNumber value="${prize}" pattern="$###,##0.00"/>
                        </td>
						 <td>
                            <a class="longWordsBreak"
                               href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="contestId"/></s:url>">
                                <s:property value="contestName"/></a>
                        </td>
                        <td>
                            <a class="longWordsBreak"
                               href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="contestId"/></s:url>">
                                <s:property value="contestId"/></a>
                        </td>
                        <td>
                            <s:property value="status"/>
                        </td>
                        <td>
                            <s:property value="reporter"/>
                        </td>
                        <td>
                            <s:property value="assignee"/>
                        </td>
                        <td>
                            <s:property value="tcoPoints"/>
                        </td>
                        <td>
                            <s:if test="resolutionDate != null">
                                <s:date name="resolutionDate" format="yyyy-MM-dd" />
                            </s:if>
                        </td>
                        <td>
                            <s:property value="votesNumber"/>
                        </td>
                        <td><s:property value="winner"/></td>

                    </tr>
                </s:iterator>
                </tbody>
            </table>

            <div class="container2Left">
                <div class="container2Right">
                    <div class="container2Bottom">
                        <div>
                            <div>

                                <div class="panel tableControlPanel">
                                    <div class="exportControl">
                                        <a href="javascript:getJiraIssuesReportAsExcel();" class="exportExcel">Export to
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
