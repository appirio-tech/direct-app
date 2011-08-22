/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Rerender the order review page.
 * Version 1.1 change notes:
 * Provide a confirmation dialog when activating the created contest.
 * 
 * Version 1.2 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Versino 1.3 TC Direct Replatforming Release 2 change notes:
 * - Display the milestone prizes for software contest.
 * - Display the specification review cost for studio contest.
 * - The studio contest can start specification review when the contest is activated.
 * 
 * @author TCSASSEMBLER
 * @version 1.3
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
   
   //milestone prizes
   var milestonePrizesTotal = 0;
   if(!mainWidget.softwareCompetition.multiRound) {
   	  $('#orswMilestonePrizesDiv').hide();
   } else {
   	  $('#orswMilestonePrizesDiv').show();
   	  var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
      var amount = prizes[prizes.length - 1].prizeAmount;   	  
      html = "";
   	  for(var i = 1; i <= prizes[prizes.length - 1].numberOfSubmissions; i++) {
   	     milestonePrizesTotal += amount;	
         html +=
         '<td>'+ i +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewSoftwarePage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';
   	  }
      html += '<td class="last">$' + milestonePrizesTotal.formatMoney(2) + '</td>';
      $('#orswMilestonePrizeTR').html(html);   	  
   }
   
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
   $('#sworTotal').html((contestPrizeCost + milestonePrizesTotal + additionalFee).formatMoney(2));
}

/**
 * Update order review page of studio contest.
 */
function updateOrderReviewStudio() {
	var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();

   if(billingProjectId > 0) {
   	 $('#orderReview_activateButton').show();     
   } else {
   	 $('#orderReview_activateButton').hide();
   }   
   
   $('#orDate').html(formatDateForReview(new Date()));   
   $('#orContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#orRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");

   $('#orBillingAccount').html((billingProjectId == -1)?"(not selected)":$("#billingProjects option[value="+ billingProjectId +"]").text());

   $('#orStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));   
   
   //prizes
   var contestPrizesTotal = 0;
   var html = "";
   var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
   $.each(prizes, function(i, prize) {
	   if (prize.prizeType.id != CONTEST_PRIZE_TYPE_ID) {
		   return;
	   }
       var place = prize.place;
       var amount = prize.prizeAmount;
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
      
      var amount = prizes[prizes.length - 1].prizeAmount;   	  
      html = "";
   	  for(var i=1;i<=prizes[prizes.length - 1].numberOfSubmissions;i++) {
   	   milestonePrizesTotal += amount;	
       html +=
       '<td>'+ i +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewPage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';          	  	
   	  }
      html += '<td class="last">$'+ milestonePrizesTotal.formatMoney(0) +'</td>';
      $('#orMilestonePrizeTR').html(html);   	  
   }
   
   var specificationReviewPayment = mainWidget.softwareCompetition.projectHeader.getSpecReviewCost();
   var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
   $('#orSpecificationReviewPayment').html(specificationReviewPayment.formatMoney(2));
   $('#orCopilotFee').html(copilotCost.formatMoney(2));
   var adminFee = mainWidget.softwareCompetition.projectHeader.contestAdministrationFee;
   $('#orAdminFee1').html('$'+adminFee.formatMoney(0));
   $('#orAdminFee2').html('$'+(adminFee + specificationReviewPayment + copilotCost).formatMoney(0));
   
   var total = contestPrizesTotal + milestonePrizesTotal + adminFee + specificationReviewPayment + copilotCost;
   $('#orTotal').html('$' + total.formatMoney(0));
}

/**
 * Calculate studio cup points.
 * 
 * @return points
 */
function calculateStudioCupPoints() {
    var isMultiRound = mainWidget.softwareCompetition.multiRound;
    var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
    var milestoneAmount = prizes[prizes.length - 1].prizeAmount;
    var milestoneTotal = 0;

    if (isMultiRound) {

        for (var i = 1; i <= prizes[prizes.length - 1].numberOfSubmissions; i++) {
            milestoneTotal += milestoneAmount;
        }
    }

    var contestPrizeTotal = 0;

    $.each(prizes, function(i, prize) {
    	if (prize.prizeType.id != CONTEST_PRIZE_TYPE_ID) {
    		return;
    	}
        var amount = prize.prizeAmount;
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
			height:240,
			width: 520,
			modal: true,
			buttons: {
				"No": function() {
					$( this ).dialog( "close" );
				},
				"Yes": function() {
					$( this ).dialog("close");
					activateContestSoftware();
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
 * Handle contest activation result.
 * 
 * @param jsonResult the json result
 */
function handleActivationResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
       var startSpecReviewUrl = "../contest/startSpecReview.action?projectId=";
       mainWidget.softwareCompetition.projectHeader.id = result.projectId;
       if(mainWidget.isSoftwareContest()) {
         //show as receipt
         $('#swOrderReview_title').text('Receipt');
         $('#swReceiptAlert').show();
         $('#swOrderReview_buttonBox').hide();        
         $('#swOrderReview_buttonBox2').show();   
         //remove all edit icons
         $('#orderReviewSoftwarePage a.tipLink').hide();
       } else {
         //show as receipt
         $('#orderReview_title').text('Receipt');
         $('#receiptAlert').show();
         $('#orderReview_buttonBox').hide();        
         $('#orderReview_buttonBox2').show();   
         //remove all edit icons
         $('#orderReviewPage a.tipLink').hide();
       }
       // show go to my spec review pop div
       if (result.hasSpecReview && !result.isSpecReviewStarted) {
    	   if(mainWidget.isSoftwareContest()) {
    		   $('#swOrderReview_bottom_review').show();
    	   } else {
    		   $('#orderReview_bottom_review').show();
    	   }
       }
       startSpecReviewUrl += result.projectId + "&";
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
	var contestId = mainWidget.softwareCompetition.projectHeader.id
	location.replace(ctx+'/contest/detail?projectId='+contestId);	   		
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
