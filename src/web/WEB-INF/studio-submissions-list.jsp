<%--
  - Author: isv, flexme, TCSDEVELOPER
  - Version 1.3 (Direct Submission Viewer Release 4 ) change notes: added Winners section.
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: include the contestVars.jsp.
  -
  - Version: 1.3
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the submissions for Studio contest in a list view.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/contest/submissionViewer/submissionViewerHtmlHead.jsp"/>
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
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property
                                        value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->
                            <jsp:include page="includes/contest/contestStats.jsp"/>
                            <!-- End .projectsStats -->
                            <div class="container2 tabs3Container">
                                <jsp:include page="includes/contest/tabs.jsp"/>
                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2BottomClear">
                                            <div class="containerNoPadding">
                                                <jsp:include page="includes/contest/submissionViewer/winners.jsp"/>
                                                <jsp:include page="includes/contest/submissionViewer/bankSelection.jsp"/>
                                                <jsp:include page="includes/contest/submissionViewer/slotTitle.jsp"/>

                                                <div id="submissionList">
                                                    <table>
                                                        <thead>
                                                        <tr>
                                                            <th class="first"></th>
                                                            <th>Thumbnail</th>
                                                            <th><span class="ascending">Submission ID </span></th>
                                                            <th>Bank Location</th>
                                                            <th>What do you want to do?</th>
                                                            <th class="last"></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <s:iterator value="viewData.contestSubmissions">
                                                            <s:set var="contestId" value="contestId" scope="page"/>
                                                            <s:set var="submissionId" value="submissionId" scope="page"/>
                                                            <s:set var="submission" value="top" scope="page"/>
                                                            <ui:studioSubmissionListItem contestId="${contestId}"
                                                                                         submission="${submission}"/>
                                                        </s:iterator>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <!-- End #submissionList -->

                                                <jsp:include page="includes/contest/submissionViewer/pagination.jsp"/>

                                            </div>
                                            <!-- End .container2Content -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->

                            <jsp:include page="includes/contest/submissionViewer/paginationDropdown.jsp"/>

                            <a href="javascript:;" class="button1 backToTop"><span>Back To Top</span></a>
                        </div>
                    </div>

                    <div class="clear"></div>

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

<jsp:include page="includes/contest/submissionViewer/contestVars.jsp"/>
<jsp:include page="includes/popups.jsp"/>
</body>
<!-- End #page -->

</html>