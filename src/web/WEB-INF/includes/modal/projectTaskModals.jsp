<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task）
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: modal windows used in the project tasks page
  -
--%>
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
        <div class="fixedHeightTaskFormWrapper">
            <ul class="taskForm">
                <li class="tasknameField">
                    <label class="fieldName">Task :</label>

                    <div class="right">
                        <input type="text" class="text limitText" name="newTaskname"/>

                        <p class="fieldNote">
                            <span class="errorMessage">Task name cannot be empty</span>
                            <span class="num">120</span> characters remaining
                        </p>
                    </div>
                </li>
                <li class="noteField">
                    <label class="fieldName">Note :</label>

                    <div class="right">
                        <textarea name="newTaskNote" rows="5" cols="10"></textarea>

                        <p class="fieldNote">
                            <span class="num">500</span> characters remaining
                        </p>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Status :</label>

                    <div class="right">
                        <select name="newTaskStatus" id="newTaskStatus">
                            <option value="s0">Not Started</option>
                            <option value="s1">In Progress</option>
                            <option value="s2">Waiting on Dependency</option>
                            <option value="s3">Completed</option>
                        </select>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Task List :</label>

                    <div class="right">
                        <select name="newTaskSelectList" id="newTaskSelectList">
                            <option value="pt0">Project Tasks</option>
                            <option value="pt1">Tasks List1</option>
                            <option value="pt2">Tasks List2</option>
                        </select>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Start Date :</label>

                    <div class="right">
                        <input type="text" name="filterStartDate" readonly="readonly" id="newStartDate"
                               class="fLeft text task-date-pick" value=""/>
                    </div>
                </li>
                <li>
                    <label class="fieldName">Due Date :</label>

                    <div class="right">
                        <input type="text" name="filterStartDate" readonly="readonly" id="newDueDate"
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
                                    <li><input name="quickAssignUser" type="checkbox" value="u1"/><label
                                            class="coderTextRed">Username 1</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u2"/><label
                                            class="coderTextRed">Username 2</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u3"/><label
                                            class="coderTextGreen">Username 3</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u4"/><label
                                            class="coderTextGreen">Username 4</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u5"/><label
                                            class="coderTextOrange">Username 5</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u6"/><label
                                            class="coderTextOrange">Username 6</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u7"/><label
                                            class="coderTextRed">Username 7</label></li>
                                    <li><input name="quickAssignUser" type="checkbox" value="u8"/><label
                                            class="coderTextRed">Username 8</label></li>
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
                        <select name="newTaskPriority" id="newTaskPriority">
                            <option value="p0">High</option>
                            <option value="p1">Normal</option>
                            <option value="p2">Low</option>
                        </select>
                    </div>
                </li>
                <li class="associateField">
                    <label class="fieldName">Associated with :</label>

                    <div class="right">
                        <div class="rightRow selectDisabled">
                            <input name="isNewTaskMileStone" type="checkbox" class="assoOption1"/>
                            <label class="chxLbl">Project Milestone</label>
                            <select name="newTaskMileStone2" id="newTaskMileStone2" disabled="disabled">
                                <option>Milestone</option>
                                <option>Milestone</option>
                                <option>Milestone</option>
                            </select>
                        </div>
                        <div class="rightRow selectDisabled">
                            <input name="isNewTaskContest2" type="checkbox" class="assoOption2"/>
                            <label class="chxLbl">Contest</label>
                            <select name="newTaskContest" id="newTaskContest2" disabled="disabled">
                                <option>Contest</option>
                                <option>Contest</option>
                                <option>Contest</option>
                            </select>
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
        <ul class="taskForm">
            <li class="taskListNameRow">
                <label class="fieldName">Task List Name :</label>

                <div class="right">
                    <input type="text" class="text limitText" name="newTaskname"/>

                    <p class="fieldNote">
                        <span class="errorMessage">Task list name cannot be empty</span>
                        <span class="num">120</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="taskListNoteRow">
                <label class="fieldName">Note :</label>

                <div class="right">
                    <textarea name="newTaskNote" rows="5" cols="10"></textarea>

                    <p class="fieldNote">
                        <span class="num">500</span> characters remaining
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
                                <li><input name="quickAssignUser" type="checkbox" value="u1"/><label
                                        class="coderTextRed">Username 1</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u2"/><label
                                        class="coderTextRed">Username 2</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u3"/><label
                                        class="coderTextGreen">Username 3</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u4"/><label
                                        class="coderTextGreen">Username 4</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u5"/><label
                                        class="coderTextOrange">Username 5</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u6"/><label
                                        class="coderTextOrange">Username 6</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u7"/><label
                                        class="coderTextRed">Username 7</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u8"/><label
                                        class="coderTextRed">Username 8</label></li>
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
                        <select name="newTaskListMileStone" id="newTaskListMileStone" disabled="disabled">
                            <option>Milestone</option>
                            <option>Milestone</option>
                            <option>Milestone</option>
                        </select>
                    </div>
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskContest" type="checkbox" class="assoOption2"/>
                        <label class="chxLbl">Contest</label>
                        <select name="newTaskListContest" id="newTaskListContest" disabled="disabled">
                            <option>Contest</option>
                            <option>Contest</option>
                            <option>Contest</option>
                        </select>
                    </div>
                </div>
            </li>

        </ul>
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
    <!-- end .modalHeader -->
    <div class="modalBody">
        <ul class="taskForm">
            <li class="taskListNameRow">
                <label class="fieldName">Task List Name :</label>

                <div class="right">
                    <input type="text" class="text limitText" name="newTaskname"
                           value="Task name Lorem ipsum dolor sit aemt"/>

                    <p class="fieldNote">
                        <span class="errorMessage">Task list name cannot be empty</span>
                        <span class="num">120</span> characters remaining
                    </p>
                </div>
            </li>
            <li class="taskListNoteRow">
                <label class="fieldName">Note :</label>

                <div class="right">
                    <textarea name="newTaskNote" rows="5" cols="10">Lorem ipsum dolor sit amet, consectetur adipisicing
                        elit, sed do eiusmod tempor incididunt ut</textarea>

                    <p class="fieldNote">
                        <span class="num">500</span> characters remaining
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
                                <li><input name="quickAssignUser" type="checkbox" value="u1"/><label
                                        class="coderTextRed">Username 1</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u2"/><label
                                        class="coderTextRed">Username 2</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u3"/><label
                                        class="coderTextGreen">Username 3</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u4"/><label
                                        class="coderTextGreen">Username 4</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u5"/><label
                                        class="coderTextOrange">Username 5</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u6"/><label
                                        class="coderTextOrange">Username 6</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u7"/><label
                                        class="coderTextRed">Username 7</label></li>
                                <li><input name="quickAssignUser" type="checkbox" value="u8"/><label
                                        class="coderTextRed">Username 8</label></li>
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
                        <select name="editTaskListMileStone" id="editTaskListMileStone" disabled="disabled">
                            <option>Milestone</option>
                            <option>Milestone</option>
                            <option>Milestone</option>
                        </select>
                    </div>
                    <div class="rightRow selectDisabled">
                        <input name="isNewTaskContest" type="checkbox" class="assoOption2"/>
                        <label class="chxLbl">Contest</label>
                        <select name="editTaskListContest" id="editTaskListContest" disabled="disabled">
                            <option>Contest</option>
                            <option>Contest</option>
                            <option>Contest</option>
                        </select>
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

<div class="outLay newOutLay" id="resolveTaskListModal">
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

<div class="outLay newOutLay" id="deleteTaskListModal">
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

<div class="outLay newOutLay" id="deleteTaskListSuccessModal">
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

<div class="outLay newOutLay" id="deleteTaskModal">
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

<div class="outLay newOutLay" id="deleteTaskSuccessModal">
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
