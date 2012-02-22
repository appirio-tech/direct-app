<%--
  - Author: Veve, isv, BLues, GreatKevin
  -
  - Version: 1.2
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project overview view.
  -
  - Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
  - Change to new UI.
  - version 1.0.2 - TC Cockpit Bug Tracking R1 Cockpit Project Tracking Assembly Change Note
  - -add issue tracking to health table
  - -add unresolved issues and ongoing bug races to project status.
  - Version 1.0.3 - Direct - TC Cockpit Contest Duration Calculation Updates Assembly Change Note:
  - added Average Contest Duration
  - Version 1.0.4 - Direct - TC Cockpit Project Health Update Assembly Change Note:
  - Added new columns to Project Health area; the data for Project Health area is loaded via AJAX call now
  - Version 1.0.5 - Release Assembly - TC Direct UI Improvement Assembly 3 Change Note:
  - Added new CSS class for project stats and project activities tables
  - Version 1.0.6 - Release Assembly - Release Assembly - TopCoder Cockpit Project Overview Update 1 Change Note:
  - Added new JSP codes for project copilots and project forum
  - Version 1.1 - Module Assembly - TC Cockpit Project Overview Project General Info Change Note:
  - Added new JSP codes for project general information table
  - Version 1.2 - Release Assembly - TC Cockpit Edit Project and Project General Info Change Note:
  - -- Added projected duration, projected cost, project ratings and additional project info into project overview page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie7.css?v=210135" />
    <![endif]-->
    <ui:projectPageType tab="overview"/>
    <link rel="stylesheet" href="/css/dashboard-view.css?v=212459" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/projectOverview.css?v=213353" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/jquery.dataTables.js?v=192711"></script>
    <script type="text/javascript" src="/scripts/jquery.ba-throttle-debounce.js?v=203928"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js?v=213353"></script>
    <script type="text/javascript" src="/scripts/jquery.jcarousel.min.js?v=211035"></script>
    <script type="text/javascript" src="/scripts/directProjectOverview.js?v=213353"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                            </div>

                            <div class="spaceWhite"></div>

                            <!-- start project general information table -->
                            <div class="projectInforDiv">
                                <div class="projectInforTitle"><a href="javascript:void(0)" class="expand">Project
                                    Information</a></div>
                                <div class="projectInformation">

                                    <div class="projectDetails" id="${fn:toLowerCase(viewData.projectGeneralInfo.statusLabel)}">

                                        <div class="columeFirst">

                                            <!-- Project Description -->
                                            <div class="projectDescription">
                                                <h3>Project Description :</h3>

                                                <div class="projectDescDetails">

                                                    <s:if test="viewData.projectGeneralInfo.project.description != null">
                                                        <p>${viewData.projectGeneralInfo.project.description}</p>
                                                    </s:if>
                                                    <s:else>
                                                        <p>
                                                            <a class="projectEditLink"
                                                               href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                                Project Description</a>
                                                        </p>
                                                    </s:else>

                                                </div>
                                            </div>
                                            <!-- End .projectDescription -->
                                        </div>

                                        <div class="columeSecond">

                                            <!-- Project Status -->
                                            <div class="projectStatus">
                                                <h3>Project Status :</h3>

                                                <p class="completedStatus">${viewData.projectGeneralInfo.statusLabel}</p>

                                                <div class="clearFix"></div>
                                            </div>
                                            <!-- End .ProjectStatus -->

                                            <div class="projectType">
                                                <h3>Project Type :</h3>

                                                <p><span>Not available for now</span>
                                                    <span></span></p>
                                            </div>

                                            <!-- Project Links -->
                                            <div class="smallProjectLinks">
                                                <h3>Project Links :</h3>
                                                    <s:if test="viewData.projectGeneralInfo.svn == null && viewData.projectGeneralInfo.jira == null">
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                            Project SVN</a> <br>
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                            Project Bug Track (JIRA)</a>
                                                    </s:if>
                                                    <s:else>
                                                <div class="linkBox">
                                                        <s:if test="viewData.projectGeneralInfo.svn != null">
                                                            <a href="${viewData.projectGeneralInfo.svn}" target="_blank" class="projectSVN">Project SVN</a>
                                                        </s:if>
                                                        <s:if test="viewData.projectGeneralInfo.jira != null">
                                                            <a href="${viewData.projectGeneralInfo.jira}" target="_blank" class="projectBug">Project Bug Track
                                                                (JIRA)</a>
                                                        </s:if>
                                                </div>
                                                    </s:else>

                                            </div>
                                            <!-- End .smallProjectLinks -->
                                        </div>

                                        <div class="columeThird">

                                            <!-- Project Budget -->
                                            <div class="projectBudget">
                                                <h3>Project Budget :</h3>

                                                <s:if test="viewData.projectGeneralInfo.totalBudget != null">
                                                    <div class="totalBudget">
                                                        <div class="projectedCost">
                                                            <div class="leftProjected">
                                                                <div class="rightProjected">
                                                                    <div class="midProjected"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="actualCost">
                                                            <div class="leftActual">
                                                                <div class="rightActual">
                                                                    <div class="midActual"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="totalBudgetInfor">
                                                        <dl>
                                                            <dt>Total Budget :</dt>
                                                            <dd><fmt:formatNumber
                                                                    value="${viewData.projectGeneralInfo.totalBudget}"
                                                                    type="currency" maxFractionDigits="0"/></dd>
                                                            <dt class="actual">Actual Cost :</dt>
                                                            <dd class="actualNum"><fmt:formatNumber
                                                                    value="${viewData.projectGeneralInfo.actualCost}"
                                                                    type="currency" maxFractionDigits="0"/></dd>
                                                            <dt class="projected">Projected Total :</dt>
                                                            <dd class="projectedNum"><fmt:formatNumber
                                                                    value="${viewData.projectGeneralInfo.projectedCost}"
                                                                    type="currency" maxFractionDigits="0"/></dd>
                                                        </dl>
                                                    </div>
                                                </s:if>
                                                <s:else>

                                                    <div class="totalBudgetInfor">
                                                        <dl>
                                                            <dt class="noData">Actual Cost :</dt>
                                                            <dd><fmt:formatNumber
                                                                    value="${viewData.projectGeneralInfo.actualCost}" type="currency" maxFractionDigits="0"/></dd>
                                                            <dt class="noData">Projected Total :</dt>
                                                            <dd><fmt:formatNumber
                                                                    value="${viewData.projectGeneralInfo.projectedCost}"
                                                                    type="currency" maxFractionDigits="0"/></dd>
                                                        </dl>
                                                    </div>
                                                    <a class="projectEditLink" href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set Project Budget</a>
                                                </s:else>

                                            </div>
                                            <!-- End .projectBudget -->

                                            <!-- Project Links -->
                                            <div class="bigProjectLinks">
                                                <h3>Project Links :</h3>


                                                <s:if test="viewData.projectGeneralInfo.svn == null && viewData.projectGeneralInfo.jira == null">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Project SVN</a> <br>
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Project Bug Track (JIRA)</a>
                                                </s:if>
                                                <s:else>
                                                    <div class="linkBox">
                                                        <s:if test="viewData.projectGeneralInfo.svn != null">
                                                            <a href="${viewData.projectGeneralInfo.svn}" target="_blank"
                                                               class="projectSVN">Project SVN</a>
                                                        </s:if>
                                                        <s:if test="viewData.projectGeneralInfo.jira != null">
                                                            <a href="${viewData.projectGeneralInfo.jira}"
                                                               target="_blank" class="projectBug">Project Bug Track
                                                                (JIRA)</a>
                                                        </s:if>
                                                    </div>
                                                </s:else>

                                            </div>
                                            <!-- End .bigProjectLinks -->
                                        </div>

                                        <div class="columeForth">

                                            <!-- Project Duration -->
                                            <div class="projectDuration">
                                                <h3>Project Duration :</h3>
                                                <s:if test="viewData.projectGeneralInfo.plannedDuration != null">
                                                    <div class="plannedDuration">
                                                        <div class="projectedDuration">
                                                            <div class="leftProjected">
                                                                <div class="rightProjected">
                                                                    <div class="midProjected"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="actualDuration">
                                                            <div class="leftActual">
                                                                <div class="rightActual">
                                                                    <div class="midActual"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="totalBudgetInfor">
                                                        <dl>
                                                            <dt>Planned Duration :</dt>
                                                            <dd>${viewData.projectGeneralInfo.plannedDuration} days</dd>
                                                            <dt class="actual">Actual Duration :</dt>
                                                            <dd class="actualNum">${viewData.projectGeneralInfo.actualDuration} days</dd>
                                                            <dt class="projected">Projected Duration</dt>
                                                            <dd class="projectedNum">${viewData.projectGeneralInfo.projectedDuration} days</dd>
                                                        </dl>
                                                    </div>
                                                </s:if>
                                                <s:else>

                                                    <div class="totalBudgetInfor">
                                                        <dl>
                                                            <dt class="noData">Actual Duration :</dt>
                                                            <dd>${viewData.projectGeneralInfo.actualDuration} days</dd>
                                                            <dt class="noData">Projected Duration</dt>
                                                            <dd>${viewData.projectGeneralInfo.projectedDuration} days
                                                            </dd>
                                                        </dl>
                                                    </div>
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Planned Duration</a>
                                                </s:else>
                                            </div>

                                            <!-- End .projectDuration -->
                                        </div>

                                        <div class="clearFix"></div>
                                    </div>

                                    <!-- Managers -->
                                    <div class="managersBox">
                                        <dl class="pRatings">
                                            <dt>Project Ratings :</dt>
                                            <s:if test="viewData.projectGeneralInfo.businessImpactRating == null && viewData.projectGeneralInfo.riskLevelRating == null && viewData.projectGeneralInfo.costRating == null
                                            && viewData.projectGeneralInfo.difficultyRating == null && viewData.projectGeneralInfo.roiRating == null ">
                                                <dd><a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Rate
                                                    The Project</a></dd>
                                            </s:if>
                                            <s:else>
                                                <dd>
                                                    <label>Business Impact</label>

                                                    <div class="ratingView rating${viewData.projectGeneralInfo.businessImpactRating}"></div>
                                                </dd>
                                                <dd>
                                                    <label>Risk Level</label>

                                                    <div class="ratingView rating${viewData.projectGeneralInfo.riskLevelRating}"></div>
                                                </dd>
                                                <dd>
                                                    <label>Cost</label>

                                                    <div class="ratingView rating${viewData.projectGeneralInfo.costRating}"></div>
                                                </dd>
                                                <dd>
                                                    <label>Difficulty</label>

                                                    <div class="ratingView rating${viewData.projectGeneralInfo.difficultyRating}"></div>
                                                </dd>
                                            </s:else>
                                        </dl>

                                        <div class="managersMask">
                                            <!-- Client Managers -->
                                            <div class="clientManagers">
                                                <h3>Client Managers : </h3>

                                                <s:if test="viewData.projectGeneralInfo.clientManagers == null || viewData.projectGeneralInfo.clientManagers.size == 0">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add Client Manager</a>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                        <s:iterator value="viewData.projectGeneralInfo.clientManagers"
                                                                    var="managerId">
                                                            <li><link:user userId="${managerId}"/></li>
                                                        </s:iterator>
                                                    </ul>
                                                </s:else>

                                            </div>

                                            <!-- End .clientManagers -->

                                            <!-- TopCoder Project Managers -->
                                            <div class="projectManagers">
                                                <h3>TopCoder Project Managers :</h3>

                                                <s:if test="viewData.projectGeneralInfo.topcoderManagers == null || viewData.projectGeneralInfo.topcoderManagers.size == 0">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                        TopCoder Project Manager</a>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                        <s:iterator value="viewData.projectGeneralInfo.topcoderManagers"
                                                                    var="managerId">
                                                            <li><link:user userId="${managerId}"/></li>
                                                        </s:iterator>
                                                    </ul>
                                                </s:else>
                                                </ul>
                                            </div>
                                        </div>

                                        <dl class="additionalInfo">
                                            <dt>Additional Project Information :</dt>

                                            <s:if test="viewData.projectGeneralInfo.additionalProjectInfo.size > 0">
                                                <s:iterator value="viewData.projectGeneralInfo.additionalProjectInfo">
                                                    <dd>
                                                        <label><s:property value="key"/> :</label>
                                                                                                <span>
                                                                                                <s:iterator
                                                                                                        value="value"
                                                                                                        status="valueStatus">
                                                                                                    <s:if test="#valueStatus.count != 1">,&nbsp;</s:if><s:property></s:property>
                                                                                                </s:iterator>
                                                                                                </span>
                                                    </dd>
                                                </s:iterator>
                                            </s:if>
                                            <s:else>
                                                <dd>
                                                    <a class="projectEditLink"
                                                        href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">
                                                        Set additional project information</a>
                                                </dd>
                                            </s:else>

                                        </dl>
                                        <!-- End .projectManagers -->

                                        <div class="clearFix"></div>
                                    </div>
                                    <!-- End .managersBox -->
                                </div>
                            </div>
                            <!-- end project general information table -->

                            <div class="dashboardTable">
                                <dl>
                                    <dt>
                                        <a href="javascript:void(0)" class="expand">Project Health</a>
                                    </dt>
                                    <dd>
                                        <div class="projectHealthHeader">
                                            <table cellpadding="0" cellspacing="0">
                                                <colgroup>
                                                    <col width="26%" />
                                                    <col width="13%" />
                                                    <col width="10%" />
                                                    <col width="14%" />
                                                    <col width="7%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                    <col width="6%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th class="first">Contest</th>
                                                        <th>Type</th>
                                                        <th>Current Phase</th>
                                                        <th>Next Phase</th>
                                                        <th>Timeline</th>
                                                        <th>Registration</th>
                                                        <th>Review</th>
                                                        <th>Forum</th>
                                                        <th>Dependencies</th>
                                                        <th>Issue Tracking</th>
                                                    </tr>
                                                </thead>
                                            </table>
                                        </div>
                                        <div class="projectHealthBody">
                                            <table  cellpadding="0" cellspacing="0" id="projectHealthTable">
                                                <colgroup>
                                                    <col width="26%" />
                                                    <col width="13%" />
                                                    <col width="10%" />
                                                    <col width="14%" />
                                                    <col width="7%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                    <col width="6%" />
                                                    <col width="6%"/>
                                                    <col width="6%"/>
                                                </colgroup>
                                                <tbody id="projectHealthTableBody">
                                                    <tr id="loaderProjectHealthWrapper">
                                                        <td colspan="10">
                                                            <div id="loaderProjectHealth">
                                                                &nbsp;<img src="/images/ajax-loader.gif" alt="Loading" width="220" height="19"/>&nbsp;
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </dd>
                                </dl>
                            </div>
                            <!-- End .dashboardTable -->


                            <div class="leftColumn">
                                <div class="areaHeader padding2 titleProjectCopilots">

                                        <h2 class="title">Project Copilots</h2>
                                </div><!-- End .areaHeader -->

                            <s:if test="copilotStats.size == 0">

                                <!-- start .projectCopilotsLeader -->
                                <div class="projectCopilotsLeader">

                  <div class="goForCopilot">
                    <h3>There is currently no copilot hired for this project.</h3>
                    <div class="goForCopilotBox">
                      <div class="leftBox">
                        <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>" class="buttonRed1"><span>GET A COPILOT</span></a>
                        <p>If you do not have a clear choice for a copilot, post the offer to available copilots.</p>
                      </div>
                      <div class="rightBox">
                        <a href="javascript:;" class="buttonRed1 triggerModal" name="copilotManageModal"><span>ADD/REMOVE</span></a>
                        <p>If you already have a copilot that you would like to assign. </p>
                      </div>
                    </div>
                  </div>
                  <!-- End .goForCopilot -->

                  <div class="projectCopilotsProblem">
                    <dl>
                      <dt>Want to know more about Copilot?</dt>
                      <dd><a href="https://www.topcoder.com/help/2011/03/10/how-can-i-get-a-copilot/">What's Copilot?</a></dd>
                      <dd><a href="https://www.topcoder.com/help/2011/03/10/how-will-using-a-copilot-benefit-the-user-and-the-users-project/">How can Copilot benefit my project?</a></dd>
                      <dd><a href="https://www.topcoder.com/help/2011/05/11/how-to-launch-a-copilot-contest/">How to hire a CoPilot</a></dd>
                    </dl>
                  </div>
                  <!-- End .projectCopilotsProblem -->

                </div><!-- End .projectCopilotsLeader -->
                            </s:if>
                            <s:else>
                               <div class="projectCopilotsList">
                  <div class="copilotsListHeader">
                    <h3><strong class="red"><s:property value="copilotStats.size"/></strong> copilot working on this project.</h3>
                  </div>
                  <div class="copilotsListBody">
                    <div id="projectCopilotsCarouselWrapper" class="noCopilotType">
                      <ul id="projectCopilotsCarousel" class="jcarousel-skin-tango">

                                                <s:iterator value="copilotStats">
                                                    <!-- item -->
                                                    <li>
                                                        <div class="itemContainer">
                                                            <div class="userPhoto">
                                                                <div class="userPhotoInner">
                                                                    <a href="https://community.topcoder.com/tc?module=ViewCopilotProfile&amp;pid=<s:property value='copilotInfo.userId'/>">
                                                                        <s:if test="copilotInfo.avatarPath == null || copilotInfo.avatarPath.length == 0">
                                                                            <img class="projectCopilotPhoto" src="/images/user-photo-placeholder.png"
                                                                                 alt="Copilot Photo"/>
                                                                        </s:if>
                                                                        <s:else>
                                                                            <img class="projectCopilotPhoto" src="<s:property value='copilotInfo.avatarPath'/>"
                                                                                 alt="Copilot Photo"/>
                                                                        </s:else>
                                                                    </a>

                                                                    <div class="handleLink">
                                                                        <link:user userId="${copilotInfo.userId}"
                                                                                   handle="${copilotInfo.handle}"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="userInfor">
                                                                <div class="userInforInner">
                                                                    <dl>
                                                                        <dd>
                                                                            <span class="dt"># of Drafts:</span>
                                                                            <span class="dd"><s:property
                                                                                value="draftContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># Active Contests :</span>
                                                                            <span class="dd"><s:property
                                                                                value="activeContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># Finished Contests :</span>
                                                                            <span class="dd"><s:property
                                                                                value="finishedContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># Failures :</span>
                                                                            <span class="dd"><s:property
                                                                                value="failuresContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt">Fulfillment :</span>
                                                                            <span class="dd"><s:if test="finishedContestsNumber + failuresContestsNumber <= 0">
                                                                                N/A
                                                                            </s:if>
                                                                            <s:else>
                                                                                <s:property value="getText('{0,number, #0.00%}',{fulfillment})"/>
                                                                            </s:else></span>
                                                                        </dd>


                                                                    </dl>
                                                                    <div class="mailTo"><a
                                                                            href="mailTo:${copilotInfo.handle}@copilots.topcoder.com">Contact
                                                                        Copilot</a></div>
                                                                </div>
                                                            </div>
                                                            <div class="clearFix"></div>
                                                        </div>
                                                    </li>
                                                    <!-- End .item -->
                                                </s:iterator>


                      </ul>
                    </div>
                  </div>
                  <div class="copilotsListButtonBox">
                    <a href="javascript:;" class="buttonRed1 triggerModal" name="copilotManageModal"><span>ADD/REMOVE</span></a>
                  </div>
                </div>
                            </s:else>

                                <div class="areaHeader padding2 titleProjectStats">
                                    <h2 class="title">Project Stats</h2>
                                </div>
                                <!-- End .areaHeader -->


                                <table class="projectStats projectOverviewStats" cellpadding="0" cellspacing="0">
                                    <s:push value="viewData.projectStats">
                                        <tbody>
                                            <tr>
                                                <td class="statusName"># of Drafts</td>
                                                <td><s:property value="draftContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName"># Scheduled</td>
                                                <td><s:property value="pipelineContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName"># Active</td>
                                                <td><s:property value="runningContestsNumber"/></td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Finished(Completed/Failed/Cancelled)</td>
                                                <td>${viewData.projectStats.finishedContestsNumber + viewData.dashboardProjectStat.cancelledNumber}(${viewData.dashboardProjectStat.completedNumber}/
                                                    ${viewData.projectStats.finishedContestsNumber-viewData.dashboardProjectStat.completedNumber}/
                                                    ${viewData.dashboardProjectStat.cancelledNumber})</td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Member Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalMemberCost}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Member Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageMemberCostPerContest}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Contest Fee</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalContestFee}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Contest Fee</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageContestFeePerContest}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Total Project Cost</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.totalProjectCost}" pattern="$#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Contest Duration</td>
                                                <td>
                                                    <c:out value="${tcdirect:getDurationTextInDays(viewData.dashboardProjectStat.averageContestDuration)}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Average Fulfillment</td>
                                                <td>
                                                    <fmt:formatNumber value="${viewData.dashboardProjectStat.averageFulfillment}" pattern="##0.##"/>%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Unresolved Issues</td>
                                                <td>
                                                    <a href="<s:url action='projectIssueTracking' namespace='/'>
                                                    <s:param name='formData.projectId' value='formData.projectId'/>
                                                     <s:param name='subTab'>issues</s:param></s:url>">

                                                    ${viewData.dashboardProjectStat.unresolvedIssuesNumber}</a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="statusName">Ongoing Bug Races</td>
                                                <td>
                                                     <a href="<s:url action='projectIssueTracking' namespace='/'>
                                                    <s:param name='formData.projectId' value='formData.projectId'/>
                                                     <s:param name='subTab'>bugRaces</s:param></s:url>">${viewData.dashboardProjectStat.ongoingBugRacesNumber}</a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </s:push>
                                </table><!-- End .projectsStats -->
                            </div>

                            <div class="rightColumn">

                                <div class="areaHeader padding2 titleProjectForum">
                                    <h2 class="title">Project Forum</h2>
                                    <s:if test='viewData.hasForumThreads'>
                                      <div id="watchForumOptions" class="watchForumOptions" style="display:none;"> 
                                        	<input type="checkbox" value="" name="chProjectForum" id="chProjectForum">
                                          <label id="chProjectForum">Watch Project Forum?</label>
                                      </div>
                                    </s:if>                                    
                                </div><!-- End .areaHeader -->

                                <s:if test='viewData.hasForumThreads'>
                                    <div id="projectForumTable">
                                        <div class="projectForumTableHeader">
                                            <div class="colTab1">Project Discussions</div>
                                            <div class="colTab2">T./M.</div>
                                            <div class="colTab3">Last post</div>
                                        </div>
                                        <div class="projectForumTableBody">
                                            <div class="projectForumTableBodyInner">
                                                <div class="ajaxTableLoader">
                                                    <img src="/images/loadingAnimation.gif" alt="loading"/></div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- End #projectForumTable -->
                                </s:if>
                                <s:elseif test='viewData.projectStats.project.projectForumCategoryId == null || viewData.projectStats.project.projectForumCategoryId.equals("")'>
                                    <div class="projectForumLeader">
                                        <h3>Create a project forum to start communication</h3>
                                        <p>If you have a question / problem about your project, you can create a project forum to start the discussion with TopCoder PM and your copilots</p>
                                        <div class="projectForumLeaderButton">
                                            <a href="javascript:;" class="buttonRed1 createForumButton"><span>CREATE PROJECT FORUM</span></a>
                                        </div>
                                    </div>
                                </s:elseif>
                                <s:else>
                                     <div class="projectForumLeader">
                                        <h3>Discuss This Project!</h3>
                                        <p>Need to discuss this project?</p>
                                        <p> Share information, requirements, comments, etc. with fellow team members, copilots and TopCoder managers.</p>
                                        <div class="projectForumLeaderButton">
                                            <a href="http://apps.topcoder.com/forums/?module=Category&categoryID=${viewData.projectStats.project.projectForumCategoryId}" class="buttonRed1"><span>LET'S TALK</span></a>
                                        </div>
                                    </div>
                                </s:else>


                                <div class="areaHeader padding2">
                                    <h2 class="title">Project Activities</h2>
                                </div><!-- End .areaHeader -->


                                <s:iterator value="viewData.latestProjectActivities.activities">
                                    <div class="projectTableContainer">
                                        <span class="leftCorner"></span>
                                        <span class="rightCorner"></span>
                                        <table class="project projectActivities" width="100%" cellpadding="0"
                                               cellspacing="0">
                                            <thead>
                                            <tr>
                                                <th colspan="5"><span class="left"><span class="right">
                                                    <a href="javascript:;"><s:property
                                                            value="key.title"/></a></span></span>
                                                </th>
                                            </tr>
                                            </thead>

                                            <tbody>
                                            <s:iterator value="value" status="status">
                                                <s:set value="originatorId" var="originatorId" scope="page"/>
                                                <s:set value="originatorHandle" var="originatorHandle" scope="page"/>
                                                <s:set value="contest" var="contest" scope="page"/>
                                                <s:set value="date" var="date" scope="page"/>

                                                <tr class="<s:property value='type.shortName'/> <s:if test='#status.index == 4'>hideStart</s:if>">
                                                    <td class="first <s:property value="type.shortName"/>"></td>
                                                    <td class="second">
                                                    <span class="ico <s:property value="type.shortName"/>">
                                                        <s:property value="type.name"/></span>
                                                    </td>
                                                    <td>
                                                        <a class="longWordsBreak"
                                                           href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>">
                                                            <c:out value="${contest.title}"/></a>
                                                    </td>
                                                    <td class="posted">
                                                        <s:property value="type.actionText"/> :
                                                        <link:user userId="${originatorId}"
                                                                   handle="${originatorHandle}"/>
                                                    </td>
                                                    <td class="date">
                                                        <c:out value="${tcdirect:getDateText(date, 'MM/dd/yyyy')}"/>
                                                    </td>
                                                </tr>

                                            </s:iterator>
                                            </tbody>
                                        </table>
                                    </div>
                                </s:iterator>

                                <jsp:include page="includes/upcomingActivities.jsp"/>
                            </div><!-- End .rightColumn -->
                        <div class="clearFix"></div>

                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

<!-- .tooltipArea -->
<div class="tooltipArea"></div>
<!-- end .tooltipArea -->

</body>
<!-- End #page -->

</html>
