<%--
 - The submissions page for marathon match contest. This page will show top # competitors' submissions of this
 - marathon match contest.
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="submissionsContainer">
    <h5>Submissions</h5>
    <div class="viewTopSubmissions">
        <a href="javascript:;">GO</a>
        <span>
            View Top
            <select id="topSubmissionSelect">
                <option selected="selected">5</option>
                <option>10</option>
            </select>
            Submissions
            Starting with
            <input name="submissionStart" id="submissionStart" size="5" value="1">
        </span>
    </div>
    <!-- End .viewTopSubmissions -->
    <div class="chartTopSubmissions">
        <div id="chartTopSubmissionsWrapper">
            <div id="chartTopSubmissions"></div>
        </div>
        <a href="javascript:;" class="prev disable">PREV</a>
        <a href="javascript:;" class="next">NEXT</a>
    </div>
</div>