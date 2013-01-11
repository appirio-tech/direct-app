<%--
  - Author: minhu, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 4th step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  -             Update button text to uppercase.
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep4" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>4</span>About the Problem Continued...</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->
    <div class="stepContainer step5Container">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->
                <div class="step5">
                    <p class="intro">All fields marked with <span class="red">*</span> are mandatory</p>
                    <div class="row q1">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide information of the test data used to evaluate performance of solutions for your problem.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label class="question29"></label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio-1" id="radio11" value="yes" autocomplete="off" checked="checked" /><label for="radio11" class="questionOption123"></label>
                                    <input type="radio" name="radio-1" id="radio12" value="no" autocomplete="off" /><label for="radio12" class="questionOption124"></label>
                                    <input type="radio" name="radio-1" id="radio13" value="unknow" autocomplete="off" /><label for="radio13" class="questionOption125"></label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label class="questionOption123"></label> 
                                    <div class="sliderCtl">
                                        <div class="ruler">
                                            <span class="first"><span>0</span></span>
                                            <span>1024</span>
                                            <span>2046</span>
                                            <span>3070</span>
                                            <span class="last">4094</span>
                                        </div>
                                        <div class="slider"></div>
                                        <div class="val">
                                            <input id="expectedSizeOfTestData" type="text" class="text"  value="0" autocomplete="off" />
                                            <span>MB</span>
                                            <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <div class="textR">
                                        <label class="questionOption124"></label>
                                        <textarea id="reasonForNoTestData" rows="10" cols="10" autocomplete="off" ></textarea>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm questionOption125"></p>
                                </div>
                            </div>
                        </div>
                </div>
                    <div class="row q2">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the requirement of the amount of used memory.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label class="question30"></label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio2" id="radio21" value="yes" autocomplete="off" checked="checked" /><label for="radio21" class="questionOption126"></label>
                                    <input type="radio" name="radio2" id="radio22" value="no" autocomplete="off" /><label for="radio22" class="questionOption127"></label>
                                    <input type="radio" name="radio2" id="radio23" value="unknow" autocomplete="off" /><label for="radio23" class="questionOption128"></label>
                                </div>
                            </div>        
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label class="questionOption126"></label>

                                    <div class="sliderCtl">
                                        <div class="ruler">
                                            <span class="first"><span>0</span></span>
                                            <span>1024</span>
                                            <span>2046</span>
                                            <span>3070</span>
                                            <span class="last">4094</span>
                                        </div>
                                        <div class="slider"></div>
                                        <div class="val">
                                            <input id="maximumAmountOfMemory" type="text" class="text" value="0" autocomplete="off"/>
                                            <span>MB</span>
                                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <label class="questionOption127"></label>

                                    <div class="sliderCtl">
                                        <div class="ruler">
                                            <span class="first"><span>0</span></span>
                                            <span>1024</span>
                                            <span>2046</span>
                                            <span>3070</span>
                                            <span class="last">4094</span>
                                        </div>
                                        <div class="slider"></div>
                                        <div class="val">
                                            <input id="expectedAmountOfMemory" type="text" class="text" value="0" autocomplete="off"/>
                                            <span>MB</span>
                                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm questionOption128"></p>
                                </div>
                            </div>        
                        </div>
                </div>
                    <div class="row q3">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the runtime speed requirement.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label class="question31"></label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio3" id="radio31" value="yes" autocomplete="off" checked="checked" /><label for="radio31" class="questionOption129"></label>
                                    <input type="radio" name="radio3" id="radio32" value="no" autocomplete="off" /><label for="radio32" class="questionOption130"></label>
                                    <input type="radio" name="radio3" id="radio33" value="unknow" autocomplete="off" /><label for="radio33" class="questionOption131"></label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label class="questionOption129"></label>
                                    <div class="textR">
                                        <input id="maximumExecutionTime" type="text" class="text" value="0" autocomplete="off"/> <strong>seconds</strong>
                                            <p class="errorTxt">This field should have positive value.</p>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <label class="questionOption130"></label>
                                    <div class="textR">
                                        <input id="allowedExecutionTime" type="text" class="text" value="0" autocomplete="off"/> <strong>seconds</strong>
                                            <p class="errorTxt">This field should have positive value.</p>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm questionOption131"></p>
                                </div>
                            </div>        
                        </div>
                    </div>
                    <div class="row q4">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the requirement of appropriate thread model.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label class="question32"></label>
                                </div>
                            </div>    
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="radios">
                                    <div class="radio">
                                        <input type="radio" name="radio4" id="radio41" autocomplete="off" checked="checked"/>
                                        <label for="radio41" class="questionOption132"></label>
                                        <p class="questionOption132"></p>
                                    </div>
                                    <div class="radio">
                                        <input type="radio" name="radio4" id="radio42" autocomplete="off" />
                                        <label for="radio42" class="questionOption133"></label>
                                        <p class="questionOption133"></p>
                                    </div>
                                    <div class="radio last">
                                        <input id="unknowToSolution" type="radio" name="radio4" id="radio43" autocomplete="off" />
                                        <label for="radio43" class="questionOption134"></label>
                                    </div>
                                </div>
                            </div>    
                        </div>
                    </div>
                    <div class="row q5">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please choose the languages you would like to allow for your competition.&lt;/p&gt;" id="toolTip5" class="toolTip" href="javascript:;"></a>
                                    <label>The Current Marathon Platform supports the following programming languages: </label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <label class="question33"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox51" autocomplete="off"/>
                                <label for="checkbox51" class="label questionOption135"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox52" autocomplete="off"/>
                                <label for="checkbox52" class="label questionOption136"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox53" autocomplete="off" />
                                <label for="checkbox53" class="label questionOption137"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox54" autocomplete="off" />
                                <label for="checkbox54" class="label questionOption138"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox55" autocomplete="off" />
                                <label for="checkbox55" class="label questionOption139"></label>
                                <input type="checkbox" name="checkbox5" id="checkbox56" autocomplete="off" />
                                <label for="checkbox56" class="label questionOption140"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row q6">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please choose the way to integrate the winning solution.&lt;/p&gt;" id="toolTip3" class="toolTip" href="javascript:;"></a>
                                    <label class="question34"></label>
                                </div>
                        </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="radios">
                                    <div class="radio">
                                        <input type="radio" name="radio6" id="radio61" autocomplete="off" checked="checked"/>
                                        <label for="radio61" class="questionOption141"></label>
                                    </div>
                                    <div class="radio">
                                        <input type="radio" name="radio6" id="radio62" autocomplete="off" />
                                        <label for="radio62" class="questionOption142"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row q7">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; If choose Yes, please provide current solution's high level overview and information on its performance.&lt;/p&gt;" id="toolTip4" class="toolTip" href="javascript:;"></a>
                                    <label class="question35"></label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio7" id="radio71" value="yes" autocomplete="off" checked="checked" /><label for="radio71" class="questionOption143"></label>
                                    <input type="radio" name="radio7" id="radio72" value="no" autocomplete="off" /><label for="radio72" class="questionOption144"></label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label class="questionOption143"></label>
                                    <div class="textR">
                                        <textarea id="overviewOnCurrentSolution" cols="10" rows="10" autocomplete="off"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

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