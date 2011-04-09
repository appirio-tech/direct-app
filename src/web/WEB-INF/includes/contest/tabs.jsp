<%--
  - Author: isv, Veve
  - Version: 1.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the tabs for dashboard and contest pages.
  -
  - Version 1.1 (Submission Viewer Release 1 assembly) changes: linked Submissions tab to submission pages
  - for Studio contests.
  - Version 1.2 (TC Cockpit Bug Tracking R1 Contest Tracking  assembly) changes: Add new tab for the issue tracking for
  - software and studio contest.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:set var="contestStats" value="viewData.contestStats" scope="page"/>
<s:push value="viewData.contestStats">
    <jsp:include page="/WEB-INF/includes/contest/link.jsp"/>
    <div id="tabs3">
        <ul>
            <li class="firstItem <c:if test="${requestScope.CURRENT_SUB_TAB eq 'details'}">on</c:if>">

                <s:if test="isStudio == true">
                    <a href="<s:url action="contest/detail" namespace="/"><s:param name="contestId" value="contest.id"/></s:url>" class="first">
                    <span class="left"><span class="right">Details</span></span></a>
                </s:if>
                <s:if test="isStudio == false">
                    <a href="<s:url action="contest/detail" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>" class="first">
                    <span class="left"><span class="right">Details</span></span></a>
                </s:if>
            </li>
            <li <c:if test="${requestScope.CURRENT_SUB_TAB eq 'specReview'}">class="on"</c:if>>
                <if:isStudioContest contestStats="${contestStats}" negate="true">
                    <a href="<s:url action="contest/viewSpecReview" namespace="/">
                        <s:param name="contestId" value="contest.id"/>
                        <s:param name="studio" value="false"/>
                    </s:url>" >
                
                    <span class="left"><span class="right">Spec Review</span></span></a>
                </if:isStudioContest>
            </li>
            <li <c:if test="${requestScope.CURRENT_SUB_TAB eq 'registrants'}">class="on"</c:if>>

                 <s:if test="isStudio == true">
                    <a href="<s:url action="contest/contestRegistrants" namespace="/"><s:param name="contestId" value="contest.id"/></s:url>">
                    <span class="left"><span class="right">Registrants (<s:property value="registrantsNumber"/>)</span></span></a>
                </s:if>
                <s:if test="isStudio == false">
                    <a href="<s:url action="contest/contestRegistrants" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>">
                    <span class="left"><span class="right">Registrants (<s:property value="registrantsNumber"/>)</span></span></a>
                </s:if>
            </li>
            <li <c:if test="${requestScope.CURRENT_SUB_TAB eq 'submissions'}">class="on"</c:if>>
                <if:isStudioContest contestStats="${contestStats}">
                    <link:studioSubmissionsGrid contestId="${contestStats.contest.id}">
                        <span class="left">
                            <span class="right">Submissions (<s:property value="submissionsNumber"/>)</span>
                        </span>
                    </link:studioSubmissionsGrid>
                </if:isStudioContest>
                <if:isStudioContest contestStats="${contestStats}" negate="true">
                    <link:softwareSubmissionsList contestId="${contestStats.contest.id}">
                        <span class="left">
                            <span class="right">Submissions (<s:property value="submissionsNumber"/>)</span>
                        </span>
                    </link:softwareSubmissionsList>
                </if:isStudioContest>
            </li>
            <li <c:if test="${requestScope.CURRENT_SUB_TAB eq 'issueTracking'}">class="on"</c:if>>

                 <s:if test="isStudio == true">
                    <a href="<s:url action="contest/contestIssuesTracking" namespace="/"><s:param name="contestId" value="contest.id"/></s:url>">
                    <span class="left"><span class="right">Issue Tracking (<s:property value="totalJiraIssuesNumber"/>)</span></span></a>
                </s:if>
                <s:if test="isStudio == false">
                    <a href="<s:url action="contest/contestIssuesTracking" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>">
                    <span class="left"><span class="right">Issue Tracking (<s:property value="totalJiraIssuesNumber"/>)</span></span></a>
                </s:if>
            </li>
			<li id="rReceiptTab" class="lastItem <c:choose>
				<c:when test="${requestScope.CURRENT_SUB_TAB eq 'receipt'}">on</c:when>
				</c:choose>">
                <s:if test="isStudio == true">
					<a class="last" href="<s:url action="contest/receipt" namespace="/"><s:param name="contestId" value="contest.id"/></s:url>">
                </s:if>
                <s:if test="isStudio == false">
					<a class="last" href="<s:url action="contest/receipt" namespace="/"><s:param name="projectId" value="contest.id"/></s:url>">
                </s:if>				
				<span class="left"><span class="right">Receipt</span></span></a>
			</li>

        </ul>
    </div>
    <!-- End #tabs3 -->
</s:push>
