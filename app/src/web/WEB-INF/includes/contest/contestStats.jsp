<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%--
  - Author: isv, morehappiness, GreatKevin
  - Version: 1.3
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the tabs for dashboard and contest pages.
  -
  - Version 1.1 (Submission Viewer Release 1 assembly) changes: linked Submissions tab to submission pages
  - for Studio contests.
  -
  - Version 1.2 (Direct Replatforming Release 4) changes: remove the condition test on whether it's studio contest or not.
  -
  - Version 1.3 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
  - - Update forum link for studio contest
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<table class="projectStats" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th>Start Date/Time</th>
        <th>End Date/Time</th>
        <th>Registrants</th>
        <th>Submissions</th>
        <th>Forums</th>
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
                    <a href="<s:url action="contestRegistrants" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>">
                        <s:property value="registrantsNumber"/>
                    </a>
            </td>
            <td>
                <if:isStudioContest contestStats="${contestStats}">
                    <link:studioSubmissionsGrid contestId="${contestStats.contest.id}">
                        <s:property value="submissionsNumber"/>
                    </link:studioSubmissionsGrid>
                </if:isStudioContest>
                <if:isStudioContest contestStats="${contestStats}" negate="true">
                    <link:softwareSubmissionsList contestId="${contestStats.contest.id}">
                        <s:property value="submissionsNumber"/>
                    </link:softwareSubmissionsList>
                </if:isStudioContest>
            </td>
            <td>
				<s:if test="forumId != -1">
                    		<s:if test="isStudio == true && !newForum"><a href="https://<%=ServerConfiguration.STUDIO_FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                    		<s:if test="isStudio == true && newForum"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
					<s:if test="isStudio == false"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=Category&categoryID=${forumId}" target="_blank"></s:if>
				</s:if>
				<s:property value="forumPostsNumber"/>
				<s:if test="forumId != -1"></a></s:if>
			</td>
        </tr>
        </tbody>
    </s:push>
</table>
<!-- End .projectsStats -->
