<%--
  - Author: GreatKevin, TCSASSEMBLER, TCSCODER
  -
  - Copyright (C) 2013 - 2016 TopCoder Inc., All Rights Reserved.
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Navigation Update)
  -
  - Version: 1.1 (TC Direct Rebranding Assembly Project and Contest related pages)
  - - Update the header title for launch new challenge page
  -
  - Version 1.2 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Update the header for the dashboard page and admin pages
  -
  - Version 1.3 (TopCoder Direct - My Created Challenges)
  - - Add "My" in top nav
  -
  - Version 1.4 (TC Direct - ASP Integration Work Management)
  - - Add "Work Manager" for project dashboard.
  -
  - Version 1.5 (TopCoder Direct - Remove ASP Integration Related Logic)
  - - Remove "Work Manager" for project dashboard.
  -
  - Description: The new cockpit header and navigation.
--%>
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%@ page import="com.topcoder.direct.services.view.action.cloudvm.DashboardVMAction" %>
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


<div id="newHeader">
<div class="topMenu">
<ul class="menus">
<li <ui:isDashboardPage>class="current"</ui:isDashboardPage>>
    <span class="t"><a href="javascript:;">Dashboard</a> <i></i></span>

    <div class="flyout lv1">
        <span class="h"><a href="javascript:;">Dashboard</a></span>

        <ul>
            <li>
                <a class="first" href="<s:url action="overview" namespace="/enterpriseDashboard"/>">Enterprise</a>
            </li>
            <c:if test="${tcdirect:isTCStaff() || tcdirect:isTCPlatformSpecialist()}">
                <li>
                    <a href="<s:url action="operationsDashboardEnterprise" namespace="/"/>">Operations</a>
                </li>
            </c:if>
            <c:if test="${tcdirect:isTCStaff() || tcdirect:isTCPlatformSpecialist()}">
                <li>
                    <a href="<s:url action="clientUserStatsReport" namespace="/"/>">Client User Statistics</a>
                </li>
            </c:if>
            <c:if test="${tcdirect:isTCStaff() || tcdirect:isTCPlatformSpecialist()}">
                <li>
                    <a href="<s:url action="platformSpecialistsReport" namespace="/"/>">Expert Services</a>
                </li>
            </c:if>
            <c:if test="${tcdirect:isTCAccounting() || tcdirect:isTCOperation()}">
                <li>
                    <a href="<s:url action="overview" namespace="/payments"/>">Accounting</a>
                </li>
            </c:if>
            <c:if test="${tcdirect:canViewInternalStats()}">
                <li>
                    <a href="<s:url action="internalStats" namespace="/"/>">Internal</a>
                </li>
            </c:if>
            <%--<li style="display: none">--%>
                <%--<a class="last" href="javascript:;">My Custom Dashboard</a>--%>
            <%--</li>--%>
        </ul>
    </div>
</li>
<span class="hide" id="recentProjectItemTemplate">
                        <a class="recentProjectName" href="javascript:;">Project 1 <span class="arrow"></span></a>

                        <div class="flyout lv3 lv3Wide">
                            <ul class="liIcons">
                                <li>
                                    <a class="first recentProjectOverview">Overview <span class="icon overviewI"></span></a>
                                </li>

                                <li>
                                    <a class="recentProjectPlan">Game Plan <span class="icon gameI"></span></a>
                                </li>

                                <li>
                                    <a class="last recentProjectSetting">Settings <span class="icon settinsI"></span></a>
                                </li>
                            </ul>
                        </div>
</span>
<li <ui:isProjectPage>class="current"</ui:isProjectPage>>
    <span class="t"><a href="javascript:;">Projects</a> <i></i></span>

    <div class="flyout lv1">
        <span class="h"><a href="javascript:;">Projects</a></span>

        <ul>
            <li>
                <a class="first" href="<s:url action="createNewProject" namespace="/"/>">Start New</a>
            </li>
            <s:if test="%{#session.currentSelectDirectProjectID > 0 && sessionData.currentProjectContext.name != null}">
                <input type="hidden" name="topNavCurrentProjectId" value="<s:property value='%{#session.currentSelectDirectProjectID}'/>"/>
                <li class="trigger">
                    <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>" title="<s:property value="sessionData.currentProjectContext.name"/>">Current Project <span class="arrow"></span></a>

                    <div class="flyout lv2">
                        <ul class="liIcons">
                            <li>
                                <a class="first" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>">Overview <span class="icon overviewI"></span></a>
                            </li>

                            <li>
                                <a href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Game Plan <span class="icon gameI"></span></a>
                            </li>

                            <li>
                                <a class="last" href="<s:url action="editProject" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Settings <span class="icon settinsI"></span></a>
                            </li>
                        </ul>
                    </div>
                </li>
            </s:if>

            <li class="trigger" id="recentProjectsTopNav">
                <a href="javascript:;">Recent Projects <span class="arrow"></span></a>

                <div class="flyout lv2 flyoutWide">
                    <ul class='recentProjectsFlyout'>

                    </ul>
                </div>
            </li>

            <li>
                <a class="last" href="<s:url action="allProjects" namespace="/"/>">All Projects</a>
            </li>

            <li class="trigger">
                <a href="javascript:;">Project Copilots <span class="arrow"></span></a>

                <div class="flyout lv2">
                    <ul class="liIcons">
                        <li>
                            <a class="first" href="<s:url action="launchCopilotContest" namespace="/copilot"></s:url>">Get a copilot</a>
                        </li>

                        <li>
                            <a href="<s:url action="listCopilotPostings" namespace="/copilot"></s:url>">Copilot Postings</a>
                        </li>

                        <li>
                            <a class="last" href="<s:url action="manageCopilots" namespace="/copilot"></s:url>">Manage Copilots</a>
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</li>

<li <ui:isChallengesPage>class="current"</ui:isChallengesPage>>
    <span class="t"><a href="javascript:;">Challenges</a> <i></i></span>

    <div class="flyout lv1">
        <span class="h"><a href="javascript:;">Challenges</a></span>

        <ul>
            <li>
                <a class="first" href="<s:url action="createdChallenges" namespace="/my"/>">Challenges I Created</a>
            </li>
            <li>
                <a class="last" href="<s:url action="challenges" namespace="/my"/>">All Challenges</a>
            </li>
        </ul>
    </div>
</li>

<li <ui:isReportPage>class="current"</ui:isReportPage>>
    <span class="t"><a href="javascript:;">Reporting</a> <i></i></span>

    <div class="flyout lv1">
        <span class="h"><a href="javascript:;">Reporting</a></span>

        <ul class="liIcons">
            <li>
                <a class="first" href="<s:url action="dashboardBillingCostReport" namespace="/"/>">Competition Costs <span class="icon costsI"></span></a>
            </li>
            <li>
                <a href="<s:url action="dashboardCostReport" namespace="/"/>">Cost Analysis <span class="icon costAnal"></span></a>
            </li>
            <li>
                <a href="<s:url action="dashboardReports" namespace="/"/>">Pipeline <span class="icon pipelineI"></span></a>
            </li>

            <li>
                <a href="<s:url action="dashboardProjectMetricsReport" namespace="/"/>">Project Metrics <span class="icon proMetricsI"></span></a>
            </li>

            <li>
                <a href="<s:url action="dashboardParticipationReport" namespace="/"/>">Participation Metrics <span class="icon parMetricsI"></span></a>
            </li>

            <li>
                <a class="last" href="<s:url action="dashboardJiraIssuesReport" namespace="/"/>">Issue Metrics <span class="icon issMetricsI"></span></a>
            </li>
        </ul>
    </div>
</li>


<li <ui:isAdminPage>class="current"</ui:isAdminPage> <ui:isGroupPage>class="current"</ui:isGroupPage>>
    <span class="t"><a href="javascript:;">Admin</a> <i></i></span>

    <div class="flyout lv1">
        <span class="h"><a href="javascript:;">Admin</a></span>

        <ul>
            <%
                pageContext.setAttribute("hasVMDashboardAccess", DashboardVMAction.isApplicable());
            %>
            <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting() || hasVMDashboardAccess || tcdirect:isTCStaff() || tcdirect:isScorecardAdmin()}">
            <li class="trigger" id="adminGeneralMenu">
                <a class="first" href="javascript:;">General <span class="arrow"></span></a>

                <div class="flyout lv2">
                    <ul class="liIcons">
                        <c:if test="${tcdirect:isSuperAdmin() || tcdirect:isTCAccounting()}">
                            <li>
                                <a class="first" href="<s:url action="contestFee" namespace="/settings/admin"/>">Challenge Fee Management <span class="icon feeI"></span></a>
                            </li>
                        </c:if>
                        <c:if test="${hasVMDashboardAccess}">
                        <li>
                            <a href="<s:url action="dashboardVMAction" namespace="/"/>">VM Management <span class="icon vmI"></span></a>
                        </li>
                        </c:if>
                        <c:if test="${tcdirect:isTCStaff()}">
                            <li>
                                <a href="<s:url action="manageCopilotFeedback" namespace="/"/>">Manage Copilot Feedback <span class="icon feedbackI"></span></a>
                            </li>

                            <li>
                                <a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=BadgeAdminHome">Manage Member Badges <span class="icon badgesI"></span></a>
                            </li>
                        </c:if>
                        <c:if test="${tcdirect:isScorecardAdmin()}">
                            <li>
                                <a class="last" href="/direct/scorecard/">Scorecard Management <span class="icon scorecardI"></span></a>
                            </li>
                        </c:if>
                        <c:if test="${tcdirect:isSuperAdmin()}">
                            <li>
                                <a class="first" href="<s:url action="syncUser" namespace="/settings/admin"/>">Synchronize User <span class="icon syncUser"></span></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </li>
            </c:if>

            <li class="trigger">
                <a class="last" href="javascript:;">Settings <span class="arrow"></span></a>

                <div class="flyout lv2">
                    <ul class="liIcons">
                        <li>
                            <a class="first" href="http://<%=ServerConfiguration.SERVER_NAME%>/tc?module=MyHome" target="_blank">My Profile <span class="icon profileI"></span></a>
                        </li>
                        <li>
                            <a href="<s:url action="notifications" namespace="/settings"/>">Notifications <span class="icon notifyI"></span></a>
                        </li>
                        <li>
                            <a href="<s:url action="permissions" namespace="/settings"/>">Permissions <span class="icon permissions"></span></a>
                        </li>
                        <if:isSecurityGroupsUIAccessible>
                            <li>
                                <a class="last" href="${viewUserGroupsUrl}">Groups <span class="icon groupsI"></span></a>
                            </li>
                        </if:isSecurityGroupsUIAccessible>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</li>

</ul>

<div class="helloUser">
    <div class="instantSearch">
        <div class="instantSearchBg">
            <input name="instantSearchInput" value="Search for challenges, projects and features"> <span class="searchIcon"></span>
        </div>

        <div class="resultDropDown">
            <div class="ajaxLoading"><img alt="" height="9" src="/images/fading-squares.gif" width="60"></div>

            <div class="quickResultList"></div>
        </div>
    </div>

    <ul>
        <li>
            <strong>Hello</strong> <link:currentUser/>
        </li>

        <li class="helloUserLink">
            <a href="https://<%=ServerConfiguration.SERVER_NAME%>/logout">Logout</a>
        </li>

        <li class="helloUserLink">
            <a href="https://help.topcoder.com" target="_blank">Help</a>
        </li>
    </ul>
</div><!-- End .helloUSer -->
</div><!-- End .topMenu -->

<c:choose>
    <c:when test="${requestScope.PAGE_TYPE eq 'copilot'}">
        <div class="midarea2">
    </c:when>
    <c:otherwise>
        <div class="midarea">
    </c:otherwise>
</c:choose>
    <c:choose>

        <c:when test="${requestScope.PAGE_TYPE eq 'dashboard'}">
            <c:choose>
                <c:when test="${requestScope.CURRENT_TAB eq 'overview'}">
                    <a href="javascript:;" class="logo overviewLogo">Dashboard</a>
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'enterprise'}">
                    <s:if test="viewData.resultType.name() == 'PM_PROJECTS'">
                        <a class="logo" href="javascript:;" style="left: 25px; top: 64px; cursor:default"> <img src="/images/all_projects_ico.png" alt="Operations DashBoard" class="projectTitle"/><span id="projectTitleSpan">Operations DashBoard</span></a>
                    </s:if>
                    <s:else>
                        <a class="logo" href="javascript:;" style="cursor:default; top:64px"> <img alt="Enterprise Dashboard" src="/images/enterprise_dashboard_logo.png"></a>
                    </s:else>
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'platformSpecialistsReport'}">
                </c:when>
                <c:when test="${requestScope.CURRENT_TAB eq 'searchAll'}">
                    <a class="logo resultsPageLogo" href="javascript:;"> Search Results </a>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'launch'}">
            <a href="javascript:;" class="logo">
                <span>Launch New Challenge</span></a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'copilot'}">
            <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>" class="logo" style="top:14px">
                <span>TopCoder Copilots</span>
            </a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'group'}">
            <a href="${viewUserGroupsUrl}" class="logo">Groups</a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'internal'}">
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'report'}">
            <a href="javascript:;" class="logo"><span>Direct Reports</span></a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'admin'}">
            <a href="javascript:;" class="logo"><span>Direct Admin</span></a>
        </c:when>
        <c:when test="${requestScope.PAGE_TYPE eq 'challenges'}">
            <a href="javascript:;" class="logo"><span>
                <c:choose>
                    <c:when test="${requestScope.CURRENT_TAB eq 'myCreatedChallenges'}">
                       Challenges I Created 
                    </c:when>
                    <c:when test="${requestScope.CURRENT_TAB eq 'myChallenges'}">
                       All Challenges
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </span></a>
        </c:when>
        <c:when test="${requestScope.CURRENT_TAB eq 'createNewProject'}">
            <a href="<s:url action="dashboardActive" namespace="/"/>" class="logo">
                <span>Start a New Project</span>
            </a>
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
                <h1><a href="<s:url action='projectOverview' namespace='/'><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>"><span><s:property value="sessionData.currentProjectContext.name"/></span></a></h1>
            </s:else>
        </c:otherwise>
    </c:choose>
    <ui:isDashboardPage>
        <c:if test="${requestScope.CURRENT_TAB eq 'enterprise' and !requestScope.NO_ENTERPRISE_DASHBOARD_TOP}">
            <div class="topBtns" id="enterpriseDashboardTop">
                <a href="${ctx}/copilot/launchCopilotContest" class="copilot" title="Finds a TopCoder Copilot for your project">Get a Copilot</a>
                <a href="<s:url action="createNewProject" namespace="/"/>" class="start" title="Starts a new project">Start a Project</a>
                <a href="${ctx}/launch/home" class="launch" title="Launch a new challenge for your project">Launch Challenge</a>
            </div>
        </c:if>
    </ui:isDashboardPage>
</div>


<ui:isProjectPage>
<c:if test="${requestScope.CURRENT_TAB ne 'allProjects' && requestScope.CURRENT_TAB ne 'createNewProject'}">
<div class="mainMenu">
    <ul class="menus">
        <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="active"</c:if>>
            <a class="ove" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><span class="icon"></span> Overview</a>
        </li>

        <li <c:if test="${requestScope.CURRENT_TAB eq 'milestone'}">class="active"</c:if>>
            <a class="mil" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/> <s:param name="formData.viewType" >list</s:param></s:url>"><span class="icon"></span> Milestones</a>
        </li>

        <li <c:if test="${requestScope.CURRENT_TAB eq 'gameplan'}">class="active"</c:if>>
            <a class="gam" href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="icon"></span> <span class="arrow"></span> Game Plan</a>

            <div class="flyout">
                <div class="section">
                    <h3>View</h3>
                    <a href="<s:url action="ProjectJsGanttGamePlanView" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Game Plan Gantt</a>
                    <a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>">List</a><br/>
                    <a href="<s:url action="projectContestsCalendar" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Calendar</a>
                </div>

                <div class="section">
                    <h3>Create</h3>
                    <a href="<s:url action="home" namespace="/launch"></s:url>">New Challenge</a>
                    <a href="<s:url action="projectPlanner" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Game Plan</a>
                </div>

                <c:if test="${hasWritePermission}">
                    <div class="section">
                        <h3>Edit</h3>
                        <a href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>">Bulk Edit</a>
                    </div>
                </c:if>
            </div>
        </li>

        <li <c:if test="${requestScope.CURRENT_TAB eq 'issues'}">class="active"</c:if>>
            <a class="iss" href="<s:url action="projectIssueTracking" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="icon"></span> Issue Tracking</a>
        </li>

        <li <c:if test="${requestScope.CURRENT_TAB eq 'assets'}">class="active"</c:if>>
            <a class="ass" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="icon"></span> Assets</a>
        </li>

        <li <c:if test="${requestScope.CURRENT_TAB eq 'tasks'}">class="active"</c:if>>
            <a class="tas" href="<s:url action="projectTasks" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="icon"></span> Tasks</a>
        </li>
	    <li <c:if test="${requestScope.CURRENT_TAB eq 'vmManagement'}">class="active"</c:if>>
           <a class="vm" href="<s:url action="projectVMManagement" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span  style="top:12px"  class="icon"></span>VM Management</a>
        </li>
    </ul>
</div><!-- End .mainMenu -->
</div>
</c:if>
</ui:isProjectPage>

<c:if test="${requestScope.CURRENT_TAB eq 'overview'}">
    <s:set name="projId" value="viewData.projectStats.project.id"/>
</c:if>
<c:if test="${requestScope.CURRENT_TAB eq 'contests'}">
    <s:set name="projId" value="viewData.contestStats.contest.project.id"/>
</c:if>

<ui:isCopilotPage>
    <div id="tabs1" class="copilotsTabs1" style="top:98px">
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
</div>

<c:if test="${requestScope.CURRENT_TAB ne 'enterprise'}">
    <jsp:include page="modalWindows.jsp"/>
</c:if>
