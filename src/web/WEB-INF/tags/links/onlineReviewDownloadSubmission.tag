<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the submission downloadable from Online Review
  - application.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="projectId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<a href="<s:url action="downloadSoftwareSubmission" namespace="/contest">
             <s:param name="projectId">${projectId}</s:param>
             <s:param name="submissionId">${submissionId}</s:param>
</s:url>" class="${styleClass}">
    <jsp:doBody/>
</a>
