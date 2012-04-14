<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 2nd step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newMobileProjectStep2" class="hide mobile newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>2</span>Application design</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepSecond2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->
            <div class="original">
                <div class="content">
                    <div class="left first">
                        <div class="title">Original or adaptation</div>
                        <div class="note">Is the design of your mobile app based on an existing application?</div>
                        <label><input type="radio" value="no" name="org"/>no, we are making a completely new design</label>
                        <label><input type="radio" value="yes" name="org"/>yes, we are adaptig an existing application</label>
                    </div>
                    <div class="left disable">
                        <div class="text">What type of application are you adapting?</div>
                        <label><input checked="checked" disabled="disabled" type="radio" name="app"/>desktop application</label>
                        <label><input disabled="disabled" type="radio" name="app"/>web application</label>
                        <label class="other"><input disabled="disabled" type="radio" name="app" value="other"/>&nbsp;</label>
                        <div class="inputCnt">
                            <input type="text" title="other" class="wateMark text" name="appOther" value="other"/>
                            <span class="errorIcon"></span>
                            <p class="message padding">
                                <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                            </p>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="tl"></div>
                <div class="tr"></div>
                <div class="br"></div>
                <div class="bl"></div>
            </div>

            <div class="products">
                <div class="left">
                    <a class="toolTip" href="javascript:;"></a>
                    <span class="label">Form factor</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText" style="display: none;">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2"><input type="checkbox" name="factorSelect"/>Unsure about this area - need help</label>
                    <div class="icon formFactor"></div>
                    <label><input checked="checked" type="checkbox" name="factor"/>smartphone</label><br/>
                    <span>4" screen approximately</span><br/>
                    <label><input type="checkbox" name="factor"/>compact tablet</label><br/>
                    <span>7" screen approximately</span><br/>
                    <label><input type="checkbox" name="factor"/>full-sized tablet</label><br/>
                    <span>10" screen and greater</span><br/>
                    <label class="other"><input type="checkbox" name="factor" value="other"/>&nbsp;</label>
                    <div class="inputCnt">
                        <input name="factorOther" type="text" title="other" class="wateMark text" value="other"/>
                        <span class="errorIcon"></span>
                        <p class="message padding">
                            <span class="errorText">This field cannot be left empty.</span>
                        </p>
                    </div>
                </div>
                <div class="left">
                    <a class="toolTip" href="javascript:;"></a>
                    <span class="label">Screen orientation</span>
                    <div class="clear"></div>
                    <label class="select2"><input type="checkbox" name="orientation"/>Unsure about this area - need help</label>
                    <div class="icon screenOrientation"></div>
                    <label><input checked="checked" type="radio" name="ori"/>portrait</label><br/>
                    <label><input type="radio" name="ori"/>landscape</label><br/>
                    <label><input type="radio" name="ori"/>both</label><br/>
                </div>
                <div class="left last">
                    <a class="toolTip" href="javascript:;"></a>
                    <span class="label">Screen resolution</span>
                    <div class="clear"></div>
                    <label class="select2"><input type="checkbox" name="resolution"/>Unsure about this area - need help</label>
                    <div class="icon screenResolution"></div>
                    <label><input checked="checked" type="radio" name="res"/>low resolution</label><br/>
                    <span>individual pixels are easily distinguished, as on iPhone 3, iPad 2</span><br/>
                    <label><input type="radio" name="res"/>high resolution</label><br/>
                    <span>"retina display" as on iPhone 4, Galaxy Nexus, iPad 3</span><br/>
                    <label><input type="radio" name="res"/>both low and high resolution</label><br/>
                    <span>extra design effort required</span><br/>
                </div>
                <div class="clear"></div>
            </div>
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
