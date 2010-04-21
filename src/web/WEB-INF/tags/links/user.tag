<%--
  - Author: isv
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for specified user account.
  -
  - TODO : Subsequent assemblies must properly update this tag to set valid value for href attribute.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="userId" required="true" type="java.lang.Long" %>
<%@ attribute name="handle" required="true" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<c:if test="${empty styleClass}">
    <c:set var="styleClass" value="postedBy"/>
</c:if>

<a href="http://www.topcoder.com/tc?module=MemberProfile&cr=${userId}" class="${styleClass}" target="_blank"><c:out value="${handle}"/></a>
