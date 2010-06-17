if (!window.notifications) var notifications = {
//    pageNumber: 0,

    /**
     * Performs ajax call to update notification settings in back-end.
     */
    update: function() {
        $('#loading').show();
        $.ajax({
            type: 'POST',
            url:'updateNotifications',
            data: $('#dashboard-notifications-form').serialize(),
            dataType: "json",
            cache:false,
            success:function(r) {
                $('#loading').hide();
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
        var projects = $('.select_project');
        var pages = $('.pagination .pages');
        pages.html('');
        $('.select_project div.collapse').click();

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
            pages.append('<a href="javascript:;"><span class="prev">Prev</span></a>');
        }
        for (var i = 0; i * pageSize < projects.length; ++i) {
            if (i == pageNumber) {
                pages.append('<a href="javascript:;" class="current" >' + (i + 1) + '</a>');
            } else {
                pages.append('<a href="javascript:notifications.showTablePage(' + i + ');" >' + (i + 1) + '</a>');
            }
        }
        if ((pageNumber + 1) * pageSize < projects.length) {
            pages.append('<a href="javascript:notifications.showTablePage(' + (pageNumber + 1) + ');" class="next">Next</a>');
        } else {
            pages.append('<a href="javascript:;"><span class="next">Next</span></a>');
        }
    }
};

/*
$(document).ready(function() {
    $('#loading').hide();
});

*/
