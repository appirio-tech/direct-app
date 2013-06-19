<%--
  - Author: xjtufreeman, Ghost_141, TCSASSEMBLER
  - Version 1.2
  -
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.

  - Version: 1.1 (TC Direct Issue Tracking Tab Update Assembly 3 v1.0) change notes:
  - Added logic to display the project bugs.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - Fix multiple bugs.
  - 
  - Description: This page renders the project issue tracking view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="issues"/>
    <link rel="stylesheet" href="/css/direct/dashboard-view.css?v=212459" media="all" type="text/css" />
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

                            <!-- start .areaHeader -->
                            <div id="issueHeader" class="areaHeader">
                                <h2 class="title issueTitle">Issue Tracking</h2>
                            </div>
                            <!-- End .areaHeader -->

                         <div class="container2 tabs3Container" id="issue">

							<div class="issueTabs">
								<div class="issueTabsLeft">
                                	<div class="issueTabRight">
                                    	<div class="issueTabInner">

                                        	<!-- total -->
                                            <div class="total">
                                            	<dl>
                                                	<dt>All Issues :</dt>
                                                    <dd><s:property value="viewData.issuesNumber"/></dd>
                                                    <dt class="lastOngoing">Unresolved Issues :</dt>
                                                    <dd class="lastOngoingNum"><s:property value="viewData.unresolvedIssuesNumber"/></dd>
                                                </dl>
                                            </div>
                                            <!-- End .total -->

                                            <div class="viewAll">
                                            	<input type="checkbox" class="checkbox" /><label>View All Details</label>
                                            </div>

                                            <a class="lanchProjectBugRaceBtn button6 btnAddNew" href="javascript:;">
                                                <span class="left">
                                                    <span class="right">LAUNCH PROJECT BUG RACE</span>
                                                </span>
                                            </a>

                                        </div>
                                    </div>
                                </div>
							</div><!-- End #issueTabs -->

							<div class="container2Left"><div class="container2Right"><div class="container2BottomClear">
                            	<div class="container2Opt">
									<div class="containerNoPadding">


                                        <div class="issueSelection">
                                        	<div class="bankSelectionHead">
                                            	<div class="viewSort">
                                                	<label>View :</label>
                                                    <select class="select2">
                                                        <option>All Issues(<s:property value="viewData.issuesNumber"/>)</option>
                                                        <option>Unresolved Issues(<s:property value="viewData.unresolvedIssuesNumber"/>)</option>
                                                        <option>Resolved Issues(<s:property value="viewData.resolvedIssuesNumber"/>)</option></select>
                                                </div>
                                                <ul class="bankSelectionTab">
                                                	<li class="issueTab"><a href="javascript:;"> <span>Issue (<s:property value="viewData.issuesNumber"/>)</span></a></li>
                                                    <li class="off bugRaceTab"><a href="javascript:;"> <span>Bug Race (<s:property value="viewData.bugRacesNumber"/>)</span> </a></li>
                                                </ul><!-- End #bankSelectionTab -->
                                            </div><!-- End #bankSelectionHead -->

                                            <!-- issue selection content -->
                                            <div class="issueSelectionContent">

                                                <!-- content -->
                                                <div class="content">

                                                	<table border="0" cellpadding="0" cellspacing="0" class="issueContest">
                                                    	<colgroup>
                                                        	<col width="170" />
                                                            <col />
                                                        </colgroup>


                                                        <s:iterator value="viewData.projectIssues" id="id" status="contestStatus">

                                                        <s:if test="value.issues.size > 0">

                                                    	<tr>
                                                        	<td class="contestNameTd">
                                                                <div class="contestNameLink">
                                                                <a href="<s:if test='key.software'>

                                                                        <s:url action='contestIssuesTracking' namespace='/contest'>
                                                        	                <s:param name='projectId' value='key.id'/>
                                                        	             </s:url>
                                                        	        </s:if>
                                                        	        <s:else>
                                                        	          <s:url action='contestIssuesTracking' namespace='/contest'>
                                                        	                <s:param name='projectId' value='key.id'/>
                                                        	             </s:url>
                                                        	        </s:else>

                                                        	        ">
                                                                    <s:property value="key.title"/>
                                                                </a>
                                                                    </div>
                                                            </td>
                                                            <td class="contestIssuesTd">


                                                             <s:iterator value="value.issues"  status="issueStatus">

                                                                   	<!-- row -->
                                                            	<div class="rowItem">

                                                                	<!-- head -->
                                                                	<div class="issueContestHead">
                                                                    	<div class="issueContestTitle">
                                                                        	<p><a href="<s:property value='issueLink'/>" target="_blank"><s:property value="projectName"/> / <s:property value="issueKey"/></a></p>
                                                                            <p class="issueName"><a href="<s:property value='issueLink'/>" target="_blank"><s:property value="issueSummary"/></a></p>
                                                                        </div>
                                                                        <div class="viewAndShow">
                                                                        	<a href="javascript:;" class="viewDetails">View Details</a>
                                                                            <a href="javascript:;" class="hideDetails hidden">Hide Details</a>
                                                                        </div>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .issueContestHead -->

                                                                    <!-- short details -->
                                                                    <div class="shortDetails">
                                                                    	<dl>
                                                                        	<dt>Status : </dt>
                                                                            <dd class="issueStatus"><strong class="<s:property value='issueStatusClass'/>"><s:property value="statusName"/></strong></dd>
                                                                            <dt>Created : </dt>
                                                                            <dd><s:property value="creationDateString"/></dd>
                                                                        </dl>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .shortDetails -->

                                                                    <!-- long details -->
                                                                    <div class="longDetails hidden">

                                                                    	<ul>
                                                                        	<!-- detail -->
                                                                        	<li class="detailList">
                                                                            	<h3>Details</h3>
                                                                            	<dl>
                                                                                	<dt>Status :</dt>
                                                                                    <dd><strong class="<s:property value='issueStatusClass'/>"><s:property value="statusName"/></strong></dd>
                                                                                    <dt>Resolution :</dt>
                                                                                    <dd><s:property value="resolutionName"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End detail -->

                                                                            <!-- people -->
                                                                            <li class="peopleList">
                                                                            	<h3>People</h3>
                                                                            	<dl>
                                                                                	<dt>Reporter :</dt>
                                                                                    <dd><a href="<s:property value='reporterProfile'/>" target="_blank"><s:property value="reporter"/></a></dd>
                                                                                    <dt>Assignee :</dt>
                                                                                    <dd>
                                                                                        <s:if test="assignee == 'Unassigned'">
                                                                                            Unassigned
                                                                                        </s:if>
                                                                                        <s:else>
                                                                                            <a href="<s:property value='assigneeProfile'/>" target="_blank"><s:property value="assignee"/></a>
                                                                                        </s:else>

                                                                                    </dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End people -->

                                                                            <!-- dates -->
                                                                            <li class="datesList">
                                                                            	<h3>Dates</h3>
                                                                            	<dl>
                                                                                	<dt>Created :</dt>
                                                                                    <dd><s:property value="creationDateString"/></dd>
                                                                                    <dt>Updated :</dt>
                                                                                    <dd><s:property value="updateDateString"/></dd>
                                                                                    <dt>Due :</dt>
                                                                                    <dd><s:property value="dueDateString"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End .dates -->
                                                                        </ul>

                                                                        <div class="clear"></div>

                                                                    </div>
                                                                    <!-- End .longDetails -->

                                                                </div>

                                                             </s:iterator>

                                                            </td>
                                                        </tr>

                                                        </s:if>

                                                        </s:iterator>
                                                    </table>

                                                </div>
                                                <!-- End .content -->
                                                <div class="clear"></div>

                                            </div>
                                            <!-- End .issueSelectionContent -->

                                        </div><!-- End #bankSelection -->

								  </div><!-- End .container2Content -->

                                  <div class="corner bl"></div>
                                  <div class="corner br"></div>

                                </div>
							</div></div></div>
						</div><!-- End .container2 -->


                         <div class="container2 tabs3Container" id="bugRace">

							<div class="issueTabs">
								<div class="issueTabsLeft">
                                	<div class="issueTabRight">
                                    	<div class="issueTabInner">

                                        	<!-- total -->
                                            <div class="total">
                                            	<dl>
                                                	<dt>All Bug Races :</dt>
                                                    <dd><s:property value="viewData.bugRacesNumber"/></dd>
                                                    <dt class="lastOngoing">Ongoing Bug Races :</dt>
                                                    <dd class="lastOngoingNum"><s:property value="viewData.unresolvedBugRacesNumber"/></dd>
                                                </dl>
                                            </div>
                                            <!-- End .total -->

                                            <div class="viewAll">
                                            	<input type="checkbox" class="checkbox" /><label>View All Details</label>
                                            </div>

                                            <a class="lanchProjectBugRaceBtn button6 btnAddNew" href="javascript:;">
                                                <span class="left">
                                                    <span class="right">LAUNCH PROJECT BUG RACE</span>
                                                </span>
                                            </a>

                                        </div>
                                    </div>
                                </div>
							</div><!-- End #issueTabs -->

							<div class="container2Left"><div class="container2Right"><div class="container2BottomClear">
                            	<div class="container2Opt">
									<div class="containerNoPadding">


                                        <div class="issueSelection">
                                        	<div class="bankSelectionHead">
                                            	<div class="viewSort">
                                                	<label>View :</label>
                                                    <select class="select2">
                                                        <option>All Bug Races(<s:property value="viewData.bugRacesNumber"/>)</option>
                                                        <option>Ongoing Bug Races(<s:property value="viewData.unresolvedBugRacesNumber"/>)</option>
                                                        <option>Resolved Bug Races(<s:property value="viewData.resolvedBugRacesNumber"/>)</option></select>
                                                </div>
                                                <ul class="bankSelectionTab">
                                                	<li class="off issueTab"><a href="javascript:;"> <span>Issue (<s:property value="viewData.issuesNumber"/>)</span></a></li>
                                                    <li class="bugRaceTab"><a href="javascript:;"> <span>Bug Race (<s:property value="viewData.bugRacesNumber"/>)</span> </a></li>
                                                </ul><!-- End #bankSelectionTab -->
                                            </div><!-- End #bankSelectionHead -->

                                            <!-- issue selection content -->
                                            <div class="issueSelectionContent">

                                                <!-- content -->
                                                <div class="content">

                                                	<table border="0" cellpadding="0" cellspacing="0" class="issueContest">
                                                    	<colgroup>
                                                        	<col width="170" />
                                                            <col />
                                                        </colgroup>


                                                        <tr <s:if test="viewData.projectBugs.size == 0">style="display:none"</s:if>>
                                                        <td class="contestNameTd">
                                                            <div class="contestNameLink">
                                                                <a href="<s:url action="projectOverview" namespace="/">
                                                                <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a>
                                                            </div>
                                                        </td>
                                                        <td class="directProjectBugs">
                                                            <c:forEach items="${viewData.projectBugs}" var="bugRace" varStatus="contestStatus">
                                                                <!-- row -->
                                                                <div class="rowItem">
                                                                    <input type="hidden" class="contestName" value="${bugRace.issueSummary}"/>
                                                                    <s:iterator value="#attr.bugRace.attachments">
                                                                    <input type="hidden" class="attachmentName" value="<s:property value="filename"/>"/>
                                                                    </s:iterator>
                                                                    <textarea class="hide description"><s:property value="#attr.bugRace.description"/></textarea>
                                                                    <textarea class="hide environment"><s:property value="#attr.bugRace.environment"/></textarea>
                                                                    <input type="hidden" class="prize" value="<fmt:formatNumber value="${bugRace.prize}" pattern="#0"/>"/>
                                                                    <input type="hidden" class="paymentStatus" value="${bugRace.paymentStatus}"/>
                                                                    <input type="hidden" class="tcoPoints" value="${bugRace.TCOPoints}"/>
                                                                    <input type="hidden" class="issueId" value="${bugRace.issueId}"/>
                                                                    <input type="hidden" class="issueCCA" value="${bugRace.cca}"/>
                                                                    <input type="hidden" class="issueType" value="${bugRace.type}"/>
                                                                    <!-- head -->
                                                                    <div class="issueContestHead">
                                                                        <div class="bugContestTitle">
                                                                            <p class="issueLink"><a href='<c:url value="${bugRace.issueLink}"/>' target="_blank"><c:out value="${bugRace.projectName}"/> / <c:out value="${bugRace.issueKey}"/></a></p>
                                                                            <p class="issueName"><a href='<c:url value="${bugRace.issueLink}"/>' target="_blank"><s:property value="#attr.bugRace.issueSummary"/></a></p>
                                                                        </div>
                                                                        <div class="viewAndShow">
                                                                            <a href="javascript:;" class="viewDetails">View Details</a>
                                                                            <a href="javascript:;" class="hideDetails hidden">Hide Details</a>
                                                                        </div>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .issueContestHead -->

                                                                    <!-- short details -->
                                                                    <div class="shortDetails">
                                                                        <dl>
                                                                            <dt>Status : </dt>
                                                                            <dd class="issueStatus"><strong class="${bugRace.issueStatusClass}"><c:out value="${bugRace.statusName}"/></strong></dd>
                                                                            <dt>Created : </dt>
                                                                            <dd class="issueCreationDate"><c:out value="${bugRace.creationDateString}"/></dd>
                                                                        </dl>
                                                                        <a class="button11" href="#">
                                                                            <span class="btnR">
                                                                                <span class="btnC">
                                                                                    <span class="btnIcon">Edit</span>
                                                                                </span>
                                                                            </span>
                                                                        </a>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .shortDetails -->

                                                                    <!-- long details -->
                                                                    <div class="longDetails hidden">

                                                                        <ul>
                                                                            <!-- detail -->
                                                                            <li class="detailList">
                                                                                <h3>Details</h3>
                                                                                <dl>
                                                                                    <dt>Status :</dt>
                                                                                    <dd class="issueStatus"><strong class="${bugRace.issueStatusClass}"><c:out value="${bugRace.statusName}"/></strong></dd>
                                                                                    <dt>Resolution :</dt>
                                                                                    <dd class="issueResolution"><c:out value="${bugRace.resolutionName}"/></dd>
                                                                                    <dt>1st Prize :</dt>
                                                                                    <dd class="issuePrize"><fmt:formatNumber value="${bugRace.prize}" pattern="$###,##0.00"/></dd>
                                                                                    <dt>Votes :</dt>
                                                                                    <dd class="issueVotes"><c:out value="${bugRace.votesNumber}"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End detail -->

                                                                            <!-- people -->
                                                                            <li class="peopleList">
                                                                                <h3>People</h3>
                                                                                <dl>
                                                                                    <dt>Reporter :</dt>
                                                                                    <dd class="issueReporter"><a href="${bugRace.reporterProfile}" target="_blank"><c:out value="${bugRace.reporter}"/></a></dd>
                                                                                    <dt>Assignee :</dt>
                                                                                    <dd class="issueAssignee">
                                                                                        <c:choose>
                                                                                            <c:when test="${bugRace.assignee == 'Unassigned'}">
                                                                                                Unassigned
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                <a href="${bugRace.assigneeProfile}" target="_blank"><c:out value="${bugRace.assignee}"/></a>
                                                                                            </c:otherwise>
                                                                                        </c:choose>

                                                                                    </dd>
                                                                                    <dt>Voters :</dt>
                                                                                    <dd>Not Available Now</dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End people -->

                                                                            <!-- dates -->
                                                                            <li class="datesList">
                                                                                <h3>Dates</h3>
                                                                                <dl>
                                                                                    <dt>Created :</dt>
                                                                                    <dd class="issueCreationDate"><c:out value="${bugRace.creationDateString}"/></dd>
                                                                                    <dt>Updated :</dt>
                                                                                    <dd class="issueUpdateDate"><c:out value="${bugRace.updateDateString}"/></dd>
                                                                                    <dt>Due :</dt>
                                                                                    <dd class="issueDueDate"><c:out value="${bugRace.dueDateString}"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End .dates -->

                                                                            <!-- issue link -->
                                                                            <li class="linkList">
                                                                                <h3>Issue Links</h3>
                                                                                <dl>
                                                                                    <dd><p>This issue is cloned by:</p>
                                                                                        Not Available Now
                                                                                    </dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End issue link -->
                                                                        </ul>

                                                                        <a class="button11" href="#">
                                                                            <span class="btnR">
                                                                                <span class="btnC">
                                                                                    <span class="btnIcon">Edit</span>
                                                                                </span>
                                                                            </span>
                                                                        </a>
                                                                        <div class="clear"></div>

                                                                    </div>
                                                                    <!-- End .longDetails -->
                                                                </div>
                                                            </c:forEach>
                                                        </td>
                                                        </tr>


                                                         <s:iterator value="viewData.projectIssues" id="id" status="contestStatus">

                                                          <s:if test="value.bugRaces.size > 0">

                                                        <tr>
                                                        	<td class="contestNameTd">
                                                                 <div class="contestNameLink">
                                                                <a href="<s:url action='contestIssuesTracking' namespace='/contest'>
                                                        	                <s:param name='projectId' value='key.id'/>
                                                        	                <s:param name='subTab'>bugRaces</s:param>
                                                        	             </s:url>
                                                        	        ">
                                                                    <s:property value="key.title"/>
                                                                </a>
                                                                     </div>
                                                            </td>
                                                            <td class="contestIssuesTd">

                                                              <s:iterator value="value.bugRaces"  status="bugRaceStatus">

                                                            	<!-- row -->
                                                            	<div class="rowItem">

                                                                	<!-- head -->
                                                                	<div class="issueContestHead">
                                                                    	<div class="bugContestTitle">
                                                                        	<p><a href="<s:property value='issueLink'/>" target="_blank"><s:property value="projectName"/> / <s:property value="issueKey"/></a></p>
                                                                            <p class="issueName"><a href="<s:property value='issueLink'/>" target="_blank"><s:property value="issueSummary"/></a></p>
                                                                        </div>
                                                                        <div class="viewAndShow">
                                                                        	<a href="javascript:;" class="viewDetails">View Details</a>
                                                                            <a href="javascript:;" class="hideDetails hidden">Hide Details</a>
                                                                        </div>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .issueContestHead -->

                                                                    <!-- short details -->
                                                                    <div class="shortDetails">
                                                                    	<dl>
                                                                        	<dt>Status : </dt>
                                                                             <dd class="issueStatus"><strong class="<s:property value='issueStatusClass'/>"><s:property value="statusName"/></strong></dd>
                                                                            <dt>Created : </dt>
                                                                            <dd><s:property value="creationDateString"/></dd>
                                                                        </dl>
                                                                        <a class="button11 contestEdit" href="javascript:;" index="<s:property value='#bugRaceStatus.index'/>">
                                                                            <span class="btnR">
                                                                                <span class="btnC">
                                                                                    <span class="btnIcon">Edit</span>
                                                                                </span>
                                                                            </span>
                                                                        </a>
                                                                        <div class="clear"></div>
                                                                    </div>
                                                                    <!-- End .shortDetails -->

                                                                    <!-- long details -->
                                                                    <div class="longDetails hidden">

                                                                    	<ul>
                                                                        	<!-- detail -->
                                                                        	<li class="detailList">
                                                                            	<h3>Details</h3>
                                                                            	<dl>
                                                                                	<dt>Status :</dt>
                                                                                    <dd><strong class="<s:property value='issueStatusClass'/>"><s:property value="statusName"/></strong></dd>
                                                                                    <dt>Resolution :</dt>
                                                                                     <dd><s:property value="resolutionName"/></dd>
                                                                                    <dt>1st Prize :</dt>
                                                                                    <dd><s:property value="getText('{0,number,$###,##0.00}',{prize})"/></dd>
                                                                                    <dt>Votes :</dt>
                                                                                    <dd><s:property value="votesNumber"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End detail -->

                                                                            <!-- people -->
                                                                            <li class="peopleList">
                                                                            	<h3>People</h3>
                                                                            	<dl>
                                                                                	<dt>Reporter :</dt>
                                                                                    <dd><a href="<s:property value='reporterProfile'/>" target="_blank"><s:property value="reporter"/></a></dd>
                                                                                    <dt>Assignee :</dt>
                                                                                    <dd>
                                                                                        <s:if test="assignee == 'Unassigned'">
                                                                                            Unassigned
                                                                                        </s:if>
                                                                                        <s:else>
                                                                                            <a href="<s:property value='assigneeProfile'/>" target="_blank"><s:property value="assignee"/></a>
                                                                                        </s:else>

                                                                                    </dd>
                                                                                    <dt>Voters :</dt>
                                                                                    <dd>Not Available Now</dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End people -->

                                                                            <!-- dates -->
                                                                            <li class="datesList">
                                                                            	<h3>Dates</h3>
                                                                            	<dl>
                                                                                	<dt>Created :</dt>
                                                                                    <dd><s:property value="creationDateString"/></dd>
                                                                                    <dt>Updated :</dt>
                                                                                    <dd><s:property value="updateDateString"/></dd>
                                                                                    <dt>Due :</dt>
                                                                                    <dd><s:property value="dueDateString"/></dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End .dates -->

                                                                            <!-- issue link -->
                                                                            <li class="linkList">
                                                                            	<h3>Issue Links</h3>
                                                                            	<dl>
                                                                                	<dd><p>This issue is cloned by:</p>
                                                                                         Not Available Now
                                                                                    </dd>
                                                                                </dl>
                                                                            </li>
                                                                            <!-- End issue link -->
                                                                        </ul>
                                                                        <a class="button11 contestEdit" href="javascript:;" index="<s:property value='#bugRaceStatus.index'/>">
                                                                            <span class="btnR">
                                                                                <span class="btnC">
                                                                                    <span class="btnIcon">Edit</span>
                                                                                </span>
                                                                            </span>
                                                                        </a>
                                                                        <div class="clear"></div>

                                                                    </div>
                                                                    <!-- End .longDetails -->
                                                                </div>

                                                              </s:iterator>

                                                            </td>
                                                        </tr>

                                                             </s:if>

                                                        </s:iterator>

                                                    </table>

                                                </div>
                                                <!-- End .content -->

                                                <!-- row -->
                                                <div id="rowItemTemplate" class="hide">
                                                    <div>
                                                        <input type="hidden" class="contestName" value=""/>
                                                        <textarea class="hide description"></textarea>
                                                        <textarea class="hide environment"></textarea>
                                                        <input type="hidden" class="prize" value=""/>
                                                        <input type="hidden" class="paymentStatus" value=""/>
                                                        <input type="hidden" class="tcoPoints" value=""/>
                                                        <input type="hidden" class="issueId" value=""/>
                                                        <input type="hidden" class="issueCCA" value="false"/>
                                                        <input type="hidden" class="issueType" value=""/>
                                                        <!-- head -->
                                                        <div class="issueContestHead">
                                                            <div class="bugContestTitle">
                                                                <p class="issueLink"><a href='' target="_blank"></a></p>
                                                                <p class="issueName"><a href='' target="_blank"></a></p>
                                                            </div>
                                                            <div class="viewAndShow">
                                                                <a href="javascript:;" class="viewDetails">View Details</a>
                                                                <a href="javascript:;" class="hideDetails hidden">Hide Details</a>
                                                            </div>
                                                            <div class="clear"></div>
                                                        </div>
                                                        <!-- End .issueContestHead -->

                                                        <!-- short details -->
                                                        <div class="shortDetails">
                                                            <dl>
                                                                <dt>Status : </dt>
                                                                <dd class="issueStatus"><strong></strong></dd>
                                                                <dt>Created : </dt>
                                                                <dd class="issueCreationDate"></dd>
                                                            </dl>
                                                            <a class="button11" href="#">
                                                                    <span class="btnR">
                                                                        <span class="btnC">
                                                                            <span class="btnIcon">Edit</span>
                                                                        </span>
                                                                    </span>
                                                            </a>
                                                            <div class="clear"></div>
                                                        </div>
                                                        <!-- End .shortDetails -->

                                                        <!-- long details -->
                                                        <div class="longDetails hidden">

                                                            <ul>
                                                                <!-- detail -->
                                                                <li class="detailList">
                                                                    <h3>Details</h3>
                                                                    <dl>
                                                                        <dt>Status :</dt>
                                                                        <dd class="issueStatus"><strong></strong></dd>
                                                                        <dt>Resolution :</dt>
                                                                        <dd class="issueResolution"></dd>
                                                                        <dt>1st Prize :</dt>
                                                                        <dd class="issuePrize"></dd>
                                                                        <dt>Votes :</dt>
                                                                        <dd class="issueVotes"></dd>
                                                                    </dl>
                                                                </li>
                                                                <!-- End detail -->

                                                                <!-- people -->
                                                                <li class="peopleList">
                                                                    <h3>People</h3>
                                                                    <dl>
                                                                        <dt>Reporter :</dt>
                                                                        <dd class="issueReporter"><a href="" target="_blank"></a></dd>
                                                                        <dt>Assignee :</dt>
                                                                        <dd class="issueAssignee"></dd>
                                                                        <dt>Voters :</dt>
                                                                        <dd>Not Available Now</dd>
                                                                    </dl>
                                                                </li>
                                                                <!-- End people -->

                                                                <!-- dates -->
                                                                <li class="datesList">
                                                                    <h3>Dates</h3>
                                                                    <dl>
                                                                        <dt>Created :</dt>
                                                                        <dd class="issueCreationDate"></dd>
                                                                        <dt>Updated :</dt>
                                                                        <dd class="issueUpdateDate"></dd>
                                                                        <dt>Due :</dt>
                                                                        <dd class="issueDueDate"></dd>
                                                                    </dl>
                                                                </li>
                                                                <!-- End .dates -->

                                                                <!-- issue link -->
                                                                <li class="linkList">
                                                                    <h3>Issue Links</h3>
                                                                    <dl>
                                                                        <dd><p>This issue is cloned by:</p>
                                                                            Not Available Now
                                                                        </dd>
                                                                    </dl>
                                                                </li>
                                                                <!-- End issue link -->
                                                            </ul>

                                                            <a class="button11" href="#">
                                                                    <span class="btnR">
                                                                        <span class="btnC">
                                                                            <span class="btnIcon">Edit</span>
                                                                        </span>
                                                                    </span>
                                                            </a>

                                                            <div class="clear"></div>

                                                        </div>
                                                        <!-- End .longDetails -->
                                                    </div>
                                                </div>

                                                <!-- add new project bug -->
                                                <div class="inputContainer">
                                                    <input type="hidden" id="attachmentNames" value=""/>
                                                    <form id="bugForm" method="POST">
                                                        <input type="hidden" name="projectId" value="${session.currentSelectDirectProjectID}"/>
                                                        <input type="hidden" id="issueId" name="issue.issueId" value=""/>
                                                        <input type="hidden" id="projectBug" name="issue.projectBug" value="true"/>
                                                        <input type="hidden" id="attachmentIds" name="attachmentIds" value=""/>
                                                        <div class="row">
                                                            <p class="projectText">Fill in the details about the Bug Race you want to launch below. When you click the Activate button, it will publish your Bug Race to the TopCoder Community on the <a href="https://apps.topcoder.com/bugs/browse/BUGR" target="_blank">Active Bug Races</a> page.</p>
                                                        </div>
                                                        <label><em>*Contest Name:</em></label>
                                                        <div class="row">
                                                            <input type="text" class="text largeText summary" id="issueName" name="issue.name"/>
                                                        </div>
                                                        <label>&nbsp;</label>
                                                        <div class="row" id="ccaRow">
                                                            <input type="checkbox" class="ccaRequired" id="cca" name="issue.cca" value="true"/>
                                                            <label for="cca" id="ccaRequiredLabel">CCA required</label>
                                                        </div>
                                                        <label>Environment:</label>
                                                        <div class="row">
                                                            <textarea rows="" cols="" class="textarea" id="environment" name="issue.environment"></textarea>
                                                            <p>For example operating system, software platform and/or hardware specifications (include as appropriate for the issue).</p>
                                                        </div>
                                                        <label>Description:</label>
                                                        <div class="row">
                                                            <textarea rows="12" cols="" class="textarea" id="description" name="issue.description"></textarea>
                                                        </div>
                                                        <label>1st place:</label>
                                                        <div class="row">
                                                            <input type="text" class="text firstPayment" id="firstPayment" name="issue.firstPlacePayment"/>
                                                            <p>Payment amounts in US $ for the issue.</p>
                                                        </div>
                                                        <label><em>*TCO Points:</em></label>
                                                        <div class="row">
                                                            <select class="selectOption" id="tcoPoints" name="issue.tcoPoints">
                                                                <option selected="selected" value="0">0</option>
                                                                <option value="10">10</option>
                                                                <option value="20">20</option>
                                                                <option value="30">30</option>
                                                            </select>
                                                            <p>Select the amount of TCO Mod Dash points that this Bug Race is worth. Bug Races with TCO Points are part of the Mod Dash. Set the amount <br />to 0 (zero) if you do not want to include your Bug Race in the TCO tournament.</p>
                                                        </div>
                                                        <label>Issue Type:</label>
                                                        <div class="row">
                                                            <select id="bugType" name="issue.type">
                                                                <option value="Bug Fix">Bug Fix</option>
                                                                <option value="Studio Bug">Studio Bug</option>
                                                            </select>
                                                            <p class="projectText">Bug Races in Studio Bug type will show up in <a href="https://studio.topcoder.com/?module=ViewActiveBugRaces">Active Studio Bug Race Competitions</a> list.</p>
                                                        </div>
                                                        <div id="existingAtt">
                                                        <label>Existing Attachments:</label>
                                                        <div class="row shiftTop">
                                                        </div>
                                                        </div>

                                                        <label>Add Attachments</label>
                                                        <div class="row shiftTop"></div>

                                                        <div id="divUpload">
                                                            <label><em>File 1:</em></label>
                                                            <div class="row">
                                                                <div class="FileUpload">

                                                                    <input type="file" id="file1" class="BrowserHidden" name="document" onchange="getElementById('txtfile1').value = getElementById('file1').value;"/>

                                                                    <input id="txtfile1" type="text" class="FileField" readonly="readonly"/>

                                                                    <a class="draft button6" href="javascript:;" style="background-position: left top;">
                                                                        <span class="left" style="background-position: left top;">
                                                                        <span class="right" style="background-position: right top;">BROWSE</span>
                                                                        </span>
                                                                    </a>
                                                                </div>

                                                                <a href="javascript:;" class="button6 btnUpload"><span class="left"><span class="right">UPLOAD</span></span></a>
                                                            </div>
                                                            <label><em>File 2:</em></label>
                                                            <div class="row">
                                                                <div class="FileUpload">

                                                                    <input type="file" id="file2" class="BrowserHidden" name="document" onchange="getElementById('txtfile2').value = getElementById('file2').value;"/>

                                                                    <input id="txtfile2" type="text" class="FileField"  readonly="readonly"/>

                                                                    <a class="draft button6" href="javascript:;" style="background-position: left top;">
                                                                        <span class="left" style="background-position: left top;">
                                                                        <span class="right" style="background-position: right top;">BROWSE</span>
                                                                        </span>
                                                                    </a>
                                                                </div>

                                                                <a href="javascript:;" class="button6 btnUpload"><span class="left"><span class="right">UPLOAD</span></span></a>
                                                            </div>
                                                            <label><em>File 3:</em></label>
                                                            <div class="row">
                                                                <div class="FileUpload">

                                                                    <input type="file" size="24" id="file3" class="BrowserHidden" name="document" onchange="getElementById('txtfile3').value = getElementById('file3').value;"/>

                                                                    <input id="txtfile3" type="text" class="FileField"  readonly="readonly"/>

                                                                    <a class="draft button6" href="javascript:;" style="background-position: left top;">
                                                                        <span class="left" style="background-position: left top;">
                                                                        <span class="right" style="background-position: right top;">BROWSE</span>
                                                                        </span>
                                                                    </a>
                                                                </div>

                                                                <a href="javascript:;" class="button6 btnUpload"><span class="left"><span class="right">UPLOAD</span></span></a>
                                                            </div>
                                                        </div>
                                                        <div class="buttonBox">
                                                            <a href="javascript:;" class="button6 btnUpdate"><span class="left"><span class="right">UPDATE</span></span></a>
                                                            <a href="javascript:;" class="button6 btnCreate"><span class="left"><span class="right">ACTIVATE</span></span></a>
                                                            <a href="javascript:;" class="button6 btnCancel"><span class="left"><span class="right">CANCEL</span></span></a>
                                                        </div>
                                                        <div class="clear"></div>
                                                    </form>
                                                </div>
                                                <!-- End add new project bug-->

                                                <div class="clear"></div>

                                            </div>
                                            <!-- End .issueSelectionContent -->

                                        </div><!-- End #bankSelection -->

								  </div><!-- End .container2Content -->

                                  <div class="corner bl"></div>
                                  <div class="corner br"></div>

                                </div>
							</div></div></div>
						</div><!-- End .container2 -->

						</div>
					</div>

                    <div class="clear"></div>

				</div><!-- End #mainContent -->

			</div><!-- End #content -->
		</div><!-- End #container -->
	</div>

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

</body>
<!-- End #page -->

</html>
