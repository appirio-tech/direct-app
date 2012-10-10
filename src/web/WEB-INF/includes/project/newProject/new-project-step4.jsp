<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 4.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow) change notes:
  -              Updated to use the commom require-copilot-step.jsp file.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added class field for question elements.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep4" class="hide customProject newProjectStep">
    <!-- step title -->
    <div class="stepTitle">
        <h3><span>4</span>Would you like a copilot for this project?</h3>
         <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    <%@ include file="/WEB-INF/includes/project/newProject/require-copilot-step.jsp" %>
</div>
