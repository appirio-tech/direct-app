<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 1st step of presentation project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPresentationProjectStep1" class="hide presentation newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Deliverable</h3>
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

                    <a href="javascript:;" class="toolTip" id="toolTip1" rel='What do you want us to deliver?'></a>
                    <span class="label">What do you want us to deliver?</span>
                </div>
                <!-- End .row -->
                <div class="clear"></div>

                <div class="content">

                    <div class="leftCnt">
                        <div class="cnt">
                            <div class="selector">
                                <label><input type="radio" checked="checked" name="presentation" value="whole"/>A Whole Presentation</label>
                                <div class="clear"></div>
                                <div class="text">You want us to develop a complete, audience-ready presentation from materials that you supply to us.</div>
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
                                            <label><input checked="checked" type="radio" name="whole"/>Start a conversation</label>
                                        </div>
                                        <div class="right">
                                            You know what the presentation is about and what you want to accomplish with it, but you're not sure how to get there. We'll discuss the possibilities with you.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label><input type="radio" name="whole"/>Create a presentation from existing materials</label>
                                        </div>
                                        <div class="right">
                                            You have the necessary data and a content outline, which you would like us to expand into a polished final product.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label><input type="radio" name="whole"/>Enhance an existing presentation</label>
                                        </div>
                                        <div class="right">
                                            You have a complete presentation which you would like us to improve with eye-catching graphics and/or meticulous text editing.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row last">
                                        <div class="left">
                                            <label><input type="radio" name="whole" value="other"/>Some other situation</label>
                                        </div>
                                        <div class="right">
                                            Please describe:
                                            <div class="textarea">
                                                <textarea rows="10" cols="10" name="whole"></textarea>
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
                                <label><input type="radio" name="presentation" value="part"/>Part of a presentation</label>
                                <div class="clear"></div>
                                <div class="text">You want us to deliver a specific component that you will integrate into the presentation yourself.</div>
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
                                            <label><input type="radio" name="part"/>Concept visualization</label>
                                        </div>
                                        <div class="right">
                                            You have an idea or a written description that you'd like us to render as a striking new image.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label><input type="radio" name="part"/>Data visualization</label>
                                        </div>
                                        <div class="right">
                                            You have a structured collection of numbers that you'd like us to translate into an informative visual representation.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row">
                                        <div class="left">
                                            <label><input type="radio" name="part"/>Graphical motif</label>
                                        </div>
                                        <div class="right">
                                            You want us to design a background and border treatment that will be applied to every page or slide of a presentation.
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="row last">
                                        <div class="left">
                                            <label><input type="radio" name="part" value="other"/>Some other situation</label>
                                        </div>
                                        <div class="right">
                                            Please describe:
                                            <div class="textarea">
                                                <textarea rows="10" cols="10" name="part"></textarea>
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