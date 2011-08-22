/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Overview Page.
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
 * - The software contest can set milestone prizes.
 * - The studio contest can have specification review cost.
 *
 * Version 1.3 TC Direct Replatforming Release 4 change notes:
 * - Add support to save the DR points for the studio contests.
 * - Add support to save the stock arts allowed flag for the studio contests.
 *
 * @author TCSASSEMBER
 * @version 1.3
 */
$(document).ready(function() {
   //technologies
   sortTechnologySelects();
   sortCategorySelects();

   $('#addTechnologies').click(function(){
       $('#masterTechnologiesSelect option:selected').appendTo('#masterTechnologiesChoosenSelect');
       sortTechnologySelects();
   });

   $('#removeTechnologies').click(function(){
       $('#masterTechnologiesChoosenSelect option:selected').appendTo('#masterTechnologiesSelect');
       sortTechnologySelects();
   });

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

   //prizes
   $('input[name="prizeRadio"]').click(function(){
       fillPrizes();
   });
   $('input[name="prizeRadio"]:nth(1)').attr('checked','checked');

   $('#swFirstPlace').bind('keyup',function() {
       onFirstPlaceChangeKeyUp();
    });
    
    $('#swDigitalRun').bind('keyup',function() {
       onDigitalRunChangeKeyUp();
    });
    
    $('#swMilestonePrize').bind('keyup', onMilestonePrizeChangeKeyUp);
    $('#swMilestoneSubmissionNumber').bind('change', onMilestonePrizeChangeKeyUp);
}); // end of initiation



function validateFieldsOverview() {
   if(mainWidget.isSoftwareContest()) {
       return validateFieldsOverviewSoftware();
   } else {
       return validateFieldsOverviewStudio();
   }
}

function validateFieldsOverviewSoftware() {
   var detailedRequirements = tinyMCE.get('swDetailedRequirements').getContent();
   var softwareGuidelines = tinyMCE.get('swGuidelines').getContent();	
   var rootCategoryId = $('#catalogSelect').val();
   //milestone prize and submission numbers
   var milestonePrizeInput = $('#swMilestonePrize').val();


   //validation
   var errors = [];

   if(!checkRequired(detailedRequirements)) {
       errors.push('Detailed requirements is empty.');
   }

   if(!checkRequired(softwareGuidelines)) {
       errors.push('Software guidelines is empty.');
   }


   if(isTechnologyContest()) {
      if($('#masterTechnologiesChoosenSelect option').length == 0) {
           errors.push('No technology is selected.');
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

   if(mainWidget.softwareCompetition.multiRound) {
      var milestonePrize = parseFloat(milestonePrizeInput);
      if(!checkRequired(milestonePrizeInput) || !checkNumber(milestonePrizeInput) || isNaN(milestonePrize)) {
         errors.push('Milestone prize is invalid.');
      }      
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
     mainWidget.softwareCompetition.assetDTO.directjsTechnologies =
      $.map($('#masterTechnologiesChoosenSelect option'), function(option,i){
          return option.value;
     });
   }

   updateSoftwarePrizes();

    // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);
   
   return true;
}

function validateFieldsOverviewStudio() {
   var contestDescription = tinyMCE.get('contestDescription').getContent();
   var contestIntroduction = tinyMCE.get('contestIntroduction').getContent();
   var round1Info = tinyMCE.get('round1Info').getContent();
   var round2Info = tinyMCE.get('round2Info').getContent();

    //milestone prize and submission numbers
    var milestonePrizeInput = $('#milestonePrize').val();

   //validation
   var errors = [];

   if(!checkRequired(contestIntroduction)) {
       errors.push('Contest introduction is empty.');
   }

   if(!checkRequired(contestDescription)) {
       errors.push('Contest description is empty.');
   }

   var prizes = validatePrizes(errors);

   var dr = 0;
   for(var i = 0, len = prizes.length; i < len; i++) {
		dr += prizes[i].prizeAmount;
   }
   
   var fileTypesResult = validateFileTypes(errors);
   var fileTypes = fileTypesResult[0];
   var otherFileTypes = fileTypesResult[1];

   var milestonePrize;
   if(mainWidget.softwareCompetition.multiRound) {
      milestonePrize = parseFloat(milestonePrizeInput);
       if(!checkRequired(milestonePrizeInput) || !checkNumber(milestonePrizeInput) || isNaN(milestonePrize)) {
           errors.push('Milestone prize is invalid.');
       }

      if(!checkRequired(round1Info)) {
          errors.push('Round 1 information is empty.');
      }

      if(!checkRequired(round2Info)) {
          errors.push('Round 2 information is empty.');
      }
      
	  dr += parseInt($('#milestoneSubmissionNumber').val()) * milestonePrize;
      prizes.push(new com.topcoder.direct.Prize(1, milestonePrize, MILESTONE_PRIZE_TYPE_ID, parseInt($('#milestoneSubmissionNumber').val())));
      mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction = round1Info;
      mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction = round2Info;
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
   // set specification review cost
   for (var i = 0; i < studioSubtypeFees.length; i++) {
	   if (studioSubtypeFees[i].id == mainWidget.softwareCompetition.projectHeader.projectCategory.id) {
		   mainWidget.softwareCompetition.projectHeader.setSpecReviewCost(studioSubtypeFees[i].specReviewCost);
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
   mainWidget.softwareCompetition.projectHeader.properties['DR points'] = dr * 0.25;

   return true;
}


function backOverview() {
   showPage('contestSelectionPage');
}

function continueOverview() {
   if(!validateFieldsOverview()) {
       return;
   }

   if(mainWidget.isSoftwareContest()) {
       showPage('reviewSoftwarePage');
       if(!hasRequirementDocument()) {
          alert("Warning: Requirement Specification document was not attached.");
       }
   } else {
       showPage('reviewPage');
   }
}

function saveAsDraftOverview() {
   if(!validateFieldsOverview()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult,
      beforeSend: beforeAjax,
      complete: afterAjax      
   });
}