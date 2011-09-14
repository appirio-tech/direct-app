/**
 * Contest Selection Page.
 * 
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 * 
 * @author TCSASSEMBER
 * @version 1.1
 *
 * Version 1.1 TC Direct- Software Creation Update Assembly changes notes
 * - Update method validateFieldsContestSelectionSoftware to get copilotUserId and name from copilot dropdown
 *
 * Version 1.2 Direct Replatforming Release 4 changes notes
 * - Add support to set Maximum Submissions to 5 for studio contest.
 *
 * @version 1.2
 * @author TCSDEVELOPER
 */
$(document).ready(function() {	 
	 initContestNamesFromDesign();
	 
   $('#devOnlyCheckBox').bind('click',function() {
   	    if($('#devOnlyCheckBox').is(':checked')) {
         	  $('#contestName').show();
         	  $('#contestNameFromDesign').hide();
         } else {
         	  $('#contestName').hide();
         	  $('#contestNameFromDesign').show();
         }
   });	 
	 
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

/**
 * Initialize the common data for software contest and studio contest. 
 */
function initCompetitionSelectionCommonData() {
	var categoryId = getContestType()[1];
	var contestName = $('input#contestName').val();
	var startDate = getDateByIdPrefix('start');
	var tcProjectId = parseInt($('select#projects').val());
	var billingProjectId = parseInt($('select#billingProjects').val());
	var isMultiRound = ('multi' == $('#roundTypes').val());
	
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

   var startDate = getDateByIdPrefix('start');
   var milestoneDateHours = $('#milestoneDateDay').val() * 24 + parseInt($("#milestoneDateHour").val());
   
   //validation
   var errors = [];

   //validate contest
	 if(needsDesignSelected()) {
      if(!checkRequired(designContestId)) {
          errors.push('Please type to select a design component.');
      }	 	  
	 } else {
	 	  validateContestName(contestName, errors);
   }
   
   
   validateTcProject(tcProjectId, errors);
   
   if(isMultiRound) {
      if(milestoneDateHours == 0) {
	     errors.push('Milestone duration must be positive.');
      }
   }
      
   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
      
	  
   initCompetitionSelectionCommonData();
   // set the copilot user id and user name
   mainWidget.softwareCompetition.copilotUserId = copilotUserId;
   mainWidget.softwareCompetition.copilotUserName = copilotName;

   if(needsDesignSelected()) {
   	  mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = true;
   	  //we need to grab some of values for category/technology etc to prepopulate
   	  if(designContestId != mainWidget.softwareCompetition.assetDTO.directjsDesignId) {
   	      mainWidget.softwareCompetition.assetDTO.directjsDesignId = designContestId;
   	      fillDevFromSelectedDesign(designContestId);
   	  }
   	  contestName = mainWidget.softwareCompetition.assetDTO.name;
   }
   
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
   if (mainWidget.softwareCompetition.multiRound) {
	   mainWidget.softwareCompetition.milestoneDate = new Date();
	   mainWidget.softwareCompetition.milestoneDate.setTime(startDate.getTime() + milestoneDateHours * 60 * 60 * 1000);
   }
   
   //prizes is on category id
   fillPrizes();
   
   return true;
}

function fillDevFromSelectedDesign(designContestId) {
   $.ajax({
      type: 'GET',
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

   //dates
   var startDate = getDateByIdPrefix('start');
   var milestoneDateHours = $('#milestoneDateDay').val() * 24 + parseInt($("#milestoneDateHour").val());
   var endDateHours = $('#endDateDay').val() * 24 + parseInt($("#endDateHour").val())
   //validation
   var errors = [];
   
   validateContestName(contestName, errors);
   validateTcProject(tcProjectId, errors);

   if(isMultiRound) {
      if (milestoneDateHours == 0) {
         errors.push('Round 1 duration should be positive.');
      }
   } else {
	   milestoneDateHours = 0;
   }
   if (endDateHours == 0) {
	   if (isMultiRound){
		   errors.push('Round 2 duration should be positive.');
	   } else {
		   errors.push('Round 1 duration should be positive.');
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

   if(copilotUserId == 0) {
       mainWidget.softwareCompetition.copilotCost = 0;
   } else {
       mainWidget.softwareCompetition.copilotCost = copilotFees[contestTypeId].copilotFee;
   }

   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification = new com.topcoder.direct.ProjectStudioSpecification();
   if (mainWidget.softwareCompetition.multiRound) {
	   mainWidget.softwareCompetition.milestoneDate = new Date();
	   mainWidget.softwareCompetition.milestoneDate.setTime(startDate.getTime() + milestoneDateHours * 60 * 60 * 1000);
   }
   // set end date
   mainWidget.softwareCompetition.subEndDate = new Date();
   mainWidget.softwareCompetition.subEndDate.setTime(startDate.getTime() + (milestoneDateHours + endDateHours) * 60 * 60 * 1000);
   
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

function initContestNamesFromDesign() {   
   $.ajax({
      type: 'GET',
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
