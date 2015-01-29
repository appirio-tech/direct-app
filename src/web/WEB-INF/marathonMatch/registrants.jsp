<%--
 - The registrants page for marathon match contest. This page will show all registrants for this marathon match contest.
 -
 - Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 - Update tc-webtag:handle to add handle property.
 -
 - author: Ghost_141
 - Version: 1.1
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<h5>Registrants</h5>
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
            <td><strong><tc-webtag:handle coderId="${userId > 0 ? userId : 0}" handle="${handle}" context="marathon_match" darkBG="false"/></strong></td>
            <td><tc-webtag:ratingColor rating="${rating}" darkBG="false">${rating}</tc-webtag:ratingColor></td>
            <td><fmt:formatDate value="${registrationTime}" pattern="MM.dd.yyyy HH:mm:ss z" timeZone="${defaultTimeZone}"/></td>
        </tr>
    </s:iterator>
    </tbody>
</table>
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
