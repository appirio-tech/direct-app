<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 1st step of presentation project creation.
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
<div id="newPresentationProjectStep1" class="hide presentation newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Deliverable</h3>
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

                    <a href="javascript:;" class="toolTip" id="toolTip1" rel='What do you want us to deliver?'></a>
                    <span class="label question14"></span>
                </div>
                <!-- End .row -->
                <div class="clear"></div>

                <div class="content">

                    <div class="leftCnt">
                        <div class="cnt">
                            <div class="selector">
                                <label class="questionOption56"><input id="wholePresentation" type="radio" checked="checked" name="presentation" value="whole"/></label>
                                <div class="clear"></div>
                                <div class="text questionOption56"></div>
                            </div>
                            <div class="top">
                                <div class="r">
                                    <div class="c"></div>
                                </div>
                            </div>
                            <div class="bg">
                                <div class="bgContnt">
                                    <div class="title">What is the starting point for this project?</div>
                                    <div class="row first">
                                        <div class="left">
                                            <label class="questionOption57"><input id="startConversion" checked="checked" type="radio" name="whole"/></label>
                                        </div>
                                        <div class="right questionOption57">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label class="questionOption58"><input id="createFromExistingMaterial" type="radio" name="whole"/></label>
                                        </div>
                                        <div class="right questionOption58">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label class="questionOption59"><input id="enhanceExistingPresentation" type="radio" name="whole"/></label>
                                        </div>
                                        <div class="right questionOption59">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row last">
                                        <div class="left">
                                            <label class="questionOption60"><input id="otherWholePresentationSituation" type="radio" name="whole" value="other"/></label>
                                        </div>
                                        <div class="right">
                                            Please describe:
                                            <div class="textarea">
                                                <textarea id="otherWholePresentationSituationVal" rows="10" cols="10" name="whole"></textarea>
                                                <span class="errorIcon hide"></span>
                                            </div>
                                            <div class="clear"></div>
                                            <p class="message noMargin hide">
                                                <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                                            </p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="bottom">
                                <div class="r">
                                    <div class="c"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="rightCnt disable">
                        <div class="cnt">
                            <div class="selector">
                                <label class="questionOption61"><input id="partPresentation" type="radio" name="presentation" value="part"/></label>
                                <div class="clear"></div>
                                <div class="text questionOption61"></div>
                            </div>
                            <div class="top">
                                <div class="r">
                                    <div class="c"></div>
                                </div>
                            </div>
                            <div class="bg">
                                <div class="bgContnt">
                                    <div class="title">What component do you want us to deliver?</div>
                                    <div class="row first">
                                        <div class="left">
                                            <label class="questionOption62"><input id="conceptVisualization" type="radio" name="part"/></label>
                                        </div>
                                        <div class="right questionOption62">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label class="questionOption63"><input id="dataVisualization" type="radio" name="part"/></label>
                                        </div>
                                        <div class="right questionOption63">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label class="questionOption64"><input id="graphicalMotif" type="radio" name="part"/></label>
                                        </div>
                                        <div class="right questionOption64">
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row last">
                                        <div class="left">
                                            <label class="questionOption65"><input id="otherPartPresentationSituation" type="radio" name="part" value="other"/></label>
                                        </div>
                                        <div class="right">
                                            Please describe:
                                            <div class="textarea">
                                                <textarea id="otherPartPresentationSituationVal" rows="10" cols="10" name="part"></textarea>
                                                <span class="errorIcon hide"></span>
                                            </div>
                                            <div class="clear"></div>
                                            <p class="message noMargin hide">
                                                <span class="errorText" style="display: inline;">This field cannot be left empty.</span>
                                            </p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="bottom">
                                <div class="r">
                                    <div class="c"></div>
                                </div>
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