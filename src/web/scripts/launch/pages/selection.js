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
 * @version 1.5
 * @author TCSDEVELOPER, bugbuka
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

function validateFieldsContestSelectionAlgorithm() {
   var categoryId = getContestType()[1];
   var contestName = $('input#contestName').val();
   var tcProjectId = parseInt($('select#projects').val());
   var billingProjectId = parseInt($('select#billingProjects').val());

   var copilotUserId = parseInt($('select#contestCopilot').val());
   var copilotName = $('select#contestCopilot option:selected').text();

   var startDate = getDateByIdPrefix('start');
   var endDate = getDateByIdPrefix('end');
   
   //validation
   var errors = [];

   //validate contest
   validateContestName(contestName, errors);
   
   validateTcProject(tcProjectId, errors);
   
   // validate schedule
   if(startDate >= endDate) {
       errors.push('The end date should be after the start date.');
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

   //prizes is on category id
   fillPrizes();
   
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

   var startDate = getDateByIdPrefix('start');
   var checkpointDateHours = $('#checkpointDateDay').val() * 24 + parseInt($("#checkpointDateHour").val());
   
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
      if(checkpointDateHours == 0) {
	     errors.push('Checkpoint duration must be positive.');
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
	   mainWidget.softwareCompetition.checkpointDate = new Date();
	   mainWidget.softwareCompetition.checkpointDate.setTime(startDate.getTime() + checkpointDateHours * 60 * 60 * 1000);
   }
   
   //prizes is on category id
   fillPrizes();
   
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

   //dates
   var startDate = getDateByIdPrefix('start');
   var checkpointDateHours = $('#checkpointDateDay').val() * 24 + parseInt($("#checkpointDateHour").val());
   var endDateHours = $('#endDateDay').val() * 24 + parseInt($("#endDateHour").val())
   //validation
   var errors = [];
   
   validateContestName(contestName, errors);
   validateTcProject(tcProjectId, errors);

   if(isMultiRound) {
      if (checkpointDateHours == 0) {
         errors.push('Round 1 duration should be positive.');
      }
   } else {
	   checkpointDateHours = 0;
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
	   mainWidget.softwareCompetition.checkpointDate = new Date();
	   mainWidget.softwareCompetition.checkpointDate.setTime(startDate.getTime() + checkpointDateHours * 60 * 60 * 1000);
   }
   // set end date
   mainWidget.softwareCompetition.subEndDate = new Date();
   mainWidget.softwareCompetition.subEndDate.setTime(startDate.getTime() + (checkpointDateHours + endDateHours) * 60 * 60 * 1000);
   if($('#lccCheckBox').is(':checked')) {
   	   mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
       enableMCEPlaceholderText = true;
       $(['contestDescription', 'round1Info', 'round2Info']).each(function() {
            var obj = CKEDITOR.instances[this];
            if (obj.getData() == "") {
                obj.setData("Only members that register for this contest will see this description.");
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
   
   return true;
}

function continueContestSelection() {
   if(!validateFieldsContestSelection()) {
       return;
   }

   if(mainWidget.isSoftwareContest()) {
   	  showPage('overviewSoftwarePage');
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
