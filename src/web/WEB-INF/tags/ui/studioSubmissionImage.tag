<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for IMG element for Studio submission image.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="imageType" required="true" type="java.lang.String" %>
<%@ attribute name="dimension" required="false" type="java.lang.Integer" %>
<%@ attribute name="artifactNum" required="false" type="java.lang.Integer" %>
<%@ attribute name="styleClass" required="false" type="java.lang.String" %>
<%@ attribute name="longdesc" required="false" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>

<img <c:if test="${not empty styleClass}">class="${styleClass}"</c:if>
     src="${tcdirect:getSubmissionPreviewImageURL(submissionId, imageType, artifactNum, pageContext.request)}"
     alt="" <c:if test="${not empty dimension}">width="${dimension}" height="${dimension}"</c:if>
     <c:if test="${not empty longdesc}">longdesc="${longdesc}"</c:if>
     <c:if test="${not empty width}">width="${width}"</c:if>
     <c:if test="${not empty height}">height="${height}"</c:if>/>
