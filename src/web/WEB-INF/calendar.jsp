<%--
  - Author: TCSDEVELOPER
  - Version: 1.1
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - Direct Improvements Assembly Release 3) changes:
  - Set the today in calendar with the today date on server, not on the client side.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <script type="text/javascript" src="/scripts/fullcalendar.min.js?v=205096"></script>
    <link rel="stylesheet" href="/css/fullcalendar.css?v=176771" media="all" type="text/css" />
    <script type="text/javascript">
        function getCalendarConfig() {
			var currentDate = new Date($.trim($("#calendarToday").text()));
            
			return {
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            editable: false,
            dayNamesShort: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
            year: currentDate.getFullYear(),
            month:currentDate.getMonth(),
			events: [
                <s:iterator value="viewData.latestActivities.activities" status="status1">
                    <s:iterator value="value" status="status2">
                        <s:set var="date" value='date'/>
                        {
                            title: '<a href=# class="<s:property value='type.shortName'/>" onmouseover=showPopup(this,\'levent<s:property value="#status1.index"/>_<s:property value="#status2.index"/>\')><s:property value='type.name'/></a>',
                            start: new Date(${tcdirect:getYear(date)}, ${tcdirect:getMonth(date)}, ${tcdirect:getDay(date)})
                        },
                    </s:iterator>
                </s:iterator>
                <s:iterator value="viewData.upcomingActivities.activities" status="status">
                    <s:set var="date" value='date'/>
                {
                    title: '<a href=# class="<s:property value='type.shortName'/>" onmouseover=showPopup(this,\'uevent<s:property value="#status.index"/>\')><s:property value='type.name'/></a>',
                    start: new Date(${tcdirect:getYear(date)}, ${tcdirect:getMonth(date)}, ${tcdirect:getDay(date)})
                },
                </s:iterator>
                {}
            ]
        }}
    </script>
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
                        <div class="areaHeader">
                            <h2 class="title">Calendar</h2>
                            <a href="<s:url action="dashboard" namespace="/"/>" class="button1"><span>List view</span></a>
                        </div><!-- End .areaHeader -->

                        <div id="calendar"></div>
							<span id="calendarToday" style="display:none"><s:date name="calendarToday" format="MM/dd/yyyy"/></span>
                            <jsp:include page="includes/copilotArea.jsp"/>
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

<div class="tooltipArea">
    <s:iterator value="viewData.latestActivities.activities" status="status1">
        <s:iterator value="value" status="status2" var="activity">
            <s:set var="index" value="#status1.index + '_' + #status2.index"/>
            <ui:calendarEvent activity="${activity}" index="levent${index}"/>
        </s:iterator>
    </s:iterator>
    <s:iterator value="viewData.upcomingActivities.activities" status="status" var="activity">
        <s:set var="index" value="#status.index"/>
        <ui:calendarEvent activity="${activity}" index="uevent${index}"/>
    </s:iterator>
</div><!-- End .tooltipArea -->

</body>
<!-- End #page -->

</html>