<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the permissions setting page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.dialog.css?v=185283" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.theme.css?v=185283" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.core.css?v=185283" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/permissions.css?v=193435" media="all" type="text/css"/>

    <script type="text/javascript" id ="js-ui" src="/scripts/ui.dialog.js?v=185283"></script>
    <script type="text/javascript" id="js-ajax" src="/scripts/AjaxPermissionProcessor.js?v=186145"></script>
    <script type="text/javascript" id="jquery-dt"src="/scripts/jquery.dataTables.min.js?v=178111"></script>
    <script type="text/javascript" id="dt-extend" src="/scripts/dataTable.extend.js?v=186145"></script>
    <script type="text/javascript" id="ptable" src="/scripts/permissionTable.js?v=213622"></script>
    <script type="text/javascript" id="pDialog" src="/scripts/permissionDialog.js?v=210124"></script>
    <script type="text/javascript" id="json" src="/scripts/json2.js?v=186145"></script>

    <script type="text/javascript">
        /**
         * Sets the projects tab click event handle.
         *
         * @param pj
         */
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
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="/WEB-INF/includes/right.jsp"/>
<div id="area1"><!-- the main area -->
    <!-- the main area -->
    <div class="area1Content">
        <div class="currentPage">
            <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
            <strong>Settings</strong> &gt;
            <strong>Permissions</strong>
        </div>

        <div class="areaHeader">
            <h2 class="title contestTitle">Permissions</h2>
            <div class="select">
                <div class="selectInnerBox">
                    <div class="selectInner">
                        <label for="selectSetting">Select a Setting Panel:</label>
                <span class="settingPanel">
                    <select id="selectSetting" name="select">
                        <option value="notifications">
                            Notifications</option>
                        <option  selected="selected" value="permissions">
                            Permissions</option>
                        <s:if test="ContestFeeSettingAccessible">
                            <option value="fee">
                                Contest Fee</option>
                        </s:if>
                        <s:if test="jiraSynAccessible">
                            <option value="sync">
                                Synchronize User in JIRA and WIKI</option>
                        </s:if>
                    </select>
                </span>
                        <div class="clearFix"></div>
                    </div>
                    <span class="corner tl"></span>
                    <span class="corner tr"></span>
                    <span class="corner bl"></span>
                    <span class="corner br"></span>
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

</body>
<!-- End #page -->


</html>
