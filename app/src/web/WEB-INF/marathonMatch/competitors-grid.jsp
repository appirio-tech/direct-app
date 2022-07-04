<%--
 - The competitor grid view page for marathon match contest. This page will show competitors for this marathon match
 - contest in grid view.
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<table border="0" cellpadding="0" cellspacing="0" class="competitor-grid">
    <colgroup>
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
    </colgroup>
    <tbody class="competitor-grid-body">
    </tbody>
</table>
<table border="0" cellpadding="0" cellspacing="0" id="gridViewTable" class="hide">
    <colgroup>
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
        <col width="20%" />
    </colgroup>
    <tbody>
    <s:iterator value="viewData.competitors">
        <tr class="grid-item">
            <td>
                <div class="gridHeader">
                    <p>
                        <a href="<s:url action="mmRegistrants" namespace="/contest">
                                                                        <s:param name="projectId">${contestId}</s:param>
                                                                        <s:param name="tab">competitors</s:param>
                                                                        <s:param name="handle" value="handle"/>
                                                                        </s:url> ">
                            Submission #${lastSubmissionNumber}
                        </a>
                    </p>
                    <a href="#">run test</a>
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