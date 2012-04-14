<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 3rd step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newMobileProjectStep3" class="hide mobile newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>Hardware support</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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
                        <span class="label">Motion sensing</span>
                        <span class="errorIcon hide"></span>
                        <div class="clear"></div>
                        <p class="message">
                            <span class="errorText">Please choose at least 1 option.</span>
                        </p>
                        <label class="select2"><input checked="checked" type="checkbox" name="motionSel"/><span>Unsure about this area - need help</span></label>
                        <div class="clear"></div>
                        <div class="icons motion"></div>
                        <label><input type="checkbox" name="motion"/><span>tilt</span></label>
                        <label><input type="checkbox" name="motion"/><span>shake</span></label>
                        <div class="clear"></div>
                        <label class="last"><input value="other" type="checkbox" name="motion" class="other"/><span>&nbsp;</span></label>
                        <span class="input">
                            <input disabled="disabled" name="motionOtherText" class="waterMark motion" title="other" type="text" value="other"/>
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
                    <span class="label audience">Geolocation</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2"><input checked="checked" type="checkbox" name="geoSel"/><span>Unsure about this area - need help</span></label>
                    <div class="clear"></div>
                    <div class="icons geolocation"></div>
                    <label><input type="checkbox" name="geo"/><span>cellular network</span></label><br/>
                    <span class="desk">low precision; dependent on proximity to cell tower</span>
                    <label><input type="checkbox" name="geo"/><span>Wi-Fi triangulation</span></label><br/>
                    <span class="desk">medium precision; dependent on Wi-Fi network proximity</span>
                    <label><input type="checkbox" name="geo"/><span>GPS</span></label><br/>
                    <span class="desk">high precision; dependent on presence of GPS receiver in device</span>
                </div>
            </div></div>
            <div class="left"><div class="rightBorder">

                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='Will your app to respond to Camera events?'></a>
                    <span class="label purpose">Camera</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2"><input checked="checked" type="checkbox" name="cameraSel"/><span>Unsure about this area - need help</span></label>
                    <div class="clear"></div>
                    <div class="icons camera"></div>
                    <label><input type="checkbox" name="camera"/><span>still photos</span></label>
                    <label><input type="checkbox" name="camera"/><span>video capture</span></label>
                    <label><input type="checkbox" name="camera"/><span>object detection</span></label>
                    <div class="clear"></div>
                    <label class="last"><input class="other" value="other" type="checkbox" name="camera"/><span>&nbsp;</span></label>
                    <span class="input">
                        <input disabled="disabled" name="cameraOtherText" class="waterMark purpose" title="other" type="text" value="other"/>
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
                    <span class="label style">Additional features</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2"><input checked="checked" type="checkbox" name="aditionalSel"/><span>Unsure about this area - need help</span></label>
                    <div class="clear"></div>
                    <div class="icons additionalFeature"></div>
                    <label><input type="checkbox" name="aditional"/><span>audio recording</span></label>
                    <label><input type="checkbox" name="aditional"/><span>phone calls</span></label>
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