<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 6th step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  -             Update button text to uppercase.
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep6" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3 class="question38"></h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <%@ include file="/WEB-INF/includes/project/newProject/assign-user-access.jsp" %>
</div>