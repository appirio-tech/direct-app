<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the submission downloadable from Online Review
  - application.
--%>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="uploadId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<a href="http://<%=ApplicationServer.SOFTWARE_SERVER_NAME%>/review/actions/DownloadContestSubmission.do?method=downloadContestSubmission&amp;uid=${uploadId}"
   class="${styleClass}">
    <jsp:doBody/>
</a>
