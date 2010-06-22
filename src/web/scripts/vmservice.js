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
                    $('.vm_instances_body').prepend(vmService.vmToHtml(r));
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
        var action = '';
        if (vm.status == 'RUNNING') {
            action = '<button type="button" value="Terminate" onclick="vmService.terminate(' + vm.instance.id + ');">Terminate</button>';
        }
        html = html.replace(/#action#/g, action);
        return html;
    }
}