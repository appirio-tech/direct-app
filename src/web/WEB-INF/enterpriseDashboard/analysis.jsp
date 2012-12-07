<%--
  - Author: GreatKevin
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
  -
  - Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 2)
  - - Add table view and volume view summary table.
  -
  - Description: The analysis contests page of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="analysis" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <jsp:include page="../includes/paginationSetup.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript" src="/scripts/search.js"></script>
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


<!-- analytics section -->
<div class="analyticsSection">

<div class="sectionInner">

<div class="analyticsTitle">
    <h3>Analytics</h3>
    <a href="javascript:;" class="icon"
       rel="Analyse the contest cost, time and fulfillment of your projects">!</a>
</div>
<!-- title -->

<div class="analyticsContainer">

<!-- filter -->
<div class="filterForAnalytics">
    <div class="filterTitle">
        <a href="javascript:;" class="folder">Filter</a>
    </div>
    <div class="filterContainer">

        <!-- column -->
        <div class="column firstColumn">
            <div class="row">
                <div>
                    <label>Customer Name:</label>
                    <s:select name="customer" list="viewData.clients" value="formData.customerId"/>
                </div>
            </div>
            <div class="row groupRow">
                <div>
                    <label>Group By:</label>
                    <s:select id="timeDimension" name="groupType" list="viewData.groupTypes" value="formData.groupType"/>
                </div>
            </div>

            <div class="row dateRow">
                <div>

                </div>
                <div class="clear"></div>
            </div>
        </div>
        <!-- End .column -->

        <!-- column -->
        <div class="column secondColumn">
            <div class="row">
                <div>
                    <label>Billing Account:</label>
                    <s:select name="billingAccount" list="viewData.billingAccounts" value="formData.billingAccountId"/>
                </div>
            </div>
            <div class="row billingRow">
                <div>
                    <label>Project Name:</label>
                    <s:select name="project" list="viewData.projects" value="formData.projectId"/>
                </div>
            </div>
        </div>
        <!-- End .column -->

        <!-- column -->
        <div class="column thirdColumn">
            <div class="row">
                <div>
                    <label>Contest Type</label>

                    <div class="selectWrapper">
                        <ul>
                            <li><input type="checkbox" id="checkAll" checked="checked"/><span>Select All</span></li>
                            <s:iterator value="viewData.contestTypes">
                                <li><input type="checkbox" value="${key}" checked="checked"/><span>${value}</span></li>
                            </s:iterator>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .column -->
        <div class="clear"></div>

        <div class="button">
            <span class="errorMessage" style="padding-left: 20px"></span>
            <a href="javascript:;" id="filterApply" class="redButton"><span class="left"><span class="right">APPLY</span></span></a>
        </div>

    </div>
</div>
<!-- End .filterForAnalytics -->


<!-- filter panel -->
<div class="filterPanel" style="margin-top:20px;">
    <div class="filterPanelHeader">
        <div class="filterPanelTitle">
            <h3></h3>
            <dl>
                <dt>Project :</dt>
                <dd id="projectHeader"></dd>
                <dt>Billing :</dt>
                <dd id="billingHeader"></dd>
                <dt>Group By :</dt>
                <dd id="groupHeader"></dd>
                <dt>From</dt>
                <dd class="last" id="timeHeader"></dd>
            </dl>
        </div>

    </div>


</div>
<!-- End .filterPanel -->

<!-- View -->
<div class="analyticsView">

<!-- title -->
<div class="viewTitle">
    <a href="javascript:;" class="folder">Chart View</a>
    <ul>
        <li class="active"><a rel="lineViewContainer" href="javascript:;" class="chartView">Chart</a></li>
        <li><a rel="tableViewContainer" href="javascript:;" class="tableView">Table</a></li>
    </ul>
</div>
<!-- End .viewTitle -->

<!-- container -->
<div class="viewContainer">

<!-- data -->
<div class="viewDate">

    <div class="tabHeader">

        <ul class="tabs customer">
            <li><a href="javascript:;" rel="customerSummary"
                   class="customer current"><span><span class="customerReplacement">Summary</span></span></a></li>
            <li><a href="javascript:;" rel="marketSummary" class="market"><span><span>Market Summary</span></span></a>
            </li>
            <li><a href="javascript:;" rel="compare" class="compare"><span><span>Compare</span></span></a></li>
        </ul>
    </div>
    <div class="tabsContent">
        <div class="customerSummary customerTabContainer" id="blue">
            <div class="leftCorner">
                <div class="rightCorner">
                    <div class="middleBg">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="fieldName tipToggle" title="The average contest cost of the customer">
                                        Avg. Cost:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerAverageCost">$3,169.50</td>
                                <td>
                                    <div class="fieldName tipToggle" title="Average Contest Duration of the customer">
                                        Avg. Duration:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerAverageDuration">10 days</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Average Contest Volume in 30 days for the customer">Avg. Vol:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerAverageVol">13.5</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Average contests fulfillment of the customer">Avg. Fulfillment:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerAverageFulfillment">90.26%</td>
                                <td>
                                    <div class="marketPercent">% of Market</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The cost range of contests for the customer, from min to max">Cost
                                        Range:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerMinMaxCost">$975.00 - $8,319.00</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The contest duration range of contests for the customer, from min to max in days">
                                        Duration Range:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerMinMaxDuration">2.3 - 20.5 days</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Total number of contests completed in the date range for customer">Total
                                        Vol:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerTotalVol">82</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The total memeber cost of the contests for the customer">Total Member
                                        Cost:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="customerTotalCost">$255,883.14</td>
                                <td class="marketPercentNumber" id="customerMarketCap">0.08%</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .customerSummary -->
        <div class="marketSummary customerTabContainer hide" id="green">
            <div class="leftCorner">
                <div class="rightCorner">
                    <div class="middleBg">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The average contest cost of the whole market in the date range">Avg.
                                        Cost:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketAverageCost">$3,290.06</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Average Contest Duration of the whole market">Avg. Duration:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketAverageDuration">9.9 days</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Average Contest Volume in 30 days for the whole market">Avg. Vol:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketAverageVol">83.9</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Average contests fulfillment of the whole market">Avg. Fulfillment:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketAverageFulfillment">89.03%</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The cost range of contests for the whole market, from min to max">Cost
                                        Range:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketMinMaxCost">$225.00 - $21,874.00</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The contest duration range of contests for the whole market, from min to max in days">
                                        Duration Range:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketMinMaxDuration">1 - 44.1 days</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="Total number of contests completed in the date range for whole market">
                                        Total Vol:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketTotalVol">998</td>
                                <td>
                                    <div class="fieldName tipToggle"
                                         title="The total memeber cost of the contests for the whole market">Total
                                        Member Cost:
                                    </div>
                                </td>
                                <td class="fieldNumber" id="marketTotalCost">$3,283,482.31</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .marketSummary -->
        <div class="compare customerTabContainer hide" id="orange">
            <div class="leftCorner">
                <div class="rightCorner">
                    <div class="middleBg">
                        <table cellpadding="0" cellspacing="0" border="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer average fulfillment with market average fulfillment">
                                        Avg. Fulfillment
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer average contest cost with market average contset cost">
                                        Avg. Cost
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer average contest duration with market average contest duration">
                                        Avg. Duration
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer average volume per month with market average volume per month">
                                        Avg. Vol
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer total contests volume with market total contests volume">
                                        Total Vol
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer total member cost with market total member cost">
                                        Total Member Cost
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer contest cost range with market contest cost range">
                                        Cost Range
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="Comparison of customer contest duration range with market contest duration range">
                                        Duration Range
                                    </div>
                                </th>
                                <th>
                                    <div class="fieldName tipToggle"
                                         title="The percentage of (customer total member cost / market total member cost)">
                                        % of Market
                                    </div>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="customerData">
                                <td class="dataTitle customerReplacement">Customer</td>
                                <td id="customerAverageFulfillmentComparison">90.26%</td>
                                <td id="customerAverageCostComparison">$3,169.50</td>
                                <td id="customerAverageDurationComparison">10 days</td>
                                <td id="customerAverageVolComparison">13.5</td>
                                <td id="customerTotalVolComparison">82</td>
                                <td id="customerTotalCostComparison">$255,883.14</td>
                                <td id="customerMinMaxCostComparison">$975.00 - $8,319.00</td>
                                <td id="customerMinMaxDurationComparison">2.3 - 20.5 days</td>
                                <td id="marketCap" rowspan="2" class="marketPercentNumber">0.08%</td>
                            </tr>
                            <tr class="marketData">
                                <td class="dataTitle">Market</td>
                                <td id="marketAverageFulfillmentComparison">89.03%</td>
                                <td id="marketAverageCostComparison">$3,290.06</td>
                                <td id="marketAverageDurationComparison">9.9 days</td>
                                <td id="marketAverageVolComparison">83.9</td>
                                <td id="marketTotalVolComparison">998</td>
                                <td id="marketTotalCostComparison">$3,283,482.31</td>
                                <td id="marketMinMaxCostComparison">$225.00 - $21,874.00</td>
                                <td id="marketMinMaxDurationComparison">1 - 44.1 days</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .compare -->
    </div>
    <!-- End .tabsContent -->

</div>
<!-- End .viewDate -->

<!-- table view -->
<div class="tableViewContainer analyticsViewTabContainer">
    <table border="0" cellpadding="0" cellspacing="0">
        <colgroup>
            <col width="8%"/>
            <col width="8%"/>
            <col width="14%"/>
            <col width="18%"/>
            <col width="12%"/>
            <col width="6%"/>
            <col width="6%"/>
            <col width="8%"/>
            <col width="8%"/>
            <col width="6%"/>
            <col width="6%"/>
        </colgroup>
        <thead>
        <tr>
            <th rowspan="2">Date</th>
            <th rowspan="2">Customer</th>
            <th rowspan="2">Projects</th>
            <th rowspan="2">Contest Name</th>
            <th rowspan="2">Contest Type</th>
            <th colspan="2">Fulfillment</th>
            <th colspan="2">Member Cost</th>
            <th colspan="2">Duration (days)</th>
        </tr>
        <tr>
            <th>Contest</th>
            <th>Market Avg</th>
            <th>Contest</th>
            <th>Market Avg</th>
            <th>Contest</th>
            <th>Market Avg</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="11">
                <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading"/></div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- End .tableViewContainer -->

<!-- line view -->
<div class="lineViewContainer analyticsViewTabContainer">

    <div id="lineView">
        <div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading"/></div>
    </div>

    <!-- button -->
    <div class="buttonArea">
        <div class="displayButton">
            <span class="label">Displaying</span>
            <ul>
                <li class="firstSelectot"><a href="javascript:;"> <span><input type="checkbox" class="chkTimeframe hide"
                                                                               id="idDuration"/>Contest Duration</span></a>
                </li>
                <li><a href="javascript:;" class="current"> <span><input type="checkbox" class="chkTimeframe hide"
                                                         id="idCost" checked="checked"/>Cost</span></a></li>
                <li><a href="javascript:;"><span><input type="checkbox" id="idFullfilment" class="chkTimeframe hide"/> Fulfillment</span></a>
                </li>
                <li><a href="javascript:;"  class="current"> <span><input type="checkbox" id="idVolume" checked="checked" class="chkTimeframe hide"/>Volume View</span></a>
                </li>
                <li class="lastSelector"><a href="javascript:;" class="current"> <span><input type="checkbox" id="idMarket"
                                                                              class="chkTimeframe hide" checked="checked"/>Market</span></a>
                </li>
            </ul>
        </div>
        <div class="zoomButton">
            <span class="label">Zoom</span>
            <ul>
                <li class="oneWeek firstSelectot"><a href="javascript:;"><span>1 Month</span></a></li>
                <li class="oneMonth34"><a href="javascript:;"><span>2 Month</span></a></li>
                <li class="threeMonth"><a href="javascript:;"><span>3 Month</span></a></li>
                <li class="sixMonth"><a href="javascript:;"><span>4 Month</span></a></li>
                <li class="oneYear lastSelector"><a href="javascript:;"><span>YTD</span></a></li>
            </ul>
        </div>
    </div>
    <!-- End .buttonArea -->

</div>
<!-- End .lineViewContainer -->


</div>
<!-- End .viewContainer -->
</div>
<!-- End .analyticsView -->

<!-- volumn view -->
<div class="volumeView hide">

    <!-- title -->

    <!-- End .viewTitle -->

    <!-- container -->
    <div class="volumeViewContainer">

        <div class="sectionTop">
            <h3 class="customerReplacement">Customer Volume Summary</h3>
        </div>

        <!-- data -->
        <div class="tableData">
            <div class="summanyTable">
                <table border="0" cellpadding="0" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Status Summary</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <div class="fieldName tipToggle"
                                 title="The average completed contest volume in 30 days in the chosen date range">Avg.
                                Completed
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="fieldName tipToggle"
                                 title="The average failed contest volume in 30 days in the chosen date range">Avg.
                                Failed
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="fieldName tipToggle"
                                 title="The total completed contest volume in the chosen date range.">Total
                                Completed
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="lastRow">
                            <div class="fieldName tipToggle"
                                 title="The total failed contest volume in the chosen date range.">Total
                                Failed
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!-- jsonData -->
            <div class="jsonData">
                <div class="jsonDataInner volumeSummaryTableDiv">
                    <table border="0" cellpadding="0" cellspacing="0" class="tableHeader" id="volumeSummaryTable">
                        <colgroup>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                            <col width="100"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="firstColumn">Prototype</th>
                            <th>Web Design</th>
                            <th>Content</th>
                            <th>Wireframes</th>
                            <th>Reporting</th>
                            <th>Other</th>
                            <th>Banners/Icons</th>
                            <th>Suites</th>
                            <th>Assembly</th>
                            <th>Marathon Match</th>
                            <th>Print</th>
                            <th>Logo</th>
                            <th>Idea</th>
                            <th>Concept</th>
                            <th>RIA Build</th>
                            <th>RIA component</th>
                            <th>Scenarios</th>
                            <th>Copilot</th>
                            <th>Dev</th>
                            <th>Design</th>
                            <th>Widget</th>
                            <th>Archi</th>
                            <th>Spec</th>
                            <th>App Design</th>
                            <th>Flash</th>
                            <th>Bug Hunt</th>
                            <th>Total</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>

                            </tr>
                            <tr class="odd">

                            </tr>
                            <tr>

                            </tr>
                            <tr class="odd">

                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="clear"></div>
        </div>

    </div>
    <!-- End .volumeViewContainer -->

</div>
<!-- End .volumeView -->

</div>

<div class="corner tl"></div>
<div class="corner tr"></div>

</div>

</div>
<!-- End #communitySection -->

</div>
<!-- End #mainSection -->

</div>

</div>
<div id="tipForTab">
    <div class="tooltipBox">
        <span class="arrow"></span>
        <div class="tooltipHeader">
            <div class="tooltipHeaderRight">
                <div class="tooltipHeaderCenter">
                    <h2></h2>
                </div>
            </div>
        </div>
        <div class="tooltipContent">
            <p></p>
        </div>
        <div class="tooltipFooter">
            <div class="tooltipFooterRight">
                <div class="tooltipFooterCenter"></div>
            </div>
        </div>
    </div>
</div>
<div id="modalBackground"></div>
<div id="new-modal">
</div>

</div>

<jsp:include page="../includes/footer.jsp"/>


</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

</body>
<!-- End #page -->
</html>

