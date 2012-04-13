<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 1 of post a copilot.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - - Update the words used in the step 1 and add new tips for project name and file uploading.
  -
  - Since: TC Cockpit Post a Copilot Assembly 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep1 stepDiv">
    <!-- form -->
    <div class="form">
        
        <div class="noteContainer">
            
           <p>
               In this step you will fill out basic information about your project. Your goal here is to portray your needs to the copilots.
               You don't need to be overly detailed (you can always expand on details in the forum), but rather focus on initiating a comfortable dialog with the copilots.
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
            <div class="projectNameTips">Select an existing project or add a new one. The copilot opportunity you are about to create will be placed in this project.
            Your Project Name is NOT visible to the community.</div>
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
            
            <div class="rowItem">
                <p>
                    <label>Copilot Opportunity Title<span class="red">*</span></label>
                    <input id="contestName" name="contestName" type="text" class="text" maxlength="254" value=""/>
                </p>
                
                <p>
                    <label>Public Description<span class="red">*</span></label>
                    <textarea rows="10" cols="10" id="allDescription"></textarea>
                    
                    <span class="tips">All TopCoder Community members will be able to see this description. It should be a general overview of our project.
                        Try to capture your basic goals <br/> and needs within 1-2 paragraphs. Do not put any confidential or sensitive information in this section.</span>
                </p>
                
                <p>
                    <label>Private Description</label>
                    <textarea rows="10" cols="10" id="privateDescription"></textarea>
                    
                    <span class="tips">Only <a target="_blank" href='<s:url action="selectFromCopilotPool"/>'>copilots</a> that register for this Copilot Opportunity will see this description.
                        If you have information that you only want to make available to <br/> the copilot community then you should enter that here.</span>
                </p>
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row uploadContent copilotFileUploadDiv">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Attach Files</h2>
                </div></div>
            </div><!-- End .title -->
            <span class="attention">Files you upload will be available as downloads to the copilots that register for this opportunity.</span>
            <div class="rowItem">
                <p>
                    <label>Select Document</label>
                    <span class="customUploadWrap">
                        <input type="file" id="fileUpload" class="customUpload" name="document"/>
                        <input type="text" readonly="readonly" class="text uploadInput">
                        <a href="javascript:;" class="draft button6"><span class="left"><span class="right">BROWSE</span></span></a>
                    </span>
				</p>
				<p>
					<label>Description</label>
                    <span>
                        <input type="text" id="fileDescription" class="text" name="fileDescription"/>
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
				</p>
				<p>
					<label>Description</label>
                    <span>
                        <input type="text" id="fileDescription" class="text" name="fileDescription"/>
                    </span>
                    <a href="javascript:;" class="button6 uploadBtnRed"><span class="left"><span class="right">UPLOAD</span></span></a>
                    <a href="javascript:;" class="addButton"></a>
                    <a href="javascript:;" class="removeButton"></a>
                </p>
            </div>
            
        </div>
        <!-- End .row -->
        
    </div>
    <!-- End .form -->

</div>
