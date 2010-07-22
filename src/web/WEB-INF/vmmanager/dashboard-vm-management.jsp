<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <script type="text/javascript" src="/scripts/vmservice.js"></script>
    <link href="/css/screen.css" rel="stylesheet" type="text/css" />
    <link href="../../css/dashboard.css" rel="stylesheet" type="text/css" /> 
</head>

<c:set var="CURRENT_TAB" scope="request" value="VM Management"/>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">VM Management</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <p class="msg">Here you can launch Virtual Machines.</p>
                                                          

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
												<label for="contest_id" >Contest id</label>
												<input type="text" id="contest_id" name="contestId" /> 
												<span id="contestIdError" style="color:red" class="error"></span>
											</div>
											<div class="line">
												<label for="svn_branch" >SVN branch</label>
												<input type="text" id="svn_branch" name="svnBranch" /> 
												<span id="svnBranchError" style="color:red" class="error"></span>
											</div>
											<div class="line">
												<label for="tc_handle" >TopCoder handle</label>
												<input type="text" id="tc_handle" name="tcHandle" />
												<span id="tcHandleError" style="color:red" class="error"></span>
											</div>	
											<div class="line">
												<label for="vm_contest_type_id" >Contest type</label>
												 <select id="vm_contest_type_id" name="vmContestTypeId">
													<c:forEach items="${vmContestTypes}" var="vmContestType">
														<option value="${vmContestType.id}">${vmContestType.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="line">
												<label for="user_data">Additional User Data</label>
												<textarea rows="5" name="userData" id="user_data"></textarea>
												<span id="userDataError" style="color:red" class="error"></span>

											</div>	
                                    	</div>
										
								<!--		<div class="col3">
											<span>&nbsp;</span>
											
											
											<span>&nbsp;</span>
											
											<a href="javascript:vmService.launch('vm-launch-form');" class="button3">Submit</a>
										</div>  -->
									</div>
									
                                    <span id="generalError" style="color:red" class="error"></span>

                                    <div id="loading" style="display:none;"> Sending request.... </div>
                                </form>
								

                            </div>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td>
								<a id="launchVM" href="javascript:vmService.launch('vm-launch-form');" class="button6"><span class="left"><span class="right">Submit</span></span></a>		
							</td></tr></table>
							<!-- End .form -->
							<hr color="black" class="plain" />
                            <p class="msg">Here you can view and destroy Virtual Machines.</p>
							
						    <div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td>
 							   	 <a id="refreshBtn" href="javascript:vmService.refresh();" class="button1"><span>Refresh</span></a>    
								</td></tr></table>    
                                <table border="1px" class="projectStats contests">
                                    <thead>
                                        <tr>
                                            <th>Contest Id</th>
                                            <th>Contest Name</th>
                                            <th>TC Image Name</th>
                                            <th>SVN Branch</th>
                                            <th>TC member handle</th>
                                            <c:if test="${admin}">
                                                <th>Manager Handle</th>
                                            </c:if>
                                            <th>Public IP</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody class="vm_instances_body">
                                        <c:forEach items="${vmInstances}" var="vmInstance">
                                            <tr id="vm_instance_${vmInstance.instance.id}">
                                                <td>${vmInstance.instance.contestId}</td>
                                                <td>${vmInstance.contestName}</td>
                                                <td>${vmInstance.vmImageTcName}</td> 
                                                <td>${vmInstance.instance.svnBranch}</td>
                                                <td>${vmInstance.instance.tcMemberHandle}</td>
                                                <c:if test="${admin}">
                                                    <td>${vmInstance.managerHandle}</td>
                                                </c:if>
                                                <td>${vmInstance.instance.publicIP}</td>
                                                <td class="vm_instance_status">${vmInstance.status}</td>
                                                <td class="vm_instance_action" align="center"><c:if test="${vmInstance.status eq 'RUNNING'}">
													<div class="term1"><div>
													<a href="javascript:vmService.terminate(${vmInstance.instance.id});" class="button6" style="margin:auto;"><span class="left"><span class="right">Terminate</span></span></a>&nbsp;
													</div></div>
													
												</c:if></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <%--template to render ajax responses--%>
                                <table style="visibility:hidden;display:none;">
                                    <tbody id="vm_instance_template">
                                    <tr id="vm_instance_#instance.id#">
                                        <td>#instance.contestId#</td>
                                        <td>#contestName#</td>
                                        <td>#vmImageTcName#</td>
                                        <td>#instance.svnBranch#</td>
                                        <td>#instance.tcMemberHandle#</td>
                                        <c:if test="${admin}">
                                            <td>#managerHandle#</td>
                                        </c:if>
                                        <td>#instance.publicIP#</td>
                                        <td class="vm_instance_status">#status#</td>
                                        <td class="vm_instance_action" align="center"><div class="term1"><div>#action#</div></div></td>
                                    </tr>
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
