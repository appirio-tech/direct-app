<%--
  - Author: KennyAlive, duxiaoyang, Ghost_141, TCSASSEMBLER
  - Version: 1.6
  - Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the create new project process.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
  -
  - Version 1.1 change notes: Added pages for steps of creating Presentation Project.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
  -             change notes: New presentation and mobile project type creation flow.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
  -             change notes: New analytics project type creation flow.
  -
  - Version 1.4 (Release Assembly - TC Direct Project Forum Configuration Assembly)
  -             change notes: Added a new step for custom project type.
  -
  - Version 1.5 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added global js variable projectQuestions.
  -
  - Version 1.6 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="createNewProject"/>
    <link rel="stylesheet" href="/css/direct/newCockpitProject.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/projectCreateFlows.css" media="all" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/newCockpitProject-ie7.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/projectCreateFlows-ie7.css" />
    <![endif]-->
    <link rel="stylesheet" href="/css/direct/ganttchart.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="/scripts/dhtmlxgantt.js"></script>
    <script type="text/javascript" src="/scripts/moment.min.js"></script>
    <script type="text/javascript" src="/scripts/moment-timezone-with-data-2010-2020.min.js"></script>
	<script type="text/javascript" src="/scripts/launch/entity.js"></script>
	<script type="text/javascript" src="/scripts/launch/main.js"></script>
    <script type="text/javascript">
        // an non-existent project id for retrieving copilot data
        var tcDirectProjectId = 99999999;
		// project questions for populate project answer.
		var projectQuestions = ${result.projectQuestions};
    </script>
    <script type="text/javascript" src="/scripts/project-create-flow-custom.js"></script>
    <script type="text/javascript" src="/scripts/project-create-flow-mobile.js"></script>
    <script type="text/javascript" src="/scripts/project-create-flow-presentation.js"></script>
    <script type="text/javascript" src="/scripts/newCockpitProject.js"></script>
    <script type="text/javascript" src="/scripts/project-create-flow-analytics.js"></script>
</head>

<body id="page">
<div id="wrapper" class="stepPage">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <div class="hide"><jsp:include page="includes/right.jsp"/></div>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content" >
                            <div class="currentPage"> <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt; <strong>Create New Project</strong> </div>
							<div class="areaHeader" style="display: none">
								<h2 class="startProjectTitle">Start a New Project</h2>
							</div>

                            <!-- End .areaHeader -->

                            <!-- step bar -->
                            <span id="stepBarLinkTemplate" class="hide">
                                <a href="javascript:;" style="text-shadow: 0px 1px 0px rgb(108, 135, 30);"></a>
                            </span>

                            <div id = "stepBarCustom" class="hide">
                                <ul>
                                    <li class="first"><span class="active istatus"><span class="arrow"><span class="bg"><a href="javascript:;">Step 1</a></span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 2</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 5</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 6</span></span></span></li>
                                    <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 7</span></span></span></li>
                                </ul>
                            </div>

                            <div id="stepBarMobilePresentation" class="hide">
                                <ul>
                                    <li class="first"><span class="active istatus"><span class="arrow"><span class="bg"><a href="javascript:;">Step 1</a></span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 2</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>
                                    <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 5</span></span></span></li>
                                </ul>
                            </div>

                            <div id="stepBarAnalytics" class="hide">
                            	<ul>
                                	<li class="first"><span class="active istatus"><span class="arrow"><span class="bg"><a href="start-a-new-analytics-project-step-1.html">Step 1</a></span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 2</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 5</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 6</span></span></span></li>
                                    <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 7</span></span></span></li>
                                </ul>
                            </div>

                            <div class="stepBar">
                            </div>
                            <!-- end step bar -->

                            <div id="stepContainer">
                                <jsp:include page="includes/project/newProject/new-project-step1.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step2.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step4.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step5.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step6.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step7.jsp"/>

                                <jsp:include page="includes/project/newProject/mobile/new-mobile-project-step1.jsp"/>
                                <jsp:include page="includes/project/newProject/mobile/new-mobile-project-step2.jsp"/>
                                <jsp:include page="includes/project/newProject/mobile/new-mobile-project-step3.jsp"/>
                                <jsp:include page="includes/project/newProject/mobile/new-mobile-project-step5.jsp"/>
                                
                                <jsp:include page="includes/project/newProject/presentation/new-presentation-project-step1.jsp"/>
                                <jsp:include page="includes/project/newProject/presentation/new-presentation-project-step2.jsp"/>
                                <jsp:include page="includes/project/newProject/presentation/new-presentation-project-step3.jsp"/>
                                <jsp:include page="includes/project/newProject/presentation/new-presentation-project-step5.jsp"/>

                                <!-- common jsp for both mobile and presentation flow --> 
                                <jsp:include page="includes/project/newProject/detailed-specification-step4.jsp"/>
                                
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step1.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step2.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step3.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step4.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step5.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step6.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step7.jsp"/>
                                <jsp:include page="includes/project/newProject/analytics/new-analytics-project-step-confirmation.jsp"/>
                               
                            </div>
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
