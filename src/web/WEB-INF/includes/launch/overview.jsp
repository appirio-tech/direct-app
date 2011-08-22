<%--
  - Author: TCSASSEMBER
  - Version: 1.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: overview page for studio contest.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - The file upload function should the same as software contest.
  - Version 1.2 (Direct Replatforming Release 4) changes: remove the requirement document radio box for the studio contest.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="contestDetail">	
  <!-- Contest Description -->
	<div class="description">    
        <h3>Contest Introduction</h3>
        <div class="textarea">
            <textarea id="contestIntroduction" rows="" cols=""></textarea>
        </div>
  </div>
  <!-- end .description -->


  <!-- Contest Introduction -->
	<div class="goals">    
        <h3><span class="icon">Contest Description</span><div id="ContestDescriptionHelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>        
        <div class="textarea">
            <textarea id="contestDescription" rows="" cols=""></textarea>
        </div>        
  </div>
  <!-- end contest introduction -->
        
  <!-- Round 1 information -->
	<div class="target hide" id="round1InfoDiv" >    
        <h3><span class="icon">Round 1 Information</span><div id="Round1HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>
        <div class="textarea">
            <textarea id="round1Info" rows="" cols=""></textarea>
        </div>
  </div>
  <!-- end round 1 information -->

  <!-- Round 2 information -->
	<div class="designs hide" id="round2InfoDiv">    
        <h3><span class="icon">Round 2 Information</span><div id="Round2HelpIcon"><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></div></h3>
        <div class="textarea">
            <textarea id="round2Info" rows="" cols=""></textarea>
        </div>
  </div>
  <!-- end round 2 information -->

  <!-- upload -->
	<div id="uploadSection">
    
        <h3>File Upload:</h3>       
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
	<div class="prizes">
    
        <h3>Contest Prizes:</h3>
        
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
    </div>
    <!-- end .prizes -->
    
    <!-- Milestone Prizes -->
	<div class="mPrizes hide" id="milestonePrizeDiv">
    
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
    
    <!-- Final Deliverables -->
	<div class="deliverables">
    
        <h3><span class="icon">Final Deliverables:</span><a href="javascript:;" class="helpIcon"><span class="hide">Help</span></a></h3>        
        <div class="deliverablesInner">
        	<div class="checkInput" id="deliverablesCheckboxs">
          </div>
          <a href="javascript:;" class="fileType">File Type</a>
          <div class="clear"></div>
        </div>
        
    </div>
    <!-- end .deliverables -->
    
    <!-- Stock Arts -->
	<div class="stockArts">
    
        <h3><span class="icon">Stock Arts:</span><a href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files" class="helpIcon" target="_blank"><span class="hide">Help</span></a></h3>        
        <div class="deliverablesInnerSa">
        	<div class="checkInput" id="stockArtsCheckboxs">
                       <input type="checkbox" value="true" id="allowStockArt" />
                       <label>Is stock photography allowed? Please be sure you understand the <a href="http://apps.topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Stock+Art+and+Font+Files">stock art policy</a> before checking this box.</label>
          </div>
          <div class="clear"></div>
        </div>
        
    </div>
    <!-- end .stockArts -->
    <c:if test="${admin}">
    <div class="submissionVisibility">
               <h3><span class="icon">Submissions Visibility</span><a class="helpIcon" href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Submissions+Visibility" target="_blank"><span class="hide">Help</span></a></h3>
               <div class="deliverablesInnerSa">
                   <div id="viewableSubmCheckbox" class="checkInput">
                       <input type="checkbox" id="viewableSubmFlag" value="true" checked="checked">
                       <label>Submissions are viewable after contest ends. NOTE: all submissions are hidden during the submission phase.</label>
                   </div>
                   <div class="clear"></div>
               </div>
    </div> <!-- end .submissionVisibility -->

    <div class="maxSubmissions">
       <h3><span class="icon">Maximum Number of Submissions</span><a class="helpIcon" href="http://topcoder.com/wiki/display/tcstudio/Studio+Policies+for+Maximum+Number+of+Submissions" target="_blank"><span class="hide">Help</span></a></h3>
       <div class="deliverablesInnerSa">
           <div id="maxSubmissionsInput">
               <input type="text" id="maxSubmissions" class="text" value="5">
               <label>Please enter the max number of submissions a competitor can upload to each round in the contest. The standard number is 5. Leave blank if you want unlimited submissions.</label>
           </div>
           <div class="clear"></div>
       </div>
   </div> <!-- end .maxSubmissions -->
   </c:if>
    <div class="buttonBox">
        <a href="javascript:continueOverview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
        <a href="javascript:saveAsDraftOverview();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
        <a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
        <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
        <a href="javascript:backOverview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>
    </div>
    
</div>