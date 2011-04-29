/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Rerender the order review page.
 * Version 1.1 change notes:
 * Provide a confirmation dialog when activating the created contest.
 * 
 * @author TCSASSEMBLER
 * @version 1.1(Direct Improvements Assembly Release 2)
 * @since 1.0
 */
/**
 * Update order review page of software contest.
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
   $('#sworProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());
   $('#sworBillingAccount').html($("#billingProjects option[value="+ billingProjectId +"]").text());
   $('#sworStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
   
   var firstPrize = mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost();
   $('#sworFirstPlaceCost').html(firstPrize.formatMoney(2));
   var secondPrize = mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost();
   $('#sworSecondPlaceCost').html(secondPrize.formatMoney(2));
   var drPoints = mainWidget.softwareCompetition.projectHeader.getDRPoints();
   $('#sworDRPoints').html(drPoints.formatMoney(2));
   var reliabilityBonusCost = mainWidget.softwareCompetition.projectHeader.getReliabilityBonusCost();
   $('#sworReliabilityBonusCost').html(reliabilityBonusCost.formatMoney(2));
   var contestPrizeCost = firstPrize + secondPrize + drPoints + reliabilityBonusCost;
   $('#sworContestPrizeCost').html(contestPrizeCost.formatMoney(2));

   var specificationReviewPayment = mainWidget.softwareCompetition.projectHeader.getSpecReviewCost();
   $('#sworSpecificationReviewPayment').html(specificationReviewPayment.formatMoney(2));
   var reviewPayment = mainWidget.softwareCompetition.projectHeader.getReviewCost();
   $('#sworReviewPayment').html(reviewPayment.formatMoney(2));
   var contestFee = mainWidget.softwareCompetition.projectHeader.getAdminFee();
   $('#sworAdminFee').html(contestFee.formatMoney(2));
   $('#sworCopilotFee').html(parseFloat(mainWidget.softwareCompetition.copilotCost).formatMoney(2));
   var additionalFee = specificationReviewPayment + reviewPayment + contestFee + parseFloat(mainWidget.softwareCompetition.copilotCost);
   $('#sworAdditionalCosts').html(additionalFee.formatMoney(2));
   $('#sworTotal').html((contestPrizeCost + additionalFee).formatMoney(2));
}

/**
 * Update order review page of studio contest.
 */
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

/**
 * Calculate studio cup points.
 * 
 * @return points
 */
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

/**
 * Validate fields in order review page.
 * 
 * @return true always
 */
function validateFieldsOrderReview() {
   return true;
}

/**
 * Show review page.
 */
function backOrderReview() {
   if(mainWidget.isSoftwareContest()) {
   	  showPage('reviewSoftwarePage');
   } else {
   	  showPage('reviewPage');
   }	   
}

/**
 * Activate contest.
 */
function activateContest() {	
   $( "#activateContestConfirmation" ).dialog({
			autoOpen: true,
			resizable: true,
			height:200,
			width: 500,
			modal: true,
			buttons: {
				"No": function() {
					$( this ).dialog( "close" );
				},
				"Yes": function() {
					$( this ).dialog("close");
					if(mainWidget.isSoftwareContest()) {
						activateContestSoftware();
					} else {
						activateContestStudio();
					}
				}
			}
	});
	// confirm that user want to activate the contest
	// if it's confirmed, a new contest will be saved
	if(!$( "#activateContestConfirmation" ).dialog( "isOpen" )) {
		$( "#activateContestConfirmation" ).dialog( "open" );
	}   	
}

/**
 * Activate software contest.
 */
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

/**
 * Activate studio contest.
 */
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
   competition.contestData.detailedStatusId=CONTEST_DETAILED_STATUS_SCHEDULED;

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

/**
 * Handle contest activation result.
 * 
 * @param jsonResult the json result
 */
function handleActivationResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
       var startSpecReviewUrl = "../contest/startSpecReview.action?contestId=";
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
         // show go to my spec review pop div
         if (result.hasSpecReview && !result.isSpecReviewStarted) {
             $('#swOrderReview_bottom_review').show();
         }
         startSpecReviewUrl += result.projectId + "&";
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
        $('#TB_window_custom .review-now').attr("href", startSpecReviewUrl + "startMode=now");
        $('#TB_window_custom .review-later').attr("href", startSpecReviewUrl + "startMode=later");
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}

/**
 * Save as draft order review.
 */
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

/**
 * Edit contest.
 */
function editContest() {
   if(mainWidget.isSoftwareContest()) {
	   var contestId = mainWidget.softwareCompetition.projectHeader.id
	   location.replace(ctx+'/contest/detail?projectId='+contestId);
   } else {
	   var contestId = mainWidget.competition.contestData.contestId;
	   location.replace(ctx+'/contest/detail?contestId='+contestId);
   }	   		
}

$(function() {
    /**
     * Show spec review pop window.
     */
    $('.specrev-goto').click(function(){
        $('#TB_overlay').show();
        $('#TB_window_custom').show();
        $('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
        $('#TB_window_custom').css({
            //'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window_custom").width() / 2)) + 'px'
            'left' : $(window).width() / 2-$('#TB_window_custom').width() / 2,
            'top' : ($(window).height() / 2-$('#TB_window_custom').height() / 2) + $(window).scrollTop()
        });
    });
    //$('#TB_window_custom').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window_custom").css('height')) / 2))});
});
