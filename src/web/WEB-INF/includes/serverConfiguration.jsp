<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  - Author: GreatKevin
  - Version: 1.0 (Tokenize the server part of URLs used in TopCoder Direct)
  - Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: Export the configurations of various topcoder server names into the javascript global variable space.
--%>
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>

<script type="text/javascript">
    SERVER_CONFIG_SERVER_NAME = "<%=ServerConfiguration.SERVER_NAME%>";
    SERVER_CONFIG_NEW_SERVER_NAME = "<%=ServerConfiguration.NEW_SERVER_NAME%>";
    SERVER_CONFIG_STUDIO_SERVER_NAME = "<%=ServerConfiguration.STUDIO_SERVER_NAME%>";
    SERVER_CONFIG_SOFTWARE_SERVER_NAME = "<%=ServerConfiguration.SOFTWARE_SERVER_NAME%>";
    SERVER_CONFIG_FORUM_SERVER_NAME = "<%=ServerConfiguration.FORUMS_SERVER_NAME%>";
    SERVER_CONFIG_STUDIO_FORUM_SERVER_NAME = "<%=ServerConfiguration.STUDIO_FORUMS_SERVER_NAME%>";
    SERVER_CONFIG_JIRA_SERVER_NAME = "<%=ServerConfiguration.JIRA_SERVER_NAME%>";
</script>

<c:set var="SERVER_CONFIG_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_NEW_SERVER_NAME" value="<%=ServerConfiguration.NEW_SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_STUDIO_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_SOFTWARE_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_FORUM_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_STUDIO_FORUM_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
<c:set var="SERVER_CONFIG_JIRA_SERVER_NAME" value="<%=ServerConfiguration.SERVER_NAME%>" scope="application"/>
