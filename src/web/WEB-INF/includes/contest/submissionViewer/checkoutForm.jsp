<%--
  - Author: flexme
  - Version: 1.0 (Submission Viewer Release 3 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders checkout form area to be displayed on Studio Submissions Checkout page.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>

<s:if test="formData.roundType.toString() == 'MILESTONE'">
    <form id="checkoutForm" action="<s:url action="saveMilestone" namespace="/contest"/>" method="POST">
        <input type="hidden" name="ranks" id="ranks" />
        <input type="hidden" name="contestId" value="${contestId}" />
    </form>
</s:if>
<s:else>
    <form id="checkoutForm" action="<s:url action="checkoutFinal" namespace="/contest"/>" method="POST">
        <input type="hidden" name="ranks" id="ranks" />
        <input type="hidden" name="additionalPurchases" id="additionalPurchases" />
        <input type="hidden" name="contestId" value="${contestId}" />
        <input type="hidden" name="billingProjectId" id="billingProjectId" value="" />
    </form>
</s:else>