<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 2 of post a copilot.
  - Changes in version 1.1 (TC Cockpit Post a Copilot Assembly 2):
  -   Retrieve copilot types from action and display on the page.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.1 (TC Cockpit Post a Copilot Assembly 2).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep2 stepDiv">
    <!-- form -->
    <div class="form">
        <div class="noteContainer">
            <p>
            In this step you will be directed to select the skills and experience needed by a copilot to work on your project, based on the type of your project. Please fill out the form below: 
           </p>
        </div>
        
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Experience</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                <label>I prefer that the copilot has previously managed the following type(s) of projects<span class="red">*</span></label>
                <div class="checkType">
                    <span>Please check the type of projects that meet your requirement :</span>
                    
                    <div class="normal">
                        <ul class="type1">
                            <c:forEach var="copilotType" items="${allProjectCopilotTypes}" end="${fn:length(allProjectCopilotTypes) / 2 - 0.5}">
                            <li>
                                <input type="checkbox" id="${copilotType.value}" value="${copilotType.key}" />
                                <label for="${copilotType.value}">${copilotType.value}</label>
                            </li>
                            </c:forEach>
                        </ul>
                        <ul class="type3">
                            <c:forEach var="copilotType" items="${allProjectCopilotTypes}" begin="${fn:length(allProjectCopilotTypes) / 2 + 0.5}">
                            <li>
                                <input type="checkbox" id="${copilotType.value}" value="${copilotType.key}" />
                                <label for="${copilotType.value}">${copilotType.value}</label>
                            </li>
                            </c:forEach>
                        </ul>
                        <div class="clear"></div>
                        
                        <label class="other">Other:</label>
                        <input type="text" class="text" id="other" />
                        
                    </div>
                    <div class="clear"></div>
                </div>
                <!--End .checkType-->
                
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
    </div>
    <!-- End .form -->    
</div>