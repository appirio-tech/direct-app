<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element rendering the Cancel button to be used for cancelling
  - the current operation initiated by dashboard views.
  -
  - TODO : Subsequent assemblies must properly update this tag to set valid value for href attribute.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<a href="<s:url action="dashboard" namespace="/"/>" class="button7 leftButton">
    <span class="left"><span class="right">Cancel</span></span></a>
