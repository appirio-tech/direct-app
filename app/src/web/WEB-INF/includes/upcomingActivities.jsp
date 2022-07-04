<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project overview view.
  -
  - Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
  - Add 'Upcoming Activity' in the header of table.
  -
  - Version 1.0.2 - Release Assembly - TC Direct UI Improvement Assembly 3 Change Note
  - Add new class for upcoming activity table
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="activity"><!-- Upcoming Activity -->
    <!-- the class "rowsToHide" is necessary for the show/hide table rows functionnality -->
    <table id="upcomingActivity" class="project rowsToHide projectActivities" width="100%" cellpadding="0"
           cellspacing="0">
        <thead>
        <tr>
            <th colspan="5">
                <span class="left"><span class="right">Upcoming Activity</span></span>
            </th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="viewData.upcomingActivities.activities" status="status">
            <s:set value="contest" var="contest" scope="page"/>
            <s:set value="date" var="date" scope="page"/>
            <tr <s:if test="#status.index == 4">class="hideStart"</s:if>>

                <td class="second"><span class="ico <s:property value="type.shortName"/>">
                    <s:property value="type.name"/></span></td>
                <td class="posted"><link:contestDetails contest="${contest}"/></td>
                <td class="date">
                    <c:out value="${tcdirect:getDateText(date, 'MM/dd/yyyy')}"/>
                </td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <div class="tableFooter">
        <div class="tableFooterLeft">
            <div class="tableFooterRight">
                <div class="tableFooterInner">
                    <a href="javascript:;" class="viewMore"
                       onclick="showHideRows(this,'upcomingActivity');">View More</a>
                </div>
            </div>
        </div>
        <!-- End .tableFooterLeft -->
    </div>
    <!-- End .tableFooter -->
</div>
<!-- End .activity -->
