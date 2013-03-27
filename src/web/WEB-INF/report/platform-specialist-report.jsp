<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: The TopCoder Platform Specialists Report
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="platformSpecialistsReport"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/jquery.multiSelect.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/jsrender-min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/search.js"></script>
    <script type="text/javascript" src="/scripts/jquery.multiselect.js"></script>
    <script type="text/javascript" src="/scripts/platformSpecialistsReport.js"></script>
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

                            <!-- main section -->
                            <div id="mainSection" style="margin: 0px !important;">
                                <h2>Platform Specialists Report</h2>

                                <!-- filter panel -->
                                <div class="filterPanel">
                                    <div class="filterPanelHeader">
                                        <div class="filterPanelTitle">
                                            <h3 id="headerPlatformSpecialist"></h3>
                                        </div>

                                    </div>
                                    <!-- header of filter panel -->
                                    <div class="filterPanelContainer">
                                        <dl>
                                            <dt>Date Range :</dt>
                                            <dd class="last" id="headerDate"></dd>
                                        </dl>
                                        <div class="filterPanelButton">
                                            <a href="javascript:;" class="greyButton triggerModal" name="filterModal"><span><span><span class="filterIcon">Filter</span></span></span></a>
                                        </div>
                                    </div>
                                    <!-- container of filter panel -->
                                </div>
                                <!-- End .filterPanel -->

                                <!-- total spend section -->

                                <!-- financials section -->
                                <div class="sectionContainer financialsSection platformSpecialistsSection">

                                    <div class="sectionInner">

                                        <div class="financialTitle">
                                            <h3>Member Spend per Month</h3>
                                            <a href="javascript:;" class="icon"
                                               rel="Shows the monthly member spend of the TopCoder Platform Specialist">!</a>
                                        </div>
                                        <!-- title -->

                                        <div class="chartSection">
                                            <div id="chartPlatformSpecialistsSpend">
                                                <div class="ajaxTableLoader"><img src="/images/rss_loading.gif"
                                                                                  alt="loading"/></div>
                                            </div>
                                        </div>

                                        <div class="dataSection">


                                            <!-- table -->
                                            <div class="tableData">

                                                <table border="0" cellspacing="0" cellpadding="0" id="loadingTable">
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
<div id="modalBackground"></div>
<div id="new-modal">

    <!-- filter modal -->
    <div class="outLay" id="filterModal">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Filter
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="filterContainer">
                <div class="selectFilter">
                    <div class="row">
                        <div class="column">
                            <div>
                                <label>Select Platform Specialists</label>
                                <s:select list="platformSpecialists" multiple="true" cssClass="multiselect" name="platformSpecialist"
                                          id="platformSpecialist" size="5"/>
                            </div>
                        </div>
                        <div class="column">
                            <div id="zoomSelect">
                                <span class="label">Zoom</span>
                                <ul>
                                    <li class="currentMonth"><a href="javascript:;"><span>Current Month</span></a></li>
                                    <li class="threeMonths"><a href="javascript:;"><span>3 Months</span></a></li>
                                    <li class="sixMonths"><a href="javascript:;"><span>6 Months</span></a></li>
                                    <li class="oneYear"><a href="javascript:;"><span>1 Year</span></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="selectDate">
                    <a href="javascript:;" class="prevYear">PREV YEAR</a>
                    <div class="monthList">
                        <div class="timeLine">
                            <ul>
                                <s:iterator value="monthOptions">
                                    <li><a href="javascript:;"><span><span><s:property/></span></span></a></li>
                                </s:iterator>
                            </ul>
                        </div>
                    </div>
                    <a href="javascript:;" class="nextYear">NEXT YEAR</a>
                </div>
            </div>

            <div class="modalCommandBox">
                <a class="redButton closeModal" id="filterButton" href="javascript:;"><span class="left"><span class="right">FILTER</span></span></a>
                <a class="redButton closeModal" href="javascript:;"><span class="left"><span class="right">CANCEL</span></span></a>
                <div class="clear"></div>
            </div>
        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- End #filterModal -->

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
