<%--
  - Author: isv, Ghost_141, GreatKevin, Veve, TCSCODER
  - Version: 1.10
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: overview page for studio contest.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - The file upload function should the same as software contest.
  -
  - Version 1.2 (Direct Replatforming Release 4) changes: remove the requirement document radio box for the studio contest.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) changes: new tinyMCE editor.
  - Version 1.4 (Release Assembly - Contest Edit and Upload Update) changes: added text on file size limit.
  - Version 1.5 (Release Assembly - TopCoder Studio CCA Integration) change notes:
  -   Disabled the submission visibility functionality.
  - Version 1.6 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  -   Fix multiple bugs.
  - Version 1.7 (Module Assembly - TC Cockpit Launch Code Contest)
  - - Add a specific CSS class for studio contest prize add and remove
  -
  - Version 1.8 (TopCoder Direct - Design Challenge Track Studio Cup Point Flag)
  - Add studio cup points checkbox and studio cup points display
  -
  - Version 1.9 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
  - - Add section for adding challenge group
  -
  - Version 1.10 (TOPCODER - SUPPORT CUSTOM COPILOT FEE FOR CHALLENGE IN DIRECT APP):
  -     Add support for custom copilot fee
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="contestDetail">	
  <!-- Contest Description -->
	<div class="description">    
        <h3>Challenge Introduction</h3>
        <div class="textarea">
            <textarea id="contestIntroduction" rows="10" cols="80"></textarea>
            <p class="mceFooterNote">All TopCoder Community members will be able to see this description. It should be a general overview of our project. Try to capture your basic goals and needs within 1-2 paragraphs. Do not put any confidential or sensitive information in this section.</p>
        </div>
  </div>
  <!-- end .description -->


  <!-- Contest Introduction -->
	<div class="goals">    
        <h3><span class="icon">Challenge Description</span><div id="ContestDescriptionHelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>        
        <div class="textarea">
            <textarea id="contestDescription" rows="10" cols="80"></textarea>
        </div>        
  </div>
  <!-- end contest introduction -->
        
  <!-- Round 1 information -->
	<div class="target hide" id="round1InfoDiv" >    
        <h3><span class="icon">Round 1 Information</span><div id="Round1HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>
        <div class="textarea">
            <textarea id="round1Info" rows="10" cols="80"></textarea>
        </div>
  </div>
  <!-- end round 1 information -->

  <!-- Round 2 information -->
	<div class="designs hide" id="round2InfoDiv">    
        <h3><span class="icon">Round 2 Information</span><div id="Round2HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>
        <div class="textarea">
            <textarea id="round2Info" rows="10" cols="80" style="padding: 0 5px; font-size: 11px;"></textarea>
        </div>
  </div>
  <!-- end round 2 information -->
  <!-- Environment -->
  <div class="prizes">
      <h3>Environment :</h3>
      <input class="environmentEdit text" name="environmentEdit" type="text" maxlength="500"/>
  </div>

  <!-- repo -->
  <div class="prizes">
      <h3>Code Repo :</h3>
      <input class="repoEdit text" name="repo" type="text" maxlength="500"/>
  </div>
  <!-- upload -->
	<div id="uploadSection">
    
        <h3>File Upload (20MB maximum):</h3>       
        <div class="uploadInner">
            <div id="documentList">
            </div>
            <div>
               File to Upload: <span id="uploadButtonDiv"><input name="document" type="file" > </span> <br/>
               File Description:
               <textarea id="fileDescription" rows="5" cols="50"></textarea>               
               <input id="fileUploadBtn"  type="button" value="Upload File -->"/> <br/>
            </div>
        </div> <!-- end .uploadInner --> 
 </div>
 <!-- end #uploadSection -->
    
    <!-- prizes -->
	<div class="prizes studioPrizes">
    
        <h3>Challenge Prizes:</h3>
        
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
            <a href="javascript:;" class="addButton studioAdd"><span class="hide">ADD</span></a>
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
            <a href="javascript:;" class="removeButton studioRemove"><span class="hide">REMOVE</span></a>
        </div>        
    </div>
    <!-- end .prizes -->
    
    <!-- Checkpoint Prizes -->
	<div class="mPrizes studioPrizes studioCheckpointPrizes hide" id="checkpointPrizeDiv">
    
        <h3><span class="icon">Checkpoint Prizes:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>
        
        <div class="mPrizesInner">
        	<label class="first">Pay</label>
            <span class="dw">$</span>
            <input type="text" id="checkpointPrize" class="prizesInput" value="" />
            <strong>for each submission up to</strong>
            <div class="numSelect">
            	<select id="checkpointSubmissionNumber" >
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
    <div class="copilotFeeDiv">
        <label>Copilot Fee:</label> <span class="dw">$</span> <input type="text" class="copilotFee studio" value="0" disabled="true"/>
    </div>

    <!-- Studio Cup Points -->
    <div class="studioCupPoints drHide" id="studioCupPointsDiv">

        <h3><span class="icon">Studio Cup Points:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>

        <div class="studioCupPointsInner">
            <input type="checkbox" id="studioCupPointsCheckBox"/> <!-- checked by default -->
            <strong>&nbsp;Studio Cup Points&nbsp;:&nbsp;&nbsp;<span id="rStudioCupPoints"></span></strong>
        </div>

    </div>
    <!-- End Studio Cup Points -->

    
    <!-- Final Deliverables -->
    <div class="deliverables">

        <h3><span class="icon">Final Deliverables:</span><a href="javascript:;" class="helpIcon"><span
                class="hide">Help</span></a></h3>

        <div class="deliverablesInner">
            <div class="checkInput" id="deliverablesCheckboxs">
            </div>
            <div class="fileType">
                <a href="javascript:;" class="fileTypeAdd">File Type</a>
            </div>
        </div>

    </div>
    <!-- end .deliverables -->
    
    <!-- Stock Arts -->
	<div class="stockArts">
    
        <h3><span class="icon">Stock Arts:</span><a href="https://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files" class="helpIcon" target="_blank"><span class="hide">Help</span></a></h3>        
        <div class="deliverablesInnerSa">
        	<div class="checkInput" id="stockArtsCheckboxs">
                       <input type="checkbox" value="true" id="allowStockArt" />
                       <label>Is stock photography allowed? Please be sure you understand the <a href="https://apps.topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files">stock art policy</a> before checking this box.</label>
          </div>
          <div class="clear"></div>
        </div>
        
    </div>
    <!-- end .stockArts -->
    <div class="submissionVisibility">
               <h3><span class="icon">Submissions Visibility</span><a class="helpIcon" href="https://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Submissions+Visibility" target="_blank"><span class="hide">Help</span></a></h3>
               <div class="deliverablesInnerSa">
                   <div id="viewableSubmCheckbox" class="checkInput">
                       <input type="checkbox" id="viewableSubmFlag" value="true" disabled="disabled">
                       <label>Submissions are viewable after challenge ends. NOTE: all submissions are hidden during the submission phase.</label>
                   </div>
                   <div class="clear"></div>
               </div>
    </div> <!-- end .submissionVisibility -->

    <div class="maxSubmissions">
       <h3><span class="icon">Maximum Number of Submissions</span><a class="helpIcon" href="https://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Maximum+Number+of+Submissions" target="_blank"><span class="hide">Help</span></a></h3>
       <div class="deliverablesInnerSa">
           <div id="maxSubmissionsInput">
               <input type="text" id="maxSubmissions" class="text" value="5">
               <label>Please enter the max number of submissions a competitor can upload to each round in the challenge. The standard number is 5. Leave blank if you want unlimited submissions.</label>
           </div>
           <div class="clear"></div>
       </div>
   </div> <!-- end .maxSubmissions -->
    <div class="buttonBox">
        <a href="javascript:continueOverview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
        <a href="javascript:saveAsDraftOverview();" class="button6 newButtonGreen draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
        <a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
        <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
        <a href="javascript:backOverview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>
    </div>
    
</div>
