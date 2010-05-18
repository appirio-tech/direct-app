<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<h2>Please Select your Contest Type</h2>
<p class="intro">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi commodo, ipsum sed pharetra gravida, orci magna rhon
cus neque, id pulvinar odio lorem non turpis. Nullam sit amet enim.</p>

<div class="tabOut">									                                    
      <!-- tab contest -->
    	<div class="tabContest">
      	
          <!-- selectDesing -->
 	  		<div class="selectDesing">
              <div class="selectX">
                  <div class="selectOut">
                      <select id="contestTypes">
                           <optgroup label="Studio">
                           	<s:iterator value="studioContestTypes">
                           	<option value='STUDIO<s:property value="contestTypeId" />'><s:property value="description" /></option>
                            </s:iterator>
                           </optgroup>														                            
                           <optgroup label="Software">
                           	<option value="SOFTWARE1">Algoritgm</option>
                           	<option value="SOFTWARE1">Conceptualization</option>
                           	<option value="SOFTWARE1">Specification</option>
                           	<option value="SOFTWARE1">Architecture</option>
                           	<option value="SOFTWARE1">Design</option>
                           	<option value="SOFTWARE1">Development</option>
                           	<option value="SOFTWARE1">Assembly</option>
                           	<option value="SOFTWARE1">Test Scenarios</option>
                           	<option value="SOFTWARE1">Test Suites</option>
                           	<option value="SOFTWARE1">UI Prototype</option>
                           	<option value="SOFTWARE1">RIA Build</option>
                           	<option value="SOFTWARE1">Bug Races</option>
                           	<option value="SOFTWARE1">High School</option>
                           	<option value="SOFTWARE1">Marathon Matches</option>
                           	<option value="SOFTWARE1">Tournaments</option>
                           	<option value="SOFTWARE1">Others</option>
                           </optgroup>														                             
                       </select>
                  </div>
                  
                  <div class="help">Contest Type Information</div>
              </div>
              
              <a href="javascript:;" class="moreLink">Learn more about Studio Contest Type</a>
              
        	</div>
          <!-- end .selectDesing -->

          
          <div class="tr"></div>
          <div class="br"></div>
          
      </div>
      <!-- end .tabContest -->
      
  </div>
  <!-- end .tabOut -->
  
  
  <!-- add new contest -->
  <div class="addNewContest">
      	
      <!-- Contest Name -->
      <div class="row">
          <label>Contest Name :</label>
          <input id="contestName" name="contestName" type="text" class="text" />
      </div>
      
      <!-- Project Name -->
      <div class="row">
          <label>Project Name :</label>
          <div class="projectSelect">
              <select id="projects" name="tcProject">
              	<option value="-1">Please select an existing project</option>
                <s:iterator value="projects">
                <option value='<s:property value="projectId" />'><s:property value="name" /></option>
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
              	<option value="-1">Please select an existing account</option>
                <s:iterator value="billingProjects">
                <option value='<s:property value="projectId" />'><s:property value="name" /></option>
                </s:iterator>
             </select>
          </div>
      </div>
      
  </div>
  <!-- end .addNewContest -->
  
  <h3>Contest Schedule:</h3>
 	
  <!-- Contest Schedule --> 
  <div class="schedule">
  	
      <!-- Round Type -->
      <div class="row">
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
          <div class="startSelect">
          	<select><option>The day after tomorrow</option><option>tomorrow</option></select>
          </div>
          <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
          <div class="startEtSelect">
          	<select id="startTime" name="startTime" ><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (GMT-04)</span>
      </div>
      
      <!-- Milestone -->
      <div class="row hide" id="mileStoneDiv">
      	<label>Milestone:</label>
          <div class="milestoneSelect">
          	<select><option>48 hours (2 days) after Start Time</option><option>24 hours (1 days) after Start Time</option></select>
          </div>
          <input  id="milestoneDate" name="milestoneDate"  type="text" class="text date-pick" readonly="true"/>
          <div class="milestoneEtSelect">
          	<select id="milestoneTime" name="milestoneTime"><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (GMT-04)</span>
      </div>
      
      <!-- End -->
      <div class="row">
      	<label>End:</label>
          <div class="endSelect">
          	<select><option>2 days</option><option>1 days</option></select>
          </div>
          <input id="endDate" name="endDate" type="text" class="text date-pick" readonly="true"/>
          <div class="endEtSelect">
          	<select id="endTime" name="endTime"><jsp:include page="../common/timeOptions.jsp"/></select>
          </div>
          <span>ET (GMT-04)</span>
      </div>
      
</div>
<!-- end .schedule -->
  
<div class="bottomButton">
	  <a href="javascript:;" class="conditions">Terms and Conditions</a>                                    
    <a href="javascript:continueContestSelection();" class="button6 contiune "><span class="left"><span class="right">CONTINUE</span></span></a>
    <a href="javascript:saveAsDraftContestSelection();" class="button6 draft"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>                                    
    <a href="javascript:cancelContest();" class="button6 preview"><span class="left"><span class="right">CANCEL</span></span></a>
</div>