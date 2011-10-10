<%--
  - Author: TCSASSEMBER
  - Version: 1.3
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: Edit Tab for studio contest detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Studio contest type is rendered by javascript now.
  - - Remove Project End date.
  - - File upload sections should be same as software contest. 
  -
  - Version 1.2 - TC Direct Replatforming Release 2 Change notes:
  - - Added "Go to my Spec Review" section to support starting specification review for studio contest.
  - Version 1.3 (Direct Replatforming Release 4) changes: Add support to make the files downloadable.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- Contest Type Display-->
<div class="no_details contest_type">                                                                                       
  <div class="caption_det_type">                                                    
        <div class="captionInner">
           <h2>Contest Type</h2>
           <a href="javascript:;" class="button11 edit_type"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
      </div>
                                                    
   </div><!-- End .caption -->
                                                
     <div class="detailsContent_det_type">
            <p class="det_font">
         <span class="name2"><strong>Contest Type</strong></span>
         <span class="value"><strong>: <span id="rContestTypeName"></span></strong></span>
         <br /> <br />
         <span class="name2"><strong>Contest Name</strong></span>
         <span class="value"><strong>: <span id="rContestName"></span></strong></span>
         <br /> <br />
         <span class="name2 billingdisplay"><strong>Billing Account</strong></span>
         <span class="value billingdisplay"><strong>: <span id="rBillingAccount"></span></strong></span>
         <br /> <br />
         <span class="name2"><strong>Project Name</strong></span>
         <span class="value"><strong>: <span id="rProjectName">${sessionData.currentProjectContext.name}</span></strong></span>
		 <br/><br/>
         <span class="name2"><strong>Copilot</strong></span>
         <span class="value"><strong>: <span id="rCopilots"></span></strong></span>
         <br /> <br />
         <span class="name2 adminFeeDisplay"><strong>Contest Fee</strong></span>
         <span class="value adminFeeDisplay"><strong>: $<span id="rAdminFee"></span></strong></span>
      </p>
    </div><!-- End .detailsContent -->                                              
</div><!-- End .details -->
<!-- End Contest Type Display-->

<!-- Contest Type Edit -->                                            
<div class="no_details contest_type_edit hide">                                         
            <div class="caption_det_type_edit">                                                 
                    <div class="captionInner">
                            <h2>Contest Type </h2>                                                                                                                   
          </div>                                                    
          </div><!-- End .caption -->                                               
          
      <div class="detailsContent_det_type_edit">
                    <div class="det_font" style="border:1px solid #BDBDBD; height:250px;padding-left:10px;">                                              
            <div id="launchContestOut">                                                         
                         <div class="tabOut">                                    
                 <!-- tab contest -->
                 <div class="tabContest tabContest1">                                       
                   <!-- selectDesing -->
                   <div class="selectDesing selectDesing1">                         
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
                                    
                 </div></div>
                                                                                                
                 <br />
                 <span class="name fixWidthName"><strong>Contest Name</strong></span>
                 <span class="value"><input type="text" class="bigin"  id="contestName" /></span>
                                  
                 <!-- Billing Account -->
                 <div id="billingAccountDivEdit">
                 <br />
                     <span class="name fixWidthName"><strong>Billing Account </strong></span>
                     <div class="billingSelect" style="float:left" >
                         <select id="billingProjects" name="billingProject">
                            <option value="-1">Please select an existing account</option>
                           <s:iterator value="billingProjects">
                           <option value='<s:property value="projectId" />'><s:property value="name" /></option>
                           </s:iterator>
                        </select>
                     </div>
                 </div>
                 
                 <br /><br />
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
                 
                 </div>
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
                    <h2>Rounds Type & Schedule</h2>
                    <a href="javascript:;" class="button11 edit_round"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
                </div>          
         </div><!-- End .caption -->                                                
        <div class="detailsContent_det_round">
             <table cellspacing="10" class="det_font_tab">
           <tr>
                <td class="first_tab"><strong>Start Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rStartDate"></span></td>
             </tr>
             <tr id="rMileStoneTR">
                <td class="first_tab"><strong>Milestone End Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rMilestoneDate"></span></td>
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
           <span class="name"><strong>Milestone Prizes</strong></span>
           <br /><br />
           <span class="small_info_spec">
              Pay <span id="rMPrizesAmount"></span> for each submission up to <span id="rMPrizesNumberOfSubmissions"></span>
           </span>
          </p>
                                            
          <div class="bottom_spec">
          </div>     

                  <p class="det_font">
           <span class="name"><strong>Round One Information</strong></span>
           <br />
           <span class="gray_name"><strong>Describe round one information.</strong>
           </span>               
           <br /><br />
           <span class="small_info_spec" id="rRound1Info">
           </span>
          </p>
                                            
          <div class="bottom_spec">
          </div>     
          
           <p class="det_font">
           <span class="name"><strong>Round Two Information</strong></span>
           <br />
           <span class="gray_name"><strong>Describe round two information.</strong>
           </span>
           
           <br /><br />
           <span class="small_info_spec" id="rRound2Info">
           </span>
           </p>
           
            <div class="bottom_spec">
           </div>
        </div>  
        
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
            <div class="schedule schedule1">
            
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
             
             <!-- Milestone -->
             <div id="mileStoneEditDiv" class="row">
                <span class="name_label"><strong>Round 1 Duration:</strong></span>
                 <div class="milestoneEtSelect">
                   <select id="milestoneDateDay" name="milestoneDateDay"><c:forEach var="i" begin="0" end="10"><option value="${i}">${i}</option></c:forEach></select>
                 </div>
                 <div class="selectSpan"><span>days</span></div>
                 <div class="milestoneEtSelect">
                   <select id="milestoneDateHour" name="milestoneDateHour"><c:forEach var="i" begin="0" end="23"><option value="${i}">${i}</option></c:forEach></select>
                  </div>
                  <div class="selectSpan"><span>hours</span></div>
                  <div class="clear"></div>
             </div>

             <!-- Submission -->
             <div class="row" id="endEditDiv">
                <span class="name_label"><strong>Round 2 Duration:</strong></span>
                <div class="endEtSelect">
                  <select id="endDateDay" name="endDateDay"><c:forEach var="i" begin="0" end="10"><option value="${i}">${i}</option></c:forEach></select>
                </div>
                <div class="selectSpan"><span>days</span></div>
                <div class="endEtSelect">
                  <select id="endDateHour" name="endDateHour"><c:forEach var="i" begin="0" end="23"><option value="${i}">${i}</option></c:forEach></select>
                </div>
                <div class="selectSpan"><span>hours</span></div>
                <div class="clear"></div>
             </div>
             </div> <!-- end .schedule -->
             
             <!-- Milestone Prizes -->
               <div class="mPrizes" id="milestonePrizeDiv">               
                   <h3><span class="icon">Milestone Prizes:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>                   
                   <div class="mPrizesInner">
                    <label class="first">Pay</label>
                       <span class="dw">$</span>
                       <input type="text" id="milestonePrize" class="prizesInput" value="" />
                       <strong>for each submission up to</strong>
                       <div class="numSelect">
                        <select id="milestoneSubmissionNumber" >
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
              
            <div class="contestDetail" id="roundInfoDiv">               
                <!-- Round one information -->
                  <div class="description">                
                      <h3><span class="icon">Round One Information</span><div id="Round1HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>                    
                      <div class="textarea1">
                          <textarea id="round1Info" rows="" cols=""></textarea>
                      </div>
                      
                      <div class="bottomTextarea">
                        <p>Round one information.</p>
                        <span class="icon"></span>                                        
                      </div>                    
                </div>
                <!-- end .description -->
                
                <!-- Round two information -->
                  <div class="guidelines">                  
                      <h3><span class="icon">Round Two Information</span><div id="Round2HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>               
                      
                      <div class="textarea1">
                          <textarea id="round2Info" rows="" cols=""></textarea>
                      </div>
                      
                      <div class="bottomTextarea">
                        <p>Round two information.</p>
                        <span class="icon"></span>                                        
                      </div>                    
                </div>
                <!-- end .guidelines -->
            </div> <!-- end .contestDetail -->
              
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
                        <a href="javascript:;" class="button11 edit_prize"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
                    </div>              
            </div><!-- End .caption -->                                             
         <div class="detailsContent_det_prize">
                            <table cellspacing="10" class="det_font_tab">
                   <tr class="rightbor">
                        <td class="first_tab"  align="left"><strong>Main Prizes</strong></td>
                         <td class="sec_tab_prize"><strong>Additional Prizes (optional)</strong></td>
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
                                <div class="prizes">
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
                        <a href="javascript:;" class="addButton"><span class="hide">ADD</span></a>
                    </div>
                    
                    <div id="extraPrizes" class="prizesInner hide">
                        <label class="first">4th Place</label>
                        <span class="dw">$</span>
                        <input type="text" id="prize4" class="prizesInput" value="" />
                        <label class="second">5th Place</label>
                        <span class="dw">$</span>
                        <input type="text" id="prize5" class="prizesInput" value="" />
                        <label class="third">&nbsp;</label>
                        <span class="dw">&nbsp;</span>
                        <a href="javascript:;" class="removeButton"><span class="hide">REMOVE</span></a>
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
                            <h2>Specification</h2>
                            <a href="javascript:;" class="button11 edit_spec"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>                            
                        </div>                  
                </div><!-- End .caption -->
                                                
            <div class="detailsContent_det_spec">
                            <p class="det_font">
               <span class="name"><strong>Contest Introduction</strong></span>
               <br />
               <span class="gray_name"><strong>Describe your project and your project goals.</strong>
               </span>               
               <br /><br />
               <span class="small_info_spec" id="rContestIntroduction">
               </span>
              </p>
                                                
              <div class="bottom_spec">
              </div>     
              
               <p class="det_font">
               <span class="name"><strong>Contest Description</strong></span>
               <br />
               <span class="gray_name"><strong>Size, Colors, Fonts? Target Audience? Reference Designs?</strong>
               </span>
               
               <br /><br />
               <span class="small_info_spec" id="rContestDescription">
               </span>
               </p>
               
                <div class="bottom_spec">
               </div>
               
               <p class="det_font">
               <span class="name"><strong>Final Deliverables</strong></span>
               <br />
               <ul class="mylist" id="rFinalDeliveries">
                </ul>                                           
                              </p>  

                                <div class="bottom_spec"></div>

                                <p class="det_font">
                                <span class="name"><strong>Stock Arts</strong></span>
                                <br /><br />
                                <span class="small_info_spec" id="rContestStockArt"></span>
                                </p>
                                <c:if test="${admin}">
                <div class="bottom_spec"></div>

                                <p class="det_font">
                                <span class="name2"><strong>Submission Visibility</strong></span>
                                <br /><br />
                                <span class="small_info_spec" id="rViewableSubmFlag"></span>
                                </p>

                <div class="bottom_spec"></div>

                                <p class="det_font">
                                <span class="name2"><strong>Maximum Number of Submissions</strong></span>
                                <br /><br />
                                <span class="small_info_spec" id="rMaxSubmissions"></span>
                                </p>
                                </c:if>
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
                <!-- Contest introduction -->
                  <div class="description">                
                      <h3><span class="icon">Contest Introduction</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>                    
                      <div class="textarea1">
                          <textarea id="contestIntroduction" rows="" cols=""></textarea>
                      </div>
                      
                      <div class="bottomTextarea">
                        <p>Describe your project and your project goals.</p>                        
                      </div>                    
                </div>
                <!-- end .description -->
                
                <!-- Contest Description -->
                  <div class="guidelines">                  
                      <h3><span class="icon">Contest Description</span><div id="ContestDescriptionHelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>               
                      
                      <div class="textarea1">
                          <textarea id="contestDescription" rows="" cols=""></textarea>
                      </div>
                      
                      <div class="bottomTextarea">
                        <p>Size, Colors, Fonts? Target Audience? Reference Designs? </p>
                      </div>                    
                </div>
                <!-- end .guidelines -->
            </div> <!-- end .contestDetail -->
           <div class="deliverables">  
               <h3><span class="icon">Final Deliverables:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>        
               <div class="deliverablesInner">
                <div class="checkInput" id="deliverablesCheckboxs">
                 </div>
                 <a href="javascript:;" class="fileType">File Type</a>
                 <div class="clear"></div>
               </div>                             
           </div> <!-- end .deliverables -->
           <div class="stockArts">
               <h3><span class="icon">Stock Arts</span><a class="helpIcon" href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files" target="_blank"><span class="hide">Help</span></a></h3>
               <div class="deliverablesInnerSa">
                   <div id="stockArtsCheckboxs" class="checkInput">
                       <input type="checkbox" id="allowStockArt" value="true">
                       <label>Is stock photography allowed? Please be sure you understand the <a href="http://apps.topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files">stock art policy</a> before checking this box.</label>
                   </div>
                   <div class="clear"></div>
               </div>
           </div> <!-- end .stockArts -->
           <div class="submissionVisibility">
               <h3><span class="icon">Submissions Visibility</span><a class="helpIcon" href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Submissions+Visibility" target="_blank"><span class="hide">Help</span></a></h3>
               <div class="deliverablesInnerSa">
                   <div id="viewableSubmCheckbox" class="checkInput">
                       <input type="checkbox" id="viewableSubmFlag" value="true">
                       <label>Submissions are viewable after contest ends. NOTE: all submissions are hidden during the submission phase.</label>
                   </div>
                   <div class="clear"></div>
               </div>
           </div> <!-- end .submissionVisibility -->

           <div class="maxSubmissions">
               <h3><span class="icon">Maximum Number of Submissions</span><a class="helpIcon" href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Maximum+Number+of+Submissions" target="_blank"><span class="hide">Help</span></a></h3>
               <div class="deliverablesInnerSa">
                   <div id="maxSubmissionsInput">
                       <input type="text" id="maxSubmissions" class="text">
                       <label>Please enter the max number of submissions a competitor can upload to each round in the contest. The standard number is 5. Leave blank if you want unlimited submissions.</label>
                   </div>
                   <div class="clear"></div>
               </div>
           </div> <!-- end .maxSubmissions -->
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
                            <a href="javascript:;" class="button11 edit_files"><span class="btnR"><span class="btnC"><span id="editTypeButton" class="btnIcon">Edit</span></span></span></a>
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
                          File to Upload: <span id="swUploadButtonDiv"><input name="document" type="file" > </span> <br/>
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
      <a href="javascript:activateContestEdit();" class="button4">Activate</a>
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
                    <p>Need help writing a great contest description? Please visit the Contest Holder Guide where you will find contest samples and templates. http://topcoder.com/wiki/display/tcstudio/Studio+Guide+for+Contest+Holders</p>
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