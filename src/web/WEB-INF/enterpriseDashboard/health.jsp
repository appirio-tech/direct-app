<%--
  - Author: TCSASSEMLBER

  - Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version: 1.0 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
  -
  - Version 1.1 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove the uneeded corners in div
  -
  - Description: The project health page, it displays project financial health data
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="health" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
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
                                <h2 data-intro="Welcome to your Projects Health Dashboard. Below you will find some information regarding your projects such as budgets and actual vs. projected costs." data-step="1">Projects Health</h2>

                                <!-- filter panel -->
                                <jsp:include page="../includes/enterpriseDashboard/filterHeader.jsp"/>
                                <!-- End .filterPanel -->

                                <!-- financials section -->
                                <div class="sectionContainer financialsSection">

                                    <div class="sectionInner">

                                        <div class="projectsHealthTitle">
                                            <h3>Projects Health</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="Showing the overall projects Health of all projects">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">

                                            <!-- table -->
                                            <div class="tableData healthTableData" data-intro="Use the filter above to select different projects. The colors below alert you to potential issues." data-step="2">

                                                <table border="0" cellspacing="0" cellpadding="0" id="financials" class="paginatedDataTable">
                                                    <colgroup>
                                                        <col width="25%" data-intro="The name of your projects." data-step="3" />
                                                        <col data-intro="Budget meter. If it is red, your project is currently exceeding the planned budget." data-step="4" />
                                                        <col width="11%" data-intro="The budget you set on your projects. Please set them if they are currently not set." data-step="5" />
                                                        <col width="11%" data-intro="The actual cost of your project to date." data-step="6" />
                                                        <col width="11%" data-intro="The projected cost based on your actuals plus the planned costs." data-step="7" />
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Project Name</th>
                                                        <th>Project Budget</th>
                                                        <th>Total Budget</th>
                                                        <th>Actual Cost</th>
                                                        <th>Projected Cost</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="5">
                                                            <div class="ajaxTableLoader"><img
                                                                    src="/images/rss_loading.gif" alt="loading"/></div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot class="reportTotal">
                                                        <tr><td colspan="2" style="text-align: right">Total Sum:&nbsp;&nbsp;</td><td></td><td></td><td></td></tr>
                                                    </tfoot>
                                                </table>

                                            </div>
                                            <!-- End .tableData -->

                                        </div>

                                    </div>
                                </div>
                                <!-- End .financialsSection -->

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

<span class="hide" id="tableTemplate">
     <table border="0" cellspacing="0" cellpadding="0">
         <colgroup>
             <col width="25%"/>
             <col/>
             <col width="11%"/>
             <col width="11%"/>
             <col width="11%"/>
         </colgroup>
         <thead>
         <tr>
             <th>Project Name</th>
             <th>Project Budget</th>
             <th>Total Budget</th>
             <th>Actual Cost</th>
             <th>Projected Cost</th>
         </tr>
         </thead>
         <tbody>
         <tr>
             <td colspan="5">
                 <div class="ajaxTableLoader"><img
                         src="/images/rss_loading.gif" alt="loading"/></div>
             </td>
         </tr>
         </tbody>
         <tfoot>
            <tr class="reportTotal"><td colspan="2" style="text-align: right">Total Sum:&nbsp;&nbsp;</td><td style="text-align: center"></td><td style="text-align: center"></td><td style="text-align: center"></td></tr>
         </tfoot>
     </table>
</span>

</body>
<!-- End #page -->

</html>
