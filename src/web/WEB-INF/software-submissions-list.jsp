<%--
  - Author: isv, TCSASSEMBLER
  - Version 1.4
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the submissions for Software contest in a list view.
  -
  - Version 1.1 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Add dashboard header.    
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Two) change Notes:
  - - add miletone submissions and sub tabs for switch between milestone and final round
  -
  - Version 1.3 (Module Assembly - Adding Contest Approval Feature in Direct) change Notes:
  - - added Approval section
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit Software Milestone Management) change Notes:
  - - updated to support software milestone management
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link type="text/css" media="all" href="/css/direct/milestone-management.css" rel="stylesheet">
    <script src="/scripts/jquery.dataTables-1.9.1.min.js" type="text/javascript"></script>
    <script src="/scripts/milestone-management.js" type="text/javascript"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property
                                        value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

							<jsp:include page="includes/contest/dashboard.jsp"/>
                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2BottomClear">                                    
                                        <s:if test="(not viewData.contestStats.multipleRound) or roundType.toString() == 'FINAL'">    
                                            <div class="containerNoPadding">                          
                                            <s:if test="not viewData.contestStats.multipleRound">
                                                <div id="bankSelection">
                                                <div id="bankSelectionHead">       
                                                <h3>Single Round Submissions</h3>
                                                <ul id="bankSelectionTab">
                                                    <li>
                                                        <a href="/direct/contest/softwareSubmissions?projectId=${formData.projectId}&amp;roundType=FINAL">
                                                            <span>Final (R1)</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </s:if>
                                            <s:else>    
                                                <div id="bankSelection" class="multiFinalDiv">
                                                <div id="bankSelectionHead">  
                                                <ul id="bankSelectionTab" class="multiFinalUL">                                                  
                                                    <li class="off">
                                                        <a href="/direct/contest/softwareSubmissions?projectId=${formData.projectId}&amp;roundType=MILESTONE">
                                                            <span>Milestone (${viewData.contestStats.milestoneSubmissionNumber})</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="/direct/contest/softwareSubmissions?projectId=${formData.projectId}&amp;roundType=FINAL">
                                                            <span>Final (${viewData.contestStats.finalSubmissionNumber})</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </s:else>
                                            </div>
                                            </div>
                                        </s:if>
                                        <s:else>
                                            <div id="tcSoftwareMM">
                                            <div class="mmTabs">
                                                <a class="on" href="/direct/contest/softwareSubmissions?projectId=${formData.projectId}&amp;roundType=MILESTONE"><span>Milestone (${viewData.contestStats.milestoneSubmissionNumber})</span></a>
                                                <a href="/direct/contest/softwareSubmissions?projectId=${formData.projectId}&amp;roundType=FINAL"><span>Final (${viewData.contestStats.finalSubmissionNumber})</span></a>
                                            </div>
                                        </s:else>                                            

                                            <s:if test="roundType.toString() == 'FINAL'">

                                            <s:if test="finalFixes.size > 0">

                                                <h2>Final Fixes</h2>
                                                <table class="softwareStats" cellpadding="0" cellspacing="0">
                                                    <colgroup>
                                                        <col width="10%"/>
                                                        <col width="20%"/>
                                                        <col width="15%"/>
                                                        <col width="15%"/>
                                                        <col width="15%"/>
                                                        <col width="15%"/>
                                                        <col width="10%"/>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th rowspan="2"><span class="icoTHDownload"></span></th>
                                                        <th rowspan="2" class="left">Handle</th>
                                                        <th rowspan="2" class="left">Version</th>
                                                        <th rowspan="2">Final Fix ID</th>
                                                        <th rowspan="2">Final Fix Date</th>
                                                        <th rowspan="2">Status</th>
                                                        <th rowspan="2">Download</th>

                                                    </tr>

                                                    </thead>
                                                    <tbody>
                                                       <s:iterator value="finalFixes" status="rowStatus">
                                                           <tr>
                                                                <s:if test='#rowStatus.count == 1'><s:set var="iconStyle"
                                                                                                          value="'gold'"
                                                                                                          scope="page"/></s:if>
                                                                <s:else><s:set var="iconStyle" value="'normal'"
                                                                               scope="page"/></s:else>
                                                               <td>
                                                                <link:downloadFinalFix contestId="${contestId}"
                                                                                           finalFix="${finalFixSub}"
                                                                                           version="${versionNumber}"
                                                                                           styleClass="icoZip ${iconStyle}"></link:downloadFinalFix>
                                                                </td>
                                                                <td class="left">
                                                                <link:user userId="${finalFixerUserId}"
                                                                           handle="${finalFixerHandle}/>"
                                                                           styleClass="handle"/>
                                                                </td>
                                                               <td class="left">
                                                                  Version <s:property value="versionNumber"/>
                                                                </td>
                                                                <td>
                                                                  <s:property value="uploadId"/>
                                                                </td>
                                                               <td>
                                                                    <s:date name="finalFixDate" format="MM.dd.yyyy"/>
                                                               </td>
                                                               <td>
                                                                  <s:if test='approved == false && reviewed == true'>Rejected</s:if><s:else></s:else>
                                                                </td>
                                                               <td>
                                                                       <link:downloadFinalFix contestId="${contestId}"
                                                                                           finalFix="${finalFixSub}"
                                                                                           version="${versionNumber}"
                                                                                           styleClass="icoZip ${iconStyle}"></link:downloadFinalFix>
                                                                </td>

                                                           </tr>
                                                       </s:iterator>
                                                    </tbody>

                                                </table>

                                            </s:if>


                                                    <s:set var="firstPlaceWinner" value="viewData.firstPlaceWinner"
                                                           scope="page"/>
                                                    <s:set var="secondPlaceWinner" value="viewData.secondPlaceWinner"
                                                           scope="page"/>
                                                <c:if test="${firstPlaceWinner != null}">
                                                    <div id="winnerPanel">
                                                        <div class="winnerCol">
                                                            <s:if test="finalFixes.size > 0">
                                                                    <link:downloadFinalFix
                                                                            contestId="${finalFixes[0].contestId}"
                                                                            finalFix="${finalFixes[0].finalFixSub}"
                                                                            version="${finalFixes[0].versionNumber}"
                                                                            styleClass="downloadFile"></link:downloadFinalFix>
                                                            </s:if>
                                                            <s:else>
                                                                    <link:onlineReviewDownloadSubmission
                                                                            projectId="${firstPlaceWinner.projectId}"
                                                                            submissionId="${firstPlaceWinner.submissionId}"
                                                                            styleClass="downloadFile">
                                                                  </link:onlineReviewDownloadSubmission>
                                                            </s:else>
                                                            <div class="winnerData">
                                                                <h3>1st Place Winner</h3>
                                                                <link:user styleClass="handle"
                                                                           handle="${firstPlaceWinner.handle}"
                                                                           userId="${firstPlaceWinner.id}"/>
                                                                <label class="score">Final Score:
                                                                    <span> <fmt:formatNumber
                                                                            value="${firstPlaceWinner.finalScore}"
                                                                            pattern="##0.00"/></span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <!-- End .winnerCol -->

                                                        <c:if test="${secondPlaceWinner != null}">
                                                            <div class="winnerCol">
                                                                    <link:onlineReviewDownloadSubmission
                                                                            projectId="${secondPlaceWinner.projectId}"
                                                                            submissionId="${secondPlaceWinner.submissionId}"
                                                                            styleClass="downloadFile">
                                                                </link:onlineReviewDownloadSubmission>
                                                                <div class="winnerData">
                                                                    <h3>2nd Place Winner</h3>
                                                                    <link:user styleClass="handle"
                                                                               handle="${secondPlaceWinner.handle}"
                                                                               userId="${secondPlaceWinner.id}"/>
                                                                    <label class="score">Final Score:
                                                                        <span> <fmt:formatNumber
                                                                                value="${secondPlaceWinner.finalScore}"
                                                                                pattern="##0.00"/></span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <!-- End .winnerCol -->
                                                        </c:if>
                                                    </div>
                                                    <!-- End #winnerPanel -->
                                                </c:if>

                                                <s:set var="viewData" value="viewData" scope="page"/>
                                                    <h2>All Final Round Submissions</h2>

<%--
                                                <table class="${viewData.showApproval ? 'softwareStatsApproval' : 'softwareStats'}" 
--%>
                                                <table class="softwareStats" cellpadding="0" cellspacing="0">
                                                    <colgroup>
                                                        <col width="5%"/>
                                                        <col width="16%"/>
                                                        <col width="10%"/>
                                                        <col width="10%"/>
                                                        <col width="10%"/>
                                                        <col width="8%"/>
                                                        <col width="8%"/>
                                                        <c:forEach begin="1" end="${viewData.reviewersCount}" step="1">
                                                            <col width="${viewData.reviewScoreColumnsWidth}%"/>
                                                        </c:forEach>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th rowspan="2"><span class="icoTHDownload"></span></th>
                                                        <th rowspan="2" class="left">Handle</th>
                                                        <th rowspan="2">ID</th>
                                                        <th rowspan="2">Date Submitted</th>
                                                        <th rowspan="2">Screening Score</th>
                                                        <th rowspan="2">Initial Score</th>
                                                        <th rowspan="2">Final Score</th>
                                                        <th colspan="${viewData.reviewersCount}"
                                                            class="reviewerScoreBg title">
                                                            <span>Reviewer's Score</span></th>
                                                    </tr>

                                                    <tr class="reviewerScoreBg">
                                                        <c:forEach items="${viewData.reviewers}" var="reviewer">
                                                            <th>
                                                                <link:user userId="${reviewer.id}"
                                                                           handle="${reviewer.handle}"/>
                                                            </th>
                                                        </c:forEach>
                                                    </tr>

                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${viewData.submissions}" var="submission"
                                                               varStatus="loop">
                                                        <c:set var="passedScreening" value="${submission.passedScreening}"/>
                                                        <c:set var="passedReview" value="${submission.passedReview}"/>
                                                        <c:set var="submitter" value="${submission.submitter}"/>
                                                            <c:set var="isWinner1"
                                                                   value="${submitter.id eq firstPlaceWinner.id}"/>
                                                            <c:set var="isWinner2"
                                                                   value="${submitter.id eq secondPlaceWinner.id}"/>
                                                        <c:set var="passedScreening" value="${submission.passedScreening}"/>
                                                        <c:set var="passedReview" value="${submission.passedReview}"/>
                                                        <c:choose>
                                                            <c:when test="${isWinner1}">
                                                                <c:set var="icoStyle" value="gold"/>
                                                            </c:when>
                                                            <c:when test="${isWinner2}">
                                                                <c:set var="icoStyle" value="silver"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="icoStyle" value="normal"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <tr ${loop.index mod 2 > 0 ? 'class="alt"' : ''}>
                                                            <td>
                                                                    <link:onlineReviewDownloadSubmission
                                                                            projectId="${param.projectId}"
                                                                            submissionId="${submission.submissionId}"
                                                                            styleClass="icoZip ${icoStyle}"></link:onlineReviewDownloadSubmission>
                                                            </td>
                                                            <td class="left">
                                                                <link:user userId="${submitter.id}"
                                                                           handle="${submitter.handle}"
                                                                           styleClass="handle"/>
                                                            </td>
                                                            <td>
                                                                <strong><c:out value="${submission.submissionId}"/></strong>
                                                            </td>
                                                            <td>
                                                                <fmt:formatDate value="${submission.submissionDate}"
                                                                                pattern="MM.dd.yyyy"/>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${submission.screeningScore ne null}">
                                                                        <link:onlineReviewScreeningScorecard
                                                                                reviewId="${submission.screeningReview.reviewId}"
                                                                                styleClass="${passedScreening ? 'icoTrue' : 'icoFalse'}">
                                                                                <fmt:formatNumber
                                                                                        value="${submission.screeningScore}"
                                                                                              pattern="##0.00"/>
                                                                        </link:onlineReviewScreeningScorecard>
                                                                    </c:when>
                                                                    <c:otherwise>n/a</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${passedScreening}">
                                                                        <c:choose>
                                                                            <c:when test="${submission.initialScore ne null}">
                                                                                    <fmt:formatNumber
                                                                                            value="${submission.initialScore}"
                                                                                                  pattern="##0.00"/>
                                                                            </c:when>
                                                                            <c:otherwise>n/a</c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>&nbsp;</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <strong>
                                                                    <c:choose>
                                                                        <c:when test="${passedScreening}">
                                                                            <c:choose>
                                                                                <c:when test="${submission.finalScore ne null}">
                                                                                    <span class="${submission.passedReview ? 'icoPassedReview' : 'icoFailedReview'}">
                                                                                        <fmt:formatNumber
                                                                                                value="${submission.finalScore}"
                                                                                                      pattern="##0.00"/>
                                                                                    </span>
                                                                                </c:when>
                                                                                <c:otherwise>n/a</c:otherwise>
                                                                            </c:choose>
                                                                        </c:when>
                                                                        <c:otherwise>&nbsp;</c:otherwise>
                                                                    </c:choose>
                                                                </strong>
                                                            </td>

                                                            <c:forEach items="${viewData.reviewers}" var="reviewer">
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${passedScreening}">
                                                                            <c:set var="review"
                                                                                   value="${tcdirect:getReview(submission, reviewer.id)}"/>
                                                                            <c:choose>
                                                                                <c:when test="${review ne null and review.committed}">
                                                                                    <link:onlineReviewScorecard
                                                                                            reviewId="${review.reviewId}" 
                                                                                            styleClass="scores">
                                                                                            <fmt:formatNumber
                                                                                                    value="${review.finalScore}"
                                                                                                          pattern="##0.00"/>
                                                                                    </link:onlineReviewScorecard>
                                                                                </c:when>
                                                                                <c:otherwise>n/a</c:otherwise>
                                                                            </c:choose>
                                                                        </c:when>
                                                                        <c:otherwise>&nbsp;</c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </c:forEach>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            <c:if test="${viewData.showApproval}">
                                                <div class="ApprovalSection">
                                                    <c:choose>
                                                        <c:when test="${viewData.approvalCommitted}">
                                                            <p>The submission has been approved.</p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p>
                                                                <input type="radio" name="radioApprove"
                                                                       id="radioApproveApproved"/>
                                                                <label for="radioApproveApproved">Approved.</label>
                                                                All requirements are met.
                                                            </p>

                                                            <p>
                                                                <input type="radio" name="radioApprove"
                                                                       id="radioApproveReject"/>
                                                                <label for="radioApproveReject">Rejected.</label>
                                                                At least one requirement has not been met and is
                                                                identified
                                                                below.
                                                            </p>

                                                            <p class="approvalRejectSection hide">
                                                                <textarea class="hint rejectTextarea" cols="" rows=""
                                                                          name="Reasons for rejection.">Reasons for rejection.</textarea>
                                                            </p>

                                                            <div class="buttons">
                                                                <a class="buttonRed1" href="javascript:;" 
                                                                   rel="${firstPlaceWinner.submissionId}_${firstPlaceWinner.projectId}"
                                                                   id="submitApprovalButton"><span>SAVE AND MARK COMPLETE</span></a>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:if>

                                            </s:if>
                                            <s:if test="roundType.toString() == 'MILESTONE'">
                                              <c:choose>
                                                <c:when test="${inMilestoneSubmissionPhase || inMilestoneReviewPhase && !hasWritePermission}">
                                                    <div class="mmDataTable"> 
                                                        <table cellpadding="0" cellspacing="0">
                                                            <colgroup>
                                                                <col width="30%"/>
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th>
                                                                        Submission
                                                                        <a class="download" href="<s:url action='downloadAllSoftwareMilestoneSubmissions' namespace='/contest'/>?projectId=${param.projectId}">Download All</a>
                                                                    </th>
                                                                    <th>
                                                                        Submission Date/Time
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${viewData.submissions}" var="submission"
                                                                           varStatus="loop">
                                                                <tr>
                                                                    <td>
                                                                        <link:onlineReviewDownloadSubmission
                                                                                projectId="${param.projectId}"
                                                                                submissionId="${submission.submissionId}"
                                                                                styleClass="submission">#${submission.submissionId}</link:onlineReviewDownloadSubmission>                                                                    
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatDate value="${submission.submissionDate}"
                                                                                        pattern="MM/dd/yyyy HH:mm "/>EST
                                                                    </td>
                                                                </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>                                
                                                    </div>
                                                </c:when>
                                                <c:when test="${afterMilestoneReviewPhase || inMilestoneReviewPhase && milestoneReviewCommitted}">                                                
                                                    <div class="notify">
                                                        To pick milestone winners click on the Select Placement icons beside each submission. Selections can be removed and/or replaced any time before you lock in the milestones. Do not forget to provide milestone general feedback and individual feedback.
                                                    </div>                        
                                                    <div class="slotsMask">
                                                        <div class="slots">
                                                            <h3>Milestone Winners</h3>
                                                            <ul>
                                                                <s:set var="places" value="{'1st','2nd','3rd','4th','5th'}"/>
                                                                <c:forEach begin="1" end="${milestoneWinnersNumber}" step="1" var="num">                                                                
                                                                <li class="slot">
                                                                    <span class="slotR">
                                                                        <span class="text">
                                                                            <span class="place">${places[num-1]} Prize ($${viewData.milestonePrizeAmount})</span>
                                                                        </span>
                                                                    </span>
                                                                </li>
                                                                </c:forEach>
                                                            </ul> 
                                                        </div>
                                                        <div class="borderLine"></div>
                                                        <div class="feedback">
                                                            <h3>General Feedback</h3>
                                                            <div class="fbArea">${viewData.milestoneSubmissionsGeneralFeedback}</div>
                                                        </div>
                                                        <div class="corner tl"></div>
                                                        <div class="corner tr"></div>
                                                        <div class="corner bl"></div>
                                                        <div class="corner br"></div>
                                                    </div>                        
                                                    <div class="mmDataTable">
                                                        <table cellpadding="0" cellspacing="0" class="readonly">
                                                            <colgroup>
                                                                <col width="29%"/>
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th>
                                                                        Submission
                                                                        <a class="download" href="<s:url action='downloadAllSoftwareMilestoneSubmissions' namespace='/contest'/>?projectId=${param.projectId}">Download All</a>
                                                                    </th> 
                                                                    <th>
                                                                        Feedback
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <s:iterator value="viewData.submissions" var="submission" status="loop">
                                                                <tr>
                                                                    <td>
                                                                        <input type="hidden" name="rankInput" class="finalRank" value="${11-submission.milestoneReviewScore/10}"/>
                                                                        <link:onlineReviewDownloadSubmission
                                                                                projectId="${param.projectId}"
                                                                                submissionId="${submission.submissionId}"
                                                                                styleClass="submission">#${submission.submissionId}</link:onlineReviewDownloadSubmission> 
                                                                        <p class="time"><fmt:formatDate value="${submission.submissionDate}"
                                                                                        pattern="MM/dd/yyyy HH:mm "/>EST</p>
                                                                    </td>
                                                                    <td class="fbTd">
                                                                        <div class="fbMask">
                                                                            <div class="fbArea show">${submission.milestoneFeedback}</div>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                </s:iterator>
                                                            </tbody>
                                                        </table>
                                                    </div>              
                                                </c:when>
                                                <c:when test="${inMilestoneReviewPhase && !milestoneReviewCommitted}">
                                                    <div class="notify"> 
                                                        To pick milestone winners click on the Select placement icons beside each submission. Selections can be removed and/or replaced any time before you lock in the Milestones. Do not forget to provide milestone general feedback and individual feedback.
                                                    </div>
                        
                                                    <div class="slotsMask">
                                                        <div class="slots">
                                                            <h3>Milestone Winners</h3>
                                                            <ul>
                                                                <s:set var="places" value="{'1st','2nd','3rd','4th','5th'}"/>
                                                                <c:forEach begin="1" end="${viewData.milestonePrizeNumber}" step="1" var="num">                                                                
                                                                <li class="slot">
                                                                    <span class="slotR">
                                                                        <span class="text">
                                                                            <span class="place">${places[num-1]} Prize ($${viewData.milestonePrizeAmount})</span>
                                                                        </span>
                                                                    </span>
                                                                </li>
                                                                </c:forEach>
                                                            </ul>
                                                            <a class="clearSlots" href="javascript:;">Clear Slots</a>
                                                        </div>
                                                        <div class="borderLine"></div>
                                                        <div class="feedback">
                                                            <h3>General Feedback</h3>
                                                            <s:if test="viewData.milestoneSubmissionsGeneralFeedback == ''||viewData.milestoneSubmissionsGeneralFeedback == null">
                                                                <div class="fbArea"></div>
                                                                <div class="action">
                                                                    <a href="javascript:;" class="add">Provide Feedback</a>
                                                                    <a href="javascript:;" class="edit hide">Edit</a>
                                                                </div>
                                                            </s:if>
                                                            <s:else>                                                            
                                                                <div class="fbArea show">${viewData.milestoneSubmissionsGeneralFeedback}</div>
                                                                <div class="action">
                                                                    <a href="javascript:;" class="add hide">Provide Feedback</a>
                                                                    <a href="javascript:;" class="edit">Edit</a>
                                                                </div>
                                                            </s:else>
                                                        </div>
                                                        <div class="corner tl"></div>
                                                        <div class="corner tr"></div>
                                                        <div class="corner bl"></div>
                                                        <div class="corner br"></div> 
                                                    </div>
                        
                                                    <div class="mmDataTable">
                                                        <input type="hidden" id="projectIdInput" value="${formData.projectId}"/>
                                                        <table cellpadding="0" cellspacing="0">
                                                            <colgroup>
                                                                <col width="190px"/>
                                                                <col width="180px"/> 
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th>
                                                                        Submission
                                                                        <a class="download" href="<s:url action='downloadAllSoftwareMilestoneSubmissions' namespace='/contest'/>?projectId=${param.projectId}">Download All</a>
                                                                    </th>
                                                                    <th>
                                                                        Select Placement
                                                                    </th>
                                                                    <th>
                                                                        Feedback
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <s:iterator value="viewData.submissions" var="submission" status="loop">
                                                                <tr>
                                                                    <td>
                                                                        <input type="hidden" name="rankInput" value="${11-submission.milestoneReviewScore/10}"/>
                                                                        <link:onlineReviewDownloadSubmission
                                                                                projectId="${param.projectId}"
                                                                                submissionId="${submission.submissionId}"
                                                                                styleClass="submission">#${submission.submissionId}</link:onlineReviewDownloadSubmission> 
                                                                        <p class="time"><fmt:formatDate value="${submission.submissionDate}"
                                                                                        pattern="MM/dd/yyyy HH:mm "/>EST</p>
                                                                    </td>
                                                                    <td>
                                                                        <span class="prizes">
                                                                            <c:forEach begin="1" end="${viewData.milestonePrizeNumber}" step="1" var="num"> 
                                                                            <a href="javascript:;" class="prize${num}">${num}</a>
                                                                            </c:forEach>
                                                                        </span>
                                                                    </td>
                                                                    <td class="fbTd">
                                                                        <div class="fbMask">
                                                                            <s:if test="milestoneFeedback==''||milestoneFeedback==null">
                                                                                <div class="fbArea"></div>
                                                                                <div class="edit hide">
                                                                                    <a href="javascript:;">Edit</a>
                                                                                </div>
                                                                                <div class="add">
                                                                                    <a href="javascript:;">Provide Feedback</a>
                                                                                </div>
                                                                            </s:if>
                                                                            <s:else>
                                                                                <div class="fbArea" style="display:block">${submission.milestoneFeedback}</div>
                                                                                <div class='edit'>
                                                                                    <a href="javascript:;">Edit</a>
                                                                                </div>
                                                                                <div class="add hide">
                                                                                    <a href="javascript:;">Provide Feedback</a>
                                                                                </div>
                                                                            </s:else>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                </s:iterator>
                                                            </tbody>                                    
                                                        </table>
                                                    </div>
                        
                                                    <div class="ops">
                                                        <a href="javascript:;" class="saveit">Save &amp; Continue later</a> 
                                                        <a class="newButton1 lock" href="javascript:;"><span class="btnR"><span class="btnC"><span class="icon">LOCK IN MILESTONES</span></span></span></a>
                                                    </div>              
                                                </c:when>
                                                <c:otherwise></c:otherwise>
                                              </c:choose>
                                            </s:if>
                                            </div>
                                            <!-- End .container2Content -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->

                            <a href="#" class="button1 backToTop"><span>Back To Top</span></a>
                        </div>
                    </div>

                    <div class="clear"></div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>
            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
