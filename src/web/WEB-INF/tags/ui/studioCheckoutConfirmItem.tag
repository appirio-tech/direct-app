<%--
  - Author: FireIce, isv
  - Version: 1.1
  - Copyright (C) 2011-2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Checkout confirmation
  - page for selected contest.
  -
  - Version 1.1 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) change notes: 
  -   Updated as per latest prototype
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="feedbackText" required="true" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<tr id="submission-${submissionId}" style="display:none;">
    <td>&nbsp;</td>
    <td>
        <div class="thumbCheckout">
            <span class="icoBankLocation posAbsolute <c:if test="${not empty styleClass}">${styleClass}</c:if>"></span>
            <a href="<s:url action="studioSubmission" namespace="/contest">
                            <s:param name="contestId" value="%{#attr['contestId']}"/>
                            <s:param name="formData.submissionId" value="%{#attr['submissionId']}"/>
                            <s:param name="formData.roundType" value="formData.roundType"/>
                            </s:url>" class="thumbList"><span></span><ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="136"/></a>
        </div>
    </td>
    <td>&nbsp;</td>
    <td class="left alignTop">
        <div class="submissionDetails">
            <label>${submissionId}</label>
            <div class="submissionDetailsContent">${feedbackText}</div>
            <a href="https://www.topcoder.com/direct/cockpit/impersonation/cockpitStudio.do?&sbmid=${submissionId}&sbt=original" class="downloadSubmission"><span>DOWNLOAD</span></a>
            <div class="clear"></div>
        </div>
    </td>
    <td class="last">&nbsp;</td>
 </tr>
