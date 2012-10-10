<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the common require copilot step jsp.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
--%>
<div class="stepForth stepContainer">

    <div class="geryContent">

        <!-- top bar -->
        <div class="topBar">
            <a href="javascript:;" class="prevStepButton">PREV STEP</a>
            <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
        </div>
        <!-- End .topBar -->

        <!-- .noteMask -->
        <div class="noteMask">
            <dl>
                <dt>
                    <strong>Note:</strong>
                    <a href="javascript:void(0)">Hide</a>
                </dt>

                <dd class="first">
                    A Copilot is a TopCoder Member who help you manage the TopCoder process for your project. For
                    example, you may ask to build a website. A Copilot will work with you to agree on a plan and
                    pricing
                    to build that website and then they would manage the process using the TopCoder Platform to
                    deliver
                    the website back to you.
                </dd>
                <dd>A Copilot will be paid per contest. Please see <a href="https://apps.topcoder.com/wiki/display/tc/Copilot+Overview">here</a> for Copilot payment details.
                </dd>
                <dd>If you do not have a clear choice for a Copilot, you can engage a Copilot by running a Copilot
                    selection contest.
                </dd>
                <dd>You can skip this step. if you do not require a Copilot. If you already have a choice for the
                    Copilot then please mention it here.
                </dd>

            </dl>
        </div>
        <!-- End .noteMask -->

        <!-- form -->
        <div class="form">

            <dl class="radioList">
                <dt><a href="javascript:;" class="toolTip"
                       rel="A Copilot is a TopCoder Member who help you manage the TopCoder process for your project."></a><span class="question"></span>
                </dt>
                <dd><input type="radio" name="radio1" class="radio radioYes questionOption" value="yes"  autocomplete="off"/><label class="optionText"></label></dd>
                <dd><input type="radio" name="radio1" class="radio radioNo questionOption" value="no"  autocomplete="off"/><label class="optionText"></label></dd>
            </dl>

            <div class="chooseCopilot">
                <div class="info">
                    <span></span>

                    <p>our projects use copilots, which helps them run more effectively.</p>
                </div>
                <div class="selection hide">
                    <dl>
                        <dt>
                            <a class="button6 chooseYourCopilot" href="javascript:;"><span class="left"><span
                                    class="right">CHOOSE A COPILOT DIRECTLY</span></span></a>
                        </dt>
                        <dd>
                            If you already have a copilot that you would like to assign.
                        </dd>
                        <dt>
                            <a class="button6 createPosting" href="javascript:;"><span class="left"><span
                                    class="right">CREATE COPILOT POSTING</span></span></a>
                        </dt>
                        <dd>
                            <div>If you do not have a clear choice for a copilot,</div>
                            "<span class="red">Create Copilot Posting</span>" to post the offer to our available
                            copilots.
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="clear"></div>

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
