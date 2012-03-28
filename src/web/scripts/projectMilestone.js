/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * Javascript for for the project management pages.
 *
 * @version 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 */
var calendarData;
var userHandleColorMap = {};
var milestoneCalViewInit;

var loadUserHandleColorMap = function () {
    if ($("#responsiblePersonList").length > 0) {
        // empty first
        userHandleColorMap = {};
        $("#responsiblePersonList a").each(function () {
            var entry = {};
            entry.color = $(this).attr('class');
            entry.url = $(this).attr('href');
            var key = $(this).text();
            userHandleColorMap[key] = entry;
        });
    }
}

var cleanUpEmptyMilestoneGroup = function() {
    if($(".milestoneListView").length > 0) {
        $(".milestoneListView .milestoneList").each(function () {
            if($(this).find("dd").length == 0) {
                // there is no milestone in the group, hide current group
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    }
}

var transformDate = function(dateStr) {
    var date = $.datepicker.parseDate('mm/dd/yy', dateStr);
    return $.datepicker.formatDate('DD, dd MM yy', date);
}

var insertMilestoneIntoList = function (result) {
    var template = unescape($('#milestoneListItemTemplate').html());

    var dateToSet = result.status == 'completed' ? result.completionDate : result.dueDate;

    var item = $($.validator.format(template, transformDate(dateToSet), result.status,
        result.dueDate, result.name, result.description.substring(0, 100), result.description, result.id, result.notification));

    if (result['ownerName']) {
        var url = "javascript:;";
        var css = "coderTextOrange";

        if (userHandleColorMap[result.ownerName]) {
            url = userHandleColorMap[result.ownerName].url;
            css = userHandleColorMap[result.ownerName].color;
        }

        var ownerDom = $("<a></a>");
        ownerDom.addClass(css).attr('href', url).text(result.ownerName);
        var ownerId = $("<input type='hidden' name='ownerId'/>").val(result.ownerId);
        item.find(".projectT").append(ownerDom.outerHTML());
        item.find(".projectT").append(ownerId);
    }

    if (result.description.length <= 100) {
        var des = $("<span></span>").addClass('short');
        des.text(result.description);
        item.find(".projectD").empty().append(des.outerHTML());
    }

    // insert into list
    var date = $.datepicker.parseDate('mm/dd/yy', result.dueDate);
    var completed = false;

    var dl = $(".milestoneManage .milestoneList.overdue");
    if (result.status == "upcoming") {
        dl = $(".milestoneManage .milestoneList.upcoming");
    } else if (result.status == 'completed') {
        dl = $(".milestoneManage .milestoneList.completed ");
        completed = true;
        date = $.datepicker.parseDate('mm/dd/yy', result.completionDate);
    }

    if(completed) {
        // set checked
        item.find("input[name='projectName']").attr('checked', 'checked');
        // add completion date
        var completionInput = $("<input type='hidden' name='completionDate'/>").val(result.completionDate);
        item.find("input[name='dueDate']").after(completionInput);
    }

    if (dl.find("dd").length > 0) {
        var dds = dl.find("dd");
        for (var i = 0; i < dds.length; i++) {
            var dd = dds[i];
            var _date = result.status == 'completed' ?
                $.datepicker.parseDate('mm/dd/yy', $(dd).find(".date input[name='completionDate']").val()) :
                $.datepicker.parseDate('mm/dd/yy', $(dd).find(".date input[name='dueDate']").val());
            if ((!completed && _date.getTime() > date.getTime()) || (completed && _date.getTime() < date.getTime())) {
                $(dd).before(item);
                break;
            }
            if (i == dds.length - 1) {
                $(dd).after(item);
            }
        }

    } else {
        dl.find("dt").after(item);
    }

    // cleanup the list
    cleanUpEmptyMilestoneGroup();
}

$(document).ready(function () {

    loadUserHandleColorMap();

    // initialize all the date pickers used in project milestone pages
    $(" .multiMilestones .dueDate input,.newOutLay .dateLine input.text").datePicker({
        startDate:'01/01/00',
        clickInput: true
    }).change(function () {
            $(this).removeClass("tip");
            if ($(this).val() != '' && $(this).val() != 'mm/dd/yyyy') {
                $(this).removeClass('invalid').parent().find(".errorMessage").text('');
            }
        });

    // initialize the Add milestone button hover behavior
    if ($.fn.tooltip) {
        $(".milestoneManage .topLine a.grayButton").tooltip({
            tip:'#addMilestonePopup1',
            position:'bottom right',
            offset:[1, -110],
            delay:10
        });
        $(".milestoneManage .bottomLine a.grayButton").tooltip({
            tip:'#addMilestonePopup2',
            position:'bottom left',
            offset:[1, 110],
            delay:10
        });
    }

    // setup the description show/hide feature
    $(".milestoneManage .milestoneList .projectD a").live("click", function () {
        var projectD = $(this).parent().parent();
        projectD.find("span.short,span.long").toggle();
        return false;
    })

    // setup all the cancel button
    $("#new-modal .cancelButton").live('click', function () {
        modalAllClose();
    });

    $(".milestoneManage .milestoneList.overdue .projectT input, .milestoneManage .milestoneList.upcoming .projectT input").live("click", function () {
        if (!$(this).is(":checked")) {
            return true;
        }

        if ($("#setDatePopup").is(":visible")) {
            $("#setDatePopup").hide();
            $("#setDatePopup").data("trigger").attr("disabled", "");
            $("#setDatePopup").data("trigger").attr("checked", "");
        }
        $("#setDatePopup input.text").val($.datepicker.formatDate('mm/dd/yy', new Date()));
        $("#setDatePopup input.text").dpSetSelected($("#setDatePopup input.text").val());
        $("#setDatePopup input#curDate").attr("checked", "checked");


        var _this = $(this);
        _this.attr("disabled", "disabled");

        $("#setDatePopup").show().css({
            "left":$(this).offset().left + 20 + "px",
            "top":$(this).offset().top - 22 + "px"
        }).data("trigger", _this);

    })

    $("#setDatePopup a.cancel").click(function () {
        $("#setDatePopup").hide();
        var trigger = $("#setDatePopup").data("trigger");
        trigger.attr("disabled", "");
        trigger.attr("checked", "");
        return false;
    })

    $("#setDatePopup .done").click(function () {
        $("#setDatePopup").hide();
        var _this = $("#setDatePopup").data("trigger");
        var ms = _this.parents(".project").parent();


        ms.find(".projectD span a").hide();
        ms.find(".projectD img").remove();

        $("<img/>", {
            "src":"/images/dots-white.gif",
            "alt":"",
            "style":"padding-left:5px"
        }).appendTo(ms.find(".projectD"));

        var formData = {};
        formData.milestoneId = ms.find("input[name='milestoneId']").val();

        // check if "current date" is selected
        if(!$("#curDate").is(":checked")) {
            // not checked, need to set completion date
            var completionDate = $("#setDatePopup input.text").val();
            formData.completionDate = completionDate;
        }

        $.ajax({
            type:"POST",
            url:"changeMilestoneStatus",
            data:{formData:formData},
            dataType:'json',
            async:true,
            success:function (jsonResult) {

                handleJsonResult2(jsonResult,
                    function (result) {
                        ms.remove();
                        insertMilestoneIntoList(result);
                    },

                    function (errorMessage) {
                        ms.find(".projectD span a").show();
                        ms.find(".projectD img").remove();
                        showErrors(errorMessage);
                    }

                );
            }
        });
    })

    $("#setDatePopup input.text").datePicker({
        createButton:false,
        startDate:'01/01/1900',
        verticalOffset:22
    })

    $("#setDatePopup #pickupDate").click(function () {
        $("#setDatePopup input.text").dpDisplay();
    })

    var loadResponsiblePersonForProject = function (projectId, selector, selectionValue) {
        var formData = {};
        formData['projectId'] = tcDirectProjectId;
        var select = $("<select></select>").attr('name', 'projectRes');
        select.append($("<option></option>").attr("value", "-1").text("Unassigned"));

        $.ajax({
            type:"POST",
            url:"getProjectResponsiblePerson",
            data:{formData:formData},
            dataType:'json',
            async:false,
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        $.each(result, function (index, value) {
                            select.append($("<option></option>").attr("value", value.userId).text(value.name));
                        });

                        var parent = selector.parent();
                        selector.remove();
                        parent.append(select.outerHTML());

                        if (selectionValue != null) {
                            parent.find("select").val(selectionValue);
                        }
                    },

                    function (errorMessage) {
                        showErrors(errorMessage);
                    }

                );
            }
        });

        return select;
    }

    $(".milestoneManage .milestoneList.completed .projectT input").live("click", function () {
        if ($(this).is(":checked")) {
            return true;
        }
        var _this = $(this);
        _this.attr("disabled", "disabled");

        var ms = _this.parents(".project").parent();

        ms.find(".projectD span a").hide();
        ms.find(".projectD img").remove();

        $("<img/>", {
            "src":"/images/dots-white.gif",
            "alt":"",
            "style":"padding-left:5px"
        }).appendTo(ms.find(".projectD"));

        var formData = {};
        formData.milestoneId = ms.find("input[name='milestoneId']").val();

        $.ajax({
            type:"POST",
            url:"changeMilestoneStatus",
            data:{formData:formData},
            dataType:'json',
            async:true,
            success:function (jsonResult) {

                handleJsonResult2(jsonResult,
                    function (result) {
                        ms.remove();
                        insertMilestoneIntoList(result);
                    },

                    function (errorMessage) {
                        ms.find(".projectD span a").show();
                        ms.find(".projectD img").remove();
                        showErrors(errorMessage);
                    }

                );
            }
        });
    })

    var modalTrigger;

    $('.milestoneManage .milestoneList .actions a.edit').live('click', function () {
        var ms = $(this).parents("dd:eq(0)");

        $("#editMilestoneModal").find("input[name='projectName']").val($.trim(ms.find(".projectT label").text())).trigger('keyup');
        var description = '';
        if(ms.find(".projectD .long span").length > 0) {
            description = ms.find(".projectD .long span").html();
        } else {
            description = ms.find(".projectD span.short").html();
        }

        $("#editMilestoneModal").find("textarea[name='projectDesc']").val($.trim(description)).trigger('keyup');

        if(ms.find(".date input[name='completionDate']").length != 0) {
            // have completion date, edit completion date
            $("#editMilestoneModal").find("input[name='projectDuedate']").val(ms.find(".date input[name='completionDate']").val());
            $("#editMilestoneModal .dateLine label").text("Completion Date :");
        } else {
            $("#editMilestoneModal").find("input[name='projectDuedate']").val(ms.find(".date input[name='dueDate']").val());
            $("#editMilestoneModal .dateLine label").text("Due Date :");
        }

        $("#editMilestoneModal input.text").dpSetSelected($("#editMilestoneModal").find("input[name='projectDuedate']").val());

        $("#editMilestoneModal").find("input[name='emailNotify']").attr('checked', '');
        if(ms.find("input[name='notification']").val() == 'true') {
            $("#editMilestoneModal").find("input[name='emailNotify']").attr('checked', 'checked');
        }

        var ownerId = -1;

        if(ms.find("input[name='ownerId']").length > 0) {
           ownerId = ms.find("input[name='ownerId']").val();
        }

        loadResponsiblePersonForProject(tcDirectProjectId, $("#editMilestoneModal select[name='projectRes']"), ownerId);

        modalTrigger = $(this);
        modalTrigger.data("milestoneId", ms.find("input[name='milestoneId']").val());
        modalTrigger.data("status", ms.find("input[name='type']").val());
        modalLoad("#editMilestoneModal");
        return false;
    });

    $("#editMilestoneModal .saveButton").click(function () {

        if (validateMilestoneModalInput("editMilestoneModal")) {

            var ms = modalTrigger.parents(".actions").parent();
            var milestoneId = modalTrigger.data("milestoneId");

            modalAllClose();

            ms.find(".projectD span a").hide();
            ms.find(".projectD img").remove();

            $("<img/>", {
                "src":"/images/dots-white.gif",
                "alt":"",
                "style":"padding-left:5px"
            }).appendTo(ms.find(".projectD"));

            var request = buildMilestoneModalRequest("editMilestoneModal", milestoneId);

            if(modalTrigger.data("status") == 'completed') {
                request.formData.milestone.completed = true;
                request.formData.milestone.completionDate = request.formData.milestone.dueDate;
                request.formData.milestone.dueDate = ms.find("input[name='dueDate']").val();
            }


            $.ajax({
                type:"POST",
                url:"updateMilestone",
                data:request,
                dataType:'json',
                async:true,
                success:function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            if ($(".milestoneListView").length > 0) {
                                // list view, insert the created one
                                ms.remove();
                                insertMilestoneIntoList(result);
                            }
                        },

                        function (errorMessage) {
                            ms.find(".projectD span a").show();
                            ms.find(".projectD img").remove();
                            showErrors(errorMessage);
                        }

                    );
                }
            });
        }

    })

    var clearMilestoneModalInput = function (modalId) {
        var modal = $("#" + modalId);
        modal.find("input[name='projectName']").val('').trigger('keyup').removeClass('invalid').parent().find(".errorMessage").text('');
        modal.find("textarea[name='projectDesc']").val('').trigger('keyup').removeClass('invalid').parent().find(".errorMessage").text('');
        modal.find("input[name='projectDuedate']").val('mm/dd/yyyy').addClass('tip').removeClass('invalid').parent().find(".errorMessage").text('');
        modal.find("input[name='emailNotify']").attr('checked', '');
        modal.find("input.text").dpClearSelected();
    }

    var validateMilestoneModalInput = function (modalId) {
        var modal = $("#" + modalId);
        var passValidation = true;
        // name, description and due date are required
        if ($.trim(modal.find("input[name='projectName']").val()) == '') {
            passValidation = false;
            modal.find("input[name='projectName']").addClass('invalid').parent().find(".errorMessage").text('Name cannot be empty');
        }
        else if (containTags($.trim(modal.find("input[name='projectName']").val()))) {
            passValidation = false;
            modal.find("input[name='projectName']").addClass('invalid').parent().find(".errorMessage").text('Name cannot contain HTML tags');
        }
        else {
            modal.find("input[name='projectName']").removeClass('invalid').parent().find(".errorMessage").text('');
        }

        if ($.trim(modal.find("textarea[name='projectDesc']").val()) == '') {
            passValidation = false;
            modal.find("textarea[name='projectDesc']").addClass('invalid').parent().find(".errorMessage").text('Description cannot be empty');
        } else if (containTags($.trim(modal.find("textarea[name='projectDesc']").val()))) {
            passValidation = false;
            modal.find("textarea[name='projectDesc']").addClass('invalid').parent().find(".errorMessage").text('Description cannot contain HTML tags');
        }
        else {
            modal.find("textarea[name='projectDesc']").removeClass('invalid').parent().find(".errorMessage").text('');
        }

        if ($.trim(modal.find("input[name='projectDuedate']").val()) == '' || modal.find("input[name='projectDuedate']").val() == 'mm/dd/yyyy') {
            passValidation = false;
            modal.find("input[name='projectDuedate']").addClass('invalid').parent().find(".errorMessage").text('Due Date should be set');
        } else {
            modal.find("input[name='projectDuedate']").removeClass('invalid').parent().find(".errorMessage").text('');
        }

        return passValidation;
    }

    var buildMilestoneModalRequest = function(modalId, milestoneId) {
        var formData = {};
        var milestone = {};
        var modal = $("#" + modalId);

        milestone.name = modal.find("input[name='projectName']").val();
        milestone.description = modal.find("textarea[name='projectDesc']").val();
        milestone.dueDate = modal.find("input[name='projectDuedate']").val();
        milestone.sendNotifications = modal.find("input[name='emailNotify']").is(':checked');
        milestone.projectId = tcDirectProjectId;
        milestone.owners = [];

        if(modal.find("select").val() > 0) {
            var owner = {};
            owner.userId = modal.find("select").val();
            owner.name = modal.find("select option:selected").text();
            milestone.owners.push(owner);
        }

        milestone.id = milestoneId;

        if(milestone.id == 0) {
            milestone.completed = false;
        }

        formData.milestone = milestone;

        return {formData:formData};
    }

    // ADD MILESTONE related
    $('.addMilestonePopup .popupMask .single a.grayButton').live('click', function () {
        modalLoad("#addMilestoneModal");
        clearMilestoneModalInput('addMilestoneModal');
        loadResponsiblePersonForProject(tcDirectProjectId, $("#addMilestoneModal select[name='projectRes']"), -1);
        return false;
    });

    $("#addMilestoneModal .saveButton").live('click', function () {
        if (validateMilestoneModalInput("addMilestoneModal")) {

            var request = buildMilestoneModalRequest("addMilestoneModal", 0);

            modalAllClose();
            modalPreloader();

            $.ajax({
                type:"POST",
                url:"addProjectMilestone",
                data:request,
                dataType:'json',
                async:true,
                success:function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            if($(".milestoneCalView").length > 0) {
                                // calendar view, reload the calendar
                                loadProjectMilestoneCalendarData(false);
                                modalAllClose();
                            } else if ($(".milestoneListView").length > 0) {
                                // list view, insert the created one
                                insertMilestoneIntoList(result);

                                modalAllClose();
                            }
                        },

                        function (errorMessage) {
                            showErrors(errorMessage);
                        }

                    );
                }
            });
        }
    });


    $('.multiMilestones td input,.multiMilestones td select').focus(function () {
        $(this).removeClass("invalid");
        $(".errorMsg").hide();
    });

    // set up removal milestone confirmation window
    $('.milestoneManage .milestoneList .actions a.remove').live('click', function () {
        modalTrigger = $(this);
        modalLoad("#removeMilestoneModal");
        return false;
    });

    $("#removeMilestoneModal .saveButton").click(function () {

        var ms = modalTrigger.parents(".actions").parent();

        ms.find(".projectD span a").hide();
        ms.find(".projectD img").remove();

        modalAllClose();

        $("<img/>", {
            "src":"/images/dots-white.gif",
            "alt":"",
            "style":"padding-left:5px"
        }).appendTo(ms.find(".projectD"));

        var formData = {};
        formData.milestoneId = ms.find("input[name='milestoneId']").val();

        $.ajax({
            type:"POST",
            url:"removeMilestone",
            data:{formData:formData},
            dataType:'json',
            async:true,
            success:function (jsonResult) {

                handleJsonResult2(jsonResult,
                    function (result) {
                        ms.remove();
                        cleanUpEmptyMilestoneGroup();
                    },

                    function (errorMessage) {
                        ms.find(".projectD span a").show();
                        ms.find(".projectD img").remove();
                        showErrors(errorMessage);
                    }

                );
            }
        });

    })

    $("#editMilestoneModal input.limitText, #addMilestoneModal input.limitText").limitedText({
        max:80
    })
    $("#editMilestoneModal textarea,#addMilestoneModal textarea").css("resize", "none");
    $("#editMilestoneModal textarea,#addMilestoneModal textarea").limitedText({
        max:250
    })

    var loadProjectMilestoneCalendarData = function (showModal) {
        var formData = {};
        formData['projectId'] = tcDirectProjectId;

        if (showModal == true) {
            modalPreloader();
        }

        $('.milestoneCalView').empty();

        $.ajax({
            type:"POST",
            url:"getProjectMilestoneCalendarData",
            data:{formData:formData},
            dataType:'json',
            async:false,
            success:function (jsonResult) {

                if (showModal == true) {
                    modalAllClose();
                }

                handleJsonResult2(jsonResult,
                    function (result) {
                        calendarData = result;
                    },

                    function (errorMessage) {
                        showErrors(errorMessage);
                        calendarData = null;
                    }

                );
            }
        });

        milestoneCalViewInit = false;

        if (calendarData != null) {

            // update handle color and link first
            $.each(calendarData, function(index, item){
                if(item.person && item.person.name) {
                    if(userHandleColorMap[item.person.name] != null) {
                        item.person.color = userHandleColorMap[item.person.name].color;
                        item.person.url = userHandleColorMap[item.person.name].url;
                    }
                }
            });

            $('.milestoneCalView').fullCalendar({
                header:{
                    left:'prev',
                    center:'title',
                    right:'next'
                },
                editable:false,
                dayNamesShort:['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                //events:calendarData.events,
                eventRender:function (event, element) {
                    var inner = element.find(".fc-event-inner");
                    switch (event["status"]) {
                        case 'completed':
                            element.addClass("fc-milestone-completed");
                            if (event.person) {
                                $("<a/>", {
                                    "text":event.person.name,
                                    "href":event.person.url,
                                    "class":event.person.color
                                }).appendTo(inner);
                            }
                            break;
                        case 'upcoming':
                            element.addClass("fc-milestone-upcoming");
                            if (event.person) {
                                $("<a/>", {
                                    "text":event.person.name,
                                    "href":event.person.url,
                                    "class":event.person.color
                                }).appendTo(inner);
                            }
                            break;
                        case 'overdue':
                            element.addClass("fc-milestone-overdue");
                            if (event.person) {
                                $("<a/>", {
                                    "text":event.person.name,
                                    "href":event.person.url,
                                    "class":event.person.color
                                }).appendTo(inner);
                            }
                            break;
                    }

                    var tcTip = $("<div/>", {
                        "class":"milestoneTips"
                    });
                    $("<div/>", {
                        "class":"triangle"
                    }).appendTo(tcTip);
                    $("<p/>", {
                        "text":event.description
                    }).appendTo(tcTip);
                    tcTip.appendTo(inner.parent());

                    inner.find(".fc-event-title").hover(function () {
                        tcTip.css("top", $(this).height() + 5 + "px");
                        inner.parent().css("z-index", 9);
                        tcTip.show();
                    }, function () {
                        tcTip.hide();
                        inner.parent().css("z-index", 8);
                    });
                },
                eventAfterRender:function (event, element) {
                    if (event["status"] == "overdue") {
                        $('.milestoneCalView .fc-content tbody td:eq(' + element.data("tdIndex") + ")").addClass("fc-overdue");
                    }
                },
                viewDisplay:function () {
                    $(".milestoneCalView .fc-today .fc-day-number").html("TODAY");
                    if (!milestoneCalViewInit) {
                        milestoneCalViewInit = true;

                        // this will be replaced with the ajax call in Assembly
                        $.each(calendarData, function (index, item) {
                            $('.milestoneCalView').fullCalendar("renderEvent", item, true);
                        })
                    }
                }
            });
        }
    }


    /* full calendar */
    if ($(".milestoneCalView").length > 0) {
        loadProjectMilestoneCalendarData(true);
    }

    var validateMultipleRow = function (row) {
        var hasInput = false;

        row.find('input').each(function () {
            if ($(this).attr('name') != 'projectDuedate' && $.trim($(this).val()) != '') {
                hasInput = true;
            } else if ($(this).attr('name') == 'projectDuedate' && $(this).val() != '' && $(this).val() != 'mm/dd/yyyy') {
                hasInput = true;
            }
        });

        var passValidation = true;

        if (hasInput) {
            // do the validation
            row.find('input').each(function () {
                if ($(this).attr('name') != 'projectDuedate' && $(this).val() == '') {
                    $(this).addClass('invalid');
                    passValidation = false;
                } else if ($(this).attr('name') == 'projectDuedate' && ($(this).val() == '' || $(this).val() == 'mm/dd/yyyy')) {
                    $(this).addClass('invalid');
                    passValidation = false;
                }
            });
        }

        return passValidation;

    }


    var buildBatchCreationRequest = function () {
        var milestones = [];

        $(".multiMilestones tbody tr").each(function () {
            var row = $(this);

            if($.trim(row.find("input[name='projectName']").val()) != '') {
                // this row has data, build request
                var milestone = {};
                milestone.id = 0;
                milestone.projectId = tcDirectProjectId;
                milestone.completed = false;
                milestone.sendNotifications = true;

                milestone.name = $.trim(row.find("input[name='projectName']").val());
                milestone.description = $.trim(row.find("input[name='projectDesc']").val());
                milestone.dueDate = $.trim(row.find("input[name='projectDuedate']").val());
                milestone.owners = [];

                if (row.find("select[name='projectRes']").val() > 0) {
                    var owner = {};
                    owner.userId = row.find("select[name='projectRes']").val();
                    owner.name = row.find("select[name='projectRes'] option:selected").text();
                    milestone.owners.push(owner);
                }

                milestones.push(milestone);
            }

        });

        return {formData:{milestones:milestones}};
    }


    // batch creation
    if($(".multiMilestones").length > 0) {

        $(".multiMilestones .saveButton").click(function () {

            var allPassedValidation = true;

            $(".multiMilestones tbody tr").each(function () {

                if(!validateMultipleRow($(this))) allPassedValidation = false;

            });


            if(!allPassedValidation) { $(".errorMsg").show(); return; }
            else {$(".errorMsg").hide();}

            var request = buildBatchCreationRequest();

            if(request.formData.milestones.length == 0) {
                showErrors("No milestone data entered");
                return;
            }


            modalAllClose();
            modalPreloader();

            $.ajax({
                type:"POST",
                url:"addProjectMilestones",
                data:request,
                dataType:'json',
                async:true,
                success:function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            window.onbeforeunload = null;
                            window.open('./projectMilestoneView?formData.projectId=' + tcDirectProjectId + "&formData.viewType=list", '_self');
                        },

                        function (errorMessage) {
                            showErrors(errorMessage);
                        }

                    );
                }
            });


        });

    }
});

jQuery.fn.outerHTML = function () {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

(function ($) {

    $.fn.limitedText = function (options) {

        var max = options.max;

        function limit(textarea) {
            var val = textarea.val();
            if (val.length > max) {
                textarea.val(val.substring(0, max));
            } else {
                textarea.parent().find(".num").text(max - val.length)
            }

            if (val.length > 0) {
                textarea.parent().find(".errorMessage").text('');
                textarea.removeClass('invalid');
            }
        }

        this.each(function () {

            $(this).parent().find(".num").text(options.max - $(this).val().length);
            $(this).keydown(function () {
                limit($(this));
            });
            $(this).keyup(function () {
                limit($(this));
            });

        })
    }

})(jQuery)
