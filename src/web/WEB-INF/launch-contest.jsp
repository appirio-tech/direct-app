<%--
  - Author: bugbuka, TCSCODER
  - Version: 1.5
  - Copyright (C) 2011 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of creating project in dashboard.
  -
  - Version 1.1 (TC Direct UI Improvement Assembly 2) changes: Solve help information layout issues.
  -
  - Version 1.2 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) changes:
  - - Add tooltip for "Create Bug Hunt Contest" checkbox
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) changes:
  - - Update to support launching mm contest.
  -
  - Version 1.4 (TOPCODER - SUPPORT TYPEAHEAD FOR TASK ASSIGNEES IN DIRECT APP):
  - - Split jquery import to other file
  -
  - Version 1.5 (TOPCODER - IMPROVE USER MANAGEMENT BEHAVIOR FOR PROJECT PERMISSIONS & NOTIFICATIONS)
  - - Move redundant code
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/jq_1_11_1.jsp"/>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/direct/projectMilestone.css" media="all" type="text/css" />

    <link rel="stylesheet" href="/css/direct/magicsuggest.css" media="all" type="text/css"/>
    <c:set var="PAGE_TYPE" value="launch" scope="request"/>
    <script type="text/javascript" src="/scripts/launch/entity.js"></script>
    <script type="text/javascript" src="/scripts/moment.min.js"></script>
    <script type="text/javascript" src="/scripts/moment-timezone-with-data-2010-2020.min.js"></script>
    <script type="text/javascript" src="/scripts/launch/main.js"></script>
    <script type="text/javascript" src="/scripts/projectMilestone.js"></script>
    <script type="text/javascript" src="/scripts/launchcontest.js"></script>
    <script type="text/javascript" src="/scripts/launch/pages/selection.js"></script>
    <script type="text/javascript" src="/scripts/launch/pages/overview.js"></script>
    <script type="text/javascript" src="/scripts/launch/pages/review.js"></script>
    <script type="text/javascript" src="/scripts/launch/pages/orderReview.js"></script>
    <script type="text/javascript">
        var showSaveChallengeConfirmation = <s:property value="showSaveChallengeConfirmation"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">

                            <div class="currentPage">
                                <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                <strong>Launch New Challenge</strong>
                            </div> <!-- End .currentPage -->

                            <div id="launchContestOut">
                                <input type="hidden" name="CMCTaskID" value='<s:property value="%{#parameters.cmcTaskId}" />'/>
                                <input type="hidden" name="CMCBillingID" value='<s:property value="cmcBillingAccount.id" />'/>
                                <input type="hidden" name="CMCBillingName" value='<s:property value="cmcBillingAccount.name" />'/>
                             <div id="contestSelectionPage" class="launchpage">
                               <jsp:include page="includes/launch/contestSelection.jsp"/>
                             </div>

                             <div id="overviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/overview.jsp"/>
                             </div>

                             <div id="overviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/overviewSoftware.jsp"/>
                             </div>
                             
                             <div id="overviewAlgorithmPage" class="launchpage">
                               <jsp:include page="includes/launch/overviewAlgorithm.jsp"/>
                             </div>

                             <div id="reviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/review.jsp"/>
                             </div>

                             <div id="reviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/reviewSoftware.jsp"/>
                             </div>
                             
                             <div id="reviewAlgorithmPage" class="launchpage hide">
                               <jsp:include page="includes/launch/reviewAlgorithm.jsp"/>
                             </div>
                             
                             <div id="orderReviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/orderReview.jsp"/>
                             </div>

                             <div id="orderReviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/orderReviewSoftware.jsp"/>
                             </div>
                             
                             <div id="orderReviewAlgorithmPage" class="launchpage hide">
                               <jsp:include page="includes/launch/orderReviewAlgorithm.jsp"/>
                             </div>

                            </div>
                            <!-- end #launchContestOut -->


                    </div> <!-- End.area1Content -->
                    </div> <!-- End #area1 -->

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

<div class="tooltipArea">
    <div id="contestLaunch1" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>
                         </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="effortDaysToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Effort Days Estimate</strong></p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestLaunch2" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Checkpoint Prizes</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<div class="tooltipArea">
    <div id="contestLaunch3" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Select the type of file you would like for the final deliverale.  You can select multiple and/or add additional types.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>


<div class="tooltipArea">
    <div id="contestScheduleToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>If you have selected a software challenge, then simply set the date and time you would like the registration and submission phase to begin.  If you have selected a Studio challenge, then please visit the Challenge Holder Guide where you will find more information about multi-round challenges. http://topcoder.com/wiki/display<br/>/tcstudio/Studio+Guide+for+<br/>Challenge+Holders</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestDescriptionToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Need help writing a great Studio challenge description? Please visit the Challenge Holder Guide where you will find challenge samples and templates. http://topcoder.com/wiki/display<br/>/tcstudio/Studio+Guide+for+<br/>Challenge+Holders</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestRound1ToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Enter any specific requirements for round 1 submissions.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestRound2ToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Enter any specific requirements for round 2 submissions.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<div class="tooltipArea">
    <div id="enforceCCAToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>In some cases, when clients would like to hold a "private" competition that has confidentiality terms in addition to the usual click-through terms, the competition will require a Non-Disclosure Agreement or "NDA" document to be completed prior to participation in the competition. https://www.topcoder.com/<br/>challenge-details/terms/<br/>detail/21153/</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<div class="tooltipArea">
    <div id="bugHuntForAssemblyToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>
                        Check this box to automatically create a Bug Hunt Competition for your Assembly Competition.
                        The Bug Hunt Competition will start at the end of your Assembly Competition. The Bug Hunt will
                        help proactively identify potential issues, enhancements, and changes.
                    </p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<jsp:include page="includes/popups.jsp"/>
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
