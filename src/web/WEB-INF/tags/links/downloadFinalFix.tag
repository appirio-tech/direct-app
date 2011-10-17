<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the submission downloadable from Online Review
  - application.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="contestId" required="true" type="java.lang.String" %>
<%@ attribute name="finalFix" required="true" type="java.lang.String" %>
<%@ attribute name="version" required="false" type="java.lang.String" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<a href="<s:url action="downloadFinalFix" namespace="/contest">
             <s:param name="contestId">${contestId}</s:param>
             <s:param name="finalFix">${finalFix}</s:param>
             <s:param name="version">${version}</s:param>
</s:url>" class="${styleClass}">
    <jsp:doBody/>
</a>
