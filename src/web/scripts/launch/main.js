/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.1.1
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

/**
 * Configurations
 */
var studioSubtypeOverviews = [];
var studioSubtypeFees = [];
var fileTypes = [];
var billingInfos = [];
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
 * software documents
 */
// current document for uploading
var swCurrentDocument = {};
// it contains all uploadDocument or compDocuments
// uses a field of "comp" of type boolean to indicate if it is comDocuments or not.
// It is added for edit handling where comDocument is handled differently
var swDocuments = [];


/**
 * Configuration/General Set up
 */
$(document).ready(function() {
  jQuery.ajaxSetup({
     timeout: 90000
  });

   $(document).ajaxError(function(event, XMLHttpRequest, ajaxOptions, thrownError){
       showGeneralError();
   });

   initDialog('msgDialog', 500);
   initDialog('errorDialog', 500);

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
          },
          function(errorMessage) {
              showErrors(errorMessage);
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
          alert("there is prize still set in this row.");
      }
   });//click

   //file types
   $('.fileType').click(function(){
     if($('.deliverablesInner .fileInput').length < 3){
       $('.checkInput').append('<input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" />');
     }
   });

  // Document uploader set up
  var uploader =
  new AjaxUpload(null, {
    action: ctx + '/launch/documentUpload',
    name : 'document',
    responseType : 'json',
    onSubmit : function(file , ext){
       currentDocument['fileName'] = file;

       uploader.setData(
       {
         contestId:mainWidget.competition.contestData.contestId,
         contestFileDescription:currentDocument['description'],
         documentTypeId:-1
         }
       );

       $.blockUI({ message: '<div id=loading> loading.... </div>' });
    },
    onComplete : function(file, jsonResult){
            handleJsonResult(jsonResult,
            function(result) {
              var documentId = result.documentId;
              currentDocument['documentId'] = documentId;
              documents.push(currentDocument);

              currentDocument = {};
              $('#fileDescription').val('');

              refreshDocuments();

              $.unblockUI();
            },
            function(errorMessage) {
                showErrors(errorMessage);
                $.unblockUI();
            });
    }
  }, true);

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

    currentDocument['description'] = description;
    uploader.submit();
  });

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

       $.blockUI({ message: '<div id=loading> loading.... </div>' });
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
              $('#swFileDescription').val('');

              swRefreshDocuments();

              $.unblockUI();
            },
            function(errorMessage) {
                showErrors(errorMessage);
                $.unblockUI();
            });
    }
  }, false);

  $('#swFileUploadBtn').click(function(){
    var fileName = swUploader._input.value;
    var description = $('#swFileDescription').val();

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
    
    var billingContestFee = getBillingContestFee(billingProjectId, isStudio, contestTypeId);
    
    if(isStudio) {    	
    	  //for studio        
        if(billingContestFee >= 0) {
        	 mainWidget.competition.contestData.contestAdministrationFee = billingContestFee;
        } else {
           $.each(studioSubtypeFees, function(i, feeItem) {
               if(feeItem.id == contestTypeId) {
                  // update contest fees
                  mainWidget.competition.contestData.contestAdministrationFee = feeItem.contestFee;
               }
           });        	
        }        
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
    var billingContestFee = getBillingContestFee(billingProjectId, isStudio, contestTypeId);

    if(isStudio) {    	
    	  //for studio        
    	  //nothing
    } else {
    	  //for software
        if(billingContestFee >= 0) {
        	 //update corresponding contest fee
        	 var feeObject = softwareContestFees[contestTypeId];
        	 if(feeObject) {
        	 	  feeObject.contestFee = billingContestFee;
        	 }
        }    	  
    }
}

function getBillingContestFee(billingProjectId,isStudio, contestTypeId) {
    if(billingProjectId <=0 ) {
       return -1;
    }
	
	  var fee = -1;
	  
	  var fees = getContestFeesForBillingProject(billingProjectId);
	  
	  $.each(fees, function(i,feeItem){
	  	 if(isStudio && feeItem.studio && feeItem.contestTypeId == contestTypeId) {
	  	 	   fee = feeItem.contestFee;
	  	 } 
	  	 
	  	 if(!isStudio && !feeItem.studio && feeItem.contestTypeId == contestTypeId) {
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
               showErrors(errorMessage);
           });
       }
    });
    
    billingFees[billingProjectId] = fees;
    return fees;
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
        window.open('http://www.topcoder.com/tc?module=ProjectDetail&pj='+mainWidget.softwareCompetition.projectHeader.id);
    } else {
        window.open('http://studio.topcoder.com/?module=ViewContestDetails&ct='+mainWidget.competition.contestData.contestId);
    }
  }
}

function isContestSaved() {
   return (mainWidget.competition.contestData.contestId > 0 || mainWidget.softwareCompetition.projectHeader.id > 0);
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
   request['contestId'] = -1;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'SOFTWARE';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;

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
   
   // the first page also gets some data
   updateSoftwarePrizes();

   //document uploads
   request['docUploadIds'] = getUploadDocumentIds();
   request['docCompIds'] = getCompDocumentIds();

   return request;
}

function saveAsDraftRequestStudio() {
   var request = {};
   request['projectId'] = -1;
   request['contestId'] = mainWidget.competition.contestData.contestId;
   request['tcDirectProjectId'] = mainWidget.competition.contestData.tcDirectProjectId;
   request['competitionType'] = 'STUDIO';
   request['contestData'] = mainWidget.competition.contestData;

   //dates
   request['startDate'] = formatDateForRequest(mainWidget.competition.startDate);
   request['endDate'] = formatDateForRequest(mainWidget.competition.endDate);
   request['prizes'] = mainWidget.competition.contestData.prizes;

   //milestone
   if(mainWidget.competition.contestData.multiRound) {
      request['milestoneDate'] = formatDateForRequest(mainWidget.competition.milestoneDate);
      request['milestonePrizeAmount'] = mainWidget.milestonePrizeData.amount;
      request['milestonePrizeNumberOfSubmissions'] = mainWidget.milestonePrizeData.numberOfSubmissions;
   }

   //document uploads
   request['docUploadIds'] = getStudioDocumentIds();

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
          showMessage("Software Contest " + contestName +" has been saved successfully!");
        } else {
          showMessage("Software Contest " + contestName +" has been updated successfully!");
        }

        //update endDate
        mainWidget.softwareCompetition.endDate = parseDate(result.endDate);
        mainWidget.softwareCompetition.paidFee = result.paidFee;
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}

function handleSaveAsDraftContestResultStudio(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.competition.contestData.name;
        if(mainWidget.competition.contestData.contestId < 0 ) {
          mainWidget.competition.contestData.contestId = result.contestId;
          showMessage("Studio Contest " + contestName +" has been saved successfully!");
        } else {
          showMessage("Studio Contest " + contestName +" has been updated successfully!");
        }

        //update admin fee
        mainWidget.competition.contestData.contestAdministrationFee = result.contestAdministrationFee;

    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}


function showPage(pageId) {
   $('.launchpage').hide();

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
   
   if(pageId == "overviewPage" && !$(".numSelect select").data('customized')){
		$(".numSelect select").data('customized',true);
      	$(".numSelect select").sSelect();
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
         $.each(fileType.fileFormats, function(j, fileFormat) {
              types.push(fileFormat.value);
         });
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
  return getDocumentIds(documents);
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
   return d.toString("MM/dd/yyyy T HH:mm EST ").replace('T ','at ').replace('EST','EST (GMT-4)');
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

/**
 * Document related functions.
 */
function removeDocument(documentId) {
   $.ajax({
      type: 'POST',
      url:  ctx+"/launch/removeDocument",
      data: {
        documentId: documentId,
        contestId:mainWidget.competition.contestData.contestId
      },
      cache: false,
      dataType: 'json',
      success: handleRemoveDocumentResult
   });
}

function handleRemoveDocumentResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
      var documentId = result.documentId;

      $.each(documents, function(i,doc) {
          if(doc.documentId == documentId) {
            documents.splice(i,1);
          }
      });

      refreshDocuments();
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}

function refreshDocuments() {
   $('#documentList').html('');

   var html = "";
   var template = unescape($('#fileTemplte').html());
   $.each(documents, function(i,doc) {
       html += $.validator.format(template, doc.documentId,doc.fileName);
   });

   $('#documentList').html(html);
}


function swRefreshDocuments() {
   $('#swDocumentList').html('');

   var html = "";
   var template = unescape($('#swFileTemplte').html());
   $.each(swDocuments, function(i,doc) {
       html += $.validator.format(template, doc.documentId,doc.fileName, doc.description);
   });

   $('#swDocumentList').html(html);

   //show requirment document if any
   $.each(swDocuments, function(i,doc) {
       var docId = doc['documentId'];
       if(doc['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
          $('#doc'+docId+'spec').show();
       } else {
         $('#doc'+docId+'spec').hide();
       }
   });

   if(!hasRequirementDocument()) {
        $('.reqDocCheck').show();
        $('#swSpecDoc').attr('checked',true);
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
        showErrors(errorMessage);
    });
}

function removeSoftwareDocument(documentId) {
      $.each(swDocuments, function(i,doc) {
          if(doc && doc.documentId == documentId) {
            swDocuments.splice(i,1);
          }
      });

      swRefreshDocuments();
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
        alert('no fee found for project category ' + projectCategoryId);
        return;
   }
   var contestCost = getContestCost(feeObject, prizeType);

   var firstPlaceAmount = contestCost.firstPlaceCost.formatMoney(2);
   $('#swFirstPlace').val(firstPlaceAmount);
   $('#rswFirstPlace').html(firstPlaceAmount);
   $('#swSecondPlace,#rswSecondPlace').html(contestCost.secondPlaceCost.formatMoney(2));
   $('#swReviewCost,#rswReviewCost').html(contestCost.reviewBoardCost.formatMoney(2));
   $('#swReliabilityBonus,#rswReliabilityBonus').html(contestCost.reliabilityBonusCost.formatMoney(2));
   $('#rswDigitalRun').html(contestCost.drCost.formatMoney(2));
   $('#swDigitalRun').val(contestCost.drCost.formatMoney(2));
   
   $('#swContestFee,#rswContestFee').html(feeObject.contestFee.formatMoney(2));
   $('#swTotal,#rswTotal').html(getContestTotal(feeObject, prizeType).formatMoney(2));

   //totals
   $('#swPrize_low').html(getContestTotal(feeObject, 'low').formatMoney(2));
   $('#swPrize_medium').html(getContestTotal(feeObject, 'medium').formatMoney(2));
   $('#swPrize_high').html(getContestTotal(feeObject, 'high').formatMoney(2));

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
function getCurrentContestTotal() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
       return 0;
   }

   var prizeType = $('input[name="prizeRadio"]:checked').val();
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];
   if(!feeObject) {
        alert('no fee found for project category ' + projectCategoryId);
        return 0;
   }
   return getContestTotal(feeObject, prizeType);
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
   var feeObject = softwareContestFees[projectCategoryId];
   var contestCost = getContestCost(feeObject, prizeType);

   projectHeader.setFirstPlaceCost(contestCost.firstPlaceCost);
   projectHeader.setSecondPlaceCost(contestCost.secondPlaceCost);
   projectHeader.setReviewCost(contestCost.reviewBoardCost);
   projectHeader.setReliabilityBonusCost(contestCost.reliabilityBonusCost);
   projectHeader.setDRPoints(contestCost.drCost);
   projectHeader.setMilestoneBonusCost(0);
   projectHeader.setAdminFee(feeObject.contestFee);
   projectHeader.setSpecReviewCost(feeObject.specReviewCost);

   projectHeader.setCostLevel(RADIOVALUE_COSTLEVEL_MAP[prizeType]);
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

   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data
   var firstPlace = parseFloat(value);
   var contestCost = getContestCost(feeObject, 'custom');
   var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
   contestCost.firstPlaceCost = firstPlace;
   contestCost.secondPlaceCost = calculateSecondPlacePrize(contestCost.firstPlaceCost);
   contestCost.reviewBoardCost = calculateReviewCost(contestCost.firstPlaceCost,categoryId);
   contestCost.reliabilityBonusCost = calculateReliabilityPrize(contestCost.firstPlaceCost,contestCost.secondPlaceCost,categoryId);
   contestCost.drCost = calculateDRPoint(contestCost.firstPlaceCost, contestCost.secondPlaceCost, contestCost.reliabilityBonusCost);
   fillPrizes();
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

   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data
   var contestCost = getContestCost(feeObject, 'custom');
   contestCost.drCost = parseFloat(value);
   fillPrizes();
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
          return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT) * 1.5;
      } else if (categoryId == getProjectCategoryIdByName('ARCHITECTURE')) {
          return getArchitectureReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
      } else if (categoryId == getProjectCategoryIdByName('CONCEPTUALIZATION')
                      || categoryId == getProjectCategoryIdByName('SPECIFICATION')
                      || categoryId == getProjectCategoryIdByName('TESTSUITES')
                      || categoryId == getProjectCategoryIdByName('TESTSCENARIOS')
                      || categoryId == getProjectCategoryIdByName('RIACOMPONENT')
                      || categoryId == getProjectCategoryIdByName('RIABUILD')
                      || categoryId == getProjectCategoryIdByName('UIPROTOTYPE')) {
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

function getContestTotal(feeObject, prizeType) {
    var contestCost = getContestCost(feeObject, prizeType);
    return contestCost.firstPlaceCost + contestCost.secondPlaceCost + contestCost.reviewBoardCost
    + contestCost.reliabilityBonusCost + contestCost.drCost + feeObject.contestFee;

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
              showErrors(errorMessage);
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
   $.each($('div.prizes .prizesInput'),function(i, element) {
        var value = $.trim($(this).val());
        prizeInputs.push(value);
        if(isNotEmpty(value)) {
            lastPrizeIndex = i;
        }
    });
    prizeInputs.splice(lastPrizeIndex+1,5);

   //validation
   if(prizeInputs.length < 2) {
       errors.push('At least first & second place prizes should be set!');
   }

   var prizes = [];
   $.each(prizeInputs, function(i, value) {
        if(!checkRequired(value) || !checkNumber(value)) {
            errors.push('Prize '+ (i+1) + ' is invalid.');
        } else {
            prizes.push(new com.topcoder.direct.PrizeData((i+1),parseFloat(value)));
        }
    });

   //check prize order
   if(errors.length ==0 ) {
      var prevPrize = -1;
     $.each(prizes, function(i, value) {
          if(i != 0 && value.amount > prevPrize) {
              errors.push('Prize '+ (i+1) + ' is too large.');
          }

          prevPrize = value.amount;
      });

      if(prizes[1].amount < 0.2 * prizes[0].amount) {
          errors.push('Second place prize should at least be 20% of first place prize!');
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
       //all except for concept and spec.
       return !((categoryId == SOFTWARE_CATEGORY_ID_CONCEPT) || (categoryId == SOFTWARE_CATEGORY_ID_SPEC));
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
	 $.blockUI({ message: '<div id=loading> loading.... </div>' });
}

function afterAjax() {
	 $.unblockUI();
}
