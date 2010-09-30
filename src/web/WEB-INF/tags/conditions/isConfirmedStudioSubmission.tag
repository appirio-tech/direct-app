<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Submission Viewer Release 4 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This conditional tag renders it's body only if the submissions represented by one of the specified
  - attributes is "Confirmed"
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="submission" required="true" type="com.topcoder.service.studio.SubmissionData" %>
<%@ attribute name="negate" required="false" type="java.lang.Boolean" %>

<s:set var="roundType" value="formData.roundType.toString()" scope="page"/>
<c:choose>
    <c:when test="${roundType eq 'MILESTONE'}">
        <c:set var="isConfirmed" value="${submission.awardMilestonePrize}"/>
    </c:when>
    <c:otherwise>
        <c:set var="isConfirmed" value="${submission.userRank > 0}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${negate and not isConfirmed}">
        <jsp:doBody/>
    </c:when>
    <c:when test="${not negate and isConfirmed}">
        <jsp:doBody/>
    </c:when>
</c:choose>