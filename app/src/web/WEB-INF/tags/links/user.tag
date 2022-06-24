<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.2
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4 ) change notes: added logic supporting Studio user profiles
  -
  - Version 1.2 (TC Direct Replatforming Release 5) change notes: The logic to support Studio user profiles was removed.
  -
  - Description: This tag renders an HTML A element referencing the view with details for specified user account.
  -
  - TODO : Subsequent assemblies must properly update this tag to set valid value for href attribute.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tc-webtag" uri="/WEB-INF/tld/tc-webtags.tld" %>

<%@ attribute name="userId" required="true" type="java.lang.Long" %>
<%@ attribute name="handle" required="false" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<c:if test="${empty styleClass}">
    <c:set var="styleClass" value="postedBy"/>
</c:if>

<tc-webtag:handle coderId="${userId}"/>
