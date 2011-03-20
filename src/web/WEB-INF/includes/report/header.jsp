<%--
  - Author: TCSDEVELOPER
  - Version: 1.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 Changes: Add support for cost report.
  - Version 1.2 Changes: Add support for billing cost report.
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
        <label for="selectReport">Select a Report:</label>
        <span name="settingPanel">
            <select name="select" id="selectReport">
                <option value="PIPELINE" <s:if test="viewData.reportType.toString() == 'PIPELINE'">selected="selected"</s:if>>
                    Pipeline</option>
                <option value="COST" <s:if test="viewData.reportType.toString() == 'COST'">selected="selected"</s:if>>
                    Cost</option>
                <option value="BILLING_COST" <s:if test="viewData.reportType.toString() == 'BILLING_COST'">selected="selected"</s:if>>
                    Billing Cost</option>

            </select>
        </span>
    </div>
</div>
<!-- End .areaHeader -->
