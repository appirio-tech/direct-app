<%--
  - Author: isv, tangzx, TCSDEVELOPER
  - Version: 1.5.2
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment is to be included to all pages from TC Direct application.
  - It renders the common page headers.
  -
  - Version 1.1 (Direct Permissions Setting Back-end and Integration Assembly 1.0) changes: added Permissions tab for
  - dahsboard pages.
  -
  - Version 1.2 (Direct Pipeline Integration Assembly 1.0) changes: added Reports tab.
  - Version 1.3 (Direct Enterprise Dashboard Assembly 1.0) changes: added Overview tab for dashboard pages.  
  - Version 1.4 (TC Direct - Launch Copilot Selection Contest assembly) changes: add copilot tab.
  - Version 1.5 (TC Direct Manage Copilots Assembly) changes: update copilot manage sub tab.
  - Version 1.5.1 (Direct Release 6 assembly) changes: fixed styles for Hello User handle.
  - Version 1.5.2 (Manage Copilot Postings assembly) change notes: linked "My Copilot Selection Contests" tab
  - to "Manage Copilot Postings" page.
--%>
<%@ page import="com.topcoder.direct.services.view.action.cloudvm.DashboardVMAction" %>
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
        <c:otherwise>
            <a href="<s:url action="currentProjectOverview" namespace="/"/>" class="logo projectLogo">
                <s:property value="sessionData.currentProjectContext.name"/>
            </a>
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
                <!--
                <li><a href="#"><span>Messages (0)</span></a></li>
                -->
            </ul>
        </ui:isCopilotPage>
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

	<c:if test="${requestScope.CURRENT_TAB eq 'overview'}">
		<s:set name="projId" value="viewData.projectStats.project.id"/>  
	</c:if>
	<c:if test="${requestScope.CURRENT_TAB eq 'contests'}">
		<s:set name="projId" value="viewData.contestStats.contest.project.id"/>  
	</c:if>	
	
    <ui:isProjectPage>
        <div id="tabs1">
             <ul>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="on"</c:if>>
            		<a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>">
						<span>Overview</span>
					</a>
                </li>

                <li <c:if test="${requestScope.CURRENT_TAB eq 'contests'}">class="on"</c:if>>
           			<a href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>">
						<span>Contests</span>
					</a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'gameplan'}">class="on"</c:if>>
                    <a href="<s:url action="ProjectGamePlanView" namespace="/"> <s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}" /></s:url>"><span>Game Plan</span></a>
                </li>
            </ul>
        </div>
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
                    <a href="<s:url action="dashboard" namespace="/"/>"><span class="dashboardSpan">Upcoming Activities</span></a>
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
                <li ><a href="javascript:;"><span>Introduction To Copilots</span></a></li>
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
