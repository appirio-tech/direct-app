<%--
  - Author: GreatKevin, TCSASSEMBLER
  - Version: 1.4
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the search result of project search.
  -
  - Version 1.1 (TC Direct - Page Layout Update Assembly 2) changes: fixed layout issues.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Project Status Management) changes:
  - - Add two columns project status and operations into the project search result table.
  - - Add features to update the project status (archived / deleted)
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) changes:
  - - Add customer
  -
  - Version 1.4 (Release Assembly - TC Cockpit All Projects Management Page Update) changes:
  - - Add project metadata data
  - - Change the operation buttons to new ones, add edit and project forum button
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="projectsResult">
    <div>
        <table class="projectStats contests resultTable paginatedDataTable" cellpadding="0" cellspacing="0">

            <colgroup>

                <col width="22%"/>
                <col width="8%"/>
                <col width="8%"/>
                <col width="6%"/>
                <col width="7%"/>
                <col width="6%"/>
                <col width="6%"/>
                <col width="6%"/>
                <col width="11%"/>
                <col width="20%"/>
                <col width="" />
                <col width="" />
            </colgroup>


            <thead>
            <tr>
                <th>Project</th>
                <th>Creation Date</th>
                <th>Completion Date</th>
                <th>Draft</th>
                <th>Scheduled</th>
                <th>Active</th>
                <th>Finished</th>
                <th>Cancelled</th>
                <th>Project Status</th>
                <th>Operations</th>
                <th class='hide'></th>
                <th class='hide'></th>
            </tr>
            </thead>
            <tbody>

            <s:iterator value="viewData.projects" status="status">
                <s:set var="project" value="project" scope="page"/>
                <s:set var="projectSummary" value="data" scope="page"/>
                <tr id="projectRow${projectSummary.projectId}">
                    <td class="first">
                        <link:projectOverview project="${project}"/>
                    </td>
                    <td>
                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${projectSummary.projectCreationDate}"/> ET
                        (GMT-400)
                    </td>
                    <td>
                        <s:if test="data.projectCompletionDate != null">
                            <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${projectSummary.projectCompletionDate}"/> ET
                            (GMT-400)
                        </s:if>
                    </td>
                    <td>${projectSummary.draft.totalNumber}</td>
                    <td>${projectSummary.scheduled.totalNumber}</td>
                    <td>${projectSummary.active.totalNumber}</td>
                    <td>${projectSummary.finished.totalNumber}</td>
                    <td>${projectSummary.cancelled.totalNumber}</td>
                    <td><span class='<s:property value="projectStatusType.projectStatusName.toLowerCase()"/>'
                              id="projectStatus${projectSummary.projectId}"
                              name="<s:property value="projectStatusType.projectStatusId"/>">${projectStatusType.projectStatusName}</span>
                    </td>
                    <td class="last">

                        <a class="short operation activateOperation <s:if test='projectStatusType.projectStatusId != 2L && projectStatusType.projectStatusId != 5L'>hide</s:if>"
                           href="javascript:;" onclick="updateDirectProjectStatus(${projectSummary.projectId}, 1)">
                            <img src="/images/activate-icon.png" alt=""/>Activate
                        </a>
                        <a class="short operation archiveOperation <s:if test='projectStatusType.projectStatusId != 1L'>hide</s:if>"
                           href="javascript:;" onclick="updateDirectProjectStatus(${projectSummary.projectId}, 2)">
                            <img src="/images/archive-icon.png" alt=""/>Archive
                        </a>

                        <a class="long operation deleteOperation <s:if test='projectStatusType.projectStatusId == 3L || projectStatusType.projectStatusId == 4L'>hide</s:if>"
                           href="javascript:;"  onclick="updateDirectProjectStatus(${projectSummary.projectId}, 3)">
                            <img src="/images/remove-milestone-icon.png" alt=""/>Cancel
                        </a>

                        <s:if test='projectStatusType.projectStatusId == 1L || projectStatusType.projectStatusId == 2L'>
                            <br class="secondRowSeparator"/>
                        </s:if>

                        <a class="short" target="_blank" href='<s:url action="editProject" namespace="/"><s:param name="formData.projectId" value="data.projectId"/></s:url>'><img src="/images/edit-icon.png" alt=""/>Edit</a>
                        <a class="long" target="_blank" <s:if test="data.projectForumCategoryId == null || data.projectForumCategoryId <=0"> style='visibility: hidden;' </s:if>
                           href="https://apps.topcoder.com/forums/?module=Category&categoryID=${projectSummary.projectForumCategoryId}">
                            <img src="/images/forum-link-icon.png" alt=""/>Project Forum
                        </a>

                    </td>
                    <td class="hide"><span>${projectSummary.customerId == -1 ? 'none' : projectSummary.customerId}</span></td>
                    <td class="hide metadataTD">
                        <s:if test="projectsMetadataMap != null">
                            <s:iterator value="projectsMetadataMap">
                                <span class="metadataGroup">
                                     <span class="metadataKeyId"><s:property value="key.id"/></span>
                                     <span class="metadataKeyName"><s:property value="key.name"/></span>
                                     <s:iterator value="value">
                                         <span class="metadataValue"><s:property value="metadataValue"/></span>
                                     </s:iterator>
                                </span>
                            </s:iterator>
                        </s:if>
                    </td>
                </tr>
            </s:iterator>

            </tbody>
        </table>
        <!-- End .projectsStats -->
    </div>
</div>
<!-- End #projectsResults -->

