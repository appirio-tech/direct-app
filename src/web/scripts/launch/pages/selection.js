/**
 * Contest Selection Page.
 *
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.1 TC Direct- Software Creation Update Assembly changes notes
 * - Update method validateFieldsContestSelectionSoftware to get copilotUserId and name from copilot dropdown
 *
 * Version 1.2 Direct Replatforming Release 4 changes notes
 * - Add support to set Maximum Submissions to 5 for studio contest.
 *
 * Version 1.3 (Release Assembly - TopCoder Studio CCA Integration) change notes:
 * - Add support CCA for studio contest.
 *
 * Version 1.4 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 *
 * Version 1.5 Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match
 * - Update method validateFieldsContestSelection to support algorithm contest
 * - Add method validateFieldsContestSelectionAlgorithm
 * - Update method continueContestSelection to support algorithm contest
 *
 * Version 1.6 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 * 1) Remove fillPrizes() from validateFieldsContestSelectionAlgorithm(), it does not have the prize structure the same
 * as software contest.
 *
 * Version 1.7 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * - Updates to support setting milestone on contest creation
 *
 * Version 1.8 (Module Assembly - TC Cockpit Launch Code contest)
 * - Disable checking on milestone for Code contest
 * - Hide 2nd place prize, DR, reliability, spec review payment in the next overview page. Display multiple Prizes.
 * - Check custom prize type for Code contest by default
 *
 * Version 1.9 (Module Assembly - TC Cockpit Launch F2F contest)
 * - Disable checking on milestone for F2F contest
 * - Hide 2nd place prize, DR, reliability, spec review payment in the next overview page.
 * - Check custom prize type for F2F contest by default
 *
 * Version 2.0 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
 * - Add support for setting checkpoint end date and submission end date for software challenge
 *
 * Version 2.1 (F2F - TC Cockpit Update Bug Hunt type)
 * - Only display 1st place cost for Bug hunt (like First2Finish), but display spec review cost for bug hunt.
 *
 * Version 2.2 (TopCoder Direct - Draft Challenge Creation/Saving Prompt)
 * - Add the save challenge confirmation
 *
 * Version 2.3 (Topcoder - Ability To Set End Date For Registration Phase and Submission Phase)
 * - Added registration and submission end date/time for design F2F
 * - Added registration length for studio contests (excluding design F2F)
 * - All software contests support modification of registration and submission end date/time
 *
 * Version 2.4 (Topcoder - Add Basic Marathon Match Creation And Update In Direct App)
 * - Update for MM registraion end date and validation
 *
 * Version 2.5 (Topcoder - Support Points Prize Type For Challenges)
 * - Add support for points prize type
 *
 * Version 2.6 (Topcoder - Add Effort Days Field)
 * - Add support for effort hours estimate
 *
 * @version 2.6
 * @author bugbuka, Veve, GreatKevin, TCSCODER
 */
$(document).ready(function () {
    initContestNamesFromDesign();

    $('#devOnlyCheckBox').bind('click', function () {
        if ($('#devOnlyCheckBox').is(':checked')) {
            $('#contestName').show();
            $('#contestNameFromDesign').hide();
        } else {
            $('#contestName').hide();
            $('#contestNameFromDesign').show();
        }
    });

    $('#billingProjects').bind('change', function () {
        onBillingProjectChange();
    });

    $('.addNewMilestone a').live('click', function () {
        loadAddProjectMilestoneModal();
    });

}); // end of initiation

function onBillingProjectChange() {
	if(mainWidget.isSoftwareContest()) {
		 //reset as medium prize
		 resetSoftwarePrizes();
	}
}

function validateFieldsContestSelection() {
   var competitionType = getContestType()[0];
   if(!checkRequired(competitionType)) {
       showErrors("No challenge type is selected.");
       return false;
   }

   if(mainWidget.isSoftwareContest()) {
	return validateFieldsContestSelectionSoftware();
   } else if(mainWidget.isStudioContest()){
	return validateFieldsContestSelectionStudio();
   } else { // Algorithm contest
      return validateFieldsContestSelectionAlgorithm();
   }
}

/**
 * Initialize the common data for software contest and studio contest.
 */
function initCompetitionSelectionCommonData() {
	var categoryId = getContestType()[1];
	var contestName = $('input#contestName').val();
	var startDate = getDateByIdPrefix('start');
	var tcProjectId = parseInt($('select#projects').val());
	var billingProjectId = parseInt($('select#billingProjects').val());
	var isMultiRound = hasMultiRound(categoryId) && ('multi' == $('#roundTypes').val());
	var effortDaysEstimateOffshore = '';
	var effortDaysEstimateOnsite = '';
	if (jQuery_1_11_1('.effortEstimateRow:visible').length > 0) {
      effortDaysEstimateOffshore = $('input#effortDaysEstimateOffshore').val().trim();
      effortDaysEstimateOnsite = $('input#effortDaysEstimateOnsite').val().trim();
    }

	// apply category id data
	var projectCategory = getProjectCategoryById(categoryId);
	mainWidget.softwareCompetition.projectHeader.projectCategory={};
	mainWidget.softwareCompetition.projectHeader.projectCategory.id = projectCategory.id;
	mainWidget.softwareCompetition.projectHeader.projectCategory.name = projectCategory.name;
	mainWidget.softwareCompetition.projectHeader.projectCategory.projectType={};
	mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = projectCategory.typeId;
	mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = projectCategory.typeName;

	mainWidget.softwareCompetition.assetDTO.name = contestName;
	mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
	mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);
	mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = false;
	mainWidget.softwareCompetition.assetDTO.directjsDesignId = -1;

	mainWidget.softwareCompetition.projectHeader.tcDirectProjectId = tcProjectId;
  mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = $("#projects option[value="+ tcProjectId +"]").text();
  mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
  mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);

  mainWidget.softwareCompetition.multiRound = isMultiRound;

  mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE] = effortDaysEstimateOffshore;
  mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE] = effortDaysEstimateOnsite
}

function validateFieldsContestSelectionAlgorithm() {
   var categoryId = getContestType()[1];
   var contestName = $('input#contestName').val();
   var tcProjectId = parseInt($('select#projects').val());
   var billingProjectId = parseInt($('select#billingProjects').val());

   var copilotUserId = parseInt($('select#contestCopilot').val());
   var copilotName = $('select#contestCopilot option:selected').text();

   var startDate = getDateByIdPrefix('start');
   var regEndDate = getDateByIdPrefix('regEnd');
   var endDate = getDateByIdPrefix('subEnd');

   //validation
   var errors = [];

   //validate contest
   validateContestName(contestName, errors);

   validateTcProject(tcProjectId, errors);

   if ($('#mmType').val() === '0') {
    errors.push('Marathon match type is required');
   }

   validateEffortDaysEstimate(errors);

   // validate schedule
   if(startDate >= regEndDate) {
       errors.push('The registration end date should be after the start date.');
   }

   if(regEndDate > endDate) {
       errors.push('The registration end date should be before or equal the end date.');
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   initCompetitionSelectionCommonData();

   // set the copilot user id and user name
   mainWidget.softwareCompetition.copilotUserId = copilotUserId;
   mainWidget.softwareCompetition.copilotUserName = copilotName;

   delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification;

   if(copilotUserId == 0) {
       mainWidget.softwareCompetition.copilotCost = 0;
   } else {
       mainWidget.softwareCompetition.copilotCost = copilotFees[categoryId].copilotFee;
   }

   if($('#lccCheckBox').is(':checked')) {
       mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
   } else {
       mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
   }


   mainWidget.softwareCompetition.subEndDate = endDate;
   mainWidget.softwareCompetition.regEndDate = regEndDate;
   mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE] = $('#mmType').val().trim();
   mainWidget.softwareCompetition.projectHeader.reviewScorecardId = 0;
   mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId = 0;
   //prizes is on category id
   // fillPrizes();

   return true;
}

function validateFieldsContestSelectionSoftware() {
    var categoryId = getContestType()[1];
    var contestName = $('input#contestName').val();
    var designContestId = $('input#contestIdFromDesign').val();
    var tcProjectId = parseInt($('select#projects').val());
    var billingProjectId = parseInt($('select#billingProjects').val());
    var isMultiRound = ('multi' == $('#roundTypes').val());

    var copilotUserId = parseInt($('select#contestCopilot').val());
    var copilotName = $('select#contestCopilot option:selected').text();

    var projectMilestoneId = parseInt($('select#contestMilestone').val());

    var startDate = getDateByIdPrefix('start');
    var regEndDate = getDateByIdPrefix('regEnd');
    var subEndDate = getDateByIdPrefix('subEnd');
    var checkPointDate = getDateByIdPrefix('checkPointEnd');

    //validation
    var errors = [];

    //validate contest
    if (needsDesignSelected()) {
        if (!checkRequired(designContestId)) {
            errors.push('Please type to select a design component.');
        }
    } else {
        validateContestName(contestName, errors);
    }


    validateTcProject(tcProjectId, errors);

    // do not check First2Finish or CODE contest for milestone
    if (categoryId != SOFTWARE_CATEGORY_ID_F2F
        && categoryId != SOFTWARE_CATEGORY_ID_CODE
        && categoryId != STUDIO_CATEGORY_ID_DESIGN_F2F
        && categoryId != SOFTWARE_CATEGORY_ID_AUTOMATE) {
        validateDirectProjectMilestone(projectMilestoneId, errors);
    }

    if (categoryId == SOFTWARE_CATEGORY_ID_F2F || categoryId == SOFTWARE_CATEGORY_ID_CODE || categoryId == SOFTWARE_CATEGORY_ID_AUTOMATE) {
        if(!$('input[name=reviewType]').is(':checked')) {
            errors.push("Please select the review type for the challenge");
        }
    }

    validateEffortDaysEstimate(errors);

    if (regEndDate.getTime() <= startDate.getTime()) {
      errors.push('Registration end date/time should be larger than Start date/time.');
    }

    if (regEndDate.getTime() > subEndDate.getTime()) {
      errors.push('Registration end date/time should be less than or equal to Submission end date/time.');
    }

    if (isMultiRound) {
        if (checkPointDate.getTime() <= startDate.getTime()) {
            errors.push('Checkpoint end date/time should be larger than Start date/time.');
        }
        if (subEndDate.getTime() <= checkPointDate) {
            errors.push('Submission end date/time should be larger than Checkpoint date/time.');
        }
    } else {
        if (subEndDate.getTime() <= startDate.getTime()) {
            errors.push('Submission end date/time should be larger than Start date/time.');
        }
    }

    if (errors.length > 0) {
        showErrors(errors);
        return false;
    }


    initCompetitionSelectionCommonData();
    // set the copilot user id and user name
    mainWidget.softwareCompetition.copilotUserId = copilotUserId;
    mainWidget.softwareCompetition.copilotUserName = copilotName;

    // set the project milestone id
    mainWidget.softwareCompetition.projectMilestoneId = projectMilestoneId;

    if (needsDesignSelected()) {
        mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = true;
        //we need to grab some of values for category/technology etc to prepopulate
        if (designContestId != mainWidget.softwareCompetition.assetDTO.directjsDesignId) {
            mainWidget.softwareCompetition.assetDTO.directjsDesignId = designContestId;
            fillDevFromSelectedDesign(designContestId);
        }
        contestName = mainWidget.softwareCompetition.assetDTO.name;
    }

    delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification;

    if (copilotUserId == 0) {
        mainWidget.softwareCompetition.copilotCost = 0;
    } else {
        mainWidget.softwareCompetition.copilotCost = copilotFees[categoryId].copilotFee;
    }

    if ($('#lccCheckBox').is(':checked')) {
        mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
    } else {
        mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    }
    if (mainWidget.softwareCompetition.multiRound) {
        mainWidget.softwareCompetition.checkpointDate = new Date();
        mainWidget.softwareCompetition.checkpointDate.setTime(checkPointDate.getTime());
    }

    mainWidget.softwareCompetition.regEndDate = regEndDate;
    mainWidget.softwareCompetition.subEndDate = subEndDate;

    //prizes is on category id
    fillPrizes();
    if (categoryId === SOFTWARE_CATEGORY_ID_F2F) {
        mainWidget.softwareCompetition.projectHeader.reviewScorecardId = 0;
        mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId = Number($('#scorecards').val());
    } else {
        mainWidget.softwareCompetition.projectHeader.reviewScorecardId = Number($('#scorecards').val());
        mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId = 0;
    }
    return true;
}

function fillDevFromSelectedDesign(designContestId) {
   $.ajax({
      type: 'POST',
      url:  ctx+"/contest/detailJson",
      data: {"projectId":designContestId},
      cache: false,
      dataType: 'json',
      async : false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
		initDevContestFromDesign(result);
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      },
      beforeSend: beforeAjax,
      complete: afterAjax
   });
}

function initDevContestFromDesign(contestJson) {
	   mainWidget.softwareCompetition.assetDTO.name = contestJson.contestName;
     mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = contestJson.rootCategoryId;
     mainWidget.softwareCompetition.assetDTO.directjsCategories = contestJson.categoryIds;
     mainWidget.softwareCompetition.assetDTO.directjsTechnologies = contestJson.technologyIds;

     $('#catalogSelect').getSetSSValue(mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId);
     updateCategories(fillCategories);

	$('#masterTechnologiesSelect').val(mainWidget.softwareCompetition.assetDTO.directjsTechnologies);
     $('#masterTechnologiesSelect option:selected').appendTo('#masterTechnologiesChoosenSelect');
     sortTechnologySelects();
}

function needsDesignSelected() {
	 var categoryId = getContestType()[1];
	 return categoryId == SOFTWARE_CATEGORY_ID_DEVELOPMENT && $('#devOnlyCheckBox').is(':not(:checked)');
}

function validateFieldsContestSelectionStudio() {
   var contestTypeId = getContestType()[1];
   var contestName = $('input#contestName').val();
   var tcProjectId = $('select#projects').val();
   var billingProjectId = $('select#billingProjects').val();
   var isMultiRound = ('multi' == $('#roundTypes').val());

   var copilotUserId = parseInt($('select#contestCopilot').val());
   var copilotName = $('select#contestCopilot option:selected').text();

    var projectMilestoneId = parseInt($('select#contestMilestone').val());

   //dates
   var startDate = getDateByIdPrefix('start');
   var regEndDateHours = $('#regEndDateDay').val() * 24 + parseInt($("#regEndDateHour").val());
   var checkpointDateHours = $('#checkpointDateDay').val() * 24 + parseInt($("#checkpointDateHour").val());
   var endDateHours = $('#endDateDay').val() * 24 + parseInt($("#endDateHour").val());

   // for design F2F
   var regEndDate = getDateByIdPrefix('regEnd');
   var subEndDate = getDateByIdPrefix('subEnd');

   //validation
   var errors = [];

   validateContestName(contestName, errors);
   validateTcProject(tcProjectId, errors);

   if(contestTypeId != SOFTWARE_CATEGORY_ID_F2F
       && contestTypeId != SOFTWARE_CATEGORY_ID_CODE
       && contestTypeId != STUDIO_CATEGORY_ID_DESIGN_F2F
       && contestTypeId != SOFTWARE_CATEGORY_ID_AUTOMATE) {
       validateDirectProjectMilestone(projectMilestoneId, errors);
   }

   validateEffortDaysEstimate(errors);

   if(isMultiRound) {
      if (checkpointDateHours == 0) {
         errors.push('Round 1 duration should be positive.');
      }
   } else {
	   checkpointDateHours = 0;
   }

   if (isDesignF2F()) {
      if (regEndDate.getTime() <= startDate.getTime()) {
        errors.push('Registration end date/time should be larger than Start date/time.');
      }

      if (regEndDate.getTime() > subEndDate.getTime()) {
        errors.push('Registration end date/time should be less than or equal to Submission end date/time.');
      }
   } else {
     if (endDateHours == 0) {
	if (isMultiRound){
		errors.push('Round 2 duration should be positive.');
	} else {
		errors.push('Round 1 duration should be positive.');
	}
     }

     if (regEndDateHours == 0) {
       errors.push('Registration Length should be positive.');
     }

     if (!isMultiRound && regEndDateHours > endDateHours) {
       errors.push('Registration length should be less than or equal to the Round 1 Duration.');
     } else if (isMultiRound && regEndDateHours > checkpointDateHours + endDateHours) {
       errors.push('Registration length should be less than or equal to the sum of Round 1 and 2 Durations.')
     }
   }

   if(startDate.getTime() - getServerTime().getTime() < 4 * 60 * 60 * 1000 ) {
       errors.push('Start time can\'t be less than within 4 hours');
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   // update competition value
   initCompetitionSelectionCommonData();
   // set the copilot user id and user name
   mainWidget.softwareCompetition.copilotUserId = copilotUserId;
   mainWidget.softwareCompetition.copilotUserName = copilotName;

    // set the project milestone id
    mainWidget.softwareCompetition.projectMilestoneId = projectMilestoneId;

   if(copilotUserId == 0) {
       mainWidget.softwareCompetition.copilotCost = 0;
   } else {
       mainWidget.softwareCompetition.copilotCost = copilotFees[contestTypeId].copilotFee;
   }

   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification = new com.topcoder.direct.ProjectStudioSpecification();
   if (mainWidget.softwareCompetition.multiRound) {
	   mainWidget.softwareCompetition.checkpointDate = new Date();
	   mainWidget.softwareCompetition.checkpointDate.setTime(startDate.getTime() + checkpointDateHours * 60 * 60 * 1000);
   }
   // set end date
   if (isDesignF2F()) {
      mainWidget.softwareCompetition.regEndDate = regEndDate;
      mainWidget.softwareCompetition.subEndDate = subEndDate;
   } else {
      mainWidget.softwareCompetition.regEndDate = new Date();
      mainWidget.softwareCompetition.regEndDate.setTime(startDate.getTime() + regEndDateHours * 60 * 60 * 1000);

      mainWidget.softwareCompetition.subEndDate = new Date();
      mainWidget.softwareCompetition.subEndDate.setTime(startDate.getTime() + (checkpointDateHours + endDateHours) * 60 * 60 * 1000);
   }


   if($('#lccCheckBox').is(':checked')) {
	mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
       enableMCEPlaceholderText = true;
       $(['contestDescription', 'round1Info', 'round2Info']).each(function() {
            var obj = CKEDITOR.instances[this];
            if (obj.getData() == "") {
                obj.setData("Only members that register for this challenge will see this description.");
            }
       });
   } else {
	mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
       $(['contestDescription', 'round1Info', 'round2Info']).each(function() {
            var obj = CKEDITOR.instances[this];
            if (obj.getData() == "") {
                obj.setData("");
            }
       });
       enableMCEPlaceholderText = false;
   }
   mainWidget.softwareCompetition.projectHeader.reviewScorecardId = 0;
   mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId = 0;
   return true;
}

function continueContestSelection() {
   if(!validateFieldsContestSelection()) {
       return;
   }
    //add challenge group to review page
    var groupsSpan="";
    var groupSelected = jQuery_1_11_1("#groups").magicSuggest().getSelection();
    $.each(groupSelected, function(i, g){
        groupsSpan+='<span>'+ g.name+'</span>';
    });
    $(".overviewData .rGroups td span").remove();

    $(groupsSpan).insertBefore(".overviewData .rGroups td a");

   if(mainWidget.isSoftwareContest()) {
	showPage('overviewSoftwarePage');


       // use a different prize layout for Code/F2F/Bug Hunt contest, hide unused prize settings
       if (isCode() || isF2F() || isBugHunt()) {
           // hide unused prize settings
           $(".topcoderPrize").hide();
           $(".codePrize").show();

           if(isBugHunt()) {
               // show spec review cost for bug hunt
               $("#swSpecCost").parent().show();
           }

           // always use custom prize settings for Code/F2F/Bug Hunt contest
           $("input[name=prizeRadio]:last").attr('checked', 'checked').trigger('click');
       } else {
           // show the prize settings for TopCoder contests
           $(".topcoderPrize").show();

           // if use custom settings, trigger the click to update the prizes
           if ($("input[name=prizeRadio]:last").is(":checked")) {
               $("input[name=prizeRadio]:last").trigger('click');
           }
       }

       if(isCode()) {
           $(".codePrize").show();
       } else if(isF2F() || isBugHunt()) {
           $(".codePrize").hide();
       }

       if(isCode()) {
           // hide the multiple prize input
           $(".prizesInner_software #prize2").show();
           $(".prizesInner_software .swAdd").show();
       } else {
           // hide the multiple prize input
           $(".prizesInner_software #prize2").hide();
           $(".prizesInner_software .swAdd").hide();
       }

   }else if(mainWidget.isAlgorithmContest()){
	showPage('overviewAlgorithmPage');
   } else {// studio contest
      if (mainWidget.softwareCompetition.projectHeader.isLccchecked()) {
        $("#viewableSubmFlag").attr("disabled","disabled");
        $("#viewableSubmFlag").attr("checked","");
        mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] = 'false';
        $("#contestIntroduction").parent().find(".mceFooterNote").show();
      } else {
        $("#viewableSubmFlag").attr("disabled","");
        $("#contestIntroduction").parent().find(".mceFooterNote").hide();
      }
      showPage('overviewPage');
   }

    $('.points .prizesInner').children().show();
    if(isDesignF2F()) {
        $('#stPoints .prizesInner').children().hide();
        $('#stPoints .prizesInner').children(':lt(3)').show();
    } else if (mainWidget.isSoftwareContest()) {
        if (isF2F() || isBugHunt()) {
            $('#swPoints .prizesInner').children().hide();
            $('#swPoints .prizesInner').children(':lt(3)').show();
        } else if (!isCode()) {
            $('#swPoints .prizesInner').children().hide();
            $('#swPoints .prizesInner').children(':lt(6)').show();
        }
    }

    if ($('#swExtraPoints').css('display') === 'block') {
        $('#swPoints .addPoint').hide();
    }
    if ($('#stExtraPoints').css('display') === 'block') {
        $('#stPoints .addPoint').hide();
    }
    if ($('#alExtraPoints').css('display') === 'block') {
        $('#alPoints .addPoint').hide();
    }
    if ($('#extraPrizes').css('display') === 'block') {
        $('.studioAdd').hide();
    }
    if ($('#swExtraPrizes').css('display') === 'block') {
        $('.swAdd').hide();
    }
    if ($('#alExtraPrizes').css('display') === 'block') {
        $('.alAdd').hide();
    }

    // the points feature should only be available, when there are groups selected
    if (!hasGroupSelected()) {
      $('.points').hide();
    } else {
      $('.points').show();
    }

    $(".drHide").hide();
}

function saveAsDraftContestSelection() {
    if (!validateFieldsContestSelection()) {
        return;
    }

    var saveDraftHandler = function () {
        //construct request data
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: handleSaveAsDraftContestResult,
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    };

    if(showSaveChallengeConfirmation == false) {
        saveDraftHandler();
    } else {
        showChallengeSaveConfiguration(function(){
            closeModal();
            saveDraftHandler();
        });
    }


}

function initContestNamesFromDesign() {
   $.ajax({
      type: 'POSt',
      url:  ctx + "/launch/getDesignComponents",
      data: {},
      cache: false,
      dataType: 'json',
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
		handleGetDesignComponentsResult(result);
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      }
   });
}


function handleGetDesignComponentsResult(result) {
	 if(!result.designs) {
		return;
	 }

	 var rows = result.designs;

   $("#contestNameFromDesign").autocomplete(rows, {
     matchContains : "word",
     autoFill : false,
     formatItem : function(row, i, max) {
	return getDesignName(row);
     },
     formatMatch : function(row, i, max) {
       return getDesignName(row);
     },
     formatResult : function(row) {
       return getDesignName(row);
     }
   }).result( function(event, row) {
     $(this).blur().focus();
     $('#contestIdFromDesign').val(row.projectId);
   });
}

function getDesignName(row) {
	 return $.trim(row.text);
}
