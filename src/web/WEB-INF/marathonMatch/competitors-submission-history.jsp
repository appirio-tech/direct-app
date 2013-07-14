<%--
 - The competitor submissions history page for marathon match contest. This page will show competitor's submission history
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<a href="<s:url action="mmRegistrants" namespace="/contest">
    <s:param name="projectId">${contestId}</s:param>
    <s:param name="tab">competitors</s:param>
    <s:param name="view">list</s:param>
    </s:url> " class="backToList">&lt;&lt; back to list</a>
<h5>Competitors</h5>
<div class="submissionHistory">
    <div class="handleIntro">
        <dl>
            <dt>Handle:</dt>
            <dd><tc-webtag:handle coderId="${viewData.competitorInfoDTO.userId}" context="marathon_match"/></dd>
            <dt>Rating:</dt>
            <dd><strong class="rating"><tc-webtag:ratingColor rating="${viewData.competitorInfoDTO.rating}" darkBG="false">${viewData.competitorInfoDTO.rating}</tc-webtag:ratingColor></strong></dd>
            <dt>Last Submission at:</dt>
            <dd><fmt:formatDate value="${viewData.competitorInfoDTO.lastSubmissionTime}" pattern="MM/dd/yyyy HH:mm z" timeZone="US/Eastern"/></dd>
            <dt>Number of Full Submissions:</dt>
            <dd>${viewData.competitorInfoDTO.noOfFullSubmissions}</dd>
        </dl>
        <div class="recentSubmission">
            <p>Recent Submissions:</p>
            <table border="0" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col />
                    <col />
                </colgroup>
                <tbody>
                <tr>
                    <s:iterator value="viewData.recentSubmissions">
                        <td>
                            <div class="gridHeader">
                                <p>Submission #${submissionNumber}</p>
                                <a>run test</a>
                            </div>
                            <div class="gridBody"></div>
                            <div class="gridFooter">
                                <dl>
                                    <dt>Language:</dt>
                                    <dd>${language}</dd>
                                    <dt>Provisional Score:</dt>
                                    <dd class="pink">${provisionalScore}</dd>
                                </dl>
                            </div>
                        </td>
                    </s:iterator>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- End .handleIntro -->
    <div class="scoreIntro">
        <dl>
            <dt>Provisional Score:</dt>
            <dd><strong class="score">${viewData.competitorInfoDTO.provisionalScore}</strong></dd>
            <dt>Provisional Rank:</dt>
            <dd>${viewData.competitorInfoDTO.rank}</dd>
        </dl>
        <div class="clearFix"></div>
        <div class="personScoreChart">
            <div id="personScoreChart"></div>
        </div>
    </div>
    <!-- End .scoreIntro -->
    <div class="clearFix"></div>
</div>
<h5>Competitor Rating Distribution</h5>
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