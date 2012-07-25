<%--
  - Author: TCSASSEMLBER
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The side bar of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- siderbar -->
<div id="silderBar">
    <ul>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'overview'}">class="active"</c:if>> <a href="<s:url action="overview" namespace="/enterpriseDashboard"/>" class="overviewIcon" title="Overview" rel="Show the overview statistics of the client">Overview</a></li>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'financial'}">class="active"</c:if>><a href='<s:url action="financial" namespace="/enterpriseDashboard"/>' class="financialsIcon" title="Financials" rel="Show the financial statistics of the client">Financials</a></li>
        <li><a href="javascript:;" class="projectHealthIcon" title="Project Health" rel="Showing the overall projects Health of all projects.">Project Health</a></li>
        <li><a href="javascript:;" class="roadmapIcon" title="Roadmap" rel="Showing the Overdue, Upcoming, and Completed Releases.">Roadmap</a></li>
        <li><a href="javascript:;" class="pipelineIcon" title="Pipeline" rel="A graph depicting the projects in Pipeline is shown here.">Pipeline</a></li>
        <li><a href="javascript:;" class="communityIcon" title="Community" rel="Showing the overall contri-bution for the projects from each country.">Community</a></li>
        <li><a href="javascript:;" class="analyticsIcon" title="Analytics" rel="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a orci sit amet orci">Analytics</a></li>
        <li><a href="javascript:;" class="activeContestIcon" title="Active Contest" rel="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a orci sit amet orci">Active Contest</a></li>
        <li><a href="javascript:;" class="latestActivityIcon" title="Latest Activity" rel="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a orci sit amet orci">Latest Activity</a></li>
    </ul>
</div>
<!-- End #siderBar -->