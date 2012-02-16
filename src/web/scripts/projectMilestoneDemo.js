/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * Description: Provides JS for the project milestones demo. It's used by project-milestone-demo.jsp
 * and project-milestone-batch-create.jsp.
 * Author: TCSASSEMBLER
 * Version: 1.0 (Module Assembly - TC Cockpit Project Milestones Service Integration and Testing)
 */


/**
 * Gets the whole HTML of the selected jquery element.
 */
jQuery.fn.outerHTML = function () {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

/**
 * Checks whether the input with the name is empty.
 *
 * @param input the input value
 * @param name the name of the input
 */
var validInput = function (input, name) {
    if ($.trim(input).length == 0) {
        alert(name + " cannot be empty");
        return false;
    }
    return true;
}

var getMilestoneToAdd = function (section, directProjectId) {
    var name = section.find("input[name='milestoneName']").val();

    if (!validInput(name, "milestone name")) {
        return;
    }

    var description = section.find("input[name='milestoneDescription']").val();

    if (!validInput(description, "milestone description")) {
        return;
    }

    var notification = section.find("select[name='notificationChoice']").val();

    var dueDate = section.find("input[name='dueDate']").val();

    var responsiblePersonId = section.find(".addResponsiblePerson select").val();
    var responsiblePersonName = section.find(".addResponsiblePerson option:selected").text();

    var milestone = {};

    milestone.projectId = directProjectId;
    milestone.name = name;
    milestone.description = description;
    milestone.dueDate = dueDate;
    milestone.sendNotifications = notification;
    milestone.owners = [];
    var owner = {};
    if (responsiblePersonId > 0) {
        owner.userId = responsiblePersonId;
        owner.name = responsiblePersonName;
    }
    milestone.owners.push(owner);

    return milestone;
}


function loadResponsiblePerson() {
    var request = {}
    request['directProjectId'] = $("#directProjectInput :selected").val();
    var select = $("<select></select>");
    select.append($("<option></option>").attr("value", "-1").text("none"));

    $.ajax({
        type:"POST",
        url:"getProjectResponsiblePerson",
        data:request,
        dataType:'json',
        async:false,
        success:function (jsonResult) {

            $.each(jsonResult.result['return'], function (index, value) {
                select.append($("<option></option>").attr("value", value.userId).text(value.name));
            });
            $(".addResponsiblePerson select").remove();
            $(".addResponsiblePerson").append(select.outerHTML());
        }
    });
    return select;
}


/**
 * Loads the selected project's project milestones and render.
 *
 * @param showModal whether to show the modal.
 */
function loadProjectMilestones(showModal) {
    var table = $("#projectMilestonesSection table tbody");

    table.find("tr").remove();

    var request = {}
    request['directProjectId'] = $("#directProjectInput :selected").val();

    if (showModal) {
        modalPreloader();
    }

    var select = loadResponsiblePerson();

    $.ajax({
        type:"POST",
        url:"getProjectMilestones",
        data:request,
        dataType:'json',

        success:function (jsonResult) {
            if (showModal) {
                modalAllClose();
            }
            $.each(jsonResult.result['return'], function (index, value) {
                var html = "<tr><td>" + value.id + "</td><td>" +
                    value.name + "</td><td>" + value.description + "</td><td>" + value.dueDate + "</td><td class='responsible'></td><td>" + value.notification + "</td><td>" + value.completed + "</td><td>" + value.status +
                    "</td><td><input type='button' value='Remove' class='delete'/> <input class='update' type='button' style='margin-left:10px' value='Update'/></td>"
                    + "<td style='display:none'>" + value.id + "</td></tr>";
                var insertTo = $(html);
                insertTo.find("td.responsible").append(select.outerHTML());
                // select the value
                insertTo.find("select").val(value.ownerUserId);
                table.append(insertTo);
            });

            table.find("tr").each(function () {
                $(this).find("td").each(function () {
                    var i = $(this).index();
                    if (i == 1 || i == 2 || i == 3 || i == 5 || i == 6) {
                        var v = $(this).text();
                        $(this).empty();
                        $(this).append($("<input type='text'> </input>").attr('value', v));
                    }
                });
            });
        }
    });
}
;


$(document).ready(function () {

    if ($(".batchCreationPage").length == 0) {


        $("#projectTitleSpan").text("Projects Milestones Demo page");


        // load the project milestone after loading page.
        loadProjectMilestones(false);


        // render the project metadata when the project is changed.
        $("#directProjectInput").change(function () {
            loadProjectMilestones(true);
        })

        $("input.delete").live('click', function () {

            var milestoneId = $(this).parent().parent().find("td:eq(0)").text();
            var request = {};
            request.milestoneId = milestoneId;
            modalPreloader();
            $.ajax({
                type:"POST",
                url:"removeProjectMilestone",
                data:request,
                dataType:'json',
                success:function (jsonResult) {
                    modalAllClose();
                    loadProjectMilestones(false);
                }
            });

        });

        $("input.update").live('click', function () {

            // get current tr row first
            var row = $(this).parent().parent();

            var directProjectId = $("#directProjectInput :selected").val();
            var id = row.find("td:eq(0)").text();
            var name = row.find("td:eq(1) input").val();

            if (!validInput(name, "milestone name")) {
                return;
            }

            var description = row.find("td:eq(2) input").val();

            if (!validInput(description, "milestone description")) {
                return;
            }

            var notification = row.find("td:eq(5) input").val();

            var completed = row.find("td:eq(6) input").val();

            var dueDate = row.find("td:eq(3) input").val();

            var responsiblePersonId = row.find("td:eq(4) select").val();
            var responsiblePersonName = row.find("td:eq(4) option:selected").text();

            var milestone = {};

            milestone.id = id;
            milestone.projectId = directProjectId;
            milestone.name = name;
            milestone.description = description;
            milestone.dueDate = dueDate;
            milestone.sendNotifications = notification;
            milestone.completed = completed;

            if (responsiblePersonId > 0) {
                milestone.owners = [];
                var owner = {};
                owner.userId = responsiblePersonId;
                owner.name = responsiblePersonName;
                milestone.owners.push(owner);
            }

            var request = {milestone:milestone};

            modalPreloader();
            $.ajax({
                type:"POST",
                url:"updateProjectMilestone",
                data:request,
                dataType:'json',
                success:function (jsonResult) {
                    modalAllClose();
                    loadProjectMilestones(false);
                }
            });
        });


        $("#addMilestoneButton").click(function () {

            var directProjectId = $("#directProjectInput :selected").val();

            var milestoneToAdd = getMilestoneToAdd($(".milestoneAddSection"), directProjectId);

            if (milestoneToAdd == null) return;

            var request = {milestone:milestoneToAdd};

            modalPreloader();
            $.ajax({
                type:"POST",
                url:"addProjectMilestone",
                data:request,
                dataType:'json',
                success:function (jsonResult) {
                    modalAllClose();
                    loadProjectMilestones(false);
                }
            });

        });

        $("input[name='deleteAll']").click(function () {

            var directProjectId = $("#directProjectInput :selected").val();

            var milestoneIdToDel = [];

            $("#projectMilestonesSection tbody tr").each(function () {
                var id = $(this).find("td:eq(0)").text();
                milestoneIdToDel.push(id);
            });

            var request = {milestoneIds:milestoneIdToDel};

            modalPreloader();
            $.ajax({
                type:"POST",
                url:"removeProjectMilestones",
                data:request,
                dataType:'json',
                success:function (jsonResult) {
                    modalAllClose();
                    loadProjectMilestones(false);
                }
            });

        });

        if ($('.date-pick').length > 0) {
            $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
        }
    } else {

        loadResponsiblePerson();

        $("#directProjectInput").change(function () {
            loadResponsiblePerson();
        })

        $("#batchCreateMilestoneButton").click(function () {
            var milestones = [];

            $(".milestoneAddSection").each(function () {
                var m = getMilestoneToAdd($(this), $("#directProjectInput :selected").val());

                if (m != null) {
                    milestones.push(m);
                }

            });

            if(milestones.length < 3) return;

            var request = {milestones:milestones};

            modalPreloader();
            $.ajax({
                type:"POST",
                url:"addProjectMilestones",
                data:request,
                dataType:'json',
                success:function (jsonResult) {
                    modalAllClose();
                    window.open('./projectMilestone', '_self');
                }
            });


        });
    }
});