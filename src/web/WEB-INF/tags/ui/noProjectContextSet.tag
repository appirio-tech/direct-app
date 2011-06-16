<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag is to check whether the project context is not set.
  -
  - Version 1.1 - Check currentSelectDirectProjectID first and if it does not exist, check currentProjectContext.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>


<s:if test="sessionData.currentSelectDirectProjectID != null">
    <s:set name="currentProjectID" value="%{sessionData.currentSelectDirectProjectID + ''}"/>
</s:if>
<s:elseif test="sessionData.currentProjectContext != null">
    <s:set name="currentProjectID" value="%{sessionData.currentProjectContext.id + ''}"/>
</s:elseif>


<s:if test='#currentProjectID == null || #currentProjectID.equals("0")'>
    <jsp:doBody/>
</s:if>
