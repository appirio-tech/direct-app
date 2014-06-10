<%--
  - Author: GreatKevin
  - Version: 1.3
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the pending approvals page.
  -
  - Changes:
  - Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) changes:
  -   updated to use the refactored pagination.jsp.
  - Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) changes:
  -   updated to use the common search-user-dialogs.jsp.
  -
  - Version 1.2.2 updates (Release Assembly - TopCoder Security Groups - Release 4)
  - - Change date picker input box editable.
  -
  - Version 1.3 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
  - - Reskin the group pages
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="PAGE_TYPE" value="group" scope="request"/>
    <c:set var="CURRENT_TAB" value="approvals" scope="request"/>
    
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

				<!-- End #header -->
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="pendingApprovalTitle">Pending Approvals</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<!-- .topFilterBox --> 
						<div class="topFilterBox">
							
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<a href="javascript:;" class="expanded"></a>
										<h4>Search</h4>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<!-- .filterContainer -->
							<div class="filterContainer">
								<dl class="filterUserGroup">
									<dd>
										<div class="leftSide">
											<span class="label">Invitee Handle:</span>
											<input type="text" class="text searchText" id="inviteeHandle"/>
											<a href="javascript:;" class="searchUser searchDetails triggerModal" rel="#searchModal">Search</a>
										</div>
										
										<div class="rightSide">
											<span class="label">Invitee Email Address:</span>
											<input type="text" class="text compareText" id="inviteeEmail"/>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Customer Name:</span>
											<s:select id="clientIdSelect" name="clientId" headerKey="0" headerValue="--"
											        list="clients" listKey="id" listValue="name"
											        cssClass="selectCustomer"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Request Date between:</span>
											<div class="secondColumn specialColumn">
												<div class="fLeft">
													<input type="text" name="formData.startDate"  id="startDateReceived" class="text date-pick dp-applied" />
												</div>
												<div class="fRight">
													<input type="text" name="formData.endDate"  id="endDateReceived" class="text date-pick dp-applied" />
												</div>
												and
												<div class="clearFixed"></div>
											</div>
										</div>
										
										<div class="clearFixed"></div>
									</dd>
								</dl>
								<div class="filterContainerCommand2">
									<a class="button6" id="filterViewInvitations" href="javascript:;"><span class="left"><span class="right">SEARCH</span></span></a>
									<div class="clearFixed"></div>
								</div>
							</div>
							<!-- End .filterContainer -->
							<input type="hidden" id="approvalPage" value="1"/>
						</div>
						<!-- End .topFolderBox -->
						
                        <div class="noInvitation <s:if test='0 < invitations.values.size()'>hide</s:if>">No Pending Approvals Found!</div>
						<div class="mainContent <s:if test='0 == invitations.values.size()'>hide</s:if>">
							
							<div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList" id="approvalsTbl">
									<colgroup>
										<col width="4%" />
										<col width="24%" />
										<col width="24%" />
										<col width="24%" />
										<col width="24%" />
										<col width="0" />
									</colgroup>
									<thead>
										<tr>
											<th></th>
											<th>Handle</th>
											<th>Email Address</th>
											<th>Request Date</th>
											<th>Customer Name</th>
											<th class="hide"></th>
										</tr>
									</thead>
									<tbody>
									    <s:iterator value="invitations.values" var="invitation" status="st">
										<tr <s:if test="#st.first">class="firstRow"</s:if>>
											<td><input type="checkbox"/></td>
											<td><s:property value="users[#st.index].handle"/></td>
											<td><s:property value="users[#st.index].emailAddress"/></td>
											<td><fmt:formatDate var="dt" pattern="MM-dd-yyyy hh:mm a" value="${sentOn}" />
											    ${dt}</td>
											<td><s:property value="groupMember.group.client.name"/></td>
											<td class="hide"><s:property value="id"/></td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<s:set var="pagedResult" value="invitations"/>
							<%@ include file="pagination.jsp" %>						
						</div>
						
						<!-- approval button -->
						<div class="approvalButton <s:if test='0 == invitations.values.size()'>hide</s:if>">
							<a class="button6 grayButton6 triggerModal" href="javascript:;" rel="#rejectModal"><span class="left"><span class="right">REJECT</span></span></a>
							<a class="button6 approveButton triggerModal" href="javascript:;" rel="#approveModal"><span class="left"><span class="right">APPROVE</span></span></a>
						</div>
						<!-- End .approvalButton -->
						
					</div>
				</div>
				<jsp:include page="../includes/footer.jsp"/>
    		</div>
		</div>
    </div>
</div>

<div id="modalBackground"></div>
<div id="new-modal">

	<!-- #rejectModal -->
	<div id="rejectModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Reject Pending Request
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="rejectTextArea">
				<p>Please enter a reason for rejecting the requests.</p>
				<textarea rows="" cols="" class="textarea"></textarea>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 newButtonBlue triggerNoPreloaderModal" rel="#rejecResulttModal"><span class="btnR"><span class="btnC">REJECT</span></span></a>
    			<a href="javascript:;" class="newButton1 newButtonOrange newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
	<!-- end #rejectModal -->
	
	<!-- #rejecResulttModal -->
	<div id="rejecResulttModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Reject Pending Request
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="check" /></div>
			<div class="noticeContent">Selected requests were rejected.</div>
			
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
	<!-- end #rejecResulttModal -->
	
	<!-- #approveResultModal -->
	<div id="approveModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Approve Pending Request
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="check" /></div>
			<div class="noticeContent">Selected requests were approved.</div>
			
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
	<!-- end #approveResultModal -->
	
    <%@ include file="search-user-dialogs.jsp" %>
</div>
</body>
<!-- End #page -->
</html>
