<%--
  - Author: bugbuka, GreatKevin, morehappiness, tangzx
  - Version: 1.3
  - Since: Module Assembly - TC Cockpit Operations Dashboard 
  - Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Eight)
  - Change the last column to stalled/late and add late status.
  -
  - Version: 1.2 (Release Assembly - TC Cockpit Operations Dashboard Bug Fix and Improvements 1)
  - Change in version 1.2: change the result table structure as requested by bugs TCCC-4724 and TCCC-4729  
  - 
  - Version: 1.3 (Release Assembly - TC Cockpit Operations Dashboard Improvements 2)
  - Change in version 1.3: Add column for 'Historical Cost Difference' and risk filter.
  -
  - Description: This page renders the search result for operations dashboard.  
  -
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
                <col width="9%">
                <col width="14%">
                <col width="7%">
                <col width="9%">
                <col width="7%">
                
                <col width="">
                <col width="">
                <col width="">
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
                <th>Historical Cost Difference</th>
                <th class="hide">Project Status</th>
                <th>Duration</th>
                <th># of Project Forum Posts</th>
                <th>Last Posters</th>
                <th>Status</th>
                <th class='hide'></th>
                <th class='hide'></th>
                <th class='hide'></th>
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
                                <span class="costPercentage ">
                                    <fmt:formatNumber value="${projectSummary.actualCost / projectSummary.totalBudget}" type="percent" maxFractionDigits="2"/> Actual
                                    <br/>
                                    <fmt:formatNumber value="${projectSummary.projectedCost / projectSummary.totalBudget}" type="percent" maxFractionDigits="2"/> Projected
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="costPercentage ">
                                	Budget not set
                                </span>
                            </c:otherwise>
                        </c:choose>
                    	
                        <div class="allThreeCosts hide">
						<c:choose>
                            <c:when test="${projectSummary.totalBudget > 0}">
                            <div>Total Budget: <fmt:formatNumber value="${projectSummary.totalBudget}" pattern="$#,##0.00"/></div>
                            </c:when>
                            <c:otherwise>
							<div>Total Budget: not set</div>
							</c:otherwise>
                        </c:choose>
                            <div>Actual Cost: <fmt:formatNumber value="${projectSummary.actualCost}" pattern="$#,##0.00"/></div>
                            <div>Projected Cost: <fmt:formatNumber value="${projectSummary.projectedCost}" pattern="$#,##0.00"/></div>
                        </div>
                    </td>
                    
                    <!-- Historical Const Difference -->
                    <td class="diffRow">
                        <span class="diffPercentage ">
                        
						<c:choose>
                            <c:when test="${projectSummary.historicalProjectedCost > 0}">
                                <c:choose>
                                    <c:when test="${projectSummary.actualCost > projectSummary.historicalProjectedCost}">
                                        <span class="redDiff2">
                                            +<fmt:formatNumber value="${(projectSummary.actualCost - projectSummary.historicalProjectedCost) / projectSummary.historicalProjectedCost}" type="percent" maxFractionDigits="2"/>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="greenDiff2">
                                            <fmt:formatNumber value="${(projectSummary.actualCost - projectSummary.historicalProjectedCost) / projectSummary.historicalProjectedCost}" type="percent" maxFractionDigits="2"/>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                HPC not set
							</c:otherwise>
                        </c:choose>
                        </span>
                        <div class="allDiffInfo hide">                                
                            <c:choose>
                                <c:when test="${projectSummary.historicalProjectedCost > 0}">
                                    <c:choose>
                                        <c:when test="${projectSummary.actualCost > projectSummary.historicalProjectedCost}">
                                            <span class="redDiff">
                                                +<fmt:formatNumber value="${(projectSummary.actualCost - projectSummary.historicalProjectedCost) / projectSummary.historicalProjectedCost}" type="percent" maxFractionDigits="2"/>
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="greenDiff">
                                                <fmt:formatNumber value="${(projectSummary.actualCost - projectSummary.historicalProjectedCost) / projectSummary.historicalProjectedCost}" type="percent" maxFractionDigits="2"/>
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <div>HPC not set</div>
                                </c:otherwise>
                            </c:choose>
                            
                            <div>HPC: <fmt:formatNumber value="${projectSummary.historicalProjectedCost}" pattern="$#,##0.00"/></div>
                            <div>HAC: <fmt:formatNumber value="${projectSummary.actualCost}" pattern="$#,##0.00"/></div>
                        </div>
                    </td>                    
                    
                    <td class="hide"><span class='<s:property value="projectStatusType.projectStatusName.toLowerCase()"/>'
                              id="projectStatus${projectSummary.projectId}"
                              name="<s:property value="projectStatusType.projectStatusId"/>">${projectStatusType.projectStatusName}</span>
                    </td>
                    <td class="durationRow">
                        <c:choose>
                        	<c:when test="${projectSummary.plannedDuration > 0}">
                                <span class="durationPercentage ">
                                    <fmt:formatNumber value="${projectSummary.actualDuration / projectSummary.plannedDuration}" type="percent" maxFractionDigits="2"/> Actual
                                    <br /><fmt:formatNumber value="${projectSummary.projectedDuration / projectSummary.plannedDuration}" type="percent" maxFractionDigits="2"/> Projected
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="durationPercentage ">
                                	Duration not set
                                </span>
                            </c:otherwise>
                        </c:choose>
                        <div class="allThreeDurations hide">
						<c:choose>
                        	<c:when test="${projectSummary.plannedDuration > 0}">
                            <div>Planned Duration: ${projectSummary.plannedDuration}</div>
							</c:when>
                            <c:otherwise>
							<div>Planned Duration: not set</div>
							</c:otherwise>
                        </c:choose>
                            <div>Actual Duration: ${projectSummary.actualDuration}</div>
                            <div>Projected Duration: ${projectSummary.projectedDuration}</div>
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
                            <div class="latestThreePostersDiv">
                                <link:user userId="${poster.handleId}" handle="${poster.handle}"/>
                                <c:choose>
                                    <c:when test="${poster.daysSincePost > 1.0}">
                                        ${fn:substringBefore(poster.daysSincePost, ".")} days ago
                                    </c:when>
                                    <c:otherwise>
                                        ${fn:substringBefore(poster.daysSincePost, ".")} day ago
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </td>
                    <td class="last">
                        <c:if test="${projectSummary.phaseLateContestsNum > 0}">
                            Phase Late (${projectSummary.phaseLateContestsNum}) <br/>
                        </c:if>
                        <c:if test="${projectSummary.launchLateContestsNum > 0}">
                            Launch Late (${projectSummary.launchLateContestsNum}) <br/>
                        </c:if>
                        <c:if test="${projectSummary.checkpointLateContestsNum > 0}">
                            Checkpoint Late (${projectSummary.checkpointLateContestsNum}) <br/>
                        </c:if>
                        <c:if test="${projectSummary.stalledContestsNum > 0}">
                            Phase Blocked (${projectSummary.stalledContestsNum}) <br/>
                        </c:if>
                        <c:if test="${projectSummary.apOffContestsNum > 0}">
                            AP Off (${projectSummary.apOffContestsNum}) <br/>
                        </c:if>
                    </td>

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
                    <td class="hide">
						<span>
							<c:choose>
								<c:when test="${empty projectSummary.projectManagerName}">
									none
								</c:when>
								<c:otherwise>
									${projectSummary.projectManagerName}
								</c:otherwise>
							</c:choose>
						</span>
					</td>
                    <!-- used for status sorting -->
                    <td class="hide">
                        ${projectSummary.phaseLateContestsNum + projectSummary.launchLateContestsNum + 
                        projectSummary.checkpointLateContestsNum + projectSummary.stalledContestsNum + projectSummary.apOffContestsNum} 
                    </td>
                    <td class="hide">
                        <span>${projectSummary.projectFulfillment}</span>
                        <span>
                            <c:choose>
                                <c:when test="${fn:length(projectSummary.latestThreePosters) > 0}">
                                    ${fn:substringBefore(projectSummary.latestThreePosters[0].daysSincePost, ".")}
                                </c:when>
                                <c:otherwise>
                                    -1
                                </c:otherwise>
                            </c:choose> 
                        </span>
                        <span>
                            <c:choose>
                                <c:when test="${projectSummary.totalBudget > 0}">
                                    ${(projectSummary.actualCost > projectSummary.projectedCost ? projectSummary.actualCost : projectSummary.projectedCost)
                                        / projectSummary.totalBudget * 100}
                                </c:when>
                                <c:otherwise>
                                    -1
                                </c:otherwise>
                            </c:choose>                               
                        </span>
                        <span>
                            <c:choose>
                                <c:when test="${projectSummary.plannedDuration > 0}">
                                	${tcdirect:maxLong(0, tcdirect:maxLong(projectSummary.actualDuration, projectSummary.projectedDuration) - projectSummary.plannedDuration)}                                
                                </c:when>
                                <c:otherwise>
                                    -1
                                </c:otherwise>
                            </c:choose>                         
                        </span>
                    </td>
                    <!-- used for forum sorting -->
                    <td class="hide">
                        <c:choose>
                            <c:when test="${fn:length(projectSummary.latestThreePosters) > 0}">
                                ${fn:substringBefore(projectSummary.latestThreePosters[0].daysSincePost, ".")}
                            </c:when>
                            <c:otherwise>
                                0
                            </c:otherwise>
                        </c:choose> 
                    </td>                    
                </tr>
            </s:iterator>

            </tbody>
        </table>
        <!-- End .projectsStats -->
    </div>
</div>
<!-- End #projectsResults -->

