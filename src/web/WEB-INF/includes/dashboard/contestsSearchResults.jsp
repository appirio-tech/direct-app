<%--
  - Author: isv, TCSASSEMBLER
  - Version: 1.2
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the list of contests matching the search criteria.
  -
  - Version 1.1 (Submission Viewer Release 1 assembly) changes: linked submission numbers for each contest
  - to respective submission pages for Studio contests.
  - Version 1.2 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="contestsResult">
    <table class="projectStats resultTable contests paginatedDataTable" cellpadding="0" cellspacing="0">

         <colgroup>
                                                <col width="16%" />
                                                <col width="8%" />
                                                <col width="18%" />
                                                <col width="9%" />
                                                <col width="9%" />
                                                <col width="8%" />
                                                <col width="8%" />
                                                <col width="8%" />
                                                <col width="8%" />
                                                <col width="7%" />

         </colgroup>

        <thead>
        <tr>
        	<th>Project</th>
            <th>Contest Type</th>
            <th>Contest Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th class="truncateRegs">Registrants</th>
            <th class="truncateSubs">Submissions</th>
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
                <td class="first">
                    <link:projectOverview project="${project}"/>
                </td>            	
                <td><s:property value="contestType"/></td>
                <td><link:contestDetails contest="${contest}"/></td>

                <td>
                    <fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)

                </td>

                 <td>
                    <fmt:formatDate value="${endTime}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)

                </td>
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
                <td class="last">
                  <s:if test="contestType == 'Studio'">
                    <a href="contest/detail?contestId=${contest.id}" class="button11">
                        <span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span>
                    </a>
                 </s:if>
                 <s:if test="contestType == 'Copilot Posting'">
                    <a href="copilot/copilotContestDetails.action?projectId=${contest.id}" class="button11">
                        <span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span>
                    </a>
                 </s:if>
                 <s:if test="contestType != 'Studio' && contestType != 'Copilot Posting'">
                    <a href="contest/detail?projectId=${contest.id}" class="button11">
                        <span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span>
                    </a>
                 </s:if>

                </td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #contestsResult -->
