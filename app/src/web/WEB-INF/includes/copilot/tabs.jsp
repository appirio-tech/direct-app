<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<c:set var="contest" value="${viewData.contestStats.contest}"/>
<jsp:include page="/WEB-INF/includes/copilot/copilotLinks.jsp"/>
<s:push value="viewData.contestStats">
<div id="tabs3">
    <ul>
        <li class="firstItem ${requestScope.CURRENT_SUB_TAB eq 'copilotContestDetails' ? 'on' : ''}">
            <a href="<s:url namespace="/copilot" action="copilotContestDetails"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>" class="first">
                <span class="left"><span class="right">Details</span></span></a>
        </li>
        <li ${requestScope.CURRENT_SUB_TAB eq 'copilotContestRegistrants' ? 'class="on"' : ''}>
            <a href="<s:url namespace="/copilot" action="listCopilotContestRegistrants">
                         <s:param name="projectId" value="%{#attr['contest'].id}"/>
                     </s:url>">
                <span class="left"><span class="right">Registrants (<s:property value="registrantsNumber"/>)</span></span></a>
        </li>
        <li ${requestScope.CURRENT_SUB_TAB eq 'copilotContestSubmissions' ? 'class="on"' : ''}>
            <a href="<s:url namespace="/copilot" action="listCopilotContestSubmissions">
                         <s:param name="projectId" value="%{#attr['contest'].id}"/>
                     </s:url>">
                <span class="left"><span class="right">Submissions (<s:property value="submissionsNumber"/>)</span></span></a>
        </li>
        <li class="lastItem ${requestScope.CURRENT_SUB_TAB eq 'copilotReceipt' ? 'on' : ''}">
            <a class="last" href="<s:url namespace="/copilot" action="copilotPostingReceipt">
                         <s:param name="projectId" value="%{#attr['contest'].id}"/>
                     </s:url>">
                <span class="left"><span class="right">Receipt</span></span>
            </a>
        </li>
    </ul>
</div>
</s:push>
<!-- End #tabs3 -->
