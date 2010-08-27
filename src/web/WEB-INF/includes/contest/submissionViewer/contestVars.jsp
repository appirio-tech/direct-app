<%--
  - Author: flexme
  - Version: 1.0 (Direct Submission Viewer Release 2 )
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the hidden variables of the contest
  - on Studio Submissions Grid and List views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<input type="hidden" id="contestId" value="${contestId}" />
<input type="hidden" id="roundType" value="${formData.roundType}" />