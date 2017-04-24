<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version: 1.2
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag is to check whether the project context is set.
  -
  - Version 1.1 - Check currentSelectDirectProjectID first and if it does not exist, check currentProjectContext.
  -
  - Version 1.2 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace name attribute for s:set with var attribute
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<s:if test="sessionData.currentSelectDirectProjectID != null">
    <s:set var="currentProjectID" value="%{sessionData.currentSelectDirectProjectID + ''}"/>
</s:if>
<s:elseif test="sessionData.currentProjectContext != null">
    <s:set var="currentProjectID" value="%{sessionData.currentProjectContext.id + ''}"/>
</s:elseif>

<s:if test='not (#currentProjectID == null || #currentProjectID.equals("0"))'>
    <jsp:doBody/>
</s:if>
