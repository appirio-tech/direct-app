<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="contestLinks">

    <ul class="links">


        <s:set name="contestLink"
               value="'http://www.topcoder.com/challenge-details/' + viewData.contestStats.contest.id + '/?type=develop&noncache=true'"/>

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
            <a href="https://apps.topcoder.com/forums/?module=Category&categoryID=${viewData.contestStats.forumId}"
               target="_blank" class="contestLinkIcon linkIconForum">Forum</a>

        </li>

        <li class="splitter"></li>


    </ul>
    <div class="clear"></div>
</div>
