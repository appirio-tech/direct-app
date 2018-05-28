<%--
  - Author: GreatKevin
  - Version 1.2
  -
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Changes:
  - Version 1.1 (Release Assembly - TopCoder Security Groups Release 3) changes:
  -   updated to fixed the bugs in this assembly.
  -
  - Version 1.2 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
  - - Reskin the group pages
  -
  - This jsp file is used to render the send group invitation page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="PAGE_TYPE" value="group" scope="request"/>
    <c:set var="CURRENT_TAB" value="sendInvitation" scope="request"/>
    
    <jsp:include page="../includes/htmlhead.jsp"/>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/layout.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/layout-groups.css" media="all" type="text/css" />

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/group.js"></script>
</head>

<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
			
				<jsp:include page="../includes/header.jsp"/>
				
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupTitle sendEmailTitle">Send Group Invitation</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						
						<div class="sendGroupInvitationTable">
							<table cellpadding="0" cellspacing="0">
								<colgroup>
									<col width="50%" />
									<col width="50%" />
								</colgroup>
								<tbody>
									<tr>
										<td class="firstColumn">
											<div class="unitLabel">Select the group you want to invite the users to:</div>
											<div class="noteLabel">Note: At least one group must be selected. Invitation emails will be sent out to the new members added to the checked groups.</div>
										</td>
										<td class="lastColumn">
											<div class="multiSelectBox" style="overflow-x: hidden;">
												<div class="multiOptionRow">
                                                    <c:choose>
                                                        <c:when test="${fn:length(groups) eq 0}">
                                                            <div>No group available</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" id="multiSelect01CheckBox00" autocomplete="off"/>
													        <label for="multiSelect01CheckBox00">Select All</label>
                                                        </c:otherwise>
                                                    </c:choose>
												</div>
                                                <c:forEach items="${groups}" var="group" varStatus="vs">
                                                <div class="multiOptionRow">
                                                    <input type="checkbox" value="${group.id}" id="multiSelect01CheckBox${vs.count}" name="groups" autocomplete="off"/>
                                                    <label for="multiSelect01CheckBox${vs.count}"><c:out value="${group.name}" /></label>
                                                </div>
                                                </c:forEach>
											</div>
										</td>
									</tr>
									<tr>
										<td class="firstColumn">
											<div class="unitLabel">Enter the member handle of the users you want to invite to the groups:</div>
										</td>
										<td class="lastColumn">
											<dl id="moreMember">
											    <c:forEach begin="1" end="5" step="1">
												<dd>
													<input type="text" class="text" />
													<a href="javascript:;" class="searchDetails searchUser triggerModal" rel="#searchModal">Search</a>
													<div class="clearFixed"></div>
												</dd>
												</c:forEach>
											</dl>
											<dl>
												<dd class="lastDd">
													<a href="javascript:;" class="newButton2 addMoreMembersButton" id="addMemberButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Add More Users</span></span></span></a>
												</dd>
											</dl>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="commandOperationBox">
							<a href="javascript:;" id="sendInvitation" class="newButton1 newButtonGreen triggerModal" rel="#sendInvitationConfirmModal"><span class="btnR"><span class="btnC">SEND INVITATION</span></span></a>
						</div>
						
					</div>
				</div>
				<jsp:include page="../includes/footer.jsp"/>
    		</div>
		</div>
    </div>
</div>


<!-- modal -->
<div id="modalBackground"></div>
<div id="new-modal">
    <%@ include file="search-user-dialogs.jsp" %>
	
	<!-- #sendInvitationConfirmModal -->
	<div id="sendInvitationConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Send Invitation Confirmation
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="correct" /></div>
			<div class="noticeContent noticeContentSendInviteConfirmation">Invitation emails have been sent to the new members added to the checked groups.</div>
			
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
	<!-- end #sendInvitationConfirmModal -->
    
    <!-- #sendInvitationConfirmModal -->
	<div id="sendInvitationWarnModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Send Invitation
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/modal-notice.png" alt="warn" /></div>
			<div class="noticeContent noticeContentSendInviteWarn"></div>
			
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
	<!-- end #sendInvitationWarnModal -->
</div>
<!-- end modal -->
</body>
<!-- End #page -->
</html>
