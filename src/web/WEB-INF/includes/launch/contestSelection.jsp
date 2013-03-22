<%--
  - Author: GreatKevin, bugbuka
  - Version: 1.6
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest selection page.

  - Version 1.0.1 (TC Direct Software Contest Creation Update Assembly) changes: add display of copilots of software contest.
  - Version 1.0.2 (TC Direct Release Bug Fix Assembly) changes: change time zone from GMT-04 to UTC-05.
  - Version 1.0.3 (Release Assembly - Direct Improvements Assembly Release 3) changes: add current date on server to populate start/end date.
  -
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - Studio contest type is rendered by javascript now.
  - - Add support for multi round type for both software contest and studio contest.
  -
  - Version 1.2 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
  - - add the check box "Create Bug Hunt Contest" for assembly contest
  -
  - Version 1.3 (Release Assembly - TopCoder Studio CCA Integration) change notes:
  -   Added CCA support for studio contest.
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit Billing Account Project Association) change notes:
  -   Add link to add billing account to project
  - 
  - Version 1.5 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0) change notes:
  - Fixed a drop down layout issue for a lenghthy project name.  
  -
  - Version 1.6 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) change notes:
  - Update to support launching mm contest. 
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
                           </optgroup>
                           <optgroup label="Software">
                           </optgroup>
                           <optgroup label="Algorithm">
                           </optgroup>
                       </select>
                  </div>

                  <div id="cca" class="lcc_chk" >
                    <input id="lccCheckBox" type="checkbox" />
                    <span class="lcc">Enforce CCA</span>
                    <a href="javascript:;" class="helpme" ><img src="/images/help_ico5.gif" alt="help" /></a>
                  </div>

                  <div id="assembly_bug_hunt" class="bug_hunt_chk hide">
                      <input id="bug_hunt_CheckBox" type="checkbox"/>
                      <span class="bug_hunt">Create Bug Hunt Contest</span>
                      <a href="javascript:;" class="helpme"><img src="/images/help_ico5.gif" alt="help"/></a>
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
             </select>
          </div>
          <a href="javascript:;" target="_blank" class="addBilling hide">Add Billing to project</a>
      </div>

      <!-- Copilot for Software Contest -->
      <div class="row">
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
      <div class="row" id="roundTypeDiv">
        <label>Round Type:</label>
          <div class="roundelect">
              <select id="roundTypes">
                <option value="single">Contest will be run in single-rounds</option>
                <option value="multi">Contest will be run in multi-rounds</option>
              </select>
          </div>
      </div>

      <div id="currentServerDate" style="display:none">
          <s:date name="currentServerDate" format="MM/dd/yyyy"/>
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
      
      <div id="endDateDiv" class="row">
        <label>End:</label>
          <input id="endDate" name="endDate" type="text"  class="text date-pick" readonly="true"/>
          <div class="endEtSelect">
            <select id="endTime" name="endTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (UTC-05)</span>
      </div>

      <!-- Milestone -->
      <div class="row" id="mileStoneDiv">
        <label>Round 1 Duration:</label>
        <div class="milestoneEtSelect">
          <select id="milestoneDateDay" name="milestoneDateDay"><c:forEach var="i" begin="0" end="10"><option value="${i}">${i}</option></c:forEach></select>
        </div>
        <div class="selectSpan"><span>days</span></div>
        <div class="milestoneEtSelect">
          <select id="milestoneDateHour" name="milestoneDateHour"><c:forEach var="i" begin="0" end="23"><option value="${i}">${i}</option></c:forEach></select>
        </div>
        <div class="selectSpan"><span>hours</span></div>
        <div class="clear"></div>
      </div>

      <!-- End -->
      <div class="row studio" id="endDiv">
        <label>Round 2 Duration:</label>
        <div class="endEtSelect">
          <select id="endDateDay" name="endDateDay"><c:forEach var="i" begin="0" end="10"><option value="${i}">${i}</option></c:forEach></select>
        </div>
        <div class="selectSpan"><span>days</span></div>
        <div class="endEtSelect">
          <select id="endDateHour" name="endDateHour"><c:forEach var="i" begin="0" end="23"><option value="${i}">${i}</option></c:forEach></select>
        </div>
        <div class="selectSpan"><span>hours</span></div>
        <div class="clear"></div>
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
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
    This will create a new contest for you and then activate the created contest. 
    Please confirm you want to create the contest and activate it. 
    After activation, you will start the contest specification review.</p>
</div>
