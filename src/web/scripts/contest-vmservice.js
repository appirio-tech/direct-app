/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
/**
 * The JS script for Contest VM service.
 *
 * Version 1.1
 * - added error message popup box handling
 * - updated the terminate button to an action list
 *
 * @author gentva, jiajizhou86
 * @version 1.1
 */
var contestVMTable;
var currentVMActionDropDownList = undefined;

function setupVMActionDropDownList() {
    // action drop down list
    $(".actionList").hide();
    $(".actionDropDownList").hover(function(){
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

$(document).ready(function() {
    var sLongStdMenu =
        '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="10">10</option>' +
            '<option value="20">20</option>' +
            '<option value="50">50</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    contestVMTable = $("#contestVMs").dataTable({
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
        "aaSorting": [[5,'asc']],
        "aoColumns": [
            null,
            null,
            null,
            null,
            null,
            { "sType": "date-direct" },
            null,
            { "sClass": "vm_instance_status" },
            null,
            { "sClass": "vm_instance_action" }
        ]
    });

    // customize the pagination part for simplicity.
    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");
    $(".dataTables_length").addClass("showPages");

    setupVMActionDropDownList();

    if (isErrorLoadingVM) {
        showErrors(vmErrorMessage);
    }
});

if (!window.contestVMService) var contestVMService = {
    terminate : function(instanceId, contestId, isStudio, elem) {
        if (window.confirm("Are you sure you want to terminate this VM?")) {
            $('#launchSuccess').hide();
            $('#deleteSuccess').hide();
            modalPreloader();
            while (elem.parentNode && elem.parentNode.tagName != "TR"){
                elem = elem.parentNode;
            }
            var rowElem = elem.parentNode;
            var actionArea = $(rowElem).find(".vm_instance_action");
            actionArea.html('');
            $.ajax({
                type: 'POST',
                url: 'terminateVMInstance',
                timeout:20000, // Timeout of 20 secs
                data: {'instanceId' : instanceId, 'contestId' : contestId, 'studio': isStudio},
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
    }
};
