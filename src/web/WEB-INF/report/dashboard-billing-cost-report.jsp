<%--
  - Author: Blues, GreatKevin
  - Version: 1.7
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
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
  - - Add the search bar for billing cost report table.
  -
  - Version 1.5 (Release Assembly - TopCoder Cockpit Reports Filter Panels Revamp) changes:
  - - Update the filter panel of billing cost report to the new one.
  -
  - Version 1.6 (TC Accounting Tracking Invoiced Payments Part 2) changes:
  - - Added invoice number, invoice date and bug description to the report detail table.
  -
  - Version 1.7 (TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) changes:
  - - Added Group by and group values into the filter panel.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/filter-panel.css?v=215476" media="all" type="text/css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/filter-panel-ie7.css?v=210396"/>
    <![endif]-->
    <script type="text/javascript" src="/scripts/tableSearchBar.js?v=210396"></script>
    <script type="text/javascript" src="/scripts/jquery.multiselect.js?v=196003"></script>
    <script type="text/javascript" src="/scripts/dashboard-billing-cost-report.js?v=214400"></script>
    <link rel="stylesheet" href="/css/dashboard-enterprise.css?v=210282" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/jquery.multiSelect.css?v=196003" media="all" type="text/css"/>

</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="billingCostReportContent">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Invoice History"/>
</jsp:include>

<%-- cost report form --%>

<div class="pipelineFilter" id="billingCostReportsPage">

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
    <s:form method="get" action="dashboardGetBillingCostReport" namespace="/"
               id="dashboardBillingCostReportForm">
	<div class="filterContainer">

		<!-- .leftFilterContent -->
		<div class="leftFilterContent">
			<input type="hidden" name="formData.excel" value="false" id="formDataExcel"/>

			<div id="customerFilter">

				<div class="filterRow firstFilterRow">
					<div class="filterRowLeft">
						<label for="formData.customerId">Customer Name:</label>
					</div>
                    <s:select list="viewData.clientAccounts" id="formData.customerId"
                              name="formData.customerId" size="1"/>

					<div class="clearFix"></div>
				</div><!-- end .filterRow -->

				<div class="filterRow">
					<div class="filterRowLeft">
						<label for="formData.billingAccountIds">Billing Account:</label>
                        <img src="/images/dots-white.gif" class="indicator" alt/>
					</div>
                    <s:select list="viewData.clientBillingProjects"
                              id="formData.billingAccountId"
                              name="formData.billingAccountId" size="1"/>
					<div class="clearFix"></div>

				</div><!-- end .filterRow -->

			</div><!-- end .customerFilter -->

			<div id="projectFilter">

				<div class="filterRow firstFilterRow">
					<div class="filterRowLeft">
						<label>Project Name:</label>
                        <img src="/images/dots-white.gif" class="indicator" alt/>
					</div>
                    <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                              id="formData.projectId"/>
					<div class="clearFix"></div>
				</div><!-- end .filterRow -->

			</div><!-- end .projectfilter -->

			<div id="datefilter">

				<div class="filterRow firstFilterRow">
					<label for="startDate" class="fLeft">Start:</label>
                    <s:textfield name="formData.startDate" readonly="true"
                                 id="startDateCostReport"
                                 cssClass="text fLeft date-pick"/>
					<div class="clearFix"></div>
				</div><!-- end .filterRow -->

				<div class="filterRow">
					<label for="endDate" class="fLeft">End:</label>
                    <s:textfield name="formData.endDate" readonly="true"
                                 id="endDateCostReport" cssClass="text fLeft date-pick"/>
					<div class="clearFix"></div>
				</div><!-- end .filterRow -->

			</div><!-- end .datefilter -->

			<div id="contestfilter">

				<div class="filterRow">
					<div class="filterRowLeft" id="contestIDFilter">
						<label>Contest ID:</label>
					</div>
                    <s:textfield id="formData.contestId" name="formData.contestId"  cssClass="text"/>
					<div class="clearFix"></div>
				</div><!-- end .filterRow -->

			</div><!-- end .contestfilter -->
            <div id="invoiceFilter">

                <div class="filterRow">
                    <div class="filterRowLeft" id="invoiceNumberFilter">
                        <label>Invoice #:</label>
                    </div>
                    <s:textfield id="formData.invoiceNumber" name="formData.invoiceNumber"  cssClass="text"/>

                    <div class="clearFix"></div>
                </div>
                <!-- end .filterRow -->

            </div>
            <!-- end .invoiceFilter -->
		</div>
		<!-- end .leftFilterContent -->

		<!-- .rightFilterContent -->
		<div class="rightFilterContent">
              <div class="col">
                    <div class="multiSelectArea statusMultiSelect">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Status:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow multiOptionRowChecked">
                                    <input type="checkbox" checked="checked" id="StatusSelectAll"
                                           name="StatusSelectAll" class="optionAll"/>
                                    <label for="StatusSelectAll" title="Select All">Select All</label>
                                </div>

                                <s:iterator value='viewData.contestStatus' status='s'>
                                    <s:set name='needCheck' value='false'/>
                                    <s:iterator value='formData.statusIds' var="statusId">
                                        <s:if test="#statusId == key.longValue()"><s:set name='needCheck' value='true'/></s:if>
                                    </s:iterator>
                                    <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                        <input type="checkbox" <s:if test='#needCheck'>checked="checked"</s:if> id="StatusSelectCheckBox${s.index}" class="optionItem" name="formData.statusIds" value="${key}"/>
                                        <label for="StatusSelectCheckBox${s.index}" title="${value}">${value}</label>
                                    </div>
                                </s:iterator>

                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->

                  <div id="groupFilter">
                      <div class="filterRow firstFilterRow">
                          <div class="filterRowLeft">
                              <label for="formDatagroup">Project Filters:</label>
                              <img src="/images/dots-white.gif" class="indicator" alt/>
                          </div>
                          <s:select id="formDatagroup"
                                    list="viewData.groupKeys"
                                    name="formData.groupId"/>
                      </div>
                      <div class="multiSelectArea" id="formDatagroupValue">
                          <div class="multiSelectAreaInner">
                              <label class="multiSelectAreaTitle">Project Filter Values:<img src="/images/dots-white.gif"
                                                                                    class="indicator" alt/>
                                  <span class="reportWarningMessage hide">No Value Available</span>
                              </label>

                              <div class="multiSelectBox">
                                  <div class="multiOptionRow multiOptionRowChecked hide">
                                      <input type="checkbox" class="optionAll" name=""
                                             id="groupValuesSelectAll" checked="checked">
                                      <label title="Select All" for="groupValuesSelectAll">Select
                                          All</label>
                                  </div>
                                  <s:iterator value='viewData.groupValues' status='c' var="groupValue">
                                      <s:set name='needCheck' value='false'/>
                                      <s:iterator value='formData.groupValues' var="group">
                                          <s:if test="#group == #groupValue"><s:set name='needCheck'
                                                                                    value='true'/></s:if>
                                      </s:iterator>
                                      <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                          <input type="checkbox"
                                                 <s:if test='#needCheck'>checked="checked"</s:if>
                                                 id="groupValuesCheckBox${c.index}" class="optionItem"
                                                 name="formData.groupValues" value="${groupValue}"/>
                                          <label for="groupValuesCheckBox${c.index}"
                                                 title="${groupValue}">${groupValue}</label>
                                      </div>
                                  </s:iterator>
                              </div>
                          </div>
                      </div>
                                          <!-- end .multiSelectArea -->
                    </div>
               </div>

            <div class="multiSelectArea">
                <div class="multiSelectAreaInner">
                    <label class="multiSelectAreaTitle">Contest Type:</label>

                    <div class="multiSelectBox">
                        <div class="multiOptionRow multiOptionRowChecked">
                            <input type="checkbox" checked="checked" id="ContestTypeSelectAll"
                                   name="ContestTypeSelectAll" class="optionAll"/>
                            <label for="ContestTypeSelectAll" title="Select All">Select All</label>
                        </div>

                        <s:iterator value='viewData.projectCategories' status='c'>
                            <s:set name='needCheck' value='false'/>
                            <s:iterator value='formData.projectCategoryIds' var="cateogryId">
                                <s:if test="#cateogryId == key.longValue()"><s:set name='needCheck'
                                                                                   value='true'/></s:if>
                            </s:iterator>
                            <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                <input type="checkbox"
                                       <s:if test='#needCheck'>checked="checked"</s:if>
                                       id="ProjectTypeCheckBox${c.index}" class="optionItem"
                                       name="formData.projectCategoryIds" value="${key}"/>
                                <label for="ProjectTypeCheckBox${c.index}" title="${value}">${value}</label>
                            </div>
                        </s:iterator>
                    </div>
                </div>
            </div>
			<!-- end .multiSelectArea -->

			<div class="multiSelectArea">
				<div class="multiSelectAreaInner">
					<label class="multiSelectAreaTitle">Payment Type:</label>

                    <div class="multiSelectBox">
                        <div class="multiOptionRow multiOptionRowChecked">
                            <input type="checkbox" checked="checked" id="PaymentTypeSelectAll"
                                   name="PaymentTypeSelectAll" class="optionAll"/>
                            <label for="PaymentTypeSelectAll" title="Select All">Select All</label>
                        </div>

                        <s:iterator value='viewData.paymentTypes' status='p'>
                            <s:set name='needCheck' value='false'/>
                            <s:iterator value='formData.paymentTypeIds' var="paymentTypeId">
                                <s:if test="#paymentTypeId == key.longValue()"><s:set name='needCheck'
                                                                                   value='true'/></s:if>
                            </s:iterator>
                            <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                <input type="checkbox"
                                       <s:if test='#needCheck'>checked="checked"</s:if>
                                       id="PaymentTypeSelectCheckBox${p.index}" class="optionItem"
                                       name="formData.paymentTypeIds" value="${key}"/>
                                <label for="PaymentTypeSelectCheckBox${p.index}" title="${value}">${value}</label>
                            </div>
                        </s:iterator>
                    </div>
			</div>
			<!-- end .multiSelectArea -->

			<div class="clearFix"></div>

			<div class="applyButtonBox">
				<a class="button6 applyButton" href="javascript:" id="costReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
			</div>
			<!-- end .applyButtonBox -->

		</div>
		<!-- end .rightFilterContent -->
		<div class="clearFix"></div>

	</div>

          <s:if test="hasActionErrors()">
                                <div id="validationErrors">
                                    <s:actionerror/>
                                </div>
           </s:if>
    </div>
   </s:form>
	<!-- End .filterContainer -->

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
<input type="hidden" id="currentDate" value="<s:date name="currentDate" format="MM/dd/yyyy" />" />

<div id="billingCostReportSection" class="resultTableContainer">

    <%-- Cost report Details --%>
<table id="billingCostDetails" class="billingCostReport resultTable paginatedDataTable" cellpadding="0"
       cellspacing="0">
    <thead>
    <tr>
        <th class="tableTitle" colspan="
        <c:if test="${viewData.canProcessInvoices}">20</c:if>
        <c:if test="${!viewData.canProcessInvoices}">16</c:if>
        
        ">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Invoice History Details</span>
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
        <th class="tableColumn">Filter Value</th>
        <th class="tableColumn">&nbsp;Contest&nbsp;</th>
        <th class="tableColumn">&nbsp;Contest ID&nbsp;</th>
        <th class="tableColumn">&nbsp;Reference ID&nbsp;</th>
        <th class="tableColumn">&nbsp;Category&nbsp;</th>
        <th class="tableColumn">&nbsp;Status&nbsp;</th>
        <th class="tableColumn">&nbsp;Launch Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Completion Date&nbsp;</th>
        
        <c:if test="${viewData.canProcessInvoices}">
        <th class="tableColumn">&nbsp;Actual Total Member Cost&nbsp;</th>
        </c:if>
        
        <th class="tableColumn">&nbsp;Payment Type&nbsp;</th>
        <th class="tableColumn">&nbsp;Amount&nbsp;</th>
        
        <th class="tableColumn">&nbsp;Invoice Number&nbsp;</th>
        <th class="tableColumn">&nbsp;Invoice Date&nbsp;</th>
        
        <c:if test="${viewData.canProcessInvoices}">
        <th class="tableColumn">&nbsp;Invoice Amount&nbsp;</th>
        <th class="tableColumn">&nbsp;Payment ID&nbsp;</th>
        <th class="tableColumn" style="width:100px">
            <input type="checkbox" id="checkAllInvoice">
            <label for="checkAllInvoice">All</label>
            <input type="button" value="Invoice" class="processBtn">
        </th>
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
                <s:property value="projectFilterValue"/>
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
                <s:if test="referenceId != null && referenceId != ''">
                    <div class="wrapContent">
                    <a  target="_blank" href="https://apps.topcoder.com/bugs/browse/${referenceId}">
                        ${paymentDesc}
                    </a>
                    </div>
                </s:if>
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
            
            <c:if test="${viewData.canProcessInvoices}">
                <td class="invoiceAmount">
                    ${actualTotalMemberCost}
                </td>            
            </c:if>
            
            <td>
                 <s:property value="paymentType"/>
            </td>
            <td class="amount">
                <s:set var="paymentA" value="paymentAmount" scope="page"/>
                <fmt:formatNumber value="${paymentA}" pattern="$###,##0.00"/>
            </td>
            
            <td class="invoiceNumber">
                <c:if test="${viewData.canProcessInvoices and not empty invoiceNumber}">
                    <a href="#" class="updInvoiceDate">${invoiceNumber}</a>
                </c:if>
                <c:if test="${!viewData.canProcessInvoices and not empty invoiceNumber}">
                    ${invoiceNumber}
                </c:if>
            </td>
            <td class="invoiceDate">
                <c:if test="${viewData.canProcessInvoices and not empty invoiceDate}">
                    <a href="#" class="updInvoiceDate"><s:date name="invoiceDate" format="yyyy-MM-dd" /></a>
                </c:if>
                <c:if test="${!viewData.canProcessInvoices and not empty invoiceDate}">
                    <s:date name="invoiceDate" format="yyyy-MM-dd" />
                </c:if>
            </td>
            <c:if test="${viewData.canProcessInvoices}">
            
            <td class="invoiceAmount">
                <s:set var="invoiceA" value="invoiceAmount" scope="page"/>
                <c:if test="${not empty invoiceNumber}">
                    <a href="#" class="updInvoiceAmount"><fmt:formatNumber value="${invoiceA}" pattern="$###,##0.00"/></a>
                </c:if>
                <c:if test="${empty invoiceNumber}">
                <fmt:formatNumber value="${invoiceA}" pattern="$###,##0.00"/>
                </c:if>
            </td>
            <td>
                <s:property value="paymentId"/>
            </td>            
            <td>
                <input type="checkbox" name="invoiceRecordProcessed" paymentid="${paymentId}" contestid="${contest.id}" invoicetype="${paymentType}" invoiceamount="${paymentAmount}" <c:if test="${processed}">checked="checked" disabled="disabled"</c:if> invoiceid="${invoiceId}" invoicerecordid="${invoiceRecordId}" invoicenumber="${invoiceNumber}" invoicedate="<s:date name="invoiceDate" format="MM/dd/yyyy" />"/>
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
