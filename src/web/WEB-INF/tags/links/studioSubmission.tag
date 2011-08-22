<%--
  - Author: isv
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId.
  -
  - Version: 1.1
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for A element referencing the Single Studio Submission
  - page for selected submission.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="roundType" required="false" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<c:choose>
    <c:when test="${submissionId > 0}">
        <a  <c:if test="${not empty id}">id="${id}"</c:if>
            <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>
            href="<s:url action="studioSubmission" namespace="/contest">
                        <s:param name="projectId" value="%{#attr['contestId']}"/>
                        <s:param name="formData.submissionId" value="%{#attr['submissionId']}"/>
                        <s:param name="formData.roundType" value="%{#attr['roundType']}"/>
                 </s:url>">
            <jsp:doBody/>
        </a>
    </c:when>
    <c:otherwise>
        <jsp:doBody/>
    </c:otherwise>
</c:choose>
