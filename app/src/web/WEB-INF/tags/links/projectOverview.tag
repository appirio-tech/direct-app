<%--
  - Author: isv
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for specified project.
--%>
<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="project" required="true" type="com.topcoder.direct.services.view.dto.project.ProjectBriefDTO" %>


<a class="longWordsBreak" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#attr['project'].id}"/></s:url>">
    <c:out value="${project.name}"/>
</a>