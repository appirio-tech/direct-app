<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The list view of copilots.
  - Since: Release Assembly - TC Direct Select From Copilot Pool Assembly
  - Version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="copilotListTable" style="display: block;">
    <table cellpadding="0" cellspacing="0">
        <colgroup>
            <col width="87" />
            <col width="100"/>
            <col width="100"/>
            <col width="100"/>
            <col width="100"/>
            <col width="112" />
            <col width="124" />
            <col width="124" />
            <col width="120" />
            
        </colgroup>
        <thead>
            <tr>
                <th class="first">
                    <input type="hidden" value="userHandleInput"></input>
                    Member
                </th>
                <th>
                    <input type="hidden" value="totalProjectsInput"></input>
                    # of Projects
                </th>
                <th>
                    <input type="hidden" value="totalContestsInput"></input>
                    # of Contest
                </th>
                <th>
                    <input type="hidden" value="repostContestsInput"></input>
                    # of Reposts
                </th>
                <th>
                    <input type="hidden" value="failureContestsInput"></input>
                    # of Failures
                </th>
                <th>
                    <input type="hidden" value="bugraceInput"></input>
                    # of Bug Races
                </th>
                <th>
                    <input type="hidden" value="currentProjectsInput"></input>
                    # of Current Projects
                </th>
                <th>
                    <input type="hidden" value="currentContestsInput"></input>
                    # of Current Contest
                </th>
                <th class="last">Action</th>
            </tr>
        </thead>
        
        <tbody>
            <c:set var="serverName" value="<%=ApplicationServer.SERVER_NAME%>"/>
            <c:forEach var="profile" items="${profiles}">
                <c:set var="profileLink" value="http://${serverName}/tc?module=ViewCopilotProfile&pid=${profile.member.copilotProfile.userId}\" target=\"_blank"/>
                <tr>
                    <td class="userLinkTD">
                        <input type="hidden" value="${profile.member.copilotProfile.handle}"></input>
                        <img src="${profile.photo.photoPath}" width="47" height="53" alt=""  />
                        <tc-webtag:handle coderId="${profile.member.copilotProfile.userId}" link="${profileLink}"/>
                    </td>
                    <td class="field_${profile.member.copilotProfile.userId}_totalProjects">loading</td>
                    <td class="field_${profile.member.copilotProfile.userId}_totalContests">loading</td>
                    <td class="field_${profile.member.copilotProfile.userId}_totalRepostedContests">loading</td>
                    <td class="field_${profile.member.copilotProfile.userId}_totalFailedContests">loading</td>
                    <td class="field_${profile.member.copilotProfile.userId}_totalBugRaces">loading</td>
                    <td class="custom greenText field_${profile.member.copilotProfile.userId}_currentProjects">loading</td>
                    <td class="custom greenText field_${profile.member.copilotProfile.userId}_currentContests">loading</td>
                    <td class="last">
                        <a href="javascript:;" class="blackButton selectCopilotList">Choose</a>
                        <input type="hidden" value="${profile.member.copilotProfile.userId}"></input>
                        <a href="${profileLink}" class="blue">view profile</a>
                    </td>
                </tr>
            </c:forEach>    
            
        </tbody>
    </table>
</div>
<!--End .copilotList-->       
