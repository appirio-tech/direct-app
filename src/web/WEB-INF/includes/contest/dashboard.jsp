<%--
  - Author: isv, Veve, GreatKevin
  - Version: 1.4
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Dashboard area for Contest Details page.
  - Version 1.1 change notes: add issue tracking health status into dashboard page.
  -
  - Version 1.2 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Apply to new dashboard prototype. 
  -
  - Version 1.3 (Release Assembly - TC Direct Cockpit Release One) change notes:
  - - Add milestone submission number and final submission number in registration box if
  -   the contest is of multiple rounds
  -
  - Version 1.4 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - - Add start time and end time of the phase and hide in the timeline of contest dashboard
  -
  - Description: Contest Dashboard area for Contest Details page
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="dashboardTable contestDashboard 
    <s:if test="!viewData.contestStats.isStudio">softWareDashboard softwareDetail</s:if>
    <s:else>studioDashboard studioDetails</s:else>
    ">
    
    <div class="dashboardModule">
        <h1 class="heading">
            <span class="tl"></span>
            <span class="tr"></span>
            Timeline
            <c:set var="projectStatus" value="${viewData.contestStats.currentStatus}"/>
            <c:choose>
                <c:when test="${projectStatus eq 'Active'}">
                    <c:choose>
                        <c:when test="${viewData.dashboard.stalled}">
                            <div class="status stalled">Contest Status: <span>Stalled</span></div>
                        </c:when>
                        <c:otherwise>
                            <div class="status active">Contest Status: <span>Active</span></div>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${projectStatus eq 'Draft'}">
                    <div class="status draft">Contest Status: <span>Draft</span></div>
                </c:when>
                <c:when test="${projectStatus eq 'Deleted'}">
                </c:when>
                <c:when test="${projectStatus == 'Completed'}">
                    <div class="status completed">Contest Status: <span>Completed</span></div>
                </c:when>
                <c:otherwise>
                    <div class="status deleted">Contest Status: <span>Cancelled</span></div>
                </c:otherwise>
            </c:choose>
        </h1>
        <div class="content">
            <span class="bl"></span>
            <span class="br"></span>
            <div class="timelineContainer <s:if test="viewData.contestStats.isStudio">studio</s:if>">
                <c:if test="${viewData.dashboard.startTime != null}">
                    <div class="projectDate startDate">
                        <span class="arrow"></span>
                        <label>Contest Start :</label>
                        <fmt:formatDate value="${viewData.dashboard.startTime}" pattern="MM/dd/yyyy HH:mm"/> 
                    </div>                                            
                </c:if>
                <c:if test="${viewData.dashboard.endTime != null}">
                    <div class="projectDate endDate">
                        <span class="arrow"></span>
                        <label>Contest End :</label>
                        <fmt:formatDate value="${viewData.dashboard.endTime}" pattern="MM/dd/yyyy HH:mm"/>
                    </div>                                        
                </c:if>
                
                <div></div><!-- fix ie7 -->
                
                <c:set var="alreadyShowDate" value="false"/>
                <c:if test="${viewData.dashboard.allPhases != null}">
                    <ul class="progressContainer">
                        
                        <c:forEach items="${viewData.dashboard.allPhases}" var="phase" varStatus="loop">
                            <li class="${phase.phaseType.htmlClass} ${phase.phaseStatus.htmlClass}">
                                <span class="phaseName">${phase.phaseType.shortName} <c:if test="${phase.num > 1}">(${phase.num})&nbsp;</c:if></span>
                                
                                <c:choose>
                                    <c:when test="${loop.first}">
                                        <div class="leftSide">
                                            <div class="progressStatus">
                                            </div>
                                        </div>            
                                    </c:when>
                                    <c:when test="${loop.last}">
                                        <div class="rightSide">
                                            <div class="progressStatus">
                                            </div>
                                        </div>      
                                    </c:when>    
                                    <c:otherwise>
                                        <div class="progressStatus">
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <span class="tooltipStartTime hide"><fmt:formatDate value="${phase.startTime}" pattern="MM/dd/yyyy HH:mm"/></span>
                                <span class="tooltipEndTime hide"><fmt:formatDate value="${phase.endTime}" pattern="MM/dd/yyyy HH:mm"/></span>
                                <c:if test="${phase.phaseStatus.phaseStatusId == 2 && !alreadyShowDate}">
                                    <c:set var="alreadyShowDate" value="true"/>
                                    <div class="phaseDate">
                                        <p class="start"><label>Start</label>: 
                                        <fmt:formatDate value="${phase.startTime}" pattern="MM/dd/yyyy HH:mm"/></p>
                                        <p><label>End</label>: 
                                        <fmt:formatDate value="${phase.endTime}" pattern="MM/dd/yyyy HH:mm"/></p>
                                    </div>
                                </c:if>
                            </li>                                                        
                            
                        </c:forEach>
                    </ul>
                </c:if>
                <div class="clear"></div>
            </div>
            <!-- End .timelineContainer -->
        </div>
        <!-- End .content -->
    </div>
    <!-- End .dashboardModule -->
    <div class="appositeContainer <s:if test="viewData.contestStats.isStudio">studio</s:if>">
        <div class="dashboardModule registrationModule">
        
            <s:set var="registrationStatus" value="viewData.dashboard.registrationStatus.toString()"/>
            <c:set var="statusWidth" value="${viewData.dashboard.regProgressPercent}"/>
            <c:choose>
                <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_ACTIVE'}">
                    <c:set var="regStatusColor" value="lessThanIdeal"/>
                    <c:set var="regStatusMessage" value="not ideal"/>
                    <c:set var="regStatusTooltip" value="Consider Increasing prize money and double-check the clarity and scope of your contest."/>
                </c:when>
                <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_CLOSED'}">
                    <c:set var="regStatusColor" value="lessThanIdeal"/>
                    <c:set var="regStatusMessage" value="not ideal"/>
                    <c:set var="regStatusTooltip" value="Consider Increasing prize money and double-check the clarity and scope of your contest."/>
                </c:when>
                <c:when test="${registrationStatus eq 'REGISTRATION_POOR'}">
                    <c:set var="regStatusColor" value="poor"/>
                    <c:set var="regStatusMessage" value="poor"/>
                    <c:set var="regStatusTooltip" value="It is unlikely you will receive good submissions.<br/>Consider reposting. "/>
                </c:when>
                <c:otherwise>
                    <c:set var="regStatusColor" value="healthy"/>
                    <c:set var="regStatusMessage" value="healthy"/>
                    <c:set var="regStatusTooltip" value="Registration is healthy."/>
                    <c:set var="statusWidth" value="100"/>
                </c:otherwise>
            </c:choose>                                    
        
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Registration
            </h1>
            <div class="content">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <s:if test="viewData.contestStats.isStudio">
                        <div class="wrap">
                        <div class="column first">
                    </s:if>
                    <p>
                        <label>Registrants</label>: ${viewData.dashboard.numberOfRegistrants}
                    </p>
                    <div class="statusP ${regStatusColor}">
                        <span class="progressStatus">
                            <span class="progress" style="width:${statusWidth}%"></span>
                            <span class="cover"></span>
                        </span>
                        <span class="status">${regStatusMessage}</span>
                        <a href="javascript:;" class="helpBtn">?</a>
                        <div class="tooltipContainer">
                            <span class="arrow"></span>
                            <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
                                <div class="tooltipBottomLeft"><div class="tooltipBottomRight">
                            
                                    <div class="tooltipCaption">
                                        <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                                            <div class="tooltipCaptionInner">
                                                <h2>What Should I do</h2>
                                            </div><!-- End .tooltipCaptionInner -->
                                        </div></div>
                                    </div><!-- End .tooltipCaption -->
                                    
                                    <div class="tooltipContent">
                                        <p>${regStatusTooltip}</p>
                                    </div><!-- End .tooltipContent -->
                                    
                                </div></div>
                            </div></div></div>
                        </div>
                        <!-- End .tooltipContainer -->
                    </div>
                    <s:if test="viewData.contestStats.isStudio">
                        </div>
                        <!-- End .column -->
                        <div class="column">
                    </s:if>
                    <p class="<s:if test='viewData.contestStats.multipleRound'>multipleSubmission</s:if>">                        
                        <s:if test="viewData.contestStats.multipleRound">
                        	<label>Milestone submissions</label>: ${viewData.contestStats.milestoneSubmissionNumber}                          
                          <label>Final submissions</label>: ${viewData.contestStats.finalSubmissionNumber}
                        </s:if>
                        <s:else>
                        	  <label>Submissions</label>:
                            ${viewData.dashboard.numberOfSubmissions}
                        </s:else>

                    </p>
                    <p>
                        <label class="prediction">Prediction</label>: 
                        <s:if test="!viewData.contestStats.isStudio">
                            ${viewData.dashboard.predictedNumberOfSubmissions}
                        </s:if>
                        <s:else>
                            N/A
                        </s:else>
                    </p>
                    <s:if test="viewData.contestStats.isStudio">
                        </div>
                        <!-- End .column -->
                        <div class="clear"></div>
                        </div>
                        <!-- End .wrap -->
                    </s:if>
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
        <!-- End .dashboardModule -->
        <div class="dashboardModule forumModule">
            <c:set var="latestForumPost" value="${viewData.dashboard.latestForumPost}"/>
            <c:set var="author" value="${latestForumPost.author}"/>                                    
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Forum
            </h1>
            <div class="content">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <p>
                        <label>Posts</label>: <a href="${viewData.dashboard.forumURL}">${viewData.dashboard.totalForumPostsCount}</a>
                    </p>
                    <p>
                        <label>Unanswered Thread</label>: <a href="${viewData.dashboard.forumURL}">${viewData.dashboard.unansweredForumPostsNumber}</a>
                    </p>
                    <c:if test="${not empty latestForumPost}">
                        <p>
                            <label class="lastestPost">Latest Post</label>
                            <!--<a href="javascript:;" class="person">TonyJ</a>-->
                            <link:user userId="${author.id}" handle="${author.handle}" />
                            <span class="date">
                            <!--04/13/2011 12:84-->
                            <fmt:formatDate value="${latestForumPost.timestamp}" pattern="MM/dd/yyyy HH:mm"/>
                            </span>
                        </p>                                                
                    </c:if>
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
        <!-- End .dashboardModule -->
        <s:if test="!viewData.contestStats.isStudio">
        <div class="dashboardModule reviewsModule">
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Reviews
            </h1>
            <div class="content">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <p class="registered">
                        ${fn:length(viewData.dashboard.reviewers)}/${viewData.dashboard.requiredReviewersNumber} Reviewers are registered
                    </p>
                    
                    <c:forEach items="${viewData.dashboard.reviewers}" var="reviewer" varStatus="loop">
                        <p><link:user userId="${reviewer.id}" handle="${reviewer.handle}"/></p>
                    </c:forEach>                                                
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
        <!-- End .dashboardModule -->
        <div class="dashboardModule dependenciesModule">
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Dependencies
            </h1>
            <div class="content"  style="overflow:auto;overflow-x:hidden;">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <p>
						<s:set var="dependenciesStatus" value="viewData.dashboard.dependenciesStatus.toString()"/>
                        <c:choose>
                            <c:when test="${dependenciesStatus eq 'DEPENDENCIES_NON_SATISFIED'}">
                                Some dependencies are not satisfied.&nbsp;&nbsp;
                            </c:when>
                            <c:when test="${dependenciesStatus eq 'DEPENDENCIES_SATISFIED'}">
                                All dependencies are satisfied.&nbsp;&nbsp;
                            </c:when>
                            <c:when test="${dependenciesStatus eq 'NO_DEPENDENCIES'}">
                                There are no dependencies.&nbsp;&nbsp;
                            </c:when>
                        </c:choose>                                                    
                    </p>
                    <c:if test="${not empty viewData.dashboard.dependencies}">
                            <c:forEach items="${viewData.dashboard.dependencies}" var="dependency">
                                <p>
                                '<c:out value="${dependency.dependencyType}"/>'
                                <link:projectOverview project="${dependency.dependencyProject}"/>
                                </p>
                            </c:forEach>
                    </c:if>                                                    
                    
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
        <!-- End .dashboardModule -->
        </s:if>
        <div class="dashboardModule issueModule">
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Issue Tracking
            </h1>
            <div class="content">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <p>
                        <strong><label>Open Issue</label>: <s:property value='viewData.contestStats.issues.unresolvedIssuesNumber'/></strong>
                    </p>
                    <s:if test="viewData.contestStats.issues.unresolvedIssuesNumber > 0">
                        <p>
                            <a href="
                                <s:url action='contestIssuesTracking'>
                                    <s:param name='projectId' value='projectId'/><s:param name='subTab'>issues</s:param></s:url>">
                                View Details
                            </a>
                        </p>
                    </s:if>
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
        <!-- End .dashboardModule -->
        <div class="clear"></div>
    </div>
    <!-- End .appositeContainer -->
</div>
