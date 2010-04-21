<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="registrants"/>
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
                                <a href="<s:url action="currentProjectDetails" namespace="/"/>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/contest/contestStats.jsp"/>

                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">
                                                    <div class="container2Content">
                                                        <table class="projectStats contests paginatedDataTable"
                                                               cellpadding="0" cellspacing="0">
                                                        <thead>
                                                            <tr>
                                                                <th>Handle</th>
                                                                <th>Rated</th>
                                                                <th>Register By</th>
                                                                <th>Submitted By</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <s:iterator value="viewData.contestRegistrants">
                                                                <s:set var="userId" value="userId" scope="page"/>
                                                                <s:set var="handle" value="username" scope="page"/>
                                                                <s:set var="registrationDate" value="registrationDate"
                                                                       scope="page"/>
                                                                <s:set var="submissionDate" value="submissionDate"
                                                                       scope="page"/>
                                                                <tr>
                                                                    <td>
                                                                        <link:user userId="${userId}" handle="${handle}"/>
                                                                    </td>
                                                                    <td>
                                                                        <s:if test="rated == false">Not</s:if>
                                                                        Rated
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatDate value="${registrationDate}"
                                                                                        pattern="MM/dd/yyyy HH:mm"/>
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatDate value="${submissionDate}" 
                                                                                        pattern="MM/dd/yyyy HH:mm"/>
                                                                    </td>
                                                                </tr>
                                                            </s:iterator>
                                                            </tbody>
                                                        </table>

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