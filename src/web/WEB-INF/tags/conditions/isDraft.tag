<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Manage Copilot Postings assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the contest represented by one of the specified
  - attributes is of Draft status
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="typedContestBrief" required="false"
              type="com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<c:set var="isDraft" value="false"/>
<c:choose>
    <c:when test="${typedContestBrief ne null}">
        <c:if test="${typedContestBrief.software and typedContestBrief.status.name eq 'Draft'}">
            <c:set var="isDraft" value="true"/>
        </c:if>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${negate and not isDraft}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isDraft}">
        <jsp:doBody/>
    </c:when>
</c:choose>