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
        <h3><span>3</span>Audience</h3>
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
                         For these types below, select the target audience of your presentation.
                    </dd>
                    <dd>If none of the listed audience types is your target audience, then you can add a new audience type from Custom Audience field.</dd> 
                     
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
                                <td class="labelTd">Customer</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="Customer" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Management</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="Management" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Academic</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="Academic" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">Conference</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="Conference" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelTd">General Public (internet reader)</td>
                                <td>
                                    <div class="selectRadioBox">
                                        <input type="checkbox" name="General Public (internet reader)" class="radio" />
                                    </div>
                                </td>
                            </tr>
                            <tr class="tfooter">
                                <td>
                                    <a href="javascript:;" class="toolTip" id="toolTip3" rel='60 characters max.'></a>
                                    <label>Custom Audience: <span class="red">*</span></label>
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