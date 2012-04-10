<%--
  - Author: isv
  - Version 1.3 (Release Assembly - TC Direct Cockpit Release Two)
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
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/htmlhead.jsp"/>
    <!-- ${viewData.approvalCommitted} ${viewData.showApproval}-->
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

                                            <div class="containerNoPadding">

                                            <div id="bankSelection">
                                                <div id="bankSelectionHead">

                                                    <s:if test="viewData.contestStats.multipleRound">
                                                        <h3>Multiple Rounds Submissions</h3>
                                                    </s:if>
                                                    <s:else>
                                                        <h3>Single Round Submissions</h3>
                                                    </s:else>

                                                    <ul id="bankSelectionTab">
                                                        <s:if test="viewData.contestStats.multipleRound">
                                                            <li
                                                                    <s:if test="roundType.toString() == 'FINAL'">class="off"</s:if>>
                                                                <a href="/direct/contest//softwareSubmissions?projectId=${formData.projectId}&amp;roundType=MILESTONE">
                                                                    <span>Milestone (R1)</span>
                                                                </a>
                                                            </li>
                                                        </s:if>
                                                            <li
                                                                    <s:if test="roundType.toString() == 'MILESTONE'">class="off"</s:if> >
                                                                <a href="/direct/contest//softwareSubmissions?projectId=${formData.projectId}&amp;roundType=FINAL">
                                                                    <s:if test="viewData.contestStats.multipleRound">
                                                                        <span>Final (R2)</span>
                                                                    </s:if>
                                                                    <s:else>
                                                                        <span>Final (R1)</span>
                                                                    </s:else>
                                                                </a>
                                                            </li>
                                                    </ul>
                                                </div>
                                            </div>

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
                                                                                        <fmt:formatNumber
                                                                                                value="${submission.finalScore}"
                                                                                                      pattern="##0.00"/>
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
                                                <h2>All Milestone Round Submissions</h2>

                                                <table class="softwareStats" cellpadding="0" cellspacing="0">
                                                    <colgroup>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th rowspan="1"><span class="icoTHDownload"></span></th>
                                                        <th rowspan="1" class="left">Handle</th>
                                                        <th rowspan="1">Milestone Submission ID</th>
                                                        <th rowspan="1">Date Submitted</th>
                                                        <th rowspan="1">Is Milestone Winner ?</th>
                                                    </tr>

                                                    </thead>
                                                    <tbody>

                                                    <c:forEach items="${viewData.submissions}" var="submission"
                                                               varStatus="loop">
                                                        <c:set var="passedScreening"
                                                               value="${submission.passedScreening}"/>
                                                        <c:set var="passedReview" value="${submission.passedReview}"/>
                                                        <c:set var="submitter" value="${submission.submitter}"/>
                                                        <c:set var="isWinner" value="false"/>
                                                        <c:forEach items="${viewData.milestoneWinners}" var="milestoneWinner">
                                                            <c:choose>
                                                                <c:when test="${submitter.id eq milestoneWinner.id}">
                                                                      <c:set var="isWinner" value="true"/>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>
                                                        <c:set var="passedReview" value="${submission.passedReview}"/>
                                                        <c:choose>
                                                            <c:when test="${isWinner}">
                                                                <c:set var="icoStyle" value="gold"/>
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
                                                                <strong><c:out
                                                                        value="${submission.submissionId}"/></strong>
                                                            </td>
                                                            <td>
                                                                <fmt:formatDate value="${submission.submissionDate}"
                                                                                pattern="MM.dd.yyyy"/>
                                                            </td>

                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${submission.passedReview}">
                                                                        <c:choose>
                                                                            <c:when test="${isWinner}">
                                                                                Milestone Winner
                                                                            </c:when>
                                                                            <c:when test="${submission.finalScore ne null}">
                                                                            </c:when>
                                                                            <c:otherwise>n/a</c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>Not Passed Review</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
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
