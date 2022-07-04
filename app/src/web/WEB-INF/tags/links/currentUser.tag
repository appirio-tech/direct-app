<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.0.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for current user account.
  - version 1.0.1 (Direct Release 6 assembly) change notes: implemented link for current user profile.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="link" tagdir="/WEB-INF/tags/links" %>

<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<c:if test="${empty styleClass}">
    <c:set var="styleClass" value="user"/>
</c:if>

<link:user userId="${sessionScope.user.userId}" handle="${sessionScope.userHandle}" styleClass="${styleClass}"/>

