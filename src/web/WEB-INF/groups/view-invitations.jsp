<%--
  - Author: TCSASSEMBER
  - Version: 1.3
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the received invitations page.
  -
  - Changes:
  - Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) changes:
  -   updated to use the refactored pagination.jsp.
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
    <c:set var="CURRENT_TAB" value="receivedInvitations" scope="request"/>
    
    <jsp:include page="../includes/htmlhead.jsp"/>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/layout.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/layout-groups.css" media="all" type="text/css" />

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/group.js"></script>
    <script type="text/javascript">
    function checkAcceptReject(){
        var errMessage = "${acceptRejectErrorMessage}";
        var action = "${acceptRejectSuccessResult['action']}";
        var operation = "${acceptRejectSuccessResult['operation']}";
        var result = "${acceptRejectSuccessResult['result']}";
        if (errMessage) {
            showErrors(errMessage);
        } else if (action == 'AcceptRejectGroupInvitationAction' && result=='success') {
            if (operation == 'accept') {
                modalLoad('#acceptModal');
            } else {
                modalLoad('#rejectModal');
            }
        }   
    }
    </script>
</head>

<body id="page" class="dashboardPage" onload="checkAcceptReject()">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
			
				<jsp:include page="../includes/header.jsp"/>
				
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupTitle">Group Invitation Statuses</h2>
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
											<span class="label">Group Name:</span>
											<input type="text" class="text" id="groupNameInput"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Access Rights:</span>
											<div class="secondColumn">
                                                <input type="checkbox" checked="checked" id="reportRadio" />
												<label for="reportRadio">Report</label>
												<input type="checkbox" checked="checked" id="readRadio" />
												<label for="readRadio">Read</label>
												<input type="checkbox" checked="checked" id="writeRadio" />
												<label for="writeRadio">Write</label>
												<input type="checkbox" checked="checked" id="fullRadio" />
												<label for="fullRadio">Full</label>
											</div>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Invitation Received between:</span>
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
										
										<div class="rightSide">
											<span class="label">Invitation Accepted between:</span>
											<div class="secondColumn specialColumn">
												<div class="fLeft">
													<input type="text" name="formData.startDate" id="startDateAccepted" class="text date-pick dp-applied" />
												</div>
												<div class="fRight">
													<input type="text" name="formData.endDate"  id="endDateAccepted" class="text date-pick dp-applied" />
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
							
						</div>
						<!-- End .topFolderBox -->
                        <div class="noInvitation <s:if test='0 < invitations.values.size()'>hide</s:if>">No Received Invitations Found!</div>
						<div class="mainContent <s:if test='0 == invitations.values.size()'>hide</s:if>">
							<div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList">
									<colgroup>
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
										<col width="20%" />
									</colgroup>
									<thead>
										<tr>
											<th>Group Name</th>
											<th>Access Rights</th>
											<th>Invitation Received On</th>
											<th>Invitation Accepted On</th>
											<th>Invitation Approved On</th>
										</tr>
									</thead>
									<tbody>
									    <s:iterator value="invitations.values" var="invitation" status="st">
										<tr <s:if test="#st.first">class="firstRow"</s:if>>
											<td><s:property value="groupMember.group.name"/></td>
											<td><s:if test="not groupMember.useGroupDefault"><s:property value="groupMember.specificPermission"/></s:if>
											    <s:else><s:property value="groupMember.group.defaultPermission"/></s:else></td>
											<td><fmt:formatDate var="dt" pattern="MM-dd-yyyy hh:mm a z" value="${sentOn}" timeZone="${defaultTimeZone}"/>
											    ${dt}</td>
											<td><s:if test="status+''=='REJECTED'">Rejected</s:if><s:else>
											        <s:if test="acceptedOrRejectedOn!=null"><fmt:formatDate var="dt" pattern="MM-dd-yyyy hh:mm a z" value="${acceptedOrRejectedOn}" timeZone="${defaultTimeZone}"/>${dt}</s:if>
											        <s:else>N/A</s:else></s:else></td>
											<td><s:if test="status+''=='APPROVAL_REJECTED'">Rejected</s:if><s:else>
											        <s:if test="approvalProcessedOn!=null"><fmt:formatDate var="dt" pattern="MM-dd-yyyy hh:mm a z" value="${approvalProcessedOn}" timeZone="${defaultTimeZone}"/>${dt}</s:if>
											        <s:else>N/A</s:else></s:else></td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							<s:set var="pagedResult" value="invitations"/>
							<%@ include file="pagination.jsp" %>
							
						</div>
						
					</div>
				</div>
				<jsp:include page="../includes/footer.jsp"/>
    		</div>
		</div>
    </div>
</div>
<div id="modalBackground"></div>
<div id="new-modal">
    <!-- accept group invitation -->
	<div id="acceptModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Accept Group Invitation
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="check" /></div>
			<div class="noticeContent">You've successfully accepted the group invitation.</div>
			
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
    <!-- reject group invitation -->
	<div id="rejectModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Reject Group Invitation
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="check" /></div>
			<div class="noticeContent">You've successfully rejected the group invitation.</div>
			
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
</div>
<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->
</html>
