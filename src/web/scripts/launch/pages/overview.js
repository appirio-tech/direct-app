/**
 * Overview Page
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

   $('#swFirstPlace').bind('change',function() {
       onFirstPlaceChange();
    });
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


   //validation
   var errors = [];

   if(!checkRequired(detailedRequirements)) {
       errors.push('Detailed requirements is empty.');
   }

   if(!checkRequired(softwareGuidelines)) {
       errors.push('Software guidelines is empty.');
   }

   if(isDevOrDesign()) {
      if( rootCategoryId <= 0 ) {
           errors.push('No catalog is selected.');
      }

      if($('#masterTechnologiesChoosenSelect option').length == 0) {
           errors.push('No technology is selected.');
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
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = detailedRequirements;
   mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines = softwareGuidelines;

   if(isDevOrDesign()) {
     mainWidget.softwareCompetition.assetDTO.directjsTechnologies =
      $.map($('#masterTechnologiesChoosenSelect option'), function(option,i){
          return option.value;
     });
     mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = rootCategoryId;
     mainWidget.softwareCompetition.assetDTO.directjsCategories =
      $.map($('#select2_categories option'), function(option,i){
          return option.value;
     });
   }

   updateSoftwarePrizes();

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

   var fileTypesResult = validateFileTypes(errors);
   var fileTypes = fileTypesResult[0];
   var otherFileTypes = fileTypesResult[1];

   var milestonePrize;
   if(mainWidget.competition.contestData.multiRound) {
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
   }


   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   //update
   mainWidget.competition.contestData.prizes = prizes;
   mainWidget.competition.contestData.finalFileFormat = fileTypes.join(',');
   mainWidget.competition.contestData.otherFileFormats = otherFileTypes.join(',');
   mainWidget.competition.contestData.shortSummary = contestIntroduction;
   mainWidget.competition.contestData.contestDescriptionAndRequirements = contestDescription;

   mainWidget.milestonePrizeData.amount = milestonePrize;
   mainWidget.milestonePrizeData.numberOfSubmissions = parseInt($('#milestoneSubmissionNumber').val());
   mainWidget.competition.contestData.multiRoundData.roundOneIntroduction = round1Info;
   mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction = round2Info;

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
      success: handleSaveAsDraftContestResult
   });
}