<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:push value="viewData.contestStats">
    <div id="tabs3">
        <ul>
            <li class="firstItem <c:if test="${requestScope.CURRENT_SUB_TAB eq 'details'}">on</c:if>">
                <a href="<s:url action="contestDetails" namespace="/"><s:param name="formData.contestId" value="contest.id"/></s:url>" class="first">
                    <span class="left"><span class="right">Details</span></span></a>
            </li>
            <li <c:if test="${requestScope.CURRENT_SUB_TAB eq 'registrants'}">class="on"</c:if>>
                <a href="<s:url action="contestRegistrants" namespace="/"><s:param name="formData.contestId" value="contest.id"/></s:url>">
                    <span class="left"><span class="right">Registrants (<s:property value="registrantsNumber"/>)</span></span></a>
            </li>
            <li>
                <a href="javascript:alert('To be implemented by sub-sequent assemblies');">
                    <span class="left"><span class="right">Submissions (<s:property value="submissionsNumber"/>)</span></span></a></li>
            <li class="lastItem">
                <a href="javascript:alert('To be implemented by sub-sequent assemblies');"
                   class="last"><span class="left"><span class="right">Milestone Feedback</span></span></a></li>
        </ul>
    </div>
    <!-- End #tabs3 -->
</s:push>
