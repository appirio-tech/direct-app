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
	<link rel="stylesheet" href="/css/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.dialog.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.theme.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.core.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/permissions.css" media="all" type="text/css"/>

    <script type="text/javascript" id ="js-ui" src="/scripts/ui.dialog.js"></script>
    <script type="text/javascript" id="js-ajax" src="/scripts/AjaxPermissionProcessor.js"></script>
    <script type="text/javascript" id="jquery-dt"src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" id="dt-extend" src="/scripts/dataTable.extend.js"></script>
    <script type="text/javascript" id="ptable" src="/scripts/permissionTable.js"></script>
    <script type="text/javascript" id="pDialog" src="/scripts/permissionDialog.js"></script>
    <script type="text/javascript" id="json" src="/scripts/json2.js"></script>
    
    <script type="text/javascript">
        $(function() {
            var limit = 50;
            $(".shorten").each(function() {
                var chars = $(this).text();
                if (chars.length > limit) {
                    $(this).empty().append(chars.substr(0, limit-1)).append("... ");
                }
            });
	   var pn = "<%=request.getParameter("pn")%>";
	   if (pn !="perm")
	   {
	   	document.getElementById("area1").innerHTML = document.getElementById("area2-noti").innerHTML;
	   	document.getElementById("area2-noti").innerHTML = "";
	   }
	   else
	   {
		 requestPermissions();
                 document.getElementById("area1").innerHTML = document.getElementById("area2-perm").innerHTML;
                 document.getElementById("area2-perm").innerHTML = "";
	   }
	});
		
		function changepn(pn) {
			if (pn == "perm")
			{
				$("#projects").after($("#permissions"));
				$("#permissions_wrapper").remove();
				$("#projects").remove();
				$("#permissions").wrap('<div id="projects"/>');

				$("#users").after($("#userTable"));
                                $("#userTable_wrapper").remove();
                                $("#users").remove();
                                $("#userTable").wrap('<div id="users"/>');

				requestPermissions();
				document.getElementById("area2-noti").innerHTML = document.getElementById("area1").innerHTML;
				document.getElementById("area1").innerHTML = document.getElementById("area2-perm").innerHTML;
				document.getElementById("area2-perm").innerHTML = "";
                        $("#area1 .areaHeader select").val("perm");
			}
			else
			{
				document.getElementById("area2-perm").innerHTML = document.getElementById("area1").innerHTML;
			    	document.getElementById("area1").innerHTML = document.getElementById("area2-noti").innerHTML;
				document.getElementById("area2-noti").innerHTML = "";
                $("#area1 .areaHeader select").val("noti");
			}
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

                <div id="mainContent">

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
								<h2 class="title notificationTitle">Notifications Setting</h2>
                                                        <div class="select">
                                                            Select a Setting Panel:
                                                            <span name="settingPanel">
                                                                <select name="select" onchange="changepn(this.value)">
                                                    <option value="noti">Notifications</option>
                                                                    <option value="perm">Permissions</option>
                                                            </select>
                                                        </span>
                                                        </div>
                                                </div><!-- End .areaHeader -->

							<div class="container2">
							<div class="container2Left"><div class="container2Right"><div class="container2Bottom">
								<div class="container2BottomLeft"><div class="container2BottomRight">
									<div class="container2Content">
                                        <form id="dashboard-notifications-form">
										<table id="notifications" class="projectStats notifications" cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<th class="permCol">Project / Contest</th>
													<th class="permCol2" colspan="2">Notifications</th>
												</tr>
											</thead>
											<tbody class="checkPermissions">
												<tr class="applyForAll">
													<td class="markRed">Apply for All Projects</td>
													<td class="checkbox">
                                                        <input class="selectUser select_timeline" type="checkbox" onclick="notifications.selectAll(this, 'timeline');" autocomplete="off"> Timeline
                                                    </td>
													<td class="checkbox">
                                                        <input class="selectUser select_forum" type="checkbox" onclick="notifications.selectAll(this, 'forum');" autocomplete="off"> Forum
                                                    </td>
												</tr>


                                                <c:forEach items="${notifications}" var="projectNotifications" varStatus="i">
                                                    <c:set var="projectClass" scope="page" value="project_${projectNotifications.projectId}"/>
                                                    <tr class="select_project">
                                                        <td><div onclick="showHideGroup(this,'${projectClass}');" class="group expand">${projectNotifications.name}</div></td>
                                                        <td class="checkbox">
                                                            <input class="selectUser select_timeline" type="checkbox" onclick="notifications.selectProject(this, 'timeline', ${projectNotifications.projectId});" autocomplete="off"> Timeline
                                                        </td>
                                                        <td class="checkbox">
                                                            <input class="selectUser select_forum" type="checkbox" onclick="notifications.selectProject(this, 'forum', ${projectNotifications.projectId});" autocomplete="off"> Forum
                                                        </td>
                                                    </tr>

                                                    <c:forEach items="${projectNotifications.contestNotifications}" var="contestNotifications" varStatus="j">
                                                        <tr class="alternate ${projectClass} hide">
                                                            <td><a class="subgroup shorten" href="contest/detail?projectId=${contestNotifications.contestId}">${contestNotifications.name} (${contestNotifications.type})</a></td>
                                                            <td class="checkbox">
                                                                <input name="timeline" value="${contestNotifications.contestId}"
                                                                       class="selectUser select_timeline" type="checkbox"
                                                                       onclick="notifications.selectContest(this, 'timeline', ${projectNotifications.projectId});"
                                                                       ${contestNotifications.projectNotification ? 'checked="checked"' : ''}
                                                                       autocomplete="off"
                                                                    >
                                                                Timeline
                                                            </td>
                                                            <td class="checkbox">
                                                                <input name="forum" value="${contestNotifications.contestId}"
                                                                       class="selectUser select_forum group_${contestNotifications.forumId}" type="checkbox"
                                                                       onclick="notifications.selectContest(this, 'forum', ${projectNotifications.projectId}, ${contestNotifications.forumId});"
                                                                       ${contestNotifications.forumNotification ? 'checked="checked"' : ''}
                                                                       autocomplete="off"
                                                                    >
                                                                Forum
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:forEach>
											</tbody>

										</table><!-- End .projectsStats -->
										<div class="pagination">
											<div class="pages">
                                            </div><!-- End .pages -->

											<div class="showPages"><!-- number of rows that can be displayed on the same page -->
												<label><strong>Show:</strong></label>
												<select id="pageSize" class="normalOption" onchange="notifications.refreshTable();">
													<option value="10">10</option>
													<option value="25">25</option>
													<option value="50">50</option>
													<option value="1000000" selected="selected">All</option>
												</select>
												<span>per page</span>
                                            </div>
											<!-- End .showPages -->
										</div>
										<!-- End .pagination -->

										<div class="panel2"><!-- this area containt the print, export to excel, export to pdf links -->
											<a class="button9" href="javascript:notifications.update();">Save Notifications</a>

										</div><!-- End .panel -->
                                        </form>

									</div><!-- End .container2Content -->
								</div></div>
							</div></div></div>
						</div><!-- End .container2 -->



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
                                                        <th class="permCol2">Read</th>
                                                        <th class="permCol2">Write</th>
                                                        <th class="permCol2">Full Access</th>
                                                        <th class="permCol2">&nbsp;</th>
                                                    </tr>
                                                    <tr class="applyForAll">
                                                        <td class="markRed">
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
                                    <span class="left"><span class="right">Submit</span></span>
                                </a>
                            </form>
                        </div>
                        <div class="clear">
                        </div>
                    </div>
                    
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
                <a class="button6" href="javascript:void(0)" id="mu_save"><span class="left"><span class="right">
                        Save</span></span></a> <a class="button6 closeDialog" href="javascript:void(0)"><span
                    class="left"><span class="right">Cancel</span></span></a></div>
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
                <a id="au_save" class="button6 assignProjects " href="javascript:void(0)"><span class="left"><span
                        class="right">Save</span></span></a> <a class="button6 closeDialog"
                                                                             href="javascript:void(0)"><span
                    class="left"><span class="right">Cancel</span></span></a></div>
        </div>
    </div>
    <div id="addProjectDialog">
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
                <a id="ap_save" class="button6" href="javascript:void(0)"><span class="left"><span class="right">
                        Save</span></span></a> <a id="ap_add" class="button6 addMoreUsers"
                                                  href="javascript:void(0)"><span
                    class="left"><span class="right">Add More Users</span></span></a> <a class="button6 closeDialog"
                                                                                         href="javascript:void(0)"><span
                    class="left"><span class="right">Cancel</span></span></a></div>
        </div>
    </div>
</div>

</body>
<!-- End #page -->


</html>