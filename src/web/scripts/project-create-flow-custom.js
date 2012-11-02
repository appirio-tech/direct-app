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
 * @version 1.2 (Release Assembly - TC Direct Project Forum Configuration Assembly) change notes:
 *              added functions for manage forums page and popups.
 * @version 1.3 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence) change notes:
 *              added function for populate project answers.
 *              added initialize project question for custom project.
 * 
 * @author: KennyAlive, TCSASSEMBLER, TCSASSEMBLY, Ghost_141
 * @version 1.3
 */
 
function initCustomProjectFlow() {
	initProjectQuestions(PROJECT_TYPE_CUSTOM);
    initCustomStep1();
    initCustomStep2();
    initCustomStep4();
    initCustomStep5();
    initCustomStep6();
}

/**
 * Moves to the step with the given step number.
 *
 * @param stepNumber the step number from 1 - 6.
 */
var goCreateProjectStep = function(stepNumber) {
    if (stepNumber < 1 || stepNumber > 7) {
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
                goCreateProjectStep(6);
            }
        } else {
            addresscloseModal();
            addressLoadModal('#alertModal');
        }
        return false;
    });
}

//-----------------------------------------------
//Step 6
//-----------------------------------------------
function initCustomStep6() {
    $('#newProjectStep6 .stepSixth .geryContent .nextStepButton').click(function() {
        addresscloseModal();
        addressLoadModal('#createProjectConfirm');
    });

    $('.stepSixth .addForum').click(function(){
        $('#createForumModal input, #createForumModal textarea').val('');
        addresscloseModal();
        addressLoadModal('#createForumModal');
    });    
 
    $("#createForumModal .projectName input.text,#createForumModal .descProject textarea").keyup(function() {
        removeError($(this));
    });

    $('#createForumModal .saveButton1').click(function() {
        removeError($("#createForumModal .descProject textarea"));
        var valid1 =  validateForum($("#createForumModal .descProject textarea")); 

        removeError($("#createForumModal .projectName input"));
        var valid2 = validateForum($("#createForumModal .projectName input"));

        if (valid1 && valid2) {
            var tmpTR = $("<tr>");
            var tmpTD = $("<td>");
            tmpTD.append('<input class="selectUserCheck" type="checkbox">');
            tmpTD.append('<span class="group">' + $("#createForumModal .projectName input").val().replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + '</span>');
            tmpTR.append(tmpTD);
            tmpTD = $("<td>");
            tmpTD.append('<span class="group">' + $("#createForumModal .descProject textarea").val().replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + '</span>');
            tmpTR.append(tmpTD);
            tmpTD = $('<td class="checkbox">');
            tmpTD.append('<a class="deleteIcon" href="javascript:;">')
            tmpTR.append(tmpTD);
            $(".stepSixth .forums").append(tmpTR);

            addresscloseModal();
            addressLoadModal('#createForumConfirmModal');
        } 
    });

    $(".stepSixth .forums .deleteIcon").live("click", function() {
        $(this).parents("tr").remove();
    });

    $('.stepSixth .forums .deleteSelect').click(function() {
        $(".stepSixth .forums .selectUserCheck:checked").each(function() {
            $(this).parents('tr').remove();
        });
    });
}

function validateForum(box){
    var value = $.trim(box.val());
    var tips = box.data("tips")?box.data("tips"):"";  
    if(value == "" || value == tips){
        box.parent().find(".message").append('<span class="errorText">This field cannot be left empty.</span>');
        box.addClass("error").val("");
        box.after('<span class="errorIcon"></span>');
        return false;
    }
    return true;
}

function populateProjectAnswersForCustom() {
	// the result array.
	result = [];
	// prepare the project questions.
	customProjectQuestions = prepareProjectQuestions(PROJECT_TYPE_CUSTOM);
	getCopilotInfo(customProjectQuestions[0], result);
	populateProjectAnswerFromProjectQuestion(customProjectQuestions[1], result);
	getProjectForum(customProjectQuestions[2], result);
	return result;
}

function getProjectForum(question, result) {
	array = new Array("Forum Name: ", ", Description: ");
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;
	answer.multipleAnswers = [];
	$(question.multipleAnswersHtmlXpath).each(function(i) {
		if(i > 1) {
			var detailedSpe = "";
			$(this).children().each(function(index) {
				if(index < 2) {
					detailedSpe = detailedSpe + array[index] + $(this).text();
				}
			});
			answer.multipleAnswers.push(detailedSpe);
		}
	});
	
	result.push(answer);
	return answer;
}