/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 *
 * Javascript file for the new creation project process.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1)
 *
 * @version 1.1 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
 *              change note: added support for new mobile and presentation project creation flow.
 *
 * @version 1.2 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow) change notes: 
 *              added support for new analytics project type creation flow.
 *              updated to remove the messy using of stepsChoices['step1'] and use activeProjectType instead.
 *              updated stepsChoices['step4'] to stepsChoices['require-copilot-step'].
 *
 * @version 1.3 (Release Assembly - TC Direct Project Forum Configuration Assembly) change notes:
 *              added forums configuration for new custom project creation flow.
 * 
 * @version 1.4 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence) change notes:
 * 				added populate project answer for mobile, presentation, analytic project type.
 *              added initProjectQuestions for initialize project question text by data from backend.
 * 				updated custom, mobile, presentation, analytics project type.
 * 
 * @author: KennyAlive, TCSASSEMBLY, Ghost_141
 * @version 1.4
 */
 
 var PROJECT_TYPE_CUSTOM = 9;
 var PROJECT_TYPE_MOBILE = 10;
 var PROJECT_TYPE_PRESENTATION = 11;
 var PROJECT_TYPE_ANALYTICS = 12;

// Defines which project type's wizard is active at the moment. 
var activeProjectType = PROJECT_TYPE_CUSTOM;

/**
* Gets the type of the project.
*/
function getProjectType() {
    return activeProjectType;
}

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

function removeError(box) {
    box.parent().find(".errorText,.errorIcon").remove();
    box.removeClass("error");
}

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

function validate2(box){
    var valid = true;
    for(var i = 0; i <box.length; i++){
        var value = $.trim($(box[i]).val());
        var tips;
        if(box.hasClass("waterMark")){
            tips = $(box[i]).attr("title")?$(box[i]).attr("title"):"";
        }else{
            tips="";
        }
        if(!$(box[i]).is(':disabled')){
            if(value == "" || value == tips){
                $(box[i]).parent().addClass("error2");
                $(box[i]).parent().next().next("p.message").find(".errorText").show();
                $(box[i]).after('<span class="errorIcon"></span>');
                valid = false;
            }
        }
    }
    return valid;
}

function removeError2(box){
    $(box).parent().find(".errorIcon").remove();
    $(box).parent().siblings().find(".errorText").hide();
    $(box).parent().removeClass("error2");
}

var generateConfirmationPage = function(projectId, projectName) {
    // determine what to show in the final confirmation page
    if ($(".stepSixth").length > 0) {
        var isPPT = false;
        var step1 = stepsChoices["step1"];
        var step4 = stepsChoices['require-copilot-step'];
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
        var projectOverviewLink = $(".stepSixth .button6");
        var hrefValue = projectOverviewLink.attr('href');
        hrefValue = hrefValue.replace("projectIdValue", projectId);
        projectOverviewLink.attr('href', hrefValue);
		
		// update project overview link
        var projectOverviewLink1 = $(".stepSixth .defaultNote .overviewLink");
        var hrefValue1 = projectOverviewLink1.attr('href');
        hrefValue1 = hrefValue1.replace("projectIdValue", projectId);
        projectOverviewLink1.attr('href', hrefValue1);

        // Display default note
        $(".stepSixth .defaultNote").show();
        /*if (step1 == "gameplan" && step4 == "chooseCopilot") {
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .completedSteps .step3.selectStep").show();
        }
        if (step1 == "gameplan" && step4 == "createCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .completedSteps .step3.createStep").show();
            $(".stepSixth .notYetSteps .step4.launchStep").show();
            $(".stepSixth .notYetSteps .step5.selectStep").show();
        }
        if (step1 == "gameplan" && step4 == "noCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.gameplanStep").show();
            $(".stepSixth .notYetSteps .step3.gameplanStep").show();
            $(".stepSixth .notYetSteps .step4.completeStep").show();
            $(".stepSixth .notYetSteps .step5.launchFirstStep").show();
        }
        if (step1 == "gameplan") {
            step2Size = stepsChoices["step2-size"];
            if ((step2Size === null) || (step2Size === undefined)) {
                updateProjSizeData($(".stepSixth .projectStats"), 0);  // Arbitrary default.
            } else {
                updateProjSizeData($(".stepSixth .projectStats"), step2Size);
            }
        }
        if (step1 == "custom") {
            $(".stepSixth .projectStats").hide();
        }
        if (step1 == "custom" && step4 == "chooseCopilot") {
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
        if (step1 == "custom" && step4 == "createCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .completedSteps .step2.createStep").show();
            $(".stepSixth .notYetSteps .step3.launchStep").show();
            $(".stepSixth .notYetSteps .step4.selectStep").show();
        }
        if (step1 == "custom" && step4 == "noCopilot") {
            $(".stepSixth .notYetSteps").show();
            $(".stepSixth .notYetSteps .step2").show();
            $(".stepSixth .notYetSteps .step3.completeStep").show();
            $(".stepSixth .notYetSteps .step4.launchFirstStep").show();
        }
        if (isPPT) {
            $(".stepSixth .defaultNote").hide();
            $(".stepSixth .notYetSteps").hide();
            $(".stepSixth .projectStats").hide();
            $(".stepSixth .completedSteps").hide();
            $(".stepSixth .pptNote").show();
            $(".stepSixth .pptProjectName").show();
            $(".stepSixth .pptSummary").show();
            $(".stepSixth .pptProjectDuration").show();
            $(".stepSixth .pptPresentationLength").show();
            $(".stepSixth .pptTargetAudience").show();
            $(".stepSixth .pptManagementMessage").show();
            // name
            $('.stepSixth dl.pptProjectName dd p').text(projectName);

            // summary
            $('.stepSixth dl.pptSummary dd p').text($("#newProjectDescription").val());

            // project duration
            $('.stepSixth dl.pptProjectDuration p').text($('.stepFirst .projectItem input:radio:checked').parents('.projectItem').find('td.dataDur').text());

            // presentation length
            $('.stepSixth dl.pptPresentationLength dd span.length').text($('.stepFirst .projectItem input:radio:checked').parents('.projectItem').find('td.dataNumCont').text());

            var audiences = "";
            $(".stepThird .speciealForm .selectRadioBox .radio:checked").each(function() {
            if (audiences.length > 0) {
                audiences += '<br/>';
            }
            audiences += htmlEncode($(this).parents('tr').find('td.labelTd').text());
        });

        // target audience
        $('.stepSixth dl.pptTargetAudience dd p').html(audiences);
        } */
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
 * Create request data to create draft copilot contest.
 */
function createDraftContestRequest(projectName, projectType) {
    var projectHeader = mainWidget.softwareCompetition.projectHeader;
    var assetDTO = mainWidget.softwareCompetition.assetDTO;

    mainWidget.competitionType = "SOFTWARE";

    projectHeader.projectCategory = {};
    projectHeader.projectCategory.id = 29;
    projectHeader.projectCategory.name = "Copilot Posting";
    projectHeader.projectCategory.projectType = {};
    projectHeader.projectCategory.projectType.id = 2;
    projectHeader.projectCategory.projectType.name = "Application";
    projectHeader.setConfidentialityTypePublic();

    var contestName = projectName + " Copilot Opportunity";
    assetDTO.name = contestName;
    assetDTO.directjsDesignNeeded = false;
    assetDTO.directjsDesignId = -1;
        
    projectHeader.tcDirectProjectName = projectName;
    projectHeader.projectSpec.privateDescription = "";
    projectHeader.setProjectName(contestName);

	if (projectType == PROJECT_TYPE_MOBILE) {
        projectHeader.projectSpec.detailedRequirements = createCopilotContestDescription_MobileProject();
    } else if (projectType == PROJECT_TYPE_PRESENTATION) {
        projectHeader.projectSpec.detailedRequirements = createCopilotContestDescription_PresentationProject();
    } else if (projectType == PROJECT_TYPE_ANALYTICS) {
        projectHeader.projectSpec.detailedRequirements = createCopilotContestDescription_AnalyticsProject();
    }
	
    var amount = 150.0;
    projectHeader.setFirstPlaceCost(amount);
    // set all prize to 0 except first place cost
    projectHeader.setSecondPlaceCost(75.0);
    projectHeader.setReviewCost(0);
    projectHeader.setReliabilityBonusCost(0);
    projectHeader.setDRPoints(0);
    projectHeader.setMilestoneBonusCost(0);
    projectHeader.setAdminFee(0);
    projectHeader.setSpecReviewCost(0);

    var prizes = [];
    prizes.push(new com.topcoder.direct.Prize(1, amount, CONTEST_PRIZE_TYPE_ID, 1));
    projectHeader.prizes = prizes;
    projectHeader.setBillingProject(0);
    delete mainWidget.softwareCompetition.assetDTO.productionDate;

    var request = saveAsDraftRequestSoftware();
    request['createCopilotPosting'] = true;
    if (projectType == PROJECT_TYPE_PRESENTATION) {
        request['presentationProject'] = true;
    }
    return request;
}

function changeIdAndClass() {
	questions = [39, 25];
	$('.question').each(function(index) {
		$(this).removeClass('question').addClass('question' + questions[index]);
	});
	options = ['needCopilotCustom', 'noCopilotCustom', 'needCopilot', 'noCopilot'];
	$('.questionOption').each(function(index) {
		$(this).removeClass('questionOption').attr('id', options[index]);
	});
	options = [148, 149, 117, 118];
	$('.optionText').each(function(index) {
		$(this).removeClass('optionText').addClass('questionOption' + options[index]);
	});
}

function getMultipleAnswers(Xpath) {
	if(Xpath != null) {
		nodes = $(".stepFirst2 .geryContent .formats label.last" + Xpath);
		multipleAnswers = [];
		$.each(nodes, function(index) {
			if($(this).is(':checked')) {
				multipleAnswers.push($(this).parent().next().find('input').val());
			}
		});
		return multipleAnswers;
	}
}

function getDetailedSpecifications(question, result) {
	array = new Array("Type: ", ", Source: ", ", Data: ");
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;
	answer.multipleAnswers = [];
	$(question.multipleAnswersHtmlXpath).each(function(i) {
		if(i > 1) {
			var detailedSpe = "";
			$(this).children().each(function(index) {
				if(index < 3) {
					detailedSpe = detailedSpe + array[index] + $(this).text();
				}
			});
			answer.multipleAnswers.push(detailedSpe);
		}
	});
	
	result.push(answer);
	return answer;
}

function initProjectQuestions(projectType) {
	questions = prepareProjectQuestions(projectType);
	if((questions == null) || (questions.length == 0)){
		showErrors("There is some thing wrong with project questions. Please contact PM. Thanks.");
	}
	$.each(questions, function(index, question) {
		var texts = question.questionText.split('||');
		$('.question' + question.id).each(function(index) {
			$(this).html(texts[index] + $(this).html());
		});
		if(question.questionOptions != null) {
			$.each(question.questionOptions, function(index, content) {
				var optionsTexts = content.questionOptionText.split('||');
				$('.questionOption' + content.id).each(function(index) {
					$(this).html($(this).html() + optionsTexts[index]);
				});
			});
		}
	});
}

function prepareProjectQuestions(projectType) {
	questions = [];
	$.each(projectQuestions, function(index, content) {
		if(content.directProjectTypeId == projectType) {questions.push(content)}
	});
	return questions;
}

// populate project answers
function populateProjectAnswerFromProjectQuestion(question, result) {
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;
	if(question.answerHtmlId != "") {
		// question has direct answer
		answer.textualAnswer = $("#" + question.answerHtmlId).val();
	} else if(question.questionOptions != null) {
		// question has multiple options
		$.each(question.questionOptions, function(index, content) {
			answerOption = {};
			answerOption.projectQuestionOption = {};
			answerOption.projectQuestionOption.id = content.id;
			// find the checked option(radio or checkbox)
			answerNode = $("#" + content.answerHtmlId);
			if(answerNode.is(':checked')) {
				if(content.hasAssociatedTextbox) {
					answerOption.answerHtmlValue = $("#" + content.associatedTextboxHtmlId).val();
				}else {
					answerOption.answerHtmlValue = answerNode.val();
				}
				answer.optionAnswers.push(answerOption);
			}
		});
	}
	if(question.multipleAnswersHtmlXpath != "") {
		elements = $(question.multipleAnswersHtmlXpath);
		answer.multipleAnswers = [];
		elements.each(function(index) {
			answer.multipleAnswers.push($(this).text());
		});
	}
	result.push(answer);
	return answer;
}

// final step
var createNewProject = function() {
    var projectType = getProjectType();

    // get project name and project description
    var projectName = $("#newProjectName").val();
    var projectDescription = $("#newProjectDescription").val();
    var permissions = [];
    var forums = [];
    var request = {};
    
    var permissionsRows;
    if (projectType == PROJECT_TYPE_CUSTOM) {
        permissionsRows = $('#newProjectStep5 .userRow');
    } else if (projectType == PROJECT_TYPE_ANALYTICS) {
        permissionsRows = $('#newAnalyticsProjectStep6 .userRow');
    }
    
    // get permissions
    if (permissionsRows) {
        permissionsRows.each(function() {
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
    
    // get forums configuration
    if (projectType == PROJECT_TYPE_CUSTOM) {
        $('.stepSixth .forums tr').each(function(i) {
            if (i >= 2) {
                var forum = {};
                forum['forumName'] = $(this).find('td:eq(0) span').html();
                forum['forumDescription'] = $(this).find('td:eq(1) span').html();
                forums.push(forum);
            }
        });
        if (forums.length > 0) {
            request['forums'] = forums;
        }
    }

    request['createCopilotPosting'] = true;   
    if (projectType == PROJECT_TYPE_CUSTOM || projectType == PROJECT_TYPE_ANALYTICS) {
        // initialize custom project request
        var step4 = stepsChoices['require-copilot-step'];    
        if(step4 == 'chooseCopilot') {
            var copilotIds = [];
            $.each(stepsChoices["copilots"], function(index, value) {
                copilotIds[copilotIds.length] = value.copilotProfileId;
            });
            request['copilotIds'] = copilotIds;
            if (projectType != PROJECT_TYPE_ANALYTICS) {
                request['createCopilotPosting'] = false;    
            }
        } else if (step4 == "noCopilot" && projectType != PROJECT_TYPE_ANALYTICS) {
            request['createCopilotPosting'] = false;
        }
    }
    if (request['createCopilotPosting']) {
        request = createDraftContestRequest(projectName, projectType);
    }

    request['projectName'] = projectName;
    request['projectDescription'] = projectDescription;
    request['permissions'] = permissions;
	
	request.projectData = {};
	if (projectType == PROJECT_TYPE_MOBILE) {
		request.projectData.projectAnswers = populateProjectAnswersForMobile();
    } else if (projectType == PROJECT_TYPE_PRESENTATION) {
		request.projectData.projectAnswers = populateProjectAnswersForPresentation();
    } else if (projectType == PROJECT_TYPE_ANALYTICS) {
		request.projectData.projectAnswers = populateProjectAnswersForAnalytics();
	} else if (projectType == PROJECT_TYPE_CUSTOM) {
		request.projectData.projectAnswers = populateProjectAnswersForCustom();
	}
	
    modalPreloader();

    $.ajax({
        type: 'POST',
        url:  "createNewCockpitProjectAjax",
        data: request,
        cache: false,
        dataType: 'json',
        async : true,
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    if (projectType == PROJECT_TYPE_CUSTOM) {
                        var projectName = result.projectName;
                        var projectId = result.projectId;
                        modalClose();
                        generateConfirmationPage(projectId, projectName);
                        goCreateProjectStep(7);
                    } else if (projectType == PROJECT_TYPE_MOBILE) {
                        $("#infoModalMobile").unbind("click").click(function() {
                            window.location.href = '/direct/projectOverview?formData.projectId=' + result.projectId;
                        });
                        addressLoadModal("#infoModalMobile");
                    } else if (projectType == PROJECT_TYPE_PRESENTATION) {
                        $("#infoModalPresentation").unbind("click").click(function() {
                            window.location.href = '/direct/projectOverview?formData.projectId=' + result.projectId;
                        });
                        addressLoadModal("#infoModalPresentation");
                    } else if (projectType == PROJECT_TYPE_ANALYTICS) {
                        modalClose();
                        $("#confirmProjectName").html(result.projectName);
                        $("#confirmOverviewLink").attr("href",
                            ctx + "/projectOverview.action?formData.projectId="+ result.projectId);
                        goAnalyticsProjectStep(8);
                    }
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

var initStepBar = function(stepBarTemplateId) {
    var html = $('#' + stepBarTemplateId).html();
    $('.stepBar').empty().html(html);

    var totalSteps = $('.stepBar li').length;

    // set z-index for step bar items
    $('.stepBar li').each(function() {
        var iNum = $('.stepBar li').index(this);
        $(this).css('z-index', totalSteps - iNum);
    });

    // width for step bar
    $('.stepBar li').css('width', ($('.stepBar').width() / totalSteps) + 16);
    $('.stepBar li:first').css('width', ($('.stepBar').width() / totalSteps) + 15);
    if (totalSteps == 7) {
        $('.stepBar li:last').css('width', ($('.stepBar').width() / totalSteps) + 24);
    } else if (totalSteps == 6) {
        $('.stepBar li:last').css('width', ($('.stepBar').width() / totalSteps) + 20);
    }

    // update the step bar width and textarea width when the browser size is changed
    $(window).resize(function() {
        $('.stepBar li').css('width', ($('.stepBar').width() / totalSteps) + 16);
        $('.stepBar li:first').css('width', ($('.stepBar').width() / totalSteps) + 15);
        if (totalSteps == 7) {
            $('.stepBar li:last').css('width', ($('.stepBar').width() / totalSteps) + 24);
        } else if (totalSteps == 6) {
            $('.stepBar li:last').css('width', ($('.stepBar').width() / totalSteps) + 20);
        }
        $('.stepSecond textarea').css('width', $('.stepSecond .row').width() - 206);

        // add line
        $('.stepThird .tfooter td .addMoreButtonBox .text').css('width',($('.stepThird .tfooter td .addMoreButtonBox').width() - 142));
    });
};

var updateStepBar = function(stepNumber) {
    $(".stepBar li").each(function() {
        var index = $(this).index() + 1;
        var s = $(this).find(">span");

        if (index < stepNumber) {
            s.removeClass();
            s.addClass("istatus").addClass("complete");
        } else if (index == stepNumber) {
            s.removeClass();
            s.addClass("istatus").addClass("active");
        } else {
            s.removeClass().addClass("istatus").addClass("inext");
            s.find(".bg").html("Step " + index);
        }

        if (index <= stepNumber) { // add link to completed steps
            var linkHTML = $("#stepBarLinkTemplate").html();
            s.find(".bg").html(linkHTML);

            var stepLink = s.find(".bg a");
            stepLink.html("Step " + index);
            stepLink.unbind('click');

            if (activeProjectType == PROJECT_TYPE_CUSTOM) {
                stepLink.attr("href", "javascript:goCreateProjectStep(" + index + ");");

                if(stepNumber >= 3 && index == 3) {
                    s.addClass("finished");
                    s.find("a").unbind('click').attr("href", "javascript:;");
                }
            } else if (activeProjectType == PROJECT_TYPE_MOBILE) {
                stepLink.attr("href", "javascript:goMobileProjectStep(" + index + ");");
            } else if (activeProjectType == PROJECT_TYPE_PRESENTATION) {
                stepLink.attr("href", "javascript:goCreatePresentationProjectStep(" + index + ");");
            } else if (activeProjectType == PROJECT_TYPE_ANALYTICS && stepNumber != 8) {
                stepLink.attr("href", "javascript:goAnalyticsProjectStep(" + index + ");");
            }
        }
    });
};

/**
* Files uploading functionality during new project creation.
* This should contain the upload logic for all project creation flow types.
*/
var initNewProjectDocumentsUpload = function() {
    // prepare uploader object
    var swUploader = new AjaxUpload(null, {
        action: ctx + '/launch/documentUpload',
        name: 'document',
        responseType: 'json',
        onSubmit: function(file , ext) {
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
                    swCurrentDocument['documentId'] = result.documentId;
                    swDocuments.push(swCurrentDocument);
                    swCurrentDocument = {};

                    // add new table row with info about uploaded file
                    if (activeProjectType == PROJECT_TYPE_ANALYTICS) {
                        var fileTd = $(".step6 .textUploadPhoto").val();    
                        var tr = '<tr><td class="first">Upload</td><td>'+fileTd+
                            '</td><td class="last"><a href="javascript:;" class="remove" rel="'+result.documentId+
                            '">Remove</a></td></tr>';
                        $(".analyticsSteps .step6 table").show();
                        $(".analyticsSteps .step6 tbody tr:last").before($(tr));
                        $(".analyticsSteps .step6 tbody tr").removeClass("even");
                        $(".analyticsSteps .step6 tbody tr:odd").addClass("even");

                        // since the uploader removes file input element from the DOM we should restore it
                        // in order to be able to upload new files.
                        var wrapper = $('.step6 .browserWrapper');
                        wrapper.append('<input type="file" class="file" name="document"/>');
                        wrapper.find('.file').css({opacity:'0'});
                        wrapper.find('.file').change(function() {
                            var fileName = getFileName(wrapper.find('.file').val());
                            $('.step6 .textUploadPhoto').val(fileName);
                        });
                    } else {
                        var type;
                        var typeRadioBtn = $(".stepFourth2 input[name=type]:checked");
                        if ($.trim(typeRadioBtn.val()) == "other"){
                            type = typeRadioBtn.parent().next().find("input").val();
                        } else {
                            type = typeRadioBtn.parent().text();
                        }
    
                        var source = $(".stepFourth2 input[name=source]:checked").parent().text();
                        var val = $(".stepFourth2 .upload input.text").val();
    
                        var tr = "<tr><td>" + type + "</td>" 
                            + "<td>" + source + "</td>"
                            + "<td>" + val + "</td>"
                            + "<td><a href='javascript:' class='remove' rel='" + result.documentId + "'>Remove</a></td></tr>";
    
                        $(".stepFourth2 table.addedItem tbody").append(tr);
                        $(".stepFourth2 table.addedItem").show();
                        tableZebraEffect($(".stepFourth2 table.addedItem"));

                        // since the uploader removes file input element from the DOM we should restore it
                        // in order to be able to upload new files.
                        var wrapper = $('.stepFourth2 .browserWrapper');
                        wrapper.append('<input type="file" class="file" name="document"/>');
                        wrapper.find('.file').css({opacity:'0'});
                        wrapper.find('.file').change(function() {
                            var fileName = getFileName(wrapper.find('.file').val());
                            $('.stepFourth2 .textUploadPhoto').val(fileName);
                        });
                    }

                    modalClose();
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                    modalClose();
                });
        }
    }, false);

    $(".step6 .documents .action a").click(function(){
        var inputRow = $(".step6 .documents .inputRow:visible");
        var type="Analytics project other document";
        var val = $.trim(inputRow.find(".text").val()); 
        var source = "Direct entry";
        if(inputRow.hasClass("isUpload")){
            source = "Upload";
        }else if(inputRow.hasClass("isUrl")){
            source = "Remote file (URL)";
        }
        if(val == "" || source == "Remote file (URL)" && val=="http://"){
            inputRow.parent().addClass("error");    
        }else{
            // check duplicate for non-Upload type
            if (source != "Upload") {
                var ok=true;
                $(".analyticsSteps .step6 table tr").each(function() {
                    if ($.trim($(this).find("td:eq(0)").text()) == source) {
                        if ($.trim($(this).find("td:eq(1)").text()) == val) {
                            ok=false;
                            return false;
                        }
                    }                      
                });
                if (!ok) {
                    showErrors("The item was already added.");
                    return false;
                }
            }            
            var tr;
            if (source == "Upload") {
                var input = $(".step6 .browserWrapper input[type=file]").get(0);
                swUploader.setInput(input);
                swCurrentDocument['description'] = type;
                swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
                swUploader.submit();
            } else if (source == "Remote file (URL)") {
                var alink = "<a class='url' href='" + val + "'>" + val + "</a> ";
                var rel = type + ": " + htmlEncode(val);
                tr = '<tr><td class="first">'+source+'</td><td>'+alink+
                    '</td><td class="last"><a href="javascript:;" class="remove" rel="'+rel+
                    '">Remove</a></td></tr>';
            } else {
                val=htmlEncode(val);
                var rel = type + ": " + val;
                tr = '<tr><td class="first">'+source+'</td><td>'+val+
                    '</td><td class="last"><a href="javascript:;" class="remove" rel="'+rel+
                    '">Remove</a></td></tr>';
            }
            if (tr) {
                $(".analyticsSteps .step6 table").show();
                $(".analyticsSteps .step6 tbody tr:last").before($(tr));
                $(".analyticsSteps .step6 tbody tr").removeClass("even");
                $(".analyticsSteps .step6 tbody tr:odd").addClass("even");
            }
        }
        return false;
    })
    
    // event handler for upload button from step4 for presentation and mobile wizards
    $(".stepFourth2 .addBtn").click(function(){
        if (!validateStep4()) {
            return false;
        }

        var type;
        var typeRadioBtn = $(".stepFourth2 input[name=type]:checked");
        if ($.trim(typeRadioBtn.val()) == "other") {
            type = typeRadioBtn.parent().next().find("input").val();
        } else {
            type = typeRadioBtn.parent().text();
        }

        var sourceId = $(".stepFourth2 input[name=source]:checked").val();

        if (sourceId == "upload") {
            var input = $(".stepFourth2 .upload input[type=file]").get(0);
            swUploader.setInput(input);
            swCurrentDocument['description'] = type;
            swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
            swUploader.submit();
        } else if (sourceId == "url") {
            var url = $(".stepFourth2 .url input.text").val();
            var val = "<a class='url' href='" + url + "'>" + url + "</a> ";
            var source = $(".stepFourth2 input[name=source]:checked").parent().text();

            var rel = type + ": " + htmlEncode(url);
            var tr = "<tr><td>" + type + "</td>" 
                + "<td>" + source + "</td>"
                + "<td>" + val + "</td>"
                + "<td><a href='javascript:;' class='remove additionalRequirement' rel='" 
                + rel + "'>Remove</a></td></tr>";

            $(".stepFourth2 table.addedItem tbody").append(tr);
            $(".stepFourth2 table.addedItem").show();
            tableZebraEffect($(".stepFourth2 table.addedItem"));
        } else if (sourceId == "source") {
            var val = htmlEncode($(".stepFourth2 .source textarea").val());
            var source = $(".stepFourth2 input[name=source]:checked").parent().text();

            var rel = type + ": " + val;
            var tr = "<tr><td>" + type + "</td>" 
                + "<td>" + source + "</td>"
                + "<td><p class='directEntryText'>" + val + "</p></td>"
                + "<td><a href='javascript:;' class='remove additionalRequirement' rel='" 
                + rel + "'>Remove</a></td></tr>";

            $(".stepFourth2 table.addedItem tbody").append(tr);
            $(".stepFourth2 table.addedItem").show();
            tableZebraEffect($(".stepFourth2 table.addedItem"));
        }
    });

    // event handler for removing uploaded document
    $('.stepFourth2 table.addedItem a.remove,.step6 table a.remove').live('click',function(){
        var v = $(this);
        var documentId = parseInt(v.attr("rel"));
        var removeRow = function () {
            v.parent().parent().remove();
            if (activeProjectType == PROJECT_TYPE_ANALYTICS) {   
                $(".analyticsSteps .step6 tbody tr").removeClass("even");
                $(".analyticsSteps .step6 tbody tr:odd").addClass("even");
            } else {
                tableZebraEffect($(".stepFourth2 table.addedItem"));
            }
        };
        if (!documentId) {
            removeRow();
            return false;
        }
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
                        removeRow();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    }
                );
            }
        });
    });
};

var validateStep4 = function() {
    var error = false;

    var typeRadioBtn = $(".stepFourth2 input[name=type]:checked");
    if ($.trim(typeRadioBtn.val()) == "other") {
        var otherInput = $(".stepFourth2 input[name=other]");

        otherInput.removeClass("error2");
        otherInput.next().find(".errorText").hide();
        otherInput.parent().find(".errorIcon").hide();

        if ($.trim(otherInput.val()) == "" || $.trim(otherInput.val()) == "other") {
            otherInput.addClass("error2");
            otherInput.next().find(".errorText").show();
            otherInput.parent().find(".errorIcon").show();
            error = true;
        }
    }

    var source = $(".stepFourth2 input[name=source]:checked").parent().text();
    var sVal = $(".stepFourth2 input[name=source]:checked").val();

    if (sVal == "source") {
        var textarea = $(".stepFourth2 .source textarea");
        if ($.trim(textarea.val()) == "") {
            textarea.addClass("error2");
            textarea.next().find(".errorText").show();
            error = true;
        } else {
            textarea.removeClass("error2");
            textarea.next().find(".errorText").hide();
        }
    } else if (sVal == "url") {
        var input = $(".stepFourth2 .url input.text");
        if ($.trim(input.val()) == "" || $.trim(input.val()) == "http://") {
            input.addClass("error2");
            input.siblings("p").find(".errorText").show();
            error = true;
        } else {
            input.removeClass("error2");
            input.siblings("p").find(".errorText").hide();
        }
    } else if (sVal == "upload") {
        var input = $(".stepFourth2 .upload input.text");
        if ($.trim(input.val()) == "") {
            input.addClass("error2");
            input.siblings("p").find(".errorText").show();
            error = true;
        } else {
            input.removeClass("error2");
            input.siblings("p").find(".errorText").hide();
        }
    } else { // unknown value
        error = true;
    }

    return !error;
};

/*set zebra effect for table*/
function tableZebraEffect(table){
    $(table).find("tbody tr").removeClass("alt");
    $(table).find("tbody tr:even").addClass("alt");
}

/*
 * Get the file name and drop the other letters in URL.
 * getFileName()
 * @ return string
 */
function getFileName(url) {
    var tmp,last;
    last = url.lastIndexOf('\\');
    tmp = url.substring(last + 1, url.length);
    return tmp;
}

function setValidationStatus2(jqueryElem, isValid) {
    if (isValid) {
        jqueryElem.removeClass("error2");
        jqueryElem.siblings("p").find("span").hide();
        jqueryElem.siblings("span.errorIcon").hide();
    } else {
        jqueryElem.addClass("error2");
        jqueryElem.siblings("p").find("span").show();
        jqueryElem.siblings("span.errorIcon").show();
    }
}

 // cache the project copilots initialization data
var widgetResult;

$(document).ready(function() {
    // text-shadow
    $('.solveProblem .inner h3,.stepBar li span.bg,.stepFifth .geryContent .downloadProfile .profileLeft').css('text-shadow', '0px 1px 0px #ffffff');
    $('.stepBar li a').css('text-shadow', '0px 1px 0px #6C871E');
    $('.stepBar li .complete a').css('text-shadow', '0px 1px 0px #53661C');
    $(".notePresentation").css("resize", "none");

    initStepBar('stepBarCustom');

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
        $('#errortModal,#detailModal,#gamePlanModal,#addUserModal,#deleteUserModal,#deleteConfirmModal,#alertModal,#selectionConfirmationModal,#createPostingModal,#customConfirmModal,#errorcModal,#createProjectConfirm,#errortModalStep4,#createForumModal,#createForumConfirmModal').hide();
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
    
    $(".stepContainer .prevStepButton, .stepContainer2 .prevStepButton").live('click', function(){
        var previousStep = $(".stepBar .active").parent().index();

        if (activeProjectType == PROJECT_TYPE_CUSTOM) {
            if (previousStep == 3) {
                 previousStep = 1;
            }
            goCreateProjectStep(previousStep);
        } else if (activeProjectType == PROJECT_TYPE_MOBILE) {
            if (previousStep == 0) {
                activeProjectType = PROJECT_TYPE_CUSTOM;
                initStepBar('stepBarCustom');
                goCreateProjectStep(1);
            } else {
                goMobileProjectStep(previousStep);
            }
        } else if (activeProjectType == PROJECT_TYPE_PRESENTATION) {
            if (previousStep == 0) {
                activeProjectType = PROJECT_TYPE_CUSTOM;
                initStepBar('stepBarCustom');
                goCreateProjectStep(1);
            } else {
                goCreatePresentationProjectStep(previousStep);
            }
        } else if (activeProjectType == PROJECT_TYPE_ANALYTICS) {
            if (previousStep == 0) {
                activeProjectType = PROJECT_TYPE_CUSTOM;
                initStepBar('stepBarCustom');
                goCreateProjectStep(1);
            } else {
                goAnalyticsProjectStep(previousStep);
            }
        }
    });
	// change element id and class
	changeIdAndClass();
    // initialize project creation wizards
    initCustomProjectFlow();
    initMobileProjectFlow();
    initPresentationProjectFlow();
    initAnalyticsProjectFlow();

    //detail modal
    $('.stepFirst .geryContent .detailButton').click(function() {
        var sizeOption = $(this).parents('.projectItem').find('select.selProjSize option:selected').text();
        var jModal = $('#detailModal');
        var cost, dur, contestList, i, jContest;
        addresscloseModal();
        addressLoadModal('#detailModal');
        if (sizeOption == 'Small') {
           jModal.find("p.paragraph1").text('Do you need to persuade someone quickly? Make it short and snappy.');
           jModal.find("p.paragraph2").text('A small project is suitable for a presentation of one to five 	pages, including the cover page. A brief presentation with the 	right choice of words and graphics can be a very powerful way to make your point.');
        } else if (sizeOption == 'Medium') {
           jModal.find("p.paragraph1").text('Are you trying to hit the sweet spot? Not too short, not too long.');
           jModal.find("p.paragraph2").text('A medium project will result in a presentation of six to ten pages, including the cover page. Sharp graphics and succinct text will make this just right for a quotidian sales pitch or business report.');
        } else if (sizeOption == 'Large') {
           jModal.find("p.paragraph1").text('Do you have an important point to make? Go big or go home.');
           jModal.find("p.paragraph2").text('A large project will build a presentation of 11 to 20 pages, including the cover page. Spectacular graphics accompanied by precise yet elegant wording will deliver your message in the best possible light.');
        }
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
        var durList = [10, 20, 30], costList = [3000, 7000, 12000], numContList = ['1-2', '3-4', '5-6'];
        jPar.find('.dataDur').html(durList[v] + " days");
        jPar.find('.dataCost').html("$ " + costList[v].toFixed(2));
        jPar.find('.dataNumCont').html(numContList[v]);
    }

    $('.stepFirst .selProjSize').each(function () {  // Initialize.
        updateProjSizeData($(this).parents('table').eq(0), $(this).val());
    });
    $('.stepFirst .selProjSize').bind("change", function () {
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

    $('.stepFirst .geryContent .gamePlanButton').click(function() {
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

    $('.stepFirst .projectItem .radio').click(function() {
        $('.stepFirst .projectItem .projectContainer').removeClass('hover');
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
        $(".stepFifth:visible .checkPermissions .userRow").each(function() {
            var userTag = $(this).find('div.group');
            var userName = $.trim(userTag.html());
            var userId = $.trim(userTag.attr('id')).substring(6);

            $('#addUserModal .addUserForm .addUserRight ul').append('<li id="' + userId + '">' + userName + '</li>');
        });
    });

    var getAddedUser = function() {
        var addedUser = {};

        $('.stepFifth:visible .addUserPlan table .checkPermissions .userRow').each(function(){
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
                $('.stepFifth:visible .addUserPlan table .checkPermissions').append ('<tr class="userRow">' + html + '</tr>');
            }
        });

        // remove the ones does not exist in the right list
        $('.stepFifth:visible .addUserPlan table .checkPermissions .userRow').each(function(){
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
            type: 'POST',
            url:  "getUser",
            data: request,
            cache: false,
            dataType: 'json',
            async : false,
            success: function (jsonResult) {
                handleJsonResult2(jsonResult,
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
        if (activeProjectType == PROJECT_TYPE_CUSTOM) {
            goCreateProjectStep(6);
        } else if (activeProjectType == PROJECT_TYPE_ANALYTICS) {
            goAnalyticsProjectStep(7);
        }
    });



    if ($.browser.msie && ($.browser.version == "7.0")) {
        $('.stepForth .form dl.radioList dd .radio').css('margin-top', '-4px');
        $('.stepForth .form dl.radioList dd').css('margin-left', '28px');
        $('.stepFirst .radioBox').css('margin-left', '0px');
        $('.stepFirst .radioBox .radio').css('margin-top', '-3px');
        $('.selectUserCheck,.selectUser').css('margin-top', '-3px');
    }

    //all
    $('.stepFifth:visible .applyForAll td:eq(1) .selectUser').live('click',function() {
        if ($(this).attr('checked')) {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
                $(this).find('td:eq(1) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
                $(this).find('td:eq(1) .selectUser').attr('checked', '');
            });
        }
    });

    $('.stepFifth:visible .applyForAll td:eq(2) .selectUser').live('click',function() {
        if ($(this).attr('checked')) {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
                $(this).find('td:eq(2) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
                $(this).find('td:eq(2) .selectUser').attr('checked', '');
            });
        }
    });

    $('.stepFifth:visible .applyForAll td:eq(3) .selectUser').live('click',function() {
        if ($(this).attr('checked')) {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
                $(this).find('td:eq(3) .selectUser').attr('checked', 'checked');
            });
        } else {
            $('.stepFifth:visible .checkPermissions tr').each(function() {
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
    $(".stepSecond .projectName input.text").inputTips("Enter your Project Name here");
    // input limited text
    $(".stepSecond .projectName input.text").limitedText({
        "max" : 60
    });

    $(".stepSecond .descProject textarea").inputTips("");
    // input limited text
    $(".stepSecond .descProject textarea").limitedText({
        "max" : 255
    });


    $(".stepSecond .projectName input.text,.stepSecond .descProject textarea").keyup(function() {
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
        type : 'POST',
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
                $("#selectionConfirmationModal .modalContainer dt").append('<dd><span>' + value.copilotHandle + '</span><a href="https://www.topcoder.com/tc?module=MemberProfile&amp;cr='
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
        stepsChoices['require-copilot-step'] = 'chooseCopilot';
        if (activeProjectType == PROJECT_TYPE_CUSTOM) {
            goCreateProjectStep(5);
        } else if (activeProjectType == PROJECT_TYPE_ANALYTICS){
            goAnalyticsProjectStep(3);
        }
    })
    $("#createPostingModal .buttonArea a.button6").click(function() {
        // $.cookie("step4", "createCopilot");
        stepsChoices['require-copilot-step'] = 'createCopilot';
        if (activeProjectType == PROJECT_TYPE_CUSTOM) {
            goCreateProjectStep(5);
        } else if (activeProjectType == PROJECT_TYPE_ANALYTICS){
            goAnalyticsProjectStep(3);
        }
    });

    // choose copilot
    $(".stepForth .chooseYourCopilot").click(function() {
        if (widgetResult == undefined) {
            showErrors("Please wait for the loading of the copilot data");
        }

        if(stepsChoices['require-copilot-step'] != 'chooseCopilot' || stepsChoices['copilots'].length == 0) {
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
        activeProjectType = PROJECT_TYPE_CUSTOM;
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
        $(this).siblings('.errorText').hide();
        $(this).siblings('.errorIcon').hide();
        $(this).removeClass('errorInput');
    });

    // presentation project step 5: Files
    $(".stepFifth .noMediaAsset").show();

    $(window).resize(function(){
        $('#customFileDescription').width($('.addMoreParts .topRowWrapper').width() - 112);
        $('.stepFifth .browserInput').width($('.stepFifth .browserParts .topRowWrapper').width() - 26);
        $('#descriptionInput').width($('.stepForth .otherTypeAreaInputBox').width() - 450);
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
    $('.stepFifth .mediaAssetTableWrapper .uploadFileButton').click(function(){
        var fileVal = $('.stepFifth .browserInput').val();
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
        if($('.stepFifth .categoryWrapper input:radio:checked').length == 0){
            $('.stepFifth .categoryWrapper').addClass('errorInput');
            $('.stepFifth .addMoreParts .subButtonBox .errorText').show();
            $('.stepFifth .addMoreParts .subButtonBox .errorIcon').show();
            refuse = true;
        }
        if(descriptTextVal.length == 0 || descriptTextVal == 'Instructions (tell us how to use the uploaded file)') {
            $('#customFileDescription').addClass('errorInput');
            $('.stepFifth .addMoreParts .subButtonBox .errorText').show();
            $('.stepFifth .addMoreParts .subButtonBox .errorIcon').show();
            refuse = true;
        }

        var categoryVal = $('.stepFifth .categoryWrapper input:radio:checked').val();

        if(!refuse){
            description = categoryVal + " " + descriptTextVal;
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
            if($(this).val() == 'Instructions (tell us how to use the uploaded file)'){
                $(this).val("");
            }
        })
        .blur(function(){
            if($(this).val().length == 0){
                $(this).val("Instructions (tell us how to use the uploaded file)");
            }
        });

    //delete button
    $(".stepForth .otherTypeTableWrapper .deleteRowIcon").live("click",function(){
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
    $('.stepForth .otherTypeAreaInputBox .addFileType').click(function(){
        var fileVal = $.trim($('#fileType').val());
        var extensionDescriptVal = $.trim($('#descriptionInput').val());
        var refuse = false;
        var cache;
        if(fileVal.length == 0){
            $('#fileType').addClass('errorInput');
            $('.stepForth .otherTypeAreaInputBox .fileTypeWrapper .errorText').show();
            $('.stepForth .otherTypeAreaInputBox .fileTypeWrapper .errorIcon').show();
            refuse = true;
        }
        if(extensionDescriptVal.length == 0){
            $('#descriptionInput').addClass('errorInput');
            $('.stepForth .otherTypeAreaInputBox .descriptionInputWrapper .errorText').show();
            $('.stepForth .otherTypeAreaInputBox .descriptionInputWrapper .errorIcon').show();
            refuse = true;
        }
        if(!refuse){
            fileVal = htmlEncode(fileVal);
            extensionDescriptVal = htmlEncode(extensionDescriptVal);
            cache = $('<tr/>');
            cache.append('<td class="leftTd"><div class="mediaRow"><span class="rowExtension"><strong>Extension:</strong><span class="strExtension">.'+fileVal+'</span><strong class="textLabel">Description:</strong><span class="strDesc">'+extensionDescriptVal+'</span></span></div></td><td class="deleteTd"><a href="javascript:;" title="delete" class="deleteRowIcon"></a></td>');
            cache.insertBefore($('.stepForth .otherTypeTableWrapper table .tfooter'));
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
    $('.stepForth .deliverablesTypeItem .typeContainer').click(function(){
        if($(this).parent().find(':checkbox').attr('checked')){
            $(this).parent().removeClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','');
        }else{
            $(this).parent().addClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','checked');
            $('.stepForth .errorStatusTips').css('display','none');
        }
    });

    //add hover status for deliverablesTypeItem
    $('.stepForth .deliverablesTypeItem label').click(function(){

        if($(this).parent().find(':checkbox').attr('checked')){
            $(this).parent().parent().removeClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','');
        }else{
            $(this).parent().parent().addClass('itemHover');
            $(this).parent().find(':checkbox').attr('checked','checked');
            $('.stepForth .errorStatusTips').css('display','none');
        }
    });

    //validation for check box
    $('.stepForth .deliverablesTypeItem :checkbox').click(function(){

        if($(this).attr('checked')){
            $(this).parent().parent().addClass('itemHover');
            $('.stepForth .errorStatusTips').css('display','none');
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

    /*water mark for input fields*/
    $(".newProjectStep input").live("focus blur",  function(e) {
        if (e.type == "focusin") {
            $(this).removeClass("waterMark");
            if($.trim($(this).val())==$(this).attr("title")){
                $(this).val("");
            }
        }
        else{
            if($.trim($(this).val())==""){
                $(this).val($(this).attr("title"));
                $(this).addClass("waterMark");
            }
        }
    });
    
    initNewProjectDocumentsUpload();
});
