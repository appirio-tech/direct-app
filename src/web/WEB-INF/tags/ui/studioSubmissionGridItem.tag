<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Submissions Grid 
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="last" required="true" type="java.lang.Boolean" %>

<li ${last ? 'class="last"' : ''}>
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

        <ui:submissionAction contestId="${contestId}" submissionId="${submissionId}"/>
    </div>
    <!-- End .submissionActionWrap -->
</li>
