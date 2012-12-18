<%--
  - Author: bugbuka, GreatKevin, morehappiness
  - Version: 1.2
  - Since: Module Assembly - TC Cockpit Operations Dashboard 
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Eight)
  - Change the last column to stalled/late and add late status.
  -
  - Description: This page renders the search result for operations dashboard.  
  - 
  - Version: 1.2 (Release Assembly - TC Cockpit Operations Dashboard Bug Fix and Improvements 1)
  - Change in version 1.2: change the result table structure as requested by bugs TCCC-4724 and TCCC-4729  
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="pmProjectsResult">
    <div>
        <table class="projectStats contests resultTable paginatedDataTable" cellpadding="0" cellspacing="0">

            <colgroup>
                <col width="15%">
                <col width="12%">
                <col width="7%">
                <col width="16%">
                <col width="16%">
                <col width="8%">
                <col width="15%">
                <col width="11%">
                <col width="9%">
                <col width="12%">
                <col width="8%">
                <col width="">
                <col width="">
                <col width="">
                <col width="">
            </colgroup>

            <thead>
            <tr>
                <th>Project Name</th>
                <th class="hide">Creation Date</th>
                <th class="hide">Completion Date</th>
                <th>Customer Name</th>
                <th>Project Fulfillment</th>
                <th>Budget</th>
                <th class="hide">Project Status</th>
                <th>Duration</th>
                <th># of Project Forum Posts</th>
                <th>Last Posters</th>
                <th>Stalled/Late</th>
                <th class='hide'></th>
                <th class='hide'></th>
                <th class='hide'></th>
                <th class='hide'></th>
            </tr>
            </thead>
            <tbody>

            <s:iterator value="viewData.projects" status="status">
                <s:set var="project" value="project" scope="page"/>
                <s:set var="projectSummary" value="data" scope="page"/>
                <tr class="dataRow" id="projectRow${projectSummary.projectId}">
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
                    <td class="budgetRow">
                        <c:choose>
                            <c:when test="${projectSummary.totalBudget > 0}">
                                <span class="costPercentage " style="font-weight:normal;">
                                    <fmt:formatNumber value="${projectSummary.actualCost / projectSummary.totalBudget}" pattern="##0.##" type="percent"/> Actual
				    <br /><fmt:formatNumber value="${projectSummary.projectedCost / projectSummary.totalBudget}" pattern="##0.##" type="percent"/> Projected
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="costPercentage " style="font-weight:normal;">
                                	Budget not set
                                </span>
                            </c:otherwise>
                        </c:choose>
                    	
                        <div class="allThreeCosts hide" style="width:180px; font-weight:normal;">
						<c:choose>
                            <c:when test="${projectSummary.totalBudget > 0}">
                            <div style="font-weight:normal;">Total Budget: <fmt:formatNumber value="${projectSummary.totalBudget}" pattern="$#,##0.00"/></div>
                            </c:when>
                            <c:otherwise>
							<div style="font-weight:normal;">Total Budget: not set</div>
							</c:otherwise>
                        </c:choose>
                            <div style="font-weight:normal;">Actual Cost: <fmt:formatNumber value="${projectSummary.actualCost}" pattern="$#,##0.00"/></div>
                            <div style="font-weight:normal;">Projected Cost: <fmt:formatNumber value="${projectSummary.projectedCost}" pattern="$#,##0.00"/></div>
                        </div>
                    </td>
                    <td class="hide"><span class='<s:property value="projectStatusType.projectStatusName.toLowerCase()"/>'
                              id="projectStatus${projectSummary.projectId}"
                              name="<s:property value="projectStatusType.projectStatusId"/>">${projectStatusType.projectStatusName}</span>
                    </td>
                    <td class="durationRow">
                        <c:choose>
                        	<c:when test="${projectSummary.plannedDuration > 0}">
                                <span class="durationPercentage " style="font-weight:normal;">
                                    <fmt:formatNumber value="${projectSummary.actualDuration / projectSummary.plannedDuration}" pattern="##0.##" type="percent"/> Actual
                                    <br /><fmt:formatNumber value="${projectSummary.projectedDuration / projectSummary.plannedDuration}" pattern="##0.##" type="percent"/> Projected
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="durationPercentage " style="font-weight:normal;">
                                	Duration not set
                                </span>
                            </c:otherwise>
                        </c:choose>
                        <div class="allThreeDurations hide" style="width:180px; font-weight:normal;">
						<c:choose>
                        	<c:when test="${projectSummary.plannedDuration > 0}">
                            <div style="font-weight:normal;">Planned Duration: ${projectSummary.plannedDuration}</div>
							</c:when>
                            <c:otherwise>
							<div style="font-weight:normal;">Planned Duration: not set</div>
							</c:otherwise>
                        </c:choose>
                            <div style="font-weight:normal;">Actual Duration: ${projectSummary.actualDuration}</div>
                            <div style="font-weight:normal;">Projected Duration: ${projectSummary.projectedDuration}</div>
                        </div>
                    </td>
                    <td>
					 <c:if test="${projectSummary.projectForumCategoryId > 0}">
						<a href="https://apps.topcoder.com/forums/?module=Category&categoryID=${projectSummary.projectForumCategoryId}" target="_blank">
						${projectSummary.messageNumber}
						</a>
                     </c:if>
					</td>
                    <td>
                        <c:forEach var="poster" items="${projectSummary.latestThreePosters}">
                            <div style="width: 180px;">
                                <link:user userId="${poster.handleId}" handle="${poster.handle}"/>
                                <c:choose>
                                    <c:when test="${poster.daysSincePost > 1}">
                                        ${poster.daysSincePost} days ago
                                    </c:when>
                                    <c:otherwise>
                                        ${poster.daysSincePost} day ago
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </td>
                    <td class="last">${projectSummary.hasStalledContests ? "Stalled" : ""} ${projectSummary.hasStalledContests && projectSummary.hasLateContests ? "/" : ""} ${projectSummary.hasLateContests ? "Late" : ""}</td>

                    <td class="hide">${(fn:length(projectSummary.customerName) eq 0) ? 'none' : projectSummary.customerName}</td>
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
                    <td class="hide metadataValTD"></td>
                    <td class="hide"><span>${projectSummary.projectManagerName}</span></td>
                </tr>
            </s:iterator>

            </tbody>
        </table>
        <!-- End .projectsStats -->
    </div>
</div>
<!-- End #projectsResults -->

