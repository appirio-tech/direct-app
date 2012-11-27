<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Release Assembly - TopCoder Security Groups - Release 2)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if current user is granted a permission to
  - access Security Groups GUI
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="isAllowedToAccessSecurityGroups" value="${tcdirect:isSecurityGroupsUIAvailable()}"/>
<c:choose>
    <c:when test="${negate and not isAllowedToAccessSecurityGroups}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isAllowedToAccessSecurityGroups}">
        <jsp:doBody/>
    </c:when>
</c:choose>