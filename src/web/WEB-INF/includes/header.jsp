<%--
  - Author: isv, tangzx, Veve, winsty, Blues, GreatKevin, bugbuka
  - Version: 1.6.7
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment is to be included to all pages from TC Direct application.
  - It renders the common page headers.
  -
  - Version 1.1 (Direct Permissions Setting Back-end and Integration Assembly 1.0) changes: added Permissions tab for
  - dahsboard pages.
  - Version 1.2 (Direct Pipeline Integration Assembly 1.0) changes: added Reports tab.
  - Version 1.3 (Direct Enterprise Dashboard Assembly 1.0) changes: added Overview tab for dashboard pages.  
  - Version 1.4 (TC Direct - Launch Copilot Selection Contest assembly) changes: add copilot tab.
  - Version 1.5 (TC Direct Manage Copilots Assembly) changes: update copilot manage sub tab.
  - Version 1.5.1 (Direct Release 6 assembly) changes: fixed styles for Hello User handle.
  - Version 1.5.2 (Manage Copilot Postings assembly) change notes: linked "My Copilot Selection Contests" tab to "Manage Copilot Postings" page.
  - version 1.5.3 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking assembly) changes notes: linked "Issue Tracking" tab
  - Version 1.5.4 (TC Direct UI Improvement Assembly 1) changes notes: Solve '"Project" tab is not highlight when select Project'
  - version 1.5.5 (Release Assembly - TC Cockpit Sidebar Header and Footer Update) changes notes: add link to topcoder community site in header
  - Version 1.6 (Release Assembly - TopCoder Cockpit Modal Windows Revamp) changes note: add include to the new modal window template jsp
  - Version 1.6.1 (Module Assembly - Project Contest Fee Management) changes note: add a contest fee tab.
  - Version 1.6.2 (Release Assembly - Project Contest Fee Management) changes note: move contest fee into settings tab
  - Version 1.6.3 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0) : add edit project button
  - Version 1.6.4 (Module Assembly - TC Cockpit Project Milestones Management Front End) : add the milestone tab for project
  - Version 1.6.5 (Module Assembly - TC Cockpit Project Contests Batch Edit ): add the dropdown menu for the contests tab in the project section
  - Version 1.6.6 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part): add the new enterprise dashboard
  - Version 1.6.7 (Module Assembly - TC Cockpit Operations Dashboard For PMs) changes note: add the new Operations DashBoard.
--%>
<%@ page import="com.topcoder.direct.services.view.action.cloudvm.DashboardVMAction" %>
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

	<!-- topcoder maintenance module -->
	<div id="topcoder-maintenance-notification">
		<div class="content">
			<div class="alert">
				<span class="title">TopCoder System Maintenance</span>
				<span class="desc">
					TopCoder will be performing a network security upgrade on Tuesday, September 4th between 20:00 EDT (GMT/UTC -4) and 22:00 EDT (GMT/UTC -4).
					During these times, you may experience some sporadic system outages.
					We apologize for any inconvenience that this may cause.

				</span>
			</div>
			<div class="noAsk">
            <a class="close" href="javascript:;">Close</a>
            <div class="clear"></div>
            <div class="noAakInner"><input type="checkbox" id="noAskMaintenanceAgain"><label for="noAskMaintenanceAgain">Don�t show again</label></div>
			</div>

		</div>
	</div>
<!-- topcoder maintenance module ends -->

<div id="header">
    <c:choose>
        <c:when test="${requestScope.PAGE_TYPE eq 'dashboard'}">
            <c:choose>
                <c:when test="${requestScope.CURRENT_TAB eq 'overview'}">
                    <a href="javascript:;" class="logo overviewLogo">Dashboard</a>
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'enterprise'}">
                    <s:if test="viewData.resultType.name() == 'PM_PROJECTS'">
                        <a class="logo" href="javascript:;" style="left: 25px; top: 60px"> <img src="/images/all_projects_ico.png" alt="Operations DashBoard" class="projectTitle"/><span id="projectTitleSpan">Operations DashBoard</span></a>
                    </s:if>
                    <s:else>
                        <a class="logo" href="javascript:;"> <img alt="Enterprise Dashboard" src="/images/enterprise_dashboard_logo.png"></a>
                    </s:else>
                </c:when>
                <c:otherwise>
                    <a href="<s:url action="dashboardActive" namespace="/"/>" class="logo">
                        <img src="/images/dashboard_logo.png" alt="Dashboard" /></a>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'launch'}">
            <a href="javascript:;" class="logo">
                <img src="/images/launghContent_logo.png" alt="Launch Contest" /></a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'copilot'}">
            <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>" class="logo">
                <img src="/images/copilot_logo.png" alt="Laungh Copilot Contest" class="copilotTitle"/>
                <span>TopCoder Copilots</span>
            </a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'internal'}">
        </c:when>
    <c:otherwise>
    <s:if test="viewData.isAllProjectsPage == true">
    <a href="<s:url action='allProjects' namespace='/'/>" class="logo" style="left: 25px; top: 60px">
        <img src="/images/all_projects_ico.png" alt="Projects" class="projectTitle"/>
        <span id="projectTitleSpan"> All Projects</span>
    </a>
    </s:if>
    <s:else>
    <a href="<s:url action='projectOverview' namespace='/'><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>" class="logo"
       style="float:left; position:relative;">
        <img src="/images/project_logo.png" alt="Projects" class="projectTitle"/>
        <span id="projectTitleSpan"> <s:property value="sessionData.currentProjectContext.name"/></span>
        <c:if test="${requestScope.CURRENT_TAB != 'editProject'}">
            <a href='<s:url action="editProject" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>'
               class="editIcon"></a>
        </c:if>
    </a>
    </s:else>
    </c:otherwise>
    </c:choose>

    <div id="tabs0"><!-- the left tabs -->
        <ui:isDashboardPage>
            <ul>
                <li class="on">
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>
                
                    <div class="subNav">
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                        </c:if>
                    </div>
                </li>
                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>

                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <c:if test="${tcdirect:isScorecardAdmin()}">
                    <li>
                        <a href="/direct/scorecard/"><span>Scorecards</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}">
                    <li>
                        <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                    </li>
                </c:if>

                <c:if test="${tcdirect:isTCStaff()}">
                    <li>
                        <a href="javascript:;"><span>Admin</span></a>
                        <div class="subNav">
                            <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback</a>
                            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                                <a href="javascript:">Manage Contest Fee</a>
                            </c:if>
                            <a href="http://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </ui:isDashboardPage>
        <ui:isProjectPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>
                
                    <div class="subNav">
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                        </c:if>
                    </div>
                </li>

                <li class="on">
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <c:if test="${tcdirect:isScorecardAdmin()}">
                    <li>
                        <a href="/direct/scorecard/"><span>Scorecards</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}">
                    <li>
                        <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                    </li>
                </c:if>

                <c:if test="${tcdirect:isTCStaff()}">
                    <li>
                        <a href="javascript:;"><span>Admin</span></a>
                        <div class="subNav">
                            <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback</a>
                            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                                <a href="javascript:">Manage Contest Fee</a>
                            </c:if>
                            <a href="http://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </ui:isProjectPage>
        <ui:isCopilotPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>

                    <div class="subNav">
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                        </c:if>
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li class="on">
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>

                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <c:if test="${tcdirect:isScorecardAdmin()}">
                    <li>
                        <a href="/direct/scorecard/"><span>Scorecards</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}">
                    <li>
                        <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                    </li>
                </c:if>
                <!--
                <li><a href="#"><span>Messages (0)</span></a></li>
                -->
                <c:if test="${tcdirect:isTCStaff()}">
                    <li>
                        <a href="javascript:;"><span>Admin</span></a>
                        <div class="subNav">
                            <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback</a>
                            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                                <a href="javascript:">Manage Contest Fee</a>
                            </c:if>
                            <a href="http://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </ui:isCopilotPage>
        <ui:isInternalPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>

                    <div class="subNav">
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                        </c:if>
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <c:if test="${tcdirect:isScorecardAdmin()}">
                    <li>
                        <a href="/direct/scorecard/"><span>Scorecards</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}">
                    <li class="on">
                        <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:isTCStaff()}">
                    <li>
                        <a href="javascript:;"><span>Admin</span></a>
                        <div class="subNav">
                            <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback</a>
                            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                                <a href="javascript:">Manage Contest Fee</a>
                            </c:if>
                            <a href="http://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </ui:isInternalPage>
        <c:if test="${requestScope.PAGE_TYPE eq 'launch'}">
            <ul>
                <li class="on">
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>

                    <div class="subNav">
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                        </c:if>
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <c:if test="${tcdirect:isScorecardAdmin()}">
                    <li>
                        <a href="/direct/scorecard/"><span>Scorecards</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}">
                    <li>
                        <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                    </li>
                </c:if>
                <c:if test="${tcdirect:isTCStaff()}">
                    <li>
                        <a href="javascript:;"><span>Admin</span></a>
                        <div class="subNav">
                            <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback</a>
                            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                                <a href="javascript:">Manage Contest Fee</a>
                            </c:if>
                            <a href="http://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </c:if>
    </div>
    <!-- End #tabs0 -->

	<div class="helloUser">
        <ul>
            <li>
                <strong>Hello</strong> <link:currentUser/>|
            </li>
            <li class="helloUserLink"><a href="<s:url action="logout" namespace="/"/>">Logout</a>|</li>
            <li class="helloUserLink"><link:help/></li>
        </ul>
    </div><!-- End .helloUSer -->

    <%-- link to community site --%>

	<div>
		<!-- TC Logo -->
		<a onclick="window.open('https://www.topcoder.com/');" href="javascript:;" class="TCLogo"><img src="/images/tc-logo.png" alt="TopCoder"></a>
		<p class="lookCP dashBoardLookCP">Looking for Community Portal?
        <a onclick="window.open('https://community.topcoder.com/');" href="javascript:;"><strong>Go There Now</strong></a>
    </p>
	</div>


    <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">
		<s:set name="projId" value="viewData.projectStats.project.id"/>  
	</c:if>
	<c:if test="${requestScope.CURRENT_TAB eq 'contests'}">
		<s:set name="projId" value="viewData.contestStats.contest.project.id"/>  
	</c:if>	
	
    <ui:isProjectPage>
        <c:if test="${requestScope.CURRENT_TAB ne 'allProjects'}">
                <div id="tabs1">
                     <ul>
                        <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="on"</c:if>>
                            <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>">
                                <span>Overview</span>
                            </a>
                        </li>
                        <li <c:if test="${requestScope.CURRENT_TAB eq 'milestone'}">class="on"</c:if>>
                             <a id="tabProjectMilestone" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/> <s:param name="formData.viewType" >list</s:param></s:url>">
                                 <span>Project Milestones</span>
                             </a>
                         </li>

                        <li <c:if test="${requestScope.CURRENT_TAB eq 'gameplan'}">class="on"</c:if>>
                            <a href="<s:url action="ProjectGamePlanView" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>"><span>Game Plan</span></a>
                        </li>

                        <li id="contest" style="position: relative;" <c:if test="${requestScope.CURRENT_TAB eq 'contests'}">class="on"</c:if>>
                            <a href="javascript:;">
                                <span>Contests<span class="arrow"></span></span>
                            </a>
                            <div class="dropDwnLst">
                                <div class="section">
                                    <h3>View Contests</h3>
                                    <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>">List View</a>
                                    <a href="<s:url action="projectContestsCalendar" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>">Calendar View</a>
                                    <a href="<s:url action="ProjectGamePlanView" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>">Game Plan View</a>
									 <a href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>">New Game Plan View (Beta)</a>
                                </div>
                                <div class="section">
                                    <h3>Create Contest</h3>
                                    <a href="<s:url action="home" namespace="/launch"></s:url>">Launch New Contest</a>
                                  <!--  <a href="javascript:;">Quick Create Draft Contest</a>
                                    <a href="javascript:;">Bulk Creation</a>-->
                                </div>
                                <div class="section">
                                    <h3>Edit Contest</h3>
                                    <a href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>">Bulk Edit Draft Contests</a>
                                </div>
                            </div>
                        </li>

                         <li <c:if test="${requestScope.CURRENT_TAB eq 'issues'}">class="on"</c:if>>
                            <a href="<s:url action="projectIssueTracking" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>"><span>Issue Tracking</span></a>
                        </li>
                    </ul>
                </div>
        </c:if>
    </ui:isProjectPage>

    <ui:isDashboardPage>
        <c:if test="${requestScope.CURRENT_TAB ne 'enterprise'}">
            <div id="tabs1">
                 <ul>
                     <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="on"</c:if>>
                         <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span class="dashboardSpan">Overview</span></a>
                     </li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'active'}">class="on"</c:if>>
                        <a href="<s:url action="dashboardActive" namespace="/"/>"><span class="dashboardSpan">Active Contests</span></a>
                    </li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'dashboard'}">class="on"</c:if>>
                        <a href="<s:url action="calendar" namespace="/"/>"><span class="dashboardSpan">Roadmap</span></a>
                    </li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'latest'}">class="on"</c:if>>
                        <a href="<s:url action="dashboardLatest" namespace="/"/>"><span class="dashboardSpan">Latest Activities</span></a>
                    </li>
                 </ul>
            </div>
        </c:if>
     </ui:isDashboardPage>
     
     <ui:isCopilotPage>
        <div id="tabs1" class="copilotsTabs1">
            <ul>
                
                <li <c:if test="${requestScope.CURRENT_TAB eq 'launchCopilot'}">class="on"</c:if>><a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Get a Copilot</span></a></li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'copilotPostings'}">class="on"</c:if>>
                    <a href="<s:url action='listCopilotPostings' namespace='/copilot'/>"><span>My Copilot Postings</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'manageCopilots'}">class="on"</c:if>><a href="<s:url action='manageCopilots' namespace='/copilot'/>"><span>Manage Copilots</span></a></li>
            </ul>
        </div>
     </ui:isCopilotPage>
    
    <div id="tabs2"><!-- tabs on the right side-->
        <ul>
          
            <li <c:if test="${requestScope.CURRENT_TAB eq 'search'}">class="on"</c:if>>
                <a href="<s:url action="dashboardSearchView" namespace="/"/>"><span>Search</span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'settings'}">class="on"</c:if>>
                <a href="<s:url action="dashboardNotifications" namespace="/"/>"><span>Settings</span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'reports'}">class="on"</c:if>>
                <a href="<s:url action="dashboardReports" namespace="/"/>"><span>Reports</span></a>
            </li>

            <%
                if (DashboardVMAction.isApplicable()) {
            %>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'VM Management'}">class="on"</c:if>>
                    <a href="<s:url action="dashboardVMAction" namespace="/"/>"><span>VM Management</span></a>
                </li>
            <%
                }
            %>
        </ul>
    </div><!-- End #tabs2 -->
</div><!-- End #header -->
<c:if test="${requestScope.CURRENT_TAB ne 'enterprise'}">
    <jsp:include page="modalWindows.jsp"/>
</c:if>
