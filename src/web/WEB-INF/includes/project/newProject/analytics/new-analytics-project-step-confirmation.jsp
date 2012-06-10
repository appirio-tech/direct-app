<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the confirmation step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStepConfirmation" class="hide analytics newProjectStep confirmStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3>Launched</h3>
    </div>
    <!-- End .stepTitle -->   
    
    <div class="stepContainer">
    
        <div class="geryContent">
           
            <!-- top bar -->
            <div class="topBar">
                <dl>
                    <dt>You have successfully created your project</dt>
                    <dd>Though it still needs to be Approved by a Topcoder Admin</dd>
                </dl>
            </div>
            <!-- End .topBar -->
    
            <dl class="content">
                <dt>Steps Already Completed</dt>
                <dd>
                    <span class="num">1</span>
                    <p>Created Project <strong id="confirmProjectName"></strong></p>
                </dd>
                <dt>Steps Yet to be Completed</dt>
                <dd class="multiLine">
                    <span class="num">2</span>
                    <p>Hire a <strong>Copilot</strong>
                    <span>Copilot is a Topcoder member who can manage the entire project until it is completed</span></p>
                </dd>
                <dd class="multiLine">
                    <span class="num">3</span>
                    <p>
                    <strong>Create Gameplan</strong>
                    <span>Gameplan is the project plan where the contest schedules, budget is decided</span></p>
                </dd>
                <dd class="multiLine">
                    <span class="num">4</span>
                    <p>
                    <strong>Create Formulation</strong>
                    <span>Here is where the exact problem is mentioned, Topcoder members will determine the best possible solution for the given problem</span></p>
                </dd>
                <dd class="multiLine">
                    <span class="num">5</span>
                    <p>
                    <strong>Prepare Datasets</strong>
                    <span>Datasets which is involved in the problem will need to be prepared/used here.</span></p>
                </dd>
                <dd class="step6">
                    <span class="num">6</span>
                    <p>
                    <strong>Launch First Contest</strong>
                    </p>
                </dd>
                <dd class="para">
                    Go to your project. Once you're in your project you can do things like monitor status, manage your copilot, run contests...
                </dd>
            </dl>
    
            <!-- bottom bar -->
            <div class="BottomBar">
                <a href="#" class="overviewBtn" id="confirmOverviewLink">Project Overview</a>
            </div>
            <!-- End .BottomBar -->
            
            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>
            
        </div>    
    </div>
    <!-- End #stepContainer -->    
</div>
    
    