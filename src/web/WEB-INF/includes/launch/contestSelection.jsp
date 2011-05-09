<%--
  - Author: TCSASSEMBLER
  - Version: 1.0.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0.1 (TC Direct Software Contest Creation Update Assembly) changes: add display of copilots of software contest.
  - Version 1.0.2 (TC Direct Release Bug Fix Assembly) changes: change time zone from GMT-04 to UTC-05.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<h2>Please Select your Contest Type</h2>

<div class="tabOut">
      <!-- tab contest -->
      <div class="tabContest">

          <!-- selectDesing -->
         <div class="selectDesing" style="padding-left:14px;">
              <div class="selectX">
                  <div class="selectOut">
                      <select id="contestTypes">
                           <optgroup label="Studio">
                             <s:iterator value="studioContestTypes">
                             <option value='STUDIO<s:property value="contestTypeId" />'><s:property value="description" /></option>
                            </s:iterator>
                           </optgroup>
                           <optgroup label="Software">
                           </optgroup>
                       </select>
                  </div>

                  <div id="cca" class="lcc_chk software hide" >
                    <input id="lccCheckBox" type="checkbox" />
                    <span class="lcc">Enforce CCA</span>
                    <a href="javascript:;" class="helpme" ><img src="/images/help_ico5.gif" alt="help" /></a>
                  </div>

                  <div class="help"><span class="new_style">Contest Type Information</span></div>
              </div>
          </div>
          <!-- end .selectDesing -->


          <div class="tr"></div>
          <div class="br"></div>

      </div>
      <!-- end .tabContest -->

  </div>
  <!-- end .tabOut -->

 <!-- this is a fake form which is used to reset onload to prevent firefox from caching local drop-down selection-->
  <form id="fakeForm">
  <!-- add new contest -->
  <div class="addNewContest">

      <!-- Contest Name -->
      <div class="row">
          <label>Contest Name :</label>
          <input id="contestName" name="contestName" type="text" class="text" maxlength="254"/>
          <input id="contestNameFromDesign" name="contestNameFromDesign" type="text" class="text hide" maxlength="300" />          
          <input id="contestIdFromDesign" name="contestIdFromDesign" type="hidden" />
          <div id="devOnlyDiv" class="lcc_chk hide" style="float:none;">
            <input id="devOnlyCheckBox" type="checkbox" />
            <span class="lcc">Dev Only</span>
          </div>          
      </div>

      <!-- Project Name -->
      <div class="row">
          <label>Project Name :</label>
          <div class="projectSelect">
              <select id="projects" name="tcProject">
                <option value="-1">Please select an existing project</option>
                <s:iterator value="projects" var="proj">
                <option value='<s:property value="projectId" />'  <s:if test="%{#proj.projectId==#session.currentProject.id}">selected="selected"</s:if> ><s:property value="name" /></option>
                </s:iterator>
              </select>
          </div>
          <div class="addNew">
              <a href="javascript:;" class="button6" id="addNewProject"><span class="left"><span class="right">ADD NEW</span></span></a>
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

      <!-- Copilot for Software Contest -->
      <div class="row software hide">
          <label>Copilot :</label>
          <div class="copilotSelect">
              <select id="contestCopilot" name="contestCopilot">
                <option value="0">Unassigned</option>
                <s:iterator value="currentProjectCopilots" var="copilot">
                    <option value='<s:property value="userId"/>'  <s:if test="%{#copilot.userId==#session.user.userId}">selected='selected'</s:if> > <s:property value="handle" /></option>
                </s:iterator>
             </select>
          </div>
      </div>

  </div>
  <!-- end .addNewContest -->
 </form>

  <h3><span class="icon">Contest Schedule:</span><div id="ContestScheduleHelpIcon"><a class="helpIcon" href="javascript:;"><span class="hide">Help</span></a></div></h3>

  <!-- Contest Schedule -->
  <div class="schedule">

      <!-- Round Type -->
      <div class="row studio" id="roundTypeDiv">
        <label>Round Type:</label>
          <div class="roundelect">
              <select id="roundTypes">
                <option value="single">Contest will be run in single-rounds</option>
                <option value="multi">Contest will be run in multi-rounds</option>
              </select>
          </div>
      </div>

      <!-- Start -->
      <div class="row">
        <label>Start:</label>
          <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
          <div class="startEtSelect">
            <select id="startTime" name="startTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (UTC-05)</span>
      </div>

      <!-- Milestone -->
      <div class="row studio" id="mileStoneDiv">
        <label>Milestone:</label>
          <input  id="milestoneDate" name="milestoneDate"  type="text" class="text date-pick" readonly="true"/>
          <div class="milestoneEtSelect">
            <select id="milestoneTime" name="milestoneTime"><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (UTC-05)</span>
      </div>

      <!-- End -->
      <div class="row studio">
        <label>End:</label>
          <input id="endDate" name="endDate" type="text" class="text date-pick" readonly="true"/>
          <div class="endEtSelect">
            <select id="endTime" name="endTime"><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (UTC-05)</span>
      </div>

</div>
<!-- end .schedule -->

<div class="bottomButton">
    <link:conditions/>
    <a href="javascript:continueContestSelection();" class="button6 contiune "><span class="left"><span class="right">CONTINUE</span></span></a>
    <a href="javascript:saveAsDraftContestSelection();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
    <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
</div>

<div id="activateContestConfirmation" title="Do you really want to activate the contest?" style="display:none;">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This will create a new contest for you and then activate the created contest. Please confirm you want to create the contest and activate it?</p>
</div>
