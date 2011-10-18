<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 1.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep1" class="newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>1</span>What is the name of the project?</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFirst stepContainer">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- form -->
            <div class="form">

                <!-- row -->
                <div class="row projectName">

                    <a href="javascript:;" class="toolTip" id="toolTip1"
                       rel='&lt;p&gt;&lt;b&gt;60 characters max.&lt;/b&gt;&lt;/p&gt;&lt;p&gt;Examples: "XYZ System Upgrade",&lt;/p&gt; &lt;p&gt;"ABC Refactoring", "XYZ Website" etc.&lt;/p&gt;'></a>
                    <label>Project Name <span class="red">*</span></label>
                    <input id="newCockpitProjectName" type="text" class="text" maxlength="60"/>
                    <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>

                    <div class="clear"></div>
                    <p class="message">
                        <span class="remaning"><span class="num">60</span> char remained.</span>
                    </p>

                </div>
                <!-- End .row -->

                <!-- row -->
                <div class="row descProject">

                    <a href="javascript:;" class="toolTip" id="toolTip2"
                       rel="&lt;p&gt;&lt;b&gt;A short description about the project,&lt;/b&gt; such as:&lt;/p&gt;&lt;p&gt;Is this a new application or an upgrade of an existing application ?&lt;/p&gt; &lt;p&gt;What is the scope of your project ?&lt;/p&gt;&lt;p&gt;What application do you want to build ?&lt;/p&gt;&lt;p&gt;What is your main objective for building the application ?&lt;/p&gt;"></a>
                    <label>Project Description<span class="red">*</span></label>
                    <textarea id="newCockpitProjectDescription" rows="10" cols="10"></textarea>

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