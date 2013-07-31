<%--
  - Author: Ghost_141, GreatKevin
  - Version: 1.4
  - Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
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
  - Version 1.4 (Release Assembly - TC Cockpit Start Project Flow Billing Account Integration)
  - - Add project billing account is step 1 of new project creation flow
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
                    <dd>
                        You can choose the billing account used for this new project in the project billing account. If
                        you don't have a billing account, the project created will be in DRAFT status until you
                        associate a billing account to it to activate it. You can always associate a billing account to
                        your project in Edit Project page. If you don't have a billing account, please contact your
                        TopCoder Account Manager
                    </dd>
                </dl>
            </div>
            <!-- End .noteMask -->

            <!-- form -->
            <div class="form">

                <!-- row -->
                <div class="row projectName" data-intro="The name of your project will be private to you and your project team. Enter something that helps you identify the project. Competitions will later be added to this project." data-step="1">

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
                <div class="row descProject" data-intro="The desciption is also private to your project team. This will NOT be presented to the community." data-step="2">

                    <a href="javascript:;" class="toolTip" id="toolTip2"
                       rel="&lt;p&gt;&lt;b&gt;A brief description of the project for your own use.&lt;/b&gt;&lt;/p&gt;&lt;p&gt;In subsequent steps, you will be able to provide detailed&lt;/p&gt; &lt;p&gt;instructions for use by our members when they participate on &lt;/p&gt;&lt;p&gt;your project.&lt;/p&gt;"></a>
                    <label>Project Description<span class="red">*</span></label>
                    <textarea id="newProjectDescription" rows="10" cols="10"></textarea>

                    <div class="clear"></div>
                    <p class="message">
                    </p>
                </div>
                <!-- End .row -->

                <!-- row -->
                <div class="row projectBilling" data-intro="Billing Accounts refer to your specific license agreement. Every project and competition will need a billing account. If you don't have one yet, you don't need to set it here. However, before you can run any competitions you will need to edit this project and set the billing account." data-step="3">

                    <a href="javascript:;" class="toolTip" id="toolTip3"
                       rel="&lt;p&gt;&lt;b&gt;The billing account used for your new project&lt;/b&gt;&lt;/p&gt;&lt;p&gt;
                       You need to set a billing account to activate your new project&lt;/p&gt;
                       &lt;p&gt;If billing account is not set, your new project will be in draft status&lt;/p&gt;
                       ">
                    </a>
                    <label>Project Billing Account<span class="red">*</span></label>
                    <select id="projectBillingAccount">
                        <option value="-1">--Please select a billing account--</option>
                        <option value="0">I don't have a billing account</option>
                        <s:iterator value="availableBillingAccounts">
                            <option value="<s:property value='id'/>"><s:property value='name'/></option>
                        </s:iterator>
                    </select>
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
