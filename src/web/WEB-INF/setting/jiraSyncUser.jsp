<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the sync user between jira and wiki setting page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/notifications.js?v=214844"></script>
    <ui:dashboardPageType tab="dashboard"/>
</head>

<c:set var="CURRENT_TAB" scope="request" value="settings"/>

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
                                <strong>Synchronize User in JIRA and WIKI</strong>
                            </div>

                            <div class="areaHeader">
                                <h2 class="title contestTitle">Sync User</h2>
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
                            <option value="fee">
                                Challenge Fee</option>
                        </s:if>
                        <s:if test="jiraSynAccessible">
                            <option selected="selected" value="sync">
                                Synchronize User in JIRA and WIKI</option>
                        </s:if>
                    </select>
                </span>
                                            <div class="clearFix"></div>
                                        </div>
                                        <span class="corner tl"></span>
                                        <span class="corner tr"></span>
                                        <span class="corner bl"></span>
                                        <span class="corner br"></span>
                                    </div>
                                </div>
                            </div>
                            <!-- End .areaHeader -->
                            <div>
                                <div style="width:300px;">
                                    <label>Handle:</label> <input type="text" size="15" id="handle" style="padding:0 5px; font-size: 11px"/>
                                    <a class="button6 applyButton" href="javascript:syncUser();"><span class="left"><span class="right">SYNC</span></span></a>
                                </div>
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
