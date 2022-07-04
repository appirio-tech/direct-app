<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="name" required="false" type="java.lang.String" %>


<c:choose>
    <c:when test="${not empty name}">
        <s:token name="%{name}"/>
    </c:when>
    <c:otherwise>
        <s:if test="%{tokenName != null}">
            <s:set var="pageTokenName" value="tokenName"/>
        </s:if>
        <s:else>
            <s:set var="pageTokenName" value="%{'token'}"/>
        </s:else>

        <s:token name="%{pageTokenName}" />
        <s:set var="sessionKey" value="%{'struts.tokens.' + #pageTokenName}"/>
    </c:otherwise>
</c:choose>


<script>
    var struts2TokenName = "<s:property value="%{pageTokenName}" />";
    var struts2Token = "<s:property value="#session[#sessionKey]" />";
</script>