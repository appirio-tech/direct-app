<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<%--@elvariable id="notifications" type="java.util.List<com.topcoder.service.facade.contest.notification.ProjectNotification>"--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <script type="text/javascript" src="/scripts/notifications.js"></script>

    <script type="text/javascript">
        $(function() {
            var limit = 50;
            $(".shorten").each(function() {
                var chars = $(this).text();
                if (chars.length > limit) {
                    $(this).empty().append(chars.substr(0, limit-1)).append("... ");
                }
            });
        });
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
                        <div class="area1Content">
							<div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong>Notifications</strong>
                            </div>

							<div class="areaHeader">
								<h2 class="title notificationTitle">Notifications Setting</h2>
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
                                                            <td><a class="subgroup shorten" href="contestDetails.action?formData.contestId=${contestNotifications.contestId}">${contestNotifications.name} (${contestNotifications.type})</a></td>
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

                </div>
                <!-- End #mainContent -->

                <jsp:include page="/WEB-INF/includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>

</body>
<!-- End #page -->


</html>