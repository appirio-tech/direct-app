<%--
  - Author: GreatKevin, Veve
  - Version: 1.5
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: JSP page for the jsgantt project game plan
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Change the use of %{#session.currentSelectDirectProjectID} to sessionData.currentSelectDirectProjectID so the JSP
  -   page can access the session on the first hit.
  -
  - Version 1.2 (BUGR-7728 Cockpit Game Plan Export Feature - Excel and Microsoft Project Support)
  - - Add game plan export link
  -
  - Version 1.3 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
  - remove include of TinyMCE, replaced with CKEditor.
  -
  - Version 1.4 (TC Direct Rebranding Assembly Project and Contest related pages)
  - Rebranding the jsgantt page
  -
  - Version 1.5 (TopCoder Direct - Change Right Sidebar to pure Ajax)
  - Add the right sidebar script reference
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Topcoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <jsp:include page="includes/serverConfiguration.jsp"/>

    <!-- External CSS -->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
    <link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/launchcontest.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/thickbox.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/jScrollPane.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css" />

    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css"/>
    <![endif]-->

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css"/>
    <![endif]-->

    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css"/>
    <![endif]-->

    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css"/>
    <![endif]-->
	
<script type="text/javascript">
//<![CDATA[
window.CKEDITOR_BASEPATH='/scripts/ckeditor/ckeditor/';
//]]>
</script>

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js"></script>

    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js"></script>
    <script type="text/javascript" src="/scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="/scripts/date.prev.js"></script>
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js"></script>
    <script type="text/javascript" src="/scripts/jquery.stylish-select.js"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js"></script>
	<script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js"></script>
	<script type="text/javascript" src="/scripts/jquery.cookie.js"></script>

    <script type="text/javascript" src="/scripts/dashboard.js"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js"></script>
    <script type="text/javascript" src="/scripts/maintenance.js"></script>
    <link rel="stylesheet" href="/css/direct/jsgantt.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jsgantt.js"></script>
    <script type="text/javascript" src="/scripts/directgantt.js"></script>
    <script type="text/javascript" src="/scripts/instantSearch.js"></script>
    <script type="text/javascript" src="/scripts/rightSidebar.js"></script>

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
                                <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><s:property value="sessionData.currentProjectContext.name"/>
                                </a> &gt;
                                <strong>Game Plan</strong>
                            </div>
                            <div class="batchButtons batchButtons2 gamePlanBatchButtons">
                                <!--<a class="batchCreate" href="javascript:;">Batch Create</a>-->
                                <a class="batchEdit" href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Batch Edit</a>
                                <a class="exportGamePlan" href="<s:url action="projectGamePlanExport" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Export Game Plan</a>
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
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
