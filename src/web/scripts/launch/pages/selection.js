/**
 * Contest Selection Page
 */
$(document).ready(function() {
   $('#billingProjects').bind('change',function() {
   	   onBillingProjectChange();
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
       showErrors("No contest type is selected.");
       return false;
   }
   	
   if(mainWidget.isSoftwareContest()) {
   	  return validateFieldsContestSelectionSoftware();
   } else {
   	  return validateFieldsContestSelectionStudio();
   }
} 

function validateFieldsContestSelectionSoftware() {
   var categoryId = getContestType()[1];
   var contestName = $('input#contestName').val();
   var tcProjectId = parseInt($('select#projects').val());
   var billingProjectId = parseInt($('select#billingProjects').val());

   var startDate = getDateByIdPrefix('start');
   
   //validation
   var errors = [];

   validateContestName(contestName, errors);
   validateTcProject(tcProjectId, errors);
      
   if(errors.length > 0) {
       showErrors(errors);
       return false;
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
   
   mainWidget.softwareCompetition.projectHeader.tcDirectProjectId = tcProjectId;
   mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = $("#projects option[value="+ tcProjectId +"]").text();
   mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);   
   mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);
   
   if($('#lccCheckBox').is(':checked')) {
   	   mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
   } else {
   	   mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
   }
   
   //prizes is on category id
   fillPrizes();
   
   return true;
}
 
function validateFieldsContestSelectionStudio() {
   var contestTypeId = getContestType()[1];
   var contestName = $('input#contestName').val();
   var tcProjectId = $('select#projects').val();
   var billingProjectId = $('select#billingProjects').val();
   var isMultiRound = ('multi' == $('#roundTypes').val());

   //dates
   var startDate = getDateByIdPrefix('start');
   var endDate = getDateByIdPrefix('end');
   var milestoneDate = getDateByIdPrefix('milestone');

   //validation
   var errors = [];

   validateContestName(contestName, errors);
   validateTcProject(tcProjectId, errors);

   //check dates
   if(startDate >= endDate) {
       errors.push('Start date should be smaller than end date.');
   }

   if(isMultiRound) {
      if(startDate >= milestoneDate || milestoneDate >= endDate) {
         errors.push('Milestone date should be between start date and end date.');
      }
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   // update competition value
   mainWidget.competition.contestData.contestTypeId = contestTypeId;
   mainWidget.competition.contestData.name = contestName;
   mainWidget.competition.contestData.tcDirectProjectId = tcProjectId;
   mainWidget.competition.contestData.billingProject = billingProjectId;
   mainWidget.competition.contestData.multiRound = isMultiRound;

   mainWidget.competition.startDate = startDate;
   mainWidget.competition.endDate = endDate;
   mainWidget.competition.milestoneDate = milestoneDate;

   return true;
}

function continueContestSelection() {
   if(!validateFieldsContestSelection()) {
       return;
   }

   if(mainWidget.isSoftwareContest()) {
   	  showPage('overviewSoftwarePage');
   } else {
      showPage('overviewPage');
   }

   
}

function saveAsDraftContestSelection() {
   if(!validateFieldsContestSelection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url:  ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult,
      beforeSend: beforeAjax,
      complete: afterAjax 
   });
}