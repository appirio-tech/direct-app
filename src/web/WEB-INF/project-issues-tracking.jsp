<%--
  - Author: TCSDEVELOPER
  -
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
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
    <link rel="stylesheet" href="/css/dashboard-view.css?v=212459" media="all" type="text/css" />
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
                                                        	                <s:param name='contestId' value='key.id'/>
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

                                                         <s:iterator value="viewData.projectIssues" id="id" status="contestStatus">

                                                          <s:if test="value.bugRaces.size > 0">

                                                        <tr>
                                                        	<td class="contestNameTd">
                                                                 <div class="contestNameLink">
                                                                <a href="<s:if test='key.software'>

                                                                        <s:url action='contestIssuesTracking' namespace='/contest'>
                                                        	                <s:param name='projectId' value='key.id'/>
                                                        	                <s:param name='subTab'>bugRaces</s:param>
                                                        	             </s:url>
                                                        	        </s:if>
                                                        	        <s:else>
                                                        	          <s:url action='contestIssuesTracking' namespace='/contest'>
                                                        	                <s:param name='contestId' value='key.id'/>
                                                        	                 <s:param name='subTab'>bugRaces</s:param>
                                                        	             </s:url>
                                                        	        </s:else>

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
