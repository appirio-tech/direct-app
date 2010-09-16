<%--
  - Author: isv, flexme
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes:
  - 1.Remove "bank:" row.
  - 2.Hide "Submitter Notes:" row.
  - 3.Display Feedback when feedback text is not null or empty.
  - 4.include the contestVars.jsp.
  -
  - Version: 1.1
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the single submission for Studio contest.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/contest/submissionViewer/submissionViewerHtmlHead.jsp"/>
</head>

<s:set var="submission" value="viewData.submission" scope="page"/>
<s:set var="previousSubmissionId" value="viewData.previousSubmissionId" scope="page"/>
<s:set var="nextSubmissionId" value="viewData.nextSubmissionId" scope="page"/>
<s:set var="roundType" value="formData.roundType.toString()" scope="page"/>

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
                                </s:url>"><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property
                                        value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/contest/contestStats.jsp"/>

                            <!-- End .projectsStats -->

                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2BottomClear">
                                            <div class="containerNoPadding">

                                                <jsp:include
                                                        page="includes/contest/submissionViewer/bankSelection.jsp"/>

                                                <ul id="navSubmissionSlideshowTop">
                                                    <li>
                                                        <link:studioSubmission
                                                            id="navSubmissionSlideshowTopPrev"
                                                            submissionId="${previousSubmissionId}"
                                                            contestId="${submission.contestId}"
                                                            roundType="${roundType}">
                                                            Previous Submission
                                                        </link:studioSubmission>
                                                    </li>
                                                    <li class="dot"></li>
                                                    <li>
                                                        <link:studioSubmission
                                                            id="navSubmissionSlideshowTopNext"
                                                            submissionId="${nextSubmissionId}"
                                                            contestId="${submission.contestId}"
                                                            roundType="${roundType}">
                                                            Next Submission
                                                        </link:studioSubmission>
                                                    </li>
                                                </ul>


                                                <jsp:include page="includes/contest/submissionViewer/slotTitle.jsp"/>
                                                <div id="singleSubmissionContainer">

                                                    <div id="singleSubmissionSlide">
                                                        <a href="javascript:;" id="navSingleSubmissionSlidePrev"
                                                           class="navSingleSubmissionSlidePrev"></a>
                                                        <a href="javascript:;" id="navSingleSubmissionSlideNext"
                                                           class="navSingleSubmissionSlideNext"></a>

                                                        <ul id="carouselSingle">
                                                            <ui:studioCarouselSubmission
                                                                    contestId="${submission.contestId}"
                                                                    submissionId="${submission.submissionId}"
                                                                    artifactNum="1"/>
                                                            <!-- Carousel images -->
                                                            <c:forEach begin="2" end="${submission.artifactCount}"
                                                                       varStatus="status">
                                                                <ui:studioCarouselSubmission
                                                                        contestId="${submission.contestId}"
                                                                        submissionId="${submission.submissionId}"
                                                                        artifactNum="${status.index}"/>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                    <!-- End #singleSubmissionSlide -->

                                                    <s:push value="viewData.submission">
                                                        <div id="submissionData">
                                                            <table>
                                                                <tr>
                                                                    <td class="label">Submission ID:</td>
                                                                    <td><s:property value="submissionId"/></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="label">Date Submitted:</td>
                                                                    <td>
                                                                        <s:set var="submissionDate"
                                                                               value="submittedDate.toGregorianCalendar().getTime()"
                                                                               scope="page"/>
                                                                        <fmt:formatDate value="${submissionDate}"
                                                                                        pattern="MMM dd, yyyy, hh:mm:ss aa"/>
                                                                    </td>
                                                                </tr>
                                                                <!-- TODO: It will be used later. -->
                                                                <tr style="display:none;">
                                                                    <td class="label">Submitter Notes:</td>
                                                                    <td>This is my submission, if you’re reading this,
                                                                        this have passed
                                                                        screening, The font I used
                                                                        was Arial and Tahoma.
                                                                    </td>
                                                                </tr>
                                                                <s:if test="viewData.submission.feedbackText != null && viewData.submission.feedbackText.length() > 0">
                                                                    <td class="label">Feedback:</td>
                                                                    <td>${viewData.submission.feedbackText}</td>
                                                                </s:if>
                                                            </table>
                                                        </div>
                                                        <!-- End #submissionData -->
                                                    </s:push>

                                                    <jsp:include page="includes/contest/submissionViewer/feedback.jsp"/>

                                                </div>


                                            </div>
                                            <!-- End .container2Content -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->

                            <a href="javascript:;" class="button1 backToTop"><span>Back To Top</span></a>
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

<jsp:include page="includes/contest/submissionViewer/contestVars.jsp"/>
<input type="hidden" id="submissionId" value="${formData.submissionId}"/>
<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>