<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%--
  - Author: isv
  -
  - Version: 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the form for reviewing the Final Fixes for Studio contests.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<c:set var="STUDIO_SERVER_NAME" value="<%=ApplicationServer.STUDIO_SERVER_NAME%>"/>
<s:if test="viewData.finalFixStatus.toString() == 'IN_PROGRESS' || viewData.finalFixStatus.toString() == 'REVIEW' || viewData.finalFixStatus.toString() == 'COMPLETED'">
    <s:set var="IN_PROGRESS" value="viewData.finalFixStatus.toString() == 'IN_PROGRESS'"/>
    <s:set var="REVIEW" value="viewData.finalFixStatus.toString() == 'REVIEW'"/>
    <s:set var="COMPLETED" value="viewData.finalFixStatus.toString() == 'COMPLETED'"/>
    <c:set var="isFirstFinalFixRound" value="${fn:length(viewData.finalFixes) eq 1}"/>
    <c:set var="finalFixesCount" value="${fn:length(viewData.finalFixes)}"/>
    <c:choose>
        <c:when test="${IN_PROGRESS}">
            <c:set var="divStyleClass" value="inProgressFF"/>
        </c:when>
        <c:when test="${REVIEW}">
            <c:set var="divStyleClass" value="reviewFF"/>
        </c:when>
        <c:when test="${COMPLETED}">
            <c:set var="divStyleClass" value="ffComplete"/>
        </c:when>
        <c:otherwise>
            <c:set var="divStyleClass" value=""/>
        </c:otherwise>
    </c:choose>

    <div class="containerNoPadding">
        <div class="${divStyleClass}">
            <c:if test="${REVIEW}">
                <a href="javascript:;" class="editBtn hide REJECT_FF_CONFIRMATION_SHOW EDIT_REVIEW_FF_HIDE" id="editRejectedFFBtn"></a>
            </c:if>
            <div class="SubmissionSlotTitle">
                <c:choose>
                    <c:when test="${IN_PROGRESS}">
                        <h3 class="red">Final Fixes in Progress</h3>

                        <p class="red">
                            Your final fix request has been sent to the winner and is now visible on the <a
                                href="http://${STUDIO_SERVER_NAME}/?module=ViewContestDetails&amp;ct=${viewData.contestId}">Studio site</a>. 
                            You will receive an email notification
                            once the fixed files have been uploaded by the winner.
                        </p>
                    </c:when>
                    <c:when test="${REVIEW}">
                        <h3 class="red REJECT_FF_CONFIRMATION_HIDE EDIT_REVIEW_FF_SHOW  reviewFinalFixesTitle">
                            <c:choose>
                                <c:when test="${isFirstFinalFixRound}">
                                    Review Final Fixes
                                </c:when>
                                <c:otherwise>
                                    Review Second Round of Final Fixes
                                </c:otherwise>
                            </c:choose>
                        </h3>

                        <h3 class="red hide REJECT_FF_CONFIRMATION_SHOW EDIT_REVIEW_FF_HIDE">
                        </h3>
                        <p class="hide REJECT_FF_CONFIRMATION_SHOW EDIT_REVIEW_FF_HIDE">
                            Review the unfixed items below before submitting them to the winner again.
                        </p>
                    </c:when>
                    <c:when test="${COMPLETED}">
                        <h3 class="red">Final Fixes Completed</h3>
                    </c:when>
                </c:choose>
            </div>
            <!-- End .SubmissionSlotTitle -->
            <div class="tabsPanel">
                <ul>
                    <c:forEach items="${viewData.finalFixes}" var="finalFix" varStatus="loop">
                        <c:set var="isCurrentFinalFixIteration" value="${(loop.index + 1) eq finalFixesCount}"/>
                        <li <c:if test="${isCurrentFinalFixIteration}">class="on"</c:if>>
                            <a href="javascript:;" class="ffTabBtn" rel="${loop.index}">
                                <c:choose>
                                    <c:when test="${loop.index eq 0}">
                                        <span>Final Fixes</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>Final Fixes (R${loop.index + 1})</span>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            
            <c:forEach items="${viewData.finalFixes}" var="finalFix" varStatus="loop">
                <c:set var="isCurrentFinalFixIteration" value="${(loop.index + 1) eq finalFixesCount}"/>
                <c:if test="${loop.index > 0}">
                    <c:set var="previousFinalFix" value="${viewData.finalFixes[loop.index - 1]}"/>
                </c:if>
                <c:if test="${(REVIEW or COMPLETED) and isCurrentFinalFixIteration}">
                    <s:url var="url" namespace="/contest" action="studioSubmission">
                        <s:param name="projectId" value="%{#attr['finalFix'].submission.upload.project}"/>
                        <s:param name="formData.submissionId" value="%{#attr['finalFix'].submission.id}"/>
                        <s:param name="formData.roundType" value="'STUDIO_FINAL_FIX_SUBMISSION'"/>
                    </s:url>
                    <div class="ffSubmit ffItems${loop.index}">
                        <div class="submission">
                            <label>Submission ID: <span class="id">${finalFix.submission.id}</span></label>

                            <a class="thumbGrid" href="${url}">
                                <ui:studioSubmissionImage submissionId="${finalFix.submission.id}" imageType="small"
                                                          dimension="200" styleClass=""/>
                            </a>

                            <div class="submissionAction" style="display: block;">
                                <div class="actionMask">
                                    <a class="actButtonzoom" href="${url}"><span class=""></span></a>

                                    <div class="dialog-mini-wrapper">
                                        <div class="dialog-mini-arrow"></div>
                                        <div class="dialog-mini-sm">
                                            <div class="dialog-mini-sm-inside">
                                                <div class="dialog-mini-sm-outside">View Larger Size</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .submissionAction -->
                        </div>

                        <link:studioSubmissionDownload submissionId="${finalFix.submission.id}" original="true" styleClass="downloadSubmission">
                            <span>DOWNLOAD FIXED FILES</span>
                        </link:studioSubmissionDownload>
                        
                    </div>
                </c:if>

                <c:if test="${isCurrentFinalFixIteration}"><div id="editableFFArea"></c:if>
                <div class="ffItems ffItems${loop.index} ${not isCurrentFinalFixIteration ? 'hide' : ''}">
                    <c:set var="showFixedColumn" value="${not isCurrentFinalFixIteration or REVIEW or COMPLETED or isCurrentFinalFixIteration and finalFix.committed}"/>
                    <table cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="40x">
                            <col>
                            <c:if test="${showFixedColumn}">
                                <col width="90px">
                            </c:if>
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="left" colspan="2">Final Fix Items</th>
                            <c:if test="${showFixedColumn}">
                                <th class="left">Fixed?</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${finalFix.comments}" var="ffComment" varStatus="loop1">
                            <c:if test="${not isCurrentFinalFixIteration or REVIEW or finalFix.committed or IN_PROGRESS and isCurrentFinalFixIteration and not (ffComment.fixed or ffComment.fixed eq null and previousFinalFix.comments[loop1.index].fixed)}">
                            <tr <c:if test="${loop1.index mod 2 eq 0}">class="odd"</c:if>>
                                <td class="first">${loop1.index + 1}.</td>
                                <td class="left"><c:out value="${ffComment.comment}"/></td>
                                <c:if test="${showFixedColumn}">
                                <c:choose>
                                    <c:when test="${not isCurrentFinalFixIteration or COMPLETED or isCurrentFinalFixIteration and finalFix.committed}">
                                        <c:choose>
                                            <c:when test="${ffComment.fixed}">
                                                <td><span class="correct"></span></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><span class="incorrect"></span></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${REVIEW and isCurrentFinalFixIteration}">
                                        <td>
                                            <input type="checkbox" class="ffItemFixedStateBox"
                                                   <c:if test="${ffComment.fixed or ffComment.fixed eq null and previousFinalFix.comments[loop1.index].fixed}">checked="checked"</c:if>/>
                                        </td>
                                    </c:when>
                                </c:choose>
                                </c:if>
                            </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${not REVIEW or COMPLETED}">
                        <c:if test="${not empty finalFix.additionalComment}">
                            <dl class="comments">
                                <dt>Comments</dt>
                                <dd><c:out value="${finalFix.additionalComment}"/></dd>
                            </dl>
                        </c:if>
                    </c:if>
                </div>
                <c:if test="${REVIEW and isCurrentFinalFixIteration}">
                    <div class="ffAction ffItems${loop.index}">
                        <div class="comment reviewFFControlsBlock ">
                            <h4>Additional Comments</h4>
                            <textarea cols="10" rows="10" id="additionalFFComment"><c:out value="${finalFix.additionalComment}" /></textarea>
                        </div>
                        <ul class="ffActionButtons reviewFFControlsBlock EDIT_REVIEW_FF_HIDE">
                            <li class="first">
                                <div style="height: 58px;">
                                    <a class="button6 saveBtn saveFFBtn" href="javascript:;">
                                        <span class="left"><span class="right">SAVE</span></span></a>
                                    <p>If you need to leave this page, save your progress now.</p>
                                </div>
                            </li>
                            <li>
                                <div style="height: 58px;">
                                    <a class="button6 rejectBtn rejectFFBtn" href="javascript:;">
                                        <span class="left"><span class="right">REJECT</span></span></a>
                                    <p>If there are unfixed items, reject the submission and Final Fixes will be
                                        resubmitted to the winner for Round ${fn:length(viewData.finalFixes) + 1}.</p>
                                </div>
                            </li>
                            <li>
                                <div style="height: 58px;">
                                    <a class="button6 acceptBtn acceptFFBtn" href="javascript:;">
                                        <span class="left"><span class="right">ACCEPT</span></span></a>
                                    <p>If all items are fixed above, you can accept and close the Final Fixes.</p>
                                </div>
                            </li>
                        </ul>
                        <div class="bottomBtns hide rejectControls EDIT_REVIEW_FF_SHOW">
                            <p>
                                <a class="button6" href="javascript:;" id="saveEditRejectedFFBtn">
                                    <span class="left"><span class="right">SAVE CHANGES</span></span></a>
                                <a href="javascript:;" id="cancelEditRejectedFFBtn">Cancel</a>
                            </p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${isCurrentFinalFixIteration}"></div></c:if>
            </c:forEach>
            
            <%-- Area for confirming the FF rejection --%>
            <c:if test="${REVIEW}">
                <div class="ffItems lesspadding hide REJECT_FF_CONFIRMATION">
                    <table cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="40x">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="left" colspan="2">Unfixed Items</th>
                        </tr>
                        </thead>
                        <tbody id="unfixedItemsBody">
                        </tbody>
                    </table>
                    <dl class="comments">
                        <dt>Comments</dt>
                        <dd id="ffRejectionReason"></dd>
                    </dl>
                </div>
                <div class="bottomBtns hide REJECT_FF_CONFIRMATION">
                    <a class="button6" href="javascript:;" id="startNextFFRoundBtn">
                        <span class="left"><span class="right">SUBMIT REQUEST FOR FINAL FIXES</span></span></a>
                </div>
            </c:if>
        
        </div>
        <!-- End .confirmFF -->
    </div>
    <input type="hidden" id="finalFixesCount" value="${fn:length(viewData.finalFixes)}"/>
</s:if>
<input type="hidden" id="studioSiteBaseURL" value="${STUDIO_SERVER_NAME}"/>
