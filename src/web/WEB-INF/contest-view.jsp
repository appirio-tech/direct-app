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
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
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

                                                        <table class="projectStats contests paginatedDataTable"
                                                               cellpadding="0"
                                                               cellspacing="0">
                                                        <thead>
                                                            <tr>

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
                                                                <s:set value="contestType" var="contestType"
                                                                       scope="page"/>
                                                                <tr>

                                                                    <td><s:property value="contestType.name"/></td>
                                                                    <td>
                                                                        <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                                            <img src="/images/<s:property value="contestType.letter"/>_small.png"
                                                                                 alt="<s:property value="contestType.letter"/>"/>

                                                                        </div>
                                                                        <div style="display:table-cell;text-align:left">
                                                                            <link:contestDetails contest="${contest}"
                                                                                    />
                                                                        </div>
                                                                    </td>
                                                                    <td><fmt:formatDate value="${startTime}"
                                                                                        pattern="MM/dd/yyyy HH:mm zzz"/>
                                                                        <c:out value="${tcdirect:getEndText(endTime)}"/></td>
                                                                    <td>
                                                                        <a href="contestRegistrants.action?formData.contestId=4">
                                                                    <s:property value="registrantsNumber"/>
                                                                        </a></td>
                                                                    <td><s:property value="submissionsNumber"/></td>
                                                                    <td>
																	<s:if test="forumId != -1"><a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
																	<s:property value="forumPostsNumber"/>
																	<s:if test="forumId != -1"></a></s:if>
																	</td>
                                                                    <td><span
                                                                            class="<s:property value="status.shortName"/>"><s:property
                                                                            value="status.name"/></span></td>
                                                                    <td>
                                                                        <a href="contest/detail?contestId=${contest.id}"
                                                                           class="button1 button"><span>View/Edit</span></a>
                                                                    </td>
                                                                </tr>
                                                            </s:iterator>

                                                            </tbody>
                                                        </table>
                                                        <!-- End .projectsStats -->

                                                        <div class="panel">
                                                            <!-- this area contains the print, export to excel, export to pdf links -->
                                                            <a href="" onclick="return false;"
                                                               class="exportPdf">Export to <strong>PDF</strong></a>
                                                            <span>|</span>
                                                            <a href="" onclick="return false;"
                                                               class="exportExcel">Export to <strong>Excel</strong></a>
                                                            <span>|</span>
                                                            <a href="" onclick="return false;"
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
