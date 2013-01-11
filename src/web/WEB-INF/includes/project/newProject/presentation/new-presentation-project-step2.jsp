<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 2nd step of presentation project creation.
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
<div id="newPresentationProjectStep2" class="hide presentation newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>2</span>Basics</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFirst2 stepSecond2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->
            <div class="left first">
                <!-- row -->
                <div class="row">
                    <a href="javascript:;" class="toolTip" rel='What is the title of your presentation? '></a>
                    <span class="label question15"></span>
                    <div class="clear"></div>
                    <div class="input">
                        <input id="presentationTitle" class="title limit" type="text"/>
                    </div>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">This field cannot be left empty.</span>
                        <span class="remaning"><span class="num">30</span> char remaining.</span>
                    </p>
                </div>
                <!-- row -->
                <div class="row">
                    <a href="javascript:;" class="toolTip" rel='Please describe in a sentence or brief paragraph the subject of your presentation.'></a>
                    <span class="label question16"></span>
                    <div class="clear"></div>
                    <div class="input">
                        <textarea id="presentationSummary" rows="" cols=""></textarea>
                    </div>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">This field cannot be left empty.</span>
                    </p>
                </div>
            </div>
            <div class="left center">
                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='What is the primary physical format of the presentation?'></a>
                    <span class="label question17"></span>
                    <div class="clear"></div>
                    <label class="questionOption66"><input id="pptSlide" checked="checked" type="radio" name="format"/></label>
                    <label class="questionOption67"><input id="wordDocument" type="radio" name="format"/></label>
                    <label class="questionOption68"><input id="pdfDocument" type="radio" name="format"/></label>
                    <label class="questionOption69"><input id="htmlPage" type="radio" name="format"/></label>
                    <label class="questionOption70"><input id="flashApplication" type="radio" name="format"/></label>
                    <div class="clear"></div>
                    <label class="last questionOption71"><input id="otherPresentationFormat" value="other" type="radio" name="format"/></label>
                    <span class="input"><input id="otherPresentationFormatVal" disabled="disabled" name="formatOther" class="waterMark format" title="other" type="text" value="other"/></span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">Please specify the format.</span>
                    </p>
                </div>
            </div>
            <div class="left last">
                <!-- row -->
                <div class="row formats">
                    <a href="javascript:;" class="toolTip" rel='How will the audience see the visual portion (as opposed to the spoken portion) of your presentation?'></a>
                    <span class="label question18"></span>
                    <div class="clear"></div>
                    <label class="questionOption72"><input id="onLargeScreen" checked="checked" type="radio" name="visuals"/></label>
                    <label class="questionOption73"><input id="onOwnComputer" type="radio" name="visuals"/></label>
                    <label class="questionOption74"><input id="onPrintedCopy" type="radio" name="visuals"/></label>
                    <label class="questionOption75"><input id="oralPresentation" type="radio" name="visuals"/></label>
                    <div class="clear"></div>
                    <label class="last questionOption76"><input id="otherPresentationVisualAid" value="other" type="radio" name="visuals"/></label>
                    <span class="input"><input id="otherPresentationVisualAidVal" disabled="disabled" name="visualsOther" class="waterMark visuals" title="other" type="text" value="other"/></span>
                    <div class="clear"></div>
                    <p class="message padding">
                        <span class="errorText">Please specify the visuals.</span>
                    </p>
                </div>
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
