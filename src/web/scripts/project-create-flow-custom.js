/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript code related to custom project creation flow.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
 *
 * @author: KennyAlive
 * @version 1.0
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

    if(stepsChoices['step1'] == 'custom') {
        $("#customGamePlanRadio").attr('checked', true);
    }

    if (stepNumber == 4) {
        if(stepsChoices['step4'] == 'chooseCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['step4'] == 'createCopilot') {
            $("#newProjectStep4 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['step4'] == 'noCopilot') {
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

                // if custom, show the custom confirm modal
                addresscloseModal();
                addressLoadModal('#customConfirmModal');

                // $.cookie("step2-size", null);
                stepsChoices['step1'] = 'custom';
            } else if (pType == 'Mobile Project Type') {
                activeProjectType = PROJECT_TYPE_MOBILE;
                stepsChoices['step1'] = 'Mobile Project Type';
                initStepBar('stepBarMobilePresentation');
                $(".stepFourth2 table.addedItem tbody").html('<tr class="hide"><td colspan="4"></td></tr>');
                swDocuments = []; // remove documents that can be left from the previous uncomplited project wizard
                goMobileProjectStep(1);
            } else if (pType == 'Presentation Project Type') {
                activeProjectType = PROJECT_TYPE_PRESENTATION;
                stepsChoices['step1'] = 'Presentation Project Type';
                initStepBar('stepBarMobilePresentation');
                $(".stepFourth2 table.addedItem tbody").html('<tr class="hide"><td colspan="4"></td></tr>');
                swDocuments = []; // remove documents that can be left from the previous uncomplited project wizard
                goCreatePresentationProjectStep(1);
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
        } else if (val == "yes") {
            addresscloseModal();
            addressLoadModal('#errorcModal');
        } else if (val == "no") {
            stepsChoices['step4'] = 'noCopilot';
            goCreateProjectStep(5);
        }
    });
}

//-----------------------------------------------
// Step 5
//-----------------------------------------------
function initCustomStep5() {
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
}
