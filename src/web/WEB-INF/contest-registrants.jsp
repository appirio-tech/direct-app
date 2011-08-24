<%--
  - Author: isv, TCSASSEMBLER
  - Version: 1.0.3
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Contest Registrants list.
  -
  - Version 1.0.1 (Direct Release 6 assembly) changes: fixed bug with displaying the reliability.
  - Version 1.0.2 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  -
  - Version 1.0.3 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Add dashboard header.      
--%>
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
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/contest/dashboard.jsp"/>

                            <div class="container2 tabs3Container" id="ProjectRegistrants" >
                                 	
                                <jsp:include page="includes/contest/tabs.jsp"/>

                                        <div class="resultTableContainer" style="padding-top:20px">

                                                        <table class="projectStats contests resultTable paginatedDataTable"
                                                               cellpadding="0" cellspacing="0">
                                                        <thead>
                                                            <tr>
                                                                <th>Handle</th>
                                                                <th>Rating</th>
                                                                <th>Reliability</th>
                                                                <th>Registration Date</th>
                                                                <th>Submission Date</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <s:iterator value="viewData.contestRegistrants">
                                                                <s:set var="userId" value="userId" scope="page"/>
                                                                <s:set var="handle" value="handle" scope="page"/>
                                                                <s:set var="registrationDate" value="registrationDate"
                                                                       scope="page"/>
                                                                <s:set var="submissionDate" value="submissionDate"
                                                                       scope="page"/>
                                                                <tr>
                                                                    <td class="first">
                                                                        <link:user userId="${userId}" handle="${handle}"/>
                                                                    </td>
                                                                    <td>
                                                                        <s:if test="rating == null">Not Rated</s:if>
                                                                        <s:if test="rating != null">
                                                                            <s:set var="rating" value="rating" scope="page"/>
                                                                            <fmt:formatNumber value="${rating}"
                                                                                              pattern="########0"/>
                                                                        </s:if>
                                                                     </td>
                                                                    <td>
                                                                        <s:if test="reliability == null">n/a</s:if>
                                                                        <s:if test="reliability != null">
                                                                            <s:set var="reliability" value="reliability" scope="page"/>
                                                                            <fmt:formatNumber value="${reliability}"
                                                                                              pattern="######0.##" />%
                                                                        </s:if>
                                                                     </td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${registrationDate ne null}">
                                                                                <fmt:formatDate value="${registrationDate}"
                                                                                                pattern="MM/dd/yyyy HH:mm"/>
                                                                            </c:when>
                                                                            <c:otherwise>&minus;</c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td class="last">
                                                                        <c:choose>
                                                                            <c:when test="${submissionDate ne null}">
                                                                                <fmt:formatDate value="${submissionDate}"
                                                                                                pattern="MM/dd/yyyy HH:mm"/>
                                                                            </c:when>
                                                                            <c:otherwise>&minus;</c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                </tr>
                                                            </s:iterator>
                                                            </tbody>
                                                        </table>

                                                    </div>

                                                    <div class="container2Left">
                                                        <div class="container2Right">
                                                            <div class="container2Bottom">
                                                                <div class="container2BottomLeft">
                                                                    <div class="container2BottomRight">

                                                                        <div class="panel tableControlPanel"></div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- End .container2Content -->

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

<s:form action="contestRegistrants" namespace="/"
        cssStyle="visibility:hidden;display:none;" id="ContestRegistrantsForm" method="get">
    <s:hidden name="formData.excel" id="formDataExcel" value="false" />
    <s:hidden name="formData.contestId"/>
    <s:hidden name="contestId"/>
    <s:hidden name="projectId"/>
</s:form>
</body>
<!-- End #page -->

</html>
