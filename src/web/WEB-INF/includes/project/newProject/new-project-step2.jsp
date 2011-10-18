<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 2.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R2 v1.0)
  - - Move custom project plan to the first
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep2" class="hide newProjectStep">

<!-- step title -->
<div class="stepTitle">
    <h3><span>2</span>What type of Project is this ?</h3>
     <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
</div>
<!-- End .stepTitle -->


<!-- step second -->
<div class="stepSecond stepContainer">

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
        <dd>You can pick one of our project templates, which will automatically create the game plan for your project
            with draft contests.
        </dd>
        <dd>You can also select the size of your project (Large, Medium or Small). The budget and schedule will change
            accordingly.
        </dd>
        <dd>The "Game Plan" link gives you a detailed view of the project plan. This includes contests for your project
            along with each contest's budget and schedule.
        </dd>
        <dd>You can select the Custom option if you want to create your own game plan from scratch.</dd>
    </dl>
</div>
<!-- End .noteMask -->

<!-- form -->
<div class="form">

<div class="projectTypeSelect">
    <label>Project Type</label>
    <select>
        <option>Mobile</option>
        <option>Web Application</option>
        <option>Prototype</option>
        <option>Graphic Design</option>
        <option>Presentation</option>
    </select>
</div>

<div class="clear"></div>

<div class="projectList">

<!-- item -->
<div class="projectItem">

    <div class="radioBox">
        <input id="customGamePlanRadio" type="radio" name="radio1" class="radio"/><label>Custom</label>

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
        <input type="radio" name="radio1" class="radio" disabled="disabled"/><label>Project Type 1</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .projectPic -->

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
                            <td class="firstTd">No of Contents</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">Details</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">Game Plan</a>
                    </div>

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
        <input type="radio" name="radio1" class="radio" disabled="disabled"/><label>Project Type 2</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .projectPic -->

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
                            <td class="firstTd">No of Contents</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">Details</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">Game Plan</a>
                    </div>

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
        <input type="radio" name="radio1" class="radio" disabled="disabled"/><label>Project Type 3</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .projectPic -->

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
                            <td class="firstTd">No of Contents</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">Details</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">Game Plan</a>
                    </div>

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
        <input type="radio" name="radio1" class="radio" disabled="disabled"/><label>Project Type 4</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .projectPic -->

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
                            <td class="firstTd">No of Contents</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">Details</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">Game Plan</a>
                    </div>

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
        <input type="radio" name="radio1" class="radio" disabled="disabled"/><label>Project Type 5</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="top">
            <div class="bottom">
                <div class="bg">

                    <!-- img -->
                    <div class="projectPic"><img src="/images/projectItem.png" alt=""/></div>
                    <!-- End .projectPic -->

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
                            <td class="firstTd">No of Contents</td>
                            <td class="dataNumCont">00</td>
                        </tr>
                    </table>

                    <div class="buttonArea">
                        <a href="javascript:;" class="blackButton detailButton">Details</a>
                        <a href="javascript:;" class="blackButton gamePlanButton">Game Plan</a>
                    </div>

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
        <input id="customGamePlanRadio" type="radio" name="radio1" class="radio"/><label>Custom</label>

    </div>

    <!-- container -->
    <div class="projectContainer">
        <div class="custom"></div>
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
<!-- End stepSecond -->

</div>
