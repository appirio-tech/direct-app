<%--
  - Author: BeBetter, isv, TCSASSEMBLER
  - Version: 1.4
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
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
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="css/dashboard-ie7.css" />
    <![endif]-->    
    <link rel="stylesheet" href="/css/dashboard-view.css?v=204285" media="all" type="text/css" />
    
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="details"/>

    <script type="text/javascript">
    //<![CDATA[
        var paramContestId = ${param.projectId};
        
        var hasContestWritePermission = ${viewData.hasContestWritePermission};
    //]]>
    </script>
    <script type="text/javascript" src="/scripts/launch/entity.js?v=209437"></script>
    <script type="text/javascript" src="/scripts/launch/main.js?v=209174"></script>
    <script type="text/javascript" src="/scripts/launch/contestDetailSoftware.js?v=210135"></script>
    <script type="text/javascript" src="/scripts/repostcontest.js?v=207440"></script>
</head>

<body id="page">
<a name="top" style="width:0px;height:0px;"></a>
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
                        <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                        <strong style="word-wrap:break-word;"><s:property value="viewData.contestStats.contest.title"/></strong>
                    </div>
                    <div class="areaHeader">
                        <h2 class="title contestTitle" style="background:url('/images/<s:property value="viewData.contest.contestType.letter"/>.png') no-repeat scroll left center transparent">
                        <s:property value="viewData.contestStats.contest.title"/>



                        </h2>
                    </div><!-- End .areaHeader -->

                    <jsp:include page="includes/contest/dashboard.jsp"/>

                    <div class="container2 tabs3Container">

                        <jsp:include page="includes/contest/tabs.jsp"/>

                        <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
                            <div class="container2BottomLeft"><div class="container2BottomRight">

                            <div class="container2Content_det">

                            <s:if test="software">
                               <jsp:include page="includes/contest/editTabSoftware.jsp"/>
                            </s:if>
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
