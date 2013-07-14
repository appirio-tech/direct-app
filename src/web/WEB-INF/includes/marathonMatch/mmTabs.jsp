<%--
 - The registrants, competitors, submissions tabs for marathon match contest.
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="tabList">
    <ul>
        <li <c:if test="${tab eq null}">class="active"</c:if> >
            <a href="<s:url action="mmRegistrants" namespace="/contest">
                                                        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                                    </s:url>">
                <span><span>Registrants</span></span>
            </a>
        </li>
        <li <c:if test="${tab eq 'competitors'}">class="active"</c:if> >
            <a href="<s:url action="mmRegistrants" namespace="/contest">
                                                        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                                        <s:param name="tab">competitors</s:param>
                                                    </s:url>">
                <span><span>Competitors</span></span>
            </a>
        </li>
        <li>
            <a href="">
                <span><span>Submissions</span></span>
            </a>
        </li>
    </ul>
</div>