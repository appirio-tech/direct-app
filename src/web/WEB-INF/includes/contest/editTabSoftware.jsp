<%--
  - Version: 2.10
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders edit softeware contest page.
  -
  - Version 1.0.1 (TC Direct Release Assembly 7) changes: added digital run input field.
  -
  - Version 1.0.2 (TC Direct Software Creation Update) changes: add display of copilots.
  -
  - Version 1.0.3 (TC Direct "Contest Links and Button" Update 24Hr Assembly) changes:
  - Change the  style for the Edit links for each contest edit sections.
  -
  - Version: 1.2
  - Description: Edit Tab for software contest detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Studio contest type is rendered by javascript now.
  - - Add support for multi round type. 
  -
  - Version 1.2 - TC Direct Replatforming Release 2 Change noets:
  - - Added checkpoint prizes section to support checkpoint prizes for software contest.
  -
  - Version 1.3 (Direct Replatforming Release 4) changes: Add checkpoint end time edit support for softare contest.
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp change notes)
  - Fix the contest requirements preview and integrate the new cockpit tinyMCE editor.
  -
  - Version 1.5 (Release Assembly - Contest Edit and Upload Update) changes: fixes for TCCC-3724, TCCC-3604
  -
  - Version 1.6 (BUGR-6609) Change notes:
  - - Added the submission end date in Schedule section.
  -
  - Version 1.7 (Release Assembly - TC Direct Cockpit Release Five)
  - - Add DR points flag check box
  - 
  - Version 1.8 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Fix bug COCKPITUI-62.
  -
  - Version 1.9 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
  - - Uses the billing accounts of the project only for the project prize edit.
  -
  - Version 1.10 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
  - - change on #billingProjects, will load by jquery

  - Version 2.0 (Module Assembly - TC Cockpit Contest Milestone Association 1)
  - - Add milestone display and edit
  -
  - Version 2.1 (Module Assembly - TC Cockpit Launch Code contest)
  - - Add multiple prize display for Code contest type
  -
  - Version 2.2 (Module Assembly - TC Cockpit Launch F2F contest)
  - - Add choose your platform control
  -
  - Version 2.3 (Release Assembly - TC Cockpit Private Challenge Update)
  -- Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
  -
  - Version 2.4 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
  - - Add checkpoint end date / submission end date / time picker for software contest. Remove old duration picker.
  -
  - Version 2.5 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
  - - Add reviewer dropdown in the challenge type section of edit software challenge
  -
  - Version 2.6 (F2F - TC Cockpit Update Auto Assign Reviewer Flow)
  - - Add review type radios to choose 'community' or 'internal' review
  -
  - Version 2.7 (Release Assembly - TC Direct Edit Challenge - prize section update v1.0)
  -- Move checkpoint prize information to prize section.
  -
  - Version 2.8 (Release Assembly - TC Direct Prize Section Update)
  - Add checkpoint prize for dev challenge prize section and update on the fly cost calculation
  -
  - Version 2.9 (Provide Way To Pre_register members When Launching Challenge)
  - - pre-register member support
  -
  - Version 2.10 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
  - - Add section for display and edit challenge group
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
                <td class="first_tab_type" style="line-height: 28px;"><strong>Challenge Type</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestTypeName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Challenge Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Review Scorecard</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rReviewScorecard"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Review Style</strong></td>
                <td class="sec_tab_type"><strong>:&nbsp;<span id="rReviewStyle"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>NDA is</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCCA"></span></strong></td>
            </tr>
            <tr></tr>
<%--
	    <tr>
                <td class="first_tab_type"><strong>Private Group</strong></td>
                <td class="sec_tab_type"><strong>: <span id="securityGroupName"></span></strong></td>
            </tr>
            <tr></tr>
--%>	    
            <tr>
                <td class="first_tab_type"><strong>Project Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProjectName"><c:out value="${sessionData.currentProjectContext.name}" /></span></strong></td>
            </tr>
            <tr></tr>
<%--		
            <tr class="cmcTask">
                <td class="first_tab_type"><strong>CMC Task ID</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCMCTaskID"></span></strong></td>
            </tr>
            <tr class="cmcTask"></tr>
--%>	    
            <tr>
                <td class="first_tab_type"><strong>Milestone</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProjectMilestone"></span></strong></td>
            </tr>
            <tr></tr>
<%--
            <tr>
                <td class="first_tab_type"><strong>Product</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProduct"></span></strong></td>
            </tr>
            <tr></tr>
--%>
            <tr>
                <td class="first_tab_type"><strong>Copilot</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCopilots"></span></strong></td>
            </tr>
            <tr></tr>

            <tr class="privateProjectRow hide">
                <td class="first_tab_type" title="Run this as an assigned task. Registration will not be open to anyone else, but it will still be publically listed."><strong>Task</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rPrivateProject"></span></strong></td>
            </tr>
            <tr></tr>
            <tr class="preRegisterUsersDiv hide">
                <td class="first_tab_type" title="Enter 1 or more members in a comma separated list. Member terms will be validated upon saving."><strong>Assign Member(s):</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rPreRegisterUsers"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Created By</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rChallengeCreator"></span></strong></td>
            </tr>
        </table>
    </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Contest Type Display-->

<!-- Contest Type Edit -->                                            
<div class="no_details contest_type_edit hide">											
			<div class="caption_det_type_edit">													
					<div class="captionInner">
							<h2>Challenge Type</h2>                                                                                                                          
          </div>													
		  </div><!-- End .caption -->												
		  
      <div class="detailsContent_det_type_edit">
					<p class="det_font">                                                
         	<div id="launchContestOut" class="contestTypeEditSection hide">                                  
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
                                    
                 <br/></div> <!-- end #launchContestOut -->

          <span class="contestTypeRO name fixWidthName hide"><strong>Challenge Type</strong></span>
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
<%--
	  <br /><br  class="cmcTask" /><p class="cmcTask">
          <span class="name fixWidthName"><strong>CMC Task ID</strong></span>
                    <span class="value">
                        <input type="text" value="" name="CMCTaskID" class="bigin">
                    </span>

          </p>
--%>	  
      	  <br/><br/>
            
          <span class="name fixWidthName"><strong>Review Style</strong></span>
          <span class="value">
              <input type="text" class="bigin"  id="reviewStyle" value="TopCoder Community Review Board" disabled="disabled"/>
          </span>
          <!-- Review Scorecard -->
          <div id="reviewScorecardDivEdit" class="editDropDown">
             <span class="name fixWidthName"><strong>Review Scorecard</strong></span>
             <div class="reviewScorecardSelect" style="float:left">
                 <select id="reviewScorecardSelects" name="reviewScorecardSelects" class="bigin">
                     <option value="0">Please select a review scorecard to associate</option>
                     <s:iterator value="reviewScorecards" var="reviewScorecard">
                          <option value='<s:property value="id"/>'>
                              <s:property value="name"/>
                          </option>
                      </s:iterator>
                  </select>
              </div>
              <div class="clearFix"></div>
          </div>
	      
	  <br />
	  <span class="name3"><input type="checkbox" id="chkboxCCA"  />&nbsp;&nbsp;&nbsp;<strong>NDA required</strong></span>

          <div id="projectEditDiv" class="editDropDown">
              <span class="name fixWidthName"><strong>Project Name</strong></span>

              <div class="projectsSelect" style="float:left">
                  <select id="projects" name="tcProject" class="bigin">
                      <option value="-1">Please select an existing project</option>
                      <s:iterator value="projects" var="proj">
                          <option value='<s:property value="projectId" />'
                                  <c:if test="${proj.projectId == sessionData.currentSelectDirectProjectID}">selected</c:if> >
                              <s:property value="name"/>
                          </option>
                      </s:iterator>

                  </select>
              </div>
              <div class="clearFix"></div>
          </div>

          <div id="milestoneEditDiv" class="editDropDown">
              <span class="name fixWidthName"><strong>Milestone</strong></span>

              <div class="milestoneSelect" style="float:left">
                  <select id="milestones" name="milestones" class="bigin">
                      <option value="0">Please select a milestone to associate</option>
                      <s:iterator value="milestones" var="milestone">
                          <option value='<s:property value="id"/>'>
                              <s:property value="name"/>
                          </option>
                      </s:iterator>
                  </select>
              </div>
              <div class="clearFix"></div>
          </div>
          <br/><br/>
 <%--
          <span class="name fixWidthName"><strong>Product</strong></span>
          <span class="value">
              <input type="text" class="bigin"  id="productName" />
              <span id="productNameText"></span>
          </span>
          <br/><br/>
---%>
          <div id="copilotEditDiv" class="editDropDown">
              <span class="name fixWidthName"><strong>Copilot</strong></span>

              <div class="copilotsSelect" style="float:left">
                  <select id="copilots" name="copilots" class="bigin">
                      <option value="0">Unassigned</option>
                      <s:iterator value="copilots" var="copilot">
                          <option value='<s:property value="userId"/>'>
                              <s:property value="handle"/>
                          </option>
                      </s:iterator>
                  </select>
              </div>
              <div class="clearFix"></div>
          </div>

          <div id="reviewTypeEditDiv" class="editDropDown">
              <span class="name fixWidthName"><strong>Review Type</strong></span>

              <input type="radio" name="reviewType" value="community" title="The Community applies the review position and do the review"/><span class="textValue">Community</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="radio" name="reviewType" value="internal" title="The project internal resource reviews the challenge, please choose reviewer below"/><span class="textValue">Internal</span>

              <div class="clearFix"></div>
          </div>

          <div id="reviewerEditDiv" class="editDropDown">
              <span class="name fixWidthName"><strong>Reviewer</strong></span>

              <div class="reviewerSelect" style="float:left">
                  <select id="reviewer" name="reviewer" class="bigin">

                  </select>
              </div>
              <div class="clearFix"></div>
          </div>
            <br />
          <div id="privateProjectEditDiv" class="hide">
              <span class="name fixWidthName" title="Run this as an assigned task. Registration will not be open to anyone else, but it will still be publically listed."><strong>Task</strong></span>
                <input type="checkbox"  name="privateProject" id="privateProject"/>
          </div>
          <br />
          <div id="preRegisterUsersEditDiv" class="hide">
              <span class="name fixWidthName" title="Enter 1 or more members in a comma separated list. Member terms will be validated upon saving."><strong>Assign Member(s):</strong></span>
              <span class="value"><input type="text"  name="preRegisterUsers" class="bigin" id="preRegisterUsers"/></span>
          </div>
          <br/><br />
          <div>

            <span class="name fixWidthName"><strong>Created By</strong></span>
               
            <span class ='small_info_spec' id="challegneCreatorLabel"></span>
                  
          </div>



                 </p>
                 <p class="save">                 	
                     <a href="javascript:;" class="cancel_text">cancel</a>
                     <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn" /></a>
                 </p>														
				</div><!-- End .detailsContent -->																
</div><!-- End .details -->
<!-- End Contest Type Edit --> 




<!-- Round Display -->
<div class="no_details contest_round">											
		 <div class="caption_det_round">		 	
		 		<div class="captionInner">
		 			<h2>Schedule </h2>
                    <c:if test="${viewData.hasContestWritePermission}">
					<a href="javascript:;" class="button11 edit_type edit_round"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
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
             <tr id="rCheckpointTR">
                <td class="first_tab"><strong>Checkpoint End Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rCheckpointDate"></span></td>
             </tr>
             <tr>
                <td class="first_tab"><strong>Submission End Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rSubEndDate"></span></td>
             </tr>
             <tr>
                <td class="first_tab"><strong>End Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rEndDate"></span></td>
             </tr>
        </table>        
        
         <div id="rMultiRoundInfoDiv">
         </div>
          
     </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Round Display -->

<!-- Round Edit -->                                            
<div class="no_details contest_round_edit hide" style="padding-bottom:20px;">											
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
                    <tr id="rCheckpointTR">
                        <td class="first_tab"><strong>Checkpoint End Date/Time</strong></td>
                        <td class="sec_tab">&nbsp;</td>
                        <td><span id="rCheckpointDateRO"></span></td>
                    </tr>
                    <tr>
                        <td class="first_tab"><strong>Submission End Date/Time</strong></td>
                        <td class="sec_tab">&nbsp;</td>
                        <td><span id="rSubEndDateRO"></span></td>
                    </tr>
                    <tr>
                        <td class="first_tab"><strong>End Date/Time</strong></td>
                        <td class="sec_tab">&nbsp;</td>
                        <td><span id="rEndDateRO"></span></td>
                    </tr>
                </table>
            </div>
            <div class="schedule schedule1" id="roundEdit">
            <!-- Round Type -->
             <div class="row" id="type">
                <span class="name_label"><strong>Round Type:</strong></span>
              <div class="roundelect">
                  <select id="roundTypes">
                    <option value="single">Challenge will be run in single-rounds</option>
                    <option value="multi">Challenge will be run in multi-rounds</option>                                              
                  </select>
              </div>
             </div>
             
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

                <div id="checkPointEndDateEditDiv" class="row">
                    <span class="name_label"><strong>Checkpoint End:</strong></span>
                    <input id="checkPointEndDate" name="endDate" type="text"  class="text date-pick" readonly="true"/>
                    <div class="endEtSelect">
                        <select id="checkPointEndTime" name="endTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
                    </div>
                    <span id="checkPointEndTimezone"><fmt:formatDate value="<%= new java.util.Date()%>"
                                          pattern="z" timeZone="${defaultTimeZone}"/></span>
                </div>

                <div id="endDateEditDiv" class="row software">
                    <span class="name_label"><strong>Submission End:</strong></span>
                    <input id="endDate" name="endDate" type="text"  class="text date-pick" readonly="true"/>
                    <div class="endEtSelect">
                        <select id="endTime" name="endTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
                    </div>
                    <span id="endTimezone"><fmt:formatDate value="<%= new java.util.Date()%>"
                                          pattern="z" timeZone="${defaultTimeZone}"/></span>
                </div>

             </div><!-- end .schedule -->
             
             </div> <!-- end .schedule -->                            
        </div> <!-- end of #launchContestOut -->
                                     
        <p class="save">        	
            <a href="javascript:;" class="cancel_text_round">cancel</a>
            <a href="javascript:;"><img src="/images/save_change.png" alt="save" class="save_btn_round" /></a>
        </p>												
    </div><!-- End .detailsContent -->												
<!-- End Round Edit -->





<!-- Prize Display -->
<div class="no_details contest_prize">											
      <div class="caption_det_prize">				
					<div class="captionInner">
						<h2>Prizes </h2>
                        <c:if test="${viewData.hasContestWritePermission}">
						<a href="javascript:;" class="button11 edit_type edit_prize"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
                        </c:if>
					</div>				
			</div><!-- End .caption -->												
	     <div class="detailsContent_det_prize">
			        <p class="det_font">
                 <span class="name billingdisplay"><strong>Billing Account</strong></span>
                 <span class="value billingdisplay"><strong>: <span id="rBillingAccount"></span></strong></span>
              </p>	     	
							<table cellspacing="10" class="det_font_tab">
                   <tr class="rightbor">
                      <td class="first_tab"  align="left"><strong>1st Place:</strong> $<span id="rswFirstPlace"></span></td>
                      <td class="sec_tab_prize extraPrize hide"><strong>2nd Place:</strong> $<span></span></td>
                      <td class="sec_tab_prize extraPrize hide"><strong>3rd Place</strong> $<span></span></td>
                      <td class="sec_tab_prize extraPrize hide"><strong>4th Place</strong> $<span></span></td>
                      <td class="sec_tab_prize extraPrize hide"><strong>5th Place</strong> $<span></span></td>
                      <td class="sec_tab_prize"><strong>Review Cost:</strong> $<span id="rswReviewCost"></span></td>
                      <td class="sec_tab_prize topcoderPrize"><strong>Reliability Bonus:</strong> $<span id="rswReliabilityBonus"></span></td>
                      <td class="sec_tab_prize topcoderPrize"><strong>Spec Review Fee:</strong> $<span id="rswSpecCost"></span></td>
                   </tr>
                   <tr class="rightbor">
                       <td class="first_tab topcoderPrize"  align="left"><strong>2nd Place:</strong> $<span id="rswSecondPlace"></span></td>
                       <td class="sec_tab_prize topcoderPrize drHide"><strong>Digital Run:</strong> $<span id="rswDigitalRun"></span></td>
                       <td class="sec_tab_prize"><strong>Copilot Fee:</strong> $<span id="rswCopilotFee"></span></td>
                       
                   </tr>
                   <tr class="rightbor">
                       <td class="first_tab"  align="left"><strong>Challenge Fee:</strong> $<span id="rswContestFee"></span></td>
					   <td class="sec_tab_prize"></td>
                   </tr>
              </table>

         <div id="rCheckPointPrizeDiv">
           <p class="det_font">
             <span class="name subTitle"><strong>Checkpoint prizes</strong></span>
             <br />
             <span class="small_info_spec">
           	  Pay <span id="rMPrizesAmount"></span> for each submission up to <span id="rMPrizesNumberOfSubmissions"></span>
             </span>
           </p>
         </div>

         <div class="totalCostContainer">
             <strong>Estimated Challenge Total:</strong> $<span id="rswTotal"></span>
             <p class="note">
                  Note: Challenge prizes, costs, and fees in this section are estimates. <br> 
                  Actual costs are based on prizes paid, review fees based on number of submissions, reliability bonuses incentives paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
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
                 <div id="billingAccountDivEdit">
                     <span class="name fixWidthName"><strong>Billing Account </strong></span>
                     <div class="prizeBillingSelect" style="float:left" >
                         <select id="billingProjects" name="billingProject">
                         	<option value="-1">Please select an existing account</option>
                        </select>
                     </div>
                   <br/><br/>  
                  </div>
                <span id="billingGroupCheckBox">
                             <input type="checkbox" style=""><span>Run this challenge in a private community ? &nbsp;&nbsp;</span>  <select id="billingGroups" name="billingGroups"></select><br/><br/>
                 </span>
               <div class="prizes">  
                     <div class="prizesInner_software">
                     	<span class="head topcoderPrize"><p>Please Select the prize structure for your challenge by choosing one of the options
                         below:</p></span> 
                         <p class="topcoderPrize">
                         <br />
                         <span class="radio_font">
                     	 <input type="radio" name="prizeRadio" value="low" />&nbsp;&nbsp;&nbsp;Low&nbsp;&nbsp;($<span id="swPrize_low"></span>)
                         <input type="radio" name="prizeRadio" value="medium" class="space_radio" />&nbsp;&nbsp;&nbsp;Medium&nbsp;&nbsp;($<span id="swPrize_medium"></span>)
                         <input type="radio" name="prizeRadio" value="high" class="space_radio" />&nbsp;&nbsp;&nbsp;High&nbsp;&nbsp;($<span id="swPrize_high"></span>)
                         <input type="radio" name="prizeRadio" value="custom" class="space_radio customRadio" /> <span class="customRadio"> &nbsp;&nbsp;&nbsp; Custom </span>
                         </span>
                         
                         </p>
                         <br />
                         <div class="prizesInner">
                             <label class="first">1st Place</label>
                             <span class="dw">$</span>
                             <input type="text" class="prizesInput" value="" id="swFirstPlace" readonly="true" />
                             <span class="codePrize topcoderPrize">
                               <label class="second">2nd Place</label>
                               <span class="dw">$</span>
                             </span>
                             <span class="topcoderPrize">
                               <span id="swSecondPlace" class="prizeInfo" style="line-height: 33px;font-size: 12px;font-weight: bold"></span>
                             </span>
                             <input type="text" id="prize2"  class="prizesInput" value="" />
                             <a href="javascript:;" class="addButton swAdd"><span class="hide">ADD</span></a>
                     	 </div>
                         <div id="swExtraPrizes" class="prizesInner hide">
                             <label class="third" style="margin-left: 12px">3rd Place</label>
                             <span class="dw">$</span>
                             <input type="text" id="prize3" class="prizesInput" value="" />
                             <label class="first">4th Place</label>
                             <span class="dw">$</span>
                             <input type="text" id="prize4" class="prizesInput" value="" />
                             <label class="first">5th Place</label>
                             <span class="dw">$</span>
                             <input type="text" id="prize5" class="prizesInput" value="" />
                             <label class="third">&nbsp;</label>
                             <span class="dw">&nbsp;</span>
                             <a href="javascript:;" class="removeButton swRemove"><span class="hide">REMOVE</span></a>
                         </div>
                         <br />
                         <span class="head">
                             <span class="first_info">Review Cost:&nbsp;&nbsp;$ <span id="swReviewCost"></span></span>
                             <span class="">
                                 <span class="mid_info topcoderPrize">Reliability Bonus:&nbsp;&nbsp;$ <span
                                         id="swReliabilityBonus"></span></span>
                                 <span class="mid_info topcoderPrize drHide">&nbsp;Digital Run:&nbsp;</span><input type="checkbox" id="DRCheckbox" class="topcoderPrize drHide"/>
                                 <span class="mid_info topcoderPrize drHide">Digital Points:&nbsp;&nbsp;$</span>
                                 <input type="text" class="prizesInput topcoderPrize drHide" value="" id="swDigitalRun" readonly="true" size="7"/>
                             </span>

                         <br />
                         <span class="first_info">Challenge Fee:&nbsp;&nbsp;$  <span id="swContestFee"></span><span id="swContestFeePercentage"></span></span>
                         <span class="mid_info topcoderPrize">Spec Review Fee:&nbsp;&nbsp;$  <span id="swSpecCost"></span></span>
                         <span class="mid_info">Copilot Fee:&nbsp;&nbsp;$  <span id="swCopilotFee"></span></span>
                         <br />
                         <span class="last_info"></span>
                         </span>                                                                
                     </div>
                 </div>
                 <!-- end .prizes -->
                   
             <!-- Checkpoint prizes -->
	           <div class="mPrizes" id="checkpointPrizeDiv">               
                   <h3><span class="icon subTitle">Checkpoint prizes:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>
                   <div class="mPrizesInner">
                   	<label class="first">Pay</label>
                       <span class="dw">$</span>
                       <input type="text" id="swCheckpointPrize" class="prizesInput" value="" />
                       <strong>for each submission up to</strong>
                       <div class="numSelect">
                       	<select id="swCheckpointSubmissionNumber" >
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                       </div>
                   </div>                   
              </div>


              <div class="totalCostContainer">
                  <strong>Estimated Challenge Total:&nbsp;&nbsp;$  <span id="swTotal"></span></strong>
                  <p class="note">
                  Note: Challenge prizes, costs, and fees in this section are estimates.</n> Actual costs are based on prizes paid, review fees based on number of submissions, reliability bonuses incentives paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
              </div>

                    <div class="clear"></div>

              <!-- end .mPrizes -->  

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
							<h2>Specification </h2>
                            <c:if test="${viewData.hasContestWritePermission}">
							<a href="javascript:;" class="button11 edit_type edit_spec"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
                            </c:if>

						</div>					
				</div><!-- End .caption -->
												
		    <div class="detailsContent_det_spec">
                <p class="det_font">
                <span class="name"><strong>Detailed Requirements</strong></span>
                <br />
                <span class="gray_name">
                    <strong>
                    <s:if test="result.projectHeader.projectCategory.id == 29">
                        A description that you want everyone to see.
                    </s:if>
                    <s:else>
                        Describe what you want to accomplish.
                    </s:else>
                    </strong>
                </span>               
                <br /><br />

                <div class="previewMask">
                <span class="small_info_spec" id="rswDetailedRequirements">
                </span>
                </div>
                </p>
                                                
                <div class="bottom_spec">
                </div>     

                <s:if test="result.projectHeader.projectCategory.id == 29">
                    <p class="det_font">
                    <span class="name"><strong>Private Description</strong></span>
                    <br />
                    <span class="gray_name"><strong>A description that is only viewable to copilots that register for this posting.</strong>
                    </span>               
                    <br /><br />
                    <span class="small_info_spec" id="rswPrivateDescription">
                    </span>
                    </p>
                                                    
                    <div class="bottom_spec">
                    </div>     
                </s:if>
                
                <s:if test="result.projectHeader.projectCategory.id != 29">
                   <p class="det_font">
                   <span class="name"><strong>Submission Guidelines</strong></span>
                   <br />
                   <span class="gray_name"><strong>Submission Deliverables?, Environment Setup Instructions?, Final Submission Guidelines?</strong>
                   </span>
                   
                   <br /><br />

                    <div class="previewMask">
                   <span class="small_info_spec" id="rswGuidelines">

                   </span>
                    </div>
                   </p>
                   
                   <div class="bottom_spec">
                   </div>

                    <div class="platform">
                        <p class="det_font">
                            <span class="name"><strong>Current Project Platforms</strong></span>

                            <br /><br />
                       <span class="small_info_spec" id="rswPlatforms">
                       </span>
                        </p>

                        <div class="bottom_spec">
                        </div>
                    </div> <!-- End of .platform -->
                   
                   <div class="technology">                         
                       <p class="det_font">
                       <span class="name"><strong>Current Project Technologies</strong></span>
                       
                       <br /><br />
                       <span class="small_info_spec" id="rswTechnologies">
                       </span>
                       </p>
                       
                        <div class="bottom_spec">
                       </div>
                    </div> <!-- End of .technology -->
               </s:if>

                <div class="groupDisplay">
                    <p class="det_font">
                        <span class="name"><strong>Challenge Groups</strong></span>

                        <br />
                       <span class="small_info_spec" id="rswGroups">
                       </span>
                    </p>

                    <div class="bottom_spec">
                    </div>
                </div>
               
               <div class="component">    
                   <p class="det_font">
                   <span class="name"><strong>Catalog Name</strong></span>
                   
                   <br /><br />
                   <span class="small_info_spec" id="rswRootCatalog">
                   </span>
                   </p>
                   
                    <div class="bottom_spec">
                   </div>     
                   
                   <p class="det_font">
                   <span class="name"><strong>Categories</strong></span>
                   
                   <br /><br />
                   <span class="small_info_spec" id="rswCategories">
                   </span>
                   </p>
                   
                    <div class="bottom_spec">
                   </div>     
               </div> <!-- End .component -->
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
            <div class="contestDetail">        		
                <!-- Detailed Requirements -->
            	<div class="description">                
                      <h3><span class="icon">Detailed Requirements</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>                    
                      <div class="textarea1">
                          <textarea id="swDetailedRequirements" rows="" cols=""></textarea>
                          <p class="mceFooterNote">
                            <s:if test="result.projectHeader.projectCategory.id == 29">
                                A description that you want everyone to see.
                            </s:if>
                            <s:else>
                                Describe what you want to accomplish.
                            </s:else>
                        </p>
                      </div>
                      
                </div>
                <!-- end .description -->
                
                <!-- Private Description -->
            	<div class="description <s:if test='result.projectHeader.projectCategory.id != 29'>hide</s:if>">                
                      <h3><span class="icon">Private Description</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>                    
                      <div class="textarea1">
                          <textarea id="swPrivateDescription" rows="" cols=""></textarea>

                          <p class="mceFooterNote">
                              A description that is only viewable to copilots that register for this posting.
                          </p>
                      </div>

                </div>
                <!-- end .description -->                
                
                <!-- Contest Description -->
                <div class="guidelines <s:if test='result.projectHeader.projectCategory.id == 29'>hide</s:if>">                  
                      <h3><span class="icon">Submission Guidelines</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>               
                      
                      <div class="textarea1">
                          <textarea id="swGuidelines" rows="" cols=""></textarea>
                          <p class="mceFooterNote">Submission Deliverables?, Environment Setup Instructions?, Final Submission Guidelines?</p>
                      </div>

                </div>
                <!-- end .guidelines -->
                            
                <s:if test='result.projectHeader.projectCategory.id != 29'>

                    <div class="prizes platform" id="swPlatformDiv">
                        <h3>Choose Your Platform:</h3>

                        <div class="prizesInner_tech">
                            <span class="head_font">Master Platforms&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span class="head_font_space">Your Project Platforms</span>
                            <br />
                            <select multiple id="masterPlatformsSelect">
                                <s:iterator value="referenceDataBean.platforms">
                                    <option value='<s:property value="id" />'><s:property value="name" /></option>
                                </s:iterator>
                            </select>

                            <div id="button_platform">
                                <img src="/images/add_tech.png" alt="add" id="addPlatforms" />
                                <br /><br />
                                <img src="/images/remove_tech.png" alt="remove" id="removePlatforms" />
                            </div>

                            <select multiple id="masterPlatformsChoosenSelect">
                            </select>
                        </div>
                    </div>


                    <div class="prizes technology" id="swTechnologyDiv">    
                        <h3>Choose Your Technology:</h3>
                          
                        <div class="prizesInner_tech">
                            <span class="head_font">Master Technologies</span>
                            <span class="head_font_space">Your Project Technologies</span>
                            <br />	
                            <select multiple id="masterTechnologiesSelect">  
                                <s:iterator value="referenceDataBean.technologies">
                                    <option value='<s:property value="id" />'><s:property value="name" /></option>
                                </s:iterator>                  	
                            </select>  
                                
                            <div id="button_tech">
                                <img src="/images/add_tech.png" alt="add" id="addTechnologies" />
                                <br /><br />
                                <img src="/images/remove_tech.png" alt="remove" id="removeTechnologies" />
                            </div>    
                              
                            <select multiple id="masterTechnologiesChoosenSelect">
                            </select>   
                        </div>
                    </div>

                    <div id="swThurgoodDiv" style="display: none">
                        <h3>Use Thurgood to check challenge submissions?</h3><br/>
                        <input type="checkbox"><span class="head">&nbsp; Check this if you want to use Thurgood to perform automated quality and security review of challenge submissions</span><br/><br/></div>
                 
                    <div class="prizes component" id="swCatalogDiv">
                        <div class="catalogSelect">
                                <label>Catalog Name:</label>
                          <select id="catalogSelect">
                                <option value='-1'>&nbsp;</option>
                            <s:iterator value="referenceDataBean.catalogs"> 
                              <option value='<s:property value="id" />'><s:property value="name" /></option>
                            </s:iterator>
                          </select>	
                        </div> <!-- end of catalogSelect -->
	              		<div class="prizesInner_tech">
	              			<span class="head_font">Master Categories</span>
	              			<span class="head_font_space_categories">Your Project Categories</span>
	              			<br />
	              			<select multiple="multiple" id="select1_categories">					
	              			</select>
	              			<div id="button_tech">
	              				<img src="/images/add_tech.png" alt="add" id="addCategories" />
	              				<br />
	              				<br />
	              				<img src="/images/remove_tech.png" alt="remove" id="removeCategories" />
	              			</div>
	              			<select multiple="multiple" id="select2_categories">
	              			</select>
	              		</div> <!-- end of prizesInner_tech -->
	              	</div>
                </s:if>
                <h3>Challenge Group</h3>
                <div class="prizes group software" id="groupSoftwareDiv">
                    <div id="group_software"></div>
                </div>
                
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
							<h2>Files </h2>
                            <c:if test="${viewData.hasContestWritePermission}">
							<a href="javascript:;" class="button11 edit_type edit_files"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
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
                          <input id="swSpecDoc" type="checkbox" class="reqDocCheck" checked="true" /> <span class="reqDocCheck">Requirements Specification</span>
                       </div>
                   </div> <!-- end .uploadInner -->
             
                   <div id="swFileTemplte" class="hide">
                    <div id="doc{0}" class="document">
                      <span class="fileInput">{1}</span>
                      <a href="javascript:swRemoveDocument({0});" >remove</a> 
                      <span id="doc{0}spec" class="hide"> (Requirements Specification) </span>
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

<div id="resubmit" class="hide activateSection">
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
