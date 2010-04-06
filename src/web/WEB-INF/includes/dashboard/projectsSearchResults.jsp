<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="projectsResult">
    <table class="projectStats contests paginatedDataTable" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <th>#</th>
            <th>Project</th>
            <th>Draft</th>
            <th>Running</th>
            <th>In Pipeline</th>
            <th>Finished</th>
            <th>Tasks</th>
            <th>Upcoming Schedule</th>
            <th>Project Fees Running/Finalized</th>
        </tr>
        </thead>
        <tbody>

        <s:iterator value="viewData.projects" status="status">
            <s:set value="project" var="project" scope="page"/>
            <s:set value="upcomingSchedule" var="upcomingSchedule" scope="page"/>
            <s:set value="feesRunning" var="feesRunning" scope="page"/>
            <s:set value="feesFinalized" var="feesFinalized" scope="page"/>
            <tr>
                <td><s:property value="#status.index + 1"/></td>
                <td>
                    <link:projectDetails project="${project}"/>
                </td>
                <td><s:property value="draftContestsNumber"/></td>
                <td><s:property value="runningContestsNumber"/></td>
                <td><s:property value="pipelineContestsNumber"/></td>
                <td><s:property value="finishedContestsNumber"/></td>
                <td>
                    <a href="javascript:alert('To be implemented by sub-sequent assemblies');">
                        <s:property value="taskedContestsNumber"/></a>
                </td>
                <td><fmt:formatDate value="${upcomingSchedule}" pattern="MM/dd/yyyy HH:mm"/></td>
                <td>$<fmt:formatNumber value="${feesRunning}" pattern="#####0.00"/>/$<fmt:formatNumber
                        value="${feesFinalized}" pattern="#####0.00"/></td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #projectsResults -->
