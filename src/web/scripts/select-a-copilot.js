/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file is used to render get a copilot page.
 * 
 * Version 1.0 Release Assembly - TC Direct Select From Copilot Pool Assembly
 * - Apply to new prototype, provide handling for selec from copilot pool page.
 * 
 * * Version 1.1 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * 
 *
 * @author TCSASSEMBLER
 * @version 1.1 
 */
$(document).ready(function(){
    $('.copilotGridDiv .firstLine a, .copilotListTable .userLinkTD a').attr("target", "_blank");
    $(".copilotPool .copilotListTable").hide();
    
    var options = {
        items_per_page: num_display,
        callback: handlePaginationClick,
        num_display_entries: 5,
        next_show_always: true,
        prev_show_always: true,
        load_first_page: true
    };
    
    $("#pagingDiv").pagination($(".wideList .projectItem").length, options);
    handleSortSelectChangeEvent(copilotListTable);
    
    $(".stepContainer.copilotPool").removeClass("hide");
    
    $(".copilotList .dataTables_wrapper .BottomBar").append($(".bottomCopyArea").html());
    
    $("#switchGridViewLink").click(function() {
        changeBetweenGridAndList(true, copilotListTable);
    });
    $("#switchListViewLink").click(function() {
        changeBetweenGridAndList(false, copilotListTable);
    });
    
    var copilotListTable = $(".copilotListTable").dataTable({
        iDisplayLength: 10,
        bFilter: false,
        bAutoWidth: false,
        sPaginationType: "full_numbers",
        sDom: 't<"BottomBar"<"pageArea"p>>',
        oLanguage: {
            oPaginate: {
                sPrevious: "Prev",
                sNext: "Next"
            }
        },
		aoColumns: [
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			{ bSortable: false }
		]
    });    
    changeBetweenGridAndList(true, copilotListTable);
    
    // copilot
	$(".selectCopilot").live('click',function() {
        handleOpenCopilotModal(this);
	});
    
    $(".selectCopilotList").live('click', function() {
        var userId = $(this).next().val();
        var obj = $('#gridSelectButton_' + userId);
        handleOpenCopilotModal(obj);
    });
    
    $("#copilotPopUp .submitBtn").live('click',function() {
		addresscloseModal();
        
        if ($(this).hasClass("button10")) {
            showErrors(["You can only choose an active copilot."]);
        } else {
            currentUser = $(this).parent().parent().find(".copilotInfo");
            showSelectContainer(currentUser);
        }
	});
    
    $("#sortSelect").change(function() {
        handleSortSelectChangeEvent(copilotListTable);
    });
    
    /**
    * Handle add new project button click event.
    */
    $('#addNewProjectBtn').click(function(){
        clearAddNewProjectForm();
        modalLoad("#addNewProjectModal");
        $('#addNewProjectModal').find('#projectName').focus();
    });     
    
    $("#hireCopilotBtn").click(function() {
        handleHireButtonClick();
    });
    
    $(window).resize(function(){
        var numChanged = false;
        
        if($(".projectList").width() > 1210){
            if (num_display != 12) {
                num_display = 12;
                
                numChanged = true;
            }

			listType(4);
		}
		else{
            if (num_display != 9) {
                num_display = 9;
                
                numChanged = true;
            }
			listType(3);
		}
        
        if (numChanged) {
            options['items_per_page'] = num_display;
            $("#pagingDiv").pagination($(".wideList .projectItem").length, options);
            handleSortSelectChangeEvent(copilotListTable);         
        }
        
    });  
    $(window).resize();    
    
    $(".copilotListTable table th").click(function() {        
        var columnVal = $(this).find("input").val();
        
        $("#sortSelect option[value=" + columnVal + "]").attr("selected", "selected"); 
    });    

});

/**
 * The displayed number.
 */
var num_display = 9;

/**
 * The current user.
 */
var currentUser;

/**
 * Handling sort select chagne event.
 *
 * @param copilotListTable the dataTable obj
 */
function handleSortSelectChangeEvent(copilotListTable) {
    var sortClass = $("#sortSelect option:selected").val();
	var order = "desc";
	if (sortClass == 'userHandleInput') {
		order = "asc";
	}
		
    if ($(".copilotPool .copilotListTable").is(":hidden")) {
		
        $(".copilotGridDiv .projectItem").tsort("." + sortClass, {
            order: order, 
            useVal: true
        });
        
        //handlePaginationClick(0);
        $("#pagingDiv").trigger('setPage', [0]);
    } else {
        var sortIndex;
        
        switch(sortClass)
        {
            case "userHandleInput":
                sortIndex = 0;
                break;   
                
            case "totalProjectsInput":
                sortIndex = 1;
                break;
            case "totalContestsInput":
                sortIndex = 2;
                break;
            case "currentProjectsInput":
                sortIndex = 6;
                break;
            case "currentContestsInput":
                sortIndex = 7;
                break;

            case "repostContestsInput":
                sortIndex = 3;
                break;
            case "failureContestsInput":
                sortIndex = 4;
                break;                       

            default:
                sortIndex = 0;
        }
        
        copilotListTable.fnSort([[sortIndex, order]]);
    }
    $(window).resize();    
}  

/**
 * Lists the copilot in grid view.
 *
 * @param num the number
 */
function listType(num){
    var listWidth = $(".projectList").width();
    var itemWidth = 293;
    $(".projectItem").css("margin-right",(listWidth - itemWidth*num) / (num-1) + "px");
    $(".wideList .projectItem").each(function(i) {
        var number =$(this).index() + 1;
        if (number%num == 0) { 
            $(this).css("margin-right","0"); 
        }
    });
}    

/**
 * Handling open copilot modal event.
 *
 * @param obj the object to add.
 */
function handleOpenCopilotModal(obj) {
    $("#copilotPopUp .copilotInfo").html("");
    
    $("#copilotPopUp .copilotInfo").append($(obj).parent().parent().html());
    
    $("#copilotPopUp .copilotInfo .selectCopilot").remove();
    $("#copilotPopUp .copilotInfo .copilotProfileLink").remove();
    
    $("#copilotPopUp .copilotInfo .copioltPic").append($(obj).parent().parent().find(".copilotProfileLink").clone());
    
    if ($(obj).parent().parent().find('td .greenText').length == 0) {
        $("#copilotPopUp .buttonArea .submitBtn").removeClass("button6");
        $("#copilotPopUp .buttonArea .submitBtn").addClass("button10");
    } else {
        $("#copilotPopUp .buttonArea .submitBtn").removeClass("button10");
        $("#copilotPopUp .buttonArea .submitBtn").addClass("button6");
    }
    
    addressLoadModal('#copilotPopUp');
}

/**
 * Handling pagination click event.
 *
 * @param new_page_index the current page index.
 * @param pagination_container the pagination container.
 */
function handlePaginationClick(new_page_index, pagination_container) {
    $(".wideList .projectItem").hide();    
    $(".wideList .projectItem").slice(new_page_index * num_display, new_page_index * num_display + num_display).show();
    
    return false;
}

/**
 * Change between grid view and list view.
 *
 * @param showGrid show grid or not.
 * @param copilotListTable the list table.
 */
function changeBetweenGridAndList(showGrid, copilotListTable) {
    if (showGrid) {
        $(".copilotPool .copilotListTable").hide();
        $(".copilotList .dataTables_wrapper .BottomBar").hide();
        
        $("#copilotGridBottomBar").show();
        $(".copilotPool .copilotGridDiv").show();
    } else {
        $(".copilotPool .copilotListTable").show();
        $(".copilotList .dataTables_wrapper .BottomBar").show();
        
        $("#copilotGridBottomBar").hide();
        $(".copilotPool .copilotGridDiv").hide();
    }
    handleSortSelectChangeEvent(copilotListTable);
}

/**
 * Show select container.
 *
 * @param currentUser the current user.
 */
function showSelectContainer(currentUser) {
    $(".currentPage strong").html("Introduction Get a Copilot");
    
    // set copilot profile section
    $("#stepContainerSelect .copilotInfo").html(currentUser.html());
    
    $("#stepContainerView").hide();
    $("#stepContainerSelect").show();
    
    $(window).resize();
}

/**
 * Adds a new project.
 */
function addNewProject() {
    var projectName = $('#addNewProjectModal').find('input[name="newProjectName"]').val();
    var projectDescription = $('#addNewProjectModal').find('textarea[name="newProjectDescription"]').val();

    var errors = [];

    if (!checkRequired(projectName)) {
        errors.push('Project name is empty.');
    }

    if (!checkRequired(projectDescription)) {
        errors.push('Project description is empty.');
    }

    if(errors.length > 0) {
       showErrors(errors);
       $("#modal-background").hide();
       return;
    }

    $.ajax({
        type: 'POST',
        url:  "../launch/createProject",
        data: setupTokenRequest({'projectName':projectName,
            'projectDescription':projectDescription}, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    var projectData = result;
                    $("<option/>").val(projectData.projectId).text(projectData.name).attr("selected", true).appendTo("#projectName");

                    modalCloseAddNewProject();
                    showSuccessfulMessage('Project <span class="messageContestName">' + projectData.name + '</span> is created successfully.');

                },
                function(errorMessage) {
                    modalCloseAddNewProject();
                    showServerError(errorMessage);
                });
        }
    });
};

/**
 * Handling hire button click event.
 */
function handleHireButtonClick() {
    var tcProjectId = parseInt($('select#projectName').val());
    var copilotProfileId = parseInt($('#stepContainerSelect .profileIdInput').val());
    
    var errors = [];
    
    if(tcProjectId < 0) {
        errors.push('Project name could not be empty.');
    }

    if(errors.length > 0) {
       showErrors(errors);
       $("#modal-background").hide();
       return;
    }    
    
    var operationData = {};
    operationData['copilotProjectOperations'] = [{
        projectId: tcProjectId,
        copilotProfileId: copilotProfileId,
        copilotProjectId: -1,
        operation : 'ADD'
    }]
    
    modalPreloader();
    $.ajax({
        type: 'POST',
        url:  "processCopilotProjects",
        data: operationData,
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    modalClose();
                    $("#confirm-copilotHandle").append($("a", $('#stepContainerSelect table tr')[0]).clone());
                    $("#confirm-projectName").html($.trim($('select#projectName').find("option:selected").text()));
                    handleHireCopilotSuccess();
                    setTimeout(function() {window.location.href = '/direct/projectOverview?formData.projectId=' + tcProjectId;}, 2000);
                },
                function(errorMessage) {
                    modalClose();
                    showServerError(errorMessage);
                });
        }
    });    
}

/**
 * Handling hire button success event.
 */
function handleHireCopilotSuccess() {
    $(".currentPage strong").html("Post a Copilot");
    
    // set copilot profile section
    $("#stepContainerSelect").hide();
    $("#stepContainerConfirmation").show();
    
    $(window).resize();
}
