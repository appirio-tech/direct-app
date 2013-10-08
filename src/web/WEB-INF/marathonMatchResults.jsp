<%--
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Marathon Match Results page.
  -
  - Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
  - - Update to inlcude resutls detail page.
  -
  - Author: Ghost_141
  - Version: 1.1
  - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="roundId" value="roundId" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="mmResults"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/marathonMatches-ie7.css"/>
    <![endif]-->

    <!-- New Style For Marathon Matches -->
    <link rel="stylesheet" href="/css/direct/marathonMatches.css" media="all" type="text/css"/>

    <!-- New Script For Marathon Matches -->
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/marathonMatches.js"></script>

    <script>
        var timeLineData = ${viewData.timeLineGraphData};
        <c:if test="${type eq null}">
        var finalScoreRankingData = ${viewData.finalScoreRankingData};
        var finalVsProvisionalScoreData = ${viewData.finalVsProvisionalScoreData};
        </c:if>
    </script>

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
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/contest/dashboard.jsp"/>

                            <div class="container2 tabs3Container" id="marathonMatchResults">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div>

                                    <c:if test="${type eq null}">
                                        <jsp:include page="marathonMatch/results.jsp"/>
                                    </c:if>
                                    <c:if test="${type eq 'system'}">
                                        <jsp:include page="marathonMatch/results-details.jsp"/>
                                    </c:if>

                                </div>
                            </div>
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
