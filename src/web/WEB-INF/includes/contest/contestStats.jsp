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
    </tr>
    </thead>
    <s:push value="viewData.contestStats">
        <s:set var="contestStartDate" value="startTime" scope="page"/>
        <s:set var="contestEndDate" value="endTime" scope="page"/>
		<s:set value="contest" var="contest" scope="page"/>
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
            <td>
				<s:if test="forumId != -1">
					<s:if test="isStudio == true"><a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${forumId}" target="_blank"></s:if>
					<s:if test="isStudio == false"><a href="http://forums.topcoder.com/?module=Category&categoryID=${forumId}" target="_blank"></s:if>
				</s:if>
				<s:property value="forumPostsNumber"/>
				<s:if test="forumId != -1"></a></s:if>
			</td>
        </tr>
        </tbody>
    </s:push>
</table>
<!-- End .projectsStats -->
