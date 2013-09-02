<%--
  - Version: 2.0
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders edit softeware contest page.
  -
  - Version 1.0.1 (TC Direct Release Assembly 7) changes: added digital run input field.
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
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- Contest Type Display-->
<div class="no_details contest_type">																						
  <div class="caption_det_type">													
	  <div class="captionInner">
           <h2>Contest Type</h2>
           <c:if test="${viewData.hasContestWritePermission}">
		   <a href="javascript:;" class="button11 edit_type editType"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
           </c:if>
      </div>
													
   </div><!-- End .caption -->
   												
	 <div class="detailsContent_det_type">
        <table cellspacing="10" class="det_font">
            <tr>
                <td class="first_tab_type" style="line-height: 28px;"><strong>Contest Type</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestTypeName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Contest Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rContestName"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Review Style</strong></td>
                <td class="sec_tab_type"><strong>: TopCoder Community Review Board<span id="rReviewStyle"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>CCA is</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCCA"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Project Name</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProjectName">${sessionData.currentProjectContext.name}</span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Milestone</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rProjectMilestone"></span></strong></td>
            </tr>
            <tr></tr>
            <tr>
                <td class="first_tab_type"><strong>Copilot</strong></td>
                <td class="sec_tab_type"><strong>: <span id="rCopilots"></span></strong></td>
            </tr>
        </table>
    </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Contest Type Display-->

<!-- Contest Type Edit -->                                            
<div class="no_details contest_type_edit hide">											
			<div class="caption_det_type_edit">													
					<div class="captionInner">
							<h2>Contest Type</h2>                                                                                                                          
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
                          		<span class="name fixWidthName"><strong>Contest Type</strong></span>
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

          <span class="contestTypeRO name fixWidthName hide"><strong>Contest Type</strong></span>
          <span class="value contestTypeRO hide">
              <span id="contestTypeNameText"></span>
              <br />
          </span>
          <br />
          
          
                 <span class="name fixWidthName"><strong>Contest Name</strong></span>
          <span class="value">
              <input type="text" class="bigin"  id="contestName" />
              <span id="contestNameText"></span>
          </span>
          <br /><br />
            
          <span class="name fixWidthName"><strong>Review Style</strong></span>
          <span class="value">
              <input type="text" class="bigin"  id="reviewStyle" value="TopCoder Community Review Board" disabled="disabled"/>
          </span>
          <br /><br />
				 <span class="name3"><input type="checkbox" id="chkboxCCA"  /><strong>CCA required</strong></span>
				 <br /> <br />
				 <div id="projectEditDiv">
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
                 <br /> <br />
          <div id="milestoneEditDiv">
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
          </div>
          <br /> <br />
          <div id="copilotEditDiv">
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
		   <p class="det_font">
             <span class="name"><strong>Checkpoint prizes</strong></span>
             <br /><br />
             <span class="small_info_spec">
           	  Pay <span id="rMPrizesAmount"></span> for each submission up to <span id="rMPrizesNumberOfSubmissions"></span>
             </span>
           </p>
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
                    <option value="single">Contest will be run in single-rounds</option>
                    <option value="multi">Contest will be run in multi-rounds</option>                                              
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
               <span>EST (UTC-05)</span>
             </div>
             
             <!-- Checkpoint -->
             <div id="checkpointEditDiv" class="row">
                <span class="name_label"><strong>Checkpoint Duration:</strong></span>
                 <div class="checkpointEtSelect">
                   <select id="checkpointDateDay" name="checkpointDateDay"><c:forEach var="i" begin="0" end="10"><option value="${i}">${i}</option></c:forEach></select>
                 </div>
                 <div class="selectSpan"><span>days</span></div>
                 <div class="checkpointEtSelect">
                   <select id="checkpointDateHour" name="checkpointDateHour"><c:forEach var="i" begin="0" end="23"><option value="${i}">${i}</option></c:forEach></select>
                  </div>
                  <div class="selectSpan"><span>hours</span></div>
                  <div class="clear"></div>
             </div>
			 <!-- End -->
             </div><!-- end .schedule -->
             
             <!-- Checkpoint prizes -->
	           <div class="mPrizes" id="checkpointPrizeDiv">               
                   <h3><span class="icon">Checkpoint prizes:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>                   
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
              <!-- end .mPrizes -->  
              
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
                      <td class="sec_tab_prize"><strong>Review Cost:</strong> $<span id="rswReviewCost"></span></td>
                      <td class="sec_tab_prize"><strong>Reliability Bonus:</strong> $<span id="rswReliabilityBonus"></span></td>
                      <td class="sec_tab_prize"><strong>Spec Review Fee:</strong> $<span id="rswSpecCost"></span></td>
                   </tr>
                   <tr class="rightbor">
                       <td class="first_tab"  align="left"><strong>2nd Place:</strong> $<span id="rswSecondPlace"></span></td>
                       <td class="sec_tab_prize"><strong>Digital Run:</strong> $<span id="rswDigitalRun"></span></td>
                       <td class="sec_tab_prize"><strong>Copilot Fee:</strong> $<span id="rswCopilotFee"></span></td>
                       
                   </tr>
                   <tr class="rightbor">
                       <td class="first_tab"  align="left"><strong>Contest Fee:</strong> $<span id="rswContestFee"></span></td>
					   <td class="sec_tab_prize"><strong>Contest Total:</strong> $<span id="rswTotal"></span></td>
                   </tr>
              </table>
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
                
               <div class="prizes">  
                     <div class="prizesInner_software">
                     	<span class="head"><p>Please Select the prize structure for your contest by choosing one of the options 
                         below:</p></span> 
                         <p>
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
                         <label class="second">2nd Place</label>
                         <span class="dw">$</span>
                         <span id="swSecondPlace" class="prizeInfo" style="line-height: 33px;font-size: 12px;font-weight: bold"></span>
                     	</div>
                         <br />
                         <span class="head">
                         <span class="first_info">Review Cost:&nbsp;&nbsp;$ <span id="swReviewCost"></span></span>
                         <span class="mid_info">Reliability Bonus:&nbsp;&nbsp;$ <span id="swReliabilityBonus"></span></span>
                         <span class="mid_info">&nbsp;Digital Run:&nbsp;</span><input type="checkbox" id="DRCheckbox"/>
                         <span class="mid_info">Digital Points:&nbsp;&nbsp;$</span>
                         <span><input type="text" class="prizesInput" value="" id="swDigitalRun" readonly="true" size="7"/></span>
                         <br />
                         <span class="first_info">Contest Fee:&nbsp;&nbsp;$  <span id="swContestFee"></span></span>
                         <span class="mid_info">Spec Review Fee:&nbsp;&nbsp;$  <span id="swSpecCost"></span></span>
                         <span class="mid_info">Copilot Fee:&nbsp;&nbsp;$  <span id="swCopilotFee"></span></span>
                         <br />
                         <span class="last_info"><strong>Contest Total:&nbsp;&nbsp;$  <span id="swTotal"></span></strong></span>
                         </span>                                                                
                     </div>
                 </div>
                 <!-- end .prizes -->
                   
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
                   <span class="name"><strong>Software Guidelines</strong></span>
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
                      <h3><span class="icon">Software Guidelines</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>               
                      
                      <div class="textarea1">
                          <textarea id="swGuidelines" rows="" cols=""></textarea>
                          <p class="mceFooterNote">Submission Deliverables?, Environment Setup Instructions?, Final Submission Guidelines?</p>
                      </div>

                </div>
                <!-- end .guidelines -->
                            
                <s:if test='result.projectHeader.projectCategory.id != 29'>
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
										
