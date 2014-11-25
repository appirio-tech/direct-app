/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
/**
 * The JS script for project level VM service.
 *
 * @author jiajizhou86
 * @version 1.0
 */

var currentVMActionDropDownList = undefined;

function setupVMActionDropDownList() {
    // action drop down list
    $(".actionDropDownList").hover(function() {
        var list = $(this).find(".actionList");
        if (list.is(':visible')) {
            // nothing
        } else {
            setTimeout(function () {
                if (currentVMActionDropDownList) {
                    currentVMActionDropDownList.hide();
                }
                currentVMActionDropDownList = list;
                list.show();
            }, 100);
        }
    }, function() {
        var list = $(this).find(".actionList");
        if (list.is(':visible')) {
            list.hide();
            currentVMActionDropDownList = undefined;
        }
    });
}

var projectVMTable;

$(document).ready(function() {
    setupVMActionDropDownList();

    var sLongStdMenu =
        '<strong>Show: </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="10">10</option>' +
            '<option value="20">20</option>' +
            '<option value="50">50</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    var theTable = $("#contest_vms");
    projectVMTable = theTable.dataTable({
        "iDisplayLength": 10,
        "bFilter": true,
        "bStateSave": false,
        "bSort": true,
        "bAutoWidth": false,
        "bInfo": false,
        "oLanguage": {
            "sLengthMenu": sLongStdMenu + " per page"
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[8,'asc']],
        "aoColumns": [
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            { "sType": "date-direct" },
            null,
            { "sClass": "vm_instance_status" },
            { "sClass": "vm_instance_action" }
        ]
    });

    // customize the pagination part for simplicity.
    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");
    $(".dataTables_length").addClass("showPages");

    if (isErrorLoadingVM) {
        showErrors(vmErrorMessage);
    }
});

function updateVMSummary(active, terminated, total) {
    $("#vmCount").text("Total " + total + " (active " + active + ", terminated " + terminated + ")");
}

function addData(vm) {
    var action = '';
    if (vm.status == 'RUNNING' && (hasFullOrWriteAccess || userHandler == vm.managerHandle)) {
        action = '<a href="javascript:void(0)" class="enabled" onclick="javascript:projectVMService.terminate(' + vm.instance.id + ', this);">Terminate</a>';
    } else {
        action = '<a href="javascript:void(0)" class="disabled">Terminate</a>';
    }

    projectVMTable.fnAddData([
            vm.instance.contestId + '',
            vm.contestName,
            vm.vmImageTcName,
            vm.accountName,
            vm.instance.svnBranch,
            vm.instance.tcMemberHandle,
            vm.managerHandle,
            vm.instance.publicIP,
            vm.vmCreationTime,
            vm.usage,
            vm.status,
            '<div class="actionDropDownList" style="position: relative;">' +
                '<a href="javascript:void(0)" class="actionLink">Action<span class="arrow"></span></a>' +
                '<div class="actionList">' +
                '<ul><li>' + action + '</ul></li>' +
                '</div>' +
            '</div>'
        ]);

}

if (!window.projectVMService) var projectVMService = {
    terminate : function(instanceId, elem) {
        if (window.confirm("Are you sure you want to terminate this VM?")) {
            $('#deleteSuccess').hide();

            // disable button
            $(elem).removeClass("enabled");
            $(elem).addClass("disabled");
            $(elem).removeAttr("onclick");

            modalPreloader();
            while (elem.parentNode && elem.parentNode.tagName != "TR"){
                elem = elem.parentNode;
            }
            var rowElem = elem.parentNode;
            $.ajax({
                type: 'POST',
                url: 'terminateVMInstance',
                timeout:20000, // Timeout of 20 secs
                data: setupTokenRequest({'instanceId' : instanceId}, getStruts2TokenName()),
                dataType: "json",
                cache: false,
                success: function(r) {
                    modalClose();
                    $(rowElem).find('.vm_instance_status').html(r.result['return'][0]);
                    $('#deleteSuccess').html("VMInstance deleted successfully");
                    $('#deleteSuccess').show();
                }
            });
        }
    },
    refresh : function(projectId) {
        $('#deleteSuccess').hide();
        modalPreloader();

        $.ajax({
            type: 'POST',
            url:'getProjectVMInstances',
            timeout:20000, // Timeout of 20 secs
            dataType: "json",
            data: {'projectId' : projectId},
            cache:false,
            success:function(r) {
                modalClose();
                r = r.result['return'];
                var errors = r.errors;
                if (errors) {
                    $('#generalError').html("failed to load vm data");
                } else {
                    projectVMTable.fnClearTable();
                    var activeVMs = 0;
                    var terminatedVMs = 0;
                    var totalVMs = 0;
                    for (var i=0; i<r.length; i++) {
                        addData(r[i]);
                        if (r[i].status == 'TERMINATED') {
                            terminatedVMs++;
                        } else if (r[i].status == 'RUNNING') {
                            activeVMs++;
                        }
                        totalVMs++;
                    }
                    setupVMActionDropDownList();
                    setupFilterPanel();
                    setupVMActionDropDownList();
                    updateVMSummary(activeVMs, terminatedVMs, totalVMs);
                }
            }
        });
    }
};
