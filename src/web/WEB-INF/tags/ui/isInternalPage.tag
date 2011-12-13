<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Judgle whether this page is a copilot page.
  - Since: TC Direct - Launch Copilot Selection Contest assembly  
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.PAGE_TYPE eq 'internal'}">
    <jsp:doBody/>
</c:if>
