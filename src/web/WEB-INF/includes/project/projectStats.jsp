<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<table class="projectStats" cellpadding="0" cellspacing="0">
    <thead>
        <tr>
            <th>Start Date</th>
            <th>Drafts</th>
            <th>Running</th>
            <th>Pipeline</th>
            <th>Finished</th>
            <th>Tasks</th>
            <th>Project Fees Running/Finalized</th>
        </tr>
    </thead>
    <tbody>
    <s:push value="viewData.projectStats">
        <s:set value="feesRunning" var="feesRunning" scope="page"/>
        <s:set value="feesFinalized" var="feesFinalized" scope="page"/>
        <s:set value="startDate" var="startDate" scope="page"/>
        <tr>
            <td class="date">
                <fmt:formatDate value="${startDate}" pattern="MM/dd/yyyy HH:mm"/>
            </td>
            <td><s:property value="draftContestsNumber"/></td>
            <td><s:property value="runningContestsNumber"/></td>
            <td><s:property value="pipelineContestsNumber"/></td>
            <td><s:property value="finishedContestsNumber"/></td>
            <td><s:property value="taskedContestsNumber"/></td>
            <td class="date fees">$ <fmt:formatNumber value="${feesRunning}" pattern="##,##0.00"/> /
                $ <fmt:formatNumber value="${feesFinalized}" pattern="##,##0.00"/></td>
        </tr>
    </s:push>
    </tbody>
</table><!-- End .projectsStats -->
