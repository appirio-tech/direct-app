/**
 * Review Page.
 * 
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.2 TC Direct Replatforming Release 2 change note
 * - Display milestone prizes for software contest.
 * 
 * Version 1.3 TC Direct Replatforming Release 4 change note
 * - Add support to save the stock arts allowed flag for the studio contests.
 *
 * @author TCSASSEMBER
 * @version 1.3
 */
/**
 * Rerender the review page.
 */ 
function updateReviewSoftware() {
   $('#rswContestTypeName').html($("#contestTypes option[value=SOFTWARE"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
   $('#rswContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#rswProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
   $('#rswBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());
   
   $('#rswStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
   
   $('#rswDetailedRequirements').html(mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements);
   $('#rswSoftwareGuidelines').html(mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines);
   
   $('#rswFirstPlaceCost').html(mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost().formatMoney(2));
   $('#rswSecondPlaceCost').html(mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost().formatMoney(2));
   
   var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#rswRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");
   if (!isMultiRound) {
	   $('#rswMileStoneTR').hide();
	   $('#rswMPrizesDiv').hide();
	   $('.rswMultiInfo').hide();
   } else {
	   $('#rswMileStoneTR').show();
	   $('.rswMultiInfo').show();
	   $('#rswMilestoneDate').html(formatDateForReview(mainWidget.softwareCompetition.milestoneDate));
	   $('#rswMPrizesDiv').show();
	   var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
	   $('#rswMPrizesAmount').html(prizes[prizes.length - 1].prizeAmount);
       $('#rswMPrizesNumberOfSubmissions').html(prizes[prizes.length - 1].numberOfSubmissions);
   }
   
   // uploads
   html = "";
   $.each(swDocuments, function(i, doc) {
       html = html + 
			 "<dt>" + doc.fileName + "</dt>" +
			 "<dd>" + doc.description + "</dd>";
   });
   $('#swDocUploadList').html(html);   
}
 
function updateReviewStudio() {
   $('#rContestTypeName').html($("#contestTypes option[value=STUDIO"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
   $('#rContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#rProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
   $('#rBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());

   var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#rRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");

   if(!isMultiRound) {
       $('#rMileStoneTR').hide();
       $('#rMPrizesDiv').hide();
       
       $('.rMultiInfo').hide();
   } else {
       $('#rMileStoneTR').show();
       $('#rMilestoneDate').html(formatDateForReview(mainWidget.softwareCompetition.milestoneDate));

       var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
       $('#rMPrizesDiv').show();
       $('#rMPrizesAmount').html(prizes[prizes.length - 1].prizeAmount);
       $('#rMPrizesNumberOfSubmissions').html(prizes[prizes.length - 1].numberOfSubmissions);
       
       $('.rMultiInfo').show();
       $('#rRound1Info').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction);
       $('#rRound2Info').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction);
   }

   $('#rStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
   $('#rEndDate').html(formatDateForReview(mainWidget.softwareCompetition.subEndDate));

   $('#rContestIntroduction').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestIntroduction);
   $('#rContestDescription').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestDescription);
   
   //prizes
   var html = "";
   var placeMap = {1:"1st Place",2:"2nd Place", 3:"3rd Place", 4:"4th Place", 5:"5th Place"};
   $.each(mainWidget.softwareCompetition.projectHeader.prizes, function(i, prize) {
	   if (prize.prizeType.id == MILESTONE_PRIZE_TYPE_ID) {
		   return;
	   }
       var place = prize.place;
       var amount = prize.prizeAmount;
       html = html +
        '<label class="first">' + placeMap[place] + '</label>' +
        '<span class="dw">$</span>' +
        '<span class="numberDor">' + amount + '</span>';
   });
   $('#rPrizes').html(html);

   // file types
   var fileTypes = mainWidget.softwareCompetition.fileTypes;

   html = "<ul>";
   var studioSubtypeId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
   var types = getStudioFileTypes(studioSubtypeId);
   $.each(fileTypes, function(i, fileType) {
       var found = false;
       $.each(types, function(i, type) {
           if(type.value == fileType && !found) {
              html += '<li>' + type.description + '</li>';
              found = true;
           }
       });
       if(!found) {
           html += '<li>' + fileType + '</li>';
       }
   });
   html += "</ul>";
   $('#rFinalDeliveries').html(html);
   
   // uploads
   html = "";
   $.each(swDocuments, function(i, doc) {
       html = html + 
			 "<dt>" + doc.fileName + "</dt>" +
			 "<dd>" + doc.description + "</dd>";
   });
   $('#docUploadList').html(html);   

   
   // update the allowStockArt flag in the review page
  $('#rStockArts').html((mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'] == 'true') ? "Stock Arts allowed" : "Stock Arts NOT allowed");
  $('#rViewableSubmFlag').html((mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] == 'true') ? 'Submissions are viewable' : 'Submissions are not viewable');

  if (mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] == '') {
      $('#rMaxSubmissions').html("There are no limits on number of submissions");
  } else {
      $('#rMaxSubmissions').html("Maximum " + mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] + " submissions allowed in each round");
  }
}

function validateFieldsReview() {
   return true;
}

function backReview() {
   if(mainWidget.isSoftwareContest()) {
   	  showPage('overviewSoftwarePage');
   } else {
   	  showPage('overviewPage');
   }
}

function continueReview() {
   if(!validateFieldsReview()) {
       return;
   }
   if(mainWidget.isSoftwareContest()) {
   	  showPage('orderReviewSoftwarePage');
   } else {
   	  showPage('orderReviewPage');
   }


}



function saveAsDraftReview() {
   if(!validateFieldsReview()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   modalPreloader();

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult
   });
}
