/**
 * Main Script. It contains the functions/variables shared for launch contest/edit contest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
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


/**
 * Configuration/General Set up
 */
$(document).ready(function() {
  jQuery.ajaxSetup({
     timeout: 90000
  });

   //$(document).ajaxStart(function(){$.blockUI({ message: '<div id=loading> loading.... </div>' });}).ajaxStop($.unblockUI);
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
  });	
  
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
   
}); // end of initiation



/**
 * Handles preview contest.
 */
function previewContest() {
  //http://studio.topcoder.com/?module=ViewContestDetails&ct=1001503
  if(!isContestSaved()) {
     showErrors("You must 'Save as Draft' before you can preview your contest.");
  } else {
  	  var contestId = mainWidget.competition.contestData.contestId;
      window.open('http://studio.topcoder.com/?module=ViewContestDetails&ct='+contestId);
  }
}

function isContestSaved() {
	 return (mainWidget.competition.contestData.contestId > 0);
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
   request['docUploadIds'] = getDocumentIds();

   return request;   
}

function handleSaveAsDraftContestResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.competition.contestData.name;
        if(mainWidget.competition.contestData.contestId < 0 ) {
          mainWidget.competition.contestData.contestId = result.contestId;
          showMessage("Contest " + contestName +" has been saved successfully!");
        } else {
          showMessage("Contest " + contestName +" has been updated successfully!");
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

   if(pageId == "reviewPage") {
      updateReview();
   }

   if(pageId == "orderReviewPage") {
      updateOrderReview();
   }

   $('#'+pageId).show();
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
 * Common function to handle JSON result.
 */
function handleJsonResult(jsonResult, successCallBack, failureCallBack) {
   if(jsonResult.result) {
       successCallBack(jsonResult.result['return']);
   } else {
       failureCallBack(jsonResult.error.errorMessage);
   }
}

/**
 * Functions to hanle message/error messages.
 */
function showMessage(message) {
   $('#msgDialog p').html(message);
   $('#msgDialog').dialog('open');
}

function showGeneralError() {
   showErrors("Error occurred! Please retry it later.");
}

function showErrors(errors) {
   if(typeof errors == 'string') {
       var singleError = errors;
       errors = new Array();
       errors.push(singleError);
   }

   $('#errorDialog p').html('<ul></ul>');
   $.each(errors,function(i, error) {
        $('#errorDialog ul').append('<li>' + error + '</li>');
   });
   $('#errorDialog').dialog('open');
}

/** 
 * Functions for handling documents.
 */
function getDocumentIds() {
	 return $.map(documents, function(doc, i){
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
   return Date.parse(datePart+' '+timePart +' EST','MM/dd/yyyy HH:mm EST');
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
   return d.toString("MM/dd/yyyy T HH:mm EST (GMT-4)").replace('T ','at ').replace('G5T','GMT');
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
   return [typeValues[1],typeValues[2]];
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


/**
 * Shared validation functions
 */
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
