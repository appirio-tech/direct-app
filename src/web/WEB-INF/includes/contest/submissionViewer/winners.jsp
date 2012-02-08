<%--
  - Author: isv
  -
  - Version 1.1 (TC Direct Replatforming Release 5) Change notes:
  - - Update EL expression for submission entity because the submission entity type is changed.
  - - Update the online review download submission link tag. 
  -
  - Version: 1.2 (Direct Submission Viewer Release 4)
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  
  - Version 1.2 (Module Assembly - TopCoder Studio and Cockpit Download All Submissions Feature) chnage notes: 
  -                                Added DOWNLOAD ALL WINNERS button
  -
  - Description: This page fragment renders the Winners area to be displayed on Studio Submissions Grid, List views.
--%>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<c:set var="studioServerName" value="<%=ApplicationServer.STUDIO_SERVER_NAME%>"/>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<s:if test="formData.roundType.toString() == 'FINAL'">
    <c:if test="${viewData.hasCheckout}">
        <div id="winnerPanel">
            <c:forEach items="${viewData.contestSubmissions}"
                       var="submission">
                <c:if test="${submission.prize != null}">
                    <c:choose>
                        <c:when test="${submission.placement eq 1}">
                            <c:set var="suffix" value="st"/>
                        </c:when>
                        <c:when test="${submission.placement eq 2}">
                            <c:set var="suffix" value="nd"/>
                        </c:when>
                        <c:when test="${submission.placement eq 3}">
                            <c:set var="suffix" value="rd"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="suffix" value="th"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="winnerCol">
                        <link:studioSubmissionDownload submissionId="${submission.id}" styleClass="downloadFile" original="true" />
                        <div class="winnerData">
                            <c:if test="${not submission.extra}">
                                <h3>${submission.placement}${suffix} Place Winner</h3>
                            </c:if>
                            <c:if test="${submission.extra}">
                                <h3>Client Selection</h3>
                            </c:if>
                            <link:user styleClass="handle" handle="${viewData.submissionResources[submission.id].properties['Handle']}"
                                       userId="${viewData.submissionResources[submission.id].properties['External Reference ID']}"/>
                        </div>
                    </div>
                <!-- End .winnerCol -->
                </c:if>
            </c:forEach>
            <div class="right">
                <a class="downloadAllbtn"
                   href="http://${studioServerName}?module=DownloadAllSubmissions&amp;ct=${contestId}&amp;round=final&amp;type=original">
                    <span>DOWNLOAD ALL WINNERS</span></a>
            </div>
        </div>
        <!-- End #winnerPanel -->
    </c:if>
</s:if>
