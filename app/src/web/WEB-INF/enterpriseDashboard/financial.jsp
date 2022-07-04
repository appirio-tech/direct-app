<%--
  - Author: GreatKevin, TCSASSEMBLER
  - Version: 1.4
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
  - - Add total spend chart drill-in popup
  -
  - Version 1.2 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Add history.js to support push state and back-compatible with IE with hash-fallback
  -
  - Version 1.3 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
  - - Add projected cost
  -
  - Version 1.4 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove the uneeded corners in div
  -
  - Description: The financial page the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="financial" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/jsrender.min.js"></script>
    <script type="text/javascript" src="/scripts/search.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.js"></script>
    <script id="financialDrillInTemplate" type="text/x-jsrender">
        <tr>
            <td class="first">
                <a href="../projectOverview?formData.projectId={{:directProjectId}}" target="_blank">{{:directProjectName}}</a>
            </td>
            <td>{{:~formatMoney(memberCost)}}</td>
            <td>{{:~formatMoney(projectedCost)}}</td>
            <td>{{:~formatMoney(contestFee)}}</td>
            <td>{{:~formatMoney(spend)}}</td>
            <td>{{:~formatMoney(projectedTotal)}}</td>
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
                                <h2>Financials</h2>

                                <!-- filter panel -->
                                <jsp:include page="../includes/enterpriseDashboard/filterHeader.jsp"/>
                                <!-- End .filterPanel -->

                                <!-- total spend section -->
                                <div class="totalSpendSection">

                                    <div class="sectionInner">

                                        <div class="totalSpendTitle">
                                            <h3>Total Spend</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="Total Amount Spent for the Projects each month is shown here.">!</a>
                                        </div>
                                        <!-- title -->
                                        <div class="numberSection">
                                            <div class="numberSectionInner">
                                                <ul>
                                                    <li class="last ajaxTableLoader"><img
                                                            src="/images/rss_loading.gif" alt="loading"/></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="chartSection">
                                            <div id="chartTotalSpend">
                                                <div class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                  alt="loading"/></div>
                                            </div>
                                        </div>

                                    </div>

                                </div>

                                <!-- financials section -->
                                <div class="sectionContainer financialsSection">

                                    <div class="sectionInner">

                                        <div class="financialTitle">
                                            <h3>Financials</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="Shows your monthly projects cost and the total sum">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">


                                            <!-- table -->
                                            <div class="tableData">

                                                <table border="0" cellspacing="0" cellpadding="0" id="financials">
                                                    <colgroup>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                        <col width="20%"/>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Month</th>
                                                        <th>Member Costs</th>
                                                        <th>Challenge Fees</th>
                                                        <th>Actual Total</th>
                                                        <th>Projected Total</th>
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

<div class="popUps">
    <div class="expandViewPopup" id="financialViewPopup" style="display:none">
        <div class="close">
            <a href="javascript:void(0)" id="financialViewClose"></a>.
        </div>
        <div class="popContent">

            <h2>Total Spend - <span id="financialViewPopupMonth"></span><a class="exportLink" href="javascript:;" target="_blank">Export</a></h2>

            <div class="dashboardTable financialTable">
                <div class="tableWrapper">
                    <table id="financialDrillInTable" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr class="head">
                            <th class="first noBT">Project Name</th>
                            <th class="noBT">Actual Member Cost</th>
                            <th class="noBT">Projected Cost</th>
                            <th class="noBT">Actual Challenge Fees</th>
                            <th class="noBT">Actual Total Cost</th>
                            <th class="noBT">Projected Total Cost</th>
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
        <a href="#" id="financialViewHandler"></a>
    </div>
</div>

<span class="hide" id="tableTemplate">
     <table border="0" cellspacing="0" cellpadding="0">
         <colgroup>
             <col width="20%"/>
             <col width="20%"/>
             <col width="20%"/>
             <col width="20%"/>
             <col width="20%"/>
         </colgroup>
         <thead>
         <tr>
             <th>Month</th>
             <th>Member Costs</th>
             <th>Challenge Fees</th>
             <th>Actual Total</th>
             <th>Projected Total</th>
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
     </table>
</span>
<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
