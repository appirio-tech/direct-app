<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%--
  - Author: BeBetter, isv, tangzx, Blues, Ghost_141, GreatKevin
  - Version: 2.1
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
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
  -
  - Version 1.7 (Module Assembly - TC Cockpit Project Contests Batch Edit) changes:
  - Move contests calendar view to a seperate jsp page.
  -
  - Version 1.8 (Release Assembly - TopCoder Cockpit Project Contest Results Export Part 1 version 1.0) changes:
  - Add a new panel for exporting project contest results.
  - 
  - Version 1.9 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - Fix a text inconsistency bug.
  -
  - Version 2.0 (TC Direct Rebranding Assembly Project and Contest related pages)
  - - Rebranding the page to use new buttons.
  -
  - Version 2.1 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
  - - Update forum link for studio contest
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="gameplan"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <jsp:include page="includes/filterPanel.jsp"/>
    <script type="text/javascript" src="/scripts/repostcontest.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/export-panel.js"></script>
    <link rel="stylesheet" href="/css/direct/exportPanel.css" media="all" type="text/css" />
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                     <div id="area1"><!-- the main area -->
                        <div class="area1Content" style="position: relative;">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                                <span id="currentDirectProjectID" class="hide"><s:property value="sessionData.currentProjectContext.id"/></span>
                            </div>

                            <div class="batchButtons">
                                <!--<a class="batchCreate" href="javascript:;">Batch Create</a>-->
                                <a class="batchEdit" href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Batch Edit</a>
                            </div>

                          <div class="contestViews">
                            <div class="areaHeader">

                                <div class="viewBtns">
                                    <a href="javascript:;" class="listViewBtn active">
                                        <span>List View</span>
                                    </a>
                                    <a href="<s:url action="projectContestsCalendar" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>" class="calendarViewBtn">
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
                                                    <span class='title'>Challenge Status</span>
                                                    <select id='contestStatusFilter'>
                                                        <option value='All'>All Challenge Status</option>
                                                    </select>
                                                </div>
                                                <div class='row'>
                                                    <span class='title'>Challenge Type</span>
                                                    <select id='contestTypeFilter'>
                                                        <option value='All'>All Challenge Types</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <!--end .column1-->
                                            <div class='column3'>
                                                <div class='row'>
                                                    <span class='title dateLabel'>Start Date</span>
                                                    <input id='startDateBegin' type='text' readonly="readonly" class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='startDateEnd' type='text' readonly="readonly" class='date-pick'/>
                                                </div>
                                                <div class='row'>
                                                    <span class='title dateLabel'>End Date</span>
                                                    <input id='endDateBegin' type='text' readonly="readonly" class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='endDateEnd' type='text' readonly="readonly" class='date-pick'/>
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

                                            <th>Challenge Type</th>
                                            <th>Challenge Name</th>
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
                                                             alt="<s:property value="contestType.letter"/>" class="contestTypeIcon"/>

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
                                                    <s:if test="isStudio == true && !newForum"><a href="https://<%=ServerConfiguration.STUDIO_FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                    <s:if test="isStudio == true && newForum"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                    <s:if test="isStudio == false"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=Category&categoryID=${forumId}" target="_blank"></s:if>
                                                </s:if>
                                                <s:property value="forumPostsNumber"/>
                                                <s:if test="forumId != -1"></a></s:if>
                                                </td>
                                                <td><span
                                                        class="<s:property value="status.shortName"/>"><s:property
                                                        value="status.name"/></span></td>
                                                <td class="last" style="text-align: left; padding-left:16px">
                                                    <s:if test="contestType.name == 'Copilot Posting'">
                                                        <a href="copilot/copilotContestDetails?projectId=${contest.id}" class="buttonEdit">View/Edit</a>
                                                    </s:if>
                                                    <s:if test="contestType.name != 'Copilot Posting'">
                                                        <a href="contest/detail?projectId=${contest.id}" class="buttonEdit">View/Edit</a>
                                                     </s:if>
                                                     <%// repost if it is software contest and it is cancelled status %>
                                                     <s:if test="status.name.startsWith('Cancelled')">
                                                        <a href="javascript:repostHandler(${contest.id},${contest.project.id},${contestType.id});" class="buttonEdit">Repost</a>
                                                     </s:if>
                                                     <%// new version if it is component design/dev contest and it is completed status %>
                                                     <s:if test="(contestType.toString() == 'COMPONENT_DESIGN' || contestType.toString() == 'COMPONENT_DEVELOPMENT') && status.name == 'Completed'">
                                                        <a href="javascript:newVersionHandler(${contest.id},${contest.project.id},<s:property value="contestType.toString() == 'COMPONENT_DESIGN'" />);" class="buttonEdit">New Version</a>
                                                     </s:if>
                                                </td>
                                            </tr>
                                        </s:iterator>

                                        <s:iterator value="viewData.projectBugRaces"
                                                    status="status">
                                            <tr>

                                                <td class="first">Race</td>
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
                                                     <a href="<s:property value='issueLink'/>" class="buttonEdit">View/Edit</a>
                                                </td>
                                            </tr>
                                        </s:iterator>


                                        </tbody>
                                    </table>
                                    <!-- End .projectsStats -->
                                </div>
                                
                          </div>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div>
                                                <div>

                                                        <div class="panel copilotPostingsPanel"></div>

                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>          
                            </div>

                            <!-- the contest result export panel -->
                            <s:if test="#session.show_contests_download">
                            <div class='exportPanel' id='ExportContestsPanel'>
                                <div class='exportHead'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='exportText'>
                                                <a href='javascript:;' class='exportCollapse' id='ExportContestsExpand'><img
                                                        src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                <span class='title'>Challenge Results Export</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end .exportHead-->

                                <div class='exportContent'>
                                    <form id="exportProjectResultForm" action="exportResults">
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='column1'>
                                                <div class='row'>
                                                    <span class='title'>Challenge Completion Date:</span>
                                                </div>
                                            </div>
                                            <!--end .column1-->
                                            <div class='column2'>
                                                <div class='row'>
                                                    <input type="hidden" name="projectId" id="exportProjectId" value='<s:property value="sessionData.currentProjectContext.id"/>'/>
                                                    <span class='title dateLabel'>Start Date</span>
                                                    <input id='exportDateBegin' name="startDate" type='text' class='date-pick'/>
                                                    <span class='title dateLabel'>End Date</span>
                                                    <input id='exportDateEnd' name="endDate" type='text' class='date-pick'/>
                                                </div>
                                            </div>
                                            <!--end .column2-->
                                            <div class="column3">
                                                <div class="row">
                                                    <a href="javascript:;" class="button6" id="exportBtn">
                                                      <span class="left">
                                                         <span class="right">Export</span>
                                                      </span>
                                                    </a>
                                                </div>
                                            </div>
                                            <!--end .column3-->
                                        </div>
                                    </div>
                                    </form>
                                </div>
                                <!--end .exportHead-->
                                <div class='exportBottom'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                                <!--end .exportBottom-->
                                <div class='collapseBottom hide'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                            </div>
                            </s:if>
                        </div>

                            </div>


                          <!-- End .container2 -->
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
