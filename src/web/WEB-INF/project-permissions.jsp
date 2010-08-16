<%--
  - Author: isv
  - Version: 1.0 (Direct Permissions Setting Back-end and Integration Assembly 1.0)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the forms for managing the project-level permissions from project and user
  - perspectives.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="permissions"/>
    <jsp:include page="includes/paginationSetup.jsp"/>

    <link rel="stylesheet" href="/css/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.dialog.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.theme.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/ui.core.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/permissions.css" media="all" type="text/css"/>

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/ui.dialog.js"></script>
    <script type="text/javascript" src="/scripts/AjaxPermissionProcessor.js"></script>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/scripts/dataTable.extend.js"></script>
    <script type="text/javascript" src="/scripts/permissionTable.js"></script>
    <script type="text/javascript" src="/scripts/permissionDialog.js"></script>
    <script type="text/javascript" src="/scripts/json2.js"></script>
</head>
<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <!-- End #header -->
                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Permissions</h2>
                                <span class="actionMessage"><s:actionmessage/></span>

                            </div>
                            <!-- End .areaHeader -->

                            <div class="container2 tabs3Container permissions">
                                <div id="tabs3">
                                    <ul>
                                        <li class="firstItem on"><a class="first" href="javascript:void(0)"><span
                                                class="left">
                                                <span class="right">Projects</span></span></a></li>
                                        <li class="lastItem"><a class="last" href="javascript:void(0)"><span
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
                                                       class="projectStats notifications" id="notifications">
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
                                <a id="permission_submit" class="button6" href="javascript:;">
                                    <span class="left"><span class="right">Submit</span></span>
                                </a>
                            </form>
                        </div>
                        <div class="clear">
                        </div>
                    </div>
                    <!-- End #mainContent -->
                    <jsp:include page="includes/footer.jsp"/>
                </div>
                <!-- End #content -->
            </div>
            <!-- End #container -->
        </div>
    </div>
</div>
<!-- End #wrapper -->
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
                    <img src="/images/add_tech.png" alt=""/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" alt=""/></a></div>
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
                    <img src="/images/add_tech.png" alt=""/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" alt=""/></a></div>
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
                    <img src="/images/add_tech.png" alt=""/></a> <a class="removeItem" href="javascript:void(0)">
                <img src="/images/remove_tech.png" alt=""/></a></div>
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
