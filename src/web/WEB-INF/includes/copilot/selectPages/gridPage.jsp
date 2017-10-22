<%--
  - Author: Ghost_141, TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - Fix multiple bugs.
  - 
  - Description: The grid view of copilots.
  - Since: Release Assembly - TC Direct Select From Copilot Pool Assembly
  - Version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="projectList copilotGridDiv" style="display: block;">
    <div class="wideList">
        <c:set var="serverName" value="<%=ApplicationServer.SERVER_NAME%>"/>
        <c:forEach var="profile" items="${profiles}">
            <c:set var="profileLink" value="https://${serverName}/tc?module=ViewCopilotProfile&pid=${profile.member.copilotProfile.userId}\" target=\"_blank"/>
            <!-- item -->
            <div class="projectItem">
                <!-- container -->
                <div class="projectContainer">
                    <div class="top"><div class="bottom"><div class="bg">
                        <div class="copilotInfo">
                            <!-- img -->
                            <div class="copioltPic">
                                <img src="${profile.photo.photoPath}" alt="" style="height:106px;width:96px"/>
                                <a href="javascript:;" class="blackButton selectCopilot" id="gridSelectButton_${profile.member.copilotProfile.userId}">CHOOSE</a>
                            </div>
                            <!-- End .projectPic -->
                            
                            <div class="infoArea">
                                <input type="hidden" value="${profile.member.copilotProfile.userId}" class="userIdInput"></input>
                                <input type="hidden" value="${profile.member.copilotProfile.id}" class="profileIdInput"></input>
                                <input type="hidden" value="${profile.member.copilotProfile.handle}" class="userHandleInput"></input>
                                
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_totalProjects totalProjectsInput" value="${profile.member.totalProjects}" ></input>
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_totalContests totalContestsInput" value="${profile.member.totalContests}" ></input>
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_currentProjects currentProjectsInput" value="${profile.member.currentProjects}" ></input>
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_currentContests currentContestsInput" value="${profile.member.currentContests}" ></input>
                                
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_totalRepostedContests repostContestsInput" value="${profile.member.totalRepostedContests}" ></input>
                                <input type="hidden" class="field_${profile.member.copilotProfile.userId}_totalFailedContests failureContestsInput" value="${profile.member.totalFailedContests}" ></input>
                                
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <colgroup>
                                        <col width="50%" />
                                        <col width="50%" />
                                    </colgroup>
                                    <tr>
                                        <td class="firstLine" colspan="2">Handle : 
                                        <tc-webtag:handle coderId="${profile.member.copilotProfile.userId}" link="${profileLink}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="firstTd padding">Status: </td>
                                        <td class="lastTd padding"><strong class="${profile.statusClass}">${profile.statusText}</strong></td>
                                    </tr>
                                    <tr>
                                        <td class="firstTd ">Suspension: </td>
                                        <td class="lastTd "><strong class="redColorText">${profile.member.copilotProfile.suspensionCount}</strong></td>
                                    </tr>                                    
                                    <tr>
                                        <td class="firstTd">Fulfillment: </td>
                                        <td class="lastTd"><strong class="field_${profile.member.copilotProfile.userId}_fullfillment">loading</strong></td>
                                    </tr>
                                    <tr>
                                        <td class="firstTd">Reliability:</td>
                                        <!-- currently Reliability is hardcode -->
                                        <td class="lastTd"><strong>n/a</strong></td>
                                    </tr>
                                </table>
                                
                                <p>Currently working on</p>
                                <ul>
                                    <li>
                                        <span>Number of Project: </span>
                                        <strong class="greenText field_${profile.member.copilotProfile.userId}_currentProjects">loading</strong>
                                    </li>
                                    <li>
                                        <span>Number of Challenge: </span>
                                        <strong class="greenText field_${profile.member.copilotProfile.userId}_currentContests">loading</strong>
                                    </li>
                                </ul>
                                
                                <a href="${profileLink}" class="blue copilotProfileLink">view profile</a>
                            </div>
                            <!--End .infoArea-->
                            
                            <div class="clear"></div>
                        </div>
                        <!--End .copilotInfo-->
                        
                    </div></div></div>
                </div>
                <!-- End .projectContainer -->
                
            </div>
            <!-- End .projectItem -->
        </c:forEach>
        
    </div>
    
    <div class="clear"></div>
    
</div>
