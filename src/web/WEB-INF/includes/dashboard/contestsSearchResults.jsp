<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="contestsResult">
    <table class="projectStats contests paginatedDataTable" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
        	  <th>Project</th>
            <th>Contest Type</th>
            <th>Contest Name</th>
            <th>Start/End</th>
            <th>Registrants</th>
            <th>Submissions</th>
            <th>Forums</th>
            <th>Status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <s:iterator value="viewData.contests" status="status">
            <s:set  var="contest" value="contest" scope="page"/>
            <s:set  var="project" value="contest.project" scope="page"/>
            <s:set  var="startTime" value="startTime"  scope="page"/>
            <s:set  var="endTime" value="endTime"  scope="page"/>
            <tr>
                <td>
                    <link:projectDetails project="${project}"/>
                </td>            	
                <td><s:property value="contestType"/></td>
                <td><link:contestDetails contest="${contest}"/></td>
                <td><fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy HH:mm zzz"/>
                    <c:out value="${tcdirect:getEndText(endTime)}"/></td>
                <td><s:property value="registrantsNumber"/></td>
                <td><s:property value="submissionsNumber"/></td>
                <td><s:property value="forumPostsNumber"/></td>
                <td><span class="<s:property value="status.shortName"/>"><s:property value="status.name"/></span></td>
                <td>
                    <a href="contest/detail?contestId=${contest.id}"
                       class="button1 button"><span>View/Edit</span></a>
                </td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #contestsResult -->
