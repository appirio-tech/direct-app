<%--
  - Author: isv
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: Update the Download link to download from OR. 
  - Version 1.2 (TC Direct Replatforming Release 5  ) chnage notes: Update the online review download submission link tag.
  - Version 1.3 (Module Assembly - TopCoder Studio and Cockpit Download All Submissions Feature) chnage notes: 
  -                                Added DOWNLOAD ALL button
  - 
  - Version: 1.3
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders Slot Title area to be displayed on Studio Submissions Grid, List and
  - Single views.
--%>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<s:set var="isCurrentCheckpointRound" value="formData.roundType.toString() == 'CHECKPOINT'" scope="page"/>
<c:set var="studioServerName" value="<%=ApplicationServer.STUDIO_SERVER_NAME%>"/>

<div class="SubmissionSlotTitle">
    <div class="left">
        <s:if test="formData.viewType.toString() == 'GRID'">
            <h3 id="subFilterText">All Submissions</h3>
            <link:studioSubmissionsList contestId="${contestId}" checkpointRound="${isCurrentCheckpointRound}"
                                        styleClass="toggleViewList">Switch to List View</link:studioSubmissionsList>
        </s:if>
        <s:if test="formData.viewType.toString() == 'LIST'">
            <h3 id="subFilterText">All Submissions</h3>
            <link:studioSubmissionsGrid contestId="${contestId}" checkpointRound="${isCurrentCheckpointRound}"
                                        styleClass="toggleViewList">Switch to Grid View</link:studioSubmissionsGrid>
        </s:if>
        <s:if test="formData.viewType.toString() == 'SINGLE'">
            <h3>Submission ID: <s:property value="formData.submissionId"/>(<link:studioSubmissionDownload
                    submissionId="${viewData.submission.id}">Download</link:studioSubmissionDownload>)</h3>
            <link:studioSubmissionsGrid contestId="${contestId}" checkpointRound="${isCurrentCheckpointRound}"
                                        styleClass="toggleViewList">See More Submissions</link:studioSubmissionsGrid>
        </s:if>
        <s:if test="viewData.hasCheckpointRound && formData.roundType.toString() != 'CHECKPOINT'">
            <link:studioSubmissionsGrid contestId="${contestId}" checkpointRound="${true}"
                                        styleClass="toggleViewCheckpoint">Show Checkpoint Submissions</link:studioSubmissionsGrid>
        </s:if>
    </div>
    <c:if test="${viewData.submissionsCount > 0}">
        <div class="right">
            <a class="downloadAllbtn"
               href="https://${studioServerName}?module=DownloadAllSubmissions&amp;ct=${contestId}&amp;round=${fn:toLowerCase(formData.roundType)}&amp;type=preview">
                <span>DOWNLOAD ALL</span></a>
        </div>
    </c:if>
</div>
<!-- End .SubmissionSlotTitle -->
