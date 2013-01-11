<%--
  - Author: Ghost_141, TCSASSEMBLER
  - Version: 1.3
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides function of creating project in dashboard.
  -
  - Version 1.1 (TC Direct UI Improvement Assembly 1) changes: Solve "an error has occurred when try to save new project without any input".
  - Version 1.2 (TC Direct UI Improvement Assembly 2) changes: Solve "Choosing Create A New Project but Calendar is highlighted".
  - Version 1.3 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes: Fix multiple bugs.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="createProject"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">Create a New Project</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <div class="message">
                                <div class="messageTop">
                                    <div class="messageBottom">
                                        <div class="messageLeft">
                                            <div class="messageRight">
                                                <div class="messageTopLeft">
                                                    <div class="messageTopRight">
                                                        <div class="messageBottomLeft">
                                                            <div class="messageBottomRight">
                                                                <div class="messageInner">
                                                                    <p>A project is a container that could have many
                                                                        contests from various type of works (Studio,
                                                                        software architecture, etc.).<br/>
                                                                        You usually use project to group and manage all
                                                                        of your contests in easy ways.</p>
                                                                </div>
                                                                <!-- .messageInner -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .message -->

                            <div class="form">
                                <s:form action="createProjectAndContest" namespace="/" method="post" name="CreateProjectForm">
                                <p>
                                    <label for="projectName">Project Name:</label>
                                    <s:textfield name="formData.name" id="projectName"/>
                                </p>

                                <p>
                                    <label for="projectDesc">Project Description:</label>
                                    <s:textarea cols="50" rows="6" name="formData.description" id="projectDesc"/>
                                </p>

                                <div class="buttons">
                                    <link:cancelDashboardOperation/>
                                    <a id="addExistContest"
                                       class="button6 middleButton">
                                        <span class="left"><span class="right">ADD A CONTEST TO THIS PROJECT</span></span></a>
                                    <a
                                       class="button6 rightButton" id="createProjectViewSave"><span class="left"><span class="right">SAVE</span></span></a>
                                </div>
                                <!-- End .buttons -->
                                </s:form>
                            </div>
                            <!-- End .form -->

                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
