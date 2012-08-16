<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: JSP page for the jsgantt project game plan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>TopCoder Cockpit</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/screen.css?v=214495" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/launchcontest.css?v=215011" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css?v=215352" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/thickbox.css?v=192822" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/jScrollPane.css?v=176771" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css?v=206355" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css?v=211688" media="all" type="text/css"/>

    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css?v=203928" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css?v=176771"/>
    <![endif]-->

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css?v=215325"/>
    <![endif]-->

    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css?v=203310"/>
    <![endif]-->

    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css?v=203310"/>
    <![endif]-->

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>

    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.bgiframe.js?v=207894"></script>
    <script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/common.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
    <script type="text/javascript" src="/scripts/jquery.stylish-select.js?v=188719"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js?v=209582"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
    <script type="text/javascript" src="/scripts/tinyMCE/tiny_mce/tiny_mce.js?v=210496"></script>
    <script type="text/javascript" src="/scripts/jquery.autocomplete.js?v=183826"></script>
    <script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js?v=215325"></script>

    <script type="text/javascript" src="/scripts/dashboard.js?v=215352"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js?v=211035"></script>
    <script type="text/javascript" src="/scripts/maintenance.js?v=214603"></script>
    <link rel="stylesheet" href="/css/direct/jsgantt.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jsgantt.js"></script>
    <script type="text/javascript" src="/scripts/directgantt.js"></script>
    <ui:projectPageType tab="gameplan"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="noRightBar">

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>"><s:property value="sessionData.currentProjectContext.name"/>
                                </a> &gt;
                                <strong>Game Plan</strong>
                            </div>
                            <div class="areaHeader" style="width:400px">
                                <div style="float:left"><h2 class="title">Game Plan Gantt Chart</h2> </div>
                                <input type="hidden" id="projectIDHolder" value="${formData.projectId}"/>
                                <div style="float:right; padding-top: 4px;">
                                    <a style="text-align: center; " class="button5" id='hideLeftAll'
                                       href="javascript:;">Hide Left Panel</a>
                                </div>
                            </div>

                            <jsp:include page="includes/project/projectStats.jsp"/>

                            <div class="container2">
                                <!-- error information container -->

                                <div id="ganttChartError" class="hide"></div>
                                <!-- gantt chart container-->
                                <div id="jsGanttChartDiv" style="position:relative" class="gantt"></div>


                            </div><!-- End .container2 -->
                        </div>
                    </div>
                </div>

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
