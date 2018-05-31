<%--
  - Author: isv, Blues, Ghost_141, csy2012, duxiaoyang
  - Version: 1.9
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for Pipeline report including form and report data.
  -
  - Version 1.1 (Direct Pipeline Stats Update assembly) change notes: Added "Drafts To Lauched" column to "Launched
  - Contests" section.
  -
  - Version 1.2 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  -
  - Version 1.2.1 (Release Assembly - TC Direct UI Improvement Assembly 3) changes: Add jsp to show server side validation error
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
  - Add the search bar for pipleline details report
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit Reports Filter Panels Revamp) changes:
  - - Updte the filter panel of pipeline report to the new one.
  -
  - Version 1.5 (TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) changes:
  - - Added Group by and group values into the filter panel.
  -
  - Version 1.6 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - - Remove the container2BottomLeft and container2BottomRight class in pagination part.
  -
  - Version 1.7 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 2.0) changes:
  - - Changed the font to bold for "FROM" and "TO".
  -
  - Version 1.8 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to report
  -
  - Version 1.9 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
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
    <script type="text/javascript" src="/scripts/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/scripts/tableSearchBar.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-pipeline.js"></script>

    <c:if test="${not viewData.showJustForm}">
        <script type="text/javascript">
            $(document).ready(function() {
                getDraftsRatio($('#scheduledContestsViewType').val());
            });
        </script>
    </c:if>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="pipelineReportContent">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->

                        <div class="area1Content">

                            <jsp:include page="/WEB-INF/includes/report/header.jsp">
                                <jsp:param name="reportTitle" value="Pipeline Report"/>
                            </jsp:include>


                          <div class="pipelineFilter" id="pipelineReportsPage" data-intro="This report gives you a past/present/future view of your competition volume in a report format. Use the filter to narrow your results." data-step="1">

                            <div class="filterTitle">
                                <div class="filterTitleRight">
                                    <div class="filterTitleCenter">
                                        <a href="javascript:;" class="expanded"></a>
                                        <h4>Filter</h4>
                                    </div>
                                    <!-- End .filterTitleCenter -->
                                </div>
                                <!-- End .filterTitleRight -->
                            </div>

                            <s:form method="post" action="dashboardPipelineReport" namespace="/"
                                    id="DashboardSearchForm">
                            <fieldset>
                            <div class="filterContainer">
                            <!-- .leftFilterContent -->
                            <div class="leftFilterContent">
                                <input type="hidden" value="1" id="pipelineDetailsPageNumber2"/>
                                <input type="hidden" name="formData.excel" value="false" id="formDataExcel"/>

                                <div id="datefilter" >

                                    <div class="filterRow firstFilterRow" data-intro="Use the date filters to constrain on the LAUNCH dates of your competitions." data-step="2">
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

                                <div id="pipelineDataFilter">

                                    <div class="filterRow">
                                        <div class="filterRowLeft" data-intro="If you want to include reposted contests in your counts, check this box." data-step="3">
                                            <label for="showReposts">Include Repost:</label>
                                        </div>
                                        <s:checkbox id="showReposts" name="formData.showReposts" cssClass="checkbox"/>

                                        <div class="clearFix"></div>
                                    </div>
                                    <!-- end .filterRow -->
                                    <div class="clearFix"></div>

                                    <div class="filterRow adjustFilterRow firstFilterRow" data-intro="Select the additional filter you want to apply and set the numerical range." data-step="4">
                                        <div class="filterRowLeft">
                                            <label for="numericalFilterMinValue">Numerical Filter:</label>
                                        </div>
                                        <s:select id="numericalFilter" list="viewData.numericalFilterOptions"
                                                  size="1" name="formData.numericalFilterType"/>
                                    </div>
                                    <!-- end .filterRow -->
                                    <div class="clearFix"></div>
                                    <div class="filterRow adjustFilterRow">
                                     	<strong> From </strong>
                                     	<s:textfield cssClass="text" name="formData.numericalFilterMinValue"
                                                          size="10" maxlength="10" id="numericalFilterMinValue"/>
                                        <span class="to"><strong>To</strong></span> <s:textfield cssClass="text"
                                                                                name="formData.numericalFilterMaxValue"
                                                                                size="10" maxlength="10"
                                                                                id="numericalFilterMaxValue"/>

                                        <div class="clearFix"></div>

                                    </div>
                                    <!-- end .filterRow -->
                                </div>

                                <!-- end .pipelineFilter -->
                            </div>
                            <!-- end .leftFilterContent -->

                            <!-- .rightFilterContent -->
                            <div class="rightFilterContent">

                                <span class="showJustForm hide">${viewData.showJustForm}</span>
                                <div id="groupFilter" data-intro="If these look familiar, then you know they are based on your project meta data. To learn more about this, visit TopCoder University." data-step="5">
                                    <div class="filterRow firstFilterRow">
                                        <div class="filterRowLeft">
                                            <label for="formDatagroup">Project Filters:</label>
                                            <img src="/images/dots-white.gif" class="indicator"/>
                                        </div>

                                        <s:select id="formDatagroup"
                                        		list="viewData.groupKeys"
                                        		name="formData.groupId"/>
                                    </div>

                                    <div class="multiSelectArea" id="formDatagroupValue">
                                        <div class="multiSelectAreaInner">
                                            <label class="multiSelectAreaTitle">Project Filter Values:<img
                                                    src="/images/dots-white.gif" class="indicator" alt
                                                    style="padding-left:4px"/><span class="reportWarningMessage hide">No Value Available</span></label>

                                            <div class="multiSelectBox">
                                                <div class="multiOptionRow multiOptionRowChecked hide">
                                                    <input type="checkbox" class="optionAll" name=""
                                                           id="groupValuesSelectAll" checked="checked">
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
                                </div>

                                    <div class="multiSelectArea selectHigher">
                                    <div class="multiSelectAreaInner" data-intro="We have many competition types.  If you don't want to see all of them, only select the ones you're interested in here." data-step="6">
                                        <label class="multiSelectAreaTitle">Challenge Type:</label>

                                        <div class="multiSelectBox">
                                            <div class="multiOptionRow multiOptionRowChecked">
                                                <input type="checkbox" checked="checked" id="ContestTypeSelectAll" class="optionAll"/>
                                                <label for="ContestTypeSelectAll" title="Select All">Select All</label>
                                            </div>

                                            <s:iterator value='viewData.contestTypeOptions' status='c'>
                                                <s:set var='needCheck' value='false'/>
                                                <s:iterator value='formData.contestTypes' var="contestType">
                                                    <s:if test="#contestType == key"><s:set var='needCheck' value='true'/></s:if>
                                                </s:iterator>
                                                <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                                    <input type="checkbox"
                                                           <s:if test='#needCheck'>checked="checked"</s:if>
                                                           id="ProjectTypeCheckBox${c.index}" class="optionItem"
                                                           name="formData.contestTypes" value="${key}"/>
                                                    <label for="ProjectTypeCheckBox${c.index}"
                                                           title="${value}">${value}</label>
                                                </div>
                                            </s:iterator>
                                        </div>

                                    </div>
                                </div>
                                <!-- end .multiSelectArea -->

                                <div class="multiSelectArea selectHigher">
                                    <div class="multiSelectAreaInner" data-intro="Narrow down based on competition statuses." data-step="7">
                                        <label class="multiSelectAreaTitle">Challenge Status:</label>

                                        <div class="multiSelectBox">
                                           <div class="multiOptionRow multiOptionRowChecked">
                                                <input type="checkbox" checked="checked" id="ContestStatusSelectAll"
                                                       class="optionAll"/>
                                                <label for="ContestStatusSelectAll" title="Select All">Select All</label>
                                            </div>

                                            <s:iterator value='viewData.contestStatusOptions' status='c'>
                                                <s:set var='needCheck' value='false'/>
                                                <s:iterator value='formData.contestStatuses' var="contestStatus">
                                                    <s:if test="#contestStatus == key"><s:set var='needCheck' value='true'/></s:if>
                                                </s:iterator>
                                                <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                                    <input type="checkbox"
                                                           <s:if test='#needCheck'>checked="checked"</s:if>
                                                           id="StatusSelectCheckBox${c.index}" class="optionItem"
                                                           name="formData.contestStatuses" value="${key}"/>
                                                    <label for="StatusSelectCheckBox${c.index}"
                                                           title="${value}">${value}</label>
                                                </div>
                                            </s:iterator>
                                        </div>

                                    </div>
                                </div>
                                <!-- end .multiSelectArea -->

                                <div class="multiSelectArea">
                                    <div class="multiSelectAreaInner">
                                        <label class="multiSelectAreaTitle">Include Clients:</label>

                                        <div class="multiSelectBox clientsSelection">
                                           <div class="multiOptionRow multiOptionRowChecked">
                                                <input type="checkbox" checked="checked" id="ClientSelectAll" class="optionAll"/>
                                                <label for="ClientSelectAll" title="Select All">Select All</label>
                                            </div>

                                            <s:iterator value='viewData.clients' status='c' var="clientValue">
                                                <s:set var='needCheck' value='false'/>
                                                <s:iterator value='formData.clients' var="client">
                                                    <s:if test="#client == value"><s:set var='needCheck' value='true'/></s:if>
                                                </s:iterator>
                                                <div class="multiOptionRow <s:if test='#needCheck'>multiOptionRowChecked</s:if>">
                                                    <input type="checkbox"
                                                           <s:if test='#needCheck'>checked="checked"</s:if>
                                                           id="ClientSelectCheckBox${c.index}" class="optionItem"
                                                           name="formData.clients" value="${value}"/>
                                                    <span class="clientIdHolder hide">${key}</span>
                                                    <label for="ClientSelectCheckBox${c.index}"
                                                           title="${value}">${value}</label>
                                                </div>
                                            </s:iterator>
                                        </div>
                                    </div>
                                </div>
                                <!-- end .multiSelectArea -->
                                <div class="clear"></div>

                                <div class="applyButtonBox" >
                                    <a class="button6 applyButton" href="javascript:" id="submitPipelineForm" data-intro="Click apply to run the report." data-step="8"><span
                                            class="left"><span class="right">APPLY</span></span></a>
                                </div>
                                <!-- end .applyButtonBox -->

                            </div>
                            <!-- end .rightFilterContent -->
                            <div class="clearFix"></div>
                            <div id="validationErrors">
                                <s:actionerror/>
                            </div>
                            <div>
                                <c:forEach items="${formData.numericalFilterTypes}" var="filterType"
                                           varStatus="loop">
                                    <p>
                                        <c:out value="${tcdirect:numericalFilterTypeToString(filterType)}"/>
                                        Numeric Filter
                                        <fmt:formatNumber
                                                value="${formData.numericalFilterMinValues[loop.index]}"
                                                pattern="##0.##"/>
                                        &lt;=
                                        &nbsp;
                                        <c:out value="${tcdirect:numericalFilterTypeToString(filterType)}"/>
                                        &nbsp;
                                        &lt;= <fmt:formatNumber
                                            value="${formData.numericalFilterMaxValues[loop.index]}"
                                            pattern="##0.##"/>
                                        <a href="javascript:" class="removeNumericalFilter">remove filter</a>
                                        <input type="hidden" name="formData.numericalFilterTypes"
                                               value="${filterType}"/>
                                        <input type="hidden" name="formData.numericalFilterMinValues"
                                               value="${formData.numericalFilterMinValues[loop.index]}"/>
                                        <input type="hidden" name="formData.numericalFilterMaxValues"
                                               value="${formData.numericalFilterMaxValues[loop.index]}"/>
                                    </p>
                                </c:forEach>
                            </div>
                             </div>
                            </fieldset>
                          </div>
                          </s:form>

                            <c:if test="${not viewData.showJustForm}">

                            <%--
                             Pipeline Report area providing stats per Client, Project, Person, Billing, Copilot.
                             Initially Client stats are displayed. The drop-down is provided for selecting the
                             desired type of stats to display
                            --%>
                            <div id="pipelineReportArea">
                                <%-- Pipeline summary --%>
                                <table id="pipelineSummary" class="pipelineStats resultTable" cellpadding="0" cellspacing="0" data-intro="A weekly summary of your challenge volume?" data-step="9"  data-position="top">
                                <thead>
                                    <tr>
                                        <th class="tableTitle" colspan="5">
                                            <a href="javascript:void(0)" class="expand">&nbsp;</a>
                                            <span>Pipeline Summary</span>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th class="tableColumn">Week</th>
                                        <th class="tableColumn">Member Costs</th>
                                        <th class="tableColumn">Challenge Fee</th>
                                        <th class="tableColumn">All</th>
                                        <th class="tableColumn">Launched</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${viewData.summaries}" var="summary" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${summary.isTotal}">
                                                    <c:set var="rowStyle" value="total"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr class="${rowStyle}">
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${summary.isTotal}">Totals</c:when>
                                                        <c:otherwise>
                                                            <a href="javascript:" class="summaryWeek"
                                                               rel="${summary.weekOf.time}">
                                                                <fmt:formatDate value="${summary.weekOf}"
                                                                                pattern="yyyy-MM-dd"/></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${summary.memberCosts}"
                                                                      pattern="$###,##0.00"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${summary.contestsFee}"
                                                                      pattern="$###,##0.00"/>
                                                </td>
                                                <td>${summary.allContestsCount}</td>
                                                <td>${summary.launchedContestsCount}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <%-- Scheduled contests --%>
                                <table id="pipelineScheduledContests" class="pipelineStats"
                                       cellpadding="0" cellspacing="0" data-intro="Group by different dimensions of data using the View By control on the right." data-step="10"  data-position="top">
                                    <thead>
                                    <tr>
                                        <th class="tableTitle" colspan="4">
                                            <a href="javascript:void(0)" class="expand">&nbsp;</a>
                                            <span>List of scheduled contests</span>
                                        </th>
                                        <th class="tableTitle viewType">
                                            <label for="scheduledContestsViewType">View By:</label>
                                            <select id="scheduledContestsViewType">
                                                <option>Client</option>
                                                <option>Manager</option>
                                                <option>Copilot</option>
                                                <option>Project</option>
                                                <option value="ContestType">Challenge Type</option>
                                                <option>Billing</option>
                                            </select>
                                        </th>
                                    </tr>
                                    <tr class="ClientScheduledContests scData">
                                        <th class="tableColumn">Client</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    <tr class="ManagerScheduledContests scData hide">
                                        <th class="tableColumn">Manager</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    <tr class="CopilotScheduledContests scData hide">
                                        <th class="tableColumn">Copilot</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    <tr class="ProjectScheduledContests scData hide">
                                        <th class="tableColumn">Project</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    <tr class="ContestTypeScheduledContests scData hide">
                                        <th class="tableColumn">Challenge Type</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    <tr class="BillingScheduledContests scData hide">
                                        <th class="tableColumn">Billing</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Draft to Launched ratio<br/>(last 90 days)</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <%-- Stats per client (visible initially) --%>
                                    <c:forEach items="${viewData.clientScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="ClientScheduledContests scData ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    <%-- Stats per person (hidden initially) --%>
                                    <c:forEach items="${viewData.personScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="ManagerScheduledContests scData hide ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    <%-- Stats per scheduled contests (hidden initially) --%>
                                    <c:forEach items="${viewData.copilotScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="CopilotScheduledContests scData hide ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    <%-- Stats per projects (hidden initially) --%>
                                    <c:forEach items="${viewData.projectScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="ProjectScheduledContests scData hide ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    <%-- Stats per project categories (hidden initially) --%>
                                    <c:forEach items="${viewData.categoriesScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="ContestTypeScheduledContests scData hide ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    <%-- Stats per billing projects (hidden initially) --%>
                                    <c:forEach items="${viewData.billingprojectScheduledLaunchedContestStats}" var="stat"
                                               varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                        <tr class="BillingScheduledContests scData hide ${rowStyle}">
                                            <td><c:out value="${stat.source}"/></td>
                                            <td>${stat.draftContestsCount}</td>
                                            <td>${stat.scheduledContestsCount}</td>
                                            <td>${stat.launchedContestsCount}</td>
                                            <td class="draftsLaunchedRatio">Calculating...</td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>

                                <div class='filterPanel searchBar' id='pipelineSearchBar'>
                                    <div class='filterHead'>
                                        <div class='rightSide'>
                                            <div class='inner'>
                                                <div class='searchContainer' data-intro="Type here to search your results." data-step="12">
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

                                <%-- Pipeline Details --%>
                                <div class="resultTableContainer" data-intro="Here are the specific details about all of the contests in your results." data-step="11"  data-position="top">
                                <table id="pipelineDetails" class="pipelineStats resultTable paginatedDataTable" cellpadding="0"
                                       cellspacing="0">
                                 <colgroup>
                                                <col width="6%" />
                                                <col width="6%" />
                                                <col width="6%" />
                                                <col width="6%" />
                                                <col width="5%" />
                                                <col width="5%" />
                                                <col width="6%" />
                                                <col width="13%" />
                                                <col width="6%" />
                                                <col width="14%" />
                                                <col width="5%" />
                                                <col width="5%" />
                                                <col width="5%" />
                                                <col width="6%" />
                                                <col width="6%" />
                                </colgroup>


                                <thead>
                                    <tr>
                                        <th class="tableTitle" colspan="15">
                                            <a href="javascript:void(0)" class="expand">&nbsp;</a>
                                            <span>Pipeline Details</span>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th class="tableColumn">&nbsp;Date&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Member Costs&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Challenge Fee&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Challenge Type&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Client&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Copilot&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Project&nbsp;</th>
                                        <th class="tableColumn">Filter Value</th>
                                        <th class="tableColumn">&nbsp;Name&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Status&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Repost&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Manager&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Added&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Changed&nbsp;</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${viewData.contests}" var="contest" varStatus="loop">
                                        <c:set var="rowStyle" value="${loop.index mod 2 eq 1 ? 'alt' : ''}"/>
                                    <tr class="pipelineDetailsRow contestOfWeek${tcdirect:getWeekOfDate(contest.startDate).time}">
                                        <td class="first">
                                            <fmt:formatDate value="${tcdirect:toDate(contest.startDate)}"
                                                            pattern="yyyy-MM-dd (EEE)"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${tcdirect:getMemberCosts(contest)}"
                                                              pattern="$###,##0.00"/>
                                        </td>
                                        <td><fmt:formatNumber value="${contest.contestFee}" pattern="$###,##0.00"/></td>
                                        <td><c:out value="${contest.contestCategory}"/></td>
                                        <td><c:out value="${contest.clientName}"/></td>
                                        <td>
                                            <c:forEach items="${contest.copilots}" var="copilot">
                                                <c:out value="${copilot}"/><br/>
                                            </c:forEach>
                                        </td>
                                        <td><c:out value="${contest.cpname}"/></td>
                                        <td>
                                            <a class="longWordsBreak"
                                               href="<s:url action="projectOverview" namespace="/">
                                                         <s:param name="formData.projectId" value="%{#attr['contest'].projectId}"/>
                                                     </s:url>"><c:out value="${contest.pname}"/></a>
                                        </td>
                                        <td>
                                            <c:out value="${viewData.contestsProjectFilterValues[contest]}"/>
                                        </td>
                                        <td>
                                            <s:if test="%{#attr['contest'].studio}" >
                                                <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['contest'].contestId}"/></s:url>">
                                                    <c:out value="${contest.cname}"/></a>
                                            </s:if>
                                            <s:elseif test="%{#attr['contest'].contestCategory == 'Copilot Posting'}" >
                                                <a class="longWordsBreak" href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['contest'].contestId}"/></s:url>">
                                                    <c:out value="${contest.cname}"/></a>
                                            </s:elseif>
                                            <s:else>
                                                <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].contestId}"/></s:url>">
                                                    <c:out value="${contest.cname}"/></a>
                                            </s:else>
                                        </td>
                                        <td><c:out value="${contest.sname}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${contest.wasReposted}">Yes</c:when>
                                                <c:otherwise>No</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${not empty contest.manager}"><c:out value="${contest.manager}"/></c:if>
                                        </td>
                                        <td class="singleLineCol">
                                            &nbsp;<fmt:formatDate value="${tcdirect:toDate(contest.createTime)}"
                                                            pattern="yyyy-MM-dd"/>&nbsp;
                                        </td>
                                        <td class="last singleLineCol">
                                            &nbsp;<fmt:formatDate value="${tcdirect:toDate(contest.modifyTime)}"
                                                                  pattern="yyyy-MM-dd"/>&nbsp;
                                        </td>
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
                                                                                <a href="javascript:directExcel();" class="exportExcel">Export to <strong>Excel</strong></a>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                        <!-- this area contains the print, export to excel, export to pdf links -->


                                    <!-- End .panel -->
                                </div>
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
