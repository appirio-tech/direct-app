<%--
  - Author: minhu
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 4th step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep4" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>4</span>About the Problem Continued...</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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
                                    <label>Do you have or can you create a relevant test data in order to evaluate performance of solutions for your problem?</label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio-1" id="radio11" value="yes" autocomplete="off" checked="checked" /><label for="radio11">Yes</label>
                                    <input type="radio" name="radio-1" id="radio12" value="no" autocomplete="off" /><label for="radio12">No</label>
                                    <input type="radio" name="radio-1" id="radio13" value="unknow" autocomplete="off" /><label for="radio13">I don't know</label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label>Please provide an expected size of all test data</label> 
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
                                            <input type="text" class="text"  value="0" autocomplete="off" />
                                            <span>MB</span>
                                            <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <div class="textR">
                                        <label>Please clarify why it is difficult to get a proper test data.</label>
                                        <textarea rows="10" cols="10" autocomplete="off" ></textarea>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm">A Topcoder PM will be approaching you soon to clarify your doubts</p>
                                </div>
                            </div>
                        </div>
                </div>
                    <div class="row q2">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the requirement of the amount of used memory.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label>Is amount of used memory essential in this problem?</label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio2" id="radio21" value="yes" autocomplete="off" checked="checked" /><label for="radio21">Yes</label>
                                    <input type="radio" name="radio2" id="radio22" value="no" autocomplete="off" /><label for="radio22">No</label>
                                    <input type="radio" name="radio2" id="radio23" value="unknow" autocomplete="off" /><label for="radio23">I don't know</label>
                                </div>
                            </div>        
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label>What maximum amount of memory would be acceptable</label>

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
                                            <input type="text" class="text" value="0" autocomplete="off"/>
                                            <span>MB</span>
                                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <label>How much memory do you think it is necessary in order to obtain proper results?</label>

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
                                            <input type="text" class="text" value="0" autocomplete="off"/>
                                            <span>MB</span>
                                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm">A Topcoder PM will be approaching you soon to clarify your doubts</p>
                                </div>
                            </div>        
                        </div>
                </div>
                    <div class="row q3">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the runtime speed requirement.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label>Is runtime speed essential in this problem?</label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio3" id="radio31" value="yes" autocomplete="off" checked="checked" /><label for="radio31">Yes</label>
                                    <input type="radio" name="radio3" id="radio32" value="no" autocomplete="off" /><label for="radio32">No</label>
                                    <input type="radio" name="radio3" id="radio33" value="unknow" autocomplete="off" /><label for="radio33">I don't know</label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label>What maximum execution time would be acceptable (assuming a single threaded solution and average modern PC)?</label>
                                    <div class="textR">
                                        <input type="text" class="text" value="0" autocomplete="off"/> <strong>seconds</strong>
                                            <p class="errorTxt">This field should have positive value.</p>
                                    </div>
                                </div>
                                <div class="noCondition hide">
                                    <label>How much execution time do you think should be allowed for a solution in order to obtain reasonable results?</label>
                                    <div class="textR">
                                        <input type="text" class="text" value="0" autocomplete="off"/> <strong>seconds</strong>
                                            <p class="errorTxt">This field should have positive value.</p>
                                    </div>
                                </div>
                                <div class="unknowCondition hide">
                                    <p class="pm">A Topcoder PM will be approaching you soon to clarify your doubts</p>
                                </div>
                            </div>        
                        </div>
                    </div>
                    <div class="row q4">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please provide the requirement of appropriate thread model.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                    <label>What type of solution is Appropriate?</label>
                                </div>
                            </div>    
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="radios">
                                    <div class="radio">
                                        <input type="radio" name="radio4" id="radio41" autocomplete="off" checked="checked"/>
                                        <label for="radio41"><strong>Single Threaded</strong></label>
                                        <p>at least before the integration to your <br/>system</p>
                                    </div>
                                    <div class="radio">
                                        <input type="radio" name="radio4" id="radio42" autocomplete="off" />
                                        <label for="radio42"><strong>Multi Threaded</strong></label>
                                        <p>ie. problem has not much meaning in a sungle threading context</p>
                                    </div>
                                    <div class="radio last">
                                        <input type="radio" name="radio4" id="radio43" autocomplete="off" />
                                        <label for="radio43">I don't know</label>
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
                                <label>Which of these languages you would like to allow for your competition?</label>
                                <input type="checkbox" name="checkbox5" id="checkbox51" autocomplete="off"/>
                                <label for="checkbox51" class="label">C++</label>
                                <input type="checkbox" name="checkbox5" id="checkbox52" autocomplete="off"/>
                                <label for="checkbox52" class="label">Java</label>
                                <input type="checkbox" name="checkbox5" id="checkbox53" autocomplete="off" />
                                <label for="checkbox53" class="label">C#</label>
                                <input type="checkbox" name="checkbox5" id="checkbox54" autocomplete="off" />
                                <label for="checkbox54" class="label">VB.NET</label>
                                <input type="checkbox" name="checkbox5" id="checkbox55" autocomplete="off" />
                                <label for="checkbox55" class="label">Python</label>
                                <input type="checkbox" name="checkbox5" id="checkbox56" autocomplete="off" />
                                <label for="checkbox56" class="label">I don't know</label>
                            </div>
                        </div>
                    </div>
                    <div class="row q6">
                        <div class="left">
                            <div class="leftMask">
                                <div class="title">
                                    <a rel="&lt;p&gt; Please choose the way to integrate the winning solution.&lt;/p&gt;" id="toolTip3" class="toolTip" href="javascript:;"></a>
                                    <label>How do you prefer to integrate the winning solution (assuming its quality is appropriate) into your software product</label>
                                </div>
                        </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="radios">
                                    <div class="radio">
                                        <input type="radio" name="radio6" id="radio61" autocomplete="off" checked="checked"/>
                                        <label for="radio61">I prefer to handle the integration myself.</label>
                                    </div>
                                    <div class="radio">
                                        <input type="radio" name="radio6" id="radio62" autocomplete="off" />
                                        <label for="radio62"> Prefer the integration to be done by TopCoder through a separate contest or a series of contests?</label>
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
                                    <label>Do you have any current solution of the problem described?</label>
                                </div>
                                <div class="radios">
                                    <input type="radio" name="radio7" id="radio71" value="yes" autocomplete="off" checked="checked" /><label for="radio71">Yes</label>
                                    <input type="radio" name="radio7" id="radio72" value="no" autocomplete="off" /><label for="radio72">No</label>
                                </div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="rightMask">
                                <div class="yesCondition">
                                    <label>Please provide its high level overview and information on its performance.</label>
                                    <div class="textR">
                                        <textarea cols="10" rows="10" autocomplete="off"></textarea>
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