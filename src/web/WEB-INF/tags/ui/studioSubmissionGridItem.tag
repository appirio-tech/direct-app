<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.1 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Submissions Grid
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.service.studio.SubmissionData" %>
<%@ attribute name="last" required="true" type="java.lang.Boolean" %>

<c:set var="submissionId" value="${submission.submissionId}"/>

<li class="${last ? 'class="last"' : ''} ${tcdirect:isCheckedOut(formData.roundType, submission) ? 'prizedSubmission' : ''}">
    <div id="submission-${submissionId}" class="statusSubmissionWrap">
        <div class="statusSubmission"></div>
        <div class="hidden">
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                      styleClass="submissionIMG"/>
        </div>
        <label>Submission ID: <span class="submissionID">${submissionId}</span>(<a href="http://studio.topcoder.com/?module=DownloadSubmission&sbmid=${submissionId}">Download</a>)</label>
        <a href="javascript:;" class="thumbGrid"><span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="200"/>
        </a>

        <ui:submissionAction contestId="${contestId}" submission="${submission}"/>
    </div>
    <!-- End .submissionActionWrap -->
</li>
