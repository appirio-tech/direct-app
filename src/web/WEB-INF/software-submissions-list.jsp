<%--
  - Author: TCSDEVELOPER
  - Version 1.0 (Direct Software Submission Viewer assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the submissions for Software contest in a list view.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/htmlhead.jsp"/>
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

                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2BottomClear">
                                            <div class="containerNoPadding">
                                                <s:set var="firstPlaceWinner" value="viewData.firstPlaceWinner" scope="page"/>
                                                <s:set var="secondPlaceWinner" value="viewData.secondPlaceWinner" scope="page"/>
                                                <c:if test="${firstPlaceWinner != null}">
                                                    <div id="winnerPanel">
                                                        <div class="winnerCol">
                                                            <link:onlineReviewDownloadSubmission projectId="${firstPlaceWinner.projectId}"
                                                                    submissionId="${firstPlaceWinner.submissionId}" styleClass="icoZip ${icoStyle}">
                                                            </link:onlineReviewDownloadSubmission>
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
                                                                <link:onlineReviewDownloadSubmission projectId="${secondPlaceWinner.projectId}"
                                                                    submissionId="${secondPlaceWinner.submissionId}" styleClass="icoZip ${icoStyle}">
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
                                                <h2>All Submissions</h2>

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
                                                        <c:set var="isWinner1" value="${submitter.id eq firstPlaceWinner.id}"/>
                                                        <c:set var="isWinner2" value="${submitter.id eq secondPlaceWinner.id}"/>
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
                                                                <link:onlineReviewDownloadSubmission projectId="${param.projectId}"
                                                                    submissionId="${submission.submissionId}" styleClass="icoZip ${icoStyle}"></link:onlineReviewDownloadSubmission>
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
                                                                        <fmt:formatNumber value="${submission.screeningScore}"
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
                                                                                <fmt:formatNumber value="${submission.initialScore}"
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
                                                                                    <fmt:formatNumber value="${submission.finalScore}"
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
                                                                                        <fmt:formatNumber value="${review.finalScore}"
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
