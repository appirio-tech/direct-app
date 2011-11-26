<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new presentation project step 3.
  -
  - Version 1.0
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPPTProjectStep3" class="hide newProjectStep">
    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>Identify the audience for your presentation.</h3>
         <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step ppt third -->
    <div class="stepThird stepContainer">
    
        <div class="geryContent">
            
            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->
            
            <!-- .noteMask -->
            <div class="noteMask">
                <dl>
                    <dt>
                        <strong>Note:</strong>
                        <a href="javascript:void(0)">Hide</a>
                    </dt>

                    <dd class="first">
                         Please identify your target audience accurately so that we can take
a suitable approach to styling and phrasing your presentation.
                    </dd>
                    <dd>You may select multiple audience types if you expect to have a mixed
audience with substantial components of each type.</dd>
                    <dd>For the greatest possible precision, select the Custom Audience type
and provide a detailed description of your audience.</dd>
                     
                </dl>
            </div><!-- End .noteMask -->

            <!-- form -->
            <div class="form speciealForm"> 
                
                <div class="tableWrapper">
                    <div class="topNotice">
                        <div class="noticeLeft"><em class="redError">At least, one audience type must be selected</em></div>
                        <div class="noticeRight">All fields marked with <span class="red">*</span> are mandatory</div>
                    </div>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th class="firstTh">Audience</th>
                                <th class="lastTh">Option</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="labelTd">Enterprise business customer</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="EnterpriseBusinessCustomer" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Small or medium business customer</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="SMBusinessCustomer" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Prospective home customer (consumer)</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="ProspectiveHomeCustomer" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Unqualified general audience (web traffic)</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="GeneralAudience" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Executive business meeting</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="ExecutiveBusinessMeeting" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Project-level business meeting</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="ProjectBusinessMeeting" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">All-hands business meeting</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="AllHandsBusinessMeeting" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Business conference</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="BusinessConference" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Academic conference</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="AcademicConference" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">TechnicalConference</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="TechnicalConference" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr class="tfooter">
                                <td>
                                    <label>Custom audience: <span class="red">be as specific as you can</span></label>
                                </td>
                                <td>
                                    <div class="addMoreButtonBox">
                                        <div class="errorStatusTips">This field cannot be left empty.</div>
                                        <div class="errorNameExist">New Custom Audience has been existed.</div>
                                        <input type="text" class="text" />
                                        <div class="errorStatus"><div></div></div>
                                        <a href="javascript:;" class="addMoreButton button12"><span class="btnR"><span class="btnC">Add More</span></span></a>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                
            </div>
            <!-- End .form -->
            
            <!-- bottom bar -->
            <div class="BottomBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .BottomBar -->
            
            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>
        </div>
    </div><!-- End .stepThird -->
</div>