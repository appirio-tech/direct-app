<%--
  - Author: GreatKevin, TCSCODER
  - Version: 1.8
  - Copyright (C) 2010 - 2018 TopCoder Inc., All Rights Reserved.
  -
  - Description: review page for software contest.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Add multiround type support to software contest.
  -
  - Version 1.2 - TC Direct Replatforming Release 2
  - - Added checkpoint prizes section.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) changes: new tinyMCE editor.
  -
  - Version 1.4 (Module Assembly - TC Cockpit Launch Code contest)
  - - Hide the uneeded prize for Code contest, add multiple prizes
  -
  - Version 1.5 (Module Assembly - TC Cockpit Launch F2F contest)
  - - Hide the uneeded prize for F2F contest
  -
  - Version 1.6 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
  - - Add section for display and edit challenge group
  -
  - Version 1.7 (Topcoder - Support Points Prize Type For Challenges):
  - - Add "Challenge Points" section.
  -
  - Version 1.8 (Topcoder - Add effort days field):
  - - Add enable effort days
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- tabHead -->
<div class="tabHead">
	<ul>
		<li class="current">Software</li>
		<li id="rswContestTypeName"></li>
	</ul>
	<a href="javascript:showPage('contestSelectionPage');" class="button6 newButtonGreen draft"><span class="left"><span class="right">EDIT</span></span></a>
	<div class="tr"></div>
</div>

<!-- overviewBox -->
<div class="overviewBox">
	<table class="overviewData" cellpadding="0" cellspacing="0">
		<tr>
			<th>Challenge Name :</th>
			<td><span id="rswContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
			<th>Project Name :</th>
			<td><span id="rswProjectName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
			<th>Billing Account :</th>
			<td><span id="rswBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
            <th>Round Type :</th>
            <td><span id="rswRoundType"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
        </tr>
		<tr>
			<th>Start :</th>
			<td><span id="rswStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr id="rswCheckpointTR">
			<th>Checkpoint End :</th>
			<td><span id="rswCheckpointDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr class="rGroups">
			<th>Group(s) :</th>
			<td><a href="javascript:backOverview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
        <tr class="effortEstimateRow">
            <th>Effort Estimates :<div><small>Offshore (In Person Days)</small></div></th>
            <td><span id="rswEffortDaysEstimateOffshore"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink">
              <img src="/images/edit-icon.png" alt="Edit"/></a>
            </td>
        </tr>
        <tr class="effortEstimateRow">
            <th>Effort Estimates :<div><small>Onsite (In Person Days)</small></div></th>
            <td><span id="rswEffortDaysEstimateOnsite"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink">
              <img src="/images/edit-icon.png" alt="Edit"/></a>
            </td>
        </tr>
	</table>
</div>
<!-- End .overviewBox -->

<!-- contentList -->
<div class="contentList previewMask">
	<dl>
		<dt>Detailed Requirements :</dt>
		<dd><span id="rswDetailedRequirements"></span><a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

		<dt>Submission Guidelines :</dt>
		<dd><span id="rswSoftwareGuidelines"></span><a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

        <dt>Platforms :</dt>
        <dd id="rPlatforms"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

        <dt>Technologies :</dt>
        <dd id="rTechnologies"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

        <dt>Environment :</dt>
        <dd class="rEnvironment"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

        <dt>Code Repo :</dt>
        <dd class="rRepo"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

	</dl>
</div>
<!-- end .contentList -->

<div class="cboxOut">

  <!-- title -->
	<h3>Files: </h3>

  <!-- cboxFiles -->
	<div class="cbox" id="swCboxFiles">
		<dl id="swDocUploadList">
		</dl>
    <a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
</div>
<!-- end #cboxFiles -->

</div>

<!-- prizes -->
<div class="prizes">
    <h3>Challenge Prizes:</h3>

    <div class="prizesInner">
        <div id="rswPrizes">
        </div>
        <a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
    </div>
</div>
<!-- end .prizes -->

<!-- Checkpoint Prizes -->
<div class="mPrizes" id="rswMPrizesDiv">
    <h3>Checkpoint Prizes:</h3>

    <div class="mPrizesInner">
        <label class="first">Pay</label>
        <span class="dw">$</span>
        <span class="numberDor" id="rswMPrizesAmount"></span>
        <strong>for each submission up to</strong>
        <span class="numberDor" id="rswMPrizesNumberOfSubmissions"></span>

        <a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
    </div>
</div>
<!-- end .mPrizes -->

<!-- points -->
<div class="points">
    <h3>Challenge Points:</h3>

    <div class="prizesInner">
        <div id="rswPoints">
        </div>
        <a href="javascript: showPage('overviewSoftwarePage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
    </div>
</div>
<!-- end .points -->

<div class="buttonBox">
    <a href="javascript:continueReview();" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
    <a href="javascript:saveAsDraftReview();" class="button6 newButtonGreen draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
		<a href="javascript:previewContest();" class="button6 preview"><span class="left"><span class="right">PREVIEW</span></span></a>
    <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
    <a href="javascript:backReview();" class="button6 preview"><span class="left"><span class="right">BACK</span></span></a>
</div>
