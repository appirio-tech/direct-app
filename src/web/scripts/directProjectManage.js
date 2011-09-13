/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * Manages the cockpit project status, used by the show all projects page.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Status Management)
 */


/**
 * Updates the direct project status by calling the backend AJAX.
 *
 * @param directProjectId the direct project id to update
 * @param statusId the project status id
 */
function updateDirectProjectStatus(directProjectId, statusId) {
    modalPreloader();
    $.ajax({
        type: 'POST',
        url:  "updateDirectProjectStatus",
        data: {'directProjectId':directProjectId,
            'directProjectStatusId':statusId},
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            modalClose();
            handleJsonResult(jsonResult,
                function(result) {
                    if (result.updatedStatusId) {

                        // update the project status
                        $("#projectStatus" + result.directProjectId).attr("class", result.updatedStatusName.toLowerCase());
                        $("#projectStatus" + result.directProjectId).html(result.updatedStatusName);

                        // update the project buttons
                        if (result.updatedStatusId == 2) {
                            // archived
                            $("#reactivateProjectButton" + result.directProjectId).show();
                            $("#archiveProjectButton" + result.directProjectId).hide();
                            $("#deleteProjectButton" + result.directProjectId).show();
                        } else if (result.updatedStatusId == 1) {
                            // activated
                            $("#reactivateProjectButton" + result.directProjectId).hide();
                            $("#archiveProjectButton" + result.directProjectId).show();
                            $("#deleteProjectButton" + result.directProjectId).show();
                        } else if (result.updatedStatusId == 3 || result.updatedStatusId == 4) {
                            // completed or deleted, hide all buttons
                            $("#reactivateProjectButton" + result.directProjectId).hide();
                            $("#archiveProjectButton" + result.directProjectId).hide();
                            $("#deleteProjectButton" + result.directProjectId).hide();
                        }

                    } else if (result.warning) {
                        showErrors(result.warning);
                    } else {
                        showErrors("Unknown response from the server side.");
                    }
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

$(document).ready(function() {

});