<%--
  - Author: isv
  - Version: 1.0.5
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: Launch copilot contest page input/edit section.
  - Since: TC Direct - Launch Copilot Selection Contest assembly  
  -
  - - Version 1.0.2 (Direct Improvements Assembly Release 1 ) Change notes:
  - - Fix bug TCCC-2900.
  - - Add support to allow setting custom contest prizes.
  -
  - Version 1.0.2 (TC Direct Release Bug Fix Assembly) changes: change time zone from GMT-04 to UTC-05.
  - Version 1.0.3 (Direct Improvements Assembly Release 2) changes: Add preview button.
  - Version 1.0.4 (Release Assembly - TC Direct UI Improvement Assembly 3) changes: Fix typo and hide activation message.
  - Version 1.0.5 (Release Assembly - Contest Edit and Upload Update) changes: added text on file size limit.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!-- add new contest -->
<div class="addNewContestSection">
    <div class="addNewContest">
        <div class="editPanelMask">
            <a class="hide" href="javascript:;" ><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a>
            <!-- Project Name -->
            <div class="row">
            <label>Select the project that needs a Copilot :</label>
                <div class="projectSelect">
                    <select id="projects" name="tcProject">
                        <option value="-1">Please select an existing project</option>
                        <s:iterator value="projects" var="proj">
                        <option value='<s:property value="projectId" />'  <s:if test="%{#proj.projectId==#session.currentProject.id}">selected="selected"</s:if> ><s:property value="name"/>
                        </option>
                        </s:iterator>
                    </select>
                </div>
                <div class="addNew">
                    <a href="javascript:;" class="button6" id="addNewProject"><span class="left"><span class="right">ADD NEW</span></span></a>
                </div>
            </div>
            
            <!-- Contest Name -->
            <div class="row">
                <label>Create a name for your Copilot Posting :</label>
                <input id="contestName" name="contestName" type="text" class="text" maxlength="254"/>
                <input id="contestNameFromDesign" name="contestNameFromDesign" type="text" class="text hide" maxlength="300" />          
                <input id="contestIdFromDesign" name="contestIdFromDesign" type="hidden" />
                <div id="devOnlyDiv" class="lcc_chk hide" style="float:none;">
                    <input id="devOnlyCheckBox" type="checkbox" />
                    <span class="lcc">Dev Only</span>
                </div>
            </div>
            
            <!-- Billing Account -->
            <div class="row">
                <label>Billing Account :</label>
                <div class="billingSelect">
                    <select id="billingProjects" name="billingProject">
                        <option value="0">Please select an existing account</option>
                        <s:iterator value="billingProjects">
                        <option value='<s:property value="projectId" />'><s:property value="name" /></option>
                        </s:iterator>
                    </select>
                </div>
            </div>    
            
            <p class="save hide"> 
                <a class="cancel_text" href="javascript:;">cancel</a>
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p>
        </div>
    </div>
    
    <div class="addNewContestInfo infoPanel hide">
        <div class="infoPanelMask">
            <ul>
                <li>
                    <label>Copilot Posting Name :</label>
                    <label id="copilotPostingNameEdit" class="textValue"></label>
                </li>
                <li>
                    <label>Project Name :</label>
                    <label id="projectNameEdit" class="textValue"></label>
                </li>
                <li>
                    <label>Billing Account :</label>
                    <label id="billingAccountEdit" class="textValue"></label>
                </li>
            </ul>
            <a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a>
        </div> 
    </div>
</div>
<!-- end .addNewContest -->

<div class="scheduleSection">
    <div class="iconDivToHide">
        <h3><span class="icon">Schedule</span></h3>    
    </div>
    <!-- Contest Schedule --> 
    <div class="schedule">
        <div class="iconDiv hide">
            <h3><span class="icon">Schedule</span>
            <a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a></h3>
        </div>
        <div class="editPanelMask">
            <!-- Start -->
            <div class="row">
			<jsp:useBean id="currentDate" class="java.util.Date" />
            <input id="currentCopilotDate" type="hidden" value='<fmt:formatDate value="${currentDate}" type="date" pattern="MM/dd/yyyy" />' />
            <label>Start:</label>
                <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
                <div class="startEtSelect">
                    <select id="startTime" name="startTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
                </div>
                <span>ET (UTC-05)</span>
            </div>
            <p class="save hide">
                <a class="cancel_text" href="javascript:;">cancel</a>
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p>            
        </div>
    </div>
    
    <div class="infoPanel scheduleInfo hide"> 
        <h3><span class="icon">Schedule</span><a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a></h3>
        <div class="infoPanelMask">
            <ul>
                <li>
                    <label>Start :</label> 
                    <label id="startDateEdit" class="textValue"></label>
                </li>
            </ul>
        </div>    
    </div>
</div>
<!-- end .schedule -->

<!-- Contest Description -->
<div class="allDescriptionSection">
    <div class="iconDivToHide">
        <h3><span class="icon">Enter a description that you want everyone to see</span><a href="javascript:;" class="helpIcon tooltipLink" id="allDescriptionIcon"><span class="hide">Help</span></a></h3>
    </div>
    <div class="description"> 
        <div class="iconDiv hide">
            <h3><span class="icon">Enter a description that you want everyone to see</span><a href="javascript:;" ><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a></h3>
        </div>
        <div class="editPanelMask">
            <div class="textarea">
               <textarea rows="15" cols="80" style="width:100%;" id="allDescription"></textarea>
            </div> 
            
            <p class="save hide">
                <a class="cancel_text" href="javascript:;">cancel</a>
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p>            
        </div>
    </div>
    
     <div class="descriptionInfo hide htmlDescription">
            <h3><span class="icon">Description that you want everyone to see</span><a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a></h3>
            <div class="infoPanelMask">
                <p id="allDescriptionEdit"></p>
            </div> 
     </div>    
</div>
<!-- end .description -->

<!-- Contest Description -->
<div class="privateDescriptionSection">
    <div class="iconDivToHide">
        <h3><span class="icon">Enter a description that is only viewable to copilots that register for this posting</span><a href="javascript:;" class="helpIcon tooltipLink" id="privateDescriptionIcon"><span class="hide">Help</span></a></h3>
    </div>
    <div class="description"> 
        <div class="iconDiv hide">
            <h3><span class="icon">Enter a description that is only viewable to copilots that register for this posting</span><a href="javascript:;" ><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a></h3>
        </div>   
        <div class="editPanelMask">        
            <div class="textarea">
                <textarea rows="15" cols="80" style="width:100%;" id="privateDescription"></textarea>
            </div> 
            
            <p class="save hide">
                <a class="cancel_text" href="javascript:;">cancel</a>
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p>         
        </div>
    </div>
    
    <div class="htmlDescription descriptionInfo hide">
            <h3><span class="icon">Description that is only viewable to copilots that register for this posting</span><a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a></h3>
            <div class="infoPanelMask">
                <p id="privateDescriptionEdit"></p>
            </div> 
     </div>     
</div>
<!-- end .description -->

 <!-- Contest Costs -->
<div class="costs">
    <div class="iconDivToHide">
        <h3><span class="icon">Costs</span><a href="javascript:;" class="helpIcon tooltipLink" id="costIcon"><span class="hide">Help</span></a></h3>
    </div>
    <div class="description">
        <div class="iconDiv hide">
            <h3><span class="icon">Costs</span><a href="javascript:;" ><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a></h3>
        </div>
        <div class="prizesInner_software editPanelMask" style="height:auto;">
            <span class="head"><p>Please Select the prize structure for your challenge by choosing one of the options below:</p></span> 
            <p>
                <br/>
                  <span class="radio_font">
                      <input type="radio" name="prizeRadio" value="low" checked/>&nbsp;&nbsp;&nbsp;Low&nbsp;&nbsp;($<span id="swPrize_low"></span>)
                    <input type="radio" name="prizeRadio" value="medium" class="space_radio" />&nbsp;&nbsp;&nbsp;Medium&nbsp;&nbsp;($<span id="swPrize_medium"></span>)
                    <input type="radio" name="prizeRadio" value="high" class="space_radio" />&nbsp;&nbsp;&nbsp;High&nbsp;&nbsp;($<span id="swPrize_high"></span>)
                    <input type="radio" name="prizeRadio" value="custom" class="space_radio customRadio" /> <span class="customRadio"> &nbsp;&nbsp;&nbsp; Custom </span>
                  </span>
            </p>
            <br/>
            <table class="prizesTable">
                <tbody><tr>
                    <td class="prize"><span>Administration Fee:</span> $ <span id="swContestFee" class="feeValue"></span> </td>
                    <td class="prize"><span>1st Prize :</span> $ <input type="text" class="prizesInput" value="" id="swFirstPlace" readonly="true" /></td>
                    <td class="prize"><span>2nd Prize :</span> $ <span id="swSecondPlace" class="prizeInfo"></span></td>
                    <td class="total">Total</td>
                    <td class="last" style="width:110px;">$ <span id="swTotal"></span></td>
                </tr></tbody>
            </table>

            <p class="save hide">
                <a class="cancel_text" href="javascript:;">cancel</a>
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p>         
        </div>
    </div>

    <div class="infoPanel descriptionInfo hide">
        <h3><span class="icon">Challenge Prizes</span><a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a></h3>
        <div class="infoPanelMask">
            <table class="prizesTable">
            <tbody><tr>
                <td class="prize"><span>Administration Fee:</span> $ <span id="sworContestFee" class="feeValue"></span> </td>
                <td class="prize"><span>1st Prize :</span> $ <span id="sworFirstPlace" class="feeValue"></span></td>
                <td class="prize"><span>2nd Prize :</span> $ <span id="sworSecondPlace" class="feeValue"></span></td>
                <td class="total">Total</td>
                <td class="last" style="width:110px;">$ <span id="sworTotal" class="feeValue"></span></td>
            </tr></tbody>
            </table>
        </div>
    </div>
</div>
<!-- end .Costs -->

<!-- upload -->
<div class="uploadSection">
    <div class="iconDivToHide">
        <h3><span class="icon">File Upload</span></h3>
    </div>    
    <div id="swUploadSection" class="fileupload">
        <div class="iconDiv hide">
            <h3><span class="icon">File Upload</span><a href="javascript:;" ><img class="edit_type" alt="edit" src="/images/edit_red.png" /></a></h3>
        </div>         
        <div class="editPanelMask">        
            <div class="uploadInner">
                <table id="swDocumentList" style="width:100%">
                </table>
                <div>
                   File to Upload (20MB maximum): <span id="swUploadButtonDiv"><input name="document" type="file" > </span> <br/>
                   File Description:
                   <textarea id="swFileDescription" rows="5" cols="50"></textarea>               
                   <input id="swFileUploadBtn"  type="button" value="Upload File" /> <br/>
                </div>
            </div> <!-- end .uploadInner -->

            <table id="swFileTemplte" class="hide">
                <tr id="doc{0}" class="document">
                    <td style="width:40%"><span class="fileInput">{1}</span></td>
                    <td style="width:40%"><span class="fileInput">{2}</span></td>
                    <td style="width:20%"><a href="javascript:swRemoveDocument({0});" >remove</a> </td>
                </tr>
            </table>
            
            <p class="save hide">
                <a href="javascript:;"><img class="save_btn" alt="save" src="/images/save_change.png" /></a>
            </p> 
        </div>       
    </div>
    
    <div class="infoPanel fileuploadInfo hide">
         <h3><span class="icon">File Upload</span><a href="javascript:;" class="editLink"><img class="edit_type" alt="edit" src="/images/edit-icon.png" /></a></h3>
         <div class="infoPanelMask">   
            <table id="fileUploadTableEdit">
            </table> 
        </div>     
    </div>     
</div>
<!-- end #uploadSection -->

<div class="bottomButton"> 
        <a class="conditions thickbox" href="/direct/conditions.jsp?height=400&amp;width=800&amp;inlineId=helpPopup">Terms and Conditions</a>
        <a id="continue" href="javascript:;" class="button6 contiune"><span class="left"><span class="right">CONTINUE</span></span></a>
        <a id="submitButton" href="javascript:;" class="button6 contiune hide"><span class="left"><span class="right">SUBMIT &amp; LAUNCH CHALLENGE</span></span></a>
        <a id="saveDraftButton" href="javascript:;" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a> 
		<a id="previewButton" href="javascript:;" class="button6 preview hide"><span class="left"><span class="right">PREVIEW</span></span></a>
</div>

<div id="activateContestConfirmation" title="Do you want to activate the challenge?" class='hide'>
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This will create a new copilot posting and activate it. Please confirm to continue?</p>
</div>
