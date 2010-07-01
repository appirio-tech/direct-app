<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
			<h2 class="sectionHead" id="orderReview_title">Order Review</h2>
			<span class="redAlert hide" id="receiptAlert" >We have sheduled your competition and processed payment.</span>
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
						<th>Competition Title :</th>
						<td><span id="orContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Project :</th>
						<td><span id="orRoundType"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
			      <th>Billing Account :</th>
			      <td><span id="orBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
					</tr>
					<tr>
						<th>Launch Time :</th>
						<td><span id="orStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
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
		<tr id='orPrizesTR'>
		</tr>
	</table>
	

  <div id="orMilestonePrizesDiv">
	  <h3>Milestone Prizes</h3>
	  <table class="prizesTable">
	  	<tr id="orMilestonePrizeTR">
	  	</tr>
	  </table>
  </div>
	
	<h3>Administration</h3>
	<table class="prizesTable">
		<tr>
			<td class="large">Administration Fee : <span id="orAdminFee1"></span></td>
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
	<a href="javascript:saveAsDraftOrderReview();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
	<a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
	<a href="javascript:backOrderReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>		
</div>

<div class="buttonBox, hide" id="orderReview_buttonBox2">
	<a href="javascript:editContest();" class="button6 contiune"><span class="left"><span class="right">Edit</span></span></a>
</div>
