<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 1st step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newMobileProjectStep1" class="hide mobile newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Development target</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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

                    <a href="javascript:;" class="toolTip" id="toolTip1"></a>
                    <span class="label">What type of app do you want to build: cross-platform, native, or web?</span>
                </div>
                <!-- End .row -->
                <div class="clear"></div>

                <div class="content">
                    <div class="left platform">
                        <div class="row cross">
                            <label><input checked="checked" type="radio" name="platform" value="cross"/>Cross-platform app</label>
                            <div class="note">
                                A single development effort results in a set of native apps that look and feel the same while running on different operating systems. A cross-platform app can use many of the hardware features on each device.
                            </div>
                        </div>
                        <div class="row native">
                            <label><input type="radio" name="platform" value="native"/>Native app</label>
                            <div class="note">
                                The app is developed for a family of mobile devices that use a specific operating system, allowing it to take full advantage of hardware capabilities. A separate development effort is required for each operating system.
                            </div>
                        </div>
                        <div class="row web">
                            <label><input type="radio" name="platform" value="web"/>Web app</label>
                            <div class="note">
                                The application is hosted "in the cloud" and users access it through their device's web browser. All Internet-connected mobile devices can use the same web app. However, a web app cannot integrate tightly with the operating system and has minimal access to hardware features.
                            </div>
                        </div>
                        <div class="row not">
                            <label><input type="radio" name="platform" value="not"/>I'm not sure</label>
                            <div class="note">
                                You're not clear on the differences between the various approaches, or you can't decide which one would suit you best. We'll discuss your goals and compare the possible solutions.
                            </div>
                        </div>
                    </div>
                    <div class="left desc">
                        <div class="crossOption options">
                            Do you require the use of a specific cross-platform
                            framework?
                            <div class="opt">
                                <label><input checked="checked" type="radio" name="cross"/>No specific requirement</label>
                                <label><input type="radio" name="cross"/>PhoneGap</label>
                                <label><input type="radio" name="cross"/>MoSync</label>
                                <label class="last"><input type="radio" name="cross" value="other"/>Other:</label>
                                <div class="inputCnt">
                                    <input name="crossOther" type="text" value="other" class="text waterMark" title="other"/>
                                    <span class="errorIcon"></span>
                                    <p class="message padding">
                                        <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                                    </p>
                                </div>
                                <div class="clear"></div>
                            </div>

                        </div>
                        <div class="nativeOption options">
                            What operating system(s) do you want to target?
                            <p class="message padding">
                                <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                            </p>
                            <div class="opt">
                                <label><input checked="checked" type="checkbox" name="native"/>Apple iOS (iPhone, iPod, iPad)</label>
                                <label><input type="checkbox" name="native"/>Android</label>
                                <label><input type="checkbox" name="native"/>BlackBerry</label>
                                <label><input type="checkbox" name="native"/>Windows Phone</label>
                                <label class="last"><input id="otherNative" type="checkbox" name="native" value="other"/>Other:</label>
                                <div class="inputCnt">
                                    <input name="nativeOther" value="other" type="text" class="text waterMark" title="other"/>
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
