<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 1 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.0 (TC Cockpit Post a Copilot Assembly 1).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep1 stepDiv">
    <!-- form -->
    <div class="form">
        
        <div class="noteContainer">
            
           <p>
            In this step you will be directed to fill out basic information about your project. This information will be useful for the copilot as reference material what is needed and done to complete this project. And this information will explain to the copilot what exactly the results you want for this project. Please fill out the form below:
           </p>
        </div>
        
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Project Details</h2>
                </div></div>
            </div><!-- End .title -->
            
            
            <div class="rowItem rowItemFirst">
                <label>Project Name<span class="red">*</span></label>
                
                <select id="projects" name="tcProject">
                    <option value="-1">Please select an existing project</option>
                    <s:iterator value="projects" var="proj">
                    <option value='<s:property value="projectId" />'  <s:if test="%{#proj.projectId==#session.currentProject.id}">selected="selected"</s:if> ><s:property value="name"/>
                    </option>
                    </s:iterator>
                </select>
                <a href="javascript:;" class="button6" id="addNewProject"><span class="left"><span class="right">ADD NEW</span></span></a>
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row">
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Copilot Opportunity Details</h2>
                </div></div>
            </div><!-- End .title -->
            
            <span class="attention">Please feel free to tell about your project in this section. This information will be valuable material for the copilot.</span>
            <div class="rowItem">
                <p>
                    <label>Copilot Opportunity Title<span class="red">*</span></label>
                    <input id="contestName" name="contestName" type="text" class="text" maxlength="254" value=""/>
                </p>
                
                <p>
                    <label>Public Description</label>
                    <textarea rows="10" cols="10" id="allDescription"></textarea>
                    
                    <span class="tips">Enter a description that you want everyone to see</span>
                </p>
                
                <p>
                    <label>Specific Description</label>
                    <textarea rows="10" cols="10" id="privateDescription"></textarea>
                    
                    <span class="tips">Enter a description that is only viewable to copilots that register for this posting</span>
                </p>
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row uploadContent copilotFileUploadDiv">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Document Upload</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                <p>
                    <label>Select Document</label>
                    <span class="customUploadWrap">
                        <input type="file" id="fileUpload" class="customUpload" name="document"/>
                        <input type="text" readonly="readonly" class="text uploadInput">
                        <a href="javascript:;" class="draft button6"><span class="left"><span class="right">BROWSE</span></span></a>
                    </span>
                    <a href="javascript:;" class="button6 uploadBtnRed"><span class="left"><span class="right">UPLOAD</span></span></a>
                    <a href="javascript:;" class="addButton"></a>
                    <a href="javascript:;" class="removeButton hide"></a>
                </p>
            </div>
            <!--End .rowItem-->
            
            <div class="hide uploadCopySource">
                <p>
                    <label>Select Document</label>
                    <span class="customUploadWrap">
                        <input type="file" id="fileUpload" class="customUpload" name="document"/>
                        <input type="text" readonly="readonly" class="text uploadInput">
                        <a href="javascript:;" class="draft button6"><span class="left"><span class="right">BROWSE</span></span></a>
                    </span>
                    <a href="javascript:;" class="button6 uploadBtnRed"><span class="left"><span class="right">UPLOAD</span></span></a>
                    <a href="javascript:;" class="addButton"></a>
                    <a href="javascript:;" class="removeButton hide"></a>
                </p>
            </div>
            
        </div>
        <!-- End .row -->
        
    </div>
    <!-- End .form -->

</div>
