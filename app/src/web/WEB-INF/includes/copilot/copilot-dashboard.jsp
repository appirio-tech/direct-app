<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
  -
  - Version 1.11 (Release Assembly - TC Cockpit Misc Bug Fixes)
  - - Fix TCCC-3857: Make the forum link in the copilot posting dashboard openned in a new window/tab
  -
  - Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: Copilot Posting Dashboard area for Copilot Posting page.
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('.requiredXperience .more').live('mouseover', function() {
            var $parent = $(this).parents('.dashboardModule:eq(0)').position();
            var r = $(this).parents('.dashboardModule:eq(0)').width() - $(this).position().left;
            var r1 = r;
            var t = $parent.top + $(this).position().top + $('.moreFlyout').height() - 10;
            if (r < $('.moreFlyout').width()) {
                r = 0;
            } else {
                r1 = $('.moreFlyout').width() - $(this).position().left + 55;
                r -= 50;
            }
            $('.moreFlyout .arrow').css({
                'left' : 'auto',
                'right' : (r1 - 20) + 'px'
            });
            $('.moreFlyout').removeClass('hide').css({
                'right' : r + 'px',
                'top' : t + 'px',
                'left' : 'auto'
            });
        });
        $('.requiredXperience .more').live('mouseleave', function() {
            window.setTimeout("$('.moreFlyout').addClass('hide');", 500);
        });
    });
</script>

<div class="flyout moreFlyout hide">
    <span class="arrow">&nbsp;</span>
    <h2>Required Experience</h2>
    <ul>
        <c:forEach var="copilotType" items="${allProjectCopilotTypes}" >
            <c:if test="${fn:contains(viewData.dashboard.copilotProjectTypes, copilotType.key)}">
                <li>- ${copilotType.value}</li>
            </c:if>
        </c:forEach>

        <c:if test="${not empty viewData.dashboard.otherManagingExperienceString}">
            <li>- ${viewData.dashboard.otherManagingExperienceString}</li>
        </c:if>
    </ul>
</div>

<div class="dashboardTable contestDashboard copilotPostingDashboard">

<div class="dashboardModule timelineModule">
    <h1 class="heading">
        <span class="tl"></span>
        <span class="tr"></span>
        Timeline
    </h1>
    <div class="content">
        <span class="bl"></span>
        <span class="br"></span>
        <div class="timelineContainer copilot">
        <c:if test="${viewData.dashboard.startTime != null}">
            <div class="projectDate startDate">
                <span class="arrow"></span>
                <label>Copilot Posting Start :</label>
                <fmt:formatDate value="${viewData.dashboard.startTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/>
            </div>
        </c:if>
        <c:if test="${viewData.dashboard.endTime != null}">
            <div class="projectDate endDate">
                <span class="arrow"></span>
                <label>Copilot Posting End :</label>
                <fmt:formatDate value="${viewData.dashboard.endTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/>
            </div>
        </c:if>

        <div></div><!-- fix ie7 -->

        <c:set var="alreadyShowDate" value="false"/>
        <c:if test="${viewData.dashboard.allPhases != null}">
            <ul class="progressContainer">

                <c:forEach items="${viewData.dashboard.allPhases}" var="phase" varStatus="loop">
                    <li class="${phase.phaseType.htmlClass} ${phase.phaseStatus.htmlClass}">
                        <span class="phaseName">
                            <c:choose>
                                <c:when test='${phase.phaseType.shortName eq "Review"}'>
                                   Choose Copilot
                                </c:when>
                                <c:otherwise>
                                    ${phase.phaseType.shortName}
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${phase.num > 1}">(${phase.num})&nbsp;</c:if>
                        </span>

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
                        <span class="tooltipStartTime hide"><fmt:formatDate value="${phase.startTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></span>
                        <span class="tooltipEndTime hide"><fmt:formatDate value="${phase.endTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></span>
                        <c:if test="${phase.phaseStatus.phaseStatusId == 2 && !alreadyShowDate}">
                            <c:set var="alreadyShowDate" value="true"/>
                            <div class="phaseDate">
                                <p class="start"><label>Start</label>:
                                    <fmt:formatDate value="${phase.startTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></p>
                                <p><label>End</label>:
                                    <fmt:formatDate value="${phase.endTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></p>
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

<div class="moduleAction dashboardModule">
    <h1 class="heading">
        <span class="tl"></span> <span class="tr"></span> Next Action
    </h1>
    <div class="content">
        <span class="bl"></span> <span class="br"></span>
        <div class="listContent actionContent">
            <c:choose>
                <c:when test="${viewData.contestStats.currentStatus eq 'Active'}">
                    <c:forEach items="${viewData.dashboard.allPhases}" var="phase" varStatus="loop">
                                <c:choose>
                                    <c:when test='${phase.phaseType.shortName eq "Review" && phase.phaseStatus.htmlClass eq "current"}'>
                                        <a class="btn" href='<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                                    <s:param name="projectId" value="%{#attr['projectId']}"/>
                                </s:url>'><span class="bRt"> <span class="bMid">CHOOSE YOUR COPILOT</span></span></a>
                                    </c:when>
                                </c:choose>
                    </c:forEach>
                </c:when>
                <c:when test="${viewData.contestStats.currentStatus eq 'Draft'}">
                    <a class="btn action-btn" href="javascript:activateContest();"><span class="bRt"> <span class="bMid">ACTIVATE COPILOT POSTING</span></span></a>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>

        </div>
        <!-- End .listContent -->
    </div>
    <!-- End .content -->
</div>
<div class="clear"></div>

<!-- End .dashboardModule -->
<div class="appositeContainer">

<div class="dashboardModule registrationModule">

    <s:set var="registrationStatus" value="viewData.dashboard.registrationStatus.toString()"/>
    <c:set var="statusWidth" value="${viewData.dashboard.regProgressPercent}"/>
    <c:choose>
        <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_ACTIVE'}">
            <c:set var="regStatusColor" value="lessThanIdeal"/>
            <c:set var="regStatusMessage" value="not ideal"/>
            <c:set var="regStatusTooltip" value="Consider Increasing prize money and double-check the clarity and scope of your challenge."/>
        </c:when>
        <c:when test="${registrationStatus eq 'REGISTRATION_LESS_IDEAL_CLOSED'}">
            <c:set var="regStatusColor" value="lessThanIdeal"/>
            <c:set var="regStatusMessage" value="not ideal"/>
            <c:set var="regStatusTooltip" value="Consider Increasing prize money and double-check the clarity and scope of your challenge."/>
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
                <div class="wrap">
                    <div class="column first">
            <p>
                <label>Registrants</label>: ${viewData.dashboard.numberOfRegistrants}
            </p>
            <div class="statusP ${regStatusColor}">
            <s:if test="viewData.contestStats.showHealth">
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
            </s:if>
            </div>
        </div>
        <!-- End .column -->
        <div class="column">
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
        </div>
        <!-- End .column -->
        <div class="clear"></div>
    </div>
    <!-- End .wrap -->
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
                <label>Posts</label>: <a target="_blank" href="${viewData.dashboard.forumURL}">${viewData.dashboard.totalForumPostsCount}</a>
            </p>
            <p>
                <label>Unanswered Thread</label>: <a target="_blank" href="${viewData.dashboard.forumURL}">${viewData.dashboard.unansweredForumPostsNumber}</a>
            </p>
            <c:if test="${not empty latestForumPost}">
                <p>
                    <label class="lastestPost">Latest Post</label>
                    <!--<a href="javascript:;" class="person">TonyJ</a>-->
                    <link:user userId="${author.id}" handle="${author.handle}" />
                            <span class="date">
                            <!--04/13/2011 12:84-->
                            <fmt:formatDate value="${latestForumPost.timestamp}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/>
                            </span>
                </p>
            </c:if>
        </div>
        <!-- End .listContent -->
    </div>
    <!-- End .content -->
</div>
<!-- End .dashboardModule -->
    <div class="dashboardModule copilotStats">
        <h1 class="heading">
            <span class="tl"></span> <span class="tr"></span> Project Criteria
        </h1>
        <div class="content">
            <span class="bl"></span> <span class="br"></span>
            <div class="listContent">
                <p>
                    <label>Project Type</label>:
                    <c:choose>
                        <c:when test="${not empty viewData.dashboard.directProjectType}">
                            ${viewData.dashboard.directProjectType}
                        </c:when>
                        <c:otherwise>
                            Not Set
                        </c:otherwise>
                    </c:choose>
                </p>
                <p class="requiredXperience">
                    <label>Required Experience</label>: <span>
                    <c:set var="displayExperience" value="false"/>
                    <c:forEach var="copilotType" items="${allProjectCopilotTypes}" >
                        <c:if test="${fn:contains(viewData.dashboard.copilotProjectTypes, copilotType.key) && displayExperience == false}">
                            ${copilotType.value}
                            <c:set var="displayExperience" value="true"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${displayExperience == true}">
                         <a class="more" href="javascript:;">More</a>
                    </c:if>
                    <c:if test="${displayExperience == false}">
                        Not Required
                    </c:if> </span>

                </p>
                <p class="projectBudget">
                    <label>Budget</label>:  <span>
                        <c:if test="${not empty viewData.dashboard.budget}">
                            $ ${viewData.dashboard.budget}
                         </c:if>
                        <c:if test="${empty viewData.dashboard.budget}">
                            Not Indicated
                        </c:if>   </span>
                </p>
                <p>
                    <label>Duration</label>:  <span>
                    <c:choose>
                        <c:when test="${not empty viewData.dashboard.directProjectDuration}">
                            ${viewData.dashboard.directProjectDuration} </span> days
                        </c:when>
                        <c:otherwise>
                            Not Indicated  </span>
                        </c:otherwise>
                    </c:choose>
                </p>
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
