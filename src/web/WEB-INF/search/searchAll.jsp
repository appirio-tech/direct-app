<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Module Assembly - TopCoder Cockpit Instant Search)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of search all.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="searchAll"/>
</head>
<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="searchAllResults">
                <jsp:include page="../includes/header.jsp"/>
                <!-- End #header -->
                <input type="hidden" name="searchAllKey" value="${searchKey}"/>
                <input type="hidden" name="isTCAdmin" value="${tCAdmin}"/>
                <div id="mainContent" class="resultsArea">
                    <div class="filterArea firstOn">
                        <ul class="resultsFilter">
                            <li class="first on"><a href="javascript:;">All Results</a></li>
                            <li><a href="javascript:;">Challenges</a></li>
                            <li><a href="javascript:;">Projects</a></li>
                            <li class="last"><a href="javascript:;">Features</a></li>
                        </ul>
                    </div>

                    <div class="resultsList">
                        <h2>All Results</h2>
                        <h3 class="typeContests">Challenges</h3>
                        <ul class="typeContests horizontalLayout">
                            <s:iterator value="contestsSearchResult">
                                <li>
                                    <p class="resultTilte"><a target="_blank" href="<s:url namespace='contest' action='detail'><s:param name="projectId" value="contestId"/></s:url>"><s:property value="contestName"/></a></p>
                                    <p class="resultBrief"><s:property value="contestTypeName"/>, <a  target="_blank" class="searchAllSmallLink" href="<s:url action='projectOverview'><s:param name="formData.projectId" value="projectId"/></s:url>"><s:property value="projectName"/></a></p>
                                </li>
                            </s:iterator>
                            <s:if test="contestsSearchResult.size() == 0">
                                <li class="noMatched">No Matched Records</li>
                            </s:if>
                        </ul>
                        <h3 class="typeProjects">Projects</h3>
                        <ul class="typeProjects horizontalLayout">
                            <s:iterator value="projectsSearchResult">
                                <li>
                                    <p class="resultTilte"><a  target="_blank" href="<s:url action='projectOverview'><s:param name="formData.projectId" value="projectId"/></s:url>"><s:property value="projectName"/></a></p>
                                    <p class="resultBrief"><s:property value="projectTypeLabel"/>
                                        <s:if test="clientName != null">
                                            , <s:property value="clientName"/></p>
                                        </s:if>
                                </li>
                            </s:iterator>
                            <s:if test="projectsSearchResult.size() == 0">
                                <li class="noMatched">No Matched Records</li>
                            </s:if>
                        </ul>
                        <h3 class="typeFeatures">Features</h3>
                        <ul class="typeFeatures verticalLayout">
                            <li class="noMatched">No Matched Records</li>
                        </ul>


                    </div>
                </div>
                <!-- End #resultsArea -->

                <jsp:include page="../includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->

    </div>
</div>
<!-- End #wrapper -->
<jsp:include page="../includes/footerScripts.jsp"/>
</body>

</html>
