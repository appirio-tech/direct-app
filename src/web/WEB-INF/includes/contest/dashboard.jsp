<%--
  - Author: isv
  - Version: 1.0 (Direct Contest Dashboard assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Dashboard area for Contest Details page
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="dashboardTable contestDashboard">
    <dl>
        <dt>
            <a href="javascript:void(0)" class="expand">Contest Dashboard</a>
            <span class="viewall">
                <input type="checkbox" id="viewAllDetailxBox"/><label for="viewAllDetailxBox">View All Details</label>
            </span>
        </dt>
        <dd>
            <table cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="16%"/>
                    <col width="84%"/>
                </colgroup>
                <tbody>

                <%-- Phases --%>
                <s:if test="software">
                <s:set var="currentPhaseStatus" value="viewData.dashboard.currentPhaseStatus.toString()"/>
                <c:choose>
                    <c:when test="${currentPhaseStatus eq 'RUNNING'}">
                        <c:set var="phaseStatusColor" value="green"/>
                        <c:set var="phaseStatusMessage" value="is on schedule."/>
                    </c:when>
                    <c:when test="${currentPhaseStatus eq 'CLOSING'}">
                        <c:set var="phaseStatusColor" value="orange"/>
                        <c:set var="phaseStatusMessage" value="is on schedule."/>
                    </c:when>
                    <c:when test="${currentPhaseStatus eq 'LATE'}">
                        <c:set var="phaseStatusColor" value="red"/>
                        <c:set var="phaseStatusMessage" value="is late."/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="phaseStatusColor" value="green"/>
                        <c:set var="phaseStatusMessage" value="There is no running phase."/>
                    </c:otherwise>
                </c:choose>
                <tr class="topRow">
                    <td class="first">
                        <label class="${phaseStatusColor}">Timeline</label>
                    </td>
                    <td>
                        <div class="info">
                                <span class="leftPart">
                                    <c:out value="${viewData.dashboard.currentPhase.phaseName}"/>
                                    <c:out value="${phaseStatusMessage}"/>&nbsp;&nbsp;
                                    <c:if test="${not empty viewData.dashboard.currentPhase}">
                                        <a href="javascript:void(0)" class="triggerDetail">View Details</a>
                                    </c:if>
                                </span>
                                <span class="rightPart">
                                    <strong>Current Phase</strong> :
                                    <c:choose>
                                        <c:when test="${empty viewData.dashboard.currentPhase}">None</c:when>
                                        <c:otherwise><c:out value="${viewData.dashboard.currentPhase.phaseName}"/></c:otherwise>
                                    </c:choose>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <strong>Next Phase</strong>  :
                                    <c:choose>
                                        <c:when test="${empty viewData.dashboard.nextPhase}">None</c:when>
                                        <c:otherwise><c:out value="${viewData.dashboard.nextPhase.phaseName}"/></c:otherwise>
                                    </c:choose>
                                </span>
                        </div>
                        <c:if test="${not empty viewData.dashboard.currentPhase}">
                            <div class="detail">
                                Start Time: <fmt:formatDate value="${viewData.dashboard.currentPhase.startTime}"
                                                            pattern="MM/dd/yyyy HH:mm"/>
                                <br/>
                                End Time: <fmt:formatDate value="${viewData.dashboard.currentPhase.endTime}"
                                                          pattern="MM/dd/yyyy HH:mm"/>
                                <br/>
                            </div>
                        </c:if>
                    </td>
                </tr>
                </s:if>

                <%-- Registration --%>
                <s:set var="registrationStatus" value="viewData.dashboard.registrationStatus.toString()"/>
                <c:choose>
                    <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_ACTIVE'}">
                        <c:set var="regStatusColor" value="orange"/>
                        <c:set var="regStatusMessage"
                               value="Registration is less than ideal.<br/>Consider increasing prize money and double-check<br/>the clarity and scope of your contest."/>
                    </c:when>
                    <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_CLOSED'}">
                        <c:set var="regStatusColor" value="orange"/>
                        <c:set var="regStatusMessage"
                               value="Registration is less than ideal.<br/>Consider re-opening registration and increasing<br/>prize money."/>
                    </c:when>
                    <c:when test="${registrationStatus eq 'REGISTRATION_POOR'}">
                        <c:set var="regStatusColor" value="red"/>
                        <c:set var="regStatusMessage"
                               value="Registration is poor.<br/>It is unlikely you will receive good submissions.<br/>Consider reposting."/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="regStatusColor" value="green"/>
                        <c:set var="regStatusMessage" value="Registration is healthy."/>
                    </c:otherwise>
                </c:choose>
                <tr class="even <s:if test="!software">topRow</s:if>">
                    <td class="first">
                        <label class="${regStatusColor}">Registration</label>
                    </td>
                    <td>
                        <div class="info" style="text-align:left;">
                                <span class="leftPart">
                                    ${regStatusMessage}
                                    <a href="javascript:void(0)" class="triggerDetail">View Details</a>
                                </span>
                                <span class="rightPart">
                                    <strong>Number of Submissions</strong> : ${viewData.dashboard.numberOfSubmissions}
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <s:if test="software">
                                        Predicted number of Submission : ${viewData.dashboard.predictedNumberOfSubmissions}
                                    </s:if>
                                    <s:else>
                                        Predicted number of Submission : N/A
                                    </s:else>
                                </span>
                        </div>
                        <div class="detail">
                            <div>
                                <strong># of registrants :</strong> ${viewData.dashboard.numberOfRegistrants}
                            </div>
                        </div>
                    </td>
                </tr>

                <%-- Forum Activity --%>
                <c:set var="latestForumPost" value="${viewData.dashboard.latestForumPost}"/>
                <c:set var="author" value="${latestForumPost.author}"/>
                <tr>
                    <td class="first">
                        <label class="${viewData.dashboard.unansweredForumPostsNumber > 0 ? 'red' : 'green'}">
                            Forum Activity</label>
                    </td>
                    <td>
                        <div class="info">
                                <span class="leftPart">
                                    ${viewData.dashboard.unansweredForumPostsNumber} unanswered threads.&nbsp;&nbsp;
                                </span>
                                <span class="rightPart">
                                    All Posts : <a href="${viewData.dashboard.forumURL}" target="_blank">${viewData.dashboard.totalForumPostsCount}</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <c:if test="${not empty latestForumPost}">
                                        Latest post by : <link:user userId="${author.id}" handle="${author.handle}"/> on
                                        <fmt:formatDate value="${latestForumPost.timestamp}" pattern="MM/dd/yyyy HH:mm:ss"/>
                                        | <a href="${latestForumPost.url}" target="_blank">View Thread</a>
                                    </c:if>
                                </span>
                        </div>
                    </td>
                </tr>

                <%-- Reviewers --%>
                <s:if test="software">
                <s:set var="reviewersSignupStatus" value="viewData.dashboard.reviewersSignupStatus.toString()"/>
                <tr class="even">
                    <td class="first">
                        <label class="${reviewersSignupStatus eq 'ALL_REVIEW_POSITIONS_FILLED'
                                        ? 'green' : (reviewersSignupStatus eq 'REVIEW_POSITIONS_NON_FILLED_WARNING'
                                                     ? 'orange' : 'red')}">Review</label>
                    </td>
                    <td>
                        <div class="info">
                                <span class="leftPart">
                                   ${fn:length(viewData.dashboard.reviewers)} of ${viewData.dashboard.requiredReviewersNumber} reviewers are
                                   registered.
                                </span>
                                <span class="rightPart">
                                    Reviewers :
                                    <c:forEach items="${viewData.dashboard.reviewers}" var="reviewer" varStatus="loop">
                                        <c:if test="${loop.index > 0}">,</c:if>
                                        <link:user userId="${reviewer.id}" handle="${reviewer.handle}"/>
                                    </c:forEach>
                                </span>
                        </div>
                    </td>
                </tr>

                <%-- Dependencies --%>
                <s:set var="dependenciesStatus" value="viewData.dashboard.dependenciesStatus.toString()"/>
                <tr>
                    <td class="first">
                        <label class="${dependenciesStatus eq 'DEPENDENCIES_NON_SATISFIED' ? 'red' : 'green'}">
                            Dependencies</label>
                    </td>
                    <td>
                        <div class="info">
                            <span class="leftPart">
                                <c:choose>
                                    <c:when test="${dependenciesStatus eq 'DEPENDENCIES_NON_SATISFIED'}">
                                        Some dependencies are not satisfied.&nbsp;&nbsp;
                                    </c:when>
                                    <c:when test="${dependenciesStatus eq 'DEPENDENCIES_SATISFIED'}">
                                        All dependencies are satisfied.&nbsp;&nbsp;
                                    </c:when>
                                    <c:when test="${dependenciesStatus eq 'NO_DEPENDENCIES'}">
                                        This contest has no dependencies.&nbsp;&nbsp;
                                    </c:when>
                                </c:choose>
                                <c:if test="${not empty viewData.dashboard.dependencies}">
                                    <a href="javascript:void(0)" class="triggerDetail">View Details</a>
                                </c:if>
                            </span>
                        </div>
                        <c:if test="${not empty viewData.dashboard.dependencies}">
                            <div class="detail">
                                <c:forEach items="${viewData.dashboard.dependencies}" var="dependency">
                                    '<c:out value="${dependency.dependencyType}"/>'
                                    <link:projectDetails project="${dependency.dependencyProject}"/>
                                </c:forEach>
                            </div>
                        </c:if>
                    </td>
                </tr>
                </s:if>    
                </tbody>
            </table>
        </dd>
    </dl>
</div>
<!-- End .dashboardTable -->
