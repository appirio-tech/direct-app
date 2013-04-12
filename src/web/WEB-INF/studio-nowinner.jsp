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
  - Description: This page renders the first page for "Can't Select Winner" flow.
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
                                                    <h3 class="red">We know that picking a winner is difficult...</h3>

                                                    <div class="content">
                                                        <p>However, it is important to fulfill your commitment to this
                                                            contest. If you decide to choose no winners, you will have
                                                            no further responsibility to purchase any of the submitted
                                                            designs.</p>

                                                        <h4>Before you choose this option, you should know:</h4>

                                                        <p>1. This is irreversible; you will not be able to purchase any
                                                            of the submissions in the future.</p>

                                                        <p>2. Your profile history will indicate that you ended a
                                                            competition and chose no winner. This may discourage members
                                                            from competing in your future contests.</p>

                                                        <p>3. The contest administration fee you paid to launch this
                                                            competition is non-refundable.</p>

                                                        <h4>Make a choice, but do something.</h4>

                                                        <p>Taking no action at all would be an ‘abandonment’ of the
                                                            competition, which also would be indicated in your public
                                                            profile. Multiple contest abandonments will result in a
                                                            cancellation of your account.</p>

                                                        <p></p>
                                                    </div>
                                                </div>

                                                <div id="bankSelectionButtonBottom">
                                                    <a href="<s:url action="studioNoWinnerDecide" namespace="/contest">
                                                                 <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                                                 <s:param name="formData.roundType" value="formData.roundType"/>
                                                             </s:url>" class="button7 btnDoNotChooseAWinner">
                                                        <span class="left"><span class="right">DO NOT CHOOSE A WINNER </span></span></a>

                                                    <link:studioSubmissionsGrid contestId="${viewData.contestId}" 
                                                                                checkpointRound="${formData.isCheckpointRound}"
                                                                                styleClass="button6 btnSelectAWinner">
                                                        <span class="left"><span class="right">SELECT A WINNER</span></span>
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