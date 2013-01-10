<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the copilot posting submissions comparison mode.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="PAGE_TYPE" value="copilot" scope="request"/>
<c:set var="CURRENT_TAB" value="copilotPostings" scope="request"/>
<c:set var="CURRENT_SUB_TAB" value="copilotContestSubmissions" scope="request"/>

<c:set var="copilotProfilesMap" value="${requestScope.copilotProfilesMap}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/direct/permissions.css?v=193435" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/copilot/copilotPosting.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/permissions.js?v=210124"></script>
    <script type="text/javascript" src="/scripts/copilot/copilotStats.js"></script>
    <jsp:include page="includes/paginationSetup.jsp"/>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="liquid">

<jsp:include page="includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="includes/right.jsp"/>

<div id="area1"><!-- the main area -->
<div class="area1Content">

<div class="currentPage">
    <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a> &gt;
    <strong>My Copilot Selection Contests</strong>
</div>
<!-- End .currentPage -->

<div id="copilotsIntroduction">
<div class="orderReview">

<div class="myCopilotsContests">
                                        <span class="introductionHeadIcon">
                                            <img src="/images/copilot_contests_icon.png" alt="copilot contests"/></span>

    <h2 class="sectionHead"><c:out value="${viewData.contestStats.contest.title}"/></h2>
</div>
<!-- end .getCopilots -->

<c:set value="${viewData.contestStats.contest.project.id}" var="tcDirectProjectId"/>
<c:set value="${viewData.contestStats.contest.project.name}" var="tcDirectProjectName"/>
<c:set value="${viewData.contestStats.contest.id}" var="projectId"/>
<input type="hidden" name="contestId" value="${viewData.contestStats.contest.id}"/>
<input type="hidden" name="directProjectId" value="${viewData.contestStats.contest.project.id}"/>

<div class="myCopilotsContestsList">

<jsp:include page="includes/copilot/copilot-dashboard.jsp"/>

<div class="container2 tabs3Container tabs3Special"
     id="CopilotPostingSubmissions">

<jsp:include page="includes/copilot/tabs.jsp"/>

<div class="container2Left ">
<div class="caption">
    <div class="fLeft comparisonResult">
        <strong>Copilot comparison</strong> - You've selected <span class="nCP"><s:property
            value="copilotSubmissions.size"/></span> copilots to compare
    </div>
    <div class="fRight">
        <a href="<s:url action='listCopilotContestSubmissions'><s:param name='projectId' value='viewData.contestStats.contest.id'/></s:url>" class="btn btn-red"><span class="bRt"> <span class="bMid">BACK TO ALL SUBMISSIONS</span></span></a> <span class="switch btn-nexPrev"> <a
            href="javascript:;" class="prev disabled"></a> <a href="javascript:;" class="next"></a>
												</span>
    </div>
</div>
<!-- /.caption -->
<div class="container2Right">
<div class="container2Content_det">
<div class="compareList">
<div class="cLeft">
    <ul class="compareLable">
        <li class="rCopilot"><p>Copilot</p></li>
        <li class="rFullFill"><p>Fulfillment</p></li>
        <li class="rWorkLoad"><p>Current Workload</p></li>
        <li class="rMatchedXp"><p>Matched Experience</p></li>
        <li class="rExperience"><p>Other Experience</p></li>
        <li class="rSkills"><p>Copilot Skills</p></li>
        <li class="rSbumission"><p>Submission</p></li>
        <li class="rPickUp doubleLine"><p>
            Pick Up<br />Your Copilot
        </p></li>
    </ul>
</div>
<div class="cRight">
<ul class="compareItems" id="compareItems">
<c:set var="firstPlaceWinner" value="${viewData.copilotPostingWinner}"/>
<c:set var="secondPlaceWinner" value="${viewData.copilotPostingRunnerUp}"/>
<s:iterator value="copilotSubmissions">

    <li class="col colCopilot">
        <input type="hidden" name="submissionId" value="${submissionId}"/>
        <input type="hidden" name="profileId" value="${copilotProfileId}"/>
        <div class="cellCopilot rCopilot cell">
            <div class="dragSection">
                <span class="fLeft"><span class="dragtable-drag-handle reorder">Drag to reorder copilots</span></span> <span class="fRight"><a href="javascript:;" class="close">&nbsp;</a></span>
            </div>
            <s:if test="imagePath == null || imagePath.length == 0">
                <img class="memberPic" alt="copilot picture"
                     src="/images/copilotPosting/copilotHeader.png"/>
            </s:if>
            <s:else>
                <img class="memberPic" alt="copilot picture" src="${imagePath}"/>
            </s:else>
            <p class="userHandleHolder">
                <link:user userId="${userId}"/>
            </p>
            <p class="track">
                <s:if test="studioCopilot">
                <a href="javascript:;" class="studio">
                    &nbsp;</a>
                </s:if>
                <s:if test="softwareCopilot">
                    <a href="javascript:;" class="dev">&nbsp;</a>
                </s:if>
                <s:if test="!softwareCopilot && !studioCopilot">
                    <a class="" href="javascript:;">
                        &nbsp;</a>
                </s:if>
            </p>
            <p class="timestamp">${country} / ${timeZone}</p>
            <p>
                <a href="http://community.topcoder.com/tc?module=ViewCopilotProfile&amp;pid=${userId}"
                   class="copilotStat" target="_blank"> View Copilot Statistics</a>
            </p>
            <ul class="feedbacks">
                <li class="positiveFeedback <s:if test='positiveFeedbackNumber <= 0'>zeroPositiveFeedback</s:if>">${positiveFeedbackNumber}</li>
                <li class="negativeFeedback <s:if test='negativeFeedbackNumber <= 0'>zeroNegativeFeedback</s:if>">${negativeFeedbackNumber}</li>
            </ul>

            <c:choose>
                <c:when test="${userId eq firstPlaceWinner.id}">
                    <span class="ribbonCP"></span>
                </c:when>
                <c:when test="${userId eq secondPlaceWinner.id}">
                    <span class="ribbonRUP"></span>
                </c:when>
            </c:choose>
        </div>
        <div class="cellFulFill rFullFill cell">
            <span class="txt${fulfillmentColor}">${roundedFulfillment}%</span>
        </div>
        <div class="cellWorkload rWorkLoad cell">
            <p>
                <strong>${currentContests}  </strong>Contests
            </p>
            <p>
                <strong>${currentProjects} </strong>Projects
            </p>
        </div>
        <div class="colExperience rMatchedXp cell">
            <s:iterator value="matchedExperience">
                <div class="row">
                    <p class="xp">${projectType} <s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                    <p class="xpStat">
                        Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                    </p>
                </div>
            </s:iterator>
        </div>
        <div class="colExperience rExperience cell">
            <s:iterator value="otherExperience">
                <div class="row">
                    <p class="xp">${projectType}<s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                    <p class="xpStat">
                        Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                    </p>
                </div>
            </s:iterator>
        </div>
        <div class="colSkills rSkills cell">
            <s:iterator value="copilotSkills">
                <input type="hidden" name="skill-rule-<s:property/>" value="y"/>
            </s:iterator>
        </div>
        <div class="colPickup pickupCell rSbumission cell">
            <p class="row highlighted">
                <link:onlineReviewDownloadSubmission
                        projectId="${projectId}"
                        submissionId="${submissionId}"
                        styleClass="submitedFile">
                    #${submissionId}
                </link:onlineReviewDownloadSubmission>
                <br />
                <span class="timeStamp"><s:date name="submitTime" format="MM/dd/yyyy | hh:mm"/>&nbsp;EST</span>
            </p>
        </div>
        <div class="colPickup pickupCell cell rPickUp <c:if test="${userId eq firstPlaceWinner.id}">cpPicked</c:if> <c:if test="${userId eq secondPlaceWinner.id}">rupPicked</c:if>">
            <s:if test="inReviewPhase">
                <c:choose>
                    <c:when test="${firstPlaceWinner eq null && allSubmissionReviewed eq false}">
                        <div class="unPickedCP row">
                            <a href="javascript:;" class="btn btn-red btn-pickup"><span class="bRt">
                            <span class="bMid"> <span class="ico"> CHOOSE</span></span></span></a>
                            <br/>
                            <a href="javascript:;" class="btn btn-white btn-pickup-rup" style="display:none"><span
                            class="bRt"> <span class="bMid"> CHOOSE AS RUNNER-UP</span></span></a>
                        </div>
                        <div class="pickedCP">
                            <span class="pikedAsCP">PICKED AS COPILOT</span>
                        </div>
                        <div class="pickedRunnerUp">
                            <span class="pikedAsRUP">PICKED AS RUNNER-UP</span>
                        </div>
                    </c:when>
                    <c:when test="${userId eq firstPlaceWinner.id}">
                        <div class="pickedCP">
                            <span class="pikedAsCP">PICKED AS COPILOT</span>
                        </div>
                    </c:when>
                    <c:when test="${secondPlaceWinner eq null && allSubmissionReviewed eq false}">
                        <div class="unPickedCP row">
                            <a href="javascript:;" class="btn btn-white btn-pickup-rup"><span
                                    class="bRt"> <span class="bMid"> CHOOSE AS RUNNER-UP</span></span></a>
                        </div>
                    </c:when>
                    <c:when test="${userId eq secondPlaceWinner.id}">
                        <div class="pickedRunnerUp">
                            <span class="pikedAsRUP">PICKED AS RUNNER-UP</span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        &nbsp;
                    </c:otherwise>
                </c:choose>
            </s:if>
            <s:else>
                <c:choose>
                    <c:when test="${userId eq firstPlaceWinner.id}">
                        <div class="pickedCP">
                            <span class="pikedAsCP">PICKED AS COPILOT</span>
                        </div>
                    </c:when>
                    <c:when test="${userId eq secondPlaceWinner.id}">
                        <div class="pickedRunnerUp">
                            <span class="pikedAsRUP">PICKED AS RUNNER-UP</span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        &nbsp;
                    </c:otherwise>
                </c:choose>
            </s:else>
        </div>
    </li>

</s:iterator>

</ul>
</div>
</div>


<div class="caption bottom">
    <div class="fRight">
        <a href="<s:url action='listCopilotContestSubmissions'><s:param name='projectId' value='viewData.contestStats.contest.id'/></s:url>" class="btn btn-red">
            <span class="bRt"> <span class="bMid">BACK TO ALL SUBMISSIONS</span></span></a>
        <span class="switch btn-nexPrev"> <a
                href="javascript:;" class="prev"></a> <a href="javascript:;" class="next"></a>
														</span>
    </div>
</div>
<!-- /.caption -->
<!-- /.compare-table -->

<div class="flyout xperienceFlyout hide">
    <span class="arrow">&nbsp;</span>
    <div class="row">
        <p class="xp">Mobile - Androids</p>
        <p class="xpStat">
            Active : <span class="active">5</span>, Completed : <span class="completed">10</span>
        </p>
    </div>
    <div class="row">
        <p class="xp">Web appilcation - Ajax</p>
        <p class="xpStat">
            Active : <span class="active">5</span>, Completed : <span class="completed">10</span>
        </p>
    </div>
    <div class="row">
        <p class="xp">Studio - Wireframe</p>
        <p class="xpStat">
            Active : <span class="active">5</span>, Completed : <span class="completed">10</span>
        </p>
    </div>
    <div class="row">                     C
        <p class="xp">Studio - Logo Design</p>
        <p class="xpStat">
            Active : <span class="active">5</span>, Completed : <span class="completed">10</span>
        </p>
    </div>
    <div class="row">
        <p class="xp">Studio - Logo Design</p>
        <p class="xpStat">
            Active : <span class="active">5</span>, Completed : <span class="completed">10</span>
        </p>
    </div>
</div>
<!-- /.xperienceFlyout -->
<div class="flyout trackFlyout hide">
    <span class="arrow">&nbsp;</span>
    <p class="data">Certified TopCoder Studio Copilot</p>
</div>
<!-- /.trackFlyout -->
<div class="flyout skillsFlyout hide">
    <span class="arrow">&nbsp;</span>
    <div class="flyoutBody">
        <div class="achiv badge-ui hide">
            <span class="quoteBadgesItem b7a"></span>
            <span class="quoteBadgesItem b2"></span>
            <span class="quoteBadgesItem b3a"></span>
            <span class="quoteBadgesItem b4a"></span>
            <span class="quoteBadgesItem b5"></span>
            <span class="quoteBadgesItem b6a"></span>
            <div class="clearFixed"></div>
        </div>
        <div class="achiv badge-imple hide">
            <span class="quoteBadgesItem b7a"></span>
            <span class="quoteBadgesItem b8"></span>
            <span class="quoteBadgesItem b9a"></span>
            <span class="quoteBadgesItem b10a"></span>
            <span class="quoteBadgesItem b11a"></span>
            <div class="clearFixed"></div>
        </div>
        <div class="achiv badge-ba hide">
            <span class="quoteBadgesItem b17"></span>
            <span class="quoteBadgesItem b18"></span>
            <span class="quoteBadgesItem b19"></span>
            <div class="clearFixed"></div>
        </div>
        <div class="achiv badge-ana hide">
            <span class="quoteBadgesItem b12a"></span>
            <span class="quoteBadgesItem b13"></span>
            <div class="clearFixed"></div>
        </div>
        <div class="achiv badge-tqa hide">
            <span class="quoteBadgesItem b14a"></span>
            <span class="quoteBadgesItem b15"></span>
            <span class="quoteBadgesItem b16a"></span>
            <div class="clearFixed"></div>
        </div>

    </div>
</div>
<!-- /.trackFlyout -->
</div>
<!-- End .container2Content_det -->
</div>
</div>
</div>
</div>
<!-- end .getCopilotsStep -->


</div>
<!-- end .orderReview -->


</div>
<!-- end #copilotsIntroduction -->

<!-- end #launchContestOut -->
</div>
<!-- End.area1Content -->
</div>
<!-- End #area1 -->

</div>
<!-- End #mainContent -->

<jsp:include page="includes/footer.jsp"/>

</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
</div>
<s:iterator value="copilotSkills">
    <input type="hidden" id="skill-rule-${ruleId}" name="${name}" value="${description}"/>
</s:iterator>
<!-- End #wrapper -->
<!-- this area will contain the popups of this page -->

<!-- End .popups -->
<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->
</html>
