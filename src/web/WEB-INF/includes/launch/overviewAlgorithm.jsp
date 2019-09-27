<%--
  - Author: bugbuka, GreatKevin, TCSCODER
  - Version: 1.6
  - Copyright (C) 2013 - 2018 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders edit algorithm contest page.
  -
  - Version 1.2 (Module Assembly - TC Cockpit Launch Code Contest)
  - - Add a specific CSS class for algorithm contest prize add and remove
  -
  - Version 1.3 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
  - - Add section for adding challenge group
  -
  - Version 1.4 (TOPCODER - SUPPORT CUSTOM COPILOT FEE FOR CHALLENGE IN DIRECT APP):
  -   Add support for custom copilot fee
  - Version 1.5 (Topcoder - Add Basic Marathon Match Creation And Update In Direct App):
  - - Remove MM problem selection
  -
  - Version 1.6 (Topcoder - Support Points Prize Type For Challenges):
  - - Add "Challenge Points" section.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="contestDetail">
    <!-- Match Details -->
    <div class="description">    
        <h3>Match Details</h3>
        <div class="textarea">
            <textarea id="matchDetails" rows="10" cols="80"></textarea>
        </div>
    </div>
    <!-- end Match Details -->


    <!-- Match Rules -->
    <div class="goals">    
        <h3><span class="icon">Match Rules</span></h3>        
        <div class="textarea">
            <textarea id="matchRules" rows="10" cols="80"></textarea>
        </div>        
    </div>
    <!-- end Match Rules -->

  <!-- Platform -->
  <div class="prizes" id="alPlatformDiv">
      <h3>Choose Your Platform:</h3>
      <div class="techPlatformDiv">
          <div id="alPlatforms"></div>
      </div>
  </div>

  <!-- Technology -->
  <div class="prizes" id="alTechnologyDiv">
      <h3>Choose Your Technology:</h3>
      <div class="techPlatformDiv">
          <div id="alTechnologies"></div>
      </div>
   </div>

<%--
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
--%>
    <!-- upload -->
    <div id="alUploadSection">
        <h3>File Upload (20MB maximum):</h3>       
        <div class="uploadInner">
            <div id="alDocumentList">
            </div>
            <div>
               File to Upload: <span id="alUploadButtonDiv"><input name="document" type="file" > </span> <br/>
               File Description:
               <textarea id="alFileDescription" rows="5" cols="50"></textarea>               
               <input id="alFileUploadBtn"  type="button" value="Upload File -->"/> <br/>
            </div>
        </div> <!-- end .uploadInner --> 
    </div>
    <!-- end #uploadSection -->

    <!-- prizes -->
    <div class="alPrizes">
    
        <h3>Challenge Prizes:</h3>
        
        <div class="prizesInner">
              <label class="first">1st Place</label>
            <span class="dw">$</span>
            <input type="text" id="alPrize1" class="prizesInput" value="" />
            <label class="second">2nd Place</label>
            <span class="dw">$</span>
            <input type="text" id="alPrize2"  class="prizesInput" value="" />
            <label class="third">3rd Place</label>
            <span class="dw">$</span>
            <input type="text" id="alPrize3" class="prizesInput" value="" />
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
    </div>
    <!-- end .prizes -->
    <div class="copilotFeeDiv">
        <label>Copilot Fee:</label> <span class="dw">$</span> <input type="text" class="copilotFee algorithm" value="0" disabled="true"/>
    </div>

    <!-- points -->
    <div id="alPoints" class="points">

        <h3>Challenge Points:</h3>

        <div class="prizesInner">
            <label class="first">1st Place</label>
            <span class="dw">Pt.</span>
            <input type="text" id="alPoint1" class="pointsInput" value="" />
            <label class="second">2nd Place</label>
            <span class="dw">Pt.</span>
            <input type="text" id="alPoint2"  class="pointsInput" value="" />
            <label class="third">3rd Place</label>
            <span class="dw">Pt.</span>
            <input type="text" id="alPoint3" class="pointsInput" value="" />
            <a href="javascript:;" class="addButton addPoint"><span class="hide">ADD</span></a>
        </div>

        <div id="alExtraPoints" class="prizesInner extraPoints hide">
            <label class="first">4th Place</label>
            <span class="dw">Pt.</span>
            <input type="text" id="alPoint4" class="pointsInput" value="" />
            <label class="second">5th Place</label>
            <span class="dw">Pt.</span>
            <input type="text" id="alPoint5" class="pointsInput" value="" />
            <label class="third">&nbsp;</label>
            <span class="dw">&nbsp;</span>
            <a href="javascript:;" class="removeButton removePoint"><span class="hide">REMOVE</span></a>
        </div>
    </div>
    <!-- end .points -->

    <div class="buttonBox">
        <a href="javascript:continueOverview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
        <a href="javascript:saveAsDraftOverview();" class="button6 newButtonGreen draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
        <a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
        <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
        <a href="javascript:backOverview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>
    </div>
</div>          
                       
