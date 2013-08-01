<%--
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Marathon Match Results page.
  -
  - Author: Ghost_141
  - Version: 1.0
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
    <script type="text/javascript" src="/scripts/highstock.js"></script>
    <script type="text/javascript" src="/scripts/marathonMatches.js"></script>

    <script>
        var timeLineData = ${viewData.timeLineGraphData};
        var finalScoreRankingData = ${viewData.finalScoreRankingData};
        var finalVsProvisionalScoreData = ${viewData.finalVsProvisionalScoreData};
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

                                    <div class="resultDetails">
                                        <h4>Results</h4>

                                        <div class="resultData">
                                            <table border="0" cellpadding="0" cellspacing="0" class="mmResultsTable">
                                                <colgroup>
                                                    <col />
                                                    <col />
                                                    <col />
                                                    <col />
                                                    <col />
                                                    <col />
                                                    <col />
                                                    <col />
                                                </colgroup>
                                                <thead>
                                                <tr>
                                                    <th>Rank</th>
                                                    <th>Handle</th>
                                                    <th>Final Score</th>
                                                    <th>Provisional Rank</th>
                                                    <th>Provisional Score</th>
                                                    <th>Language</th>
                                                    <th>Results</th>
                                                    <th>Download</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <s:iterator value="viewData.results">
                                                    <tr>
                                                        <td>${finalRank}</td>
                                                        <td><strong><tc-webtag:handle coderId="${userId}" context="marathon_match" darkBG="false"/> </strong></td>
                                                        <td>${finalScore}</td>
                                                        <td>${provisionalRank}</td>
                                                        <td>${provisionalScore}</td>
                                                        <td>${language}</td>
                                                        <td><a href="" class="viewResultLink">View Result</a></td>
                                                        <td>
                                                            <a href="
                                                                <s:url action="submission" namespace="/marathonmatch">
                                                                    <s:param name="submissionNum" value="submissionNumber" />
                                                                    <s:param name="coderId" value="userId"/>
                                                                    <s:param name="problemId" value=""/>
                                                                    <s:param name="roundId" value="roundId"/>
                                                                </s:url> "
                                                               class="downloadLink <c:if test="${finalRank eq '2'}">silverStyleLink</c:if> <c:if test="${finalRank > '2'}">darkStyleLink</c:if>">DOWNLOAD</a>
                                                        </td>
                                                    </tr>
                                                </s:iterator>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- End .resultData -->

                                        <div class="container2Left">
                                            <div class="container2Right">
                                                <div class="container2Bottom">
                                                    <div>
                                                        <div>

                                                            <div class="panel tableControlPanel"></div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="tabList" id="socreTab">
                                            <ul>
                                                <li class="active"><a href="javascript:;"><span><span>Final Scores &amp; Ranking</span></span></a></li>
                                                <li><a href="javascript:;"><span><span>Final vs Provisional Scores</span></span></a></li>
                                            </ul>
                                        </div>
                                        <!-- End .tabList -->

                                        <div class="tabContainer" id="scoreTabContainer">
                                            <div class="tabsContent rankingContent">
                                                <h5>Final Scores and Ranking</h5>
                                                <div id="rankingChart"></div>
                                            </div>
                                            <!-- End .rankingContent -->
                                            <div class="tabsContent scoreContent">
                                                <h5>Final vs Provisional Scores</h5>
                                                <div class="scoreChartWrapper">
                                                    <div id="scoreChart"></div>
                                                    <p>Please note that direct comparison of provisional and final scores does not make sense for all types of problems. <br />In particular, provisional and final scores may have different scales for scoring.</p>
                                                </div>
                                            </div>
                                            <!-- End .scoreContent -->
                                        </div>
                                        <!-- End .tabContainer -->

                                    </div>

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
