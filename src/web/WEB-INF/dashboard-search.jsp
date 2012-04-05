<%--
  - Author: winsty, GreatKevin
  - Version: 1.4
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of search projects and contests.
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  - Version 1.2 (TC Direct UI Improvement Assembly 1) changes: Solve "Project is not highlight when select Project"
  - Version 1.2.1 (Release Assembly - TopCoder Cockpit Project Status Management) changes: add direct project manage JS into head.
  - Version 1.3 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
  - Add the filter panel for the all projects and project search reuslt page
  - Version 1.4 (Release Assembly - TC Cockpit All Projects Management Page Update)
  - Add project filters and project filter values to filter panel
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
     <s:if test="viewData.isAllProjectsPage == false">
        <ui:dashboardPageType tab="search"/>
    </s:if>
    <s:if test="viewData.isAllProjectsPage == true">
        <ui:projectPageType tab="allProjects"/>
    </s:if>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <jsp:include page="includes/filterPanel.jsp"/>
    <script type="text/javascript" src="/scripts/directProjectManage.js?v=214865"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="allProjectsPageContent">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->

                    <div class="area1Content">
					<div class="areaHeader">
                    <s:if test="viewData.isAllProjectsPage != true">
                        <h2 class="title">
						<s:if test="viewData.isAllProjectsPage == false">Search</s:if>
						<s:if test="viewData.isAllProjectsPage == true">Projects</s:if>
						</h2>
                    </s:if>
                    </div><!-- End .areaHeader -->

					<s:if test="viewData.isAllProjectsPage == false">
                    <div class="search" style="height:auto;overflow:hidden">
                        <s:form method="get" action="dashboardSearch" namespace="/" id="DashboardSearchForm">
                            <label class="fLeft" for="searchFor">Search For:</label>
                            <s:textfield cssClass="fLeft" name="formData.searchFor" id="searchFor"/>
                            <label class="fLeft" for="searchIn"> In</label>
                            <s:select id="searchIn" list="requestData.dashboardSearchTypes" name="formData.searchIn"/>
                            <div id="datefilter" style="float:left;">
                               <label for="startDate" class="fLeft">Start:</label> <s:textfield cssClass="fLeft text date-pick dp-applied" style="width:80px;" name="formData.startDate" id="startDate" readonly="true"/>
                               <label for="endDate" class="fLeft">End:</label> <s:textfield cssClass="fLeft text date-pick dp-applied" name="formData.endDate" style="width:80px;" id="endDate" readonly="true"/>
                            </div>
                            <s:hidden name="formData.excel" id="formDataExcel" value="false" />
                            <a href="javascript:directSearch();" class="button1 fLeft">
                                <span>Search</span></a>
                        </s:form>
                    </div>
					</s:if>

                    <s:if test="viewData.resultType != null">

                        <s:if test="viewData.resultType.name() == 'PROJECTS'">

                            <form id="filterPanelForm" autocompleted="off">
                               <div class='filterPanel' id='allProjectsFilter'>
                                <div class='filterHead'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='filterText'>
                                                <a href='javascript:;' class='collapse'><img
                                                        src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                <span class='title'>Filter</span>
                                            </div>
                                            <div class='searchContainer'>
                                                <span class='title'>Search</span>

                                                <div class='filterSearch'>
                                                    <input type='text' class='searchBox'/>
                                                </div>
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
                                                <div class='row'>
                                                    <span class='title'>Project Status</span>
                                                    <select id='projectStatusFilter'>
                                                        <option value='All'>All Project Status</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class='column2'>
                                                <div class='row'>
                                                    <span class='title'>Project Filters</span>
                                                    <select id='groupBy' disabled="disabled">
                                                        <option value='no'>No Filter Applied</option>
                                                    </select>
                                                </div>
                                                <div class='row'>
                                                    <span class='title'>Project Filter Values</span>
                                                    <select id='groupValue' disabled="disabled">
                                                        <option value='all'>All Filter Values</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <!--end .column1-->
                                            <div class='column3'>
                                                <div class='row'>
                                                    <span class='title dateLabel'>Creation Date</span>
                                                    <input id='startDateBegin' type='text' class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='startDateEnd' type='text' class='date-pick'/>
                                                </div>
                                                <div class='row'>
                                                    <span class='title dateLabel'>Complete Date</span>
                                                    <input id='endDateBegin' type='text' class='date-pick'/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='endDateEnd' type='text' class='date-pick'/>
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

                        </s:if>

                        <div class="container2 resultTableContainer" id="resultsContainer">
                            <div>

                                                    <s:if test="viewData.resultType.name() == 'PROJECTS'">
                                                        <s:include value="includes/dashboard/projectsSearchResults.jsp"/>
                                                    </s:if>
                                                    <s:if test="viewData.resultType.name() == 'CONTESTS'">
                                                        <s:include value="includes/dashboard/contestsSearchResults.jsp"/>
                                                    </s:if>
                                                    <s:if test="viewData.resultType.name() == 'MEMBERS'">
                                                        <s:include value="includes/dashboard/membersSearchResults.jsp"/>
                                                    </s:if>

                                                </div>

                                                 <div class="container2Left">
                                                        <div class="container2Right">
                                                            <div class="container2Bottom">
                                                                <div class="container2BottomLeft">
                                                                    <div class="container2BottomRight">

                                                                        <div class="panel tableControlPanel"></div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                <!-- End .container2Content -->

                        </div>
                        <!-- End .container2 -->
                    </s:if>
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

</body>
<!-- End #page -->

</html>
