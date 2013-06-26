<%--
  - Author: Ghost_141
  - Version: 1.0
  - Copyright (C) 2001-2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Marathon Match Registrants and Submission list.
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
        var registrantsRatingBarData = ${viewData.registrantsRatingBarData};
        var registrantsRatingPieData = ${viewData.registrantsRatingPieData};
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

                            <div class="container2 tabs3Container" id="marathonMatchRegistrants" >

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div>

                                    <div class="registrantsAndSubmissions">
                                        <h4>Registrants &amp; Submissions</h4>
                                        <div class="tabList">
                                            <ul>
                                                <li class="active"><a href="javascript:;"><span><span>Registrants</span></span></a></li>
                                                <li><a href=""><span><span>Competitors</span></span></a></li>
                                                <li><a href=""><span><span>Submissions</span></span></a></li>
                                            </ul>
                                        </div>
                                        <div class="registrantsContaner">
                                            <h5>Registrants</h5>
                                            <s:if test="hasRoundId">
                                                <table border="0" cellpadding="0" cellspacing="0" id="registrantsTable" class="paginatedDataTable">
                                                    <colgroup>
                                                        <col width="25%" />
                                                        <col width="30%" />
                                                        <col width="45%" />
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Handle</th>
                                                        <th>Rating</th>
                                                        <th>Registration Time</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <s:iterator value="viewData.registrants" status="status">
                                                        <tr>
                                                            <td><strong><tc-webtag:handle coderId="${userId}" context="marathon_match"/></strong></td>
                                                            <td><tc-webtag:ratingColor rating="${rating}">${rating}</tc-webtag:ratingColor></td>
                                                            <td><fmt:formatDate value="${registrationTime}" pattern="MM.dd.yyyy HH:mm:ss"/></td>
                                                        </tr>
                                                    </s:iterator>
                                                    </tbody>
                                                </table>
                                            </s:if>
                                            <s:else>
                                                <span>The data is not available as the round id is not set.</span>
                                            </s:else>
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

                                            <!-- paginations -->
                                            <h5>Registrant Rating Distribution</h5>
                                            <div class="chartWrapper">
                                                <div class="chartPie">
                                                    <div id="ratingPieWrapper">
                                                        <div id="ratingPie"></div>
                                                    </div>
                                                </div>
                                                <div class="chartBar">
                                                    <div id="ratingBarWrapper">
                                                        <div id="ratingBar"></div>
                                                    </div>
                                                </div>
                                                <div class="clearFix"></div>
                                            </div>
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
