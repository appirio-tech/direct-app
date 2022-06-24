<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.PAGE_TYPE eq 'admin'}">
    <jsp:doBody/>
</c:if>
