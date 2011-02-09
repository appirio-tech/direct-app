<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project overview view.
  -
  - Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
  - Change to new UI.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="overview"/>
    <link rel="stylesheet" href="/css/dashboard-view.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/jquery.dataTables.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js"></script>
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
                                <a href="project-overview.html">Projects</a> &gt;
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                            </div>

                            <div class="spaceWhite"></div> 
                            <div class="dashboardTable">
                                <dl>
                                    <dt>
                                        <a href="javascript:void(0)" class="expand">Project Health</a>
                                    </dt>
                                    <dd>
                                        <table  cellpadding="0" cellspacing="0" id="projectHealthTable">
                                            <div class="dashboardTableHeader">
                                                <colgroup>
                                                    <col width="40%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%"/>
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th class="first">Contest</th>
                                                        <th>Timeline</th>
                                                        <th>Registration</th>
                                                        <th>Review</th>
                                                        <th>Forum</th>
                                                        <th>Dependencies</th>
                                                    </tr>
                                                </thead>
                                            </div>
                                            <div>
                                                <!--
                                                <colgroup>
                                                    <col width="40%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                    <col width="10%" />
                                                </colgroup>
                                                -->
                                                <tbody>
                                                    <!--
                                                        <th>Timeline</th>
                                                        <th>Registration</th>
                                                        <th>Review</th>
                                                        <th>Forum</th>
                                                        <th>Dependencies</th>
                                                    -->
                                                        
                                                    <s:iterator value="viewData.contests" status="sta">
                                                        <tr <c:if test="${sta.even}">class='even'</c:if>>
                                                            <td class="first">
                                                                <s:if test="%{#attr['key'].software}" >
                                                                <a class="longWordsBreak ${value.contestStatusColor.name}" href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <c:out value="${key.title}"/></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a class="longWordsBreak ${value.contestStatusColor.name}" href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <c:out value="${key.title}"/></a>
                                                                </s:else>
                                                            </td>
                                                            <td>
                                                                <s:if test="%{#attr['key'].software}" >
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.phaseStatusColor.name}"></span></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.phaseStatusColor.name}"></span></a>
                                                                </s:else>
                                                            </td>
                                                            <td>
                                                                <s:if test="%{#attr['key'].software}" >
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.regStatusColor.name}"></span></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.regStatusColor.name}"></span></a>
                                                                </s:else>
                                                            </td>
                                                            <td>
                                                                <s:if test="%{#attr['key'].software}" >
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.reviewersSignupStatusColor.name}"></span></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.reviewersSignupStatusColor.name}"></span></a>
                                                                </s:else>
                                                            </td>
                                                            <td>
                                                                <s:if test="%{#attr['key'].software}" >
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.forumActivityStatusColor.name}"></span></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.forumActivityStatusColor.name}"></span></a>
                                                                </s:else>
                                                            </td>
                                                            <td>
                                                                 <s:if test="%{#attr['key'].software}" >
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.dependenciesStatusColor.name}"></span></a>
                                                                </s:if>
                                                                <s:else>
                                                                <a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['key'].id}"/></s:url>">
                                                                    <span class="${value.dependenciesStatusColor.name}"></span></a>
                                                                </s:else>
                                                            </td>
                                                        </tr>
                                                    </s:iterator>
                                                </tbody>
                                            </div>
                                        </table>
                                    </dd>
                                </dl>
                            </div>
                            <!-- End .dashboardTable -->


                            <div class="leftColumn">
                                <div class="areaHeader padding2">
                                        <h2 class="title">Project Status</h2>
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
                                                <td class="statusName">Finished(Completed/Failed)</td>
                                                <td>${viewData.projectStats.finishedContestsNumber}(${viewData.dashboardProjectStat.completedNumber}/
                                                    ${viewData.projectStats.finishedContestsNumber-viewData.dashboardProjectStat.completedNumber})</td>
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
                                                <td class="statusName">Average Fulfillment</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageFulfillment}" pattern="##0.##"/>%
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
                                                    <s:if test="%{#attr['contest'].software}" >
                                                    <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>">
                                                        Link To <c:out value="${contest.title}"/></a>
                                                    </s:if>
                                                    <s:else>
                                                    <a class="longWordsBreak" href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['contest'].id}"/></s:url>">
                                                        Link To <c:out value="${contest.title}"/></a>
                                                    </s:else>
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

</body>
<!-- End #page -->

</html>
