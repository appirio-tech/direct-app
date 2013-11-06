<%--
  - Copyright (C) 2001-2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Marathon Match Registrants and Submission list.
  -
  - Version 1.1 - (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab) changes:
  - - Update to add content for competitor list, grid, submission history page.
  -
  - Version 1.2 - (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab) changes:
  - - Add script field to store the time line graph data.
  - - Update to support submissions tab in the page.
  -
  - Author: Ghost_141
  - Version: 1.2
  - Since: PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="mmRegistrants"/>

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
    </script>

    <c:if test="${tab eq null}">
        <script>
            var barData = ${viewData.registrantsRatingBarData};
            var pieData = ${viewData.registrantsRatingPieData};
        </script>
    </c:if>
    <c:if test="${tab eq 'competitors'}">
        <script>
            var barData = ${viewData.submittersRatingBarData};
            var pieData = ${viewData.submittersRatingPieData};
        </script>
    </c:if>
    <c:if test="${handle ne null}">
        <script>
            var submissionHistory = ${viewData.submissionHistoryData};
        </script>
    </c:if>
    <c:if test="${tab eq 'submissions'}">
        <script>
            var submissionLineData = ${viewData.submissionsLineGraphData};
        </script>
    </c:if>

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

                            <div class="container2 tabs3Container" id="marathonMatchRegistrants" >

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div>

                                    <div class="registrantsAndSubmissions"
                                         <c:if test="${!hasRoundId}">style="border: #cccccc solid 1px;border-top: none" </c:if>>
                                        <s:if test="hasRoundId">
                                            <h4>Registrants &amp; Submissions</h4>

                                            <jsp:include page="includes/marathonMatch/mmTabs.jsp"/>

                                            <c:if test="${tab ne 'submissions'}">
                                                <div class="registrantsContaner">
                                                    <!-- Registrant page -->
                                                    <c:if test="${tab eq null}">
                                                        <jsp:include page="marathonMatch/registrants.jsp"/>
                                                    </c:if>
                                                    <!-- Competitor list and grid view page -->
                                                    <c:if test="${tab eq 'competitors' && handle eq null}">

                                                        <jsp:include page="includes/marathonMatch/list-grid.jsp"/>

                                                        <h5>Competitors</h5>
                                                        <!-- Competitor grid view page -->
                                                        <c:if test="${view eq null || view eq 'grid'}">
                                                            <jsp:include page="marathonMatch/competitors-grid.jsp"/>
                                                        </c:if>
                                                        <!-- Competitor list view page -->
                                                        <c:if test="${view eq 'list'}">
                                                            <jsp:include page="marathonMatch/competitors-list.jsp"/>
                                                        </c:if>
                                                    </c:if>
                                                    <!-- Competitor Submission History Page -->
                                                    <c:if test="${tab eq 'competitors' && handle ne null}">
                                                        <jsp:include page="marathonMatch/competitors-submission-history.jsp"/>
                                                    </c:if>
                                                </div>
                                            </c:if>
                                            <c:if test="${tab eq 'submissions'}">
                                                <!-- Submissions page -->
                                                <jsp:include page="marathonMatch/submissions.jsp"/>
                                            </c:if>
                                        </s:if>
                                        <s:else>
                                            <span>The data is not available as the round id is not set.</span>
                                        </s:else>
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

<s:form action="contestRegistrants" namespace="/"
        cssStyle="visibility:hidden;display:none;" id="ContestRegistrantsForm" method="get">
    <s:hidden name="formData.excel" id="formDataExcel" value="false" />
    <s:hidden name="formData.contestId"/>
    <s:hidden name="contestId"/>
    <s:hidden name="projectId"/>
</s:form>
</body>
<!-- End #page -->

</html>
