<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.1 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Submissions List
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.service.studio.SubmissionData" %>

<c:set var="submissionId" value="${submission.submissionId}"/>

<tr id="submission-${submissionId}">
    <td></td>
    <td>
        <a href="javascript:;"
           class="${tcdirect:isCheckedOut(formData.roundType, submission) ? 'prizedSubmission' : ''} thumbList">
            <span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="130"/>
            <span class="hidden">
                <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                          styleClass="submissionIMG"/>
            </span>
        </a>

    </td>
    <td class="submissionID" title="${submissionId}">${submissionId} (<a href="http://studio.topcoder.com/?module=DownloadSubmission&sbmid=${submissionId}">Download</a>)</td>
    <td><span class="icoBankLocation"></span></td>
    <td>
        <ui:submissionAction contestId="${contestId}" submission="${submission}"/>
    </td>
    <td></td>
</tr>

