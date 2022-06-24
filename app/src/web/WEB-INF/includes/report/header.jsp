<%--
  - Author: TCSASSEMBLER
  - Version: 1.5
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 Changes: Add support for cost report.
  - Version 1.2 Changes: Add support for billing cost report.
  - Version 1.3 Changes: Change to new Look And Feel.
  - Version 1.4 Changes: Add support for project metrics report.
  - Version 1.5 Changes: Add support for the new Jira Issues report.
  -
  - Description: This page fragment is to be included to all pages from TC Direct application which provide views for
  - various reports.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <strong>Reports</strong>
</div>

<div class="areaHeader">
    <h2 class="title"><c:out value="${param.reportTitle}"/></h2>

    <div class="select">
        <div class="selectInnerBox">
            <div class="selectInner">
                <label for="selectReport">Select a Report:</label>
                <span class="settingPanel">
                    <select name="select" id="selectReport">
                        <option value="BILLING_COST" <s:if test="viewData.reportType.toString() == 'BILLING_COST'">selected="selected"</s:if>>
                            Competition Costs(Invoice History)</option>
                        <option value="PIPELINE" <s:if test="viewData.reportType.toString() == 'PIPELINE'">selected="selected"</s:if>>
                            Competition Pipeline</option>
                        <option value="COST" <s:if test="viewData.reportType.toString() == 'COST'">selected="selected"</s:if>>
                            Cost Analysis</option>
                        <option value="PROJECT_METRICS" <s:if test="viewData.reportType.toString() == 'PROJECT_METRICS'">selected="selected"</s:if>>
                            Project Metrics</option>
                        <option value="PARTICIPATION" <s:if test="viewData.reportType.toString() == 'PARTICIPATION'">selected="selected"</s:if>>
                            Participation Metrics</option>
                    </select>
                </span>
                <div class="clearFix"></div>
            </div>
        </div>
    </div>
</div>
<!-- End .areaHeader -->
