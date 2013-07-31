
<%--
  - Author: KennyAlive, TCSASSEMBLER, Ghost_141
  - Version: 1.3
  - Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 2.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
  -             change notes: new mobile and presentation project type flow. Removed old presentation flow.
  - Version 1.2 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
  -             change notes: new analytics project type flow.
  - Version 1.3 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  -             Fix multiple bugs.
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep2" class="hide newProjectStep">

<!-- step title -->
<div class="stepTitle">
    <h3><span>2</span>Choose a type and size for your project.</h3>
     <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
</div>
<!-- End .stepTitle -->


<!-- step second -->
<div class="stepFirst stepContainer">

<div class="geryContent">

<!-- top bar -->
<div class="topBar">
    <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
    <a href="javascript:;" class="prevStepButton">PREV STEP</a>
</div>
<!-- End .topBar -->

<!-- .noteMask -->
<div class="noteMask">
    <dl>
        <dt>
            <strong>Note:</strong>
            <a href="javascript:void(0)">Hide</a>
        </dt>
        <dd>Our project templates contain sample contest schedules for standard project types. Experienced users
            of the TopCoder platform may opt for a custom project and draft their own contests.
        </dd>
        <dd>Within your chosen template, select the size of your project in order to determine the contest schedule.
        </dd>
        <dd>Click on "Details" for an explanation of the contest type and size, or on "Game plan" for a chronological
            view of the contest schedule.
        </dd>
    </dl>
</div>
<!-- End .noteMask -->

<!-- form -->
<div class="form">

    <!--
<div class="projectTypeSelect">
    <label>Project Type</label>
    <select>
        <option>Mobile</option>
        <option>Web Application</option>
        <option>Prototype</option>
        <option>Graphic Design</option>
        <option>Presentation</option>
    </select>
</div>  -->

<div class="clear"></div>

<div class="projectList">

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input id="customGamePlanRadio" type="radio" name="gamePlanType" class="radio"/><label>Custom</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="custom"></div>
    </div>
    <!-- End .projectContainer -->

</div>
<!-- End .projectItem -->

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input type="radio" name="gamePlanType" class="radio"/><label>Mobile Project Type</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .comingSoon -->
<!--
                    <table border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
                        <tr>
                            <td class="firstTd">Size of Project</td>
                            <td>
                                <select class="selProjSize">
                                    <option value="0">Small</option>
                                    <option value="1">Medium</option>
                                    <option value="2">Large</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Duration</td>
                            <td class="dataDur">000 days</td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Cost</td>
                            <td class="dataCost">$ 00000.00</td>
                        </tr>
                        <tr>
                            <td class="firstTd">No of Contests</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">DETAILS</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">GAME PLAN</a>
                    </div>
-->
                </div>
            </div>
        </div>
    </div>
    <!-- End .projectContainer -->

</div>
<!-- End .projectItem -->

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input type="radio" name="gamePlanType" class="radio"/><label>Presentation Project Type</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem-1.png" alt=""/></div>
                    <!-- End .projectPic -->
<!--
                    <table border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
                        <tr>
                            <td class="firstTd">Size of Project</td>
                            <td>
                                <select class="selProjSize">
                                        <option value="0">Small</option>
                                    <option value="1">Medium</option>
                                    <option value="2">Large</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Duration</td>
                            <td class="dataDur">000 days</td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Cost</td>
                            <td class="dataCost">$ 00000.00</td>
                        </tr>
                        <tr>
                            <td class="firstTd">No of Contests</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">DETAILS</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">GAME PLAN</a>
                    </div>
-->
                </div>
            </div>
        </div>
    </div>
    <!-- End .projectContainer -->

</div>
<!-- End .projectItem -->

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input type="radio" name="gamePlanType" class="radio"/><label>Analytics Project Type</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .comingSoon -->
<!--
                    <table border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
                        <tr>
                            <td class="firstTd">Size of Project</td>
                            <td>
                                <select class="selProjSize">
                                    <option value="0">Small</option>
                                    <option value="1">Medium</option>
                                    <option value="2">Large</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Duration</td>
                            <td class="dataDur">000 days</td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Cost</td>
                            <td class="dataCost">$ 00000.00</td>
                        </tr>
                        <tr>
                            <td class="firstTd">No of Contests</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">DETAILS</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">GAME PLAN</a>
                    </div>
-->
                </div>
            </div>
        </div>
    </div>
    <!-- End .projectContainer -->

</div>
<!-- End .projectItem -->

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input type="radio" name="gamePlanType" class="radio" disabled="disabled"/><label>Project Type 2 (coming soon)</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="comingSoon"><img src="/images/coming_soon_img.png" alt=""/></div>
                    <!-- End .comingSoon -->
<!--
                    <table border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
                        <tr>
                            <td class="firstTd">Size of Project</td>
                            <td>
                                <select class="selProjSize">
                                    <option value="0">Small</option>
                                    <option value="1">Medium</option>
                                    <option value="2">Large</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Duration</td>
                            <td class="dataDur">000 days</td>
                        </tr>
                        <tr>
                            <td class="firstTd">Total Cost</td>
                            <td class="dataCost">$ 00000.00</td>
                        </tr>
                        <tr>
                            <td class="firstTd">No of Contests</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">DETAILS</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">GAME PLAN</a>
                    </div>
-->
                </div>
            </div>
        </div>
    </div>
    <!-- End .projectContainer -->

</div>
<!-- End .projectItem -->

<div class="clear"></div>

</div>

</div>
<!-- End .form -->

<!-- bottom bar -->
<div class="BottomBar">
    <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
    <a href="javascript:;" class="prevStepButton">PREV STEP</a>
</div>
<!-- End .BottomBar -->

<!-- corner -->
<div class="corner tl"></div>
<div class="corner tr"></div>
<div class="corner bl"></div>
<div class="corner br"></div>

</div>

</div>
<!-- End stepFirst -->

</div>
