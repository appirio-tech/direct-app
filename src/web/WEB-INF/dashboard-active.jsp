<%--
  - Author: tangzx, GreatKevin
  - Version: 1.4
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (TC Direct "Contest Links and Button" Update 24Hr Assembly) changes:
  - Change the  style for the View/Edit buttons. 
  - Version 1.1.1 (Release Assembly - Direct Improvements Assembly Release 3) changes:
  - Fix the bread crumb link to "Active Contests"
  - Version 1.2 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination) changes:
  - 1) Change the bread crumb of the active contests page
  - 2) Add the hidden column customerId into the active contests table, it will be used to filter active contests.
  -
  - Version 1.3 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination) changes:
  - 1) Add the filter panel for the dashboard active contests.
  -
  - Version 1.4 (Release Assembly - TC Direct Cockpit Release Eight)
  - 1) Add mulestiple active phases to the status column
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="active"/>
	<jsp:include page="includes/paginationSetup.jsp"/>
    <jsp:include page="includes/filterPanel.jsp"/>
<!--[if IE 6]>
    <script type="text/javascript" src="/scripts/DD_belatedPNG.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></script>
    <script type="text/javascript" src="/scripts/ie6.js?v=205148"></script>
<![endif]-->
</head>

<body id="page">



<!-- ie6 notification module -->
<div id="ie6-notification">
    <div class="content">
        <div class="alert">
            <span class="title">You&acute;re using Internet Explorer 6 or Lower. </span>
            <span class="desc">For the full features supported and best experience we strongly recommend upgrading to 
                <strong class="ie8"><a href="javascript:;">Internet Explorer 8</a>
                    <span class="tip">
                        <span class="tipTop"></span>
                        <span class="tipBg">
                            <span class="pic"><img src="/images/ie_icon.png" alt="Internet Explorer 8" /></span>
                            <a href="https://windows.microsoft.com/en-US/internet-explorer/downloads/ie-8" class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>, 
                <strong class="ff"><a href="javascript:;">Firefox 5.0</a>
                    <span class="tip">
                        <span class="tipTop"></span>
                        <span class="tipBg">
                            <span class="pic"><img src="/images/ff_icon.png" alt="Firefox 5.0" /></span>
                            <a href="https://www.mozilla.com/en-US/firefox/fx/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>, 
                <strong class="safari"><a href="javascript:;">Safari 5</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/safari_icon.png" alt="Safari 5.0" /></span>
                                <a href="https://www.apple.com/safari/download/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong> or 
                    <strong class="chrome"><a href="javascript:;">Google Chrome</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/chrome_icon.png" alt="Google Chrome" /></span>
                                <a href="https://www.google.com/chrome/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>
            </span>
        </div>
        <div class="noAsk">
            <a href="javascript:;" class="close">Close</a>
            <div class="clear"></div>
            <div class="noAakInner"><input type="checkbox" id="noAshAgain" /><label>Don&acute;t show again</label>
          </div>
        </div>
    </div>
</div>
<!-- ie6 notification module ends -->

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
                                <strong>All Active Contests</strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Contests</h2>
                            </div>
                            <!-- End .areaHeader -->
                            <form id="filterPanelForm" autocompleted="off">
                            <!-- start filter panel -->
                            <div class='filterPanel' id='activeContestsFilter'>
                                <div class='filterHead'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                        	<div class='filterText'>
                                                <a href='javascript:;' class='collapse'><img src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                <span class='title'>Filter</span>
                                            </div>
                                            <div class='searchContainer'>
                                            	<span class='title'>Search</span>
                                                <div class='filterSearch'>
                                                	<input type='text' class='searchBox' />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end .filterHead-->
                                <div class='filterContent'>
                                	<div class='rightSide'>
                                        <div class='inner'>
                                            <div class='column1'>
                                            	<div class='row'>
                                                	<span class='title short'>Customer</span>
                                                    <select id='customerFilter'>
                                                    	<option value='All Customers'>All Customers</option>
                                                    </select>
                                                </div>
                                                <div class='row'>
                                                	<span class='title short'>Project</span>
                                                    <select id='projectNameFilter'>
                                                    	<option value='All'>All Projects</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <!--end .column1-->
                                            <div class='column2'>
                                            	<div class='row'>
                                                	<span class='title'>Contest Type</span>
                                                    <select id='contestTypeFilter'>
                                                    	<option value='All'>All Contest Types</option>
                                                    </select>
                                                </div>
                                                <div class='row'>
                                                	<span class='title'>Contest Status</span>
                                                    <select id='contestStatusFilter'>
                                                    	<option value='All'>All Contest Status</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <!--end .column2-->
                                            <div class='column3'>
                                            	<div class='row'>
                                                	<span class='title dateLabel'>Start Date</span>
                                                    <input id='startDateBegin' type='text' class="date-pick"/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='startDateEnd' type='text' class="date-pick"/>
                                                </div>
                                                <div class='row'>
                                                	<span class='title dateLabel'>End Date</span>
                                                    <input id='endDateBegin' type='text' class="date-pick"/>
                                                    <span class='title toLabel'>To</span>
                                                    <input id='endDateEnd' type='text' class="date-pick"/>
                                                </div>
                                            </div>
                                            <!--end .column3-->
                                        </div>
                                    </div>
                                </div>
                                <!--end .filterHead-->
                                <div class='filterBottom'><div class='rightSide'><div class='inner'></div></div></div>
                                <!--end .filterBottom-->
                                <div class='collapseBottom hide'><div class='rightSide'><div class='inner'></div></div></div>
                            </div>
                            <!--end .filterPanel-->
                            </form>
                            <div class="container2 resultTableContainer" id="activeContests">
                                <div>
                                    <table id="activeContests" class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0"
                                           cellspacing="0">
                                           
                                        <colgroup>
                                            <col width="9%" />
                                            <col width="14%" />
                                            <col width="12%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="8%" />
                                            <col width="8%" />
                                            <col width="8%" />
                                            <col width="10%" />
                                            <col width="11%" />
                                            <col width=""/>
                                        </colgroup>  
                                        
                                        <thead>
                                            <tr>
                                                <th>Contest Type</th>
                                                <th>Contest Name</th>
                                                <th>Project Name</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
                                                <th class="truncateRegs">Registrants</th>
                                                <th class="truncateSubs">Submissions</th>
                                                <th>Forums</th>
                                                <th>Status</th>
                                                <th></th>
                                                <th class="hide"></th>
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

                                                    <td class="first"><s:property value="contestType.name"/></td>
                                                    <td>
                                                        <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                            <img class="contestTypeIcon" src="/images/<s:property value="contestType.letter"/>_small.png"
                                                                 alt="<s:property value="contestType.letter"/>"/>

                                                        </div>
                                                        <div style="display:table-cell;text-align:left">
                                                            <link:contestDetails contest="${contest}"
                                                                    />
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="display:table-cell;text-align:left">
                                                            <a href="projectOverview?formData.projectId=${contest.project.id}"><s:property value="contest.project.name"/></a>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)
                                                    </td>

                                                     <td>
                                                        <fmt:formatDate value="${endTime}" pattern="MM/dd/yyyy HH:mm"/> ET (GMT-400)
                                                    </td>                                                    
                                                    <td>
                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                            <a href="copilot/listCopilotContestRegistrants.action?projectId=${contest.id}">
                                                                <s:property value="registrantsNumber"/>
                                                            </a>
                                                        </s:if>
                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                            <a href="contest/contestRegistrants.action?projectId=${contest.id}">
                                                                <s:property value="registrantsNumber"/>
                                                            </a>
                                                        </s:if>
                                                       
                                                    </td>
                                                    <td>
                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                            <a href="copilot/listCopilotContestSubmissions.action?projectId=${contest.id}">
                                                                <s:property value="submissionsNumber"/>
                                                            </a>
                                                        </s:if>
                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                            <s:if test="isStudio == true">
                                                            <link:studioSubmissionsGrid contestId="${contest.id}">
                                                                    <s:property value="submissionsNumber"/>
                                                            </link:studioSubmissionsGrid>
                                                            </s:if>
                                                            <s:if test="isStudio == false">
                                                            <link:softwareSubmissionsList contestId="${contest.id}">
                                                                    <s:property value="submissionsNumber"/>
                                                            </link:softwareSubmissionsList>
                                                            </s:if>
                                                        </s:if>
                                                    </td>
                                                    <td>
                                                    <s:if test="forumId != -1">
                                                        <s:if test="isStudio == true"><a href="https://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
                                                        <s:if test="isStudio == false"><a href="https://apps.topcoder.com/forums/?module=Category&categoryID=${forumId}" target="_blank"></s:if>
                                                    </s:if>
                                                    <s:property value="forumPostsNumber"/>
                                                    <s:if test="forumId != -1"></a></s:if>
                                                    </td>
                                                    <td>

                                                        <span
                                                            class="<s:property value="status.shortName"/>"><s:property
                                                            value="status.name"/></span>

                                                        <s:if test="status2 != null">
                                                            <span
                                                                    class="<s:property value="status2.shortName"/>"><s:property
                                                                    value="status2.name"/></span>
                                                        </s:if>

                                                        <s:if test="status3 != null">
                                                            <span
                                                                    class="<s:property value="status3.shortName"/>"><s:property
                                                                    value="status3.name"/></span>
                                                        </s:if>

                                                    </td>
                                                    <td class="last">
                                                        <s:if test="contestType.name == 'Copilot Posting'">
                                                            <a href="copilot/copilotContestDetails?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                        </s:if>
                                                        <s:if test="contestType.name != 'Copilot Posting'">
                                                            <a href="contest/detail?projectId=${contest.id}" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                        </s:if>
                                                    </td>
                                                    <td class="hide"><span><s:property value="contest.customerId"/></span></td>
                                                </tr>
                                            </s:iterator>

                                            <s:iterator value="viewData.projectBugRaces"
                                                        status="status">
                                                <tr>

                                                    <td class="first">Bug Race</td>
                                                    <td>
                                                        <div style="display: table-cell; vertical-align: middle; padding-left:5px; padding-right:5px">
                                                            <img src="/images/br_small.png"
                                                                 alt="BR"/>

                                                        </div>
                                                        <div style="display:table-cell;text-align:left">
                                                            <a href="<s:property value='issueLink'/>"><s:property value='issueKey'/> <s:property value='title'/></a>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="display:table-cell;text-align:left">
                                                            <a href="projectOverview?formData.projectId=${directProjectId}"><s:property value="directProjectName"/></a>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${creationDate}"/> ET (GMT-400)
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${endDate}"/> ET (GMT-400)
                                                    </td>

                                                    <td>
                                                        <a href="<s:property value='issueLink'/>">
                                                            <s:property value="votesNumber"/>
                                                        </a></td>
                                                    <td>
                                                        n/a
                                                    </td>
                                                    <td>
                                                        <a href="<s:property value='issueLink'/>">0</a>
                                                    </td>
                                                    <td><span
                                                            class="<s:property value="contestLikeStatusClass"/>"><s:property
                                                            value="contestLikeStatus"/></span></td>
                                                    <td class="last">
                                                        <a href="<s:property value='issueLink'/>" class="button1"><span class="btnR"><span class="btnC"><span class="btnIcon">View/Edit</span></span></span></a>
                                                    </td>
                                                    <td class="hide"><span><s:if test="clientId <= 0">none</s:if><s:else><s:property value='clientId'/></s:else></span></td>
                                                </tr>
                                            </s:iterator>

                                        </tbody>
                                    </table>
                                    <!-- End .projectsStats -->

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
                            </div>
                            <!-- End .container2 -->
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
