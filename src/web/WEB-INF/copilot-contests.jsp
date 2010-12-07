<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="PAGE_TYPE" value="copilot" scope="request"/>
<c:set var="CURRENT_TAB" value="copilotPostings" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" style="overflow:visible">

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

                                        <h2 class="sectionHead">My Copilot Selection Contests</h2>
                                    </div>
                                    <!-- end .getCopilots -->

                                    <div class="myCopilotsContestsList" id="MyCopilotPostings">

                                        <table id="tableSorterUsed"
                                               class="projectStats contests contestsStatus paginatedDataTable"
                                               cellpadding="0" cellspacing="0">
                                            <thead>
                                            <tr>
                                                <th class="copilotPosting">Copilot posting</th>
                                                <th class="projectName">Project name</th>
                                                <th class="projectStatus">Status</th>
                                                <th class="currentPhase">Current Phase</th>
                                                <th class="{sorter: false}">&nbsp;</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${viewData.contests}" var="contest" varStatus="loop">
                                                <tr>
                                                    <td class="copilotPosting">
                                                        <a href="<s:url namespace="/copilot" action="copilotContestDetails">
                                                                     <s:param name="projectId" value="%{#attr['contest'].id}"/>
                                                                 </s:url>">
                                                            <c:out value="${contest.title}"/></a>
                                                    </td>
                                                    <td class="projectName">
                                                        <c:out value="${contest.project.name}"/>
                                                    </td>
                                                    <td class="projectStatus">
                                                        <if:isActive typedContestBrief="${contest}">
                                                            <span class="running">Active</span>
                                                        </if:isActive>
                                                        <if:isActive typedContestBrief="${contest}" negate="true">
                                                            <span class="${contest.status.shortName}">
                                                                <c:out value="${contest.status.name}"/></span>
                                                        </if:isActive>
                                                    </td>
                                                    <td class="currentPhase">
                                                        <c:choose>
                                                            <c:when test="${empty contest.currentPhases}">&nbsp;</c:when>
                                                            <c:otherwise>
                                                                <c:out value="${contest.currentPhases[0].phaseName}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="currentCommand">
                                                        <if:isDraft typedContestBrief="${contest}">
                                                            <a href="<s:url namespace="/copilot" action="copilotContestDetails">
                                                                       <s:param name="projectId" value="%{#attr['contest'].id}"/>
                                                                     </s:url>"
                                                               class="button1"><span>View/Edit</span>
                                                            </a>
                                                        </if:isDraft>
                                                        <if:isActive typedContestBrief="${contest}">
                                                            <if:isInRegistrationOrSubmissionPhase phasedContest="${contest}">
                                                                <a href="<s:url namespace="/copilot" action="listCopilotContestRegistrants">
                                                                           <s:param name="projectId" value="%{#attr['contest'].id}"/>
                                                                         </s:url>"
                                                                   class="button1"><span>View</span></a>
                                                            </if:isInRegistrationOrSubmissionPhase>
                                                            <if:isInReviewPhase phasedContest="${contest}">
                                                                <a href="<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                                                                             <s:param name="projectId" value="%{#attr['contest'].id}"/>
                                                                         </s:url>"
                                                                   class="button1"><span>Review</span>
                                                            </if:isInReviewPhase>
                                                        </if:isActive>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                            </tbody>
                                        </table>
                                        </div>
                                   </div>
                                    <!-- end .getCopilotsStep -->
                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">

                                                        <div class="panel"></div>

                                                    </div>
                                            </div>
                                        </div>
                                    </div>
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
