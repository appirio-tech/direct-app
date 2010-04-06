<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<s:if test="sessionData.currentProjectContext != null">
    <jsp:doBody/>
</s:if>
