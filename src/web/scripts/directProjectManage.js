/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * Manages the cockpit project status, used by the show all projects page.
 *
 * version 1.0 (Release Assembly - TopCoder Cockpit Project Status Management)
 *
 * Version 1.1 (Release Assembly - TC Cockpit All Projects Management Page Update) changes
 * - Update to use the new icons for status change operations.
 * 
 * Version 1.2 (Module Assembly - TC Cockpit Operations Dashboard For PMs) changes
 * - Add support for operations dashboard.
 * 
 * @author TCSASSEMBLER
 * @version 1.2
 */


/**
 * Updates the direct project status by calling the backend AJAX.
 *
 * @param directProjectId the direct project id to update
 * @param statusId the project status id
 */
function updateDirectProjectStatus(directProjectId, statusId) {

    var row = $("#projectRow" + directProjectId);

    modalPreloader();
    $.ajax({
        type: 'POST',
        url:  "updateDirectProjectStatus",
        data: setupTokenRequest({'directProjectId':directProjectId,
            'directProjectStatusId':statusId}, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            modalClose();
            handleJsonResult(jsonResult,
                function(result) {
                    if (result.updatedStatusId) {

                        var statusClassName = result.updatedStatusName.toLowerCase();
                        if (statusClassName == 'on hold') {
                            statusClassName = 'archived';
                        }

                        // update the project status
                        $("#projectStatus" + result.directProjectId).attr("class", statusClassName);
                        $("#projectStatus" + result.directProjectId).html(result.updatedStatusName);

                        // update the project buttons
                        if (result.updatedStatusId == 2) {
                            // archived
                            row.find("a.operation").show();
                            row.find("a.archiveOperation").hide();
                        } else if (result.updatedStatusId == 1) {
                            // activated
                            row.find("a.operation").show();
                            row.find("a.activateOperation").hide();
                        } else if (result.updatedStatusId == 3 || result.updatedStatusId == 4) {
                            // completed or deleted, hide all buttons
                            row.find("a.operation, .secondRowSeparator").hide();
                        }

                        if ($("#projectsResult").length > 0 || $("#pmProjectsResult").length ) {
                            // get status td row and column number
                            var statusTD = $("#projectStatus" + result.directProjectId).parent();
                            var index = $.allProjectTable.fnGetPosition(statusTD.get(0));

                            $.allProjectTable.fnUpdate(statusTD.html(), index[0], index[1]);

                            var projectStatusFilter = tableHandle.fnGetData();
                            var len = projectStatusFilter.length;
                            var currentValue = $('#projectStatusFilter').val();
                            var statusMap = {};
                            for (var i = 0; i < len; i++) {
                                var index1 = projectStatusFilter[i][8].indexOf('>');
                                var index2 = projectStatusFilter[i][8].indexOf('<', 1);
                                var value = projectStatusFilter[i][8].substring(index1 + 1, index2);

                                statusMap[value] = true;
                            }
                            $('#projectStatusFilter option:gt(0)').remove();
                            $.each(statusMap, function (key, value) {
                                $('#projectStatusFilter').append("<option value=" + key + ">" + key + "</option>");
                            });

                            if(currentValue == null || currentValue == '' || (typeof currentValue === undefined)) {
                                currentValue = "All";
                            }

                            $("#projectStatusFilter").val(currentValue);
                            $("#projectStatusFilter").trigger('change');
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

$(document).ready(function(){
    $("#projectStatusFilter").val("Active").trigger('change');
})
