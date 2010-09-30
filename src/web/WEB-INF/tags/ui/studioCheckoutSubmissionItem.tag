<%--
  - Author: flexme, TCSDEVELOPER
  - Version: 1.1 (Submission Viewer Release 3 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: The form for saving feedback is displayed
  - only if the submission is not "Confirmed". Added "isConfirmed" attribute.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Checkout
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="feedbackText" required="true" type="java.lang.String" %>
<%@ attribute name="isConfirmed" required="true" type="java.lang.Boolean" %>

<tr id="submission-${submissionId}" style="display:none;">
    <td></td>
    <td>
        <div class="thumbCheckout">
            <span class="icoBankLocation posAbsolute"></span>
            <a href="<s:url action="studioSubmission" namespace="/contest">
                            <s:param name="contestId" value="%{#attr['contestId']}"/>
                            <s:param name="formData.submissionId" value="%{#attr['submissionId']}"/>
                            <s:param name="formData.roundType" value="formData.roundType"/>
                            </s:url>" class="thumbList">
                <span></span><ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="136"/></a>
        </div>
    </td>
    <td>${submissionId}</td>
    <td class="left">
        <c:choose>
            <c:when test="${isConfirmed}">
                ${feedbackText}
            </c:when>
            <c:otherwise>
                <textarea class="txtFeedback" cols="10" rows="5" id="feedbackText${submissionId}">${feedbackText}</textarea>
                <a href="#" class="button6 saveFeedback" rel="${submissionId}">
                    <span class="left"><span class="right">Save Feedback</span></span></a>
            </c:otherwise>
        </c:choose>
    </td>
    <td></td>
</tr>