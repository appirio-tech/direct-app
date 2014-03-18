<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the jira page for the a project.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="projectId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<%@ attribute name="target" required="false" type="java.lang.String" %>

<a href="https://www.topcoder.com/bugs/secure/IssueNavigator.jspa?reset=true&jqlQuery=%22Contest+ID%22+%7E+%22${projectId}%22+ORDER+BY+resolved+DESC%2C+priority+DESC"
   class="${styleClass}" target="${target}">
    <jsp:doBody/>
</a>
