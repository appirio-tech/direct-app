<%--
  - Author: TCSASSEMBLER, Ghost_141
  - Version: 1.3
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 1.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R2 v1.0)
  - Version 1.1 change notes: Added selection for presentation project type.
  - - Move custom project plan to the first
  - Version 1.2 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
                change notes: new mobile and presentation project type flow. Removed old presentation flow.
  - Version 1.3 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
                Fix multiple bugs.
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep1" class="newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>Name and summarize your new project.</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step second -->
    <div class="stepSecond stepContainer">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
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
                    <dd>The project name and summary are meant to help you keep track of your
own project, and will not be shown to TopCoder members.
                    </dd>
                    <dd>If you are undecided on a name and description, you will be able to edit them later after you create the project.
                    </dd>
                </dl>
            </div>
            <!-- End .noteMask -->

            <!-- form -->
            <div class="form">

                <!-- row -->
                <div class="row projectName">

                    <a href="javascript:;" class="toolTip" id="toolTip1"
                       rel='&lt;p&gt;&lt;b&gt;60 characters maximum&lt;/b&gt;&lt;/p&gt;&lt;p&gt;Examples: "Acme Mobile App",&lt;/p&gt; &lt;p&gt;"2012 Biocomputing Symposium Keynote"&lt;/p&gt;'></a>
                    <label>Project Name <span class="red">*</span></label>
                    <input id="newProjectName" type="text" class="text" maxlength="60"/>
                    <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>

                    <div class="clear"></div>
                    <p class="message">
                        <span class="remaning"><span class="num">60</span> characters remaining.</span>
                    </p>

                </div>
                <!-- End .row -->

                <!-- row -->
                <div class="row descProject">

                    <a href="javascript:;" class="toolTip" id="toolTip2"
                       rel="&lt;p&gt;&lt;b&gt;A brief description of the project for your own use.&lt;/b&gt;&lt;/p&gt;&lt;p&gt;In subsequent steps, you will be able to provide detailed&lt;/p&gt; &lt;p&gt;instructions for use by our members when they participate on &lt;/p&gt;&lt;p&gt;your project.&lt;/p&gt;"></a>
                    <label>Project Description<span class="red">*</span></label>
                    <textarea id="newProjectDescription" rows="10" cols="10"></textarea>

                    <div class="clear"></div>
                    <p class="message">
                    </p>
                </div>
                <!-- End .row -->

            </div>
            <!-- End .form -->

            <!-- bottom bar -->
            <div class="BottomBar">
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
