<%--
  - Author: GreatKevin
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  -
  - Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
  - - Add pipeline widget
  -
  - Version 1.2 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part) updates:
  - Add roadmap section in overview.
  -
  - Version 1.3 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Add history.js to support push state and back-compatible with IE with hash-fallback
  -
  - Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: The overview page of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="overview" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>

    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript"  src="/scripts/enterpriseDashboard.js"></script>
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
                    <h2 data-intro="Welcome to your Enterprise Dashboard. This page is a synopsis of information relevant to your projects.  It's a great starting point to view financials, pipeline, roadmap, and participation metrics. Click Next for a tour of the widgets below." data-step="1">Overview</h2>

                    <!-- filter panel -->
                    <jsp:include page="../includes/enterpriseDashboard/filterHeader.jsp"/>
                    <!-- End .filterPanel -->

                    <!-- Financial Section in OverView -->
                    <%--<div class="sectionContainer overFinancialSection">--%>
                        <%--<div class="sectionInner">--%>

                            <%--<div class="financailsTitle">--%>
                                <%--<h3>Financials</h3>--%>
                                <%--<a href="javascript:;" class="icon" rel="View the financial statistics of the filtered projects">!</a>--%>
                                <%--<a href="<s:url action='financial' namespace='/enterpriseDashboard'/>" class="viewAllLink">View all projects</a>--%>
                            <%--</div>--%>
                            <%--<!-- title -->--%>

                            <%--<div class="mainSection">--%>

                                <%--<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>--%>

                                <%--<div class="dateSection">--%>

                                    <%--<!-- side bar -->--%>
                                    <%--<div class="financailSideBar">--%>
                                        <%--<div class="sideBarInner">--%>
                                            <%--<ul>--%>
                                                <%--<li></li>--%>
                                            <%--</ul>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<!-- End .financailSideBar -->--%>

                                    <%--<!-- table -->--%>
                                    <%--<div class="financailTableData">--%>
                                        <%--<h4>Project Information</h4>--%>
                                        <%--<table border="0" cellspacing="0" cellpadding="0" id="overviewFinancailData">--%>
                                            <%--<colgroup>--%>
                                                <%--<col width="23%" />--%>
                                                <%--<col />--%>
                                                <%--<col width="12%" />--%>
                                                <%--<col width="12%" />--%>
                                                <%--<col width="12%" />--%>
                                            <%--</colgroup>--%>
                                            <%--<thead>--%>
                                            <%--<tr>--%>
                                                <%--<th>Project Name</th>--%>
                                                <%--<th>Project Budget</th>--%>
                                                <%--<th>Total Budget</th>--%>
                                                <%--<th>Actual Cost</th>--%>
                                                <%--<th>Projected Cost</th>--%>
                                            <%--</tr>--%>
                                            <%--</thead>--%>
                                            <%--<tbody>--%>
                                            <%--<tr class="last">--%>
                                                <%--<td colspan="5"></td>--%>
                                            <%--</tr>--%>
                                            <%--</tbody>--%>
                                        <%--</table>--%>
                                    <%--</div>--%>
                                    <%--<!-- End .financailTableData -->--%>

                                    <%--<div class="clear"></div>--%>

                                <%--</div>--%>

                            <%--</div>--%>
                            <%--<!-- End .mainSection -->--%>

                            <%--<div class="corner tl"></div>--%>
                            <%--<div class="corner tr"></div>--%>

                        <%--</div>--%>
                    <%--</div>--%>
                    <!-- End .overFinancialSection -->

                    <!-- left column -->
                    <div class="leftColumn">

                        <!-- total Spend -->
                        <div class="sectionContainer overTotalSpendSection">
                            <div class="sectionInner">

                                <div class="totalSpendTitle" data-intro="Total spend is calculated by adding together all of your competitions costs as well as any fees." data-step="2">
                                    <h3>Total Spend</h3>
                                    <a onclick="javascript:introJs().start();" href="javascript:;" class="icon" rel="Total Amount Spent for the Projects each month is shown here.">!</a>
                                    <a href="<s:url action='financial' namespace='/enterpriseDashboard'/>" class="viewAllLink viewAllSync" data-intro="Click View All for a full screen view with drill down features." data-step="3">View All</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection">
                                    <div id="overviewTotalSpend">
                                        <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>
                                    </div>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>

                            </div>
                        </div>
                        <!-- End .overTotalSpendSection -->

                        <!-- total Spend -->
                        <div class="sectionContainer overRoadMapSection" data-intro="Your Roadmap is a summary of all your project milestones.  Use these to track overall progress with your projects and be alerted to potential time delays." data-step="4" data-position="right">
                            <div class="sectionInner">

                                <div class="roadMapTitle">
                                    <h3>Road Map</h3>
                                    <a href="javascript:;" class="icon" rel="Showing the Overdue, Upcoming, and Completed Releases.">!</a>
                                    <a href="<s:url action='roadmap' namespace='/enterpriseDashboard'/>" class="viewAllLink viewAllSync">View All</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection noBorderSection">
                                    <div id="tab">
                                        <div class="tabPanel" data-intro="Switch between overdue, upcoming, and completed project milestones." data-step="5">
                                            <ul>
                                                <li class="overDue"><a href="javascript:;" class="current"><span><span>Overdue</span></span></a></li>
                                                <li class="upcoming"><a href="javascript:;"><span><span>Upcoming</span></span></a></li>
                                                <li class="completed"><a href="javascript:;"><span><span>Completed</span></span></a></li>
                                            </ul>
                                        </div>

                                        <div class="placeHolder"></div>

                                        <div class="tabContainer">

                                            <!-- overDueTable -->
                                            <div class="tabSection overDueTable">
                                                <!-- End .pagePanel -->
                                                <table border="0" cellspacing="0" cellpadding="0" id="overDueData">
                                                    <colgroup>
                                                        <col width="65%" />
                                                        <col />
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Overdue Releases</th>
                                                        <th>Due Date</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="2" class="alignCenter">
                                                            <div class="ajaxTableLoader">
                                                                <img src="/images/rss_loading.gif" alt="loading" />
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- End .overDueTable -->

                                            <!-- upcomingTable -->
                                            <div class="tabSection upcomingTable">
                                                <!-- End .pagePanel -->
                                                <table border="0" cellspacing="0" cellpadding="0" id="upcomingData">
                                                    <colgroup>
                                                        <col width="65%" />
                                                        <col />
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Upcoming Releases</th>
                                                        <th>Due Date</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="2" class="alignCenter">
                                                            <div class="ajaxTableLoader">
                                                                <img src="/images/rss_loading.gif" alt="loading" />
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- End .upcomingTable -->

                                            <!-- completedTable -->
                                            <div class="tabSection completedTable">
                                                <!-- End .pagePanel -->
                                                <table border="0" cellspacing="0" cellpadding="0" id="completedData">
                                                    <colgroup>
                                                        <col width="65%" />
                                                        <col />
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Completed Releases</th>
                                                        <th>Completion Date</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="2" class="alignCenter">
                                                            <div class="ajaxTableLoader">
                                                                <img src="/images/rss_loading.gif" alt="loading" />
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- End .completedTable -->

                                        </div>

                                    </div>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>

                            </div>
                        </div>
                        <!-- End .overTotalSpendSection -->

                    </div>
                    <!-- End .leftColumn -->

                    <!-- middle column -->
                    <div class="middleColumn">
					
						 <!-- pipeline -->
                        <div class="sectionContainer overpipelineSection" data-intro="Use the pipeline to help you understand and track the work you are putting through the platform. Click on the legend labels to show/hide various competition statuses." data-step="6">
                            <div class="sectionInner">

                                <div class="pipelinehTitle">
                                    <h3>Pipeline</h3>
                                    <a href="javascript:;" class="icon" rel="A graph depicting the projects in Pipeline is shown here.">!</a>
                                    <a href="<s:url action='pipeline' namespace='/enterpriseDashboard'/>" class="viewAllLink viewAllSync">View All</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection">
                                    <div id="overviewPipeline">
                                        <%--<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>--%>
                                    </div>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>

                            </div>
                        </div>
                        <!-- End .overpipelineSection -->

                        <!-- Projects Health -->
                        <div class="sectionContainer overprojectsHealthSection">
                            <div class="sectionInner">

                                <div class="projectHealthTitle">
                                    <h3>Projects Health</h3>
                                    <a href="javascript:;" class="icon" rel="Showing the overall projects Health of all projects.">!</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection">
                                    <div id="overviewProjectHealth">
                                        <%--<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>--%>
                                        <div class="ajaxTableLoader" >Coming Soon!</div>
                                    </div>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>

                            </div>
                        </div>
                        <!-- End .overprojectsHealthSection -->

                       

                    </div>
                    <!-- End .middleColumn -->

                    <!-- right column -->
                    <div class="rightColumn">

                        <!-- Projects -->
                        <div class="sectionContainer overProjectsSection" data-intro="Your active projects and the next milestone scheduled for each" data-step="7">
                            <div class="sectionInner">

                                <div class="projectTitle">
                                    <h3>Projects</h3>
                                    <a href="javascript:;" class="icon" rel="List of recently modified/ created projects is shown here. Hover on Project Name  to see more details.">!</a>
                                    <a href="<s:url action='allProjects'/>" class="viewAllLink">View All</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection noBorderSection">
                                    <table border="0" cellpadding="0" cellspacing="0" id="overProjectsTableData">
                                        <colgroup>
                                            <col width="45%" />
                                            <col />
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>Project Name</th>
                                            <th>Next Milestone</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td colspan="2" class="alignCenter">
                                                <div class="ajaxTableLoader">
                                                    <%--<img src="/images/rss_loading.gif" alt="loading" />--%>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>
                            </div>
                        </div>
                        <!-- End .overprojectsHealthSection -->

                        <!-- Community -->
                        <div class="sectionContainer overCommunitySection">
                            <div class="sectionInner">

                                <div class="communityTitle">
                                    <h3>Community</h3>
                                    <a href="javascript:;" class="icon" rel="Showing the overall contri-bution for the projects from each country.">!</a>
                                    <a href="javascript:;" class="viewAllLink">View All</a>
                                </div>
                                <!-- title -->

                                <!-- container -->
                                <div class="containerSection noBorderSection">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <colgroup>
                                            <col width="45%" />
                                            <col width="55%" />
                                        </colgroup>
                                        <thead>
                                        <tr style="height: 30px">
                                            <th>Country Name</th>
                                            <th>%</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td colspan="2" class="alignCenter">
                                                <div class="ajaxTableLoader"></div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div id="overviewCommunity"></div>
                                </div>
                                <!-- End .containerSection -->

                                <div class="corner tl"></div>
                                <div class="corner tr"></div>

                            </div>
                        </div>
                        <!-- End .overCommunitySection -->

                    </div>
                    <!-- End .rightColumn -->

                    <div class="clear"></div>

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

</body>
<!-- End #page -->

</html>
