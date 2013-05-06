<%--
  - Author: isv, tangzx, Veve, winsty, Blues, GreatKevin, bugbuka, leo_lol, xjtufreeman
  - Version: 2.7
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
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
  - Version 1.6.8 (Module Assembly - TC Client Users Stats Report Generation ) changes note: add the new User Stats DashBoard.
  - Version 1.7 (Release Assembly - TopCoder Security Groups Frontend - View Group Details) : add tabs for security groups
  - Version 1.8 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) :
  -   updated tabs for Pending Approvals and Received Invitations 
  - Version 1.9 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) :
  -   updated link for view user groups tabs 
  - Version 2.0 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) :
  -   updated link for create customer administrator, send invitation, audit information tabs
  - Version 2.1 (Release Assembly - TopCoder Security Groups - Release 2) change notes: made the UI related to security
  -   groups displayed if user is granted a permission to access security groups UI
  - Version 2.2 (https://apps.topcoder.com/bugs/browse/TCCC-4704) change notes: add the 'Report' permission for group security
  - Version 2.3 (Release Assembly - TopCoder Security Groups - Release 4) change notes: Add link to the top icon in group related pages.
  - Version 2.4 (Release Assembly - TopCoder Security Groups - Release 5) change notes:
  - - Added hasWritePermission variable if current page is in project context.
  - - For the pages under project context, hide some edit project links if user doesn't have permission.
  - Version 2.5 (Module Assembly - TopCoder Cockpit Instant Search) change notes: Add instant search box.
  - Version 2.6 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Change the cursor style for header logo 
  - Version 2.7 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph) change notes:
  - - Add link to TopCoder Platform Specialists report, the report can only be accessed by TC staff.
  - Version 2.8 (Release Assembly - TopCoder Cockpit - Billing Management) change notes: add billing tab
  - Version 2.9 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0) change notes: add member payment item
--%>
<%@ page import="com.topcoder.direct.services.view.action.cloudvm.DashboardVMAction" %>
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ page import="com.topcoder.direct.services.view.action.accounting.ClientInvoiceManageAction" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

	<!-- topcoder maintenance module -->
	<div id="topcoder-maintenance-notification">
		<div class="content">
			<div class="alert">
				<span class="title">TopCoder System Maintenance</span>
				<span class="desc">
					TopCoder will be performing a network upgrade on Thursday, March 7th between 19:00 EDT (GMT/UTC -4) and 22:00 EDT (GMT/UTC -4).
					During these times, you may experience some sporadic system outages.
					We apologize for any inconvenience that this may cause.

				</span>
			</div>
			<div class="noAsk">
            <a class="close" href="javascript:;">Close</a>
            <div class="clear"></div>
            <div class="noAakInner"><input type="checkbox" id="noAskMaintenanceAgain"><label for="noAskMaintenanceAgain">Don't show again</label></div>
			</div>

		</div>
	</div>
<!-- topcoder maintenance module ends -->

<s:url var="viewUserGroupsUrl" action="viewUserGroupsAction" namespace="/group">
    <s:param name="criteria.permissions" value="{'REPORT', 'READ','WRITE','FULL'}"/>
</s:url>
    
<div id="header">
    <c:choose>
        <c:when test="${requestScope.PAGE_TYPE eq 'dashboard'}">
            <c:choose>
                <c:when test="${requestScope.CURRENT_TAB eq 'overview'}">
                    <a href="javascript:;" class="logo overviewLogo">Dashboard</a>
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'enterprise'}">
                    <s:if test="viewData.resultType.name() == 'PM_PROJECTS'">
                        <a class="logo" href="javascript:;" style="left: 25px; top: 60px; cursor:default"> <img src="/images/all_projects_ico.png" alt="Operations DashBoard" class="projectTitle"/><span id="projectTitleSpan">Operations DashBoard</span></a>
                    </s:if>
                    <s:else>
                        <a class="logo" href="javascript:;" style="cursor:default"> <img alt="Enterprise Dashboard" src="/images/enterprise_dashboard_logo.png"></a>
                    </s:else>
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'platformSpecialistsReport'}">
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'searchAll'}">
                    <a class="logo resultsPageLogo" href="javascript:;"> Search Results </a>
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
        <c:when test="${requestScope.PAGE_TYPE eq 'group'}">
            <a href="${viewUserGroupsUrl}" class="logo">Groups</a>
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
    <c:set var="hasWritePermission" value="${tcdirect:hasWriteProjectPermission(session.currentSelectDirectProjectID)}" scope="request"/>
    <a href="<s:url action='projectOverview' namespace='/'><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>" class="logo"
       style="float:left; position:relative;">
        <img src="/images/project_logo.png" alt="Projects" class="projectTitle"/>
        <span id="projectTitleSpan"> <s:property value="sessionData.currentProjectContext.name"/></span>
        <c:if test="${requestScope.CURRENT_TAB != 'editProject' and hasWritePermission}">
            <a href='<s:url action="editProject" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'
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
                    <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>"><span>Dashboards</span></a>
                
                    <div class="subNav">
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                            <a href="<s:url action="platformSpecialistsReport" namespace="/"/>">Platform Specialists Report</a>
                        </c:if>
                        <c:if test="${tcdirect:isTCAccounting()}">
                            <a href="<s:url action="overview" namespace="/payments"/>">Member Payments Dashboard</a>
                        </c:if>                        
                    </div>
                </li>
                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>

                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>

                <if:isSecurityGroupsUIAccessible>
                    <li>
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
                </if:isSecurityGroupsUIAccessible>
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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
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
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                        </c:if>
                        <c:if test="${tcdirect:isTCAccounting()}">
                            <a href="<s:url action="overview" namespace="/payments"/>">Member Payments Dashboard</a>
                        </c:if> 
                    </div>
                </li>

                <li class="on">
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>
 
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>
                
                <if:isSecurityGroupsUIAccessible>
                    <li>
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
                </if:isSecurityGroupsUIAccessible>

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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
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
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                        </c:if>
                        <c:if test="${tcdirect:isTCAccounting()}">
                            <a href="<s:url action="overview" namespace="/payments"/>">Member Payments Dashboard</a>
                        </c:if> 
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>
 
                </li>

                <li class="on">
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>

                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>
                <if:isSecurityGroupsUIAccessible>
                    <li>
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
                </if:isSecurityGroupsUIAccessible>
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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
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
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                        </c:if>
                        <c:if test="${tcdirect:isTCAccounting()}">
                            <a href="<s:url action="overview" namespace="/payments"/>">Member Payments Dashboard</a>
                        </c:if> 
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>
 
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>
                <if:isSecurityGroupsUIAccessible>
                    <li>
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
                </if:isSecurityGroupsUIAccessible>
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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
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
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                        </c:if>
                        <c:if test="${tcdirect:isTCAccounting()}">
                            <a href="<s:url action="overview" namespace="/payments"/>">Member Payments Dashboard</a>
                        </c:if> 
                    </div>
                </li>

                <li>
                    <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>
 
                </li>

                <li>
                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                    <div class="subNav">
                        <a href="<s:url action="launchCopilotContest" namespace="/copilot"/>">Get a Copilot</a>
                        <a href="<s:url action="listCopilotPostings" namespace="/copilot"/>">My Copilot Postings</a>
                        <a href="<s:url action="manageCopilots" namespace="/copilot"/>">Manage Copilots</a>
                    </div>
                </li>
                <if:isSecurityGroupsUIAccessible>
                    <li>
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
                </if:isSecurityGroupsUIAccessible>
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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </c:if>
        <ui:isGroupPage>
            <ul>
                <li>
                    <a href="<s:url action="dashboardEnterprise" namespace="/"/>"><span>Dashboard</span></a>

                    <div class="subNav">
                        <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise Dashboard (Beta)</a>
                        <a href="<s:url action="dashboardEnterprise" namespace="/"/>">Cockpit Dashboard</a>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations Dashboard</a>
                            <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Stats</a>
                        </c:if>
                    </div>
                </li>

                <li>
                     <a href="<s:url action="allProjects" namespace="/"/>"><span>Projects</span></a>

                    <div class="subNav">
                        <a href="<s:url action="createNewProject" namespace="/"/>">Start New Project</a>
                    </div>                     
                </li>

                <li>
                     <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>"><span>Copilots</span></a>
                </li>

                <if:isSecurityGroupsUIAccessible>
                    <li class="on">
                        <a href="${viewUserGroupsUrl}"><span>Groups</span></a>
                    </li>
               </if:isSecurityGroupsUIAccessible>

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
                            <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </ui:isGroupPage>
    </div>
    <!-- End #tabs0 -->

	<div class="helloUser">
        <div class="instantSearch">
            <div class="instantSearchBg">
                <input name="instantSearchInput" value="Search for contests, projects and features" />
                <span class="searchIcon"></span>
            </div>
            <div class="resultDropDown">
                <div class="ajaxLoading"><img src="/images/fading-squares.gif" alt="" width="60" height="9" /></div>
                <div class="quickResultList"></div>
            </div>
        </div>
        <ul>
            <li>
                <strong>Hello</strong> <link:currentUser/>|
            </li>
            <li class="helloUserLink"><a href="<s:url action="logout" namespace="/"/>">Logout</a>|</li>
            <li class="helloUserLink"><link:help/></li>
        </ul>
    </div><!-- End .helloUSer -->

    <ui:isDashboardPage>
        <c:if test="${requestScope.CURRENT_TAB eq 'enterprise'}">
            <div class="topBtns" id="enterpriseDashboardTop">
                <a href="${ctx}/copilot/launchCopilotContest" class="copilot" title="Finds a TopCoder Copilot for your project">Get a Copilot</a>
                <a href="<s:url action="createNewProject" namespace="/"/>" class="start" title="Starts a new project">Start a Project</a>
                <a href="${ctx}/launch/home" class="launch" title="Launch a new contest for your project">Launch Contest</a>
            </div>
        </c:if>
    </ui:isDashboardPage>


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
                            <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>">
                                <span>Overview</span>
                            </a>
                        </li>
                        <li <c:if test="${requestScope.CURRENT_TAB eq 'milestone'}">class="on"</c:if>>
                             <a id="tabProjectMilestone" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/> <s:param name="formData.viewType" >list</s:param></s:url>">
                                 <span>Project Milestones</span>
                             </a>
                         </li>

                        <li <c:if test="${requestScope.CURRENT_TAB eq 'gameplan'}">class="on"</c:if>>
                            <a href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span>Game Plan</span></a>
                        </li>

                        <li id="contest" style="position: relative;" <c:if test="${requestScope.CURRENT_TAB eq 'contests'}">class="on"</c:if>>
                            <a href="javascript:;">
                                <span>Contests<span class="arrow"></span></span>
                            </a>
                            <div class="dropDwnLst">
                                <div class="section">
                                    <h3>View Contests</h3>
                                    <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>">List View</a>
                                    <a href="<s:url action="projectContestsCalendar" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Calendar View</a>
                                    <%--<a href="<s:url action="ProjectGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Game Plan View</a>--%>
									 <a href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Game Plan View</a>
                                </div>
                                <div class="section">
                                    <h3>Create Contest</h3>
                                    <a href="<s:url action="home" namespace="/launch"></s:url>">Launch New Contest</a>
                                  <!--  <a href="javascript:;">Quick Create Draft Contest</a>
                                    <a href="javascript:;">Bulk Creation</a>-->
                                </div>
                                <c:if test="${hasWritePermission}">
                                <div class="section">
                                    <h3>Edit Contest</h3>
                                    <a href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Bulk Edit Draft Contests</a>
                                </div>
                                </c:if>
                            </div>
                        </li>

                         <li <c:if test="${requestScope.CURRENT_TAB eq 'issues'}">class="on"</c:if>>
                            <a href="<s:url action="projectIssueTracking" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span>Issue Tracking</span></a>
                        </li>
                    </ul>
                </div>
        </c:if>
    </ui:isProjectPage>

    <ui:isDashboardPage>
        <c:if test="${requestScope.CURRENT_TAB ne 'enterprise' && requestScope.CURRENT_TAB ne 'searchAll' && requestScope.CURRENT_TAB ne 'platformSpecialistsReport'}">
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
    
    <if:isSecurityGroupsUIAccessible>
        <ui:isGroupPage>
            <div id="tabs1">
               <ul>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'detail'}">class="on"</c:if>>
                        <a href="${viewUserGroupsUrl}"><span class="dashboardSpan">Groups</span></a></li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'sendInvitation'}">class="on"</c:if>>
                        <a href='<s:url action="enterSendInvitationAction" namespace="/group"/>'><span
                                class="dashboardSpan">Send Invitation</span></a></li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'approvals'}">class="on"</c:if>>
                        <s:url var="viewPendingApprovalUrl" action="viewPendingApprovalUserAction" namespace="/group">
                        </s:url>
                        <a href="${viewPendingApprovalUrl}"><span class="dashboardSpan">Approvals</span></a></li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'createAdministrator'}">class="on"</c:if>>
                        <a href='<s:url action="enterCreateCustomerAdminAction" namespace="/group"/>'><span
                                class="dashboardSpan">Create Admin</span></a></li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'audit'}">class="on"</c:if>>
                        <s:url var="auditInfoUrl" action="viewAuditingInfoAction" namespace="/group">
                            <s:param name="criteria.permissions" value="{'REPORT', 'READ','WRITE','FULL'}"/>
                        </s:url>
                        <a href="${auditInfoUrl}"><span class="dashboardSpan">Audit Information</span></a></li>
                    <li <c:if test="${requestScope.CURRENT_TAB eq 'receivedInvitations'}">class="on"</c:if>>
                        <s:url var="viewInvitationUrl" action="viewInvitationStatusAction" namespace="/group">
                            <s:param name="criteria.permissions" value="{'REPORT', 'READ','WRITE','FULL'}"/>
                        </s:url>
                        <a href="${viewInvitationUrl}"><span class="dashboardSpan">Received Invitations</span></a></li>
                </ul>
            </div>
        </ui:isGroupPage>
    </if:isSecurityGroupsUIAccessible>
    <div id="tabs2"><!-- tabs on the right side-->
        <ul>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'settings'}">class="on"</c:if>>
                <a href="<s:url action="notifications" namespace="/settings"/>"><span>Settings</span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'reports'}">class="on"</c:if>>
                <a href="<s:url action="dashboardReports" namespace="/"/>"><span>Reports</span></a>
            </li>
            <%
                if (ClientInvoiceManageAction.canViewBillingTab()) {
            %>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'billing'}">class="on"</c:if>>
                <a href="<s:url action="billing" namespace="/"/>"><span>Billing</span></a>
            </li>
            <%
                }
            %>
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
