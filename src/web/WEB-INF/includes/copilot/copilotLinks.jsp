<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version 1.1
  - Copyright (C) - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace name attribute for s:set with var attribute
--%>

<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="contestLinks">

    <ul class="links">


        <s:set var="contestLink"
               value="'http://' + #application['SERVER_CONFIG_NEW_SERVER_NAME'] + '/challenge-details/' + viewData.contestStats.contest.id + '/?type=develop&noncache=true'"/>

        <s:if test="viewData.contestStats.draft">

            <li id="previewContestButton">
                <a href="<s:property value='#contestLink'/>" target="_blank"
                   class="contestLinkIcon linkIconPreviewContest"
                   style="text-shadow: 0px 1px 1px rgb(255, 255, 255);">Preview Copilot Posting</a>
            </li>

        </s:if>
        <s:else>

            <li id="viewContestButton">
                <a href="<s:property value='#contestLink'/>" target="_blank" class="contestLinkIcon linkIconViewContest"
                   style="text-shadow: 0px 1px 1px rgb(255, 255, 255);">View Copilot Posting</a>
            </li>
        </s:else>

        <li class="splitter"></li>

        <li>
            <link:onlineReviewProjectDetails projectId="${param.projectId}" target="_blank"
                                             styleClass="contestLinkIcon linkIconOnlineReview">Online Review</link:onlineReviewProjectDetails>
        </li>

        <li class="splitter"></li>

        <li>
            <a href="https://<%=ServerConfiguration.FORUMS_SERVER_NAME%>?module=Category&categoryID=${viewData.contestStats.forumId}"
               target="_blank" class="contestLinkIcon linkIconForum">Forum</a>

        </li>

        <li class="splitter"></li>


    </ul>
    <div class="clear"></div>
</div>
