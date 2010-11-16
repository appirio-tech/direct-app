<%--
  - Author: isv
  - Version: 1.0 (Direct Pipeline Integration Assembly 1.0)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the view for Pipeline report including form and report data.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:dashboardPageType tab="reports"/>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
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
                                <jsp:param name="reportTitle" value="Pipeline Report"/>
                            </jsp:include>

                            <%-- Pipeline form --%>
                            <div class="pipelineSearch">
                                <s:form method="get" action="dashboardPipelineReport" namespace="/"
                                        id="DashboardSearchForm">
                                    <fieldset>
                                        <input type="hidden" name="ppp"
                                               value="${empty param.ppp ? '1' : param.ppp}" id="pipelineDetailsPageNumber2"/>
                                        <s:hidden name="formData.excel" id="formDataExcel" value="false" />
                                        <div id="datefilter">
                                            <label for="startDate" class="fLeft">Start:</label>
                                            <s:textfield cssClass="fLeft text date-pick dp-applied"
                                                         name="formData.startDate" id="startDate" readonly="true"/>
                                            <label for="endDate" class="fLeft">End:</label>
                                            <s:textfield cssClass="fLeft text date-pick dp-applied"
                                                         name="formData.endDate" id="endDate" readonly="true"/>
                                        </div>
                                        <div>
                                            <span>&nbsp;</span>
                                        </div>
                                        <div id="contestFilter">
                                            <label for="contestType">Contest Type:</label>
                                            <s:select id="contestType" list="viewData.contestTypeOptions"
                                                      multiple="true" name="formData.contestTypes" size="5"/>
                                            <label for="contestStatus">Contest Status:</label>
                                            <s:select id="contestStatus" list="viewData.contestStatusOptions"
                                                      multiple="true" name="formData.contestStatuses" size="5"/>
                                            <label for="clients">Include Clients:</label>
                                            <s:select id="clients" list="viewData.clients" multiple="true" size="5"
                                                      name="formData.clients"/>
                                        </div>
                                        <div id="pipelineFilter">
                                            <label for="showReposts">Include Reposts:</label>
                                            <s:checkbox id="showReposts" name="formData.showReposts"/>
                                            <label for="numericalFilter">Numerical Filter:</label>
                                            <s:textfield cssClass="text" name="formData.numericalFilterMinValue"
                                                         size="10" maxlength="10" id="numericalFilterMinValue"/>
                                            &lt:=
                                            <s:select id="numericalFilter" list="viewData.numericalFilterOptions"
                                                      size="1" name="formData.numericalFilterType"/>
                                            &lt:=
                                            <s:textfield cssClass="text" name="formData.numericalFilterMaxValue"
                                                         size="10" maxlength="10" id="numericalFilterMaxValue"/>
                                        </div>
                                        <a href="javascript:" class="button1" id="submitPipelineForm">
                                            <span>Submit</span></a>
                                        <div class="clear"></div>
                                        <div id="validationErrors"></div>
                                        <div>
                                            <c:forEach items="${formData.numericalFilterTypes}" var="filterType"
                                                       varStatus="loop">
                                                <p>
                                                    <c:out value="${tcdirect:numericalFilterTypeToString(filterType)}"/>
                                                    Numeric Filter
                                                    <fmt:formatNumber
                                                               value="${formData.numericalFilterMinValues[loop.index]}"
                                                               pattern="##0.##" />
                                                    &lt;=
                                                    &nbsp;
                                                    <c:out value="${tcdirect:numericalFilterTypeToString(filterType)}"/>
                                                    &nbsp;
                                                    &lt;= <fmt:formatNumber
                                                               value="${formData.numericalFilterMaxValues[loop.index]}"
                                                               pattern="##0.##" />
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
                                    </fieldset>
                                </s:form>
                            </div>

                            <c:if test="${not viewData.showJustForm}">

                            <%--
                             Pipeline Report area providing stats per Client, Project, Person, Billing, Copilot.
                             Initially Client stats are displayed. The drop-down is provided for selecting the
                             desired type of stats to display
                            --%>
                            <div id="pipelineReportArea">
                                <%-- Pipeline summary --%>
                                <table id="pipelineSummary" class="pipelineStats" cellpadding="0" cellspacing="0">
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
                                        <th class="tableColumn">Contest Fee</th>
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
                                       cellpadding="0" cellspacing="0">
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
                                                <option value="ContestType">Contest Type</option>
                                                <option>Billing</option>
                                            </select>
                                        </th>
                                    </tr>
                                    <tr class="ClientScheduledContests scData">
                                        <th class="tableColumn">Client</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
                                    </tr>
                                    <tr class="ManagerScheduledContests scData hide">
                                        <th class="tableColumn">Manager</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
                                    </tr>
                                    <tr class="CopilotScheduledContests scData hide">
                                        <th class="tableColumn">Copilot</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
                                    </tr>
                                    <tr class="ProjectScheduledContests scData hide">
                                        <th class="tableColumn">Project</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
                                    </tr>
                                    <tr class="ContestTypeScheduledContests scData hide">
                                        <th class="tableColumn">Contest Type</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
                                    </tr>
                                    <tr class="BillingScheduledContests scData hide">
                                        <th class="tableColumn">Billing</th>
                                        <th class="tableColumn">Drafts</th>
                                        <th class="tableColumn">Scheduled</th>
                                        <th class="tableColumn">Launched</th>
                                        <th class="tableColumn">Launched / All</th>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
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
                                            <td>
                                                <c:if test="${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount != 0}">
                                                    <fmt:formatNumber value="${stat.launchedContestsCount * 100.0 / (stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount)}" pattern="##0.##"/>%&nbsp;
                                                </c:if>
                                                (${stat.launchedContestsCount}/${stat.launchedContestsCount + stat.draftContestsCount + stat.scheduledContestsCount})
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>

                                <%-- Pipeline Details --%>
                                <table id="pipelineDetails" class="pipelineStats paginatedDataTable" cellpadding="0" 
                                       cellspacing="0">
                                <thead>
                                    <tr>
                                        <th class="tableTitle" colspan="14">
                                            <a href="javascript:void(0)" class="expand">&nbsp;</a>
                                            <span>Pipeline Details</span>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th class="tableColumn">&nbsp;Date&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Member Costs&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Contest Fee&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Contest Type&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Client&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Copilot&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Billing&nbsp;</th>
                                        <th class="tableColumn">&nbsp;Project&nbsp;</th>
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
                                    <tr class="${rowStyle} pipelineDetailsRow contestOfWeek${tcdirect:getWeekOfDate(contest.startDate).time}">
                                        <td>
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
                                            <s:if test="%{#attr['contest'].studio}" >
                                                <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['contest'].contestId}"/></s:url>">
                                                    <c:out value="${contest.cname}"/></a>
                                            </s:if>
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
                                        <td>
                                            &nbsp;<fmt:formatDate value="${tcdirect:toDate(contest.createTime)}"
                                                            pattern="yyyy-MM-dd"/>&nbsp;
                                        </td>
                                        <td>
                                            &nbsp;<fmt:formatDate value="${tcdirect:toDate(contest.modifyTime)}"
                                                                  pattern="yyyy-MM-dd"/>&nbsp;
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                    <div class="panel">
                                        <!-- this area contains the print, export to excel, export to pdf links -->
                                        <strong id="showText">Show:</strong>
                                        <a href="javascript:directExcel();"
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
