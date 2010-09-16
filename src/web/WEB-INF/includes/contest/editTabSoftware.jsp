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
      </p>
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
         	<div id="launchContestOut" class="hide">                                                        	
						 <div class="tabOut">                                    
                 <!-- tab contest -->
                 <div class="tabContest tabContest1">                                    	
                   <!-- selectDesing -->
                   <div class="selectDesing selectDesing1">                      	
                          <div class="selectX">
                          		<span class="name"><strong>Contest Type</strong></span>
                                <div class="selectOut">                                	
                                    <select id="contestTypes">
                                         <optgroup label="Studio">
                                         	<s:iterator value="studioContestTypes">
                                         	<option value='STUDIO<s:property value="contestTypeId" />'><s:property value="description" /></option>
                                          </s:iterator>
                                         </optgroup>														                            
                                         <optgroup label="Software">
                                         </optgroup>														                             
                                     </select>
                                  </div>                                                
                           </div> <!-- End of .selectX -->                                                                                        
                                            
                      </div>
                      <!-- end .selectDesing -->                                        
                  </div>
                  <!-- end .tabContest -->
                                    
                 </div></div> <!-- end #launchContestOut -->
                                                                                                
                 <span class="name2"><strong>Contest Name</strong></span>
                 <span class="value"><input type="text" class="bigin"  id="contestName" /></span>
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
		 			<h2>Schedule <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_round" /></a></h2>                                        
		 		</div>		 	
		 </div><!-- End .caption -->												
		<div class="detailsContent_det_round">
			 <table cellspacing="10" class="det_font_tab">
           <tr>
            	<td class="first_tab"><strong>Start Date/Time</strong></td>
                <td class="sec_tab">&nbsp;</td>
                <td><span id="rStartDate"></span></td>
             </tr>
        </table>        
     </div><!-- End .detailsContent -->												
</div><!-- End .details -->
<!-- End Round Display -->

<!-- Round Edit -->                                            
<div class="no_details contest_round_edit hide">											
		<div class="caption_det_round_edit">													
				<div class="captionInner">
					<h2>Schedule <a href="javascript:;"></h2>
				</div>													
		</div><!-- End .caption -->
										
		<div class="detailsContent_det_round_edit">										 
        <div id="launchContestOut">
            <div class="schedule schedule1" style="height:90px;">
                         
             <!-- Start -->
             <div class="row">
             	<span class="name_label"><strong>Start:</strong></span>
               <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
               <div class="startEtSelect">
               	<select id="startTime" name="startTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
               </div>
               <span>ET (GMT-04)</span>
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
						<h2>Prizes <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_prize" /></a></h2>
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
                      <td class="sec_tab_prize"><strong>Contest Fee:</strong> $<span id="rswContestFee"></span></td>
                   </tr>
                   <tr class="rightbor">
                     	<td class="first_tab"  align="left"><strong>2nd Place:</strong> $<span id="rswSecondPlace"></span></td>
                      <td class="sec_tab_prize"><strong>Digital Run:</strong> $<span id="rswDigitalRun"></span></td>
                      <td class="sec_tab_prize">&nbsp;</td>
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
                     <span class="name"><strong>Billing Account </strong></span>
                     <div class="prizeBillingSelect" style="float:left" >
                         <select id="billingProjects" name="billingProject">
                         	<option value="-1">Please select an existing account</option>
                           <s:iterator value="billingProjects">
                           <option value='<s:property value="projectId" />'><s:property value="name" /></option>
                           </s:iterator>
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
                         <span id="swSecondPlace" class="mid_info" style="line-height:30px;"></span>
                         
                     	</div>
                         <br />
                         <span class="head">
                         <span class="first_info">Review Cost:&nbsp;&nbsp;$ <span id="swReviewCost"></span></span>
                         <span class="mid_info">Reliability Bonus:&nbsp;&nbsp;$ <span id="swReliabilityBonus"></span></span>
                         <span class="mid_info">Digital Run:&nbsp;&nbsp;$  <span id="swDigitalRun"></span></span>
                         <span class="mid_info">Contest Fee:&nbsp;&nbsp;$  <span id="swContestFee"></span></span>
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
							<h2>Specification
              <a href="javascript:;"><img src="/images/edit.png" alt="edit" class="edit_spec" /></a>                                            
              </h2>                                            
						</div>					
				</div><!-- End .caption -->
												
		    <div class="detailsContent_det_spec">
							<p class="det_font">
               <span class="name"><strong>Detailed Requirements</strong></span>
               <br />
               <span class="gray_name"><strong>Describe what you want to accomplish.</strong>
               </span>               
               <br /><br />
               <span class="small_info_spec" id="rswDetailedRequirements">
               </span>
              </p>
                                                
              <div class="bottom_spec">
              </div>     
              
               <p class="det_font">
               <span class="name"><strong>Software Guidelines</strong></span>
               <br />
               <span class="gray_name"><strong>Submission Deliverables?, Environment Setup Instructions?, Final Submission Guidelines?</strong>
               </span>
               
               <br /><br />
               <span class="small_info_spec" id="rswGuidelines">
               </span>
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
                      </div>
                      
                      <div class="bottomTextarea">
                      	<p>Describe what you want to accomplish.</p>
                      	<span class="icon"></span>                                        
                      </div>                    
                </div>
                <!-- end .description -->
                
                <!-- Contest Description -->
            	  <div class="guidelines">                  
                      <h3><span class="icon">Software Guidelines</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>               
                      
                      <div class="textarea1">
                          <textarea id="swGuidelines" rows="" cols=""></textarea>
                      </div>
                      
                      <div class="bottomTextarea">
                      	<p>Submission Deliverables?, Environment Setup Instructions?, Final Submission Guidelines?</p>
                      	<span class="icon"></span>                                        
                      </div>                    
                </div>
                <!-- end .guidelines -->
                            
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
                   	<td class="first_tab"  align="left"><strong>{0}. {1}</strong></td>
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

<div id="resubmit" class="hide">
	  <a href="javascript:activateContestEdit();" class="button4">Activate</a>
</div>
										
<div class="panel"> 
	&nbsp;
</div><!-- End .panel -->
										
