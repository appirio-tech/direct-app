<%--
  - Author: TCSASSEMLBER
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
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

                                        <div class="chartSection">
                                            <div id="chartTotalSpend">
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

                                        <div class="financialTitle">
                                            <h3>Financials</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a orci sit amet orci">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="dataSection">

                                            <div class="numberSection">
                                                <div class="numberSectionInner">
                                                    <ul>
                                                        <li class="last ajaxTableLoader"><img
                                                                src="/images/rss_loading.gif" alt="loading"/></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <!-- End .numberSection -->

                                            <h4>Project Information</h4>

                                            <!-- table -->
                                            <div class="tableData">

                                                <table border="0" cellspacing="0" cellpadding="0" id="financials">
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
     </table>
</span>

</body>
<!-- End #page -->

</html>
