<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 1st step of mobile project creation.
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
<div id="newMobileProjectStep1" class="hide mobile newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Development target</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFirst2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->
            <div class="form">

                <!-- row -->
                <div class="row projectName">

                    <a href="javascript:;" class="toolTip" id="toolTip1" rel="Which type of app do you want to build"></a>
                    <span class="label question1"></span>
                </div>
                <!-- End .row -->
                <div class="clear"></div>

                <div class="content">
                    <div class="left platform">
                        <div class="row cross">
                            <label class="questionOption1"><input id="crossPlatformApp" checked="checked" type="radio" name="platform" value="cross"/></label>
                            <div class="note questionOption1">
                            </div>
                        </div>
                        <div class="row native">
                            <label class="questionOption2"><input id="nativeApp" type="radio" name="platform" value="native"/></label>
                            <div class="note questionOption2">
                            </div>
                        </div>
                        <div class="row web">
                            <label class="questionOption3"><input id="webApp" type="radio" name="platform" value="web"/></label>
                            <div class="note questionOption3">
                            </div>
                        </div>
                        <div class="row not">
                            <label class="questionOption4"><input id="typeNotSure" type="radio" name="platform" value="not"/></label>
                            <div class="note questionOption4">
                            </div>
                        </div>
                    </div>
                    <div class="left desc">
                        <div class="crossOption options question2">
                            <div class="opt">
                                <label class="questionOption5"><input id="noSpecific" checked="checked" type="radio" name="cross"/></label>
                                <label class="questionOption6"><input id="phoneGap" type="radio" name="cross"/></label>
                                <label class="questionOption7"><input id="moSync" type="radio" name="cross"/></label>
                                <label class="last questionOption8"><input id="otherCrossPlatform" type="radio" name="cross" value="other"/></label>
                                <div class="inputCnt">
                                    <input id="otherCrossPlatformVal" name="crossOther" type="text" value="other" class="text waterMark" title="other"/>
                                    <span class="errorIcon"></span>
                                    <p class="message padding">
                                        <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                                    </p>
                                </div>
                                <div class="clear"></div>
                            </div>

                        </div>
                        <div class="nativeOption options question3">
                            <p class="message padding">
                                <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                            </p>
                            <div class="opt">
                                <label class="questionOption9"><input id="ios" checked="checked" type="checkbox" name="native"/></label>
                                <label class="questionOption10"><input id="android" type="checkbox" name="native"/></label>
                                <label class="questionOption11"><input id="blackberry" type="checkbox" name="native"/></label>
                                <label class="questionOption12"><input id="windowsPhone" type="checkbox" name="native"/></label>
                                <label class="last questionOption13"><input id="otherNative" type="checkbox" name="native" value="other"/></label>
                                <div class="inputCnt">
                                    <input id="otherNativeVal" name="nativeOther" value="other" type="text" class="text waterMark" title="other"/>
                                    <span class="errorIcon"></span>
                                    <p class="message padding">
                                        <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                                    </p>
                                </div>
                                <div class="clear"></div>
                            </div>

                        </div>
                    </div>
                    <div class="clear"></div>
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

    </div>

</div>
