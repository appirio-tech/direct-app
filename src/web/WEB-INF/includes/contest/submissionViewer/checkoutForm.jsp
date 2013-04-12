<%--
  - Author: flexme
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId. Add another
  - - two parameters (additionalPurchases and roundType).
  -
  - Version 1.2 (TC Direct Replatforming Release 5) Change notes:
  - - The final round check out process was changed, so the unused parameters were removed.
  -
  - Version: 1.2
  - Since: Submission Viewer Release 3 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders checkout form area to be displayed on Studio Submissions Checkout page.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>

<s:if test="formData.roundType.toString() == 'CHECKPOINT'">
    <form id="checkoutForm" action="<s:url action="saveContestCheckpointSubmissionRank" namespace="/contest"/>" method="POST">
    <input type="hidden" name="ranks" id="ranks" />
    <input type="hidden" name="additionalPurchases" id="additionalPurchases" />
    <input type="hidden" name="roundType" value="${formData.roundType}" />
    <input type="hidden" name="projectId" value="${contestId}" />
</form>
</s:if>
<s:else>
    <form id="checkoutForm" action="<s:url action="saveContestSubmissionRank" namespace="/contest"/>" method="POST">
    <input type="hidden" name="ranks" id="ranks" />
    <input type="hidden" name="additionalPurchases" id="additionalPurchases" />
    <input type="hidden" name="roundType" value="${formData.roundType}" />
    <input type="hidden" name="projectId" value="${contestId}" />
</form>
</s:else>


