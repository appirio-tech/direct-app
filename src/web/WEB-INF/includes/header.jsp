<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="header">
    <c:choose>
        <c:when test="${requestScope.PAGE_TYPE eq 'dashboard'}">
            <a href="<s:url action="dashboard" namespace="/"/>" class="logo">
                <img src="/images/dashboard_logo.png" alt="Direct Dashboard" /></a>
        </c:when>
        <c:otherwise>
            <a href="<s:url action="currentProjectOverview" namespace="/"/>" class="logo projectLogo">
                <s:property value="sessionData.currentProjectContext.name"/>
            </a>
        </c:otherwise>
    </c:choose>

    <div class="helloUser">
        <ul>
            <li>
                <strong>Hello</strong> <link:currentUser/>|
            </li>
            <li><a href="<s:url action="logout" namespace="/"/>">Logout</a>|</li>
            <li><link:help/></li>
        </ul>
    </div><!-- End .helloUSer -->

    <div id="tabs1"><!-- the left tabs -->
        <ui:isDashboardPage>
            <ul>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'dashboard'}">class="on"</c:if>>
                    <a href="<s:url action="dashboard" namespace="/"/>"><span>Dashboard</span></a>
                </li>
                <li><a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Tasks</span></a>
                </li>
                <li><a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>CoPilots</span></a>
                </li>
            </ul>
        </ui:isDashboardPage>
        <ui:isProjectPage>
            <ul>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'overview'}">class="on"</c:if>>
                    <a href="<s:url action="currentProjectOverview" namespace="/"/>"><span>Overview</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'tasks'}">class="on"</c:if>>
                    <a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Tasks</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'contests'}">class="on"</c:if>>
                    <a href="<s:url action="currentProjectDetails" namespace="/"/>"><span>Contests</span></a>
                </li>
                <li <c:if test="${requestScope.CURRENT_TAB eq 'permissions'}">class="on"</c:if>>
                    <a href="<s:url action="currentProjectPermissionsView" namespace="/"/>"><span>Permissions</span></a>
                </li>
            </ul>
        </ui:isProjectPage>
    </div><!-- End #tabs1 -->

    <div id="tabs2"><!-- tabs on the right side-->
        <ul>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'pipeline'}">class="on"</c:if>>
                <a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Pipeline</span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'search'}">class="on"</c:if>>
                <a href="<s:url action="dashboardSearchView" namespace="/"/>"><span>Search</span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_TAB eq 'settings'}">class="on"</c:if>>
                <a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Settings</span></a>
            </li>
        </ul>
    </div><!-- End #tabs2 -->
</div><!-- End #header -->
