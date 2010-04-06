<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description:
  - This page defines the commonly used constants. - This page fragment is to be included to all pages
  - from TC Direct application which are making use of those constants.
--%>
<%@ page import="com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="DASHBOARD_SEARCH_TYPE_PROJECTS" value="DashboardSearchCriteriaType.PROJECTS" scope="request"/>
<s:set var="DASHBOARD_SEARCH_TYPE_CONTESTS" value="DashboardSearchCriteriaType.CONTESTS" scope="request"/>
<s:set var="DASHBOARD_SEARCH_TYPE_MEMBERS" value="DashboardSearchCriteriaType.MEMBERS" scope="page"/>

