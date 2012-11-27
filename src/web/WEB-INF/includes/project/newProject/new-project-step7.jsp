<%--
  - Author: TCSASSEMBLER, TCSASSEMBLY
  - Version: 1.2
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 7.
  -
  - Version 1.2 change notes: Changed this step to step 7.
  - Version 1.1 change notes: Added informations for presentation project type.
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep7" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>7</span>Success!</h3>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepSixth stepContainer">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <!--
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                -->
            </div>
            <!-- End .topBar -->

            <!-- form -->
            <div class="form">

                <!-- success -->
                <div class="successDiv">
                    <div class="communicate">
                            <a class="button6 colorFix" title="Go To Project" href='<s:url action="projectOverview"><s:param name="formData.projectId" value="%{'projectIdValue'}" /></s:url>'><span class="left"><span
                                class="right">Go To Project</span></span></a>
                    </div>
                    <p class='defaultNote'>You have successfully created your project. <br/>
                        Please go to your <span><a class="overviewLink" href='<s:url action="projectOverview"><s:param name="formData.projectId" value="%{'projectIdValue'}" /></s:url>'>Project Overview</a></span> to finish setting up your project.
                    </p>
                    <p class='pptNote hide'>Your presentation project has been launched. That was easy, wasn't it? <br/>
                        Your specifications are summarized below.
                    </p>
                </div>
                <!-- End .successDiv -->

                <div class="projectOverView">
                    <!-- list -->
                    <dl class="pptProjectName hide">
                        <dt>Name:</dt>
                        <dd><p>Floating Futon Sales Pitch</p></dd>
                    </dl>

                    <dl class="pptSummary hide">
                        <dt>Summary:</dt>
                        <dd><p>I am presenting my idea for a helium-filled futon to a major mattress manufacturer.</p></dd>
                    </dl>

                    <dl class="pptProjectDuration hide">
                        <dt>Project duration:</dt>
                        <dd><p><span class="duration">45</span> days</p></dd>
                    </dl>

                    <dl class="pptPresentationLength hide">
                        <dt>Presentation length:</dt>
                        <dd><p><span class="length">6-10</span> pages, including the cover page</p></dd>
                    </dl>

                    <dl class="pptTargetAudience hide">
                        <dt>Target audience:</dt>
                        <dd><p>Enterprise business customer</p></dd>
                    </dl>

                    <dl class="pptManagementMessage hide">
                        <dt></dt>
                        <dd><p style="width: 100%;line-height: 20px;font-size: 14px;">Our management team is reviewing your full specifications in order to assign the most qualified copilot to your project. You can expect to hear from your new copilot within 48 hours.<br/>We have already set up your project in the Cockpit, where you will be able to follow our progress in building your presentation.</p></dd>
                        <dd style="text-align: center;"><p style="font-size: 14px;">Thank you for choosing TopCoder!</p></dd>
                    </dl>

                    <!-- End .list -->

                    <table cellspacing="0" cellpadding="0" border="0" class="projectStats">
                        <colgroup>
                            <col width="32%"/>
                            <col width="32%"/>
                            <col width="36%"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th class="first">Estimated Total Duration</th>
                            <th>Estimated Total Cost</th>
                            <th class="last">Estimated No of Contests</th>
                        </tr>
                        <tr>
                            <td class="dataDur">00 Days</td>
                            <td class="dataCost">$ 0000.00</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                        </tbody>

                    </table>

                    <dl class="notYetSteps hide">
                        <dt>Steps yet to be completed:</dt>
                        <dd class="step2 hide"><span class="icon">2</span>

                            <p>Complete gameplan</p></dd>
                        <dd class="step3 gameplanStep hide"><span class="icon">3</span>

                            <p>Complete gameplan</p></dd>
                        <dd class="step3 completeStep hide"><span class="icon">3</span>

                            <p>Complete contest specifications and schedule contests</p></dd>
                        <dd class="step3 launchStep hide"><span class="icon">3</span>

                            <p>Complete and launch copilot posting</p></dd>
                        <dd class="step3 pptStep hide"><span class="icon">3</span>

                            <p>Project Manger review your project</p></dd>
                        <dd class="step4 completeStep hide"><span class="icon">4</span>

                            <p>Complete contest specifications and schedule contests</p></dd>
                        <dd class="step4 launchFirstStep hide"><span class="icon">4</span>

                            <p>Launch first contest</p></dd>
                        <dd class="step4 launchStep hide"><span class="icon">4</span>

                            <p>Complete and launch copilot posting</p></dd>
                        <dd class="step4 selectStep hide"><span class="icon">4</span>

                            <p class="twoLine">Select copilot from copilot posting submitters<br/><span
                                    class="smallText">Copilot will help you to complete the other processes</span></p>
                        </dd>
                        <dd class="step4 pptStep hide"><span class="icon">4</span>

                            <p>Launch Copilot Opportunity and Pick Copilot</p></dd>
                        <dd class="step5 launchFirstStep hide"><span class="icon">5</span>

                            <p>Launch first contest</p></dd>
                        <dd class="step5 selectStep hide"><span class="icon">5</span>

                            <p class="twoLine">Select copilot from copilot posting submitters<br/><span
                                    class="smallText">Copilot will help you to complete the other processes</span></p>
                        </dd>
                        <dd class="step5 pptStep hide"><span class="icon">5</span>

                            <p>Launch first contest</p></dd>
                    </dl>
                    <!-- End .list -->

                </div>




            </div>
            <!-- End .form -->

            <!-- bottom bar -->
            <!--
            <div class="BottomBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
            </div>
            -->
            <!-- End .BottomBar -->

            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>

        </div>

    </div>
    <!-- End .stepFirst -->

</div>
