<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
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
                                <h2 class="title">Latest Activities</h2>
                                <a href="<s:url action="calendar" namespace="/"/>" class="button1"><span>Calendar view</span></a>
                            </div>
                            <!-- End .areaHeader -->

                            <s:iterator value="viewData.latestActivities.activities">
                                <s:set value="date" var="date" scope="page"/>
                                <s:set value="key" var="project" scope="page"/>
                                <table class="project" width="100%" cellpadding="0" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th colspan="5">
                                            <span class="left"><span class="right">
                                                <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="key.id"/></s:url>"
                                                   style="text-decoration:none;">
                                                    <s:property value="key.name"/>
                                                </a>
                                            </span></span>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <s:iterator value="value" status="status">
                                        <s:set value="originatorId" var="originatorId" scope="page"/>
                                        <s:set value="originatorHandle" var="originatorHandle" scope="page"/>
                                        <s:set value="contest" var="contest" scope="page"/>
                                        <tr <s:if test="#status.index == 4">class="hideStart"</s:if>>
                                            <td class="first <s:property value="type.shortName"/>"></td>
                                            <td class="second">
                                                <span class="ico <s:property value="type.shortName"/>">
                                                    <s:property value="type.name"/></span>
                                            </td>
                                            <td><div style="display:table-cell; vertical-align: middle; padding-right: 5px;">
                                                <img src="/images/<s:property value="contest.contestType.letter"/>_small.png"
                                                                                 alt="<s:property value="contest.contestType.letter"/>"/> 
															</div>
                                                <div style="display: table-cell;"> <link:contestDetails contest="${contest}"/> </div>
                                            </td>
                                            <td class="posted"><s:property value="type.actionText"/> :
                                                <link:user userId="${originatorId}" handle="${originatorHandle}"/></td>
                                            <td class="date">
                                                <c:out value="${tcdirect:getDateText(date, 'MM/dd/yyyy')}"/>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                    </tbody>
                                </table>
                            </s:iterator>

                            <jsp:include page="includes/upcomingActivities.jsp"/>

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

</body>
<!-- End #page -->

</html>
