<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4 ) change notes: added logic supporting Studio user profiles
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
<%@ attribute name="handle" required="true" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<%@ attribute name="isStudio" required="false" type="java.lang.Boolean" %>
<c:if test="${empty styleClass}">
    <c:set var="styleClass" value="postedBy"/>
</c:if>

<c:choose>
    <c:when test="${isStudio}">
        <a href="http://<%=ApplicationServer.STUDIO_SERVER_NAME%>/?module=ViewMemberProfile&ha=${handle}"
           class="${styleClass}" target="_blank">
            <c:out value="${handle}"/></a>
    </c:when>
    <c:otherwise>
        <tc-webtag:handle coderId="${userId}"/>
    </c:otherwise>
</c:choose>
