<%--
  - Author: GreatKevin
  - Version: 1.1 (Release Assembly - TC Cockpit Tasks Management Release 2)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: modal windows used in the project tasks page
  -
  - Version 1.1 (Release Assembly - TC Cockpit Tasks Management Release 2)
  - - Updates modal to suppor task creation / update, task list creation / update.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="addProjectTaskModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                ADD TASK
                <a href="javascript:;" class="closeModal closeProjectModal" title="Close"
                   onclick="modalCloseAddNewProject();">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->
    <div class="modalBody">
        <form novalidate="novalidate">
        <div class="fixedHeightTaskFormWrapper">
            <ul class="taskForm">
                <li class="tasknameField">
                    <label class="fieldName">Task :</label>

                    <div class="right">
                        <input type="text" class="text limitText" name="name"/>

                        <p class="fieldNote">
                            <span class="errorMessage">Task name cannot be empty</span>
                            <span class="num">80</span> characters remaining
                        </p>
                    </div>
                </li>
                <li class="noteField">
                    <label class="fieldName">Note :</label>

                    <div class="right">
                        <textarea name="notes" rows="5" cols="10" maxlength="250"></textarea>

                        <p class="fieldNote">
                            <span class="num">250</span> characters remaining
                        </p>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Status :</label>

                    <div class="right">
                        <s:select list="taskStatuses" name="statusId" id="newTaskStatus"/>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Task List :</label>

                    <div class="right">
                        <select name="taskListId" id="newTaskSelectList">

                        </select>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Start Date :</label>

                    <div class="right">
                        <input type="text" name="startDate" readonly="readonly" id="newStartDate"
                               class="fLeft text task-date-pick" value=""/>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Due Date :</label>

                    <div class="right">
                        <input type="text" name="dueDate" readonly="readonly" id="newDueDate"
                               class="fLeft text task-date-pick" value=""/>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Assign to :</label>

                    <div class="right">
                        <div class="taskMultiSelector fLeft">
                            <div class="trigger"><a class="msValue" href="javascript:;" title="Select Users">Select
                                Users</a><a class="selectorArrow"></a><label class="hidden">Select Users</label></div>
                            <div class="dropDown">
                                <ul>
                                    <s:iterator value="taskUserIds" var="userId">
                                        <li><input name="assignUserIds[]" type="checkbox" value="${userId}" /><label><link:user userId="${userId}"/></label></li>
                                    </s:iterator>
                                </ul>
                                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Priority :</label>

                    <div class="right">
                        <s:select list="taskPriorities" name="priorityId" id="newTaskPriority"/>
                    </div>
                </li>
                <li class="associateField">
                    <label class="fieldName">Associated with :</label>

                    <div class="right">
                        <div class="rightRow selectDisabled">
                            <input name="isNewTaskMileStone" type="checkbox" class="assoOption1"/>
                            <label class="chxLbl">Project Milestone</label>
                            <s:select name="associatedMilestoneId" list="projectMilestones" disabled="disabled"/>
                        </div>
                        <div class="rightRow selectDisabled">
                            <input name="isNewTaskContest2" type="checkbox" class="assoOption2"/>
                            <label class="chxLbl">Challenge</label>
                            <s:select name="associatedContestId" list="projectContests" disabled="disabled"/>
                        </div>
                    </div>
                </li>
                <li>
                    <label class="fieldName">File(s) :</label>

                    <div class="right">
                        <div class="newFileList">
                            <div class="rightRow"><input type="file" size="40" class="fileInput"/></div>
                            <div class="rightRow"><input type="file" size="40" class="fileInput"/></div>
                            <div class="rightRow"><input type="file" size="40" class="fileInput"/></div>
                        </div>
                        <div class="rightRow">
                            <a href="javascript:;" class="newTaskAddFile">Attach Another File</a>

                            <div class="newFileRow hide">
                                <div class="rightRow"><input type="file" size="40" class="fileInput"/></div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        </form>
        <div class="buttonArea">
            <a class="button6 saveNewProjectTask fLeft" title="ADD" href="javascript:;"><span class="left"><span
                    class="right">ADD</span></span></a>
            <a class="button6 closeNewProjectTask fLeft" title="CANCEL" href="javascript:;"><span class="left"><span
                    class="right">CANCEL</span></span></a>

            <div class="clearFix"></div>
        </div>
    </div>
    <!-- end .modalBody -->
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #addProjectTaskModal -->

<div id="addProjectTaskSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Task has been added.</p>
            </div>
            <div class="buttonArea">
                <a href="javascript:;" class="closebutton button6"><span class="left"><span
                        class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #addProjectTaskSuccessModal -->

<div id="addProjectTaskListModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                ADD TASK LIST
                <a href="javascript:;" class="closeModal closeProjectModal" title="Close"
                   onclick="modalCloseAddNewProject();">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->
    <div class="modalBody">
        <form id="addProjectTaskListModalForm" novalidate="novalidate">
            <ul class="taskForm">
            <li class="taskListNameRow">
                <label class="fieldName">Task List Name :</label>

                <div class="right">
                    <input type="text" class="text limitText" name="name" maxlength="120"/>

                    <p class="fieldNote">
                        <span class="errorMessage">Task list name cannot be empty</span>
                        <span class="num">80</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="taskListNoteRow">
                <label class="fieldName">Note :</label>

                <div class="right">
                    <textarea name="notes" rows="5" cols="10" maxlength="250"></textarea>

                    <p class="fieldNote">
                        <span class="num">250</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="privacyRow">
                <label class="fieldName">Privacy Setting :</label>

                <div class="right">
                    <div class="rightRow radioRow">
                        <input name="newTaskListPrivacy" type="radio" value="public"
                               checked="checked"/><label>Public</label>
                        <input name="newTaskListPrivacy" type="radio" value="private"/><label>Private</label>
                    </div>
                </div>
            </li>
            <li class="permissionRow hide">
                <label class="fieldName">&nbsp;</label>

                <div class="right">
                    <p>Users with permission to this task list :</p>

                    <div class="taskMultiSelector fLeft">
                        <div class="trigger"><a class="msValue" href="javascript:;" title="Select Users">Select
                            Users</a><a class="selectorArrow"></a><label class="hidden">Select Users</label></div>
                        <div class="dropDown">
                            <ul>
                                <s:iterator value="taskPermissionUserIds" var="userId">
                                    <li><input name="permittedUsers[][userId]" type="checkbox" value="${userId}" /><label><link:user userId="${userId}"/></label></li>
                                </s:iterator>
                            </ul>
                            <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="associateField">
                <label class="fieldName">Associated with :</label>

                <div class="right">
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskMileStone" type="checkbox" class="assoOption1"/>
                        <label class="chxLbl">Project Milestone</label>
                        <s:select name="associatedToProjectMilestones[][milestoneId]" list="projectMilestones" disabled="disabled"/>
                    </div>
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskContest" type="checkbox" class="assoOption2"/>
                        <label class="chxLbl">Challenge</label>
                        <s:select name="associatedToContests[][contestId]" list="projectContests" disabled="disabled"/>
                    </div>
                </div>
            </li>

        </ul>
        </form>
        <div class="buttonArea">
            <a class="button6 saveNewProjectList fLeft" title="ADD" href="javascript:;"><span class="left"><span
                    class="right">ADD</span></span></a>
            <a class="button6 closebutton cancelButton fLeft" title="CANCEL" href="javascript:;"><span
                    class="left"><span class="right">CANCEL</span></span></a>

            <div class="clearFix"></div>
        </div>
    </div>
    <!-- end .modalBody -->
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #addProjectTaskListModal -->

<div id="addProjectTaskListSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Task list has been added.</p>
            </div>
            <div class="buttonArea">
                <a href="javascript:;" class="closebutton button6"><span class="left"><span
                        class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #addProjectTaskListSuccessModal -->

<div id="editProjectTaskListModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                Edit TASK LIST
                <a href="javascript:;" class="closeModal closeProjectModal" title="Close"
                   onclick="modalCloseAddNewProject();">Close</a>
            </div>
        </div>
    </div>
    <form novalidate="novalidate">
    <!-- end .modalHeader -->
        <div class="modalBody">
        <ul class="taskForm">
            <li class="taskListNameRow">
                <label class="fieldName">Task List Name :</label>

                <div class="right">
                    <input type="text" class="text limitText" name="name"/>

                    <p class="fieldNote">
                        <span class="errorMessage">Task list name cannot be empty</span>
                        <span class="num">80</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="taskListNoteRow">
                <label class="fieldName">Note :</label>

                <div class="right">
                    <textarea name="notes" rows="5" cols="10"></textarea>

                    <p class="fieldNote">
                        <span class="num">250</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="privacyRow">
                <label class="fieldName">Privacy Setting :</label>

                <div class="right">
                    <div class="rightRow radioRow">
                        <input name="newTaskListPrivacy" type="radio" value="public"
                               checked="checked"/><label>Public</label>
                        <input name="newTaskListPrivacy" type="radio" value="private"/><label>Private</label>
                    </div>
                </div>
            </li>
            <li class="permissionRow hide">
                <label class="fieldName">&nbsp;</label>

                <div class="right">
                    <p>Users with permission to this task list :</p>

                    <div class="taskMultiSelector fLeft">
                        <div class="trigger"><a class="msValue" href="javascript:;" title="Select Users">Select
                            Users</a><a class="selectorArrow"></a><label class="hidden">Select Users</label></div>
                        <div class="dropDown">
                            <ul>
                                <s:iterator value="taskPermissionUserIds" var="userId">
                                    <li><input name="permittedUsers[][userId]" type="checkbox" value="${userId}" /><label><link:user userId="${userId}"/></label></li>
                                </s:iterator>
                            </ul>
                            <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="associateField">
                <label class="fieldName">Associated with :</label>

                <div class="right">
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskMileStone" type="checkbox" class="assoOption1"/>
                        <label class="chxLbl">Project Milestone</label>
                        <s:select name="associatedToProjectMilestones[][milestoneId]" list="projectMilestones" disabled="disabled" id="editTaskListMilestone"/>
                    </div>
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskContest" type="checkbox" class="assoOption2"/>
                        <label class="chxLbl">Challenge</label>
                        <s:select name="associatedToContests[][contestId]" list="projectContests" disabled="disabled" id="editTaskListContest"/>
                    </div>
                </div>
            </li>

        </ul>
        <div class="buttonArea">
            <a class="button6 saveEditProjectList fLeft" title="SAVE" href="javascript:;"><span class="left"><span
                    class="right">SAVE</span></span></a>
            <a class="button6 closebutton cancelButton fLeft" title="CANCEL" href="javascript:;"><span
                    class="left"><span class="right">CANCEL</span></span></a>

            <div class="clearFix"></div>
        </div>
    </div>
    </form>
    <!-- end .modalBody -->
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #editProjectTaskListModal -->

<div id="editProjectTaskListSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Changes have been saved.</p>
            </div>
            <div class="buttonArea">
                <a href="javascript:;" class="closebutton button6"><span class="left"><span
                        class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #editProjectTaskListSuccessModal -->

<div id="resolveTaskListModal" class="outLay newOutLay" >
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    RESOLVE AND ARCHIVE TASK LIST
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Are you sure you want to resolve all tasks in the list and archive the list ?</p>
            </div>
            <div class="buttonArea">
                <a class="button6 confirmResolveTaskList" href="javascript:;"><span class="left"><span
                        class="right">YES</span></span></a>
                <a class="closebutton button6 cancelButton" href="javascript:;"><span class="left"><span class="right">NO</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #resolveTaskListModal -->

<div id="resolveTaskListSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Task List has been resolved and archived.</p>
            </div>
            <div class="buttonArea">
                <a href="javascript:;" class="closebutton button6"><span class="left"><span
                        class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #resolveTaskListSuccessModal -->

<div id="deleteTaskListModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    DELETE TASK LIST
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Are you sure you want to delete the task list and all tasks in the list?</p>
            </div>
            <div class="buttonArea">
                <a class="button6 confirmDeleteTaskList" href="javascript:;"><span class="left"><span
                        class="right">YES</span></span></a>
                <a class="closebutton button6 cancelButton" href="javascript:;"><span class="left"><span class="right">NO</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #deleteTaskListModal -->

<div id="deleteTaskListSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Task List has been deleted.</p>
            </div>
            <div class="buttonArea">
                <a class="closebutton button6 cancelButton" href="javascript:;"><span class="left"><span class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #deleteTaskListSuccessModal -->

<div  id="deleteTaskModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    DELETE TASK
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Are you sure you want to delete the task?</p>
            </div>
            <div class="buttonArea">
                <a class="button6 confirmDeleteTask" href="javascript:;"><span class="left"><span
                        class="right">YES</span></span></a>
                <a class="closebutton button6 cancelButton" href="javascript:;"><span class="left"><span class="right">NO</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #deleteTaskModal -->

<div  id="deleteTaskSuccessModal" class="outLay newOutLay">
    <div class="inner">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    MESSAGE
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader --><!-- content -->
        <div class="modalBody">
            <div class="confirmInfo">
                <p>Task has been deleted.</p>
            </div>
            <div class="buttonArea">
                <a class="closebutton button6 cancelButton" href="javascript:;"><span class="left"><span class="right">OK</span></span></a>

                <div class="clearFix"></div>
            </div>
        </div>
        <!-- End .content -->
        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
</div>
<!-- end #deleteTaskSuccessModal -->
