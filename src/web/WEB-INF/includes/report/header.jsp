<%--
  - Author: TCSDEVELOPER
  - Version: 1.3
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 Changes: Add support for cost report.
  - Version 1.2 Changes: Add support for billing cost report.
  - Version 1.3 Changes: Change to new Look And Feel.
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
                        <option value="PIPELINE" <s:if test="viewData.reportType.toString() == 'PIPELINE'">selected="selected"</s:if>>
                            Pipeline</option>
                        <option value="COST" <s:if test="viewData.reportType.toString() == 'COST'">selected="selected"</s:if>>
                            Cost Analysis</option>
                        <option value="BILLING_COST" <s:if test="viewData.reportType.toString() == 'BILLING_COST'">selected="selected"</s:if>>
                            Invoice History</option>
                        <option value="PARTICIPATION" <s:if test="viewData.reportType.toString() == 'PARTICIPATION'">selected="selected"</s:if>>
                            Participation Metrics</option>
                    </select>
                </span>
                <div class="clearFix"></div>
            </div>
            <span class="corner tl"></span>
            <span class="corner tr"></span>
            <span class="corner bl"></span>
            <span class="corner br"></span>
        </div>
    </div>
</div>
<!-- End .areaHeader -->
