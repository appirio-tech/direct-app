<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Change the use of %{#session.currentSelectDirectProjectID} to sessionData.currentSelectDirectProjectID so the JSP
  -   page can access the session on the first hit.
  -
  - Description: This page renders the project milestones calendar view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <ui:projectPageType tab="milestone"/>
    <link rel="stylesheet" href="/css/direct/dashboard-view.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/projectMilestone.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/milestone-fullCalendar.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/milestone-fullCalendar.js"></script>
    <script type="text/javascript" src="/scripts/projectMilestone.js"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="projectMilestoneListViewPage">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong>Milestones</strong>
                            </div>

                            <div class="spaceWhite"></div>

                            <div class="milestoneManage">
                                <div class="topLine">
                                    <h2>Project Milestone</h2>
                                    <a class="grayButton" href="javascript:;">
                                        <span class="buttonMask"><span class="text"><span
                                                class="plus">Add Milestone</span></span></span>
                                    </a>

                                    <div class="viewBtns">
                                        <a class="listViewBtn" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/><s:param name="formData.viewType">list</s:param></s:url>">
                                            <span>List View</span>
                                        </a>
                                        <a class="calendarViewBtn active"
                                           href="javascript:;">
                                            <span>Calendar</span>
                                        </a>
                                    </div>
                                </div>
                                <!-- End .topLine -->

                                <div class="milestoneCalView">

                                </div>
                                <!-- End .milestoneCalView -->

                                <div class="bottomLine">
                                    <a class="grayButton" href="javascript:;">
                                        <span class="buttonMask"><span class="text"><span
                                                class="plus">Add Milestone</span></span></span>
                                    </a>
                                </div>

                                <div id="responsiblePersonList" class="hide">
                                    <s:iterator value="viewData.responsiblePersons">
                                        <link:user userId="${userId}"/>
                                    </s:iterator>
                                </div>

                            </div>
                        </div>
                        <!-- end area1 -->
                    </div>
                </div>
                <!-- End #mainContent -->

                <jsp:include page="../includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/popups.jsp"/>
<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
