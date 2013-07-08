<%--
  - Author: TCSASSEMBER, freegod
  - Version: 1.2 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the auditing information page.
  -
  - Version 1.1 updates (Release Assembly - TopCoder Security Groups - Release 4)
  - - Add support for rendering at most a configurable rows of an array contents.
  - - Change data picker input box editable.
  - Version 1.2 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) changes:
  - - Added Auto Grant Permissions column.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="PAGE_TYPE" value="group" scope="request"/>
    <c:set var="CURRENT_TAB" value="audit" scope="request"/>
    
    <jsp:include page="../includes/htmlhead.jsp"/>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/layout.css?v=212459" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/layout-groups.css?v=210792" media="all" type="text/css" />

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=222286"></script>
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
							<h2 class="auditTitle">Audit Information</h2>
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
											<span class="label">Group Name with Access:</span>
											<input type="text" class="text compareText" id="groupNameInput"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Member Handle with Access:</span>
											<input type="text" class="text compareText" id="memberHandleInput" />
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Access to Customer Name:</span>
											<s:select id="clientIdSelect" name="clientId" headerKey="0" headerValue="--"
											        list="clients" listKey="id" listValue="name"
											        cssClass="selectCustomer"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Access to Billing Account:</span>
											<input type="text" class="text" id="billingAccountInput"/>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Access to Project:</span>
											<input type="text" class="text" id="projectNameInput" />
										</div>
										
										<div class="rightSide">
											<span class="label">Had Access between Dates:</span>
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
									<dd class="last">
										<div class="leftSide">
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
										<div class="rightSide">
											<a class="button6" id="filterAuditInfo" href="javascript:;"><span class="left"><span class="right">SEARCH</span></span></a>
										</div>
										<div class="clearFixed"></div>
									</dd>
								</dl>
							</div>
							<!-- End .filterContainer -->
							
						</div>
						<input type="hidden" id="auditPage" value="true"/>
						<!-- End .topFolderBox -->
                        <div class="noInvitation <s:if test='0 < historicalData.values.size()'>hide</s:if>">
                            No Audit Information Found!
                        </div>
						<div class="mainContent <s:if test='0 == historicalData.values.size()'>hide</s:if>">
						    <div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList">
									<colgroup>
										<col width="12%" />
										<col width="8%" />
										<col width="12%" />
										<col width="12%" />
										<col width="20%" />
                                        <col width="12%"/>
										<col width="16%" />
										<col width="8%" />
									</colgroup>
									<thead>
										<tr>
											<th>Group Name</th>
											<th>Member</th>
											<th>Customer Name</th>
											<th>Billing Accounts</th>
											<th>Projects</th>
                                            <th>Auto Grant Permissions</th>
											<th>Access From / To</th>
											<th>Access Rights</th>
										</tr>
									</thead>
									<tbody>
									    <s:iterator value="result.items" var="audiInfo" status="st">
										<tr <s:if test="#st.first">class="firstRow"</s:if>>
											<td><s:property value="groupName"/></td>
											<td><s:property value="member"/></td>
											<td><s:property value="customerName"/></td>
											<td class="multirowsCell"><s:iterator value="accounts" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}</s:iterator></td>
											<td class="multirowsCell"><s:iterator value="projects" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}</s:iterator></td>
											<td>
                                                <s:if test="autoGrant">Yes</s:if>
                                                <s:if test="%{null == autoGrant || !autoGrant}">No</s:if>
                                            </td>
                                            <td><s:iterator value="fromTo" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}</s:iterator></td>
											<td><s:property value="accessRights"/></td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<s:set var="pagedResult" value="historicalData"/>
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
<div id="new-modal"></div>
<%@ include file="tooltip.jsp" %>

</body>
<!-- End #page -->
</html>
