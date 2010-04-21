<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<table class="projectStats" cellpadding="0" cellspacing="0">
    <thead>
        <tr>
         
            <th>Drafts</th>
            <th>Scheduled</th>
            <th>Active</th>
            <th>Finished</th>
            
        </tr>
    </thead>
    <tbody>
    <s:push value="viewData.projectStats">
        <s:set value="feesRunning" var="feesRunning" scope="page"/>
        <s:set value="feesFinalized" var="feesFinalized" scope="page"/>
        <s:set value="feesScheduled" var="feesScheduled" scope="page"/>
        <s:set value="feesDraft" var="feesDraft" scope="page"/>
        <tr>
            
            <td class="date fees"><s:property value="draftContestsNumber"/>/$<fmt:formatNumber value="${feesDraft}" pattern="##,##0.00"/></td>
            <td class="date fees"><s:property value="pipelineContestsNumber"/>/$<fmt:formatNumber value="${feesScheduled}" pattern="##,##0.00"/></td>
            <td class="date fees"><s:property value="runningContestsNumber"/>/$<fmt:formatNumber value="${feesRunning}" pattern="##,##0.00"/></td>
            <td class="date fees"><s:property value="finishedContestsNumber"/>/$<fmt:formatNumber value="${feesFinalized}" pattern="##,##0.00"/></td>
           
        </tr>
    </s:push>
    </tbody>
</table><!-- End .projectsStats -->
