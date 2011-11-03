/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * Javascript file for the new creation project process.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1)
 * @author: TCSASSEMBLER
 */


/**
 * Creates the game plan for the create new project page.
 *
 * @param htmlDiv the id of the div to put generated game plan in
 * @param hasTreePanel whether to display tree panel in the game plan
 */
function createChartControl(htmlDiv, hasTreePanel) {
    var ganttChartControl;

    // Create Gantt control
    ganttChartControl = new GanttChart();
    // Setup paths and behavior
    ganttChartControl.setImagePath("/images/gantt/");
    ganttChartControl.setEditable(false);
    ganttChartControl.showContextMenu(false);
    ganttChartControl.showTreePanel(hasTreePanel);

    ganttChartControl.getMonthScaleLabel = function(date) {
        return "";
    }


    // Build control on the page
    ganttChartControl.create(htmlDiv);
    ganttChartControl.showDescTask(true, 'n,e,d');
    ganttChartControl.showDescProject(true, 'n');

    // Load data structure
    ganttChartControl.loadData("../scripts/data.xml", true, true);
}

/**
 * Recreates the game plan.
 *
 * @param htmlDiv the id of the div to put generated game plan in
 * @param hasTreePanel whether to display tree panel in the game plan
 */
function recreateChartControl(htmlDiv, hasTreePanel) {
    if ($('#' + htmlDiv).length > 0) {
        $('#' + htmlDiv).html('');
        createChartControl(htmlDiv, hasTreePanel);
    }
}

// remember user's choices
var stepsChoices = {};

/**
 * Moves to the step with the given step number.
 *
 * @param stepNumber the step number from 1 - 6.
 */
var goCreateProjectStep = function(stepNumber) {

    if (stepNumber < 1 || stepNumber > 6) {
        return;
    }

    var step;
    for (step = 1; step <= 6; ++step) {
        if (stepNumber == step) {
            $("#newProjectStep" + step).show();
        } else {
            $("#newProjectStep" + step).hide();
        }
    }

    $(".stepBar li").each(function() {
        var index = $(this).index() + 1;
        var s = $(this).find(">span");
        if (index < stepNumber) {
            // completed
            s.removeClass();
            s.addClass("istatus").addClass("complete");
        } else if (index == stepNumber) {
            s.removeClass();
            s.addClass("istatus").addClass("active");
        } else {
            s.removeClass().addClass("istatus").addClass("inext");
            s.find(".bg").html("Step " + index);
        }

        if (index <= stepNumber) {
            // add link
            var linkHTML = $("#stepBarLinkTemplate").html();
            s.find(".bg").html(linkHTML);
            var stepLink = s.find(".bg a");
            stepLink.html("Step " + index);

            stepLink.unbind('click');
            stepLink.attr("href", "javascript:goCreateProjectStep(" + index + ");");


            if(stepNumber >= 3 && stepsChoices['step2'] == 'custom' && index == 3) {
                s.addClass("finished");
                s.find("a").unbind('click').attr("href", "javascript:;");
            }
        }
    });

    if (stepNumber == 6) {
        $(".stepBar li").each(function() {
            var index = $(this).index() + 1;
            var s = $(this).find(">span");
            s.addClass("finished");
            s.find("a").unbind('click').attr("href", "javascript:;");

        });
    }

    if(stepsChoices['step2'] == 'custom') {
        $("#customGamePlanRadio").attr('checked', true);
    }

    if (stepNumber == 2) {
        // check custom project plan by default for now
        $("#customGamePlanRadio").attr("checked", "checked");
    }

    if (stepNumber == 4) {
        // check no - do not need copilot by default for now
        $(".stepForth #radioNo").attr("checked", true);
    }

};

var generateConfirmationPage = function(projectId, projectName) {
    // determine what to show in the final confirmation page
    if ($(".stepSixth").length > 0) {
        var step2 = stepsChoices["step2"];
        var step4 = stepsChoices["step4"];
        var step2Size;

        // add project name
        var text = $(".stepSixth .step1 p").html();
        text = text + ' <span class="newProjectName"> ' + projectName + "</span>";
        $(".stepSixth .step1 p").html(text);

        // update project overview link
        var projectOverviewLink = $(".stepSixth .bottomLine .button6");
        var hrefValue = projectOverviewLink.attr('href');
        hrefValue = hrefValue.replace("projectIdValue", projectId);
        projectOverviewLink.attr('href', hrefValue);

        if (step2 == "gameplan" && step4 == "chooseCopilot") {
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .completedSteps .step3.selectStep").show();
        }

        if (step2 == "gameplan" && step4 == "createCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .completedSteps .step3.createStep").show();
            $(".stepSixth .notYetSteps .step4.launchStep").show();
            $(".stepSixth .notYetSteps .step5.selectStep").show();
        }

        if (step2 == "gameplan" && step4 == "noCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .notYetSteps .step3.gameplanStep").show();
            $(".stepSixth .notYetSteps .step4.completeStep").show();
            $(".stepSixth .notYetSteps .step5.launchFirstStep").show();
        }

        if (step2 == "gameplan") {
            step2Size = stepsChoices["step2-size"];
            if ((step2Size === null) || (step2Size === undefined)) {
                updateProjSizeData($(".stepSixth .projectStats"), 0);  // Arbitrary default.
            } else {
                updateProjSizeData($(".stepSixth .projectStats"), step2Size);
            }
        }

        if (step2 == "custom") {
            $(".stepSixth .projectStats").hide();
        }

        if (step2 == "custom" && step4 == "chooseCopilot") {
            $(".stepSixth .completedSteps .step2.selectStep").show();
        }

        if (step2 == "custom" && step4 == "createCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.createStep").show();
            $(".stepSixth .notYetSteps .step3.launchStep").show();
            $(".stepSixth .notYetSteps .step4.selectStep").show();
        }

        if (step2 == "custom" && step4 == "noCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .notYetSteps .step2").show();
            $(".stepSixth .notYetSteps .step3.completeStep").show();
            $(".stepSixth .notYetSteps .step4.launchFirstStep").show();
        }
    }

};

// final step
var createNewProject = function() {
    // get project name and project description
    var projectName = $("#newProjectName").val();
    var projectDescription = $("#newProjectDescription").val();

    var permissions = [];

    // get permissions
    $('.stepFifth .checkPermissions .userRow').each(function() {
        var permission = {};
        var userId = $(this).find('div.group').attr('id').substring(6);
        var userName = $.trim($(this).find('div.group').html());
        var permissionType;

        // check permission from higher
        if ($(this).find('.fullPermissionSelect:checked').length > 0) {
            permissionType = "full";
        } else if ($(this).find('.writePermissionSelect:checked').length > 0) {
            permissionType = "write";
        } else if ($(this).find('.readPermissionSelect:checked').length > 0) {
            permissionType = "read";
        }

        if (permissionType != undefined && permissionType != null) {
            permission['userId'] = userId;
            permission['handle'] = userName;
            permission['permission'] = permissionType;

            permissions.push(permission);
        }

    });


    var request = {};
    request['projectName'] = projectName;
    request['projectDescription'] = projectDescription;
    request['permissions'] = permissions;

    modalPreloader();

    $.ajax({
        type: 'GET',
        url:  "createNewCockpitProjectAjax",
        data: request,
        cache: false,
        dataType: 'json',
        async : false,
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    var projectName = result.projectName;
                    var projectId = result.projectId;

                    modalClose();

                    generateConfirmationPage(projectId, projectName);

                    goCreateProjectStep(6);
                },
                function(errorMessage) {
                    modalClose();
                    showErrors(errorMessage);
                })
        }
    });

};


// utility to limit the text input and text area
(function($) {

    // text limitation for text area and text input
    $.fn.limitedText = function(options) {
        var max = options.max;

        function limit(box) {
            var val = box.val();
            if (val.length > max) {
                box.val(val.substring(0, max));
            } else {
                box.parent().find(".num").text(max - val.length);
            }
        }

        this.each(function() {
            if (!$(this).hasClass("tipIt"))$(this).parent().find(".num").text(options.max - $(this).val().length);
            $(this).keydown(function() {
                limit($(this));
            });
            $(this).keyup(function() {
                limit($(this));
            });
        })
    }

    // input tips
    $.fn.inputTips = function(tip) {
        this.each(function() {
            $(this).data("tips", tip);
            $(this).focusin(function() {
                var value = $.trim($(this).val());
                if (value == "" || value == tip) {
                    $(this).removeClass("tipIt").val("");
                }
            });
            $(this).focusout(function() {
                var value = $.trim($(this).val());
                if (value == "" || value == tip) {
                    $(this).addClass("tipIt").val(tip);
                }
            });
            $(this).trigger("focusout");
        })
    }
})(jQuery)



$(document).ready(function() {

    // text-shadow
    $('.solveProblem .inner h3,.stepBar li span.bg,.stepFifth .geryContent .downloadProfile .profileLeft').css('text-shadow', '0px 1px 0px #ffffff');
    $('.stepBar li a').css('text-shadow', '0px 1px 0px #6C871E');
    $('.stepBar li .complete a').css('text-shadow', '0px 1px 0px #53661C');

    // z-index for step bar
    $('.stepBar li').each(function() {
        var totalStep = $('.stepBar li').length;
        var iNum = $('.stepBar li').index(this);
        $(this).css('z-index', totalStep - iNum);
    });

    // comment this statement because we want to disable some checkboxes
    // $('.stepSecond .radio,.stepForth .radio').attr('checked', '');

    // text area
    $('.stepFirst textarea').css('width', $('.stepFirst .row').width() - 206);

    // width for step bar
    $('.stepBar li').css('width', ($('.stepBar').width() / 6) + 17);
    $('.stepBar li:first').css('width', ($('.stepBar').width() / 6) + 15);

    // update the step bar width and textarea width when the browser size is changed
    $(window).resize(function() {
        $('.stepBar li').css('width', ($('.stepBar').width() / 6) + 17);
        $('.stepBar li:first').css('width', ($('.stepBar').width() / 6) + 15);
        $('.stepFirst textarea').css('width', $('.stepFirst .row').width() - 206);
    });


    // Sets up the toolTip
    $('.toolTip').hover(function() {
        $(this).append('<div class="toolTipContainer"><div class="arrow"></div><p class="textBox"></p></div>');
        $(this).find('p').append($(this).attr('rel'));
    }, function() {
        $(this).empty();
    });

    // adjust the position of the modal window
    addressPositionModal = function(isFull) {
        var wWidth = window.innerWidth;
        var wHeight = window.innerHeight;

        if (wWidth == undefined) {
            wWidth = document.documentElement.clientWidth;
            wHeight = document.documentElement.clientHeight;
        }

        var boxLeft = parseInt((wWidth / 2) - ( $("#new-modal").width() / 2 ));
        var boxTop = parseInt((wHeight / 2) - ( $("#new-modal").height() / 2 ));

        // position modal
        if (!isFull) {
            $("#new-modal").css({
                'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
            });
        }

        $("#modalBackground").css({
            "width" : $(document).width(),
            "opacity": "0.6"
        });

        if ($("body").height() > $("#modalBackground").height()) {
            $("#modalBackground").css("height", $("body").height() + "px");
        }
    }

    // load the modal window
    addressLoadModal = function(itemId, isFull) {
        $('#modalBackground').show();
        $(itemId).show();
        addressPositionModal(isFull);
    }

    // close the modal window
    addresscloseModal = function() {
        $('#modalBackground').hide();
        $('#errortModal,#detailModal,#gamePlanModal,#addUserModal,#deleteUserModal,#deleteConfirmModal,#alertModal,#selectionConfirmationModal,#createPostingModal,#customConfirmModal,#errorcModal,#createProjectConfirm,#errortModalStep4').hide();
    }

    // reposition the modal window when the browser size is changed
    $(window).resize(function() {
        if ($('#new-modal .outLay:visible').length > 0) {
            addressPositionModal();
        }
    });

    Request = {
        QueryString : function(item) {
            var svalue = location.search.match(new
                RegExp('[\?\&]' + item + '=([^\&]*)(\&?)', 'i'));
            return svalue ? svalue[1] : svalue;
        }
    }
    var key = Request.QueryString('id');

    $(".stepFirst .nextStepButton").click(function() {

        removeError($(".stepFirst .descProject textarea"));
        var valid1 = validate($(".stepFirst .descProject textarea"));

        removeError($(".stepFirst .projectName input"));
        var valid2 = validate($(".stepFirst .projectName input"));

        if (valid1 && valid2) {
            goCreateProjectStep(2);
            return true;
        } else {
            return false;
        }
    });


    // STEP 2 project type and project plan validations
    $('.stepSecond .geryContent .nextStepButton').click(function() {
        var falgRadio = false, jPar;
        $('.stepSecond .radio').each(function() {
            if ($(this).attr('checked')) {
                falgRadio = true;
            }
        });
        if (!falgRadio) {
            // no project plan is chosen
            addresscloseModal();
            addressLoadModal('#errortModal');
        } else {
            jPar = $('.stepSecond .radio:checked').parent();
            if (jPar.find('label').text() == 'Custom') {

                // if custom, show the custom confirm modal
                addresscloseModal();
                addressLoadModal('#customConfirmModal');

                // $.cookie("step2-size", null);
                stepsChoices['step2'] = 'custom';

            } else {
                // $.cookie("step2", "gameplan");
                // $.cookie("step2-size", jPar.parent().find(".selProjSize").val());
                // window.location.href = 'start-a-new-project-step-3.html';
                showErrors("Does not support now, will be implemented in next assembly.");
            }
        }
    });

    $('.stepForth .geryContent .nextStepButton').click(function() {
        var copilotYesNoChecked = false;

        $('.stepForth .radio').each(function() {
            if ($(this).attr('checked')) {
                copilotYesNoChecked = true;
            }
        });

        // checked value
        var val = $('.stepForth .radio:checked').val();

        if (!copilotYesNoChecked) {

            addresscloseModal();
            addressLoadModal('#errortModalStep4');

        } else if (val == "yes") {

            addresscloseModal();
            addressLoadModal('#errorcModal');

        } else if (val == "no") {

            stepsChoices['step4'] = 'noCopilot';
            goCreateProjectStep(5);
        }
    });

    $(".stepContainer .prevStepButton").live('click', function(){
        var previousStep = $(".stepBar .active").parent().index();

        if(previousStep == 3 && stepsChoices['step2'] == 'custom') {
             previousStep = 2;
        }

        goCreateProjectStep(previousStep);
    });

    $('.stepFifth .geryContent .nextStepButton').click(function() {
        var hasUserPermissionAdded = false;

        $('.stepFifth .checkPermissions .userRow').each(function() {
                hasUserPermissionAdded = true;
        });
        if ($('.addUserPlan').is(":visible")) {
            if ((!hasUserPermissionAdded)) {

                addresscloseModal();
                addressLoadModal('#alertModal');

            } else {

                addresscloseModal();
                addressLoadModal('#createProjectConfirm');

            }
        } else {

            addresscloseModal();
            addressLoadModal('#alertModal');

        }
    });

    // custom project type confirmation button
    $("#customConfirmModal .button6").live('click', function(){
        goCreateProjectStep(4);
    });

    //detail modal
    $('.stepSecond .geryContent .detailButton').click(function() {
        var jModal = $('#detailModal');
        var cost, dur, contestList, i, jContest;
        addresscloseModal();
        addressLoadModal('#detailModal');
        jModal.find('h2').text($(this).parent().parent().parent().parent().parent().parent().find('.radioBox label').text());
        function randInt(lo, hi) {
            return lo + Math.floor(Math.random() * (hi - lo + 1));
        }

        cost = randInt(10, 1000);  // TODO: Use real data.
        dur = randInt(1, 31);  // TODO: Use real duration.
        jModal.find('.dataCost').html("$ " + cost.toFixed(2));
        jModal.find('.dataDur').html(dur + ((dur === 1) ? " day" : " days"));

        contestList = [randInt(1, 6), randInt(1, 6), randInt(1, 6), randInt(1, 6), randInt(1, 6), randInt(1, 6)];  // TODO: Use real data.
        jContest = jModal.find('.dataContest');
        for (i = 0; i < contestList.length; ++i) {
            jContest.eq(i).html(contestList[i]);
        }
    });

    /**
     * Provides the sample data for the project size dropdown.
     *
     * @param jPar the html elements for project size data
     * @param v the index
     */
    function updateProjSizeData(jPar, v) {
        var durList = [30, 60, 120], costList = [10000, 30000, 60000], numContList = [5, 10, 20];
        jPar.find('.dataDur').html(durList[v] + " days");
        jPar.find('.dataCost').html("$ " + costList[v].toFixed(2));
        jPar.find('.dataNumCont').html(numContList[v]);
    }

    $('.stepSecond .selProjSize').each(function () {  // Initialize.
        updateProjSizeData($(this).parents('table').eq(0), $(this).val());
    });
    $('.stepSecond .selProjSize').bind("change", function () {
        updateProjSizeData($(this).parents('table').eq(0), $(this).val());
    });

    if ($('.stepThird').length > 0) {
        if (key == 'custom') {
            $('.stepThird .form').hide();
            $('.stepThird .customForm').show();
        }
    }

    function adjustGamePlanModal(modal) {
        var width = $(window).width();
        if (width < 1006) {
            modal.css({
                "width" : width - 20 + "px"
            });
            modal.find("#ganttChartDiv").css({
                "width" : width - 60 + "px"
            })
        } else {
            modal.css({
                "width" : "1006px"
            });
            modal.find("#ganttChartDiv").css({
                "width" : "946px"
            })
        }
    }

    $('.stepSecond .geryContent .gamePlanButton').click(function() {
        addresscloseModal();
        adjustGamePlanModal($("#gamePlanModal"));
        addressLoadModal('#gamePlanModal', $(window).width() < 1006);
        if ($('#ganttChartDiv').length > 0) recreateChartControl('ganttChartDiv', true);
        var title = $(this).parent().parent().parent().parent().parent().parent().find('.radioBox label').text();
        $('#gamePlanModal h2').text('Game Plan for ' + title + ' - Medium');
        $("#gamePlanModal").data("title", title);
    });

    $('#deleteUserModal .yesbutton').click(function() {
        addresscloseModal();
        addressLoadModal('#deleteConfirmModal');
        $('#notifications tr.userRow td.permCol :checked').closest('tr').remove();
    });

    $(".closeModal,.closebutton").live('click', function() {
        addresscloseModal();
        return true;
    });

    $('.stepSecond .projectItem .radio').click(function() {
        $('.stepSecond .projectItem .projectContainer').removeClass('hover');
        $(this).parent().parent().find('.projectContainer').addClass('hover');
    });

    /** START - User permission management modal **/

    //scroll
    $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');

    //list selected
    $('#addUserModal .addUserForm .addUserList li').live('click', function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    });

    var removeDuplicationEntry = function(entries) {
        var checked = {};
        $.each(entries, function() {
            var username = $(this).html();
            var userId = $(this).attr('id');

            if (checked[userId] == undefined) {
                checked[userId] = username;
            } else {
                $(this).remove();
            }

        });
    }

    // user permission modal - select all
    $('#addUserModal .addUserForm .selectAll').click(function() {
        $('#addUserModal .addUserForm .addUserLeft ul li').addClass('selected');
    });

    // user permission modal - remove all
    $('#addUserModal .addUserForm .removeAll').click(function() {
        $('#addUserModal .addUserForm .addUserLeft ul').append($('#addUserModal .addUserForm .addUserRight ul').html());
        removeDuplicationEntry($('#addUserModal .addUserForm .addUserLeft ul li'));
        $('#addUserModal .addUserForm .addUserRight ul').empty();
        if ($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186) {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237) {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // user permission modal - add user
    $('#addUserModal .addUserForm .addItem').live('click', function() {
        $('#addUserModal .addUserForm .addUserLeft ul li.selected').each(function() {
            $('#addUserModal .addUserForm .addUserRight ul').append ('<li id="' + $(this).attr('id') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        removeDuplicationEntry($('#addUserModal .addUserForm .addUserRight ul li'));
        if ($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186) {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237) {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // user permission modal - remove user
    $('#addUserModal .addUserForm .removeItem').live('click', function() {
        $('#addUserModal .addUserForm .addUserRight ul li.selected').each(function() {
            $('#addUserModal .addUserForm .addUserLeft ul').append('<li id="' + $(this).attr('id') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        removeDuplicationEntry($('#addUserModal .addUserForm .addUserLeft ul li'));
        if ($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186) {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237) {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });


    // STEP 5 - add user permission modal triggers
    $('.stepFifth .geryContent .addUserButton,.stepFifth .geryContent .addMoreUsers,.stepFifth .geryContent .folderIcon').live('click', function() {
        addresscloseModal();
        addressLoadModal('#addUserModal');
        // clear the user search box
        $("#permissionSearchUserBox").val('');

        // load the users in the permission table to the right list
        $('#addUserModal .addUserForm .addUserRight ul').empty();
        $(".checkPermissions .userRow").each(function() {
            var userTag = $(this).find('div.group');
            var userName = $.trim(userTag.html());
            var userId = $.trim(userTag.attr('id')).substring(6);

            $('#addUserModal .addUserForm .addUserRight ul').append('<li id="' + userId + '">' + userName + '</li>');
        });
    });

    var getAddedUser = function() {
        var addedUser = {};

        $('.stepFifth .addUserPlan table .checkPermissions .userRow').each(function(){
            var user = $(this).find("div.group");
            var userName = $.trim(user.html());
            var userId = user.attr('id').substring(6);

            addedUser[userId] = userName;
        });

        return addedUser;
    }

    // Save button
    $('#addUserModal .saveButton').live('click', function() {
        addresscloseModal();
        $('.stepFifth .form').hide();

        // insert selected users
        var template = unescape($("#userRowTemplate").html());
        var addedUser = getAddedUser();
        var userToAdd = {};

        $('#addUserModal .addUserForm .addUserRight ul li').each(function() {

            var html = $.validator.format(template, $.trim($(this).html()), $(this).attr('id'));
            userToAdd[$(this).attr('id')] = $(this).html();

            if(addedUser[$(this).attr('id')] == undefined) {
                // only add these which do not exist in the permission table
                $('.stepFifth .addUserPlan table .checkPermissions').append ('<tr class="userRow">' + html + '</tr>');
            }
        });

        // remove the ones does not exist in the right list
        $('.stepFifth .addUserPlan table .checkPermissions .userRow').each(function(){
            var id = $(this).find("div.group").attr('id').substring(6);
            if(userToAdd[id] == undefined) {
                $(this).remove();
            }
        });



        $('.stepFifth .addUserPlan').show();
        return false;
    });

    // delete Confirm
    $('.stepFifth .geryContent .deleteSelect').live('click', function() {
        addresscloseModal();
        addressLoadModal('#deleteUserModal');
    });

    $('.stepFifth .geryContent .deleteIcon').live('click', function() {
        addresscloseModal();
        addressLoadModal('#deleteConfirmModal');
        $(this).closest('tr').remove();
    });

    // search user feature
    $("#addUserModal .searchUserHandle").click(function() {
        // get search text first
        var searchText = $.trim($("#permissionSearchUserBox").val());

        var request = {"searchText":searchText};

        $.ajax({
            type: 'GET',
            url:  "getUser",
            data: request,
            cache: false,
            dataType: 'json',
            async : false,
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        // insert into the left list
                        $('#addUserModal .addUserForm .addUserLeft ul').empty();
                        $.each(result, function() {
                            $('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
                            $('#addUserModal .addUserForm .addUserLeft ul').append('<li id="' + this.userId + '">' + this.handle + '</li>');
                        })


                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    })
            }
        });

    });


    /** END - User permission management modal **/


    $('#alertModal .step6Button').live('click', function() {
         createNewProject();
    });



    if ($.browser.msie && ($.browser.version == "7.0")) {
        $('.stepForth .form dl.radioList dd .radio').css('margin-top', '-4px');
        $('.stepForth .form dl.radioList dd').css('margin-left', '28px');
        $('.stepSecond .radioBox').css('margin-left', '0px');
        $('.stepSecond .radioBox .radio').css('margin-top', '-3px');
        $('.selectUserCheck,.selectUser').css('margin-top', '-3px');
    }

    //all
    $('.applyForAll td:eq(1) .selectUser').click(function() {
        if ($(this).attr('checked')) {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(1) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(1) .selectUser').attr('checked', '');
            });
        }
    });

    $('.applyForAll td:eq(2) .selectUser').click(function() {
        if ($(this).attr('checked')) {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(2) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(2) .selectUser').attr('checked', '');
            });
        }
    });

    $('.applyForAll td:eq(3) .selectUser').click(function() {
        if ($(this).attr('checked')) {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(3) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.checkPermissions tr').each(function() {
                $(this).find('td:eq(3) .selectUser').attr('checked', '');
            });
        }
    });


    /*$('.stepForth .form dl.radioList dd span').click(function(){
     if($(this).parent().find('.radio').attr('checked')){
     $(this).parent().find('.radio').attr('checked','');
     }else{
     $(this).parent().find('.radio').attr('checked','checked');
     }
     });*/

    if ($.browser.msie && ($.browser.version == "8.0")) {
        $('#notifications .deleteSelect,#notifications .addMoreUsers').css('margin-top', '3px');
    }

    $('.projectContainer').click(function() {
        // only apply to custom project now
        if ($(this).find("div").hasClass("custom")) {

            $('.projectContainer').removeClass('hover');
            $(this).addClass('hover');
            // $('.projectList').find('.radio').attr('checked', '');
            $(this).parent().find('.radio').attr('checked', 'checked');
        }
    });

    $('.projectItem label').click(function() {
        if ($(this).html() == 'Custom') {

            if ($(this).parent().find('.radio').attr('checked')) {
                $(this).parent().find('.radio').attr('checked', '');
            } else {
                $(this).parent().find('.radio').attr('checked', 'checked');
            }
            $('.projectContainer').removeClass('hover');
            $(this).parent().parent().find('.projectContainer').addClass('hover');
        }
    });


    /* Added JS code for Cockpit "Start a New Project" Update Prototype - http://apps.topcoder.com/wiki/pages/viewpage.action?pageId=64258944 */
    // input tips
    $(".stepFirst .projectName input.text").inputTips("Enter your Project Name here");
    // input limited text
    $(".stepFirst .projectName input.text").limitedText({
        "max" : 60
    });

    $(".stepFirst .descProject textarea").inputTips("");
    // input limited text
    $(".stepFirst .descProject textarea").limitedText({
        "max" : 255
    });


    // validate step 1
    function validate(box) {
        var value = $.trim(box.val());
        var tips = box.data("tips") ? box.data("tips") : "";
        if (value == "" || value == tips) {
            box.parent().find(".message").append('<span class="errorText">This field cannot be left empty.</span>');
            box.addClass("error").val("");
            box.after('<span class="errorIcon"></span>');
            return false;
        }
        return true;
    }

    function removeError(box) {
        box.parent().find(".errorText,.errorIcon").remove();
        box.removeClass("error");
    }

    $(".stepFirst .projectName input.text,.stepFirst .descProject textarea").keyup(function() {
        removeError($(this));
    });

    // noteMask
    $(".noteMask dt a").click(function() {
        $(this).blur();
        var mask = $(this).parents(".noteMask");
        if (mask.hasClass("collapse")) {
            $(this).text("Hide")
        } else {
            $(this).text("Show")
        }
        mask.toggleClass("collapse");
    });

    // step 4
    $(".stepForth .radioList input.radio").click(function() {
        if ($(this).val() == "yes") {
            $(".stepForth .chooseCopilot .info").hide();
            $(".stepForth .chooseCopilot .selection").show();
        } else {
            $(".stepForth .chooseCopilot .info").show();
            $(".stepForth .chooseCopilot .selection").hide();
        }
    })
    // choose copilot
    $(".stepForth .chooseYourCopilot").click(function() {
        addresscloseModal();
        addressLoadModal('#addUserModal');
    })

    $("#selectionConfirmationModal dd a").attr("target", "_blank");
    $(".chooseCopilotModal .buttonArea a.saveButton1").click(function() {
        addresscloseModal();
        addressLoadModal('#selectionConfirmationModal');
    })
    $(".stepForth .createPosting").click(function() {
        addresscloseModal();
        addressLoadModal('#createPostingModal');
    })

    // full screen Gameplan
    function adjustFullScreen(modal) {
        var width = $(window).width();
        var height = $(window).height();
        /* modal.css({
         "width" : width + "px"
         }); */
        modal.find("#ganttChartBigDiv").css({
            "width" : width - 20 + "px",
            "min-height" : height - 80 + "px"
        });
        modal.show();
        $("#wrapper").hide();
        $("#new-modal").css({
            'margin': "0",
            "position":"absolute"
        });
    }

    $("#gamePlanModal a.whiteBtn").click(function() {
        addresscloseModal();
        adjustFullScreen($("#gamePlanBigModal"));
        if ($('#ganttChartDiv').length > 0)  recreateChartControl('ganttChartBigDiv', true);
        //$('#gamePlanBigModal h2').text('Game Plan for ' +  $("#gamePlanModal").data("title") +' - Full Screen');
        $("#gamePlanBigModal .taskPanel").parent().css({
            "height":"auto",
            "overflow" : "auto"
        });
    })
    $("#gamePlanBigModal a.button6").click(function() {
        $("#gamePlanBigModal").hide();
        $("#wrapper").show();
    })


    $(window).resize(function() {
        if (!$("#gamePlanBigModal").is(":hidden")) {
            $("#gamePlanModal a.whiteBtn").trigger("click");
        }
        if (!$("#gamePlanModal").is(":hidden") && $("#gamePlanModal").length > 0) {
            addresscloseModal();
            adjustGamePlanModal($("#gamePlanModal"));
            addressLoadModal('#gamePlanModal', $(window).width() < 1006);
            if ($('#ganttChartDiv').length > 0) recreateChartControl('ganttChartDiv', true);
        }
    });


    $("#gamePlanBigModal .buttonArea a.button6,#gamePlanBigModal .closeModal").click(function() {
        $("#new-modal").css({
            "position":"fixed"
        });
    })

    // step 2 cookie function
    $("#customConfirmModal .buttonArea a.button6").click(function() {
        // $.cookie("step2", "custom");
        stepsChoices['step2'] = 'custom';
        goCreateProjectStep(4); // skip the page 3
    });

    // step 4 cookie function
    $("#selectionConfirmationModal a.confirmBtn").click(function() {
        // $.cookie("step4", "chooseCopilot");
        stepsChoices['step4'] = 'chooseCopilot';
    })
    $("#createPostingModal .buttonArea a.button6").click(function() {
        // $.cookie("step4", "createCopilot");
        stepsChoices['step4'] = 'createCopilot';
    })


    $("#createProjectConfirm .step6Button").live('click', createNewProject);

    createChartControl('ganttChartDiv', true);

    $(window).resize(function() {
        if ($(".stepThird #ganttChartDiv").length > 0) {
            recreateChartControl('ganttChartDiv', true);
        }
    });
});
