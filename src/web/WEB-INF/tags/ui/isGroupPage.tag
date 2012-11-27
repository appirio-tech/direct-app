<%--
  - Author: backstreetlili
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: Judge whether this page is a group page.
  - Since: Release Assembly - TopCoder Security Groups Frontend - View Group Details 
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.PAGE_TYPE eq 'group'}">
    <jsp:doBody/>
</c:if>
