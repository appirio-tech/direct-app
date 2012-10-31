<%--
  - Author: GreatKevin
  - Version: 1.1
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
  - - Added copilot posting dashboard
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="PAGE_TYPE" value="copilot" scope="request"/>
<c:set var="CURRENT_TAB" value="copilotPostings" scope="request"/>
<c:set var="CURRENT_SUB_TAB" value="copilotContestRegistrants" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/direct/copilot/copilotPosting.css" media="all" type="text/css"/>
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
                                <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a> &gt;
                                <strong>My Copilot Selection Contests</strong>
                            </div>
                            <!-- End .currentPage -->

                            <div id="copilotsIntroduction">
                                <div class="orderReview">

                                    <div class="myCopilotsContests">
                                        <span class="introductionHeadIcon">
                                            <img src="/images/copilot_contests_icon.png" alt="copilot contests"/></span>

                                        <h2 class="sectionHead"><c:out value="${viewData.contestStats.contest.title}"/></h2>
                                    </div>
                                    <!-- end .getCopilots -->

                                    <div class="myCopilotsContestsList">

                                        <jsp:include page="includes/copilot/copilot-dashboard.jsp"/>

                                        <div class="container2 tabs3Container tabs3Special" id="CopilotPostingRegistrants">

                                            <jsp:include page="includes/copilot/tabs.jsp"/>

                                            <div class="container2Left">
                                                <div class="container2Right">
                                                    <div class="container2Bottom">
                                                        <div class="container2BottomLeft">
                                                            <div class="container2BottomRight">
                                                                <div class="tableContainer">
                                                                    <table class="projectStats newContestsStatus paginatedDataTable"
                                                                           cellpadding="0" cellspacing="0">
                                                                        <thead>
                                                                        <tr>
                                                                            <th>Copilot</th>
                                                                            <th>Profiles</th>
                                                                            <th>Registration Time</th>
                                                                            <th>Submission Time</th>
                                                                        </tr>
                                                                        </thead>
                                                                        <tbody>

                                                                        <c:forEach items="${viewData.contestRegistrants}" var="registrant" varStatus="loop">
                                                                            <tr>
                                                                                <td class="photo">
                                                                                    <img src="/images/photo_people.png"
                                                                                         alt="photo"/>
                                                                                    <span><c:out value="${registrant.handle}"/></span>
                                                                                </td>
                                                                                <td>
                                                                                    <a rel="_blank"
                                                                                       href="https://www.topcoder.com/tc?module=MemberProfile&amp;cr=${registrant.userId}"
                                                                                       class="memberProfile">
                                                                                        <span class="profileLeft">Member Profile</span></a>
                                                                                    <a rel="_blank"
                                                                                       href="https://www.topcoder.com/tc?module=ViewCopilotProfile&amp;pid=${registrant.userId}"
                                                                                       class="copilotProfile">
                                                                                        <span class="profileLeft">Copilot Profile</span></a>
                                                                                    <!--this link should be replaced by Copilot Profile page-->
                                                                                </td>
                                                                                <td>
                                                                                    <fmt:formatDate
                                                                                            value="${registrant.registrationDate}"
                                                                                            pattern="MM/dd/yyyy HH:mm"/>
                                                                                </td>
                                                                                <td>
                                                                                    <fmt:formatDate
                                                                                            value="${registrant.submissionDate}"
                                                                                            pattern="MM/dd/yyyy HH:mm"/>
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>

                                                                        </tbody>
                                                                    </table>
                                                                    <!-- End .projectsStats -->
                                                                </div>

                                                                <div class="panel"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- end .getCopilotsStep -->


                                </div>
                                <!-- end .orderReview -->


                            </div>
                            <!-- end #copilotsIntroduction -->

                            <!-- end #launchContestOut -->
                        </div>
                        <!-- End.area1Content -->
                    </div>
                    <!-- End #area1 -->

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
<!-- this area will contain the popups of this page -->
<div class="popups">
</div>
<!-- End .popups -->
<jsp:include page="includes/popups.jsp"/>
</body>
<!-- End #page -->
</html>
