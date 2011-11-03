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
    try{
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
    } catch (e) {}
}

/**
 * Checks whether the project is presentation project.
 */
function isPresentationProject() {
    return stepsChoices['step2'] == 'Presentation Project Type';
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

    $(".newProjectStep").hide();
    if (isPresentationProject() && (stepNumber == 3 || stepNumber ==  4 || stepNumber == 5)) {
        $("#newPPTProjectStep" + stepNumber).show();
    } else {
        $("#newProjectStep" + stepNumber).show();
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

    if (stepNumber == 3 && isPresentationProject()) {
        //new add line
        $('.stepThird .tfooter td .addMoreButtonBox .text').css('width',$('.stepThird .tfooter td .addMoreButtonBox').width() - 142);
    }
    if (stepNumber == 4 && !isPresentationProject()) {
        if(stepsChoices['step4'] == 'chooseCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['step4'] == 'createCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['step4'] == 'noCopilot') {
            $("#newProjectStep4 .radioNo").attr("checked", "checked");
        }
    }
    if (stepNumber == 5 && isPresentationProject()) {
        $('#selectFileDescription').width($('.addMoreParts .topRowWrapper').width() - 90);
        $('#customFileDescription').width($('#selectFileDescription').width() - 22);
        $('.stepFifth .browserInput').width($('.stepFifth .browserParts .topRowWrapper').width() - 26);
        $('#descriptionInput').width($('.stepFifth .otherTypeAreaInputBox').width() - 450);
    }
};

var generateConfirmationPage = function(projectId, projectName) {
    // determine what to show in the final confirmation page
    if ($(".stepSixth").length > 0) {
        var isPPT = isPresentationProject();
        var step2 = stepsChoices["step2"];
        var step4 = stepsChoices["step4"];
        var step2Size;

        // add project name
        var text = $(".stepSixth .step1 p").html();
        if (isPPT) {
            text = 'Create Presentation Project <span class="newProjectName"> ' + projectName + "</span>";
        } else {
            text = text + ' <span class="newProjectName"> ' + projectName + "</span>";
        }
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
            // fill in the copilots
            var copilots = stepsChoices["copilots"];

            var names = "";
            $.each(copilots, function(index, value) {
                if (index > 0) names += ", ";
                names += value.copilotHandle;
            });
            $(".stepSixth .completedSteps .step2.selectStep .smallText").html(names);
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
        
        if (isPPT) {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .pptStep").show();
        }
    }

};

/**
 * Encode a html content.
 */
function htmlEncode(html) {
    if (html.length == 0) return "";
    html = html.replace(/&/g, "&amp;");
    html = html.replace(/</g, "&lt;");
    html = html.replace(/>/g, "&gt;");
    html = html.replace(/ /g, "&nbsp;");
    html = html.replace(/\'/g, "'");
    html = html.replace(/\"/g, "&quot;");
    html = html.replace(/\n/g, "<br/>");
    return html;
}

/**
 * Create the public description content for copilot contest of Presentation Project.
 */
function createCopilotContestPublicDescription() {
    var content = "";
    var projectName = htmlEncode($("#newProjectName").val());
    var projectDescription = htmlEncode($("#newProjectDescription").val());
    var style = $("input[name='styleSelectRadio']:checked").next().html();
    var notes = htmlEncode($(".stepForth .notePresentation").val());
    var pageLength = $("input[name='pageLengthSetting']:checked").next().html();
    var audiences = "";
    $(".stepThird .speciealForm .selectRadioBox .radio:checked").each(function() {
        if (audiences.length > 0) {
            audiences += ', ';
        }
        audiences += htmlEncode($(this).attr('name'));
    });
    var deliverableTypes = "";
    $(".stepFifth .deliverablesTypeItem input[type='checkbox']:checked").each(function() {
        deliverableTypes += $(this).next().html() + '<br/>';
    });
    $(".stepFifth .speciealForm .otherTypeTableWrapper tbody tr").each(function() {
        if ($(this).hasClass("tfooter")) return;
        var ext = $(".strExtension", $(this)).html();
        var desc = $(".strDesc", $(this)).html();
        deliverableTypes += ext + ' : ' + desc + '<br/>';
    });
    
    content += '<p><strong>Project Name:</strong> ' + projectName + '</p>';
    content += '<p><strong>Project Overview:</strong><br/>' + projectDescription + '</p>';
    content += '<p><strong>Which are your preferred style for the presentation?</strong><br/>' + style + '</p>';
    content += '<p><strong>Any notes on presentation style?</strong><br/>' + notes + '</p>';
    content += '<p><strong>Project Length:</strong> ' + pageLength + '</p>';
    content += '<p><strong>Target Audience:</strong><br/>' + audiences + '</p>';
    content += '<p><strong>Deliverable Type:</strong><br/>' + deliverableTypes + '</p>';
    return content;
}

/**
 * Create request data to create draft copilot conetst for Presentation Project.
 */
function createDraftContestRequestForPresentationProject(projectName) {
    mainWidget.competitionType = "SOFTWARE";
    
    mainWidget.softwareCompetition.projectHeader.projectCategory = {};
    mainWidget.softwareCompetition.projectHeader.projectCategory.id = 29;
    mainWidget.softwareCompetition.projectHeader.projectCategory.name = "Copilot Posting";
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType = {};
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = 2;
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = "Application";
   
    mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = false;
    mainWidget.softwareCompetition.assetDTO.directjsDesignId = -1;   
      
    mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    
    var contestName = projectName + " Copilot Opportunity";
    mainWidget.softwareCompetition.assetDTO.name = contestName;
    mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = projectName;
    mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = createCopilotContestPublicDescription();
    mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription = "";
    mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);
    
    var projectHeader = mainWidget.softwareCompetition.projectHeader;
    var amount = 150.0;
    projectHeader.setFirstPlaceCost(amount);
    // set all prize to 0 except first place cost
    projectHeader.setSecondPlaceCost(0);
    projectHeader.setReviewCost(0);
    projectHeader.setReliabilityBonusCost(0);
    projectHeader.setDRPoints(0);
    projectHeader.setMilestoneBonusCost(0);
    projectHeader.setAdminFee(0);
    projectHeader.setSpecReviewCost(0);
    
    var prizes = [];
    prizes.push(new com.topcoder.direct.Prize(1, amount, CONTEST_PRIZE_TYPE_ID, 1));
    mainWidget.softwareCompetition.projectHeader.prizes = prizes;
    mainWidget.softwareCompetition.projectHeader.setBillingProject(0);
    delete mainWidget.softwareCompetition.assetDTO.productionDate;
    
    var request = saveAsDraftRequestSoftware();
    request['presentationProject'] = true;
    return request;
}

// final step
var createNewProject = function() {
    var isPPT = isPresentationProject();
    // get project name and project description
    var projectName = $("#newProjectName").val();
    var projectDescription = $("#newProjectDescription").val();

    var permissions = [];
    if (!isPPT) {
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
    }

    var request = {};
    if (isPPT) {
        request = createDraftContestRequestForPresentationProject(projectName);
    } else {
        var step4 = stepsChoices["step4"];

        if(step4 == 'chooseCopilot') {
            var copilotIds = [];
            $.each(stepsChoices["copilots"], function(index, value) {
                copilotIds[copilotIds.length] = value.copilotProfileId;
            });
            request['copilotIds'] = copilotIds;
            request['createCopilotPosting'] = false;

        } else if (step4 == "createCopilot") {

            request['createCopilotPosting'] = true;

        }
    }
    request['projectName'] = projectName;
    request['projectDescription'] = projectDescription;
    request['permissions'] = permissions;

    modalPreloader();

    $.ajax({
        type: 'POST',
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
    $(".notePresentation").css("resize", "none");

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
        //new add line
        $('.stepThird .tfooter td .addMoreButtonBox .text').css('width',($('.stepThird .tfooter td .addMoreButtonBox').width() - 142));
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
            var pType = jPar.find('label').text();
            if (pType == 'Custom') {

                // if custom, show the custom confirm modal
                addresscloseModal();
                addressLoadModal('#customConfirmModal');

                // $.cookie("step2-size", null);
                stepsChoices['step2'] = 'custom';

            } else if (pType == 'Presentation Project Type') {
                stepsChoices['step2'] = 'Presentation Project Type';
                goCreateProjectStep(3);
            } else {
                // $.cookie("step2", "gameplan");
                // $.cookie("step2-size", jPar.parent().find(".selProjSize").val());
                // window.location.href = 'start-a-new-project-step-3.html';
                showErrors("Does not support now, will be implemented in next assembly.");
            }
        }
    });

    $('.stepForth .geryContent .nextStepButton').click(function() {
        if (isPresentationProject()) {
            var refuse = false;
            var val = $.trim($('.stepForth .notePresentation').val());
            if($('.stepForth .speciealForm .styleItem :radio:checked').length == 0){
                $('.stepForth .errorStatusTips').css('display','inline');
                refuse = true;
            }
            if(val.length == 0){
                $('.stepForth .textFieldWrapper .notePresentation').addClass('errorInput');
                $('.stepForth .textFieldWrapper .errorText, .stepForth .textFieldWrapper .errorIcon').show();
                refuse = true;
            }
            if(refuse) return false;
            goCreateProjectStep(5);
        } else {
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
        if (isPresentationProject()) {
            var refuse = false;
            if($('.stepFifth .speciealForm .deliverablesTypeItem :checked').length == 0){
                $('.stepFifth .errorStatusTips').css('display','inline');
                refuse = true;
            }
            if(refuse) return false;
            addresscloseModal();
            addressLoadModal('#createProjectConfirm');
        } else {
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
        addresscloseModal();
        if (isPresentationProject()) {
            createNewProject();
        } else {
            addressLoadModal('#createProjectConfirm');
        }
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
        if (!$("input[type='radio']", $(this).parent()).attr("disabled")) {

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
    });
    
        /** Copilot management widget codes **/

    var request = {};
    request['directProjectId'] = tcDirectProjectId;
     // cache the project copilots initialization data
    var widgetResult;

    // mapping of copilot profile id to
    var copilotMapping = {};

    // scroll
    $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');

    // list selected
    $('#copilotManageModal .addUserForm .addUserList li').live('click', function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    });

    // select all
    $('#copilotManageModal .addUserForm .selectAll').click(function() {
        $('#copilotManageModal .addUserForm .addUserLeft ul li').filter(":visible").addClass('selected');
    });

    // remove all
    $('#copilotManageModal .addUserForm .removeAll').click(function() {
        $('#copilotManageModal .addUserForm .addUserLeft ul').append($('#copilotManageModal .addUserForm .addUserRight ul').html());
        $('#copilotManageModal .addUserForm .addUserRight ul').empty();
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // add item
    $('#copilotManageModal .addUserForm .addItem').live('click', function() {
        $("#copilotManageModal .errorMessage").html('');
        $('#copilotManageModal .addUserForm .addUserLeft ul li.selected').each(function() {
            $('#copilotManageModal .addUserForm .addUserRight ul').append('<li name="' + $(this).attr('name') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // remove item
    $('#copilotManageModal .addUserForm .removeItem').live('click', function() {
        $('#copilotManageModal .addUserForm .addUserRight ul li.selected').each(function() {
            $('#copilotManageModal .addUserForm .addUserLeft ul').append('<li name="' + $(this).attr('name') + '" id="' + $(this).attr('id') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // handle the copilot management widget search feature
    $("#copilotManageModal .searchBox a").live('click', function() {
        var searchText = $("#copilotManageModal .searchBox input").val().toLowerCase();
        var leftList = $("#copilotManageModal .addUserLeft .addUserList ul li");

        leftList.each(function() {

            $(this).html(copilotMapping[$(this).attr('name')]);

            $(this).show();
        });

        if ($.trim(searchText).length == 0) {
            return;
        } else {
            leftList.each(function() {
                var copilotHandle = $.trim($(this).html());
                var copilotHandleLower = copilotHandle.toLowerCase();
                var index = copilotHandleLower.indexOf(searchText);
                if (index == -1) {
                    // hide
                    $(this).hide();
                } else {
                    var highlighted = copilotHandle.substring(0, index - 1) + '<strong>' +
                        copilotHandle.substring(index, index + searchText.length) +
                        '</strong>' + copilotHandle.substring(index + searchText.length);
                    $(this).html(highlighted);
                }

            });
        }
    });

    // initialize the project copilots management widget
    var initializeWidget = function(result) {
        var leftList = $("#copilotManageModal .addUserLeft .addUserList ul").html('');
        var rightList = $("#copilotManageModal .addUserRight .addUserList ul").html('');

        $.each(result.allCopilots, function(k, v) {
            leftList.append('<li name="' + k + '">' + v + '</li>');
            copilotMapping[k] = v;
        });

        $.each(result.projectCopilots, function(k, v) {
            rightList.append('<li name="' + k + '" id="copilotProject_' + v.copilotProjectId + '">' + v.handle + '</li>');
            copilotMapping[k] = v.handle;
        });

        widgetResult = result;
    }

    var errorHandler = function(error) {
        showErrors(error);
    }

    // loads the copilots data for project copilots management widget
    $.ajax({
        type : 'get',
        url : 'getProjectCopilotsWidgetData',
        cache : false,
        data : request,
        dataType : 'json',
        success : function(result) {
            handleJsonResult(result, initializeWidget, errorHandler)
        },
        error: function(result) {
            showErrors("Fail to load the project copilots data");
        }
    });

    // save the project copilots information
    $('#copilotManageModal .saveButton').live('click', function() {

        var rightList = $("#copilotManageModal .addUserRight .addUserList ul li");

        var copilotsData = [];

         // check add first
        $.each(rightList, function() {
            if (!$(this).attr('id')) {

                var copilotProfileId = $(this).attr('name');
                var copilotHandle = $.trim($(this).html());

                copilotsData[copilotsData.length] = {
                    copilotProfileId : parseInt(copilotProfileId),
                    copilotHandle : copilotHandle
                };

            }
        });

        if (copilotsData.length == 0) {
            $("#copilotManageModal .errorMessage").html("Please choose your copilot and add to the right panel first.");
            return false;
        } else {

            $("#selectionConfirmationModal .modalContainer dd").remove();

            var userIdsMap = widgetResult.userIdsMap;

            $.each(copilotsData, function(index, value){
                $("#selectionConfirmationModal .modalContainer dt").append('<dd><span>' + value.copilotHandle + '</span><a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr='
                    + userIdsMap[value.copilotProfileId] + '">View Copilot Profile</a></dd>');
            });

            $("#selectionConfirmationModal .modalContainer dd:first").css("padding-top", "5px");

            stepsChoices['copilots'] = copilotsData;

        }

        modalAllClose();
        addresscloseModal();
        addressLoadModal('#selectionConfirmationModal');
    });


    // close button
    $('#copilotManageModal .cancelButton').live('click', function() {
        modalAllClose();
        if (widgetResult != null) {
            initializeWidget(widgetResult);
        }
        return false;
    });

    /** end copilot management widget codes **/

    // step 4 cookie function
    $("#selectionConfirmationModal a.confirmBtn").click(function() {
        // $.cookie("step4", "chooseCopilot");
        stepsChoices['step4'] = 'chooseCopilot';
        goCreateProjectStep(5);
    })
    $("#createPostingModal .buttonArea a.button6").click(function() {
        // $.cookie("step4", "createCopilot");
        stepsChoices['step4'] = 'createCopilot';
        goCreateProjectStep(5);
    });
    
    // choose copilot
    $(".stepForth .chooseYourCopilot").click(function() {
        if (widgetResult == undefined) {
            showErrors("Please wait for the loading of the copilot data");
        }

        if(stepsChoices['step4'] != 'chooseCopilot' || stepsChoices['copilots'].length == 0) {
            initializeWidget(widgetResult);
        }

        $("#copilotManageModal .searchBox input").val('');
        $("#copilotManageModal .errorMessage").html('');
        modalLoad('#copilotManageModal');
        return false;
    })

    $("#selectionConfirmationModal dd a").attr("target", "_blank");
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

    $("#createProjectConfirm .step6Button").live('click', function() {
        addresscloseModal();
        createNewProject();
    });
    
    // check custom project plan by default for now
    $("#customGamePlanRadio").click();
    
    // presentation project step 3 : Add more audience type
    //add more button 
    $('.stepThird .addMoreButton').click(function(){
        var val = $.trim($(this).siblings('input').val());
        var cache,audienceName,existFlat = false;
        
        $('.stepThird .speciealForm table .labelTd').each(function(){
            if(val == $.trim($(this).text())){
                existFlat = true;
            }
        });
        $('.stepThird .addMoreButtonBox .errorNameExist').hide();
        $('.stepThird .addMoreButtonBox .errorStatusTips').hide();
        if(val.length == 0){
            $('.stepThird .addMoreButtonBox .errorStatusTips,.addMoreButtonBox .errorStatus div').show();
            $('.stepThird .addMoreButtonBox input').addClass('errorInput');
        }else{
            if(!existFlat){
                val = htmlEncode(val);
                cache = $('<tr/>');
                cache.append('<td class="labelTd">'+val+'</td><td><div class="selectRadioBox"><input type="checkbox" name="'+val+'" class="radio" checked="checked"/></div></td>');
                cache.insertBefore($('.stepThird .speciealForm table .tfooter'));
                $('.stepThird .topNotice .redError').hide();
                $('.stepThird .addMoreButtonBox input').val('');
            }else{
                $('.stepThird .addMoreButtonBox input').addClass('errorInput');
                $('.stepThird .addMoreButtonBox .errorNameExist,.addMoreButtonBox .errorStatus div').show();
            }
            
        }
    });
    //focus text field remove the error status 
    $('.stepThird .addMoreButtonBox :text').focus(function(){
        $('.stepThird .addMoreButtonBox .errorStatusTips,.addMoreButtonBox .errorStatus div,.addMoreButtonBox .errorNameExist').hide();
        $('.stepThird .addMoreButtonBox input').removeClass('errorInput')
    });
    $(".stepThird .selectRadioBox .radio").click(function() {
        if ($(".stepThird .selectRadioBox .radio:checked").length > 0) {
            $('.stepThird .topNotice .redError').hide();
        }
    });
    $(".stepThird .nextStepButton").click(function() {
        if (isPresentationProject()) {
            $('.topNotice .redError').hide();
            if ($(".stepThird .selectRadioBox .radio:checked").length == 0) {
                $('.stepThird .topNotice .redError').show();
                return false;
            }
            goCreateProjectStep(4);
        } else {
            // TODO: will be implemented in next assemblies
        }
    });
    // presentation project step 4 : Presentation Style
    $('.stepForth .styleItem .radio').click(function(){
        $('.stepForth .styleItem').removeClass('itemHover');
        $(this).parent().parent().addClass('itemHover');
    });
    // click box for add hover status
    $('.stepForth .styleContainer').click(function(){
        $('.stepForth .styleItem').removeClass('itemHover');
        $(this).parent().addClass('itemHover');    
        $('.stepForth .styleSelectList').find('.radio').attr('checked','');
        $(this).parent().find('.radio').attr('checked','checked');
    });
    
    // click label for add hover status
    $('.stepForth .styleItem label').click(function(){
        $(this).parent().find('.radio').attr('checked','checked');    
        $('.stepForth .styleItem').removeClass('itemHover');
        $(this).parent().parent().addClass('itemHover');
    });
    
    // add focus for radio when clicking the label
    $('.stepForth .pageLengthSetArea label').live('click',function(){
        $(this).prev(':radio').trigger('click');
    });
        
    // remove error status
    $('.stepForth .speciealForm .styleItem').click(function(){
        $('.errorStatusTips').hide();
    });
    
    //remove error status
    $('.stepForth .notePresentation').focus(function(){
        $('.stepForth .textFieldWrapper .errorText, .stepForth .textFieldWrapper .errorIcon').hide();
        $(this).removeClass('errorInput');
    });
    
    // presentation project step 5: Files
    $(".stepFifth .noMediaAsset").show();
    
    $(window).resize(function(){
        $('#selectFileDescription').width($('.addMoreParts .topRowWrapper').width() - 90);
        $('#customFileDescription').width($('#selectFileDescription').width() - 22);
        $('.stepFifth .browserInput').width($('.stepFifth .browserParts .topRowWrapper').width() - 26);
        $('#descriptionInput').width($('.stepFifth .otherTypeAreaInputBox').width() - 450);
    });
    
    //set opacity for #uploadFile button
    $('#uploadFile').css('opacity', '0.01');
    if($.browser.msie){
        $('#uploadFile').css({left:'auto',right:'0'});
        setInterval(function() {
            if ($('#uploadFile').length > 0) {
                var filename = $('#uploadFile').val();
                if (filename.length > 0)
                $('.stepFifth .browserInput').val(filename);
            }
        }, 500);
    } else {
        $('#uploadFile').change(function(){
            $('.stepFifth .browserInput').val($(this).val());
        });
        $('#browserButton,.browserInput').click(function(){
            $('#uploadFile').trigger('click');
        });
    }
    
    // uploader
    var swUploader =
        new AjaxUpload(null, {
            action: ctx + '/launch/documentUpload',
            name: 'document',
            responseType: 'json',
            onSubmit: function(file , ext) {
                //document
                swCurrentDocument['fileName'] = file;

                swUploader.setData({
                    studio:false,
                    contestFileDescription:swCurrentDocument['description'],
                    documentTypeId:swCurrentDocument['documentTypeId']
                });

                modalPreloader();
            },
            onComplete: function(file, jsonResult) { handleJsonResult(jsonResult,
                    function(result) {
                        var documentId = result.documentId;
                        swCurrentDocument['documentId'] = documentId;
                        swDocuments.push(swCurrentDocument);
                        
                        var cache = $('<tr class="itemLine"></tr>');
                        cache.append('<td class="firstTd"><a href="#">'+file+'</a><span class="description">('+htmlEncode(swCurrentDocument['description'])+')</span></td><td class="deleteTd"><a href="javascript:;" title="delete" class="deleteRowIcon" rel="' + documentId + '"></a></td>');
                        cache.insertBefore($('.stepFifth .mediaAssetTableWrapper table .tfooter'));
                        $('.stepFifth .mediaAssetTableWrapper .noMediaAsset').hide();
                        $(".stepFifth .browserWrapper").append('<input id="uploadFile" type="file" name="document" />');
                        if(!$.browser.msie){
                            $('#uploadFile').change(function(){
                                $('.stepFifth .browserInput').val($(this).val());
                            });
                        }
                        $('#uploadFile').click(function(){
                            $('.stepFifth .browserInput').removeClass('errorInput');
                            $('.stepFifth .browserParts .topRowWrapper .errorText').hide();
                            $('.stepFifth .browserParts .topRowWrapper .errorIcon').hide();
                        });
                        $("#uploadFile").hover(function(){
                            $("#browserButton").css("background-position", "left -72px");
                            $("#browserButton .btnR").css("background-position", "right -120px");
                            $("#browserButton .btnC").css("background-position", "left -96px");
                        },function() {
                            $("#browserButton").css("background-position", "left top");
                            $("#browserButton .btnR").css("background-position", "right -48px");
                            $("#browserButton .btnC").css("background-position", "left -24px");
                        });
                        //set opacity for #uploadFile button
                        $('#uploadFile').css('opacity', '0.01');
                        if($.browser.msie){
                            $('#uploadFile').css({left:'auto',right:'0'});
                        }
                        $(".stepFifth .browserInput").val("File Name");
                        $("#selectFileDescription").val(0);
                        $("#customFileDescription").val("Custom File Description");
                        swCurrentDocument = {};
                        
                        modalClose();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                        modalClose();
                    });
            }
        }, false);
    
    //add more button and validation
    $('.stepFifth .mediaAssetTableWrapper .addMoreButton').click(function(){
        var fileVal = $('.stepFifth .browserInput').val();
        var descriptVal = $('#selectFileDescription').val();
        var descriptTextVal = $('#customFileDescription').val();
        var description;
        var refuse = false;
        var cache;
        
        if(fileVal.length == 0 || fileVal == 'File Name'){
            $('.stepFifth .browserInput').addClass('errorInput');
            $('.stepFifth .browserParts .topRowWrapper .errorText').show();
            $('.stepFifth .browserParts .topRowWrapper .errorIcon').show();
            refuse = true;
        }
        if(descriptVal == 0){
            $('#selectFileDescription').parent().addClass('errorInput');
            $('#selectFileDescription').addClass('errorInput');
            $('.stepFifth .addMoreParts .subButtonBox .errorText').show();
            $('.stepFifth .addMoreParts .subButtonBox .errorIcon').show();
            refuse = true;
        }
        if(descriptTextVal.length == 0 || descriptTextVal == 'Custom File Description') {
            $('#customFileDescription').addClass('errorInput');
            $('.stepFifth .addMoreParts .subButtonBox .errorText').show();
            $('.stepFifth .addMoreParts .subButtonBox .errorIcon').show();
            refuse = true;
        }
        
        if(!refuse){
            description = descriptVal + " " + descriptTextVal;
            swUploader.setInput($("#uploadFile").get(0));
            swCurrentDocument['description'] = description;
            swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
            swUploader.submit();
        }else{
            return false;
        }
    });
    
    //upload
    $('#uploadFile').click(function(){
        $('.stepFifth .browserInput').removeClass('errorInput');
        $('.stepFifth .browserParts .topRowWrapper .errorText').hide();
        $('.stepFifth .browserParts .topRowWrapper .errorIcon').hide();
    });
    
    //remove error status
    $('#selectFileDescription').focus(function(){
        $('#selectFileDescription').parent().removeClass('errorInput');
        $('#selectFileDescription').removeClass('errorInput');
        if (!$('#selectFileDescription').hasClass('errorInput') && !$('#customFileDescription').hasClass('errorInput')) {
            $('.stepFifth .addMoreParts .subButtonBox .errorText').hide();
            $('.stepFifth .addMoreParts .subButtonBox .errorIcon').hide();
        }
    });
    
    //remove error status and focus, blur
    $('#customFileDescription')
        .focus(function(){
            $('#customFileDescription').removeClass('errorInput');
            if (!$('#selectFileDescription').hasClass('errorInput') && !$('#customFileDescription').hasClass('errorInput')) {
                $('.stepFifth .addMoreParts .subButtonBox .errorText').hide();
                $('.stepFifth .addMoreParts .subButtonBox .errorIcon').hide();
            }
            if($(this).val() == 'Custom File Description'){
                $(this).val("");
            }
        })
        .blur(function(){
            if($(this).val().length == 0){
                $(this).val("Custom File Description");
            }
        });
    
    //delete button 
    $(".stepFifth .otherTypeTableWrapper .deleteRowIcon").live("click",function(){
        $(this).parent().parent().remove();
    });
    $('.stepFifth .mediaAssetTableWrapper .deleteRowIcon').live('click',function(){
        var row = $(this);
        var documentId = row.attr("rel");
        var doc = $.grep(swDocuments, function(doc,i){
            return doc.documentId == documentId;
        })[0];
        $.ajax({
            type: 'POST',
            url:  ctx+"/launch/removeDocument",
            data: {
                documentId: documentId,
                studio: false
            },
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        var documentId = result.documentId;
                        $.each(swDocuments, function(i,doc) {
                            if(doc && doc.documentId == documentId) {
                                swDocuments.splice(i,1);
                            }
                        });
                        if($('.stepFifth .mediaAssetTableWrapper .itemLine').length == 1){
                            $('.stepFifth .mediaAssetTableWrapper .noMediaAsset').show();
                        }else{
                            $('.stepFifth .mediaAssetTableWrapper .noMediaAsset').hide();
                        }
                        row.parent().parent().remove();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    }
                );
            }
        });
    });
    
    //add more file type
    $('.stepFifth .otherTypeAreaInputBox .addFileType').click(function(){
        var fileVal = $.trim($('#fileType').val());
        var extensionDescriptVal = $.trim($('#descriptionInput').val());
        var refuse = false;
        var cache;
        if(fileVal.length == 0){
            $('#fileType').addClass('errorInput');
            $('.stepFifth .otherTypeAreaInputBox .fileTypeWrapper .errorText').show();
            $('.stepFifth .otherTypeAreaInputBox .fileTypeWrapper .errorIcon').show();
            refuse = true;
        }
        if(extensionDescriptVal.length == 0){
            $('#descriptionInput').addClass('errorInput');
            $('.stepFifth .otherTypeAreaInputBox .descriptionInputWrapper .errorText').show();
            $('.stepFifth .otherTypeAreaInputBox .descriptionInputWrapper .errorIcon').show();
            refuse = true;
        }
        if(!refuse){
            fileVal = htmlEncode(fileVal);
            extensionDescriptVal = htmlEncode(extensionDescriptVal);
            cache = $('<tr/>');
            cache.append('<td class="leftTd"><div class="mediaRow"><span class="rowExtension"><strong>Extension:</strong><span class="strExtension">.'+fileVal+'</span><strong class="textLabel">Description:</strong><span class="strDesc">'+extensionDescriptVal+'</span></span></div></td><td class="deleteTd"><a href="javascript:;" title="delete" class="deleteRowIcon"></a></td>');
            cache.insertBefore($('.stepFifth .otherTypeTableWrapper table .tfooter'));
            $('#fileType').val("");
            $('#descriptionInput').val("");
        }else{
            return false;
        }
        
    });
    
    //remove error status
    $('#fileType,#descriptionInput').focus(function(){
        $(this).removeClass('errorInput');
        $(this).siblings('.errorIcon').hide();
        $(this).siblings('.errorText').hide();
    });
    
    //add hover status for deliverablesTypeItem
    $('.stepFifth .deliverablesTypeItem .typeContainer').click(function(){
        if($(this).parent().find(':checkbox').attr('checked')){
            $(this).parent().removeClass('itemHover');    
            $(this).parent().find(':checkbox').attr('checked','');
        }else{
            $(this).parent().addClass('itemHover');    
            $(this).parent().find(':checkbox').attr('checked','checked');
            $('.stepFifth .errorStatusTips').css('display','none');
        }
    });
    
    //add hover status for deliverablesTypeItem
    $('.stepFifth .deliverablesTypeItem label').click(function(){
        
        if($(this).parent().find(':checkbox').attr('checked')){
            $(this).parent().parent().removeClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','');
        }else{
            $(this).parent().parent().addClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','checked');
            $('.stepFifth .errorStatusTips').css('display','none');
        }
    });
    
    //validation for check box
    $('.stepFifth .deliverablesTypeItem :checkbox').click(function(){
        
        if($(this).attr('checked')){
            $(this).parent().parent().addClass('itemHover');
            $('.stepFifth .errorStatusTips').css('display','none');
        }else{
            $(this).parent().parent().removeClass('itemHover');
        }
    });
    
    createChartControl('ganttChartDiv', true);

    $(window).resize(function() {
        if ($(".stepThird #ganttChartDiv").length > 0) {
            recreateChartControl('ganttChartDiv', true);
        }
    });
    
    $("#uploadFile").hover(function(){
        $("#browserButton").css("background-position", "left -72px");
        $("#browserButton .btnR").css("background-position", "right -120px");
        $("#browserButton .btnC").css("background-position", "left -96px");
    },function() {
        $("#browserButton").css("background-position", "left top");
        $("#browserButton .btnR").css("background-position", "right -48px");
        $("#browserButton .btnC").css("background-position", "left -24px");
    });
});
