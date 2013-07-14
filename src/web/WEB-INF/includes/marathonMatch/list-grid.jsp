<%--
 - The list and grid button for marathon match contest.
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<ul class="gridListView">
    <li>
        <a href="
            <c:if test="${view eq 'list'}">
                <s:url action="mmRegistrants" namespace="/contest">
                    <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                    <s:param name="tab">competitors</s:param>
                </s:url>
            </c:if>"
            id="gridView" <c:if test="${view eq null || view eq 'grid'}">class="active"</c:if>>
            GRID
        </a>
    </li>
    <li>
        <a href="
            <c:if test="${view eq null || view eq 'grid'}">
                <s:url action="mmRegistrants" namespace="/contest">
                    <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                    <s:param name="tab">competitors</s:param>
                    <s:param name="view">list</s:param>
                </s:url>
            </c:if>"
            id="listView" <c:if test="${view eq 'list'}">class="active"</c:if>>
            LIST
        </a>
    </li>
</ul>