<%--
  - Author: bugbuka, TCSCCODER
  - Version: 1.2
  - Copyright (C) 2013 - 2018 TopCoder Inc., All Rights Reserved.
  -
  - Description: review page for algorithm contest.
  -
  - Version 1.1 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
  - - Add section for displaying challenge group
  - 
  - Version 1.2 (Topcoder - Support Points Prize Type For Challenges):
  - - Add "Challenge Points" section.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- tabHead -->
<div class="tabHead">
	<ul>
		<li class="current">Algorithm</li>
		<li id="ralContestTypeName"></li>
	</ul>
	<a href="javascript:showPage('contestSelectionPage');" class="button6 newButtonGreen draft"><span class="left"><span class="right">EDIT</span></span></a>
	<div class="tr"></div>								
</div>

<!-- overviewBox -->
<div class="overviewBox">
	<table class="overviewData" cellpadding="0" cellspacing="0">
		<tr>
			<th>Challenge Name :</th>
			<td><span id="ralContestName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
			<th>Project Name :</th>
			<td><span id="ralProjectName"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
			<th>Billing Account :</th>
			<td><span id="ralBillingAccount"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
		<tr>
			<th>Start :</th>
			<td><span id="ralStartDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
        <tr>
            <th>End :</th>
            <td><span id="ralEndDate"></span><a href="javascript:showPage('contestSelectionPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
        </tr>
		<tr class="rGroups">
			<th>Group(s) :</th>
			<td><a href="javascript:backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a></td>
		</tr>
	</table>
</div>
<!-- End .overviewBox -->

<!-- contentList -->
<div class="contentList previewMask">
	<dl>
        <dt>Problem Statement :</dt>
        <dd><span id="ralProblemStatement"></span><a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>
	
		<dt>Match Details :</dt>
		<dd><span id="ralMatchDetails"></span><a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

		<dt>Match Rules :</dt>
		<dd><span id="ralMatchRules"></span><a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>
<%--
        <dt>Environment :</dt>
        <dd class="rEnvironment"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>

        <dt>Code Repo :</dt>
        <dd class="rRepo"><a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a> </dd>
--%>
	</dl>
</div>
<!-- end .contentList -->
              
<div class="cboxOut">
              	
  <!-- title -->
	<h3>Files: </h3>
                  
  <!-- cboxFiles -->
	<div class="cbox" id="alCboxFiles">		
		<dl id="alDocUploadList">
		</dl>                      
    <a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
</div>
<!-- end #cboxFiles -->
                  
</div>

<!-- prizes -->
<div class="prizes">
    <h3>Challenge Prizes:</h3>
    
    <div class="prizesInner">
        <div  id="ralPrizes">
        </div>
        <a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
    </div>    
</div>
<!-- end .prizes -->

<!-- points -->
<div class="points">
    <h3>Challenge Points:</h3>
    
    <div class="prizesInner">
        <div id="ralPoints">
        </div>
        <a href="javascript: showPage('overviewAlgorithmPage');" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>
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
