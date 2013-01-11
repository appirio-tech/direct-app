<%--
  - Author: GreatKevin, tangzx, GreatKevin, Blues, Ghost_141
  - Version: 2.1
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Two) changes:
  - Add new button to allow user not choose the 2nd place winner of copilot posting contest when review.
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
  - - Add new button "I don't want to choose copilot"
  -
  - Version 1.3 BUGR-7279
  - fix button align issue
  -
  - Version 1.4 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
  - - Added copilot posting dashboard
  -
  - Version 2.0 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
  - - Change to the new copilot posting submissions page.
  - 
  - Version 2.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Update the button text to uppercase.
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
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
    <link rel="stylesheet" href="/css/direct/permissions.css?v=193435" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/copilot/copilotPosting.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/permissions.js?v=210124"></script>
    <script type="text/javascript" src="/scripts/copilot/copilotStats.js"></script>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <script type="text/javascript">
        $(document).ready(function(){
           if($(".noCopilotButton").length > 0) {

               if (!$($(".noCopilotButton").get(0)).hasClass("chooseSecondPlaceButton")) {
                   $("#noCopilotButtonArea").append($(".noCopilotButton").get(0));
                   $("#noCopilotButtonArea").find(".noCopilotButton").removeClass("hide");
               }

           }
        });
    </script>
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
                                    <c:set var="firstPlaceWinner" value="${viewData.copilotPostingWinner}"/>
                                    <c:set var="secondPlaceWinner" value="${viewData.copilotPostingRunnerUp}"/>
                                    <input type="hidden" id="currentCopilotPostingId" value="${viewData.contestStats.contest.id}" />
                                    <input type="hidden" name="contestId" value="${viewData.contestStats.contest.id}"/>
                                    <input type="hidden" name="directProjectId" value="${viewData.contestStats.contest.project.id}"/>
                                    <div class="myCopilotsContestsList">

                                        <jsp:include page="includes/copilot/copilot-dashboard.jsp"/>

                                        <div class="container2 tabs3Container tabs3Special"
                                             id="CopilotPostingSubmissions">

                                            <jsp:include page="includes/copilot/tabs.jsp"/>

                                            <div class="container2Left ">
                                            <div class="caption">
                                                <div class="fLeft">
                                                    <a href="<s:url action='downloadAllCopilotPostingSubmissions' namespace='/copilot'/>?projectId=${param.projectId}" class="download link">Download all Submissions</a>
                                                    <c:if test="${firstPlaceWinner eq null && allSubmissionReviewed eq false}">
                                                        <span class="seprator">&nbsp;</span>
                                                        <a href="javascript:;" class="choose link noCopilotChoosen">I don't want to choose any copilot</a>
                                                    </c:if>
                                                    <c:if test="${firstPlaceWinner ne null && secondPlaceWinner eq null && allSubmissionReviewed eq false}">
                                                        <span class="seprator">&nbsp;</span>
                                                        <a href="javascript:;" class="choose link noRunnerUpChosen">I don't want to choose runner-up copilot</a>
                                                    </c:if>

                                                </div>
                                                <div class="fRight">
                                                    <a class="btn btn-compare"
                                                       href="javascript:;"><span class="bRt"> <span
                                                            class="bMid">COMPARE COPILOTS</span></span></a>
												<span class="switch">
                                                     <s:if test="viewType eq 'list'">
                                                         <a href="javascript:;" class="active listView"></a>
                                                         <a href="<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                                                                <s:param name="projectId" value="projectId"/>
                                                                <s:param name="viewType">grid</s:param>
                                                        </s:url>" class="gridview"></a>
                                                     </s:if>
                                                     <s:else>
                                                         <a href="<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                                                                <s:param name="projectId" value="projectId"/>
                                                                <s:param name="viewType">list</s:param>
                                                        </s:url>" class="listView"></a>
                                                         <a href="javascript:;" class="active gridview"></a>
                                                     </s:else>
												</span>
                                                </div>
                                            </div>
                                            <!-- /.caption -->
                                            <div class="container2Right">

                                            <div class="container2Content_det">

                                            <s:if test='viewType == "list"'>
                                                <table class="listview-table">
                                                    <colgroup>
                                                        <col width="22.7%"/>
                                                        <col width=""/>
                                                        <col width="11%"/>
                                                        <col width="22.3%"/>
                                                        <col width="21.3%"/>
                                                        <col width="16.6%"/>
                                                    </colgroup>
                                                    <thead>
                                                    <tr>
                                                        <th>Copilot</th>
                                                        <th>Fulfillment</th>
                                                        <th>Current Workload</th>
                                                        <th>Matched Experience</th>
                                                        <th>Copilot Skills</th>
                                                        <th>Choose your copilot</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <s:iterator value="copilotSubmissions">
                                                        <tr class="<c:choose><c:when test="${userId eq firstPlaceWinner.id}">rowSelCP</c:when><c:when test="${userId eq secondPlaceWinner.id}">rowSelRUP</c:when></c:choose>">
                                                            <input type="hidden" name="submissionId" value="${submissionId}"/>
                                                            <input type="hidden" name="profileId" value="${copilotProfileId}"/>
                                                            <td class="tdCopilot">
                                                                <div class="colLt">

                                                                    <s:if test="imagePath == null || imagePath.length == 0">
                                                                        <img class="memberPic" alt="copilot picture"
                                                                             src="/images/copilotPosting/copilotHeader.png"/>
                                                                    </s:if>
                                                                    <s:else>
                                                                        <img class="memberPic" alt="copilot picture" src="${imagePath}"/>
                                                                    </s:else>
                                                                    <p class="track">
                                                                        <s:if test="studioCopilot">
                                                                            <a href="javascript:;" class="studio">
                                                                                &nbsp;</a>
                                                                        </s:if>
                                                                        <s:if test="softwareCopilot">
                                                                            <a href="javascript:;" class="dev">&nbsp;</a>
                                                                        </s:if>

                                                                    </p>
                                                                    <p class="compare">
                                                                        <input type="checkbox" id="chkList${userId}" class="chkCompare"> <label for="chkList${userId}">Compare</label>
                                                                    </p>
                                                                    <c:choose>
                                                                        <c:when test="${userId eq firstPlaceWinner.id}">
                                                                            <span class="ribbon"></span>
                                                                        </c:when>
                                                                        <c:when test="${userId eq secondPlaceWinner.id}">
                                                                            <span class="ribbon"></span>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </div>
                                                                <div class="colRt">
                                                                    <div class="top">
                                                                        <p class="userHandleHolder">
                                                                            <link:user userId="${userId}"/>
                                                                        </p>

                                                                        <p>${country}</p>

                                                                        <p>${timeZone}</p>

                                                                        <p>
                                                                            <a href="http://community.topcoder.com/tc?module=ViewCopilotProfile&amp;pid=${userId}"
                                                                               class="copilotStat" target="_blank"> View Copilot Statistics</a>
                                                                        </p>

                                                                        <ul class="feedbacks">
                                                                            <li class="positiveFeedback <s:if test='positiveFeedbackNumber <= 0'>zeroPositiveFeedback</s:if>">${positiveFeedbackNumber}</li>
                                                                            <li class="negativeFeedback <s:if test='negativeFeedbackNumber <= 0'>zeroNegativeFeedback</s:if>">${negativeFeedbackNumber}</li>
                                                                        </ul>
                                                                    </div>


                                                                </div>
                                                            </td>

                                                            <td class="colFulFill"><span class="txt${fulfillmentColor}">${roundedFulfillment}%</span></td>
                                                            <td class="colWorkload">
                                                                <p>
                                                                    <strong>${currentContests} </strong>Contests
                                                                </p>

                                                                <p>
                                                                    <strong>${currentProjects} </strong>Projects
                                                                </p>
                                                            </td>

                                                            <td class="colExperience">
                                                                <s:iterator value="matchedExperience">
                                                                    <div class="row">
                                                                        <p class="xp">${projectType} <s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                                                                        <p class="xpStat">
                                                                            Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                                                                        </p>
                                                                    </div>
                                                                </s:iterator>
                                                                <p class="link">
                                                                    <a href="javascript:;" class="viewXperience">View other experience</a>
                                                                </p>

                                                                <div class="hide otherExperienceDiv">
                                                                    <span class="arrow">&nbsp;</span>
                                                                    <s:iterator value="otherExperience">
                                                                        <div class="row">
                                                                            <p class="xp">${projectType}<s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                                                                            <p class="xpStat">
                                                                                Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                                                                            </p>
                                                                        </div>
                                                                    </s:iterator>
                                                                </div>
                                                            </td>

                                                            <td class="colSkills">
                                                                <s:iterator value="copilotSkills">
                                                                    <input type="hidden" name="skill-rule-<s:property/>" value="y"/>
                                                                </s:iterator>

                                                            </td>
                                                            <td class="colPickup pickupCell <c:if test="${userId eq firstPlaceWinner.id}">cpPicked</c:if> <c:if test="${userId eq secondPlaceWinner.id}">rupPicked</c:if>">
                                                                <p class="row highlighted">
                                                                    <link:onlineReviewDownloadSubmission
                                                                            projectId="${projectId}"
                                                                            submissionId="${submissionId}"
                                                                            styleClass="submitedFile">
                                                                        #${submissionId}
                                                                    </link:onlineReviewDownloadSubmission>
                                                                    <br>
                                                                    <span class="timeStamp"><s:date name="submitTime" format="MM/dd/yyyy | hh:mm"/>&nbsp;EST</span>
                                                                </p>

                                                                <s:if test="inReviewPhase">
                                                                    <c:choose>
                                                                        <c:when test="${firstPlaceWinner eq null && allSubmissionReviewed eq false}">
                                                                            <div class="unPickedCP row">
                                                                                <a class="btn btn-red btn-pickup"
                                                                                   href="javascript:;">
                                                                            <span class="bRt"> <span class="bMid"> <span
                                                                                    class="ico">CHOOSE</span></span></span></a><br>
                                                                                <a class="btn btn-white btn-pickup-rup" style="display:none"
                                                                                   href="javascript:;">
                                                                                    <span class="bRt"> <span class="bMid"> CHOOSE AS RUNNER-UP</span></span></a>
                                                                            </div>
                                                                            <div class="pickedCP">
                                                                                <span class="pikedAsCP">CHOSEN AS COPILOT</span>
                                                                            </div>
                                                                            <div class="pickedRunnerUp">
                                                                                <span class="pikedAsRUP">CHOSEN AS RUNNER-UP</span>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:when test="${userId eq firstPlaceWinner.id}">
                                                                            <div class="pickedCP">
                                                                                <span class="pikedAsCP">CHOSEN AS COPILOT</span>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:when test="${secondPlaceWinner eq null && allSubmissionReviewed eq false}">
                                                                            <div class="unPickedCP row">
                                                                                <a href="javascript:;"
                                                                                   class="btn btn-white btn-pickup-rup"><span
                                                                                        class="bRt"> <span class="bMid"> CHOOSE AS RUNNER-UP</span></span></a>
                                                                            </div>
                                                                            <div class="pickedCP">
                                                                                <span class="pikedAsCP">CHOSEN AS COPILOT</span>
                                                                            </div>
                                                                            <div class="pickedRunnerUp">
                                                                                <span class="pikedAsRUP">CHOSEN AS RUNNER-UP</span>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:when test="${userId eq secondPlaceWinner.id}">
                                                                            <div class="pickedRunnerUp">
                                                                                <span class="pikedAsRUP">CHOSEN AS RUNNER-UP</span>
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
                                                                                <span class="pikedAsCP">CHOSEN AS COPILOT</span>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:when test="${userId eq secondPlaceWinner.id}">
                                                                            <div class="pickedRunnerUp">
                                                                                <span class="pikedAsRUP">CHOSEN AS RUNNER-UP</span>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            &nbsp;
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </s:else>
                                                            </td>
                                                        </tr>
                                                    </s:iterator>


                                                    </tbody>
                                                </table>
                                            </s:if>
                                            <s:else>
                                                <!-- /.listview-table -->
                                                <table class="gridview-table submission-table">
                                                    <colgroup>
                                                        <col width="50%"/>
                                                        <col width="50%"/>
                                                    </colgroup>
                                                    <tbody>

                                                    <s:iterator value="copilotSubmissions" status="rowStatus">
                                                        <s:if test="#rowStatus.odd == true">
                                                            <tr>
                                                        </s:if>

                                                        <td class="<c:choose><c:when test="${userId eq firstPlaceWinner.id}">rowSelCP</c:when><c:when test="${userId eq secondPlaceWinner.id}">rowSelRUP</c:when></c:choose>">
                                                            <input type="hidden" name="submissionId" value="${submissionId}"/>
                                                            <input type="hidden" name="profileId" value="${copilotProfileId}"/>
                                                            <div class="col1 col">
                                                                <s:if test="imagePath == null || imagePath.length == 0">
                                                                    <img class="memberPic" alt="copilot picture" src="/images/copilotPosting/copilotHeader.png"/>
                                                                </s:if>
                                                                <s:else>
                                                                    <img class="memberPic" alt="copilot picture" src="${imagePath}"/>
                                                                </s:else>
                                                                <p class="userHandleHolder">
                                                                    <link:user userId="${userId}"/>
                                                                </p>

                                                                <p>
                                                                    <a href="http://community.topcoder.com/tc?module=ViewCopilotProfile&amp;pid=${userId}"
                                                                       class="copilotStat" target="_blank">
                                                                        View Copilot Statistics</a>
                                                                </p>

                                                                <p class="track">
                                                                    <s:if test="studioCopilot">
                                                                        <a href="javascript:;" class="studio">
                                                                            &nbsp;</a>
                                                                    </s:if>
                                                                    <s:if test="softwareCopilot">
                                                                        <a href="javascript:;" class="dev">&nbsp;</a>
                                                                    </s:if>
                                                                </p>

                                                                <div class="feedbackWrapper">
                                                                    <ul class="feedbacks">
                                                                        <li class="positiveFeedback <s:if test='positiveFeedbackNumber <= 0'>zeroPositiveFeedback</s:if>">${positiveFeedbackNumber}</li>
                                                                        <li class="negativeFeedback <s:if test='negativeFeedbackNumber <= 0'>zeroNegativeFeedback</s:if>">${negativeFeedbackNumber}</li>
                                                                    </ul>
                                                                </div>
                                                                <p class="compare">
                                                                    <input type="checkbox" id="chkGrid${userId}" class="chkCompare"> <label for="chkGrid${userId}">Compare</label>
                                                                </p>
                                                                <c:choose>
                                                                    <c:when test="${userId eq firstPlaceWinner.id}">
                                                                        <span class="ribbon"></span>
                                                                    </c:when>
                                                                    <c:when test="${userId eq secondPlaceWinner.id}">
                                                                        <span class="ribbon"></span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </div>
                                                            <div class="colGrp">
                                                                <!-- /.col1 -->
                                                                <div class="col2 col">
                                                                    <p class="location">${country}</p>

                                                                    <div class="fullfillMent bg${fulfillmentColor}">
                                                                        <span>Fulfillment</span> <span
                                                                            class="percent bg${fulfillmentColor}">${roundedFulfillment}% <span
                                                                            class="tl corner"></span> <span
                                                                            class="tr corner"></span> <span class="bl corner"></span> <span class="br corner"></span>
																		</span>
                                                                    </div>
                                                                </div>
                                                                <!-- /.col2 -->
                                                                <div class="col3 col">
                                                                    <p class="time">${timeZone}</p>

                                                                    <div class="workload">
                                                                        <p class="title">Current Workload</p>

                                                                        <p class="value">
                                                                            <span class="v1"><strong>${currentContests}</strong><br/>Contests</span> <span
                                                                                class="v2"><strong>${currentProjects}</strong><br/>Projects</span> <span
                                                                                class="tl corner"></span> <span class="tr corner"></span> <span
                                                                                class="bl corner"></span> <span class="br corner"></span>
                                                                        </p>
                                                                    </div>
                                                                </div> <!-- /.col3 -->
                                                                <div class="col4">
                                                                    <div class="xperience colExperience">
                                                                        <p class="title">Matched Experience</p>

                                                                        <s:iterator value="matchedExperience">
                                                                            <div class="row">
                                                                                <p class="xp">${projectType} <s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                                                                                <p class="xpStat">
                                                                                    Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                                                                                </p>
                                                                            </div>
                                                                        </s:iterator>
                                                                        <p class="link">
                                                                            <a href="javascript:;" class="viewXperience">View other experience</a>
                                                                        </p>

                                                                        <div class="hide otherExperienceDiv">
                                                                            <span class="arrow">&nbsp;</span>
                                                                            <s:iterator value="otherExperience">
                                                                                <div class="row">
                                                                                    <p class="xp">${projectType}<s:if test="projectCategory != null">- ${projectCategory}</s:if></p>

                                                                                    <p class="xpStat">
                                                                                        Active : <span class="active">${activeProjectNumber}</span>, Completed : <span class="completed">${completedProjectNumber}</span>
                                                                                    </p>
                                                                                </div>
                                                                            </s:iterator>
                                                                        </div>

                                                                    </div>
                                                                    <div class="skills">
                                                                        <p class="title">Copilot Skills</p>
                                                                        <div class="colSkills">
                                                                            <s:iterator value="copilotSkills">
                                                                                <input type="hidden" name="skill-rule-<s:property/>" value="y"/>
                                                                            </s:iterator>
                                                                        </div>
                                                                    </div>
                                                                    <div class="highlightedCell corAlt">
                                                                        <link:onlineReviewDownloadSubmission
                                                                                projectId="${projectId}"
                                                                                submissionId="${submissionId}"
                                                                                >
                                                                            #${submissionId}
                                                                        </link:onlineReviewDownloadSubmission>
                                                                        <br> <span class="timeStamp"><s:date name="submitTime" format="MM/dd/yyyy | hh:mm"/>&nbsp;EST</span>
                                                                        <span class="tl corner"></span> <span class="tr corner"></span>
                                                                        <span class="bl corner"></span> <span class="br corner"></span>
                                                                    </div>
                                                                    <div class="pickupCell corAlt <c:if test="${userId eq firstPlaceWinner.id}">cpPicked</c:if> <c:if test="${userId eq secondPlaceWinner.id}">rupPicked</c:if>">
                                                                        <s:if test="inReviewPhase">
                                                                            <c:choose>
                                                                                <c:when test="${firstPlaceWinner eq null && allSubmissionReviewed eq false}">
                                                                                    <div class="unPickedCP">
                                                                                        <a class="btn btn-red btn-pickup"
                                                                                           href="javascript:;"><span
                                                                                                class="bRt"> <span class="bMid">
                                                                                <span class="ico">CHOOSE</span></span></span></a>
                                                                                        <a class="btn btn-white btn-pickup-rup" style="display:none"
                                                                                           href="javascript:;">
                                                                                    <span class="bRt"> <span
                                                                                            class="bMid">CHOOSE AS RUNNER-UP</span></span></a>
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
                                                                                    <div class="pickedCP">
                                                                                        <span class="pikedAsCP">PICKED AS COPILOT</span>
                                                                                    </div>
                                                                                    <div class="pickedRunnerUp">
                                                                                        <span class="pikedAsRUP">PICKED AS RUNNER-UP</span>
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
                                                                        <span class="tl corner"></span> <span class="tr corner"></span> <span class="bl corner"></span> <span class="br corner"></span>
                                                                    </div>
                                                                </div>


                                                                <div class="clear"></div>
                                                            </div>


                                                            <!-- /.col3 -->
                                                        </td>


                                                        <s:if test="#rowStatus.even == true">
                                                            </tr>
                                                        </s:if>

                                                    </s:iterator>

                                                    </tr>
                                                    </tbody>

                                                </table>
                                            </s:else>

                                            <!-- /.gridview-table -->
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
                                                <div class="row">
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
<div class="popups">
</div>
<!-- End .popups -->
<jsp:include page="includes/popups.jsp"/>

<div class="dialogContent" style="display: none;">

    <div id="removeProjectDialog">

        <c:if test="${firstPlaceWinner eq null}">
            <div class="header">
                    <div class="title">Choose Copilot Confirmation</div>
                    <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
            </div>

            <div class="body">
                Choose <strong id="firstPlaceCopilot"></strong> as copilot of project <strong id="projectNameLabel"></strong> ?
            </div>

            <div class="foot">
                <div class="separator"></div>
                <div class="buttons">
                    <a class="button6 chooseConfirmationButton selectWinnerCopilot" href="javascript:">
                        <span class="left"><span class="right">YES</span></span></a>
                </div>
            </div>
        </c:if>


        <c:if test="${firstPlaceWinner ne null and secondPlaceWinner eq null}">
        <div class="header">
                <div class="title">Choose second place Confirmation</div>
                <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
        </div>

        <div class="body">
            Choose <strong id="secondPlaceCopilot"></strong> as second place of this copilot posting ?
        </div>

        <div class="foot">
            <div class="separator"></div>
            <div class="buttons">
                <a class="button6 okeyButton chooseConfirmationButton selectRunnerUpCopilot" href="javascript:">
                    <span class="left"><span class="right">YES</span></span></a>
            </div>
        </div>
        </c:if>
        <s:form action="selectCopilot" namespace="/copilot" method="post" id="SelectCopilotForm">
            <s:hidden name="projectId" value="%{#attr['projectId']}" id="scpProjectId"/>
            <s:hidden name="tcDirectProjectId" value="" id="scpTcdProjectId"/>
            <s:hidden name="placement" value="" id="scpPlacement"/>
            <s:hidden name="submissionId" value="" id="scpSubmissionId"/>
            <s:hidden name="profileId" value="" id="scpProfileId"/>
        </s:form>
    </div>

</div>

</body>
<!-- End #page -->
</html>
