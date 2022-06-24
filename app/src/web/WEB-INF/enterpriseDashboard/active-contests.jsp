<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%--
  - Author: GreatKevin, Ghost_141
  -
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Add checkpoint / final submission number to the submission column of active contests table.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Add readonly in data pick element.
  -
  - Version 1.3 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove the uneeded corners in div
  -
  - Version 1.4 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
  - - Update forum link for studio contest
  -
  - Version 1.5 (TopCoder Direct - Update jira issues retrieval to Ajax) @author -jacob- @challenge 30044583
  - - Comment out the jira issues listing jsp code
  -
  - Description: The active contests page of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<fmt:setLocale value="en_US"/>
<ui:dashboardPageType tab="enterprise"/>
<c:set var="CURRENT_SIDEBAR" value="activeContests" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <jsp:include page="../includes/paginationSetup.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/enterpriseDashboard.css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/exporting.js"></script>
    <script type="text/javascript" src="/scripts/enterpriseDashboard.js"></script>

    <jsp:include page="../includes/filterPanel.jsp"/>
</head>

<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">

                <jsp:include page="../includes/header.jsp"/>

				<div id="wholeContent">
					<div id="wholeArea">
						
						<div class="enterpriseOverView">
						
                            <jsp:include page="../includes/enterpriseDashboard/sidebar.jsp"/>
							
							<!-- main section -->
							<div id="mainSection">
								<h2 data-intro="Welcome to your Active Competitions. This page is a real-time view of all your active competitions.  This is the community in action on your project(s)." data-step="1">Active Challenges</h2>
								
								<!-- active contest -->
								<div id="activeContest">
								
                                    <!-- filter panel -->
                                    <div class="filter" data-intro="Use the filter area to narrow down your list of active competitions." data-step="2">
										<div class="filterTitle">
											<a href="javascript:;" class="folder">Filter</a>
											<div class="searchInputArea" data-intro="This search box will narrow your results in real-time as you type. It searches all text fields in the results below." data-step="3">
												<label>Search:</label>
												<div class="inputLeft">
													<div class="inputRight">
														<div class="inputMiddle">
															<input type="text" class="text searchBox" />
															<a href="javascript:;">SEARCH</a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="filterContainer">
											<div class="row">
												<div class="column firstColumn">
													<div>
														<label>Customer:</label>
														<select id='customerNameFilter'>
															<option selected="selected" value="All">All Customers</option>
                                                            <s:iterator value="viewData.userProjects.projectsCustomers" var="customer">
                                                                <s:if test="key != 0">
                                                                    <option value="<s:property value='key'/>"><s:property value='value'/></option>
                                                                </s:if>
                                                            </s:iterator>
															<option value="none">No Customer</option>
                                                        </select>
													</div>
												</div>
												<div class="column secondColumn">
													<div>
														<label>Challenge Type:</label>
														<select id='contestTypeFilter'>
															<option selected="selected" value="All">All Challenge Types</option>
														</select>
													</div>	
												</div>
												<div class="column thirdCokumn">
													<div>
														<label>Start Date:</label>
														<input id='startDateBegin' type='text' readonly="readonly" class="date-pick"/>
														<span class='title toLabel'>To</span>
														<input id='startDateEnd' type='text' readonly="readonly" class="date-pick"/>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="column firstColumn">
													<div>
														<label>Project:</label>
														<select id='projectNameFilter'>
															<option selected="selected" value="All">All Projects</option>
														</select>
													</div>
												</div>
												<div class="column secondColumn">
													<div>
														<label>Challenge Status:</label>
														<select id='contestStatusFilter'>
															<option selected="selected" value="All">All Challenge Status</option>
														</select>
													</div>	
												</div>
												<div class="column thirdCokumn">
													<div>
														<label>End Date:</label>
														<input id='endDateBegin' type='text' readonly="readonly" class="date-pick"/>
														<span class='title toLabel'>To</span>
														<input id='endDateEnd' type='text' readonly="readonly" class="date-pick"/>
													</div>
												</div>
											</div>
										</div>
									</div>
                                    <!-- End .filterPanel -->
									
									<!-- financials section -->
									<div class="sectionContainer activeContestContainer">
									
										<div class="sectionInner">
											
											<div class="dataSection">
												
												<!-- table -->
												<div class="tableData">
													<table border="0" cellspacing="0" cellpadding="0" id="activeContestData" class="paginatedDataTable">
														<colgroup>
															<col width="10%" />
															<col width="18%" />
															<col width="14%" />
															<col width="12%" />
															<col width="12%" />
															<col />
															<col />
															<col />
															<col width="18%" />
															<col width="85" />
														</colgroup>
														<thead>
															<tr data-intro="Your active competitions are in this table. Click the column headers to sort on any column." data-step="4">
																<th>Challenge Type</th>
																<th>Challenge Name</th>
																<th>Project Name</th>
																<th>Start Date</th>
																<th>End Date</th>
																<th>Registrants</th>
																<th>Submissions</th>
																<th>Forums</th>
																<th colspan="2">Status</th>
															</tr>
														</thead>
                                                        <tbody>

                                                            <s:iterator value="viewData.projectContests.contests"
                                                                        status="status">
                                                                <s:set value="contest" var="contest" scope="page"/>
                                                                <s:set value="startTime" var="startTime" scope="page"/>
                                                                <s:set value="endTime" var="endTime" scope="page"/>
                                                                <s:set value="contestType" var="contestType"
                                                                       scope="page"/>
                                                                <tr>

                                                                    <td class="first"><s:property value="contestType.name"/></td>
                                                                    <td>
                                                                        <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                                            <img class="contestTypeIcon" src="/images/<s:property value="contestType.letter"/>_small.png"
                                                                                 alt="<s:property value="contestType.letter"/>"/>

                                                                        </div>
                                                                        <div style="display:table-cell;text-align:left">
                                                                            <link:contestDetails contest="${contest}"
                                                                                    />
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <div style="display:table-cell;text-align:left">
                                                                            <a href="../projectOverview?formData.projectId=${contest.project.id}"><s:property value="contest.project.name"/></a>
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatDate value="${startTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/>
                                                                    </td>

                                                                     <td>
                                                                        <fmt:formatDate value="${endTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/>
                                                                    </td>                                                    
                                                                    <td>
                                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                                            <a href="../copilot/listCopilotContestRegistrants.action?projectId=${contest.id}">
                                                                                <s:property value="registrantsNumber"/>
                                                                            </a>
                                                                        </s:if>
                                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                                            <a href="../contest/contestRegistrants.action?projectId=${contest.id}">
                                                                                <s:property value="registrantsNumber"/>
                                                                            </a>
                                                                        </s:if>
                                                                       
                                                                    </td>
                                                                    <td>
                                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                                            <a href="copilot/listCopilotContestSubmissions.action?projectId=${contest.id}">
                                                                                <s:property value="submissionsNumber"/>
                                                                            </a>
                                                                        </s:if>
                                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                                            <s:if test="isStudio == true">

                                                                                <s:if test="multipleRound">
                                                                                    <link:studioSubmissionsGrid contestId="${contest.id}" checkpointRound="true">C(${checkpointSubmissionNumber})</link:studioSubmissionsGrid>:
                                                                                    <link:studioSubmissionsGrid contestId="${contest.id}" checkpointRound="false">F(${finalSubmissionNumber})</link:studioSubmissionsGrid>
                                                                                </s:if>
                                                                                <s:else>
                                                                                    <link:studioSubmissionsGrid contestId="${contest.id}">
                                                                                        <s:property value="submissionsNumber"/>
                                                                                    </link:studioSubmissionsGrid>
                                                                                </s:else>


                                                                            </s:if>
                                                                            <s:if test="isStudio == false">
                                                                                <s:if test="multipleRound">
                                                                                    <link:softwareSubmissionsList contestId="${contest.id}" checkpointRound="true">C(${checkpointSubmissionNumber})</link:softwareSubmissionsList>:
                                                                                    <link:softwareSubmissionsList contestId="${contest.id}" checkpointRound="false">F(${finalSubmissionNumber})</link:softwareSubmissionsList>
                                                                                </s:if>
                                                                                <s:else>
                                                                                    <link:softwareSubmissionsList contestId="${contest.id}">
                                                                                        <s:property value="submissionsNumber"/>
                                                                                    </link:softwareSubmissionsList>
                                                                                </s:else>
                                                                            </s:if>
                                                                        </s:if>
                                                                    </td>
                                                                    <td>
                                                                    <s:if test="forumId != -1">
                                                                        <s:if test="isStudio == true && !newForum"><a href="https://<%=ServerConfiguration.STUDIO_FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                                        <s:if test="isStudio == true && newForum"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                                        <s:if test="isStudio == false"><a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=Category&categoryID=${forumId}" target="_blank"></s:if>
                                                                    </s:if>
                                                                    <s:property value="forumPostsNumber"/>
                                                                    <s:if test="forumId != -1"></a></s:if>
                                                                    </td>
                                                                    <td class="contestStatus">
                                                                        <span
                                                                                class="<s:property value="status.shortName"/>"><s:property
                                                                                value="status.name"/><s:if
                                                                                test="statusLate"><font style="color: red">&nbsp;(Late)</font></s:if></span>

                                                                        <s:if test="status2 != null">
                                                                        <span
                                                                                class="<s:property value="status2.shortName"/>"><s:property
                                                                                value="status2.name"/><s:if test="status2Late"><font style="color: red">&nbsp;(Late)</font></s:if></span>
                                                                        </s:if>
                                                                        <s:if test="status3 != null">
                                                                            <span
                                                                                    class="<s:property value="status3.shortName"/>"><s:property
                                                                                    value="status3.name"/><s:if test="status3Late"><font style="color: red">&nbsp;(Late)</font></s:if></span>
                                                                        </s:if>
                                                                    </td>
                                                                    <td class="last">
                                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                                            <a href="../copilot/copilotContestDetails?projectId=${contest.id}" class="short" style="text-align: center"><img alt="" src="/images/edit-icon.png">View/Edit</a>
                                                                        </s:if>
                                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                                            <a href="../contest/detail?projectId=${contest.id}" class="short" style="text-align: center;display: inline-block"><img alt="" src="/images/edit-icon.png">View/Edit</a>
                                                                        </s:if>
                                                                    </td>
                                                                    <td class="hide"><span><s:property value="contest.customerId"/></span></td>
                                                                </tr>
                                                            </s:iterator>

                                                            <%--<s:iterator value="viewData.projectBugRaces"--%>
                                                                        <%--status="status">--%>
                                                                <%--<tr>--%>

                                                                    <%--<td class="first">Race</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">--%>
                                                                            <%--<img class="contestTypeIcon" src="/images/br_small.png"--%>
                                                                                 <%--alt="BR"/>--%>

                                                                        <%--</div>--%>
                                                                        <%--<div style="display:table-cell;text-align:left">--%>
                                                                            <%--<a href="<s:property value='issueLink'/>"><s:property value='issueKey'/> <s:property value='title'/></a>--%>
                                                                        <%--</div>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<div style="display:table-cell;text-align:left">--%>
                                                                            <%--<a href="../projectOverview?formData.projectId=${directProjectId}"><s:property value="directProjectName"/></a>--%>
                                                                        <%--</div>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${creationDate}"/> ET (GMT-400)--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${endDate}"/> ET (GMT-400)--%>
                                                                    <%--</td>--%>

                                                                    <%--<td>--%>
                                                                        <%--<a href="<s:property value='issueLink'/>">--%>
                                                                            <%--<s:property value="votesNumber"/>--%>
                                                                        <%--</a></td>--%>
                                                                    <%--<td>--%>
                                                                        <%--n/a--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<a href="<s:property value='issueLink'/>">0</a>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td class="contestStatus"><span--%>
                                                                            <%--class="<s:property value="contestLikeStatusClass"/>"><s:property--%>
                                                                            <%--value="contestLikeStatus"/></span></td>--%>
                                                                    <%--<td class="last">--%>
                                                                        <%--<a href="<s:property value='issueLink'/>" class="short" style="text-align: center;display: inline-block"><img alt="" src="/images/edit-icon.png">View/Edit</a>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td class="hide"><span><s:if test="clientId <= 0">none</s:if><s:else><s:property value='clientId'/></s:else></span></td>--%>
                                                                <%--</tr>--%>
                                                            <%--</s:iterator>--%>

                                                        </tbody>
                                                        
													</table>
													<!-- End .pagePanel -->
													
												</div>
												<!-- End .tableData -->
												
											</div>

										</div>
									</div>
									<!-- End .financialsSection -->
									
								</div>
								<!-- End #activeContest -->
								
							</div>
							<!-- End #mainSection -->
							
						</div>
						
					</div>
				</div>

                <jsp:include page="../includes/footer.jsp"/>
                
			
			</div>
			<!-- End #content -->
		</div>
		<!-- End #container -->
	</div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->
</html>

