/**
 * Contest Detail Javascript
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - Add repost and new version function on details page.
 * 
 * @author TCSDEVELOPER
 * @version 1.1
 */
$(document).ready(function(){
	  //general initialization
	      // populate the select option for software group
    $.each(projectCategoryArray,function(i, projectCategory) {
        $("<option/>").val("SOFTWARE"+projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Software']");
    });

	  /* init select */
	  if($('select').length > 0){
	  	//$('.selectSoftware select,.selectDesing select,.projectSelect select,.billingSelect select,.roundelect select,.startSelect select,.milestoneSelect select,.endSelect select,.startEtSelect select,.milestoneEtSelect select,.endEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect(); 
	  	
	  	//$('.selectDesing div.selectedTxt').html('Select Contest Type');
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
			 setRollbackDocuments();
			 showDocumentSectionEdit();
		});
		
		$(".save_btn_files").click(function(){
			 saveDocumentSection();
		});

		$(".cancel_text_files").click(function(){
			 rollbackDocuments();
			 populateDocumentSection();
			 showDocumentSectionDisplay();
		});

   	//Get the contest and populate each section
   // loading some configuration data
   $.ajax({
      type: 'GET',
      url:  ctx+"/contest/detailJson",
      data: {"projectId":paramContestId},
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
            populatePrizeSection(true);
            populateSpecSection(true);
            populateDocumentSection();
            
            //execute some actions specific for component design/dev
            //onContestTypeChange();
          },
          function(errorMessage) {
              showErrors(errorMessage);
          })
      }
   });
   
   // choose contest type
   $('#contestTypes').bind("change", function() {
   	   onContestTypeChange();
   });   
   
	 //technologies/categories
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
   
   // prizes
   $('input[name="prizeRadio"]').click(function(){
   	  fillPrizes();
   });
   
   $('#swFirstPlace').bind('keyup',function() {
       onFirstPlaceChangeKeyUp();
    });
    
  tinyMCE.init({
  	mode : "exact",
  	elements : "swDetailedRequirements,swGuidelines",
  	plugins : "paste",
  	theme : "advanced",  	
	  theme_advanced_buttons1 : "bold,italic,underline,strikethrough,undo,redo,pasteword, bullist,numlist,link",
	  theme_advanced_buttons2 : "",
	  theme_advanced_buttons3 : "",
	  init_instance_callback : function() {
	  	  $('table.mceLayout').css('width','100%');
	  }
  });
      
});

/**
 * event handler function when contest type is changed.
 */
function onContestTypeChange() {
   	  var contestType = getContestType(true)[0];
   	  var typeId = getContestType(true)[1];   	  
   	  var currentTypeId = -1;
   	  if(isContestSaved()) {
   	  	 currentTypeId = mainWidget.isSoftwareContest()? mainWidget.softwareCompetition.projectHeader.projectCategory.id:mainWidget.competition.contestData.contestTypeId;
   	  }   	  
 
   	  if(isContestSaved() && mainWidget.competitionType != contestType) {   	  	
   	  	   alert("You can not switch between studio and software after it is saved.");   	  	   
   	  	 
   	  	   return;
   	  }
   	  
   	  mainWidget.competitionType = contestType;
   	     	  
   	  if(isDevOrDesign()) {
   	  	 $('.component').show();
   	  } else {
   	  	 $('.component').hide();
   	  }

   	  if(typeId == currentTypeId) {
   	  	 // it is a revert, nothing to do here
   	  	 return;
   	  }
   	  
}


function initContest(contestJson) {
   mainWidget.competitionType = 'SOFTWARE';

   //contest data
   mainWidget.softwareCompetition.projectHeader.id = contestJson.contestId;
   mainWidget.softwareCompetition.projectHeader.projectCategory= contestJson.projectCategory;
   mainWidget.softwareCompetition.assetDTO.name = contestJson.contestName;
   mainWidget.softwareCompetition.endDate = parseDate(contestJson.endDate);
   mainWidget.softwareCompetition.paidFee = contestJson.paidFee;
   mainWidget.softwareCompetition.adminFee = contestJson.adminFee;

   var startDate =  parseDate(contestJson.startDate);  
   mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
   mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);
   
   if(isDevOrDesign()) {
     mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = contestJson.rootCategoryId;
     mainWidget.softwareCompetition.assetDTO.directjsCategories = contestJson.categoryIds;
   }
   
   if(isTechnologyContest()) {
     mainWidget.softwareCompetition.assetDTO.directjsTechnologies = contestJson.technologyIds;
   }
   
   var projectHeader = mainWidget.softwareCompetition.projectHeader;   
   projectHeader.tcDirectProjectId = contestJson.tcDirectProjectId;
   projectHeader.tcDirectProjectName = contestJson.tcDirectProjectName;
   projectHeader.setBillingProject(contestJson.billingProjectId);   
   projectHeader.setProjectName(contestJson.contestName);
   projectHeader.properties = contestJson.properties;
      
   projectHeader.projectSpec.detailedRequirements = contestJson.detailedRequirements;
   projectHeader.projectSpec.finalSubmissionGuidelines = contestJson.softwareGuidelines;
   
   //prizes: if custom level, initiate customCosts object so it is not derived from custom level any more   
   if(projectHeader.getCostLevel() == COST_LEVEL_CUSTOM) {
   	    customCosts = {};
        customCosts.firstPlaceCost = parseFloat(projectHeader.getFirstPlaceCost());
        customCosts.secondPlaceCost = parseFloat(projectHeader.getSecondPlaceCost());
        customCosts.reviewBoardCost = parseFloat(projectHeader.getReviewCost());
        customCosts.reliabilityBonusCost = parseFloat(projectHeader.getReliabilityBonusCost());
        customCosts.drCost = parseFloat(projectHeader.getDRPoints());   	   
   }
   
   
   //documentations, each doc has fields of documentId, fileName, description, documentTypId
   swDocuments = contestJson.documentation;
   // mark them as documentation
   $.each(swDocuments, function(i, doc) {
   	  doc['comp'] = true;
   });      
   setRollbackDocuments();

   //it ensures the data is here so we could show prize data
   softwareContestFees = contestJson.softwareContestFees;  
   //billing information
   initContestFeeForEdit(false, mainWidget.softwareCompetition.projectHeader.projectCategory.id, projectHeader.getBillingProject());      
   
	 if(isPrizeEditable(contestJson.billingProjectId)) {
	 	  $('.customRadio').show();
	 } else {
	 	  $('.customRadio').hide();
	 }	     
	 
	 //it show/hide the component specific part
   if(isDevOrDesign()) {
   	 $('.component').show();
   } else {
   	 $('.component').hide();
   }

   if(isTechnologyContest()) {
   	 $('.technology').show();
   } else {
     $('.technology').hide();
   }      
   
   //repost and new version
   var statusName = contestJson.projectStatus.name;    
   if(statusName.indexOf('Cancelled') != -1) {
    	 repostProjectId = contestJson.contestId;
	     repostTcProjectId = contestJson.tcDirectProjectId;
   	  
   	  $('#repostButton').show();
   	  $('#repostButton').click(function(){
   	      $('#repostDialog').dialog('open');	 
   	  });
   }

   if(statusName.indexOf('Completed') != -1) {
	     newVersionProjectId = contestJson.contestId;
	     newVersionTcProjectId = contestJson.tcDirectProjectId;
	     newVersionDevCreation = isDesign();
	     newVersionIsDesign = isDesign();
	     newVersionMinorVersion = false;
   	  
   	  $('#newVersionButton').show();
   	  $('#newVersionButton').click(function(){
   	      $('#newVersionDialog').dialog('open');	 
   	  });
   }
   
   //if any phase is open, no edit
   if(contestJson.phaseOpen) {
       $('img[alt="edit"]').parent().hide()   
   }
}



/**
 * Type Section Functions
 */
function populateTypeSection() {
	//edit
	$('#contestTypes').getSetSSValue("SOFTWARE"+mainWidget.softwareCompetition.projectHeader.projectCategory.id);
	$('#contestName').val(mainWidget.softwareCompetition.assetDTO.name);
	$('#chkboxCCA').attr('checked', mainWidget.softwareCompetition.projectHeader.isLccchecked());
	
	
	//display
	$('#rContestTypeName').html($("#contestTypes option[value=SOFTWARE"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
	$('#rContestName').html(mainWidget.softwareCompetition.assetDTO.name);
	$('#rCCA').html(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
}

function saveTypeSection() {
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
      },
      beforeSend: beforeAjax,
      complete: afterAjax            
   });	 
}

function validateFieldsTypeSection() {
   var categoryId = getContestType(true)[1];
   var contestName = $('input#contestName').val();


   //validation
   var errors = [];

   validateContestName(contestName, errors);

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
   
   var projectCategory = getProjectCategoryById(categoryId);
   if ($('input#chkboxCCA').attr('checked')) {
       mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
   } else {
       mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
   }
   mainWidget.softwareCompetition.projectHeader.projectCategory={};
   mainWidget.softwareCompetition.projectHeader.projectCategory.id = projectCategory.id;
   mainWidget.softwareCompetition.projectHeader.projectCategory.name = projectCategory.name;
   mainWidget.softwareCompetition.projectHeader.projectCategory.projectType={};
   mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = projectCategory.typeId;
   mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = projectCategory.typeName;
         
   mainWidget.softwareCompetition.assetDTO.name = contestName;         
   mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);
   
   return true;	
}

function showTypeSectionDisplay() {
	 $(".contest_type").css("display","block");
	 $(".contest_type_edit").css("display","none");												         	
}

function showTypeSectionEdit() {
	 $(".contest_type").css("display","none");
	 $(".contest_type_edit").css("display","block");
	 if(!$(".selectDesing select").data('customized')){
		$(".selectDesing select").data('customized',true);
      	$(".selectDesing select").sSelect();
		$('.selectDesing div.selectedTxt').html('Select Contest Type');
    }	
}


/**
 * Round/Schedule Section Functions
 */
function populateRoundSection() {
	var startDate = mainWidget.softwareCompetition.assetDTO.directjsProductionDate;
	
	//edit
	$('#startDate').datePicker().val(getDatePart(startDate)).trigger('change');
	$('#startTime').val(getTimePart(startDate));
		
	//display
	 var startDateString = formatDateForReview(startDate);
   $('#rStartDate').html(startDateString);
   $('table.projectStats td:eq(0)').html(startDateString);
   var endDateString = formatDateForReview(mainWidget.softwareCompetition.endDate);
   $('table.projectStats td:eq(1)').html(endDateString);
}

function saveRoundSection() {
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
      },
      beforeSend: beforeAjax,
      complete: afterAjax            
   });	    
}

function validateFieldsRoundSection() {
   var startDate = getDateByIdPrefix('start');
   

   //validation
   var errors = [];


   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
   mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);

   return true;
}

function showRoundSectionDisplay() {
	$(".contest_round").css("display","block");
	$(".contest_round_edit").css("display","none");
}

function showRoundSectionEdit() {
	$(".contest_round").css("display","none");
	$(".contest_round_edit").css("display","block");
	if(!$(".startEtSelect select").data('customized')){
		$(".startEtSelect select").data('customized',true);
      	$(".startEtSelect select").sSelect({ddMaxHeight: '220',yscroll: true});
    }
}

/**
 * Prize Section Functions
 */
function populatePrizeSection(initFlag) {
  //billing account
  var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject(); 
  $('#billingProjects').getSetSSValue(billingProjectId);
  //potential rollback
	$('#billingProjects').trigger("change");
	if(isBillingEditable()) {		
		$('#billingAccountDivEdit').show(); 	  
  } else {
  	$('#billingAccountDivEdit').hide(); 
  }

	if(isBillingViewable()) {
	   $('.billingdisplay').show(); 
     $('#rBillingAccount').html((billingProjectId <= 0)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());	
  } else {
  	 $('.billingdisplay').hide();   	   	 
  }

	//edit
	//display
	//set radio button
	var radioButtonValue = (COSTLEVEL_RADIOVALUE_MAP[mainWidget.softwareCompetition.projectHeader.getCostLevel()]) || "medium";	
	$('input[name="prizeRadio"][value="' + radioButtonValue + '"]').attr("checked","checked");
	
	fillPrizes();	
	
   
  if(initFlag) {
    //show activate button if it needs to : the fee is not paied up fully
    if(getCurrentContestTotal() > mainWidget.softwareCompetition.paidFee) {
    	  $('#resubmit').show(); 
    }         
  }	


  
}

function isBillingViewable() {
	// billing can not be found, meaning is not eligible, don't show/edit
	// or not set yet, allow edit
	var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();  
	return billingProjectId <=0 || $("#billingProjects option[value="+ billingProjectId +"]").length == 1;
}

function isBillingEditable() {
	 var notePaid = (mainWidget.softwareCompetition.paidFee == 0);
	 return isBillingViewable() && notePaid;
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
      },
      beforeSend: beforeAjax,
      complete: afterAjax            
   });	 
}

function validateFieldsPrizeSection() {
   //validation
   var errors = [];

	 var prizeType = $('input[name="prizeRadio"]:checked').val();
	 if(prizeType == 'custom') {
	   var value = $('#swFirstPlace').val();
	   if(!checkRequired(value) || !checkNumber(value)) {
	   	   errors.push('first place value is invalid.');
	   }
	 }
	 
	 if(getCurrentContestTotal() < mainWidget.softwareCompetition.paidFee) {
	 	  errors.push('Your payment can not be lower than paid amount.');
	 }
   
   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }
   
   if(isBillingEditable()) {		
   	 var billingProjectId = $('select#billingProjects').val();
   	mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
   }
   
   updateSoftwarePrizes();

   return true;	
}

function showPrizeSectionDisplay() {
	$(".contest_prize").css("display","block");
	$(".contest_prize_edit").css("display","none");
}

function showPrizeSectionEdit() {
	$(".contest_prize").css("display","none");
	$(".contest_prize_edit").css("display","block");
	if(!$(".prizeBillingSelect select").data('customized')){
		$(".prizeBillingSelect select").data('customized',true);
      	$(".prizeBillingSelect select").sSelect({ddMaxHeight: '220',yscroll: true});
    }
}


/**
 * Spec Section Functions
 */
/**
 * Populate spec section.
 *
 * @param initFlag initiation Flag when the section is first populated. It is for some special action only performed when
 * first time initialized. It could be ignored if it is not first time initialized.
 */ 
function populateSpecSection(initFlag) {	
   var detailedRequirements = mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements;
   var guidelines = mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines;
	
	//edit
	$('#swDetailedRequirements').val(detailedRequirements);
	$('#swGuidelines').val(guidelines);		
  if(isDevOrDesign()) {  	   
       if(mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId != $('#catalogSelect').val() || initFlag){ 
          $('#catalogSelect').val(mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId);
          updateCategories(fillCategories);
       } else {
       	  fillCategories();
       }       
  } 

  if(isTechnologyContest()) {
  	   //technlogies
  	   $('#masterTechnologiesSelect').val(mainWidget.softwareCompetition.assetDTO.directjsTechnologies);
       $('#masterTechnologiesSelect option:selected').appendTo('#masterTechnologiesChoosenSelect');
       sortTechnologySelects();                	
  }
		
	//display
  $('#rswDetailedRequirements').html(detailedRequirements);   
  $('#rswGuidelines').html(guidelines);   
  if(isDevOrDesign()) {
     $('#rswRootCatalog').html($("#catalogSelect option[value="+ mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId +"]").text());
     
     var html = "";
     $.each($('#select2_categories option'),function(i,option){
     	  html += option.text +"<br/>";
     });
     $('#rswCategories').html(html);     
  } 

  if(isTechnologyContest()) {
  	 var html = "";
     $.each($('#masterTechnologiesChoosenSelect option'),function(i,option){     	  
     	  html += option.text +"<br/>";
     });
     $('#rswTechnologies').html(html);     
  } 
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
      },
      beforeSend: beforeAjax,
      complete: afterAjax            
   });	 
}

function validateFieldsSpecSection() {
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
      
      if($('#select2_categories option').length == 0) {
      	   errors.push('No category is selected.');
      }
   }

  if(isTechnologyContest()) {
      if($('#masterTechnologiesChoosenSelect option').length == 0) {
      	   errors.push('No technology is selected.');
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
 * Document Section Functions
 */
// document array used for rollback for cancel button 
var rbDocuments; 

/**
 * Set rollback documents. It simply takes the snapshot of current swDocuments array.
 */
function setRollbackDocuments() {
	rbDocuments = swDocuments.slice(0);
}

/**
 * rolls back the document
 */
function rollbackDocuments() {
	swDocuments = rbDocuments.slice(0);
}

function populateDocumentSection() {
	//edit
  swRefreshDocuments();	
	
	//display
	var html = "";
	var template = $('#documentTemplate tbody').html();
	$.each(swDocuments, function(i,document) {
		 html += $.validator.format(template,(i+1),document['fileName'],document['description']);
	});
	$('#documentTable').html(html);
	
   if(hasRequirementDocument()) {
      $('.reqDocCheck').hide();
   }	
}

function showDocumentSectionDisplay() {
	 $(".contest_files").css("display","block");
	 $(".contest_files_edit").css("display","none");												
}

function showDocumentSectionEdit() {
	 $(".contest_files").css("display","none");
	 $(".contest_files_edit").css("display","block");												
}

function saveDocumentSection() {
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
         populateDocumentSection();  
         showDocumentSectionDisplay();         			
      },
      beforeSend: beforeAjax,
      complete: afterAjax            
   });	 
}


/**
 * Activation in edit page
 */
function activateContestEdit() {	
   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()
   
   if(billingProjectId <= 0) {
   	  showErrors("no billing project is selected.");
   	  return;
   }
   

   //construct request data
   var request = saveAsDraftRequest();
   request['activationFlag'] = true;

   $.ajax({
      type: 'POST',
      url:  ctx + "/launch/saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleActivationResultEdit,
      beforeSend: beforeAjax,
      complete: afterAjax            
   });   
}


function handleActivationResultEdit(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        mainWidget.softwareCompetition.paidFee = result.paidFee;
        if(getCurrentContestTotal() > mainWidget.softwareCompetition.paidFee) {
        	  $('#resubmit').show(); 
        } else {                 
            $('#resubmit').hide();
        }

        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        showMessage("Contest " + contestName +" has been activated successfully!");
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}
 
