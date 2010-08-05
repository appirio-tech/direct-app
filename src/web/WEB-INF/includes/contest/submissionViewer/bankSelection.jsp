<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders Bank Selection area to be displayed on Studio Submissions Grid, List and
  - Single views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="bankSelection">
    <div id="bankSelectionHead">
        <h3>Selection Bank</h3>
        <ul id="bankSelectionTab">
            <s:set var="contestId" value="viewData.contestStats.contest.id" scope="page"/>
            <s:if test="viewData.hasMilestoneRound">
                <s:set var="isMilestoneRound" value="formData.roundType.toString() == 'MILESTONE'" scope="page"/>
                <li <s:if test="formData.roundType.toString() != 'MILESTONE'">class="off"</s:if>>
                    <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${true}">
                        <span>Milestone (R1)</span>
                    </link:studioSubmissionsGrid>
                </li>
                <li <s:if test="formData.roundType.toString() != 'FINAL'">class="off"</s:if>>
                    <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${false}">
                        <span>Final (R2)</span>
                    </link:studioSubmissionsGrid>
                </li>
            </s:if>
            <s:else>
                <s:set var="isMilestoneRound" value="false" scope="page"/>
                <li>
                    <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${false}">
                        <span>Final (R1)</span>
                    </link:studioSubmissionsGrid>
                </li>
            </s:else>
        </ul>
        <!-- End #bankSelectionTab -->
    </div>
    <!-- End #bankSelectionHead -->

    <div id="bankSelectionContent">
        <p class="information">Drag your selections to the provided slots. Selections can be removed and/or replaced
            anytime you wish. Click on a slot to view its contents. Alternatively, you can click the corresponding
            buttons under each submission.</p>

        <div id="bankSelectionSlotsContainer">
            <div id="bankSelectionSlots">
                <div id="bankSelectionItem">
                    <ul id="bankSelectionItemList">
                        <li id="firstPrize" class="sortableItem">
                            <!-- <a href="#" class="btn_remove"></a>-->
                            <a href="bankSelection.jsp#" class="thumb"><span></span>
                                <!--<img src="/images/submission/thumb_slot_5.png" alt="" />-->
                            </a>
                            <!--<label>270008</label>-->
                        </li>
                        <li id="secondPrize" class="sortableItem">
                            <!-- <a href="#" class="btn_remove"></a>-->
                            <a href="bankSelection.jsp#" class="thumb"><span></span>
                                <!--<img src="/images/submission/thumb_slot_5.png" alt="" />-->
                            </a>
                            <!--<label>270008</label>-->
                        </li>
                        <li id="thirdPrize" class="sortableItem">
                            <!-- <a href="#" class="btn_remove"></a>-->
                            <a href="bankSelection.jsp#" class="thumb"><span></span>
                                <!--<img src="/images/submission/thumb_slot_5.png" alt="" />-->
                            </a>
                            <!--<label>270008</label>-->
                        </li>
                        <li id="fourthPrize" class="sortableItem">
                            <!-- <a href="#" class="btn_remove"></a>-->
                            <a href="bankSelection.jsp#" class="thumb"><span></span>
                                <!--<img src="/images/submission/thumb_slot_5.png" alt="" />-->
                            </a>
                            <!--<label>270008</label>-->
                        </li>
                        <li id="fifthPrize" class="sortableItem">
                            <!-- <a href="#" class="btn_remove"></a>-->
                            <a href="bankSelection.jsp#" class="thumb"><span></span>
                                <!--<img src="/images/submission/thumb_slot_5.png" alt="" />-->
                            </a>
                            <!--<label>270008</label>-->
                        </li>

                        <li id="extraPrize">
                            <a href="bankSelection.jsp#" class="thumb"><span id="numExtra"></span></a>
                        </li>
                    </ul>
                </div>

                <div id="bankSelectionFolder">
                    <ul>
                        <li>
                            <link:studioSubmissionsGrid contestId="${contestId}" milestoneRound="${isMilestoneRound}">
                                All Submissions (<s:property value="viewData.submissionsCount"/>)
                            </link:studioSubmissionsGrid>
                        </li>
                        <li id="likeFolder"><a href="studio-final-like.html">Submission You Like (<span
                                id="likeCount">0</span>)</a></li>
                        <li id="dislikeFolder"><a href="studio-final-dislike.html">Submission You Don't Like (<span
                                id="dislikeCount">0</span>)</a></li>
                    </ul>
                </div>
                <!-- End #bankSelectionFolder -->
            </div>
            <!-- End #bankSelectionSlotsContainer -->
        </div>
        <!-- End #bankSelectionSlots -->


        <div id="bankSelectionButton">
            <a href="studio-final-checkout.html" class="buttonBankSelection"><span class="left"><span class="right">Lock-in Winners</span></span></a>
            <a href="bankSelection.jsp#" id="clearSlots" class="buttonBankSelection"><span class="left"><span
                    class="right">Clear Slots</span></span></a>
        </div>

    </div>
    <!-- End #bankSelectionContent -->

</div>
<!-- End #bankSelection -->
