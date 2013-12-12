<%--
  - Author: isv, Ghost_141, GreatKevin
  - Version: 1.8
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders edit softeware contest page.
  -
  - Version 1.0.1 (TC Direct Release Assembly 7) changes: added digital run input field.
  - Description: overview page for software contest.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Change some attribute of file upload elements to make sure that file upload function can work fine for
  - - both studio contest and software contest.
  -
  - Version 1.2 - TC Direct Replatforming Release 2 Change Note
  - - Add checkpoint prizes section to support checkpoint prizes for software contest.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) changes: new tinyMCE editor.
  - Version 1.4 (Release Assembly - Contest Edit and Upload Update) changes: added text on file size limit.
  -
  - Version 1.5 (Release Assembly - TC Direct Cockpit Release Five) changes:
  - - Add DR points check flag in software contest details page.
  - 
  - Version 1.6 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - - Fix multiple bugs.
  -
  - Version 1.7 (Module Assembly - TC Cockpit Launch Code contest)
  - - Add multiple prize support
  -
  - Version 1.8 (Module Assembly - TC Cockpit Launch F2F contest)
  - - Add platform control
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="contestDetail">

  <!-- Contest Description -->
	<div class="description">    
        <h3><span class="icon">Detailed Requirements</span><!--<a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a>--></h3>
        
        <div class="textarea">
            <textarea id="swDetailedRequirements" rows="15" cols="80"></textarea>
            <p class="mceFooterNote">Describe your competition requirements and goals. All TopCoder Community members will see this description. If you have information you want 
                to keep confidential, please include it as an attachment below or in the competition forum.</p> 
        </div>        
  </div>
    
  <!-- guidelines -->
	<div class="guidelines">    
        <h3><span class="icon">Software Guidelines</span><!--<a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a>--></h3>
        
        <div class="textarea">
            <textarea id="swGuidelines" rows="15" cols="80"></textarea>
            <p class="mceFooterNote">Describe any specific standards, environments, or other technical constraints here. All TopCoder Community members will see this description. If you have information you want to keep confidential, please include it as an attachment below or in the competition forum. </p>
        </div>        
  </div>
  <!-- end .guidelines -->

  <div class="prizes" id="swPlatformDiv">
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
        
  <div class="prizes" id="swTechnologyDiv">    
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
         
   <div class="prizes" id="swCatalogDiv">
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
<!-- Contest Detail -->
 
  <!-- upload -->
	<div id="swUploadSection">
    
        <h3>File Upload (20MB maximum): </h3>       
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
         <div class="doc{0}" class="document">
           <span class="fileInput">{1}</span>
           <a href="javascript:swRemoveDocument({0});" >remove</a> 
           <span class="doc{0}spec hide"> (Requirements Specification) </span>
         </div>
       </div>  
        
 </div>
 <!-- end #uploadSection -->
                                    
<!-- prizes -->
<div class="prizes" id="swPrizesSection">
    <h3>Contest Prizes:</h3>

    <div class="prizesInner_software">
      	<span class="head topcoderPrize"><p>Please Select the prize structure for your contest by choosing one of the options
            below:</p></span>

        <p>
            <br/>
          <span class="radio_font topcoderPrize">
              <input type="radio" name="prizeRadio" value="low"/>&nbsp;&nbsp;&nbsp;Low&nbsp;&nbsp;($<span
                      id="swPrize_low"></span>)
              <input type="radio" name="prizeRadio" value="medium" class="space_radio"/>&nbsp;&nbsp;&nbsp;Medium&nbsp;&nbsp;($<span
                      id="swPrize_medium"></span>)
              <input type="radio" name="prizeRadio" value="high"
                     class="space_radio"/>&nbsp;&nbsp;&nbsp;High&nbsp;&nbsp;($<span id="swPrize_high"></span>)
              <input type="radio" name="prizeRadio" value="custom" class="space_radio customRadio"/> <span
                      class="customRadio"> &nbsp;&nbsp;&nbsp; Custom </span>
          </span>

        </p>
        <br/>

        <div class="prizesInner">
            <label class="first">1st Place</label>
            <span class="dw">$</span>
            <input type="text" class="prizesInput" value="" id="swFirstPlace" readonly="true"/>
            <span class="codePrize topcoderPrize">
                <label class="second">2nd Place</label>
                <span class="dw">$</span>
            </span>
            <span class="topcoderPrize">
                <span id="swSecondPlace" class="prizeInfo"></span>
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

        <!-- Checkpoint Prizes -->
        <div class="mPrizes hide" id="swCheckpointPrizeDiv">

            <h3><span class="icon">Checkpoint Prizes:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a>
            </h3>

            <div class="mPrizesInner">
                <label class="first">Pay</label>
                <span class="dw">$</span>
                <input type="text" id="swCheckpointPrize" class="prizesInput" value=""/>
                <strong>for each submission up to</strong>

                <div class="numSelect">
                    <select id="swCheckpointSubmissionNumber">
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

        <br/>
          <span class="head">
			  <span class="first_info">Review Cost:&nbsp;&nbsp;$ <span id="swReviewCost"></span></span>
              <span class="topcoderPrize">
                  <span class="mid_info">Reliability Bonus:&nbsp;&nbsp;$ <span id="swReliabilityBonus"></span></span>
                   <span class="mid_info">&nbsp;Digital Run:&nbsp;</span><input type="checkbox" id="DRCheckbox"
                                                                                checked="checked" autocomplete="off"/>
                  <span class="mid_info">
                    Digital Points:&nbsp;&nbsp;$
                  </span>
                  <input type="text" class="prizesInput" value="" id="swDigitalRun" readonly="true" size="7"
                         style="padding: 0 5px; font-size: 11px;"/>
                  <br/>
                  <span class="first_info">Spec Review Fee:&nbsp;&nbsp;$  <span id="swSpecCost"></span></span>
              </span>
			  <span class="mid_info">Contest Fee:&nbsp;&nbsp;$  <span id="swContestFee"></span></span>
              <span class="mid_info">Copilot Fee:&nbsp;&nbsp;$  <span id="swCopilotFee"></span></span>
			  <span class="last_info"><strong>Contest Total:&nbsp;&nbsp;$ <span id="swTotal"></span></strong></span>
		  </span>
    </div>
</div>


  <!-- end .prizes -->
  <div class="clear"></div>
    <div class="buttonBox">
        <a href="javascript:continueOverview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
        <a href="javascript:saveAsDraftOverview();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
        <a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
        <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
        <a href="javascript:backOverview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>
    </div>


</div>
