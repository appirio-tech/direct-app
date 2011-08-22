<%--
  - Author: isv, TCSASSEMBER
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: Update the Download link to download from OR. 
  - Version 1.2 (TC Direct Replatforming Release 5  ) chnage notes: Update the online review download submission link tag.
  - 
  - Version: 1.2
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders Slot Title area to be displayed on Studio Submissions Grid, List and
  - Single views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<s:set var="isCurrentMilestoneRound" value="formData.roundType.toString() == 'MILESTONE'" scope="page"/>

<div class="SubmissionSlotTitle">
    <s:if test="formData.viewType.toString() == 'GRID'">
        <h3 id="subFilterText">All Submissions</h3>
        <link:studioSubmissionsList contestId="${contestId}" milestoneRound="${isCurrentMilestoneRound}"
                                    styleClass="toggleViewList">Switch to List View</link:studioSubmissionsList>
    </s:if>
    <s:if test="formData.viewType.toString() == 'LIST'">
        <h3 id="subFilterText">All Submissions</h3>
        <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${isCurrentMilestoneRound}"
                                    styleClass="toggleViewList">Switch to Grid View</link:studioSubmissionsGrid>
    </s:if>
    <s:if test="formData.viewType.toString() == 'SINGLE'">
        <h3>Submission ID: <s:property value="formData.submissionId"/>(<link:studioSubmissionDownload submissionId="${viewData.submission.id}">Download</link:studioSubmissionDownload>)</h3>
        <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${isCurrentMilestoneRound}"
                                    styleClass="toggleViewList">See More Submissions</link:studioSubmissionsGrid>
    </s:if>
    <s:if test="viewData.hasMilestoneRound && formData.roundType.toString() != 'MILESTONE'">
        <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${true}"
                                    styleClass="toggleViewMilestone">Show Milestone Submissions</link:studioSubmissionsGrid>
    </s:if>
</div>
<!-- End .SubmissionSlotTitle -->
