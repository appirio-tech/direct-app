<%@ tag language="java" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="tab" required="true" type="java.lang.String" %>

<c:set var="PAGE_TYPE" value="challenges" scope="request"/>
<c:set var="CURRENT_TAB" value="${tab}" scope="request"/>
