<%--
  - Author: duxiaoyang, GreatKevin
  - Version: 1.2
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 2 of post a copilot.
  -
  - Changes in version 1.1 (TC Cockpit Post a Copilot Assembly 2):
  -   Retrieve copilot types from action and display on the page.
  -
  - Changes in version 1.2 (Release Assembly - TC Direct Cockpit Release Three version 1.0):
  -   Update the top notes in the step 2
  -
  - Since: TC Cockpit Post a Copilot Assembly 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep2 stepDiv">
    <!-- form -->
    <div class="form">
        <div class="noteContainer">
            <p>
                Tell the copilots what type of experience or expertise you are looking for. This is not asking that the
                copilot has developed these types of applications. Instead, you are telling the copilots that you would
                like someone who has experience delivering these types of applications or projects.
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
