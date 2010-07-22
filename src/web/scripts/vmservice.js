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
                    for (p in errors) {
                        $('#' + errors[p].propertyName + 'Error').html(errors[p].messages[0]);
                    }
                } else {
					          var container = $('.vm_instances_body');
					          for (var i in r) { // is able to launch multiple VMs at once
                        container.prepend(vmService.vmToHtml(r[i]));
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
                    var container = $('.vm_instances_body');
                    container.html('');
                    for (var i in r) {
                        container.append(vmService.vmToHtml(r[i]));
                    }
                }
            }
        });
    },


    terminate : function(instanceId) {
        $('#loading').show();
        var actionArea = $('#vm_instance_' + instanceId + ' .vm_instance_action');
        actionArea.html('');
        $.ajax({
            type: 'POST',
            url: 'terminateVMInstance',
            data: {'instanceId' : instanceId},
            dataType: "json",
            cache: false,
            success: function(r) {
                $('#loading').hide();
                $('#vm_instance_' + instanceId + ' .vm_instance_status').html(r.result['return'][0]);
            }
        });
    },

    vmToHtml : function(vm) {
        var template = $('#vm_instance_template').html();
        var html = template.replace(/#instance.id#/g, vm.instance.id);
        html = html.replace(/#instance.contestId#/g, vm.instance.contestId);
        html = html.replace(/#instance.svnBranch#/g, vm.instance.svnBranch);
        html = html.replace(/#instance.tcMemberHandle#/g, vm.instance.tcMemberHandle);
        html = html.replace(/#managerHandle#/g, vm.managerHandle);
        html = html.replace(/#status#/g, vm.status);
        html = html.replace(/#contestName#/g, vm.contestName);
        html = html.replace(/#vmImageTcName#/g, vm.vmImageTcName);
        html = html.replace(/#instance.publicIP#/g, vm.instance.publicIP);
        var action = '';
        if (vm.status == 'RUNNING') {
           	action = '<a href="javascript:vmService.terminate(' + vm.instance.id + ');" class="button6" style="margin:auto;"><span class="left"><span class="right">Terminate</span></span></a>';
        }
        html = html.replace(/#action#/g, action);
        return html;
    }
}
