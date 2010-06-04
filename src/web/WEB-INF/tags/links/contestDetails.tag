<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for specified contest.
  -
  - Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
  - - change contest edit link
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contest" required="true" type="com.topcoder.direct.services.view.dto.contest.ContestBriefDTO" %>


<a href="<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['contest'].id}"/></s:url>">
    <c:out value="${contest.title}"/></a>



