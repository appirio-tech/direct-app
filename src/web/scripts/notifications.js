/**
 * The JS script for notification.
 *
 *  Version 1.1 - (TC Direct - Page Layout Update Assembly 2) changes:
 *  - Update the codes to fix layout issues of notification
 *
 *  Version 1.2 - (Release Assembly - TC Cockpit Project Forum Settings) changes:
 *  - Add codes for handling the new project notification table.
 *
 * @author TCSASSEMBLER
 * @version 1.2
 */
if (!window.notifications) var notifications = {
//    pageNumber: 0,

    /**
     * Performs ajax call to update notification settings in back-end.
     */
    update: function() {
        modalPreloader();
        $.ajax({
            type: 'POST',
            url:'updateNotifications',
            data: $('#dashboard-notifications-form').serialize(),
            dataType: "json",
            cache:false,
            success:function(r) {
                modalClose();
                showSuccessfulMessage("Your notification settings have been updated.");
            }
        });
    },

    /**
     * Processes click on 'select all' checkbox.
     */
    selectAll: function(obj, nType) {
        $('#dashboard-notifications-form .select_' + nType).attr('checked', obj.checked);
    },

    /**
     * Processes click on 'select project' checkbox.
     */
    selectProject: function(obj, nType, projectId) {
        $('#dashboard-notifications-form .applyForAll .select_' + nType).attr('checked', false);
        $('#dashboard-notifications-form .project_' + projectId + ' .select_' + nType).attr('checked', obj.checked);
    },

    /**
     * Processes click on 'select contest' checkbox.
     */
    selectContest: function(obj, nType, projectId, groupId) {
        $('#dashboard-notifications-form .applyForAll .select_' + nType).attr('checked', false);
        if (groupId != undefined && groupId > 0) {
            $('#dashboard-notifications-form .group_' + groupId).attr('checked', obj.checked);
        }
    },

    /**
     * Refreshes result table.
     */
    refreshTable: function() {
        notifications.showTablePage(0);
    },

    /**
     * Shows given page of result table.
     *
     * @param pageNumber page number to show
     */
    showTablePage: function(pageNumber) {
        var pageSize = $('#pageSize').val();
        var projects = $('#dashboard-notifications-form .select_project');
        var pages = $('#dashboard-notifications-form .pagination .pages');
        pages.html('');
        $('#dashboard-notifications-form .select_project div.collapse').click();

        // show only necessary projects
        projects.each(function(index) {
            if (index >= pageNumber * pageSize && index < (pageNumber + 1) * pageSize) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
        
        if (projects.length == 0 || pageSize == 1000000) {
            return;
        }

        // update page links
        if (pageNumber > 0) {
            pages.append('<a href="javascript:notifications.showTablePage(' + (pageNumber - 1) + ');" class="prev">Prev</a>');
        } else {
            pages.append('<a href="javascript:;" class="prev">Prev</a>');
        }
        for (var i = 0; i * pageSize < projects.length; ++i) {
            if (i == pageNumber) {
                pages.append('<a href="javascript:;" class="current" >' + (i + 1) + '</a>');
            } else {
                pages.append('<a class="pageNumber" href="javascript:notifications.showTablePage(' + i + ');" >' + (i + 1) + '</a>');
            }
        }
        if ((pageNumber + 1) * pageSize < projects.length) {
            pages.append('<a href="javascript:notifications.showTablePage(' + (pageNumber + 1) + ');" class="next">Next</a>');
        } else {
            pages.append('<a href="javascript:;" class="next">Next</a>');
        }
    }
};

function savePreference() {
    var request = {
        userPreferences: []
    };
    
    $('#preDiv input').each(function(index, item) {
        var id = $(item).attr('name').substring('pre_'.length);
        var value = $(item).attr('checked');
        
        request.userPreferences[index] = {
            preferenceId: parseInt(id),
            value: value
        };
    });

    modalPreloader();
    
    $.ajax({
        type: 'POST',
        url:'preference/updatePreferences',
        data: request,
        dataType: "json",
        cache:false,
        success:function(r) {
            modalClose();
            showSuccessfulMessage("Your notification preferences have been saved.");
        }
    });
    
}

function syncUser() {
    var handle = $("#handle").val();
    if (handle.length == 0) {
        showErrors("Please input handle");
        return;
    }
    
    $.ajax({
        type: 'POST',
        url:'syncUser?handle=' + handle,
        dataType: "json",
        cache:false,
        beforeSend:modalPreloader,
        complete:modalClose,
        success:function(jsonResult) {
            modalClose();
            if (!jsonResult['result']) {
                showServerError(jsonResult.error.errorMessage);
            } else {
                var result = jsonResult.result['return'];
                var html = "Synchronize JIRA " + (result.syncJIRA ? "successful" : "failed") + "<br/>";
                html += "Synchronize WIKI " + (result.syncWIKI ? "successful" : "failed");
                showSuccessfulMessage(html);
            }
        }
    });
}

/**
 * Changes the project forum pagination option.
 *
 * @since 1.2
 */
var projectForumPageChange = function() {
    showProjectForumPage(0);
};

/**
 * Shows given page of result table.
 *
 * @param pageNumber page number to show
 *
 * @since 1.2
 */
var showProjectForumPage = function(pageNumber) {
    var pageSize = $('#projectForumPageSize').val();
    var projects = $('#projectForumNotifications .select_project');
    var pages = $('#projectForumNotifications .pagination .pages');
    pages.html('');

    // show only necessary projects
    projects.each(function(index) {
        if (index >= pageNumber * pageSize && index < (pageNumber + 1) * pageSize) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    if (projects.length == 0 || pageSize == 1000000) {
        return;
    }

    // update page links
    if (pageNumber > 0) {
        pages.append('<a href="javascript:showProjectForumPage(' + (pageNumber - 1) + ');" class="prev">Prev</a>');
    } else {
        pages.append('<a href="javascript:;" class="prev">Prev</a>');
    }
    for (var i = 0; i * pageSize < projects.length; ++i) {
        if (i == pageNumber) {
            pages.append('<a href="javascript:;" class="current" >' + (i + 1) + '</a>');
        } else {
            pages.append('<a class="pageNumber" href="javascript:showProjectForumPage(' + i + ');" >' + (i + 1) + '</a>');
        }
    }
    if ((pageNumber + 1) * pageSize < projects.length) {
        pages.append('<a href="javascript:showProjectForumPage(' + (pageNumber + 1) + ');" class="next">Next</a>');
    } else {
        pages.append('<a href="javascript:;" class="next">Next</a>');
    }
}

/**
 * The cache for project forum notification settings.
 *
 * @since 1.2
 */
var projectForumNotifications;


/**
 * Loads the project forum notifications from the page into cache.
 *
 * @since 1.2
 */
var loadProjectForumNotifications = function() {
    projectForumNotifications = {};
    $("#projectForumNotifications .select_project").each(function(){
        var projectId = $(this).find("[name='projectForumProjectId']").val();
        var forumId = $(this).find("[name='projectForumCategoryId']").val();
        var setting = $(this).find(".select_forum").is(":checked");

        projectForumNotifications[projectId] = {forumId:forumId, notification:setting};
    });
}

/**
 * Saves the project forum notifications settings via ajax.
 *
 * @since 1.2
 */
var saveProjectForumNotifications = function() {
    var request = [];
    $("#projectForumNotifications .select_project").each(function(){
        var projectId = $(this).find("[name='projectForumProjectId']").val();
        var forumId = $(this).find("[name='projectForumCategoryId']").val();
        var newSetting = $(this).find(".select_forum").is(":checked");
        var oldSetting = projectForumNotifications[projectId].notification;

        if(newSetting != oldSetting) {
            var toAdd = {projectId:projectId, forumId:forumId, forumNotification:newSetting};
            request.push(toAdd);
        }
    });

    modalPreloader();

    $.ajax({
        type: 'POST',
        url:'updateDirectProjectNotifications',
        data: {directProjectNotifications:request},
        dataType: "json",
        cache:false,
        success:function(jsonResult) {

            handleJsonResult2(jsonResult,
                function(result) {
                    modalAllClose();

                    if (result.response == 'updated') {
                        showSuccessfulMessage("Project Forum notifications have been successfully saved");
                        // reload the cache
                        loadProjectForumNotifications();
                    } else {
                        showSuccessfulMessage("No project forum notification setting is changed");
                    }

                },
                function(errorMessage) {
                    modalAllClose();
                    showServerError(errorMessage);
                });
        }
    });
}


$(document).ready(function() {
    $("#projectForumPageSize").trigger('change');

    // initialize the project forum notifications map
    loadProjectForumNotifications();
});