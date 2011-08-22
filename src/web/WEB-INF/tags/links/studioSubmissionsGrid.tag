<%--
  - Author: isv
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId.
  -
  - Version: 1.1
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for A element referencing the page with Studio Submissions Grid
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="milestoneRound" required="false" type="java.lang.Boolean" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<c:choose>
    <c:when test="${milestoneRound}">
        <a href="<s:url action="submissions" namespace="/contest">
                     <s:param name="projectId" value="%{#attr['contestId']}"/>
                     <s:param name="formData.viewType" value="'GRID'"/>
                     <s:param name="formData.roundType" value="'MILESTONE'"/>
                 </s:url>" <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:when>
    <c:otherwise>
        <a href="<s:url action="submissions" namespace="/contest">
                     <s:param name="projectId" value="%{#attr['contestId']}"/>
                     <s:param name="formData.viewType" value="'GRID'"/>
                     <s:param name="formData.roundType" value="'FINAL'"/>
                 </s:url>" <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:otherwise>
</c:choose>
