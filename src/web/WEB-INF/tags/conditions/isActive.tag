<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Manage Copilot Postings assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the contest represented by one of the specified
  - attributes is of Active status
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="typedContestBrief" required="false"
              type="com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="status" value="${typedContestBrief.status.name}"/>
<c:set var="isActive" value="false"/>
<c:choose>
    <c:when test="${typedContestBrief ne null}">
        <c:if test="${typedContestBrief.software and (status eq 'Active'
                                                      or status ne 'Active' and status ne 'Draft'
                                                         and status ne 'Scheduled' and status ne 'Completed'
                                                         and not fn:startsWith(status, 'Cancelled'))}">
            <c:set var="isActive" value="true"/>
        </c:if>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${negate and not isActive}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isActive}">
        <jsp:doBody/>
    </c:when>
</c:choose>