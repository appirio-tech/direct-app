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
                <a href="<s:url action="listCopilotContestRegistrants" namespace="/copilot"><s:param name="projectId" value="contest.id"/></s:url>">
                    <s:property value="registrantsNumber"/>
                </a>
            </td>
            <td>
                <s:if test="forumId != -1">

                <a href="http://forums.topcoder.com/?module=Category&categoryID=${forumId}" target="_blank">
                    </s:if>
                    <s:property value="forumPostsNumber"/>
                    <s:if test="forumId != -1"></a></s:if></td>
            <td>
                <a href="<s:url action="listCopilotContestSubmissions" namespace="/copilot"><s:param name="projectId" value="contest.id"/></s:url>">
                    <s:property value="submissionsNumber"/>
                </a>
            </td>
            <td class="fees">
                <if:isActive typedContestBrief="${contest}">
                    <if:isInReviewPhase phasedContest="${contest}">
                        <a href="<s:url action="listCopilotContestSubmissions" namespace="/copilot"><s:param name="projectId" value="contest.id"/></s:url>">
                            Choose Copilot
                        </a>

                    </if:isInReviewPhase>

                    <s:set var="passReviewPhase" value="true"/>
                    <s:iterator value="contest.currentPhases">
                        <s:if test='phaseName in {"Registration", "Submission", "Screening", "Review"}'>
                            <s:set var="passReviewPhase" value="false"/>
                        </s:if>
                    </s:iterator>

                    <s:if test="#passReviewPhase == true">
                        <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="contest.project.id"/></s:url>">
                            Go To My Project
                        </a>
                    </s:if>


                </if:isActive>
            </td>
        </tr>
        </tbody>
    </s:push>
</table>
<!-- End .projectsStats -->
