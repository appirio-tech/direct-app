<%--
  - Author: Blues, flexme, GreatKevin, Ghost_141, csy2012, duxiaoyang
  - Version: 2.4
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
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
  -
  - Version 1.5 (Release Assembly - TopCoder Cockpit Reports Filter Panels Revamp) changes:
  - - Update the final panel of cost report to the new one.
  - -
  - Version 1.6 (TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) changes:
  - - Added Group by and group values into the filter panel.
  -
  - Version 1.7 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
  - - Added new column launch date in the cost analysis report
  -
  - Version 1.8 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Remove the container2BottomLeft and container2BottomRight class in pagination part.
  -
  - Version 1.9 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0)
  - - Fixed the date, title layout issue for 'cost breakdown view' result table.
  -
  - Version 2.0 (Release Assembly - TC Cockpit Project Total Cost Fixes)
  - - Handles the display of project level cost data
  -
  - Version 2.1 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 2)
  - - Show contest fee for project level cost
  -
  - Version 2.2 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to report
  -
  - Version 2.3 (TC Direct Rebranding Assembly Copilot and Reporting related pages)
  - - Rebranding the copilot and reporting related pages.
  -
  - Version 2.4 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace name attribute for s:set with var attribute
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:reportPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/direct/filter-panel.css" media="all" type="text/css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/filter-panel-ie7.css"/>
    <![endif]-->
    <script type="text/javascript" src="/scripts/tableSearchBar.js"></script>
    <script type="text/javascript" src="/scripts/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-cost-report.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <link rel="stylesheet" href="/css/direct/dashboard-enterprise.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery.multiSelect.css" media="all" type="text/css"/>

</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="costReportContent">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent" class="newSidebarCollapse">

<jsp:include page="/WEB-INF/includes/right.jsp"/>

<div id="area1"><!-- the main area -->

<div class="area1Content">

<jsp:include page="/WEB-INF/includes/report/header.jsp">
    <jsp:param name="reportTitle" value="Cost Analysis"/>
</jsp:include>

<%-- cost report form --%>
<div class="pipelineSearch" id="costReportsPage">
    <s:form method="post" action="dashboardGetCostReport" namespace="/"
            id="dashboardCostReportForm">
        <s:hidden name="formData.excel" id="formDataExcel" value="false"/>
        <s:hidden name="formData.showBreakdown" id="formDataShowBreakdown" value="false"/>
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

        <div class="filterContainer" data-intro="This report summarizes the costs associated to your projects and competitions. Costs are aggregated at the competition level, so keep that in mind when looking at the dates. Use the filter to narrow your results." data-step="1">

        		<!-- .leftFilterContent -->
        		<div class="leftFilterContent">
        			<input type="hidden" value="1" id="pipelineDetailsPageNumber2"/>
        			<input type="hidden" name="formData.excel" value="false" id="formDataExcel"/>

        			<div id="customerFilter">

        				<div class="filterRow firstFilterRow">
        					<div class="filterRowLeft">
        						<label for="formData.customerIds">Customer Name:</label>
        					</div>
                            <s:select list="viewData.clientAccounts" id="formData.customerId"
                                                          name="formData.customerId" size="1"/>
        					<div class="clearFix"></div>
        				</div><!-- end .filterRow -->

        				<div class="filterRow" data-intro="Select a specific billing account, or view All." data-step="2">
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

        				<div class="filterRow firstFilterRow" data-intro="Select a specific project, or view All." data-step="3">
        					<div class="filterRowLeft">
        						<label>Project Name:</label>
                                <img src="/images/dots-white.gif" class="indicator" alt/>
        					</div>
                            <s:select list="viewData.projectsLookupMap" name="formData.projectId"
                                                          id="formData.projectId"/>
        					<div class="clearFix"></div>
        				</div><!-- end .filterRow -->

        			</div><!-- end .projectfilter -->

                    <div id="datefilter" data-intro="The date filter applies to the end dates of competitions.  This is NOT correlated exactly to when costs are incurred.  Start/End sets the range of competition end dates to include in your report." data-step="4">

                        <div class="filterRow firstFilterRow">
                            <label for="startDate" class="fLeft">Start:</label>
                            <s:textfield name="formData.startDate" readonly="true"
                                         id="startDateCostReport"
                                         cssClass="text fLeft date-pick"/>
                            <div class="clearFix"></div>
                        </div>
                        <!-- end .filterRow -->

                        <div class="filterRow">
                            <label for="endDate" class="fLeft">End:</label>
                            <s:textfield name="formData.endDate" readonly="true"
                                         id="endDateCostReport" cssClass="text fLeft date-pick"/>
                            <div class="clearFix"></div>
                        </div>
                        <!-- end .filterRow -->

                    </div>
                    <!-- end .datefilter -->

        		</div>
        		<!-- end .leftFilterContent -->

        		<!-- .rightFilterContent -->
        		<div class="rightFilterContent">

                    <div id="groupFilter" data-intro="If these look familiar, then you know they are based on your project meta data. To learn more about this, visit TopCoder University." data-step="5">
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
                                <label class="multiSelectAreaTitle">Project Filter Values:
                                    <img src="/images/dots-white.gif" class="indicator" style="padding-left:4px" alt/>
                                    <span class="reportWarningMessage hide">No Value Available</span></label>
                                </label>

                                <div class="multiSelectBox">
                                    <div class="multiOptionRow multiOptionRowChecked hide">
                                        <input type="checkbox" class="optionAll" id="groupValuesSelectAll" checked="checked">
                                        <label title="Select All" for="groupValuesSelectAll">Select
                                            All</label>
                                    </div>
                                    <s:iterator value='viewData.groupValues' status='c' var="groupValue">
                                        <s:set var='needCheck' value='false'/>
                                        <s:iterator value='formData.groupValues' var="group">
                                            <s:if test="#group == #groupValue"><s:set var='needCheck'
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

                    <div class="multiSelectArea" data-intro="Include or exclude different competition statuses." data-step="6">
        				<div class="multiSelectAreaInner">
        					<label class="multiSelectAreaTitle">Status:</label>
        					<div class="multiSelectBox">
                                <div class="multiOptionRow multiOptionRowChecked">
                                    <input type="checkbox" checked="checked" id="StatusSelectAll" class="optionAll"/>
                                    <label for="StatusSelectAll" title="Select All">Select All</label>
                                </div>

                                <s:iterator value='viewData.contestStatus' status='s'>
                                    <s:set var='needCheck' value='false'/>
                                    <s:iterator value='formData.statusIds' var="statusId">
                                        <s:if test="#statusId == key.longValue()"><s:set var='needCheck' value='true'/></s:if>
                                    </s:iterator>
                                    <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                        <input type="checkbox"
                                               <s:if test='#needCheck'>checked="checked"</s:if>
                                               id="StatusSelectCheckBox${s.index}" class="optionItem"
                                               name="formData.statusIds" value="${key}"/>
                                        <label for="StatusSelectCheckBox${s.index}" title="${value}">${value}</label>
                                    </div>
                                </s:iterator>
        					</div>
        				</div>
        			</div>
        			<!-- end .multiSelectArea -->

        			<div class="multiSelectArea" data-intro="We have many competition types.  If you don't want to see all of them, only select the ones you're interested in here." data-step="7">
        				<div class="multiSelectAreaInner">
        					<label class="multiSelectAreaTitle">Challenge Type:</label>
        					<div class="multiSelectBox">
                                <div class="multiOptionRow multiOptionRowChecked">
                                    <input type="checkbox" checked="checked" id="ContestTypeSelectAll" class="optionAll"/>
                                    <label for="ContestTypeSelectAll" title="Select All">Select All</label>
                                </div>

                                <s:iterator value='viewData.projectCategories' status='c'>
                                    <s:set var='needCheck' value='false'/>
                                    <s:iterator value='formData.projectCategoryIds' var="cateogryId">
                                        <s:if test="#cateogryId == key.longValue()"><s:set var='needCheck'
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
        			<div class="clearFix"></div>

        			<div class="applyButtonBox" data-intro="Click Apply to run the report." data-step="8">
        				<a class="button6 newButtonGreen applyButton" href="javascript:" id="costReportSubmit"><span class="left"><span class="right">APPLY</span></span></a>
        			</div>
        			<!-- end .applyButtonBox -->

        		</div>
        		<!-- end .rightFilterContent -->
        		<div class="clearFix"></div>
                <s:if test="hasActionErrors()">
                            <div id="validationErrors">
                                <s:actionerror/>
                            </div>
                 </s:if>
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
       cellpadding="0" cellspacing="0" data-intro="Aggegate view of your cost data.  Use the View By control on the right to pivot on different dimensions of your data." data-step="9">
    <thead>
    <tr>
        <th class="tableTitle" colspan="4">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Aggregation Cost Analysis</span>
        </th>
        <th class="tableTitle viewType">
            <label for="aggregationCostReportType">View By:</label>
            <select id="aggregationCostReportType">
                <option value="project">Project</option>
                <option value="billing">Billing Account</option>
                <option value="contestType">Challenge Type</option>
                <option value="status">Challenge Status</option>
            </select>
        </th>
    </tr>
    <tr class="projectAggregationCostReport scData">
        <th class="tableColumn">Project</th>
        <th class="tableColumn">Challenge Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="billingAggregationCostReport scData hide">
        <th class="tableColumn">Billing Account</th>
        <th class="tableColumn">Challenge Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="contestTypeAggregationCostReport scData hide">
        <th class="tableColumn">Challenge Type</th>
        <th class="tableColumn">Challenge Fees</th>
        <th class="tableColumn">Estimated Member Cost</th>
        <th class="tableColumn">Actual Member Cost</th>
        <th class="tableColumn">Total</th>
    </tr>
    <tr class="statusAggregationCostReport scData hide">
        <th class="tableColumn">Challenge Status</th>
        <th class="tableColumn">Challenge Fees</th>
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
       cellspacing="0" data-intro="This table contains the full details for every competition included within your filter. Click the column headers to sort.  Click a challenge or project to drill into details." data-step="10">
    <thead>
    <tr>
        <th class="tableTitle" colspan="11">
            <a href="javascript:void(0)" class="expand">&nbsp;</a>
            <span>Cost Details</span>
        </th>
        <th class="tableTitle viewType" colspan="3">
            <a title="Cost Breakdown View" class="viewBtnCostBreakdown" href="javascript:;"></a>
            <a title="Default View" class="viewBtnDefault current" href="javascript:;"></a>
        </th>
    </tr>
    <tr>
        <th class="tableColumn">&nbsp;Customer&nbsp;</th>
        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
        <th class="tableColumn">&nbsp;Project&nbsp;</th>
        <th class="tableColumn">&nbsp;Filter Value&nbsp;</th>
        <th class="tableColumn">&nbsp;Challenge&nbsp;</th>
		<th class="tableColumn">&nbsp;Challenge Id&nbsp;</th>
        <th class="tableColumn">&nbsp;Challenge Type&nbsp;</th>
        <th class="tableColumn">&nbsp;Status&nbsp;</th>
        <th class="tableColumn">&nbsp;Launch Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Completion Date&nbsp;</th>
        <th class="tableColumn">&nbsp;Challenge Fee&nbsp;</th>
        <th class="tableColumn">&nbsp;Estimated Member Cost&nbsp;</th>
        <th class="tableColumn">&nbsp;Actual Member Cost&nbsp;</th>
        <th class="tableColumn">&nbsp;&nbsp;&nbsp;Total&nbsp;&nbsp;&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${viewData.costDetails}" var="item" varStatus="loop">
        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
        <tr class="pipelineDetailsRow" id="contest_${item.contest.id}">
            <td class="firstCol">
                <c:out value="${item.client.name}"/>
            </td>
            <td>
                <c:out value="${item.billing.name}"/>
            </td>
            <td>
                <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="%{#attr['item'].project.id}"/></s:url>">

                    <c:out value="${item.project.name}"/>
                </a>
            </td>
            <td>
                <c:out value="${item.projectFilterValue}" />
            </td>
            <td>
                <s:if test="%{#attr['item'].contest.id > 0}">
                    <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['item'].contest.id}"/></s:url>">
                        <c:out value="${item.contest.name}"/>
                    </a>
                </s:if>
            </td>
			<td>
                <s:if test="%{#attr['item'].contest.id > 0}">
                    <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['item'].contest.id}"/></s:url>">
                        <c:out value="${item.contest.id}"/>
                    </a>
                </s:if>
            </td>
            <td>
                <c:out value="${item.contestType.name}"/>
            </td>
            <td>
                <c:out value="${item.status}"/>
            </td>
            <td  class="singleLineCol">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.launchDate}"/>
            </td>
            <td  class="singleLineCol">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${item.completionDate}"/>
            </td>
            <td>
                 <fmt:formatNumber value="${item.contestFee}" pattern="$###,##0.00"/>
            </td>
            <td>
                <s:if test="%{#attr['item'].contest.id > 0}">
                    <fmt:formatNumber value="${item.estimatedCost}" pattern="$###,##0.00"/>
                </s:if>
            </td>
            <td>

                 <c:set var="status" value="${fn:trim(item.status)}"/>
                <%-- only display the actual cost when the challenge is finished --%>
                <%--<c:if test="${status == 'Finished'}"> --%>
                    <fmt:formatNumber value="${item.actualCost}" pattern="$###,##0.00"/>
                <%--</c:if>--%>
            </td>
            <td class="lastCol">
                <fmt:formatNumber value="${item.total}" pattern="$###,##0.00"/>
            </td>

        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
        <tr class="reportTotal"> <td colspan="10" style="text-align:right"> Total Sum:&nbsp;&nbsp; </td><td></td><td></td><td></td><td class="lastCol"></td></tr>
    </tfoot>
</table>

        <div class="container2Left">
            <div class="container2Right">
                <div class="container2Bottom">
                    <div>
                        <div>

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
            <a href="javascript:void(0)" id="contestDViewClose"></a>
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
                                <th class="tableColumn sorting">Filter Value</th>
                                <th class="tableColumn sorting">Challenge</th>
								<th class="tableColumn sorting">Challenge Id</th>
                                <th class="tableColumn sorting">Challenge Type</th>
                                <th class="tableColumn sorting">Status</th>
                                <th class="tableColumn sorting">Launch Date</th>
                                <th class="tableColumn sorting">Completion Date</th>
                                <th class="tableColumn sorting">Challenge Fee</th>
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
                                <th class="tableColumn sorting">Testing</th>
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
<jsp:include page="/WEB-INF/includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
