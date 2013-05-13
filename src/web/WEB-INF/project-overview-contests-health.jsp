<%--
  - Author: isv, GreatKevin, Veve
  -
  - Version: 1.2 (Project Health Update assembly)
  - Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Five)
  - Hide the review column data if the contest is copilot posting or studio contest
  -
  - Version 1.2 (BUGR-8693 TC Cockpit Add active bug races of project to the project overview page)
  - Adds the active bug races to the active contests of the project overview page
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
            <s:if test="%{#attr['key'].contestTypeName == 'Copilot Posting' || #attr['key'].software == false}" >
                <a style="visibility: hidden;" href="<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['key'].id}"/></s:url>" class="tooltopsBtn">
                <span style="visibility: hidden;"  class="${value.reviewersSignupStatusColor.name}"></span></a>
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

<s:iterator value="activeBugRaces" status="sta">
<s:set var="currentPhaseStartTime" value="%{#attr['value'].dashboardData.currentPhase.startTime}" scope="page"/>
<s:set var="currentPhaseEndTime" value="%{#attr['value'].dashboardData.currentPhase.endTime}" scope="page"/>
<s:set var="currentPhaseName" value="%{#attr['value'].dashboardData.currentPhase.phaseName}" scope="page"/>
<s:set var="nextPhaseName" value="%{#attr['value'].dashboardData.nextPhase.phaseName}" scope="page"/>
<s:set var="nextPhaseStartTime" value="%{#attr['value'].dashboardData.nextPhase.startTime}" scope="page"/>
<s:set var="nextPhaseEndTime" value="%{#attr['value'].dashboardData.nextPhase.endTime}" scope="page"/>
<s:set var="numberOfRegistrants" value="%{#attr['value'].dashboardData.numberOfRegistrants}" scope="page"/>
<tr <c:if test="${sta.even}">class='even'</c:if>>
<td class="first">
    <s:if test="votesNumber > 1" >
        <a class="longWordsBreak green" href="<s:property value='issueLink'/>">
            <s:property value='title'/>
        </a>
    </s:if>
    <s:elseif test="votesNumber == 1" >
        <a class="longWordsBreak orange" href="<s:property value='issueLink'/>">
            <s:property value='title'/>
        </a>
    </s:elseif>
    <s:else>
        <a class="longWordsBreak red" href="<s:property value='issueLink'/>">
            <s:property value='title'/>
        </a>
    </s:else>
</td>
<td class="alignTop">
    <div class="unitContent">Bug Race</div>
</td>
<td class="alignTop">
    <div class="unitContent">Submission</div>
</td>
<td class="alignTop">
    <div class="unitContent dateNotice">Review&nbsp;</div>
    <div class="unitContent dateFullFormat">
    </div>
</td>
    <%-- Timeline --%>
<td>
        <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="green"></span>
        </a>
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
            <p>
                Timeline data not available. Bug Race competition ends upon successful submission review.
            </p>
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
    <s:if test="votesNumber > 1" >
        <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="green"></span></a>
    </s:if>
    <s:elseif test="votesNumber == 1" >
        <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="orange"></span></a>
    </s:elseif>
    <s:else>
        <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="red"></span></a>
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
            <s:if test="votesNumber > 1" >
                <h3><c:out value="Registration is healthy."/></h3>
            </s:if>
            <s:elseif test="votesNumber == 1" >
                <h3><c:out value="Registration is less than ideal."/></h3>
                <p><c:out value="Consider increasing prize money and double-check the clarity and scope of your bug race."/></p>
            </s:elseif>
            <s:else>
                <h3><c:out value="Registration is poor."/></h3>
                <p><c:out value="It is unlikely you will receive good bug race submissions. Consider increasing the prize or reduce the scope."/></p>
            </s:else>
            <p><strong># of registrants</strong> : <s:property value="votesNumber"/></p>
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

   <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
       <span class="green"></span>
   </a>

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
            <p>Review performed by Copilot and/or project team.</p>
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
        <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="green"></span></a>

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
            <p>Please see comments in Bug Race ticket.</p>
            <p><a href="<s:property value='issueLink'/>">View Ticket</a></p>
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
   <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
        <span class="green"></span>
   </a>

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
            <p>
                Dependency information not available.
            </p>
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

    <a href="<s:property value='issueLink'/>" class="tooltopsBtn">
            <span class="green"></span></a>

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
            <p>
                Not applicable
            </p>
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
