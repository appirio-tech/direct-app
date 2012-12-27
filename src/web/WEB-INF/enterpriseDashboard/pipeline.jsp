<%--
  - Author: GreatKevin
  - Version: 1.0 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Add the projects pipeline chart into the pipeline page
  -
  - Description: The pipeline page the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="pipeline" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
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
                                <h2>Pipeline</h2>

                                <!-- filter panel -->
                                <jsp:include page="../includes/enterpriseDashboard/filterHeader.jsp"/>
                                <!-- End .filterPanel -->

                                <div class="sectionContainer pipelineSection">

                                    <div class="sectionInner projectsPipeline">

                                        <div class="pipelineTitle">
                                            <h3>Project Pipeline</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="This chart depicts the projects in Pipeline">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">

                                            <!-- numberSection -->
                                            <div class="numberSection">
                                                <div class="numberSectionInner">
                                                    <ul>
                                                        <li class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                         alt="loading"/></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <!-- End .numberSection -->

                                            <!-- Pipeline -->
                                            <div class="pipelineChart">

                                                <div id="projectPipelineChartWrapper">
                                                    <div id="projectPipelineChart">
                                                        <div class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                          alt="loading"/></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- End .pipelineChart -->

                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>
                                    </div>

                                    <div class="sectionInner contestsPipeline">

                                        <div class="pipelineTitle">
                                            <h3>Contest Pipeline</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="This chart depicts the contests of projects in Pipeline">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">

                                            <!-- numberSection -->
                                            <div class="numberSection">
                                                <div class="numberSectionInner">
                                                    <ul>
                                                        <li class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                         alt="loading"/></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <!-- End .numberSection -->

                                            <!-- Pipeline -->
                                            <div class="pipelineChart">

                                                <div id="pipelineChartWrapper">
                                                    <div id="pipelineChart">
                                                        <div class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                          alt="loading"/></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- End .pipelineChart -->

                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>
                                    </div>
                                </div>

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
