<%--
  - Author: Ghost_141, GreatKevin, TCSASSEMBER
  - Version: 1.5
  - Copyright (C) 2010 - 2018 TopCoder Inc., All Rights Reserved.
  -
  - Description: order review page for software contest page.
  -
  - Version 1.1 - TC Direct Replatforming Release 2 Change Note
  - - Add checkpoint prizes section.
  - 
  - Version 1.2 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0 Change Note
  - - Fix multiple bugs.
  -
  - Version 1.3 - Module Assembly - TC Cockpit Launch Code contest
  - - Mark the prizes not needed for the Code contest, they can be hidden. Add support for multiple prizes (up to 5)
  -
  - Version 1.4 - Module Assembly - TC Cockpit Launch F2F contest
  - - Mark the prizes not needed for the First2Finish contest, they can be hidden in F2F mode
  - 
  - Version 1.5 - Topcoder - Support Points Prize Type For Challenges
  - - Add "Challenge Points" section.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
			<h2 class="sectionHead" id="swOrderReview_title">Order Review</h2>
			<span class="redAlert hide" id="swReceiptAlert" >We have scheduled your competition and processed payment.<br/></span>
			 <!-- overviewBox -->
			<div class="overviewBox">
				<table class="overviewData" cellpadding="0" cellspacing="0">
					<tr>
						<th>Date :</th>
						<td><span id="sworDate"></span></td>
					</tr>
					<tr>
						<th>Competition Type :</th>
						<td>Software</td>
					</tr>
					<tr>
						<th>Review Style :</th>
						<td>TopCoder Community Review Board</td>
					</tr>
					<tr>
						<th>Competition Title :</th>
						<td><span id="sworContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Project :</th>
						<td><span id="sworProjectName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
			      <th>Billing Account :</th>
			      <td><span id="sworBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Launch Time :</th>
						<td><span id="sworStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
				</table>
			</div>
		  <!-- end .overviewBox -->
									
</div>
<!-- end .orderReview -->
								
<div class="contestDetails">

	<h2 class="sectionHead">Challenge Details</h2>
	<h3 class="points">Challenge Points</h3>
	<table class="prizesTable points" id="sworPointsTable">
		<tbody><tr></tr></tbody>
	</table>
	<h3>Challenge Prizes</h3>
  <table class="prizesTable" id="sworPrizesTable">
  		<tbody><tr class="hide"></tr><tr>
  			<td>First Place Cost : $<span id="sworFirstPlaceCost"></span> </td>
  			<td class="topcoderPrize">Second Place Cost : $<span id="sworSecondPlaceCost"></span> </td>
  			<td class="topcoderPrize drHide">DR points : $<span id="sworDRPoints"></span> </td>
  			<td class="topcoderPrize">Reliability Bonus Cost : $<span id="sworReliabilityBonusCost"></span> </td>
        <td><a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
        <td class="last">$<span id="sworContestPrizeCost"></span></td>
  		</tr></tbody>  				
  </table>	
  <div id="orswCheckpointPrizesDiv">
    <h3>Checkpoint Prizes</h3>
    <table class="prizesTable">
      <tr id="orswCheckpointPrizeTR"></tr>
    </table>
  </div>
	<h3>Additional Costs:</h3>
	<table class="prizesTable">
		<tr>
  			<td>Challenge Fee : $<span id="sworAdminFee"></span> </td>
  			<td class="topcoderPrize">Specification Review : $<span id="sworSpecificationReviewPayment"></span> </td>
  			<td>Review  : $<span id="sworReviewPayment"></span> </td>
        <td>Copilot Fee : $<span id="sworCopilotFee"></span> </td>
			  <td><a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
  			<td class="last">$<span id="sworAdditionalCosts"></span></td>
		</tr>
	</table>
	<table class="total">
		<tr>
			<td class="toLeft">Estimated Challenge Total:</td>
			<td class="toRight">$<span id="sworTotal"></span></td>
		</tr>
	</table>	

	<table lass="total">
		<tr>
        <p class="note">
        Note: Challenge prizes, costs, and fees in this section are estimates. <br>
        Actual costs are based on prizes paid, review fees based on number of submissions, reliability bonuses paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
        </tr>
    </table>    
  
</div>
<!-- end .contestDetails -->
<hr class="dualDivider" />

<div class="buttonBox" id="swOrderReview_buttonBox">
	<a href="javascript:activateContest();" class="button6 contiune" id="swOrderReview_activateButton"><span class="left"><span class="right">SUBMIT &amp; LAUNCH CHALLENGE</span></span></a>
	<a href="javascript:saveAsDraftOrderReview();" class="button6 newButtonGreen draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
	<a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
	<a href="javascript:backOrderReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>		
</div>

<div class="buttonBox, hide" id="swOrderReview_buttonBox2">
	<a href="javascript:editContest();" class="button6 contiune"><span class="left"><span class="right">EDIT</span></span></a>
</div>

<div class="bottom-review" style="display:none" id="swOrderReview_bottom_review">
    <a href="javascript:;" class="specrev-goto button"></a>
    <a href="javascript:;" class="specrev-goto">Go to my Spec Review</a>
    <p></p>
    <br /><br /><br />
</div>
