<%--
  - Author: Ghost_141, TCSDEVELOPER
  -
  - Version 1.1 (TC Direct Replatforming Release 5) change notes: Change contestId parameter to projectId.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes: Fix multiple bugs.
  - 
  - Version: 1.2
  - Since: Submission Viewer Release 4 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the third page for "Can't Select Winner" flow.
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
                                <a href="<s:url action="currentProjectDetails" namespace="/"/>"><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
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

                                                <div class="noWinnerSuggestion">
                                                    <h3 class="red">Care to try again?</h3>

                                                    <div class="content">
                                                        <p>As a valued customer, you have the opportunity to post this
                                                            contest one more time at no additional cost to you. This is
                                                            great chance to analyze what worked (and didn't work) in
                                                            your last contest.</p>

                                                        <p>By simply making some minor modifications to your
                                                            descriptions, prize amounts, etc., you may increase your
                                                            chances of getting more successful submissions.</p>

                                                        <p>You can learn more about reposting contests by
                                                            <a href="#">visiting our help section</a>.</p>

                                                        <p>So what would you like to do?</p>
                                                    </div>
                                                </div>


                                                <div id="bankSelectionButtonBottom">
                                                    <a href="<s:url action="studioNoWinnerAbandoned" namespace="/contest">
                                                                 <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                                                 <s:param name="formData.roundType" value="formData.roundType"/>
                                                             </s:url>" class="button7 btnNoThankYou">
                                                        <span class="left"><span class="right">NO, THANK YOU</span></span></a>

                                                    <link:studioSubmissionsGrid contestId="${viewData.contestId}"
                                                                                milestoneRound="${formData.isMilestoneRound}"
                                                                                styleClass="button6 btnRunAgian">
                                                        <span class="left"><span class="right">RUN THIS CONTEST AGAIN</span></span>
                                                    </link:studioSubmissionsGrid>
                                                </div>

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