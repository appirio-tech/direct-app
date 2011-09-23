<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 5 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.0 (TC Cockpit Post a Copilot Assembly 1).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep5 stepDiv">
    <!-- form -->
    <div class="form">
        <div class="noteContainer">
            <p>
            In this step you will be directed to set your billing account for this project. Please fill out the form below:
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
                    <s:iterator value="billingProjects">
                    <option value='<s:property value="projectId" />'><s:property value="name" /></option>
                    </s:iterator>
                </select>                
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
    </div>
    <!-- End .form -->  
</div>