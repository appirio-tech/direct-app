<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 2nd step of presentation project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPresentationProjectStep2" class="hide presentation newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>2</span>Basics</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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
                    <span class="label">Title</span>
                    <div class="clear"></div>
                    <div class="input">
                        <input class="title limit" type="text"/>
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
                    <span class="label">Summary</span>
                    <div class="clear"></div>
                    <div class="input">
                        <textarea rows="" cols=""></textarea>
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
                    <span class="label">Format</span>
                    <div class="clear"></div>
                    <label><input checked="checked" type="radio" name="format"/><span>PowerPoint slides</span></label>
                    <label><input type="radio" name="format"/><span>Word document</span></label>
                    <label><input type="radio" name="format"/><span>PDF document</span></label>
                    <label><input type="radio" name="format"/><span>HTML (web) pages</span></label>
                    <label><input type="radio" name="format"/><span>Flash application</span></label>
                    <div class="clear"></div>
                    <label class="last"><input value="other" type="radio" name="format"/><span>&nbsp;</span></label>
                    <span class="input"><input disabled="disabled" name="formatOther" class="waterMark format" title="other" type="text" value="other"/></span>
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
                    <span class="label">Visuals</span>
                    <div class="clear"></div>
                    <label><input checked="checked" type="radio" name="visuals"/><span>on one large screen</span></label>
                    <label><input type="radio" name="visuals"/><span>on their own computers</span></label>
                    <label><input type="radio" name="visuals"/><span>on printed copies</span></label>
                    <label><input type="radio" name="visuals"/><span>this is an oral presentation without visual aids</span></label>
                    <div class="clear"></div>
                    <label class="last"><input value="other" type="radio" name="visuals"/><span>&nbsp;</span></label>
                    <span class="input"><input disabled="disabled" name="visualsOther" class="waterMark visuals" title="other" type="text" value="other"/></span>
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
