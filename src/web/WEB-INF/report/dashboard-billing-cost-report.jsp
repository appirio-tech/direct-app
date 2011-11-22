<%--
  - Author: Blues, GreatKevin
  - Version: 1.4
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for cost report including form and report data.
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  - Version 1.2 (TC Cockpit Permission and Report Update One) change log:
  - - Change parameter name from projectIds to projectId.
  - - Change parameter name from billingAccountIds to billingAccount.
  - - Change parameter name from customerIds to customerId.
  - Version 1.3 (TC Accounting Tracking Invoiced Payments) changes: Display the processed columns in "Billing Cost Detail" table.
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
  - - Add the search bar for billing cost report table.--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/filter-panel.css" media="all" type="text/css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/filter-panel-ie7.css"/>
    <![endif]-->
    <script type="text/javascript" src="/scripts/tableSearchBar.js"></script>
    <script type="text/javascript" src="/scripts/jquery.multiselect.js?v=196003"></script>
    <script type="text/javascript" src="/scripts/dashboard-billing-cost-report.js?v=210122"></script>
    <link rel="stylesheet" href="/css/dashboard-enterprise.css?v=210135" media="all" type="text/css"/>
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
    <jsp:param name="reportTitle" value="Billing Cost Report"/>
</jsp:include>

<%-- cost report form --%>
<div class="pipelineSearch">
    <s:form method="get" action="dashboardGetBillingCostReport" namespace="/"
            id="dashboardBillingCostReportForm">
        <s:hidden name="formData.excel" id="formDataExcel" value="false"/>
        <div id="costReportFilters" class="filterLinkArea">
          <div id="costReportFilterShowControl">
            <a class="fiterButton" href="javascript:">Filters</a>
          </div>

            <div class="filterArea">

                <div class="filterProject">
                    <span class="label">Project Name </span>
                    <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
                </div>

                <div class="firstRow">
                    <div class="datePicker">
                        <div class="columns contestType">
                            <strong>Payment Date</strong><br/>
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

                    <div class="timeDimension">
                        <div class="columns contestType">
                            <strong>Contest ID</strong><br/>
                            <s:textfield id="formData.contestId" name="formData.contestId"/>

                        </div>
                    </div>
                </div>
                <div class="spacer"></div>
                <div class="secondRow">
                    <div class="filterContest">
                        <div class="columns paymentType">
                            <strong>Payment Type</strong><br/>
                            <s:select list="viewData.paymentTypes" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.paymentTypeIds"
                                      name="formData.paymentTypeIds" size="3"/>
                        </div>
                        <div class="columns contestType">
                            <strong>Contest Type</strong><br/>
                            <s:select list="viewData.projectCategories" multiple="true"
                                      cssClass="multiselect"
                                      id="formData.projectCategoryIds"
                                      name="formData.projectCategoryIds" size="5"/>
                        </div>

                        <div class="columns" id="customerNameFilter">
                            <strong>Customer Name</strong><br/>
                            <s:select list="viewData.clientAccounts" id="formData.customerId"
                                      name="formData.customerId" size="1"/>
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


    <div class='filterPanel searchBar' id='billingCostReportSearchBar'>
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

<%--
  Cost report area
--%>
<div id="billingCostReportSection" class="resultTableContainer">

    <%-- Cost report Details --%>
<table id="billingCostDetails" class="billingCostReport resultTable paginatedDataTable" cellpadding="0"
       cellspacing="0">
    <thead>
    <tr>
        <th class="tableTitle" colspan="<c:if test="${viewData.canProcessInvoices}">14</c:if><c:if test="${!viewData.canProcessInvoices}">13</c:if>">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Billing Cost Details</span>
            <span style="float:right;padding-top:4px; padding-right:5px">
                <s:set var="totalPayment" value="viewData.totalAmount" scope="page"/>
                Total Amount: <fmt:formatNumber value="${totalPayment}" pattern="$###,##0.00"/>
            </span>
        </th>
    </tr>
    <tr>
        <th class="tableColumn">&nbsp;Payment Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Customer&nbsp;</th>
        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
        <th class="tableColumn">&nbsp;Project&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest ID&nbsp;</th>
        <th class="tableColumn">&nbsp;Reference ID&nbsp;</th>
        <th class="tableColumn">&nbsp;Category&nbsp;</th>
        <th class="tableColumn">&nbsp;Status&nbsp;</th>
        <th class="tableColumn">&nbsp;Launch Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Completion Date&nbsp;</th>
   <!--     <th class="tableColumn">&nbsp;Actual Total Member Cost&nbsp;</th> -->
        <th class="tableColumn">&nbsp;Payment Type&nbsp;</th>
        <th class="tableColumn">&nbsp;Amount&nbsp;</th>
        <c:if test="${viewData.canProcessInvoices}">
        <th class="tableColumn">&nbsp;Processed?&nbsp;<br/><input type="checkbox" id="invoiceRecordSelectAll"/></th>
        </c:if>
    </tr>
    </thead>
    <tbody>

    <s:iterator value="viewData.entries" status="statusValue">
        <tr class="$pipelineDetailsRow">
            <td>
                <s:date name="paymentDate" format="yyyy-MM-dd" />
            </td>
            <td>
                <s:property value="client.name"/>
            </td>
            <td>
                <s:property value="billing.name"/>
            </td>
            <td>
                <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="project.id"/></s:url>">

                   <s:property value="project.name"/>

                </a>

            </td>
            <td>

                <s:if test="studio">
                    <a class="longWordsBreak"
                       href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="contest.id"/></s:url>">
                        <s:property value="contest.name"/></a>
                </s:if>
                <s:else>
                    <a class="longWordsBreak"
                       href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="contest.id"/></s:url>">
                         <s:property value="contest.name"/></a>
                </s:else>


            </td>
            <td>
                 <s:property value="contest.id"/>
            </td>
            <td>
                 <s:property value="referenceId"/>
            </td>
            <td>
                 <s:property value="contestType.name"/>
            </td>
            <td>
                 <s:property value="status"/>
            </td>
            <td>
                 <s:date name="launchDate" format="yyyy-MM-dd" />
            </td>
            <td>
                 <s:date name="completionDate" format="yyyy-MM-dd" />
            </td>
            <td>
                 <s:property value="paymentType"/>
            </td>
            <td>
                <s:set var="paymentA" value="paymentAmount" scope="page"/>
                <fmt:formatNumber value="${paymentA}" pattern="$###,##0.00"/>
            </td>
            <c:if test="${viewData.canProcessInvoices}">
            <td>
                <input type="checkbox" name="invoiceRecordProcessed" paymentid="${paymentId}" contestid="${contest.id}" invoicetype="${paymentType}" <c:if test="${processed}">checked="checked"</c:if>/>
            </td>
            </c:if>

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
                                    <a href="javascript:getBillingCostReportAsExcel();" class="exportExcel">Export to
                                        <strong>Excel</strong></a>
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

</body>
<!-- End #page -->

</html>
