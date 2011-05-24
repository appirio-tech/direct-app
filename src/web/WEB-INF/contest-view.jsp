<%--
  - Author: BeBetter, isv, TCSASSEMBLER
  - Version: 1.5
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
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="contests"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/repostcontest.js?v=4"></script>
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
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Contests</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/project/projectStats.jsp"/>

                            <div class="container2"  id="ProjectContests">
                                <div class="myProjectContests">                                    
                                    <table id="ProjectContests" class="projectStats contests paginatedDataTable" width="auto"
                                           cellpadding="0"
                                           cellspacing="0">
                                            
                        
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
                                                    <s:if test="isStudio == true">
                                                    <a href="contest/contestRegistrants.action?contestId=${contest.id}">
                                                    </s:if>
                                                    <s:if test="isStudio == false">
                                                    <a href="contest/contestRegistrants.action?projectId=${contest.id}">
                                                    </s:if>
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
                                                    <s:if test="isStudio == true"><a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                    <s:if test="isStudio == false"><a href="http://forums.topcoder.com/?module=Category&categoryID=${forumId}" target="_blank"></s:if>
                                                </s:if>
                                                <s:property value="forumPostsNumber"/>
                                                <s:if test="forumId != -1"></a></s:if>
                                                </td>
                                                <td><span
                                                        class="<s:property value="status.shortName"/>"><s:property
                                                        value="status.name"/></span></td>
                                                <td class="last">
                                                    <s:if test="isStudio == true">
                                                        <a href="contest/detail?contestId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                     </s:if>
                                                     <s:if test="isStudio == false">
                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                            <a href="copilot/copilotContestDetails?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                        </s:if>
                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                            <a href="contest/detail?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                        </s:if>
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
                            <!-- End .container2 -->
                        </div>
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
