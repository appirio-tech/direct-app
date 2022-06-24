<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version: 1.1 (Release Assembly - TopCoder Cockpit Navigation Update)
  - Copyright (C) 2013 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace name attribute for s:set with var attribute
  -
  - Description: Refactor the header to include the new header file, but it can also be easily switch back to
  - old header if needed.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<hidden:token/>
<jsp:include page="newHeader.jsp"/>
<s:set var="defaultTimeZone" value="defaultTimeZone"/>
<s:set var="defaultDateTimeFormat" value="defaultDateTimeFormat"/>
