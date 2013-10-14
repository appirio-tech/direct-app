<%--
 - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 - 
 - The results page for marathon match contest. This page will show all results for this marathon match contest.
 -
 - Version 1.1 - BUGR - 9794
 - - Remove the submission number parameter in dwonload submission link.
 -
 - author: Ghost_141
 - Version: 1.1
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
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
                    <td><strong><tc-webtag:handle coderId="${userId}" handle="${handle}" context="marathon_match" darkBG="false"/> </strong></td>
                    <td>${finalScore}</td>
                    <td>${provisionalRank}</td>
                    <td>${provisionalScore}</td>
                    <td>${language}</td>
                    <td>
                        <a href="<s:url action="mmResults" namespace="/contest">
                            <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                            <s:param name="type">system</s:param>
                            <s:param name="sr" value="place"/>
                            <s:param name="stc" value="1"/>
                        </s:url> " class="viewResultLink">View Result</a>
                    </td>
                    <td>
                        <a href="<s:url action="submission" namespace="/marathonmatch">
                        <s:param name="coderId" value="userId"/>
                        <s:param name="roundId" value="viewData.roundId"/>
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