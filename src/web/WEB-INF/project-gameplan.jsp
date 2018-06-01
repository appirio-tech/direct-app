<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/direct/ganttchart.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="/scripts/dhtmlxgantt.js"></script>
    <script type="text/javascript" src="/scripts/directgantt.js"></script>
    <ui:projectPageType tab="gameplan"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="noRightBar">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content" style="position: relative;">
                    <div class="currentPage">
                        <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><s:property value="sessionData.currentProjectContext.name"/>
                        </a> &gt;
                        <strong>Game Plan</strong>
                    </div>
                        <div class="batchButtons gamePlanBatchButtons">
                            <!--<a class="batchCreate" href="javascript:;">Batch Create</a>-->
                            <a class="batchEdit" href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Batch Edit</a>
                            <a class="exportGamePlan" href="<s:url action="projectGamePlanExport" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Export Game Plan</a>
                        </div>
                    <div class="areaHeader" style="width:400px">
                        <div style="float:left"><h2 class="title">Game Plan Gantt Chart</h2> </div>
                        <input type="hidden" id="projectIDHolder" value="${formData.projectId}"/>
                        <div style="float:right; padding-top: 4px;">
                        <a style="text-align: center; " class="button5"
                           href="javascript:ganttChartControl.printToWindow();">Full Screen View</a>
                        </div>
                    </div>


                    <jsp:include page="includes/project/projectStats.jsp"/>

                    <div class="container2">
                        <!-- error information container -->

                        <div id="ganttChartError" class="hide"></div>
                        <!-- gantt chart container-->
                        <div id="ganttChartDiv"></div>

		                        
                    </div><!-- End .container2 -->
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
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
