<%--
  - Author: isv
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the list of contests matching the search criteria.
  -
  - Version 1.1 (Submission Viewer Release 1 assembly) changes: linked submission numbers for each contest
  - to respective submission pages for Studio contests.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="contestsResult">
    <table class="projectStats contests paginatedDataTable" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
        	  <th>Project</th>
            <th>Contest Type</th>
            <th>Contest Name</th>
            <th>Start/End</th>
            <th>Registrants</th>
            <th>Submissions</th>
            <th>Forums</th>
            <th>Status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <s:iterator value="viewData.contests" status="status">
            <s:set var="contest" value="contest" scope="page"/>
            <s:set var="project" value="contest.project" scope="page"/>
            <s:set var="startTime" value="startTime"  scope="page"/>
            <s:set var="endTime" value="endTime"  scope="page"/>
            <tr>
                <td>
                    <link:projectOverview project="${project}"/>
                </td>            	
                <td><s:property value="contestType"/></td>
                <td><link:contestDetails contest="${contest}"/></td>
                <td><fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy HH:mm zzz"/>
                    <c:out value="${tcdirect:getEndText(endTime)}"/></td>
                <td>
                 <s:if test="contestType == 'Studio'">
                    <a href="<s:url action="contestRegistrants" namespace="/"><s:param name="contestId" value="contest.id"/></s:url>">
                    <s:property value="registrantsNumber"/></a>
                 </s:if>
                 <s:if test="contestType != 'Studio'">
                    <a href="<s:url action="contestRegistrants" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>">
                    <s:property value="registrantsNumber"/></a>
                 </s:if>
                </td>
                <td>
                    <if:isStudioContest contestBrief="${contest}">
                        <link:studioSubmissionsGrid contestId="${contest.id}">
                            <s:property value="submissionsNumber"/>
                        </link:studioSubmissionsGrid>
                    </if:isStudioContest>
                    <if:isStudioContest negate="true" contestBrief="${contest}">
                        <link:softwareSubmissionsList contestId="${contest.id}">
                            <s:property value="submissionsNumber"/>
                        </link:softwareSubmissionsList>
                    </if:isStudioContest>
                </td>
                <td>
					<s:if test="forumId != -1">
						<s:if test="contestType == 'Studio'"><a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
						<s:if test="contestType != 'Studio'"><a href="http://forums.topcoder.com/?module=Category&categoryID=${forumId}" target="_blank"></s:if>
					</s:if>
					<s:property value="forumPostsNumber"/>
					<s:if test="forumId != -1"></a></s:if>
				</td>
                <td><span class="<s:property value="status.shortName"/>"><s:property value="status.name"/></span></td>
                <td>
                   <s:if test="contestType == 'Studio'">
                    <a href="contest/detail?contestId=${contest.id}" class="button1 button"><span>View/Edit</span></a>
                 </s:if>
                 <s:if test="contestType != 'Studio'">
                    <a href="contest/detail?projectId=${contest.id}" class="button1 button"><span>View/Edit</span></a>
                 </s:if>
                    
                </td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #contestsResult -->
