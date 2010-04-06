<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="tab" required="true" type="java.lang.String" %>

<c:set var="PAGE_SUB_TYPE" value="contest" scope="request"/>
<c:set var="CURRENT_SUB_TAB" value="${tab}" scope="request"/>
