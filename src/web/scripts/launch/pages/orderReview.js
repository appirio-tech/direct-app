/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
/**
 * Rerender the order review page.
 * Version 1.1 change notes:
 * Provide a confirmation dialog when activating the created contest.
 * 
 * Version 1.2 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.3 TC Direct Replatforming Release 2 change notes:
 * - Display the checkpoint prizes for software contest.
 * - Display the specification review cost for studio contest.
 * - The studio contest can start specification review when the contest is activated.
 * 
 * Version 1.4 - Module Assembly - Contest Fee Based on % of Member Cost User Part
 * - Modify contest fee calculation. If the billing is configured by percentage of member cost,
 * - the contest fee will be calculated as a percentage of the member cost.
 * - Changed method: updateOrderReviewStudio.
 *
 * Version 1.5 - Release Assembly - TC Direct Cockpit Release Four updates:
 * - ask user to choose start spec review when activating the contest in launch contest
 * 
 * Version 1.6 - Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match
 * - add updateOrderReviewAlgorithm method to update order review page of algorithm contest
 * - update backOrderReview method to support algorithm contest
 *
 * @author pvmagacho, GreatKevin, bugbuka
 * @version 1.6
 */

/**
 * Update order review page of algorithm contest.
 */
function updateOrderReviewAlgorithm() {
   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()

   $('#alorDate').html(formatDateForReview(new Date()));   
   $('#alorContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#alorProject').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   $('#alorBillingAccount').html((billingProjectId == -1)?"(not selected)":$("#billingProjects option[value="+ billingProjectId +"]").text());

   $('#alorStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));  
   
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
       '<td>'+ place +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewAlgorithmPage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';       
   });
   html += '<td style="width:47%;"></td>';
   html += '<td class="last">$'+ contestPrizesTotal.formatMoney(0) +'</td>';
   $('#alorPrizesTR').html(html);

   var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
   $('#alorCopilotFee').html(copilotCost.formatMoney(2));

   var specificationReviewPayment = mainWidget.softwareCompetition.projectHeader.getSpecReviewCost();
   var reviewPayment = mainWidget.softwareCompetition.projectHeader.getReviewCost();
   var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
   // Modify admin Fee if contest percentage is to be used
   if (typeof billingFeesPercentage != 'undefined' && billingFeesPercentage[billingProjectId]!= null) {
       var contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;
       if (contestFeePercentage!=null) {
           var memberCost = contestPrizesTotal + copilotCost;
           if(typeof specificationReviewPayment != 'undefined') {
               memberCost += specificationReviewPayment;
           }
           if(typeof reviewPayment != 'undefined') {
               memberCost += reviewPayment;
           }
           mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = contestFeePercentage * memberCost;
           mainWidget.softwareCompetition.adminFee = contestFeePercentage * memberCost;
           mainWidget.softwareCompetition.projectHeader.setAdminFee(mainWidget.softwareCompetition.projectHeader.contestAdministrationFee);
           mainWidget.softwareCompetition.projectHeader.setContestFeePercentage(contestFeePercentage);
       }
   }

   var adminFee = mainWidget.softwareCompetition.adminFee;
   $('#alorAdminFee1').html('$'+adminFee.formatMoney(0));
   $('#alorAdminFee2').html('$'+(adminFee + copilotCost).formatMoney(0));
   
   var total = contestPrizesTotal + adminFee + copilotCost;
   $('#alorTotal').html('$' + total.formatMoney(0));
}


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
   
   //checkpoint prizes
   var checkpointPrizesTotal = 0;
   if(!mainWidget.softwareCompetition.multiRound) {
   	  $('#orswCheckpointPrizesDiv').hide();
   } else {
   	  $('#orswCheckpointPrizesDiv').show();
   	  var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
      var amount = prizes[prizes.length - 1].prizeAmount;   	  
      html = "";
   	  for(var i = 1; i <= prizes[prizes.length - 1].numberOfSubmissions; i++) {
   	     checkpointPrizesTotal += amount;	
         html +=
         '<td>'+ i +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewSoftwarePage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';
   	  }
      html += '<td class="last">$' + checkpointPrizesTotal.formatMoney(2) + '</td>';
      $('#orswCheckpointPrizeTR').html(html);   	  
   }
   
   var firstPrize = mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost();
   $('#sworFirstPlaceCost').html(firstPrize.formatMoney(2));
   var secondPrize = mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost();
   $('#sworSecondPlaceCost').html(secondPrize.formatMoney(2));
   var drPoints = mainWidget.softwareCompetition.projectHeader.getDRPoints();
   $('#sworDRPoints').html(drPoints.formatMoney(2));

   if(!$("#DRCheckbox").is(":checked")) {
       $('#sworDRPoints').parents("td").hide();
   } else {
       $('#sworDRPoints').parents("td").show()
   }
   var reliabilityBonusCost = mainWidget.softwareCompetition.projectHeader.getReliabilityBonusCost();
   $('#sworReliabilityBonusCost').html(reliabilityBonusCost.formatMoney(2));
   var contestPrizeCost = firstPrize + secondPrize + reliabilityBonusCost;

    if($("#DRCheckbox").is(":checked")) {
        contestPrizeCost += drPoints;
    }

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
   $('#sworTotal').html((contestPrizeCost + checkpointPrizesTotal + additionalFee).formatMoney(2));
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
   
   //checkpoint prizes
   var  checkpointPrizesTotal = 0;
   if(!isMultiRound) {
   	  $('#orCheckpointPrizesDiv').hide();
   } else {
   	  $('#orCheckpointPrizesDiv').show();
      
      var amount = prizes[prizes.length - 1].prizeAmount;   	  
      html = "";
   	  for(var i=1;i<=prizes[prizes.length - 1].numberOfSubmissions;i++) {
   	   checkpointPrizesTotal += amount;	
       html +=
       '<td>'+ i +' : $'+ amount.formatMoney(0) +'<a href="javascript: showPage(\'overviewPage\');" class="tipLink"><img src="/images/penicon.gif" alt="Edit" /></a></td>';          	  	
   	  }
      html += '<td class="last">$'+ checkpointPrizesTotal.formatMoney(0) +'</td>';
      $('#orCheckpointPrizeTR').html(html);   	  
   }
   
   var specificationReviewPayment = mainWidget.softwareCompetition.projectHeader.getSpecReviewCost();
   var reviewPayment = mainWidget.softwareCompetition.projectHeader.getReviewCost();
   var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
   $('#orSpecificationReviewPayment').html(specificationReviewPayment.formatMoney(2));
   $('#orReviewPayment').html(reviewPayment.formatMoney(2));
   $('#orCopilotFee').html(copilotCost.formatMoney(2));
   
   // Modify admin Fee if contest percentage is to be used
   if (typeof billingFeesPercentage != 'undefined' && billingFeesPercentage[billingProjectId]!= null) {
       var contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;
       if (contestFeePercentage!=null) {
           var memberCost = contestPrizesTotal + checkpointPrizesTotal + specificationReviewPayment + reviewPayment + copilotCost; /* + calculateStudioCupPoints() ; left to FF. */
           mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = contestFeePercentage * memberCost;
           mainWidget.softwareCompetition.adminFee = contestFeePercentage * memberCost;
           mainWidget.softwareCompetition.projectHeader.setAdminFee(mainWidget.softwareCompetition.projectHeader.contestAdministrationFee);
           mainWidget.softwareCompetition.projectHeader.setContestFeePercentage(contestFeePercentage);
       }
   }
   
   var adminFee = mainWidget.softwareCompetition.projectHeader.contestAdministrationFee;
   $('#orAdminFee1').html('$'+adminFee.formatMoney(0));
   $('#orAdminFee2').html('$'+(adminFee + specificationReviewPayment + copilotCost + reviewPayment).formatMoney(0));
   
   var total = contestPrizesTotal + checkpointPrizesTotal + adminFee + specificationReviewPayment + copilotCost;
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
    var checkpointAmount = prizes[prizes.length - 1].prizeAmount;
    var checkpointTotal = 0;

    if (isMultiRound) {

        for (var i = 1; i <= prizes[prizes.length - 1].numberOfSubmissions; i++) {
            checkpointTotal += checkpointAmount;
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

    return (checkpointTotal + contestPrizeTotal) * 0.25;

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
   } else if (mainWidget.isStudioContest()){
   	  showPage('reviewPage');
   } else {
      showPage('reviewAlgorithmPage');
   }	   
}

/**
 * Activate contest.
 */
function activateContest() {

    showConfirmation("Do you really want to activate the contest ?",
        "This will create the new contest <span class='messageContestName'>" + mainWidget.softwareCompetition.projectHeader.getProjectName()
            + "</span> for you and then activate it. " +
            "Please confirm you want to create the contest and activate it. After activation, "
            + "you will start the contest specification review.",
        "YES",
        function() {
            closeModal();
            showActivateSpecReviewModal();
        }
    );

}

/**
 * Show the activate and spec review modal.
 * @since 1.5
 */
function showActivateSpecReviewModal() {
    // show spec review popup
    $('#TB_overlay').show();
    $('#TB_window_custom').show();
    $('.TB_overlayBG').css('height', document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
    $('#TB_window_custom').css({
        //'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window_custom").width() / 2)) + 'px'
        'left':$(window).width() / 2 - $('#TB_window_custom').width() / 2,
        'top':($(window).height() / 2 - $('#TB_window_custom').height() / 2) + $(window).scrollTop()
    });

    // set button click
    $('#TB_window_custom .review-now').attr("href", "javascript:activateContestSoftware('now')");
    $('#TB_window_custom .review-later').attr("href", "javascript:activateContestSoftware('later')");
}

function hideActivateSpecReviewModal() {
    $('#TB_overlay').hide();
    $('#TB_window_custom').hide();
}

/**
 * Activate software contest.
 *
 * Update in version 1.5:
 * - Start spec review when activate the contest
 */
function activateContestSoftware(mode) {
    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()
    if (billingProjectId < 0) {
        showErrors("no billing project is selected.");
        return;
    }

    //construct request data
    var request = saveAsDraftRequest();
    request['activationFlag'] = true;
    request['specReviewStartMode'] = mode;

    $.ajax({
        type:'POST',
        url:"saveDraftContest",
        data:request,
        cache:false,
        dataType:'json',
        success:handleActivationResult,
        beforeSend: function() {
            hideActivateSpecReviewModal();
            modalPreloader();
        },
        complete:afterAjax
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
       hideActivateSpecReviewModal();
       // var startSpecReviewUrl = "../contest/startSpecReview.action?projectId=";
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

//       // show go to my spec review pop div
//       if (result.hasSpecReview && !result.isSpecReviewStarted) {
//    	   if(mainWidget.isSoftwareContest()) {
//    		   $('#swOrderReview_bottom_review').show();
//    	   } else {
//    		   $('#orderReview_bottom_review').show();
//    	   }
//       }
//       startSpecReviewUrl += result.projectId + "&";
//        $('#TB_window_custom .review-now').attr("href", startSpecReviewUrl + "startMode=now");
//        $('#TB_window_custom .review-later').attr("href", startSpecReviewUrl + "startMode=later");
    },
    function(errorMessage) {
        showServerError(errorMessage);
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
