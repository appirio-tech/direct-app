/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * Javascript for for the project tasks pages.
 *
 * @version 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 * @author TCSASSEMBLER
 */
var quickTaskNameHint = "Task Name";

var userLinksCache = {};

var statusMap = {
    "NOT_STARTED": {id: 0, name: "Not Started"},
    "IN_PROGRESS": {id: 1, name: "In Progress"},
    "WAIT_ON_DEPENDENCY": {id: 2, name: "Wait On Dependency"},
    "COMPLETED": {id: 3, name: "Completed"}
}

if($.views) {
    $.views.helpers({
        getUserLink: getUserLink,
        getStatusId: getStatusId,
        getStatusName: getStatusName
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

$(document).ready(function(){

    // build user links cache
    $(".addTaskPanel input[name=quickAssignUser]").each(function () {
        var userId = $(this).val();
        var userLink = $(this).next("a")[0].outerHTML;
        userLinksCache[userId] = userLink;
    });

    //Init Task pages, update number, hide completed items.
	$("#switchTaskList")[0].selectedIndex = 0;
	$("#switchTaskGroupBy")[0].selectedIndex = 0;
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
	
	$('.taskMultiSelector .trigger').live('click', function(){
		var wrapper = $(this).parent();
		var wrapperWidth = wrapper.width();
		wrapper.addClass("taskMultiSelectorOpen");
		$('.dropDown', wrapper).width(wrapperWidth - 2);
	})
	$('.taskMultiSelector .btnWrapper a').live('click', function(){
		var wrapper = $(this).parents(".taskMultiSelector").eq(0);
		wrapper.removeClass("taskMultiSelectorOpen");
		var displayTxt = ""
		$("input", wrapper).each(function() {
			if ($(this).attr("checked")){
				if (displayTxt==""){
					displayTxt = $(this).next("a").text();
				}else{
					displayTxt += ", " + $(this).next("a").text();
				}
			}
		});
		if (displayTxt == ""){
			displayTxt = $(".trigger label", wrapper).text();
		}
		$(".trigger .msValue", wrapper).text(displayTxt).attr("title", displayTxt);
	})
	$('.taskMultiSelector li label, #filterByStatus li label, #filterByPriority li label, .checkBoxWrapper label, ').live('click', function(){
		var li = $(this).parent();
		$("input", li).trigger("click");
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
				$(".groupByTaskList .taskListPanel").show();
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
		}else{
			$(".groupByTaskList").hide();
			$(".groupByDueDate").show();
			if(ptName == "ptAll"){
				$(".groupByDueDate .taskListPanel").show();
				$(".groupByDueDate .taskItem").show();
			}else{
				$(".groupByDueDate .taskListPanel").show();
				$(".groupByDueDate .taskItem").hide();
				$(".groupByDueDate ." + ptName).show();
				$('.groupByDueDate .taskListPanel').each(function() {
					if ($(".taskItem:visible", $(this)).length < 1){
						$(this).hide();
					}
				});
			}
		}
	})

	
	//show completed tasks
	$('.showCompletedTasks').click(function(){
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
	
	//show complted task list
	$('.showCompletedTaskLists').click(function(){
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
	
	
	//Edit task
	$('.taskEditLink').live("click",function () {
		var wrapper = $(this).parents(".taskItem").eq(0);
		
		if (wrapper.hasClass("taskItemDetailed")){
			var editForm = $(".taskEditTemplate").html();
			wrapper.addClass("taskItemEditing");
			wrapper.hide();
			wrapper.after(editForm);
			var editForm = wrapper.next();
			$(".dueDateWrapper", editForm).html("<input type='text' name='editStartDate' readonly='readonly' id='editStartDate' onchange='setEditDueStart()' class='fLeft text task-date-pick' value=''/><span class='fLeft'>to</span><input type='text' name='editDueDate' id='editDueDate' readonly='readonly' class='fLeft text task-date-pick' value=''/>");
			$(".task-date-pick", editForm).datePicker({startDate:'01/01/2001'}).dpSetSelected('12/20/2012');
			var taskName = $(".priorityLabel", wrapper).text();
			$(".inputWrapper input", editForm).val(taskName);


			$('.taskAttachment', editForm).each(function() {
				if ( $(this).text().length > 30) {
					turncatedTxt  = $(this).text().substring(0, 27) + " ...";
					$(this).text(turncatedTxt);
				}
			});


		}else{
			var editForm = $(".quickTaskEditTemplate").html();
			wrapper.addClass("taskItemEditing");
			wrapper.hide();
			wrapper.after(editForm);
			var editForm = wrapper.next();
			$(".quickEditCol3", editForm).html("<label>Due Date</label><input type='text' name='quickEditDueDate' readonly='readonly' class='fLeft text task-date-pick quickEditDueDate'/>");
			$(".task-date-pick", editForm).datePicker({startDate:'01/01/2001'}).dpSetSelected('12/20/2012');
			var taskName = $(".priorityLabel", wrapper).text();
			$(".inputWrapper input", editForm).val(taskName);
			var progress = $(".col2 p", wrapper).text();
			if (progress == "Not Started"){
				$(".quickEditCol2 select", editForm)[0].selectedIndex = 1;
			}else if (progress == "In Progress"){
				$(".quickEditCol2 select", editForm)[0].selectedIndex = 2;
			}else if (progress == "Waiting on Dependency"){
				$(".quickEditCol2 select", editForm)[0].selectedIndex = 3;
			}else if (progress == "Completed"){
				$(".quickEditCol2 select", editForm)[0].selectedIndex = 4;
			}
		}
		

	
	});
	$('.taskEditSave').live("click",function () {
		var wrapper = $(this).parents(".editTaskItem").eq(0);
		wrapper.prev().show().removeClass("taskItemEditing");
		wrapper.remove();
	});
	
	$('.taskCancelSave').live("click",function () {
		var wrapper = $(this).parents(".editTaskItem").eq(0);
		wrapper.prev().show().removeClass("taskItemEditing");
		wrapper.remove();
	});
	
	//Delete task
	$('.taskDeleteLink').live("click",function () {
		modalLoad("#deleteTaskModal");
		return false;
	});
	$('.confirmDeleteTask').click(function () {
		modalClose();
		$('#deleteTaskModal').hide();
		modalLoad("#deleteTaskSuccessModal");
		return false;
	});
	
	//Add task
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
            url: 'quickCreateNewTask',
            data: {newTask: newTask, formData:{projectId:tcDirectProjectId}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function (result) {
                        modalAllClose();
                        modalLoad("#addProjectTaskSuccessModal");

                        if(!$("#empty").hasClass("hide")) {
                            $("#empty").addClass('hide');
                            $("#pt0").show();
                        }

                        var renderResult = $("#newTaskTemplate").render(result);

                        $(renderResult).insertAfter("#pt0 .taskListContent .taskListInfo");

                        // clear the inputs

                        // 1) clear task name
                        $("#quickTaskName").val('');
                        $("#quickTaskName").trigger('blur');

                        // 2) reset task status
                        $("#quickTaskStatus").val(0);

                        // 3) start date & due date
                        $("#quickStartDate").val("Start Date").removeClass('selected');
                        $("#quickDueDate").val("Due Date").removeClass('selected');


                        // 4) assignees
                        $(".addTaskPanel .taskMultiSelector  .dropDown input").removeAttr('checked');
                        $(".addTaskPanel .taskMultiSelector a.msValue").text("Assign to User");
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });




    $('.addNewTaskToList').click(function () {
		resetAddTaskModal();
        modalLoad("#addProjectTaskModal");
        return false;
    });
	$('.saveNewProjectTask').click(function () {
		modalClose();
		$('#addProjectTaskModal').hide();
		modalLoad("#addProjectTaskSuccessModal");
		return false;
	});
	$('.closeNewProjectTask').click(function () {
		modalClose();
		$('#addProjectTaskModal').hide();
		return false;
	});
	
	//Add task list
	$('.addTaskList').click(function () {
		resetAddTaskList();
        modalLoad("#addProjectTaskListModal");
        return false;
    });
	$('.saveNewProjectList').click(function () {
		modalClose();
		$('#addProjectTaskListModal').hide();
		modalLoad("#addProjectTaskListSuccessModal");
		return false;
    });
	
	//Eidt task list
	$('.editTaskList').click(function () {
        modalLoad("#editProjectTaskListModal");
		$("#editProjectTaskListModal input.limitText").limitedText({
			max:120
		})
		$("#editProjectTaskListModal textarea").limitedText({
			max:500
		})
        return false;
    });
	$('.saveEditProjectList').click(function () {
		modalClose();
		$('#editProjectTaskListModal').hide();
		modalLoad("#editProjectTaskListSuccessModal");
		return false;
    });

	//Delete task list	
	$('.deleteTaskList').click(function () {
		modalLoad("#deleteTaskListModal");
		return false;
	});
	$('.confirmDeleteTaskList').click(function () {
		modalClose();
		$('#deleteTaskListModal').hide();
		modalLoad("#deleteTaskListSuccessModal");
		return false;
	});
	
	//Resolve task list
	$('.resolveTaskList').click(function () {
		modalLoad("#resolveTaskListModal");
		return false;
	});
	$('.confirmResolveTaskList').click(function () {
		modalClose();
		$('#resolveTaskListModal').hide();
		modalLoad("#resolveTaskListSuccessModal");
		return false;
	});	
	
	//close modal
	$('.modalBody .closebutton').click(function () {
		var modalID = $(this).parents(".outLay").eq(0).attr("id");
		modalClose();
		$('#' + modalID).hide();
		return false;
	});	
});
function resetAddTaskModal(isMore){
	if (isMore == "more"){
		$("#addProjectTaskModal .tasknameField input").val($("#quickTaskName").val());
		if ($("#addProjectTaskModal .tasknameField input").val() == "Task Name"){
			$("#addProjectTaskModal .tasknameField input").val("");		
		}
		$("#newTaskStatus")[0].selectedIndex = ($("#quickTaskStatus")[0].selectedIndex );
		$("#newTaskSelectList")[0].selectedIndex = ($("#quickAssignTask")[0].selectedIndex - 1 );
		$('.addTaskPanel .taskMultiSelector input').each(function(index) {
			var targetInput = $('#addProjectTaskModal .taskMultiSelector input').eq(index);
			if ( targetInput.attr("checked") != $(this).attr("checked") ) {
				targetInput.trigger("click");
			}
		});
		
		if ($("#quickStartDate").hasClass("selected")){
			$("#newStartDate").dpSetSelected($("#quickStartDate").val());	
		}else{
			$("#newStartDate").val("");
			$("#newDueDate").dpSetStartDate("01/01/2001");
		}
		if ($("#quickDueDate").hasClass("selected")){
			$("#newDueDate").dpSetSelected($("#quickDueDate").val());	
		}else{
			$("#newDueDate").val("");
		}	
	}else{
		$("#addProjectTaskModal input").val("");
		$("#newDueDate").dpSetStartDate("01/01/2001");
		$('#addProjectTaskModal select').each(function() {
			$(this)[0].selectedIndex = 0;
		});
		$('#addProjectTaskModal input').each(function() {
			if ($(this).attr("checked")){
				$(this).trigger("click");
			}
		});
	}
	$('#addProjectTaskModal .taskMultiSelector .buttonRed1').trigger("click");
	$("#addProjectTaskModal textarea").val("");
	var fileRow = $('#addProjectTaskModal .newFileRow').html();
	$('#addProjectTaskModal .newFileList').html("").append(fileRow).append(fileRow).append(fileRow);
    $("#addProjectTaskModal input.limitText").limitedText({
        max:120
    })
    $("#addProjectTaskModal textarea").limitedText({
        max:500
    })
}

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
        max:120
    })
    $("#addProjectTaskListModal textarea").limitedText({
        max:500
    })
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