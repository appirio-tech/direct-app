<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
  - - First version of payment overview page.
  -
  - Description: Render the payment overview page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="payment" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-payments.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/jquery.history.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.js"></script>
    <script type="text/javascript" src="/scripts/enterprisePayment.js"></script>
</head>

<body id="page" class="paymentPage">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="wholeContent">
                    <div id="wholeArea">

                        <div class="enterpriseOverView">

                            <jsp:include page="../includes/payments/sidebar.jsp"/>

                            <!-- main section -->
                            <div id="mainSection" class="dashboardPaymentArea">
                                <h2>
                                    Member Payments
                                    <span class="utilityBtns">
                                        <a href="<s:url action="overview-print" namespace="/payments"/>" target="_blank" class="print">PRINT</a>
                                    </span>
                                </h2>

                                <jsp:include page="./payment-overview-content.jsp"/>
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
<div class="popups">
    <div id="modalBackground"></div>
    <div id="new-modal">
        <!-- #setWesternUnionBalanceModal -->
        <div id="setWesternUnionBalanceModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Set Western Union Balance Amount
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Balance Amount:</label>
                        <input type="text" class="text" id="westernUnionBalanceAmount" name="westernUnionBalanceAmount"/>
                        <div class="clearFix"></div>
                        <div class="errorInfo errorInfoEmpty hide">Please input balance amount</div>
                        <div class="errorInfo errorInfoNaN hide">balance amount is not a number</div>
                        <div class="errorInfo errorInfoNotPositive hide">balance amount must be positive</div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 newButtonSet"><span class="btnR"><span class="btnC">SET</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
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
        <!-- end #setWesternUnionBalanceModal -->
    </div>
</div>
</body>
<!-- End #page -->

</html>
