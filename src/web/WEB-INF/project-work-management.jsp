<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%--
  - Author: isv
  -
  - Version: 1.1 (TC Direct - ASP Integration Work Management)
  - Copyright (C) 2015-2016 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project work step management view.
  - Version 1.1 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT) changes:
  -  Added button for checking the submission's push status 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Topcoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/screen.css?v=214495" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/launchcontest.css?v=215011" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css?v=215352" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/thickbox.css?v=192822" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/jScrollPane.css?v=176771" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css?v=206355" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css?v=211688" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/introjs.css"/>

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

    <jsp:include page="includes/serverConfiguration.jsp"/>

    <!-- External javascript -->
    <script type="text/javascript">
        //<![CDATA[
        window.CKEDITOR_BASEPATH='/scripts/ckeditor/ckeditor/';
        //]]>
    </script>

    <script type="text/javascript" src="/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>

    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.bgiframe.js?v=207894"></script>
    <script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/date.js?v=185881"></script>
    <script type="text/javascript" src="/scripts/common.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
    <script type="text/javascript" src="/scripts/jquery.stylish-select.js?v=188719"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js?v=209582"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
    <script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="/scripts/jquery.autocomplete.js?v=183826"></script>
    <script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js?v=215325"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=215325"></script>

    <script type="text/javascript" src="/scripts/dashboard.js?v=215352"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js?v=211035"></script>
    <script type="text/javascript" src="/scripts/maintenance.js?v=2146111"></script>
    <script type="text/javascript" src="/scripts/instantSearch.js"></script>
    <script type="text/javascript" src="/scripts/intro.js"></script>
    <script type="text/javascript" src="/scripts/jquery.jqtransform.js" ></script>
    <script type="text/javascript" src="/scripts/rightSidebar.js" ></script>
    <link rel="stylesheet" href="/css/direct/jqtransform.css" media="all" type="text/css"/>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
    <link rel="stylesheet" href="/css/direct/hcolumns.css" type="text/css">
    <script src="/scripts/jquery.hcolumns.js"></script>
    <script src="/scripts/projectWorkManager.js"></script>
    <ui:projectPageType tab="workManagement"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <!-- begin area1 -->
                    <div id="area1">

                        <!-- the main area -->
                        <div class="area1Content">

                            <div class="spaceWhite"></div>

                            <!-- begin topLine -->
                            <div class="projectVMManagement">

                                <%
                                    String[] splits = ApplicationServer.SERVER_NAME.split(".");
                                    String topServerName = "topcoder.com";
                                    if (splits != null && splits.length >= 2) {
                                        topServerName = splits[splits.length - 2] + "." + splits[splits.length - 1];
                                    }
                                %>

                                <h2>Work Management</h2> <a href="https://connect.<%=topServerName%>/customer/projects/${demandWorkId}/timeline" target="_blank">Work Manager Project</a>
                            </div>
                            <!-- end topLine -->

                            <div id="WorkManagerDiv"></div>
                            <div id="pushButtonDiv">
                                <a class="newButtonGreen" style="cursor: default;" id="pushSubmissionsBtn">Push 5 checkpoint submissions</a>
                                <br/>
                                <br/>
                                <a class="newButtonGreen pushStatus" style="cursor: default;" id="checkPushStatusBtn">Check push status</a>
                            </div>


                        </div>
                        <!-- end main area -->
                    </div>
                    <!-- end area1 -->
                </div>
                <!-- end main content -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- end content -->
        </div>
        <!-- end container -->
    </div>
    <!-- end wrapper inner -->
</div>
<!-- end wrapper -->

<jsp:include page="includes/popups.jsp"/>
</body>
