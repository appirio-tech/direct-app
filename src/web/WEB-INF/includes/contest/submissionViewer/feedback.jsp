<%--
  - Author: isv, flexme, TCSDEVELOPER
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: Only display the update feedback form when
  - feedback text is null or empty.
  - Version 1.2 (Direct Submission Viewer Release 4 ) change notes: The form for updating feedback is displayed only
  - if the submission is not "Confirmed"
  -
  - Version: 1.2
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the form for submitting feedback for a single Studio contest.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<if:isConfirmedStudioSubmission submission="${viewData.submission}" negate="true">
<div id="submissionFeedback">
    <h4>Feedback:</h4>
    <textarea cols="10" rows="5" id="feedback">${viewData.submission.feedbackText}</textarea>
    <a href="javascript:;" class="button6" id="updateFeedback"><span class="left"><span
            class="right">Save Feedback</span></span></a>
    <div class="clear"></div>
</div>
</if:isConfirmedStudioSubmission>
<!-- End #submissionFeedback -->
