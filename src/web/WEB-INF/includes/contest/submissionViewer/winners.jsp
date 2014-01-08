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
  
  - Version 1.3 chnage notes: Added area for confirming/rejecting request for final fixes from winner; added Final Fixes
  - area, updated Download icon for winning submission
  -
  - Description: This page fragment renders the Winners area to be displayed on Studio Submissions Grid, List views.
--%>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<c:set var="studioServerName" value="<%=ApplicationServer.STUDIO_SERVER_NAME%>"/>
<s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
<s:if test="formData.roundType.toString() == 'FINAL'">
    <c:if test="${viewData.hasCheckout}">
        <s:if test="viewData.finalFixes.size > 0">

            <h2>Final Fixes</h2>
            <table class="softwareStats" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="10%"/>
                    <col width="20%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <col width="10%"/>
                </colgroup>
                <thead>
                <tr>
                    <th rowspan="2"><span class="icoTHDownload"></span></th>
                    <th rowspan="2" class="left">Handle</th>
                    <th rowspan="2" class="left">Version</th>
                    <th rowspan="2">Final Fix ID</th>
                    <th rowspan="2">Final Fix Date</th>
                    <th rowspan="2">Status</th>
                    <th rowspan="2">Download</th>

                </tr>

                </thead>
                <tbody>
                <s:iterator value="viewData.finalFixes" status="rowStatus">
                    <tr>
                        <s:if test='#rowStatus.count == 1'><s:set var="iconStyle"
                                                                  value="'gold'"
                                                                  scope="page"/></s:if>
                        <s:else><s:set var="iconStyle" value="'normal'"
                                       scope="page"/></s:else>
                        <td>
                            <link:studioSubmissionDownload submissionId="${submissionId}"
                                                           styleClass="icoZip ${iconStyle}"
                                                           original="true"/>
                        </td>
                        <td class="left">
                            <link:user userId="${finalFixerUserId}"
                                       handle="${finalFixerHandle}/>"
                                       styleClass="handle"/>
                        </td>
                        <td class="left">
                            Version <s:property value="versionNumber"/>
                        </td>
                        <td>
                            <s:property value="uploadId"/>
                        </td>
                        <td>
                            <s:date name="finalFixDate" format="MM.dd.yyyy"/>
                        </td>
                        <td>
                            <s:if test='approved == false && reviewed == true'>Rejected</s:if><s:else></s:else>
                        </td>
                        <td>
                            <link:studioSubmissionDownload submissionId="${submissionId}"
                                                           styleClass="icoZip ${iconStyle}"
                                                           original="true"/>
                        </td>

                    </tr>
                </s:iterator>
                </tbody>

            </table>

        </s:if>


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
                        
                        <c:choose>
                            <c:when test="${submission.placement eq 1 and not empty viewData.finalFixes}">
                                <c:set var="latestFinalFix" value="${viewData.finalFixes[0]}"/>
                                <link:studioSubmissionDownload submissionId="${latestFinalFix.submissionId}"
                                                               styleClass="downloadFile"
                                                               original="true"/>
                            </c:when>
                            <c:otherwise>
                                <link:studioSubmissionDownload submissionId="${submission.id}" styleClass="downloadFile"
                                                               original="true"/>
                            </c:otherwise>
                        </c:choose>
                        
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
                   href="https://${studioServerName}?module=DownloadAllSubmissions&amp;ct=${contestId}&amp;round=final&amp;type=original">
                    <span>DOWNLOAD ALL WINNERS</span></a>
            </div>
        </div>
    <c:if test="${viewData.hasCheckout and viewData.finalFixesDecisionMade ne null and not viewData.finalFixesDecisionMade}">
            <div class="finalFixConfirm">
                <div class="p1">
                    <p>Would you like the winner to provide Final Fixes?  <br/>
                        <a target="_blank" href="http://community.topcoder.com/studio/help/final-fixes/faqs/">
                            Final Fixes are explained here</a>.
                        If you aren't sure, please ask your copilot before clicking "no".</p> <br/>
                    <a href="javascript:;" class="noBtn">NO</a>
                    <a href="javascript:;" class="yesBtn">YES</a>
                </div>

                <div class="p2 hide">
                    <p>You have chosen not to request Final Fixes from the winner and the challenge has now
                        closed.</p>

                </div>
            </div>
    </c:if>

        <!-- End #winnerPanel -->
    </c:if>
</s:if>
