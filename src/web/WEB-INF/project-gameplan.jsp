<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/ganttchart.css" media="all" type="text/css"/>
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

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">
                    <div class="currentPage">
                        <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="currentProjectDetails" namespace="/"/>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                        <strong>Game Plan</strong>
                    </div>
                    <div class="areaHeader">
                        <h2 class="title">Game Plan Gantt Chart</h2>
                    </div><!-- End .areaHeader -->

      <!--              <table class="projectStats" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Start Date</th>
                                <th>Drafts</th>
                                <th>Running</th>
                                <th>Pipeline</th>
                                <th>Finished</th>
                                <th>Tasks</th>
                                <th>Upcoming Schedule</th>
                                <th>Project Fees Running/Finalized</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="date">01/22/2010</td>
                                <td>10</td>
                                <td>5</td>
                                <td>22</td>
                                <td>12</td>
                                <td>8</td>
                                <td class="date fees">00/00/0000 00:00</td>
                                <td class="date fees">$ 00,000 / $ 00,000</td>
                            </tr>
                        </tbody>
                    </table><!-- End .projectsStats -->

                    <div class="container2">
                        <!-- error information container -->
                        <div id="ganttChartError"></div>
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

</body>
<!-- End #page -->

</html>