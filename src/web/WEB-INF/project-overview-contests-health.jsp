<%--
  - Author: isv
  -
  - Version: 1.0 (Project Health Update assembly)
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project contests health view.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:iterator value="model.dataAsMap['result']" status="sta">
    <s:set var="currentPhaseStartTime" value="%{#attr['value'].dashboardData.currentPhase.startTime}" scope="page"/>
    <s:set var="currentPhaseEndTime" value="%{#attr['value'].dashboardData.currentPhase.endTime}" scope="page"/>
    <s:set var="currentPhaseName" value="%{#attr['value'].dashboardData.currentPhase.phaseName}" scope="page"/>
    <s:set var="nextPhaseName" value="%{#attr['value'].dashboardData.nextPhase.phaseName}" scope="page"/>
    <s:set var="nextPhaseStartTime" value="%{#attr['value'].dashboardData.nextPhase.startTime}" scope="page"/>
    <s:set var="nextPhaseEndTime" value="%{#attr['value'].dashboardData.nextPhase.endTime}" scope="page"/>
    <s:set var="numberOfRegistrants" value="%{#attr['value'].dashboardData.numberOfRegistrants}" scope="page"/>
    <tr <c:if test="${sta.even}">class='even'</c:if>>
        <td class="first">
             <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
             <a class="longWordsBreak ${value.contestStatusColor.name}" href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                         <c:out value="${key.title}"/></a>
             </s:if>
             <s:else>
             <a class="longWordsBreak ${value.contestStatusColor.name}" href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>">
                         <c:out value="${key.title}"/></a>
             </s:else>
        </td>
        <td class="alignTop">
            <div class="unitContent"><s:property value="%{#attr['key'].contestTypeName}"/></div>
        </td>
        <td class="alignTop">
            <div class="unitContent"><c:out value="${currentPhaseName}"/>&nbsp;</div>
        </td>
        <td class="alignTop">
            <div class="unitContent dateNotice"><c:out value="${nextPhaseName}"/>&nbsp;</div>
            <div class="unitContent dateFullFormat">
                <fmt:formatDate value="${pageScope.nextPhaseStartTime}" pattern="MM/dd/yyyy HH:mm"/>
            </div>
        </td>
        <%-- Timeline --%>
        <td>
            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
            <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.phaseStatusColor.name}"></span></a>
            </s:if>
            <s:else>
            <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.phaseStatusColor.name}"></span></a>
            </s:else>
            <div class="tooltipBox">
                <span class="arrow"></span>
                <div class="tooltipHeader">
                    <div class="tooltipHeaderRight">
                        <div class="tooltipHeaderCenter">
                            <h2>Timeline</h2>
                        </div>
                    </div>
                </div><!-- End .tooltipHeader -->
                
                <div class="tooltipContent">
                    <s:set var="currentPhaseStatus" value="%{#attr['value'].currentPhaseStatus.toString()}"/>
                    <h3>
                        <c:choose>
                            <c:when test="${currentPhaseStatus eq 'RUNNING'}">
                                <c:out value="${currentPhaseName}"/> <c:out value="is on schedule."/>
                            </c:when>
                            <c:when test="${currentPhaseStatus eq 'CLOSING'}">
                                <c:out value="${currentPhaseName}"/> <c:out value="is on schedule."/>
                            </c:when>
                            <c:when test="${currentPhaseStatus eq 'LATE'}">
                                <c:out value="${currentPhaseName}"/> <c:out value="is late."/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="There is no running phase."/>
                            </c:otherwise>
                        </c:choose>
                    </h3>
                    <s:if test="%{#attr['value'].dashboardData.nextPhase != null}">
                        <p>Start Time: <fmt:formatDate value="${pageScope.currentPhaseStartTime}" pattern="MM/dd/yyyy HH:mm"/></p>
                        <p>End Time: <fmt:formatDate value="${pageScope.currentPhaseEndTime}" pattern="MM/dd/yyyy HH:mm"/></p>
                    </s:if>
                </div><!-- End .tooltipContent -->
                
                <div class="tooltipFooter">
                    <div class="tooltipFooterRight">
                        <div class="tooltipFooterCenter"></div>
                    </div>
                </div><!-- End .tooltipFooter -->
            </div><!-- End .tooltipBox -->
        </td>
        <%-- Registration --%>
        <td>
            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
            <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.regStatusColor.name}"></span></a>
            </s:if>
            <s:else>
            <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.regStatusColor.name}"></span></a>
            </s:else>
            
            <div class="tooltipBox">
                <span class="arrow"></span>
                <div class="tooltipHeader">
                    <div class="tooltipHeaderRight">
                        <div class="tooltipHeaderCenter">
                            <h2>Registration</h2>
                        </div>
                    </div>
                </div><!-- End .tooltipHeader -->
                    
                <div class="tooltipContent">
                    <s:set var="registrationStatus" value="%{#attr['value'].registrationStatus.toString()}"/>
                    <c:choose>
                        <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_ACTIVE'}">
                            <h3><c:out value="Registration is less than ideal."/></h3>
                            <p><c:out value="Consider increasing prize money and double-check the clarity and scope of your contest."/></p>
                        </c:when>
                        <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_CLOSED'}">
                            <h3><c:out value="Registration is less than ideal."/></h3>
                            <p><c:out value="Consider re-opening registration and increasing prize money."/></p>
                        </c:when>
                        <c:when test="${registrationStatus eq 'REGISTRATION_POOR'}">
                            <h3><c:out value="Registration is poor."/></h3>
                            <p><c:out value="It is unlikely you will receive good submissions. Consider reposting."/></p>
                        </c:when>
                        <c:otherwise>
                            <h3><c:out value="Registration is healthy."/></h3>
                        </c:otherwise>
                    </c:choose>
                    <p><strong># of registrants</strong> : <c:out value="${numberOfRegistrants}"/></p>
                </div><!-- End .tooltipContent -->
                    
                <div class="tooltipFooter">
                    <div class="tooltipFooterRight">
                        <div class="tooltipFooterCenter"></div>
                    </div>
                </div><!-- End .tooltipFooter -->
            </div><!-- End .tooltipBox -->
        </td>
        <%-- Review --%>
        <td>
            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
            <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.reviewersSignupStatusColor.name}"></span></a>
            </s:if>
            <s:else>
            <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.reviewersSignupStatusColor.name}"></span></a>
            </s:else>
            
            <div class="tooltipBox">
                <span class="arrow"></span>
                <div class="tooltipHeader">
                    <div class="tooltipHeaderRight">
                        <div class="tooltipHeaderCenter">
                            <h2>Review</h2>
                        </div>
                    </div>
                </div><!-- End .tooltipHeader -->
                
                <div class="tooltipContent">
                    <p><strong>${fn:length(value.dashboardData.reviewers)}</strong> of <strong>${value.dashboardData.requiredReviewersNumber}</strong> reviewers are registered.</p>
                </div><!-- End .tooltipContent -->
                
                <div class="tooltipFooter">
                    <div class="tooltipFooterRight">
                        <div class="tooltipFooterCenter"></div>
                    </div>
                </div><!-- End .tooltipFooter -->
            </div><!-- End .tooltipBox -->                
        </td>
        <%-- Forum --%>
        <td>
            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
            <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.forumActivityStatusColor.name}"></span></a>
            </s:if>
            <s:else>
            <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.forumActivityStatusColor.name}"></span></a>
            </s:else>
            
            <div class="tooltipBox">
                <span class="arrow"></span>
                <div class="tooltipHeader">
                    <div class="tooltipHeaderRight">
                        <div class="tooltipHeaderCenter">
                            <h2>Forum</h2>
                        </div>
                    </div>
                </div><!-- End .tooltipHeader -->
                    
                <div class="tooltipContent">
                    <p><strong>${value.unansweredForumPostsNumber}</strong> unanswered threads.</p>
                    <p><a href="${value.dashboardData.forumURL}">View Thread</a></p>
                </div><!-- End .tooltipContent -->
                    
                <div class="tooltipFooter">
                    <div class="tooltipFooterRight">
                        <div class="tooltipFooterCenter"></div>
                    </div>
                </div><!-- End .tooltipFooter -->
            </div><!-- End .tooltipBox -->
            
        </td>
        
        <%-- Dependencies --%>
        <td>
                <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
                <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
                <span class="${value.dependenciesStatusColor.name}"></span></a>
                </s:if>
                <s:else>
                <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
                <span class="${value.dependenciesStatusColor.name}"></span></a>
                </s:else>
                 
                 <div class="tooltipBox">
                     <span class="arrow"></span>
                     <div class="tooltipHeader">
                         <div class="tooltipHeaderRight">
                             <div class="tooltipHeaderCenter">
                                 <h2>Dependencies</h2>
                             </div>
                         </div>
                     </div><!-- End .tooltipHeader -->
                    
                     <div class="tooltipContent">
                         <s:set var="dependenciesStatus" value="%{#attr['value'].dependenciesStatus.toString()}" scope="page"/>
                         <c:choose>
                             <c:when test="${dependenciesStatus eq 'DEPENDENCIES_NON_SATISFIED'}">
                                 Some dependencies are not satisfied.
                             </c:when>
                             <c:when test="${dependenciesStatus eq 'DEPENDENCIES_SATISFIED'}">
                                 All dependencies are satisfied.
                             </c:when>
                             <c:when test="${dependenciesStatus eq 'NO_DEPENDENCIES'}">
                                 This contest has no dependencies.
                             </c:when>
                         </c:choose>
                     </div><!-- End .tooltipContent -->
                     
                     <div class="tooltipFooter">
                         <div class="tooltipFooterRight">
                             <div class="tooltipFooterCenter"></div>
                         </div>
                     </div><!-- End .tooltipFooter -->
                 </div><!-- End .tooltipBox -->
        </td>
        
        <%-- Issue Tracking --%>
        <td>

            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting'}" >
            <a href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.contestIssuesColor.name}"></span></a>
            </s:if>
            <s:else>
            <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
            <span class="${value.contestIssuesColor.name}"></span></a>
            </s:else>
            
            <div class="tooltipBox">
                <span class="arrow"></span>
                <div class="tooltipHeader">
                    <div class="tooltipHeaderRight">
                        <div class="tooltipHeaderCenter">
                            <h2>Issue Tracking</h2>
                        </div>
                    </div>
                </div><!-- End .tooltipHeader -->
                    
                <div class="tooltipContent">
                    <a href="<s:url action='contestIssuesTracking'><s:param name='projectId' value='%{#attr["key"].id}'/><s:param name='subTab'>issues</s:param></s:url>" 
                       class="viewDetailsLink">View Details</a>
                    <p>Open Issue : <strong>${value.unresolvedIssuesNumber}</strong></p>
                    <div class="clearFix"></div>
                </div><!-- End .tooltipContent -->
                    
                <div class="tooltipFooter">
                    <div class="tooltipFooterRight">
                        <div class="tooltipFooterCenter"></div>
                    </div>
                </div><!-- End .tooltipFooter -->
            </div><!-- End .tooltipBox -->
            
        </td>
    </tr>
</s:iterator>
