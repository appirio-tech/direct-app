<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
  - - Set read only in date pick element.
  - 
  - Description: This page provides the demonstration of create, update, delete and retrieval project milestones.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!-- the tab does not exist because it's demo -->
    <ui:projectPageType tab="metadata"/>
    <script type="text/javascript" src="/scripts/projectMilestoneDemo.js?v=213183"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" style="min-width: 1500px">

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

                            <div id="metdataInputSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                    <span>Project Milestone Batch Creation </span>
                                    <a style="font-size: 13px; color:red" href="projectMilestoneBatchCreatePage">Click
                                        Here</a>
                                </div>


                                <s:iterator value="predefinedKeys">
                                    <div style="padding-top:8px; padding-left:20px;">
                                    <span>
                                        <s:property value="name"/> (
                                        <s:if test="single">
                                            Single Value
                                        </s:if>
                                        <s:else>
                                            Multiple Values
                                        </s:else>
                                        )
                                    </span>
                                        <input type="hidden" class="metadataKeyID" value='<s:property value="id"/>'/>
                                        <s:if test="predefinedValues.size == 0">
                                            <input type="text" class="metadataValue"
                                                   style="width:250px;margin-left:20px"/>
                                        </s:if>
                                        <s:else>
                                            <s:select label="Choose the value"
                                                      name="metadataListValue"
                                                      list="predefinedValues"
                                                      listKey="predefinedMetadataValue"
                                                      listValue="predefinedMetadataValue"
                                                      multiple="false"
                                                      size="1"
                                                      cssStyle="width:300px"
                                                    />
                                        </s:else>
                                        <input class="add" type="button" style="margin-left:10px" value="ADD"/>
                                    </div>
                                </s:iterator>


                            </div>

                            <div class="milestoneAddSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                    <span>Add Project Milestone  </span>
                                </div>

                                <div style="padding-left:20px">

                                    <div style="padding-top:20px">
                                        <span>Milestone Name:</span>
                                        <input type="text" name="milestoneName" size="50"/>
                                    </div>

                                    <div style="padding-top:20px">
                              <span>
                                  Milestone Description:</span>
                                        <input type="text" name="milestoneDescription" size="100"/>
                                    </div>

                                    <div style="padding-top:20px">
                                        <span>Milestone Due Date:</span> </br></br>
                                        <input name='dueDate' type='text' readonly="readonly" class='date-pick'/>
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
                                        <input type="button" id="addMilestoneButton" value="Add Milestone"/>
                                    </div>
                                </div>
                            </div>

                            <div id="projectMilestonesSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                    <span>Project Milestones : <span class="selectedProjectName"> </span></span>
                                </div>

                                <table style="margin-top:20px; margin-left: 20px;" border="1">

                                    <thead>
                                    <th style="width:30px">ID</th>
                                    <th>Milestone Name</th>
                                    <th>Milestone Description</th>
                                    <th>Milestone Due Date</th>
                                    <th>Responsible Person</th>
                                    <th>Notification</th>
                                    <th>Completed</th>
                                    <th>Status</th>
                                    <th style="width:140px">Operations</th>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>

                                <input type="button" name="deleteAll" value="Delete All Milestones" style="margin-top:20px; float:right; margin-right:50px;"/>
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
