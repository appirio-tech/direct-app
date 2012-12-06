<%--
  - Author: GreatKevin, hanshuai
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  -
  - Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
  -  - Updated pipeline icon to link to pipeline page.
  -
  - Version 1.2 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part) changes:
  - - Add link for the roadmap icon in the sidebar
  -
  - Version 1.3 (Module Assembly - TC Cockpit Enterprise Dashboard New Active Contests) changes:
  - - Add link for the active contests icon in the sidebar
  -
  - Version 1.4 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
  - - Add link for the analysis icon in the sidebar
  -
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The side bar of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- siderbar -->
<div id="silderBar">
    <ul>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'overview'}">class="active"</c:if>> <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>" class="overviewIcon filterSynEnabled" title="Overview" rel="Summarizes statistics about your projects and general platform usage.">Overview</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'financial'}">class="active"</c:if>><a href='<s:url action="financial" namespace="/enterpriseDashboard"/>' class="financialsIcon filterSynEnabled" title="Financials" rel="Your financial data.">Financials</a></li>
        <li><a href="javascript:;" class="projectHealthIcon" title="Project Health" rel="Showing the overall projects Health of all projects.">Project Health</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'roadmap'}">class="active"</c:if>><a href="<s:url action="roadmap" namespace="/enterpriseDashboard"/>" class="roadmapIcon filterSynEnabled" title="Roadmap" rel="Aggregate view of all your project milestones.">Roadmap</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'pipeline'}">class="active"</c:if>><a href='<s:url action="pipeline" namespace="/enterpriseDashboard"/>' class="pipelineIcon filterSynEnabled" title="Pipeline" rel="A historical and future view of your contest and project pipeline.">Pipeline</a></li>
        <li><a href="javascript:;" class="communityIcon" title="Community" rel="See what your community looks like.">Community</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'analysis'}">class="active"</c:if>><a href="<s:url action="analysis" namespace="/enterpriseDashboard"/>" class="analyticsIcon filterSynEnabled" title="Analytics" rel="Gain insight into your platform usage.  See average cost, duration, fulfillment, volume, etc.">Analytics</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'activeContests'}">class="active"</c:if>><a href='<s:url action="activeContests" namespace="/enterpriseDashboard"/>' class="activeContestIcon" title="Active Contests" rel="See your current activity.">Active Contests</a></li>
        <li><a href="javascript:;" class="latestActivityIcon" title="Latest Activity" rel="Your recently completed activity.">Latest Activity</a></li>
    </ul>
</div>
<!-- End #siderBar -->
