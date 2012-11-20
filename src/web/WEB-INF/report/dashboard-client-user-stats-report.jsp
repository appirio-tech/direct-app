<%--
  - Author: leo_lol
  - Version: 1.0 (Module Assembly - TC Client Users Stats Report Generation v1.0)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for client user stats report including form and report data.
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
    <script type="text/javascript" src="/scripts/dashboard-client-user-stats-report.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <link rel="stylesheet" href="/css/direct/dashboard-enterprise.css" media="all" type="text/css"/>
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

<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <strong>Reports</strong>
</div>

<div class="areaHeader">
    <h2 class="title">Client User Stats Report</h2>
</div>

<!-- .pipelineFilter -->
<div class="pipelineFilter" id="clientUserStatsReports">

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
    <s:form method="get" action="clientUserStatsReport" namespace="/"
            id="clientUserStatsReportForm">
        <s:hidden name="excel" id="formDataExcel" value="false"/>
        <div class="filterContainer">
            <div class="filterLeftTwoParts">
                <div class="filterColumnPart">
                    <!-- .filterCustomerName -->
                    <div class="filterCustomerName">
                        <label for="formData.customerId">Client Name:</label>
                        <select name="currentClientName" id="clientIdSelect">
                        	<option>All</option>
                        	<c:forEach items="${clientNames}" var="item">
								<option><c:out value="${item}"/></option>
							</c:forEach> 
                        </select>
                    </div>
                    <!-- end .filterCustomerName -->
                </div>

                <div class="filterColumnPart">
                    <!-- .filterBillingAccount -->
                    <div class="filterBillingAccount">
                        <label for="formData.billingAccountId">Year/Month:</label>
                        <select name="currentYearMonth" id="yearMonthSelect">
                        	<option>All</option>
                        	<c:forEach items="${yearMonths}" var="item">
								<option class="yearMonthItem">${item}</option>
							</c:forEach> 
                        </select>
                    </div>
                    <!-- end .filterBillingAccount -->
                </div>
                <div class="clearFix"></div>
            </div>

            <div class="filterColumnThird">
            </div>
        </div>
    </s:form>
    <!-- End .filterContainer -->

</div>
<!-- End .pipelineFilter -->

<s:if test="not hasActionErrors()">
    <div id="clientUserStatsReportsSection" style="width:100%;display: table">
        <div class="resultTableContainer">
            <table id="clientUserStatsDetails" class="pipelineStats paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                    <th class="tableTitle" colspan="17">
                        <a href="javascript:void(0)" class="expand">&nbsp;</a>
                        <span>Client User Stats Report</span>
                    </th>
                </tr>

                <tr class="projectAggregationCostReport scData subTheadRow">
                    <th class="tableColumn">Client Name</th>
                    <th class="tableColumn">Year/Month</th>
                    <th class="tableColumn"># of users</th>                
                </tr>
                </thead>
                <tbody>
                <s:iterator value="clientUserStats" status="statusValue">
                    <tr class="projectAggregationReport">
                        <td><s:property value="clientName"/></td>
                        <td><s:property value="yearMonth"/></td>
                        <td><s:property value="userCount"/></td>
                    </tr>
                </s:iterator>
                </tbody>
            </table>

            <div class="container2Left">
                <div class="container2Right">
                    <div class="container2Bottom">
                        <div class="container2BottomLeft">
                            <div class="container2BottomRight">
                                <div class="panel tableControlPanel">
                                    <div class="exportControl">
                                        <a href="javascript:getClientUserStatsReportAsExcel();" class="exportExcel">Export to
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
