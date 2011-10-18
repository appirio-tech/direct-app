<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 6.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep6" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>6</span>Setup the Project</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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
                        <span>Want to communicate?</span>
                        <a href="#">Project Forums</a>
                    </div>
                    <p>You have successfully created your project. <br/>
                        You must complete project setup from Project Overview Page before you can start your project.
                    </p>
                </div>
                <!-- End .successDiv -->

                <div class="projectOverView">

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

                    <!-- list -->
                    <dl class="topStep completedSteps">
                        <dt>Steps already completed:</dt>
                        <dd class="step1"><span class="icon">1</span>

                            <p>Create project</p></dd>
                        <dd class="step2 createStep hide"><span class="icon">2</span>

                            <p>Create draft copilot posting</p></dd>
                        <dd class="step2 gameplanStep hide"><span class="icon">2</span>

                            <p>Create gameplan from project template with draft contests</p></dd>
                        <dd class="step2 selectStep hide"><span class="icon">2</span>

                            <p class="twoLine">Select copilot<br/><span class="smallText">Username1, iamcopilot</span>
                            </p></dd>
                        <dd class="step3 selectStep hide"><span class="icon">3</span>

                            <p class="twoLine">Select copilot<br/><span class="smallText">Username1, iamcopilot</span>
                            </p></dd>
                        <dd class="step3 createStep hide"><span class="icon">3</span>

                            <p>Create draft copilot posting</p></dd>
                    </dl>

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
                        <dd class="step5 launchFirstStep hide"><span class="icon">5</span>

                            <p>Launch first contest</p></dd>
                        <dd class="step5 selectStep hide"><span class="icon">5</span>

                            <p class="twoLine">Select copilot from copilot posting submitters<br/><span
                                    class="smallText">Copilot will help you to complete the other processes</span></p>
                        </dd>
                    </dl>
                    <!-- End .list -->

                    <div class="bottomLine">
                        <a class="button6" title="PROJECT OVERVIEW" href='<s:url action="projectOverview"><s:param name="formData.projectId" value="%{'projectIdValue'}" /></s:url>'><span class="left"><span
                                class="right">PROJECT OVERVIEW</span></span></a>

                        <p>
                            Go to your project. Once you're in your project you can do things like monitor status,
                            manage your copilot, run contests...
                        </p>
                    </div>

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
