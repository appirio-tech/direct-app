<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
			<h2 class="sectionHead" id="swOrderReview_title">Order Review</h2>
			<span class="redAlert hide" id="swReceiptAlert" >We have sheduled your competition and processed payment.</span>
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
  			<td>1st Place : $<span id="sworFirstPlaceCost"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>2nd Place : $<span id="sworSecondPlaceCost"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
  			<td>&nbsp;</td>
  			<td>&nbsp;</td>
  			<td>&nbsp;</td>
  			<td>&nbsp;</td>
  		</tr></tbody>  				
  </table>	
	<h3>Administration</h3>
	<table class="prizesTable">
		<tr>
			<td class="large">Administration Fee : $<span id="sworAdminFee"></span> <a href="javascript:showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/penicon.gif" alt="Edit"/></a></td>
			<td>&nbsp;</td>
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
