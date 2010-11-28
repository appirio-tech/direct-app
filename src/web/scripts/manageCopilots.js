/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * <p>
 * A javascript file used to handle event for copilot manage page.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
$(function() {
    // add even class
    $('table.newContestsStatus tbody tr:even').addClass('even');
    $('table.newManageStatus tbody tr.trNormal:odd').addClass('even');
    $('table.newManageStatus tbody tr.trNormal:odd').nextUntil('tr.trNormal')
            .addClass('even');

    /**
     * Handle row expand link click event.
     */
    $('table.newManageStatus tbody tr.trNormal .expand').click(function() {
        if ($(this).hasClass('fold')) {
            $(this).parent().parent().nextUntil('.trNormal').addClass(
                    'hide');
            $(this).removeClass('fold');
        } else {
            $(this).parent().parent().nextUntil('.trNormal')
                    .removeClass('hide');
            $(this).addClass('fold');
        }
    });

    /**
     * Initiate manage user dialog.
     */
    $('#manageUserDialog').dialog( {
        autoOpen : false,
        height : 450,
        width : 580,
        modal : true,
        draggable : false,
        resizable : false
    });

    /**
     * Initiate remove project dialog and make sure dialog.
     */
    $.each( [ "removeProjectDialog", "makeSureDialog" ], function(index, item) {
        $('#' + item).dialog( {
            autoOpen : false,
            height : 160,
            width : 580,
            modal : true,
            draggable : false,
            resizable : false
        });
    });

    /**
     * Set close event for each dialog.
     */
    $.each( [ "manageUserDialog", "makeSureDialog", "removeProjectDialog" ], function(index, item) {
        $('#' + item + ' .closeDialog').click(function() {
            $('#' + item).dialog("close");
            return false;
        });

        $('#' + item + ' .foot .okey2Button').click(function() {
            $('#' + item).dialog("close");
            return false;
        });
    });

    /**
     * Handle add copilot button click event.
     */
    $('.addCopilotUsers').click(function(e) {
        // initiate left panel
		clearDialogLeftItems();
		$('#manageUserDialog .searchBox .searchTxt input').val('')

		// initiate right panel
		initiateDialogRightPanel(e);

		// set save event
		setSaveButtonClickEvent(e);

		$('#manageUserDialog .searchBox .button1').click();
		
		// open dialog
		$('#manageUserDialog').dialog("open");
		return false;
	});

    /**
     * Handle search button click event.
     */
    $('#manageUserDialog .searchBox .button1').click(function() {
        clearDialogLeftItems();

        var searchTxt = $('#manageUserDialog .searchBox .searchTxt input').val();

        $('#copilotsList input').each(function(index, item) {
            if ($(item).val()
                    .indexOf(searchTxt) != -1) {
                if ($('#manageUserDialog .right .list .listItem[name=' + $(
                        item).attr("name") + ']').length == 0) {
                    // add item to left only if
                    // it's not exist in right
                    // panel
                    var content = $(item).val();
                    // content =
                    // content.replace(new
                    // RegExp(searchTxt, "g"),
                    // "<span class='b'>" +
                    // searchTxt + "</span>");
                    content = replaceAllWithString(content, searchTxt, "<span class='b'>" + searchTxt + "</span>");
                    content = "<div class='listItem' name='"
                            + $(item).attr("name")
                            + "'>"
                            + content
                            + "</div>";

                    $('#manageUserDialog .left .list').append(content);
                }
            }
        });

        setItemClickEvent();
    });

    /**
     * Handle select all event.
     */
    $('#manageUserDialog .left .rightTxt').click(function() {
        $('#manageUserDialog .left .list .listItem').addClass("active");
    });

    /**
     * Handle add items event.
     */
    $('#manageUserDialog .middle .addItem').click(function() {
        changeItems("left", "right");
    });

    /**
     * Handle remove items event.
     */
    $('#manageUserDialog .middle .removeItem').click(function() {
        changeItems("right", "left");
    });

    /**
     * Handle remove all event.
     */
    $('#manageUserDialog .right .rightTxt').click(function() {
        $('#manageUserDialog .right .list .listItem').addClass("active");
        changeItems("right", "left");
    });

    /**
     * Handle click project tab event.
     */
    $('#projectViewLink').click(function() {
        $('.myCopilotsContestsList #tabs3 .firstItem').addClass("on");
        $('.myCopilotsContestsList #tabs3 .lastItem').removeClass("on");

        $('#copilotProjectTable').removeClass("hide");
        $('#copilotContestTable').addClass("hide");
    });

    /**
     * Handle click contest tab event.
     */
    $('#contestViewLink').click(function() {
        $('.myCopilotsContestsList #tabs3 .firstItem').removeClass("on");
        $('.myCopilotsContestsList #tabs3 .lastItem').addClass("on");

        $('#copilotProjectTable').addClass("hide");
        $('#copilotContestTable').removeClass("hide");
    });

    // update rows
    updateRows();
});

/**
 * Replace src to dst with content string.
 * 
 * @param content
 *            the content string
 * @param src
 *            the src string to be replaced
 * @param dst
 *            the dst string to be replaced as
 * @return replaced string
 */
function replaceAllWithString(content, src, dst) {
    return content.replace(new RegExp(src, "g"), dst);
};

/**
 * Change items from src panel to dst panel.
 * 
 * @param src
 *            the class of src panel
 * @param dst
 *            the class of dst panel
 */
function changeItems(src, dst) {
    $('#manageUserDialog .' + src + ' .list .listItem.active').each(function() {
        $(this).removeClass("active");
        $('#manageUserDialog .' + dst + ' .list').append(this);
    });

    $('#manageUserDialog .' + src + ' .list .listItem.active').remove();
};

/**
 * Clear left panel for manage user panel.
 */
function clearDialogLeftItems() {
    $('#manageUserDialog .left .list .listItem').remove();
};

/**
 * Initiate the right panel for manage user panel.
 * 
 * @param e
 *            the click event
 */
function initiateDialogRightPanel(e) {
    $('#manageUserDialog .right .list .listItem').remove();

    $(e.target).parent().parent().parent().nextUntil('.trNormal').each(function(index, item) {
        var span = $(item).children().first().find("span");
        var name = span.attr("name");
        var handle = span.html();
        var content = "<div class='listItem' name='" + name + "'>"
                + handle + "</div>";

        $('#manageUserDialog .right .list').append(content);
    });

    setItemClickEvent();
};

/**
 * Set items click event.
 */
function setItemClickEvent() {
    $(".ui-dialog .body .list .listItem").unbind("click");
    $(".ui-dialog .body .list .listItem").click(function() {
        $(this).toggleClass('active');
    });
};

/**
 * Remove one copilot project.
 * 
 * @param projectId
 *            the project id
 * @param copilotProfileId
 *            the copilot profile id
 * @param copilotProjectId
 *            the copilot project id
 */
function removeCopilotProject(projectId, copilotProfileId, copilotProjectId) {
    var operations = [];
    operations[0] = {
        projectId : parseInt(projectId),
        copilotProfileId : parseInt(copilotProfileId),
        copilotProjectId : parseInt(copilotProjectId),
        operation : 'REMOVE'
    };

    processCopilotProjectOperations(operations, true);
};

/**
 * Process update copilot project action.
 * 
 * @param operations
 *            the operations
 */
function processCopilotProjectOperations(operations, isRemove) {
    var request = {
        copilotProjectOperations : operations
    };

    $.ajax( {
        type : 'post',
        url : 'processCopilotProjects',
        cache : false,
        data : request,
        dataType : 'json',
        success : function(result) {
			hanldeCopilotProjectOperationsResult(result, isRemove);
		}
    });
};

/**
 * Handle update copilot projects result.
 * 
 * @param jsonResult
 *            the json result
 */
function hanldeCopilotProjectOperationsResult(jsonResult, isRemove) {
    if (jsonResult.result != null) {
        var data = jsonResult.result["return"];
        var projectName;
        var handle;
		
		projectName = $('.trNormal[name=project_' + data[0].projectId + '] .tdTitle .longWordsBreak').html();
		handle = $('#copilotsList input[name=copilot_' + data[0].copilotProfileId + ']').val();

        $.each( ["makeSureDialog", "removeProjectDialog" ], function(index, item) {
            $('#' + item + ' .closeDialog').unbind("click");
            $('#' + item + ' .closeDialog').click(function() {
                $('#' + item).dialog("close");
                handleOperationsResult(data);
                
                return false;
            });

            $('#' + item + ' .foot .okey2Button').unbind("click");
            $('#' + item + ' .foot .okey2Button').click(function() {
                $('#' + item).dialog("close");
                handleOperationsResult(data);
                
                return false;
            });
        });
    
		if (isRemove) {
            $('#removeProjectDialog .projectName').html(projectName);
            $('#removeProjectDialog .handle').html(handle);
            $('#removeProjectDialog').dialog("open");	    
		} else {
            $('#makeSureDialog .projectName').html(projectName);
            $('#makeSureDialog').dialog("open");
		}
    } else {
        alert("Error occurs when update copilot project.");
    }
};

/**
 * Handle operations result.
 *
 * @param data 
 *              the data result
 */
function handleOperationsResult(data) {
    $.each(data, function(index, item) {
        projectName = $('.trNormal[name=project_' + item.projectId + '] .tdTitle .longWordsBreak').html();
        handle = $('#copilotsList input[name=copilot_' + item.copilotProfileId + ']').val();

        if (item.operation == 'REMOVE') {
            $('#project_' + item.projectId
                + "_copilot_"
                + item.copilotProfileId).parent().parent().remove();

        } else {
            var tr = $('.trNormal[name=project_' + item.projectId + ']');
            var trToAdd = $('#trTemplate').html();

            trToAdd = replaceAllWithString(trToAdd, '@projectId@', item.projectId);
            trToAdd = replaceAllWithString(trToAdd, '@copilotProfileId@',
                    item.copilotProfileId);
            trToAdd = replaceAllWithString(trToAdd, '@copilotProjectId@', item.copilotProjectId);
            trToAdd = replaceAllWithString(trToAdd, '@copilotType@', item.copilotType);
            trToAdd = replaceAllWithString(trToAdd, '@handle@', $('#copilotsList input[name=copilot_' + item.copilotProfileId + ']').val());

            $(trToAdd).insertAfter(tr);
        }
    });

    updateRows();
};

/**
 * Update rows.
 */
function updateRows() {
    $('.trNormal .expand').each(function(index, item) {
        // set lastTr
        $(this).parent().parent().nextUntil('.trNormal').removeClass(
                'lastTr');
        $(this).parent().parent().nextUntil('.trNormal').last().addClass(
                'lastTr');

        // set hide class
        if ($(this).hasClass('fold')) {
            $(this).parent().parent().nextUntil('.trNormal').removeClass(
                    'hide');
        } else {
            $(this).parent().parent().nextUntil('.trNormal').addClass(
                    'hide');
        }
    });

    // set even rows
    $('.trNormal.even .expand').each(function(index, item) {
        $(this).parent().parent().nextUntil('.trNormal').addClass('even');
    });

    // set copilots number
    $('#copilotProjectTable .trNormal').each(function(index, item) {
        $($(item).find('td')[1]).html($(item).nextUntil('.trNormal').length);
    });

    // set 'None' if there is no copilot assign to contest
    $('#copilotContestTable .trChild .photo').each(function(index, item) {
        if ($(item).find('div').length == 0) {
            if ($(item).find('span').length == 0) {
                $(item).append("<span class='noneCopilot' style='padding-left:32px;'>None</span>");
            }
        } else {
            $(item).find("span.noneCopilot").remove();
        }
    });
};

/**
 * Handle save button click event.
 * 
 * @param e
 *            the event
 */
function handleSaveEvent(e) {
    var tr = $(e.target).parent().parent().parent();
    var projectId = tr.attr("name").substring("project_".length);
    var operations = [];

    // handle remove actions
    var copilots = [];
    var copilotProjectIds = [];
    tr.nextUntil('.trNormal').each(function(index, item) {
        var span = $(item).children().first().find("span");
        var name = span.attr("name");
        if ($('#manageUserDialog .right .list .listItem[name=' + name + ']').length == 0) {
            var copilotProfileId = name
                    .substring('copilot_'.length);
            var copilotProjectId = $(
                    '#project_' + projectId + "_copilot_"
                            + copilotProfileId).val();

            operations[operations.length] = {
                projectId : parseInt(projectId),
                copilotProfileId : parseInt(copilotProfileId),
                copilotProjectId : parseInt(copilotProjectId),
                operation : 'REMOVE'
            };
        }
    });

    // handle add actions
    $('#manageUserDialog .right .list .listItem').each(function(index, item) {
        var copilotProfileId = $(item).attr("name").substring(
                'copilot_'.length);
        if ($('#project_' + projectId + "_copilot_"
                + copilotProfileId).length == 0) {
            operations[operations.length] = {
                projectId : parseInt(projectId),
                copilotProfileId : parseInt(copilotProfileId),
                // copilotProjectId :
                // parseInt(copilotProjectId),
                operation : 'ADD'
            };
        }
    });

	$('#manageUserDialog').dialog("close");
	
    // process operation request
    if (operations.length > 0) {
        processCopilotProjectOperations(operations);
    }
};

/**
 * Set save button click event.
 * 
 * @param e
 *            the event
 */
function setSaveButtonClickEvent(e) {
    $('#manageUserDialog .foot .saveDialogButton').unbind("click");
    $('#manageUserDialog .foot .saveDialogButton').click(function() {
        handleSaveEvent(e);
    });
};