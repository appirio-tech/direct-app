<%--
  - Author: isv
  - Version: 1.1 (Direct Submission Viewer Release 4 Assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the page with download for specified submission in
  - Studio application.
  
  - Version 1.1 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) change notes: 
  -   Made the reference to Studio site configurable.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<%@ attribute name="original" required="false" type="java.lang.Boolean" %>
<%@ attribute name="contestId" required="false" type="java.lang.Long" %>

<c:set var="STUDIO_SERVER_NAME" value="<%=ApplicationServer.STUDIO_SERVER_NAME%>"/>

<c:set var="link" value="/direct/contest/downloadSoftwareSubmission.action?projectId=${contestId}&submissionId=${submissionId}" />
<a href="${link}"
   class="${styleClass}">
    <jsp:doBody/>
</a>
