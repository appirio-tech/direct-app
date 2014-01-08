<%--
  - Author: winsty, kanakarajank, Ghost_141, jiajizhou86, GreatKevin
  - Version: 1.5
  - Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of VM management in dashboard.
  -
  - Version 1.1 (TC Direct UI Improvement Assembly 1) changes: Solve "all clear buttons in "VM Management" cannot work"
  - Version 1.2 Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
  -             Added span for showing Successful creation of VM/ Successful Deletion of VM
  - Version 1.3 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  -             Update button text to uppercase.
  - Version 1.4 (Release Assembly - TopCoder Direct VM Instances Management) changes:
                Add pre-populated contest id.
  - Version 1.5 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <ui:adminPageType tab="VM Management"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/vmservice.js?v=215290"></script>
    <link href="/css/direct/screen.css?v=214495" rel="stylesheet" type="text/css" />
    <link href="../../css/direct/dashboard.css?v=215352" rel="stylesheet" type="text/css" />
    <jsp:include page="/WEB-INF/includes/filterPanel.jsp"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">VM Management</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <p class="msg">Here you can launch Virtual Machines.</p>

							<span id="launchSuccess" style="color:green" class="success hide"></span>
							
                            <div class="form">
                                <form id="vm-launch-form" name="vm-launch-form" action="javascript:vmService.launch('vm-launch-form');">
									<div id="vmLaunch">
										<div class="col2">
											<div class="line">
												<label for="vm_image_id" >Image</label>
												<select id="vm_image_id" name="vmImageId">
													<c:forEach items="${vmImages}" var="vmImage">
														<option value="${vmImage.id}">${vmImage.awsImageId} (${vmImage.tcName})</option>
													</c:forEach>
												</select>
											</div>	
											<div class="line">
												<label for="contest_id" >Challenge id</label>
												<input type="text" id="contest_id" name="contestId"
                                                       value="<c:if test="${contestId != 0}"><s:property value="contestId"/></c:if>" />
												<span id="contestIdError" style="color:red" class="error hide"></span>
											</div>
											<div class="line">
												<label for="svn_branch" >SVN branch</label>
												<input type="text" id="svn_branch" name="svnBranch" /> 
												<span id="svnBranchError" style="color:red" class="error hide"></span>
											</div>
											<div class="line">
												<label for="tc_handle" >TopCoder handle</label>
												<input type="text" id="tc_handle" name="tcHandle" />
												<span id="tcHandleError" style="color:red" class="error hide"></span>
											</div>	
											<div class="line">
												<label for="vm_contest_type_id" >Challenge type</label>
												 <select id="vm_contest_type_id" name="vmContestTypeId">
													<c:forEach items="${vmContestTypes}" var="vmContestType">
														<option value="${vmContestType.id}">${vmContestType.name}</option>
													</c:forEach>
												</select>
											</div>
                                            <div class="line">
												<label for="vm_usage_id" >Usage</label>
												 <select id="vm_usage_id" name="vmUsageId">
													<c:forEach items="${vmUsages}" var="vmUsage">
														<option value="${vmUsage.id}">${vmUsage.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="line">
												<label for="user_data">Additional User Data</label>
												<textarea rows="5" name="userData" id="user_data"></textarea>
												<div id="userDataError" style="color:red" class="error hide" style="display:inline"></div>

											</div>	
                                    	</div>
										
								<!--		<div class="col3">
											<span>&nbsp;</span>
											
											
											<span>&nbsp;</span>
											
											<a href="javascript:vmService.launch('vm-launch-form');" class="button3">Submit</a>
										</div>  -->
									</div>
									
                                    <span id="generalError" style="color:red" class="error hide"></span>

                                    <div id="loading" style="display:none;"> Sending request.... </div>
                                </form>
								

                            </div>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td>
								<a id="launchVM"  class="button6"><span class="left"><span class="right">SUBMIT</span></span></a>		
							</td></tr></table>
							<!-- End .form -->
							<hr color="black" class="plain" />
                            <p class="msg">Here you can view and destroy Virtual Machines.</p>

							<span id="deleteSuccess" style="color:green" class="success hide"></span>



                            <div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"  style="margin-top:-22px"><tr><td>
 							   	 <a id="refreshBtn" href="javascript:vmService.refresh();" class="button1"><span>Refresh</span></a>    
								</td></tr>
                                </table>

                                <div class='filterPanel' id='VMFilter' style="margin-top:10px">
                                    <div class='filterHead'>
                                        <div class='rightSide'>
                                            <div class='inner'>
                                                <div class='filterText'>
                                                    <a href='javascript:;' class='collapse'><img
                                                            src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                    <span class='title'>Filter</span>
                                                </div>
                                                <div class='searchContainer'>
                                                    <span class='title'>Search</span>

                                                    <div class='filterSearch'>
                                                        <input type='text' class='searchBox'/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--end .filterHead-->
                                    <div class='filterContent'>
                                        <div class='rightSide'>
                                            <div class='inner'>
                                                <div class='column1'>
                                                    <div class='row'>
                                                        <span class='title'>Challenge Name</span>
                                                        <select id='contestNameFilter'>
                                                            <option value='All'>All Challenge Names</option>
                                                        </select>
                                                    </div>
                                                    <div class='row'>
                                                        <span class='title'>Member Handle</span>
                                                        <select id='memberHandleFilter'>
                                                            <option value='All'>All Handles</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <!--end .column1-->
                                                <div class='column3'>
                                                    <div class='row'>
                                                        <span class='title dateLabel'>Creation Date</span>
                                                        <input id='startDateBegin' type='text' readonly="readonly" class='date-pick'/>
                                                        <span class='title toLabel'>To</span>
                                                        <input id='startDateEnd' type='text' readonly="readonly" class='date-pick'/>
                                                    </div>
                                                    <div class='row'>
                                                        <span class='title' style="width:90px">VM Usage</span>
                                                        <select id='vmUsageFilter'>
                                                            <option value='All'>All VM Usages</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <!--end .column3-->
                                            </div>
                                        </div>
                                    </div>
                                    <!--end .filterHead-->
                                    <div class='filterBottom'>
                                        <div class='rightSide'>
                                            <div class='inner'></div>
                                        </div>
                                    </div>
                                    <!--end .filterBottom-->
                                    <div class='collapseBottom hide'>
                                        <div class='rightSide'>
                                            <div class='inner'></div>
                                        </div>
                                    </div>
                                </div>
                                <table border="1px" class="projectStats contests" id="contest_vms">
                                    <thead>
                                        <tr>
                                            <th>Challenge Id</th>
                                            <th>Challenge Name</th>
                                            <th>TC Image Name</th>
                                            <th>Account Name</th>
                                            <th>SVN Branch</th>
                                            <th>TC member handle</th>
                                            <c:if test="${admin}">
                                                <th>Manager Handle</th>
                                            </c:if>
                                            <th>Public IP</th>
                                            <th>Creation Time</th>
					    <th>Usage</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody class="vm_instances_body">
                                        <c:forEach items="${vmInstances}" var="vmInstance">
                                            <tr>
                                                <td>${vmInstance.instance.contestId}</td>
                                                <td>${vmInstance.contestName}</td>
                                                <td>${vmInstance.vmImageTcName}</td>
                                                <td>${vmInstance.accountName}</td>
                                                <td>${vmInstance.instance.svnBranch}</td>
                                                <td>${vmInstance.instance.tcMemberHandle}</td>
                                                <c:if test="${admin}">
                                                    <td>${vmInstance.managerHandle}</td>
                                                </c:if>
                                                <td>${vmInstance.instance.publicIP}</td>
                                                <td><fmt:formatDate value="${vmInstance.instance.creationTime}" pattern="MM/dd/yyyy HH:mm"/></td>
						<td>
						<c:forEach items="${vmUsages}" var="vmUsage">
						    <c:if test="${vmUsage.id == vmInstance.instance.usageId}">
						        ${vmUsage.name}
						    </c:if>
						</c:forEach>
					        </td>
                                                <td class="vm_instance_status">${vmInstance.status}</td>
                                                <td class="vm_instance_action" align="center"><c:if test="${vmInstance.status eq 'RUNNING'}">
													<div class="term1"><div>
													<a href="javascript:void(0)" onclick="javascript:vmService.terminate(${vmInstance.instance.id}, this);" class="button6" style="margin:auto;"><span class="left"><span class="right">Terminate</span></span></a>&nbsp;
													</div></div>
													
												</c:if></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>


                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="/WEB-INF/includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
