<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.3 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  -
  - Version 1.2 (TC Direct Replatforming Release 3  ) change notes: Update the type of Submission and update the Download link. 
  -
  - Version 1.3 (TC Direct Replatforming Release 5) change notes: Update online review download submission link tag.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Submissions Grid
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="if" tagdir="/WEB-INF/tags/conditions" %>
<%@ taglib prefix="link" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.management.deliverable.Submission" %>
<%@ attribute name="last" required="true" type="java.lang.Boolean" %>

<c:set var="submissionId" value="${submission.id}"/>

<li class="${last ? 'class="last"' : ''} <if:isConfirmedStudioSubmission negate="false">prizedSubmission</if:isConfirmedStudioSubmission>">
    <div id="submission-${submissionId}" class="statusSubmissionWrap">
        <div class="statusSubmission"></div>
        <div class="hidden">
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                      styleClass="submissionIMG"/>
        </div>
        <label>Submission ID: <span class="submissionID">${submissionId}</span>(<link:studioSubmissionDownload submissionId="${submission.id}">Download</link:studioSubmissionDownload>)</label>
        <a href="javascript:;" class="thumbGrid"><span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="200"/>
        </a>

        <ui:submissionAction contestId="${contestId}" submission="${submission}"/>
    </div>
    <!-- End .submissionActionWrap -->
</li>
