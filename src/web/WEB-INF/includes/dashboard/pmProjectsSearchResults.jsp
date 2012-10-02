<%--
  - Author: bugbuka
  - Version: 1.0
  - Since: Module Assembly - TC Cockpit Operations Dashboard 
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the search result for operations dashboard.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="pmProjectsResult">
    <div>
        <table class="projectStats contests resultTable paginatedDataTable" cellpadding="0" cellspacing="0">

            <colgroup>

                <col width="9%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="9%"/>
                <col width="7%"/>
                <col width="9%"/>
                <col width="7%"/>
                <col width="" />
                <col width="" />
            </colgroup>


            <thead>
            <tr>
                <th>Project Name</th>
                <th class="hide">Creation Date</th>
                <th class="hide">Completion Date</th>
                <th>Customer Name</th>
                <th>Project Fulfillment</th>
                <th>Total Budget</th>
                <th>Actual Cost</th>
                <th>Projected Cost</th>
                <th class="hide">Project Status</th>
                <th>Planned Duration</th>
                <th>Actual Duration</th>
                <th>Projected Duration</th>
                <th># of Project Forum Posts</th>
                <th>Days since last post</th>
                <th>Handle of last post</th>
                <th>Stalled Contests</th>
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
                    <td class="hide">
                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${projectSummary.projectCreationDate}"/> ET
                        (GMT-400)
                    </td>
                    <td class="hide">
                        <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${projectSummary.projectCompletionDate}"/> ET
                        (GMT-400)
                    </td>
                    <td>${projectSummary.customerName}</td>
                    <td>
                    	<fmt:formatNumber value="${projectSummary.projectFulfillment}" pattern="##0.##"/>%
                    </td>
                    <td>
                    	<fmt:formatNumber value="${projectSummary.totalBudget}" pattern="$#,##0.00"/>
                    </td>
                    <td>
                    	<fmt:formatNumber value="${projectSummary.actualCost}" pattern="$#,##0.00"/>
                    </td>
                    <td>
                    	<fmt:formatNumber value="${projectSummary.projectedCost}" pattern="$#,##0.00"/>
                    </td>
                    <td class="hide"><span class='<s:property value="projectStatusType.projectStatusName.toLowerCase()"/>'
                              id="projectStatus${projectSummary.projectId}"
                              name="<s:property value="projectStatusType.projectStatusId"/>">${projectStatusType.projectStatusName}</span>
                    </td>
                    <td>
                    	<c:if test="${projectSummary.plannedDuration > 0}">
  							${projectSummary.plannedDuration} days
                        </c:if>
                    </td>
                    <td>
                    	<c:if test="${projectSummary.actualDuration > 0}">
  							${projectSummary.actualDuration} days
                        </c:if>
                    </td>
                    <td>
                    	<c:if test="${projectSummary.projectedDuration > 0}">
  							${projectSummary.projectedDuration} days
                        </c:if>
                    </td>
                    
                    <td>${projectSummary.messageNumber}</td>
                    <td>
                    	<c:if test="${projectSummary.daysSinceLastPost > 0}">
  							${projectSummary.daysSinceLastPost} days
                        </c:if>
                    </td>
                    <td>
                    	<link:user userId="${projectSummary.lastPostHandleId}" handle="${projectSummary.lastPostHandle}"/>
                    </td>
                    <td class="last">${projectSummary.hasStalledContests ? "Yes" : "No"}</td>

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

