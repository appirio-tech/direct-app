<%--
  - Author: isv
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment is to be included to all pages from TC Direct application.
  - It defines the commonly used taglibs.
  -
  - Version 1.1 (Submission Viewer Release 1 assembly) changes: added "if" taglib.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="button" tagdir="/WEB-INF/tags/buttons" %>
<%@ taglib prefix="link" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="if" tagdir="/WEB-INF/tags/conditions" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="hidden" tagdir="/WEB-INF/tags/hidden" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>
<%@ taglib prefix="tc-webtag" uri="/WEB-INF/tld/tc-webtags.tld" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
