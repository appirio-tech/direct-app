/**
 * Order Review Page
 */
/**
 * Rerender the order review page.
 */ 
function updateOrderReviewSoftware() {
   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()
   if(billingProjectId > 0) {
   	 $('#swOrderReview_activateButton').show();     
   } else {
   	 $('#swOrderReview_activateButton').hide();
   }   

   $('#sworDate').html(formatDateForReview(new Date()));   
   $('#sworContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#sworBillingAccount').html($("#billingProjects option[value="+ billingProjectId +"]").text());
   $('#sworStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
   
   $('#sworFirstPlaceCost').html(mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost().formatMoney(2));   
   $('#sworSecondPlaceCost').html(mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost().formatMoney(2));	 
   $('#sworAdminFee').html(mainWidget.softwareCompetition.projectHeader.getAdminFee().formatMoney(2));
   $('#sworTotal').html(getCurrentContestTotal().formatMoney(2));
}
 
function updateOrderReviewStudio() {
   var competition = mainWidget.competition;

   if(competition.contestData.isBillingSelected()) {
   	 $('#orderReview_activateButton').show();     
   } else {
   	 $('#orderReview_activateButton').hide();
   }   
   
   $('#orDate').html(formatDateForReview(new Date()));   
   $('#orContestName').html(mainWidget.competition.contestData.name);
   var isMultiRound = mainWidget.competition.contestData.multiRound;
   $('#orRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");

   var billingProjectId = mainWidget.competition.contestData.billingProject;
   $('#orBillingAccount').html((billingProjectId == -1)?"(not selected)":$("#billingProjects option[value="+ billingProjectId +"]").text());

   $('#orStartDate').html(formatDateForReview(mainWidget.competition.startDate));   
   
   //prizes
   var contestPrizesTotal = 0;
   var html = "";
   $.each(mainWidget.competition.contestData.prizes, function(i, prize) {
       var place = i+1;
       var amount = prize.amount;
       contestPrizesTotal += amount;
       html +=
       '<td>'+ place +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewPage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';       
   });
   html +=   '<td>' +'Studio Cup points : ' + calculateStudioCupPoints() + '</td>';
   html += '<td class="last">$'+ contestPrizesTotal.formatMoney(0) +'</td>';
   $('#orPrizesTR').html(html);
   
   //milestone prizes
   var  milestonePrizesTotal = 0;
   if(!isMultiRound) {
   	  $('#orMilestonePrizesDiv').hide();
   } else {
   	  $('#orMilestonePrizesDiv').show();
      
      var amount = mainWidget.milestonePrizeData.amount;   	  
      html = "";
   	  for(var i=1;i<=mainWidget.milestonePrizeData.numberOfSubmissions;i++) {
   	   milestonePrizesTotal += amount;	
       html +=
       '<td>'+ i +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewPage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';          	  	
   	  }
      html += '<td class="last">$'+ milestonePrizesTotal.formatMoney(0) +'</td>';
      $('#orMilestonePrizeTR').html(html);   	  
   }
   
   var adminFee = mainWidget.competition.contestData.contestAdministrationFee;
   $('#orAdminFee1').html('$'+adminFee.formatMoney(0));
   $('#orAdminFee2').html('$'+adminFee.formatMoney(0));
   
   var total = contestPrizesTotal + milestonePrizesTotal + adminFee;
   $('#orTotal').html('$' + total.formatMoney(0));
}

function calculateStudioCupPoints() {
    var isMultiRound = mainWidget.competition.contestData.multiRound;
    var milestoneAmount = mainWidget.milestonePrizeData.amount;
    var milestoneTotal = 0;

    if (isMultiRound) {

        for (var i = 1; i <= mainWidget.milestonePrizeData.numberOfSubmissions; i++) {
            milestoneTotal += milestoneAmount;
        }
    }


    var contestPrizeTotal = 0;

    $.each(mainWidget.competition.contestData.prizes, function(i, prize) {
        var amount = prize.amount;
        contestPrizeTotal += amount;
    });

    return (milestoneTotal + contestPrizeTotal) * 0.25;

}

function validateFieldsOrderReview() {
   return true;
}

function backOrderReview() {
   if(mainWidget.isSoftwareContest()) {
   	  showPage('reviewSoftwarePage');
   } else {
   	  showPage('reviewPage');
   }	   
}

function activateContest() {	
   if(mainWidget.isSoftwareContest()) {
   	  activateContestSoftware();
   } else {
   	  activateContestStudio();
   }	   	
}

function activateContestSoftware() {	
   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()
   if(billingProjectId < 0) {
   	  showErrors("no billing project is selected.");
   	  return;
   }   

   //construct request data
   var request = saveAsDraftRequest();
   request['activationFlag'] = true;

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleActivationResult,
      beforeSend: beforeAjax,
      complete: afterAjax            
   });   
}

function activateContestStudio() {	
   if(!validateFieldsOrderReview()) {
       return;
   }

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
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleActivationResult,
      beforeSend: beforeAjax,
      complete: afterAjax       
   });   
}


function handleActivationResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
       if(mainWidget.isSoftwareContest()) {
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
          mainWidget.softwareCompetition.projectHeader.id = result.projectId;
        }
         
         //show as receipt
         $('#swOrderReview_title').text('Receipt');
         $('#swReceiptAlert').show();
         $('#swOrderReview_buttonBox').hide();        
         $('#swOrderReview_buttonBox2').show();   
         //remove all edit icons
         $('#orderReviewSoftwarePage a.tipLink').hide();    
       } else {
         if(mainWidget.competition.contestData.contestId < 0 ) {
           mainWidget.competition.contestData.contestId = result.contestId;
         } 
         
         //show as receipt
         $('#orderReview_title').text('Receipt');
         $('#receiptAlert').show();
         $('#orderReview_buttonBox').hide();        
         $('#orderReview_buttonBox2').show();   
         //remove all edit icons
         $('#orderReviewPage a.tipLink').hide();    
       }	   	    	
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}


function saveAsDraftOrderReview() {
   if(!validateFieldsOrderReview()) {
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

function editContest() {
   if(mainWidget.isSoftwareContest()) {
	   var contestId = mainWidget.softwareCompetition.projectHeader.id
	   location.replace(ctx+'/contest/detail?projectId='+contestId);
   } else {
	   var contestId = mainWidget.competition.contestData.contestId;
	   location.replace(ctx+'/contest/detail?contestId='+contestId);
   }	   		
}
