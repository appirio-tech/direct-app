<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project process which contains 6 steps.
  -
  - Version 1.1 change notes: Added pages for steps of creating Presentation Project.
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="createNewProject"/>
    <link rel="stylesheet" href="/css/newCockpitProject.css?v=210469" media="all" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/newCockpitProject-ie7.css?v=210469"/>
    <![endif]-->
    <link rel="stylesheet" href="/css/ganttchart.css?v=198728" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/dhtmlxcommon.js?v=210661"></script>
    <script type="text/javascript" src="/scripts/dhtmlxgantt.js?v=209663"></script>
	<script type="text/javascript" src="/scripts/launch/entity.js?v=210792"></script>
	<script type="text/javascript" src="/scripts/launch/main.js?v=213622"></script>
    <script type="text/javascript">
        // an non-existent project id for retrieving copilot data
        var tcDirectProjectId = 99999999;
    </script>
    <script type="text/javascript" src="/scripts/newCockpitProject.js?v=211772"></script>
</head>

<body id="page">
<div id="wrapper" class="stepPage">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <div class="hide"><jsp:include page="includes/right.jsp"/></div>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage"> <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt; <strong>Create New Project</strong> </div>
							<div class="areaHeader">
								<h2 class="startProjectTitle">Start a New Project</h2>
							</div>

                            <!-- End .areaHeader -->

                              <!-- step -->
                            <div class="stepBar">
                                <span id="stepBarLinkTemplate" class="hide">
                                    <a href="javascript:;" style="text-shadow: 0px 1px 0px rgb(108, 135, 30);"></a>
                                </span>

                            	<ul>
                                	<li class="first"><span class="active istatus"><span class="arrow"><span class="bg"><a href="javascript:;">Step 1</a></span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 2</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>
                                    <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 5</span></span></span></li>
                                    <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 6</span></span></span></li>
                                </ul>

                            </div>


                            <div id="stepContainer">
                                <jsp:include page="includes/project/newProject/new-project-step1.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step2.jsp"/>
                                <jsp:include page="includes/project/newProject/new-presentation-project-step3.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step4.jsp"/>
                                <jsp:include page="includes/project/newProject/new-presentation-project-step4.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step5.jsp"/>
                                <jsp:include page="includes/project/newProject/new-presentation-project-step5.jsp"/>
                                <jsp:include page="includes/project/newProject/new-project-step6.jsp"/>
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
