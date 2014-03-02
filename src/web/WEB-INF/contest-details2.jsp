<%--
  - Author: BeBetter, isv, GreatKevin, Ghost_141, Veve
  - Version: 1.10
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - add repost and new version buttons and assoicated js file.
  - Version 1.2 - Direct - Contest Dashboard Assembly Change Note
  - - replaced contest stats area with contest dashboard area.
  - Version 1.3 - TC Direct Replatforming Release 1
  - - Studio contest is the nearly the same as software contest. Don't use special javascript file for studio contest.
  - Version 1.3 - TC Direct Release Assembly 7 Change Note
  - - added hasContestWritePermission field.
  - Version 1.4 - TC Direct "Contest Links and Button" Update 24Hr Assembly Change Note
  - - removed the 'View Contest', 'Repost Contest', 'New Version' links.
  - Version 1.5 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Change the use of %{#session.currentSelectDirectProjectID} to sessionData.currentSelectDirectProjectID so the JSP
  -   page can access the session on the first hit.
  - Version 1.6 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Remove the container2BottomLeft and container2BottomRight class in pagination part.
  - Version 1.7 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
  - - Adds support for marathon contest details page.
  - Version 1.8 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
  - - Add support for CCA specific on billing accounts
  - Version 1.9 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress) change notes: 
  - - Add support for marathon match contest.
  - Version 1.10 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab) change notes:
  - - Update dashboard part to show the real time line graph.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/marathonMatches-ie7.css"/>
    <![endif]-->    
    <link rel="stylesheet" href="/css/direct/dashboard-view.css?v=212459" media="all" type="text/css" />

    <!-- New Style For Marathon Matches -->
    <link rel="stylesheet" href="/css/direct/marathonMatches.css" media="all" type="text/css"/>
    
    <ui:projectPageType tab="gameplan"/>
    <ui:contestPageType tab="details"/>

    <script type="text/javascript">
    //<![CDATA[
        var paramContestId = ${param.projectId};
        
        var hasContestWritePermission = ${viewData.hasContestWritePermission};
    //]]>
    </script>
    <script type="text/javascript" src="/scripts/launch/entity.js?v=215011"></script>
    <script type="text/javascript" src="/scripts/launch/main.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/launch/contestDetailSoftware.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/repostcontest.js?v=207440"></script>

    <!-- New Script For Marathon Matches -->
    <script type="text/javascript" src="/scripts/highcharts.js"></script>
    <script type="text/javascript" src="/scripts/marathonMatches.js"></script>
    <script>
        <c:if test="${hasRoundId}">
            var timeLineData = ${viewData.timeLineGraphData};
        </c:if>
    </script>
</head>

<body id="page">
<a name="top" style="width:0px;height:0px;"></a>
<div id="wrapper" style="min-width: 1100px;">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <!-- set min-width to 1000px to make sure the timeline displayed correctly -->
                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">
                    <div class="currentPage">
                        <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                        <strong style="word-wrap:break-word;"><s:property value="viewData.contestStats.contest.title"/></strong>
                    </div>

                    <div class="areaHeader">
                        <h2 class="title contestTitle" style="background:url('/images/<s:if test="marathon">icon-marathon</s:if><s:else><s:property value="viewData.contest.contestType.letter"/></s:else>.png') no-repeat scroll left center transparent">
                        <s:property value="viewData.contestStats.contest.title"/>  <img id="contestLoading" style="display:none" src="/images/dots-white.gif"/>
                            <a href="javascript:activateContestEdit();" class="activateButton" style="float:right;display: none"></a>
                        </h2>

                    </div><!-- End .areaHeader -->


                    <jsp:include page="includes/contest/dashboard.jsp"/>

                    <div class="container2 tabs3Container">

                        <jsp:include page="includes/contest/tabs.jsp"/>

                        <div class="container2Left"><div class="container2Right"><div class="container2Bottom" style="background: url(../../images/container2_bottom.png) repeat-x bottom;">
                            <div><div>

                            <div class="container2Content_det contestDetailsContainer">
                            <script>
                               var billingAccounts = [];
                               <s:iterator value="billingAccountsForProject">
                               billingAccounts.push({"id": "<s:property value="id" />",
                            	    "name" : "<s:property value="name" />",
                            	    "cca" : "<s:property value="cca" />"
                               });
                               </s:iterator>
                            </script>
                            <s:if test="marathon">
                               <jsp:include page="includes/contest/editTabMarathon.jsp"/>
                            </s:if>
                            <s:elseif test="software">
                                <jsp:include page="includes/contest/editTabSoftware.jsp"/>
                            </s:elseif>
                            <s:else>
                               <jsp:include page="includes/contest/editTab.jsp"/>
                            </s:else>

                            </div><!-- End .container2Content_det -->

                            </div></div>
                        </div></div></div>
                    </div><!-- End .container2 -->

                    <a href="#top" class="button1 backToTop"><span>Back To Top</span></a>
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
