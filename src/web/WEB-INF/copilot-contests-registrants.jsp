<%--
  - Author: GreatKevin
  - Version: 1.3
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
  - - Added copilot posting dashboard
  -
  - Version 1.2 (Module Assembly - Cockpit Copilot Posting Registrants Analysis)
  - - Update the copilot posting registrants to a new look
  -
  - Version 1.3 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
  - - Update copilot skills data
  - - Add copilot feedback number stats
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="PAGE_TYPE" value="project" scope="request"/>
<c:set var="CURRENT_TAB" value="contests" scope="request"/>
<c:set var="CURRENT_SUB_TAB" value="copilotContestRegistrants" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/direct/copilot/copilotPosting.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/copilot/copilotStats.js"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="liquid">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">

                        <div class="currentPage">
                            <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                            <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
                                    value="sessionData.currentProjectContext.name"/></a> &gt;
                            <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilot Posting</a> &gt;
                            <strong><c:out value="${viewData.contestStats.contest.title}"/></strong>
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

                                    <div class="myCopilotsContestsList">

                                        <jsp:include page="includes/copilot/copilot-dashboard.jsp"/>

                                        <div class="container2 tabs3Container tabs3Special" id="CopilotPostingRegistrants">

                                            <jsp:include page="includes/copilot/tabs.jsp"/>

<div class="container2Left ">
<div class="caption">
    <div class="fRight">
												<span class="switch">
                                                     <s:if test="viewType eq 'list'">
                                                         <a href="javascript:;" class="active listView"></a>
                                                         <a href="<s:url namespace="/copilot" action="listCopilotContestRegistrants">
                                                                <s:param name="projectId" value="projectId"/>
                                                                <s:param name="viewType">grid</s:param>
                                                        </s:url>" class="gridview"></a>
                                                     </s:if>
                                                     <s:else>
                                                         <a href="<s:url namespace="/copilot" action="listCopilotContestRegistrants">
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
            <col width="25%"/>
            <col width=""/>
            <col width="14%"/>
            <col width="25%"/>
            <col width="25%"/>
        </colgroup>
        <thead>
        <tr>
            <th>Copilot</th>
            <th>Fulfillment</th>
            <th>Current Workload</th>
            <th>Matched Experience</th>
            <th>Copilot Skills</th>
        </tr>
        </thead>
        <tbody>

        <s:iterator value="copilots">
            <tr>
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
                    </div>
                    <div class="colRt">
                        <div class="top">
                            <p>
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
            </tr>
        </s:iterator>


        </tbody>
    </table>
</s:if>
<s:else>
    <!-- /.listview-table -->
    <table class="gridview-table">
        <colgroup>
            <col width="50%"/>
            <col width="50%"/>
        </colgroup>
        <tbody>

        <s:iterator value="copilots" status="rowStatus">
            <s:if test="#rowStatus.odd == true">
                <tr>
            </s:if>

            <td>
                <div class="col1 col">
                    <s:if test="imagePath == null || imagePath.length == 0">
                        <img class="memberPic" alt="copilot picture" src="/images/copilotPosting/copilotHeader.png"/>
                    </s:if>
                    <s:else>
                        <img class="memberPic" alt="copilot picture" src="${imagePath}"/>
                    </s:else>
                    <p>
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
    <s:iterator value="copilotSkills">
        <input type="hidden" id="skill-rule-${ruleId}" name="${name}" value="${description}"/>
    </s:iterator>
</div>
<!-- End #wrapper -->
<!-- this area will contain the popups of this page -->
<div class="popups">
</div>
<!-- End .popups -->
<jsp:include page="includes/popups.jsp"/>
</body>
<!-- End #page -->
</html>
