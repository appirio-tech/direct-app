<%--
  - Author: TCSASSEMBLER
  - Version: 1.0.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the demonstration of batch creation of project milestones.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!-- the tab does not exist because it's demo -->
    <ui:projectPageType tab="milestone"/>
    <script type="text/javascript" src="/scripts/projectMilestoneDemo.js?v=213183"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="batchCreationPage" style="min-width: 1300px">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->

                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">
                                    Direct Project Milestone Demo
                                </h2>
                            </div>
                            <!-- End .areaHeader -->
                            <span style="font-size:13px;padding-right:8px;color:#f02020; font-weight:bold">Please choose a project to manage its milestones:</span>
                            <s:select label="Choose a project for milestone management"
                                      name="directProjectInput"
                                      list="viewData.projects"
                                      listKey="data.projectId"
                                      listValue="data.projectName"
                                      multiple="false"
                                      size="1"
                                      cssStyle="width:400px"
                                    />


                            <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                <span>Batch Add Project Milestones  </span>
                            </div>

                            <div class="milestoneAddSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px;width: 300px;float: left">


                                <div style="padding-left:20px">

                                    <div style="padding-top:20px">
                                        <span>Milestone Name:</span>
                                        <input type="text" name="milestoneName" width="400px"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Description:</span>
                                        <input type="text" name="milestoneDescription" width="600px"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Due Date:</span> </br></br>
                                        <input name="dueDate" type='text' class='date-pick'/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Notification:</span>
                                        <select name="notificationChoice">
                                            <option value="true">Yes</option>
                                            <option value="false">No</option>
                                        </select>
                                    </div>


                                    <div class="addResponsiblePerson" style="padding-top:20px">
                                        <span>Responsible Person:</span>
                                    </div>

                                </div>
                            </div>


                            <div class="milestoneAddSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px;width: 300px;float:left">


                                <div style="padding-left:20px">

                                    <div style="padding-top:20px">
                                        <span>Milestone Name:</span>
                                        <input type="text" name="milestoneName" width="400px"/>
                                    </div>

                                    <div style="padding-top:20px">
                              <span>
                                  Milestone Description:</span>
                                        <input type="text" name="milestoneDescription" width="600px"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Due Date:</span> </br></br>
                                        <input name='dueDate' type='text' class='date-pick'/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Notification:</span>
                                        <select name="notificationChoice">
                                            <option value="true">Yes</option>
                                            <option value="false">No</option>
                                        </select>
                                    </div>


                                    <div class="addResponsiblePerson" style="padding-top:20px">
                                        <span>Responsible Person:</span>
                                    </div>

                                </div>
                            </div>


                            <div class="milestoneAddSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px;width: 300px;float:left">

                                <div style="padding-left:20px">

                                    <div style="padding-top:20px">
                                        <span>Milestone Name:</span>
                                        <input type="text" name="milestoneName" width="400px"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Description:</span>
                                        <input type="text" name="milestoneDescription" width="600px"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Due Date:</span> </br></br>
                                        <input name='dueDate' type='text' class='date-pick'/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Notification:</span>
                                        <select name="notificationChoice">
                                            <option value="true">Yes</option>
                                            <option value="false">No</option>
                                        </select>
                                    </div>


                                    <div class="addResponsiblePerson" style="padding-top:20px">
                                        <span>Responsible Person:</span>
                                    </div>
                                    <div style="padding-top:20px">
                                        <input type="button" id="batchCreateMilestoneButton"
                                               value="Add All Milestones"/>
                                    </div>
                                </div>
                            </div>


                        </div>
                        <!-- End .container2 -->
                    </div>
                </div>

            </div>
            <!-- End #mainContent -->

            <jsp:include page="../includes/footer.jsp"/>

        </div>
        <!-- End #content -->
    </div>
    <!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
