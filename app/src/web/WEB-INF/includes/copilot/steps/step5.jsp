<%--
  - Author: GreatKevin
  - Version: 1.2
  - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Update in version 1.1 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - - Update the top note of step 5.

 - Version 1,2 (Release Assembly - TC Cockpit Private Challenge Update)
  -- Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
  -
  - Description: The step 5 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep5 stepDiv">
    <!-- form -->
    <div class="form">
        <div class="noteContainer">
            <p>
                In order to post this Copilot Opportunity to the community you will need to select a Billing Account. 
                This will tell us how to invoice you. If you do not have a Billing Account in the list below and feel you should, 
                please email <a href="mailto:support@topcoder.com">support@topcoder.com</a>
           </p>
        </div>
        
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Billing Account</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                <label>Select your billing account<span class="red">*</span></label>
                <select id="billingProjects" name="billingProject">
                    <option value="0">Please select an existing account</option>
                </select>
                <a href="javascript:;" target="_blank" class="addBilling hide">Add Billing to project</a>                
            </div>

            <div class="rowItem" id="billingGroupCheckBox" style="font-size: 12px !important">
                <label>Private Group<span class="red">*</span></label>
                <input type="checkbox" style=""><span style="font-size: 12px !important">Run this copilot posting in a private community ? &nbsp;&nbsp;</span>  <select id="billingGroups" name="billingGroups"></select><br/><br/>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
    </div>
    <!-- End .form -->  
</div>
