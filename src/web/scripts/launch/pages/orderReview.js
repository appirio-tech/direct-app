/**
 * Order Review Page
 */
/**
 * Rerender the order review page.
 */ 
function updateOrderReview() {
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
   showPage('reviewPage');
}

function activateContest() {	
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
      success: handleActivationResult
   });   
}


function handleActivationResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.competition.contestData.name;
        if(mainWidget.competition.contestData.contestId < 0 ) {
          mainWidget.competition.contestData.contestId = result.contestId;
          //there is already some information on the page. might not need it
          //showMessage("Contest " + contestName +" has been activated successfully!");
        } else {
        	//there is already some information on the page. might not need it
          //showMessage("Contest " + contestName +" has been activated successfully!");
        }
        
        //show as receipt
        $('#orderReview_title').text('Receipt');
        $('#receiptAlert').show();
        $('#orderReview_buttonBox').hide();        
        $('#orderReview_buttonBox2').show();   
        //remove all edit icons
        $('#orderReviewPage a.tipLink').hide();    
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
      success: handleSaveAsDraftContestResult
   });
}

function editContest() {
	 var contestId = mainWidget.competition.contestData.contestId;
	 location.replace(ctx+'/contest/detail?contestId='+contestId);
}
