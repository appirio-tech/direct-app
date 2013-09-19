/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * Javascript for for the project tasks pages.
 *
 * - Version 1.1 (Module Assembly - TopCoder Cockpit Project Planner)
 * Refactor the multiple selection drop down to dashboard.js
 *
 * - Version 1.2 (Release Assembly - TC Cockpit Tasks Management Release 2)
 * - Add methods to suppor task CRUD and task list CRUD.
 *
 * - Version 1.3 (TC - Cockpit Tasks Management Assembly 3)
 * - Adds the task filtering feature
 * - Adds the task group by due date feature
 *
 * @version 1.3
 * @author GreatKevin
 */
var quickTaskNameHint = "Task Name";

var userLinksCache = {};

var statusMap = {
    "NOT_STARTED": {id: 0, name: "Not Started"},
    "IN_PROGRESS": {id: 1, name: "In Progress"},
    "WAIT_ON_DEPENDENCY": {id: 2, name: "Wait On Dependency"},
    "COMPLETED": {id: 3, name: "Completed"}
}

var priorityMap = {
    "HIGH": {id: 0, name: "High"},
    "LOW": {id: 1, name: "Low"},
    "NORMAL": {id: 2, name: "Normal"}
}

var taskValidationRules = {
    rules: {
        name: {
            required: true,
            maxlength:80
        },
        notes: {
            maxlength: 250
        }
    },
    messages: {
        name: {
            required: "Task Name cannot be empty",
            maxlength: "Task name cannot exceeds 80 characters"
        },
        notes: {
            maxlength: "Notes cannot exceeds 250 characters"
        }
    }
};

var taskListValidationRules = {
    rules: {
        name: {
            required: true,
            maxlength:80
        },
        notes: {
            maxlength: 250
        }
    },
    messages: {
        name: {
            required: "Task List Name cannot be empty",
            maxlength: "Task List name cannot exceeds 80 characters"
        },
        notes: {
            maxlength: "Notes cannot exceeds 250 characters"
        }
    }
};



var localVM = {};

var filterApplied = false;

if($.views) {
    $.views.helpers({
        getUserLink: getUserLink,
        getStatusId: getStatusId,
        getStatusName: getStatusName,
        getPriorityName: getPriorityName
    });
}

function getUserLink(userId) {
    return userLinksCache[userId];
}

function getStatusId(enumName) {
    return statusMap[enumName].id;
}

function getStatusName(enumName) {
    return statusMap[enumName].name;
}

function getPriorityId(enumName) {
    return priorityMap[enumName].id;
}

function getPriorityName(enumName) {
    return priorityMap[enumName].name;
}

function buildFilterRequest() {
    var filters = {};
    var _filterPanel = $(".taskListFilter");

    var name = $(".searchBox", _filterPanel).val();
    var assignee = $("#filterAssignedTo").val();
    var dueType = $("#filterDueType").val();
    var dueDateFrom = $("#filterStartDate").val();
    var dueDateTo = $("#filterDueDate").val();
    var priorityIds = [];
    $("input[name=priorityFilter]:checked").each(function(){
        priorityIds.push($(this).val());
    });
    var statusIds = [];
    $("input[name=statusFilter]:checked").each(function(){
        statusIds.push($(this).val());
    });
    var milestoneIds = [];
    $("input[name=milestoneFilter]:checked").each(function(){
        milestoneIds.push($(this).val());
    });
    var contestIds = [];
    $("input[name=contestFilter]:checked").each(function(){
        contestIds.push($(this).val());
    });

    if(name.length > 0) {
        filters.name = name;
    }

    if(assignee && assignee > 0) {
        filters.assigneeId = assignee;
    }

    filters.dueType = dueType;

    if(dueType == 0) {
        if(dueDateFrom && dueDateFrom.length > 0) {
            filters.dueDateFrom = dueDateFrom;
        }
        if(dueDateTo && dueDateTo.length > 0) {
            filters.dueDateTo = dueDateTo;
        }
    }

    filters.priorityIds = priorityIds;
    filters.statusIds = statusIds;

    if(milestoneIds.length > 0) {
        filters.milestoneIds = milestoneIds;
    }

    if(contestIds.length > 0) {
        filters.contestIds = contestIds;
    }

    filters.applyFilter = true;

    return filters;
}

function resetAssociationInput(item, name) {
    var selector = item.find("select[name='" + name + "']");
    selector.find("option").removeAttr('selected');
    var wrapper = selector.parent();
    wrapper.addClass("selectDisabled");
    selector.attr('disabled', 'disabled');
    wrapper.find("input[type=checkbox]").removeAttr("checked");
}

function loadTaskList(taskListId, callBackFunction) {
    var taskListPanel = $("#taskList" + taskListId);

    var request;
    if(filterApplied == true) {
        request = {formData:{projectId:tcDirectProjectId}, taskListId:taskListId, filter: buildFilterRequest()};
    } else {
        request = {formData:{projectId:tcDirectProjectId}, taskListId:taskListId};
    }

    $.ajax({
        type: 'POST',
        url: ctx + "/getTaskLists",
        data: request,
        cache: false,
        dataType: 'json',
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function (result) {
                    result['isDefault'] = result['default']; // fix ie8 default keyword issue in template
                    taskListPanel.html($($("#taskListTemplate").render(result)).html());

                    displayProgress();
                    taskListPanel.find(".completedItem").hide();

                    if(callBackFunction) {
                        callBackFunction();
                    }

                    localVM[result.id] = result;
                    updateCompletedStat();
                },
                function (errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });
}

function updateCompletedStat() {
    var completedTasksNum = 0;
    var totalTaskNum = 0;
    var totalTaskListNum = 0;
    $("#switchTaskList option:gt(0)").remove();

    $.each(localVM, function (key, value) {
        completedTasksNum += value.completedTasks.length;
        totalTaskNum += value.tasks.length;
        totalTaskListNum ++;
        if(value.active) {
            $("#switchTaskList").append($('<option/>').text(value.name + ' (' + value.completedTasks.length + "/" + value.tasks.length + ')').attr('value', 'taskList' + value.id));
        }
    });

    $(".summaryTxt .allCompletedNum").text(completedTasksNum);
    $(".summaryTxt .allTotalNum").text(totalTaskNum);
    $(".summaryTxt .allTaskListNum").text(totalTaskListNum);

    displayProgress();
}

function loadAllTaskLists(callbackFunction, applyFilters) {

    $('.groupByTaskList').children().not('#empty').remove();

    localVM = {};
    var request = {formData:{projectId:tcDirectProjectId}};

    if(applyFilters == true) {
        request = {formData: {projectId: tcDirectProjectId}, filter: buildFilterRequest()};
    }

    if($("#switchTaskGroupBy").val() > 0) {
        request.group = {groupTypeId: $("#switchTaskGroupBy").val()};

        if($("#switchTaskList").val() != 'ptAll') {
            // has a specific task list selected
            request.group['groupListId'] = $("#switchTaskList").val().substr(8);
        }

    }

    $.ajax({
        type: 'POST',
        url: ctx + "/getTaskLists",
        data: request,
        cache: false,
        async: false,
        dataType: 'json',
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function (result) {

                    // handle default group by task list
                    if($("#switchTaskGroupBy").val() <= 0) {
                        _taskResult = result.tasks;

                        if(!applyFilters && (!_taskResult || _taskResult.length == 0)) {
                            $("#empty").show();
                            return;
                        } else {
                            $("#empty").hide();
                        }

                        $("select[name=taskListId] option").remove();
                        $("#switchTaskList option:gt(0)").remove();
                        var activeLists = [];
                        var completedLists = [];

                        // iterate over each task list
                        $.each(_taskResult, function(index, item){

                            // if task list is default and there is no task, show the empty tasks hint
                            if(item['default']) {
                                if(item.tasks.length == 0) {
                                    $("#empty").show();
                                } else {
                                    $("#empty").hide();
                                }

                            }

                            item['isDefault'] = item['default']; // fix ie8 default keyword issue in template


                            // append each task list to "task list selector" of creating new task: 1) quick add task 2) add task modal window
                            $("select[name=taskListId]").append($("<option/>").attr('value', item.id).text(item.name));

                            if(item.active) {
                                // task list is active, push to active list stack and append the list name with count to "Switch Task List" select
                                activeLists.push(item);
                                $("#switchTaskList").append($('<option/>').text(item.name + ' (' + item.completedTasks.length + "/" + item.tasks.length + ')').attr('value', 'taskList' + item.id));
                            } else {
                                // task list is resolved one, push to the completed task list stack
                                completedLists.push(item);
                            }

                            // put into the local cache
                            localVM[item.id] = item;
                        });

                        // update the task list completion stats
                        updateCompletedStat();

                        // append the main content of task lists with template rendering
                        $('.groupByTaskList').children().not('#empty').remove();
                        $(".groupByTaskList").append($("#taskListsTemplate").render({activeLists:activeLists,completedLists: completedLists})).show();
                        $(".groupByDueDate").hide();

                        // reset "Switch Task List" select
                        $("#switchTaskList")[0].selectedIndex = 0;

                        // reset "group by" select
                        $("#switchTaskGroupBy")[0].selectedIndex = 0;

                        // update the progress indicators of each task list and the total project indicator of all task lists
                        displayProgress();


                        $(".taskListContainer .completedItem").hide();
                        $(".completedTaskList .completedItem").show();
                        $(".taskListContainer .completedTaskList").hide();

                        $('.taskAttachmentUl a.taskAttachment').each(function() {
                            if ( $(this).text().length > 23) {
                                turncatedTxt  = $(this).text().substring(0, 20) + " ...";
                                $(this).text(turncatedTxt);
                            }
                        });

                        // if applyFilter is true
                        if(applyFilters == true && _taskResult.length == 0) {
                            $('.groupByTaskList').append('<p style="font-size:13px; text-align: center; padding-top: 40px">There is no matched task</p>');
                        }

                        if(callbackFunction) {
                            callbackFunction();
                        }
                    } else {
                        // handle group by due date
                        $(".groupByTaskList").hide();
                        $(".groupByDueDate").html('').append($("#dueDateGroupsTemplate").render({dueDateGroups:result})).show();
                        $(".groupByDueDate .taskListPanel").each(function(){
                            if($(this).find(".isOverdue").length > 0) {
                                $(this).addClass('overDueList');
                            }
                        });


                    }

                },
                function (errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });
}

$(document).ready(function(){

    $(':input').live('focus',function(){
        $(this).attr('autocomplete', 'off');
    });


    // build user links cache
    $(".addTaskPanel input[name=quickAssignUser]").each(function () {
        var userId = $(this).val();
        var userLink = $(this).next("label").html();
        userLinksCache[userId] = userLink;
    });

    $("#filterAssignedTo option").each(function() {
        var userId = $(this).attr('value');
        if(userId > 0) {
            $(this).text($(userLinksCache[userId] + '').text());
        }
    })

    // load all the task lists when page ready
    loadAllTaskLists();

    /*Focus / Blur Effect*/
    $('#quickTaskName').focus(function () {
        if ($(this).val() == quickTaskNameHint) {
            $(this).val("");
        }
        $(this).addClass("focused");
    });
    //Reset Instant Search Hint Text
    $('#quickTaskName').blur(function () {
        if ($(this).val() == "") {
            $(this).val(quickTaskNameHint);
            $(this).removeClass("focused");
        }
    });
	
    /* init date-pack */
	$("#quickStartDate").val("Start Date");
	$("#quickDueDate").val("Due Date");
    $('.task-date-pick').each(function() {
       $(this).datePicker({
			startDate:'01/01/2001'
		});
    });
	
	//date picker, after selected, 
	$('.task-date-pick').bind("change", function(){
		$(this).addClass("selected");
		var objID = $(this).attr("id");
		if (objID == "quickStartDate"){
			$("#quickDueDate").dpSetStartDate($(this).val());
		}else if (objID == "filterStartDate"){
			$("#filterDueDate").dpSetStartDate($(this).val());
		}
		else if (objID == "newStartDate"){
			$("#newDueDate").dpSetStartDate($(this).val());
		}
		
	})
	
	$('.radioRow label').click(function(){
		$(this).prev().trigger("click");
	})
	$('.privacyRow input').click(function(){
		if ($(this).val() == "public"){
			$(".permissionRow").hide();
		}else{
			$(".permissionRow").show();
		}
	})
	$('.chxLbl').live('click', function(){
		$(this).prev().trigger("click");
	})
	$('.associateField input').live('click', function(){
		var wrapper = $(this).parent();
		var associateWrapper = $(this).parents(".associateField").eq(0);
		if ($(this).hasClass("assoOption1")){
			var anotherInput = $(".assoOption2", associateWrapper );
		}else{
			var anotherInput = $(".assoOption1", associateWrapper );
		}
		if (wrapper.hasClass("selectDisabled")){
			wrapper.removeClass("selectDisabled");
			$("select", wrapper).removeAttr('disabled');
			if (anotherInput.attr("checked")){
				anotherInput.trigger("click");
			}
		}else{
			wrapper.addClass("selectDisabled");
			$("select", wrapper).attr("disabled", "disabled");
		}
	})
	$('.newTaskAddFile').live('click', function(){
		var wrapper = $(this).parent().parent().parent();
		newRow = $(this).next(".newFileRow").html();
		$(".newFileList", wrapper).append(newRow);
	})
	$('.removeFile').live('click', function(){
		var wrapper = $(this).parent();
		wrapper.remove();
	})
	
	
	//switch task list
	$("#switchTaskList, #switchTaskGroupBy").change(function(){
		var ptName = $("#switchTaskList").val();
		var groupIdx = $("#switchTaskGroupBy")[0].selectedIndex;
		
		if (groupIdx == "0"){
			$(".groupByTaskList").show();
			$(".groupByDueDate").hide();
			if(ptName == "ptAll"){
				$(".taskListWrapper").removeClass("singlePt");
				$(".groupByTaskList .taskListPanel").not("#empty").show();
				$(".groupByTaskList .completedItem").hide();
				$(".groupByTaskList .completedTaskList .completedItem").show();
				$(".groupByTaskList .completedTaskList").hide();
				$(".showCompletedTasks").removeClass('showed').text("Show Completed Tasks");
				$(".showCompletedTaskLists").removeClass('showed').text("Show Completed Task Lists");
				$(".completedTaskListTogger").removeClass('completedTaskListsShowed');
				 
			}else{
				$(".taskListWrapper").addClass("singlePt");
				$(".groupByTaskList .taskListPanel").hide();
				var targetPt = $("#" + ptName);
				targetPt.show();
			}
		} else{
            loadAllTaskLists();
		}
	})

	
	//show completed tasks
	$('.showCompletedTasks').live('click', function(){
		$(this).toggleClass("showed");
		var wrapper = $(this).parents(".taskListPanel").eq(0);
		if ($(this).hasClass('showed')){
			$(this).text("Hide Completed Tasks");
			$(".completedItem", wrapper).show();
		}else{
			$(".completedItem", wrapper).hide();
			$(this).text("Show Completed Tasks");
		}
	})
	
	//show completed task list
	$('.showCompletedTaskLists').live('click', function(){
		$(this).toggleClass("showed");
		if ($(this).hasClass('showed')){
			$(".completedTaskList").show();
			$(this).parents(".completedTaskListTogger").eq(0).addClass("completedTaskListsShowed");
			$(this).text("Hide Completed Task Lists");
		}else{
			$(".completedTaskList").hide();
			$(this).parents(".completedTaskListTogger").eq(0).removeClass("completedTaskListsShowed");
			$(this).text("Show Completed Task Lists");
		}
	})
	
	//show more details
	$(".taskMoreDetailsLink").live("click",function () {
		var wrapper = $(this).parents(".taskItem").eq(0);
		wrapper.toggleClass("taskItemDetailed");
		if (wrapper.hasClass('taskItemDetailed')){
			$(this).text("Less Details");
		}else{
			$(this).text("More Details");
		}
	})

    // filter task
    $("#applyFilterToggle").click(function(){
        loadAllTaskLists(function () {
            $("#empty").hide();
        }, true);
        filterApplied = true;
    });

    $("#clearFilterToggle").click(function(){
        $(".taskListFilter input[type=text]").val('');
        $(".taskListFilter select").each(function(){
            $(this).find("option:eq(0)").attr('selected', 'selected');
        });

        $("#filterByPriority input[type=checkbox], #filterByStatus input[type=checkbox]").attr('checked', 'checked');

        $("#filerByPM input[type=checkbox], #filterByContests input[type=checkbox]").removeAttr('checked');

        $("#filerByPM .selectorArrow").click();
        $("#filerByPM .buttonRed1").click();
        $("#filterByContests .selectorArrow").click();
        $("#filterByContests .buttonRed1").click();

        loadAllTaskLists();
        filterApplied = false;
    })

    // group
    $("#switchTaskGroupBy").change(function(){
        loadAllTaskLists();
    })
	
	
	// Edit task
	$('.taskEditLink').live("click",function () {
		var wrapper = $(this).parents(".taskItem").eq(0);

        var taskId = wrapper.find("input[name=taskId]").val();

        $.ajax({
            type: 'POST',
            url: ctx + "/getTask",
            data: {newTask:{id:taskId},formData:{projectId:tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        var editForm = $(wrapper.hasClass("taskItemDetailed") ? "#detailsEditTemplate" : "#quickEditTemplate").render(result);
                        wrapper.addClass("taskItemEditing");
                        wrapper.hide();
                        wrapper.after(editForm);
                        editForm = wrapper.next();

                        editForm.find("option:selected").removeAttr("selected");

                        // populate assignee
                        $.each(result.assignees, function(index, user){
                            editForm.find(".taskMultiSelector li input[value='" + user.userId + "']").attr('checked',
                                'checked');
                        });
                        editForm.find(".taskMultiSelector .selectorArrow").trigger('click');
                        editForm.find(".taskMultiSelector .buttonRed1").trigger('click');

                        // populate task status
                        editForm.find("select[name=statusId]").val(getStatusId(result.status));

                        if (wrapper.hasClass("taskItemDetailed")) {

                            // details edit
                            $(".dueDateWrapper",
                                editForm).html("<input type='text' name='startDate' readonly='readonly' onchange='setEditDueStart()' class='fLeft text task-date-pick' value=''/><span class='fLeft'>to</span><input type='text' name='dueDate' readonly='readonly' class='fLeft text task-date-pick' value=''/>");
                            var startPicker = $("input[name=startDate]", editForm).datePicker({startDate: '01/01/2001'});
                            var endPicker =   $("input[name=dueDate]", editForm).datePicker({startDate: '01/01/2001'});
                            if(result.startDate) {
                                var startDate = Date.parse(result.startDate);
                                startPicker.dpSetSelected($.datepicker.formatDate('mm/dd/yy', startDate));
                            }
                            if(result.dueDate) {
                                var dueDate = Date.parse(result.dueDate);
                                endPicker.dpSetSelected($.datepicker.formatDate('mm/dd/yy', dueDate));
                            }

                            // populate priority
                            $("select[name=priorityId]").val(getPriorityId(result.priority));

                            // populate association
                            var selectorM = editForm.find("select[name=associatedMilestoneId]");
                            var selectorC = editForm.find("select[name=associatedContestId]");
                            if(result.associatedToProjectMilestones.length > 0) {
                                selectorM.parent().removeClass('selectDisabled').find("input[type=checkbox]").attr('checked', 'checked');
                                selectorM.val(result.associatedToProjectMilestones[0].milestoneId).removeAttr('disabled');
                                selectorC.attr('disabled', 'disabled');
                            } else if (result.associatedToContests.length > 0) {
                                selectorC.parent().removeClass('selectDisabled').find("input[type=checkbox]").attr('checked', 'checked');
                                selectorC.val(result.associatedToContests[0].contestId).removeAttr('disabled');
                                selectorM.attr('disabled', 'disabled');
                            }
                            
                            $('.taskAttachment', editForm).each(function () {
                                if ($(this).text().length > 30) {
                                    turncatedTxt = $(this).text().substring(0, 27) + " ...";
                                    $(this).text(turncatedTxt);
                                }
                            });


                        } else {
                            // populate due date
                            $(".quickEditCol3",
                                editForm).html("<label>Due Date</label><input type='text' name='dueDate' readonly='readonly' class='fLeft text task-date-pick quickEditDueDate'/>");
                            var endPicker =   $("input[name=dueDate]", editForm).datePicker({startDate: '01/01/2001'});
                            if(result.dueDate) {
                                var dueDate = Date.parse(result.dueDate);
                                endPicker.dpSetSelected($.datepicker.formatDate('mm/dd/yy', dueDate));
                            }

                            if(result.startDate) {
                                // start date is a hidden input in task summary edit
                                var startDate = Date.parse(result.startDate);
                                $("input[name=startDate]", editForm).val($.datepicker.formatDate('mm/dd/yy',
                                    startDate));
                            }

                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
//
//        if (wrapper.hasClass("taskItemDetailed")) {
//
//            // details edit
//
//            var editForm = $(".taskEditTemplate").html();
//            wrapper.addClass("taskItemEditing");
//            wrapper.hide();
//            wrapper.after(editForm);
//            var editForm = wrapper.next();
//            $(".dueDateWrapper",
//                editForm).html("<input type='text' name='editStartDate' readonly='readonly' id='editStartDate' onchange='setEditDueStart()' class='fLeft text task-date-pick' value=''/><span class='fLeft'>to</span><input type='text' name='editDueDate' id='editDueDate' readonly='readonly' class='fLeft text task-date-pick' value=''/>");
//            $(".task-date-pick", editForm).datePicker({startDate: '01/01/2001'}).dpSetSelected('12/20/2012');
//            var taskName = $(".priorityLabel", wrapper).text();
//            $(".inputWrapper input", editForm).val(taskName);
//
//
//            $('.taskAttachment', editForm).each(function () {
//                if ($(this).text().length > 30) {
//                    turncatedTxt = $(this).text().substring(0, 27) + " ...";
//                    $(this).text(turncatedTxt);
//                }
//            });
//
//
//        } else {
//
//            // simple edit
//
//            var editForm = $(".quickTaskEditTemplate").html();
//            wrapper.addClass("taskItemEditing");
//            wrapper.hide();
//            wrapper.after(editForm);
//            var editForm = wrapper.next();
//            $(".quickEditCol3",
//                editForm).html("<label>Due Date</label><input type='text' name='quickEditDueDate' readonly='readonly' class='fLeft text task-date-pick quickEditDueDate'/>");
//            $(".task-date-pick", editForm).datePicker({startDate: '01/01/2001'}).dpSetSelected('12/20/2012');
//            var taskName = $(".priorityLabel", wrapper).text();
//            $(".inputWrapper input", editForm).val(taskName);
//            var progress = $(".col2 p", wrapper).text();
//            if (progress == "Not Started") {
//                $(".quickEditCol2 select", editForm)[0].selectedIndex = 1;
//            } else if (progress == "In Progress") {
//                $(".quickEditCol2 select", editForm)[0].selectedIndex = 2;
//            } else if (progress == "Waiting on Dependency") {
//                $(".quickEditCol2 select", editForm)[0].selectedIndex = 3;
//            } else if (progress == "Completed") {
//                $(".quickEditCol2 select", editForm)[0].selectedIndex = 4;
//            }
//        }
	});

	$('.taskEditSave').live("click",function () {
        var editForm = $(this).parents(".editTaskItem").eq(0);

        if(!validateTask(editForm.find("form"))) {
            return;
        }


        var taskWrapper = editForm.prev();

        var taskId = taskWrapper.find("input[name=taskId]").val();

        var taskUpdate = editForm.find("form").serializeObject();
        taskUpdate['id'] = taskId;

        $.ajax({
            type: 'POST',
            url: ctx + "/updateTask",
            data: {newTask:taskUpdate,formData:{projectId:tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        taskWrapper.show().removeClass("taskItemEditing");
                        taskWrapper.html($($("#newTaskTemplate").render(result)).html());
                        if(taskWrapper.hasClass('taskItemDetailed')) {
                            taskWrapper.find('a.taskMoreDetailsLink').text("Less Details");
                        }
                        editForm.remove();
                        if($("#switchTaskGroupBy").val() <= 0) {
                            if(result.status == 'COMPLETED') {
                                taskWrapper.addClass('completedItem');
                                loadTaskList(result.taskListId);
                            }
                        } else {
                            loadAllTaskLists();
                        }

                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

	});
	
	$('.taskCancelSave').live("click",function () {
		var wrapper = $(this).parents(".editTaskItem").eq(0);
		wrapper.prev().show().removeClass("taskItemEditing");
		wrapper.remove();
	});

    // Resolve Task
    $("input[name='taskCompleteToggle']").live('click', function () {

        var taskListPanel = $(this).parents(".taskListPanel");
        var taskId = $(this).parents(".taskItem").find("input[name=taskId]").val();

        $.ajax({
            type: 'POST',
            url: ctx + "/toggleTask",
            data: {newTask: {id: taskId}, formData:{projectId:tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {

                        if ($("#switchTaskGroupBy").val() <= 0) {
                            loadTaskList(result.taskListId);

                            if (result.status != 'COMPLETED' && taskListPanel.hasClass("completedTaskList")) {
                                // task reactived && task list is a completed, do a reload
                                loadAllTaskLists();
                            }
                        } else {
                            loadAllTaskLists();
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });
	
	// Delete task
	$('.taskDeleteLink').live("click",function () {

        $("#deleteTaskModal").data("taskToDelete", $(this).parents(".taskItem").find("input[name=taskId]").val());
        $("#deleteTaskModal").data("taskListToUpdate", $(this).parents(".taskListPanel ").find("input[name=taskListId]").val());

		modalLoad("#deleteTaskModal");

		return false;
	});


	$('.confirmDeleteTask').click(function () {
		modalClose();
		$('#deleteTaskModal').hide();

        $.ajax({
            type: 'POST',
            url: ctx + "/removeTask",
            data: {newTask: {id: $("#deleteTaskModal").data("taskToDelete")}, formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        if($("#switchTaskGroupBy").val() <= 0) {
                            loadTaskList($("#deleteTaskModal").data("taskListToUpdate"), function() {modalLoad("#deleteTaskSuccessModal");});
                        } else {
                            loadAllTaskLists();
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

		return false;
	});
	
	// Add task
	 $('.jsAddTaskDetailedPopup').click(function () {
		resetAddTaskModal("more");
        modalLoad("#addProjectTaskModal");
        return false;
    });
	
    function validationQuickAddTask() {
        var taskName = $("#quickTaskName").val();
        var taskStatus = $("#quickTaskStatus").val();
        var taskListId = $("#quickAssignTask").val();

        var errors = [];

        if (taskName.length == 0 || taskName == 'Task Name') {
            errors.push("Task Name cannot be empty");
        }

        if (taskStatus > 3 || taskStatus < 0) {
            errors.push("Task Status should be selected");
        }

        if (!taskListId || taskListId < 0) {
            errors.push("Task List to assign should be selected");
        }

        var startDate = $("#quickStartDate").val();
        var endDate = $("#quickDueDate").val();

        if (startDate != "Start Date" && endDate != "Due Date") {
            // both set, check if due date >= start date
            var sd = startDate.split("/");
            var sdValue = parseFloat(sd[0]) * 30 + parseFloat(sd[1]) + parseFloat(sd[2]) * 365;
            var ed = endDate.split("/");
            var edValue = parseFloat(ed[0]) * 30 + parseFloat(ed[1]) + parseFloat(ed[2]) * 365;

            if (sdValue > edValue) {
                errors.push("Task Due Date should not less than the task start date");
            }
        }

        if (errors.length > 0) {
            showErrors(errors);
            return false;
        } else {
            return true;
        }

    }

    $('.quickAddTaskBtn').click(function () {

        if (!validationQuickAddTask()) {
            return;
        }

        var taskName = $("#quickTaskName").val();
        var taskStatusId = $("#quickTaskStatus").val();
        var taskListId = $("#quickAssignTask").val();

        var startDate = $("#quickStartDate").val();
        var dueDate = $("#quickDueDate").val();

        var wrapper = $('.addTaskPanel .taskMultiSelector ul');
        var ids = [];
        $("input", wrapper).each(function () {
            if ($(this).is(":checked")) {
                ids.push($(this).val());
            }
        });

        var newTask = {name: taskName, statusId: taskStatusId, taskListId: taskListId};

        if(ids.length > 0) {
            newTask.assignUserIds = ids;
        }

        if (startDate != "Start Date") {
            newTask.startDate = startDate;
        }

        if (dueDate != "Due Date") {
            newTask.dueDate = dueDate;
        }

        modalPreloader();

        $.ajax({
            type: 'post',
            url: 'addTask',
            data: {newTask: newTask, formData:{projectId:tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function (result) {
                        modalAllClose();
                        modalLoad("#addProjectTaskSuccessModal");

//                        if(!$("#empty").hasClass("hide")) {
//                            $("#empty").addClass('hide');
//                            $("#pt0").show();
//                        }
//
//                        var renderResult = $("#newTaskTemplate").render(result);
//
//                        $(renderResult).insertAfter("#pt0 .taskListContent .taskListInfo");
//
//                        // clear the inputs
//
//                        // 1) clear task name
//                        $("#quickTaskName").val('');
//                        $("#quickTaskName").trigger('blur');
//
//                        // 2) reset task status
//                        $("#quickTaskStatus").val(0);
//
//                        // 3) start date & due date
//                        $("#quickStartDate").val("Start Date").removeClass('selected');
//                        $("#quickDueDate").val("Due Date").removeClass('selected');
//
//
//                        // 4) assignees
//                        $(".addTaskPanel .taskMultiSelector  .dropDown input").removeAttr('checked');
//                        $(".addTaskPanel .taskMultiSelector a.msValue").text("Assign to User");
                        if($("#empty").is(":visible")) {
                            loadAllTaskLists();
                        } else {
                            loadTaskList(result.taskListId);
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });



    // Add New Task
    $('.addNewTaskToList').live('click', function () {
		resetAddTaskModal();
        modalLoad("#addProjectTaskModal");
        // auto select current task list
        $("#addProjectTaskModal form select[name=taskListId]")
            .val($(this).parents(".taskListPanelHeader").find("input[name=taskListId]").val()).trigger('change');
        return false;
    });


    $('.saveNewProjectTask').click(function () {

        if(!validateTask($('#addProjectTaskModal form'))) {
            return;
        }

        modalClose();
        $('#addProjectTaskModal').hide();
        modalPreloader();
        $.ajax({
            type: 'POST',
            url: ctx + "/addTask",
            data: {newTask: $("#addProjectTaskModal form").serializeObject(), formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
//                        if(result.status != 'COMPLETED') {
//                            $($("#newTaskTemplate").render(result)).insertBefore($("#taskList" + result.taskListId + " div.completedTaskTogger"));
//                        } else {
//                            $($("#newTaskTemplate").render(result)).insertAfter($("#taskList" + result.taskListId + " div.completedTaskTogger"));
//                            $("div.completedTaskTogger").show();
//                        }

                        if($("#empty").is(":visible")) {
                            loadAllTaskLists();
                        } else {
                            loadTaskList(result.taskListId, function () {
                                modalLoad("#addProjectTaskSuccessModal");
                            });
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

	$('.closeNewProjectTask').click(function () {
		modalClose();
		$('#addProjectTaskModal').hide();
		return false;
	});
	
	// Add New task list
    $('.addTaskList').live('click', function () {
        resetAddTaskList();
        modalLoad("#addProjectTaskListModal");
        return false;
    });

    $('.saveNewProjectList').click(function () {

        if(!validateTaskList($('#addProjectTaskListModal form'))) {
            return;
        }

        modalClose();
        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/addTaskList",
            data: {newTaskList: $("#addProjectTaskListModal form").serializeObject(), formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        $('#addProjectTaskListModal').hide();
                        loadAllTaskLists(function() { modalLoad("#addProjectTaskListSuccessModal"); });
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

    // Edit task list
    $('.editTaskList').live('click', function () {

        var taskListPanel = $(this).parents(".taskListPanel");
        var taskListId = taskListPanel.find("input[name=taskListId]").val();

        var request;
        if(filterApplied == true) {
            request = {formData:{projectId:tcDirectProjectId}, taskListId:taskListId, filter: buildFilterRequest()};
        } else {
            request = {formData:{projectId:tcDirectProjectId}, taskListId:taskListId};
        }


        // populate EDIT TASK LIST MODAL with task list data retrieved from the server
        $.ajax({
            type: 'POST',
            url: ctx + "/getTaskLists",
            data: request,
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {

                        var m = $("#editProjectTaskListModal");
                        // name
                        $('input[name=name]', m).val(result.name);
                        // notes
                        $('textarea[name=notes]', m).val(result.notes);


                        $("input[name='permittedUsers[][userId]']", m).removeAttr('checked');

                        // privacy setting
                        if (result.permittedUsers.length > 0) {
                            // private task list
                            $("input[name=newTaskListPrivacy]:eq(1)", m).attr('checked', 'checked').trigger('click');

                            $.each(result.permittedUsers, function(index, user){
                                m.find(".taskMultiSelector li input[value='" + user.userId + "']").attr('checked',
                                    'checked');
                            });

                        } else {
                            // public task list
                            $("input[name=newTaskListPrivacy]:eq(0)", m).attr('checked', 'checked').trigger('click');
                        }
                        m.find(".taskMultiSelector .selectorArrow").trigger('click');
                        m.find(".taskMultiSelector .buttonRed1").trigger('click');

                        // association
                        var selectorM = m.find("select[name='associatedToProjectMilestones[][milestoneId]']");
                        var selectorC = m.find("select[name='associatedToContests[][contestId]']");

                        resetAssociationInput(m, 'associatedToProjectMilestones[][milestoneId]');
                        resetAssociationInput(m, 'associatedToContests[][contestId]');

                        if(result.associatedToProjectMilestones.length > 0) {
                            selectorM.parent().removeClass('selectDisabled').find("input[type=checkbox]").attr('checked', 'checked');
                            selectorM.val(result.associatedToProjectMilestones[0].milestoneId).removeAttr('disabled');
                            selectorC.attr('disabled', 'disabled');
                        } else if (result.associatedToContests.length > 0) {
                            selectorC.parent().removeClass('selectDisabled').find("input[type=checkbox]").attr('checked', 'checked');
                            selectorC.val(result.associatedToContests[0].contestId).removeAttr('disabled');
                            selectorM.attr('disabled', 'disabled');
                        }
                        $("#editProjectTaskListModal").data("taskListId", taskListId);
                        modalLoad("#editProjectTaskListModal");

                        $("#editProjectTaskListModal input.limitText").limitedText({
                            max: 80
                        })
                        $("#editProjectTaskListModal textarea").limitedText({
                            max: 250
                        })
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

    $('.saveEditProjectList').click(function () {

        if(!validateTaskList($('#editProjectTaskListModal form'))) {
            return;
        }

        modalClose();
        $('#editProjectTaskListModal').hide();
        var taskForm = $('#editProjectTaskListModal form').serializeObject();
        taskForm['id'] = $("#editProjectTaskListModal").data("taskListId");

        $.ajax({
            type: 'POST',
            url: ctx + "/updateTaskList",
            data: {newTaskList: taskForm, formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        loadTaskList(result.id, function(){ modalLoad("#editProjectTaskListSuccessModal"); });
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

    // Delete task list
    $('.deleteTaskList').live('click', function () {
        var taskListPanel = $(this).parents(".taskListPanel");
        var taskListId = taskListPanel.find("input[name=taskListId]").val();
        modalLoad("#deleteTaskListModal");
        $('#deleteTaskListModal').data("taskListId", taskListId);
        return false;
    });
    $('.confirmDeleteTaskList').click(function () {
        modalClose();
        $('#deleteTaskListModal').hide();

        $.ajax({
            type: 'POST',
            url: ctx + "/deleteTaskList",
            data: {newTaskList: {id: $('#deleteTaskListModal').data("taskListId")}, formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        $("#taskList" + result.taskListDeletedId).remove();
                        modalLoad("#deleteTaskListSuccessModal");
                        loadAllTaskLists();
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
        return false;
    });

    // Resolve task list
    $('.resolveTaskList').live('click', function () {
        var taskListPanel = $(this).parents(".taskListPanel");
        var taskListId = taskListPanel.find("input[name=taskListId]").val();
        modalLoad("#resolveTaskListModal");
        $('#resolveTaskListModal').data("taskListId", taskListId);
        return false;
    });
    $('.confirmResolveTaskList').click(function () {
        modalClose();
        $('#resolveTaskListModal').hide();

        $.ajax({
            type: 'POST',
            url: ctx + "/resolveTaskList",
            data: {newTaskList: {id: $('#resolveTaskListModal').data("taskListId")}, formData: {projectId: tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        $("#taskList" + result.taskListResolvedId).remove();
                        loadAllTaskLists(function () {
                            modalLoad("#resolveTaskListSuccessModal")
                        });
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

    // close modal
    $('.modalBody .closebutton').click(function () {
        var modalID = $(this).parents(".outLay").eq(0).attr("id");
        modalClose();
        $('#' + modalID).hide();
        return false;
    });


    $("#filterDueType").change(function () {
        if ($(this).val() > 0) {
            // disable the date pickers
            $('.taskListFilter .task-date-pick').dpSetDisabled(true);
            $('.taskListFilter .task-date-pick').addClass('disable');
        } else {
            // enable the date pickers
            $('.taskListFilter .task-date-pick').dpSetDisabled(false);
            $('.taskListFilter .task-date-pick').removeClass('disable');
        }
    });
});

function resetAddTaskModal(isMore) {
    if (isMore == "more") {

        // populate task name
        $("#addProjectTaskModal .tasknameField input").val($("#quickTaskName").val());

        if ($("#addProjectTaskModal .tasknameField input").val() == "Task Name") {
            $("#addProjectTaskModal .tasknameField input").val("");
        }

        // populate status id
        $("#addProjectTaskModal select[name=statusId]").val($("#quickTaskStatus").val());

        // populate task list selection
        $("#newTaskSelectList").val($("#quickAssignTask").val());

        $('.addTaskPanel .taskMultiSelector input').each(function (index) {
            var targetInput = $('#addProjectTaskModal .taskMultiSelector input').eq(index);
            if (targetInput.attr("checked") != $(this).attr("checked")) {
                targetInput.trigger("click");
            }
        });

        // the priority is default to normal
        $("#newTaskPriority").val(2);

        if ($("#quickStartDate").hasClass("selected")) {
            $("#newStartDate").dpSetSelected($("#quickStartDate").val());
        } else {
            $("#newStartDate").val("");
            $("#newDueDate").dpSetStartDate("01/01/2001");
        }
        if ($("#quickDueDate").hasClass("selected")) {
            $("#newDueDate").dpSetSelected($("#quickDueDate").val());
        } else {
            $("#newDueDate").val("");
        }

    } else {

        $("#addProjectTaskModal input:not(:checkbox)").val("");
        $("#newDueDate").dpSetStartDate("01/01/2001");
        $('#addProjectTaskModal select').each(function () {
            $(this)[0].selectedIndex = 0;
        });
        $('#addProjectTaskModal input').each(function () {
            if ($(this).attr("checked")) {
                $(this).trigger("click");
            }
        });
    }

    resetAssociationInput($("#addProjectTaskModal"), "associatedMilestoneId");
    resetAssociationInput($("#addProjectTaskModal"), "associatedContestId");

    $('#addProjectTaskModal .taskMultiSelector .selectorArrow').trigger("click");
    $('#addProjectTaskModal .taskMultiSelector .buttonRed1').trigger("click");
    $("#addProjectTaskModal textarea").val("");
    var fileRow = $('#addProjectTaskModal .newFileRow').html();
    $('#addProjectTaskModal .newFileList').html("").append(fileRow).append(fileRow).append(fileRow);
    $("#addProjectTaskModal input.limitText").limitedText({
        max: 80
    })
    $("#addProjectTaskModal textarea").limitedText({
        max: 250
    })

    // default the task priority to Normal
    $("#addProjectTaskModal select[name=priorityId]").val(2);
}

$("#addProjectTaskModal select[name=taskListId]").live('change', function(){
    var taskList = $("#taskList" + $(this).val());
    if(taskList.find("input[name=hasTaskListAssociation]").length > 0) {
        // has task list association, the task cannot have any more
        $("#addProjectTaskModal li.associateField").hide();
    } else {
        $("#addProjectTaskModal li.associateField").show();
    }
});

function resetAddTaskList(){
	$("#addProjectTaskListModal input.text ").val("");
	$("#addProjectTaskListModal textarea").val("");
	$('#addProjectTaskListModal select').each(function() {
		$(this)[0].selectedIndex = 0;
	});
	$('#addProjectTaskListModal input').each(function() {
		if ($(this).attr("checked")){
			$(this).trigger("click");
		}
	});
	$('#addProjectTaskListModal .taskMultiSelector .buttonRed1').trigger("click");
	$("#addProjectTaskListModal .privacyRow input").eq(0).trigger("click");
    $("#addProjectTaskListModal input.limitText").limitedText({
        max:80
    })
    $("#addProjectTaskListModal textarea").limitedText({
        max:250
    })

    resetAssociationInput($("#addProjectTaskListModal"), 'associatedToProjectMilestones[][milestoneId]');
    resetAssociationInput($("#addProjectTaskListModal"), 'associatedToContests[][contestId]');
}
function setEditDueStart(){
	$("#editStartDate").addClass("selected");
	$("#editDueDate").dpSetStartDate($("#editStartDate").val());
}
function displayProgress (){
	//update progress bar display
	$(".taskListContainer .totalNum").each(function() {
		var wrapper = $(this).parent().parent();
		var totalNum = parseInt($(this).text());
		var completedNum = parseInt($(".completedNum", wrapper).text());
		var totalWidth = $(".jsProgress", wrapper).width();
		var greenBarObj = $(".jsProgressNum", wrapper).parent().parent();
		if (completedNum < 1){
			greenBarObj.hide();
		}else {
			greenBarObj.show();
			var paddingFix = 0;
			if (greenBarObj.hasClass("progressBarLeft")){
				paddingFix = 8;	
			}else if (greenBarObj.hasClass("smallProgressBarLeft")){
				paddingFix = 6;	
			}
			var barLength = (completedNum/totalNum)*totalWidth;
			var barNetLength = Math.max(0, (barLength - paddingFix));
			$(".jsProgressNum", wrapper).width(barNetLength);
		}
		
	});
}
function validateTaskList(form) {
    var errors = [];
    if($.trim(form.find("input[name=name]").val()).length == 0) {
        errors.push("Task List name cannot be empty");
    }

    if(form.find("input[name=newTaskListPrivacy]:eq(1)").is(":checked")
        && form.find("input[name='permittedUsers[][userId]']:checked").length == 0) {
        errors.push("Please select at least one user to give the private permission");
    }

    if(errors.length == 0) {
        return true;
    } else {
        showErrors(errors);
        return false;
    }
}

function validateTask(form) {
    var errors = [];
    var taskName = form.find("input[name=name]").val();
    if($.trim(taskName).length == 0) {
        errors.push("Task name cannot be empty");
    }

    if(taskName.length > 80) {
        errors.push("Task name cannot exceed 80 chars");
    }

    if(form.find("textarea[name=notes]").length > 0 && form.find("textarea[name=notes]").val().length > 250) {
        errors.push("Task notes cannot exceed 250 chars");
    }

    var startDate = form.find("input[name=startDate]").val();
    var dueDate = form.find("input[name=dueDate]").val();

    if(startDate && dueDate) {
        startDate = Date.parse(startDate);
        dueDate = Date.parse(dueDate);
        if(dueDate < startDate) {
            errors.push("The task due date should not be less than the task start date");
        }
    }

    if(errors.length == 0) {
        return true;
    } else {
        showErrors(errors);
        return false;
    }
}

(function ($) {

    $.fn.limitedText = function (options) {

        var max = options.max;

        function limit(textarea) {
            var val = textarea.val();
            if (val.length > max) {
                textarea.val(val.substring(0, max));
            } else {
                textarea.parent().find(".num").text(max - val.length)
            }

            if (val.length > 0) {
                textarea.parent().find(".errorMessage").text('');
                textarea.removeClass('invalid');
            }
        }

        this.each(function () {

            $(this).parent().find(".num").text(options.max - $(this).val().length);
            $(this).keydown(function () {
                limit($(this));
            });
            $(this).keyup(function () {
                limit($(this));
            });

        })
    }

})(jQuery)
