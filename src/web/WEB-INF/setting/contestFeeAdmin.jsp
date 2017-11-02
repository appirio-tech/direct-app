<%--
  - Author: TCSASSEMBLER
  -
  - Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
  -
  - Version 1.2 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove the uneeded corners in div
  -
  - Description: This page renders the contest fee admin setting page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>

    <ui:adminPageType tab="contestFee"/>
</head>

<body id="page">

<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent" class="newSidebarCollapse">

<jsp:include page="/WEB-INF/includes/right.jsp"/>
<div id="area1"><!-- the main area -->
    <div class="area1Content">
        <div class="currentPage">
            <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
            <strong>Settings</strong> &gt;
            <strong>Challenge Fee Management</strong>
        </div>

        <div class="areaHeader">
            <h2 class="title contestTitle">Challenge Fee Management</h2>
            <div class="select">
                <div class="selectInnerBox">
                    <div class="selectInner">
                        <label for="selectSetting">Select a Setting Panel:</label>
                <span class="settingPanel">
                    <select id="selectSetting" name="select">
                        <option value="notifications">
                            Notifications</option>
                        <option value="permissions">
                            Permissions</option>
                        <s:if test="ContestFeeSettingAccessible">
                            <option selected="selected" value="fee">
                                Challenge Fee</option>
                        </s:if>
                        
                    </select>
                </span>
                        <div class="clearFix"></div>
                    </div>

                </div>
            </div>
        </div>
        <!-- End .areaHeader -->
        <div>
            <p class="billingAccountArchive"><a
                    href='<s:url action="listBillingAccountAction" namespace="/"/>'>Project Challenge
                Fees Management for Billing Accounts</a></p>

            <p class="billingAccountArchive"><a
                    href='<s:url action="createContestFeesHome" namespace="/"/>'>Project Challenge
                Fees Creation for Billing Accounts</a></p>

            <p class="billingAccountArchive"><a
                    href='<s:url action="listCustomerPlatformFee" namespace="/"/>'>Platform Fee Management for Client</a></p>

            <p class="billingAccountArchive"><a
                    href='<s:url action="enterCreateCustomerPlatformFee" namespace="/"/>'>Platform Fee Creation for Client</a></p>
        </div>
    </div>
</div>

</div>


</div>
<!-- End #mainContent -->
</div>
<!-- End #content -->
<jsp:include page="/WEB-INF/includes/footer.jsp"/>

</div>
<!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>


</body>
<!-- End #page -->


</html>
