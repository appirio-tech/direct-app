<%--
  - Author: TCSASSEMBER, TCSASSEMBER
  - Version: 1.1 (For TCCC-2827)
  - Copyright (C) 2011 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest links area for Contest Details page
  -
  - Version 1.1 (TC Direct "Contest Links and Button" Update 24Hr Assembly) changes:
  - 1. Change the  style for the links. 
  - 2. Add the 'View Contest', 'Repost Contest', 'New Version' links.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="contestLinks">
    <ul class="links">
		<li>
        <a href="javascript:;" id="repostButton" class="contestLinkIcon linkIconRepostContest" style="text-shadow: 0px 1px 1px rgb(255, 255, 255); display:none;">Repost</a>
        </li>
		<li class="splitter" id="repostButtonSplitter" style="display:none;"></li>
		<li>
		<a href="javascript:;" id="newVersionButton" class="contestLinkIcon linkIconNewVersion" style="text-shadow: 0px 1px 1px rgb(255, 255, 255); display:none;">New Version</a>
		</li>
		<li class="splitter" id="newVersionButtonSplitter" style="display:none;"></li>
		<li id="previewContestButton" style="display:none;">
		<a href="javascript:previewContest();" class="contestLinkIcon linkIconPreviewContest" style="text-shadow: 0px 1px 1px rgb(255, 255, 255);">Preview Contest</a>
        </li>
		<li id="viewContestButton" style="display:none;">
		<a href="javascript:previewContest();" class="contestLinkIcon linkIconViewContest" style="text-shadow: 0px 1px 1px rgb(255, 255, 255);">View Contest</a>
        </li>
		<li class="splitter"></li>
		
        <s:if test="!viewData.contestStats.isStudio">
		<li>
        <link:onlineReviewProjectDetails projectId="${param.projectId}" target="_blank" styleClass="contestLinkIcon linkIconOnlineReview">Online Review</link:onlineReviewProjectDetails>
        </li>
		<li class="splitter"></li>
        <li><a href="javascript:previewContest();" class="contestLinkIcon linkIconRegistrationPage">Registration Page</a></li>
		<li class="splitter"></li>
        </s:if>
        <li>
        <s:if test="!viewData.contestStats.isStudio">
        <a href="http://forums.topcoder.com/?module=Category&categoryID=${viewData.contestStats.forumId}" target="_blank"  class="contestLinkIcon linkIconForum" >Forum</a>
        </s:if>
        <s:else>
        <a href="http://studio.topcoder.com/forums?module=ThreadList&forumID=${viewData.contestStats.forumId}" target="_blank" class="contestLinkIcon linkIconForum">Forum</a>
        </s:else>
        </li>
		<li class="splitter"></li>
        <li>
        <s:if test="!viewData.contestStats.isStudio">
        <link:jira projectId="${param.projectId}" target="_blank" styleClass="contestLinkIcon linkIconJIRA" >JIRA</link:jira>
        </s:if>
        <s:else>
        <link:jira projectId="${param.contestId}" target="_blank" styleClass="contestLinkIcon linkIconJIRA" >JIRA</link:jira>
        </s:else>
        </li>

        <c:if test="${!viewData.contestStats.isStudio && ! empty viewData.contestStats.svn}">
		<li class="splitter"></li>
        <li><a href="${viewData.contestStats.svn}" target="_blank"  class="contestLinkIcon linkIconSVN">SVN</a></li>
        </c:if>
    </ul>
    <div class="clear"></div>
</div>

