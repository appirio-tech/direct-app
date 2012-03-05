/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Main Script. It contains the functions/variables shared for launch contest/edit contest.
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - move some functions for modal pop ups to main.js file
 *
 * Version 1.1.1 (TC Direct Release Assembly 7) Change notes:
 * - Allow set digital run field manually.
 *
 * Version 1.1.2 (TC Direct - Software Creation Update Assembly) Change notes:
 * - Add method getCopilotsByDirectProjectId to get copilots info via Ajax.
 * - Update method saveAsDraftRequestSoftware to add copilot id and name into request.
 * Version 1.2 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.1.3 (TC Direct - Release Bug Fix Assembly) Change notes:
 * - Change time zone from GMT-04 to UTC-05.
 * - Update removeDocument method to add "studio:true" parameter.
 * Version 1.3 TC Direct Replatforming Release 2 change notes:
 * - Add support to set milestone prizes for software contest.
 * - The studio contest can have specification review cost.
 * Version 1.4 TC Direct Replatforming Release 4 change notes:
 * - Add support to set Maximum Submissions for studio contest.
 *
 * Version 1.4.1 (Direct Improvements Assembly Release 2) Change notes:
 * - Change the previewContest function to preview the copilot contest correctly.
 *
 * Version 1.5 (Release Assembly - TC Direct UI Improvement Assembly 3) Change notes:
 * - Fix the styles of catalog selection dropdown.
 *
 * Version 1.6 TC Cockpit Post a Copilot Assembly  change notes:
 * - Add more checks.
 * 
 * Version 1.7 (Release Assembly - TC Cockpit Contest Edit and Upload Update) Change notes:
 * - Fixed bug TCCC-3724. Updated logic for editing the contests.
 *
 * Version 1.8 (Release Assembly - TC Direct Cockpit Release One) change notes:
 * - Update fillPrizes(), always try loading billing contest fee for billingFees global object first
 *   because it's loaded via ajax and has the latest data.
 *
 * @author isv, TCSASSEMBLER
 * @version 1.8
 */

 /**
 * mainWidget
 */
var mainWidget = new com.topcoder.direct.MainWidget();

/**
 * documents
 */
var currentDocument = {};
var documents = [];

var originalPrizes;
var phaseOpen;
var isCompleted;
var isCancelled;

/**
 * Configurations
 */
var studioSubtypeOverviews = [];
var studioSubtypeFees = [];
var fileTypes = [];
var billingInfos = [];
var copilotFees = {};
//software
var softwareContestFees = {};
var originalSoftwareContestFees = {};

/**
 * Local cache for fees for certain billing project.
 *
 * The object key is the billing project id, the value is fee object array.
 */
var billingFees = {};

/**
 * Local cache for copilots for direct project.
 */
var directProjectCopilots = {};

/**
 * software documents
 */
// current document for uploading
var swCurrentDocument = {};
// it contains all uploadDocument or compDocuments
// uses a field of "comp" of type boolean to indicate if it is comDocuments or not.
// It is added for edit handling where comDocument is handled differently
var swDocuments = [];

// represents project id of reporting contest type. 
var REPORTING_ID = "36"
/**
 * Configuration/General Set up
 */
$(document).ready(function() {

   // loading some configuration data
   $.ajax({
      type: 'GET',
      url:  ctx+"/launch/getStudioConfigs",
      data: {},
      cache: false,
      dataType: 'json',
      async : false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
            studioSubtypeOverviews = result.overview;
            studioSubtypeFees = result.fees;
            fileTypes = result.fileTypes;
            softwareContestFees = result.softwareContestFees;
            originalSoftwareContestFees = $.extend(true,{},softwareContestFees);
            billingInfos = result.billingInfos;
			copilotFees = result.copilotFees;
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      }
   });

   //prizes
   $('.prizesInner .addButton').click(function(){
     if($('#extraPrizes').is( ":hidden ")){
       $('#extraPrizes input').val('');
       $('#extraPrizes').show();
     }
   }); //click


   $('.prizesInner .removeButton').click(function(){
      if(isExtraPrizesEmpty()) {
         $('#extraPrizes').hide();
      } else {
          showErrors("there is prize still set in this row.");
      }
   });//click

   //file types
   $('.fileType').click(function(){
       $('.deliverablesInner .checkInput').append('<div><input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" /></div>');
   });

  // Document uploader set up
  var uploader =
  new AjaxUpload(null, {
	    action: ctx + '/launch/documentUpload',
	    name : 'document',
	    responseType : 'json',
	    onSubmit : function(file , ext){
	       //software document
	       swCurrentDocument['fileName'] = file;

	       uploader.setData(
	       {
	         studio:false,
	         contestFileDescription:swCurrentDocument['description'],
	         documentTypeId:swCurrentDocument['documentTypeId']
	         }
	       );

	       modalPreloader();
	    },
	    onComplete : function(file, jsonResult){
	            handleJsonResult(jsonResult,
	            function(result) {
	              var documentId = result.documentId;
	              swCurrentDocument['documentId'] = documentId;
	              swDocuments.push(swCurrentDocument);

	              //only one requirement document
	              if(swCurrentDocument['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
	                 $('.reqDocCheck').hide();
	              }

	              swCurrentDocument = {};
	              $('#swFileDescription, #documentList').val('');

	              swRefreshDocuments();

	              modalClose();
	              uploader._input = $("#uploadButtonDiv input[type='file']").get(0);
	            },
	            function(errorMessage) {
	                showServerError(errorMessage);
	                modalClose();
	            });
	    }
	  }, true);

//Software uploader
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
    onComplete : function(file, jsonResult){
            handleJsonResult(jsonResult,
            function(result) {
              var documentId = result.documentId;
              swCurrentDocument['documentId'] = documentId;
              swDocuments.push(swCurrentDocument);

              //only one requirement document
              if(swCurrentDocument['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
                 $('.reqDocCheck').hide();
              }

              swCurrentDocument = {};
              $('#swFileDescription, #documentList').val('');

              swRefreshDocuments();

              modalClose();
            },
            function(errorMessage) {
                showServerError(errorMessage);
                modalClose();
            });
    }
  }, false);
  
  $('#fileUploadBtn').click(function(){
    var fileName = uploader._input.value;
    var description = $('#fileDescription').val();

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

    swCurrentDocument['description'] = description;
    if($('#specDoc').is(':visible:checked')) {
      swCurrentDocument['documentTypeId'] = REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
    } else {
      swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
    }
   
    uploader.submit();
  });

  $('#swFileUploadBtn').click(function(){
    var fileName = swUploader._input.value;
    var description = $('#swFileDescription').val();

    var errors = [];

    if(!checkRequired(fileName)) {
        errors.push('No file is selected.');
    } else {
        var fileNameToCheck = fileName.toLowerCase();
        if (fileNameToCheck.indexOf('fakepath') >= 0) {
            var p1 = fileNameToCheck.lastIndexOf("\\");
            var p2 = fileNameToCheck.lastIndexOf("/");
            var p = Math.max(p1, p2);
            fileNameToCheck = fileNameToCheck.substring(p + 1);
        }
        $('.fileInput').each(function(index, item) {
            if ($(item).html().toLowerCase() == fileNameToCheck) {
                errors.push('Such a file is already uploaded');
            }
        });
    }

    if(!checkRequired(description)) {
        errors.push('File description is empty.');
    }

    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }

    swCurrentDocument['description'] = description;
    if($('#swSpecDoc').is(':visible:checked')) {
      swCurrentDocument['documentTypeId'] = REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
    } else {
      swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
    }
    swUploader.submit();
  });


}); // end of initiation


/**
 * <p>
 * Update contest administration fee when billing project or studio sub type is changed.
 * </p>
 */
function updateContestFee( ) {
    var isStudio = ('STUDIO' == getContestType(true)[0]);    
    var contestTypeId = getContestType(true)[1];	
    var billingProjectId = $('select#billingProjects').val();
    
    var billingContestFee = getBillingContestFee(billingProjectId, contestTypeId);
    
    if(isStudio) {    	
    	  //for studio        
        if(billingContestFee >= 0) {
        	 mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = billingContestFee;
             mainWidget.softwareCompetition.adminFee = billingContestFee;
        } else {
           $.each(studioSubtypeFees, function(i, feeItem) {
               if(feeItem.id == contestTypeId) {
                  // update contest fees
            	   mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = feeItem.contestFee;
				   mainWidget.softwareCompetition.adminFee = feeItem.contestFee;
               }
           });        	
        }        
        mainWidget.softwareCompetition.projectHeader.setAdminFee(mainWidget.softwareCompetition.projectHeader.contestAdministrationFee);
    } else {
    	  //for software
    	  var feeObject = softwareContestFees[contestTypeId];
        if(billingContestFee >= 0) {
        	 //update corresponding contest fee        	 
        	 if(feeObject) {
        	 	  feeObject.contestFee = billingContestFee;
        	 }
        } else {
        	 //rollback
        	 if(feeObject) {
        	 	  feeObject.contestFee = originalSoftwareContestFees[contestTypeId].contestFee;
        	 }
        }   	  
    	  
    	  resetSoftwarePrizes();
    	  fillPrizes();
    }
}

/**
 * initiate contest fee in edit page
 */
function initContestFeeForEdit(isStudio, contestTypeId, billingProjectId) {    
    var billingContestFee = getBillingContestFee(billingProjectId, contestTypeId);

    if(isStudio) {    	
    	  //for studio        
    	  //nothing
    } else {
    	  //for software
        if(billingContestFee >= 0) {
        	 //update corresponding contest fee
        	 var feeObject = softwareContestFees[contestTypeId];
        	 if(feeObject) {
                 softwareContestFees[contestTypeId].contestFee = billingContestFee;
        	 }
        }    	  
    }
}

function getBillingContestFee(billingProjectId, contestTypeId) {
    if(billingProjectId <=0 ) {
       return -1;
    }
	
	  var fee = -1;
	  
	  var fees = getContestFeesForBillingProject(billingProjectId);
	  
	  $.each(fees, function(i,feeItem){
		  // here studio contest is the same as software competition, they are both different with the original studio contest.
	  	 if(feeItem.contestTypeId == contestTypeId) {
	  	 	   fee = feeItem.contestFee;
	  	 } 	  	 
	  });
	  
	  return fee;
}

/**
 * Gets contest fees for billing project.
 *
 * @param billingProjectId billing project id
 */
function getContestFeesForBillingProject(billingProjectId) {
	  if(billingFees[billingProjectId] != null) {
	  	 return billingFees[billingProjectId];
	  }
	  
	  var fees = [];
	  
	  var request = {billingProjectId:billingProjectId};
	  
    $.ajax({
       type: 'GET',
       url:  ctx + "/launch/getBillingProjectContestFees",
       data: request,
       cache: false,
       async: false,
       dataType: 'json',
       success: function(jsonResult) {
           handleJsonResult(jsonResult,
           function(result) {
               if(result.fees) {
                  fees = result.fees;
               }
           },
           function(errorMessage) {
               showServerError(errorMessage);
           });
       }
    });
    
    billingFees[billingProjectId] = fees;
    return fees;
}

/**
 * Gets copilots for direct project.
 *
 * @param directProjectId the direct project id
 */
function getCopilotsByDirectProjectId(directProjectId) {
    if(directProjectCopilots[directProjectId] != null) {
        return directProjectCopilots[directProjectId];
    }

    var returnValue = {};

    var request = {directProjectId:directProjectId};

    $.ajax({
       type: 'GET',
       url:  ctx + "/launch/getDirectProjectCopilots",
       data: request,
       cache: false,
       async: false,
       dataType: 'json',
       success: function(jsonResult) {
           handleJsonResult(jsonResult,
           function(result) {
               returnValue.copilots = result.copilots;
               returnValue.selected = result.selected;
           },
           function(errorMessage) {
               showServerError(errorMessage);
           });
       }
    });

    directProjectCopilots[directProjectId] = returnValue;
    return returnValue;
}


/**
 * Handles preview contest.
 */
function previewContest() {
  //http://studio.topcoder.com/?module=ViewContestDetails&ct=1001503
  if(!isContestSaved()) {
     showErrors("You must 'Save as Draft' before you can preview your contest.");
  } else {
    if(mainWidget.isSoftwareContest()) {
        window.open('https://www.topcoder.com/tc?module=ProjectDetail&pj='+mainWidget.softwareCompetition.projectHeader.id);
    } else {
        window.open('https://studio.topcoder.com/?module=ViewContestDetails&ct='+mainWidget.softwareCompetition.projectHeader.id);
    }
  }
}

function isContestSaved() {
   return (mainWidget.softwareCompetition.projectHeader.id > 0);
}


/**
 * Handles cancel contest.
 */
function cancelContest() {
   if(window.confirm("Are you sure you want to cancel? Please save your work first if you want to keep this contest.")) {
       window.location.href = "home";
   }
}

function saveAsDraftRequest() {
   if(mainWidget.isSoftwareContest()) {
       return saveAsDraftRequestSoftware();
   } else {
       return saveAsDraftRequestStudio();
   }
}

function saveAsDraftRequestSoftware() {
   var request = {};

   request['projectId'] = mainWidget.softwareCompetition.projectHeader.id;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'SOFTWARE';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;
    if (!isNaN(mainWidget.softwareCompetition.copilotUserId)) {
        request['contestCopilotId'] = mainWidget.softwareCompetition.copilotUserId;
        request['contestCopilotName'] = mainWidget.softwareCompetition.copilotUserName;
    }

   if(isDevOrDesign()) {
      //only check for design
      if(isDesign() && mainWidget.softwareCompetition.projectHeader.id <= 0) {
          if(window.confirm("Would you like to create the corresponding Component Development contest?")) {
             request['autoCreateDev'] = true;
          } else {
             request['autoCreateDev'] = false;
          }
      }

      request['rootCategoryId'] = mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId;
      request['categories'] = mainWidget.softwareCompetition.assetDTO.directjsCategories;
   }

   // update technologies
   if(isTechnologyContest()) {
      request['technologies'] = mainWidget.softwareCompetition.assetDTO.directjsTechnologies;
   }

   // if dev is derived from selected design
   if(mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded) {
   	  request['selectedDesignId'] = mainWidget.softwareCompetition.assetDTO.directjsDesignId;   	  
   }
   
   //milestone
   if(mainWidget.softwareCompetition.multiRound) {
      request['milestoneDate'] = formatDateForRequest(mainWidget.softwareCompetition.milestoneDate);
   }
   
   // the first page also gets some data
   updateSoftwarePrizes();

   // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);
   
   //document uploads
   request['docUploadIds'] = getUploadDocumentIds();
   request['docCompIds'] = getCompDocumentIds();
   request['hasMulti'] = mainWidget.softwareCompetition.multiRound;

   return request;
}

function saveAsDraftRequestStudio() {
   var request = {};
   request['projectId'] = mainWidget.softwareCompetition.projectHeader.id;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'STUDIO';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;
    
    if (!isNaN(mainWidget.softwareCompetition.copilotUserId)) {
        request['contestCopilotId'] = mainWidget.softwareCompetition.copilotUserId;
        request['contestCopilotName'] = mainWidget.softwareCompetition.copilotUserName;
    }

   updateStudioPrizes();
   // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

   //milestone
   if(mainWidget.softwareCompetition.multiRound) {
      request['milestoneDate'] = formatDateForRequest(mainWidget.softwareCompetition.milestoneDate);
   }
   // end date
   request['endDate'] = formatDateForRequest(mainWidget.softwareCompetition.subEndDate);
   request['hasMulti'] = mainWidget.softwareCompetition.multiRound;

   //document uploads
   request['docUploadIds'] = getStudioDocumentIds();
   request['docCompIds'] = getCompDocumentIds();
   request['fileTypes'] = mainWidget.softwareCompetition.fileTypes;

   return request;
}

function handleSaveAsDraftContestResult(jsonResult) {
   if(mainWidget.isSoftwareContest()) {
       handleSaveAsDraftContestResultSoftware(jsonResult);
   } else {
       handleSaveAsDraftContestResultStudio(jsonResult);
   }
}

function handleSaveAsDraftContestResultSoftware(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
          mainWidget.softwareCompetition.projectHeader.id = result.projectId;
          modalClose();
          showSuccessfulMessage("Software Contest <span class='messageContestName'>" + contestName +"</span> has been saved successfully.");
        } else {
          modalClose();
          showSuccessfulMessage("Software Contest <span class='messageContestName'>" + contestName +"</span> has been updated successfully.");
        }

        //update endDate
        mainWidget.softwareCompetition.endDate = parseDate(result.endDate);
        mainWidget.softwareCompetition.paidFee = result.paidFee;
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

function handleSaveAsDraftContestResultStudio(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
        	mainWidget.softwareCompetition.projectHeader.id = result.projectId;
          showSuccessfulMessage("Studio Contest <span class='messageContestName'>" + contestName +"</span> has been saved successfully.");
        } else {
          showSuccessfulMessage("Studio Contest <span class='messageContestName'>" + contestName +"</span> has been updated successfully.");
        }

        //update admin fee, to be fixed
        mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = result.paidFee;

    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}


function showPage(pageId) {
   $('.launchpage').hide();

   // remove the 'Maximum Submissions' property and Allow stock art property
   if(pageId == "contestSelectionPage") {
		delete mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'];
		delete mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'];
   }
   
   if(pageId == "overviewSoftwarePage") {
      if(isTechnologyContest()) {
      	 $('#swTechnologyDiv').show();
      } else {
         $('#swTechnologyDiv').hide();
      }
      
      if(isDevOrDesign()) {         
         $('#swCatalogDiv').show();
      } else {
         $('#swCatalogDiv').hide();
      }
   }

   if(pageId == "reviewPage") {
      updateReviewStudio();
   }

   if(pageId == "reviewSoftwarePage") {
      updateReviewSoftware();
   }

   if(pageId == "orderReviewPage") {
      updateOrderReviewStudio();
   }

   if(pageId == "orderReviewSoftwarePage") {
      updateOrderReviewSoftware();
   }

   $('#'+pageId).show();
   
   if (pageId == "overviewPage" && $("#milestonePrizeDiv").is(":visible") && !$("#milestonePrizeDiv .numSelect select").data('customized')) {
       $("#milestonePrizeDiv .numSelect select").data('customized',true);
       $("#milestonePrizeDiv .numSelect select").sSelect();
       $('#milestonePrizeDiv div div div div').html('5');
       $('#milestoneSubmissionNumber').val('5');
       $('#milestonePrizeDiv div div div ul li:eq(0) a').removeClass('hiLite');
       $('#milestonePrizeDiv div div div ul li:eq(4) a').addClass('hiLite');
       $('#milestonePrize').val('50');
   }
   if (pageId == "overviewSoftwarePage") {

       if (isDevOrDesign()) {
           $('#catalogSelect').sSelect();
       }
   }

   if (pageId == "overviewSoftwarePage" && $("#swMilestonePrizeDiv").is(":visible") && !$("#swMilestonePrizeDiv .numSelect select").data('customized')){
        $("#swMilestonePrizeDiv .numSelect select").data('customized',true);
        $("#swMilestonePrizeDiv .numSelect select").sSelect();
        $('#swMilestonePrizeDiv div div div div').html('2');
        $('#swMilestoneSubmissionNumber').val('2');
        $('#swMilestonePrizeDiv div div div ul li:eq(0) a').removeClass('hiLite');
        $('#swMilestonePrizeDiv div div div ul li:eq(1) a').addClass('hiLite');
        $('#swMilestonePrize').val('200');
   }
   
   $('html, body').animate({scrollTop:0}, 'fast');
}

function isExtraPrizesEmpty() {
  var empty = true;

  $.each($('#extraPrizes input'),function(i, element){
     if(isNotEmpty($(this).val())) {
        empty = false;
     }
  });

  return empty;
}

/**
 * Gets file types for each studio sub types
 */
function getStudioFileTypes(studioSubtypeId) {
  var types = [];

  $.each(fileTypes, function(i, fileType) {
      if(studioSubtypeId == fileType.id) {
         types = fileType.fileFormats;
      }
  });

  return types;
}

/**
 * Functions for handling documents.
 */
/**
 * Gets all studio document ids.
 *
 * @return studio document id array
 */
function getStudioDocumentIds() {
  return getDocumentIds(swDocuments);
}

/**
 * Gets uploadDocument ids for software competition.
 * It uses comp field to indicate if it is uploadDocument or compDocument
 *
 * @return upload document id array
 */
function getUploadDocumentIds() {
  var uploadDocuments = $.grep(swDocuments, function(doc,i) {
      return !doc['comp'];
  });
  return getDocumentIds(uploadDocuments);
}

/**
 * Gets compDocument ids.
 *
 * @return compDocument id array
 */
function getCompDocumentIds() {
  var compDocuments = $.grep(swDocuments, function(doc,i) {
      return doc['comp'];
  });
  return getDocumentIds(compDocuments);
}

/**
 * Gets document ids.
 *
 * @param docs the documents from which the ids will be extracted
 * @return document id array
 */
function getDocumentIds(docs) {
   return $.map(docs, function(doc, i){
      return doc.documentId;
    });
}

/**
 * Gets the Date object.
 *
 * @param datePart the format of MM/dd/yyyy
 * @param timePart  the format of HH:mm
 * @return Date object
 */
function getDate(datePart, timePart) {
   return Date.parse(datePart+' '+timePart,'MM/dd/yyyy HH:mm EST');
}

function formatDateForRequest(d) {
   if(d == null) {
       return null;
   }
   //rfc3399 format
   return d.toString("yyyy-MM-ddTHH:mm:00");
}

function formatDateForReview(d) {
   if(d == null) {
      return null;
   }
   return d.toString("MM/dd/yyyy T HH:mm EST ").replace('T ','at ').replace('EST','EST (UTC-05)');
}

function getDatePart(d) {
   if(d == null) {
      return null;
   }
   return d.toString("MM/dd/yyyy");
}

function getTimePart(d) {
   if(d == null) {
      return null;
   }
   return d.toString("HH:mm");
}

function getRoundedTime(d) {
    if(d == null) {
        return null;
    }
    return d.toString("HH:00");
}

function getServerTime() {
    var d = new Date();
    var utc = d.getTime() + (d.getTimezoneOffset() * 60000);
    return new Date(utc -5 * 3600000); // TC time
}

/**
 * Gets the date object using id prefix.
 *
 * @param idPrefix the id prefix
 * @return Date object
 */
function getDateByIdPrefix(idPrefix) {
  return getDate($('#'+idPrefix+'Date').val(),$('#'+idPrefix+'Time').val());
}

/**
 * Parsing date object. It could be directly date string or the date field of the
 * passed object.
 *
 * @return the <code>Date</code> object from the date object
 */
function parseDate(dateObj) {
     if(!dateObj) {
         return null;
     }

     if(dateObj.date) {
        return Date.parse(dateObj.date,'MM/dd/yyyy HH:mm');
     } else {
         return Date.parse(dateObj,'MM/dd/yyyy HH:mm');
     }
}


/**
 * Gets contest type.
 *
 * @param ignoreTextCheck true if it doesn't need to check the selected text. it will be passed
 *        as true when the method is called from contestTypes change listener
 * @return a 2 element array. first element's value is "STUDIO" or "SOFTWARE"
 *         second element is the sub type id. both could be null if nothing is
 *         selected
 */
function getContestType(ignoreTextCheck) {
   if(!ignoreTextCheck && $('.selectDesing div.selectedTxt').html() == 'Select Contest Type') {
       return [null,null];
   }

   var typeValues = $('#contestTypes').val().match(/^(STUDIO|SOFTWARE)(\d+)$/);
   return [typeValues[1],parseInt(typeValues[2])];
}

function swRefreshDocuments() {
   $('#swDocumentList, #documentList').html('');

   var html = "";
   var template = unescape($('#swFileTemplte').html());
   $.each(swDocuments, function(i,doc) {
       html += $.validator.format(template, doc.documentId,doc.fileName, doc.description);
   });

   $('#swDocumentList, #documentList').html(html);

   //show requirment document if any
   $.each(swDocuments, function(i,doc) {
       var docId = doc['documentId'];
       if(doc['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
          $('.doc'+docId+'spec').show();
       } else {
         $('.doc'+docId+'spec').hide();
       }
   });

   if(!hasRequirementDocument()) {
        $('.reqDocCheck').show();
        $('#swSpecDoc, #specDoc').attr('checked',true);
   }
}

function hasRequirementDocument() {
   return $.grep(swDocuments, function(doc,i) {
       return doc['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
   }).length > 0;
}

function swRemoveDocument(documentId) {
   var doc = $.grep(swDocuments, function(doc,i){
       return doc.documentId == documentId;
   })[0];
   // if it is comp document, remove it directly
   if(doc['comp']) {
       removeSoftwareDocument(documentId);
       return;
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
      success: swHandleRemoveDocumentResult
   });

}

function swHandleRemoveDocumentResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
      var documentId = result.documentId;
      removeSoftwareDocument(documentId);
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

function removeSoftwareDocument(documentId) {
      $.each(swDocuments, function(i,doc) {
          if(doc && doc.documentId == documentId) {
            swDocuments.splice(i,1);
          }
      });

      swRefreshDocuments();

	  if ($(".copilotFileUploadDiv").length > 0) {
        var p = $(".copilotFileUploadDiv input[type=hidden][value=" + documentId + "]").parent().parent();
        if ($(".copilotFileUploadDiv .rowItem p").length == 1) {
            p.find(".addButton").click();
        }
        p.remove();
    }
}

/**
 * Software Prize related function
 */
var customCosts = null;

/**
 * reset the prizes radios/items. It is called when contest type or billing project is changed.
 *
 * NOTE: the actual rendering is in validate or continue method where the category id is applied
 */
function resetSoftwarePrizes() {
     var billingProjectId = parseInt($('select#billingProjects').val());

     $('input[name="prizeRadio"]:nth(1)').attr('checked','checked');

     //reset custom costs
     //customCosts = null;

     /*
     if(isPrizeEditable(billingProjectId)) {
         $('.customRadio').show();
     } else {
         $('.customRadio').hide();
     }
     */
     $('.customRadio').show();
}

/**
 * Rerender the prizes on the page depending on the contest type and prize type.
 * It is called when either contest type or prize type is changed. Therefore it is called in
 * validateFieldsContestSelectionSoftware (it is when contest type is changed) and listener function
 * when the prize radio button is changed.
 *
 * @see updateSoftwarePrizes which is for persisting all changes
 */
function fillPrizes() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
       return;
   }

   var prizeType = $('input[name="prizeRadio"]:checked').val();
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];
   if(!feeObject) {
        showErrors('no fee found for project category ' + projectCategoryId);
        return;
   }
   var contestCost = getContestCost(feeObject, prizeType);

   if (contestCost == undefined) {
        return;
   }

   var firstPlaceAmount = contestCost.firstPlaceCost.formatMoney(2);
//    originalPrizes = [];
//    originalPrizes.push(contestCost.firstPlaceCost + '');
//    originalPrizes.push(contestCost.drCost + '');
    
   $('#swFirstPlace').val(firstPlaceAmount);
   $('#rswFirstPlace').html(firstPlaceAmount);
   $('#swSecondPlace,#rswSecondPlace').html(contestCost.secondPlaceCost.formatMoney(2));
   $('#swReviewCost,#rswReviewCost').html(contestCost.reviewBoardCost.formatMoney(2));
   $('#swReliabilityBonus,#rswReliabilityBonus').html(contestCost.reliabilityBonusCost.formatMoney(2));
   $('#rswDigitalRun').html(contestCost.drCost.formatMoney(2));
   $('#swDigitalRun').val(contestCost.drCost.formatMoney(2));
   
    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();

    var contestBillingFee = -1;

    if(billingFees[billingProjectId]!= null) {
        var fees = billingFees[billingProjectId];

        for(var i = 0; i < fees.length; ++i) {
            if(fees[i].contestTypeId == projectCategoryId) {
                contestBillingFee = fees[i].contestFee;
            }
        }
    }

    if(contestBillingFee >= 0) {
        $('#swContestFee,#rswContestFee').html(contestBillingFee);
    } else {
        // no billing is loaded, use the default fee loaded from configuration
   $('#swContestFee,#rswContestFee').html(feeObject.contestFee.formatMoney(2));
    }

   
   $('#swCopilotFee,#rswCopilotFee').html(mainWidget.softwareCompetition.copilotCost.formatMoney(2));

   $('#swTotal,#rswTotal').html((getContestTotal(feeObject, prizeType, false, true, (contestBillingFee >= 0 ? contestBillingFee : null)) + mainWidget.softwareCompetition.copilotCost).formatMoney(2));

   //totals
   $('#swPrize_low').html((getContestTotal(feeObject, 'low', false, true, (contestBillingFee >= 0 ? contestBillingFee : null)) + mainWidget.softwareCompetition.copilotCost).formatMoney(2));
   $('#swPrize_medium').html((getContestTotal(feeObject, 'medium', false, true, (contestBillingFee >= 0 ? contestBillingFee : null)) + mainWidget.softwareCompetition.copilotCost).formatMoney(2));
   $('#swPrize_high').html((getContestTotal(feeObject, 'high', false, true, (contestBillingFee >= 0 ? contestBillingFee : null)) + mainWidget.softwareCompetition.copilotCost).formatMoney(2));

   // spec cost
   $('#swSpecCost,#rswSpecCost').html(feeObject.specReviewCost.formatMoney(2));

   //if custom, make the first place editable
   if(prizeType == 'custom') {
      $('#swFirstPlace').attr('readonly',false);
      $('#swFirstPlace').val(contestCost.firstPlaceCost);
      
      $('#swDigitalRun').attr('readonly',false);
      $('#swDigitalRun').val(contestCost.drCost);
   } else {
      $('#swFirstPlace').attr('readonly',true);
      $('#swDigitalRun').attr('readonly',true);
   }
}

/**
 * Gets current total contest fee.
 */
function getCurrentContestTotal(useDomElem) {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
       return 0;
   }

   var prizeType = $('input[name="prizeRadio"]:checked').val();
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject;
   if (mainWidget.competitionType == "STUDIO") {
	   for (var i = 0; i < studioSubtypeFees.length; i++) {
		   if (studioSubtypeFees[i].id == projectCategoryId) {
			   feeObject = studioSubtypeFees[i];
			   break;
		   }
	   } 
   } else {
	   feeObject = softwareContestFees[projectCategoryId];
   }
	   
   if(!feeObject) {
        showErrors('no fee found for project category ' + projectCategoryId);
        return 0;
   }
   
   if (mainWidget.competitionType == "STUDIO") {
	   var total = parseFloat(mainWidget.softwareCompetition.adminFee);
	   var prizeInputs = [];
	   var lastPrizeIndex = -1;
	   var stop = false;
	   $.each($('div.prizes .prizesInput'),function(i, element) {
	        var value = $.trim($(this).val());
	        prizeInputs.push(value);
	        if(isNotEmpty(value)) {
	        	if (!stop) {
	        		lastPrizeIndex = i;
	        	}
	        } else {
	        	stop = true;
	        }
	    });
	   prizeInputs.splice(lastPrizeIndex+1,10);
	   $.each(prizeInputs, function(i, value) {
	        if (checkRequired(value) && checkNumber(value)) {
	        	total += parseFloat(value);
	        }
	   });
	   if ($('#roundTypes').val() == 'multi') {
		   total += parseFloat($('#milestonePrize').val()) * parseFloat($('#milestoneSubmissionNumber').val());
	   }
	   // spec review cost
	   total += feeObject.specReviewCost;
	   return total;
   }
   
   return getContestTotal(feeObject, prizeType, useDomElem);
}

/**
 * It will fill and update the prizes in softwareCompetition object depending on the current contest type,prize type
 * Once you define the  contest type and prize type, all values will be determined.
 * Billing will only affect if the custom will show or not.
 */
function updateSoftwarePrizes() {
   //update all fees
   var projectHeader = mainWidget.softwareCompetition.projectHeader;
   var prizeType = $('input[name="prizeRadio"]:checked').val();
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var billingProjectId =  mainWidget.softwareCompetition.projectHeader.getBillingProject();
   var contestFee = -1;

    if (billingProjectId > 0) {
        var fees = billingFees[billingProjectId];

        for (var i = 0; i < fees.length; ++i) {
            if (fees[i].contestTypeId == projectCategoryId) {
                contestFee = fees[i].contestFee;
            }
        }
    } else {
        contestFee = softwareContestFees[projectCategoryId].contestFee;
    }

    if(contestFee < 0) {
        // still not get contest fee, use default
        contestFee = softwareContestFees[projectCategoryId].contestFee;
    }

   var feeObject = softwareContestFees[projectCategoryId];
   var contestCost = getContestCost(feeObject, prizeType);

   if (contestCost == undefined) {
        return;
   }

   projectHeader.setFirstPlaceCost(contestCost.firstPlaceCost);
   projectHeader.setSecondPlaceCost(contestCost.secondPlaceCost);
   projectHeader.setReviewCost(contestCost.reviewBoardCost);
   projectHeader.setReliabilityBonusCost(contestCost.reliabilityBonusCost);
   projectHeader.setDRPoints(contestCost.drCost);
   projectHeader.setMilestoneBonusCost(0);
   projectHeader.setAdminFee(contestFee);
   projectHeader.setSpecReviewCost(feeObject.specReviewCost);
   
   var prizes = [];
   prizes.push(new com.topcoder.direct.Prize(1, contestCost.firstPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
   prizes.push(new com.topcoder.direct.Prize(2, contestCost.secondPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
   if(mainWidget.softwareCompetition.multiRound) {
	   prizes.push(new com.topcoder.direct.Prize(1, parseFloat($('#swMilestonePrize').val()), MILESTONE_PRIZE_TYPE_ID, parseInt($('#swMilestoneSubmissionNumber').val())));
   }
   mainWidget.softwareCompetition.projectHeader.prizes = prizes;

   projectHeader.setCostLevel(RADIOVALUE_COSTLEVEL_MAP[prizeType]);
}

/**
 * This method fills and updates the prizes in softwareCompetition object depending on the current Studio contest type.
 * Once contest type is defined, all values are determined.
 */
function updateStudioPrizes() {
   //update all fees
   var projectHeader = mainWidget.softwareCompetition.projectHeader;
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = getStudioContestCost(projectCategoryId);
   if (projectHeader.prizes.length == 0) {
       projectHeader.setFirstPlaceCost(feeObject.firstPlaceCost);
       projectHeader.setSecondPlaceCost(feeObject.secondPlaceCost);
       var prizes = [];
       prizes.push(new com.topcoder.direct.Prize(1, feeObject.firstPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
       prizes.push(new com.topcoder.direct.Prize(2, feeObject.secondPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
       prizes.push(new com.topcoder.direct.Prize(1, 0, MILESTONE_PRIZE_TYPE_ID, 1));
       projectHeader.prizes = prizes;
       projectHeader.setDRPoints((feeObject.secondPlaceCost + feeObject.firstPlaceCost) * 0.25); 
   }
   projectHeader.setReviewCost(feeObject.reviewCost);
   projectHeader.setSpecReviewCost(feeObject.specReviewCost);   
}

/**
 * determine if the prize is editable or not
 * if the billing project is not selected, then it is not editable
 */
function isPrizeEditable(billingProjectId) {
   var editable = false;

   $.each(billingInfos, function(i, billingInfo) {
        if(billingInfo.id == billingProjectId) {
           editable = billingInfo.manualPrizeSetting;
        }
    });

    return editable;
}

function onFirstPlaceChangeKeyUp() {
   var value = $('#swFirstPlace').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        return;
   }
   
   onFirstPlaceChange();
}

/**
 * Handle digital run field key up event.
 */
function onDigitalRunChangeKeyUp() {
   var value = $('#swDigitalRun').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        return;
   }
   
   onDigitalRunChange();
}

/**
 * Handle milestone prize field key up event or milestone numberOfSubmission change event.
 */
function onMilestonePrizeChangeKeyUp() {
	var value = $('#swMilestonePrize').val();
	if(!checkRequired(value) || !checkNumber(value)) {
        return;
    }
	
	fillPrizes();
}

function onFirstPlaceChange() {
   var prizeType = $('input[name="prizeRadio"]:checked').val();
   if(prizeType != 'custom') {
       return;
   }

   var value = $('#swFirstPlace').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        showErrors('first place value is invalid.');
        return;
   }
   calcPrizes(value);
    fillPrizes();
}

function calcPrizes(firstPlacePrizeValue) {
   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data
   var firstPlace = parseFloat(firstPlacePrizeValue);
   var contestCost = getContestCost(feeObject, 'custom');
   var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
   contestCost.firstPlaceCost = firstPlace;
   contestCost.secondPlaceCost = calculateSecondPlacePrize(contestCost.firstPlaceCost);
   contestCost.reviewBoardCost = calculateReviewCost(contestCost.firstPlaceCost,categoryId);
   if (projectCategoryId != REPORTING_ID) 
   {
       contestCost.reliabilityBonusCost = calculateReliabilityPrize(contestCost.firstPlaceCost,contestCost.secondPlaceCost,categoryId);
       contestCost.drCost = calculateDRPoint(contestCost.firstPlaceCost, contestCost.secondPlaceCost, contestCost.reliabilityBonusCost);
   } else {
       contestCost.reliabilityBonusCost = 0;
       contestCost.drCost = 0;
   }
}

/**
 * Handle digital run field change event.
 */
function onDigitalRunChange() {
   var prizeType = $('input[name="prizeRadio"]:checked').val();
   if(prizeType != 'custom') {
       return;
   }

   var value = $('#swDigitalRun').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        showErrors('digital run value is invalid.');
        return;
   }

    calcDR(value);
   fillPrizes();
}

function calcDR(drPoints){
   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data
   var contestCost = getContestCost(feeObject, 'custom');
   contestCost.drCost = parseFloat(drPoints);
}

function calculateSecondPlacePrize(firstPlaceCost) {
    return firstPlaceCost * 0.5;
}

function calculateReviewCost(firstPlacePrize, categoryId) {
      var STANDARD_SUBMISSION_COUNT = 3;
      var STANDARD_PASSED_SCREENING_COUNT = 3;

      if (categoryId == getProjectCategoryIdByName('DEVELOPMENT')
              || categoryId == getProjectCategoryIdByName('DESIGN')) {
          // calculate as per component reviewer calculator.
          return getComponentReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
      } else if (categoryId == getProjectCategoryIdByName('ASSEMBLY')) {
          // calculate as per assembly reviewer calculator.
          return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT) * 1.5 * 1.2;
      } else if (categoryId == getProjectCategoryIdByName('CONCEPTUALIZATION')) {
          // calculate as per assembly reviewer calculator.
          return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT) * 1.5;
      } else if (categoryId == getProjectCategoryIdByName('ARCHITECTURE')) {
          return getArchitectureReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
      } else if (categoryId == getProjectCategoryIdByName('SPECIFICATION')
                      || categoryId == getProjectCategoryIdByName('TESTSUITES')
                      || categoryId == getProjectCategoryIdByName('TESTSCENARIOS')
                      || categoryId == getProjectCategoryIdByName('RIACOMPONENT')
                      || categoryId == getProjectCategoryIdByName('RIABUILD')
                      || categoryId == getProjectCategoryIdByName('UIPROTOTYPE')
                      || categoryId == getProjectCategoryIdByName('REPORTING')) {
          // calculate as per application reviewer calculator logic.
          return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
      } else {
          return 0;
      }

      return firstPlacePrize;
}

function getComponentReviewCost(firstPlacePrize, submissionCount, passedScreeningCount) {
    var prizePurse=1.5 * firstPlacePrize;
    var initialPurse=0.40 * prizePurse;
    var incrementalPurse=0.15 * prizePurse;
    var screeningCost=(0.86 * initialPurse + incrementalPurse * (submissionCount - 1)) * 0.10;
    var reviewCost=(0.86 * initialPurse + incrementalPurse * (passedScreeningCount - 1)) * 0.9 / 3.0;
    var aggregationCost=0.04 * initialPurse;
    var finalReviewerCost=0.10 * initialPurse;

    return screeningCost + 3 * reviewCost + aggregationCost + finalReviewerCost;
}

function getApplicationReviewCost(firstPlacePrize, submissionCount, passedScreeningCount) {
    var standardPrize = 750;
    var calculatedBaseRate=15 + (firstPlacePrize - standardPrize) * 0.01;
    var actualBaseRate=calculatedBaseRate;
    var calculatedReviewCost=26 * calculatedBaseRate;

    var screeningCost=actualBaseRate * 0.5 * submissionCount;
    var reviewCost=(Math.max(0, submissionCount + 1 - passedScreeningCount) * 1.5 + 2 * passedScreeningCount) * actualBaseRate;
    var aggregationCost=2 * actualBaseRate * 0.25;
    var finalReviewerCost=2 * actualBaseRate * 0.75;

    return screeningCost + 3 * reviewCost + aggregationCost + finalReviewerCost;
}

function getArchitectureReviewCost(firstPlacePrize, submissionCount, passedScreeningCount) {

        var multiplier = 1.5;
        var standardPrize = 750;
        var calculatedBaseRate=15 + (firstPlacePrize - standardPrize) * 0.01;
        var actualBaseRate=calculatedBaseRate;
        var calculatedReviewCost=26 * calculatedBaseRate;
        
        var screeningCost=actualBaseRate * 0.5 * submissionCount;
        var reviewCost=(Math.max(0, submissionCount + 1 - passedScreeningCount) * 1.5 + 2 * passedScreeningCount) * actualBaseRate;
        var aggregationCost=2 * actualBaseRate * 0.25;
        var finalReviewerCost=2 * actualBaseRate * 0.75;
        
        return screeningCost * multiplier + 3 * reviewCost * multiplier + aggregationCost * multiplier + finalReviewerCost * multiplier;
}


function calculateReliabilityPrize(firstPlacePrize, secondPlacePrize, categoryId) {
    return (firstPlacePrize + secondPlacePrize) * 0.2;
}

function calculateDRPoint(firstPlacePrize, secondPlacePrize, reliabilityPrize) {
   return (firstPlacePrize + secondPlacePrize + reliabilityPrize) * 0.25;
}

function getContestTotal(feeObject, prizeType, useDomElem, noMilestoneCost, actualFee) {
    var contestCost = getContestCost(feeObject, prizeType);
    var total = contestCost.firstPlaceCost + contestCost.secondPlaceCost + contestCost.reviewBoardCost
    + contestCost.reliabilityBonusCost + contestCost.drCost + (actualFee == null ? feeObject.contestFee : actualFee) + feeObject.specReviewCost;
    console.log(actualFee);
    if (noMilestoneCost) {
    	return total;
    }
    if (!useDomElem) {
    	var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
    	for (var i = 0; i < prizes.length; i++) {
    		if (prizes[i].prizeType.id == MILESTONE_PRIZE_TYPE_ID) {
    			total += prizes[i].prizeAmount * prizes[i].numberOfSubmissions;
    		}
    	}
    } else if (mainWidget.softwareCompetition.multiRound) {
    	var prize = parseFloat($("#swMilestonePrize").val());
    	if (!prize) {
    		prize = 0;
    	}
    	total += prize * parseFloat($("#swMilestoneSubmissionNumber").val());
    }
    return total;
}

function getContestCost(feeObject,prizeType) {
    if(prizeType == 'custom' ) {
       //If custom costs is not set, use medium to initalize it
       customCosts  = customCosts || $.extend({},getContestCost(feeObject, 'medium'));
       return customCosts;
    }

    return $.grep(feeObject.contestCost.contestCostBillingLevels, function(cost, i) {
        return cost.id == prizeType;
    })[0];
}

function getStudioContestCost(projectCategoryId) {
    for (var i = 0; i < studioSubtypeFees.length; i++) {
        if (studioSubtypeFees[i].id == projectCategoryId) {
            return studioSubtypeFees[i];
        }
    } 
}


/**
 * Software Technology/Category functions
 */
function sortTechnologySelects() {
   sortSelectOptions('masterTechnologiesSelect');
   sortSelectOptions('masterTechnologiesChoosenSelect');
}

function sortCategorySelects() {
   sortSelectOptions('select1_categories');
   sortSelectOptions('select2_categories');
}

function updateCategories(callback) {
   var catalogId = $('#catalogSelect').val();

   // reset categories
   $('#select1_categories').children().remove();
   $('#select2_categories').children().remove();

   if(catalogId <= 0 ) {
       return;
   }

   // loading categories data for the given catalog id
   $.ajax({
      type: 'GET',
      url:  ctx+"/launch/getCategories",
      data: {'catalogId' : catalogId },
      cache: false,
      dataType: 'json',
      async : false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
             if(result) {
                $.each(result, function(i, category) {
                     $("<option/>").val(category.id).text(category.name).appendTo("#select1_categories");
                });

                if(typeof callback == 'function') {
                   callback();
                }
             }
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      }
   });
}

function fillCategories() {
	     $('#select1_categories').val(mainWidget.softwareCompetition.assetDTO.directjsCategories);
       $('#select1_categories option:selected').appendTo('#select2_categories');
       sortCategorySelects();   	 	 
}


/**
 * Shared validation functions
 */
function validateContestName(contestName, errors) {
   if(!checkRequired(contestName)) {
       errors.push('Contest name is empty.');
   }
}

function validateTcProject(tcProjectId, errors) {
   if(tcProjectId < 0) {
       errors.push('No project is selected.');
   }
}

function validatePrizes(errors) {
   var prizeInputs = [];
   var lastPrizeIndex = -1;
    var errorsAdded = false;
   var stop = false;
   $.each($('div.prizes .prizesInput'),function(i, element) {
        var value = $.trim($(this).val());
        prizeInputs.push(value);
        if(isNotEmpty(value)) {
        	if (!stop) {
        		lastPrizeIndex = i;
        	}
        } else {
        	stop = true;
        }
    });
    prizeInputs.splice(lastPrizeIndex+1, 10);

   //validation
   if(prizeInputs.length < 2) {
       errors.push('At least first & second place prizes should be set!');
       errorsAdded = true;
   }

   var prizes = [];
   $.each(prizeInputs, function(i, value) {
        if(!checkRequired(value) || !checkNumber(value)) {
            errors.push('Prize '+ (i+1) + ' is invalid.');
            errorsAdded = true;
        } else {
            prizes.push(new com.topcoder.direct.Prize((i+1),parseFloat(value), CONTEST_PRIZE_TYPE_ID, 1));
        }
    });

   //check prize order
   if(errors.length ==0 ) {
      var prevPrize = -1;
     $.each(prizes, function(i, value) {
          if(i != 0 && value.prizeAmount > prevPrize) {
              errors.push('Prize '+ (i+1) + ' is too large.');
              errorsAdded = true;
          }
          if (value.prizeType.id == CONTEST_PRIZE_TYPE_ID) {
        	  if (value.place == 1) {
        		  mainWidget.softwareCompetition.projectHeader.setFirstPlaceCost(value.prizeAmount);
        	  } else if (value.place == 2) {
        		  mainWidget.softwareCompetition.projectHeader.setSecondPlaceCost(value.prizeAmount);
        	  }
          }

          prevPrize = value.prizeAmount;
      });

      if(prizes[1].prizeAmount < 0.2 * prizes[0].prizeAmount) {
          errors.push('Second place prize should at least be 20% of first place prize!');
          errorsAdded = true;
      }
   }
    if (!errorsAdded) {
        if (phaseOpen) {
            var newPrizes = [];
            for (var i = 1; i <=5; i++) {
                var value = $('#prize' + i).val();
                if ($.trim(value).length > 0) {
                    newPrizes.push(value);
                }
            };
            
            if (newPrizes.length < originalPrizes.length) {
                errors.push('The prizes can not be deleted');
            } else {
                var n = Math.min(newPrizes.length, originalPrizes.length);
                for (var i = 0; i < n; i++) {
                    if (parseFloat(newPrizes[i]) < parseFloat(originalPrizes[i])){
                        errors.push('The prizes can not be decreased');
                        break;
                    }
                }
            }
        }
    }

   return prizes;
}

function validateFileTypes(errors) {
    //file deliverables
    var fileTypes = [];
    $.each($('#deliverablesCheckboxs input.defaultFileType'),function(i, element) {
       if($(this).is(':checked')) {
           fileTypes.push($(this).val());
       }
    });

    var otherFileTypes = [];
    $.each($('#deliverablesCheckboxs input.fileInput'),function(i, element) {
       var value = $.trim($(this).val());
       if($(this).prev().is(':checked') && isNotEmpty(value)) {
           otherFileTypes.push(value);
       }
    });

   if(fileTypes.length ==0 && otherFileTypes.length == 0 ) {
       errors.push("No file deliverable type is selected.");
   }

    $.each(otherFileTypes,function(i, value) {
       if(!checkFileType(value)) {
            errors.push("File type "+ value +" is invalid.");
       }
    });

   return [fileTypes,otherFileTypes];
}

/**
 * Checks to see if the technology is needed for the contest
 */
function isTechnologyContest() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       //all except for concept, spec and content creation.
       return !((categoryId == SOFTWARE_CATEGORY_ID_CONCEPT) || (categoryId == SOFTWARE_CATEGORY_ID_SPEC) 
	                     || (categoryId == SOFTWARE_CATEGORY_ID_CONTENT) || (categoryId == SOFTWARE_CATEGORY_ID_MARATHON) );
   }	
}


function isDevOrDesign() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DESIGN) || (categoryId == SOFTWARE_CATEGORY_ID_DEVELOPMENT);
   }
}

function isDev() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DEVELOPMENT);
   }
}

function isDesign() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DESIGN);
   }
}


function beforeAjax() {
	 modalPreloader();
}

function afterAjax() {
	 modalClose();
}
