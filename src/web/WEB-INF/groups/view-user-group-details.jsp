<%--
  - Author: backstreetlili, freegod, GreatKevin
  - Version: 1.6
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (Release Assembly - TopCoder Security Groups Frontend - View Group Details) changes:
  - Initialized the page functions.
  - Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Create Update Group) changes:
  - - Add link to the "Update Group" button.
  - Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) changes:
  - - Added logic to the "DELETE GROUP" click event.
  - - Added link to the "Back to Groups" button.
  -
  - Version 1.3 (Release Assembly - TopCoder Security Groups Release 3) changes:
  - - updated to fixed the bugs in this assembly.
  - Version 1.4 (Release Assembly - TopCoder Security Groups Release 4) changes:
  - - Add "Status" column.
  -
  - Version 1.5 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
  - - Remove Resource Restrictions part and add Automatically Grant Permissions
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
    <link rel="stylesheet" href="/css/direct/layout.css?v=212459" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/layout-groups.css?v=210792" media="all" type="text/css" />

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/group.js"></script>
</head>

<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
			
				<jsp:include page="../includes/header.jsp"/>
				<input type="hidden" id="groupDetailsPage" value="true"/>
				<!-- End #header -->
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupDetailsTitle">View Group - <c:out value="${group.name}" /></h2>
                            <s:url var="viewUserGroupsUrl" action="viewUserGroupsAction" namespace="/group">
                              <s:param name="criteria.permissions" value="{'REPORT', 'READ','WRITE','FULL'}"/>
                            </s:url>
							<a class="button7 newButtonBlue" href="${viewUserGroupsUrl}"><span class="left">BACK TO GROUPS</span></a>
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
										<col width="25%" />
										<col width="25%" />
										<col width="25%" />
										<col width="25%" />
									</colgroup>
									<tbody>
										<tr class="firstRow">
											<td class="firstColumn">Group Name:</td>
											<td><c:out value="${group.name}" /></td>
											<td class="firstColumn">Access Rights:</td>
											<td>
											    <c:if test="${group.defaultPermission eq 'FULL'}">Full</c:if>
                                                <c:if test="${group.defaultPermission eq 'REPORT'}">Report</c:if>
                                                <c:if test="${group.defaultPermission eq 'READ'}">Read</c:if>
                                                <c:if test="${group.defaultPermission eq 'WRITE'}">Write</c:if>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Customer:</td>
											<td><c:out value="${group.client.name}" /></td>
											<td class="firstColumn">Billing Accounts:</td>
											<td>
											    <s:iterator value="group.billingAccounts" id="billingAccount" status="status">
											    <s:if test="#status.First">
                                                    <s:property value="name"/>
                                                </s:if>
											    <s:if test="!#status.First">
                                                    ,&nbsp;<s:property value="name"/>
                                                </s:if>
											    </s:iterator>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Projects:</td>
											<td>
											    <s:iterator value="group.directProjects" id="directProject" status="status">
                                                <s:if test="#status.First">
                                                    <s:property value="name"/>
                                                </s:if>
                                                <s:if test="!#status.First">
                                                    ,&nbsp;<s:property value="name"/>
                                                </s:if>
                                                </s:iterator>
											</td>
											<td class="firstColumn">Automatically Grant Permissions:</td>
											<td>
											    <s:if test="group.autoGrant">Yes</s:if>
                                                <s:if test="%{null == group.autoGrant || !group.autoGrant}">No</s:if>
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
								<table cellpadding="0" cellspacing="0" class="normalTableList detailsGroupTable">
									<colgroup>
										<col width="35%" />
										<col width="35%" />
                                        <col width="30%" />
									</colgroup>
									<thead>
										<tr>
											<th>Handle</th>
											<th>Access Rights</th>
                                            <th>Status</th>
										</tr>
									</thead>
									<tbody>
									    <s:iterator value="group.groupMembers" id="groupMember" status="status">
                                        <s:if test="#groupMember.getUnassignedOn() == null">
										<tr>
											<td><s:property value="handle"/></td>
											<td>
											    <s:if test="#groupMember.isUseGroupDefault()">
											        <c:if test="${group.defaultPermission eq 'FULL'}">Full</c:if>
                                                    <c:if test="${group.defaultPermission eq 'REPORT'}">Report</c:if>
                                                    <c:if test="${group.defaultPermission eq 'READ'}">Read</c:if>
                                                    <c:if test="${group.defaultPermission eq 'WRITE'}">Write</c:if>
                                                </s:if>
                                                <s:if test="!#groupMember.isUseGroupDefault()">
                                                    <s:if test="#groupMember.specificPermission.toString() == 'FULL'">Full</s:if>
                                                    <s:if test="#groupMember.specificPermission.toString() == 'REPORT'">Report</s:if>
                                                    <s:if test="#groupMember.specificPermission.toString() == 'READ'">Read</s:if>
                                                    <s:if test="#groupMember.specificPermission.toString() == 'WRITE'">Write</s:if>
                                                </s:if>
											</td>
                                            <td>
                                                ${fn:replace(groupInvitations[groupMember.userId].status,'_',' ')}
                                            </td>
										</tr>
                                        </s:if>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<div class="groupDetailsButton">
                                <c:if test="${not groupFullPermission}">
								<a class="newButton1 newButtonGreen newButtonGray triggerModal deleteGroupButton" href="javascript:;"><span class="btnR"><span class="btnC">DELETE GROUP</span></span></a>
								<a class="newButton1 newButtonGreen updateGroupButton" href="<s:url action="enterUpdateGroup" namespace="/group"><s:param name="groupId">${group.id}</s:param></s:url>"><span class="btnR"><span class="btnC">EDIT GROUP</span></span></a>
                                </c:if>
							</div>
							
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
<input type="hidden" id="groupIdInput" value="${group.id}"/>
<input type="hidden" id="groupNameInput" value="${group.name}"/>
<%@ include file="delete-group-dialogs.jsp" %>
</body>
<!-- End #page -->
</html>
