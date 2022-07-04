<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Manage Copilot Postings assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the contest represented by one of the specified
  - attributes is in Review phase
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="phasedContest" required="false"
              type="com.topcoder.direct.services.view.dto.contest.PhasedContestDTO" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="isInDesiredPhase" value="false"/>
<c:choose>
    <c:when test="${phasedContest ne null}">
        <c:if test="${phasedContest.software}">
            <c:forEach items="${phasedContest.currentPhases}" var="phase">
                <c:if test="${phase.phaseName eq 'Review'}">
                    <c:set var="isInDesiredPhase" value="true"/>
                </c:if>
            </c:forEach>
        </c:if>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${negate and not isInDesiredPhase}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isInDesiredPhase}">
        <jsp:doBody/>
    </c:when>
</c:choose>