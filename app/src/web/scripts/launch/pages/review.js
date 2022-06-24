/**
 * Review Page.
 *
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.2 TC Direct Replatforming Release 2 change note
 * - Display checkpoint prizes for software contest.
 *
 * Version 1.3 TC Direct Replatforming Release 4 change note
 * - Add support to save the stock arts allowed flag for the studio contests.
 *
 * Version 1.4 Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match
 * - Add method updateReviewAlgorithm.
 * - Update method backReview to support algorithm contest.
 * - Update method continueReview to support algorithm contest.
 *
 * Version 1.5 (Module Assembly - TC Cockpit Launch Code Contest)
 * - Add multiple prize support for Code contest type
 *
 * Version 1.6 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * - Handles new Design First2Finish contest
 *
 * Version 1.7 (TopCoder Direct - Draft Challenge Creation/Saving Prompt)
 * - Add the save challenge confirmation
 *
 * Version 1.8 (Topcoder - Support Points Prize Type For Challenges)
 * - Add support for points prize type
 *
 * Version 1.9 (Topcoder - Add effort hours field)
 * - Add enable effort hours
 *
 * @author bugbuka, GreatKevin
 * @version 1.9
 */
/**
 * Rerender the review page.
 */
function updateReviewAlgorithm() {
   $('#ralContestTypeName').html($("#contestTypes option[value=ALGORITHM"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
   $('#ralContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#ralProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
   $('#ralBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());
   $('#ralEffortDaysEstimateOffshore').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE]);
   $('#ralEffortDaysEstimateOnsite').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE]);
   $('#ralStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));

   // to do
   $('#ralEndDate').html(formatDateForReview(mainWidget.softwareCompetition.subEndDate));

   $('#ralProblemStatement').html(mainWidget.softwareCompetition.projectHeader.projectMMSpecification.problemName);
   $('#ralMatchDetails').html(mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchDetails);
   $('#ralMatchRules').html(mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchRules);

   //prizes
   var html = "";
   var placeMap = {1:"1st Place",2:"2nd Place", 3:"3rd Place", 4:"4th Place", 5:"5th Place"};
   $.each(mainWidget.softwareCompetition.projectHeader.prizes, function(i, prize) {
       if (prize.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
           return;
       }
       var place = prize.place;
       var amount = prize.prizeAmount;
       html = html +
        '<label class="first">' + placeMap[place] + '</label>' +
        '<span class="dw">$</span>' +
        '<span class="numberDor">' + amount + '</span>';
   });
   $('#ralPrizes').html(html);

   // points
   $('#ralPoints').html(getPointsHTML(placeMap));

   // uploads
   html = "";
   $.each(swDocuments, function(i, doc) {
       html = html +
             "<dt>" + doc.fileName + "</dt>" +
             "<dd>" + doc.description + "</dd>";
   });
   $('#alDocUploadList').html(html);
}

function updateReviewSoftware() {
   $('#rswContestTypeName').html($("#contestTypes option[value=SOFTWARE"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
   $('#rswContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#rswProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
   $('#rswBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());

   $('#rswEffortDaysEstimateOffshore').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE]);
   $('#rswEffortDaysEstimateOnsite').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE]);

   $('#rswStartDate').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));

   $('#rswDetailedRequirements').html(mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements);
   $('#rswSoftwareGuidelines').html(mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines);

    // prize for Code contest type
    var prizeHTML = "";
    var placeMap = {1: "1st Place", 2: "2nd Place", 3: "3rd Place", 4: "4th Place", 5: "5th Place"};
    $.each(mainWidget.softwareCompetition.projectHeader.prizes, function (i, prize) {
        if (prize.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID || prize.prizeAmount <= 0) {
            return;
        }
        var place = prize.place;
        var amount = prize.prizeAmount;
        prizeHTML = prizeHTML +
            '<label class="first">' + placeMap[place] + '</label>' +
            '<span class="dw">$</span>' +
            '<span class="numberDor">' + amount + '</span>';
    });

    $('#rswPrizes').html(prizeHTML);

    var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#rswRoundType').html((!isMultiRound)?"Challenge will be run in single-round":"Challenge will be run in multi-rounds");
   if (!isMultiRound) {
	   $('#rswCheckpointTR').hide();
	   $('#rswMPrizesDiv').hide();
	   $('.rswMultiInfo').hide();
   } else {
	   $('#rswCheckpointTR').show();
	   $('.rswMultiInfo').show();
	   $('#rswCheckpointDate').html(formatDateForReview(mainWidget.softwareCompetition.checkpointDate));
	   $('#rswMPrizesDiv').show();
	   var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
	   $('#rswMPrizesAmount').html(prizes[prizes.length - 1].prizeAmount);
       $('#rswMPrizesNumberOfSubmissions').html(prizes[prizes.length - 1].numberOfSubmissions);
   }

   // points
   $('#rswPoints').html(getPointsHTML(placeMap));

   // uploads
   var uploadsHTML = "";
   $.each(swDocuments, function(i, doc) {
       uploadsHTML = uploadsHTML +
			 "<dt>" + doc.fileName + "</dt>" +
			 "<dd>" + doc.description + "</dd>";
   });
   $('#swDocUploadList').html(uploadsHTML);
}

function updateReviewStudio() {
   $('#rContestTypeName').html($("#contestTypes option[value=STUDIO"+ mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
   $('#rContestName').html(mainWidget.softwareCompetition.assetDTO.name);
   $('#rProjectName').html($("#projects option[value="+ mainWidget.softwareCompetition.projectHeader.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
   $('#rBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());

   var isMultiRound = mainWidget.softwareCompetition.multiRound;
   $('#rRoundType').html((!isMultiRound)?"Challenge will be run in single-round":"Challenge will be run in multi-rounds");

   if(!isMultiRound) {
       $('#rCheckpointTR').hide();
       $('#rMPrizesDiv').hide();

       $('.rMultiInfo').hide();
   } else {
       $('#rCheckpointTR').show();
       $('#rCheckpointDate').html(formatDateForReview(mainWidget.softwareCompetition.checkpointDate));

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
	   if (prize.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
		   return;
	   }
       var place = prize.place;
       var amount = prize.prizeAmount;

       if(amount <= 0) return;

       html = html +
        '<label class="first">' + placeMap[place] + '</label>' +
        '<span class="dw">$</span>' +
        '<span class="numberDor">' + amount + '</span>';
   });

   $('#rPrizes').html(html);

   // points
   $('#rPoints').html(getPointsHTML(placeMap));

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

    if (studioSubtypeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
        $("#reviewPage .maxSubmissions").hide();
    } else {
        $("#reviewPage .maxSubmissions").show();
    }
    $('#rEffortDaysEstimateOffshore').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE]);
    $('#rEffortDaysEstimateOnsite').html(mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE]);
}

/**
 * Get HTML of points row.
 * @param placeMap map from place number to string description
 * @requires HTML of points row
 */
function getPointsHTML(placeMap) {
    var pointHTML = '';
    $.each(mainWidget.softwareCompetition.projectHeader.points, function (i, point) {
        if (point.prizeType.id !== CHALLENGE_POINT_TYPE_ID || point.prizeAmount <= 0) {
            return;
        }
        var place = point.place;
        var amount = point.prizeAmount;
        pointHTML = pointHTML +
            '<label class="first">' + placeMap[place] + '</label>' +
            '<span class="dw">Pt.</span>' +
            '<span class="numberDor">' + amount + '</span>';
    });
    return pointHTML;
}

function validateFieldsReview() {
   return true;
}

function backReview() {
   if(mainWidget.isSoftwareContest()) {
	showPage('overviewSoftwarePage');
   } else if(mainWidget.isStudioContest()) {
	showPage('overviewPage');
   } else {
      showPage('overviewAlgorithmPage');
   }
}

function continueReview() {
   if(!validateFieldsReview()) {
       return;
   }
   if(mainWidget.isSoftwareContest()) {
      showPage('orderReviewSoftwarePage');
   } else if(mainWidget.isStudioContest()){
      showPage('orderReviewPage');
   } else {
      showPage('orderReviewAlgorithmPage');
   }


}



function saveAsDraftReview() {
    if (!validateFieldsReview()) {
        return;
    }

    var saveDraftHandler = function () {
        //construct request data
        var request = saveAsDraftRequest();

        modalPreloader();

        $.ajax({
            type: 'POST',
            url: "saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: handleSaveAsDraftContestResult
        });
    };

    if (showSaveChallengeConfirmation == false) {
        saveDraftHandler();
    } else {
        showChallengeSaveConfiguration(function () {
            closeModal();
            saveDraftHandler();
        });
    }
}
