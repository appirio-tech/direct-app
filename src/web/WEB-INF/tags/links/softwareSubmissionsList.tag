<%--
  - Author: GreatKevin
  - Version: 1.2
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Two)
  - - update to support round type MILESTONE and FINAL
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Five)
  - - update to support null round type.
  -
  - Description: This tag renders the HTML markup for A element referencing the page with Software Submissions List
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="milestoneRound" required="false" type="java.lang.Boolean" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>


<c:choose>
    <c:when test="${milestoneRound == true}">
        <a href="<s:url action="softwareSubmissions" namespace="/contest">
                     <s:param name="projectId" value="%{#attr['contestId']}"/>
                     <s:param name="roundType" value="'MILESTONE'"/>
                 </s:url>" <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
    <jsp:doBody/>
        </a>
    </c:when>
    <c:when test="${milestoneRound == false}">
        <a href="<s:url action="softwareSubmissions" namespace="/contest">
                     <s:param name="projectId" value="%{#attr['contestId']}"/>
                     <s:param name="roundType" value="'FINAL'"/>
                 </s:url>" <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:when>
    <c:otherwise>
        <a href="<s:url action="softwareSubmissions" namespace="/contest">
                     <s:param name="projectId" value="%{#attr['contestId']}"/>
                 </s:url>" <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:otherwise>
</c:choose>
