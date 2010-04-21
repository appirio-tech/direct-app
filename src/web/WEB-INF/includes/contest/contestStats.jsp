<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<table class="projectStats" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <th>Start Date/Time</th>
        <th>End Date/Time</th>
        <th>Registrants</th>
        <th>Submissions</th>
        <th>Forums</th>
        <th>Next Action</th>
    </tr>
    </thead>
    <s:push value="viewData.contestStats">
        <s:set var="contestStartDate" value="startTime" scope="page"/>
        <s:set var="contestEndDate" value="endTime" scope="page"/>
        <tbody>
        <tr>
            <td class="date">
                <fmt:formatDate value="${contestStartDate}" pattern="MM/dd/yyyy HH:mm zzz"/>
            </td>
            <td class="date">
                <fmt:formatDate value="${contestEndDate}" pattern="MM/dd/yyyy HH:mm zzz"/>
            </td>
            <td><s:property value="registrantsNumber"/></td>
            <td><s:property value="submissionsNumber"/></td>
            <td><s:property value="forumPostsNumber"/></td>
            <td class="fees">
                <p>Milestone Feedback : <strong>Tomorrow</strong></p>
                <a href="" onclick="return false;" class="button4">
                    Milestone Feedback</a>
                <a href="" onclick="return false;" class="button5">
                    Notification Option</a>
            </td>
        </tr>
        </tbody>
    </s:push>
</table>
<!-- End .projectsStats -->
