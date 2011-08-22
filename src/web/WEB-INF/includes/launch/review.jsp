<%--
  - Author: TCSASSEMBER
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: review page for studio contest.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Change Milestone Date to Milestone End Date.
  - - Remove End Date for project.
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- tabHead -->
<div class="tabHead">
    <ul>
        <li class="current">Design</li>
        <li id="rContestTypeName"></li>
    </ul>
    <a href="javascript:showPage('contestSelectionPage');" class="button6 draft"><span class="left"><span class="right">EDIT</span></span></a>                              
    <div class="tr"></div>                              
</div>

<!-- overviewBox -->
<div class="overviewBox">
    <table class="overviewData" cellpadding="0" cellspacing="0">
        <tr>
            <th>Contest Name :</th>
            <td><span id="rContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr>
            <th>Project Name :</th>
            <td><span id="rProjectName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr>
            <th>Billing Account :</th>
            <td><span id="rBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr>
            <th>Round Type :</th>
            <td><span id="rRoundType"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr>
            <th>Start :</th>
            <td><span id="rStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr id="rMileStoneTR">
            <th>Milestone End :</th>
            <td><span id="rMilestoneDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
        <tr>
            <th>End :</th>
            <td><span id="rEndDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
        </tr>
    </table>
</div>
<!-- End .overviewBox -->

<!-- contentList -->
<div class="contentList">
    <dl>
        <dt>Contest Introduction :</dt>
        <dd><span id="rContestIntroduction"></span><a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a> </dd>        

        <dt>Contest Description :</dt>
        <dd><span id="rContestDescription"></span><a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a> </dd>     

        <dt class="rMultiInfo">Round One Information :</dt>
        <dd class="rMultiInfo"><span id="rRound1Info"></span><a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a> </dd>      

        <dt class="rMultiInfo">Round Two Information :</dt>
        <dd class="rMultiInfo"><span id="rRound2Info"></span><a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a> </dd>      
    </dl>
</div>
<!-- end .contentList -->
              
<div class="cboxOut">
                
  <!-- title -->
    <h3>Files: </h3>
                  
  <!-- cboxFiles -->
    <div class="cbox" id="cboxFiles">       
        <dl id="docUploadList">
        </dl>                      
    <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>     
</div>
<!-- end #cboxFiles -->
                  
</div>

<!-- prizes -->
<div class="prizes">
    <h3>Contest Prizes:</h3>
    
    <div class="prizesInner">
        <div  id="rPrizes">
        </div>
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>        
    </div>    
</div>
<!-- end .prizes -->

<!-- Milestone Prizes -->
<div class="mPrizes" id="rMPrizesDiv">
    <h3>Milestone Prizes:</h3>
    
    <div class="mPrizesInner">
        <label class="first">Pay</label>
        <span class="dw">$</span>
        <span class="numberDor" id="rMPrizesAmount"></span>
        <strong>for each submission up to</strong>
        <span class="numberDor" id="rMPrizesNumberOfSubmissions"></span>
        
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>
        
    </div>
    
</div>
<!-- end .mPrizes -->

<!-- Final Deliverables -->
<div class="deliverables">

    <h3>Final Deliverables:</h3>
    
    <div class="deliverablesInner">
        <div class="rCheckInput" id="rFinalDeliveries" >
        </div>
        
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>
        
        <div class="clear"></div>
        
    </div>
    
</div>
<!-- end .deliverables -->

<!-- Stock Arts -->
<div class="stockArts">
    <h3>Stock Arts:</h3>
    
    <div class="stockArtsInner">
        <div  id="rStockArts">
        </div>
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>        
    </div>    
</div>
<!-- end .stockArts -->
<c:if test="${admin}">
<!-- Submission Visibility -->
<div class="submissionVisibility">
    <h3>Submission Visibility:</h3>

    <div class="submissionVisibilityInner">
        <div  id="rViewableSubmFlag">
        </div>
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>
    </div>
</div>
<!-- end .submissionVisibility -->

<!-- Max Submissions -->
<div class="maxSubmissions">
    <h3>Max Number of Submissions:</h3>

    <div class="maxSubmissionsInner">
        <div  id="rMaxSubmissions">
        </div>
        <a href="javascript: showPage('overviewPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a>
    </div>
</div>
<!-- end .submissionVisibility -->
</c:if>
<div class="buttonBox"> 
    <a href="javascript:continueReview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
    <a href="javascript:saveAsDraftReview();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
        <a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>  
    <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
    <a href="javascript:backReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>        
</div>
