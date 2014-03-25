<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  -- Version 1.1 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
  -
  - Version 1.2 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Remove th uneeded corners in div and update the button style
  -
  - Description: This page renders the notifications setting page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>

    <ui:adminPageType tab="notifications"/>
    <script type="text/javascript" src="/scripts/notifications.js?v=214844"></script>

    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.dialog.css?v=185283" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.theme.css?v=185283" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/ui.core.css?v=185283" media="all" type="text/css"/>

    <script type="text/javascript" id="js-ui" src="/scripts/ui.dialog.js?v=185283"></script>
    <script type="text/javascript" id="jquery-dt" src="/scripts/jquery.dataTables.min.js?v=178111"></script>
    <script type="text/javascript" id="dt-extend" src="/scripts/dataTable.extend.js?v=186145"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            var contestNameLengthLimit = 200;
            $(".shorten").each(function () {
                var chars = $(this).text();
                if (chars.length > contestNameLengthLimit) {
                    $(this).empty().append(chars.substr(0, contestNameLengthLimit - 1)).append("... ");
                }
            });
        });
    </script>
</head>

<c:set var="CURRENT_TAB" scope="request" value="settings"/>

<body id="page">

<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="/WEB-INF/includes/header.jsp"/>

<div id="mainContent" class="newSidebarCollapse">

<jsp:include page="/WEB-INF/includes/right.jsp"/>
<div id="area1"><!-- the main area -->
<div><!-- the main area -->
<div class="area1Content">
<div class="currentPage">
    <a href="<s:url action=" dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <Strong>Settings</Strong> &gt;
    <strong>Notifications</strong>
</div>
<div class="areaHeader">
    <h2 class="title notificationTitle">Notification Settings</h2>
    <div class="select">
        <div class="selectInnerBox">
            <div class="selectInner">
                <label for="selectSetting">Select a Setting Panel:</label>
                <span class="settingPanel">
                    <select id="selectSetting" name="select">
                        <option selected="selected" value="notifications">
                            Notifications</option>
                        <option value="permissions">
                            Permissions</option>
                        <s:if test="ContestFeeSettingAccessible">
                            <option value="fee">
                                Challenge Fee</option>
                        </s:if>
                        <s:if test="jiraSynAccessible">
                            <option value="sync">
                                Synchronize User in JIRA and WIKI</option>
                        </s:if>
                    </select>
                </span>
                <div class="clearFix"></div>
            </div>

        </div>
    </div>
    <%--<div class="select">--%>
        <%--Select a Setting Panel:--%>
                                        <%--<span name="settingPanel">--%>
                                            <%--<select name="select" onchange="changepn(this.value)">--%>
                                                <%--<option value="noti">Notifications</option>--%>
                                                <%--<option value="perm">Permissions</option>--%>
                                                <%--<s:if test="viewContestFeeOption">--%>
                                                    <%--<option value="fee">Challenge Fee</option>--%>
                                                <%--</s:if>--%>
                                                <%--<s:if test="syncUser">--%>
                                                    <%--<option value="sync">Synchronize User in JIRA and WIKI</option>--%>
                                                <%--</s:if>--%>
                                            <%--</select>--%>
                                        <%--</span>--%>
    <%--</div>--%>
</div>
<!-- End .areaHeader -->

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
                                <input type="checkbox"
                                       name="pre_${pre.preferenceId}" ${pre.value ? 'checked="checked"' : ''}></input>
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
                <td>
                    <div onclick="showHideGroup(this,'${projectClass}');"
                         class="group expand">${projectNotifications.name}</div>
                </td>
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

                <c:set var="contestCheckTimeId" scope="page"
                       value="contestCheckTimeId${contestNotifications.contestId}"/>
                <c:set var="contestCheckForumId" scope="page"
                       value="contestCheckTimeId${contestNotifications.contestId}"/>

                <tr class="alternate ${projectClass} hide">
                    <td><a class="subgroup shorten"
                           href="../contest/detail?projectId=${contestNotifications.contestId}">${contestNotifications.name}
                        (${contestNotifications.type})</a></td>
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
                               class="selectUser select_forum group_${contestNotifications.forumId}" type="checkbox"
                               style="vertical-align: text-top;"
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

                            <div class="showPages bottom1">
                                <!-- number of rows that can be displayed on the same page -->
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



</div>
<!-- End .container2Content -->
</div>
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


</body>
<!-- End #page -->


</html>
