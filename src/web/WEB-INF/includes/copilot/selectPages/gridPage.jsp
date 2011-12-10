<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
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
        <c:forEach var="profile" items="${profiles}">
            <!-- item -->
            <div class="projectItem">
                <!-- container -->
                <div class="projectContainer">
                    <div class="top"><div class="bottom"><div class="bg">
                        <div class="copilotInfo">
                            <!-- img -->
                            <div class="copioltPic">
                                <img src="${profile.photo.photoPath}" alt="" style="height:${profile.photo.height}px;width:${profile.photo.width}px"/>
                                <a href="javascript:;" class="blackButton selectCopilot" id="gridSelectButton_${profile.member.copilotProfile.userId}">Choose</a>
                            </div>
                            <!-- End .projectPic -->
                            
                            <div class="infoArea">
                                <input type="hidden" value="${profile.member.copilotProfile.userId}" class="userIdInput"></input>
                                <input type="hidden" value="${profile.member.copilotProfile.id}" class="profileIdInput"></input>
                                <input type="hidden" value="${profile.member.totalProjects}" class="totalProjectsInput"></input>
                                <input type="hidden" value="${profile.member.totalContests}" class="totalContestsInput"></input>
                                <input type="hidden" value="${profile.member.currentProjects}" class="currentProjectsInput"></input>
                                <input type="hidden" value="${profile.member.currentContests}" class="currentContestsInput"></input>
                                
                                <input type="hidden" value="${profile.member.copilotProfile.handle}" class="userHandleInput"></input>
                                <input type="hidden" value="${profile.member.totalRepostedContests}" class="repostContestsInput"></input>
                                <input type="hidden" value="${profile.member.totalFailedContests}" class="failureContestsInput"></input>
                                <input type="hidden" value="${profile.member.totalBugRaces}" class="bugraceInput"></input>
                                
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <colgroup>
                                        <col width="50%" />
                                        <col width="50%" />
                                    </colgroup>
                                    <tr>
                                        <td class="firstLine" colspan="2">Handle : 
                                        <link:user userId="${profile.member.copilotProfile.userId}" handle="${profile.member.copilotProfile.handle}"/>
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
                                        <td class="firstTd">Fullfillment: </td>
                                        <td class="lastTd"><strong>${profile.fullfillment}</strong></td>
                                    </tr>
                                    <tr>
                                        <td class="firstTd">Reliability:</td>
                                        <!-- currently Reliability is hardcode -->
                                        <td class="lastTd"><strong>n/a</strong></td>
                                    </tr>
                                </table>
                                
                                <p>Current Working On</p>
                                <ul>
                                    <li>
                                        <span>Number of Project: </span>
                                        <strong class="greenText">${profile.member.currentProjects}</strong>
                                    </li>
                                    <li>
                                        <span>Number of Contest: </span>
                                        <strong class="greenText">${profile.member.currentContests}</strong>
                                    </li>
                                </ul>
                                
                                <a href="http://<%=ApplicationServer.SERVER_NAME%>/tc?module=ViewCopilotProfile&pid=${profile.member.copilotProfile.userId}" class="blue copilotProfileLink">view profile</a>
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