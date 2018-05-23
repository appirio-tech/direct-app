<%--
  - Author: freegod, caru, suno1234, GreatKevin
  - Version: 1.6
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the create/update security group page.
  -
  - Changes:
  - Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) changes:
  -   updated to support the "Skip invitation process for newly added members" option.
  -   updated to use the common search-user-dialogs.jsp.
  -   updated the "Back to Groups" link.
  -
  - Changes:
  - Version 1.2 (Release Assembly - TopCoder Security Groups Release 3) changes:
  -   updated to fixed the bugs in this assembly.
  -
  - Version 1.3 updates (Release Assembly - TopCoder Security Groups - Release 4)
  - - Add "Status" column.
  - - Change the link of "Cancel" button.
  -
  - Version 1.4 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
  - - Remove Resource Restrictions and add Automatically Grant Permissions
  -
  - Version 1.4.1
  - - "Skip Invitation" checkbox checked by default
  -
  - Version 1.5 (48hr Cockpit Group Management Improvement Release Assembly) change notes:
  - - Add a hide input to the member data row to store the userId.
  -
  - Version 1.6 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
  - - Reskin the group pages
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="PAGE_TYPE" value="group" scope="request"/>
    <c:set var="CURRENT_TAB" value="detail" scope="request"/>
    
    <jsp:include page="../includes/htmlhead.jsp"/>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/layout.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/layout-groups.css" media="all" type="text/css" />

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/group.js"></script>
</head>

<c:set var="PAGE_TYPE" value="group" scope="request"/>
<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
			
				<jsp:include page="../includes/header.jsp"/>
				
				<!-- End #header -->
                <!-- End #header -->
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
                            <c:if test="${not empty group}">
                                <h2 class="updateGroupTitle">Edit Group - <s:property value="#attr.group.name"/></h2>
                            </c:if>
                            <c:if test="${empty group}">
                                <h2 class="createGroupsTitle">Create New Group</h2>
                                <a class="button7 newButtonBlue" href="${viewUserGroupsUrl}"><span class="left">BACK TO GROUPS</span></a>
                            </c:if>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<div class="groupDetailsTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Details</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupDetailsTableContainer">
								<table cellpadding="0" cellspacing="0">
									<colgroup>
										<col width="50%" />
										<col width="50%" />
									</colgroup>
									<tbody>
										<tr class="firstRow" id="groupNameFieldRow">
											<td class="firstColumn">Group Name:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>
											<td>
												<input type="text" class="text" id="groupName" id="inputCreateGroupName" value="<s:property value="#attr.group.name"/>"/>
										    </td>											
										</tr>
										<tr id="accessRightsFieldRow">
											<td class="firstColumn">Group Access Rights:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>											
											<td>
												<div class="attributesWrapper">
                                                    <input type="radio" id="reportRadio" name="defaultPerm" value="REPORT" <c:if test="${group.defaultPermission eq 'REPORT'}">checked="checked"</c:if>  autocomplete="off"/>
													<label for="reportRadio">Report</label>
													<input type="radio" id="readRadio" <c:if test="${empty group or group.defaultPermission eq 'READ'}">checked="checked"</c:if> name="defaultPerm" value="READ" autocomplete="off"/>
													<label for="readRadio">Read</label>
													<input type="radio" id="writeRadio" name="defaultPerm" value="WRITE" <c:if test="${group.defaultPermission eq 'WRITE'}">checked="checked"</c:if> autocomplete="off"/>
													<label for="writeRadio">Write</label>
													<input type="radio" id="fullRadio" name="defaultPerm" value="FULL" <c:if test="${group.defaultPermission eq 'FULL'}">checked="checked"</c:if> autocomplete="off"/>
													<label for="fullRadio">Full</label>
												</div>
											</td>
										</tr>
										<tr id="customerNameFieldRow">
											<td class="firstColumn">Customer Name:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>											
											<td>
												<select id="selectCreateCustomerName" autocomplete="off">
                                                    <c:forEach items="${clients}" var="client" varStatus="vs">
                                                        <option value="${client.id}" <c:if test="${selectedClientId eq client.id}">selected="selected"</c:if>>${client.name}</option>
                                                    </c:forEach>
												</select>
											</td>
										</tr>
										<tr id="billingAccountsFieldRow">
											<td class="firstColumn">Billing Accounts:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>											
											<td>
												<div class="multiSelectBox" id="billingAccounts">
													<div class="multiOptionRow">
                                                        <c:choose>
                                                            <c:when test="${fn:length(accounts) eq 0}">
                                                                <div>No billing account available</div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" id="multiSelect01CheckBox00" autocomplete="off"/>
														        <label for="multiSelect01CheckBox00">Select All</label>
                                                            </c:otherwise>
                                                        </c:choose>
													</div>
                                                    <c:forEach items="${accounts}" var="account" varStatus="vs">
													<div class="multiOptionRow">
                                                        <c:set var="isC" value="${false}"/>
                                                        <c:forEach items="${group.billingAccounts}" var="ba">
                                                            <c:if test="${ba.id eq account.id}">
                                                                <c:set var="isC" value="${true}"/>
                                                            </c:if>
                                                        </c:forEach>
														<input type="checkbox" value="${account.id}" id="multiSelect01CheckBox${vs.count}" name="accounts" <c:if test="${isC}">checked="checked"</c:if> autocomplete="off"/>
														<label for="multiSelect01CheckBox${vs.count}">${account.name}</label>
													</div>
                                                    </c:forEach>
												</div>
											</td>
										</tr>
										<tr id="projectsFieldRow">
											<td class="firstColumn">Projects:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>											
											<td>
												<div class="multiSelectBox" id="directProjects">
													<div class="multiOptionRow">
                                                        <c:choose>
                                                            <c:when test="${fn:length(directProjects) eq 0}">
                                                                <div>No project available</div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" id="multiSelect02CheckBox00" autocomplete="off"/>
														        <label for="multiSelect02CheckBox00">Select All</label>
                                                            </c:otherwise>
                                                        </c:choose>
													</div>
                                                    <c:forEach items="${directProjects}" var="project" varStatus="vs">
													<div class="multiOptionRow">
                                                        <c:set var="isC" value="${false}"/>
                                                        <c:forEach items="${group.directProjects}" var="dp">
                                                            <c:if test="${dp.directProjectId eq project.projectId}">
                                                                <c:set var="isC" value="${true}"/>
                                                            </c:if>
                                                        </c:forEach>
														<input type="checkbox" value="${project.projectId}" id="multiSelect02CheckBox${vs.count}" name="projects" <c:if test="${isC}">checked="checked"</c:if> autocomplete="off"/>
														<label for="multiSelect02CheckBox${vs.count}">${project.name}</label>
													</div>
                                                    </c:forEach>
												</div>
											</td>
										</tr>
										<tr id="autoGrantPermissionRow">
											<td class="firstColumn">Automatically Grant Permissions:
												<a class="helpme" href="javascript:;"><img alt="help" src="/images/help_ico5.gif"></a>
											</td>
											<td>
												<div class="restrictionsWrapper">
                                                    <c:set var="isAuto" value="${false}"/>
                                                    <c:if test="${group.autoGrant}">
                                                        <c:set var="isAuto" value="${true}"/>
                                                    </c:if>
													<input type="checkbox" id="autoGrantCheck" autocomplete="off" <c:if test="${isAuto}">checked="checked"</c:if>/>
													<label for="autoGrantCheck">All billings and projects for this customer</label>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						<div class="groupMembersTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Members</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupMembersTableContainer">
								<table cellpadding="0" cellspacing="0" id="groupMemberTable">
									<colgroup>
										<col width="25%" />
										<col width="25%" />
										<col width="25%" />
                                        <c:set var="renderStatus" value="${not empty group and fn:length(group.groupMembers) gt 0}"/>
                                        <c:if test="${renderStatus}">
                                        <col width="12%" />
                                        </c:if>
										<col width="13%" />
									</colgroup>
									<thead>
										<tr>
											<th>Member Handle</th>
											<th>Group Member Authorization Attributes</th>
											<th>Access Rights</th>
                                            <c:if test="${renderStatus}">
                                            <th>Status</th>
                                            </c:if>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
                                        <c:set var="currentId" value="${1}"/>
                                        <c:forEach items="${group.groupMembers}" var="member">
                                            <c:if test="${empty member.unassignedOn}">
                                            <tr>
                                                <td class="firstColumn">
                                                    <input type="hidden" class="userId" value="<s:property value="#attr.member.userId"/>"/>
                                                    <input type="text" class="text" value="<s:property value="#attr.member.handle"/>"/>
                                                    <a href="javascript:;" class="searchDetails searchUser triggerModal" rel="#searchModal">Search</a>
                                                </td>
                                                <td class="secondColumn">
                                                    <div class="leftPart">
                                                        <input type="radio" name="radioGroupMembers${currentId}" value="Group Default" id="gruopRadio${currentId}" checked="checked" autocomplete="off" <c:if test="${member.useGroupDefault}">checked="checked"</c:if>/>
                                                        <label for="gruopRadio${currentId}">Group Default</label>
                                                    </div>
                                                    <div class="rightPart">
                                                        <input type="radio" name="radioGroupMembers${currentId}" value="User Specific" id="userRadio${currentId}" autocomplete="off" <c:if test="${not member.useGroupDefault}">checked="checked"</c:if>/>
                                                        <label for="userRadio${currentId}">User Specific</label>
                                                    </div>
                                                </td>
                                                <td class="thirdColumn">
                                                    <div class="optionWrapper">
                                                    <div class="unit">
                                                        <input type="radio" <c:if test="${member.useGroupDefault}">disabled="disabled"</c:if> id="reportRadio${currentId}" value="REPORT" name="accesslevel${currentId}" autocomplete="off" <c:if test="${(not member.useGroupDefault) and member.specificPermission eq 'REPORT'}">checked="checked"</c:if> />
                                                        <label for="reportRadio${currentId}">Report</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" <c:if test="${member.useGroupDefault}">disabled="disabled"</c:if> id="readRadio${currentId}" <c:if test="${member.useGroupDefault or member.specificPermission eq 'READ'}">checked="checked"</c:if> value="READ" name="accesslevel${currentId}" autocomplete="off"/>
                                                        <label for="readRadio${currentId}">Read</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" <c:if test="${member.useGroupDefault}">disabled="disabled"</c:if> id="writeRadio${currentId}"  value="WRITE" name="accesslevel${currentId}" autocomplete="off" <c:if test="${(not member.useGroupDefault) and member.specificPermission eq 'WRITE'}">checked="checked"</c:if>/>
                                                        <label for="writeRadio${currentId}">Write</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" <c:if test="${member.useGroupDefault}">disabled="disabled"</c:if> id="fullRadio${currentId}" value="FULL" name="accesslevel${currentId}" autocomplete="off" <c:if test="${(not member.useGroupDefault) and member.specificPermission eq 'FULL'}">checked="checked"</c:if>/>
                                                        <label for="fullRadio${currentId}">Full</label>
                                                    </div>
                                                    <div style="clear:both;"></div>
                                                    </div>
                                                </td>
                                                <td class="forthColumn"> ${fn:replace(groupInvitations[member.userId].status,'_',' ')}
                                                </td>
                                                <td class="forthColumn">
                                                    <a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
                                                </td>
                                            </tr>
                                            <c:set var="currentId" value="${currentId + 1}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:set var="emptyRows" value="${5 - (currentId - 1) % 5}"/>
                                        <c:forEach begin="${1}" end="${emptyRows}" var="ind">
                                            <tr>
                                                <td class="firstColumn">
                                                    <input type="hidden" class="userId" value="0"/>
                                                    <input type="text" class="text" />
                                                    <a href="javascript:;" class="searchDetails searchUser triggerModal" rel="#searchModal">Search</a>
                                                </td>
                                                <td class="secondColumn">
                                                    <div class="leftPart">
                                                        <input type="radio" name="radioGroupMembers${currentId}" value="Group Default" id="gruopRadio${currentId}" checked="checked" autocomplete="off"/>
                                                        <label for="gruopRadio${currentId}">Group Default</label>
                                                    </div>
                                                    <div class="rightPart">
                                                        <input type="radio" name="radioGroupMembers${currentId}" value="User Specific" id="userRadio${currentId}" autocomplete="off"/>
                                                        <label for="userRadio${currentId}">User Specific</label>
                                                    </div>
                                                </td>
                                                <td class="thirdColumn">
                                                    <div class="optionWrapper">
                                                    <div class="unit">
                                                        <input type="radio" disabled="disabled" id="reportRadio${currentId}"  value="REPORT" name="accesslevel${currentId}" autocomplete="off"/>
                                                        <label for="reportRadio${currentId}">Report</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" disabled="disabled" id="readRadio${currentId}" checked="checked" value="READ" name="accesslevel${currentId}" autocomplete="off"/>
                                                        <label for="readRadio${currentId}">Read</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" disabled="disabled" id="writeRadio${currentId}"  value="WRITE" name="accesslevel${currentId}" autocomplete="off"/>
                                                        <label for="writeRadio${currentId}">Write</label>
                                                    </div>
                                                    <div class="unit">
                                                        <input type="radio" disabled="disabled" id="fullRadio${currentId}" value="FULL" name="accesslevel${currentId}" autocomplete="off"/>
                                                        <label for="fullRadio${currentId}">Full</label>
                                                    </div>
                                                    <div style="clear:both;"></div>
                                                    </div>
                                                </td>
                                                <c:if test="${renderStatus}">
                                                <td class="forthColumn"></td>
                                                </c:if>
                                                <td class="forthColumn">
                                                    <a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a>
                                                </td>
                                            </tr>
                                            <c:set var="currentId" value="${currentId + 1}"/>
                                        </c:forEach>
									</tbody>
								</table>
							</div>
							<div class="groupMembersTableControl">
								<div class="groupMembersTableControlNotice">Enter the handle if the member is already a registered member.</div>
								<a href="javascript:;" class="newButton2 addMoreMembersButton" id="addGroupMemberButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Add More Members</span></span></span></a>
							</div>
							
						</div>
						
						<div class="commandOperationBox">
						    <input type="checkbox" id="skipInvitationEmail" class="skipInvitationEmail" autocomplete="off" checked="checked"/>
						    <label for="skipInvitationEmail" class="skipInvitationEmail">Skip invitation process for newly added members</label>
                            <c:if test="${empty group}">
							<a id="createGroup" href="javascript:;" class="newButton1 newButtonGreen triggerModal" rel="#createGroupConfirmModal"><span class="btnR"><span class="btnC">CREATE GROUP</span></span></a>
                            </c:if>
                            <c:if test="${not empty group}">
                            <a id="updateGroup" href="javascript:;" class="newButton1 newButtonGreen triggerModal" rel="#createGroupConfirmModal"><span class="btnR"><span class="btnC">SAVE GROUP</span></span></a>
                            <input type="hidden" id="groupId" value="${group.id}"/>
                            </c:if>
                            
                            <c:if test="${not empty group}">
                            <s:url var="backUrl" action="viewGroupAction" namespace="/group">
                                <s:param name="groupId" value="groupId"/>
                            </s:url>
                            </c:if>
                            
                            <c:if test="${empty group}">
                            <s:url var="backUrl" action="viewUserGroupsAction" namespace="/group">
                                <s:param name="criteria.permissions" value="new java.lang.String[]{'REPORT','READ','WRITE','FULL'}"/>
                            </s:url>
                            </c:if>
							<a href="${backUrl}" class="newButton1 newButtonOrange newButtonGray"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
						</div>
						
					</div>
				</div>
                <!-- End #mainContent -->
				<jsp:include page="../includes/footer.jsp"/>
			
			</div>
			<!-- End #content -->
		</div>
		<!-- End #container -->
	</div>
</div>
<!-- End #wrapper -->

<div id="modalBackground"></div>
<div id="new-modal">
    <%@ include file="search-user-dialogs.jsp" %>
	
	<!-- #createGroupConfirmModal -->
	<div id="createGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Group Created
					<a href="javascript:;" class="closeModal gotoGroupDetail" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="question" /></div>
			<div class="noticeContent">"<span class="confirmGroupName"></span>" Group has been successfully created for <span id="confirmCustomName"></span>. <span class="emailMessage">Invitation emails have been sent to the members added to the group.</span></div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 newButtonBlue closeModal gotoGroupDetail"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #createGroupConfirmModal -->
    
    <!-- #updateGroupConfirmModal -->
	<div id="updateGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Group Details Updated
					<a href="javascript:;" class="closeModal gotoGroupDetail" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="question" /></div>
			<div class="noticeContent">"<span class="confirmGroupName"></span>" Group details have been successfully updated. <span class="emailMessage">Invitation emails have been sent to the members added to the group.</span></div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 newButtonBlue closeModal gotoGroupDetail"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #updateGroupConfirmModal -->
    
    <!-- #noChangeConfirmModal -->
	<div id="noChangeConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Group Details No Change
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="question" /></div>
			<div class="noticeContent">"<span id="noChangeGroupName"></span>" Group details haven't been changed.</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 newButtonBlue closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #noChangeConfirmModal -->
</div>
<!-- end modal -->

<!-- tooltips -->
<%@ include file="tooltip.jsp" %>

</body>
<!-- End #page -->
</html>
