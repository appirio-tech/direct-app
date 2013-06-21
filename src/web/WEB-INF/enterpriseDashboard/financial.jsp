<%--
  - Author: GreatKevin
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
  - - Add total spend chart drill-in popup
  -
  - Version 1.2 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Add history.js to support push state and back-compatible with IE with hash-fallback
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
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.svn219021.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.svn228165.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/jsrender-min.svn225439.js"></script>
    <script type="text/javascript" src="/scripts/search.svn228935.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.svn218673.js"></script>
    <script type="text/javascript" src="/scripts/exporting.svn218673.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.svn227019.js"></script>
    <script id="financialDrillInTemplate" type="text/x-jsrender">
        <tr>
            <td class="first">
                <a href="../projectOverview?formData.projectId={{:directProjectId}}" target="_blank">{{:directProjectName}}</a>
            </td>
            <td>{{:~formatMoney(memberCost)}}</td>
            <td>{{:~formatMoney(contestFee)}}</td>
            <td>{{:~formatMoney(spend)}}</td>
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

                                        <div class="totalSpendTitle"  data-intro="Total spend is calculated by adding together all of your competitions costs as well as any fees. The blue bars represent your spend per month.  The red line is a 3 month moving average." data-step="1">
                                            <h3>Total Spend</h3>
                                            <a onclick="javascript:introJs().start();" href="javascript:;" class="icon"
                                               rel="Total Amount Spent for the Projects each month is shown here.">!</a>
                                        </div>
                                        <!-- title -->
                                        <div class="numberSection">
                                            <div class="numberSectionInner" data-intro="At a quick glance, see what you spent so far this month, last month, your average per month, and the total for the time range in your filter." data-step="2">
                                                <ul>
                                                    <li class="last ajaxTableLoader"><img
                                                            src="/images/rss_loading.gif" alt="loading"/></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="chartSection">
                                            <div id="chartTotalSpend" data-intro="Click a blue bar to drill into the data." data-step="3" data-position="top">
                                                <div class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                  alt="loading"/></div>
                                            </div>
                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>

                                    </div>

                                </div>

                                <!-- financials section -->
                                <div class="sectionContainer financialsSection">

                                    <div class="sectionInner">

                                        <div class="financialTitle"  data-intro="The financials table is a tabular summary of your monthly spend.  It's the same data that you see in the chart above." data-step="4" data-position="top">
                                            <h3>Financials</h3>
                                            <a onclick="javascript:introJs().start();" href="javascript:;" class="icon"
                                               rel="Shows your monthly projects cost and the total sum">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">


                                            <!-- table -->
                                            <div class="tableData" data-intro="Member costs include all competition and task costs that are paid to the community. Fees include your per-competition fee.  This is in addition to the community payments. Total cost currently does NOT include your platform license fees." data-step="5" data-position="top">

                                                <table border="0" cellspacing="0" cellpadding="0" id="financials">
                                                    <colgroup>
                                                        <col width="25%"/>
                                                        <col width="25%"/>
                                                        <col width="25%"/>
                                                        <col width="25%"/>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Month</th>
                                                        <th>Member Costs</th>
                                                        <th>Contest Fees</th>
                                                        <th>Total</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="4">
                                                            <div class="ajaxTableLoader"><img
                                                                    src="/images/rss_loading.gif" alt="loading"/></div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>

                                            </div>
                                            <!-- End .tableData -->

                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>

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
                            <th class="noBT">Member Cost</th>
                            <th class="noBT">Contest Fees</th>
                            <th class="noBT">Total Cost</th>
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
             <col width="25%"/>
             <col width="25%"/>
             <col width="25%"/>
             <col width="25%"/>
         </colgroup>
         <thead>
         <tr>
             <th>Month</th>
             <th>Member Costs</th>
             <th>Contest Fees</th>
             <th>Total</th>
         </tr>
         </thead>
         <tbody>
         <tr>
             <td colspan="4">
                 <div class="ajaxTableLoader"><img
                         src="/images/rss_loading.gif" alt="loading"/></div>
             </td>
         </tr>
         </tbody>
     </table>
</span>

</body>
<!-- End #page -->

</html>
