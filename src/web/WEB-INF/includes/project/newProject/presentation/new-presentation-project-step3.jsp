<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 3rd step of presentation project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
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
                    <span class="label audience question19"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="questionOption77"><input id="customer" checked="checked" type="checkbox" name="audience"/></label>
                    <label class="questionOption78"><input id="colleague" type="checkbox" name="audience"/></label>
                    <label class="questionOption79"><input id="businessPartner" type="checkbox" name="audience"/></label>
                    <label class="questionOption80"><input id="topExcutive" type="checkbox" name="audience"/></label>
                    <label class="questionOption81"><input id="upperManagement" type="checkbox" name="audience"/></label>
                    <label class="questionOption82"><input id="lowerManagement" type="checkbox" name="audience"/></label>
                    <label class="questionOption83"><input id="technicalStaff" type="checkbox" name="audience"/></label>
                    <label class="questionOption84"><input id="saleStaff" type="checkbox" name="audience"/></label>
                    <label class="questionOption85"><input id="laborer" type="checkbox" name="audience"/></label>
                    <label class="questionOption86"><input id="academic" type="checkbox" name="audience"/></label>
                    <label class="questionOption87"><input id="industryInsider" type="checkbox" name="audience"/></label>
                    <label class="questionOption88"><input id="generalPublic" type="checkbox" name="audience"/></label>
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
                    <span class="label purpose question20"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="questionOption89"><input id="introduceProduct" checked="checked" type="checkbox" name="purpose"/></label>
                    <label class="questionOption90"><input id="presentPlan" type="checkbox" name="purpose"/></label>
                    <label class="questionOption91"><input id="offerDeal" type="checkbox" name="purpose"/></label>
                    <label class="questionOption92"><input id="recruitCustomer" type="checkbox" name="purpose"/></label>
                    <label class="questionOption93"><input id="honorOccasion" type="checkbox" name="purpose"/></label>
                    <label class="questionOption94"><input id="inspireAction" type="checkbox" name="purpose"/></label>
                    <label class="questionOption95"><input id="improveMorale" type="checkbox" name="purpose"/></label>
                    <label class="questionOption96"><input id="describeIdea" type="checkbox" name="purpose"/></label>
                    <label class="questionOption97"><input id="analyzeSituation" type="checkbox" name="purpose"/></label>
                    <label class="questionOption98"><input id="arguePosition" type="checkbox" name="purpose"/></label>
                    <label class="questionOption99"><input id="explainProblem" type="checkbox" name="purpose"/></label>
                    <label class="questionOption100"><input id="offerSolution" type="checkbox" name="purpose"/></label>
                    <label class="questionOption101"><input id="describeResearch" type="checkbox" name="purpose"/></label>
                    <label class="questionOption102"><input id="summarizeFinace" type="checkbox" name="purpose"/></label>
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
                    <span class="label style question21"></span>
                    <span class="errorIcon hide"></span>
                    <div class="clear"></div>
                    <p class="message">
                        <span class="errorText">Please choose at least 1 option.</span>
                    </p>
                    <label class="questionOption103"><input id="casual" checked="checked" type="checkbox" name="style"/></label>
                    <label class="questionOption104"><input id="formal" type="checkbox" name="style"/></label>
                    <label class="questionOption105"><input id="concise" type="checkbox" name="style"/></label>
                    <label class="questionOption106"><input id="detail" type="checkbox" name="style"/></label>
                    <label class="questionOption107"><input id="statistical" type="checkbox" name="style"/></label>
                    <label class="questionOption108"><input id="textual" type="checkbox" name="style"/></label>
                    <label class="questionOption109"><input id="graphical" type="checkbox" name="style"/></label>
                    <label class="questionOption110"><input id="humorous" type="checkbox" name="style"/></label>
                    <label class="questionOption111"><input id="dispassionate" type="checkbox" name="style"/></label>
                    <label class="questionOption112"><input id="factual" type="checkbox" name="style"/></label>
                    <label class="questionOption113"><input id="emotional" type="checkbox" name="style"/></label>
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
