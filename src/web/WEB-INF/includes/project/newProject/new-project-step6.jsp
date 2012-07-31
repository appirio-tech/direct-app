<%--
  - Author: TCSASSEMBLY
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 6.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep6" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>6</span>Manage Forums</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
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
                    <dd>Create custom forums with description or remove the forums already in the list.</dd>
                    <dd>Forum 'Project Requirements' can not be deleted.</dd>
                </dl>
            </div><!-- End .noteMask -->                                        

            <!-- form -->
            <div class="form"> 

                <!-- table -->
                <div class="tableOut" id="projects">

                    <table class="projectStats forums" border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="205" />
                            <col width="345" />
                            <col width="45" />
                        </colgroup>
                        <tr>
                            <th class="first">Forum Lists</th>
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
                                <span class="group predefined">${template.forumName}</span>
                            </td>
                            <td>
                                <span class="group">${template.forumDescription}</span>
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
