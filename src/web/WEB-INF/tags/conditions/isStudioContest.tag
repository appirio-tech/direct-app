<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the contest represented by one of the specified
  - attributes is a Studio contest
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="contestStats" required="false"
              type="com.topcoder.direct.services.view.dto.contest.ContestStatsDTO" %>
<%@ attribute name="contestBrief" required="false"
              type="com.topcoder.direct.services.view.dto.contest.ContestBriefDTO" %>
<%@ attribute name="typedContestBrief" required="false"
              type="com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="isStudio" value="false"/>
<c:choose>
    <c:when test="${contestStats ne null}">
        <c:if test="${contestStats.isStudio}">
            <c:set var="isStudio" value="true"/>
        </c:if>
    </c:when>
    <c:when test="${contestBrief ne null}">
        <c:if test="${not contestBrief.software}">
            <c:set var="isStudio" value="true"/>
        </c:if>
    </c:when>
    <c:when test="${typedContestBrief ne null}">
        <c:if test="${not typedContestBrief.software and typedContestBrief.contestType.studio}">
            <c:set var="isStudio" value="true"/>
        </c:if>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${negate and not isStudio}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isStudio}">
        <jsp:doBody/>
    </c:when>
</c:choose>