<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Direct Submission Viewer Release 4 Assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the page with download for specified submission in
  - Studio application.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<a href="http://studio.topcoder.com/?module=DownloadSubmission&sbmid=${submissionId}"
   class="${styleClass}">
    <jsp:doBody/>
</a>
