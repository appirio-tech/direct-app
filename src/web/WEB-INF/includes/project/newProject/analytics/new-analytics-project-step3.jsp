<%--
  - Author: minhu
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 3rd step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep3" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>3</span>About the Problem</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    <div class="stepContainer step4Container">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

                <div class="step4">
                    <p class="intro">All fields marked with <span class="red">*</span> are mandatory</p>
                    <div class="row">
                        <label>Please provide a high level overview of the problem that needs to be solved. <span class="red">*</span></label>
                        <textarea cols="10" rows="10" autocomplete="off"></textarea>
                         <span class="errorIcon"></span>
                         <p class="errorTxt">This field cannot be left empty.</p>
                    </div>
                    <table cellpadding="0"  cellspacing="0">
                        <colgroup>
                            <col width="50%"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <td class="left">
                                    <div class="title">
                                        <a rel="&lt;p&gt; If choose Yes, please list the metrics that are essential to evaluate the quality of a solution.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                        <label>Do you know the metrics that are essential to evaluate the quality of a <br/>solution to the described problem</label>
                                    </div>
                                    <div class="radios">
                                        <input type="radio" name="metrics" id="metricsYes" value="yes" autocomplete="off"/><label for="metricsYes">Yes</label>
                                        <input type="radio" name="metrics" id="metricsNo" value="no"  checked="checked" autocomplete="off"/><label for="metricsNo">No</label>
                                    </div>
                                    <div class="yesWrapper hide">
                                        <label>Please list the metrics</label>
                                        <textarea cols="10" rows="10" autocomplete="off"></textarea>
                                    </div>
                                </td>
                                <td class="right">
                                    <label>Example:</label>
                                    <p>
                                        <em>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus a leo nunc. Nulla dictum hendrerit molestie. Vivamus semper leo et mi aliquam id auctor odio mollis. Curabitur porttitor malesuada sodales. Donec cursus, erat sed dapibus euismod, quam metus mattis risus, non</em>
                                    </p>
                                    <label>More help information can be shown here</label>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus a leo nunc. Nulla dictum hendrerit molestie. Vivamus semper leo et mi aliquam id auctor odio mollis. Curabitur porttitor malesuada sodales. Donec cursus, erat sed dapibus euismod, quam metus mattis risu</p>
                                    <a class="more" href="#"><strong>Read more</strong></a>
                                </td>
                            </tr>
                            <tr >
                                <td>
                                    <div class="leftMask">
                                        <div class="title">
                                            <a rel="&lt;p&gt; If choose Yes, please list the 'fields of knowledge'.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                                            <label>Do you know the 'fields of knowledge' that one needs to have a deep <br/>knowledge of in order to be successful in solving the problem.</label>
                                        </div>
                                        <div class="radios">
                                            <input type="radio" name="knowledge" id="knowledgeYes" value="yes" autocomplete="off"/><label for="knowledgeYes">Yes</label>
                                            <input type="radio" name="knowledge" id="knowledgeNo" value="no"  checked="checked" autocomplete="off"/><label for="knowledgeNo">No</label>
                                        </div>
                                        <div class="addKnowledge hide">
                                            <label>Field of Knowledge:</label>
                                            <div class="field">
                                                <input type="text" class="text tipIt" maxlength="30" autocomplete="off"/>
                                                <a class="smallRedBtn" href="javascript:;">ADD</a>
                                                <div class="clear"></div>
                                                <p class="fieldMessage">
                                                <span class="remaning"><span class="num">30</span> characters remaining.</span>
                                                <span class="errorText">This field cannot be empty.</span>
                                                </p>
                                            </div>
                                            <p>This could be any fields related to your problem, by listing the fields of knowledge you can <br/>give idea to the community on what the problem depends on</p>
                                        </div>
                                    </div>
                                   <div class="currentSelect hide">
                                            <label>Currently Selected:</label>
                                            <p>
                                            </p>
                                        </div>
                                </td>

                                <td class="right">
                                    <label>
                                        For example:
                                    </label>
                                    <p>
                                        Take a look at the scenario below
                                    </p>
                                    <p>
                                        <em><span>Here we describe a simple sample problem and then show them what the fields of knowledge required to solve this problem</span> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus a leo nunc. Nulla dictum hendrerit molestie. Vivamus semper leo et mi aliquam id auctor odio mollis. Curabitur porttitor malesuada sodales. Donec cursus, erat sed dapibus euismod, quam metus mattis risu</em>
                                    </p>
                                    <label>Fields of knowledge:</label>
                                    <p>
                                        For the above probelm we have identified the below fields of knowledge
                                    </p>
                                    <div class="fieldList">
                                        <span>Field1</span>
                                        <span>Field2</span>
                                        <span>Field3</span>
                                    </div>
                                </td>
                        </tbody> 
                    </table>
            </div>

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