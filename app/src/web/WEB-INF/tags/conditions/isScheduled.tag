<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Manage Copilot Postings assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the contest represented by one of the specified
  - attributes is of Scheduled status
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="typedContestBrief" required="false"
              type="com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="statusName" value="${typedContestBrief.status.name}"/>
<c:set var="isScheduled" value="false"/>
<c:choose>
    <c:when test="${typedContestBrief ne null}">
        <c:if test="${typedContestBrief.software and statusName eq 'Scheduled'}">
            <c:set var="isScheduled" value="true"/>
        </c:if>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${negate and not isScheduled}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isScheduled}">
        <jsp:doBody/>
    </c:when>
</c:choose>