<%--
  - Author: TCSDEVELOPER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the page with details for specified review scorecard in
  - Online Review application.
--%>
<%@ tag import="com.topcoder.shared.util.ApplicationServer" %>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="reviewId" required="true" type="java.lang.Long" %>
<%@ attribute name="contestTypeId" required="false" type="java.lang.Long" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>


<c:choose>
    <c:when test="${not empty contestTypeId && contestTypeId == 38}">
        <a href="https://<%=ApplicationServer.SOFTWARE_SERVER_NAME%>/review/actions/ViewIterativeReview?rid=${reviewId}"
           <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:when>
    <c:otherwise>
        <a href="https://<%=ApplicationServer.SOFTWARE_SERVER_NAME%>/review/actions/ViewReview.do?method=viewReview&amp;rid=${reviewId}"
           <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>>
            <jsp:doBody/>
        </a>
    </c:otherwise>
</c:choose>

