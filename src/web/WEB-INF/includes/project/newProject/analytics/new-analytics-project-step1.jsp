<%--
  - Author: minhu
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 1st step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep1" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Estimation of budget and timeline</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepContainer step2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

                <div class="step2Mask">
                
                    <!-- .noteMask -->
                    <div class="noteMask">
                        <dl>
                            <dt>
                                <strong>Note:</strong>
                                <a href="javascript:void(0)">Hide</a>
                            </dt>

                            <dd class="first">
                                In this step, you are just providing an estimation of Budget that you would wish you spend for the project and the duration within which you would like to complete this project
                             </dd>
                        </dl>
                    </div><!-- End .noteMask -->

                    <p class="intro">All fields marked with <span class="red">*</span> are mandatory</p>

                    <div class="eBudget">
                        <div class="title">
                            <a rel="&lt;p&gt; An estimation of Budget that you would wish you spend for the project.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                            <label>Estimated Budget<span class="red">*</span></label>
                        </div>
                        <div class="sliderCtl">
                            <div class="ruler">
                                <span class="first"><span>0K</span></span>
                                <span>20K</span>
                                <span>40K</span>
                                <span>60K</span>
                                <span>80K</span>
                                <span>100K</span>
                                <span>120K</span>
                                <span>140K</span>
                                <span>160K</span>
                                <span>180K</span>
                                <span class="last">200K</span> 
                            </div>    
                            <div class="slider"></div>
                            <div class="val">
                                <input type="text" class="text" value="" autocomplete="off"/>
                                <span>K US Dollar</span>
                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div><!-- End .eBudget -->

                    <div class="eTimeline">
                        <div class="title">
                            <a rel="&lt;p&gt; The duration within which you would like to complete this project.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                            <label>Estimated Timeline<span class="red">*</span></label>
                        </div> 
                        <div class="sliderCtl">
                            <input type="radio" name="eTimeline" class="radio" checked="checked" value="slider" />
                            <div class="ruler">
                                <span class="first"><span>>0</span></span>
                                <span>50</span>
                                <span>100</span>
                                <span>150</span>
                                <span>200</span>
                                <span>250</span>
                                <span>300</span>
                                <span>350</span>
                                <span class="last">400</span>
                            </div> <div class="slider"></div>
                            <div class="val">
                                <input type="text" class="text" value="" autocomplete="off"/>
                                <span>Days</span>
                                <p class="errorTxt">This field cannot have empty or zero value.</p>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="dp">
                            <input type="radio" name="eTimeline" class="radio" value="dp" />
                            <label>Specific Dates</label>
                            <input type="text" class="text from" readonly="readonly" autocomplete="off"/>
                            <span>to</span>
                            <input type="text" class="text to"  readonly="readonly" autocomplete="off"/>
                            <p class="errorTxt">This field cannot have empty or zero value.</p>
                            <div class="disableMask"></div>
                        </div>
                    </div><!-- End .eBudget -->
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