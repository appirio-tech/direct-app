<%--
  - Author: TCSASSEMBER
  - Version: 1.1
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: order review page for software contest page.
  -
  - Version 1.1 - TC Direct Replatforming Release 2 Change Note
  - - Add milestone prizes section.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
			<h2 class="sectionHead" id="swOrderReview_title">Order Review</h2>
			<span class="redAlert hide" id="swReceiptAlert" >We have scheduled your competition and processed payment.<br/>This is an objective contest, TopCoder will declare the winners through member review process.</span>
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
						<th>Competition Title :</th>
						<td><span id="sworContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Project :</th>
						<td><span id="sworProjectName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
			      <th>Billing Account :</th>
			      <td><span id="sworBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Launch Time :</th>
						<td><span id="sworStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
				</table>
			</div>
		  <!-- end .overviewBox -->
									
</div>
<!-- end .orderReview -->
								
<div class="contestDetails">

	<h2 class="sectionHead">Contest Details</h2>
	<h3>Contest Prizes</h3>
  <table class="prizesTable">
  		<tbody><tr>
  			<td>First Place Cost : $<span id="sworFirstPlaceCost"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>Second Place Cost : $<span id="sworSecondPlaceCost"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>DR points : $<span id="sworDRPoints"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>Reliability Bonus Cost : $<span id="sworReliabilityBonusCost"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td class="last"><span id="sworContestPrizeCost"></span></td>
  		</tr></tbody>  				
  </table>	
  <div id="orswMilestonePrizesDiv">
    <h3>Milestone Prizes</h3>
    <table class="prizesTable">
      <tr id="orswMilestonePrizeTR"></tr>
    </table>
  </div>
	<h3>Additional Costs:</h3>
	<table class="prizesTable">
		<tr>
  			<td>Contest Fee : $<span id="sworAdminFee"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>Specification Review : $<span id="sworSpecificationReviewPayment"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>Review  : $<span id="sworReviewPayment"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
            <td>Copilot Fee : $<span id="sworCopilotFee"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
			<td>&nbsp;</td>
  			<td class="last"><span id="sworAdditionalCosts"></span></td>
		</tr>
	</table>
	<table class="total">
		<tr>
			<td class="toLeft">Total:</td>
			<td class="toRight">$<span id="sworTotal"></span></td>
		</tr>
	</table>	
</div>
<!-- end .contestDetails -->
<hr class="dualDivider" />

<div class="buttonBox" id="swOrderReview_buttonBox">
	<a href="javascript:activateContest();" class="button6 contiune" id="swOrderReview_activateButton"><span class="left"><span class="right">SUBMIT &amp; LAUNCH CONTEST</span></span></a>
	<a href="javascript:saveAsDraftOrderReview();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
	<a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
	<a href="javascript:backOrderReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>		
</div>

<div class="buttonBox, hide" id="swOrderReview_buttonBox2">
	<a href="javascript:editContest();" class="button6 contiune"><span class="left"><span class="right">Edit</span></span></a>
</div>

<div class="bottom-review" style="display:none" id="swOrderReview_bottom_review">
    <a href="javascript:;" class="specrev-goto button"></a>
    <a href="javascript:;" class="specrev-goto">Go to my Spec Review</a>
    <p></p>
    <br /><br /><br />
</div>
