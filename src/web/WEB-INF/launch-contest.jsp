<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of creating project in dashboard.
  -
  - Version 1.1 (TC Direct UI Improvement Assembly 2) changes: Solve help information layout issues.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="launch" scope="request"/>
    <script type="text/javascript" src="/scripts/launch/entity.js?v=209437"></script>
    <script type="text/javascript" src="/scripts/launch/main.js?v=209174"></script>
    <script type="text/javascript" src="/scripts/launchcontest.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/launch/pages/selection.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/launch/pages/overview.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/launch/pages/review.js?v=207440"></script>
    <script type="text/javascript" src="/scripts/launch/pages/orderReview.js?v=207440"></script>
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
                                <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                <strong>Launch New Contest</strong>
                            </div> <!-- End .currentPage -->

                            <div id="launchContestOut">

                             <div id="contestSelectionPage" class="launchpage">
                               <jsp:include page="includes/launch/contestSelection.jsp"/>
                             </div>

                             <div id="overviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/overview.jsp"/>
                             </div>

                             <div id="overviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/overviewSoftware.jsp"/>
                             </div>

                             <div id="reviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/review.jsp"/>
                             </div>

                             <div id="reviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/reviewSoftware.jsp"/>
                             </div>

                             <div id="orderReviewPage" class="launchpage hide">
                               <jsp:include page="includes/launch/orderReview.jsp"/>
                             </div>

                             <div id="orderReviewSoftwarePage" class="launchpage hide">
                               <jsp:include page="includes/launch/orderReviewSoftware.jsp"/>
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

                <div class="tooltipContent" id="contestDescriptionTooltip">
                    <p>Contest Description</p>
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
                    <p>Milestone Prizes</p>
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
                    <p>If you have selected a software contest, then simply set the date and time you would like the registration and submission phase to begin.  If you have selected a Studio contest, then please visit the Contest Holder Guide where you will find more information about multi-round contests. http://topcoder.com/wiki/display<br/>/tcstudio/Studio+Guide+for+<br/>Contest+Holders</p>
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
                    <p>Need help writing a great Studio contest description? Please visit the Contest Holder Guide where you will find contest samples and templates. http://topcoder.com/wiki/display<br/>/tcstudio/Studio+Guide+for+<br/>Contest+Holders</p>
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
                    <p>In some cases, when clients would like to hold a "private" competition that has confidentiality terms in addition to the usual click-through terms, the competition will require a Competition Confidentiality Agreement or "CCA" document to be completed prior to participation in the competition. http://apps.topcoder.com/wiki<br/>/display/tc/Competition+<br/>Confidentiality+Agreement+<br/>%28CCA%29</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
