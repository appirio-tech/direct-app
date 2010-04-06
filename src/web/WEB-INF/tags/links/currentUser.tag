<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for current user account.
  -
  - TODO : Subsequent assemblies must properly update this tag to set valid value for href attribute.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<c:if test="${empty styleClass}">
    <c:set var="styleClass" value="user"/>
</c:if>

<s:if test="sessionData != null">
    <s:push value="sessionData">
        <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
           class="${styleClass}"><s:property value="currentUserHandle"/></a>
    </s:push>
</s:if>
