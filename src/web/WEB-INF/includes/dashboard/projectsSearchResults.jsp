<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the search result of project search.
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="projectsResult">
    <table class="projectStats contests resultTable paginatedDataTable" cellpadding="0" cellspacing="0">

         <colgroup>

             <col width="30%" />
             <col width="14%" />
             <col width="14%" />
             <col width="14%" />
             <col width="14%" />
             <col width="14%" />


        </colgroup>


        <thead>
        <tr>
            <th>Project</th>
            <th>Draft</th>
            <th>Scheduled</th>
            <th>Active</th>
            <th>Finished</th>
            <th>Cancelled</th>
        </tr>
        </thead>
        <tbody>

        <s:iterator value="viewData.projects" status="status">
            <s:set var="project" value="project"  scope="page"/>
            <s:set var="projectSummary" value="data"  scope="page"/>
            <tr>
                <td class="first">
                    <link:projectOverview project="${project}"/>
                </td>
                <td>${projectSummary.draft.totalNumber}/<fmt:formatNumber value="${projectSummary.draft.totalPayment}" type="currency" /></td>
                <td>${projectSummary.scheduled.totalNumber}/<fmt:formatNumber value="${projectSummary.scheduled.totalPayment}" type="currency" /></td>
                <td>${projectSummary.active.totalNumber}/<fmt:formatNumber value="${projectSummary.active.totalPayment}" type="currency" /></td>
                <td>${projectSummary.finished.totalNumber}/<fmt:formatNumber value="${projectSummary.finished.totalPayment}" type="currency" /></td>
                <td class="last">${projectSummary.cancelled.totalNumber}/<fmt:formatNumber value="${projectSummary.cancelled.totalPayment}" type="currency" /></td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #projectsResults -->
