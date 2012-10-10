<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 2nd step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
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
                        <div class="title question4"></div>
                        <div class="note question4"></div>
                        <label class="questionOption14"><input id="newDesign" type="radio" value="no" name="org"/></label>
                        <label class="questionOption15"><input id="adaptExisting" type="radio" value="yes" name="org"/></label>
                    </div>
                    <div class="left disable">
                        <div class="text question5"></div>
                        <label class="questionOption16"><input id="desktopApplication" checked="checked" disabled="disabled" type="radio" name="app"/></label>
                        <label class="questionOption17"><input id="webApplication" disabled="disabled" type="radio" name="app"/></label>
                        <label class="other questionOption18"><input id="otherApplication" disabled="disabled" type="radio" name="app" value="other"/></label>
                        <div class="inputCnt">
                            <input id="otherApplicationVal" type="text" title="other" class="wateMark text" name="appOther" value="other"/>
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
                    <span class="label question6"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText" style="display: none;">Please choose at least 1 option.</span>
                    </p>
                    <label class="select2 questionOption19"><input id="formFactorNotSure" type="checkbox" name="factorSelect"/></label>
                    <div class="icon formFactor"></div>
                    <label class="questionOption20"><input id="smartPhone" checked="checked" type="checkbox" name="factor"/></label><br/>
                    <span class="questionOption20"></span><br/>
                    <label class="questionOption21"><input id="compactTablet" type="checkbox" name="factor"/></label><br/>
                    <span class="questionOption21"></span><br/>
                    <label class="questionOption22"><input id="fullSizedTablet" type="checkbox" name="factor"/></label><br/>
                    <span class="questionOption22"></span><br/>
                    <label class="other questionOption23"><input id="otherFormFactor" type="checkbox" name="factor" value="other"/></label>
                    <div class="inputCnt">
                        <input id="otherFormFactorVal" name="factorOther" type="text" title="other" class="wateMark text" value="other"/>
                        <span class="errorIcon"></span>
                        <p class="message padding">
                            <span class="errorText">This field cannot be left empty.</span>
                        </p>
                    </div>
                </div>
                <div class="left">
                    <a class="toolTip" href="javascript:;"></a>
                    <span class="label question7"></span>
                    <div class="clear"></div>
                    <label class="select2 questionOption24"><input id="screenOrientationNotSure" type="checkbox" name="orientation"/></label>
                    <div class="icon screenOrientation"></div>
                    <label class="questionOption25"><input id="portrait" checked="checked" type="radio" name="ori"/></label><br/>
                    <label class="questionOption26"><input id="landscape" type="radio" name="ori"/></label><br/>
                    <label class="questionOption27"><input id="bothOrientation" type="radio" name="ori"/></label><br/>
                </div>
                <div class="left last">
                    <a class="toolTip" href="javascript:;"></a>
                    <span class="label question8"></span>
                    <div class="clear"></div>
                    <label class="select2 questionOption28"><input id="screenResolutionNotSure" type="checkbox" name="resolution"/></label>
                    <div class="icon screenResolution"></div>
                    <label class="questionOption29"><input id="lowResolution" checked="checked" type="radio" name="res"/></label><br/>
                    <span class="questionOption29"></span><br/>
                    <label class="questionOption30"><input id="highResolution" type="radio" name="res"/></label><br/>
                    <span class="questionOption30"></span><br/>
                    <label class="questionOption31"><input id="bothResolution" type="radio" name="res"/></label><br/>
                    <span class="questionOption31"></span><br/>
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
