<%--
  - Author: TCSASSEMBLER
  - Version: 1.2
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - Direct Improvements Assembly Release 3) changes:
  - Set the today in calendar with the today date on server, not on the client side.
  -
  - Version 1.2 (Release Assembly - TC Cockpit Enterprise Calendar Revamp) changes:
  - Update the page to show the milestones not activities
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <jsp:include page="includes/filterPanel.jsp"/>
    <link rel="stylesheet" href="/css/direct/milestone-fullCalendar.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/projectMilestone.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/milestone-fullCalendar.js"></script>

</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content milestoneManage enterpriseCalendar">
                            <div class="areaHeader">
                                <h2 class="title roadmap">Roadmap</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <form id="filterPanelForm" autocompleted="off">
                                <div class='filterPanel' id='enterpriseCalendarFilter'>
                                    <div class='filterHead'>
                                        <div class='rightSide'>
                                            <div class='inner'>
                                                <div class='filterText'>
                                                    <a href='javascript:;' class='collapse'><img
                                                            src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                    <span class='title'>Filter</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--end .filterHead-->
                                    <div class='filterContent'>
                                        <div class='rightSide'>
                                            <div class='inner'>
                                                <div class='column1'>
                                                    <div class='row'>
                                                        <span class='title'>Customer</span>
                                                        <select id='customerFilter'>
                                                            <option value='All Customers'>All Customers</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class='column2'>
                                                    <div class='row'>
                                                        <span class='title'>Project Filters</span>
                                                        <select id='groupBy' disabled="disabled">
                                                            <option value='0'>No Filter Applied</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <!--end .column1-->
                                                <div class='column3'>
                                                    <div class='row'>
                                                        <span class='title'>Project Filter Values</span>
                                                        <select id='groupValue' disabled="disabled">
                                                            <option value='all'>All Filter Values</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <!--end .column3-->
                                            </div>
                                        </div>
                                    </div>
                                    <!--end .filterHead-->
                                    <div class='filterBottom'>
                                        <div class='rightSide'>
                                            <div class='inner'></div>
                                        </div>
                                    </div>
                                    <!--end .filterBottom-->
                                    <div class='collapseBottom hide'>
                                        <div class='rightSide'>
                                            <div class='inner'></div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <div class="roadmapViewArea">
                                <div class="loading">
                                    <img src="/images/loadingAnimation.gif" alt=""/>
                                </div>
                                <div class="milestoneCalView">

                                </div>
                            </div>

                            <span id="calendarToday" style="display:none"><s:date name="calendarToday"
                                                                                  format="MM/dd/yyyy"/></span>

                            <jsp:include page="includes/copilotArea.jsp"/>
                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
