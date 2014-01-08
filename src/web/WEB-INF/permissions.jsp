<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="permissions"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">
                    <div class="currentPage">
                        <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                        <strong>Permissions</strong>
                    </div>
                    <div class="areaHeader">
                        <h2 class="title contestTitle">Permissions</h2>
                    </div><!-- End .areaHeader -->

                    <table class="projectStats" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Start Date</th>
                                <th>Drafts</th>
                                <th>Running</th>
                                <th>Pipeline</th>
                                <th>Finished</th>
                                <th>Tasks</th>
                                <th>Upcoming Schedule</th>
                                <th>Project Fees Running/Finalized</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="date">01/22/2010</td>
                                <td>10</td>
                                <td>5</td>
                                <td>22</td>
                                <td>12</td>
                                <td>8</td>
                                <td class="date fees">00/00/0000 00:00</td>
                                <td class="date fees">$ 00,000 / $ 00,000</td>
                            </tr>
                        </tbody>
                    </table><!-- End .projectsStats -->

                    <div class="container2">
                        <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
                            <div><div>
                                <div class="container2Content">
                                    <div class="search">
                                        <a href="#" class="button1"><span>Search</span></a><input type="text" /><label>Search User:</label>
                                    </div>
                                    <table id="permissions" class="projectStats contests" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Select All <br /><input type="checkbox" onclick="checkAll(this,'permissions')"/></th>
                                                <th>Group/User</th>
                                                <th>Challenge</th>
                                                <th class="permCol">Permissions</th>
                                            </tr>
                                        </thead>
                                        <tbody class="checkPermissions">
                                            <tr>
                                                <td class="checkbox"><input type="checkbox" class="selectUser"/></td>
                                                <td><a href="javascript:;" class="group expand" onclick="showHideGroup(this,'group1');">Group Name</a></td>
                                                <td><a href="<s:url action="projectOverview" namespace="/"/>">Challenge Name</a></td>
                                                <td></td>
                                            </tr>
                                            <tr class="group1 hide"><!-- this row belong to the group1 -->
                                                <td class="checkbox"><input type="checkbox" class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group1 hide"><!-- this row belong to the group1 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group1 hide"><!-- this row belong to the group1 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group1 hide"><!-- this row belong to the group1 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="javascript:;" class="group expand" onclick="showHideGroup(this,'group2');">Group Name</a></td>
                                                <td><a href="<s:url action="projectOverview" namespace="/"/>">Challenge Name</a></td>
                                                <td></td>
                                            </tr>
                                            <tr class="group2 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group2 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group2 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox" class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group2 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="javascript:;" class="group expand" onclick="showHideGroup(this,'group3');">Group Name</a></td>
                                                <td><a href="<s:url action="projectOverview" namespace="/"/>">Challenge Name</a></td>
                                                <td></td>
                                            </tr>
                                            <tr class="group3 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group3 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox" class="selectUser" /></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" /><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                            <tr class="group3 hide"><!-- this row belong to the group2 -->
                                                <td class="checkbox"><input type="checkbox"  class="selectUser"/></td>
                                                <td><a href="#">Handle Name</a></td>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox"/><label>Read</label>
                                                    <input type="checkbox" /><label>Write</label>
                                                    <input type="checkbox" /><label>Full Access</label>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table><!-- End .projectsStats -->
                                    <div class="pagination">
                                        <div class="pages">
                                            <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
                                            <a href="#" class="current">1</a>
                                            <a href="#">2</a>
                                            <a href="#" class="next">Next</a>											</div><!-- End .pages -->
                                        <div class="showPages"><!-- number of rows that can be displayed on the same page -->
                                            <label><strong>Show:</strong></label>
                                            <select>
                                                <option>10</option>
                                                <option>25</option>
                                                <option>50</option>
                                                <option>All</option>
                                            </select>
                                            <span>per page</span>											</div>
                                        <!-- End .showPages -->
                                    </div>
                                    <!-- End .pagination -->

                                    <div class="panel"><!-- this area containt the print, export to excel, export to pdf links -->
                                        <a href="TB_inline?height=90&amp;width=300&amp;inlineId=deleteUserPopup" class="deleteUser thickbox">Delete Group/User</a> <span>|</span>
                                        <a href="#" class="editUser">Edit Group/User</a> <span>|</span>
                                        <a href="#" class="addUser">Add Group/User</a>
                                    </div><!-- End .panel -->

                                </div><!-- End .container2Content -->
                            </div></div>
                        </div></div></div>
                    </div><!-- End .container2 -->
                    </div>
                </div>
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
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>