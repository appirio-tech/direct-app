<%--
 - The competitor list view page for marathon match contest. This page will show competitors for this marathon match
 - contest in list view.
 -
 - Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 - Update usage of tc-webtag:handle tag to add a new property in it.
 -
 - author: Ghost_141
 - Version: 1.1
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<table border="0" cellpadding="0" cellspacing="0" id="listViewTable">
    <colgroup>
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
        <th>Handle</th>
        <th>Rating</th>
        <th>Rank</th>
        <th>Provisional Score</th>
        <th>Last Submission Time</th>
        <th>Language</th>
        <th>Submission History</th>
    </tr>
    </thead>
    <tbody>
    <s:iterator value="viewData.competitors">
        <tr>
            <td><strong><tc-webtag:handle coderId="${userId > 0 ? userId : 0}" handle="${handle}" context="marathon_match" darkBG="false"/></strong></td>
            <td><tc-webtag:ratingColor rating="${rating}" darkBG="false">${rating}</tc-webtag:ratingColor></td>
            <td><c:if test="${rank eq null}">--</c:if> <c:if test="${rank ne null}">${rank}</c:if></td>
            <td><strong class="score">${provisionalScore}</strong></td>
            <td><fmt:formatDate value="${lastSubmissionTime}" pattern="MM.dd.yyyy HH:mm:ss" timeZone="US/Eastern"/></td>
            <td>${language} </td>
            <td>
                <a href="<s:url action="mmRegistrants" namespace="/contest">
                    <s:param name="projectId">${contestId}</s:param>
                    <s:param name="tab">competitors</s:param>
                    <s:param name="handle" value="handle"/>
                    </s:url> ">View</a>
            </td>
        </tr>
    </s:iterator>
    </tbody>
</table>
<!-- pagination -->
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