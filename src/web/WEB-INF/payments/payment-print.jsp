<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
  - - First version of payment overview print page.
  -
  - Description: Render the payment overview print page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
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
<body class="dashboardPage">
<div id="wrapper" class="paymentPrintVersion">
    <!-- main section -->
    <div id="mainSection" class="dashboardPaymentArea">
        <h2>Member Payment Report<span class="timeStamp"></span></h2>
        
        <jsp:include page="./payment-overview-content.jsp"/>
    </div>
    <!-- End #mainSection -->
    <div class="footer">Copyright TopCoder, Inc. 2001-2013</div>
</div>
<!-- End #wrapper -->

</body>
<!-- End #page -->
</html>
