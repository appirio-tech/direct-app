<%--
  - Author: TCSDEVELOPER
  - Version: 1.1
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the page with details for specified project in
  - Online Review application.
  -
  - Version 1.1 - TCCC-2827 change notes:
  - - add target attribute which is not required.
--%>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="projectId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<%@ attribute name="target" required="false" type="java.lang.String" %>

<a href="http://<%=ApplicationServer.SOFTWARE_SERVER_NAME%>/review/actions/ViewProjectDetails.do?method=viewProjectDetails&amp;pid=${projectId}"
   class="${styleClass}" target="${target}">
    <jsp:doBody/>
</a>
