<%--
  - Author: isv
  -
  - Version: 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the form for requesting the Final Fixes for Studio contests.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:if test="viewData.finalFixStatus.toString() == 'NOT_STARTED'">
    
<div class="containerNoPadding finalFix-notStarted">
    <div class="SubmissionSlotTitle">
        <h3 class="red">Request Final Fixes</h3>

        <p>
            You are requesting Final Fixes for the winning file shown below.
            Need to take another look at the submission before you list your
            Final Fixes? Click on the "Submissions" tab above to view the
            winning file in Gallery mode.
        </p>
    </div>
    <!-- End .SubmissionSlotTitle -->
    <div class="specityFF">
        <div class="winnerBox">
            <link:studioSubmissionDownload submissionId="${viewData.finalFixes[0].submission.id}" original="true" styleClass="zip">
            </link:studioSubmissionDownload>

            <div class="meta">
                <h3>1st Place Winner</h3>
                <c:if test="${viewData.winnerResource ne null}">
                    <link:user styleClass="handle"
                               handle="${viewData.winnerResource.properties['Handle']}"
                               userId="${viewData.winnerResource.properties['External Reference ID']}"/>
                </c:if>
                <span>${viewData.finalFixes[0].submission.id}</span>
            </div>
        </div>
        <div class="alert">
            <p class="red">Remember - Final Fixes must meet the <a
                    href="http://community.topcoder.com/studio/the-process/final-fixes/">definition
                stated here</a>. Don't ask to combine submissions and don't
                add new tasks that were not originally defined in the
                challenge.</p>

            <p>Final Fixes must be submitted to the winner within 5 days of
                announcing winners. Your winner has 72 hours to complete the
                Fixes.</p>
        </div>
        <div class="addTasks">
            <div class="task">
                <label>Describe an item to be fixed:</label>
                <textarea cols="10" rows="5" class="FFitem"></textarea>
            </div>
            <div class="task cloneit noValidation">
                <textarea cols="10" rows="5" class="FFitem"></textarea>
            </div>
            <div class="addLink"><a href="javascript:void(0)">Add more
                tasks</a></div>
        </div>
        <div class="bottomBtns">
            <a href="javascript:;" class="button6 EDIT_FF_HIDE" id="previewFFRequestBtn"><span
                    class="left"><span class="right">PREVIEW REQUEST FOR FINAL FIXES</span></span></a>

            <p id="editFFButtonsArea" class="EDIT_FF_SHOW hide">
                <a href="javascript:;" class="button6" id="saveEditFFBtn">
                    <span class="left"><span class="right">SAVE CHANGES</span></span></a>
                <a href="javascript:;" id="cancelEditFFBtn">Cancel</a>
            </p>
        </div>
    </div>
    <!-- End .specityFF -->
</div>

    <div class="containerNoPadding finalFix-notStarted-confirm" style="display: none">
        <div class="confirmFF">
            <a class="editBtn" id="editFFBtn" href="javascript:;"></a>

            <div class="SubmissionSlotTitle confirmFFdiv">
                <h3 class="red">Confirm Final Fixes</h3>

                <p>
                    Review your Final Fixes below before submitting them to the winner.
                </p>
            </div>
            <!-- End .SubmissionSlotTitle -->
            <div class="clear"></div>
            <div class="tabsPanel">
                <ul>
                    <li class="on">
                        <a href="javascript:;">
                            <span>Final Fixes</span>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="ffItems">
                <table cellpadding="0" cellspacing="0">
                    <colgroup>
                        <col width="40x">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="left" colspan="2">Final Fix Items</th>
                    </tr>
                    </thead>
                    <tbody id="ffConfirmTBody">
                    </tbody>
                </table>
            </div>
            <div class="bottomBtns">
                <a href="javascript:;" class="button6" id="saveFFItemsBtn">
                    <span class="left"><span class="right">SUBMIT REQUEST FOR FINAL FIXES</span></span></a>
            </div>
        </div>
        <!-- End .confirmFF -->
    </div>
</s:if>
<input type="hidden" id="contestId" value="${viewData.contestId}"/>
