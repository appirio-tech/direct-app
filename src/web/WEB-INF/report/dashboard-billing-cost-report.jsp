<%--
  - Author: Blues
  - Version: 1.0 ((TopCoder Cockpit - Cost Report Assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for cost report including form and report data.
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
    <script type="text/javascript" src="/scripts/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-billing-cost-report.js"></script>
    <link rel="stylesheet" href="/css/dashboard-enterprise.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/datepicker.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/jquery.multiSelect.css" media="all" type="text/css"/>

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
                    <s:select list="viewData.projectsLookupMap" name="formData.projectIds"
                              id="formData.projectIds"/>
                </div>

                <div class="firstRow">
                    <div class="datePicker">
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
                            <strong>Status</strong><br/>
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
                            <s:select list="viewData.clientAccounts" id="formData.customerIds"
                                      name="formData.customerIds" size="1"/>
                        </div>

                        <div class="columns" id="clientBillingProjectsFilter">
                            <strong>Billing Account</strong><br/>
                            <s:select list="viewData.clientBillingProjects"
                                      id="formData.billingAccountIds"
                                      name="formData.billingAccountIds" size="1"/>
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
<div id="billingCostReportSection">

    <%-- Cost report Details --%>
<table id="billingCostDetails" class="billingCostReport paginatedDataTable" cellpadding="0"
       cellspacing="0">
    <thead>
    <tr>
        <th class="tableTitle" colspan="15">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Billing Cost Details</span>
        </th>
    </tr>
    <tr>
        <th class="tableColumn">&nbsp;Payment Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Customer&nbsp;</th>
        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
        <th class="tableColumn">&nbsp;Project&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest ID&nbsp;</th>
        <th class="tableColumn">&nbsp;Category&nbsp;</th>
        <th class="tableColumn">&nbsp;Status&nbsp;</th>
        <th class="tableColumn">&nbsp;Launch Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Completion Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Actual Total Member Cost&nbsp;</th>
        <th class="tableColumn">&nbsp;Payment Type&nbsp;</th>
        <th class="tableColumn">&nbsp;Amount&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${viewData.entries}" var="item" varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="${rowStyle} pipelineDetailsRow">
            <td>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.paymentDate}"/>
            </td>
            <td>
                <c:out value="${item.client.name}"/>
            </td>
            <td>
                <c:out value="${item.billing.name}"/>
            </td>
            <td>
                <c:out value="${item.project.name}"/>
            </td>
            <td>
                <c:out value="${item.contest.name}"/>
            </td>
            <td>
                <c:out value="${item.contest.id}"/>
            </td>
            <td>
                <c:out value="${item.contestType.name}"/>
            </td>
            <td>
                <c:out value="${item.status}"/>
            </td>
            <td>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.launchDate}"/>
            </td>
            <td>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.completionDate}"/>
            </td>
             <td>

                 <c:set var="status" value="${fn:trim(item.status)}"/>
                <%-- only display the actual cost when the contest is finished --%>
                <c:if test="${status == 'Finished'}">
                    <fmt:formatNumber value="${item.actualTotalMemberCost}" pattern="$###,##0.00"/>
                </c:if>
            </td>
             <td>
                <c:out value="${item.paymentType}"/>
            </td>
            <td>
                <fmt:formatNumber value="${item.paymentAmount}" pattern="$###,##0.00"/>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="panel">
    <!-- this area contains the print, export to excel, export to pdf links -->
    <a href="javascript:getBillingCostReportAsExcel();"
       class="exportExcel">Export to <strong>Excel</strong></a>
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
