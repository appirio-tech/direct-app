<%--
  - Author: isv, flexme, TCSDEVELOPER, TCSASSEMBLER
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes:
  - 1.Remove "bank:" row.
  - 2.Hide "Submitter Notes:" row.
  - 3.Display Feedback when feedback text is not null or empty.
  - 4.include the contestVars.jsp.
  -
  - Version 1.2 (Direct Submission Viewer Release 4 ) change notes:
  - 1.Display Feedback as text only when submission is "Confirmed"
  -
  - Version 1.3 (TC Direct Release Assembly 7) change Notes: 
  - 1.Added hasContestWritePermission field.  
  -
  - Version 1.4 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Apply to new submission view.
  - 2.Add dashboard header.  
  -
  - Version: 1.4
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
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
    
    <script type="text/javascript">
        var hasContestWritePermission = ${viewData.hasContestWritePermission};
    </script>    
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

							<jsp:include page="includes/contest/dashboard.jsp"/>
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

                                                        <ul id="carouselSingle" style="position: relative; width: 750px; height: 550px; overflow: hidden;">
                                                            <%--
                                                            For Wireframe contests only preview image is shown;
                                                            For other contests images carousel is shown
                                                            --%>
                                                            <c:choose>
                                                                <c:when test="${viewData.currentContest.contestType.id eq 25}">
                                                                    <ui:studioCarouselSubmission
                                                                            contestId="${submission.contestId}"
                                                                            submission="${submission}"
                                                                            artifactNum="0"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <ui:studioCarouselSubmission
                                                                            contestId="${submission.contestId}"
                                                                            submission="${submission}"
                                                                            artifactNum="1"/>
                                                                    <!-- Carousel images -->
                                                                    <c:forEach begin="2" end="${submission.artifactCount}"
                                                                               varStatus="status">
                                                                        <ui:studioCarouselSubmission
                                                                                contestId="${submission.contestId}"
                                                                                submission="${submission}"
                                                                                artifactNum="${status.index}"/>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </ul>
														<ui:submissionAction contestId="${submission.contestId}" submission="${submission}"/>
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
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <h3>Declarations</h3><i>Any notes and declared fonts or stock images that are used in the submission. <a href="http://topcoder.com/wiki/display/tcstudio/Declarations-+Stock+Art+and+Fonts" target="_blank">Read more here.</a></i>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="label">Comment:</td>
                                                                    <td>${viewData.submission.comment}</td>
                                                                </tr>
                                                                <s:if test="viewData.submission.fonts != null && viewData.submission.fonts.size() > 0">
                                                                <tr>
                                                                    <td class="label">Fonts:</td>
                                                                    <td><c:forEach items="${viewData.submission.fonts}" var="font"><a href="${font.url}" target="blank">${font.name}</a><br/></c:forEach></td>
                                                                </tr>
                                                                </s:if>
                                                                <s:if test="viewData.submission.stockArts != null && viewData.submission.stockArts.size() > 0">
                                                                <tr>
                                                                    <td class="label">Stock Art:</td>
                                                                    <td><c:forEach items="${viewData.submission.stockArts}" var="stockArt"><a href="${stockArt.url}" target="blank">${stockArt.name}</a><br/></c:forEach></td>
                                                                </tr>
                                                                </s:if>
                                                                <s:if test="viewData.submission.feedbackText != null && viewData.submission.feedbackText.length() > 0">
                                                                    <if:isConfirmedStudioSubmission
                                                                            submission="${viewData.submission}">
                                                                        <td class="label">Feedback:</td>
                                                                        <td>${viewData.submission.feedbackText}</td>
                                                                    </if:isConfirmedStudioSubmission>
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

