<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 3 of post a copilot.
  - Changes in version 1.1 (TC Cockpit Post a Copilot Assembly 2):
  -   Added id attribute for budget selection.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.1 (TC Cockpit Post a Copilot Assembly 2).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep3 stepDiv">
    <!-- form -->
    <div class="form ">
        
       <div class="noteContainer">
            <p>
           In this step you will be directed to choose what you have estimated budget for this project or not. Please fill out the form below:
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
                        <input type="radio" class="radio" name="budget" id="haveBudget"/> <label for="haveBudget">a. I have a budget</label> <input id="budget" type="text" class="text" value="$" />
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