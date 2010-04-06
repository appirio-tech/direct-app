<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="contests"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong>Contests</strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Contests</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/project/projectStats.jsp"/>

                            <div class="container2">
                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">
                                                    <div class="container2Content">

                                                        <table class="projectStats contests paginatedDataTable" cellpadding="0"
                                                               cellspacing="0">
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Contest Type</th>
                                                                <th>Contest Name</th>
                                                                <th>Start/End</th>
                                                                <th>Registrants</th>
                                                                <th>Submissions</th>
                                                                <th>Forums</th>
                                                                <th>Status</th>
                                                                <th></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>

                                                            <s:iterator value="viewData.projectContests.contests"
                                                                        status="status">
                                                                <s:set value="contest" var="contest" scope="page"/>
                                                                <s:set value="startTime" var="startTime" scope="page"/>
                                                                <s:set value="endTime" var="endTime" scope="page"/>
                                                                <tr>
                                                                    <td><s:property value="#status.index + 1"/></td>
                                                                    <td><s:property value="contestType.name"/></td>
                                                                    <td><link:contestDetails contest="${contest}"/></td>
                                                                    <td><fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy HH:mm zzz"/>
                                                                        <c:out value="${tcdirect:getEndText(endTime)}"/></td>
                                                                    <td><s:property value="registrantsNumber"/></td>
                                                                    <td><s:property value="submissionsNumber"/></td>
                                                                    <td><s:property value="forumPostsNumber"/></td>
                                                                    <td><span class="<s:property value="status.shortName"/>"><s:property value="status.name"/></span></td>
                                                                    <td>
                                                                        <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
                                                                           class="button1 button"><span><s:property value="status.actionText"/></span></a>
                                                                    </td>
                                                                </tr>
                                                            </s:iterator>

                                                            </tbody>
                                                        </table>
                                                        <!-- End .projectsStats -->

                                                        <div class="panel">
                                                            <!-- this area contains the print, export to excel, export to pdf links -->
                                                            <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
                                                               class="exportPdf">Export to <strong>PDF</strong></a>
                                                            <span>|</span>
                                                            <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
                                                               class="exportExcel">Export to <strong>Excel</strong></a>
                                                            <span>|</span>
                                                            <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
                                                               class="print">Print</a>
                                                        </div>
                                                        <!-- End .panel -->

                                                    </div>
                                                    <!-- End .container2Content -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->
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