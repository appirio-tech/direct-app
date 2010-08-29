<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the page with details for specified screening
  - scorecard in Online Review application.
--%>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="reviewId" required="true" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>

<a href="https://<%=ApplicationServer.SOFTWARE_SERVER_NAME%>/review/actions/ViewScreening.do?method=viewScreening&amp;rid=${reviewId}"
   <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
    <jsp:doBody/>
</a>
