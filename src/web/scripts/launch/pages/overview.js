/**
 * Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
 *
 * Overview Page (the second page of the launch challenge flow)
 *
 * <p>
 * Version 1.0.1 (TC Direct Release Assembly 7) Change notes:
 * - Bind digital run input field change event.
 * </p>
 *
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 * 
 * Version 1.2 TC Direct Replatforming Release 2 change notes:
 * - The software contest can set checkpoint prizes.
 * - The studio contest can have specification review cost.
 *
 * Version 1.3 TC Direct Replatforming Release 4 change notes:
 * - Add support to save the DR points for the studio contests.
 * - Add support to save the stock arts allowed flag for the studio contests.
 *
 * Version 1.4 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 * 
 * Version 1.5 Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match
 * - update validateFieldsOverview method to support algorithm contest.
 * - add method validateFieldsOverviewAlgorithm
 * - update continueOverview method to support algorithm contest.
 *
 * Version 1.6 (Module Assembly - TC Cockpit Launch Code contest)
 * - Add support for multiple prizes for Code Contest type
 *
 * Version 1.7 (Module Assembly - TC Cockpit Launch F2F contest)
 * - Add support for "Choose Project Platforms" control
 *
 * Version 1.8 (TopCoder Direct - Design Challenge Track Studio Cup Point Flag)
 * - Add handler for prizes changes in studio contest to validate and auto calculate the studio cup points
 * - Add handler for studio cup points checkbox to turn studio cup ON/OFF
 *
 * Version 1.9 (TopCoder Direct - Draft Challenge Creation/Saving Prompt)
 * - Add the save challenge confirmation
 *
 * Version 1.10 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * - Update review page for groups selected section
 *
 * Version 1.11 (TOPCODER - SUPPORT CUSTOM COPILOT FEE FOR CHALLENGE IN DIRECT APP):
 * - Add support for custom copilot fee
 *
 * Version 1.12 (Topcoder - Add Basic Marathon Match Creation And Update In Direct App)
 * - Remove MM problem entry
 * @author bugbuka, GreatKevin, Veve, GreatKevin, TCSASSEMBLER
 * @version 1.12
 */
$(document).ready(function() {
   // categories
   sortCategorySelects();

   $('#catalogSelect').bind("change", function() {
        updateCategories();
   });


   $('#addCategories').click(function(){
       $('#select1_categories option:selected').appendTo('#select2_categories');
       sortCategorySelects();
   });

   $('#removeCategories').click(function(){
       $('#select2_categories option:selected').appendTo('#select1_categories');
       sortCategorySelects();
   });

   // software prizes event handlers
   $('input[name="prizeRadio"]').click(function(){
       fillPrizes();
   });
   $('input[name="prizeRadio"]:nth(1)').attr('checked','checked');

   $('#swFirstPlace').bind('keyup',function() {
       onFirstPlaceChangeKeyUp();
    });

    $(".prizesInner_software input[type=text].prizesInput").bind('keyup', function(){
        if($(this).attr('id') == 'swFirstPlace') return;
        var prizeIndex = $(".prizesInner_software input[type=text].prizesInput").index(this) + 1;
        var prizeName;
        switch (prizeIndex) {
            case 2 : prizeName = "2nd"; break;
            case 3 : prizeName = "3rd"; break;
            default: prizeName = prizeIndex + "th"; break;
        }
        onSoftwarePrizeInputChange($(this), prizeName + " Place Prize", false);
    })
    
    $('#swDigitalRun').bind('keyup',function() {
       onDigitalRunChangeKeyUp();
    });

    $('#swCheckpointPrize').bind('keyup', onCheckpointPrizeChangeKeyUp);
    $('#swCheckpointSubmissionNumber').bind('change', onCheckpointPrizeChangeKeyUp);


    // studio prizes event handles
    $(".studioPrizes select").change(function () {
        studioPrizeChangeHandler();
    });

    $(".studioPrizes input[type=text]").keyup(function() {
        delay(studioPrizeChangeHandler, 500);
    })

    $("#studioCupPointsCheckBox").change(function(){
        studioPrizeChangeHandler();
    });

    $(".copilotFee").focusout(function(){
        var error = validateCopilotFee($(this).val(), true);
        if(error){
            //revert the value
            $(this).val(mainWidget.softwareCompetition.copilotCost);
        }
    });
    $(".copilotFee").keyup(function(){
        if(checkNumber($(this).val()) && !isNaN(parseFloat($(this).val()))){
            if($(this).hasClass("software")){
                fillPrizes();
            }
        }//if not valid error messege show on focusout or save
    });
}); // end of initiation


var studioPrizeChangeHandler = function() {
    var errors = [];

    var prizes = validatePrizes(errors);

    if (mainWidget.softwareCompetition.multiRound) {
        var checkPointPrize = $('#checkpointPrize').val();
        var checkpointPrizeNumber = parseFloat(checkPointPrize);
        if (!checkRequired(checkPointPrize) || !checkNumber(checkPointPrize) || isNaN(checkpointPrizeNumber)) {
            errors.push('Checkpoint prize is invalid.');
        }

        prizes.push(new com.topcoder.direct.Prize(1, checkpointPrizeNumber, CHECKPOINT_PRIZE_TYPE_ID, parseInt($('#checkpointSubmissionNumber').val())));
    }

    if(errors.length > 0) {
        showErrors(errors);
        $("#rStudioCupPoints").text('0');
    } else {
        var studioCupPoints = calculateStudioCupPoints(prizes);
        $("#rStudioCupPoints").text(studioCupPoints);
    }

    if(!$("#studioCupPointsCheckBox").is(":checked")) {
        $("#rStudioCupPoints").text('0');
    }
};

function validateFieldsOverview() {
   if(mainWidget.isSoftwareContest()) {
       return validateFieldsOverviewSoftware();
   } else if (mainWidget.isStudioContest()){
       return validateFieldsOverviewStudio();
   } else { // isAlgorithmContest
       return validateFieldsOverviewAlgorithm();
   }
}

function validateFieldsOverviewSoftware() {
   var detailedRequirements = CKEDITOR.instances.swDetailedRequirements.getData();
   var softwareGuidelines = CKEDITOR.instances.swGuidelines.getData();

   var rootCategoryId = $('#catalogSelect').val();
   //checkpoint prize and submission numbers
   var checkpointPrizeInput = $('#swCheckpointPrize').val();


   //validation
   var errors = [];

   if(!checkRequired(detailedRequirements)) {
       errors.push('Detailed requirements is empty.');
   }

   if(!checkRequired(softwareGuidelines)) {
       errors.push('Submission guidelines is empty.');
   }


   if(isTechnologyContest()) {
      if(jQuery_1_11_1("#technologies").magicSuggest().getSelection().length == 0) {
           errors.push('No technology is selected.');
      }
   }

   if(isPlatformContest()) {
       if(jQuery_1_11_1("#platforms").magicSuggest().getSelection().length == 0) {
           errors.push('No Platform is selected.');
       }
   }

   if(isDevOrDesign()) {
      if( rootCategoryId <= 0 ) {
           errors.push('No catalog is selected.');
      }

      if($('#select2_categories option').length == 0) {
           errors.push('No category is selected.');
      }
   }

   var prizeType = $('input[name="prizeRadio"]:checked').val();
   if(prizeType == 'custom') {
     var value = $('#swFirstPlace').val();
     if(!checkRequired(value) || !checkNumber(value)) {
          errors.push('first place value is invalid.');
     }
     
     value = $('#swDigitalRun').val();
     if(!checkRequired(value) || !checkNumber(value)) {
        errors.push('digital run value is invalid.');
     }
   }

   if(isCode()) {
       // validate multiple prizes settings
       var codePrizes = validateCodePrizes(errors);
   }

   if(mainWidget.softwareCompetition.multiRound) {
      var checkpointPrize = parseFloat(checkpointPrizeInput);
      if(!checkRequired(checkpointPrizeInput) || !checkNumber(checkpointPrizeInput) || isNaN(checkpointPrize)) {
         errors.push('Checkpoint prize is invalid.');
      }      
   }
   var error = validateCopilotFee($("input.copilotFee:not([disabled])").val(), false);
   if(error) {
      errors.push(error);
   }
   
   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = detailedRequirements;
   mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines = softwareGuidelines;
   if(isDevOrDesign()) {
     mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = rootCategoryId;
     mainWidget.softwareCompetition.assetDTO.directjsCategories =
      $.map($('#select2_categories option'), function(option,i){
          return option.value;
     });
   }
   
   if(isTechnologyContest()) {
       var selectedTechnologies = jQuery_1_11_1("#technologies").magicSuggest().getSelection();
       mainWidget.softwareCompetition.assetDTO.directjsTechnologies = $.map(selectedTechnologies, function (val, i) {
           return val.id.toString();
       });
       var techSpan="";
       $.each(selectedTechnologies, function(i, g){
           techSpan+='<span>'+ g.name+'</span>';
       });
       $("#rTechnologies span").remove();
       $(techSpan).insertBefore("#rTechnologies a");
   }

   if(isPlatformContest()) {
       var selectedPlatforms = jQuery_1_11_1("#platforms").magicSuggest().getSelection();
       mainWidget.softwareCompetition.platforms = $.map(selectedPlatforms, function (val, i) {
           return val.id.toString();
       });

       var platformSpan="";
       $.each(selectedPlatforms, function(i, g){
           platformSpan+='<span>'+ g.name+'</span>';
       });

       $("#rPlatforms span").remove();
       $(platformSpan).insertBefore("#rPlatforms a");
    }

   updateSoftwarePrizes();

    // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

   return true;
}

function validateFieldsOverviewStudio() {
   var contestDescription = CKEDITOR.instances.contestDescription.getData();
   var contestIntroduction = CKEDITOR.instances.contestIntroduction.getData();
   var round1Info = CKEDITOR.instances.round1Info.getData();
   var round2Info = CKEDITOR.instances.round2Info.getData();

    //checkpoint prize and submission numbers
    var checkpointPrizeInput = $('#checkpointPrize').val();

   //validation
   var errors = [];

   if(!checkRequired(contestIntroduction)) {
       errors.push('Challenge introduction is empty.');
   }

   if(!checkRequired(contestDescription)) {
       errors.push('Challenge description is empty.');
   }

   var prizes = validatePrizes(errors);

   var dr = 0;
   for(var i = 0, len = prizes.length; i < len; i++) {
		dr += prizes[i].prizeAmount;
   }
   
   var fileTypesResult = validateFileTypes(errors);
   var fileTypes = fileTypesResult[0];
   var otherFileTypes = fileTypesResult[1];

   var checkpointPrize;
   if(mainWidget.softwareCompetition.multiRound) {
      checkpointPrize = parseFloat(checkpointPrizeInput);
       if(!checkRequired(checkpointPrizeInput) || !checkNumber(checkpointPrizeInput) || isNaN(checkpointPrize)) {
           errors.push('Checkpoint prize is invalid.');
       }

      if(!checkRequired(round1Info)) {
          errors.push('Round 1 information is empty.');
      }

      if(!checkRequired(round2Info)) {
          errors.push('Round 2 information is empty.');
      }
      
	  dr += parseInt($('#checkpointSubmissionNumber').val()) * checkpointPrize;
      prizes.push(new com.topcoder.direct.Prize(1, checkpointPrize, CHECKPOINT_PRIZE_TYPE_ID, parseInt($('#checkpointSubmissionNumber').val())));
      mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction = round1Info;
      mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction = round2Info;
   }
   var error = validateCopilotFee($("input.copilotFee:not([disabled])").val(), false);
   if(error) {
      errors.push(error);
   }
   if ($('#maxSubmissions').length) {
       var maxSubmissions = $('#maxSubmissions').val();

       if (!(optional(maxSubmissions) || (/^\d+$/.test(maxSubmissions) && parseInt(maxSubmissions) > 0))) {
          errors.push('Max Submissions field should be empty or positive integer.');
       }
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   //update
   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestDescription = contestDescription;
   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestIntroduction = contestIntroduction;
   
   mainWidget.softwareCompetition.projectHeader.prizes = prizes;
   
   mainWidget.softwareCompetition.fileTypes = fileTypes.concat(otherFileTypes);
   // set specification review and review/screening cost
   for (var i = 0; i < studioSubtypeFees.length; i++) {
	   if (studioSubtypeFees[i].id == mainWidget.softwareCompetition.projectHeader.projectCategory.id) {
		   mainWidget.softwareCompetition.projectHeader.setSpecReviewCost(studioSubtypeFees[i].specReviewCost);
           mainWidget.softwareCompetition.projectHeader.setReviewCost(studioSubtypeFees[i].reviewCost);
	   }
   }
   
   // save the allow stock art
   mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'] = '' + $('#allowStockArt').is(':checked');

   if ($('#maxSubmissions').length) {
       var maxSubmissions = $('#maxSubmissions').val();
       if ($.trim(maxSubmissions).length == 0) {
           mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] = '';
       } else {
           mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] = parseInt(maxSubmissions);
       }
   } else {
       mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] = 5;
   }

   if ($('#viewableSubmFlag').length) {
       mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] = '' + $('#viewableSubmFlag').is(":checked");
   } else {
       mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] = 'true';
   }

   //save the DR points
    if($("#studioCupPointsCheckBox").is(":checked")) {
        mainWidget.softwareCompetition.projectHeader.properties['DR points'] = dr * 0.25;
        mainWidget.softwareCompetition.projectHeader.properties['Digital Run Flag'] = 'On';
    } else {
        mainWidget.softwareCompetition.projectHeader.properties['DR points'] = 0;
        mainWidget.softwareCompetition.projectHeader.properties['Digital Run Flag'] = 'Off';
    }

   return true;
}

function validateFieldsOverviewAlgorithm() {
   var matchDetails = CKEDITOR.instances.matchDetails.getData();
   var matchRules = CKEDITOR.instances.matchRules.getData();

   //validation
   var errors = [];

   if(!checkRequired(matchDetails)) {
       errors.push('Match Details is empty.');
   }

   if(!checkRequired(matchRules)) {
       errors.push('Match Rules is empty.');
   }

   var prizes = validatePrizes(errors);
   var error = validateCopilotFee($("input.copilotFee:not([disabled])").val(), false);
   if(error) {
      errors.push(error);
   }
   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   //update
   mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchDetails = matchDetails;
   mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchRules = matchRules;
   
   
   mainWidget.softwareCompetition.projectHeader.prizes = prizes;

   // mainWidget.softwareCompetition.projectHeader.prizes = prizes;

   return true;
}

function backOverview() {
    if(validateCopilotFee($("input.copilotFee:not([disabled])").val(), false)){
        //revert
        $(".copilotFee").val(mainWidget.softwareCompetition.copilotCost);
        return;
    }
    showPage('contestSelectionPage');
}

function continueOverview() {
   if(!validateFieldsOverview()) {
       return;
   }
   if (!mainWidget.isStudioContest()) {
       mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT] = ($(".environmentEdit:visible").val() || "").trim();
       mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO] = ($(".repoEdit:visible").val() || "").trim();
   }
   var backLink = '<a href="javascript: backReview();" class="tipLink"><img src="/images/edit-icon.png" alt="Edit"/></a>';
   $(".rEnvironment").html(mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT] + backLink);
   $(".rRepo").html(mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO] + backLink);
   if(mainWidget.isSoftwareContest()) {
       if(!hasRequirementDocument()) {
          showWarningMessage("Requirements Specification Document was not attached, continue?", "YES", function(){showPage('reviewSoftwarePage');closeModal();});
          return;
       }
       showPage('reviewSoftwarePage');
   } else if(mainWidget.isStudioContest()) {
       showPage('reviewPage');
   } else { // Algorithm contest
       showPage('reviewAlgorithmPage');
   }
}

function saveAsDraftOverview() {
    if (!validateFieldsOverview()) {
        return;
    }

    if (!mainWidget.isStudioContest()) {
        mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT] = ($(".environmentEdit").val() || "").trim();
        mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO] = ($(".repoEdit").val() || "").trim();
    }
    var saveDraftHandler = function () {
        //construct request data
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: "saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: handleSaveAsDraftContestResult,
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    };


    if (showSaveChallengeConfirmation == false) {
        saveDraftHandler();
    } else {
        showChallengeSaveConfiguration(function () {
            closeModal();
            saveDraftHandler();
        });
    }
}
