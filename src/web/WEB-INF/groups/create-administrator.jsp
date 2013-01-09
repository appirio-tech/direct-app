<%--
  - Author: TCSASSEMBER
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the create customer administrator page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="PAGE_TYPE" value="group" scope="request"/>
    <c:set var="CURRENT_TAB" value="createAdministrator" scope="request"/>
    
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
				
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="newAdminTitle">Create New Administrator User</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<div class="groupDetailsTable">
							
							<div class="groupDetailsTableContainer">
								<table cellpadding="0" cellspacing="0">
									<colgroup>
										<col width="50%" />
										<col width="50%" />
									</colgroup>
									<tbody>
										<tr>
											<td class="firstColumn">Select the Customer Name for whom you are creating the Customer Administrator:</td>
											<td>											
											<s:select id="clientIdSelect" name="clientId" headerKey="0" headerValue="--"
											        list="clients" listKey="id" listValue="name"
											        cssClass="newAdminSelect"/></td>
										</tr>
										<tr>
											<td class="firstColumn">Enter the member handle of the new Customer Administrator:</td>
											<td>
												<input type="text" class="text newAdminText" id="newAdminHandle" />
												<a href="javascript:;" class="searchIcon searchUser triggerModal" rel="#searchModal">Search</a>
												<div class="clear"></div>
												<p class="alertIcon">Enter the handle if the member is already a registered member.</p>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<!-- create new admin button -->
							<div class="createAdminButton">
								<a href="javascript:;" class="newButton1 triggerModal" rel="#createAdminModal"><span class="btnR"><span class="btnC">CREATE</span></span></a>
							</div>
							<!-- End .createAdminButton -->
							
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
	
	<!-- #createAdminModal -->
	<div id="createAdminModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Administrator User Created
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-check.png" alt="question" /></div>
			<div class="noticeContent">Customer Administrator user was successfully <br />created.</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
	<!-- end #createAdminModal -->
</div>
<!-- end modal -->
</body>
<!-- End #page -->
</html>
