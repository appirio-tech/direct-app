<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project milestones list view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <!--[if IE]>
        <style type="text/css">

            .multiMilestones table td input,.newOutLay input.text{
                line-height:22px;
            }

        </style>
    <![endif]-->
    <ui:projectPageType tab="milestone"/>
    <link rel="stylesheet" href="/css/dashboard-view.css?v=212459" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/projectMilestone.css?v=215476" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <script type="text/javascript" src="/scripts/projectMilestone.js?v=214829"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="projectMilestoneListViewPage">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>'><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong>Milestones</strong>
                            </div>

                            <div class="spaceWhite"></div>

                            <div class="multiMilestones">
                            <div class="topLine">
                                <h2>Add Project Milestones</h2>
                                <a class="button6" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/><s:param name="formData.viewType">list</s:param></s:url>"><span class="left"><span class="right">CANCEL</span></span></a>
                                <a class="button6 saveButton" href="javascript:;"><span class="left"><span class="right">SAVE</span></span></a>
                            </div>
                            <!-- End .topLine -->

                            <table>
                                <colgroup>
                                    <col/>
                                    <col/>
                                    <col width="120px"/>
                                    <col width="146px"/>
                                </colgroup>
                                <thead>

                                <tr>
                                    <th>Project Milestone Name &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</th>
                                    <th>Project Milestone Description</th>
                                    <th>Due Date</th>
                                    <th>People Responsible</th>
                                </tr>
                                </thead>
                                <tbody>

                                    <s:iterator status="stat" value="{1,2,3,4,5,6,7,8,9,10}" >
                                        <tr>
                                            <td>
                                                <input type="text" name="projectName"/>
                                            </td>
                                            <td>
                                                <input type="text" name="projectDesc"/>
                                            </td>
                                            <td class="dueDate">
                                                <input type="text" value="mm/dd/yyyy" readonly="readonly" class="tip" name="projectDuedate"/>
                                            </td>
                                            <td>
                                                <select name="projectRes">
                                                    <option value="-1">Unassigned</option>
                                                    <s:iterator value="viewData.responsiblePersons">
                                                        <option value="${userId}">${name}</option>
                                                    </s:iterator>
                                                </select>
                                            </td>
                                        </tr>

                                    </s:iterator>

                                </tbody>
                            </table>
                            <!-- End table -->

                            <div class="bottomLine">
                                                                <span class="errorMsg hide">
                                                                    The highlight fields can not be empty.
                                                                </span>
                                <a class="button6" href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/><s:param name="formData.viewType">list</s:param></s:url>"><span class="left"><span class="right">CANCEL</span></span></a>
                                <a class="button6 saveButton" href="javascript:;"><span class="left"><span class="right">SAVE</span></span></a>
                            </div>
                            <!-- End .topLine -->
                            </div>


                        </div>
                        <!-- end area1 -->
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