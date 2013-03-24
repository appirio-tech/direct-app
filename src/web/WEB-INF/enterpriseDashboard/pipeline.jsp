<%--
  - Author: GreatKevin
  - Version: 1.3
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Add the projects pipeline chart into the pipeline page
  -
  - Version 1.2 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
  - Add contest / project pipeline drill-in popup
  -
  - Version 1.3 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Add history.js to support push state and back-compatible with IE with hash-fallback
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
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript"  src="/scripts/jsrender-min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.js"></script>
    <script id="contestPipelineDrillInTemplate" type="text/x-jsrender">
        <tr>
            <td class="first">
                <a href="../projectOverview.action?formData.projectId={{:directProjectId}}" target="_blank">{{:directProjectName}}</a>
            </td>
            <td>
                <a href="../contest/detail.action?projectId={{:contestId}}" target="_blank">{{:contestName}}</a>
            </td>
            <td>{{:contestStatus}}</td>
            <td>{{:startDate}}</td>
            <td>{{:endDate}}</td>
            <td>
                {{:copilot}}
            </td>
        </tr>
    </script>
    <script id="projectPipelineDrillInTemplate" type="text/x-jsrender">
        <tr>
            <td class="first">
                <a href="../projectOverview.action?formData.projectId={{:directProjectId}}" target="_blank">{{:directProjectName}}</a>
            </td>
            <td>{{:projectStatus}}</td>
            <td>{{:startDate}}</td>
            <td>{{:endDate}}</td>
        </tr>
    </script>
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

<div class="popUps">
    <div class="expandViewPopup" id="contestPipelineViewPopup" style="display:none">
        <div class="close">
            <a href="javascript:void(0)" id="contestPipelineViewClose"></a>.
        </div>
        <div class="popContent">
            <h2><span id="contestPipelineViewPopupMonth"></span><a class="exportLink" href="javascript:;" target="_blank">Export</a></h2>

            <div class="dashboardTable pipelineTable">
                <div class="tableWrapper">
                    <table id="contestPipelineDrillInTable" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr class="head">
                            <th class="first noBT">Project Name</th>
                            <th class="noBT">Contest Name</th>
                            <th class="noBT">Status</th>
                            <th class="noBT">Start Date</th>
                            <th class="noBT">End Date</th>
                            <th class="noBT">Copilot</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="hide handler">
        <a href="#" id="contestPipelineViewHandler"></a>
    </div>
</div>

<div class="popUps">
    <div class="expandViewPopup" id="projectPipelineViewPopup" style="display:none">
        <div class="close">
            <a href="javascript:void(0)" id="projectPipelineViewClose"></a>.
        </div>
        <div class="popContent">
            <h2><span id="projectPipelineViewPopupMonth"></span><a class="exportLink" href="javascript:;" target="_blank">Export</a></h2>

            <div class="dashboardTable pipelineTable">
                <div class="tableWrapper">
                    <table id="projectPipelineDrillInTable" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr class="head">
                            <th class="first noBT">Project Name</th>
                            <th class="noBT">Project Status</th>
                            <th class="noBT">Project Creation Time</th>
                            <th class="noBT">Project Completion Time</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="hide handler">
        <a href="#" id="projectPipelineViewHandler"></a>
    </div>
</div>

</body>
<!-- End #page -->

</html>
