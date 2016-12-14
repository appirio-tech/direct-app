<%--
  - Version: 1.6
  - Copyright (C) 2013 - 2016 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
  - - change on #billingProjects, will load by jquery
  -
  - Version 1.2 (Module Assembly - TC Cockpit Launch Code Contest)
  - - Add a specific CSS class for studio contest prize add and remove
  -
  - Version 1.3 (Release Assembly - TC Cockpit Private Challenge Update)
  -- Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
  -
  - Version 1.4 (Topcoder Direct - add total cost and estimate note to Marathon Match challenge)
  -- Add total cost with esitmation notes for marathon challenge
  -
  - Version 1.5 (Topcoder Direct - Allow a user to link a marathon match round id to direct mm challenge)
  - @author Veve @channegeId 30046969
  - - Add the marathon round id project info save for marathon challenge
  -
  - Description: Edit Tab for algorithm - marathon contest detail page
  - Since: Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page
  -
  - Version 1.6 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES)
  - Add Pre-Register user for private challenge
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- Contest Type Display-->
<div class="no_details contest_type">
    <div class="caption_det_type">
        <div class="captionInner">
            <h2>Challenge Type</h2>
            <c:if test="${viewData.hasContestWritePermission}">
                <a href="javascript:;" class="button11 edit_type editType"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
            </c:if>
        </div>

    </div><!-- End .caption -->

    <div class="detailsContent_det_type">
        <table cellspacing="10" class="det_font">
            <tr>
                <td class="first_tab_type"><strong>Challenge Type</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestTypeName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Challenge Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Review Style</strong></td>
                <td class="sec_tab_type"><strong>: System Test <span id="rReviewStyle"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>NDA is</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCCA"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Billing Account</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rBillingAccount"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Private Group</strong></td>
                <td class="sec_tab_type"><strong>: <span id="securityGroupName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr class="matchRoundId">
                <td class="first_tab_type"><strong>Match Round ID</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rMatchRoundId"></span></strong></td>
            </tr>
            <tr></tr>
            <tr class="cmcTask">
                <td class="first_tab_type"><strong>CMC Task ID</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCMCTaskID"></span></strong></td>
            </tr>
            <tr></tr>
            <tr class="cmcTask"></tr>
            <tr>
                <td class="first_tab_type"><strong>Project Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProjectName"><c:out value="${sessionData.currentProjectContext.name}" /></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Copilot</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCopilots"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Challenge Fee</strong></td>
                <td class="sec_tab_type"><strong>: $<span id="rAdminFee"></span></strong></td>
            </tr>
            <tr class="preRegisterUsersDiv hide">
                <td class="first_tab_type"><strong>Pre-Register Members</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rPreRegisterUsers"></span></strong></td>
            </tr>
            <tr>
                <td class="first_tab_type"><strong>Created By</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rChallengeCreator"></span></strong></td>
            </tr>
            <tr></tr>
        </table>
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Contest Type Display-->

<!-- Contest Type Edit -->
<div class="no_details contest_type_edit hide">
    <div class="caption_det_type_edit">
        <div class="captionInner">
            <h2>Challenge Type </h2>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_type_edit">
        <div class="det_font" style="height:350px;padding-left:10px;">
            <div id="launchContestOut" class="contestTypeEditSection">
                <div class="tabOut">
                    <!-- tab contest -->
                    <div class="tabContest tabContest1">
                        <!-- selectDesing -->
                        <div class="selectDesing selectDesing1" id="contestTypeSelectDiv">
                            <div class="selectX">
                                <span class="name fixWidthName"><strong>Challenge Type</strong></span>
                                <div class="selectOut">
                                    <select id="contestTypes">
                                    </select>
                                </div>
                            </div> <!-- End of .selectX -->

                        </div>
                        <!-- end .selectDesing -->
                    </div>
                    <!-- end .tabContest -->

                </div></div>

            <span class="hide contestTypeRO name fixWidthName"><strong>Challenge Type</strong></span>
                        <span class="value contestTypeRO hide">
                            <span id="contestTypeNameText"></span>
                            <br />
                        </span>

            <br />
            <span class="name fixWidthName"><strong>Challenge Name</strong></span>
                        <span class="value">
                            <input type="text" class="bigin"  id="contestName" />
                            <span id="contestNameText"></span>
                        </span>
            <br /><br />
            <span class="name fixWidthName"><strong>Review Style</strong></span>
                        <span class="value">
                            <input type="text" class="bigin"  id="reviewStyle" value="System Test" disabled="disabled"/>
                        </span>
            <br /><br />
            <span class="name"><input type="checkbox" id="chkboxCCA"  />&nbsp;&nbsp;&nbsp;<strong>NDA required</strong></span>
            <!-- Billing Account -->
            <div id="billingAccountDivEdit">
                <br />
                <span class="name fixWidthName"><strong>Billing Account </strong></span>
                <div class="billingSelect" style="float:left" >
                    <select id="billingProjects" name="billingProject">
                        <option value="-1">Please select an existing account</option>
                    </select>
                </div>
            </div>

            <br /><br />
            <span id="billingGroupCheckBox"><br />
                             <input type="checkbox" style=""><span>Run this challenge in a private community ? &nbsp;&nbsp;</span>  <select id="billingGroups" name="billingGroups"></select>
            </span>
            <span class="matchRoundId"><br />
                    <span class="name fixWidthName"><strong>Match Round ID</strong></span>
                    <span class="value">
                        <input type="text" class="smallin"  name="MatchRoundID" value=""/>
                    </span>
             </span><br />
             <span class="cmcTask"><br />
                    <span class="name fixWidthName"><strong>CMC Task ID</strong></span>
                    <span class="value">
                        <input type="text" class="bigin"  name="CMCTaskID" value=""/>
                    </span>
             </span><br />
            
            <div id="projectEditDiv">
                <br />
                <span class="name fixWidthName"><strong>Project Name</strong></span>
                <div class="projectsSelect" style="float:left">
                    <select id="projects" name="tcProject" class="bigin">
                        <option value="-1">Please select an existing project</option>
                        <s:iterator value="projects" var="proj">
                            <option value='<s:property value="projectId" />'  <c:if test="${proj.projectId == sessionData.currentSelectDirectProjectID}">selected</c:if> >
                            <s:property value="name" />
                            </option>
                        </s:iterator>
                    </select>
                </div>
            </div>
            <br /><br />
            <div id="copilotEditDiv">
                <br />
                <span class="name fixWidthName"><strong>Copilot</strong></span>
                <div class="copilotsSelect" style="float:left">
                    <select id="copilots" name="copilots" class="bigin">
                        <option value="0">Unassigned</option>
                        <s:iterator value="copilots" var="copilot">
                            <option value='<s:property value="userId"/>'>
                                <s:property value="handle" />
                            </option>
                        </s:iterator>
                    </select>
                </div>
            </div>

            <br /><br />

                <span class="name fixWidthName"><strong>Created By</strong></span>
                <span class ='small_info_spec' id="challegneCreatorLabel"></span>
     

        </div>
        <p class="save">
            <a href="javascript:;" class="cancel_text">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn" /></a>
        </p>
        <div class="clear"></div>             
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Contest Type Edit -->




<!-- Round Display -->
<div class="no_details contest_round">
    <div class="caption_det_round">
        <div class="captionInner">
            <h2>Rounds Type & Schedule</h2>
            <c:if test="${viewData.hasContestWritePermission}">
                <a href="javascript:;" class="button11 edit_round"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
            </c:if>
        </div>
    </div><!-- End .caption -->
    <div class="detailsContent_det_round">
        <table cellspacing="10" class="det_font_tab">
            <tr>
                <td class="first_tab"><strong>Start Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rStartDate"></span></td>
            </tr>
            <tr>
                <td class="first_tab"><strong>End Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rEndDate"></span></td>
            </tr>
        </table>

    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Round Display -->

<!-- Round Edit -->
<div class="no_details contest_round_edit hide">
    <div class="caption_det_round_edit">
        <div class="captionInner">
            <h2>Rounds Type & Schedule</h2>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_round_edit">
        <div id="launchContestOut">
            <div class="schedule schedule1" id="roundText">
                <table cellspacing="10" class="det_font_tab">
                    <tr>
                        <td class="first_tab"><strong>Start Date/Time</strong></td>
                        <td class="sec_tab">&nbsp;</td>
                        <td><span id="rStartDateRO"></span></td>
                    </tr>
                    <tr>
                        <td class="first_tab"><strong>End Date/Time</strong></td>
                        <td class="sec_tab">&nbsp;</td>
                        <td><span id="rEndDateRO"></span></td>
                    </tr>
                </table>
            </div>
            <div class="schedule schedule1" id="roundEdit">

                <!-- Start -->
                <div class="row">
                    <span class="name_label"><strong>Start:</strong></span>
                    <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
                    <div class="startEtSelect">
                        <select id="startTime" name="startTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
                    </div>
                    <span id="startTimezone"><fmt:formatDate value="<%= new java.util.Date()%>"
                                          pattern="z" timeZone="${defaultTimeZone}"/></span>
                </div>

                <div class="row">
                    <span class="name_label"><strong>End:</strong></span>
                    <input id="endDate" name="endDate" type="text"  class="text date-pick" readonly="true"/>
                    <div class="startEtSelect">
                        <select id="endTime" name="endTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
                    </div>
                    <span id="endTimezone"><fmt:formatDate value="<%= new java.util.Date()%>"
                                          pattern="z" timeZone="${defaultTimeZone}"/></span>
                </div>

            </div> <!-- end .schedule -->


        </div> <!-- end of #launchContestOut -->

        <p class="save">
            <a href="javascript:;" class="cancel_text_round">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn_round" /></a>
        </p>
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Round Edit -->





<!-- Prize Display -->
<div class="no_details contest_prize">
    <div class="caption_det_prize">
        <div class="captionInner">
            <h2>Prizes</h2>
            <c:if test="${viewData.hasContestWritePermission}">
                <a href="javascript:;" class="button11 edit_prize"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
            </c:if>
        </div>
    </div><!-- End .caption -->
    <div class="detailsContent_det_prize">
        <table cellspacing="10" class="det_font_tab">
            <tr class="rightbor">
                <td class="first_tab"  align="left"><strong>Main Prizes</strong></td>
                <td class="sec_tab_prize"><strong>Additional Prizes</strong></td>
            </tr>
            <tr class="rightbor">
                <td class="first_tab_prize"><strong>First Prize
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;
                </strong><span id="rPrize1"></span></td>
                <td class="sec_tab"><strong>3rd Prize&nbsp;&nbsp;&nbsp;&nbsp;:
                    &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize3"></span></td>
            </tr>
            <tr class="rightbor">
                <td class="first_tab_prize"><strong>Second Prize&nbsp;&nbsp;&nbsp;&nbsp;:
                    &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize2"></span></td>
                <td class="sec_tab"><strong>4th Prize&nbsp;&nbsp;&nbsp;&nbsp;:
                    &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize4"></span></td>
            </tr>
            <tr>
                <td class="first_tab"></td>
                <td class="sec_tab"><strong>5th Prize&nbsp;&nbsp;&nbsp;&nbsp;:
                    &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize5"></span></td>
            </tr>
        </table>


        <div class="totalCostContainer">
            <strong>Estimated Challenge Total:</strong> $<span id="rswTotal"></span>
            <p class="note">
                Note: Challenge prizes, costs, and fees in this section are estimates. <br>
                Actual costs are based on prizes paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
        </div>

        <div class="clear"></div>
    </div><!-- End .detailsContent -->

</div><!-- End .details -->
<!-- End Prize Display -->

<!-- Prize Edit -->
<div class="no_details contest_prize_edit hide">
    <div class="caption_det_prize_edit">
        <div class="captionInner">
            <h2>Prizes</h2>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_prize_edit">
        <div class="prizes alPrizes">
            <div class="prizesInner">
                <label class="first">1st Place</label>
                <span class="dw">$</span>
                <input type="text" id="prize1" class="prizesInput" value="" />
                <label class="second">2nd Place</label>
                <span class="dw">$</span>
                <input type="text" id="prize2"  class="prizesInput" value="" />
                <label class="third">3rd Place</label>
                <span class="dw">$</span>
                <input type="text" id="prize3" class="prizesInput" value="" />
                <a href="javascript:;" class="addButton alAdd"><span class="hide">ADD</span></a>
            </div>

            <div id="alExtraPrizes" class="prizesInner hide">
                <label class="first">4th Place</label>
                <span class="dw">$</span>
                <input type="text" id="prize4" class="prizesInput" value="" />
                <label class="second">5th Place</label>
                <span class="dw">$</span>
                <input type="text" id="prize5" class="prizesInput" value="" />
                <label class="third">&nbsp;</label>
                <span class="dw">&nbsp;</span>
                <a href="javascript:;" class="removeButton alRemove"><span class="hide">REMOVE</span></a>
            </div>
        </div> <!-- End .prizes -->

        <p class="save">
            <a href="javascript:;" class="cancel_text_prize">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn_prize" /></a>
        </p>
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Prize Edit -->




<!-- Spec Display -->
<div class="no_details contest_spec">
    <div class="caption_det_spec">
        <div class="captionInner">
            <h2>Match Specification</h2>
            <c:if test="${viewData.hasContestWritePermission}">
                <a href="javascript:;" class="button11 edit_spec"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
            </c:if>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_spec">
        <p class="det_font">
            <span class="name"><strong>Problem Statement</strong></span>
            <br/>
               <span class="gray_name" id="rProblemStatement">
               </span>
            <br />
        </p>

        <div class="bottom_spec">
        </div>

        <p class="det_font">
            <span class="name"><strong>Match Details</strong></span>
            <br />
               <span class="gray_name"><strong>Describe the details of the marathon match</strong>
               </span>

            <br /><br />
        <div class="previewMask">
                   <span class="small_info_spec" id="rMatchDetails">
                   </span>
        </div>
        </p>

        <div class="bottom_spec">
        </div>

        <p class="det_font">
            <span class="name"><strong>Match Rules</strong></span>
            <br />
               <span class="gray_name"><strong>Describe the rules of the marathon match</strong>
               </span>

            <br /><br />
        <div class="previewMask">
                   <span class="small_info_spec" id="rMatchRules">
                   </span>
        </div>
        </p>
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- END Spec Display -->

<!-- Spec Edit -->
<div class="no_details contest_spec_edit hide">
    <div class="caption_det_spec_edit">
        <div class="captionInner">
            <h2>Specfication</h2>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_spec_edit">
        <div id="launchContestOut">
            <div class="problemDiv">
                <div class="row">
                    <div class="problemSelect">
                        <h3 style="margin-top: 2px"><span class="icon">Select Problem Statement</span></h3>
                        <select id="problems">
                            <option value="-1">Please select a problem</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="contestDetail">
                <!-- Contest introduction -->
                <div class="description">
                    <h3><span class="icon">Match Details</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>
                    <div class="textarea1">
                        <textarea id="marathonMatchDetails" rows="" cols=""></textarea>
                        <p class="mceFooterNote">Describe the details of the Marathon Match</p>
                    </div>

                </div>
                <!-- end .description -->

                <!-- Contest Description -->
                <div class="guidelines">
                    <h3><span class="icon">Match Rules</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>

                    <div class="textarea1">
                        <textarea id="marathonMatchRules" rows="" cols=""></textarea>
                        <p class="mceFooterNote">Describe the rules of the Marathon Match</p>
                    </div>

                </div>
                <!-- end .guidelines -->
            </div> <!-- end .contestDetail -->

        </div> <!-- End .launchContestOut -->

        <p class="save">
            <a href="javascript:;" class="cancel_text_spec">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn_spec" /></a>
        </p>
    </div><!-- End .detailsContent_det_spec_edit -->
</div><!-- End .details -->
<!-- End Spec Edit -->




<!-- Document Display -->
<div class="no_details contest_files">
    <div class="caption_det_files">
        <div class="captionInner">
            <h2>Files</h2>
            <c:if test="${viewData.hasContestWritePermission}">
                <a href="javascript:;" class="button11 edit_files"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
            </c:if>
        </div>
    </div><!-- End .caption -->
    <div class="detailsContent_det_files">
        <p class="det_font">
        <table cellspacing="10" class="det_font_tab" id="documentTable">
        </table>
        </p>
    </div><!-- End .detailsContent -->
    <div id="documentTemplate" class='hide'>
        <table><tbody>
        <tr class="rightbor">
            <td class="first_tab"  align="left"><strong>{0}. <a href="${ctx}/launch/downloadDocument?documentId={3}" target="_blank">{1}</a></strong></td>
            <td class="sec_tab_files">{2}</td>
        </tr>
        </tbody></table>
    </div>
</div><!-- End .details -->
<!-- End Document Display -->

<!-- Document Upload -->
<div class="no_details contest_files_edit hide">
    <div class="caption_det_files_edit">
        <div class="captionInner">
            <h2>Files</h2>
        </div>
    </div><!-- End .caption -->

    <div class="detailsContent_det_files_edit">
        <!-- upload -->
        <div id="swUploadSection">
            <div class="uploadInner">
                <div id="swDocumentList">
                </div>
                <div>
                    File to Upload (20MB maximum): <span id="swUploadButtonDiv"><input name="document" type="file" > </span> <br/>
                    File Description:
                    <textarea id="swFileDescription" rows="5" cols="50"></textarea>
                    <input id="swFileUploadBtn"  type="button" value="Upload File -->" /> <br/>
                </div>
            </div> <!-- end .uploadInner -->

            <div id="swFileTemplte" class="hide">
                <div id="doc{0}" class="document">
                    <span class="fileInput">{1}</span>
                    <a href="javascript:swRemoveDocument({0});" >remove</a>
                </div>
            </div>
        </div>
        <!-- end #uploadSection -->

        <p class="save">
            <a href="javascript:;" class="cancel_text_files">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn_files" /></a>
        </p>
    </div><!-- End .detailsContent -->
</div><!-- End .details -->
<!-- End Document Upload -->

<div id="resubmit" class="hide">
    <a href="javascript:activateContestEdit();" class="activateButton"></a>
</div>

<div class="panel">
    &nbsp;
</div><!-- End .panel -->

<div class="bottom-review" style="display:none" id="swEdit_bottom_review">
    <a href="javascript:;" class="specrev-goto button"></a>
    <a href="javascript:;" class="specrev-goto">Go to my Spec Review</a>
    <p></p>
    <br /><br /><br />
</div>

<div class="tooltipArea">
    <div id="contestDescriptionToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Need help writing a great challenge description? Please visit the challenge Holder Guide where you will find challenge samples and templates. http://topcoder.com/wiki/display/tcstudio/Studio+Guide+for+Contest+Holders</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestRound1ToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Enter any specific requirements for round 1 submissions.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    <!-- End .tooltipContainer -->
</div>
<div class="tooltipArea">
    <div id="contestRound2ToolTip" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>Help</h2>
                            <a href="javascript:;" class="closeIco"></a>
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->

                <div class="tooltipContent">
                    <p>Enter any specific requirements for round 2 submissions.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    <!-- End .tooltipContainer -->
</div>
