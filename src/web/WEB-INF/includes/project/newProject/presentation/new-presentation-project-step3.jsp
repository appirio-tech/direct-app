<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 3rd step of presentation project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPresentationProjectStep3" class="hide presentation newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>Strategy</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFirst2 stepThird2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->

            <div class="left"><div class="rightBorder">
                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='To what kind of audience will you be presenting? Select all applicable options.'></a>
                    <span class="label audience">Audience</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label><input checked="checked" type="checkbox" name="audience"/><span>customers</span></label>
                    <label><input type="checkbox" name="audience"/><span>colleagues</span></label>
                    <label><input type="checkbox" name="audience"/><span>business partners</span></label>
                    <label><input type="checkbox" name="audience"/><span>top executives</span></label>
                    <label><input type="checkbox" name="audience"/><span>upper management</span></label>
                    <label><input type="checkbox" name="audience"/><span>lower management</span></label>
                    <label><input type="checkbox" name="audience"/><span>technical staff</span></label>
                    <label><input type="checkbox" name="audience"/><span>sales staff</span></label>
                    <label><input type="checkbox" name="audience"/><span>laborers</span></label>
                    <label><input type="checkbox" name="audience"/><span>academic audience</span></label>
                    <label><input type="checkbox" name="audience"/><span>industry insiders</span></label>
                    <label><input type="checkbox" name="audience"/><span>general public</span></label>
                    <div class="clear"></div>
                    <label class="last"><input value="other" type="checkbox" name="audience" class="other"/><span>&nbsp;</span></label>
                    <span class="input"><input disabled="disabled" name="audienceOther" class="waterMark audience" title="other" type="text" value="other"/></span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">This field cannot be left empty.</span>
                        <a id="audience" class="addField" href="javascript:">Add field</a>
                    </p>
                </div>
            </div></div>
            <div class="left"><div class="rightBorder">

                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='What do you hope to achieve with the presentation? Select all applicable options.'></a>
                    <span class="label purpose">Purpose</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label><input checked="checked" type="checkbox" name="purpose"/><span>introduce a product</span></label>
                    <label><input type="checkbox" name="purpose"/><span>present a plan</span></label>
                    <label><input type="checkbox" name="purpose"/><span>offer a deal</span></label>
                    <label><input type="checkbox" name="purpose"/><span>recruit a customer</span></label>
                    <label><input type="checkbox" name="purpose"/><span>honor an occasion</span></label>
                    <label><input type="checkbox" name="purpose"/><span>inspire an action</span></label>
                    <label><input type="checkbox" name="purpose"/><span>improve morale</span></label>
                    <label><input type="checkbox" name="purpose"/><span>describe an idea</span></label>
                    <label><input type="checkbox" name="purpose"/><span>analyze a situation</span></label>
                    <label><input type="checkbox" name="purpose"/><span>argue for a position</span></label>
                    <label><input type="checkbox" name="purpose"/><span>explain a problem</span></label>
                    <label><input type="checkbox" name="purpose"/><span>offer a solution</span></label>
                    <label><input type="checkbox" name="purpose"/><span>describe research</span></label>
                    <label><input type="checkbox" name="purpose"/><span>summarize finances</span></label>
                    <div class="clear"></div>
                    <label class="last"><input value="other" type="checkbox" name="purpose" class="other"/><span>&nbsp;</span></label>
                    <span class="input"><input disabled="disabled" name="purposeOther" class="waterMark purpose" title="other" type="text" value="other"/></span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">This field cannot be left empty.</span>
                        <a id="purpose" class="addField" href="javascript:">Add field</a>
                    </p>
                </div>
            </div></div>
            <div class="left last"><div class="rightBorder">

                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='What style of presentation do you wish to use? Select all applicable options.'></a>
                    <span class="label style">Style</span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label><input checked="checked" type="checkbox" name="style"/><span>casual</span></label>
                    <label><input type="checkbox" name="style"/><span>formal</span></label>
                    <label><input type="checkbox" name="style"/><span>concise</span></label>
                    <label><input type="checkbox" name="style"/><span>detailed</span></label>
                    <label><input type="checkbox" name="style"/><span>statistical</span></label>
                    <label><input type="checkbox" name="style"/><span>textual</span></label>
                    <label><input type="checkbox" name="style"/><span>graphical</span></label>
                    <label><input type="checkbox" name="style"/><span>humorous</span></label>
                    <label><input type="checkbox" name="style"/><span>dispassionate</span></label>
                    <label><input type="checkbox" name="style"/><span>factual</span></label>
                    <label><input type="checkbox" name="style"/><span>emotional</span></label>
                    <div class="clear"></div>
                    <label class="last"><input value="other" type="checkbox" name="style" class="other"/><span>&nbsp;</span></label>
                    <span class="input"><input disabled="disabled" name="styleOther" class="waterMark style" title="other" type="text" value="other"/></span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">This field cannot be left empty.</span>
                        <a id="style" class="addField" href="javascript:">Add field</a>
                    </p>
                </div>
            </div></div>
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
