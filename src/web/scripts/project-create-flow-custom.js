/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript code related to custom project creation flow.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
 *
 * @version 1.1 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow) change notes: 
 *              added support for new analytics project type creation flow.
 *              updated to remove the messy using of stepsChoices['step1'] and use activeProjectType instead.
 *              updated stepsChoices['step4'] to stepsChoices['require-copilot-step'].
 *
 * @author: KennyAlive, TCSASSEMBLER
 * @version 1.1
 */
 
function initCustomProjectFlow() {
    initCustomStep1();
    initCustomStep2();
    initCustomStep4();
    initCustomStep5();
}

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
    $("#newProjectStep" + stepNumber).show();

    // text area
    $('.stepSecond textarea').css('width', $('.stepSecond .row').width() - 206);
    
    updateStepBar(stepNumber);

    if (stepNumber == 6) {
        $(".stepBar li").each(function() {
            var index = $(this).index() + 1;
            var s = $(this).find(">span");
            s.addClass("finished");
            s.find("a").unbind('click').attr("href", "javascript:;");

        });
    }

    if(activeProjectType = PROJECT_TYPE_CUSTOM) {
        $("#customGamePlanRadio").attr('checked', true);
    }

    if (stepNumber == 4) {
        if(stepsChoices['require-copilot-step'] == 'chooseCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['require-copilot-step'] == 'createCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['require-copilot-step'] == 'noCopilot') {
            $("#newProjectStep4 .radioNo").attr("checked", "checked");
        }
    }
};

//-----------------------------------------------
// Step 1
//
// NOTE: we refer here to .stepSecond class since the 1st and 2nd page were swapped. At the 
// moment changing class's names is very error prone (a lot of css, scripts), so it's were not renamed.
// This can be done in separate 'clean up contest'.
//-----------------------------------------------
function initCustomStep1() {
    $('.stepSecond textarea').css('width', $('.stepSecond .row').width() - 206);

    $(".stepSecond .nextStepButton").click(function() {
        removeError($(".stepSecond .descProject textarea"));
        var valid1 = validate($(".stepSecond .descProject textarea"));

        removeError($(".stepSecond .projectName input"));
        var valid2 = validate($(".stepSecond .projectName input"));

        if (valid1 && valid2) {
            goCreateProjectStep(2);
            return true;
        } else {
            return false;
        }
    });
}

//-----------------------------------------------
// Step 2
//
// NOTE: we refer here to .stepFirst class since the 1st and 2nd page were swapped. At the 
// moment changing classe's names is very error prone (a lot of css, scripts), so it's were not renamed.
// This can be done in separate 'clean up contest'.
//-----------------------------------------------
function initCustomStep2() {
    $('.stepFirst .nextStepButton').click(function() {
        var falgRadio = false, jPar;
        $('.stepFirst .radio').each(function() {
            if ($(this).attr('checked')) {
                falgRadio = true;
            }
        });
        if (!falgRadio) {
            // no project plan is chosen
            addresscloseModal();
            addressLoadModal('#errortModal');
        } else {
            jPar = $('.stepFirst .radio:checked').parent();
            var pType = jPar.find('label').text();
            if (pType == 'Custom') {
                // the activeProjectType is PROJECT_TYPE_CUSTOM here
                activeProjectType = PROJECT_TYPE_CUSTOM;

                // if custom, show the custom confirm modal
                addresscloseModal();
                addressLoadModal('#customConfirmModal');

                // $.cookie("step2-size", null);
            } else if (pType == 'Mobile Project Type') {
                activeProjectType = PROJECT_TYPE_MOBILE;
                initStepBar('stepBarMobilePresentation');
                $(".stepFourth2 table.addedItem tbody").html('<tr class="hide"><td colspan="4"></td></tr>');
                swDocuments = []; // remove documents that can be left from the previous uncomplited project wizard
                goMobileProjectStep(1);
            } else if (pType == 'Presentation Project Type') {
                activeProjectType = PROJECT_TYPE_PRESENTATION;
                initStepBar('stepBarMobilePresentation');
                $(".stepFourth2 table.addedItem tbody").html('<tr class="hide"><td colspan="4"></td></tr>');
                swDocuments = []; // remove documents that can be left from the previous uncomplited project wizard
                goCreatePresentationProjectStep(1);
            } else if (pType == 'Analytics Project Type') {
                activeProjectType = PROJECT_TYPE_ANALYTICS;
                initStepBar('stepBarAnalytics');
                swDocuments = []; // remove documents that can be left from the previous uncomplited project wizard
                goAnalyticsProjectStep(1);
            } else {
                // $.cookie("step2", "gameplan");
                // $.cookie("step2-size", jPar.parent().find(".selProjSize").val());
                // window.location.href = 'start-a-new-project-step-3.html';
                showErrors("Does not support now, will be implemented in next assembly.");
            }
        }
    });

    // Custom project type confirmation button
    $("#customConfirmModal .button6").live('click', function(){
        goCreateProjectStep(4);
    });
}

//-----------------------------------------------
// Step 4
//-----------------------------------------------
function initCustomStep4() {
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
            return false;
        } else if (val == "yes") {
            addresscloseModal();
            if (!stepsChoices['copilots'] && stepsChoices['require-copilot-step'] != 'createCopilot') { 
                addressLoadModal('#errorcModal');
                return false;
            }
        } else if (val == "no") {
            stepsChoices['require-copilot-step'] = 'noCopilot';
        }
        if (activeProjectType == PROJECT_TYPE_CUSTOM) {
            goCreateProjectStep(5);
        } else if (activeProjectType == PROJECT_TYPE_ANALYTICS){
            goAnalyticsProjectStep(3);
        }
        return false;
    });
}

//-----------------------------------------------
// Step 5
//-----------------------------------------------
function initCustomStep5() {
    $('#newProjectStep5 .stepFifth .geryContent .nextStepButton').click(function() {
        var hasUserPermissionAdded = ($('#newProjectStep5 .stepFifth .checkPermissions .userRow').length >0);
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
}
