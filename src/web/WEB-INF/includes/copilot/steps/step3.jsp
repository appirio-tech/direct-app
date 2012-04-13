<%--
  - Author: duxiaoyang, TCSASSEMBLER
  - Version: 1.2
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 3 of post a copilot.
  -
  - Changes in version 1.1 (TC Cockpit Post a Copilot Assembly 2):
  -   Added id attribute for budget selection.
  -
  - Changes in version 1.2 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  -   Update the top note of step 3.
  -
  - Since: TC Cockpit Post a Copilot Assembly 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep3 stepDiv">
    <!-- form -->
    <div class="form ">
        
       <div class="noteContainer">
            <p>
                If you have a strict budget that you need to stick to, let the copilots know what it is.
                Many people prefer to exclude their budgets at this point to avoid adding bias to the copilots' plans.
           </p>
       </div>
       
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Budget Information</h2>
                </div></div>
            </div><!-- End .title -->
            
            
            <div class="rowItem">
                <label>Do you have a budget?<span class="red">*</span></label>
                    
                <ul>
                    <li>
                        <input type="radio" class="radio" name="budget" id="haveBudget"/> <label for="haveBudget">a. I have a budget</label> <input id="budget" type="text" class="text" value="" />
                    </li>
                    <li>
                        <input type="radio" class="radio" name="budget" id="notHaveBudget"/> <label for="notHaveBudget">b. I don't have a budget yet.</label> 
                    </li>
                </ul>
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
       
        
    </div>
    <!-- End .form -->
</div>
