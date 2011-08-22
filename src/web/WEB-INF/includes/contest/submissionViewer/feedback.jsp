<%--
  - Author: isv, flexme, TCSDEVELOPER
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: Only display the update feedback form when
  - feedback text is null or empty.
  - Version 1.2 (Direct Submission Viewer Release 4 ) change notes: The form for updating feedback is displayed only
  - if the submission is not "Confirmed"
  - Version 1.3 (TC Direct Replatforming Release 3  ) change notes: update some tags parameters according to the DTO and the new tags.
  -
  - Version: 1.3
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the form for submitting feedback for a single Studio contest.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<if:isConfirmedStudioSubmission negate="true">
<div id="submissionFeedback">
    <h4>Feedback:</h4>
    <textarea cols="10" rows="5" id="feedback">${viewData.feedbackText}</textarea>
    <a href="javascript:;" class="button6" id="updateFeedback"><span class="left"><span
            class="right">Save Feedback</span></span></a>
    <div class="clear"></div>
</div>
</if:isConfirmedStudioSubmission>
<!-- End #submissionFeedback -->
