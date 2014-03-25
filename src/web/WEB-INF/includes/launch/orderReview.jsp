<%--
  - Author: TCSASSEMBER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: order review page for studio contest page.
  -
  - Version 1.1 - TC Direct Replatforming Release 2 Change Note
  - - Add specification review cost.
  - - Add "Go to my Spec Review" section to support starting specification review for studio contest. 
  - 
  - Version 1.2 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0 Change Note
  - - Fix multiple bugs.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
			<h2 class="sectionHead" id="orderReview_title">Order Review</h2>
			<span class="redAlert hide" id="receiptAlert" >We have scheduled your competition and processed payment.<br/></span>
			 <!-- overviewBox -->
			<div class="overviewBox">
				<table class="overviewData" cellpadding="0" cellspacing="0">
					<tr>
						<th>Date :</th>
						<td><span id="orDate"></span></td>
					</tr>
					<tr>
						<th>Competition Type :</th>
						<td>Studio</td>
					</tr>
					<tr>
						<th>Review Style :</th>
						<td>User Selection</td>
					</tr>
					<tr>
						<th>Competition Title :</th>
						<td><span id="orContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Review Style :</th>
						<td>User Selection</td>
					</tr>
					<tr>
						<th>Project :</th>
						<td><span id="orRoundType"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
			      <th>Billing Account :</th>
			      <td><span id="orBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Launch Time :</th>
						<td><span id="orStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
					</tr>
				</table>
			</div>
		  <!-- end .overviewBox -->
									
</div>
<!-- end .orderReview -->
								
<div class="contestDetails">

	<h2 class="sectionHead">Challenge Details</h2>
	<h3>Challenge Prizes</h3>
	<table class="prizesTable">
		<tr id='orPrizesTR'>
		</tr>
	</table>
	

  <div id="orCheckpointPrizesDiv">
	  <h3>Checkpoint Prizes</h3>
	  <table class="prizesTable">
	  	<tr id="orCheckpointPrizeTR">
	  	</tr>
	  </table>
  </div>
	
	<h3>Administration</h3>
	<table class="prizesTable">
		<tr>
			<td>Administration Fee : <span id="orAdminFee1"></span></td>
			<td>Specification Review : <span id="orSpecificationReviewPayment"></span> <a href="javascript:showPage('overviewPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
            <td>Screening : <span id="orReviewPayment"></span> </td>
			<td>Copilot Fee : <span id="orCopilotFee"></span></td>
			<td class="last"><span id="orAdminFee2"></span></td>
		</tr>
	</table>
	
	<table class="total">
		<tr>
			<td class="toLeft">Total:</td>
			<td class="toRight"><span id="orTotal"></span></td>
		</tr>
	</table>
	
</div>
<!-- end .contestDetails -->
<hr class="dualDivider" />

<div class="buttonBox" id="orderReview_buttonBox">
	<a href="javascript:activateContest();" class="button6 contiune" id="orderReview_activateButton"><span class="left"><span class="right">SUBMIT &amp; LAUNCH CONTEST</span></span></a>
	<a href="javascript:saveAsDraftOrderReview();" class="button6 newButtonGreen draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
	<a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
	<a href="javascript:backOrderReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>		
</div>

<div class="buttonBox, hide" id="orderReview_buttonBox2">
	<a href="javascript:editContest();" class="button6 contiune"><span class="left"><span class="right">EDIT</span></span></a>
</div>

<div class="bottom-review" style="display:none" id="orderReview_bottom_review">
    <a href="javascript:;" class="specrev-goto button"></a>
    <a href="javascript:;" class="specrev-goto">Go to my Spec Review</a>
    <p></p>
    <br /><br /><br />
</div>