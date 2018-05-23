<%--
  - Author: TCSASSEMBLER
  -
  - The jsp for the project contests calendar.
  -
  - Version 1.0 (Module Assembly - TC Cockpit Project Contests Batch Edit ) changes:
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="gameplan"/>
    <link rel="stylesheet" href="/css/direct/fullcalendar-1.5.2.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/fullcalendar-1.5.2.min.js"></script>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="includes/header.jsp"/>

<div id="mainContent" class="newSidebarCollapse">

<jsp:include page="includes/right.jsp"/>

<div id="area1"><!-- the main area -->
<div class="area1Content" style="position: relative;">
<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
    <span id="currentDirectProjectID" class="hide"><s:property value="formData.projectId"/></span>
</div>

<div class="batchButtons">
    <!--<a class="batchCreate" href="javascript:;">Batch Create</a>-->
    <a class="batchEdit" href="<s:url action="batchDraftContestsEdit" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>">Batch Edit</a>
</div>

<div class="contestViews">
<div class="areaHeader">
    <div class="calendarLegends">
        <span class="legendColor draftLegendColor"></span><span class="legendText">Draft</span>
        <span class="legendColor activeLegendColor"></span><span class="legendText">Active</span>
        <span class="legendColor completedLegendColor"></span><span class="legendText">Completed</span>
        <span class="legendColor cancelledLegendColor"></span><span class="legendText">Cancelled</span>
    </div>
    <div class="viewBtns">
        <a href="<s:url action="projectDetails" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>" class="listViewBtn">
            <span>List View</span>
        </a>
        <a href="javascript:;" class="calendarViewBtn active">
            <span>Calendar</span>
        </a>
    </div>
</div>
<!-- End .areaHeader -->

<jsp:include page="includes/project/projectStats.jsp"/>

<span id="calendarToday" class="hide"><s:date name="calendarToday" format="MM/dd/yyyy"/></span>
<div id="contestsCalendarView" class="contestCView hide">
    <div class="loading">
        <img src="/images/loadingAnimation.gif" alt=""/>
    </div>
    <div class="calendar"></div>
</div>

</div>
<!-- End .container2 -->
</div>
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

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
