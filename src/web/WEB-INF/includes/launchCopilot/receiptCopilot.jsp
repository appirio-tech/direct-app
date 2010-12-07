<%--
  - Author: tangzx, TCSDEVELOPER
  - Version: 1.0.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Launch copilot selection contest receipt section page.
  - Since: TC Direct - Launch Copilot Selection Contest assembly
  -
  - Version 1.0.1 (Manage Copilot Postings assembly) change notes: linked "Go to My Copilot Selection Contests" link
  - to "Manage Copilot Postings" page. 
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div class="orderReview">
    <span class="paymentNumBox" id="idReceipt"></span>
    <h2 class="sectionHead">Receipt</h2>
    <span class="redAlert">We have sheduled your Copilot Posting and processed payment.</span>
     <!-- overviewBox -->
    <div class="overviewBox">
        <table class="overviewData" cellpadding="0" cellspacing="0">
            <tr>
                <th>Copilot Posting Name :</th>
                <td><span id="contestNameReceipt"></span></td>
            </tr>
            <tr>
                <th>Project Name:</th>
                <td><span id="projectNameReceipt"></span></td>
            </tr>
            <tr>
                <th>Payment Method :</th>
                <td><span id="paymentMethodReceipt"></span></td>
            </tr>
            <tr>
                <th>Start Time :</th>
                <td><span id="startTimeReceipt"></span></td>
            </tr>
        </table>
    </div>
    <!-- end .overviewBox -->
</div>
<!-- end .orderReview -->

<div class="contestDetails">
    <h2 class="sectionHead">Costs</h2> 
    <table class="prizesTable">
        <tbody><tr>
            <td class="prize"><span>Administration Fee:</span> $<span id="adminFeeReceipt" class="feeValue"></span> </td>
            <td class="prize"><span>1st Prize :</span> $<span id="firstFeeReceipt" class="feeValue"></span> </td>
            <td class="prize"><span>2nd Prize :</span> $<span id="secondFeeReceipt" class="feeValue"></span></td>
            <td class="total">Total</td>
            <td class="last totalFeeReceipt"></td>
        </tr>
    </tbody></table>
     
    <table class="total">
        <tr>
            <td class="toLeft">Total:</td>
            <td class="toRight totalFeeReceipt"></td>
        </tr>
    </table>
    
</div>
<!-- end .contestDetails -->
<hr class="dualDivider" />

<div class="bottom-review" id="contestDetailLinkDiv">
    <a href="#" class="button"></a>
    <a href="<s:url action='listCopilotPostings' namespace='/copilot'/>" >Go to My Copilot Selection Contests</a>
    <p>Go to My Copilot Selection Contests section to view your copilot posting.</p>
    <br /><br /><br />
</div>
