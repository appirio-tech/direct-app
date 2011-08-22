<%--
  - Author: TCSDEVELOPER
  -
  - Version 1.1 (TC Direct Replatforming Release 5) Change notes:
  - - Update EL expression for submission entity because the submission entity type is changed.
  - - Update the online review download submission link tag. 
  -
  - Version: 1.1 (Direct Submission Viewer Release 4)
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the Winners area to be displayed on Studio Submissions Grid, List views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
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
                        <link:studioSubmissionDownload submissionId="${submission.id}" styleClass="downloadFile" />
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
        </div>
        <!-- End #winnerPanel -->
    </c:if>
</s:if>
