<%--
  - Author: flexme
  - Version: 1.0 (Submission Viewer Release 3 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders checkout buttons area to be displayed on Studio Submissions Checkout page.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>

<s:if test="formData.roundType.toString() == 'MILESTONE'">
    <s:if test="viewData.hasCheckout == false">
    <a href="#" class="buttonBankSelection saveMilestone"><span class="left"><span class="right">Save Milestone</span></span></a>
    <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${true}" styleClass="buttonBankSelection">
        <span class="left"><span class="right">Make Changes</span></span>
    </link:studioSubmissionsGrid>
    </s:if>
</s:if>
<s:else>
    <a href="#" class="buttonBankSelection checkout"><span class="left"><span class="right">Checkout</span></span></a>
    <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${false}" styleClass="buttonBankSelection">
        <span class="left"><span class="right">Make Changes</span></span>
    </link:studioSubmissionsGrid>
</s:else>