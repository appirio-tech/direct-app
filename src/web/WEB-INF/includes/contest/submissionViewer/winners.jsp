<%--
  - Author: TCSDEVELOPER
  - Version: 1.0 (Direct Submission Viewer Release 4)
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
                <if:isConfirmedStudioSubmission submission="${submission}">
                    <c:choose>
                        <c:when test="${submission.userRank eq 1}">
                            <c:set var="suffix" value="st"/>
                        </c:when>
                        <c:when test="${submission.userRank eq 2}">
                            <c:set var="suffix" value="nd"/>
                        </c:when>
                        <c:when test="${submission.userRank eq 3}">
                            <c:set var="suffix" value="rd"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="suffix" value="th"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="winnerCol">
                        <link:studioSubmissionDownload styleClass="downloadFile"
                                                       submissionId="${submission.submissionId}"/>
                        <div class="winnerData">
                            <h3>${submission.userRank}${suffix} Place Winner</h3>
                            <link:user styleClass="handle" handle="${viewData.submitterHandles[submission.submitterId]}"
                                       isStudio="true" userId="${submission.submitterId}"/>
                        </div>
                    </div>
                </if:isConfirmedStudioSubmission>
                <!-- End .winnerCol -->
            </c:forEach>
        </div>
        <!-- End #winnerPanel -->
    </c:if>
</s:if>
