<%--
 - The registrants, competitors, submissions tabs for marathon match contest.
 -
 - Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab
 - - Update the link of submission tab. Make it link to real submission page.
 -
 - Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 - - Update the link to competitor tab to list view.
 -
 - author: Ghost_141
 - Version: 1.2
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
                                                        <s:param name="view">list</s:param>
                                                    </s:url>">
                <span><span>Competitors</span></span>
            </a>
        </li>
        <li <c:if test="${tab eq 'submissions'}"> class="active" </c:if>>
            <a href="<s:url action="mmRegistrants" namespace="/contest">
                        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                        <s:param name="tab">submissions</s:param>
                    </s:url> ">
                <span><span>Submissions</span></span>
            </a>
        </li>
    </ul>
</div>