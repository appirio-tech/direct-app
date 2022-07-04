<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 3rd step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  -             change notes: Update button text to uppercase.
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newMobileProjectStep3" class="hide mobile newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>Hardware support</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFirst2 stepThird2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->

            <div class="left">
                <div class="rightBorder">
                    <!-- row -->
                    <div class="row formats">
                        <a href="javascript:;" class="toolTip" rel='Will your app to respond to motion events?'></a>
                        <span class="label question9"></span>
                        <span class="errorIcon hide"></span>
                        <div class="clear"></div>
                        <p class="message">
                            <span class="errorText">Please choose at least 1 option.</span>
                        </p>
                        <label class="select2 questionOption32"><input id="motionSensingNotSure" checked="checked" type="checkbox" name="motionSel"/></label>
                        <div class="clear"></div>
                        <div class="icons motion"></div>
                        <label class="questionOption33"><input id="tilt" type="checkbox" name="motion"/></label>
                        <label class="questionOption34"><input id="shake" type="checkbox" name="motion"/></label>
                        <div class="clear"></div>
                        <label class="last questionOption35"><input id="otherMotionSensing" value="other" type="checkbox" name="motion" class="other"/></label>
                        <span class="input">
                            <input id="otherMotionSensingVal" disabled="disabled" name="motionOtherText" class="waterMark motion" title="other" type="text" value="other"/>
                            <span class="errorIcon"></span>
                        </span>
                        <div class="clear"></div>
                        <p class="message padding">
                            <span class="errorText">Please specify the setting.</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="left"><div class="rightBorder">
                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='Will your app to respond to Geolocation events?'></a>
                    <span class="label audience question10"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2 questionOption36"><input id="geolocationNotSure" checked="checked" type="checkbox" name="geoSel"/></label>
                    <div class="clear"></div>
                    <div class="icons geolocation"></div>
                    <label class="questionOption37"><input id="cellularNetwork" type="checkbox" name="geo"/></label><br/>
                    <span class="desk questionOption37"></span>
                    <label class="questionOption38"><input id="wifiTriangulation" type="checkbox" name="geo"/></label><br/>
                    <span class="desk questionOption38"></span>
                    <label class="questionOption39"><input id="gps" type="checkbox" name="geo"/></label><br/>
                    <span class="desk questionOption39"></span>
                </div>
            </div></div>
            <div class="left"><div class="rightBorder">

                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='Will your app to respond to Camera events?'></a>
                    <span class="label purpose question11"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2 questionOption40"><input id="cameraNotSure" checked="checked" type="checkbox" name="cameraSel"/></label>
                    <div class="clear"></div>
                    <div class="icons camera"></div>
                    <label class="questionOption41"><input id="stillPhotos" type="checkbox" name="camera"/></label>
                    <label class="questionOption42"><input id="videoCapture" type="checkbox" name="camera"/></label>
                    <label class="questionOption43"><input id="objectDetection" type="checkbox" name="camera"/></label>
                    <div class="clear"></div>
                    <label class="last questionOption44"><input id="otherCamera" class="other" value="other" type="checkbox" name="camera"/></label>
                    <span class="input">
                        <input id="otherCameraVal" disabled="disabled" name="cameraOtherText" class="waterMark purpose" title="other" type="text" value="other"/>
                            <span class="errorIcon"></span>
                        </span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">Please specify the setting.</span>
                    </p>
                </div>
            </div></div>
            <div class="left last"><div class="rightBorder">

                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='Will your app to respond to Additional features?'></a>
                    <span class="label style question12"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2 questionOption45"><input id="additionalFeatureNotSure" checked="checked" type="checkbox" name="aditionalSel"/></label>
                    <div class="clear"></div>
                    <div class="icons additionalFeature"></div>
                    <label class="questionOption46"><input id="audioRecording" type="checkbox" name="aditional"/></label>
                    <label class="questionOption47"><input id="phoneCall" type="checkbox" name="aditional"/></label>
                    <div class="clear"></div>
                    <label class="last"><input class="other" value="other" type="checkbox" name="aditional"/><span>&nbsp;</span></label>
                    <span class="input">
                        <input disabled="disabled" name="aditionalOtherText" class="waterMark style" title="other" type="text" value="other"/>
                            <span class="errorIcon"></span>
                    </span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">This field cannot be left empty.</span>
                        <a id="aditional" class="addField" href="javascript:">Add field</a>
                    </p>
                </div>
            </div></div>
            <div class="clear"></div>
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

    </div>

</div>