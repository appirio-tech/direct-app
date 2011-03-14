<%--
  - Author: TCSASSEMBER
  - Version: 1.0 (For TCCC-2827)
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest links area for Contest Details page
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="contest-links">
    <div class="link-title"><strong>Links:</strong></div>
    <ul class="links">
        <s:if test="!viewData.contestStats.isStudio">
        <li>
        <link:onlineReviewProjectDetails projectId="${param.projectId}" target="_blank">Online Review</link:onlineReviewProjectDetails>
        </li>
        <li><a href="javascript:previewContest();">Registration Page</a></li>
        </s:if>
        <li>
        <s:if test="!viewData.contestStats.isStudio">
        <a href="http://forums.topcoder.com/?module=Category&categoryID=${viewData.contestStats.forumId}" target="_blank">Forum</a>
        </s:if>
        <s:else>
        <a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${viewData.contestStats.forumId}" target="_blank">Forum</a>
        </s:else>
        </li>
        <li>
        <s:if test="!viewData.contestStats.isStudio">
        <link:jira projectId="${param.projectId}" target="_blank">JIRA</link:jira>
        </s:if>
        <s:else>
        <link:jira projectId="${param.contestId}" target="_blank">JIRA</link:jira>
        </s:else>
        </li>
        
        <c:if test="${!viewData.contestStats.isStudio && ! empty viewData.contestStats.svn}">
        <li><a href="${viewData.contestStats.svn}" target="_blank">SVN</a></li>
        </c:if>
    </ul>
    <div class="clear"></div>
</div>
