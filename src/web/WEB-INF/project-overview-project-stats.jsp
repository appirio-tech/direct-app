<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:push value="viewData.projectStats">
    <tr>
        <td class="statusName"># of Drafts</td>
        <td><s:property value="draftContestsNumber"/></td>
    </tr>
    <tr>
        <td class="statusName"># Scheduled</td>
        <td><s:property value="pipelineContestsNumber"/></td>
    </tr>
    <tr>
        <td class="statusName"># Active</td>
        <td><s:property value="runningContestsNumber"/></td>
    </tr>
    <tr>
        <td class="statusName">Finished(Completed/Failed/Cancelled)</td>
        <td>${viewData.projectStats.finishedContestsNumber + viewData.dashboardProjectStat.cancelledNumber}(${viewData.dashboardProjectStat.completedNumber}/
                ${viewData.projectStats.finishedContestsNumber-viewData.dashboardProjectStat.completedNumber}/
                ${viewData.dashboardProjectStat.cancelledNumber})
        </td>
    </tr>
    <tr>
        <td class="statusName">Total Member Cost</td>
        <td>
            <fmt:formatNumber value="${viewData.dashboardProjectStat.totalMemberCost}" pattern="$#,##0.00"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Average Member Cost</td>
        <td>
            <fmt:formatNumber value="${viewData.dashboardProjectStat.averageMemberCostPerContest}" pattern="$#,##0.00"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Total Challenge Fee</td>
        <td>
            <fmt:formatNumber value="${viewData.dashboardProjectStat.totalContestFee}" pattern="$#,##0.00"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Average Challenge Fee</td>
        <td>
            <fmt:formatNumber value="${viewData.dashboardProjectStat.averageContestFeePerContest}" pattern="$#,##0.00"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Total Project Cost</td>
        <td id="totalProjectCostValue">
            <fmt:formatNumber value="${viewData.dashboardProjectStat.totalProjectCost}" pattern="$#,##0"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Average Challenge Duration</td>
        <td>
            <c:out value="${tcdirect:getDurationTextInDays(viewData.dashboardProjectStat.averageContestDuration)}"/>
        </td>
    </tr>
    <tr>
        <td class="statusName">Average Fulfillment</td>
        <td>
            <fmt:formatNumber value="${viewData.dashboardProjectStat.averageFulfillment}" pattern="##0.##"/>%
        </td>
    </tr>
    <tr>
        <td class="statusName">Unresolved Issues</td>
        <td id="unresolvedIssuesTD">
        </td>
    </tr>
    <tr>
        <td class="statusName">Ongoing Races</td>
        <td id="ongoingRacesTD">
        </td>
    </tr>
</s:push>
