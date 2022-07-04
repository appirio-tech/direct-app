<%--
  - Author: GreatKevin, TCSASSEMBLER
  -
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - Add history.js to support push state and back-compatible with IE with hash-fallback
  -
  - Version 1.2 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove the uneeded corners in div
  -
  - Description: The roadmap page the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="roadmap" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <link rel="stylesheet" href="/css/direct/milestone-fullCalendar.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/projectMilestone.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/milestone-fullCalendar.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.js"></script>
</head>

<body id="page" class="dashboardPage">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="wholeContent">
                    <div id="wholeArea">

                        <div class="enterpriseOverView">

                            <jsp:include page="../includes/enterpriseDashboard/sidebar.jsp"/>

                            <!-- main section -->
                            <div id="mainSection">
                                <h2 data-intro="Your Roadmap is a summary of all your project milestones.  Use these to track overall progress with your projects and be alerted to potential time delays. Green is complete.  Yellow is approaching.  Red is overdue.  Click on a milestone to view the details of it within that particular project." data-step="1">Road Map</h2>

                                <!-- filter panel -->
                                <jsp:include page="../includes/enterpriseDashboard/filterHeader.jsp"/>
                                <!-- End .filterPanel -->

                                <div class="roadmapViewArea" style="margin-top: 15px; min-height: 400px">
                                    <div class="loading">
                                        <img src="/images/loadingAnimation.gif" alt=""/>
                                    </div>
                                    <div class="milestoneCalView">

                                    </div>
                                </div>

                            <span id="calendarToday" style="display:none" ><s:date name="calendarToday"
                                                                                  format="MM/dd/yyyy"/></span>

                                <!-- start road map section -->
                                <div class="sectionContainer roadMapSection">

                                    <div class="sectionInner">

                                        <div class="projectsHealthTitle">
                                            <h3>Road Map</h3>
                                            <a onclick="javascript:introJs().start();" href="javascript:;" class="icon" rel="Showing the Overdue, Upcoming, and Completed Project Milestones.">!</a>
                                        </div>
                                        <!-- title -->

                                        <!-- roadMapNum -->
                                        <div class="roadMapNum">
                                            <div class="roadMapNumInner">
                                                <ul>
                                                    <li class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!-- End .roadMapNum -->

                                        <!-- tab -->
                                        <div id="tab">

                                            <!-- tabPanel -->
                                            <div class="tabPanel" data-intro="Quickly see overdue, upcoming, and completed project milestones." data-step="2">
                                                <ul>
                                                    <li class="overDue"><a href="javascript:;"><span><span>Overdue <strong>(0)</strong></span></span></a></li>
                                                    <li class="upcoming"><a href="javascript:;"><span><span>Upcoming <strong>(0)</strong></span></span></a></li>
                                                    <li class="completed"><a href="javascript:;"><span><span>Completed <strong>(0)</strong></span></span></a></li>
                                                </ul>
                                            </div>
                                            <!-- End .tabPanel -->

                                            <!-- tabContainer -->
                                            <div class="tabContainer">

                                                <!-- tabSection -->
                                                <div class="tabSection overDueTable">
                                                    <div class="tableData">
                                                        <!-- End .pagePanel -->
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <colgroup>
                                                                <col width="55%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>Overdue Releases</th>
                                                                <th>Project</th>
                                                                <th>Due Date</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!-- End .tabSection -->

                                                <!-- tabSection -->
                                                <div class="tabSection upcomingTable">
                                                    <div class="tableData">
                                                        <!-- End .pagePanel -->
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <colgroup>
                                                                <col width="55%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>Upcoming Releases</th>
                                                                <th>Project</th>
                                                                <th>Due Date</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!-- End .tabSection -->

                                                <!-- tabSection -->
                                                <div class="tabSection completedTable">
                                                    <div class="tableData">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <colgroup>
                                                                <col width="55%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>Completed Releases</th>
                                                                <th>Project</th>
                                                                <th>Completion Date</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!-- End .tabSection -->

                                            </div>
                                            <!-- tabContainer -->

                                        </div>
                                        <!-- End #tab -->

                                    </div>
                                </div>
                                <!-- end road map section -->



                            </div>
                            <!-- End #mainSection -->

                        </div>

                    </div>
                </div>

                <jsp:include page="../includes/footer.jsp"/>

            </div>
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->
<jsp:include page="../includes/enterpriseDashboard/filterPopup.jsp"/>
<span id="tableTemplate" class="hide">
    <div class="tabSection overDueTable">
        <div class="tableData">
            <!-- End .pagePanel -->
            <table border="0" cellspacing="0" cellpadding="0" id="overDueData">
                <colgroup>
                    <col width="55%"/>
                    <col/>
                </colgroup>
                <thead>
                <tr>
                    <th>Overdue Releases</th>
                    <th>Project</th>
                    <th>Due Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="3">
                        <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading"/></div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- End .tabSection -->

    <!-- tabSection -->
    <div class="tabSection upcomingTable">
        <div class="tableData">
            <!-- End .pagePanel -->
            <table border="0" cellspacing="0" cellpadding="0"
                   id="upcomingData">
                <colgroup>
                    <col width="55%"/>
                    <col/>
                </colgroup>
                <thead>
                <tr>
                    <th>Upcoming Releases</th>
                    <th>Project</th>
                    <th>Due Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="3">
                        <div class="ajaxTableLoader"><img
                                src="/images/rss_loading.gif"
                                alt="loading"/></div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- End .tabSection -->

    <!-- tabSection -->
    <div class="tabSection completedTable">
        <div class="tableData">
            <table border="0" cellspacing="0" cellpadding="0"
                   id="completedData">
                <colgroup>
                    <col width="55%"/>
                    <col/>
                </colgroup>
                <thead>
                <tr>
                    <th>Completed Releases</th>
                    <th>Project</th>
                    <th>Completion Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="3">
                        <div class="ajaxTableLoader"><img
                                src="/images/rss_loading.gif"
                                alt="loading"/></div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</span>
<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
