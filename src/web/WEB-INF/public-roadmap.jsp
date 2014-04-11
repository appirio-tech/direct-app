<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: Public roadmap page for TopCoder
  - Since: Module Assembly - TC Cockpit Public Page for TopCoder Road map and RSS syndication
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>TopCoder Direct</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css"/>
    <![endif]-->
    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css"/>
    <![endif]-->
    <link rel="stylesheet" href="/css/direct/public-roadmap.css" media="all" type="text/css"/>

    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/public-roadmap.js"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <div id="wholeContent">
                    <div id="wholeArea">

                        <div class="enterpriseOverView">


                            <!-- main section -->
                            <div id="mainSection">
                                <!-- filter panel -->
                                <div class="filterPanel">
                                    <div class="filterPanelHeader">
                                        <div class="filterPanelTitle">
                                            <h3>TopCoder Road Map</h3>

                                        </div>
                                        <div class="filterPanelButton">
                                            <a href="./TopCoderRoadmapRSS" class="rss">RSS</a>
                                        </div>
                                    </div>

                                </div>
                                <!-- End .filterPanel -->

                                <!-- Projects Health section -->
                                <div class="sectionContainer roadMapSection">

                                    <div class="sectionInner">

                                        <!-- tab -->
                                        <div id="tab">

                                            <!-- tabPanel -->
                                            <div class="tabPanel">
                                                <ul>
                                                    <li class="overDue"><a
                                                            href="javascript:;"><span><span>Overdue <strong>(0)</strong></span></span></a>
                                                    </li>
                                                    <li class="upcoming"><a
                                                            href="javascript:;"><span><span>Upcoming <strong>(0)</strong></span></span></a>
                                                    </li>
                                                    <li class="completed"><a
                                                            href="javascript:;"><span><span>Completed <strong>(0)</strong></span></span></a>
                                                    </li>
                                                </ul>
                                            </div>
                                            <!-- End .tabPanel -->

                                            <!-- tabContainer -->
                                            <div class="tabContainer">

                                                <!-- tabSection -->
                                                <div class="tabSection overDueTable">
                                                    <div class="tableData">

                                                        <!-- End .pagePanel -->
                                                        <table border="0" cellspacing="0" cellpadding="0"
                                                               id="overDueData">
                                                            <colgroup>
                                                                <col width="65%"/>
                                                                <col/>
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
                                                                        <div class="ajaxTableLoader"><img
                                                                                src="/images/rss_loading.gif"
                                                                                alt="loading"/>
                                                                        </div>
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
                                                                <col width="65%"/>
                                                                <col/>
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
                                                                                alt="loading"/>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>


                                                        <!-- End .pagePanel -->
                                                    </div>
                                                </div>
                                                <!-- End .tabSection -->

                                                <!-- tabSection -->
                                                <div class="tabSection completedTable">
                                                    <div class="tableData">

                                                        <table border="0" cellspacing="0" cellpadding="0"
                                                               id="completedData">
                                                            <colgroup>
                                                                <col width="65%"/>
                                                                <col/>
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
                                                                                alt="loading"/>
                                                                        </div>
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

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>
                                    </div>
                                </div>

                            </div>
                            <!-- End #mainSection -->

                        </div>

                    </div>
                </div>

                <jsp:include page="includes/footer.jsp"/>

            </div>
        </div>
    </div>
</div>

</body>

</html>