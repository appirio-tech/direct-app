<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Direct Manage Copilot Postings assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the stats for Copilot Posting contests.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<table class="projectStats contests" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th>Start Date/Time</th>
        <th>End Date/Time</th>
        <th>Registrants</th>
        <th>Forums</th>
        <th>Submissions</th>
        <th>Next Action</th>
    </tr>
    </thead>
    <s:set value="viewData.contestStats" var="contestStats" scope="page"/>
    <s:push value="viewData.contestStats">
        <s:set var="contestStartDate" value="startTime" scope="page"/>
        <s:set var="contestEndDate" value="endTime" scope="page"/>
        <s:set value="contest" var="contest" scope="page"/>
        <tbody>
        <tr>
            <td class="date">
                <fmt:formatDate value="${contestStartDate}" pattern="MM/dd/yyyy HH:mm zzz"/>
            </td>
            <td class="date">
                <fmt:formatDate value="${contestEndDate}" pattern="MM/dd/yyyy HH:mm zzz"/>
            </td>
            <td>
                <s:property value="registrantsNumber"/>
            </td>
            <td><s:property value="forumPostsNumber"/></td>
            <td><s:property value="submissionsNumber"/></td>
            <td class="fees">
                <if:isActive typedContestBrief="${contest}">
                    <if:isInReviewPhase phasedContest="${contest}">
                        Choose Copilot
                    </if:isInReviewPhase>
                </if:isActive>
            </td>
        </tr>
        </tbody>
    </s:push>
</table>
<!-- End .projectsStats -->
