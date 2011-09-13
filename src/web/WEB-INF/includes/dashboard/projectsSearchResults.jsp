<%--
  - Author: GreatKevin
  - Version: 1.2
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the search result of project search.
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Project Status Management) changes:
  - - Add two columns project status and operations into the project search result table.
  - - Add features to update the project status (archived / deleted)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="projectsResult">
    <table class="projectStats contests resultTable paginatedDataTable" cellpadding="0" cellspacing="0">

         <colgroup>

             <col width="27%" />
             <col width="8%" />
             <col width="9%" />
             <col width="8%" />
             <col width="8%" />
             <col width="8%" />
             <col width="10%" />
             <col width="22%" />
        </colgroup>


        <thead>
        <tr>
            <th>Project</th>
            <th>Draft</th>
            <th>Scheduled</th>
            <th>Active</th>
            <th>Finished</th>
            <th>Cancelled</th>
            <th>Project Status</th>
            <th>Operations</th>
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
                <td>${projectSummary.cancelled.totalNumber}/<fmt:formatNumber value="${projectSummary.cancelled.totalPayment}" type="currency" /></td>
                <td><span class='<s:property value="projectStatusType.projectStatusName.toLowerCase()"/>' id="projectStatus${projectSummary.projectId}" name="<s:property value="projectStatusType.projectStatusId"/>">${projectStatusType.projectStatusName}</span></td>
                <td class="last">

                        <a href="javascript:void(0)" id="archiveProjectButton${projectSummary.projectId}" onclick="updateDirectProjectStatus(${projectSummary.projectId}, 2)" class="button1 <s:if test='projectStatusType.projectStatusId == 1L'>show</s:if>"><span class="btnR"><span
                                class="btnC"><span class="btnIcon">Archive</span></span></span></a>
                        <a href="javascript:void(0)" id="reactivateProjectButton${projectSummary.projectId}" onclick="updateDirectProjectStatus(${projectSummary.projectId}, 1)" class="button1 <s:if test='projectStatusType.projectStatusId == 2L'>show</s:if>"><span class="btnR"><span
                                class="btnC"><span class="btnIcon">Activate</span></span></span></a>
                        <a href="javascript:void(0)" id="deleteProjectButton${projectSummary.projectId}" onclick="updateDirectProjectStatus(${projectSummary.projectId}, 3)" class="button1 <s:if test='projectStatusType.projectStatusId != 3L && projectStatusType.projectStatusId != 4L'>show</s:if>"><span class="btnR"><span
                                class="btnC"><span class="btnIcon">Delete</span></span></span></a>

                </td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #projectsResults -->
