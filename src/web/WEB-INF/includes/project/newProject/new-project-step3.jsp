<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 3.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep3" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>Project Game Plan</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepThird stepContainer">

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
                        <div>Your project will consist of various contests. Each contest will address a specific project
                            task.
                        </div>
                        <div>For example, the Web Design contests will address the graphic design aspect of your
                            application, the Development contests will address the coding aspect of your application.
                        </div>
                    </dd>
                    <dd>Each contest will have its own timeline and budget. Some of the contests depend on other
                        contests, where the output of one contest is used as input for another contest. Some contests
                        can be run parallely.
                    </dd>

                </dl>
            </div>
            <!-- End .noteMask -->

            <!-- form -->
            <div class="form">

                <div id="ganttChartDiv"></div>

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
    <!-- End .stepFirst -->

</div>
<!-- End #stepContainer -->