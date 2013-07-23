<%--
  - Author: isv, Veve, GreatKevin, Ghost_141, Veve
  - Version: 1.8
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Dashboard area for Contest Details page.
  - Version 1.1 change notes: add issue tracking health status into dashboard page.
  -
  - Version 1.2 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Apply to new dashboard prototype. 
  -
  - Version 1.3 (Release Assembly - TC Direct Cockpit Release One) change notes:
  - - Add checkpoint submission number and final submission number in registration box if
  -   the contest is of multiple rounds
  -
  - Version 1.4 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - - Add start time and end time of the phase and hide in the timeline of contest dashboard
  -
  - Version 1.5 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Fix multiple bugs.
  -
  - Version 1.6 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
  - - Add support for marathon contest dashboard
  -
  - Version 1.7 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
  - - Remove some error message when there is no round id hooked with contest.
  - - Update registrant, cureent top ranking tab when there is no round id hooked with contest.
  -
  - Version 1.8 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab)
  - - Update Current Standings tab and time line module for marathon match contest.
  - 
  - Description: Contest Dashboard area for Contest Details page
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="dashboardTable contestDashboard 
    <s:if test="!viewData.contestStats.isStudio && !marathon">softWareDashboard softwareDetail</s:if>
    <s:else>studioDashboard studioDetails</s:else>
    " data-intro="Welcome to your competition dashboard. This is where you control and monitor individual competitions within your project." data-step="1" data-position="top">
    
    <div class="dashboardModule" id="timelineModule" data-intro="Here is the timeline for your competition.  The system defines this based on the type of competition. Your copilot might tweak it slightly, but generally they remain pretty consistent." data-step="2" data-position="top">
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
            <s:if test="hasRoundId">
                <div class="timelineIntro">
                    <dl>
                        <s:if test="active">
                            <dt>Currently Top Ranking Submissions:</dt>
                        </s:if>
                        <s:else>
                            <dt>Winner:</dt>
                        </s:else>
                        <dd><tc-webtag:handle coderId="${viewData.commonInfo.bestScoreUserId}" context="marathon_match" darkBG="false"/></dd>
                        <s:if test="active">
                            <dt>Best Provisional Score:</dt>
                        </s:if>
                        <s:else>
                            <dt>Best Final Score:</dt>
                        </s:else>
                        <dd class="last">
                            <a href="<s:url action="mmRegistrants" namespace="/contest">
                                <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                <s:param name="tab">competitors</s:param>
                                <s:param name="handle" value="viewData.commonInfo.bestScoreHandle"/>
                            </s:url> <s:if test="active">class="finalScore"</s:if> ">
                            ${viewData.commonInfo.bestScore}
                            </a>
                        </dd>
                    </dl>
                </div>
            </s:if>
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

            <s:if test="hasRoundId">
                <div id="timelineChartWrapper">
                    <div id="timelineChart"></div>
                </div>
            </s:if>
            <!-- End .timelineContainer -->
        </div>
        <!-- End .content -->
    </div>
    <!-- End .dashboardModule -->

    <div <s:if test="marathon">id="appositeContainer"</s:if> class="appositeContainer <s:if test="viewData.contestStats.isStudio">studio</s:if>">
    <s:if test="hasRoundId">
        <div class="dashboardModule registrationModule">
            <s:set var="registrationStatus" value="viewData.dashboard.registrationStatus"/>
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
            <div class="content" data-intro="The system monitors the health of your competition to try to determine whether or not you will get a good result. Check here to get an early gauge on success." data-step="3" data-position="top">
                <span class="bl"></span>
                <span class="br"></span>
                <div class="listContent">
                    <p>
                        <label>Registrants: </label>${viewData.commonInfo.numRegistrants}
                    </p>
                    <p>
                        <label>Competitors: </label>${viewData.commonInfo.numCompetitors}
                    </p>
                    <p>
                        <label>Submissions: </label>${viewData.commonInfo.numSubmissions}
                    </p>
                    <p>
                        <label>Avg. Submissions: </label><fmt:formatNumber value="${viewData.commonInfo.numSubmissions / viewData.commonInfo.numCompetitors}" pattern="##.##"/>
                    </p>
                    <p class="statusP ${regStatusColor}">
                        <label>Healthy: </label>
                        <span class="progressStatus">
                            <span class="progress" style="width:${statusWidth}%">
                                <span>
                                    <span>${statusWidth}%</span>
                                </span>
                            </span>
                        </span>
                    </p>
                </div>
                <!-- End .listContent -->
            </div>
            <!-- End .content -->
        </div>
    </s:if>
    <s:elseif test="marathon && !hasRoundId">
        <div class="dashboardModule registrationModule">
            <h1 class="heading">
                <span class="tl"></span>
                <span class="tr"></span>
                Registration
            </h1>
            <div class="content">
                <span class="bl"></span>
                <span class="br"></span>
                    <div class="listContent">
                        <span>There is no round id hooked with the contest.</span>
                    </div>
            </div>
        </div>
    </s:elseif>
    <s:if test="!marathon">
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
                                                        <h2>What should I do</h2>
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
                                    <label>Checkpoint submissions</label>: ${viewData.contestStats.checkpointSubmissionNumber}
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
    </s:if>

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
        <s:if test="!viewData.contestStats.isStudio && !marathon">
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
                        ${fn:length(viewData.dashboard.reviewers)}/${viewData.dashboard.requiredReviewersNumber} <label>Reviewers are registered</label>
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
                            <label>
                                Some dependencies are not satisfied.&nbsp;&nbsp;
                            </label>
                            </c:when>
                            <c:when test="${dependenciesStatus eq 'DEPENDENCIES_SATISFIED'}">
                            <label>
                                All dependencies are satisfied.&nbsp;&nbsp;
                            </label>
                            </c:when>
                            <c:when test="${dependenciesStatus eq 'NO_DEPENDENCIES'}">
                            <label>
                                There are no dependencies.&nbsp;&nbsp;
                            </label>
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
        <div class="dashboardModule <s:if test="marathon">issueTrackingModule</s:if><s:else>issueModule</s:else>">
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
                        <strong><label>Open Issues</label>: <s:property value='viewData.contestStats.issues.unresolvedIssuesNumber'/></strong>
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
        <s:if test="hasRoundId">
            <div class="dashboardModule currentStandingsModule">
                <h1 class="heading">
                    <span class="tl"></span>
                    <span class="tr"></span>
                    Current Standings - Top 3
                </h1>
                <div class="content">
                    <span class="bl"></span>
                    <span class="br"></span>
                    <div class="listContent">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <colgroup>
                                <col />
                                <col />
                                <col />
                                <col />
                            </colgroup>
                            <thead>
                            <tr>
                                <th>Rank</th>
                                <th>Handle</th>
                                <s:if test="active">
                                    <th>Provisional Score</th>
                                </s:if>
                                <s:else>
                                    <th>Final Score</th>
                                </s:else>
                                <th>Rating</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="viewData.topCompetitors">
                                <tr>
                                    <c:if test="${rank eq null}">
                                        <td>--</td>
                                        <td>--</td>
                                        <td>--</td>
                                        <td>--</td>
                                    </c:if>
                                    <c:if test="${rank ne null}">
                                        <td>${rank}</td>
                                        <td><tc-webtag:handle coderId="${userId}" context="marathon_match" darkBG="false"/></td>
                                        <s:if test="active">
                                            <td>${provisionalScore}</td>
                                        </s:if>
                                        <s:else>
                                            <td>${finalScore}</td>
                                        </s:else>
                                        <td><tc-webtag:ratingColor rating="${rating}" darkBG="false">${rating}</tc-webtag:ratingColor></td>
                                    </c:if>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                    </div>
                    <!-- End .listContent -->
                </div>
                <!-- End .content -->
            </div>
        </s:if>
        <s:elseif test="marathon && !hasRoundId">
            <div class="dashboardModule currentStandingsModule">
                <h1 class="heading">
                    <span class="tl"></span>
                    <span class="tr"></span>
                    Current Standings - Top 3
                </h1>
                <div class="content">
                    <span class="bl"></span>
                    <span class="br"></span>
                    <div class="listContent">
                        <span>There is no round id hooked with this contest.</span>
                    </div>
                 </div>
            </div>
        </s:elseif>
        <!-- End .dashboardModule -->
        <div class="clear"></div>
    </div>
    <!-- End .appositeContainer -->
</div>
