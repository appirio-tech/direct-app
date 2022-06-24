<%--
  - Author: TCSASSEMBLY, Ghost_141
  - Version: 1.2
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 6.
  - 
  - Version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence)
  -             change notes: Added id and class field for question elements.
  - 
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  -             Fix a text inconsistency bug.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep6" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3 class="question41"></h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step sixth -->
    <div class="stepSixth stepContainer">

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
                    <dd>Your project will have a private forum associated to it.  Use this screen to configure the topics you will have in your forum.  By default, one has been created for you.</dd>
                    <dd>The topic named project Discussion” cannot be removed.</dd>
                </dl>
            </div><!-- End .noteMask -->                                        

            <!-- form -->
            <div class="form"> 

                <!-- table -->
                <div class="tableOut" id="projects" data-intro="Your project will have a private forum that you can use for discussion with your team and Copilot(s). The forum will have Topics to help organize conversations. In this step, you can customize the Topics. You can also do this later from your project dashboard." data-step="1">

                    <table class="projectStats forums" border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="205" />
                            <col width="345" />
                            <col width="45" />
                        </colgroup>
                        <tr>
                            <th class="first">Project Forum Topics</th>
                            <th>Description</th>
                            <th class="last"></th> 
                        </tr>
                        <tr class="applyForAll">
                            <td class="markRed permCol">
                                <a class="downloadProfile deleteSelect" href="javascript:;">
                                    <span class="profileLeft">Delete Selected</span>
                                </a>
                                <a class="downloadProfile addForum" href="javascript:;">
                                    <span class="profileLeft">Add More Forums</span>
                                </a>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <c:forEach items="${viewData}" var="template">
                        <tr>
                            <td>
                                <span class="group predefined"><c:out value="${template.forumName}" /></span>
                            </td>
                            <td>
                                <span class="group"><c:out value="${template.forumDescription}" /></span>
                            </td>
                            <td class="checkbox">
                            </td>
                        </tr>
                        </c:forEach>
                    </table>

                </div>
                <!-- End .tableOut --> 

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
