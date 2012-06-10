<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 7th step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep7" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>7</span>Review</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->   
    
    <div class="stepContainer">
    
        <div class="geryContent">
            
            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:" class="prevStepButton">PREV STEP</a>
            </div>
            <!-- End .topBar -->
    
            <div class="step8">
                <div class="title">
                    <h4>Name and Summary of the Project</h4>
                    <a href="javascript:goAnalyticsProjectStep(0);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">Project Name</div></div>
                        <div class="right"><div class="rightMask" id="reviewProjectName"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Summary</div></div>
                        <div class="right"><div class="rightMask" id="reviewProjectDesc"></div></div>
                    </div>
                </div>
                <div class="title">
                    <h4>Budget and Timeline</h4>
                    <a href="javascript:goAnalyticsProjectStep(1);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">Budget</div></div>
                        <div class="right"><div class="rightMask" id="reviewBudget"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Timeline</div></div>
                        <div class="right"><div class="rightMask" id="reviewTimeline"></div></div>
                    </div>
                </div>
                
                <div class="title">
                    <h4>Would you like a copilot for this project</h4>
                    <a href="javascript:goAnalyticsProjectStep(2);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">Do you require a Copilot for your Project?</div></div>
                        <div class="right"><div class="rightMask" id="reviewCopilotYesNo"></div></div>
                    </div>
                    <div class="row" id="reviewCopilotsDiv">
                        <div class="left"><div class="leftMask">You've chosen the copilot(s) for your project.</div></div>
                        <div class="right"><div class="rightMask" id="reviewCopilots"></div></div>
                    </div>
                    <div class="row" id="reviewCreateCopilotDiv">
                        <div class="left"><div class="leftMask">You've selected "Create a copilot posting" to select copilot.</div></div>
                        <div class="right"><div class="rightMask">Yes</div></div>
                    </div>
                </div>
                
                <div class="title">
                    <h4>About The Problem</h4>
                    <a href="javascript:goAnalyticsProjectStep(3);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">Please provide a high level overview of the problem that needs to be solved.</div></div>
                        <div class="right"><div class="rightMask" id="reviewOverview"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Please list the metrics that are essential to evaluate the quality of a solution to the described problem. If any of these metrics can't be computed automatically (for example because they are subjective), please point this out.</div></div>
                        <div class="right"><div class="rightMask" id="reviewMetrics"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Please list the fields of knowledge that one needs to have a deep knowledge of in order to be successful in solving the problem. If any of those fields do not belong to Programming, Computer Science or Math, please point this out.</div></div>
                        <div class="right"><div class="rightMask" id="reviewFields"></div></div>
                    </div> 
                </div>
                <div class="title">
                    <h4>About The Problem Continued</h4>
                    <a href="javascript:goAnalyticsProjectStep(4);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">Do you have or can you create a relevant test data in order to evaluate performance of solutions for your problem?</div></div>
                        <div class="right"><div class="rightMask" id="reviewTestDataYesNo"></div></div>
                    </div>
                    <div class="row" id="reviewTestDataSizeDiv">
                        <div class="left"><div class="leftMask">Please provide an expected size of all test data.</div></div>
                        <div class="right"><div class="rightMask" id="reviewTestDataSize"></div></div>
                    </div>
                    <div class="row" id="reviewTestDataDifficultDiv">
                        <div class="left"><div class="leftMask">Please clarify why it is difficult to get a proper test data.</div></div>
                        <div class="right"><div class="rightMask" id="reviewTestDataDifficult"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Is amount of used memory essential in this problem?</div></div>
                        <div class="right"><div class="rightMask" id="reviewMemoryYesNo"></div></div>
                    </div>
                    <div class="row" id="reviewMemoryMaxDiv">
                        <div class="left"><div class="leftMask">What maximum amount of memory would be acceptable?</div></div>
                        <div class="right"><div class="rightMask" id="reviewMemoryMax"></div></div>
                    </div>
                    <div class="row" id="reviewMemoryProperDiv">
                        <div class="left"><div class="leftMask">How much memory do you think it is necessary in order to obtain proper results?</div></div>
                        <div class="right"><div class="rightMask" id="reviewMemoryProper"></div></div>
                    </div>
                    
                    <div class="row">
                        <div class="left"><div class="leftMask">Is runtime speed essential in this problem?</div></div>
                        <div class="right"><div class="rightMask" id="reviewSpeedYesNo"></div></div>
                    </div>
                    <div class="row" id="reviewSpeedMaxDiv">
                        <div class="left"><div class="leftMask">What maximum execution time would be acceptable (assuming a single threaded solution and average modern PC)?</div></div>
                        <div class="right"><div class="rightMask" id="reviewSpeedMax"></div></div>
                    </div>
                    <div class="row" id="reviewSpeedReasonableDiv">
                        <div class="left"><div class="leftMask">How much execution time do you think should be allowed for a solution in order to obtain reasonable results?</div></div>
                        <div class="right"><div class="rightMask" id="reviewSpeedReasonable"></div></div>
                    </div>                   
                    
                    <div class="row">
                        <div class="left"><div class="leftMask">What type of solution is Appropriate?</div></div>
                        <div class="right"><div class="rightMask" id="reviewSolution"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Languages you would like to allow for your competition?</div></div>
                        <div class="right"><div class="rightMask" id="reviewLanguages"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">How do you prefer to integrate the winning solution?</div></div>
                        <div class="right"><div class="rightMask" id="reviewIntegrate"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Do you have any current solution of the problem described?</div></div>
                        <div class="right"><div class="rightMask" id="reviewCurrentYesNo"></div></div>
                    </div>
                    <div class="row" id="reviewCurrentInfoDiv">
                        <div class="left"><div class="leftMask">Please provide its high level overview and information on its performance.</div></div>
                        <div class="right"><div class="rightMask" id="reviewCurrentInfo"></div></div>
                    </div>
                </div>
                <div class="title">
                    <h4>Other Details and Documents</h4>
                    <a href="javascript:goAnalyticsProjectStep(5);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="left"><div class="leftMask">If there are any other details that you think are relevant but  are not covered by any questions in the previous steps, please list them here.</div></div>
                        <div class="right"><div class="rightMask" id="reviewOtherDetails"></div></div>
                    </div>
                    <div class="row">
                        <div class="left"><div class="leftMask">Additional Documents</div></div>
                        <div class="right"><div class="rightMask" id="reviewDocuments">
                            <table cellpadding="0" cellspacing="0">
                                <colgroup>
                                    <col width="35%" />
                                    <col width="65%" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>Source</th>        
                                        <th>Data</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div></div> 
                    </div>
                </div>
                
                <div class="title">
                    <h4>Who can access your Project</h4>
                    <a href="javascript:goAnalyticsProjectStep(6);" class="grayBtn">Make Changes</a>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="rightMask" id="reviewUsers">
                            <table cellpadding="0" cellspacing="0">
                                <colgroup>
                                    <col width="40%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>User Lists</th>        
                                        <th>Read</th>
                                        <th>Write</th>        
                                        <th>Full Access</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div> 
                </div>
            </div>
            
            <!-- bottom bar -->
            <div class="BottomBar">
                <a href="javascript:" class="prevStepButton">PREV STEP</a>
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
    
    <div class="launchBox">
    <a href="javascript:;" class="launchBtn">Confirm and Launch</a>
    <div class="proceedMsg">
        <p>Now we are going to create and setup your new project, do you want to proceed?</p>
        <a href="javascript:" class="smallRedBtn no">NO</a>
        <a href="javascript:" class="smallRedBtn yes">YES</a> 
    </div>
    </div>
    
</div>
    
    