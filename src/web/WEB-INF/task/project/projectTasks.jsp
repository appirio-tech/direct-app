<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project tasks view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../../includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css?v=214041"/>
    <![endif]-->

    <ui:projectPageType tab="tasks"/>

    <link rel="stylesheet" href="/css/direct/projectTasks.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jsrender-min.js"></script>
    <script type="text/javascript" src="/scripts/projectTasks.js"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
    <script id="newTaskTemplate" type="text/x-jsrender">
        <div class="taskItem">
            <div class="taskItemHeader">
                <div class="checkBoxWrapper fLeft"><input name="taskListItem" type="checkbox" value="task1" />
                    <label class="priorityLabel priorityNormal">{{:name}}</label></div>
                <a href="javascript:;" class="fRight taskItemLink taskMoreDetailsLink">More Details</a>
                <a href="javascript:;" class="fRight taskItemLink taskDeleteLink">Delete</a>
                <a href="javascript:;" class="fRight taskItemLink taskEditLink">Edit</a>
            </div>
            <div class="taskItemContent">
                <div class="leftCol">
                    <div class="col1">
                        <label>Assigned to</label>
                        {{if assignees}}
                            {{for assignees}}
                                <p>
                                {{:~getUserLink(userId)}}
                                </p>
                            {{/for}}
                        {{/if}}

                    </div>
                    <div class="col2">
                        <label>Status</label>
                        <p class="statusText{{:~getStatusId(status)}}">{{:~getStatusName(status)}}</p>
                    </div>
                    <div class="col3">
                        <label>Start</label>
                        <p class="dateTxt">{{:startDate}}</p>
                    </div>
                    <div class="col4 {{if overdue}}isOverdue{{/if}}">
                        <div>
                            <label>Due</label>
                            <p class="dateTxt">{{:dueDate}}</p>
                        </div>
                        <span class="overDueAlert">Overdue</span>
                    </div>
                    <div class="col5">
                        <label>Note</label>
                        <p class="noteTxt">{{>notes}}</p>
                    </div>
                </div>
                <div class="rightCol">
                    <div class="col6">
                        <label>Files(s)</label>
                        <ul class="taskAttachmentUl">

                        </ul>
                    </div>
                    <div class="col7">
                        <label>Associate With</label>

                    </div>
                </div>
                <div class="clearLeftFix"></div>
            </div>
        </div>
    </script>
</head>

<body id="page" class="taskPage">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="../../includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="../../includes/right.jsp"/>

<div id="area1">
<!-- the main area -->
<div class="area1Content">

<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
    &gt;
    <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
    <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
            value="sessionData.currentProjectContext.name"/></a> &gt;
    <strong>Tasks</strong>
</div>

<div class="addTaskPanel">
    <div class="panelHeader">
        <h3>ADD A TASK</h3>
        <a href="javascript:;" class="jsAddTaskDetailedPopup"><span><span>More Details</span></span></a>
    </div>
    <div class="panelContent">
        <form action="">
            <div class="col1 fLeft">
                <input type="text" class=" textInput" value="Task Name" name="taskName" id="quickTaskName" />
            </div>
            <div class="col2 fLeft">

                <s:select list="taskStatuses" name="quickTaskStatus" id="quickTaskStatus" cssClass="fLeft" />

                <%--<select name="quickTaskStatus" id="quickTaskStatus" class="fLeft">--%>
                    <%--<option value="s0">Not Started</option>--%>
                    <%--<option value="s1">In Progress</option>--%>
                    <%--<option value="s2">Waiting on Dependency</option>--%>
                    <%--<option value="s3">Completed</option>--%>
                <%--</select>--%>
            </div>
            <div class="col3 fLeft">
                <input type="text" name="quickStartDate" readonly="readonly" id="quickStartDate" class="fLeft text task-date-pick" value="Start Date"/>
                <input type="text" name="quickDueDate" readonly="readonly" id="quickDueDate" class="fLeft text task-date-pick" value="Due Date"/>
            </div>
            <div class="col4 fLeft">
                <div class="taskMultiSelector fLeft">
                    <div class="trigger"><a class="msValue" href="javascript:;" title="">Assign to User</a><a class="selectorArrow"></a><label class="hidden">Assign to User</label></div>
                    <div class="dropDown">
                        <ul>

                            <s:iterator value="taskUserIds" var="userId">
                                <li><input name="quickAssignUser" type="checkbox" value="${userId}" /><link:user userId="${userId}"/></li>
                            </s:iterator>

                            <%--<li><input name="quickAssignUser" type="checkbox" value="u1" /><label class="coderTextRed">Username 1</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u2" /><label class="coderTextRed">Username 2</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u3" /><label class="coderTextGreen">Username 3</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u4" /><label class="coderTextGreen">Username 4</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u5" /><label class="coderTextOrange">Username 5</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u6" /><label class="coderTextOrange">Username 6</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u7" /><label class="coderTextRed">Username 7</label></li>--%>
                            <%--<li><input name="quickAssignUser" type="checkbox" value="u8" /><label class="coderTextRed">Username 8</label></li>--%>
                        </ul>
                        <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
                    </div>
                </div>
            </div>
            <div class="col5 fLeft">
                <select name="quickAssignTask" id="quickAssignTask" class="fLeft">
                    <option value="${defaultProjectTask.id}" selected="selected">${defaultProjectTask.name}</option>
                </select>
            </div>
        </form>
        <a class="buttonRed1 quickAddTaskBtn" href="javascript:;"><span><i></i>ADD</span></a>
    </div>
    <div class="corner cornerBl"></div>
    <div class="corner cornerBr"></div>
</div>
<!--end .addTaskPanel-->

<div class="taskListContainer">
<div class="taskListFilter">
    <p class="fieldLabel">Task name:</p>
    <div class="searchTaskByName">
        <input type="text" class="searchBox" />
    </div>
    <p class="fieldLabel">Assign to:</p>
    <select name="filterAssignedTo" id="filterAssignedTo">
        <option value="fa0">Any</option>
        <option value="fa1">Username 1</option>
        <option value="fa2">Username 2</option>
        <option value="fa3">Username 3</option>
    </select>
    <p class="fieldLabel">Due Date</p>
    <div class="dueDateFilter">
        <input type="text" name="filterStartDate" readonly="readonly" id="filterStartDate" class="fLeft text task-date-pick" value=""/>
        <span class="fLeft"> To </span>
        <input type="text" name="filterDueDate" readonly="readonly" id="filterDueDate" class="fLeft text task-date-pick" value=""/>
    </div>
    <p class="fieldLabel">Priority:</p>
    <ul id="filterByPriority">
        <li><input name="priorityFilter" type="checkbox" value="pf1" /><label class="priorityLabel priorityHigh">High</label></li>
        <li><input name="priorityFilter" type="checkbox" value="pf2" /><label class="priorityLabel priorityNormal">Normal</label></li>
        <li><input name="priorityFilter" type="checkbox" value="pf3" /><label class="priorityLabel priorityLow">Low</label></li>
    </ul>
    <p class="fieldLabel">Status:</p>
    <ul id="filterByStatus">
        <li><input name="statusFilter" type="checkbox" value="sf0" /><label>Not Started</label></li>
        <li><input name="statusFilter" type="checkbox" value="sf1" /><label>In Progress</label></li>
        <li><input name="statusFilter" type="checkbox" value="sf2" /><label>Waiting on Dependency</label></li>
        <li><input name="statusFilter" type="checkbox" value="sf3" /><label>Completed</label></li>
    </ul>
    <p class="fieldLabel">Associated With Project Milestone</p>
    <div class="taskMultiSelector" id="filerByPM">
        <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select</a><a class="selectorArrow"></a><label class="hidden">Select</label></div>
        <div class="dropDown">
            <ul>
                <li><input name="milestoneFilter" type="checkbox" value="mf1" /><label>Milestone Name</label></li>
                <li><input name="milestoneFilter" type="checkbox" value="mf2" /><label>Milestone Name</label></li>
                <li><input name="milestoneFilter" type="checkbox" value="mf3" /><label>Milestone Name</label></li>
                <li><input name="milestoneFilter" type="checkbox" value="mf4" /><label>Milestone Name</label></li>
                <li><input name="milestoneFilter" type="checkbox" value="mf5" /><label>Milestone Name</label></li>
            </ul>
            <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
        </div>
    </div>
    <p class="fieldLabel">Associated With Contests</p>
    <div class="taskMultiSelector" id="filterByContests">
        <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select</a><a class="selectorArrow"></a><label class="hidden">Select</label></div>
        <div class="dropDown">
            <ul>
                <li><input name="contestFilter" type="checkbox" value="cf1" /><label>Contest Name</label></li>
                <li><input name="contestFilter" type="checkbox" value="cf2" /><label>Contest Name</label></li>
                <li><input name="contestFilter" type="checkbox" value="cf3" /><label>Contest Name</label></li>
                <li><input name="contestFilter" type="checkbox" value="cf4" /><label>Contest Name</label></li>
                <li><input name="contestFilter" type="checkbox" value="cmf5" /><label>Contest Name</label></li>
            </ul>
            <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
        </div>
    </div>
    <div class="corner cornerTl"></div>
    <div class="corner cornerTr"></div>
    <div class="corner cornerBl"></div>
    <div class="corner cornerBr"></div>
</div>
<!-- End  .taskListFilter -->
<div class="taskListWrapper">
<div class="taskListControlPanel">
    <h2>Task List</h2>

    <div class="taskListSummary">
        <div class="progressBarBg jsProgress">
            <div class="progressBar">
                <div class="progressBarLeft">
                    <div class="progressBarRight">
                        <div class="progressBarMid jsProgressNum"></div>
                    </div>
                </div>
            </div>
        </div>
        <p class="summaryTxt"><span class="completedNum allCompletedNum">0</span> out of <span class="totalNum allTotalNum">1</span> completed across 1 Task List</p>
        <p class="emptyTxt hide">No Task on this project</p>
    </div>

    <div class="taskListControlArea">
        <label class="fLeft">View:</label>
        <select name="switchTaskList" id="switchTaskList" class="fLeft">
            <option value="ptAll">All Task Lists</option>
            <option value="pt0">Project Tasks</option>
            <option value="pt1">Tasks List1</option>
            <option value="pt2">Tasks List2</option>
        </select>
        <label class="fLeft">Group by:</label>
        <select name="switchTaskGroupBy" id="switchTaskGroupBy" class="fLeft">
            <option>Task List Name</option>
            <option>Due Date</option>
        </select>
        <a class="buttonRed1 fRight addTaskList jsAddTaskList" href="javascript:;"><span><i></i>ADD A TASK LIST</span></a>
    </div>
    <div class="corner cornerTl"></div>
    <div class="corner cornerTr"></div>
    <div class="corner cornerBl"></div>
    <div class="corner cornerBr"></div>
</div>
<!-- End .taskListControlPanel -->

<div class="groupByTaskList">
<div class="taskListPanel <s:if test='defaultProjectTask.tasks.size() > 0'>hide</s:if>" id="empty">
    <div class="taskListPanelHeader">
        <div class="taskListPanelHeaderRight">
            <div class="taskListPanelHeaderInner">
                <h3 class="defaultList fLeft">Project Tasks</h3>
                <a class="buttonGray addNewTaskToList fRight" href="javascript:;"><span><i></i>Add New Task</span></a>
            </div>
        </div>
    </div>
    <div class="taskListContent">
        <div class="emptyHint ">
            <p>This is your default task list for this project, you can add tasks by clicking on “<strong>Add New Task</strong>” button, you can quickly add task by using the top section. “<strong>More details button</strong>” allows you to input other infomation about the task.</p>
            <p>You can also create new task list by clicking on “<strong>Add a Task List</strong>” button.</p>
            <h4>Have questions or need help?</h4>
            <div class="linkswrapper">
                <a href="javascript:;" class="taskListHelpLink">Tasks FAQs</a>  &nbsp;|&nbsp;
                <a href="javascript:;" class="taskListHelpLink">Tasks Video</a>  &nbsp;|&nbsp;
                <a href="javascript:;" class="taskListHelpLink">Tasks Tutorials</a>
            </div>
        </div>
    </div>
    <div class="corner cornerBl"></div>
    <div class="corner cornerBr"></div>
</div>

<div class="taskListPanel <s:if test='defaultProjectTask.tasks == null || defaultProjectTask.tasks.size() == 0'>hide</s:if>" id="pt0">
<div class="taskListPanelHeader">
    <div class="taskListPanelHeaderRight">
        <div class="taskListPanelHeaderInner">
            <h3 class="defaultList fLeft">Project Tasks</h3>
            <a class="buttonGray resolveTaskList fRight" href="javascript:;"><span><i></i>Resolve</span></a>
            <a class="buttonGray deleteTaskList fRight" href="javascript:;"><span><i></i>Delete</span></a>
            <a class="buttonGray editTaskList fRight" href="javascript:;"><span><i></i>Edit</span></a>
            <a class="buttonGray addNewTaskToList fRight" href="javascript:;"><span><i></i>Add New Task</span></a>
        </div>
    </div>
</div>
<div class="taskListContent">
<div class="taskListInfo">
    <%--<p class="associatedInfo"><strong>Associated with :</strong> <a href="javascript:;">Contest Name 1</a>, <a href="javascript:;">Project Milestone 1</a></p>--%>
    <div class="taskListProgress">
        <div class="smallProgressBarBg fRight jsProgress">
            <div class="smallProgressBar">
                <div class="smallProgressBarLeft">
                    <div class="smallProgressBarRight">
                        <div class="smallProgressBarMid jsProgressNum"></div>
                    </div>
                </div>
            </div>
        </div>
        <p class="fRight"><span class="completedNum">0</span> out of <span class="totalNum"><s:property value="defaultProjectTask.tasks.size()"/></span> Completed</p>
    </div>
    <p class="taksListBrief"><s:property value="defaultProjectTask.notes"/></p>
</div>
<!--end .taskListInfo-->

<s:iterator value="defaultProjectTask.tasks">
    <div class="taskItem">
        <div class="taskItemHeader">
            <div class="checkBoxWrapper fLeft"><input name="taskListItem" type="checkbox" value="task1" />
                <label class="priorityLabel priority<s:property value="priority.name"/>">${name}</label></div>
            <a href="javascript:;" class="fRight taskItemLink taskMoreDetailsLink">More Details</a>
            <a href="javascript:;" class="fRight taskItemLink taskDeleteLink">Delete</a>
            <a href="javascript:;" class="fRight taskItemLink taskEditLink">Edit</a>
        </div>
        <div class="taskItemContent">
            <div class="leftCol">
                <div class="col1">
                    <label>Assigned to</label>
                    <s:iterator value="assignees">
                        <p><link:user userId="${userId}"/></p>
                    </s:iterator>

                </div>
                <div class="col2">
                    <label>Status</label>
                    <p class="statusText<s:property value="status.id"/>"><s:property value="status.name"/></p>
                </div>
                <div class="col3">
                    <label>Start</label>
                    <p class="dateTxt"><s:date name="startDate" format="dd MMMMMMM"/></p>
                </div>
                <div class="col4 <s:if test='overdue'>isOverdue</s:if>">
                    <div>
                        <label>Due</label>
                        <p class="dateTxt"><s:date name="dueDate" format="dd MMMMMMM"/></p>
                    </div>
                    <span class="overDueAlert">Overdue</span>
                </div>
                <div class="col5">
                    <label>Note</label>
                    <p class="noteTxt"><s:property value="status.notes"/></p>
                </div>
            </div>
            <div class="rightCol">
                <div class="col6">
                    <label>Files(s)</label>
                    <ul class="taskAttachmentUl">

                    </ul>
                </div>
                <div class="col7">
                    <label>Associate With</label>

                </div>
            </div>
            <div class="clearLeftFix"></div>
        </div>
    </div>
</s:iterator>

<div class="emptyHint hide">
    <p>This is your default task list for this project, you can add tasks by clicking on “<strong>Add New Task</strong>” button, you can quickly add task by using the top section. “<strong>More details button</strong>” allows you to input other infomation about the task.</p>
    <p>You can also create new task list by clicking on “<strong>Add a Task List</strong>” button.</p>
    <h4>Have questions or need help?</h4>
    <div class="linkswrapper">
        <a href="javascript:;" class="taskListHelpLink">Tasks FAQs</a>  &nbsp;|&nbsp;
        <a href="javascript:;" class="taskListHelpLink">Tasks Video</a>  &nbsp;|&nbsp;
        <a href="javascript:;" class="taskListHelpLink">Tasks Tutorials</a>
    </div>
</div>
</div>
<div class="corner cornerBl"></div>
<div class="corner cornerBr"></div>
</div>
<!-- End .taskListPanel -->


</div>
<!-- End  .groupByTaskList -->

<div class="quickTaskEditTemplate hide">
    <div class="taskItem editTaskItem quickEditTaskItem">
        <div class="taskItemHeader">
            <div class="inputWrapper fLeft"><input name="taskName" type="text" size="20"/></div>
            <a href="javascript:;" class="fRight taskItemLink taskCancelSave">Cancel</a>
            <a href="javascript:;" class="fRight taskItemLink taskEditSave">Save</a>
        </div>
        <div class="taskItemContent">
            <div class="quickEditCol1">
                <label>Assigned to to</label>
                <div class="taskMultiSelector fLeft">
                    <div class="trigger"><a class="msValue" href="javascript:;" title="Select Users">Select Users</a><a class="selectorArrow"></a><label class="hidden">Select Users</label></div>
                    <div class="dropDown">
                        <ul>
                            <li><input name="quickAssignUser" type="checkbox" value="u1" /><label class="coderTextRed">Username 1</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u2" /><label class="coderTextRed">Username 2</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u3" /><label class="coderTextGreen">Username 3</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u4" /><label class="coderTextGreen">Username 4</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u5" /><label class="coderTextOrange">Username 5</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u6" /><label class="coderTextOrange">Username 6</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u7" /><label class="coderTextRed">Username 7</label></li>
                            <li><input name="quickAssignUser" type="checkbox" value="u8" /><label class="coderTextRed">Username 8</label></li>
                        </ul>
                        <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
                    </div>
                </div>
            </div>
            <div class="quickEditCol2">
                <label>Status</label>
                <select class="fLeft" id="quickEditTaskStatus" name="quickEditTaskStatus">
                    <option value="s0">Not Started</option>
                    <option value="s1">In Progress</option>
                    <option value="s2">Waiting on Dependency</option>
                    <option value="s3">Completed</option>
                </select>
            </div>
            <div class="quickEditCol3">
                <label>Due Date</label>
                <input type="text" name="quickEditDueDate" readonly="readonly" id="quickEditDueDate" class="fLeft text task-date-pick" value=""/>
            </div>
            <div class="clearLeftFix"></div>
        </div>
    </div>
</div>
<!--end .quickTaskEditTemplate-->
<div class="taskEditTemplate hide">
    <div class="taskItem editTaskItem">
        <div class="taskItemHeader">
            <div class="inputWrapper fLeft"><input name="taskName" type="text" size="20"/></div>
            <a href="javascript:;" class="fRight taskItemLink taskCancelSave">Cancel</a>
            <a href="javascript:;" class="fRight taskItemLink taskEditSave">Save</a>
        </div>
        <div class="taskItemContent">
            <div class="editColLeft">
                <div class="editCol1">
                    <div class="editCol1-l">
                        <label>Assigned to to</label>
                        <div class="taskMultiSelector fLeft">
                            <div class="trigger"><a class="msValue" href="javascript:;" title="Select Users">Select Users</a><a class="selectorArrow"></a><label class="hidden">Select Users</label></div>
                            <div class="dropDown">
                                <ul>
                                    <li><input name="quickAssignUser" type="checkbox" value="u1" /><label class="coderTextRed">Username 1</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u2" /><label class="coderTextRed">Username 2</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u3" /><label class="coderTextGreen">Username 3</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u4" /><label class="coderTextGreen">Username 4</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u5" /><label class="coderTextOrange">Username 5</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u6" /><label class="coderTextOrange">Username 6</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u7" /><label class="coderTextRed">Username 7</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u8" /><label class="coderTextRed">Username 8</label></li>
                                </ul>
                                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
                            </div>
                        </div>
                    </div>
                    <div class="editCol1-r">
                        <label>Status</label>
                        <select class="fLeft" id="editTaskStatus" name="quickEditTaskStatus">
                            <option value="s0">Not Started</option>
                            <option value="s1">In Progress</option>
                            <option value="s2">Waiting on Dependency</option>
                            <option value="s3">Completed</option>
                        </select>
                    </div>
                    <div class="editCol1-l">
                        <label>Priority</label>
                        <select class="fLeft" id="editTaskPriority" name="quickEditTaskStatus">
                            <option>Select</option>
                            <option >Low</option>
                            <option selected="selected">Normal</option>
                            <option>High</option>
                        </select>
                    </div>
                    <div class="editCol1-r">
                        <label>Due Date </label>
                        <div class="dueDateWrapper">
                            <input type="text" name="filterStartDate" readonly="readonly" class="fLeft text task-date-pick" value=""/>
                            <span class="fLeft">to</span>
                            <input type="text" name="filterDueDate" readonly="readonly" class="fLeft text task-date-pick" value=""/>
                        </div>
                    </div>
                    <div class="editCol1-w associateField">
                        <label>Associate With</label>
                        <div class="editCol1-l selectDisabled">
                            <input name="isNewTaskMileStone" type="checkbox" class="assoOption1" />
                            <label class="chxLbl">Project Milestone</label><br/>
                            <select name="newTaskMileStone" id="newTaskMileStone" disabled="disabled">
                                <option>Milestone</option>
                                <option>Milestone</option>
                                <option>Milestone</option>
                            </select>
                        </div>
                        <div class="editCol1-r">
                            <input name="isNewTaskContest" type="checkbox" checked="checked" class="assoOption2" />
                            <label class="chxLbl">Contest</label><br/>
                            <select name="newTaskContest" id="newTaskContest">
                                <option>Contest</option>
                                <option>Contest</option>
                                <option>Contest</option>
                            </select>
                        </div>
                    </div>
                    <div class="editCol1-w">
                        <label>Notes</label>
                        <textarea name="newTaskNote" rows="5" cols="10">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi commodo, ipsum sed pharetra gravida, orci magna rhoncus neque, id pulvinar odio lorem non turpis. </textarea>
                    </div>
                </div>
            </div>
            <div class="editColRight">
                <label>File(s)</label>
                <ul class="editTaskAttachment">
                    <li>
                        <a class="taskAttachment typeZip" href="javascript:;" title="FilenameloremipsumFilenameloremipsum.zip">FilenameloremipsumFilenameloremipsum.zip</a>
                        <a href="javascript:;" class="removeFile">Remove</a></li>
                    <li>
                        <a class="taskAttachment typePdf" href="javascript:;" title="FilenameldolorsitFilenameldolorsit.pdf">FilenameldolorsitFilenameldolorsit.pdf</a>
                        <a href="javascript:;" class="removeFile">Remove</a></li>
                    <li>
                        <a class="taskAttachment typeZip" href="javascript:;" title="Filenameloremipsum.zip">Filenameloremipsum.zip</a>
                        <a href="javascript:;" class="removeFile">Remove</a></li>
                    <li>
                        <a class="taskAttachment typeDoc" href="javascript:;" title="Filenameldolorsit.docx">Filenameldolorsit.docx</a>
                        <a href="javascript:;" class="removeFile">Remove</a></li>
                </ul>
                <div class="newFileList">
                    <div class="rightRow"><input type="file" size="26" class="fileInput" value="Select file to attach"/></div>
                </div>
                <div class="rightRow">
                    <a href="javascript:;" class="newTaskAddFile">Attach Another File</a>
                    <div class="newFileRow hide"><div class="rightRow"><input type="file" size="26" class="fileInput"/></div></div>
                </div>
            </div>
            <div class="clearLeftFix"></div>
        </div>
    </div>
</div>
<!--end .taskEditTemplate-->
</div>
<!-- End  .taskListWrapper -->
</div>

<!-- end area1 -->
</div>
</div>


<div class="clear"></div>
<!-- End #mainContent -->


</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="../../includes/footer.jsp"/>

<jsp:include page="../../includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
