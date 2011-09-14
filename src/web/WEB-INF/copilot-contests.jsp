<%--
  - Author: TCSDEVELOPER, tangzx
  - Version: 1.1
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly) Change notes:
  - Apply to new prototype.
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

                                        <h2 class="sectionHead">My Copilot Selection Contests</h2>
                                    </div>
                                    <!-- end .getCopilots -->

                                    <div class="myCopilotsContestsList" id="MyCopilotPostings">

                                        <table id="tableSorterUsed"
                                               class="projectStats contests contestsStatus paginatedDataTable"
                                               cellpadding="0" cellspacing="0">

                                            <colgroup>
                                                <col width="24%" />
                                                <col width="18%" />
                                                <col width="9%" />
                                                <col width="9%" />
                                                <col width="8%" />
                                                <col width="8%" />
                                                <col width="8%" />
                                                <col width="9%" />
                                                <col width="9%" />

                                            </colgroup>                                               
                                            
                                            <thead>
                                            <tr>
                                                <th class="sorting_asc alignLeft">Copilot Posting</th>
                                                <th class="sorting alignLeft">Project Name</th>
                                                <th class="sorting">Start Date</th>
                                                <th class="sorting">End Date</th>
                                                <th class="sorting truncateRegs">Registrants</th>
                                                <th class="sorting truncateSubs">Submissions </th>
                                                <th class="sorting">Forums</th>
                                                <th class="sorting">Status</th>
                                                <th class="{sorter: false} sorting">&nbsp;</th>                                                
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${viewData.contests}" var="contest" varStatus="loop">
                                            
                                                <tr>
                                                    <td class="alignLeft first">
                                                        <a href="<s:url namespace="/copilot" action="copilotContestDetails">
                                                                     <s:param name="projectId" value="%{#attr['contest'].contest.id}"/>
                                                                 </s:url>">
                                                            <c:out value="${contest.contest.title}"/></a>
                                                    </td>
                                                    <td class="alignLeft">
                                                        <a href="../projectOverview?formData.projectId=${contest.contest.project.id}">${contest.contest.project.name}</a>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${contest.startTime}"/> ET (GMT-400)
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${contest.endTime}"/> ET (GMT-400)
                                                    </td>
                                                    <td>
                                                        <a href="<s:url namespace="/copilot" action="listCopilotContestRegistrants">
                                                                <s:param name="projectId" value="%{#attr['contest'].contest.id}"/>
                                                            </s:url>">
                                                            ${contest.registrantsNumber}
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                                                                <s:param name="projectId" value="%{#attr['contest'].contest.id}"/>
                                                            </s:url>">
                                                            ${contest.submissionsNumber}
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <s:if test="%{#attr['contest'].forumId != -1}">
                                                            <a href="https://apps.topcoder.com/forums/?module=Category&categoryID=${contest.forumId}" target="_blank">
                                                                ${contest.forumPostsNumber}
                                                            </a>
                                                        </s:if>
                                                        <s:else>
                                                            ${contest.forumPostsNumber}
                                                        </s:else>
                                                    </td>
                                                    <td>
                                                        <s:if test="%{#attr['contest'].status.name == 'Completed'}">
                                                            Completed
                                                        </s:if>
                                                        <s:if test="%{#attr['contest'].status.shortName == 'draft' || #attr['contest'].status.shortName == 'running'}">
                                                            <span class="${contest.status.shortName}">
                                                                ${contest.status.name}
                                                            </span>
                                                        </s:if>
                                                    </td>
                                                    <td class="last">
                                                        <s:if test="%{#attr['contest'].status.shortName == 'draft' || #attr['contest'].status.shortName == 'running'}">
                                                            <a href="<s:url namespace="/copilot" action="copilotContestDetails">
                                                                       <s:param name="projectId" value="%{#attr['contest'].contest.id}"/>
                                                                     </s:url>"
                                                               class="button11">
							                                    <span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span>
                                                            </a>
                                                        </s:if>
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

                                                        <div class="panel copilotPostingsPanel"></div>

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
