/**
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for edit project page..
 *
 *  Version 1.0 - Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0
 *
 *  Version 1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info) changes:
 *  - Add javascripts codes to edit and save project ratings.
 *
 *  Version 2.0 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 *  - Add javascripts to edit project type & category, project permissions, project notifications and contest notifications.
 *
 * @author GreatKevin
 * @version 2.0
 */
Date.format = 'mm/dd/yyyy';

var projectPermissionMap = {0:'report', 1:'read', 2:'write', 3:'full'};

var setupEditProjectToolTip = function() {

    $(".tctips").remove();


    $('#editProjectName a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Name",
            content:'Set the name of your project, max 60 characters'
        });
    });

    $('#editProjectDescription a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Description",
            content:'Set the description of your project, max 2000 characters'
        });
    });

    $('.projectTypeArea a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Type & Category",
            content:'Set the type and category of the project. After set, it will be displayed on the project overview page'
        });
    });

    $('#editProjectInfo a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Information",
            content:'Edit the general project information like budget, planned duration etc'
        });
    });

    $('#editCustomProjectMetadata a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Custom Project Metadata",
            content:'Edit or Add custom information for your project. Custom metadata can be used to store additional information about your project'
        });
    });

    $('.metaAreaRight a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Ratings",
            content:'Rating your project with 5 different ratings'
        });
    });

    $('#editProjectStatus a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Status",
            content:'Set the project status'
        });
    });

    $('#editProjectPrivacy a.toolTipIcon').each(function () {
        $(this).tctip({
            title:"Project Privacy",
            content:'Set whether you want your project name viewable to TopCoder community'
        });
    });

}


$(document).ready(function (e) {

    if ($('.editPage').length > 0) {

        $(".ratingEdit span").live("mouseenter", function () {
            var wrapper = $(this).parent();
            var index = wrapper.find("span").index($(this));
            wrapper.find("span").removeClass("active");
            wrapper.find("span:lt(" + (index + 1) + ")").addClass("active");
        })
        $(".ratingEdit span").live("mouseleave", function () {
            var wrapper = $(this).parent();
            var index = wrapper.find("span").index($(this));
            var rating = wrapper.data("rating");
            wrapper.find("span").removeClass("active");
            wrapper.find("span:lt(" + rating + ")").addClass("active");
        })
        $(".ratingEdit span").live("click", function () {
            var wrapper = $(this).parent();
            var index = wrapper.find("span").index($(this));
            wrapper.find("span:lt(" + index + ")").addClass("active");
            wrapper.data("rating", index + 1);
            return false;
        });

        $(".ratingEdit").each(function () {
            var _this = $(this);
            for (var i = 0; i < 5; i++) {
                var span = $("<span/>").appendTo(_this);
            }

            // check the value
            var item = $(this).parent();
            var value = item.find("input[name='rating']").val();

            if(value <= 0) {
                _this.data("rating", 0);
            } else {
                $(this).find("span:eq(" + (value - 1) + ")").trigger('mouseenter').trigger('click');
            }

        });

        // project type change
        $(".projectTypeArea #projectType").change(function () {
            var projectTypeId = $(this).val();
            var category = $(".projectTypeArea #projectCategory");
            category.find("option").remove();
            if (projectTypeId == -1) {
                $("<option/>").attr('value', '-1').text('None').appendTo(category);
            } else {
                $.ajax({
                    type:'post',
                    url:'getProjectCategories',
                    data:{projectTypeId:projectTypeId},
                    cache:false,
                    dataType:'json',
                    success:function (jsonResult) {
                        handleJsonResult2(
                            jsonResult,
                            function (result) {
                                $.each(result, function(key, value){
                                    $("<option/>").attr('value', key).text(value).appendTo(category)
                                });
                                category.val(-1);
                            },
                            function (errorMessage) {
                                showServerError(errorMessage);
                            });
                    }
                });
            }

        });

        $('.permissionsNotifications tbody tr:odd').addClass('odd');
        $('#settingModal tbody tr:even').addClass('odd');
        $('.projectTypeArea .comboContainer select').css('width',($('.projectTypeArea .comboContainer').width() - 150)/2);

        if($('#settingModal .settingTable .tableBody tr').length > 9){
            if($.browser.mozilla){
                $('#settingModal .settingTable .tableBody').css('height',$('#settingModal .settingTable .tableBody td').height()*8-1);
            }else{
                $('#settingModal .settingTable .tableBody').css('height','240px');
            }
            $('#settingModal .settingTable .tableBody').css('overflow-x','hidden');
            $('#settingModal .settingTable .tableBody').css('overflow-y','auto');
        }


        //scroll
        $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','scroll');


        ///////////////// START: Add user permission modal

        var currentUserPermissionModalCache = {};

        $("#addUser").click(function(){
            modalLoad('#' + $(this).attr('name'));
            var modal = $("#addUserModal");
            modal.find(".searchBox input").val('');
            var leftSide = modal.find('.addUserLeft ul');
            leftSide.find('li').remove();
            modal.find('.addUserRight .addUserList ul li').remove();

            // pre-populate the left side of the modal
            $(".permissionsNotifications td.permissionUser").each(function () {
                var handle = $(this).find("a").text();
                var userId = $(this).find("input").val();
                var entry = {};
                entry.name = handle;
                entry.id = userId;
                currentUserPermissionModalCache[userId] = entry;

                modal.find('.addUserRight .addUserList ul').append($('<li name="' + userId + '">' + handle + '</li>'));
                var lis = $('li:contains(' + handle + ')', leftSide);
                lis.remove();
            });

        });

        $("#addUserModal .saveButton").click(function () {
            for (var i = 0; i < $('#addUserModal .addUserRight li').length; i++) {
                var handle = $('#addUserModal .addUserRight li').eq(i).text();
                var userId = $('#addUserModal .addUserRight li').eq(i).attr('name');

                if(currentUserPermissionModalCache[userId] == null) {
                    $('.permissionsNotifications table tbody').append('<tr><td class="permissionUser"><a href="javascript:;" class="useName">' + handle
                        + '</a><input type="hidden" value="' + userId + '" /></td><td><ul><li><input type="radio" name="radio' + userId
                        + '"  checked="checked" /></li><li><input type="radio" name="radio' + userId
                        + '" /></li><li><input type="radio" name="radio' + userId
                        + '" /></li><li><input type="radio" name="radio' + userId
                        + '" /></li></ul></td><td class="alignCenter"><input type="checkbox" /></td><td class="alignCenter"><a name="settingModal" class="setting triggerModal" href="javascript:;">Setting</a></td><td class="alignCenter"><a name="preloaderModal" class="triggerModal remove"  href="javascript:;">Remove</a></td></tr>');
                }

            }

            $('.permissionsNotifications tbody tr').removeClass('odd');
            $('.permissionsNotifications tbody tr:odd').addClass('odd');
            modalAllClose();
        });


       ///////////////// END: Add user permission modal


        // Save project permissions & notifications
        $("#savePermissionNotification").click(function () {
            var formData = {};
            formData.projectPermissions = [];
            formData.projectNotifications = [];
            formData.projectId = $("input[name='editProjectId']").val();

            $(".permissionsNotifications tbody tr").each(function () {
                var handle = $(this).find(".useName").text();
                var userId = $(this).find(".permissionUser input").val();

                var permissionTypeId = $(this).find("input[type=radio]").index($(this).find("input[type=radio]:checked"));

                var notificationChecked = $(this).find("td:eq(2) input").is(":checked");

                var permission = {};
                permission.userId = userId;
                permission.handle = handle;
                permission.studio = false;
                permission.permission = projectPermissionMap[permissionTypeId];
                permission.projectId = formData.projectId;

                formData.projectPermissions.push(permission);

                var notification = {};
                notification.projectId = formData.projectId;
                notification.forumNotification = notificationChecked;
                notification.userId = userId;
                formData.projectNotifications.push(notification);

            });

            modalPreloader();

            $.ajax({
                type:'post',
                url:'saveProjectPermissionsAndNotifications',
                data:{formData:formData},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            showSuccessfulMessage("Project Permissions and Notifications are successfully updated.")
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });

        });

        // remove user permission from project
        $(".permissionsNotifications a.remove").live('click', function(){
            var row = $(this).parents("tr");
            var formData = {};
            formData.projectId = $("input[name='editProjectId']").val();
            formData.projectPermissions = [];
            var handle = row.find(".useName").text();
            var userId = row.find(".permissionUser input").val();
            var permission = {};
            permission.userId = userId;
            permission.handle = handle;
            permission.studio = false;
            permission.permission = '';
            permission.projectId = formData.projectId;

            formData.projectPermissions.push(permission);

            modalPreloader();

            $.ajax({
                type:'post',
                url:'saveProjectPermissionsAndNotifications',
                data:{formData:formData},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            row.remove();
                            $('.permissionsNotifications tbody tr').removeClass('odd');
                            $('.permissionsNotifications tbody tr:odd').addClass('odd');
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });

        });

        //////////////////// Start contest notification setting modal ///////////////

        $(".permissionsNotifications .setting").live('click', function () {

            modalPreloader();

            var formData = {};
            formData.projectId = $("input[name='editProjectId']").val();
            var userId = $(this).parents('tr').find("td.permissionUser input").val();
            var handle = $(this).parents('tr').find("td.permissionUser a").text();
            $.ajax({
                type:'post',
                url:'getProjectContestsNotificationsForUser',
                data:{formData:formData, userId:userId},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            var table = $("#settingModal .settingTable tbody");
                            var checkedStr = ' checked="checked" ';
                            table.find('tr').remove();
                            $.each(result, function(index, value){

                                var row = '<tr><td><a href="contest/detail?projectId=' + value.id + '">' + value.name
                                + '</a><input type="hidden" value="' + value.id +  '" /></td>'
                                + '<td class="alignCenter"><input type="checkbox"' + (value.timelineNotification == 'true' ? checkedStr : '') + '/></td>'
                                + '<td class="alignCenter"><input type="checkbox"' + (value.forumNotification == 'true' ? checkedStr : '') +  '/></td></tr>';

                                table.append(row);
                            });

                            $("#settingModal").find(".userTitle").text(handle);
                            setupTotalCheckBox();
                            modalLoad("#settingModal");

                            $("#settingModal").data('currentUserId', userId);

                            if ($('#settingModal .settingTable .tableBody tr').length > 9) {
                                if ($.browser.mozilla) {
                                    $('#settingModal .settingTable .tableBody').css('height', $('#settingModal .settingTable .tableBody td').height() * 8 - 1);
                                } else {
                                    $('#settingModal .settingTable .tableBody').css('height', '240px');
                                }
                                $('#settingModal .settingTable .tableBody').css('overflow-x', 'hidden');
                                $('#settingModal .settingTable .tableBody').css('overflow-y', 'auto');
                            }
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });


        });

        // modal cancel button
        $("#settingModal .cancelBtn").click(function () {
            modalAllClose();
        });

        // modal save button
        $("#settingModal .saveSetting").click(function(){
            modalAllClose();
            var formData = {};
            formData.projectId = $("input[name='editProjectId']").val();
            formData.userId = $("#settingModal").data('currentUserId');
            formData.contestsTimeline = [];
            formData.contestsNotification = [];

            $(this).parents(".modalBody").find(".tableBody tbody tr").each(function(){
                var contestId = $(this).find("td:eq(0) input").val();
                var timelineChecked = $(this).find("td:eq(1) input").is(":checked");
                var forumChecked = $(this).find("td:eq(2) input").is(":checked");

                if(timelineChecked) {
                    formData.contestsTimeline.push(contestId);
                }

                if(forumChecked) {
                    formData.contestsNotification.push(contestId);
                }

            });

            modalPreloader();

            $.ajax({
                type:'post',
                url:'saveContestsNotificationsForUser',
                data:{formData:formData},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            showSuccessfulMessage("The contests notifications setting have been saved.");
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });

        });

        $("#settingModal table tbody tr input[type=checkbox]").live('change', function(){
            setupTotalCheckBox();
        });

        var setupTotalCheckBox = function() {
            var timelineAllChecked = true;
            var forumAllChecked = true;
            $('#settingModal table tbody tr').each(function () {
                timelineAllChecked = timelineAllChecked && $(this).find('td:eq(1)').find('input').is(":checked");
                forumAllChecked = forumAllChecked && $(this).find('td:eq(2)').find('input').is(":checked");
            });

            $('#settingModal table th:eq(1) input').attr('checked', timelineAllChecked);

            $('#settingModal table th:eq(2) input').attr('checked', forumAllChecked);
        };


        $('#settingModal table th:eq(1) input').live('click',function(){
            if($(this).attr('checked')){
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(1)').find('input').attr('checked',true);
                });
            }else{
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(1)').find('input').attr('checked',false);
                });
            }
        });

        $('#settingModal table th:eq(2) input').live('click',function(){
            if($(this).attr('checked')){
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(2)').find('input').attr('checked',true);
                });
            }else{
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(2)').find('input').attr('checked',false);
                });
            }
        });

        $('#settingModal table th:eq(1) label').live('click',function(){
            if($(this).parents('th').find('input').attr('checked')){
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(1)').find('input').attr('checked',false);
                });
                $(this).parents('th').find('input').attr('checked',false);
            }else{
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(1)').find('input').attr('checked',true);
                });
                $(this).parents('th').find('input').attr('checked',true);
            }
        });

        $('#settingModal table th:eq(2) label').live('click',function(){
            if($(this).parents('th').find('input').attr('checked')){
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(2)').find('input').attr('checked',false);
                });
                $(this).parents('th').find('input').attr('checked',false);
            }else{
                $('#settingModal table tr').each(function(){
                    $(this).find('td:eq(2)').find('input').attr('checked',true);
                });
                $(this).parents('th').find('input').attr('checked',true);
            }
        });

        //////////////////// End contest notification setting modal ////////////////


        $('.addCustomMetaBtnContainer .triggerModal').click(function () {
            modalLoad('#' + $(this).attr('name'));

            $('#addCustomeMeta input:text').val('');
            $('#addCustomeMeta textarea').val('');
            $('#addCustomeMeta input[name="isGrouping"]:eq(1)').trigger('click');
            $('#addCustomeMeta input[name="allowedValues"]:eq(0)').trigger('click');
            $(".customKeyError").hide();
            $('#addCustomeMeta .validateLength:eq(1)').focus();
            $('#addCustomeMeta .validateLength:eq(0)').focus();
        });

        $('.editPage .cancelButton').live('click', function () {
            modalAllClose();
            return false;
        });

        function validateInput(input, length, label) {
            var value = input.val();
            if (value.length > length) {
                value = value.substring(0, length);
                input.val(value);
            }
            var left = length - value.length;
            label.text(left + ' characters remaining');
        }

        window.setTextInput = function () {

            $('.validateLength:focus').each(function (index, elem) {
                var id = $(this).attr('id');
                limit = parseInt(id.substr(3), 10);
                var hint = $(this).next('.hintTxt').find('span').eq(1);
                validateInput($(this), limit, hint);
            });

            $('.inputWrapper input:focus').each(function () {
                var parent = $(this).parent();
                var clearLink = $('a', parent);
                if ($(this).val().length > 0) {
                    clearLink.removeClass('clearValueDisabled');
                } else {
                    clearLink.addClass('clearValueDisabled');
                }
            });
        }

        window.setInterval('setTextInput()', 100);

        $('.validateLength').each(function () {
            var id = $(this).attr('id');
            limit = parseInt(id.substr(3), 10);
            var hint = $(this).next('.hintTxt').find('span').eq(1);
            validateInput($(this), limit, hint);
        });

        $('.inputWrapper .clearValue').live('click', function () {
            if ($(this).hasClass('clearValueDisabled')) {
                return false;
            }
            var p = $(this).parent();
            var input = $('input', p);
            input.val('');
            $(this).addClass('clearValueDisabled');
            return false;
        });

        $('.inputWrapper').each(function () {
            if ($('input', $(this)).val().length > 0) {
                $('a', $(this)).removeClass('clearValueDisabled');
            } else {
                $('a', $(this)).addClass('clearValueDisabled');
            }
        });

        $('.moreValue').live('click', function () {
            $('<div class="inputContainer inputWrapper"><input type="text" value="" name="customMetadataValue" class="textInput" autocomplete="off"><a href="javascript:;" class="clearValue clearValueDisabled">Clear</a></div>').insertBefore($(this));
            setupEditProjectToolTip();
            return false;
        });

        $('.memberList ul li, .techList ul li').each(function () {
            $(this).append($('<a href="javascript:;" class="close" title="Remove this handle"></a>'));
            $(this).hover(
                function () {
                    $(this).addClass('hovered');
                },
                function () {
                    $(this).removeClass('hovered');
                }
            );
        });

        $('.memberList .close').live('click', function () {
            var p = $(this).parent();
            var metadataId = p.find("span").attr('name');
            var operations = [];
            var operation = {};
            var modalName = p.parent().find(".triggerManagerModal").attr('name');
            var keyId = (modalName == 'clientManagersModal' ? 1 : 2);

            operation.KeyId = keyId;
            operation.id = metadataId;
            operation.operation = 'remove';
            operations.push(operation);

            var formData = {};
            formData.projectId = $("input[name='editProjectId']").val();
            if (modalName == 'clientManagersModal') {
                formData.clientManagers = operations;
            } else {
                formData.projectManagers = operations;
            }

            var requestURL = (modalName == 'clientManagersModal' ? 'saveClientProjectManagers' : 'saveTopCoderManagers');

            modalPreloader();

            $.ajax({
                type:'post',
                url:requestURL,
                data:{formData:formData},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            p.remove();
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });


            p.remove();
            return false;
        });

        var initialBudget = $('#budgetOutput').val();

        if ($.trim(initialBudget).length == 0) {
            initialBudget = 0;
            $('#budgetOutput').val('');
        } else {
            initialBudget = parseInt(initialBudget, 10);
            $('#budgetOutput').val(formatNumber(initialBudget));
        }


        $('#budgetSlider').slider({
            rule:['0K', '20K', '40K', '60K', '80K', '100K', '120K', '140K', '160K', '180K', '200K'],
            defaultValue:initialBudget,
            output:$('#budgetOutput'),
            convert:function (label) {
                label = label.replace('K', '');
                return parseInt(label, 10) * 1000;
            },
            onExceed:function (value) {
                $('#budgetOutput').val(formatNumber(parseInt(value, 10)));
                $('#budgetSlider').enableSlider(false);
            },
            onIn:function () {
                $this = $('#budgetSlider');
                var s = $this.data('options');
                if (!s.enabled) {
                    $this.enableSlider(true);
                    $('#budgetOutput').trigger('change');
                }
            },
            step:1000
        });


        var initialDuration = $('#durationOutput').val();

        if ($.trim(initialDuration).length == 0) {
            initialDuration = 0;
            $('#durationOutput').val('');
        } else {
            initialDuration = parseInt(initialDuration, 10);
        }

        $('#durationSlider').slider({
            rule:['>0', '50', '100', '150', '200', '250', '300', '350', '400'],
            defaultValue:initialDuration,
            output:$('#durationOutput'),
            convert:function (label) {
                label = label.replace('>', '');
                return parseInt(label, 10);
            },
            close:true,
            onExceed:function (value) {
                $('#durationOutput').val(value);
                $('.datePickerView .radio').trigger('click').trigger('change');
            }
        });

        $('.sliderView input.radio').change(function () {
            // Enable itself.
            $('#durationSlider').enableSlider(true);
            $('.datePickerView .date-pick').dpSetDisabled(true);
            $('.datePickerView').addClass('disable');
            $('.sliderView').removeClass('disable');

            $('#durationOutput').trigger('change');
        });


        function changeDate() {
            var startDate = $('#startDate').dpGetSelected();
            var endDate = $('#endDate').dpGetSelected();
            if (startDate.length > 0 && endDate.length > 0) {
                var d1 = new Date(startDate[0]);
                var d2 = new Date(endDate[0]);
                var t1 = d1.getTime();
                var t2 = d2.getTime();
                var duration = Math.floor((t2 - t1) / (3600 * 1000 * 24));
                $('#durationOutput').val(duration);
                if (duration > 400) {
                    $('.durationPanel .sliderView input.radio').attr('disabled', 'disabled');
                } else {

                    $('.durationPanel .sliderView input.radio').removeAttr('disabled');
                }

            }
            if (startDate.length > 0) {
                var s = new Date(startDate[0]);
                s.addDays(1);
                $('#endDate').dpSetStartDate(s.asString());
            } else if (endDate.length > 0) {
                var s = new Date(endDate[0]);
                s.addDays(-1);
                $('#startDate').dpSetEndDate(s.asString());
            } else {
                //1. Set today to the selected date.
                var today = new Date();
                $('#startDate').dpSetSelected(today.asString());
                today.addDays(1);
                $('#endDate').dpSetStartDate(today.asString());

            }
        }

        $('.datePickerView input.radio').change(function () {
            // Enable itself.
            $('#durationSlider').enableSlider(false);
            $('.datePickerView .date-pick').dpSetDisabled(false);
            $('.datePickerView').removeClass('disable');
            $('.sliderView').addClass('disable');

            var startDate = $('#startDate').dpGetSelected();
            var endDate = $('#endDate').dpGetSelected();

            $('#durationOutput').trigger('change');
        });


        /* init date-pack */
        if ($('.datePickerView .date-pick').length > 0) {


            $(".datePickerView .date-pick").datePicker({
                startDate:'01/01/00'
            });

            $('.datePickerView .date-pick').change(changeDate);

            $('#durationOutput').change(function () {
                var v = $(this).val().replace(/[,]/g, '').replace(/[ ]/g, '');
                var reg = /^[0-9]+$/;
                if (!($.trim(v).length == 0) && !reg.test(v)) {
                    showErrors('Please input a positive integer for project duration.');
                    var opt = $(this).data('options');
                    if (opt) {
                        $(this).val(opt.orgValue);
                    }
                    return;
                }
                var num = parseInt($(this).val(), 10);
                if (num < 0 || isNaN(num)) {
                    num = 0;
                }
                if (!$('.datePickerView').hasClass('disable')) {
                    var startDate = $('#startDate').dpGetSelected();
                    var endDate = $('#endDate').dpGetSelected();
                    if (startDate.length > 0) {
                        var date = new Date(startDate[0]);
                        date.addDays(num);
                        $('#endDate').dpSetSelected(date.asString());
                    } else if (endDate.length > 0) {
                        var date = new Date(endDate[0]);
                        date.addDays(-num);
                        $('#startDate').dpSetSelected(date.asString());
                    } else {
                        var s = new Date();
                        $('#startDate').dpSetSelected(s.asString());
                        s.addDays(num);
                        $('#endDate').dpSetSelected(s.asString());
                    }

                    if (num > 400) {
                        $('.durationPanel .sliderView input.radio').attr('disabled', 'disabled');
                    } else {
                        $('.durationPanel .sliderView input.radio').removeAttr('disabled');
                    }
                }
            });
        }

        // scroll
        $('.userManagementModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');

        // list selected
        $('.userManagementModal .addUserForm .addUserList li').live('click', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                $(this).addClass('selected');
            }
        });

        var adjustLeftRightPanel = function (modal) {
            if (modal.find(' .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
                modal.find('.addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
            } else {
                modal.find('.addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
            }
            if (modal.find('.addUserForm .addUserRight .addUserList ul').height() >= 266) {
                modal.find('.addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
            } else {
                modal.find('.addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
            }
        }

        // select all
        $('.userManagementModal .addUserForm .selectAll').click(function () {
            var modal = $(this).parents('.userManagementModal:first');
            modal.find('.addUserForm .addUserLeft ul li').filter(":visible").addClass('selected');
        });

        // remove all
        $('.userManagementModal .addUserForm .removeAll').click(function () {
            var modal = $(this).parents('.userManagementModal:first');
            modal.find('.addUserForm .addUserLeft ul').append(modal.find('.addUserForm .addUserRight ul').html());
            modal.find('.addUserForm .addUserRight ul').empty();
            var p = {};
            modal.find('.addUserForm .addUserLeft ul li').each(function(){
                var name = $(this).attr('name');
                if (p[name] == true) {
                    $(this).remove();
                } else {
                    p[name] = true;
                }
            });
        });

        // add item
        $('.userManagementModal .addUserForm .addItem').live('click', function () {
            var modal = $(this).parents('.userManagementModal:first');
            modal.find('.addUserForm .addUserLeft ul li.selected').each(function () {
                modal.find('.addUserForm .addUserRight ul').append('<li name="' + $(this).attr('name') + '">' + $(this).html() + '</li>');
                $(this).remove();
            });
            var p = {};
            modal.find('.addUserForm .addUserRight ul li').each(function () {
                var name = $(this).attr('name');
                if (p[name] == true) {
                    $(this).remove();
                } else {
                    p[name] = true;
                }
            });
            adjustLeftRightPanel(modal);
        });

        // remove item
        $('.userManagementModal .addUserForm .removeItem').live('click', function () {
            var modal = $(this).parents('.userManagementModal:first');
            modal.find('.addUserForm .addUserRight ul li.selected').each(function () {
                modal.find('.addUserForm .addUserLeft ul').append('<li name="' + $(this).attr('name') + '" id="' + $(this).attr('id') + '">' + $(this).html() + '</li>');
                $(this).remove();
            });
            var p = {};
            modal.find('.addUserForm .addUserLeft ul li').each(function () {
                var name = $(this).attr('name');
                if (p[name] == true) {
                    $(this).remove();
                } else {
                    p[name] = true;
                }
            });
            adjustLeftRightPanel(modal);
        });


        $('a.removeCustomeMeta').each(function () {
            $(this).tctip({
                title:"Delete Custom Key",
                content:"By deleting key, all values in key will be deleted."
            });
        });

        setupEditProjectToolTip();

//        $('.userManagementModal a.downloadProfile').each(function () {
//            $(this).tctip({
//                title:"Search user",
//                content:"Search user with username or use SQL wildcards like '%', '_'"
//            });
//        });


        var validateEditProjectPage = function () {
            var errors = [];

            // check if project name is empty
            if ($.trim($('input[name="projectName"]').val()).length == 0) {
                errors.push("Project name cannot be empty");
            }

            // check if project description is empty
            if ($.trim($('textarea[name="projectDesc"]').val()).length == 0) {
                errors.push("Project description cannot be empty");
            }

            if (errors.length > 0) {
                showErrors(errors);
            }

            return errors.length > 0;
        }

        var buildSaveProjectRequest = function () {
            var formData = {};

            // set project id
            formData.projectId = $("input[name='editProjectId']").val();
            // set project name
            formData.projectName = $('input[name="projectName"]').val();
            // set project description
            formData.projectDescription = $('textarea[name="projectDesc"]').val();
            // set project status id
            formData.projectStatusId = $("input[name='projectStatus']:checked").val();

            formData.projectTypeId = $("#projectType").val();

            formData.projectCategoryId = $("#projectCategory").val();

            // get project budget
            var budgetValue = $.trim($('#budgetOutput').val().replace(/[,]/g, '').replace(/[ ]/g, ''));
            var budgetKey = $.trim($('#budgetOutput').attr('name'));
            if (!(budgetValue.length == 0 && budgetKey == '')) {
                var budgetUpdate = {};
                budgetUpdate.keyId = 3;
                if (budgetValue.length == 0 && budgetKey != '') {
                    // remove
                    budgetUpdate.id = budgetKey;
                    budgetUpdate.operation = 'remove';
                } else if (budgetValue.length != 0 && budgetKey != '') {
                    budgetUpdate.id = budgetKey;
                    budgetUpdate.operation = 'update';
                    budgetUpdate.value = budgetValue;
                } else if (budgetValue.length != 0 && budgetKey == '') {
                    budgetUpdate.operation = 'add';
                    budgetUpdate.value = budgetValue;
                }

                formData.budget = budgetUpdate;
            }

            // get project duration
            var durationValue = $.trim($('#durationOutput').val().replace(/[,]/g, '').replace(/[ ]/g, ''));
            var durationKey = $.trim($('#durationOutput').attr('name'));
            if (!(durationValue.length == 0 && durationKey == '')) {
                var durationUpdate = {};
                durationUpdate.keyId = 6;
                if (durationValue.length == 0 && durationKey != '') {
                    // remove
                    durationUpdate.id = durationKey;
                    durationUpdate.operation = 'remove';
                } else if (durationValue.length != 0 && durationKey != '') {
                    durationUpdate.id = durationKey;
                    durationUpdate.operation = 'update';
                    durationUpdate.value = durationValue;
                } else if (durationValue.length != 0 && durationKey == '') {
                    durationUpdate.operation = 'add';
                    durationUpdate.value = durationValue;
                }

                formData.duration = durationUpdate;
            }

            // get project svn address
            var svnValue = $.trim($('#svnAddress').val());
            var svnKey = $.trim($('#svnAddress').attr('name'));
            if (!(svnValue.length == 0 && svnKey == '')) {
                var svnUpdate = {};
                svnUpdate.keyId = 4;
                if (svnValue.length == 0 && svnKey != '') {
                    // remove
                    svnUpdate.id = svnKey;
                    svnUpdate.operation = 'remove';
                } else if (svnValue.length != 0 && svnKey != '') {
                    svnUpdate.id = svnKey;
                    svnUpdate.operation = 'update';
                    svnUpdate.value = svnValue;
                } else if (svnValue.length != 0 && svnKey == '') {
                    svnUpdate.operation = 'add';
                    svnUpdate.value = svnValue;
                }

                formData.svn = svnUpdate;
            }

            // get project jira address
            var jiraValue = $.trim($('#jiraAddress').val());
            var jiraKey = $.trim($('#jiraAddress').attr('name'));
            if (!(jiraValue.length == 0 && jiraKey == '')) {
                var jiraUpdate = {};
                jiraUpdate.keyId = 5;
                if (jiraValue.length == 0 && jiraKey != '') {
                    // remove
                    jiraUpdate.id = jiraKey;
                    jiraUpdate.operation = 'remove';
                } else if (jiraValue.length != 0 && jiraKey != '') {
                    jiraUpdate.id = jiraKey;
                    jiraUpdate.operation = 'update';
                    jiraUpdate.value = jiraValue;
                } else if (jiraValue.length != 0 && jiraKey == '') {
                    jiraUpdate.operation = 'add';
                    jiraUpdate.value = jiraValue;
                }

                formData.jira = jiraUpdate;
            }

            // get project privacy
            var privacyValue = $("input[name='privateFlag']:checked").val();
            var privacyKey = $("#privacyMetadataId").attr('name');
            var privacyUpdate = {keyId:9};
            if (privacyKey == '') {
                privacyUpdate.operation = 'add';
            } else {
                privacyUpdate.id = privacyKey;
                privacyUpdate.operation = 'update';
            }
            privacyUpdate.value = privacyValue;

            formData.privacy = privacyUpdate;

            var ratingOperations = [];

            // get project ratings
            $(".pRatings li").each(function(){
                var currentRating = $(this).find(".ratingEdit").data("rating");
                var oldRating = $(this).find("input[name='rating']").val();
                var keyId = $(this).find("input[name='key']").val();

                if(currentRating != oldRating) {

                    var ratingOperation = {keyId:keyId};

                    if(oldRating == 0) {
                        // add
                        ratingOperation.operation = "add";
                    } else {
                        ratingOperation.operation = "update";
                        var metadataId = $(this).find("input[name='metadataId']").val();
                        ratingOperation.id = metadataId;
                    }

                    ratingOperation.value = currentRating;

                    ratingOperations.push(ratingOperation);
                }

            });

            formData.projectRatings = ratingOperations;

            // get custom project metadata values

            if ($("input[name='customMetadataValue']").length > 0) {
                var customMetadataValues = [];

                $("input[name='customMetadataValue']").each(function () {
                    var inputWrapper = $(this).parents('.inputCWrapper:first');
                    var value = $(this).val();
                    var id = $(this).attr('id');
                    var keyId = inputWrapper.attr('id').substr('9');
                    var operation = {};
                    operation.keyId = keyId;

                    if(id != null && id.length != 0) {
                        id = id.substr(14);
                    }

                    if(id.length != 0 && value.length == 0) {
                        // remove
                        operation.id = id;
                        operation.operation = 'remove';
                        customMetadataValues.push(operation);
                    } else if (id.length != 0 && value.length != 0) {
                        // update
                        operation.id = id;
                        operation.value = value;
                        operation.operation = 'update';
                        customMetadataValues.push(operation);
                    } else if (id.length == 0 && value.length != 0) {
                        // add
                        operation.operation = 'add';
                        operation.value = value;
                        customMetadataValues.push(operation);
                    }

                });

                formData.customMetadataValues = customMetadataValues;
            }

            return {formData:formData};
        }

        $('.saveProjectButton').click(function () {

            if (validateEditProjectPage()) return false;

            modalLoad("#saveProject");

            var request = buildSaveProjectRequest();

            $.ajax({
                type:'post',
                url:"saveProjectSettings",
                data:request,
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult2(
                        jsonResult,
                        function (result) {
                            window.onbeforeunload = null;
                            window.open('./projectOverview?formData.projectId=' + request.formData.projectId, '_self');
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });

        });

        var currentModalCache = {};

        // Save the chosen managers
        $('.userManagementModal .saveButton').click(function () {
            var modal = $(this).parents('.userManagementModal:first');

            if(modal.attr('id') == 'addUserModal') return;

            var metadataKey = modal.attr('id') == 'clientManagersModal' ? 1 : 2;
            var requestURL = modal.attr('id') == 'clientManagersModal' ? 'saveClientProjectManagers' : 'saveTopCoderManagers';

            modal.find(".addUserLeft ul").empty();

            var list = modal.find('.addUserRight .addUserList ul li');

            var allInList = {};
            var toAdd = {};
            var toRemove = {};

            list.each(function () {
                var userId = $(this).attr('name');
                allInList[userId] = true;
                if (!(userId in currentModalCache)) {
                    toAdd[userId] = true;
                }
            });

            $.each(currentModalCache, function (key, value) {
                if (!(key in allInList)) {
                    toRemove[key] = value;
                }
            });


            var operations = [];

            // process toRemove
            $.each(toRemove, function (key, value) {
                var operation = {};
                operation.keyId = metadataKey;
                operation.id = value.id;
                operation.operation = 'remove';
                operations.push(operation);
            });

            // process toAdd
            $.each(toAdd, function (key, value) {
                var operation = {};
                operation.keyId = metadataKey;
                operation.operation = 'add';
                operation.value = key;
                operations.push(operation);
            });


            var formData = {projectId:$("input[name='editProjectId']").val()};

            modalAllClose();

            if (operations.length == 0) {
                return;
            }

            modalPreloader();

            if (modal.attr('id') == 'clientManagersModal') {
                formData.clientManagers = operations;
            } else {
                formData.projectManagers = operations;
            }


            $.ajax({
                type:'post',
                url:requestURL,
                data:{formData:formData},
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            // window.open('./editProject?formData.projectId=' + request.formData.projectId, '_self');
                            var handleList = $("a.triggerManagerModal[name='" + modal.attr('id') + "']").prev();
                            handleList.empty();
                            $.each(result, function (key, value) {

                                var entry = $('<li class="memberLink"><span name="' + key + '" class="hide"></span>'
                                    + '<a href="https://community.topcoder.com/tc?module=MemberProfile&cr='
                                    + value.userId + '" class="memberLink">' + value.handle + '</a><a href="javascript:;" class="close" title="Remove this handle"></a></li>');
                                handleList.append(entry);
                                entry.hover(
                                    function () {
                                        $(this).addClass('hovered');
                                    },
                                    function () {
                                        $(this).removeClass('hovered');
                                    }
                                );
                            });
                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });

        });

        // open the modal window of managers
        $('a.triggerManagerModal').click(function () {
            modalLoad('#' + $(this).attr('name'));

            var modalName = $(this).attr('name');
            var modal = $("#" + modalName);
            var leftSide = modal.find('.addUserLeft ul');

            // remove right side
            modal.find('.removeAll').trigger('click');

            // clear search input
            modal.find(".searchBox input").val('');

            // empty left side search result
            leftSide.empty();

            adjustLeftRightPanel(modal);

            var mLink = $('ul li a.memberLink', $(this).parent());

            // reset cache when open
            currentModalCache = {};

            for (var i = 0; i < mLink.length; i++) {
                var $this = mLink.eq(i);
                var text = $this.text();
                var index = $this.attr('href').indexOf("cr=");
                var id = -1;
                if (index != -1) {
                    id = $this.attr('href').substring(index + 3);
                }
                var entry = {};
                entry.name = text;
                entry.id = $this.prev().attr('name');
                currentModalCache[id] = entry;

                modal.find('.addUserRight .addUserList ul').append($('<li name="' + id + '">' + text + '</li>'));
                var lis = $('li:contains(' + $this.text() + ')', leftSide);
                lis.remove();

            }
            adjustLeftRightPanel(modal);
        });

        $(".userManagementModal .downloadProfile").click(function () {
            var modal = $(this).parents('.userManagementModal:first');

            // get search text first
            var searchText = $.trim(modal.find(".searchBox input").val());

            var request = {"searchText":searchText};

            $.ajax({
                type:'GET',
                url:"getUser",
                data:request,
                cache:false,
                dataType:'json',
                async:false,
                success:function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            // insert into the left list
                            modal.find('.addUserForm .addUserLeft ul').empty();
                            $.each(result, function () {
                                modal.find('.addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
                                modal.find('.addUserForm .addUserLeft ul').append('<li name="' + this.userId + '">' + this.handle + '</li>');
                            })


                        },
                        function (errorMessage) {
                            showErrors(errorMessage);
                        })
                }
            });

        });

        function closeIt() {
            return "Your updates haven\'t been saved. Would you like to save it?";
        }

        // window.onbeforeunload = closeIt;


        window.gToBeDelete = null;

//        $('#removeCustomeMeta .button6:eq(0)').click(function () {
//            if (window.gToBeDelete) {
//                window.gToBeDelete.remove();
//
//                $('.projectMetaAreaField').removeClass('oddRowItem');
//                $('.projectMetaAreaField:odd').addClass('oddRowItem');
//            }
//        });

        $('input:radio').each(function () {
            var label = $(this).next('label');
            label.css('cursor', 'pointer');
            label.data('radio', $(this));
            label.click(function () {
                var radio = $(this).data('radio');
                radio.trigger('click');
            });
        });

//        $('.removeCustomeMeta').click(function () {
//            var wp = $(this).parents('.projectMetaAreaField').eq(0);
//            var val = $('.projectMetaLabel', wp).text().replace(':', '');
//            window.gToBeDelete = wp;
//            $('#removeCustomeMeta .modalBody .deleteNotes h3 strong').text(val);
//        });

        function LoopMedaField() {
            $('.projectMetaArea .projectMetaAreaField').each(function (index) {
                $(this).css({
                    position:'relative',
                    zIndex:1000 - index
                });
            });
        }

        $('.projectMetaArea').each(function (index) {
            $(this).css({
                zIndex:1000 - index
            });
            if ($(this).css('position') != 'absolute') {
                $(this).css('position', 'relative');
            }
            $('.projectMetaAreaLabel', $(this)).css({
                zIndex:1000
            });
            if ($('.projectMetaAreaLabel', $(this)).css('position') != 'absolute') {
                $('.projectMetaAreaLabel', $(this)).css('position', 'relative');
            }

        });

        LoopMedaField();

        var validateCustomKeyCreation = function (name, description) {

            var passedValidation = true;

            if (name == null || $.trim(name).length == 0) {
                $("#customKeyNameError").text("Metadata key name cannot be empty");
                $("#customKeyNameError").show();
                passedValidation = false;
            } else {
                $("#customKeyNameError").hide();
            }

            if (description == null || $.trim(description).length == 0) {
                $("#customKeyDescriptionError").text("Metadata key description cannot be empty");
                $("#customKeyDescriptionError").show();
                passedValidation = false;
            } else {
                $("#customKeyDescriptionError").hide();
            }

            return passedValidation;
        }

        $('#addCustomeMeta .saveCustomKey').click(function () {

            var name = $('#addCustomeMeta #lib60').val();
            var singleRadio = $('#addCustomeMeta input[name="allowedValues"]');
            var singleFlag = $('#addCustomeMeta input[name="allowedValues"]:checked').val();

            var description = $("#addCustomeMeta textarea[name='customMetadataKeyDescription']").val();
            var groupFlag = $('#addCustomeMeta input[name="isGrouping"]:checked').val();
            var clientId = $("input[name='clientId']").val();

            if (!validateCustomKeyCreation(name, description)) {
                return;
            }

            var newKey = {};
            newKey.name = name;
            newKey.description = description;
            newKey.grouping = groupFlag;
            newKey.single = singleFlag;
            newKey.clientId = clientId;

            var formData = {formData:{newCustomKey:newKey}};

            formData.formData.projectId = $("input[name='editProjectId']").val();

            modalAllClose();
            modalPreloader();

            $.ajax({
                type:'post',
                url:"addNewCustomMetadataKey",
                data:formData,
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(
                        jsonResult,
                        function (result) {
                            var keyName = result.name;
                            var id = result.id;
                            var single = (result.single == 'true');

                            var subTitle = single ? '(Single)' : '(Multiple)';

                            name = keyName + ' ' + subTitle + ':';

                            var newMeta = $('<div class="projectMetaAreaField multiValueArea"><h4 class="projectMetaLabel"></h4><div class="inputCWrapper"><div class="inputContainer inputWrapper"><input type="text" name="customMetadataValue" value="" autocomplete="off" class="textInput"><a href="javascript:;" class="clearValue clearValueDisabled">Clear</a></div><a href="javascript:;" class="buttonGray moreValue"><span>More Value</span></a></div></div>');
                            newMeta.insertBefore($('.projectMetaArea .addCustomMetaBtnContainer'));

                            newMeta.addClass('multiValueArea');
                            var h = $('h4.projectMetaLabel', newMeta);
                            $('div.inputCWrapper', newMeta).attr('id', 'customKey' + id);
                            h.text(name);
                            h.css('word-wrap', 'break-word');
                            var height = h.height();
                            if (newMeta.height() < height + 10) {
                                newMeta.css('min-height', height + 10 + 'px');
                            }
                            if (single) {
                                $('.moreValue', newMeta).hide();
                            }

                            $('.projectMetaAreaField').removeClass('oddRowItem');
                            $('.projectMetaAreaField:odd').addClass('oddRowItem');

                            LoopMedaField();
                            setupEditProjectToolTip();

                        },
                        function (errorMessage) {
                            modalAllClose();
                            showServerError(errorMessage);
                        });
                }
            });


        });

        $('.sliderView input.radio').trigger('click');
        $('#durationSlider').enableSlider(true);
        $('.datePickerView .date-pick').dpSetDisabled(true);
        $('.datePickerView').addClass('disable');
        $('.sliderView').removeClass('disable');

    }

    try {
        $(window).resize(function () {
            var w1 = $('.editProjectForm').width() - 200;
            $('#lim2000').css('max-width', w1 + 'px');
            $('.projectTypeArea .comboContainer select').css('width',($('.projectTypeArea .comboContainer').width() - 130)/2);
            modalPosition();
        });

        var w1 = $('.editProjectForm').width() - 200;
        $('#lim2000').css('max-width', w1 + 'px');

    } catch (e) {
    }


    $('#budgetOutput').val(initialBudget > 0 ? initialBudget : '');
    $('#budgetOutput').trigger('change');
    $('#budgetOutput').val(initialBudget > 0 ? initialBudget : '');
    $('#durationOutput').val(initialDuration > 0 ? initialDuration : '');
    if (initialDuration > 400) {
        $('.datePickerView .radio').trigger('click').trigger('change');
        $('.datePickerView .radio').trigger('change');
    }
    $('#durationOutput').val(initialDuration > 0 ? initialDuration : '');

    // update the styles of the member links
    $("li.memberLink").each(function () {
        $(this).find("a:eq(0)").removeClass().addClass("memberLink");
    });
});

// Format the number.
function formatNumber(number) {
    var str = number + '';
    var l = str.length % 3;
    var temp = [];
    if (l > 0) {
        temp.push(str.substr(0, l));
    }
    while (l < str.length) {
        temp.push(str.substr(l, 3));
        l += 3;
    }
    return temp.join(',');
}
;

(function ($) {
    /* Enable/disable the slider. */
    $.fn.enableSlider = function (enable) {
        var s = $(this).data('options');
        s.enabled = enable;
        var handler = $('.sHandler', $(this));
        if (enable) {
            $(this).removeClass('disabled');
            if (handler.length > 0) {
                handler.draggable('enable');
            }
        } else {
            $(this).addClass('disabled');
            if (handler.length > 0) {
                handler.draggable('disable');
            }
        }

        $('.ui-state-disabled').css('opacity', 1);
    };

    /* Slide JQuery plug-in. */
    $.fn.slider = function (s) {
        s = $.extend({
            rule:[],
            defaultValue:0,
            output:null,
            convert:null,
            enabled:true,
            close:false,
            onExceed:null,
            onIn:null,
            step:0
        }, s || {});
        this.css('relative');
        this.data('options', s);
        if (s.rule && s.rule.length > 2) {
            s.value = [];

            var rule = $('<div class="slideContainer"><div class="slideRule"><div class="valueRule"></div></div></div>');
            $(this).append(rule);
            var w = $(this).width() - 16;

            var v1 = 8;
            var v2 = w + 6;

            var xv1 = s.rule[0];
            var xv2 = s.rule[s.rule.length - 1];

            if (s.convert) {
                xv1 = s.convert.call(s, xv1);
                xv2 = s.convert.call(s, xv2);
            }

            var d = (xv2 - xv1) / (v2 - v1);

            for (var i = 0; i < s.rule.length; i++) {
                if (s.convert) {
                    var v = s.convert.call(s, s.rule[i]);
                    s.value.push(v);
                } else {
                    s.value.push(s.rule[i]);
                }
                var spot = $('<div class="spot"><div class="inSpot"><div class="insideText"></div></div></div>');
                rule.append(spot);
                $('.insideText', spot).text(s.rule[i]);

                var l = s.value[i];
                l = Math.round((s.value[i] - xv1) / d + v1);

                spot.css('left', l + 'px');
                if (i == 0) {
                    spot.addClass('firstSpot');
                }
            }


            $('.slideContainer', $(this)).append('<div class="sContainer"><div class="sHandler"></div></div>');
            var handler = $('.sHandler', $(this));
            var container = $('.slideContainer', $(this));
            handler.data('w', container.width() - handler.width());
            handler.data('options', s);
            if ($.browser.msie && ($.browser.msie.version == '7.0' || $.browser.version == '8.0')) {
                handler.addClass('addMore');
            }
            var onDrag = function (target, left) {
                var container = target.parent();
                var w = target.data('w');
                var wrapper = container.parent();

                var s = target.data('options');
                var value = s.value;
                var minValue = value[0];
                var maxValue = value[value.length - 1];

                var value = Math.round((left / w) * distance) + minValue;
                if (value <= minValue) {
                    if (s.close) {
                        value = minValue + 1;
                    } else {
                        value = minValue;
                    }
                }
                if (value > maxValue) {
                    value = maxValue;
                }
                left = (value - minValue) / distance * w;
                $('.valueRule', wrapper).width(left);
                var output = s.output;
                if (output) {
                    if(!s.step){
                        output.val(formatNumber(parseInt(value, 10)));
                    }else{
                        output.val(formatNumber(parseInt(parseInt(value, 10)/1000)*1000));
                    }
                }
                return left;
            };
            handler.draggable({
                axis:'x',
                containment:'parent',
                drag:function (event, ui) {
                    var left = ui.position.left;
                    onDrag($(this), left);
                }
            });

            $('.sContainer', $(this)).click(function (event) {
                var s = $(this).parent().parent().data('options');
                if (s && s.enabled == false) {
                    return;
                }
                var h = $('.sHandler', $(this));
                var x = event.pageX;
                var offset = $(this).offset();
                var left = x - offset.left - h.width() / 2;

                h.css('left', onDrag(h, left) + 'px');

            });

            function setValue(s, value) {
                var d1 = s.container.width() - s.handler.width();
                var l = 0;
                if (value >= s.maxValue) {
                    l = d1;
                } else if (value <= s.minValue) {
                    l = 0;
                } else {
                    var v = value - s.minValue;
                    l = v / distance * d1;
                }
                s.handler.css('left', l + 'px');
                onDrag(s.handler, l);
            }

            if (s.output) {
                var value = s.value;
                var minValue = value[0];
                var maxValue = value[value.length - 1];
                var distance = maxValue - minValue;

                s.handler = handler;
                s.container = container;
                s.dis = distance;
                s.minValue = minValue;
                s.maxValue = maxValue;
                s.output.data('options', s);
                s.output.focus(function () {
                    var opt = $(this).data('options');
                    if (opt) {
                        opt.orgValue = $(this).val();
                    }
                });
                s.output.change(function () {

                    var opt = $(this).data('options');
                    var value = $(this).val();
                    var v = value.replace(/[,]/g, '').replace(/[ ]/g, '');
                    var reg = /^[0-9]+$/;
                    if (!($.trim(v).length == 0) && !reg.test(v)) {
                        showErrors('Please input a positive integer for project budget.');
                        $(this).val(opt.orgValue);
                        return;
                    }
                    value = parseInt(v, 10);
                    if (isNaN(value)) {
                        value = 0;
                    }
                    if (opt && opt.enabled) {
                        if (value <= s.minValue) {
                            if (s.close) {
                                value = s.minValue + 1;
                            } else {
                                value = s.minValue;
                            }
                        }
                        if (value > s.maxValue) {
                            if (s.onExceed) {
                                s.onExceed.apply(this, [value]);
                                return;
                            } else {
                                value = s.maxValue;
                            }
                        }
                        setValue(opt, value);
                    } else {
                        if (value <= s.minValue) {
                            if (s.close) {
                                value = s.minValue + 1;
                            } else {
                                value = s.minValue;
                            }
                        }
                        if (value <= s.maxValue) {
                            if (s.onIn) {
                                s.onIn.apply(this, []);
                            }
                        }
                    }

                    if (value == 0) {
                        $(this).val('');
                    }
                });

                setValue(s, s.defaultValue);

            }
        }


    };

}) (jQuery);
