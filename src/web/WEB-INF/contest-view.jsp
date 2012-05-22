<%--
  - Author: BeBetter, isv, tangzx, Blues
  - Version: 1.6
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the list of contests for a single selected project.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - add repost and new version buttons and assoicated js file.
  -
  - Version 1.2 (Submission Viewer Release 1 assembly) changes: linked submission numbers for each contest
  - to respective submission pages for Studio contests.
  -
  - Version 1.3 (Software Submission Viewer assembly) changes: linked submission numbers for each contest
  - to respective submission pages for Software contests.
  -
  - Version 1.4 (TC Direct - Page Layout Update Assembly) changes:
  - Apply to new prototype.  
  -
  - Version 1.5 (TC Direct "Contest Links and Button" Update 24Hr Assembly) changes:
  - Change the  style for the View/Edit buttons.
  -
  - Version 1.6 (Release Assembly - 24hrs TopCoder Cockpit Project Contests Calendar View) changes:
  - Add a new view project contests calendar view.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="contests"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <jsp:include page="includes/filterPanel.jsp"/>
    <link rel="stylesheet" href="/css/fullcalendar-1.5.2.css?v=207789" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/repostcontest.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/fullcalendar-1.5.2.min.js?v=207789"></script>
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
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                                <span id="currentDirectProjectID" class="hide"><s:property value="sessionData.currentProjectContext.id"/></span>
                            </div>


                          <div class="contestViews">
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Contests</h2>

                                <div class="calendarLegends hide">
                                    <span class="legendColor draftLegendColor"></span><span class="legendText">Draft</span>
                                    <span class="legendColor activeLegendColor"></span><span class="legendText">Active</span>
                                    <span class="legendColor completedLegendColor"></span><span class="legendText">Completed</span>
                                    <span class="legendColor cancelledLegendColor"></span><span class="legendText">Cancelled</span>
                                </div>
                                <div class="viewBtns">
                                    <a href="javascript:;" class="listViewBtn active">
                                        <span>List View</span>
                                    </a>
                                    <a href="javascript:;" class="calendarViewBtn">
                                        <span>Calendar</span>
                                    </a>
                                </div>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/project/projectStats.jsp"/>


                            <div class='filterPanel' id='ProjectsContestsFilter'>
                                <div class='filterHead'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='filterText'>
                                                <a href='javascript:;' class='collapse'><img
                                                        src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                <span class='title'>Filter</span>
                                            </div>
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
                                <div class='filterContent'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='column1'>
                                                <div class='row'>
                                                    <span class='title'>Contest Status</span>
                                                    <select id='contestStatusFilter'>
                                                        <option value='All'>All Contest Status</option>
                                                    </select>
                                                </div>
                                                <div class='row'>
                                                    <span class='title'>Contest Type</span>
                                                    <select id='contestTypeFilter'>
                                                        <option value='All'>All Contest Types</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <!--end .column1-->
                                            <div class='column3'>
                                                <div class='row'>
                                                    <span class='title dateLabel'>Start Date</span>
                                                    <input id='startDateBegin' type='text' class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='startDateEnd' type='text' class='date-pick'/>
                                                </div>
                                                <div class='row'>
                                                    <span class='title dateLabel'>End Date</span>
                                                    <input id='endDateBegin' type='text' class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='endDateEnd' type='text' class='date-pick'/>
                                                </div>
                                            </div>
                                            <!--end .column3-->
                                        </div>
                                    </div>
                                </div>
                                <!--end .filterHead-->
                                <div class='filterBottom'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                                <!--end .filterBottom-->
                                <div class='collapseBottom hide'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                            </div>


                            <div class="container2 contestTView"  id="ProjectContests">
                                <div class="myProjectContests">                                    
                                    <table id="ProjectContests" class="projectStats contests paginatedDataTable" width="auto"
                                           cellpadding="0"
                                           cellspacing="0">

                                     <colgroup>
                                            <col width="11%" />
                                            <col width="21%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="9%" />
                                            <col width="9%" />
                                            <col width="9%" />
                                            <col width="10%" />
                                            <col width="11%" />
                                        </colgroup>
                        
                                    <thead>
                                        <tr>

                                            <th>Contest Type</th>
                                            <th>Contest Name</th>
                                            <th>Start Date</th>
                                            <th>End Date</th>
                                            <th class="truncateRegs">Registrants</th>
                                            <th class="truncateSubs">Submissions</th>
                                            <th>Forums</th>
                                            <th>Status</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <s:iterator value="viewData.projectContests.contests"
                                                    status="status">
                                            <s:set value="contest" var="contest" scope="page"/>
                                            <s:set value="startTime" var="startTime" scope="page"/>
                                            <s:set value="endTime" var="endTime" scope="page"/>
                                            <s:set value="contestType" var="contestType"
                                                   scope="page"/>
                                            <tr>

                                                <td class="first"><s:property value="contestType.name"/></td>
                                                <td>
                                                    <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                        <img src="/images/<s:property value="contestType.letter"/>_small.png"
                                                             alt="<s:property value="contestType.letter"/>"/>

                                                    </div>
                                                    <div style="display:table-cell;text-align:left">
                                                        <link:contestDetails contest="${contest}"
                                                                />
                                                    </div>
                                                </td>
                                                                                                                       
                                                <td>
                                                    <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${startTime}"/> ET (GMT-400)
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${endTime}"/> ET (GMT-400)
                                                </td>
                                                    
                                                <td>
                                                    <a href="contest/contestRegistrants.action?projectId=${contest.id}">
                                                        <s:property value="registrantsNumber"/>
                                                    </a></td>
                                                <td>
                                                    <if:isStudioContest contestBrief="${contest}">
                                                        <link:studioSubmissionsGrid contestId="${contest.id}">
                                                            <s:property value="submissionsNumber"/>
                                                        </link:studioSubmissionsGrid>
                                                    </if:isStudioContest>
                                                    <if:isStudioContest negate="true" contestBrief="${contest}">
                                                        <link:softwareSubmissionsList contestId="${contest.id}">
                                                            <s:property value="submissionsNumber"/>
                                                        </link:softwareSubmissionsList>
                                                    </if:isStudioContest>
                                                </td>
                                                <td>
                                                <s:if test="forumId != -1">
                                                    <s:if test="isStudio == true"><a href="https://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                    <s:if test="isStudio == false"><a href="https://apps.topcoder.com/forums/?module=Category&categoryID=${forumId}" target="_blank"></s:if>
                                                </s:if>
                                                <s:property value="forumPostsNumber"/>
                                                <s:if test="forumId != -1"></a></s:if>
                                                </td>
                                                <td><span
                                                        class="<s:property value="status.shortName"/>"><s:property
                                                        value="status.name"/></span></td>
                                                <td class="last">
                                                    <s:if test="contestType.name == 'Copilot Posting'">
                                                        <a href="copilot/copilotContestDetails?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                    </s:if>
                                                    <s:if test="contestType.name != 'Copilot Posting'">
                                                        <a href="contest/detail?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                     </s:if>
                                                     <%// repost if it is software contest and it is cancelled status %>
                                                     <s:if test="!isStudio && status.name.startsWith('Cancelled')">
                                                        <a href="javascript:repostHandler(${contest.id},${contest.project.id});" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">Repost</span></span></span></a>
                                                     </s:if>
                                                     <%// new version if it is component design/dev contest and it is completed status %>
                                                     <s:if test="(contestType.toString() == 'COMPONENT_DESIGN' || contestType.toString() == 'COMPONENT_DEVELOPMENT') && status.name == 'Completed'">
                                                        <a href="javascript:newVersionHandler(${contest.id},${contest.project.id},<s:property value="contestType.toString() == 'COMPONENT_DESIGN'" />);" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">New Version</span></span></span></a>
                                                     </s:if>
                                                </td>
                                            </tr>
                                        </s:iterator>

                                        <s:iterator value="viewData.projectBugRaces"
                                                    status="status">
                                            <tr>

                                                <td class="first">Bug Race</td>
                                                <td>
                                                    <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                        <img src="/images/br_small.png"
                                                             alt="BR"/>

                                                    </div>
                                                    <div style="display:table-cell;text-align:left">
                                                        <a href="<s:property value='issueLink'/>"><s:property value='issueKey'/> <s:property value='title'/></a>
                                                    </div>
                                                </td>

                                                <td>
                                                    <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${creationDate}"/> ET (GMT-400)
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${endDate}"/> ET (GMT-400)
                                                </td>

                                                <td>
                                                    <a href="<s:property value='issueLink'/>">
                                                        <s:property value="votesNumber"/>
                                                    </a></td>
                                                <td>
                                                    n/a
                                                </td>
                                                <td>
                                                    <a href="<s:property value='issueLink'/>">0</a>
                                                </td>
                                                <td><span
                                                        class="<s:property value="contestLikeStatusClass"/>"><s:property
                                                        value="contestLikeStatus"/></span></td>
                                                <td class="last">
                                                     <a href="<s:property value='issueLink'/>" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                </td>
                                            </tr>
                                        </s:iterator>


                                        </tbody>
                                    </table>
                                    <!-- End .projectsStats -->
                                </div>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">

                                                        <div class="panel copilotPostingsPanel"></div>

                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>          
                            </div>

                              <span id="calendarToday" class="hide"><s:date name="calendarToday" format="MM/dd/yyyy"/></span>
                              <div class="contestCView hide">
                                <div class="loading">
                                    <img src="/images/loadingAnimation.gif" alt=""/>
                                </div>
                                <div class="calendar"></div>
                            </div>

                          </div>
                            <!-- End .container2 -->
                        </div>
                    </div>
                </div>

<div class="clear"></div>


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
