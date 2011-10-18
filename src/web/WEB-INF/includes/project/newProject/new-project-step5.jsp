<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new project step 5.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1 v1.0)
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newProjectStep5" class="hide newProjectStep">
    <!-- step title -->
    <div class="stepTitle">
        <h3><span>5</span>Who can access your Project?</h3>
         <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFifth stepContainer">

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
                    <dd>Add users to your project to grant an access to them. Search for the user handle and add the
                        users to the list of authorized users.
                    </dd>
                </dl>
            </div>
            <!-- End .noteMask -->

            <!-- form -->
            <div class="form">

                <!-- table -->
                <div class="tableOut">

                    <table class="projectStats" border="0" cellpadding="0" cellspacing="0">
                        <colgroup>
                            <col width="295"/>
                            <col width="145"/>
                            <col width="145"/>
                            <col width="145"/>
                            <col width="145"/>
                        </colgroup>
                        <tr>
                            <th class="first">User Lists</th>
                            <th>Read</th>
                            <th>Write</th>
                            <th>Full Access</th>
                            <th class="last">Action</th>
                        </tr>
                        <tr>
                            <td><a href="javascript:;" class="searchUserHandle addUserButton"><span class="profileLeft">Add Users</span></a>
                            </td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                    </table>

                </div>
                <!-- End .tableOut -->

            </div>
            <!-- End .form -->

            <div class="form addUserPlan">

                <div id="projects">
                    <table cellspacing="0" cellpadding="0" class="projectStats notifications" id="notifications">
                        <thead>
                        <tr>
                            <th class="permCol first">User Lists</th>
                            <th class="permCol2">Read</th>
                            <th class="permCol2">Write</th>
                            <th class="permCol2">Full Access</th>
                            <th class="permCol2 last">&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody class="checkPermissions">
                        <tr class="applyForAll">
                            <td class="markRed permCol"><a href="javascript:;"
                                                           class="searchUserHandle deleteSelect"><span
                                    class="profileLeft">Delete Selected</span></a><a href="javascript:;"
                                                                                     class="searchUserHandle addMoreUsers"><span
                                    class="profileLeft">Add More Users</span></a></td>
                            <td class="checkbox"><input type="checkbox" class="selectUser" checked="checked"/></td>
                            <td class="checkbox"><input type="checkbox" class="selectUser"/></td>
                            <td class="checkbox"><input type="checkbox" class="selectUser"/></td>
                            <td></td>
                        </tr>
                        <tr id="userRowTemplate" class="hide">
                            <td class="permCol"><input type="checkbox" class="selectUserCheck" checked="checked"/>

                                <div class="group" id="userId{1}">{0}</div>
                            </td>
                            <td class="checkbox"><input type="checkbox" class="selectUser readPermissionSelect" checked="checked"/></td>
                            <td class="checkbox"><input type="checkbox" class="selectUser writePermissionSelect"/></td>
                            <td class="checkbox"><input type="checkbox" class="selectUser fullPermissionSelect"/></td>
                            <td class="checkbox"><a href="javascript:;" class="folderIcon"></a><a href="javascript:;"
                                                                                                  class="deleteIcon"></a>
                            </td>
                        </tr>



                        </tbody>
                    </table>
                </div>

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
    <!-- End .stepFirst -->

</div>
<!-- End #stepContainer -->