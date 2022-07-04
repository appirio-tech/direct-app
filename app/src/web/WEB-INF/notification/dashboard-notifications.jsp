<%--
  - Author: greatKevin, winsty, isv, Ghost_141, TCSASSEMBLER
  - Version: 1.9
  - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the user notifications.
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  - Version 1.2 (TC Direct UI Improvement Assembly 1) changes notes: Solve "checkbox exists when no data in Settings > Permissions".
  - Version 1.3 (Release Assembly - Project Contest Fee Management) changes notes: Added contest fee option to the page.
  - Version 1.4 (Release Assembly - Project Contest Fees Management Update 1) changes notes: Added option for contest
  - fee creation to the page.
  - Version 1.5 (Release Assembly - TC Cockpit Project Forum Settings) change notes:
  - - Add the new project forum notifications table into the page.
  - Version 1.6 (Release Assembly - TC Cockpit Project Report Permission) change notes:
  - - Add new column 'report permission' to the permission table
  - Version 1.7 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page) changes notes: Added platform fee
   -management and creation functions to the page.
  - Version 1.8 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - 1) Update button text to uppercase.
  - 2) Rewrite part of the page to update layout.
  -
  - Version 1.9 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Update the button style
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<%--@elvariable id="notifications" type="java.util.List<com.topcoder.service.facade.contest.notification.ProjectNotification>"--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <script type="text/javascript" src="/scripts/notifications.js"></script>
  <link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.dialog.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.theme.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.core.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/permissions.css" media="all" type="text/css"/>

    <script type="text/javascript" id ="js-ui" src="/scripts/ui.dialog.js"></script>
    <script type="text/javascript" id="js-ajax" src="/scripts/AjaxPermissionProcessor.js"></script>
    <script type="text/javascript" id="jquery-dt"src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" id="dt-extend" src="/scripts/dataTable.extend.js"></script>
    <script type="text/javascript" id="ptable" src="/scripts/permissionTable.js"></script>
    <script type="text/javascript" id="pDialog" src="/scripts/permissionDialog.js"></script>
    <script type="text/javascript" id="json" src="/scripts/json2.js"></script>

    <script type="text/javascript">
		
		var currentPanel;
		var canViewContestFeeOption = <s:property value="viewContestFeeOption" />;
        $(function() {
            var limit = 50;
            $(".shorten").each(function() {
                var chars = $(this).text();
                if (chars.length > limit) {
                    $(this).empty().append(chars.substr(0, limit-1)).append("... ");
                }
            });
		   currentPanel = "<%=request.getParameter("pn")%>";
		   if (currentPanel == "perm") {
			    requestPermissions();
			    document.getElementById("area1").innerHTML = document.getElementById("area2-perm").innerHTML;
			    document.getElementById("area2-perm").innerHTML = "";
				$("#area1 .areaHeader select").val("perm");
		   } else if (currentPanel == "fee" && canViewContestFeeOption) {
			    // for newly added contest-fee
			    document.getElementById("area1").innerHTML = document.getElementById("area2-fee").innerHTML;
			    document.getElementById("area2-fee").innerHTML = "";
				$("#area1 .areaHeader select").val("fee");
		   } else {		    
				document.getElementById("area1").innerHTML = document.getElementById("area2-noti").innerHTML;
				document.getElementById("area2-noti").innerHTML = "";
				currentPanel = "noti";
				$("#area1 .areaHeader select").val("noti");
		   }
		});
		
		// called when the setting-panel selection dropdown list's value is changed
		function changepn(pn) {
			if (currentPanel == pn) {
				return;
			}
			if (currentPanel == "noti") {
				document.getElementById("area2-noti").innerHTML = document.getElementById("area1").innerHTML;
			} else if (currentPanel == "fee") {
				// for newly added contest-fee
				document.getElementById("area2-fee").innerHTML = document.getElementById("area1").innerHTML;
			} else if (currentPanel == "sync") {
                document.getElementById("area2-sync").innerHTML = document.getElementById("area1").innerHTML;
			} else {
				document.getElementById("area2-perm").innerHTML = document.getElementById("area1").innerHTML;
			}


			if (pn == "perm") {
				$("#projects").after($("#permissions"));
				$("#permissions_wrapper").remove();
				$("#projects").remove();
				$("#permissions").wrap('<div id="projects"/>');


				requestPermissions();							

				document.getElementById("area1").innerHTML = document.getElementById("area2-perm").innerHTML;
				document.getElementById("area2-perm").innerHTML = "";

                $("#area1 .areaHeader select").val("perm");
                $(".paging_permission_button .next").removeClass("next");

			} else if (pn == "noti") {			
			    document.getElementById("area1").innerHTML = document.getElementById("area2-noti").innerHTML;
				document.getElementById("area2-noti").innerHTML = "";
                $("#area1 .areaHeader select").val("noti");
			} else if (pn == "sync") {
                document.getElementById("area1").innerHTML = document.getElementById("area2-sync").innerHTML;
			    document.getElementById("area2-sync").innerHTML = "";
				$("#area1 .areaHeader select").val("sync");
			} else {
				// for newly added contest-fee
				document.getElementById("area1").innerHTML = document.getElementById("area2-fee").innerHTML;
			    document.getElementById("area2-fee").innerHTML = "";
				$("#area1 .areaHeader select").val("fee");
			}

			currentPanel = pn;
		}

      function projects_click(pj) {
        $("#projects").show();
        $("#users").hide();
        $(".permissions .firstItem").addClass("on");
        $(".permissions .lastItem").removeClass("on");

        $.permission.currentTable = 0;
      }

      /**
       * Set the user side click event handle.
       */
      function users_click(user) {
        $("#users").show();
        $("#projects").hide();
        $(".permissions .lastItem").addClass("on");
        $(".permissions .firstItem").removeClass("on");

        $.permission.currentTable = 1;
      }

    </script>

</head>

<c:set var="CURRENT_TAB" scope="request" value="settings"/>

<body id="page">
<div id="loading" style="display:none;"> Saving.... </div>

<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>
                    <div id="area1"><!-- the main area -->
                    </div>

                    <div id="area2-noti" style="display:none">
                    <div><!-- the main area -->
                        <div class="area1Content">
              <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong>Notifications</strong>
                            </div>
              <div class="areaHeader">
                <h2 class="title notificationTitle">Notification Settings</h2>
                                    <div class="select">
                                        Select a Setting Panel:
                                        <span name="settingPanel">
                                            <select name="select" onchange="changepn(this.value)">
                                                <option value="noti">Notifications</option>
                                                <option value="perm">Permissions</option>
												<s:if test="viewContestFeeOption">
													<option value="fee">Challenge Fee</option>
												</s:if>
                                                
                                            </select>
                                        </span>
                                    </div>
                            </div><!-- End .areaHeader -->

                            <div class="activity">
                                <table width="100%" cellspacing="0" cellpadding="0" id="preferenceTable" class="project">
                                    <thead>
                                    <tr class=" ">
                                        <th colspan="5">
                                            <span class="left"><span class="right">Default Settings</span></span>
                                        </th>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="">
                                    <div class="container2LeftClear">
                                        <div class="container2RightClear">
                                            <div class="container2BottomClear">
                                                <div class="container2Content" id="preDiv">
                                                    <c:forEach items="${preferences}" var="pre">
                                                        <div>
                                                            <input type="checkbox" name="pre_${pre.preferenceId}" ${pre.value ? 'checked="checked"' : ''}></input>
                                                            <span class="preferenceItem">${pre.desc}</span>
                                                        </div>
                                                    </c:forEach>

                                                    <div class="panel2">
                                                        <a href="javascript:savePreference();" id="savePreferenceButton" class="button6">
                                                            <span class="left">
                                                                <span class="right">SAVE PREFERENCES</span>
                                                            </span>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <!-- End .tableFooterLeft -->
                                </div>
                                <!-- End .tableFooter -->
                            </div>

                            <div class="line"></div>

         <!--     <h5 style="margin-bottom:6px">Project Forum Notifications</h5>

              <div class="container2">

                  <div class="dashboardNotificationsDiv">

                      <div id="projectForumNotifications">
                          <table id="projectNotifications" class="projectStats notifications" cellpadding="0"
                                 cellspacing="0">
                              <thead>
                              <tr>
                                  <th class="permCol">Project Name</th>
                                  <th class="permCol2">Project Forum</th>
                              </tr>
                              </thead>
                              <tbody class="checkPermissions">


                              <s:iterator value="projectNotifications">

                                  <tr class="select_project">
                                      <td>
                                          <div> <a  href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="projectId"/></s:url>">

                                              <s:property value="name"/>

                                          </a></div>
                                      </td>
                                      <td class="checkbox2">
                                          <input type="hidden" name="projectForumCategoryId" value="${forumId}"/>
                                          <input type="hidden" name="projectForumProjectId" value="${projectId}"/>
                                          <input id='projectForumCheckId${projectId}' class="selectUser select_forum"
                                                 type="checkbox" autocomplete="off" <s:if test='forumNotification == true'>checked="checked"</s:if> />
                                          <label for='projectForumCheckId${projectId}'>Forum</label>
                                      </td>
                                  </tr>

                              </s:iterator>
                              </tbody>

                          </table>


                          <div class="container2Left">
                              <div class="container2Right">
                                  <div class="container2Bottom">
                                      <div class="container2BottomLeft">
                                          <div class="container2BottomRight">



                                              <div class="pagination">

                                                  <div class="pages bottom2"></div>

                                                  <div class="showPages bottom1">
                                                      <label><strong>Show:</strong></label>
                                                      <select id="projectForumPageSize" class="normalOption" onchange="projectForumPageChange()">
                                                          <option value="10" selected="selected">10</option>
                                                          <option value="25">25</option>
                                                          <option value="50">50</option>
                                                          <option value="1000000">All</option>
                                                      </select>
                                                      <span>per page</span>
                                                  </div>
                                              </div>



                                              <div class="panel2">
                                                  <a class="button9" id="saveProjectForumNotifications" href="javascript:saveProjectForumNotifications();">Save Notifications</a>
                                              </div>

                                          </div>


                                      </div>
                                  </div>
                              </div>
                          </div>

                      </div>-->


                      <h5 style="margin-bottom:6px">Challenge Timeline/Forum Notifications</h5>
                                        <form id="dashboard-notifications-form">

                                            <table id="notifications" class="projectStats notifications" cellpadding="0" cellspacing="0">
                                                <thead>
                                                    <tr>
                                                        <th class="permCol">Challenge Settings</th>
                                                        <th class="permCol2" colspan="2">Notifications</th>
                                                    </tr>
                                                </thead>
                                                <tbody class="checkPermissions">
                                                   


                                                    <c:forEach items="${notifications}" var="projectNotifications" varStatus="i">
                                                        <c:set var="projectClass" scope="page" value="project_${projectNotifications.projectId}"/>
                                                        <c:set var="projectCheckTimeId" scope="page" value="projectCheckTimeId${projectNotifications.projectId}"/>
                                                        <c:set var="projectCheckForumId" scope="page" value="projectCheckForumId${projectNotifications.projectId}"/>
                                                        <tr class="select_project">
                                                            <td><div onclick="showHideGroup(this,'${projectClass}');" class="group expand">${projectNotifications.name}</div></td>
                                                            <td class="checkbox">
                                                           <!--     <input id='${projectCheckTimeId}' class="selectUser select_timeline" type="checkbox" onclick="notifications.selectProject(this, 'timeline', ${projectNotifications.projectId});" autocomplete="off">
                                                                <label for='${projectCheckTimeId}'>Timeline</label>-->
                                                            </td>
                                                            <td class="checkbox">
                                                              <!--  <input id='${projectCheckForumId}' class="selectUser select_forum" type="checkbox" onclick="notifications.selectProject(this, 'forum', ${projectNotifications.projectId});" autocomplete="off">
                                                                <label for='${projectCheckForumId}'>Forum</label>-->
                                                            </td>
                                                        </tr>

                                                        <c:forEach items="${projectNotifications.contestNotifications}" var="contestNotifications" varStatus="j">

                                                            <c:set var="contestCheckTimeId" scope="page" value="contestCheckTimeId${contestNotifications.contestId}"/>
                                                            <c:set var="contestCheckForumId" scope="page" value="contestCheckTimeId${contestNotifications.contestId}"/>

                                                            <tr class="alternate ${projectClass} hide">
                                                                <td><a class="subgroup shorten" href="contest/detail?projectId=${contestNotifications.contestId}">${contestNotifications.name} (${contestNotifications.type})</a></td>
                                                                <td class="checkbox">
                                                                    <input id='${contestCheckTimeId}' name="timeline" value="${contestNotifications.contestId}"
                                                                           class="selectUser select_timeline" type="checkbox" style="vertical-align: text-top;" 
                                                                           onclick="notifications.selectContest(this, 'timeline', ${projectNotifications.projectId});"
                                                                           ${contestNotifications.projectNotification ? 'checked="checked"' : ''}
                                                                           autocomplete="off"
                                                                        >
                                                                    <label for="${contestCheckTimeId}">Timeline</label>
                                                                </td>
                                                                <td class="checkbox">
                                                                    <input id='${contestCheckForumId}' name="forum" value="${contestNotifications.contestId}"
                                                                           class="selectUser select_forum group_${contestNotifications.forumId}" type="checkbox" style="vertical-align: text-top;" 
                                                                           onclick="notifications.selectContest(this, 'forum', ${projectNotifications.projectId}, ${contestNotifications.forumId});"
                                                                           ${contestNotifications.forumNotification ? 'checked="checked"' : ''}
                                                                           autocomplete="off"
                                                                        >
                                                                    <label for="${contestCheckForumId}">Forum</label>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </tbody>

                                            </table>
                                            <div class="container2Left">
                                                <div class="container2Right">
                                                    <div class="container2Bottom">
                                                        <div>
                                                            <div>



                                                                <div class="pagination">

                                                                    <div class="pages bottom2"></div>

                                                                    <div class="showPages bottom1"><!-- number of rows that can be displayed on the same page -->
                                                                        <label><strong>Show:</strong></label>
                                                                        <select id="pageSize" class="normalOption" onchange="notifications.refreshTable();">
                                                                            <option value="10">10</option>
                                                                            <option value="25">25</option>
                                                                            <option value="50">50</option>
                                                                            <option value="1000000" selected="selected">All</option>
                                                                        </select>
                                                                        <span>per page</span>
                                                                    </div>

                                                                    <!-- End .pages -->
                                                                    <!-- End .showPages -->
                                                                </div>



                                                                <div class="panel2"><!-- this area containt the print, export to excel, export to pdf links -->
                                                                    <a href="javascript:notifications.update();" class="button6" style="margin-left: 10px;">
                                                                        <span class="left">
                                                                            <span class="right">SAVE NOTIFICATIONS</span>
                                                                        </span>
                                                                    </a>
                                                                </div>

                                                            </div>


                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>




                                            <!-- End .projectsStats -->

                    <!-- End .pagination -->


                                            <!-- End .panel -->


                </div><!-- End .container2Content -->
              </div>
                        </div>
          </div>


                    <div id="area2-perm" style="display:none">
                        <!-- the main area -->
                        <div class="area1Content">
                          <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong>Permissions</strong>
                            </div>

                            <div class="areaHeader">
                                <h2 class="title contestTitle">Permissions</h2>
                                <div class="select">
                                        Select a Setting Panel:
                                        <span name="settingPanel">
                                        <select name="select" onchange="changepn(this.value)">
												<option value="noti">Notifications</option>
                                                <option value="perm" selected>Permissions</option>
												<s:if test="viewContestFeeOption">
													<option value="fee">Challenge Fee</option>
												</s:if>
                                        </select>
                                    </span>
                                    </div>
                                <span class="actionMessage"><s:actionmessage/></span>

                            </div>
                            <!-- End .areaHeader -->

                            <div class="container2 tabs3Container permissions">
                                <div id="tabs3">
                                    <ul>
                                        <li class="firstItem on" onclick="projects_click('this')"><a class="first" href="javascript:void(0)" id="link-projects"><span
                                                class="left">
                                                <span class="right">Projects</span></span></a></li>
                                        <li class="lastItem" onclick="users_click('this')"><a class="last" href="javascript:void(0)"><span
                                                class="left"><span
                                                class="right">Users</span></span></a></li>
                                    </ul>
                                </div>
                                <!-- End #tabs3 -->
                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Content">
                                            <div id="projects">
                                                <table cellspacing="0" cellpadding="0"
                                                       class="projectStats notifications" id="permissions">
                                                    <thead>
                                                    <tr>
                                                        <th class="permCol">Project Name</th>
                                                        <th class="permCol2">Report</th>
                                                        <th class="permCol2">Read</th>
                                                        <th class="permCol2">Write</th>
                                                        <th class="permCol2">Full Access</th>
                                                        <th class="permCol2">&nbsp;</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody class="checkPermissions">
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div id="users">
                                                <table id="userTable" cellspacing="0" cellpadding="0"
                                                       class="projectStats notifications">
                                                    <thead>
                                                    <tr>
                                                        <th class="permCol">
                                                            <a style="color:#FFFFFF;text-decoration:none"
                                                               href="javascript:;">
                                                                User List <img src="/images/up.png" alt=""/></a>

                                                        </th>
                                                        <th class="permCol2">Report</th>
                                                        <th class="permCol2">Read</th>
                                                        <th class="permCol2">Write</th>
                                                        <th class="permCol2">Full Access</th>
                                                        <th class="permCol2">&nbsp;</th>
                                                    </tr>
                                                    <tr class="applyForAll">
                                                        <td class="markRed">
                                                            <a id="u_addMoreUserA" class="button1 addMoreUsers"
                                                               href="javascript:void(0)"><span>Add More Users</span></a>
                                                            <a id="u_deleteUsersA" class="button1"
                                                               href="javascript:void(0)"><span>Delete Selected Users</span></a>
                                                        </td>
                                                        <td class="checkbox">
                                                            <input type="checkbox" class="selectUser"/>
                                                        </td>
                                                        <td class="checkbox">
                                                            <input type="checkbox" class="selectUser"/>
                                                        </td>
                                                        <td class="checkbox">
                                                            <input type="checkbox" class="selectUser"/>
                                                        </td>
                                                        <td class="checkbox">
                                                            <input type="checkbox" class="selectUser"/>
                                                        </td>
                                                        <td>
                                                        </td>
                                                    </tr>
                                                    </thead>
                                                    <tbody class="checkPermissions">
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- End .container2Content -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <form id="permissionForm" action="updatePermission" method="post">
                                <input type="hidden" id="permissionHiddenInput" name="permissionsJSON" value="test"/>
                                <a id="permission_submit" class="button6" onclick="pbutton_submit()" href="javascript:;">
                                    <span class="left"><span class="right">SUBMIT</span></span>
                                </a>
                            </form>
                        </div>
                        <div class="clear">
                        </div>
                    </div>


				  <s:if test="viewContestFeeOption">
					<div id="area2-fee" style="display:none">
						<div class="area1Content">
                        	<div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong>Contest Fee Management</strong>
                            </div>
                             
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Challenge Fee Management</h2>
                                <div class="select">
                                        Select a Setting Panel:
                                        <span name="settingPanel">
                                        <select name="select" onchange="changepn(this.value)">
												<option value="noti">Notifications</option>
                                                <option value="perm">Permissions</option>												
												<option value="fee">Challenge Fee</option>
                                        </select>
                                    </span>
                                </div>
                            </div>
                            <!-- End .areaHeader -->
                            <div>
                                <p class="billingAccountArchive"><a
                                        href='<s:url action="listBillingAccountAction" namespace="/"/>'>Project Challenge
                                    Fees Management for Billing Accounts</a></p>

                                <p class="billingAccountArchive"><a
                                        href='<s:url action="createContestFeesHome" namespace="/"/>'>Project Challenge
                                    Fees Creation for Billing Accounts</a></p>
                                    
                                <p class="billingAccountArchive"><a
                                        href='<s:url action="listCustomerPlatformFee" namespace="/"/>'>Platform Fee Management for Client</a></p>

                                <p class="billingAccountArchive"><a
                                        href='<s:url action="enterCreateCustomerPlatformFee" namespace="/"/>'>Platform Fee Creation for Client</a></p>
                            </div>
                        </div>
                    </div>
                  </s:if>

                </div>
                <!-- End #mainContent -->
            </div>
            <!-- End #content -->
            <jsp:include page="/WEB-INF/includes/footer.jsp"/>

        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>

<!-- defined the dialog for model window -->
<div class="dialogContent" style="display: none;">
    <div id="manageUserDialog">
        <div class="header">
            <div class="title">
                User Management
            </div>
            <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
        </div>
        <div class="body">
            <div class="left">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Available Users</a> <a href="javascript:void(0)"
                                                                                        class="rightTxt">Select All</a>
                </div>
                <div class="searchBox">
                    <input type="text" value="adr" class="searchTxt"/>&nbsp; <a class="button1"
                                                                                href="javascript:void(0)">
                    <span>Search</span></a></div>
                <div class="list">
                </div>
            </div>
            <div class="middle">
                <a class="addItem" href="javascript:void(0)">
                    <img src="/images/add_tech.png" title="Add User"/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" title="Remove User"/></a></div>
            <div class="right">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Chosen Users</a> <a href="javascript:void(0)"
                                                                                     class="rightTxt">Remove All</a>
                </div>
                <div class="list">
                </div>
            </div>
        </div>
        <div class="foot">
            <div class="separator">
            </div>
            <div class="buttons">
                <a class="button6" href="javascript:void(0)" id="mu_save" style="margin-left: 22px;"><span class="left"><span class="right">
                        SAVE</span></span></a> <a class="button6 closeDialog" href="javascript:void(0)"><span
                    class="left"><span class="right">CANCEL</span></span></a></div>
        </div>
    </div>
    
    <div id="addUserDialog">
        <div class="header">
            <div class="title">
                Add to Users List...
            </div>
            <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
        </div>
        <div class="body">
            <div class="left">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Available Users</a> <a href="javascript:void(0)"
                                                                                        class="rightTxt">Select All</a>
                </div>
                <div class="searchBox">
                    <input type="text" class="searchTxt"/>&nbsp; <a class="button1" href="javascript:void(0)">
                    <span>Search</span></a></div>
                <div class="list">
                </div>
            </div>
            <div class="middle">
                <a class="addItem" href="javascript:void(0)">
                    <img src="/images/add_tech.png" title="Add User"/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" title="Remove User"/></a></div>
            <div class="right">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Chosen Users</a> <a href="javascript:void(0)"
                                                                                     class="rightTxt">Remove All</a>
                </div>
                <div class="list">
                </div>
            </div>
        </div>
        <div class="foot">
            <div class="separator">
            </div>
            <div class="buttons">
                <a id="au_save" class="button6 assignProjects " href="javascript:void(0)" style="margin-left: 55px;"><span class="left"><span
                        class="right">SAVE</span></span></a> <a class="button6 closeDialog"
                                                                             href="javascript:void(0)"><span
                    class="left"><span class="right">CANCEL</span></span></a></div>
        </div>
    </div>
    <div id="addProjectDialogPm">
        <div class="header">
            <div class="title">
                Assign Project(s)...
            </div>
            <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
        </div>
        <div class="body">
            <div class="left">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Available Projects</a> <a href="javascript:void(0)"
                                                                                           class="rightTxt">Select
                    All</a></div>
                <div class="searchBox">
                    <input type="text" class="searchTxt"/>&nbsp; <a class="button1" href="javascript:void(0)">
                    <span>Search</span></a></div>
                <div class="list">
                </div>
            </div>
            <div class="middle">
                <a class="addItem" href="javascript:void(0)">
                    <img src="/images/add_tech.png" title="Add Project"/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" title="Remove Project"/></a></div>
            <div class="right">
                <div class="subtitle">
                    <a href="javascript:void(0)" class="leftTxt">Chosen Projects</a> <a href="javascript:void(0)"
                                                                                        class="rightTxt">Remove All</a>
                </div>
                <div class="list">
                </div>
            </div>
        </div>
        <div class="foot">
            <div class="separator">
            </div>
            <div class="buttons">
                <a id="ap_save" class="button6" href="javascript:void(0)" style="margin-left: -58px;"><span class="left"><span class="right">
                        SAVE</span></span></a> <a id="ap_add" class="button6 addMoreUsers"
                                                  href="javascript:void(0)"><span
                    class="left"><span class="right">ADD MORE USERS</span></span></a> <a class="button6 closeDialog"
                                                                                         href="javascript:void(0)"><span
                    class="left"><span class="right">CANCEL</span></span></a></div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footerScripts.jsp"/>
</body>
<!-- End #page -->


</html>
