/**
 * Review Page
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
   $('#rContestTypeName').html($("#contestTypes option[value=STUDIO"+ mainWidget.competition.contestData.contestTypeId +"]").text());
   $('#rContestName').html(mainWidget.competition.contestData.name);
   $('#rProjectName').html($("#projects option[value="+ mainWidget.competition.contestData.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.competition.contestData.billingProject;
   $('#rBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());

   var isMultiRound = mainWidget.competition.contestData.multiRound;
   $('#rRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");

   if(!isMultiRound) {
       $('#rMileStoneTR').hide();
       $('#rMPrizesDiv').hide();
       
       $('.rMultiInfo').hide();
   } else {
       $('#rMileStoneTR').show();
       $('#rMilestoneDate').html(formatDateForReview(mainWidget.competition.milestoneDate));

       $('#rMPrizesDiv').show();
       $('#rMPrizesAmount').html(mainWidget.milestonePrizeData.amount);
       $('#rMPrizesNumberOfSubmissions').html(mainWidget.milestonePrizeData.numberOfSubmissions);
       
       $('.rMultiInfo').show();
       $('#rRound1Info').html(mainWidget.competition.contestData.multiRoundData.roundOneIntroduction);
       $('#rRound2Info').html(mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction);
   }

   $('#rStartDate').html(formatDateForReview(mainWidget.competition.startDate));
   $('#rEndDate').html(formatDateForReview(mainWidget.competition.endDate));

   $('#rContestIntroduction').html(mainWidget.competition.contestData.shortSummary);
   $('#rContestDescription').html(mainWidget.competition.contestData.contestDescriptionAndRequirements);
   
   //prizes
   var html = "";
   var placeMap = {1:"1st Place",2:"2nd Place", 3:"3rd Place", 4:"4th Place", 5:"5th Place"};
   $.each(mainWidget.competition.contestData.prizes, function(i, prize) {
       var place = i+1;
       var amount = prize.amount;
       html = html +
        '<label class="first">' + placeMap[place] + '</label>' +
        '<span class="dw">$</span>' +
        '<span class="numberDor">' + amount + '</span>';
   });
   $('#rPrizes').html(html);

   // file types
   var fileTypes = mainWidget.competition.contestData.finalFileFormat.split(",");
   $.merge(fileTypes,mainWidget.competition.contestData.otherFileFormats.split(","));

   html = "";
   $.each(fileTypes, function(i, fileType) {
       html += '&nbsp; <label>' + fileType + '</label>';
   });
   $('#rFinalDeliveries').html(html);
   
   // uploads
   html = "";
   $.each(documents, function(i, doc) {
       html = html + 
			 "<dt>" + '<a href="'+ctx+'/launch/downloadDocument?documentId='+ doc.documentId +'" target="_blank">'+ doc.fileName + "</a></dt>" +
			 "<dd>" + doc.description + "</dd>";
   });
   $('#docUploadList').html(html);   

  // stock arts
  $('#rStockArts').html(mainWidget.competition.contestData.allowStockArt ? "Stock Arts allowed" : "Stock Arts not allowed");
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

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult
   });
}
