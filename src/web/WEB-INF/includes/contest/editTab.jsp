<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- Contest Type Display-->
<div class="no_details contest_type">																						
  <div class="caption_det_type">													
	    <div class="captionInner">
           <h2>Contest Type <a href="javascript:;"><img src="/images/edit.png" alt="edit" id="editTypeButton" class="edit_type" /></a></h2>                                                            
      </div>
													
   </div><!-- End .caption -->
   												
	 <div class="detailsContent_det_type">
			<p class="det_font">
         <span class="name"><strong>Contest Type</strong></span>
         <span class="value"><strong>: <span id="rContestTypeName"></span></strong></span>
         <br /> <br />
         <span class="name2"><strong>Contest Name</strong></span>
         <span class="value"><strong>: <span id="rContestName"></span></strong></span>
         <br /> <br />
         <span class="name2 billingdisplay"><strong>Billing Account</strong></span>
         <span class="value billingdisplay"><strong>: <span id="rBillingAccount"></span></strong></span>
         <br /> <br />
         <span class="name2 adminFeeDisplay"><strong>Admin Fee</strong></span>
         <span class="value adminFeeDisplay"><strong>: $<span id="rAdminFee"></span></strong></span>
      </p>
    </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Contest Type Display-->

<!-- Contest Type Edit -->                                            
<div class="no_details contest_type_edit hide">											
			<div class="caption_det_type_edit">													
					<div class="captionInner">
							<h2>Contest Type <a href="javascript:;"><img src="/images/edit_red.png" alt="edit" class="edit_type" /></a></h2>                                                                                                                        
          </div>													
		  </div><!-- End .caption -->												
		  
      <div class="detailsContent_det_type_edit">
					<div class="det_font" style="border:1px solid #BDBDBD; height:165px;">                                                
         	<div id="launchContestOut">                                                        	
						 <div class="tabOut">                                    
                 <!-- tab contest -->
                 <div class="tabContest tabContest1">                                    	
                   <!-- selectDesing -->
                   <div class="selectDesing selectDesing1">                      	
                          <div class="selectX">
                          		<span class="name"><strong>Contest Type</strong></span>
                                <div class="selectOut">                                	
                                    <select id="contestTypes">
                                         	<s:iterator value="studioContestTypes">
                                         	<option value='STUDIO<s:property value="contestTypeId" />'><s:property value="description" /></option>
                                          </s:iterator>
                                     </select>
                                  </div>                                                
                           </div> <!-- End of .selectX -->                                                                                        
                                            
                      </div>
                      <!-- end .selectDesing -->                                        
                  </div>
                  <!-- end .tabContest -->
                                    
                 </div></div>
                                                                                                
                 <br /><br />
                 <span class="name2"><strong>Contest Name</strong></span>
                 <span class="value"><input type="text" class="bigin"  id="contestName" /></span>
                                  
                 <!-- Billing Account -->
                 <div id="billingAccountDivEdit">
                 <br /><br />
                     <span class="name"><strong>Billing Account </strong></span>
                     <div class="billingSelect" style="float:left" >
                         <select id="billingProjects" name="billingProject">
                         	<option value="-1">Please select an existing account</option>
                           <s:iterator value="billingProjects">
                           <option value='<s:property value="projectId" />'><s:property value="name" /></option>
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
		 			<h2>Rounds Type & Schedule <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_round" /></a></h2>                                        
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
            	<td class="first_tab"><strong>Milestone Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rMilestoneDate"></span></td>
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
					<h2>Rounds Type & Schedule <a href="javascript:;"><img src="/images/edit_red.png" alt="edit" class="edit_round" /></a></h2>
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
               <span>ET (GMT-04)</span>
             </div>
             
             <!-- Milestone -->
             <div id="mileStoneEditDiv" class="row">
             	<span class="name_label"><strong>Milestone:</strong></span>
               <input  id="milestoneDate" name="milestoneDate"  type="text" class="text date-pick" readonly="true"/>
               <div class="milestoneEtSelect">
               	<select id="milestoneTime" name="milestoneTime"><jsp:include page="../common/timeOptions.jsp"/></select>
               </div>
               <span>ET (GMT-04)</span>
             </div>
             
             <!-- End -->
             <div class="row" id="end">
             	<span class="name_label"><strong>End:</strong></span>
              <input id="endDate" name="endDate" type="text" class="text date-pick" readonly="true"/>
              <div class="endEtSelect">
              	<select id="endTime" name="endTime"><jsp:include page="../common/timeOptions.jsp"/></select>
              </div>
              <span>ET (GMT-04)</span>
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
						<h2>Prizes <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_prize" /></a></h2>
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
                         &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize4"></span></strong></td>
                      </tr>                      
                      <tr>
                     	<td class="first_tab"></td>
                         <td class="sec_tab"><strong>5th Prize&nbsp;&nbsp;&nbsp;&nbsp;:
                         &nbsp;&nbsp;&nbsp;&nbsp;</strong><span id="rPrize5"></span></strong></td>                         
	                   </tr>                                                     
              </table>
				</div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Prize Display -->

<!-- Prize Edit -->                                            
<div class="no_details contest_prize_edit hide">											
			 <div class="caption_det_prize_edit">													
					<div class="captionInner">
						<h2>Prizes
            <a href="javascript:;"><img src="/images/edit_red.png" alt="edit" class="edit_prize" /></a></h2>
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
							<h2>Specification
              <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_spec" /></a>                                            
              </h2>                                            
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
					</div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- END Spec Display -->
               
<!-- Spec Edit -->                                            
<div class="no_details contest_spec_edit hide">											
		<div class="caption_det_spec_edit">			
				<div class="captionInner">
					<h2>Specfication <a href="javascript:;"><img src="/images/edit_red.png" alt="edit" class="edit_spec" /></a></h2>                                                                                                                        
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
							<h2>Files <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_files" /></a></h2>
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
				 			<h2>Files <a href="javascript:;"><img src="/images/edit_red.png" alt="edit" class="edit_files" /></a></h2>
				 		</div>				 	
				 </div><!-- End .caption -->
												
			   <div class="detailsContent_det_files_edit">
              <!-- upload -->
	            <div id="uploadSection">    
                    <div class="uploadInner">
                        <div id="documentList">
                        </div>
                        <div>
                           File to Upload: <span id="uploadButtonDiv"><input name="document" type="file" > </span> <br/>
                           File Description:
                           <textarea id="fileDescription" rows="5" cols="50"></textarea>               
                           <input id="fileUploadBtn"  type="button" value="Upload File -->"/>	
                        </div>
                    </div> <!-- end .uploadInner -->
              
                    <div id="fileTemplte" class="hide">
                     <div id="doc{0}" class="document">
                       <span class="fileInput">{1}</span>
                       <a href='${ctx}/launch/downloadDocument?documentId={0}' target="_blank" >download</a>            	  
                       <a href="javascript:removeDocument({0});" >remove</a>            
                     </div>
                   </div>          
              </div>

              <p class="save">                                                	
                  <a href="javascript:;" class="cancel_text_files">close</a>
              </p>														
			   </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Document Upload -->

<div id="resubmit" class="hide">
	  <a href="javascript:activateContestEdit();" class="button4">Activate</a>
</div>
										
<div class="panel"><!-- this area containt the print, export to excel, export to pdf links -->
	<a href="javascript:alert('to be implemented in next assembly');" class="exportPdf">Export to <strong>PDF</strong></a> <span>|</span>
	<a href="javascript:alert('to be implemented in next assembly');" class="exportExcel">Export to <strong>Excel</strong></a> <span>|</span>
	<a href="javascript:alert('to be implemented in next assembly');" class="print">Print</a>  
</div><!-- End .panel -->
										
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
