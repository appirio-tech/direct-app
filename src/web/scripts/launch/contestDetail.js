/**
 * Contest Detail Javascript
 *
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
$(document).ready(function(){
	  //general initialization
	  /* init select */
	  if($('select').length > 0){
	  	$('.selectSoftware select,.selectDesing select,.projectSelect select,.billingSelect select,.roundelect select,.startSelect select,.milestoneSelect select,.endSelect select,.startEtSelect select,.milestoneEtSelect select,.endEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect(); 
	  	
	  	$('.selectDesing div.selectedTxt').html('Select Contest Type');
	  }
	  
    /* init date-pack */
    if($('.date-pick').length > 0){
      $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
    }
	  
	  
	  /* Optgroup 2 columns fix */
	  if($('.selectDesing optgroup, .selectDesign .newListOptionTitle').length > 0){
	  	var optgroupMaxHeight = 0, num;
	  	
	  	$('.selectDesing optgroup').each(function(){
	  		num = $(this).children().length + 1;
	  		optgroupMaxHeight = num * 22 > optgroupMaxHeight ? num * 22 : optgroupMaxHeight;
	  	});
	  	
	  	$('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');
	  	
	  	$(window).resize(function(){$('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');});
	  	$(window).scroll(function(){$('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');});
	  }
		
    //contest type
		$("#editTypeButton").click(function(){			
			showTypeSectionEdit();
		});
		
		$(".save_btn").click(function(){			
			saveTypeSection();
		});
		
		$(".cancel_text").click(function(){			
			populateTypeSection();
			showTypeSectionDisplay();
		});

    //round 		
		$(".edit_round").click(function(){			
			showRoundSectionEdit();
		});
		
		$(".save_btn_round").click(function(){			
			saveRoundSection();
		});
		
		$(".cancel_text_round").click(function(){
			 populateRoundSection();
       showRoundSectionDisplay();												
		});
		
		//prize
		$(".edit_prize").click(function(){			
        showPrizeSectionEdit();			
		});
		
		$(".save_btn_prize").click(function(){			
			  savePrizeSection();									
		});
		$(".cancel_text_prize").click(function(){
			  populatePrizeSection();
        showPrizeSectionDisplay();												
		});		
		
		//spec
		$(".edit_spec").click(function(){
			 showSpecSectionEdit();
		});
		
		$(".save_btn_spec").click(function(){
			 saveSpecSection();
		});
		$(".cancel_text_spec").click(function(){
			  populateSpecSection();
			  showSpecSectionDisplay();
		});		

		//documents/files
		$(".edit_files").click(function(){
			 showDocumentSectionEdit();
		});
		
		$(".cancel_text_files").click(function(){
			 populateDocumentSection();
			 showDocumentSectionDisplay();
		});

   	//Get the contest and populate each section
   // loading some configuration data
   $.ajax({
      type: 'GET',
      url:  ctx+"/contest/detailJson",
      data: {"contestId":paramContestId},
      cache: false,
      dataType: 'json',
      async : false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
            initContest(result);
            
            //render values
            populateTypeSection();
            populateRoundSection();
            populatePrizeSection();
            populateSpecSection();
            populateDocumentSection();
          },
          function(errorMessage) {
              showErrors(errorMessage);
          })
      }
   });


   
   // round types
   $('#roundTypes').bind("change", function() {
        var roundType = $('#roundTypes').val();
        if(roundType == 'single') {
           $('#mileStoneEditDiv').hide();
           $('#milestonePrizeDiv').hide();  	
           $('#roundInfoDiv').hide();	     
           $('#rMileStoneTR').hide();
           $('#rMultiRoundInfoDiv').hide();                 
        } else {
           $('#mileStoneEditDiv').show();
           $('#milestonePrizeDiv').show();  	
           $('#roundInfoDiv').show();	     
           $('#rMileStoneTR').show();
           $('#rMultiRoundInfoDiv').show();                 
        }
   });
   $('#roundTypes').trigger("change");      
  
  //tiny mce BUGR 3813 
  tinyMCE.init({
  	mode : "exact",
  	elements : "contestDescription,contestIntroduction,round1Info,round2Info",
  	plugins : "paste",
  	theme : "advanced",  	
	  theme_advanced_buttons1 : "bold,italic,underline,strikethrough,undo,redo,pasteword, bullist,numlist",
	  theme_advanced_buttons2 : "",
	  theme_advanced_buttons3 : "",
	  init_instance_callback : function() {
	  	  $('table.mceLayout').css('width','100%');
	  }
  });
});


function initContest(contestJson) {
   mainWidget.competitionType = 'STUDIO';

   //contest data
   mainWidget.competition.contestData.contestId = contestJson.contestId;
   mainWidget.competition.contestData.contestTypeId = contestJson.contestTypeId;
   mainWidget.competition.contestData.name = contestJson.name;
   mainWidget.competition.contestData.projectId = contestJson.projectId;
   mainWidget.competition.contestData.tcDirectProjectId = contestJson.tcDirectProjectId;
   mainWidget.competition.contestData.tcDirectProjectName = contestJson.tcDirectProjectName;
   mainWidget.competition.contestData.billingProject = contestJson.billingProject;
   mainWidget.competition.contestData.shortSummary = contestJson.shortSummary;
   mainWidget.competition.contestData.contestDescriptionAndRequirements = contestJson.contestDescriptionAndRequirements;
   mainWidget.competition.contestData.finalFileFormat = contestJson.finalFileFormat || '';
   mainWidget.competition.contestData.otherFileFormats = contestJson.otherFileFormats || '';
   mainWidget.competition.contestData.statusId=contestJson.statusId;
   mainWidget.competition.contestData.detailedStatusId=contestJson.detailedStatusId;
   
   
   //multi round
   mainWidget.competition.contestData.multiRound = contestJson.multiRound;
   if(mainWidget.competition.contestData.multiRound) {
      mainWidget.milestonePrizeData.amount = contestJson.milestonePrize.amount;
      mainWidget.milestonePrizeData.numberOfSubmissions = contestJson.milestonePrize.numberOfSubmissions;
      mainWidget.competition.contestData.multiRoundData.roundOneIntroduction = contestJson.multiRoundData.roundOneIntroduction;
      mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction = contestJson.multiRoundData.roundTwoIntroduction;
   }

   //dates
   mainWidget.competition.startDate = parseDate(contestJson.startTime);
   mainWidget.competition.endDate = parseDate(contestJson.endTime);
   mainWidget.competition.milestoneDate = parseDate(contestJson.multiRoundData.milestoneDate);

   //prizes
   mainWidget.competition.contestData.prizes = [];
   $.each(contestJson.prizes,function(i,prize){
   	    mainWidget.competition.contestData.prizes.push(new com.topcoder.direct.PrizeData(prize.place,prize.amount));
   });
   mainWidget.competition.contestData.prizes.sort(function(a,b){return a.place - b.place;});
   
   //docs:
   documents = [];
   $.each(contestJson.docUploads,function(i, docUpload) {
   	   var doc = {};
   	   doc['documentId'] = docUpload.documentId;
   	   doc['fileName'] = docUpload.fileName;
   	   doc['description'] = docUpload.description;
   	   documents.push(doc);
   });
   
   //show activate button if it is still in draft
   if(CONTEST_DETAILED_STATUS_DRAFT == mainWidget.competition.contestData.detailedStatusId) {
   	  $('#resubmit').show(); 
   }
   
   //BUGR-3812: only show edit buttons if it is in draft or scheduled status
   if(CONTEST_DETAILED_STATUS_DRAFT != mainWidget.competition.contestData.detailedStatusId &&
      CONTEST_DETAILED_STATUS_SCHEDULED != mainWidget.competition.contestData.detailedStatusId) {
       $('img[alt="edit"]').parent().hide()   
    }   
   
   
   function parseDate(dateObj) {
   	   if(dateObj == null) {
   	   	  return null;
   	   }
   	   
   	   return Date.parse(dateObj.date,'MM/dd/yyyy HH:mm');
   }	 
}

/**
 * Type Section Functions
 */
function populateTypeSection() {
	//edit
	$('#contestTypes').getSetSSValue(mainWidget.competition.contestData.contestTypeId);
	$('#contestName').val(mainWidget.competition.contestData.name);
	if(isBillingEditable()) {		
		$('#billingAccountDivEdit').show(); 
	  $('#billingProjects').getSetSSValue(mainWidget.competition.contestData.billingProject);
  } else {
  	$('#billingAccountDivEdit').hide(); 
  }
	
	//display
	$('#rContestTypeName').html($("#contestTypes option[value=STUDIO"+ mainWidget.competition.contestData.contestTypeId +"]").text());
	$('#rTypeIntroduction').html(mainWidget.competition.contestData.shortSummary);
	$('#rContestName').html(mainWidget.competition.contestData.name);
	
	if(isBillingViewable()) {
	   $('.billingdisplay').show(); 
     var billingProjectId = mainWidget.competition.contestData.billingProject;
     $('#rBillingAccount').html((billingProjectId <= 0)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());	
  } else {
  	 $('.billingdisplay').hide();   	 
  }
}

function isBillingViewable() {
	// billing can not be found, meaning is not eligible, don't show/edit
	var billingProjectId = mainWidget.competition.contestData.billingProject;
	return billingProjectId <=0 || $("#billingProjects option[value="+ billingProjectId +"]").length == 1;
}

function isBillingEditable() {
	 var editableStatus = (CONTEST_DETAILED_STATUS_DRAFT == mainWidget.competition.contestData.detailedStatusId);
	 return isBillingViewable() && editableStatus;
}

function saveTypeSection() {
   // only handle studio type
   if("SOFTWARE" == getContestType()[0]) {
        alert("it is studio contest and you can not switch to software contest.");
        return;
   }
	
   if(!validateFieldsTypeSection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url: ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: function(jsonResult) {
         handleSaveAsDraftContestResult(jsonResult);
         populateTypeSection();  
         showTypeSectionDisplay();         			
      }
   });	 
}

function validateFieldsTypeSection() {
   var competitionType = getContestType()[0];
   var contestTypeId = parseInt(getContestType()[1]);
   var contestName = $('input#contestName').val();


   //validation
   var errors = [];

   if(!checkRequired(competitionType)) {
       errors.push('No contest type is selected.');
   }

   if(!checkRequired(contestName)) {
       errors.push('Contest name is empty.');
   }
   

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
   
   mainWidget.competition.contestData.contestTypeId = contestTypeId;
   mainWidget.competition.contestData.name = contestName;   
   if(isBillingEditable()) {		
   	 var billingProjectId = $('select#billingProjects').val();
   	 mainWidget.competition.contestData.billingProject = billingProjectId;
   }
      
   return true;	
}

function showTypeSectionDisplay() {
	 $(".contest_type").css("display","block");
	 $(".contest_type_edit").css("display","none");												         	
}

function showTypeSectionEdit() {
	 $(".contest_type").css("display","none");
	 $(".contest_type_edit").css("display","block");												         	
}


/**
 * Round Section Functions
 */
function populateRoundSection() {
	var isMultiRound = mainWidget.competition.contestData.multiRound;
	
	//edit
	$('#roundTypes').val(isMultiRound?'multi':'single');
	$('#roundTypes').trigger("change");         			      	
	$('#startDate').datePicker().val(getDatePart(mainWidget.competition.startDate)).trigger('change');
	$('#startTime').val(getTimePart(mainWidget.competition.startDate));
	$('#endDate').datePicker().val(getDatePart(mainWidget.competition.endDate)).trigger('change');
	$('#endTime').val(getTimePart(mainWidget.competition.startDate));
	
   if(!isMultiRound) {	
      $('#mileStoneEditDiv').hide();  		     
      
      $('#milestonePrizeDiv').hide();  	
      $('#roundInfoDiv').hide();	     
	 } else {
	 	  $('#mileStoneEditDiv').show();  	
	 	  $('#milestoneDate').datePicker().val(getDatePart(mainWidget.competition.milestoneDate)).trigger('change');
	 	  $('#milestoneTime').val(getTimePart(mainWidget.competition.milestoneDate));
	 	  
	 	  $('#milestonePrizeDiv').show();
	 	  $('#milestonePrize').val(mainWidget.milestonePrizeData.amount);
	 	  $('#milestoneSubmissionNumber').val(mainWidget.milestonePrizeData.numberOfSubmissions);
	 	  
	 	  $('#roundInfoDiv').show();	     
      $('#round1Info').val(mainWidget.competition.contestData.multiRoundData.roundOneIntroduction);
      $('#round2Info').val(mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction);	 	  
	 }
	
	//display
   $('#rStartDate').html(formatDateForReview(mainWidget.competition.startDate));
   $('table.projectStats td:eq(0)').html(formatDateForReview(mainWidget.competition.startDate));
   $('#rEndDate').html(formatDateForReview(mainWidget.competition.endDate));
   $('table.projectStats td:eq(1)').html(formatDateForReview(mainWidget.competition.endDate));
   
	
   if(!isMultiRound) {	
      $('#rMileStoneTR').hide();
      $('#rMultiRoundInfoDiv').hide();      
	 } else {
	 	  $('#rMileStoneTR').show();  	
	 	  $('#rMilestoneDate').html(formatDateForReview(mainWidget.competition.milestoneDate));
	 	  	 	  
      $('#rMultiRoundInfoDiv').show();
      $('#rMPrizesAmount').text('$'+mainWidget.milestonePrizeData.amount.formatMoney(2));
      $('#rMPrizesNumberOfSubmissions').html(mainWidget.milestonePrizeData.numberOfSubmissions);
      $('#rRound1Info').html(mainWidget.competition.contestData.multiRoundData.roundOneIntroduction);
      $('#rRound2Info').html(mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction);	 	  
	 }
}

function saveRoundSection() {
   // only handle studio type
   if("SOFTWARE" == getContestType()[0]) {
        alert("it is studio contest and you can not switch to software contest.");
        return;
   }
	
   if(!validateFieldsRoundSection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url: ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: function(jsonResult) {
         handleSaveAsDraftContestResult(jsonResult);
         populateRoundSection();  
         showRoundSectionDisplay();                  
      }
   });	    
}

function validateFieldsRoundSection() {
   var isMultiRound = ('multi' == $('#roundTypes').val());

   //dates
   var startDate = getDateByIdPrefix('start');
   var endDate = getDateByIdPrefix('end');
   var milestoneDate = getDateByIdPrefix('milestone');
   
   var round1Info = tinyMCE.get('round1Info').getContent(); 
   var round2Info = tinyMCE.get('round2Info').getContent(); 
   //milestone prize and submission numbers
   var milestonePrizeInput = $('#milestonePrize').val();
   

   //validation
   var errors = [];

   //check dates
   if(startDate >= endDate) {
       errors.push('Start date should be smaller than end date.');
   }

   var milestonePrize;
   if(isMultiRound) {
      if(startDate >= milestoneDate || milestoneDate >= endDate) {
         errors.push('Milestone date should be between start date and end date.');
      }

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

   mainWidget.competition.contestData.multiRound = isMultiRound;

   mainWidget.competition.startDate = startDate;
   mainWidget.competition.endDate = endDate;

   if(isMultiRound) {
      mainWidget.competition.milestoneDate = milestoneDate;
      mainWidget.milestonePrizeData.amount = milestonePrize;
      mainWidget.milestonePrizeData.numberOfSubmissions = parseInt($('#milestoneSubmissionNumber').val());
      mainWidget.competition.contestData.multiRoundData.roundOneIntroduction = round1Info;
      mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction = round2Info;
   }

   return true;
}

function showRoundSectionDisplay() {
	$(".contest_round").css("display","block");
	$(".contest_round_edit").css("display","none");
}

function showRoundSectionEdit() {
	$(".contest_round").css("display","none");
	$(".contest_round_edit").css("display","block");
}

/**
 * Prize Section Functions
 */
function populatePrizeSection() {
   $.each(mainWidget.competition.contestData.prizes, function(i, prize) {
       var place =  prize.place;
       var amount = prize.amount;
       $('#rPrize'+place).text('$'+amount.formatMoney(2));
       $('#prize'+place).val(amount);       
   });
   
   if(!isExtraPrizesEmpty()) {
      $('#extraPrizes').show();   	  
   } else {
   	  $('#extraPrizes').hide();   	  
   }
}

function savePrizeSection() {
   if(!validateFieldsPrizeSection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url: ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: function(jsonResult) {
         handleSaveAsDraftContestResult(jsonResult);
         populatePrizeSection();  
         showPrizeSectionDisplay();         			
      }
   });	 
}

function validateFieldsPrizeSection() {
   //validation
   var errors = [];

   var prizes = validatePrizes(errors);
   
   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
   
   mainWidget.competition.contestData.prizes = prizes;

   return true;	
}

function showPrizeSectionDisplay() {
	$(".contest_prize").css("display","block");
	$(".contest_prize_edit").css("display","none");
}

function showPrizeSectionEdit() {
	$(".contest_prize").css("display","none");
	$(".contest_prize_edit").css("display","block");
}


/**
 * Spec Section Functions
 */
function populateSpecSection() {	
	//edit
	$('#contestIntroduction').val(mainWidget.competition.contestData.shortSummary);
	$('#contestDescription').val(mainWidget.competition.contestData.contestDescriptionAndRequirements);
	
	//file types
   $('#deliverablesCheckboxs').html('');

   // default types
   var types = mainWidget.competition.contestData.finalFileFormat.split(",");
   var html = "";
   $.each(types, function(i, type) {
   	  if(isNotEmpty(type)) {
         html += '<input type="checkbox" checked="checked" value="' + type +'" class="defaultFileType" /> <label>' + type + '</label>';
       }  
   });
   // other file types
   types = mainWidget.competition.contestData.otherFileFormats.split(",");
   $.each(types, function(i, type) {
   	    if(isNotEmpty(type)) {
           html += '<input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" value="' + escape(type) + '"/>'
        }
   });

   $('#deliverablesCheckboxs').html(html);
	
	
	//display
   $('#rContestIntroduction').html(mainWidget.competition.contestData.shortSummary);   
   $('#rContestDescription').html(mainWidget.competition.contestData.contestDescriptionAndRequirements);
   
   //update it
   $('#rTypeIntroduction').html(mainWidget.competition.contestData.shortSummary);
   
   // file types
   var fileTypes = mainWidget.competition.contestData.finalFileFormat.split(",");
   $.merge(fileTypes,mainWidget.competition.contestData.otherFileFormats.split(","));

   html = "";
   $.each(fileTypes, function(i, fileType) {
   	   if(isNotEmpty(fileType)) {
          html += '<li>'+ fileType +'</li>';
       }
   });
   $('#rFinalDeliveries').html(html);   
}

function saveSpecSection() {
   if(!validateFieldsSpecSection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url: ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: function(jsonResult) {
         handleSaveAsDraftContestResult(jsonResult);
         populateSpecSection();  
         showSpecSectionDisplay();         			
      }
   });	 
}

function validateFieldsSpecSection() {
   var contestDescription = tinyMCE.get('contestDescription').getContent();
   var contestIntroduction = tinyMCE.get('contestIntroduction').getContent(); 
	
   //validation
   var errors = [];

   if(!checkRequired(contestIntroduction)) {
       errors.push('Contest introduction is empty.');
   }

   if(!checkRequired(contestDescription)) {
       errors.push('Contest description is empty.');
   }

   var fileTypesResult = validateFileTypes(errors);
   var fileTypes = fileTypesResult[0];
   var otherFileTypes = fileTypesResult[1];

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
   
   mainWidget.competition.contestData.finalFileFormat = fileTypes.join(',').toUpperCase();
   mainWidget.competition.contestData.otherFileFormats = otherFileTypes.join(',').toUpperCase();
   mainWidget.competition.contestData.shortSummary = contestIntroduction;
   mainWidget.competition.contestData.contestDescriptionAndRequirements = contestDescription;      
   return true;	
}

function showSpecSectionDisplay() {
		$(".contest_spec").css("display","block");
		$(".contest_spec_edit").css("display","none");												
}

function showSpecSectionEdit() {
		$(".contest_spec").css("display","none");
		$(".contest_spec_edit").css("display","block");												
}

/**
 * Document/Upload Section Functions
 */
function populateDocumentSection() {
	//edit
  refreshDocuments();	
	
	//display
	var html = "";
	var template = unescape($('#documentTemplate tbody').html());
	$.each(documents, function(i,doc) {
		 html += $.validator.format(template,(i+1),doc['fileName'],doc['description'],doc['documentId']);
	});
	$('#documentTable').html(html);
}

function showDocumentSectionDisplay() {
	 $(".contest_files").css("display","block");
	 $(".contest_files_edit").css("display","none");												
}

function showDocumentSectionEdit() {
	 $(".contest_files").css("display","none");
	 $(".contest_files_edit").css("display","block");												
}

/**
 * Activation in edit page
 */
function activateContestEdit() {	
   var competition = mainWidget.competition;

   if(!competition.contestData.isBillingSelected()) {
   	  showErrors("no billing project is selected.");
   	  return;
   }
   
   competition.contestData.statusId=CONTEST_STATUS_ACTIVE_PUBLIC;
   competition.contestData.detailedStatusId=CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC;

   //construct request data
   var request = saveAsDraftRequest();
   request['activationFlag'] = true;

   $.ajax({
      type: 'POST',
      url:  ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleActivationResultEdit
   });   
}


function handleActivationResultEdit(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.competition.contestData.name;
        showMessage("Contest " + contestName +" has been activated successfully!");
        $('#resubmit').hide();
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}
 