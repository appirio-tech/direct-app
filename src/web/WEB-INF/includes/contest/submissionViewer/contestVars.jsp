<%--
  - Author: flexme
  - Version 1.1 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId.
  -
  - Version: 1.1
  - Since: Direct Submission Viewer Release 2
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the hidden variables of the contest
  - on Studio Submissions Grid and List views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<input type="hidden" id="contestId" value="${projectId}" />
<input type="hidden" id="roundType" value="${formData.roundType}" />
<input type="hidden" id="hasCheckout" value="${viewData.hasCheckout}" />