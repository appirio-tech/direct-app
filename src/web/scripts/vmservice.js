var vmTable;
var columnCount;

$(document).ready(function() {
    columnCount = $("#contest_vms").find('tr')[0].cells.length;

    if (columnCount == 11) {
        vmTable = $("#contest_vms").dataTable({
            "bPaginate": false,
            "bFilter": false,
            "bSort": true,
            "bAutoWidth": false,
            "bInfo": false,
            "aaSorting": [[4,'asc']],
            "aoColumns": [
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
    } else {
        vmTable = $("#contest_vms").dataTable({
            "bPaginate": false,
            "bFilter": false,
            "bSort": true,
            "bAutoWidth": false,
            "bInfo": false,
            "aaSorting": [[4,'asc']],
            "aoColumns": [
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
    }
});

function addData(vm) {
    var action = '';
    if (vm.status == 'RUNNING') {
       	action = '<a href="javascript:void(0)" onclick="javascript:vmService.terminate(' + vm.instance.id + ', this);" class="button6" style="margin:auto;"><span class="left"><span class="right">Terminate</span></span></a>';
    }

    if (columnCount == 11) {
        vmTable.fnAddData([
            vm.instance.contestId,
            vm.contestName,
            vm.vmImageTcName,
            vm.instance.svnBranch,
            vm.instance.tcMemberHandle,
            vm.managerHandle,
            vm.instance.publicIP,
            vm.vmCreationTime,
            vm.usage,
            vm.status,
            action
        ]);
    } else {
        vmTable.fnAddData([
            vm.instance.contestId,
            vm.contestName,
            vm.vmImageTcName,
            vm.instance.svnBranch,
            vm.instance.tcMemberHandle,
            vm.instance.publicIP,
            vm.vmCreationTime,
            vm.usage,
            vm.status,
            action
        ]);
    }
}

if (!window.vmService) var vmService = {
    launch : function(formId) {
        var data = $('#' + formId).serialize();
        $(".error").empty();
        var actionArea = $('#launch_vm_button');
        var save = actionArea.html();
        actionArea.html('');
        $('#loading').show();

        $.ajax({
            type: 'GET',
            url:'launchVMInstance',
            data: data,
            dataType: "json",
            cache:false,
            success:function(r) {
                $('#loading').hide();
                actionArea.html(save);
                r = r.result['return'];
                var errors = r.errors;
                if (errors) {
                    for (var p=0; p<errors.length; p++) {
                        $('#' + errors[p].propertyName + 'Error').html(errors[p].messages[0]);
                    }
                } else {
					var container = $('.vm_instances_body');
					for (var i=0; i<r.length; i++) { // is able to launch multiple VMs at once
                        addData(r[i]);
                    }


                }
            }
        });
    },

    refresh : function() {
        $(".error").empty();
        $('#loading').show();

        $.ajax({
            type: 'GET',
            url:'showVMInstances',
            dataType: "json",
            cache:false,
            success:function(r) {
                $('#loading').hide();
                r = r.result['return'];
                var errors = r.errors;
                if (errors) {
                    $('#generalError').html("failed to load vm data");
                } else {
                    vmTable.fnClearTable();
                    for (var i=0; i<r.length; i++) {
                        addData(r[i]);
                    }
                }
            }
        });
    },


    terminate : function(instanceId, elem) {                         
        if (window.confirm("Are you sure you want to terminate this VM?")) {                                    
            $('#loading').show();
            while (elem.parentNode && elem.parentNode.tagName != "TR"){
                elem = elem.parentNode;
            }
            var rowElem = elem.parentNode;
            var actionArea = $(rowElem).find(".vm_instance_action");
            actionArea.html('');
            $.ajax({
                type: 'POST',
                url: 'terminateVMInstance',
                data: {'instanceId' : instanceId},
                dataType: "json",
                cache: false,
                success: function(r) {
                    $('#loading').hide(); 
                    $(rowElem).find('.vm_instance_status').html(r.result['return'][0]);                                       
                }
            });
        }        
    }
}
