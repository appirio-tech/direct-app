<%--
  - Author: isv, tangzx, Veve, winsty, Blues, GreatKevin, TCSASSEMBLER
  - Version: 1.6.4
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
--%>
<%@ page import="com.topcoder.direct.services.view.action.cloudvm.DashboardVMAction" %>
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="header">
    <c:choose>
        <c:when test="${requestScope.PAGE_TYPE eq 'dashboard'}">
            <c:choose>
                <c:when test="${requestScope.CURRENT_TAB eq 'overview'}">
                    <a href="javascript:;" class="logo overviewLogo">Dashboard</a>
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
    <a href="<s:url action='currentProjectOverview' namespace='/'><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>" class="logo"
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
                    <a href="<s:url action="dashboardActive" namespace="/"/>"><span>Dashboard</span></a>
                </li>
				<li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

		        <c:if test="${tcdirect:isScorecardAdmin()}" >
		        <li>
                     <a href="/direct/scorecard/"><span>Scorecards</span></a>
                </li>
                </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}" >
		        <li>
                    <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                </li>
                </c:if>
            </ul>
        </ui:isDashboardPage>
        <ui:isProjectPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardActive" namespace="/"/>"><span>Dashboard</span></a>
                </li>

				<li class="on">
                     <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

		<c:if test="${tcdirect:isScorecardAdmin()}" >
		   <li>
                      <a href="/direct/scorecard/"><span>Scorecards</span></a>
                   </li>
               </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}" >
		        <li>
                    <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                </li>
                </c:if>
            </ul>
        </ui:isProjectPage>
	<ui:isCopilotPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardActive" namespace="/"/>"><span>Dashboard</span></a>
                </li>

                <li>
                     <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li class="on">
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

		<c:if test="${tcdirect:isScorecardAdmin()}" >
		   <li>
                      <a href="/direct/scorecard/"><span>Scorecards</span></a>
                   </li>
               </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}" >
		        <li>
                    <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                </li>
                </c:if>
                <!--
                <li><a href="#"><span>Messages (0)</span></a></li>
                -->
            </ul>
        </ui:isCopilotPage>
        <ui:isInternalPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardActive" namespace="/"/>"><span>Dashboard</span></a>
                </li>

                <li>
                     <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

		<c:if test="${tcdirect:isScorecardAdmin()}" >
		   <li>
                      <a href="/direct/scorecard/"><span>Scorecards</span></a>
                   </li>
               </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}" >
		        <li class="on">
                    <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                </li>
                </c:if>
            </ul>
        </ui:isInternalPage>
        <c:if test="${requestScope.PAGE_TYPE eq 'launch'}">
            <ul>
                <li class="on">
                    <a href="<s:url action="dashboard" namespace="/"/>"><span>Dashboard</span></a>
                </li>

				<li>
                     <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>
                </li>

                <li>
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

		<c:if test="${tcdirect:isScorecardAdmin()}" >
		   <li>
                      <a href="/direct/scorecard/"><span>Scorecards</span></a>
                   </li>
               </c:if>
                <c:if test="${tcdirect:canViewInternalStats()}" >
		        <li>
                    <a href="<s:url action="internalStats" namespace="/"/>"><span>Internal</span></a>
                </li>
                </c:if>
            </ul>
        </c:if>
    </div><!-- End #tabs0 -->

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

                        <li <c:if test="${requestScope.CURRENT_TAB eq 'contests'}">class="on"</c:if>>
                            <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>">
                                <span>Contests</span>
                            </a>
                        </li>

                         <li <c:if test="${requestScope.CURRENT_TAB eq 'issues'}">class="on"</c:if>>
                            <a href="<s:url action="projectIssueTracking" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>"><span>Issue Tracking</span></a>
                        </li>
                    </ul>
                </div>
        </c:if>
    </ui:isProjectPage>

    <ui:isDashboardPage>
        <div id="tabs1">
             <ul>
                 <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="on"</c:if>>
                     <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span class="dashboardSpan">Overview</span></a>
                 </li>
				<li <c:if test="${requestScope.CURRENT_TAB eq 'active'}">class="on"</c:if>>
                    <a href="<s:url action="dashboardActive" namespace="/"/>"><span class="dashboardSpan">Active Contests</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'dashboard'}">class="on"</c:if>>
                    <a href="<s:url action="calendar" namespace="/"/>"><span class="dashboardSpan">Calendar</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'latest'}">class="on"</c:if>>
                    <a href="<s:url action="dashboardLatest" namespace="/"/>"><span class="dashboardSpan">Latest Activities</span></a>
                </li>
             </ul>
        </div>
            
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

<jsp:include page="modalWindows.jsp"/>
