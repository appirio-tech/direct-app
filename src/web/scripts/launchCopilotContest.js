/**
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * This javascript file is used to render elements to launch copilot contest page, and handle
 * events.
 * 
 * Changes in 1.1 (TCCC-2926 and TCCC-2900 and TCCC-2965):
 * - Add support to restrict the max characters of private description and public description.
 * - Fix bug TCCC-2900.
 * - Add support to allow setting customer contest prizes.
 * Version 1.2 Direct Improvements Assembly Release 2 Assembly change note
 * - Add previw button for the copilot creation page.
 *
 * Version 1.3 Release Assembly - TC Direct UI Improvement Assembly 3 change note
 * - remove event click for conditions modal window, it's not needed
 *
 * Version 1.4 TC Cockpit Post a Copilot Assembly 1 change note
 * - Apply to new prototype.
 * 
 * Changes in version 1.5 (TC Cockpit Post a Copilot Assembly 2):
 * - Finished the logic in step 2 and 3.
 *
 * Version 1.6 Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp change note:
 * - integrate the new cockpit tinyMCE editor
 *
 * Version 1.6.1 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
 * - check whether the public description is filled under step 1 of creating copilot posting
 * 
 * @author duxiaoyang, GreatKevin, TCSASSEMBLER
 * @version 1.6.1
 */

var roundMinutes = function(date) {

    date.setHours(date.getHours() + Math.round(date.getMinutes()/60));
    date.setMinutes(0);

    return date;
}

$(document).ready(function(){

    setupTinyMCE('allDescription', 12000);
    setupTinyMCE('privateDescription', 4096);

    initPanel();

	/**
     * Handle add new project button click event.
     */
    $('#addNewProject').click(function(){
        clearAddNewProjectForm();
        modalLoad("#addNewProjectModal");
        $('#addNewProjectModal').find('input[name="newProjectName"]').focus();
    });
    
    /**
     * Initiate date pick.
     */
    var postTime = Date.parse($.trim($("#currentCopilotDate").val()));
    postTime.add({hours:24});
    postTime = roundMinutes(postTime);

    if($('.date-pick').length > 0){
        $(".date-pick").datePicker().val(postTime.toString('MM/dd/yyyy')).trigger('change');
        var hours = postTime.toString('HH');
        $("#startTime option:eq(" + parseInt(hours) + ")").attr('selected', 'selected');
        $(".startEtSelect .selectedTxt").text(hours + ":00");
    }

    /**
     * Represent prev popup window.
     */
    var prevPopup = null;
    
    /**
     * Initiate TB section.
     */
    $('.TB_overlayBG').css("opacity", "0.6");
    $('#TB_HideSelect').css("opacity", "0");
    $('#TB_overlay').hide();
    $('#TB_window_custom').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window_custom").css('height')) / 2))});
    $('#TB_overlay').bgiframe();
    //$('#TB_window').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});
    
    /**
     * Handle TB window close button click event.
     */
    $('#TB_closeWindowButton').click(function(){
        $('#TB_overlay').hide();
        $('#TB_window').hide();                                          
    });

    /**
     * Bind save as draft action to save button click event.
     */
    $("#saveDraftButton").click(function() {
        saveAsDraft();
    });
    
    /**
     * Bind submit action to submit button click event.
     */
    $("#submitButton").click(function() {
        submitCompetition();
    });
    
	/**
     * Bind preview action to preview button click event.
     */
    $("#previewButton").click(function() {
        previewContest();
    });    
    
    changeStep(1);
    
    $(".stepContainer .prevStepButton").click(function() {
        changeStep(currStep - 1);
    });
    
    $(".stepContainer .nextStepButton").click(function() {
        changeStep(currStep + 1);
    });

    $(".stepBar li span.istatus a").click(function() {
        var c = $(this).attr("id").substring("stepLink_".length);
        changeStep(parseInt(c));
    });    
    
	$(".proceedRadio").click(function() {
        $(this).parent().parent().find(".amountText").attr("disabled", false);
    });
    $(".useAmountRadio").click(function() {
        $(this).parent().find(".amountText").attr("disabled", false);
    });
    
    var currentUploadInput;
    
	$(".customUpload").css({filter:"alpha(opacity:0)",opacity:"0"});
	$(".customUpload").live("change",function(){
		$(this).siblings(".uploadInput").val($(this).val());
	});
    
    
    $(".customUpload").siblings(".button6").click(function() {
        currentUploadInput = $(this).parent();
        $(this).siblings(".customUpload").trigger('click');
        $(this).siblings(".uploadInput").val($(this).siblings(".customUpload").val());
    });
    /*
	$(".customUpload").siblings(".text").click(function(){
        currentUploadInput = $(this).parent();
        $(this).siblings(".customUpload").trigger('click');
        $(this).val($(this).siblings(".customUpload").val());
	});
    
	if ($.browser.msie) {
		$(".customUpload").css("width","100px");
    }  */  
    
	$(".get-a-copilot .customUploadWrap").hover( 
		function(){
			$(this).children(".draft").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right bottom");
		},
		function(){
			$(this).children(".draft").css("background-position","left top");
			$(this).children(".draft").children("span.left").css("background-position","left top");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right top");
		}
    );
    
    // copilot contest uploader
    var swUploader =
        new AjaxUpload(null, {
            action: ctx + '/launch/documentUpload',
            name : 'document',
            responseType : 'json',
            onSubmit : function(file , ext){
                //software document
                swCurrentDocument['fileName'] = file;

                swUploader.setData(
                {
                    studio:false,
                    contestFileDescription:swCurrentDocument['description'],
                    documentTypeId:swCurrentDocument['documentTypeId']
                    }
                );

                modalPreloader();
            },
            onComplete : function(file, jsonResult){ handleJsonResult(jsonResult,
                    function(result) {
                        var documentId = result.documentId;
                        swCurrentDocument['documentId'] = documentId;
                        swDocuments.push(swCurrentDocument);

                        swCurrentDocument = {};

                        currentUploadInput.parent().find(".uploadBtnRed").addClass("hide");
                        currentUploadInput.find(".draft").addClass("hide");
                        currentUploadInput.find(".uploadInput").unbind("click");
                        currentUploadInput.parent().find(".removeButton").removeClass("hide");
                        currentUploadInput.append("<input type='hidden'>");
                        currentUploadInput.find("input[type=hidden]").val(documentId);
                        
                        currentUploadInput.parent().find(".removeButton").click(function() {
                            swRemoveDocument(documentId);
                        });
                        
                        modalClose();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                        modalClose();
                    });
            }
        }, false);    
    
    $(".uploadBtnRed").live("click", function() {
        swUploader.setInput($(this).parent().prev().find("input[type=file]").get(0));
        
        var fileName = swUploader._input.value;
       	var description = $(this).parent().find("#fileDescription").val();

        var errors = [];

        if(!checkRequired(fileName)) {
            errors.push('No file is selected.');
        }

        if(!checkRequired(description)) {
            errors.push('File description is empty.');
        }

        if(errors.length > 0) {
            showErrors(errors);
            return false;
        }
        
        currentUploadInput = $(this).prev();

        swCurrentDocument['description'] = description;
        swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
        swUploader.submit();
    });
	
	
	/* add button */
	$('.uploadContent .addButton').live("click",function(){
		var inputLine = $(".uploadCopySource").html();
        
		$(this).parent().parent().append(inputLine);
        
        var lastP = $(this).parent().parent().find("p").last();
		lastP.find(".customUploadWrap").hover(
            function(){
                $(this).children(".draft").css("background-position","left bottom");
                $(this).children(".draft").children("span.left").css("background-position","left bottom");
                $(this).children(".draft").children("span.left").children("span.right").css("background-position","right bottom");
            },
            function(){
                $(this).children(".draft").css("background-position","left top");
                $(this).children(".draft").children("span.left").css("background-position","left top");
                $(this).children(".draft").children("span.left").children("span.right").css("background-position","right top");
            }
        );
        
        lastP.find(".customUploadWrap .uploadInput, .customUploadWrap .draft").click(function() {
            currentUploadInput = $(this).parent();
            currentUploadInput.find("input[type=file]").click();
        });        
        
	}); 
    
	/* remove  button */
	$('.uploadContent .removeButton').live("click",function(){
		$(this).parent().prev().remove();
		$(this).parent().remove();
	}); 
	
    $(".postCopilotStep1 #contestName").val('');
    $(".postCopilotStep1 textarea").val('');
    
    $(".postCopilotStep6 .edit").click(function() {
        var name = $(this).attr("name");
        changeStep(parseInt(name.substring("editStep".length)));
    });

	$('.amountText').bind('keyup',function() {
		var value = $(this).val();
        
		if(!checkRequired(value) || !checkNumber(value)) {
			errors.push("The amount is invalid.");
            $(this).parent().find('.prizeInfo').html("");
		} else {
			var secondPrize = parseFloat(value / 2);
			$(this).parent().find('.prizeInfo').html(secondPrize);
		}
    });

    setupContestFee($("#billingProjects").val());

    $("#billingProjects").change(function(){
        var billingAccountId = $(this).val();
        if (billingAccountId > 0) {
            getContestFeesForBillingProject(billingAccountId);
        }
        setupContestFee(billingAccountId);
    });
});

(function($) {
    /**
     * Function to style the input file.
     */
   $.fn.styleingInputFile = function(){
       this.each(function(){
           var fileInput = $(this);
           var parentWrap = fileInput.parents(".attachFile");
           var wrapOffset = parentWrap.offset();
           fileInput.attr("style","opacity: 0;-moz-opacity: 0;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);")
           parentWrap.mousemove(function(event){
               fileInput.blur();
               var relatedX = event.pageX - wrapOffset.left - fileInput.width() + 12;
               var relatedY = event.pageY - wrapOffset.top;
               fileInput.css("left",relatedX + "px");
               fileInput.css("top","0px");
           });
           fileInput.change(function(){
               $(this).blur();
               parentWrap.find(".fakeText input").val($(this).val());
           })
       })
   }
})(jQuery);

/**
 * Adds a new project.
 */
function addNewProject() {
    var projectName = $('#addNewProjectModal').find('input[name="newProjectName"]').val();
    var projectDescription = $('#addNewProjectModal').find('textarea[name="newProjectDescription"]').val();

    var errors = [];

    if (!checkRequired(projectName)) {
        errors.push('Project name is empty.');
    }

    if (!checkRequired(projectDescription)) {
        errors.push('Project description is empty.');
    }

   if(errors.length > 0) {
       showErrors(errors);
       $("#modal-background").hide();
       return;
   }


    $.ajax({
        type: 'POST',
        url:  "../launch/createProject",
        data: {'projectName':projectName,
            'projectDescription':projectDescription},
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    var projectData = result;
                    $("<option/>").val(projectData.projectId).text(projectData.name).attr("selected", true).appendTo("#projects");
                    //$('#projects').resetSS();
                    //$('#projects').getSetSSValue(projectData.projectId);

                    modalCloseAddNewProject();
                    showSuccessfulMessage('Project <span class="messageContestName">' + projectData.name + '</span> is created successfully.');

                },
                function(errorMessage) {
                    modalCloseAddNewProject();
                    showServerError(errorMessage);
                });
        }
    });
};

/**
 * The current step page.
 */
var currStep = 1;

/**
 * The class of step pages.
 */
var stepContainerClasses = [
    "",
    "stepFirst stepContainer",
    "stepSecond stepContainer",
    "stepThird stepContainer budget",
    "stepForth stepContainer",
    "stepFifth stepContainer",
    "stepSix stepContainer summary",
    "stepSeven stepContainer summary"
];

/**
 * The tile of step pages.
 */
var stepTiles = [
    "",
    "Basic",
    "Copilot Experience",
    "Budget",
    "Schedule",
    "Billing"
];

mainWidget.competitionType = "SOFTWARE";

/**
 * Validate step inputs.
 *
 * @return true if pass, false otherwise
 */
function validateStepInputs() {
    var errors = [];
    
    switch(currStep) {
        case 1:
            var contestName = $('input#contestName').val();
            var tcProjectId = parseInt($('select#projects').val());
            var allDescription = tinyMCE.get('allDescription').getContent();
            var privateDescription = tinyMCE.get('privateDescription').getContent();

            
            if(tcProjectId < 0) {
                errors.push('Project name could not be empty.');
            } 
            if(!checkRequired(contestName)) {
                errors.push('Copilot Opportunity Title could not be empty.');
            }
            if (!checkRequired(allDescription)) {
                errors.push('Public Description cannot be empty.');
            }

            
            if(errors.length > 0) {
                break;
            }
            
            mainWidget.softwareCompetition.assetDTO.name = contestName;
            mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = $("#projects option[value="+ tcProjectId +"]").text();
            mainWidget.softwareCompetition.projectHeader.tcDirectProjectId = tcProjectId;
            mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = allDescription;
            mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription = privateDescription;
            mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);
            
            $("#projectNameSummary").html(mainWidget.softwareCompetition.projectHeader.tcDirectProjectName);
            $("#contestNameSummary").html(contestName);
            $("#publicDescriptionSummary").html(allDescription + "&nbsp;");
            $("#specificDescriptionSummary").html(privateDescription + "&nbsp;");
            
            $("#uploadFilesSummary").html("");
            $.each(swDocuments, function() {
                $("#uploadFilesSummary").append('<li><a href="javascript:;" class="uploadDoc">' + this.fileName + '</a></li>');
            });
            
            break;
        case 2:
            var typeSelected = false;
        	var copilotTypesSummary = "";
        	var copilotTypes = [];
            $('.postCopilotStep2 .rowItem input:checked').each(function(){
            	typeSelected = true;
            	copilotTypes.push(new com.topcoder.direct.ProjectCopilotType($(this).val(), $(this).attr('id'), $(this).attr('id')));
            	if (copilotTypesSummary.length > 0) {
            		copilotTypesSummary = copilotTypesSummary + ", ";
            	}
            	copilotTypesSummary = copilotTypesSummary + $(this).attr('id');
            });
            removeCopilotExtraInfo(OTHER_EXTRA_INFO_TYPE);
            var other = $('.postCopilotStep2 .rowItem input#other').val();
            if (other.length > 0) {
            	typeSelected = true;
            	var infoType = new com.topcoder.direct.CopilotContestExtraInfo();
            	infoType.type = OTHER_EXTRA_INFO_TYPE;
            	infoType.value = other;
            	mainWidget.softwareCompetition.projectHeader.copilotContestExtraInfos.push(infoType);
            	if (copilotTypesSummary.length > 0) {
            		copilotTypesSummary = copilotTypesSummary + "<br/>";
            	}
            	copilotTypesSummary = copilotTypesSummary + other;
            }
            if (!typeSelected) {
            	errors.push("There's no selected type(s) of projects! You must select 1(one) before continue to next step.");
            	break;
            }
            mainWidget.softwareCompetition.projectHeader.projectCopilotTypes = copilotTypes;
            $("#copilotTypes").html(copilotTypesSummary);
            
            break;
        case 3:
            if ($('.postCopilotStep3 .rowItem input:checked').length == 0) {
            	errors.push('Please select your budget options for this contest.');
            	break;
            }
        	removeCopilotExtraInfo(BUDGET_EXTRA_INFO_TYPE);
            if ($('.postCopilotStep3 .rowItem input#haveBudget:checked').length > 0) {
            	var budget = $('.postCopilotStep3 .rowItem input#budget').val();
            	if (budget.length == 0 || (budget.length == 1 && budget.charAt(0) == '$')) {
            		errors.push('Please enter your budget for this contest.');
            		break;
            	}
            	var infoType = new com.topcoder.direct.CopilotContestExtraInfo();
            	infoType.type = BUDGET_EXTRA_INFO_TYPE;
            	if (budget.charAt(0) == '$') {
            		infoType.value = budget.substring(1);
            	} else {
            		infoType.value = budget;
            		budget = "$" + budget;
            	}
            	if (!checkRequired(infoType.value) || !checkNumber(infoType.value)) {
            		errors.push('The budget is invalid.');
            		break;
            	}
            	mainWidget.softwareCompetition.projectHeader.copilotContestExtraInfos.push(infoType);
            	$("#copilotContestBudget").html(budget);
            } else {
        		$("#copilotContestBudget").html('N/A');
        	}
        	
            break;
        case 4:
            if ($(".lineItem.inputItem .postFrame.hide").length == 2) {
                errors.push("You haven't posted the opportunity to the Copilots.");
            } else {
                var startDate;
                var lineItem;
                var amount;
                var startNow;
                
                if ($(".lineItem.amountItem .postFrame").hasClass("hide")) {
                    // do it later
                    startDate = getDateByIdPrefix('start');
                    
                    lineItem = $(".lineItem.dateItem .postFrame");
                    startNow = false;
                    
                    if (startDate.compareTo(new Date()) < 0) {
                        errors.push("The start date should after current time.");
                    }
                } else {
                    // post now
                    startDate = new Date();
                    startDate.setMinutes(startDate.getMinutes() + 5);
                    
                    lineItem = $(".lineItem.amountItem .postFrame");
                    startNow = true;
                }
                
                if (lineItem.find(".proceedRadio").attr('checked')) {
                    var feeObject = softwareContestFees[29];
                    var contestCost = getContestCost(feeObject, 'high'); 
                    var firstPlaceCost = contestCost.firstPlaceCost;
                            
                    amount = firstPlaceCost;
                } else if (lineItem.find(".useAmountRadio").attr('checked')) {
                    var value = lineItem.find(".newAmountText").val();
                    if(!checkRequired(value) || !checkNumber(value)) {
                        errors.push("The amount is invalid.");
                    }
                    
                    amount = parseFloat(value);
                } else {
                    errors.push("Please set the amount.");
                }
                
                if(errors.length > 0) {
                    break;
                }
                
                mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
                mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);  
                
                var projectHeader = mainWidget.softwareCompetition.projectHeader;
                projectHeader.setFirstPlaceCost(amount);
                projectHeader.setSecondPlaceCost(parseFloat(amount/2));

                // set all prize to 0 except first place cost
                projectHeader.setReviewCost(0);
                projectHeader.setReliabilityBonusCost(0);
                projectHeader.setDRPoints(0);
                projectHeader.setMilestoneBonusCost(0);
                projectHeader.setAdminFee(0);
                projectHeader.setSpecReviewCost(0);                
                
                var prizes = [];
                prizes.push(new com.topcoder.direct.Prize(1, amount, CONTEST_PRIZE_TYPE_ID, 1));
                prizes.push(new com.topcoder.direct.Prize(2, parseFloat(amount/2), CONTEST_PRIZE_TYPE_ID, 2));
                mainWidget.softwareCompetition.projectHeader.prizes = prizes;
                
                if (startNow) {
                    $("#startTimeSummary").html("Post Now");
                } else {
                    $("#startTimeSummary").html("Post at " + startDate);
                }
                
                $("#amountSummaryFirst").html("$" + amount);
				$("#amountSummarySecond").html("$" + parseFloat(amount / 2));
            }
            break;
        case 5:
            var billingProjectId = parseInt($('select#billingProjects').val()); 
            mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
            
            if (billingProjectId > 0) {
                $("#billingSummary").html($("#billingProjects option[value="+ billingProjectId +"]").text());
            } else {
                $("#billingSummary").html("");
            }
            var amount = mainWidget.softwareCompetition.projectHeader.prizes[0].prizeAmount;
            $("#contestFeeTotal").html("$" + parseFloat(mainWidget.softwareCompetition.projectHeader.getAdminFee()).toFixed(1))
            $("#amountSummaryTotal").html("$" + (parseFloat(amount * 3 / 2) + parseFloat(mainWidget.softwareCompetition.projectHeader.getAdminFee())).toFixed(1));

            break;
            
    }

    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }    
    return true;
}

/**
 * Remove copilot contest extra info with specified type from project header. This function is needed because user may
 * modify the extra info several times and It is impossible to remove all such items because both step 2 and 3 can add
 * such extra info.
 *
 * @param infoType the type of the copilot contest extra info to remove.
 */
function removeCopilotExtraInfo(infoType) {
	if (typeof infoType == "undefined" || typeof infoType.id == "undefined") {
		return;
	}
	var length = mainWidget.softwareCompetition.projectHeader.copilotContestExtraInfos.length;
	for (var i = 0; i < length; ++i) {
		var info = mainWidget.softwareCompetition.projectHeader.copilotContestExtraInfos.shift();
		if (info.type.id == infoType.id) {
			return;
		} else {
			mainWidget.softwareCompetition.projectHeader.copilotContestExtraInfos.push(info);
		}
	}
}

/**
 * Show errors.
 *
 * @param errors the errors to show
 */
function showErrors(errors) {
    $("#errortModal .modalContainer dl").hide().find("dd").remove();
    $("#errortModal .modalContainer p").hide().html("");
    
    if (typeof errors == "string") {
        errors = [errors];
    }
    
    if(errors.length > 0) {
        if (errors.length > 1) {
            $.each(errors, function(index, item) {
                $("#errortModal .modalContainer dl").append("<dd>" + item + "</dd>");
            });
            $("#errortModal .modalContainer dl").show();
        } else {
            $("#errortModal .modalContainer p").html(errors[0]);
            $("#errortModal .modalContainer p").show()
        }
        addresscloseModal();
        addressLoadModal('#errortModal');
    }
    
}

/**
 * Move to targeted step.
 *
 * @param target the target step
 * @param forumUrl the forum id, optional
 */
function changeStep(target, forumUrl) {
    if (target > currStep) {
        if (!validateStepInputs()) {
            return;
        }
    }

    $(".stepContainer .stepDiv").hide();
    $(".stepContainer .stepDiv.postCopilotStep" + target).show();
    
    currStep = target;
    
    if (currStep == 1) {
        $(".stepContainer .prevStepButton").hide();
    } else {
        $(".stepContainer .prevStepButton").show();
    }
    
    if (currStep == 4) {
        var feeObject = softwareContestFees[29];
        var contestCost = getContestCost(feeObject, 'high'); 
        var firstPlaceCost = contestCost.firstPlaceCost;
        
        $(".defaultFirstPlaceCost").html(firstPlaceCost);
		$(".defaultSecondPlaceCost").html(parseInt(firstPlaceCost / 2, 10));
		$(".defaultTotalPlaceCost").html(parseInt(firstPlaceCost * 3 / 2, 10));
    }
    
    if (currStep == 6) {
        $(".stepContainer .topBar, .stepContainer .BottomBar").hide();
        
        if (mainWidget.softwareCompetition.projectHeader.getBillingProject() > 0) {
            $(".stepNav .submitButton").show();
        }
        $(".buttonBottom .viewContest").show();
    } else {
        $(".stepContainer .topBar, .stepContainer .BottomBar").show();
        $(".stepNav .submitButton").hide();
        $(".buttonBottom .viewContest").hide();
    }
    
    if (currStep == 7) {
        $(".stepBar").hide();
        
        $(".backDashboardBtn").unbind("click");
        $(".stepTitle").append($(".backDashboardBtn"));
        $(".stepNav").hide();
        $(".BottomBar, .topBar").hide();
        $(".buttonBottom .viewContest").show();
        
        $(".buttonBottom .viewContest").attr("href", "https://www.topcoder.com/tc?module=ProjectDetail&pj=" + mainWidget.softwareCompetition.projectHeader.id);
		$(".buttonBottom .viewProjectOverview").show();
		$(".buttonBottom .viewProjectOverview").attr("href", "/direct/projectOverview?formData.projectId=" + mainWidget.softwareCompetition.projectHeader.tcDirectProjectId);
        if (forumUrl) {
            $(".buttonBottom .forumIcon").attr("href", forumUrl);
            $(".buttonBottom .forumIcon").show();    
        }
        
        $(".buttonBottom .viewContest span").removeClass("noBorderRight");
    } else {
		$(".buttonBottom .viewProjectOverview").hide();
        $(".buttonBottom .forumIcon").hide();
        $(".buttonBottom .viewContest span").addClass("noBorderRight");
    }

    
    $(".stepBar li span.istatus").each(function(index, item) {
        $(item).removeClass("complete active inext");
        $(item).find("a, .stepText").hide();
       
        if (index + 1 < currStep) {
            $(item).addClass("complete");
            $(item).find("a").show();
        } else if (index + 1 == currStep) {
            $(item).addClass("active");
            $(item).find("a").show();
        } else {
            $(item).addClass("inext");
            $(item).find(".stepText").show();
        }
    });
    
    $(".stepContainer").attr("class", stepContainerClasses[currStep]);

    if (currStep < 6) {
		$(".stepTitle h3").removeClass("infoIcon");
        $(".stepTitle h3").html('<span class="small">' + currStep + '</span>' + stepTiles[currStep]);
    } else if (currStep == 6) {
        $(".stepTitle h3").html('Summary').attr("class", "infoIcon");
    } else {
        $(".stepTitle h3").html('Confirmation').attr("class", "tickIcon");
    }
    $(window).resize();
}
    
/**
 * Initiate the panel, and set some parameters.
 */
function initPanel() {
    mainWidget.softwareCompetition.projectHeader.projectCategory={};
    mainWidget.softwareCompetition.projectHeader.projectCategory.id = 29;
    mainWidget.softwareCompetition.projectHeader.projectCategory.name = "Copilot Posting";
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType={};
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = 2;
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = "Application";
   
    mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = false;
    mainWidget.softwareCompetition.assetDTO.directjsDesignId = -1;   
      
    mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    
    // update prize fields
    //updateSoftwarePrizes();
    fillPrizes();
};

function setupContestFee(billingProjectId) {
    var contestBillingFee = 0;
    var contestFeePercentage = null;

    mainWidget.softwareCompetition.projectHeader.properties['Contest Fee Percentage'] = 0;

    if (billingFees[billingProjectId] != null) {
        var fees = billingFees[billingProjectId];

        for(var i = 0; i < fees.length; ++i) {
            if(fees[i].contestTypeId == 29) {
                contestBillingFee = fees[i].contestFee;
            }
        }
    }

    if (billingFeesPercentage[billingProjectId] != null) {
        contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;

        if (contestFeePercentage != null && contestFeePercentage > 0) {
            var totalAmount = 0;
            $.each(mainWidget.softwareCompetition.projectHeader.prizes, function(index, entry){
                totalAmount += entry.prizeAmount;
            });
            contestBillingFee = (totalAmount * contestFeePercentage).toFixed(2);
            mainWidget.softwareCompetition.projectHeader.properties['Contest Fee Percentage'] = contestFeePercentage;
        }
    }

    mainWidget.softwareCompetition.projectHeader.setAdminFee(contestBillingFee);
}

/**
 * Handle save as draft action.
 */
function saveAsDraft() {
    if (!validateStepInputs()) {
        return;
    }
    
    // construct request data
    var request = saveAsDraftRequestSoftware();
    
    if (!request['assetDTO']['productionDate']) {
        delete request['assetDTO']['productionDate'];
    }
    if (!request['startDate']) {
        delete request['startDate'];
    }

    request['projectHeader'].properties['Contest Fee Percentage'] = mainWidget.softwareCompetition.projectHeader.properties['Contest Fee Percentage'];
    request['projectHeader'].properties['Admin Fee'] = mainWidget.softwareCompetition.projectHeader.getAdminFee();
    
    $.ajax({
        type: 'POST',
        url: '../launch/saveDraftContest',
        data: request,
        cache: false,
        dataType: 'json',
        success: handleCopilotContestSaveAsDraftResult,
        beforeSend: beforeAjax,
        complete: afterAjax   
    });
};

/**
 * Handle result of save as draft action. 
 * 
 * @param jsonResult the json result to handle
 */
function handleCopilotContestSaveAsDraftResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) { 
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        var messageToShow = "Your Copilot Posting <span class='messageContestName'>" + contestName + "</span>";
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
            messageToShow += " has been saved as draft successfully.";
        } else {
            messageToShow += " has been updated as draft successfully.";
        }
        
        //update paid fee
        mainWidget.softwareCompetition.paidFee = result.paidFee;

        $('#saveAsDraft dt').html(messageToShow);
        $('#draftPanelTrigger').overlay().load();
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
};

/**
 * Submit competition. 
 */
function submitCompetition() {
    var request = saveAsDraftRequestSoftware();
    request['startDate'] = formatDateForRequest(mainWidget.softwareCompetition.assetDTO.directjsProductionDate);
    request['activationFlag'] = true;
    
    $.ajax({
        type: 'POST',
        url: '../launch/saveDraftContest',
        data: request,
        cache: false,
        dataType: 'json',
        success: handleCopilotContestActivateResult,
        beforeSend: beforeAjax,
        complete: afterAjax   
    });
}

/**
 * Handle result of submit contest action. 
 * 
 * @param jsonResult the json result to handle
 */
function handleCopilotContestActivateResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
        } 

        changeStep(7, result.forumUrl);
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });   
};

/**
 * Close tb box. 
 */
function closeTBBox() {
    $('#TB_overlay').hide();
    $('#TB_window').hide();
	$('#TB_window div').remove();
}
