<%--
  - Author: Veve, isv
  -
  - Version: 1.0.4
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project overview view.
  -
  - Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
  - Change to new UI.
  - version 1.0.2 - TC Cockpit Bug Tracking R1 Cockpit Project Tracking Assembly Change Note
  - -add issue tracking to health table
  - -add unresolved issues and ongoing bug races to project status.
  - Version 1.0.3 - Direct - TC Cockpit Contest Duration Calculation Updates Assembly Change Note:
  - added Average Contest Duration 
  - Version 1.0.4 - Direct - TC Cockpit Project Health Update Assembly Change Note:
  - Added new columns to Project Health area; the data for Project Health area is loaded via AJAX call now 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie7.css" />
    <![endif]-->
    <ui:projectPageType tab="overview"/>
    <link rel="stylesheet" href="/css/dashboard-view.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/jquery.dataTables.js"></script>
    <script type="text/javascript" src="/scripts/jquery.ba-throttle-debounce.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                            </div>

                            <div class="spaceWhite"></div> 
                            <div class="dashboardTable">
                                <dl>
                                    <dt>
                                        <a href="javascript:void(0)" class="expand">Project Health</a>
                                    </dt>
                                    <dd>
                                        <div class="projectHealthHeader">
                                            <table cellpadding="0" cellspacing="0">
                                                <colgroup>
                                                    <col width="26%" />
                                                    <col width="13%" />
                                                    <col width="10%" />
                                                    <col width="14%" />
                                                    <col width="7%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                    <col width="6%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th class="first">Contest</th>
                                                        <th>Type</th>
                                                        <th>Current Phase</th>
                                                        <th>Next Phase</th>
                                                        <th>Timeline</th>
                                                        <th>Registration</th>
                                                        <th>Review</th>
                                                        <th>Forum</th>
                                                        <th>Dependencies</th>
                                                        <th>Issue Tracking</th>
                                                    </tr>
                                                </thead>
                                            </table>
                                        </div>
                                        <div class="projectHealthBody">
                                            <table  cellpadding="0" cellspacing="0" id="projectHealthTable">
                                                <colgroup>
                                                    <col width="26%" />
                                                    <col width="13%" />
                                                    <col width="10%" />
                                                    <col width="14%" />
                                                    <col width="7%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                    <col width="6%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                </colgroup>
                                                <tbody id="projectHealthTableBody">
                                                    <tr id="loaderProjectHealthWrapper">
                                                        <td colspan="10">
                                                            <div id="loaderProjectHealth">
                                                                &nbsp;<img src="/images/ajax-loader.gif" alt="Loading" width="220" height="19"/>&nbsp;
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </dd>
                                </dl>
                            </div>
                            <!-- End .dashboardTable -->


                            <div class="leftColumn">
                                <div class="areaHeader padding2">
                                        <h2 class="title">Project Stats</h2>
                                </div><!-- End .areaHeader -->
                                <table class="projectStats" cellpadding="0" cellspacing="0">
                                    <s:push value="viewData.projectStats">
                                        <tbody>
                                            <tr>
                                                <td class="statusName"># of Drafts</td>
                                                <td><s:property value="draftContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName"># Scheduled</td>
                                                <td><s:property value="pipelineContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName"># Active</td>
                                                <td><s:property value="runningContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Finished(Completed/Failed/Cancelled)</td>
                                                <td>${viewData.projectStats.finishedContestsNumber + viewData.dashboardProjectStat.cancelledNumber}(${viewData.dashboardProjectStat.completedNumber}/
                                                    ${viewData.projectStats.finishedContestsNumber-viewData.dashboardProjectStat.completedNumber}/
                                                    ${viewData.dashboardProjectStat.cancelledNumber})</td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Member Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalMemberCost}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Member Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageMemberCostPerContest}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Contest Fee</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalContestFee}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Contest Fee</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageContestFeePerContest}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Project Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalProjectCost}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Contest Duration</td>
                                                <td>
                                                    <c:out value="${tcdirect:getDurationTextInDays(viewData.dashboardProjectStat.averageContestDuration)}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Fulfillment</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageFulfillment}" pattern="##0.##"/>%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Unresolved Issues</td>
                                                <td>
                                                    <a href="<s:url action='projectIssueTracking' namespace='/'>
                                                    <s:param name='formData.projectId' value='formData.projectId'/>
                                                     <s:param name='subTab'>issues</s:param></s:url>">

                                                    ${viewData.dashboardProjectStat.unresolvedIssuesNumber}</a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Ongoing Bug Races</td>
                                                <td>
                                                     <a href="<s:url action='projectIssueTracking' namespace='/'>
                                                    <s:param name='formData.projectId' value='formData.projectId'/>
                                                     <s:param name='subTab'>bugRaces</s:param></s:url>">${viewData.dashboardProjectStat.ongoingBugRacesNumber}</a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </s:push>
                                </table><!-- End .projectsStats -->
                            </div>

                            <div class="rightColumn">
                                <div class="areaHeader padding2">
                                    <h2 class="title">Project Activities</h2>
                                </div><!-- End .areaHeader -->

                                <s:iterator value="viewData.latestProjectActivities.activities">
                                    <table class="project" width="100%" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th colspan="5"><span class="left"><span class="right">
                                                    <a href="javascript:;"><s:property value="key.title"/></a></span></span>
                                                </th>
                                            </tr>
                                        </thead>
                                    
                                        <tbody>
                                        <s:iterator value="value" status="status">
                                            <s:set value="originatorId" var="originatorId" scope="page"/>
                                            <s:set value="originatorHandle" var="originatorHandle" scope="page"/>
                                            <s:set value="contest" var="contest" scope="page"/>
                                            <s:set value="date" var="date" scope="page"/>
                                            
                                            <tr class="<s:property value='type.shortName'/> <s:if test='#status.index == 4'>hideStart</s:if>">
                                                <td class="first <s:property value="type.shortName"/>"></td>
                                                <td class="second">
                                                    <span class="ico <s:property value="type.shortName"/>">
                                                        <s:property value="type.name"/></span>
                                                </td>
                                                <td>
                                                    <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>">
                                                        <c:out value="${contest.title}"/></a>
                                                </td>
                                                <td class="posted">
                                                    <s:property value="type.actionText"/> :
                                                    <link:user userId="${originatorId}" handle="${originatorHandle}"/>
                                                </td>                                                
                                                <td class="date">
                                                    <c:out value="${tcdirect:getDateText(date, 'MM/dd/yyyy')}"/>
                                                </td>
                                            </tr>
                                            
                                        </s:iterator>
                                        </tbody>
                                    </table>
                                </s:iterator>
                                
                                <jsp:include page="includes/upcomingActivities.jsp"/>
                            </div><!-- End .rightColumn -->

                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

<!-- .tooltipArea -->
<div class="tooltipArea"></div>
<!-- end .tooltipArea -->

</body>
<!-- End #page -->

</html>
