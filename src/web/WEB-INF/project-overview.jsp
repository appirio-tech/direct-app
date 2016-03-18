<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%--
  - Author: Veve, isv, BLues, GreatKevin, duxiaoyang, GreatKevin, TCSASSEMBLER
  -
  - Version: 2.1
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
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
  - Version 1.3 - Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration
  - - Add the project permission general info.
  - Version 1.4 - Release Assembly - TopCoder Cockpit Project Overview Performance Improvement
  - - Change project stats and activities and part of project information to be loaded via ajax
  - Version 1.5 - Release Assembly - TopCoder Security Groups - Release 5
- - Hide some links/sections if user doesn't have write permission on the project.
  - Version 1.5 - Release Assembly - TC Direct Project Forum Configuration Assembly 2 
  - - Add Configure Project Forum button inside the project forum section.
  - Version 1.6 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
  - - Fix bug COCKPITUI-208.
  - Version 1.7 - Release Assembly - TopCoder Direct Cockpit Release Assembly Ten
  - - Add TopCoder Account Managers in the project general information section
  - - Add email icon with mail to for TopCoder Platform Specialists
  - Version 1.8 - Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly
  - - Add email icon with mail to for Client Managers and TopCoder Account Managers
  - Version 1.9 - Release Assembly - TopCoder Copilot Feedback Updates
  - - Adds 4 ratings to the copilot feedback
  - Version 2.0 - TC Direct Rebranding Assembly Project and Contest related pages
  - - Rebranding the project overview page
  - Version 2.1 - (topcoder Direct - Add Project Billings to project overview) @author deedee @challenge 30045142 changes:
  - - Add billing accounts list
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041" />
    <![endif]-->
    <ui:projectPageType tab="overview"/>
    <link rel="stylesheet" href="/css/direct/dashboard-view.css?v=212459" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/projectOverview.css?v=213353" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/jquery.dataTables.js?v=192711"></script>
    <script type="text/javascript" src="/scripts/jquery.ba-throttle-debounce.js?v=203928"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/jquery.jcarousel.min.js?v=211035"></script>
    <script type="text/javascript" src="/scripts/directProjectOverview.js?v=215290"></script>
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

                <div id="mainContent" class="newSidebarCollapse">

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
                            <div class="projectInforDiv" data-intro="Welcome to your project dashboard. This is the central hub for managing your project. There is a lot of information on this page. Let's boil it down and start from the top." data-step="1" data-position="top">
                                <div class="projectInforTitle"><a href="javascript:void(0)" class="expand">Project
                                    Information</a></div>
                                <c:if test="${hasWritePermission}">
                                <div class="editProject" data-intro="If you need to edit this project, manage permissions, set budget, etc. just click here." data-step="19" data-position="left">
                                    <a href="<s:url action='editProject'>
                                    <s:param name='formData.projectId'>${formData.projectId}</s:param></s:url>">
                                        Edit</a>
                                </div>
                                </c:if>
                                <div class="exportProject">
                                    <a href="<s:url action='projectInfoExport'>
                                    <s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">
                                        Export</a>
                                </div>
                                <div class="projectInformation">

                                    <div class="projectDetails" id="${fn:toLowerCase(viewData.projectGeneralInfo.statusLabel)}">

                                        <div class="columeFirst">

                                            <!-- Project Description -->
                                            <div class="projectDescription" data-intro="This is your project description. It is what you entered when you created the project. It is only visible to you and your project team members." data-step="2" data-position="right">
                                                <h3>Project Description :</h3>

                                                <div class="projectDescDetails">

                                                    <s:if test="viewData.projectGeneralInfo.project.description != null">
                                                        <p><c:out value="${viewData.projectGeneralInfo.project.description}"></c:out></p>
                                                    </s:if>
                                                    <s:else>
                                                        <c:if test="${hasWritePermission}">
                                                        <p>
                                                            <a class="projectEditLink"
                                                               href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                                Project Description</a>
                                                        </p>
                                                        </c:if>
                                                    </s:else>

                                                </div>
                                            </div>
                                            <!-- End .projectDescription -->
                                        </div>

                                        <div class="columeSecond">

                                            <!-- Project Status -->
                                            <div class="projectStatus" data-intro="The current status of your project. You control this setting manually." data-step="3" data-position="left">
                                                <h3>Project Status :</h3>

                                                <p class="completedStatus">
                                                    <s:if test="viewData.projectGeneralInfo.statusLabel.toLowerCase() == 'archived'">
                                                        On Hold
                                                    </s:if>
                                                    <s:else>
                                                        ${viewData.projectGeneralInfo.statusLabel}
                                                    </s:else>

                                                </p>

                                                <div class="clearFix"></div>
                                            </div>
                                            <!-- End .ProjectStatus -->

                                            <div class="projectType" data-intro="Project type is defined by you. By setting this, the TopCoder Platform will better understand how to help you." data-step="4" data-position="left">
                                                <h3>Project Type :</h3>
                                                <s:if test="viewData.projectGeneralInfo.project.projectType == null && viewData.projectGeneralInfo.project.projectCategory == null">
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink" href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set Project Type</a>
                                                    </c:if>
                                                </s:if>
                                                <s:else>
                                                    <p><span><s:property value="viewData.projectGeneralInfo.project.projectType.name"/></span>
                                                        <s:if test="viewData.projectGeneralInfo.project.projectCategory != null">
                                                         - <span><s:property value="viewData.projectGeneralInfo.project.projectCategory.name"/></span>
                                                        </s:if>
                                                    </p>
                                                </s:else>

                                            </div>

                                            <!-- Project permissions -->
                                            <div class="projectTechnology" data-intro="Who has access to your project? Click here to see the full list." data-step="5" data-position="left">
                                                <h3>Users with permission :</h3>
                                                <p>${viewData.projectGeneralInfo.permissionInfo.totalPermissionNumber} Users (Report ${viewData.projectGeneralInfo.permissionInfo.reportPermissionNumber}/ Read ${viewData.projectGeneralInfo.permissionInfo.readPermissionNumber}/ Write ${viewData.projectGeneralInfo.permissionInfo.writePermissionNumber}/ Full ${viewData.projectGeneralInfo.permissionInfo.fullPermissionNumber})</p>
                                                <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink" href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>#permissionsNotifications">Edit Project Permission</a>
                                                </c:if>
                                            </div>
                                            <!-- End Project Permissions -->
                                            <!-- Project Billing -->
                                            <div class="projectTechnology" data-intro="What is associated billing account? Click here to see the full list." data-step="6" data-position="left">
                                                <h3>Project Billings :</h3>
                                                <s:iterator value="viewData.billingAccounts" var="billing">
                                                        <p>${billing.name}</p>
                                                </s:iterator>
                                                <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink" href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>#projectBilling">Edit Project Billings</a>
                                                </c:if>
                                            </div>
                                            <!-- End Project Billing -->
                                            <!-- Project Links -->
                                            <div class="smallProjectLinks" >
                                                <h3 >Project Links :</h3>
                                                    <s:if test="viewData.projectGeneralInfo.svn == null && viewData.projectGeneralInfo.jira == null">
                                                        <c:if test="${hasWritePermission}">
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                            Project SVN</a> <br>
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                            Project Bug Track (JIRA)</a>
                                                        </c:if>
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
                                            <div class="projectBudget" data-intro="Summary of your project's financials. You set the Total Budget. Actual Cost is calculated based on what you've spent using TopCoder. Projected Total is your Actual Cost so far plus any scheduled costs in your game plan." data-step="7" data-position="left">
                                                <h3>Project Budget :</h3>

                                                <s:if test="viewData.projectGeneralInfo.totalBudget != null">
                                                    <div class="totalBudget" style="visibility: hidden;">
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
                                                            <dd class="actualNum actualCostSlot"><span style="font-size:11px">Loading...</span></dd>
                                                            <dt class="projected">Projected Total :</dt>
                                                            <dd class="projectedNum projectedCostSlot"><span style="font-size:11px">Loading...</span></dd>
                                                            <span class='hide plannedCostValue'>${viewData.projectGeneralInfo.projectedCost}</span>
                                                        </dl>
                                                    </div>
                                                </s:if>
                                                <s:else>

                                                    <div class="totalBudgetInfor">
                                                        <dl>
                                                            <dt class="noData">Actual Cost :</dt>
                                                            <dd class="actualCostSlot"><span style="font-size:11px">Loading...</span></dd>
                                                            <dt class="noData">Projected Total :</dt>
                                                            <dd class="projectedCostSlot"><span style="font-size:11px">Loading...</span></dd>
                                                            <span class='hide plannedCostValue'>${viewData.projectGeneralInfo.projectedCost}</span>
                                                        </dl>
                                                    </div>
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink" href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set Project Budget</a>
                                                    </c:if>
                                                </s:else>

                                            </div>
                                            <!-- End .projectBudget -->

                                            <!-- Project Links -->
                                            <div class="bigProjectLinks" data-intro="These are configurable by you to help manage the location of your version control and issue tracking." data-step="6" data-position="left">
                                                <h3>Project Links :</h3>


                                                <s:if test="viewData.projectGeneralInfo.svn == null && viewData.projectGeneralInfo.jira == null">
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Project SVN</a> <br>
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Project Bug Track (JIRA)</a>
                                                    </c:if>
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
                                            <div class="projectDuration" data-intro="Summary of your project's timeline. You set the Planned Duration. Actual Duration is calculated based on the time your game plan started through today. Projected Duration is your Actual Duration so far plus any scheduled time in your game plan." data-step="8" data-position="left">
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
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Set
                                                        Planned Duration</a>
                                                    </c:if>
                                                </s:else>
                                            </div>

                                            <!-- End .projectDuration -->
                                        </div>

                                        <div class="clearFix"></div>
                                    </div>

                                    <!-- Managers -->
                                    <div class="managersBox">
                                        <dl class="pRatings" data-intro="These ratings are subjective and are set by you. They are optional, but consider using them to help view and manage the ROI of your project portfolio." data-step="9" data-position="right">
                                            <dt>Project Ratings :</dt>
                                            <s:if test="viewData.projectGeneralInfo.businessImpactRating == null && viewData.projectGeneralInfo.riskLevelRating == null && viewData.projectGeneralInfo.costRating == null
                                            && viewData.projectGeneralInfo.difficultyRating == null && viewData.projectGeneralInfo.roiRating == null ">
                                                <c:if test="${hasWritePermission}">
                                                <dd><a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Rate
                                                    The Project</a></dd>
                                                </c:if>
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
                                            <div class="clientManagers" data-intro="Client managers are likely you, assuming you are a customer of TopCoder. Add your managers here." data-step="10" data-position="left">
                                                <h3>Client Managers : </h3>

                                                <s:if test="viewData.projectGeneralInfo.clientManagers == null || viewData.projectGeneralInfo.clientManagers.size == 0">
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add Client Manager</a>
                                                    </c:if>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                        <s:iterator value="viewData.projectGeneralInfo.clientManagers"
                                                                    var="managerId">
                                                            <li><link:user userId="${managerId}"/><a href="mailto:${viewData.projectGeneralInfo.projectResourceEmails[managerId]}"><img src="/images/icon-email.png"/></a></li>
                                                        </s:iterator>
                                                    </ul>
                                                </s:else>

                                            </div>

                                            <!-- End .clientManagers -->

                                            <!-- TopCoder Project Managers -->
                                            <div class="projectManagers platformManagers" data-intro="Expert Services are TopCoder resources that are assigned to your projects. Reach out to them if you're having issues or have questions." data-step="11" data-position="left">
                                                <h3>TopCoder Expert Services :</h3>

                                                <s:if test="viewData.projectGeneralInfo.topcoderManagers == null || viewData.projectGeneralInfo.topcoderManagers.size == 0">
                                                    <c:if test="${hasWritePermission}">
                                                    <a class="projectEditLink"
                                                       href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                        TopCoder Expert Services</a>
                                                    </c:if>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                        <s:iterator value="viewData.projectGeneralInfo.topcoderManagers"
                                                                    var="managerId">
                                                            <li><link:user userId="${managerId}"/><a href="mailto:${viewData.projectGeneralInfo.projectResourceEmails[managerId]}"><img src="/images/icon-email.png"/></a></li>
                                                        </s:iterator>
                                                    </ul>
                                                </s:else>
                                                </ul>
                                            </div>

                                            <div class="projectManagers" data-intro="Account Managers are TopCoder resources that are responsible for your relationship with TopCoder." data-step="12" data-position="left">
                                                <h3>TopCoder Account Managers :</h3>

                                                <s:if test="viewData.projectGeneralInfo.accountManagers == null || viewData.projectGeneralInfo.accountManagers.size == 0">
                                                    <c:if test="${hasWritePermission}">
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                            TopCoder Account Managers</a>
                                                    </c:if>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                        <s:iterator value="viewData.projectGeneralInfo.accountManagers"
                                                                    var="managerId">
                                                            <li><link:user userId="${managerId}"/><a href="mailto:${viewData.projectGeneralInfo.projectResourceEmails[managerId]}"><img src="/images/icon-email.png"/></a></li>
                                                        </s:iterator>
                                                    </ul>
                                                </s:else>
                                                </ul>
                                            </div>

                                            <div class="projectManagers" data-intro="Appirio Project Manager are TopCoder resources that are responsible for your relationship with TopCoder." data-step="12" data-position="left">
                                                <h3>Appirio Project Managers :</h3>

                                                <s:if test="viewData.projectGeneralInfo.appirioManager == null">
                                                    <c:if test="${hasWritePermission}">
                                                        <a class="projectEditLink"
                                                           href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">Add
                                                            Appirio Project Manager</a>
                                                    </c:if>
                                                </s:if>
                                                <s:else>
                                                    <ul>
                                                  
                                                            <li><link:user userId="${viewData.projectGeneralInfo.appirioManager}"/><a href="mailto:${viewData.projectGeneralInfo.projectResourceEmails[viewData.projectGeneralInfo.appirioManager]}"><img src="/images/icon-email.png"/></a></li>
                                                       
                                                </s:else>
                                                </ul>
                                            </div>

                                        </div>

                                        <dl class="additionalInfo" data-intro="You can define additional fields for your projects.  Define them once by editing this project, and then you'll see the same fields in all projects.  You can then slice your data by these fields in your reports." data-step="13" data-position="left">
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
                                                <c:if test="${hasWritePermission}">
                                                <dd>
                                                    <a class="projectEditLink"
                                                        href="<s:url action='editProject'><s:param name='formData.projectId'>${viewData.projectGeneralInfo.project.projectId}</s:param></s:url>">
                                                        Set additional project information</a>
                                                </dd>
                                                </c:if>
                                            </s:else>

                                        </dl>
                                        <!-- End .projectManagers -->

                                        <div class="clearFix"></div>
                                    </div>
                                    <!-- End .managersBox -->
                                </div>
                            </div>
                            <!-- end project general information table -->

                            <div class="dashboardTable dashboardTable2" data-intro="The active work in your project is shown here. Check the green/yellow/red indicators to quickly monitor the health of each competition or task." data-step="14" data-position="top">
                                <dl>
                                    <dt>
                                        <a href="javascript:void(0)" class="expand">Active Challenges Health</a>
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
                                                        <th class="first">Challenge</th>
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
                                <div class="areaHeader padding2 titleProjectCopilots" data-intro="These are you project copilots. Be sure to provide feedback about them. Don't have a copilot? Click the green Get Copilot button." data-step="15" data-position="top">

                                        <h2 class="title">Project Copilots</h2>
                                </div><!-- End .areaHeader -->

                            <s:if test="copilotStats.size == 0">

                                <!-- start .projectCopilotsLeader -->
                                <div class="projectCopilotsLeader">

                  <div class="goForCopilot">
                    <h3>There is currently no copilot hired for this project.</h3>
                    <c:if test="${hasWritePermission}">
                    <div class="goForCopilotBox">
                      <div class="leftBox">
                        <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>" class="buttonRed1 newButtonGreen"><span>GET A COPILOT</span></a>
                        <p>If you do not have a clear choice for a copilot, post the offer to available copilots.</p>
                      </div>
                      <div class="rightBox">
                        <a href="javascript:;" class="buttonRed1 newButtonGreen triggerModal" name="copilotManageModal"><span>ADD/REMOVE</span></a>
                        <p>If you already have a copilot that you would like to assign. </p>
                      </div>
                    </div>
                    </c:if>
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
                    <h3><strong class="red"><s:property value="copilotStats.size"/></strong> &nbsp;<s:if test="copilotStats.size == 1">copilot</s:if><s:else>copilots</s:else> working on this project.</h3>
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
                                                                    <a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=ViewCopilotProfile&amp;pid=<s:property value='copilotInfo.userId'/>">
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
                                                                <input type="hidden" value="${copilotInfo.copilotProjectId}" name="copilotProjectIdInput"/>
                                                                <s:if test="feedback != null">
                                                                    <input type="hidden" name="copilotFeedbackStatus" value="${feedback.status}">
                                                                    <input type="hidden" name="copilotFeedbackAnswer" value="${feedback.answer}">
                                                                    <input type="hidden" name="copilotFeedbackText" value="<s:property value='feedback.text'/>">
                                                                    <input type="hidden" name="copilotFeedbackTimelineRating" value="<s:property value='feedback.timelineRating'/>">
                                                                    <input type="hidden" name="copilotFeedbackQualityRating" value="<s:property value='feedback.qualityRating'/>">
                                                                    <input type="hidden" name="copilotFeedbackCommunicationRating" value="<s:property value='feedback.communicationRating'/>">
                                                                    <input type="hidden" name="copilotFeedbackManagementRating" value="<s:property value='feedback.managementRating'/>">
                                                                </s:if>
                                                                <div class="userInforInner">
                                                                    <dl>
                                                                        <dd>
                                                                            <span class="dt"># of Drafts:</span>
                                                                            <span class="dd"><s:property
                                                                                value="draftContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># of Active :</span>
                                                                            <span class="dd"><s:property
                                                                                value="activeContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># of Finished :</span>
                                                                            <span class="dd"><s:property
                                                                                value="finishedContestsNumber"/></span>
                                                                        </dd>
                                                                        <dd>
                                                                            <span class="dt"># of Failures :</span>
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
                  <c:if test="${hasWritePermission}">
                  <div class="copilotsListButtonBox">
                        <a href="javascript:;" class="buttonRed1 newButtonGreen triggerModal" name="newCopilotFeedbackModal"><span>LEAVE FEEDBACK</span></a>
                        <a href="javascript:;" class="buttonRed1 newButtonGreen triggerModal hide" name="viewCopilotFeedbackModal"><span>VIEW FEEDBACK</span></a>
                        <a href="javascript:;" class="buttonRed1 newButtonGreen triggerModal hide" name="editCopilotFeedbackModal"><span>EDIT FEEDBACK</span></a>
                        <a href="javascript:;" class="buttonRed1 newButtonGreen triggerModal" name="copilotManageModal"><span>ADD/REMOVE</span></a>
                  </div>
                  </c:if>
                </div>
                            </s:else>

                                <div class="areaHeader padding2 titleProjectStats">
                                    <h2 class="title">Project Stats</h2>
                                </div>
                                <!-- End .areaHeader -->
                                <table id="projectStatistics" class="projectStats projectOverviewStats" cellpadding="0" cellspacing="0"  data-intro="Summary of your project metrics. Compare these to the market date on your Enterprise Dashboard." data-step="16" data-position="top">
                                    <tbody>
                                        <tr>
                                            <td><div class="ajaxTableLoader"><img src="/images/loadingAnimation.gif" alt="loading" /></div></td>
                                        </tr>
                                        <%@include file="project-overview-project-stats.jsp"%>
                                    </tbody>
                                </table>
                               <!-- End .projectsStats -->
                            </div>

                            <div class="rightColumn">

                                <div class="areaHeader padding2 titleProjectForum" data-intro="Every project has it's own private forum. Only you and your team members see this. It is not visible to the general community. Use your project forum to discuss the project with your team." data-step="17" data-position="bottom">
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
										<div class="projectForumButton">
                                            <a href="javascript:;" class="buttonRed1 newButtonGreen configreButton buttonToolTip">
                                                <span>CONFIGURE PROJECT FORUM</span>
                                                <div class="buttonToolTipContainer hide">
                                                    <div class="arrow"></div>
                                                    <p class="textBox">
                                                        <b>Configure Project Forums</b>
                                                    </p>
                                                    <p class="tooltipContent">You can Add/Remove Forums to the <br/> Project from here...</p>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                    <!-- End #projectForumTable -->
                                </s:if>
                                <s:elseif test='viewData.projectStats.project.projectForumCategoryId == null || viewData.projectStats.project.projectForumCategoryId.equals("")'>
                                    <div class="projectForumLeader">
                                        <h3>Create a project forum to start communication</h3>
                                        <p>If you have a question / problem about your project, you can create a project forum to start the discussion with TopCoder PM and your copilots</p>
                                        <c:if test="${hasWritePermission}">
                                        <div class="projectForumLeaderButton">
                                            <a href="javascript:;" class="buttonRed1 newButtonGreen createForumButton"><span>CREATE PROJECT FORUM</span></a>
                                        </div>
                                        </c:if>
                                    </div>
                                </s:elseif>
                                <s:else>
                                     <div class="projectForumLeader">
                                        <h3>Discuss This Project!</h3>
                                        <p>Need to discuss this project?</p>
                                        <p> Share information, requirements, comments, etc. with fellow team members, copilots and TopCoder managers.</p>
                                        <div class="projectForumLeaderButton">
                                            <a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=Category&categoryID=${viewData.projectStats.project.projectForumCategoryId}" class="buttonRed1 newButtonGreen"><span>LET'S TALK</span></a>
											 <a href="javascript:;" class="buttonRed1 newButtonGreen configreButton buttonToolTip">
												<span>CONFIGURE PROJECT FORUM</span>
												<div class="buttonToolTipContainer hide">
													<div class="arrow"></div>
													<p class="textBox">
                                                    <b>Configure Project  Forums</b>
													<p class="tooltipContent">You can Add/Remove Forums to the <br/> Project from here...</p>
													</p>
												</div>
											</a>
										</div>
                                    </div>
                                </s:else>


                                <div class="areaHeader padding2 activityHeader">
                                    <h2 class="title">Project Activities</h2>
                                </div><!-- End .areaHeader -->

                                 <div class="projectTableContainer" data-intro="These are the recent competitions that have started and completed." data-step="18" data-position="top">
                                   <span class="leftCorner"></span>
                                    <span class="rightCorner"></span>
                                    <table width="100%" cellspacing="0" cellpadding="0" id="projectActivities" class="project">

                                        <thead>
                                        <tr class="">
                                            <th class="activitiesTitle"><span class="left"><span class="right">
                                                    <a href="javascript:;">Recent Activity</a></span></span>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td><div class="ajaxTableLoader"><img src="/images/loadingAnimation.gif" alt="loading" /></div></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
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
