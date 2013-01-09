<%--
  - Author: TCSASSEMBER
  - Version: 1.1 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file is used to render the user groups page.
  -
  - Version 1.1 updates (Release Assembly - TopCoder Security Groups - Release 4)
  - - Add support for rendering at most a configurable rows of an array contents.
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
							<h2 class="groupTitle">Groups</h2>
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
											<span class="label">Customer Name:</span>
											<input type="text" class="text" id="customerNameInput"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Account Name:</span>
											<input type="text" class="text" id="accountNameInput"/>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Project Name:</span>
											<input type="text" class="text" id="projectNameInput"/>
										</div>
										
										<div class="rightSide">
											<span class="label">Group Member Name:</span>
											<input type="text" class="text" id="groupMemberNameInput"/>
										</div>
										<div class="clearFixed"></div>
									</dd>
								</dl>
								<div class="filterContainerCommand2 noPaddingTop">
									<a class="button6" id="filterUserGroups" href="javascript:;"><span class="left"><span class="right">SEARCH</span></span></a>
									<div class="clearFixed"></div>
								</div>
							</div>
							<!-- End .filterContainer -->
							
						</div>
						<input type="hidden" id="groupPage" value="true"/>
						<!-- End .topFolderBox -->
                        <div class="noInvitation <s:if test='0 < groups.values.size()'>hide</s:if>">
							<div class="tableControlBox tableControlBoxTop">
								<div class="leftSide">
									<a class="button7 createNewGroup" href="<s:url action='enterCreateGroup' namespace='/group'/>"><span class="left">Create New Group</span></a>
								</div>
								<div class="rightSide"></div>
							</div>
							<div class="clearFixed"></div>
						    <div class="tableGroupWrapper"><div class="emptyTableList">
                            No User Groups Found!
                            </div></div>
							<div class="tableControlBox tableControlBoxBottom">
								<div class="leftSide">
									<a class="button7 createNewGroup" href="<s:url action='enterCreateGroup' namespace='/group'/>"><span class="left">Create New Group</span></a>
								</div>
								<div class="rightSide"></div>
							</div>
							<div class="clearFixed"></div>
                        </div>
						<div class="mainContent <s:if test='0 == groups.values.size()'>hide</s:if>">
							<div class="tableControlBox tableControlBoxTop">
							    <%@ include file="process-group-buttons.jsp" %>
							</div>
						    <div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList">
									<colgroup>
										<col width="3%" />
										<col width="15%" />
										<col width="10%" />
										<col width="10%" />
										<col width="13%" />
										<col width="22%" />
										<col width="15%" />
										<col width="12%" />
									</colgroup>
									<thead>
										<tr>
											<th>&nbsp;</th>
											<th>Group Name</th>
											<th>Access Rights</th>
											<th>Customer</th>
											<th>Account</th>
											<th>Project</th>
											<th>Resource Restrictions</th>
											<th>Group Members</th>
										</tr>
									</thead>
									<tbody>
									    <s:iterator value="result.items" var="group" status="st">
										<tr <s:if test="#st.first">class="firstRow"</s:if>>
											<td><input type="radio" name="userGroupSelectRadio" class="userGroupSelectRadio" <s:if test="#st.first">checked="checked"</s:if> /></td>
											<td><s:url var="viewUserGroupDetailsUrl" action="viewGroupAction" namespace="/group">
                                                  <s:param name="groupId" value="groupId"/>
                                                </s:url><a href="${viewUserGroupDetailsUrl}" rel="<s:property value='groupId'/>"><s:property value="groupName"/></a></td>
											<td><s:property value="accessRights"/></td>
											<td><s:property value="customerName"/></td>
											<td class="multirowsCell"><s:iterator value="accounts" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}</s:iterator></td>
											<td class="multirowsCell"><s:iterator value="projects" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}</s:iterator></td>
											<td><s:iterator value="resources" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}s</s:iterator></td>
											<td><s:iterator value="members" var="ele" status="st2"><s:if test="not #st2.first"><br/></s:if>${ele}s</s:iterator></td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<div class="tableControlBox tableControlBoxBottom">
							    <%@ include file="process-group-buttons.jsp" %>
							</div>
							<s:set var="pagedResult" value="groups"/>
							<%@ include file="pagination.jsp" %>
						</div>
						
					</div>
				</div>
				<jsp:include page="../includes/footer.jsp"/>
    		</div>
		</div>
    </div>
</div>

<%@ include file="delete-group-dialogs.jsp" %>
<%@ include file="tooltip.jsp" %>
    
</body>
<!-- End #page -->
</html>
