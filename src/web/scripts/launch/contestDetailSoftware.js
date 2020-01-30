/**
 * Copyright (C) 2010 - 2018 TopCoder Inc., All Rights Reserved.
 *
 * Contest Detail Javascript
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - Add repost and new version function on details page.
 *
 * Version 1.2 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.3 TC Direct Replatforming Release 2 change note
 * - add support to support checkpoint prizes for software contest.
 * - add support to switch the number of rounds when the contest is draft.
 *
 * Version 1.1.2 (TC Direct - Software Creation Update Assembly) Change notes:
 * - Update method populateTypeSection to populate copilots data.
 *
 * Version 1.1.3 (TCCC-2926)
 * -  Add support to restrict the max characters of private description and public description.
 *
 * Version 1.1.4 (TC Direct "Contest Links and Button" Update 24Hr Assembly)
 * - Add support to show the 'Preview Contest' link if the contest is in draft status.
 *
 * Version 1.4 TC Direct Replatforming Release 4 change note
 * - add support to save DR points for studio contest.
 *
 * Version 1.1.5 (Release Assembly - Direct Improvements Assembly Release 3)
 * - Fixes the contest fee bug in contest detail screen, load the actual contest fee from contest data rather than from config.
 *
 * Version 1.4.1 TC Direct Release 6 change note
 * - Updated retrieveContestCostWithoutAdminFee method to check for non-existing feeObject
 *
 *  Version 1.4.2 Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp changes:
 * - Updated software contest details page to use new cockpit tinyMCE editor
 *
 * Version 1.4.3 (Release Assembly - TC Cockpit Contest Edit and Upload Update) Change notes:
 * - Fixed bug TCCC-3724. Updated logic for editing the contests.
 *
 * Version 1.4.4 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp)
 * - Updated updateContestCostData method to check Studio type.
 *
 * Version 1.5 (Module Assembly - Contest Fee Based on % of Member Cost User Part)
 * - Modify contest fee calculation. If the billing is configured by percentage of member cost,
 * - the contest fee will be calculated as a percentage of the member cost.
 * - Changed methods: updateContestCostData, savePrizeSection, saveTypeSection.
 *
 * Version 1.6 (Release Assembly - TC Direct Cockpit Release Four) updates:
 * - Show start spec review when user activates the contest
 *
 * Version 1.7 (Release Assembly - TopCoder Studio CCA Integration) change notes:
 * - Add place holder support for tinyMCE editors for sutdio contest description, round1 info
 * - and round2 info fields.
 *
 * Version 1.8 (Release Assembly - TC Direct Cockpit Release Five) change notes:
 * - Fix the DR points, checkpoint prizes, contest fee percentage calculation etc.
 *
 * Version 1.9 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 * - Always use the value stored in project info (49) as the copilot cost of the contest
 *
 * Version 2.0 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 *
 * Version 2.1 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 * 1) Add supports for marathon contest details page - initialize the marathon contest details specification
 *
 * Version 2.2 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
 * - Add billing account CCA specific
 *
 * Version 2.3 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * - Add support for milestone association with contest in contest detail page.
 *
 * Version 2.4 (Module Assembly - TC Cockpit Launch Code contest)
 * - Add support for Code contest type, it supports multiple prizes.
 *
 * Version 2.5 (Module Assembly - TC Cockpit Launch F2F contest)
 * - Add support for F2F contest
 *
 * Version 2.6 (Release Assembly - TC Cockpit Private Challenge Update)
 * - Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
 *
 * Version 2.7 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
 * - Add support for setting checkpoint end date and submission end date for software challenge
 *
 * Version 2.8 (F2F - TC Cockpit Update Bug Hunt type)
 * - Only display 1st place cost for Bug hunt (like First2Finish), but display spec review cost for bug hunt.
 *
 * Version 2.9 (F2F - TC Cockpit Update Auto Assign Reviewer Flow)
 * - Add review type radios to choose 'community' or 'internal' review
 *
 * Version 3.0 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * - Handles new Design First2Finish contest
 *
 * Version 3.1 ( Release Assembly - TC Direct Edit Challenge - prize section update v1.0)
 * - Add support for updates in edit contest JSPs (checkpoint prize and studio costs related changes).
 *
 * Version 3.2 (Release Assembly - TC Direct Prize Section Update)
 * - Update prize section to support on the fly cost calculation for design challenge
 * - Add checkpoint prize for dev challenge prize section and update on the fly cost calculation
 *
 * Versin 3.3 (Topcoder Direct - add total cost and estimate note to Marathon Match challenge)
 * -  Add total cost for marathon match
 *
 * Version 3.4 (Topcoder Direct - Allow a user to link a marathon match round id to direct mm challenge)
 * @author Veve @channegeId 30046969
 * - Add the marathon round id project info save for marathon challenge
 *
 * Version 3.5 (TopCoder Direct - Design Challenge Track Studio Cup Point Flag)
 * - Add studio cup points checkbox
 *
 * Version 3.6 (TopCoder Direct - EST/EDT switch in date picker)
 * - Setup event handler for auto changing date time timezone
 *
 * Version 3.7 (TopCoder Direct - Draft Challenge Creation/Saving Prompt)
 * - Add the save challenge confirmation
 *
 * Version 3.8 (Provide Way To Pre_register members When Launching Challenge)
 * - Add pre-register member
 *
 * Version 3.9 (TOPCODER DIRECT - CLOSE PRIVATE CHALLENGE IMMEDIATELY)
 * - Add support for closing and canceling private challenge
 *
 * Version 3.10 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * - Add support for challenge group(view and editing)
 *
 * Version 4.0 (Topcoder - Ability To Set End Date For Registration Phase and Submission Phase)
 * - Added registration and submission end date/time for design F2F
 * - Added registration length for studio contests (excluding design F2F)
 * - All software contests support modification of registration and submission end date/time
 *
 * Version 4.1 (TOPCODER - SUPPORT CUSTOM COPILOT FEE FOR CHALLENGE IN DIRECT APP):
 * - Add support for custom copilot fee
 *
 * Version 4.2 (Topcoder - Be Able To Lower the Prize After Activation)
 * - Add support for lowering prize after challenge being activated
 *
 * Version 4.3 (TOPCODER - SUPPORT TYPEAHEAD FOR TASK ASSIGNEES IN DIRECT APP):
 * - Move task assign member to use magicSuggest
 *
 * Version 4.4 (Topcoder - Add Basic Marathon Match Creation And Update In Direct App):
 * - Update for Marathon match registration end_date
 *
 * Version 4.5 (Topcoder - Support Points Prize Type For Challenges)
 * - Add support for points prize type
 *
 * Version 4.6 (Topcoder - Add effort days field)
 * - Add enable effort days
 * @author isv, minhu, pvmagacho, GreatKevin, Veve, GreatKevin, TCSCODER
 * @version 4.6
 */
// can edit multi round
var canEditMultiRound = true;
var contestHasSpecReview = true;
var loadingChallengeDetails = false;

function getContestPrize(prizesData, place) {
    if(prizesData && prizesData.length >= place) {
        for(var i = 0; i < prizesData.length; ++i) {
            if(prizesData[i].place == place && prizesData[i].prizeType.id == 15) {
                return prizesData[i].prizeAmount;
            }
        }
    }

    return -1;
}

$(document).ready(function(){
    if($("#timelineModule .heading .draft").length == 0) {
        // no draft challenge
        showSaveChallengeConfirmation = false;
    }

	  /* init select */
	  if($('select').length > 0){
	  	//$('.selectSoftware select,.selectDesing select,.projectSelect select,.billingSelect select,.roundelect select,.startSelect select,.checkpointSelect select,.endSelect select,.startEtSelect select,.checkpointEtSelect select,.endEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect();

	  	//$('.selectDesing div.selectedTxt').html('Select Contest Type');
	  }

    /* init date-pack */
    if ($('.date-pick').length > 0) {
        $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
        setupDateTimeSuffix("start");
        setupDateTimeSuffix("end");
        setupDateTimeSuffix("checkPointEnd");
    }

    $.each(billingAccounts, function(key, value) {
    	var _cca = value["cca"] == "true" ? true : false;
      var _enableEffortDays = value["enableEffortDays"] == "true" ? true : false;
      $("#billingProjects").append($('<option></option>').val(value["id"]).html(value["name"]).data("cca", _cca).data("enableEffortDays", _enableEffortDays));
    });
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
		    groupCancel = true;
		    jQuery_1_11_1("#groups").magicSuggest().clear();
		    jQuery_1_11_1("#groups").magicSuggest().setValue(mainWidget.softwareCompetition.groups);
            groupCancel = false;

            jQuery_1_11_1("#preRegisterUsers").magicSuggest().clear();
            jQuery_1_11_1("#preRegisterUsers").magicSuggest().setValue(mainWidget.softwareCompetition.registrants);
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

        $(".cancel_text_round").click(function () {
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
            if (mainWidget.competitionType == 'SOFTWARE') {
                calcPrizes(originalPrizes.prizes);
                calcDR(originalPrizes.digitalRun);
            }
            populatePrizeSection();
            updateContestCostData();
            showPrizeSectionDisplay();
        });

		//point
		$(".edit_point").click(function(){
			showPointSectionEdit();
		});
		$(".save_btn_point").click(function(){
			savePointSection();
		});
		$(".cancel_text_point").click(function(){
			showPointSectionDisplay();
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

    var SGTemplatesList = ['/scripts/ckeditor/templates/software_guidelines_templates.js'];
    var DRTemplatesList = ['/scripts/ckeditor/templates/detailed_requirements_templates.js'];
    var StudioContestSpecTemplates = ['/scripts/ckeditor/templates/studio/studio_contest_spec_templates.js'];
    CKEDITOR.loadTemplates(SGTemplatesList);
    CKEDITOR.loadTemplates(DRTemplatesList);
    CKEDITOR.loadTemplates(StudioContestSpecTemplates);

   	//Get the contest and populate each section
   // loading some configuration data
   $.ajax({
      type: 'POST',
      url:  ctx+"/contest/detailJson",
      data: {"projectId":paramContestId},
      cache: false,
      dataType: 'json',
      async : true,
      beforeSend: function() {
        $("#contestLoading").show();
        loadingChallengeDetails = true;
      },
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
            loadingChallengeDetails = false;

            try {
            	CKEDITOR.replace( 'contestIntroduction' );
            } catch (err) {
            	// pass
            }
            try {
            	CKEDITOR.replace( 'round1Info' );
            } catch (err) {
            	// pass
            }
            try {
            	CKEDITOR.replace( 'round2Info' );
            } catch (err) {
            	// pass
            }
            if(mainWidget.isAlgorithmContest()) {
                        try {
                            CKEDITOR.replace('marathonMatchDetails');
                            CKEDITOR.replace('marathonMatchRules');
                        } catch (err) {
                            // ignore
                        }
            }
            //execute some actions specific for component design/dev
            //onContestTypeChange();
            $("#contestLoading").hide();
            var waitForMCE=function() {
                if (!CKEDITOR.instances.contestDescription) {
                    setTimeout(waitForMCE, 500);
                    return;
                }
                updateMCEPlaceHolderCtl();
            };
            setTimeout(waitForMCE, 500);
            //update group
            var currentData = jQuery_1_11_1("#groups").magicSuggest().getData();
            var currentGroupIds = currentData.map(function(val){return val.id});
            var additionGroups = mainWidget.softwareCompetition.groups.filter(function(val){
                return !(currentGroupIds.indexOf(val) > 0)});

            $(mainWidget.softwareCompetition.groups).each(function(i, val){
                if (additionGroups.indexOf(val) > -1){
                    currentData.unshift({id: val, name: mainWidget.softwareCompetition.groupNames[i], selected: true})
                }
                });
            groupCancel = true;
            if (currentData.length > 0 ){
                jQuery_1_11_1("#groups").magicSuggest().enable();
            }
            jQuery_1_11_1("#groups").magicSuggest().setData(currentData);
            jQuery_1_11_1("#groups").magicSuggest().setValue(mainWidget.softwareCompetition.groups);

            groupCancel = false;
            showPointSectionDisplay();
          },
          function(errorMessage) {
              showServerError(errorMessage);
              $("#contestLoading").hide();
          })
      }
   });

   // round types
   $('#roundTypes').bind("change", function() {
        var roundType = $('#roundTypes').val();
        if(roundType == 'single') {
            // single round
		   $('#endEditDiv .name_label').html('<strong>Round 1 Duration:</strong>');
           $('#checkpointEditDiv').hide();
           $('#checkPointEndDateEditDiv').hide();
           $('#checkpointPrizeDiv').hide();
           $('#roundInfoDiv').hide();
           $('[id=rCheckpointTR]').hide();
           $('#rMultiRoundInfoDiv').hide();
           $('#rCheckPointPrizeDiv').hide();
        } else {
            // multiple round
		   $('#endEditDiv .name_label').html('<strong>Round 2 Duration:</strong>');
           $('#checkpointEditDiv').show();
           $('#checkPointEndDateEditDiv').show();
           $('#checkpointPrizeDiv').show();
           $('#roundInfoDiv').show();
           $('[id=rCheckpointTR]').show();
           $('#rMultiRoundInfoDiv').show();
           $('#rCheckPointPrizeDiv').show();
		   $(".checkpointEtSelect select,.numSelect select, #checkPointEndTime").each(function(index){
				if(!$(this).is(":hidden") && !$(this).data('customized')){
					$(this).data('customized',true);
					$(this).sSelect({ddMaxHeight: '220',yscroll: true});
				}
			});
		   if (!mainWidget.softwareCompetition.multiRound && mainWidget.softwareCompetition.assetDTO.directjsProductionDate) {
			   var startDate = mainWidget.softwareCompetition.assetDTO.directjsProductionDate;
			   // $('#checkpointDate').datePicker().val(getDatePart(new Date(startDate.getTime() + 3 * 24 * 60 * 60 * 1000))).trigger('change');
			   $('#checkPointEndDate').datePicker().val(getDatePart(new Date(startDate.getTime()+ 2 * 24 * 60 * 60 * 1000))).trigger('change');
               $("#checkPointEndTime").val('09:00').trigger('change');
		   }
        }
   });
   $('#roundTypes').trigger("change");

   // choose contest type
   $('#contestTypes').bind("change", function() {
   	   onContestTypeChange();
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
      var billingProjectId = $('select#billingProjects').val();
   	  fillPrizes(billingProjectId);
   });

   $('#swFirstPlace').bind('keyup',function() {
       onFirstPlaceChangeKeyUp();
    });

    $("#swCheckpointPrize").bind('keyup', function() {
        onSoftwarePrizeInputChange($('#swCheckpointPrize'), "Checkpoint prize", true);
    });

    $("#swCheckpointSubmissionNumber").bind('change', function() {
        onSoftwarePrizeInputChange($('#swCheckpointPrize'), "Checkpoint prize", true);
    })

    $("#checkpointPrize").bind('keyup', function() {
        onStudioPrizeInputChange($('#checkpointPrize'), "Checkpoint prize", true);
    });

    $("#checkpointSubmissionNumber").bind('change', function() {
        onStudioPrizeInputChange($('#checkpointPrize'), "Checkpoint prize", true);
    })

    $("#studioCupPointsCheckBox").change(function(){
        onTheFlyCalculateStudioCosts();
    })

    $(".prizesInner_software input[type=text].prizesInput").bind('keyup', function(){
        if($(this).attr('id') == 'swFirstPlace') return;
        var prizeIndex = $(".prizesInner_software input[type=text].prizesInput").index(this) + 1;
        var prizeName;
        switch (prizeIndex) {
            case 2 : prizeName = "2nd"; break;
            case 3 : prizeName = "3rd"; break;
            default: prizeName = prizeIndex + "th"; break;
        }
        onSoftwarePrizeInputChange($(this), prizeName + " Place Prize", false);
    })


    $(".studioPrizes input[type=text].prizesInput").bind('keyup', function(){
        var prizeIndex = $(".studioPrizes input[type=text].prizesInput").index(this) + 1;
        var prizeName;
        switch (prizeIndex) {
            case 1 : prizeName = "1st"; break;
            case 2 : prizeName = "2nd"; break;
            case 3 : prizeName = "3rd"; break;
            default: prizeName = prizeIndex + "th"; break;
        }
        onStudioPrizeInputChange($(this), prizeName + " Place Prize", prizeIndex == 1);
    })

    $('#swDigitalRun').bind('keyup',function() {
       onDigitalRunChangeKeyUp();
    });


   $('#ContestDescriptionHelpIcon .helpIcon').hover(function(){
	   showPopup(this,'contestDescriptionToolTip');
   },function(){
	   $('#contestDescriptionToolTip').hide();
   });

   $('#Round1HelpIcon .helpIcon').hover(function(){
	   showPopup(this,'contestRound1ToolTip');
   },function(){
	   $('#contestRound1ToolTip').hide();
   });

   $('#Round2HelpIcon .helpIcon').hover(function(){
	    showPopup(this,'contestRound2ToolTip');
   },function(){
	   $('#contestRound2ToolTip').hide();
   });

    $("#reviewScorecardSelects").sSelect({ddMaxHeight: '220',yscroll: true});
    $("input[name=reviewType]").click(function(){
        if('internal' == $("input[name=reviewType]:checked").val()) {
            // show the reviewer dropdown
            var selectedValue = $("#reviewer").val();
            $("#reviewerEditDiv").show();
            $("#reviewer").resetSS();
            $("#reviewer").getSetSSValue(selectedValue);
        } else {
            $("#reviewerEditDiv").hide();
        }
    });

    $("#billingGroupCheckBox input[type=checkbox]").change(function () {
        if ($(this).is(":checked")) {
            $("#billingGroupCheckBox select").show();
            $("#billingGroupCheckBox select option").remove();
            var selectedBillingID = $("#billingProjects").val();
            if(selectedBillingID > 0 && billingGroups[selectedBillingID] && billingGroups[selectedBillingID].length > 0) {
                $.each(billingGroups[selectedBillingID], function(i, v){
                    $("#billingGroupCheckBox select").append($("<option/>").attr('value', v.id).text(v.name));
                })
            }
        } else {
            $("#billingGroupCheckBox select").hide();
        }
    });

    // billing projects
    $('#billingProjects').bind("change", function() {
        if ($(this).find(":selected").data("cca")){
            mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
            $("#chkboxCCA").attr('checked','true').attr('disabled','true');
            $('#rCCA').html(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
        }else{
            $("#chkboxCCA").removeAttr('disabled');
        }

        if($(this).find(":selected").data("enableEffortDays")) {
          $('.effortEstimateRow').show();
        } else {
          $('.effortEstimateRow').hide();
        }

        if(!loadingChallengeDetails) {
            updateContestFee();
        }
        updateBillingGroups();
    });

    $('#registrants').sSelect({ddMaxHeight: '220',yscroll: true});
    $("#copilots").change(function(){
        if ($(this).val() == 0){
            $(".copilotFee").val(0);
            $(".copilotFee").attr("disabled", true);
            mainWidget.softwareCompetition.copilotCost = 0;
        }else{
            mainWidget.softwareCompetition.copilotCost = copilotFees[getContestType(true)[1]]["copilotFee"];
            $(".copilotFee").attr("disabled", false);
            $(".copilotFee").val(mainWidget.softwareCompetition.copilotCost);
        }
    });
    $(".copilotFee").focusout(function(){
        var error = validateCopilotFee($(this).val(), true);
        if(error){
            //revert the value
            $(this).val(mainWidget.softwareCompetition.copilotCost);
        }
    });
    $(".copilotFee").keyup(function(){
        if(checkNumber($(this).val()) && !isNaN(parseFloat($(this).val()))){
            if(mainWidget.isStudioContest()){
                fillStudioPrizes();
            }else {
                fillPrizes();
            }
        }
    });
});

var ACTIVE_PROJECT_STATUS = 1;
var isActiveContest = false;
var startedContest = true;
var preCost = 0;
var reviewScorecards;

function loadReviewScorecardList(typeId){
    var $contestReviewScorecard = $("#reviewScorecardSelects");

    $contestReviewScorecard.html("");

    $contestReviewScorecard.append($('<option></option>').val(0).html("Please select a review scorecard to associate"));

    if(typeId === null){
        return;
    }
    reviewScorecards = getReviewScorecards(typeId);

    $.each(reviewScorecards, function(id, value) {
        $contestReviewScorecard.append($('<option></option>').val(value.id).text(htmlEncode(value.scorecardName + ' ' + value.scorecardVersion)).attr('title', htmlEncode(value.scorecardName + ' ' + value.scorecardVersion)));
    });

    $("#reviewScorecardSelects").resetSS();
    // Select default review scorecard.
    $.each(reviewScorecards, function(id, value) {
        if(value.projectCategory == 6 || value.projectCategory == 28){
          $('#reviewScorecardSelects').getSetSSValue(value.id);
          return false;
        }
    });
    // Override default review scorecard. Select reviewScorecardId for this challenge if it is listed.
    $.each(reviewScorecards, function(id, value) {
        if(value.projectCategory == typeId){
            if (value.projectCategory != SOFTWARE_CATEGORY_ID_F2F) {
                if(value.id == mainWidget.softwareCompetition.projectHeader.reviewScorecardId){
                    $('#reviewScorecardSelects').getSetSSValue(value.id);
                    return false;
                }
            } else {
                if(value.id == mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId){
                    $('#reviewScorecardSelects').getSetSSValue(value.id);
                    return false;
                }
            }

        }
    });
}

/**
 * event handler function when contest type is changed.
 */
function onContestTypeChange() {
    var currentTypeId = -1;
    var contestType = getContestType(true)[0];
    var typeId = getContestType(true)[1];
    loadReviewScorecardList(typeId);

    if(isContestSaved()) {
        currentTypeId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        if(currentTypeId == typeId && $("#contestTypes").data("initialized")) {
            return false;
        }
    }

    var SGTemplatesList = ['/scripts/ckeditor/templates/software_guidelines_templates.js'];
    var DRTemplatesList = ['/scripts/ckeditor/templates/detailed_requirements_templates.js'];
    var StudioContestSpecTemplates = ['/scripts/ckeditor/templates/studio/studio_contest_spec_templates.js'];
    if (contestType == 'SOFTWARE' && typeId != 37) {
        var swGuidelines = CKEDITOR.instances['swGuidelines'];
        if (swGuidelines) {
            swGuidelines.destroy(true);
        }
        var swDetailedRequirements = CKEDITOR.instances['swDetailedRequirements'];
        if (swDetailedRequirements) {
            swDetailedRequirements.destroy(true);
        }
        CKEDITOR.replace('swGuidelines', {
            templates: getSGTemplatesName(typeId),
            templates_files: SGTemplatesList
        });
        CKEDITOR.replace('swDetailedRequirements', {
            templates: getDRTemplatesName(typeId),
            templates_files: DRTemplatesList
        });
    } else if (contestType == 'STUDIO'){
        var contestDescription = CKEDITOR.instances['contestDescription'];
        if (contestDescription) {
            contestDescription.destroy(true);
        }
        CKEDITOR.replace('contestDescription', {
            templates: getStudioTemplatesName(typeId),
            templates_files: StudioContestSpecTemplates
        });
    } else if (contestType == 'ALGORITHM') {
        // algorithm has only one type now, no need to destory
    }

    if(isContestSaved() && typeId != currentTypeId) {
        if(typeId == SOFTWARE_CATEGORY_ID_F2F) {
            showErrors("You cannot change saved non-First2Finish challenge to First2Finish challenge type");
            // switch back to First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == SOFTWARE_CATEGORY_ID_F2F) {
            showErrors("You cannot change saved First2Finish challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);
            return;
        }
        if(typeId == SOFTWARE_CATEGORY_ID_CODE) {
            showErrors("You cannot change saved non-Code challenge to Code challenge type");
            // switch back to First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == SOFTWARE_CATEGORY_ID_CODE) {
            showErrors("You cannot change saved Code challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);
            return;
        }
        if(typeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
            showErrors("You cannot change saved non-Design F2F challenge to Design F2F challenge type");
            // switch back to Design First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('STUDIO' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
            showErrors("You cannot change saved Design F2F challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('STUDIO' + currentTypeId);
            }, 1000);
            return;
        }
        if(typeId == SOFTWARE_CATEGORY_ID_AUTOMATE) {
            showErrors("You cannot change saved non-AutomatedTesting challenge to Code challenge type");
            // switch back to First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == SOFTWARE_CATEGORY_ID_AUTOMATE) {
            showErrors("You cannot change saved AutomatedTesting challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);
            return;
        }
    }


    if (isContestSaved() && mainWidget.competitionType != contestType) {
        showErrors("You can not switch between studio / software / algorithm after it is saved.");
        return;
    }

    mainWidget.competitionType = contestType;

    if(isDevOrDesign()) {
     $('.component').show();
    } else {
     $('.component').hide();
    }

    $("#contestTypes").data("initialized", true);

    if(typeId == currentTypeId) {
     // it is a revert, nothing to do here
     return;
    }
}

/**
 * Fix the id in mainWidget.softwareCompetition.fileTypes.
 * @since 1.7
 */
function fixFileTypeIds() {
    var types = getSplitFileTypes(mainWidget.softwareCompetition.fileTypes);
    var fileTypes = [];
    for (var i = 0; i < types[0].length; i++) {
        fileTypes.push(types[0][i].value);
    }
    for (var i = 0; i < types[1].length; i++) {
        fileTypes.push(types[1][i]);
    }
    mainWidget.softwareCompetition.fileTypes = fileTypes;
}

function initContest(contestJson) {
    if (contestJson.projectMMSpecification) {
        mainWidget.competitionType = 'ALGORITHM';
        // set  marathon match specification to main widget
        mainWidget.softwareCompetition.projectHeader.projectMMSpecification = contestJson.projectMMSpecification;
        delete mainWidget.softwareCompetition.projectHeader.projectMMSpecification.creationTimestamp;
        delete mainWidget.softwareCompetition.projectHeader.projectMMSpecification.creationUser;
        delete mainWidget.softwareCompetition.projectHeader.projectMMSpecification.modificationTimestamp;
        delete mainWidget.softwareCompetition.projectHeader.projectMMSpecification.modificationUser;
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification;
    } else if (!contestJson.projectStudioSpecification) {
        mainWidget.competitionType = 'SOFTWARE';
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification;
    } else {
        mainWidget.competitionType = 'STUDIO';
        mainWidget.softwareCompetition.projectHeader.projectStudioSpecification = contestJson.projectStudioSpecification;
        var fileTypes = [];
        var types = contestJson.fileTypes;
        if (types) {
            for (var i = 0; i < types.length; i++) {
                fileTypes.push(types[i].description);
            }
        }
        mainWidget.softwareCompetition.fileTypes = fileTypes;
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.creationTimestamp;
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.creationUser;
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.modificationTimestamp;
        delete mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.modificationUser;
    }
    var isStudio = (mainWidget.competitionType == 'STUDIO');
    var isSoftware = (mainWidget.competitionType == 'SOFTWARE');
    var isAlgorithm = (mainWidget.competitionType == 'ALGORITHM');

    //general initialization
    // populate the select option for software group
    $.each(projectCategoryArray, function (i, projectCategory) {
        if (projectCategory.hideInDropdown) {
            return;
        }
        // not show copilot contest type
        if (isSoftware && projectCategory.id != 29 && projectCategory.id != 37 && projectCategory.typeId != 3) {
            if ($("optgroup[label='Software']").length > 0) {
                $("<option/>").val("SOFTWARE" + projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Software']");
            } else {
                $("<option/>").val("SOFTWARE" + projectCategory.id).text(projectCategory.label).appendTo("#contestTypes");
            }
//            $("<option/>").val("SOFTWARE"+projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Software']");
        }
        if (isStudio && projectCategory.typeId == 3) {
            if ($("optgroup[label='Studio']").length > 0) {
                $("<option/>").val("STUDIO" + projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Studio']");
            } else {
                $("<option/>").val("STUDIO" + projectCategory.id).text(projectCategory.label).appendTo("#contestTypes");
            }
        }
        if (isAlgorithm && projectCategory.id == 37) {
            if ($("optgroup[label='Algorithm']").length > 0) {
                $("<option/>").val("ALGORITHM" + projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Algorithm']");
            } else {
                $("<option/>").val("ALGORITHM" + projectCategory.id).text(projectCategory.label).appendTo("#contestTypes");
            }
        }
    });

   //contest data
   mainWidget.softwareCompetition.multiRound = contestJson.hasMulti;
   if (contestJson.hasMulti) {
	   mainWidget.softwareCompetition.checkpointDate = parseDate(contestJson.multiRoundEndDate);
   }
   mainWidget.softwareCompetition.projectHeader.id = contestJson.contestId;
   mainWidget.softwareCompetition.projectHeader.projectCategory= contestJson.projectCategory;
   mainWidget.softwareCompetition.projectHeader.projectStatus = contestJson.projectStatus;
   mainWidget.softwareCompetition.projectHeader.securityGroupId = contestJson.securityGroupId;
   mainWidget.softwareCompetition.assetDTO.name = contestJson.contestName;
   mainWidget.softwareCompetition.endDate = parseDate(contestJson.endDate);
   mainWidget.softwareCompetition.paidFee = contestJson.paidFee;
   mainWidget.softwareCompetition.adminFee = contestJson.adminFee;
    mainWidget.softwareCompetition.registrants = contestJson.registrant;
    mainWidget.softwareCompetition.registrants.sort(function(a, b){
        return a.name.localeCompare(b.name);
    })
   var startDate =  parseDate(contestJson.startDate);
   mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
   mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);
   mainWidget.softwareCompetition.subEndDate = parseDate(contestJson.subEndDate);
   mainWidget.softwareCompetition.regEndDate = parseDate(contestJson.regEndDate);

   $('#contestTypeNameText').text(getProjectCategoryById(mainWidget.softwareCompetition.projectHeader.projectCategory.id).name);
   mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE] = contestJson.properties[ESTIMATE_EFFORT_OFFSHORE];
   mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE] = contestJson.properties[ESTIMATE_EFFORT_ONSITE];

    // copilots
    var copilots = contestJson.copilots; // get copilots data from result
    var hasCopilot = false;

    $.each(copilots, function(k, v){
        mainWidget.softwareCompetition.copilotUserId = k;
        mainWidget.softwareCompetition.copilotUserName = v;
        hasCopilot = true;
    });

    mainWidget.softwareCompetition.copilotCost = parseFloat(contestJson.copilotsFee);

    // milestone
    if(mainWidget.competitionType != 'ALGORITHM') {
        mainWidget.softwareCompetition.projectMilestoneId = contestJson.directProjectMilestoneId;
        mainWidget.softwareCompetition.projectMilestoneName = contestJson.directProjectMilestoneName;
    }

   if(isDevOrDesign()) {
     mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = contestJson.rootCategoryId;
     mainWidget.softwareCompetition.assetDTO.directjsCategories = contestJson.categoryIds;
   }

   if(isTechnologyContest()) {
     mainWidget.softwareCompetition.assetDTO.directjsTechnologies = contestJson.technologyIds;
   }

   if(isPlatformContest()) {
       mainWidget.softwareCompetition.platforms = contestJson.platformIds;
   }

    mainWidget.softwareCompetition.groups = contestJson.groupIds;
    mainWidget.softwareCompetition.groupNames = contestJson.groupNames || [];

   var projectHeader = mainWidget.softwareCompetition.projectHeader;
   projectHeader.tcDirectProjectId = contestJson.tcDirectProjectId;
   projectHeader.tcDirectProjectName = contestJson.tcDirectProjectName;
   projectHeader.setBillingProject(contestJson.billingProjectId);
   projectHeader.setProjectName(contestJson.contestName);
   projectHeader.properties = contestJson.properties;
   projectHeader.challengeCreator = contestJson.challengeCreator;
   projectHeader.reviewScorecardId = contestJson.reviewScorecardId ? contestJson.reviewScorecardId: 0;
   projectHeader.iterativeReviewScorecardId = contestJson.iterativeReviewScorecardId ? contestJson.iterativeReviewScorecardId : 0;
   projectHeader.projectSpec.detailedRequirements = contestJson.detailedRequirements;
   projectHeader.projectSpec.finalSubmissionGuidelines = contestJson.softwareGuidelines;
   projectHeader.projectSpec.privateDescription = contestJson.privateDescription;
   projectHeader.prizes = [];
   projectHeader.points = [];
   if (contestJson.prizes) {
     var hasContestPrize = false;
	   for (var i = 0; i < contestJson.prizes.length; i++) {
		   delete contestJson.prizes[i].creationTimestamp;
		   delete contestJson.prizes[i].creationUser;
		   delete contestJson.prizes[i].modificationTimestamp;
		   delete contestJson.prizes[i].modificationUser;
		   if (contestJson.prizes[i].prizeType.id === CHALLENGE_POINT_TYPE_ID) {
		       projectHeader.points.push(contestJson.prizes[i]);
		   } else {
           if (contestJson.prizes[i].prizeType.id === CONTEST_PRIZE_TYPE_ID) {
             hasContestPrize = true;
           }
		       projectHeader.prizes.push(contestJson.prizes[i]);
		   }
     }
     if (!hasContestPrize) {
       projectHeader.prizes.push(new com.topcoder.direct.Prize(1, 0, CONTEST_PRIZE_TYPE_ID, 1));
       if(!(isDesignF2F() || isF2F() || isBugHunt() || isCode())) {
         projectHeader.prizes.push(new com.topcoder.direct.Prize(2, 0, CONTEST_PRIZE_TYPE_ID, 1));
       }
     }
   }
   if (!contestJson.groupIds || !contestJson.groupIds.length) {
    projectHeader.points = [];
   }

    var digitalRunPoints = projectHeader.getDRPoints();
    var digitalRunFlag = projectHeader.properties['Digital Run Flag'];

    if(digitalRunFlag != 'On' || digitalRunPoints == undefined) {
        digitalRunPoints = 0;
    }

   //prizes: if custom level, initiate customCosts object so it is not derived from custom level any more
   if(projectHeader.getCostLevel() == COST_LEVEL_CUSTOM) {
   	    customCosts = {};
        customCosts.firstPlaceCost = parseFloat(projectHeader.getFirstPlaceCost());

       if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1) > 0) {
           customCosts.firstPlaceCost = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1);
       }


        customCosts.secondPlaceCost = parseFloat(projectHeader.getSecondPlaceCost());

       if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2) > 0) {
           customCosts.secondPlaceCost = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2);
       }


        customCosts.reviewBoardCost = parseFloat(projectHeader.getReviewCost());
        customCosts.reliabilityBonusCost = parseFloat(projectHeader.getReliabilityBonusCost());
        customCosts.specReviewCost = parseFloat(projectHeader.getSpecReviewCost());
        customCosts.drCost = parseFloat(digitalRunPoints);

        if(projectHeader.prizes.length > 2) {
            // there are extra prizes
            var extraPrizes = [];

            for(var placement = 3; placement <= 5; ++placement) {
                var extraPrizeAmount = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, placement);
                if(extraPrizeAmount > 0) {
                    extraPrizes.push(extraPrizeAmount);
                }
            }
            customCosts.extraPrizes = extraPrizes;
        }
   }
    //documentations, each doc has fields of documentId, fileName, description, documentTypId, url
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

    // Check if the prizes were updated manually in Online Review, if so then treat this as custom
    // prize schema
    var customPrizesUsed = false;
    if (projectHeader.getCostLevel() != COST_LEVEL_CUSTOM) {
        var prize1 = parseFloat(projectHeader.getFirstPlaceCost());

        if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1) > 0) {
            prize1 = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1);
        }

        var prize2 = parseFloat(projectHeader.getSecondPlaceCost());

        if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2) > 0) {
            prize2 = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2);
        }

        var reviewCost = parseFloat(projectHeader.getReviewCost());
        var reliabilityBonusCost = parseFloat(projectHeader.getReliabilityBonusCost());
        var drPointsCost = parseFloat(digitalRunPoints);
        var specReviewCost = parseFloat(projectHeader.getSpecReviewCost());

        // Iterate over billing schemas and try to find which matches the prizes exacly
        // If no such schema found then this indicates that the prizes were updated in O/R
        // manually and treat this as custom schema
        var index;
        if (projectHeader.getCostLevel() == 'A') {
            index = 0;
        }
        if (projectHeader.getCostLevel() == 'B') {
            index = 1;
        } else {
            index = 2;
        }
        var feesConfig = softwareContestFees[mainWidget.softwareCompetition.projectHeader.projectCategory.id + ''];
        if (feesConfig) {
            var c = feesConfig.contestCost.contestCostBillingLevels[index];
            if (c) {
                var s_prize1 = c.firstPlaceCost;
                var s_prize2 = c.secondPlaceCost;
                var s_reviewCost = c.reviewBoardCost;
                var s_reliabilityBonusCost = c.reliabilityBonusCost;
                var s_drPointsCost = c.drCost;
                var s_specReviewCost = feesConfig.specReviewCost;

                if ((prize1 != s_prize1) || (prize2 != s_prize2) || (reviewCost != s_reviewCost)
                    || (reliabilityBonusCost != s_reliabilityBonusCost)
                    || (drPointsCost != s_drPointsCost || digitalRunFlag != 'On') || (specReviewCost != s_specReviewCost)) {
                    customPrizesUsed = true;
                }
            }
        }
        if (customPrizesUsed) {
            customCosts = {};
            customCosts.firstPlaceCost = prize1;
            customCosts.secondPlaceCost = prize2;
            customCosts.reviewBoardCost = reviewCost;
            customCosts.reliabilityBonusCost = reliabilityBonusCost;
            customCosts.drCost = drPointsCost;
            customCosts.specReviewCost = specReviewCost;
            mainWidget.softwareCompetition.projectHeader.setCostLevel(COST_LEVEL_CUSTOM);
        }
    }

	 if(isPrizeEditable(contestJson.billingProjectId) || projectHeader.getCostLevel() == COST_LEVEL_CUSTOM
             || customPrizesUsed) {
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

    // use a different prize layout for Code/F2F/Bug Hunt contest, hide unused prize settings
    if (isCode() || isF2F() || isBugHunt()) {
        // hide unused prize settings
        $(".topcoderPrize").hide();
        $(".codePrize").show();

        if(isBugHunt()) {
            $("#swSpecCost").parent().show();
            $("#rswSpecCost").parent().show();
        }

        mainWidget.softwareCompetition.reviewers = contestJson.reviewers;
    } else {
        // show the prize settings for TopCoder contests
        $(".topcoderPrize").show();
    }

    if(isCode()) {
        $(".codePrize").show();
    } else if(isF2F() || isBugHunt()) {
        $(".codePrize").hide();
    }

    if(isCode()) {
        // hide the multiple prize input
        $(".prizesInner_software #prize2").show();
        $(".prizesInner_software .swAdd").show();
    } else {
        // hide the multiple prize input
        $(".prizesInner_software #prize2").hide();
        $(".prizesInner_software .swAdd").hide();
    }

   if(isTechnologyContest()) {
   	 $('.technology').show();
   } else {
     $('.technology').hide();
   }

   if(isPlatformContest()) {
     $(".platform").show();
   } else {
     $(".platform").hide();
   }

   // preview contest, repost and new version
   var statusName = contestJson.projectStatus.name;
    if(statusName.indexOf('Draft') != -1) {
         $('#previewContestButton').show();
    } else {
         $('#viewContestButton').show();
    }
   if(statusName.indexOf('Cancelled') != -1) {
    	 repostProjectId = contestJson.contestId;
	     repostTcProjectId = contestJson.tcDirectProjectId;

   	  $('#repostButton').show();
      $('#repostButtonSplitter').show();
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
      $('#newVersionButtonSplitter').show();
   	  $('#newVersionButton').click(function(){
   	      $('#newVersionDialog').dialog('open');
   	  });
   }

    phaseOpen = contestJson.phaseOpen;
    isCompleted = contestJson.projectStatus.id == 7;
    isCancelled = (contestJson.projectStatus.id > 3) && !isCompleted;
    var isTask = projectHeader.properties[TASK_FLAG] == "1";
    var hasPhaseOpen = $("ul.progressContainer li.current").size() > 0
    var morePhaseCompleted = $("ul.progressContainer li.completed").size() > 1

    // if has no write permission, no edit; if any phase is open, no edit
    $('#contestNameText').hide();
    if (!hasContestWritePermission) {
        $('a.button11').hide();
        $("#resubmit").hide();
        $(".activateButton").hide();
        $("#swEdit_bottom_review").hide();
    } else if(isCompleted || isCancelled) {
        $('a.button11').hide();
        $('a.editType').show();
        $('#chkboxCCA').attr("disabled", true);
        $('#contestName').hide();
        $('#contestNameText').show();
        $('#copilots').attr("disabled", true);
        $('#milestones').attr("disabled", true);
        $('.copilotsSelect').hide();
        if (mainWidget.softwareCompetition.copilotUserId <= 0) {
            $('<span style="line-height:30px;font-size:14px"></span>').text("Unassigned").insertAfter(".copilotsSelect");
        } else {
            $('<span style="line-height:30px;font-size:14px"></span>').text(mainWidget.softwareCompetition.copilotUserName).insertAfter(".copilotsSelect");

        }
        $('.detailsContent_det_type_edit span.name3 input').hide();
        $('.detailsContent_det_type_edit span.name3 strong').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "NDA is Required" : "NDA is Not Required");

        $('#contestTypes').attr("disabled", true);
        $('.contestTypeRO').show();
        $('.contestTypeEditSection').hide();

    } else if(phaseOpen) {
        $('a.button11').hide();
        $('a.editType').show();
        $('#chkboxCCA').attr("disabled", true);
//        $('#contestName').hide();
//        $('#contestNameText').show();
        $('#copilots').attr("disabled", true);
        $('#milestones').attr("disabled", true);
        $('.copilotsSelect').hide();
        if (mainWidget.softwareCompetition.copilotUserId <= 0) {
            $('<span style="line-height:30px;font-size:14px"></span>').text("Unassigned").insertAfter(".copilotsSelect");
        } else {
            $('<span style="line-height:30px;font-size:14px"></span>').text(mainWidget.softwareCompetition.copilotUserName).insertAfter(".copilotsSelect");

        }
        $('.detailsContent_det_type_edit span.name3 input').hide();
        $('.detailsContent_det_type_edit span.name3 strong').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "NDA is Required" : "NDA is Not Required");

        $('#contestTypes').attr("disabled", true);
        $('.contestTypeRO').show();
        $('.contestTypeEditSection').hide();

        // open prize edit section if project is active
        if (contestJson.projectStatus != null && contestJson.projectStatus.name == DRAFT_STATUS) {
            isActiveContest = true;
            $(".edit_prize").parent().show();
            if (isTask){
                $(".edit_round").show();
                $('#roundEdit').show();
                $(".edit_prize").show();
                $(".edit_point").show();
            }
        } else {
            $(".edit_prize").show();
            $(".edit_point").show();
            $(".edit_round").show();
            //enable edit date for task, not have open phase, not have phase complete other than registration
            if (isTask && !hasPhaseOpen && !morePhaseCompleted){
                $('#roundEdit').show();
                $('#roundText').hide();
            }else {
                $('#roundEdit').hide();
                $('#roundText').show();
            }
        }

        $(".edit_spec").show();
        $(".edit_files").show();
    } else {
        $('.contestTypeEditSection').show();
    }
    // if review / iterative review (can have multiple) phases are all closed - do not allow prize edit
    if(contestJson.isReviewPhaseClosed) {
        $(".edit_prize").hide();
        $(".edit_point").hide();
        $(".edit_round").hide();
        $(".privateCmd").hide();
    }else if (contestJson.projectStatus != null && contestJson.projectStatus.id == ACTIVE_PROJECT_STATUS &&
    contestJson.properties[TASK_FLAG] == "1"){
        $(".privateCmd").show();
    }


    showSpecReview(contestJson);

    // can't change the multi round type if conetst is not draft
    if (contestJson.projectStatus.name != "Draft") {
    	canEditMultiRound = false;
    }

    $(".drHide").hide();
}

/**
 * Retrieve contest cost without admin fee.
 */
function retrieveContestCostWithoutAdminFee() {
    var prizeType = $('input[name="prizeRadio"]:checked').val();
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
    var feeObject = softwareContestFees[projectCategoryId];

    if(!feeObject) {
         return 0;
    }
    return getContestTotal(feeObject, prizeType) - mainWidget.softwareCompetition.adminFee;
}

/**
 * Type Section Functions
 */
function populateTypeSection() {
        var p = mainWidget.softwareCompetition.projectHeader.properties;
        var contestPercentage = parseFloat(p['Contest Fee Percentage']);
		var adminFee = parseFloat(p['Admin Fee']);

	//edit
	$('#contestTypes').getSetSSValue(mainWidget.competitionType+mainWidget.softwareCompetition.projectHeader.projectCategory.id);
	$('#contestName').val(mainWidget.softwareCompetition.assetDTO.name);
	$('#contestNameText').text(mainWidget.softwareCompetition.assetDTO.name);
  $('#challegneCreatorLabel').text(mainWidget.softwareCompetition.projectHeader.challengeCreator);
	$('#chkboxCCA').attr('checked', mainWidget.softwareCompetition.projectHeader.isLccchecked());

	//product
    /*
    if (mainWidget.softwareCompetition.projectHeader.properties[PRODUCT_SKU] != null){
        $("#rProduct").text(mainWidget.softwareCompetition.projectHeader.properties[PRODUCT_SKU]);
        $("#productName").val(mainWidget.softwareCompetition.projectHeader.properties[PRODUCT_SKU]);
    }
    */
	$('#rContestTypeName').text($("#contestTypes option[value=" + mainWidget.competitionType + mainWidget.softwareCompetition.projectHeader.projectCategory.id +"]").text());
  loadReviewScorecardList(null);

  $.each(reviewScorecards,function(){
   if(this.id === mainWidget.softwareCompetition.projectHeader.reviewScorecardId || this.id === mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId){
     $('#rReviewScorecard').text(this.scorecardName + " " + this.scorecardVersion);
   }
  });

	$('#rContestName').text(mainWidget.softwareCompetition.assetDTO.name);
  $('#rChallengeCreator').text(mainWidget.softwareCompetition.projectHeader.challengeCreator);

	$('#rCCA').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
	if (mainWidget.softwareCompetition.projectHeader.tcDirectProjectName != null) {
		$('#rProjectName').text(mainWidget.softwareCompetition.projectHeader.tcDirectProjectName);
	}

    if (isF2F() || isDesignF2F()) {
        var privateProject = p[TASK_FLAG];
        var registrants = [];
        for (var i=0; i < mainWidget.softwareCompetition.registrants.length; i++) {
            registrants.push(mainWidget.softwareCompetition.registrants[i]["name"]);
        }
        var preRegisterUsers = registrants.join(",");

        $(".privateProjectRow").show();
        $("#privateProjectEditDiv").show();
        if (privateProject === "1") {
            $("#rPrivateProject").text("Yes");
            $("#privateProject").attr("checked", "checked");
            $(".preRegisterUsersDiv").show();
            $("#preRegisterUsersEditDiv").show();
            $("#rPreRegisterUsers").text(preRegisterUsers);
            jQuery_1_11_1("#preRegisterUsers").magicSuggest().setValue(mainWidget.softwareCompetition.registrants);
        }else{
            $("#rPrivateProject").text("No");
            $("#privateProject").attr("checked", false);
            $(".preRegisterUsersDiv").hide();
            $("#preRegisterUsersEditDiv").hide();
        }
        if (mainWidget.softwareCompetition.projectHeader.projectStatus.name == ACTIVE_STATUS) {
            $("#privateProject").attr("disabled", "true");
        }
    } else {
        $(".privateProjectRow").hide();
        $("#privateProjectEditDiv").hide();
        $("#preRegisterUsersEditDiv").hide();
    }

    if (mainWidget.softwareCompetition.projectMilestoneId > 0) {
        $('#rProjectMilestone').text(mainWidget.softwareCompetition.projectMilestoneName);
    } else {
        $('#rProjectMilestone').text('');
    }

	//$('#rAdminFee').html(parseFloat(mainWidget.softwareCompetition.adminFee).formatMoney(2));

  if (mainWidget.softwareCompetition.copilotUserId <=0) {
  	 $("#rCopilots").text("Unassigned");
  } else {
  	 $("#rCopilots").html('<a href=https://' + SERVER_CONFIG_SERVER_NAME + '/tc?module=MemberProfile&cr=' + mainWidget.softwareCompetition.copilotUserId + '>' + mainWidget.softwareCompetition.copilotUserName + '</a>');
  }

    // set copilot selection value in edit mode
       $("#copilots").val(mainWidget.softwareCompetition.copilotUserId);

    // set milestone id in edit mode
       $("#milestones").val(mainWidget.softwareCompetition.projectMilestoneId);

  //billing account
  var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
  $('#billingProjects').getSetSSValue(billingProjectId);

  if (contestPercentage!= null && contestPercentage > 0) {
      var total = 0;
      var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
      for (var i = 0; i < prizes.length; i++) {
          total += prizes[i].prizeAmount * prizes[i].numberOfSubmissions;
      }

      total += parseFloat((isNaN(mainWidget.softwareCompetition.projectHeader.getReviewCost()) ? 0 : mainWidget.softwareCompetition.projectHeader.getReviewCost()));
      total += parseFloat((isNaN(mainWidget.softwareCompetition.projectHeader.getSpecReviewCost()) ? 0 : mainWidget.softwareCompetition.projectHeader.getSpecReviewCost()));
      total += parseFloat((isNaN(mainWidget.softwareCompetition.projectHeader.getDRPoints())? 0 : mainWidget.softwareCompetition.projectHeader.getDRPoints()));
      var contestFee = (total + (isNaN(mainWidget.softwareCompetition.copilotCost)?0:mainWidget.softwareCompetition.copilotCost)) * contestPercentage;
      $('#rAdminFee').html(parseFloat(contestFee).formatMoney(2) + ' (' + (contestPercentage * 100).toFixed(2) + '% markup)');
  } else {
      $('#rAdminFee').html(parseFloat(adminFee).formatMoney(2));
  }
  //potential rollback
	// $('#billingProjects').trigger("change");
	if(isBillingEditable()) {
		$('#billingAccountDivEdit').show();
  } else {
  	$('#billingAccountDivEdit').hide();
  }

    if (isBillingViewable()) {
        $('.billingdisplay').show();
        $('#rBillingAccount').html((billingProjectId <= 0) ? "&nbsp;" : $("#billingProjects option[value=" + billingProjectId + "]").text());
    } else {
        $('.billingdisplay').hide();
    }

/*
    if (mainWidget.softwareCompetition.projectHeader.properties['CloudSpokes CMC Task']) {
        $('#rCMCTaskID').text(mainWidget.softwareCompetition.projectHeader.properties['CloudSpokes CMC Task']);
        $('input[name=CMCTaskID]').val(mainWidget.softwareCompetition.projectHeader.properties['CloudSpokes CMC Task']);
        $(".cmcTask").show();
    }
*/
    if (mainWidget.softwareCompetition.projectHeader.properties.hasOwnProperty('Marathon Match Id')) {
        $('#rMatchRoundId').text(mainWidget.softwareCompetition.projectHeader.properties['Marathon Match Id']);
        $('input[name=MatchRoundID]').val(mainWidget.softwareCompetition.projectHeader.properties['Marathon Match Id']);
        $(".matchRoundId").show();
    }

    var effortDaysEstimateOffshore = p[ESTIMATE_EFFORT_OFFSHORE] || '';
    var effortDaysEstimateOnsite = p[ESTIMATE_EFFORT_ONSITE] || '';
    $('#rEffortDaysEstimateOffshore').text(effortDaysEstimateOffshore);
    $('#rEffortDaysEstimateOnsite').text(effortDaysEstimateOnsite);
    $('input[name=effortDaysEstimateOffshore]').val(effortDaysEstimateOffshore);
    $('input[name=effortDaysEstimateOnsite]').val(effortDaysEstimateOnsite);

    if (isNaN(p[ESTIMATE_EFFORT_OFFSHORE]) && isNaN(p[ESTIMATE_EFFORT_ONSITE]) ) {
        $('.effortEstimateRow').hide();
    } else {
      $('.effortEstimateRow').show();
    }

    if (mainWidget.softwareCompetition.projectHeader.properties.hasOwnProperty(MM_TYPE)) {
        $('#rMMType').text(mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE]);
        $('#mmType').getSetSSValue(mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE]);
    }

    // populate review style
    if (mainWidget.softwareCompetition.projectHeader.properties['Review Type'] && mainWidget.competitionType == "SOFTWARE") {
        var reviewType = mainWidget.softwareCompetition.projectHeader.properties['Review Type'];
        if(reviewType == 'INTERNAL') {
            $("#rReviewStyle").text("Internal Review");
            $('input[value="internal"]').attr('checked', 'checked');
        } else if(reviewType == 'COMMUNITY') {
            $("#rReviewStyle").text("TopCoder Community Review Board");
            $('input[value="community"]').attr('checked', 'checked');
        } else {
            $("#rReviewStyle").text(reviewType);
        }


    }

    $('#rswGroups').html(mainWidget.softwareCompetition.groupNames.join(", "));
}

/**
 * Update the editors to support or not support the placeholder function.
 * @since 1.7
 */
function updateMCEPlaceHolderCtl() {
    if (mainWidget.softwareCompetition.projectHeader.isLccchecked()) {
        enableMCEPlaceholderText = true;
        $(['contestDescription', 'round1Info', 'round2Info']).each(function() {
            var obj = CKEDITOR.instances[this];
            if (obj.getData() == "") {
                obj.setData("Only members that register for this challenge will see this description.");
            }
       });
     } else {
        $(['contestDescription', 'round1Info', 'round2Info']).each(function() {
            var obj = CKEDITOR.instances[this];
            if (obj.getData() == "") {
                obj.setData("");
            }
       });
       enableMCEPlaceholderText = false;
     }
}

function saveTypeSection() {
    if (!validateFieldsTypeSection()) {
        return;
    }

    var saveDraftHandler = function() {
        if (mainWidget.competitionType != "SOFTWARE") {
            if (mainWidget.softwareCompetition.projectHeader.isLccchecked()) {
                $("#viewableSubmFlag").attr("disabled", "disabled");
                $("#viewableSubmFlag").attr("checked", "");
                mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] = 'false';
            } else {
                $("#viewableSubmFlag").attr("disabled", "");
            }
            populateSpecSection();
        }

        //construct request data
        fixFileTypeIds();
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                mainWidget.softwareCompetition.groups = jQuery_1_11_1("#groups").magicSuggest().getValue();
                mainWidget.softwareCompetition.groupNames=[];
                $.each(jQuery_1_11_1("#groups").magicSuggest().getSelection(), function(i, val){
                    mainWidget.softwareCompetition.groupNames.push(val.name);
                })
                mainWidget.softwareCompetition.registrants = jQuery_1_11_1("#preRegisterUsers").magicSuggest().getSelection().slice();
                populateTypeSection();
                populateRoundSection();
                if (mainWidget.competitionType == "SOFTWARE") {
                    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
                    if (billingFeesPercentage[billingProjectId] != null && billingFeesPercentage[billingProjectId].contestFeePercentage != null) {
                        populatePrizeSection();
                    }
                }

                if (mainWidget.competitionType == "ALGORITHM") {
                    populatePrizeSection();
                }
                if (!hasGroupSelected()) {
                  mainWidget.softwareCompetition.projectHeader.points = [];
                }
                showPointSectionDisplay();

                showTypeSectionDisplay();
                updateMCEPlaceHolderCtl();
            },
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    };


    if(showSaveChallengeConfirmation == false) {
        saveDraftHandler();
    } else {
        showChallengeSaveConfiguration(function() {
            closeModal();
            saveDraftHandler();
        });
    }
}

function validateFieldsTypeSection() {
    var categoryId = getContestType(true)[1];
    var contestName = $('input#contestName').val();
    var tcProjectId = parseInt($('select#projects').val());
    var copilotUserId = parseInt($('select#copilots').val());
    var copilotName = $('select#copilots option:selected').text();
    var milestoneId = parseInt($('select#milestones').val());

    //validation
    var errors = [];

    validateContestName(contestName, errors);
    validateTcProject(tcProjectId, errors);
    if ($("input[name=MatchRoundID]").length > 0 && $.trim($("input[name=MatchRoundID]").val()).length > 0) {
        if(!isPositiveIntegerInput($("input[name=MatchRoundID]").val())) {
            errors.push("The Marathon Match Round ID should be positive integer");
        }
    }

    // validate effort hours estimate
    validateEffortDaysEstimate(errors);

    // do NOT need milestone for First2Finish and CODE contest
    if (categoryId != SOFTWARE_CATEGORY_ID_F2F
        && categoryId != SOFTWARE_CATEGORY_ID_CODE
        && categoryId != STUDIO_CATEGORY_ID_DESIGN_F2F
        && categoryId != SOFTWARE_CATEGORY_ID_AUTOMATE) {
        validateDirectProjectMilestone(milestoneId, errors);
    }
    if (categoryId == ALGORITHM_CATEGORY_ID_MARATHON && $('#mmType').val() === '0') {
        errors.push('Marathon match type is required');
    }
    if (errors.length > 0) {
        showErrors(errors);
        return false;
    }
    var projectCategory = getProjectCategoryById(categoryId);
    if ($('input#chkboxCCA').attr('checked')) {
        mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
    } else {
        mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    }
    mainWidget.softwareCompetition.projectHeader.projectCategory = {};
    mainWidget.softwareCompetition.projectHeader.projectCategory.id = projectCategory.id;
    mainWidget.softwareCompetition.projectHeader.projectCategory.name = projectCategory.name;
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType = {};
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = projectCategory.typeId;
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = projectCategory.typeName;

    mainWidget.softwareCompetition.assetDTO.name = contestName;
    mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);

    // set the copilot user id and user name
    mainWidget.softwareCompetition.copilotUserId = copilotUserId;
    mainWidget.softwareCompetition.copilotUserName = copilotName;

    // set the milestone id
    mainWidget.softwareCompetition.projectMilestoneId = milestoneId;

    mainWidget.softwareCompetition.projectHeader.tcDirectProjectId = tcProjectId;
    mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = $('select#projects option[value=' + tcProjectId + ']').html()
    if (!hasMultiRound(mainWidget.softwareCompetition.projectHeader.projectCategory.id)) {
        mainWidget.softwareCompetition.multiRound = false;
    }

    if (isBillingEditable()) {
        var billingProjectId = $('select#billingProjects').val();
        mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
    }

    if (categoryId == SOFTWARE_CATEGORY_ID_F2F) {
        // set review scorecard.
        mainWidget.softwareCompetition.projectHeader.iterativeReviewScorecardId = parseInt($('select#reviewScorecardSelects').val());
    } else if (categoryId != ALGORITHM_CATEGORY_ID_MARATHON){
        // set iterative review scorecard
        mainWidget.softwareCompetition.projectHeader.reviewScorecardId = parseInt($('select#reviewScorecardSelects').val());
    }

    if (categoryId == ALGORITHM_CATEGORY_ID_MARATHON) {
        mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE] = $('#mmType').val().trim();
    }

    return true;
}

function showTypeSectionDisplay() {
	 $(".contest_type").css("display","block");
	 $(".contest_type_edit").css("display","none");
}

function showTypeSectionEdit() {
     $("#reviewerEditDiv").hide();
     $("#reviewTypeEditDiv").hide();

	 $(".contest_type").css("display","none");
	 $(".contest_type_edit").css("display","block");
	 if(!$("#billingProjects").data('customized')) {
		 $("#billingProjects").data('customized',true);
	     $('#billingProjects').sSelect({ddMaxHeight: '220',yscroll: true});
	 }
	 $("#billingProjects").getSetSSValue(mainWidget.softwareCompetition.projectHeader.getBillingProject());
	 if(!$(".selectDesing select").data('customized')) {
		$(".selectDesing select").data('customized',true);
      	$(".selectDesing select").sSelect();
		$('.selectDesing div.selectedTxt').html('Select Challenge Type');
    }

     $.each(billingAccounts,function(k, v) {
    	 var _cca = v["cca"] == "true" ? true : false;
       var _enableEffortDays = v["enableEffortDays"] == "true" ? true : false;
  	   	if (v["id"] == mainWidget.softwareCompetition.projectHeader.getBillingProject()) {
  		   if (_cca) {
  			   $("#chkboxCCA").attr('disabled','true');
  		   }
         if(_enableEffortDays) {
           $(".effortEstimateRow").show();
         }
  	   }
     });

    if(!$('#projects').data('customized')) {
        $('#projects').sSelect({ddMaxHeight: '220',yscroll: true}).change(function() {
              handleProjectDropDownChange();
          });
        $('#projects').data('customized',true);
    }

    if(!$('#copilots').data('customized')) {
       $('#copilots').sSelect({ddMaxHeight: '220',yscroll: true});
       $('#copilots').data('customized',true);
    }

    if(!$('#milestones').data('customized')) {
        $('#milestones').sSelect({ddMaxHeight: '220',yscroll: true});
        $('#milestones').data('customized',true);
    }

    if(!$('#reviewer').data('customized')) {
        $('#reviewer').sSelect({ddMaxHeight: '220',yscroll: true});
        $('#reviewer').data('customized',true);
    }

    if(!$('#mmType').data('customized')) {
            $('#mmType').sSelect({ddMaxHeight: '220',yscroll: true});
            $('#mmType').data('customized',true);
    }

	 $('#contestTypes').getSetSSValue(mainWidget.competitionType + mainWidget.softwareCompetition.projectHeader.projectCategory.id);
    var projectType = mainWidget.competitionType;
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
    setupReviewerDropdown(projectCategoryId, mainWidget.softwareCompetition.projectHeader.tcDirectProjectId);
}

/**
 * Gets the milstone prizes as array. The first element is the prize for each winner, the second element is
 * the number of the checkpoint submissions.
 */
function getCheckpointPrizes() {
	var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
	for (var i = 0; i < prizes.length; i++) {
		if (prizes[i].prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
			return [prizes[i].prizeAmount, prizes[i].numberOfSubmissions];
		}
	}
	return [0, 0];
}

function getDurationDayHour(start, end) {
	var t = end.getTime() - start.getTime() + 5 * 60 * 1000; // add 5 mintues due to diff between reg and sub
	t = parseInt(t / 1000 / 60 / 60);
	return [parseInt(t / 24), t % 24];
}

/**
 * Round/Schedule Section Functions
 */
function populateRoundSection() {
	var startDate = mainWidget.softwareCompetition.assetDTO.directjsProductionDate;
	var endDate = mainWidget.softwareCompetition.endDate;
	var subEndDate = mainWidget.softwareCompetition.subEndDate;
  var regEndDate = mainWidget.softwareCompetition.regEndDate;
	var isMultiRound = mainWidget.softwareCompetition.multiRound;
	var subDuration = getDurationDayHour(startDate, subEndDate);
  var regDuration = getDurationDayHour(startDate, regEndDate);
	if (isMultiRound) {
		var checkpointDate = mainWidget.softwareCompetition.checkpointDate;
		var checkpointDuration = getDurationDayHour(startDate, checkpointDate);
		subDuration = getDurationDayHour(checkpointDate, subEndDate);
	}

	//edit
    $('#roundTypes').val(isMultiRound ? 'multi' : 'single');
    $('#roundTypes').trigger("change");
    $('#startDate').datePicker().val(getDatePart(startDate)).trigger('change');
    $('#startTime').val(getRoundedTime(startDate));

    $('#endDateDay').val(subDuration[0]).trigger('change');
    $('#endDateHour').val(subDuration[1]).trigger('change');
    $('#regEndDateDay').val(regDuration[0]).trigger('change');
    $('#regEndDateHour').val(regDuration[1]).trigger('change');

    if (mainWidget.isAlgorithmContest()) {
        $('#regEndDate').datePicker().val(getDatePart(regEndDate)).trigger('change');
        $('#regEndTime').val(getRoundedTime(regEndDate));
        $('#endDate').datePicker().val(getDatePart(endDate)).trigger('change');
        $('#endTime').val(getRoundedTime(endDate));
    }
    if (mainWidget.isSoftwareContest() || isDesignF2F()) {
        $('#regEndDate').datePicker().val(getDatePart(regEndDate)).trigger('change');
        $('#regEndTime').val(getRoundedTime(regEndDate));

        // set end date to submission end date
        $('#endDate').datePicker().val(getDatePart(subEndDate)).trigger('change');
        $('#endTime').val(getRoundedTime(subEndDate));
        if (isMultiRound) {
            // for multiple round software contest, set checkpoint date / time
            var checkpointDate = mainWidget.softwareCompetition.checkpointDate;
            $('#checkPointEndDate').datePicker().val(getDatePart(checkpointDate)).trigger('change');
            $('#checkPointEndTime').val(getRoundedTime(checkpointDate));
        }
    }

    if (isDesignF2F()) {
        $('#regEndEditDiv').hide();
        $("#endEditDiv").hide();
        $('.row.designF2F').show();
    } else {
        $('.row.designF2F').hide();
        $('#regEndEditDiv').show();
    }


	if (!hasMultiRound(mainWidget.softwareCompetition.projectHeader.projectCategory.id) || !canEditMultiRound) {
		$('#type').hide();
   	} else {
		$('#type').show();
	}
	if(!isMultiRound) {
    	   $('#checkpointEditDiv').hide();

	    $('#roundInfoDiv').hide();
	} else {
		$('#checkpointEditDiv').show();
		$('#checkpointDateDay').val(checkpointDuration[0]).trigger('change');
		$('#checkpointDateHour').val(checkpointDuration[1]).trigger('change');

		$('#roundInfoDiv').show();
		if (mainWidget.competitionType == "STUDIO") {
			$('#round1Info').val(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction);
			$('#round2Info').val(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction);
		}
	}

	//display
    var startDateString = formatDateForReview(startDate);
    $('#rStartDate').html(startDateString);
    $('#rStartDateRO').html(startDateString);
    $('table.projectStats td:eq(0)').html(startDateString);
    var endDateString = formatDateForReview(endDate);
    $('#rEndDate').html(endDateString);
    $('#rEndDateRO').html(endDateString);
    $('table.projectStats td:eq(1)').html(endDateString);

    $('#rRegEndDate').html(formatDateForReview(regEndDate));
    $('#rRegEndDateRO').html(formatDateForReview(regEndDate));
  	$('#rSubEndDate').html(formatDateForReview(subEndDate));
  	$('#rSubEndDateRO').html(formatDateForReview(subEndDate));

    if(!isMultiRound) {
    	$('#rCheckpointTR').hide();
        $('#rMultiRoundInfoDiv').hide();
  	} else {
  		$('#rCheckpointTR').show();
  	 	$('#rCheckpointDate').html(formatDateForReview(checkpointDate));
  	 	$('#rCheckpointDateRO').html(formatDateForReview(checkpointDate));

        $('#rMultiRoundInfoDiv').show();
        if (mainWidget.competitionType == "STUDIO") {
        	$('#rRound1Info').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction);
        	$('#rRound2Info').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction);
        }
  	}
}

function saveRoundSection() {
    if (!validateFieldsRoundSection()) {
        return;
    }

    var saveDraftHandler = function () {
        var preStartDate = mainWidget.softwareCompetition.assetDTO.directjsProductionDate;
        var preRegEndDate = mainWidget.softwareCompetition.regEndDate;
        var preSubEndDate = mainWidget.softwareCompetition.subEndDate;
        var preCheckpointDate = mainWidget.softwareCompetition.checkpointDate;

        //construct request data
        fixFileTypeIds();
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                if (jsonResult.error) {
                    mainWidget.softwareCompetition.assetDTO.directjsProductionDate = preStartDate;
                    mainWidget.softwareCompetition.regEndDate = preRegEndDate;
                    mainWidget.softwareCompetition.subEndDate = preSubEndDate;
                    mainWidget.softwareCompetition.checkpointDate = preCheckpointDate;
                }
                populateRoundSection();
                populatePrizeSection();
                showRoundSectionDisplay();
            },
            beforeSend: beforeAjax,
            complete: afterAjax
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

/**
 * Gets the index of checkpoint type prize from a list of prizes.
 *
 * @param prizes the list of prizes
 * @returns {Number} the index of checkpoint type prize
 */
function getCheckpointPrize(prizes) {
	for (var i = 0; i < prizes.length; i++) {
		if (prizes[i].prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
			return i;
		}
	}
	return -1;
}

/**
 * Gets the content of a CKEditor.
 *
 * @param id the id of CKEditor
 * @returns the content of the CKEditor
 */
function getCKEditorContent(id) {
	var obj = CKEDITOR.instances[id];
	if (!obj) {
		return "";
	}
	return obj.getData();
}

function validateFieldsRoundSection() {
	var isMultiRound = ('multi' == $('#roundTypes').val());

	var startDate = getDateByIdPrefix('start');
  var regEndDate = getDateByIdPrefix('regEnd');
  var subEndDate = getDateByIdPrefix('end');

  // check point date/time for software
  var checkPointDate = getDateByIdPrefix('checkPointEnd');

  var regEndDateHours = $('#regEndDateDay').val() * 24 + parseInt($("#regEndDateHour").val());

  // check point hours duration for studio
	var checkpointDateHours = $('#checkpointDateDay').val() * 24 + parseInt($('#checkpointDateHour').val());

	var round1Info = getCKEditorContent('round1Info');
	var round2Info = getCKEditorContent('round2Info');

	//validation
	var errors = [];

    if (startDate < getServerTime()) {
        // errors.push('Start Date must be in future.');
    }

    // validate the dates
    if (isMultiRound) {
        // multiple round
		  if (mainWidget.isStudioContest() && checkpointDateHours == 0) {
            // check if studio has positive round 1 duration set
			   errors.push('Round 1 duration should be positive.');
		  } else if (mainWidget.isSoftwareContest()) {
        // software contest - check if start date > checkpoint date > sub end date
        if (checkPointDate.getTime() <= startDate.getTime()) {
          errors.push('Checkpoint end date/time should be larger than Start date/time.');
        }
        if (subEndDate.getTime() <= checkPointDate) {
          errors.push('End date/time should be larger than Checkpoint date/time.');
        }
      }
    } else {
        // single round - check for software and algorithm contest
        if (mainWidget.isSoftwareContest() || mainWidget.isAlgorithmContest() || isDesignF2F()) {
            if (subEndDate.getTime() <= startDate.getTime()) {
                errors.push('End date/time should be larger than Start date/time.');
            }
        }
    }

    if (mainWidget.isSoftwareContest() || isDesignF2F() || mainWidget.isAlgorithmContest()) {
      if (regEndDate.getTime() <= startDate.getTime()) {
        errors.push('Registration end date/time should be larger than Start date/time.');
      }

      if (regEndDate.getTime() > subEndDate.getTime()) {
        errors.push('Registration end date/time should be less than or equal to Submission end date/time.');
      }
    }


  var subEndDateHours = $('#endDateDay').val() * 24 + parseInt($('#endDateHour').val());
	if (mainWidget.competitionType == "STUDIO" && !isDesignF2F()) {

		if (subEndDateHours == 0) {
		   if (isMultiRound) {
			   errors.push('Round 2 duration should be positive.');
		   } else {
			   errors.push('Round 1 duration should be positive.');
		   }
		}
	}

	if(isMultiRound  && !isDesignF2F()) {
		if (mainWidget.competitionType == "STUDIO") {
			if(!checkRequired(round1Info)) {
				errors.push('Round 1 information is empty.');
			}

			if(!checkRequired(round2Info)) {
				errors.push('Round 2 information is empty.');
			}

      if (regEndDateHours > checkpointDateHours + subEndDateHours) {
        errors.push('Registration length should be less than or equal to the sum of Round 1 and 2 Durations.');
      }


		}
	} else if (mainWidget.competitionType == "STUDIO" && !isDesignF2F()) {
        // set check point date hours for studio to 0 for single round contest
		checkpointDateHours = 0;

    if (regEndDateHours > subEndDateHours) {
      errors.push('Registration length should be less than or equal to the Round 1 Duration.');
    }
	}

	// check total payment
	/**if(getCurrentContestTotal(true) < mainWidget.softwareCompetition.paidFee) {
		errors.push('Your payment can not be lower than paid amount.');
	}*/

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   mainWidget.softwareCompetition.multiRound = isMultiRound;
   mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
   mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);
   mainWidget.softwareCompetition.startDate = startDate;

   if(mainWidget.isStudioContest() && isMultiRound) {
       mainWidget.softwareCompetition.checkpointDate = new Date();
       mainWidget.softwareCompetition.checkpointDate.setTime(startDate.getTime() + checkpointDateHours * 60 * 60 * 1000);
   } else if(mainWidget.isSoftwareContest() && isMultiRound) {
       mainWidget.softwareCompetition.checkpointDate = checkPointDate;
   }


   if (mainWidget.isStudioContest() && !isDesignF2F()) {
	   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundOneIntroduction = round1Info;
	   mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.roundTwoIntroduction = round2Info;

     mainWidget.softwareCompetition.regEndDate = new Date();
     mainWidget.softwareCompetition.regEndDate.setTime(startDate.getTime() + (regEndDateHours) * 60 * 60 * 1000);

	   mainWidget.softwareCompetition.subEndDate = new Date();
	   mainWidget.softwareCompetition.subEndDate.setTime(startDate.getTime() + (checkpointDateHours + subEndDateHours) * 60 * 60 * 1000);
   }

   if (mainWidget.isSoftwareContest() || isDesignF2F() || mainWidget.isAlgorithmContest()) {
      mainWidget.softwareCompetition.regEndDate = getDateByIdPrefix('regEnd');
      mainWidget.softwareCompetition.subEndDate = getDateByIdPrefix('end');
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
	$(".roundelect select,.startEtSelect select,.checkpointEtSelect select,.numSelect select,.endEtSelect select").each(function(index) {
		if(!$(this).is(":hidden") && !$(this).data('customized')) {
			$(this).data('customized',true);
			$(this).sSelect({ddMaxHeight: '220',yscroll: true});
		}
	});
	if (!hasMultiRound(mainWidget.softwareCompetition.projectHeader.projectCategory.id) || !canEditMultiRound) {
		$('#type').hide();
   	} else {
		$('#type').show();
	}
}


/**
 * Prize Section Functions
 */
function populatePrizeSection(initFlag) {
    if(mainWidget.softwareCompetition.multiRound) {
        $('#rCheckPointPrizeDiv').show();
        var checkpointPrizes = getCheckpointPrizes();
        $('#rMPrizesAmount').text('$'+checkpointPrizes[0].formatMoney(2));
        $('#rMPrizesNumberOfSubmissions').html(checkpointPrizes[1]);
        $('#swCheckpointPrize,#checkpointPrize').val(checkpointPrizes[0].formatMoney(2));
        $('#swCheckpointSubmissionNumber,#checkpointSubmissionNumber').val(checkpointPrizes[1]);
    } else {
        $('#rCheckPointPrizeDiv').hide();
    }

    //billing account
    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
    $('#billingProjects').getSetSSValue(billingProjectId);
    //potential rollback
    // $('#billingProjects').trigger("change");
    if (isBillingEditable()) {
        $('#billingAccountDivEdit').show();
    } else {
        $('#billingAccountDivEdit').hide();
    }

    if (isBillingViewable()) {
        $('.billingdisplay').show();
        $('#rBillingAccount').html((billingProjectId <= 0) ? "&nbsp;" : $("#billingProjects option[value=" + billingProjectId + "]").text());
    } else {
        $('.billingdisplay').hide();
    }

    if (mainWidget.competitionType == "STUDIO" || mainWidget.competitionType == "ALGORITHM") {
        var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
        var maxPlace = 0;
        for (var i = 1; i <= 5; i++) {
            $('#rPrize' + i).text('');
            $('#prize' + i).val('');
        }
        for (var i = 0; i < prizes.length; i++) {
            if (prizes[i].prizeType.id == CONTEST_PRIZE_TYPE_ID) {
                maxPlace = Math.max(maxPlace, prizes[i].place);
                $('#rPrize' + prizes[i].place).text('$' + prizes[i].prizeAmount.formatMoney(2));
                $('#prize' + prizes[i].place).val(prizes[i].prizeAmount);
            }
        }
        if (maxPlace > 2) {
            // have additional payments
            $('#extraPrizes').show();
        } else {
            $('#extraPrizes').hide();
        }


        if (!initFlag) {
            fillStudioPrizes();
        } else {
            updateContestCostData();
        }

        if(isDesignF2F()) {
            // hide non first prize in challenge details
            for(var i = 2; i <= 5; i++) {
                $('#rPrize' + i).parents("td:eq(0)").hide();
            }

            // hide non first prize input in challenge prizes edit
            $(".contest_prize_edit .prizesInner").children().hide();
            $(".contest_prize_edit .prizesInner").children(":lt(3)").show();

            // hide max submissions display in challenge details
            $("#rMaxSubmissions").parents("p:eq(0)").hide();

            // hide max submission edit in the challenge details
            $(".maxSubmissions").hide();
        }

    } else {
        //edit
        //display
        //set radio button
        var radioButtonValue = (COSTLEVEL_RADIOVALUE_MAP[mainWidget.softwareCompetition.projectHeader.getCostLevel()]) || "medium";
        $('input[name="prizeRadio"][value="' + radioButtonValue + '"]').attr("checked", "checked");

        // if init flag is true - open contest detail page - show actual cost data
        if (!initFlag) {
            fillPrizes();
        } else {
            updateContestCostData();
        }
    }
    // TODO: need to update getCurrentContestTotal to work with studio contest

    if (initFlag) {
        //show activate button if it needs to : the fee is not paid up fully
        if (mainWidget.softwareCompetition.projectHeader.projectStatus.name == DRAFT_STATUS) {
            $('#resubmit').show();
            $(".activateButton").show();
            startedContest = false;
        }
    }
    preCost = retrieveContestCostWithoutAdminFee();
}

/**
 * Show the contest cost data according to the actual contest cost returned from server.
 */
function updateContestCostData() {
    if (!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
        return;
    }

    var prizeType = $('input[name="prizeRadio"]:checked').val();
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
    var feeObject = softwareContestFees[projectCategoryId];
    if (!feeObject && mainWidget.competitionType != 'STUDIO' && mainWidget.competitionType != 'ALGORITHM') {
        showErrors('no fee found for project category ' + projectCategoryId);
        return;
    }

    // gets all cost data from contest data
    var p = mainWidget.softwareCompetition.projectHeader.properties;

    // use the prize data
    var firstPlacePrize = parseFloat(p['First Place Cost']);

    if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1) > 0) {
        firstPlacePrize = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1);
    }


    var secondPlacePrize = parseFloat(p['Second Place Cost']);

    if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2) > 0) {
        secondPlacePrize = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 2);
    }


    var reviewCost = parseFloat(p['Review Cost']);
    var reliability = parseFloat(p['Reliability Bonus Cost']);
    var specReview = parseFloat(p['Spec Review Cost']);
    var digitalRunFlag = p['Digital Run Flag'];

    var digitalRun = parseFloat(p['DR points']);

    if(digitalRunFlag != 'On' || isNaN(digitalRun)) {
        // if DR flag is not On or digital run value does not exist, set to 0
        digitalRun = 0;
    }

    var contestFee = parseFloat(p['Admin Fee']);
    var contestPercentage = parseFloat(p['Contest Fee Percentage']);
    var copilotFee = parseFloat(mainWidget.softwareCompetition.copilotCost);

    // update to use contest data
    //copilotFee = parseFloat(p['Copilot Cost']);

    var isMultipleRound = mainWidget.softwareCompetition.multiRound;
    // no prize data filled into mainWidget.softwareCompetition
    var domOnly = mainWidget.softwareCompetition.projectHeader.id < 0;

    // (1) set the first place prize
    $('#swFirstPlace').val(firstPlacePrize.formatMoney(2));
    $('#rswFirstPlace').html(firstPlacePrize.formatMoney(2));

    // (2) set the second place prize
    $('#swSecondPlace,#rswSecondPlace').html(secondPlacePrize.formatMoney(2));

    // (3) set the review cost
    $('#swReviewCost,#rswReviewCost').html(reviewCost.formatMoney(2));

    // (4) set the reliability
    $('#swReliabilityBonus,#rswReliabilityBonus').html(reliability.formatMoney(2));

    // (5) set the digital run
    $('#rswDigitalRun').html(digitalRun.formatMoney(2));
    $('#swDigitalRun').val(digitalRun.formatMoney(2));

    if(digitalRunFlag != 'On') {
        $('#swDigitalRun').attr('disabled', 'disabled');
        $('#DRCheckbox').removeAttr('checked');
        $('#studioCupPointsCheckBox').removeAttr('checked');

    } else {
        $('#swDigitalRun').removeAttr('disabled');
        $('#DRCheckbox').attr('checked', 'checked');
        $('#studioCupPointsCheckBox').attr('checked', 'checked');
    }

    originalPrizes = {};
    var prizes = [];
    prizes.push(firstPlacePrize + '');

    if(isCode()) {
        // push multiple prizes data for code contest type
        var prizesData = mainWidget.softwareCompetition.projectHeader.prizes;
        for(var k = 1; k < prizesData.length; ++k) {
            // start from second place cost
            var ep = prizesData[k].prizeAmount;
            prizes.push(prizesData[k]);
            $("td.extraPrize:eq(" + (k-1) +")").show().find("span").text(ep.formatMoney(2));
        }
    }

    originalPrizes.prizes = prizes;
    originalPrizes.digitalRun = digitalRun;

    // checkpoint prize and extra prize
    var checkpointPrize = 0;
    var extraPrize = 0;
    $.each(mainWidget.softwareCompetition.projectHeader.prizes, function(i, prize) {
        if(prize.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
            checkpointPrize += prize.numberOfSubmissions * prize.prizeAmount;
        }
        if(prize.prizeType.id == CONTEST_PRIZE_TYPE_ID && prize.place > 2) {
            // sum extra prize
            extraPrize += prize.numberOfSubmissions * prize.prizeAmount;
        }
    });

    // (6) set the contest fee
    if (contestPercentage!= null && contestPercentage > 0) {
        // calculate the percentage fee
        var actualFee = (getContestTotal(feeObject, prizeType, domOnly, !isMultipleRound, 0) + copilotFee) * contestPercentage;

        if(mainWidget.competitionType == 'STUDIO' || mainWidget.competitionType == 'ALGORITHM') {
            actualFee = (firstPlacePrize + (secondPlacePrize || 0) + extraPrize + checkpointPrize + reviewCost + (reliability || 0) + specReview + (digitalRun || 0) + copilotFee) * contestPercentage;
        }

       $('#rswContestFee').html(actualFee.formatMoney(2) + ' (' + (contestPercentage * 100).toFixed(2) + '% markup)');
       $('#swContestFee').html(actualFee.formatMoney(2));
       $("#swContestFeePercentage").text(' (' + (contestPercentage * 100).toFixed(2) + '% markup)');
       if(actualFee != contestFee) {
           // this can be commented out for debug the contest fee consistency
           // alert('DEBUG:not matched -> calculated fee:' + actualFee + " fee value in project info:" + contestFee);
           contestFee = actualFee;
       }

    } else {
        // the fixed fee
        $('#swContestFee,#rswContestFee').html(contestFee.formatMoney(2));
        $("#swContestFeePercentage").text('');
    }

    // (7) set the copilot cost
    $('#rswCopilotFee').html(copilotFee);
    $('.copilotFee').val(copilotFee);
    if($("#copilots").val()>0){
        $(".copilotFee").attr("disabled", false);
    }else{
        $(".copilotFee").attr("disabled", true);
    }

    // (8) set the spec review cost
    $('#swSpecCost,#rswSpecCost').html(specReview.formatMoney(2));

    var total = firstPlacePrize + (secondPlacePrize || 0) + reviewCost + (reliability || 0) + specReview + (digitalRun || 0) + contestFee + copilotFee;

    // add checkpoint prize and extra prizes if there is any
    $.each(mainWidget.softwareCompetition.projectHeader.prizes, function(i, prize) {
        if(prize.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
            total += prize.numberOfSubmissions * prize.prizeAmount;
        }
        if(prize.prizeType.id == CONTEST_PRIZE_TYPE_ID && prize.place > 2) {
            // sum extra prize
            total += prize.numberOfSubmissions * prize.prizeAmount;
        }
    });

    $('#swTotal,#rswTotal').html(total.formatMoney(2));

    //totals
    if (mainWidget.competitionType != 'STUDIO' && mainWidget.competitionType != 'ALGORITHM') {
        $('#swPrize_low').html((getContestTotal(feeObject, 'low') + copilotFee).formatMoney(2));
        $('#swPrize_medium').html((getContestTotal(feeObject, 'medium') + copilotFee).formatMoney(2));
        $('#swPrize_high').html((getContestTotal(feeObject, 'high') + copilotFee).formatMoney(2));
    }

    //if custom, make the first place editable
    if (prizeType == 'custom') {
        $('#swFirstPlace').attr('readonly', false);
        $('#swFirstPlace').val(firstPlacePrize.formatMoney(2));

        $('#swDigitalRun').attr('readonly', false);
        $('#swDigitalRun').val(digitalRun.formatMoney(2));
//        originalPrizes = [];
//        originalPrizes.push(firstPlacePrize + '');
//        originalPrizes.push(digitalRun + '');
    } else {
        $('#swFirstPlace').attr('readonly', true);
        $('#swDigitalRun').attr('readonly', true);
    }
}

function isBillingViewable() {
	// billing can not be found, meaning is not eligible, don't show/edit
	// or not set yet, allow edit
	var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
	return billingProjectId <=0 || $("#billingProjects option[value="+ billingProjectId +"]").length == 1;
}

function isBillingEditable() {
	 var draft = (DRAFT_STATUS == mainWidget.softwareCompetition.projectHeader.projectStatus.name);
	 return isBillingViewable() && draft;
}

function savePrizeSection() {
    if (!validateFieldsPrizeSection()) {
        return;
    }

    var saveDraftHandler = function () {
        //construct request data
        fixFileTypeIds();
        var request = saveAsDraftRequest();

        if (startedContest) {
            var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();

            if (billingProjectId <= 0) {
                showErrors("no billing project is selected.");
                return;
            }
            request['activationFlag'] = true;
        }

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                populatePrizeSection();
                //populateTypeSection();
                showPrizeSectionDisplay();
                if (mainWidget.competitionType == "STUDIO" || mainWidget.competitionType == "ALGORITHM") {
                    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
                    if (billingFeesPercentage[billingProjectId] != null && billingFeesPercentage[billingProjectId].contestFeePercentage != null) {
                        populateTypeSection();
                    }
                }
            },
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    };

    var confirmPrizeSave = function () {
        if (isPrizeDecreased()) {
            showConfirmation("Decreased Prize", "Are you sure you want to decrease the prize?", "OK", function () {
                saveDraftHandler();
                closeModal();
            });
        } else {
            saveDraftHandler();
        }
    }

    if (showSaveChallengeConfirmation == false) {
        confirmPrizeSave();
    } else {
        showChallengeSaveConfiguration(function () {
            closeModal();
            confirmPrizeSave();
        });
    }
}

function isPrizeDecreased() {
	//checkpoint prize
    var isMultiRound = ('multi' == $('#roundTypes').val());
    var checkpointPrizeInput;
    var checkpointPrize;
	if (isMultiRound) {
        if (mainWidget.competitionType == "STUDIO") {
            checkpointPrizeInput = $('#checkpointPrize').val();
        } else {
            checkpointPrizeInput = $('#swCheckpointPrize').val();
        }
        checkpointPrize = parseFloat(checkpointPrizeInput);
        if (checkRequired(checkpointPrizeInput) && checkNumber(checkpointPrizeInput) && !isNaN(checkpointPrize)) {
            // If registration is already started then check that the checkpoint prize is decreased
            if (phaseOpen) {
                if (checkpointPrize < getCheckpointPrizes()[0]) {
                    return true;
                }
            }
        }
	}
	if (mainWidget.competitionType == "SOFTWARE") {
        if (disablePrizeAdjustment()) {
            var newFirstPlacePrize = $('#swFirstPlace').val();
            var newDigitalRun = $('#swDigitalRun').val();
            if (checkNumber(newFirstPlacePrize)) {
                if (checkNumber(originalPrizes.prizes[0])) {
                    if (parseFloat(newFirstPlacePrize) < parseFloat(originalPrizes.prizes[0])) {
                        return true;
                    }
                }
            }
        }
    }
	return false;
}

function validateFieldsPrizeSection() {
    //validation
    var errors = [];

    //checkpoint prize
    var isMultiRound = ('multi' == $('#roundTypes').val());
    var checkpointPrizeInput;
    var checkpointPrize;
    if (isMultiRound) {
        if (mainWidget.competitionType == "STUDIO") {
            checkpointPrizeInput = $('#checkpointPrize').val();
        } else {
            checkpointPrizeInput = $('#swCheckpointPrize').val();
        }
        checkpointPrize = parseFloat(checkpointPrizeInput);
        if (!checkRequired(checkpointPrizeInput) || !checkNumber(checkpointPrizeInput) || isNaN(checkpointPrize)) {
            errors.push('Checkpoint prize is invalid.');
        }

        if (mainWidget.competitionType == "STUDIO") {
            // set checkpoint prizes
            var find = false;
            var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
            if (!prizes) {
                prizes = [];
            }
            var newPrizes = [];
            for (var i = 0; i < prizes.length; i++) {
                newPrizes.push(new com.topcoder.direct.Prize(prizes[i].place, prizes[i].prizeAmount, prizes[i].prizeType.id, prizes[i].numberOfSubmissions));
            }
            prizes = newPrizes;
            var ind = getCheckpointPrize(prizes);
            if (ind == -1) {
                prizes.push(new com.topcoder.direct.Prize(1, checkpointPrize, CHECKPOINT_PRIZE_TYPE_ID, parseInt($('#checkpointSubmissionNumber').val())));
            } else {
                prizes[ind].prizeAmount = checkpointPrize;
                prizes[ind].numberOfSubmissions = parseInt($('#checkpointSubmissionNumber').val());
            }
        }
    } else if (mainWidget.competitionType == "STUDIO") {
        // remove the checkpoint prize
        var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
        var newPrizes = [];
        for (var i = 0; i < prizes.length; i++) {
            if (prizes[i].prizeType.id != CHECKPOINT_PRIZE_TYPE_ID) {
                newPrizes.push(prizes[i]);
            }
        }
        prizes = newPrizes;
    }
    if (mainWidget.competitionType == "STUDIO") {
        mainWidget.softwareCompetition.projectHeader.prizes = prizes;
    }

    if (mainWidget.competitionType == "SOFTWARE") {
        var prizeType = $('input[name="prizeRadio"]:checked').val();
        if (prizeType == 'custom') {
            var value = $('#swFirstPlace').val();
            if (!checkRequired(value) || !checkNumber(value)) {
                errors.push('First place value is invalid.');
            }
        }

        if (isCode()) {
            // validate multiple prizes settings for Code contest type
            validateCodePrizes(errors);
        }

        if (disablePrizeAdjustment()) {
            var newFirstPlacePrize = $('#swFirstPlace').val();
            var newDigitalRun = $('#swDigitalRun').val();
            if (checkNumber(newDigitalRun)) {
                if (checkNumber(originalPrizes.digitalRun)) {
                    if (parseFloat(newDigitalRun) < parseFloat(originalPrizes.digitalRun)) {
                        errors.push('The DR points can not be decreased');
                    }
                }
            }
        }
    } else {
        var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
        if (!prizes) {
            prizes = [];
        }
        var ind = getCheckpointPrize(prizes);
        // store checkpoint prizes first
        var checkpointPrize = null;
        if (ind != -1) {
            checkpointPrize = prizes[ind];
        }

        var prizes = validatePrizes(errors);
        var dr = 0;
        for (var i = 0, len = prizes.length; i < len; i++) {
            dr += prizes[i].prizeAmount;
        }
        if (!prizes) {
            prizes = [];
        }
        if (checkpointPrize) {
            dr += parseInt($('#checkpointSubmissionNumber').val()) * checkpointPrize.prizeAmount;
            prizes.push(checkpointPrize);
        }

        //save the DR points
        if($("#studioCupPointsCheckBox").is(":checked")) {
            mainWidget.softwareCompetition.projectHeader.properties['DR points'] = dr * 0.25;
            mainWidget.softwareCompetition.projectHeader.turnDRFlagOn();
        } else {
            mainWidget.softwareCompetition.projectHeader.properties['DR points'] = 0;
            mainWidget.softwareCompetition.projectHeader.turnDRFlagOff();
        }
    }

    if (isActiveContest && !mainWidget.softwareCompetition.isPrivateProject()) {
        var totalCostWithoutAdminFee = retrieveContestCostWithoutAdminFee();
        if (totalCostWithoutAdminFee < preCost) {
            errors.push('The cost of active challenge should not be decreased.');
        }
    }
    var error = validateCopilotFee($(".copilotFee").val());
    if (error){
        errors.push(error);
    }

    if (errors.length > 0) {
        showErrors(errors);
        return false;
    }

    if (isBillingEditable()) {
        var billingProjectId = $('select#billingProjects').val();
        mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
    }

    // add copilot cost into project header
    mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

    if (mainWidget.competitionType == "SOFTWARE") {
        updateSoftwarePrizes();
    } else {
        mainWidget.softwareCompetition.projectHeader.prizes = prizes;
    }

    return true;
}

/**
 * Populate point section.
 */
function populatePointSection() {
    var points = mainWidget.softwareCompetition.projectHeader.points;
    $('.contest_point td').hide();
    if (points && points.length > 0) {
        for (var i = 0; i < points.length; i++) {
            var td = $('.contest_point td:eq(' + (points[i].place - 1) + ')');
            td.show();
            td.find('span').text(points[i].prizeAmount);
        }
    } else {
        $('.contest_point td.na_tab').show();
    }

    if(isDesignF2F()) {
        $('.points .prizesInner').children().hide();
        $('.points .prizesInner').children(':lt(3)').show();
    } else if (mainWidget.isSoftwareContest()) {
        if (isF2F() || isBugHunt()) {
            $('.points .prizesInner').children().hide();
            $('.points .prizesInner').children(':lt(3)').show();
        } else if (!isCode()) {
            $('.points .prizesInner').children().hide();
            $('.points .prizesInner').children(':lt(6)').show();
        }
    }
}

/**
 * Show points section.
 */
function showPointSectionDisplay() {
  if (hasGroupSelected()) {
    $(".contest_point").show();
    $(".contest_point_edit").hide();
  } else {
    $(".contest_point").hide();
    $(".contest_point_edit").hide();
  }
  populatePointSection();
}

/**
 * Show edit points section.
 */
function showPointSectionEdit() {
  $('.pointsInput').val('');
  $('#extraPoints').hide();

	var points = mainWidget.softwareCompetition.projectHeader.points;
	originalPoints = points || [];

  if (points && points.length) {
      var maxPlace = -1;
      for (var i = 0; i < points.length; i++) {
          maxPlace = Math.max(maxPlace, points[i].place);
          var input = $('#point' + points[i].place);
          input.val(points[i].prizeAmount);
      }

      if (maxPlace > 3 || (isCode() && maxPlace > 2)) {
          $('.points .addPoint').hide();
          $('#extraPoints').show();
      }
  }

  $(".contest_point").hide();
	$(".contest_point_edit").show();
}

/**
 * Check whether point decreased.
 * @param pointInputs new point inputs
 * @returns true if point decreased; false otherwise
 */
function isPointDecreased(pointInputs) {
    if (disablePrizeAdjustment()) {
        if (pointInputs.length < originalPoints.length) {
            return true;
        } else {
            var n = Math.min(pointInputs.length, originalPoints.length);
            for (var i = 0; i < n; i++) {
                if (parseFloat(pointInputs[i].prizeAmount) < parseFloat(originalPoints[i].prizeAmount)) {
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * Save point section.
 */
function savePointSection() {
    var errors = [];
    var points = validatePoints(errors);
    if(errors.length > 0) {
       showErrors(errors);
       return false;
    }

    var saveDraftHandler = function () {
        //construct request data
        fixFileTypeIds();
        mainWidget.softwareCompetition.projectHeader.points = points;
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                showPointSectionDisplay();
            },
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    };

    var confirmPointSave = function () {
        if (isPointDecreased(points)) {
            showConfirmation("Decreased Point", "Are you sure you want to decrease the point?", "OK", function () {
                saveDraftHandler();
                closeModal();
            });
        } else {
            saveDraftHandler();
        }
    }

    if (showSaveChallengeConfirmation == false) {
        confirmPointSave();
    } else {
        showChallengeSaveConfiguration(function () {
            closeModal();
            confirmPointSave();
        });
    }
}

function showPrizeSectionDisplay() {
	$(".contest_prize").css("display","block");
	$(".contest_prize_edit").css("display","none");
}

function showPrizeSectionEdit() {

    if (mainWidget.competitionType == "SOFTWARE") {
        var p = mainWidget.softwareCompetition.projectHeader.properties;

        var firstPlacePrize = parseFloat(p['First Place Cost']);

        if(getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1) > 0) {
            firstPlacePrize = getContestPrize(mainWidget.softwareCompetition.projectHeader.prizes, 1);
        }


        var digitalRunFlag = p['Digital Run Flag'];
        var digitalRun = parseFloat(p['DR points']);

        if(digitalRunFlag != 'On' || isNaN(digitalRun)) {
            digitalRun = 0;
        }

        originalPrizes = {};
        var prizes = [];
        prizes.push(firstPlacePrize + '');

        if(isCode()) {
            // push multiple prizes data for code contest type
            var prizesData = mainWidget.softwareCompetition.projectHeader.prizes;
            for(var k = 1; k < prizesData.length; ++k) {
                // start from second place cost
                prizes.push(prizesData[k].prizeAmount);
            }
        }

        originalPrizes.prizes = prizes;
        originalPrizes.digitalRun = digitalRun;
    } else {
        originalPrizes = [];
        for (var i = 1; i <=5; i++) {
            var value = $('#prize' + i).val();
            if ($.trim(value).length > 0) {
                originalPrizes.push(value);
            }
        };
    }
    $(".contest_prize").css("display","none");
	$(".contest_prize_edit").css("display","block");
/*	if(!$(".prizeBillingSelect select").data('customized')){
		$(".prizeBillingSelect select").data('customized',true);
        $('.prizeBillingSelect select').sSelect({ddMaxHeight: '220',yscroll: true});
    }
	$('.prizeBillingSelect select').resetSS(); */
	$('.prizeBillingSelect select').resetSS();
    $('#billingProjects').bind("change", function() {
        if ($(this).find(":selected").data("cca")) {
            mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
            $("#chkboxCCA").attr('checked','true').attr('disabled','true');
            $('#rCCA').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
        }else{
            $("#chkboxCCA").removeAttr('disabled');
        }

        if($(this).find(":selected").data("enableEffortDays")) {
          $('.effortEstimateRow').show();
        } else {
          $('.effortEstimateRow').hide();
        }

        updateContestFee();
        updateBillingGroups();
    });
	$("#billingProjects").getSetSSValue(mainWidget.softwareCompetition.projectHeader.getBillingProject());

    if(mainWidget.competitionType == "STUDIO") {
        onTheFlyCalculateStudioCosts();
    }
    fillPrizes();
}

/**
 * Gets the default file types and other format file types from a list of file types.
 *
 * @param allFileTypes the list of file type.
 * @returns the default file types and other format file types
 */
function getSplitFileTypes(allFileTypes) {
	var defaultFileTypes = [];
	var otherFileTypes = [];
    var predefinedFileTypes = [];
	if (mainWidget.softwareCompetition.projectHeader.projectCategory.id > 0) {
		for (var i = 0; i < fileTypes.length; i++) {
			if (fileTypes[i].id == mainWidget.softwareCompetition.projectHeader.projectCategory.id) {
                predefinedFileTypes = fileTypes[i].fileFormats;
                for (var k = 0; k < allFileTypes.length; k++) {
                    var matched = false;
                    for (var j = 0; j < fileTypes[i].fileFormats.length; j++) {
                            if(fileTypes[i].fileFormats[j].description == allFileTypes[k] || fileTypes[i].fileFormats[j].value == allFileTypes[k]) {
                                defaultFileTypes.push(fileTypes[i].fileFormats[j]);
                                matched = true;
                                break;
                            }
                    }
                    if (!matched) {
                        otherFileTypes.push(allFileTypes[k]);
	                }
		        }
                break;
            }
		}
	}
	return [defaultFileTypes, otherFileTypes, predefinedFileTypes];
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
   var privateDescription = mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription;

	//edit
	$('#swDetailedRequirements').val(detailedRequirements);
	$('#swGuidelines').val(guidelines);
    $('#swPrivateDescription').val(privateDescription);
    if(isDevOrDesign()) {
       if(mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId != $('#catalogSelect').val() || initFlag) {
          $('#catalogSelect').val(mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId);
          updateCategories(fillCategories);
       } else {
       	  fillCategories();
       }
  }
  if(isTechnologyContest()) {
  	   //technlogies
      jQuery_1_11_1("#technologies").magicSuggest().setValue(mainWidget.softwareCompetition.assetDTO.directjsTechnologies);
      var technologyMap = {};
      var technologies = jQuery_1_11_1("#technologies").magicSuggest().getData();
      $.each(technologies, function(i, val){
          technologyMap[''+val.id]=val.name;
      });
      var selectedTechName = [];
      $.each(mainWidget.softwareCompetition.assetDTO.directjsTechnologies, function(i, val){
          selectedTechName.push(technologyMap[val]);
      });

      $('#rTechnologies').html(selectedTechName.join("<br/>"));
      technologyAndPlatformSelectsChanged();
  }
    if(isPlatformContest()) {
        //platforms
        jQuery_1_11_1("#platforms").magicSuggest().setValue(mainWidget.softwareCompetition.platforms);
        var platformMap = {};
        var platforms=jQuery_1_11_1("#platforms").magicSuggest().getData();
        $.each(platforms, function(i, val){
            platformMap[''+val.id]=val.name;
        });
        var selectedPlatformName = [];
        $.each(mainWidget.softwareCompetition.platforms, function(i, val){
            selectedPlatformName.push(platformMap[val]);
        });

        $('#rPlatforms').html(selectedPlatformName.join("<br/>"));
        technologyAndPlatformSelectsChanged();
    }

    if(mainWidget.softwareCompetition.projectHeader.properties['Thurgood Platform'] == 'Salesforce'
        || mainWidget.softwareCompetition.projectHeader.properties['Thurgood Language'] == 'Java') {
        $("#swThurgoodDiv input").attr("checked", "checked");
    } else {
        $("#swThurgoodDiv input").removeAttr("checked");
    }
  // for studio
  if (mainWidget.competitionType == "STUDIO") {
	  $('#contestIntroduction').val(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestIntroduction);
	  $('#contestDescription').val(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestDescription);
	  $('#deliverablesCheckboxs').html('');
	  var types = getSplitFileTypes(mainWidget.softwareCompetition.fileTypes);
	  // default types
      var predefinedFileTypes = types[2];
	  var html = "";

      $.each(predefinedFileTypes, function(i, type) {
          var selected = false;
          $.each(types[0], function(i, type1) {
              if(type1.description == type.description) {
                  selected = true;
              }
	      });

          if(selected) {
              html += '<div><input type="checkbox" checked="checked" value="' + type.value +'" class="defaultFileType" /> <label>' + type.description + '</label></div>';
          } else {
              html += '<div><input type="checkbox" value="' + type.value +'" class="defaultFileType" /> <label>' + type.description + '</label></div>';
          }
      });
	  // other file types
	  $.each(types[1], function(i, type) {
	      html += '<div><input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" value="' + type + '"/></div>';
	  });
	  $('#deliverablesCheckboxs').html(html);
  }

  //display
  $('#rswDetailedRequirements').html(detailedRequirements);
  $('#rswGuidelines').html(guidelines);
  $('#rswPrivateDescription').html(privateDescription);

  if(isDevOrDesign()) {
     $('#rswRootCatalog').html($("#catalogSelect option[value="+ mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId +"]").text());

     var html = "";
     $.each($('#select2_categories option'),function(i,option) {
     	  html += option.text +"<br/>";
     });
     $('#rswCategories').html(html);
  }

  // For studio
  if (mainWidget.competitionType == "STUDIO") {
	  $('#rContestIntroduction').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestIntroduction);
	  $('#rContestDescription').html(mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestDescription);
	  html = "";
      var studioSubtypeId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
      var types = getStudioFileTypes(studioSubtypeId);
	  $.each(mainWidget.softwareCompetition.fileTypes, function(i, fileType) {
		  if(isNotEmpty(fileType+"")) {
              var found = false;
              for (var i = 0; i < types.length; i++) {
                  if(types[i].value == fileType) {
                      html += '<li>'+ types[i].description +'</li>';
                      found = true;
                      break;
                  }
              }
              if(!found) {
			  html += '<li>'+ fileType +'</li>';
              }
		  }
	  });
	  $('#rFinalDeliveries').html(html);
	  $('#allowStockArt').attr('checked', mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'] == 'true');
	  $('#rContestStockArt').html((mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'] == 'true') ? 'Stock Arts allowed' : 'Stock Arts not allowed');

      if ($('#viewableSubmFlag').length) {
          $('#viewableSubmFlag').attr('checked', mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] == 'true');
          if (mainWidget.softwareCompetition.projectHeader.isLccchecked()) {
                $("#viewableSubmFlag").attr("disabled","disabled");
                $("#viewableSubmFlag").attr("checked","");
          } else {
                $("#viewableSubmFlag").attr("disabled","");
          }
      }
      if (mainWidget.softwareCompetition.projectHeader.isLccchecked()) {
        $("#contestIntroduction").parent().find(".mceFooterNote").show();
      } else {
        $("#contestIntroduction").parent().find(".mceFooterNote").hide();
      }

      if ($('#rViewableSubmFlag').length) {
	      $('#rViewableSubmFlag').html((mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] == 'true') ? 'Submissions are viewable' : 'Submissions are not viewable');
      }
      if ($('#maxSubmissions').length) {
          $('#maxSubmissions').val(mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions']);
      }

      if ($('#rMaxSubmissions').length) {
          if (mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] == '') {
              $('#rMaxSubmissions').html("There are no limits on number of submissions");
          } else {
              $('#rMaxSubmissions').html("Maximum " + mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] + " submissions allowed in each round");
          }
      }
  }

   if(mainWidget.isAlgorithmContest()) {
        var marathonSpec = mainWidget.softwareCompetition.projectHeader.projectMMSpecification;
        $("#rMatchDetails").html(marathonSpec.matchDetails);
        $("#rMatchRules").html(marathonSpec.matchRules);
        $("#marathonMatchDetails").val(marathonSpec.matchDetails);
        $("#marathonMatchRules").val(marathonSpec.matchRules);
    }
    $('#rswEnvironment').html(mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT]);
    $('#rswRepo').html(mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO]);
}

function saveSpecSection() {
    if (!validateFieldsSpecSection()) {
        return;
    }

    if (!mainWidget.isStudioContest()){
        mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT] = ($(".environmentEdit").val() || "").trim();
        mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO] = ($(".repoEdit").val() || "").trim();
    }
    var saveDraftHandler = function () {

        //construct request data
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                populateSpecSection();
                showSpecSectionDisplay();
            },
            beforeSend: beforeAjax,
            complete: afterAjax
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

function validateFieldsSpecSection() {
    // for software contest
    var detailedRequirements = getCKEditorContent('swDetailedRequirements');
    var softwareGuidelines = getCKEditorContent('swGuidelines');
    var privateDescription = getCKEditorContent('swPrivateDescription');

    // for studio contest
    var contestDescription = getCKEditorContent('contestDescription');
    var contestIntroduction = getCKEditorContent('contestIntroduction');

    // for algorithm contest
    var matchDetails = getCKEditorContent('marathonMatchDetails');
    var matchRules = getCKEditorContent('marathonMatchRules');

    var rootCategoryId = $('#catalogSelect').val();

    //validation
    var errors = [];

    if (mainWidget.isStudioContest()) {
        if (!checkRequired(contestIntroduction)) {
            errors.push('Challenge introduction is empty.');
        }

        if (!checkRequired(contestDescription)) {
            errors.push('Challenge description is empty.');
        }

        var fileTypesResult = validateFileTypes(errors);
        var fileTypes = fileTypesResult[0];
        var otherFileTypes = fileTypesResult[1];
    } else if (mainWidget.isSoftwareContest()) {
        if (mainWidget.softwareCompetition.projectHeader.projectCategory.id != 29) {
            if (!checkRequired(detailedRequirements)) {
                errors.push('Detailed requirements is empty.');
            }

            if (!checkRequired(softwareGuidelines)) {
                errors.push('Submission guidelines is empty.');
            }

            if (isDevOrDesign()) {
                if (rootCategoryId <= 0) {
                    errors.push('No catalog is selected.');
                }

                if ($('#select2_categories option').length == 0) {
                    errors.push('No category is selected.');
                }
            }

            if (isTechnologyContest()) {
                if (jQuery_1_11_1("#technologies").magicSuggest().getSelection().length == 0) {
                    errors.push('No technology is selected.');
                }
            }

            if (isPlatformContest()) {
                if (jQuery_1_11_1("#platforms").magicSuggest().getSelection().length == 0) {
                    errors.push('No Platform is selected.');
                }
            }
        }
    } else if (mainWidget.isAlgorithmContest()) {

        if(!checkRequired(matchDetails)) {
            errors.push('Marathon Match Details cannot be empty');
        }

        if(!checkRequired(matchRules)) {
            errors.push('Marathon Match Rules cannot be empty');
        }
    }

    if($('#maxSubmissions').length) {
        var maxSubmissions = $('#maxSubmissions').val();

        if (!(optional(maxSubmissions) || (/^\d+$/.test(maxSubmissions) && parseInt(maxSubmissions) > 0))) {
            errors.push('Max Submissions field should be empty or positive integer.');
        }
    }


    if (errors.length > 0) {
        showErrors(errors);
        return false;
    }

    if (mainWidget.isStudioContest()) {
        mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestDescription = contestDescription;
        mainWidget.softwareCompetition.projectHeader.projectStudioSpecification.contestIntroduction = contestIntroduction;
        mainWidget.softwareCompetition.fileTypes = fileTypes.concat(otherFileTypes);
    } else if (mainWidget.isSoftwareContest()) {
        mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = detailedRequirements;
        mainWidget.softwareCompetition.projectHeader.projectSpec.finalSubmissionGuidelines = softwareGuidelines;
        mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription = privateDescription;
    } else if (mainWidget.isAlgorithmContest()) {
        mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchDetails = matchDetails;
        mainWidget.softwareCompetition.projectHeader.projectMMSpecification.matchRules = matchRules;
    }

    if (isDevOrDesign()) {
        mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId = rootCategoryId;
        mainWidget.softwareCompetition.assetDTO.directjsCategories =
            $.map($('#select2_categories option'), function (option, i) {
                return option.value;
            });
    }

    if (isTechnologyContest()) {
        var selectedTechnologies = jQuery_1_11_1("#technologies").magicSuggest().getSelection();
        mainWidget.softwareCompetition.assetDTO.directjsTechnologies = $.map(selectedTechnologies, function (val, i) {
            return val.id.toString();
        });
    }

    if(isPlatformContest()) {
        var selectedPlatforms = jQuery_1_11_1("#platforms").magicSuggest().getSelection();
        mainWidget.softwareCompetition.platforms = $.map(selectedPlatforms, function (val, i) {
            return val.id.toString();
        });
    }

    mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'] = '' + $('#allowStockArt').is(":checked");
    // sets the Maximum Submissions
    if ($.trim(maxSubmissions).length == 0) {
        mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] = '';
    } else {
        mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'] = parseInt(maxSubmissions);
    }
    mainWidget.softwareCompetition.projectHeader.properties['Viewable Submissions Flag'] = '' + $('#viewableSubmFlag').is(":checked");

    return true;
}

function showSpecSectionDisplay() {
    $(".contest_spec").css("display", "block");
    $(".contest_spec_edit").css("display", "none");
}

function showSpecSectionEdit() {
    $(".contest_spec").css("display", "none");
    $(".contest_spec_edit").css("display", "block");

    if(mainWidget.isAlgorithmContest()) {
        $("#problems").resetSS();
        $("#problems").getSetSSValue();
    }
    $('.environmentEdit').val(mainWidget.softwareCompetition.projectHeader.properties[ENVIRONMENT]);
    $('.repoEdit').val(mainWidget.softwareCompetition.projectHeader.properties[CODE_REPO]);
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
    var template = unescape($('#documentTemplate tbody').html());
    $.each(swDocuments, function (i, document) {
        html += $.validator.format(template, (i + 1), htmlEncode(document['fileName']), htmlEncode(document['description']),
            document['documentId']);
    });
    $('#documentTable').html(html);

    if (hasRequirementDocument()) {
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

    var saveDraftHandler = function () {
        //construct request data
        fixFileTypeIds();
        var request = saveAsDraftRequest();

        $.ajax({
            type: 'POST',
            url: ctx + "/launch/saveDraftContest",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleSaveAsDraftContestResult(jsonResult);
                // gets the documents details
                $.ajax({
                    type: 'POST',
                    url: ctx + "/contest/detailJson",
                    data: {"projectId": paramContestId},
                    cache: false,
                    dataType: 'json',
                    async: false,
                    success: function (jsonResult) {
                        handleJsonResult(jsonResult,
                            function (result) {
                                //documentations, each doc has fields of documentId, fileName, description, documentTypId, url
                                swDocuments = result.documentation;
                                // mark them as documentation
                                $.each(swDocuments, function (i, doc) {
                                    doc['comp'] = true;
                                });
                                setRollbackDocuments();
                                populateDocumentSection();
                                showDocumentSectionDisplay();
                            },
                            function (errorMessage) {
                                showServerError(errorMessage);
                            })
                    }
                });
            },
            beforeSend: beforeAjax,
            complete: afterAjax
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


/**
 * Activation in edit page
 */
function activateContestEdit() {
   var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject()

    if(!validateFieldsSpecSection()) {
        return;
    }

   if(billingProjectId <= 0) {
   	  showErrors("no billing project is selected.");
   	  return;
   }

    if (contestHasSpecReview) {
        showActivateSpecReviewModal();
    } else {
        activateAndStartSpecReview('none');
    }

}

/**
 * Show activate and start spec review modal.
 *
 * @since 1.6
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
    $('#TB_window_custom .review-now').attr("href", "javascript:activateAndStartSpecReview('now')");
    $('#TB_window_custom .review-later').attr("href", "javascript:activateAndStartSpecReview('later')");
}

/**
 * Hides activate and start spec review modal.
 *
 * @since 1.6
 */
function hideActivateSpecReviewModal() {
    $('#TB_overlay').hide();
    $('#TB_window_custom').hide();
}

function activateAndStartSpecReview(mode) {

    //construct request data
    fixFileTypeIds();
    var request = saveAsDraftRequest();
    request['activationFlag'] = true;
    request['specReviewStartMode'] = mode;

    $.ajax({
        type: 'POST',
        url:  ctx + "/launch/saveDraftContest",
        data: setupTokenRequest(request, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: handleActivationResultEdit,
        beforeSend: function() {
            hideActivateSpecReviewModal();
            modalPreloader();
        },
        complete: afterAjax
    });
}


function handleActivationResultEdit(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        mainWidget.softwareCompetition.paidFee = result.paidFee;
        // can't change the multiple - round type if contest is not draft
        if (result.projectStatus.name != DRAFT_STATUS) {
            canEditMultiRound = false;
            $('#resubmit').hide();
            $(".activateButton").hide();
            if (mainWidget.softwareCompetition.isPrivateProject()) {
                $(".privateCmd").show();
            }
            $("#timelineModule .heading .status").removeClass('draft');
            $("#timelineModule .heading .status").addClass(result.projectStatus.name.toLowerCase());
            $("#timelineModule .heading .status span").text(result.projectStatus.name);
        }
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        var specResponse = " and specification review has be scheduled.";
        if(!contestHasSpecReview) {
            specResponse = ".";
        }
        //showSuccessfulMessage("Challenge <span class='messageContestName'>" + contestName +"</span> has been activated successfully" + specResponse);
        showSuccessfulMessageWithOperation("Challenge <span class='messageContestName'>" + contestName +"</span> has been activated successfully" + specResponse, "OK", function() {
            closeModal();
            location.reload();
        });
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

$(function() {
    /**
     * Show spec review pop window.
     */
    $('.specrev-goto').click(function() {
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


function showSpecReview(contestJson) {

   var startSpecReviewUrl = "../contest/startSpecReview.action?projectId=";
   var PROJECT_STATUS_ACTIVE = 1;
   contestHasSpecReview = contestJson.hasSpecReview;
   // only if contest is active (activated), has spec review phases, and sepc review phaase have not started
   if(contestJson.hasSpecReview && !contestJson.isSpecReviewStarted
          && contestJson.projectStatus.id == PROJECT_STATUS_ACTIVE) {

       $.ajax({
           type: 'POST',
           url:  ctx+"/contest/specReviewScheduled",
           data: {"projectId":paramContestId},
           cache: false,
           dataType: 'json',
           async : true,
           success: function (jsonResult) {
               handleJsonResult(jsonResult,
                   function(result) {
                        if(result.specReviewScheduled) {
                            $('#swEdit_bottom_review').hide();
                        } else {
                            $('#swEdit_bottom_review').show();
                        }
                   },
                   function(errorMessage) {
                       showServerError(errorMessage);
                   })
           }
       });

       startSpecReviewUrl += contestJson.contestId + "&studio=false&";


   }
   $('#TB_window_custom .review-now').attr("href", startSpecReviewUrl + "startMode=now");
   $('#TB_window_custom .review-later').attr("href", startSpecReviewUrl + "startMode=later");

}

/**
 * Gets copilots for direct project.
 *
 * @param directProjectId the direct project id
 */
function getCopilotsByDirectProjectId(directProjectId) {

    var returnValue = {};

    var request = {directProjectId:directProjectId};

    $.ajax({
       type: 'POST',
       url:  ctx + "/launch/getDirectProjectCopilots",
       data: request,
       cache: false,
       async: false,
       dataType: 'json',
       success: function(jsonResult) {
           handleJsonResult(jsonResult,
           function(result) {
               returnValue.copilots = result.copilots;
               returnValue.selected = result.selected;
           },
           function(errorMessage) {
               showServerError(errorMessage);
           });
       }
    });
    return returnValue;
}

// method to populate copilots selection based on the project selection change
function handleProjectDropDownChange() {

    var value = $('#projects').getSetSSValue();

    var result = getCopilotsByDirectProjectId(value);

    var copilots = result.copilots;
    var selected = result.selected;
    var $contestCopilots = $("#copilots");

    $contestCopilots.html("");

    $contestCopilots.append($('<option></option>').val(0).html("Unassigned"));

    $.each(copilots, function(key, value) {

        $contestCopilots.append($('<option></option>').val(key).html(value));
    });

    // set the selection drop down value
    $contestCopilots.val(selected);

    $('#copilots').resetSS();
    $('#copilots').getSetSSValue(selected);



    var milestones = getMilestonesByDirectProjectId(value);

    var $contestMilestones = $("#milestones");

    $contestMilestones.html("");

    $contestMilestones.append($('<option></option>').val(0).html("Please select a milestone to associate"));

    $.each(milestones, function(id, value) {
        $contestMilestones.append($('<option></option>').val(value.id).text(value.name).attr('title', value.description));
    });

    $("#milestones").resetSS();

    billingAccounts = getBillingAccountsByDirectProjectId(value);
    $("#billingProjects").empty();
    $("#billingProjects").append($('<option></option>').val(0).html("Please select an existing account"));

    $.each(billingAccounts, function(key, value) {
    	var _cca = value["cca"] == "true" ? true : false;
      var _enableEffortDays = value["enableEffortHours"] == "true" ? true : false;
      $("#billingProjects").append($('<option></option>').val(value["id"]).text(value["name"]).data("cca", _cca).data("enableEffortDays", _enableEffortDays));
    });
    $("#chkboxCCA").removeAttr('checked');
    $("#chkboxCCA").removeAttr('disabled');
    mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    $("#billingProjects").val(0);

    $('#rCCA').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
    //showPrizeSectionEdit();
	if(!$("#billingProjects").data('customized')) {
		$("#billingProjects").data('customized',true);
        $('#billingProjects').sSelect({ddMaxHeight: '220',yscroll: true});
    }

	$('#billingProjects').resetSS();
	$('#billingProjects').bind("change", function() {
        if ($(this).find(":selected").data("cca")) {
                mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
                $("#chkboxCCA").attr('checked','true').attr('disabled','true');
                $('#rCCA').text(mainWidget.softwareCompetition.projectHeader.isLccchecked() ? "Required" : "Not Required");
        }else{
                $("#chkboxCCA").removeAttr('disabled');
        }

        if($(this).find(":selected").data("enableEffortDays")) {
          $('.effortEstimateRow').show();
        } else {
          $('.effortEstimateRow').hide();
        }

        updateContestFee();
        updateBillingGroups();
    });

    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;

    setupReviewerDropdown(projectCategoryId, value);
}

function setupReviewerDropdown(challengeTypeId, directProjectId) {
    if(challengeTypeId == SOFTWARE_CATEGORY_ID_CODE || challengeTypeId == SOFTWARE_CATEGORY_ID_F2F || challengeTypeId == SOFTWARE_CATEGORY_ID_AUTOMATE) {
        // show review type radios if the contest is Code or First2Finish and there is no reviewer for the contest
        if(getObjectSize(mainWidget.softwareCompetition.reviewers) == 0) {

            $("#reviewTypeEditDiv").show();

            var resources = getProjectResourcesByDirectProjectId(directProjectId);

            var $projectResources = $("#reviewer");

            $projectResources.html("");

            $.each(resources, function(id, value) {
                $projectResources.append($('<option></option>').val(value.userId).text(value.name));
            });

            if('internal' == $("input[name=reviewType]:checked").val()) {
                // show the reviewer dropdown
                var selectedValue = $("#reviewer").val();
                $("#reviewerEditDiv").show();
                $("#reviewer").resetSS();
                $("#reviewer").getSetSSValue(selectedValue);
            } else {
                $("#reviewerEditDiv").hide();
            }
        }
    }
}

function doCloseContest(winnerId) {
    var request = {"projectId": mainWidget.softwareCompetition.projectHeader.id , "winner": parseInt(winnerId)};
    $.ajax({
        type: 'POST',
        url:  ctx+"/contest/close",
        data: setupTokenRequest(request, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        async : true,
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    showConfirmation("Success", "The system will close the challenge shortly.", "OK", function () {
                        $(".privateCmd").hide();
                        closeModal();
                    });
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                })
        }
    });
}

function closeContest() {
    showSelectWinner("#selectWinnerConfirmation", "Select winner",
        function () {
            if (parseInt($("#registrants").val()) <= 0) {
                closeModal();
                showConfirmation("Error", "Please choose a winner", "OK", function() {
                    closeModal();
                    closeContest();
                });
            }else{
                closeModal();
                var winnerId = $("#registrants").val();
                doCloseContest(winnerId);
            }
        });
}

function forceCancelContest() {
    displayUserConfirmation("#yesNoConfirmation", "Confirmation", "Are you sure to cancel this challenge?",
        null, function () {
            var request = {"projectId": mainWidget.softwareCompetition.projectHeader.id};
            $.ajax({
                type: 'POST',
                url: ctx + "/contest/cancel",
                data: setupTokenRequest(request, getStruts2TokenName()),
                cache: false,
                dataType: 'json',
                async: true,
                success: function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function (result) {
                            closeModal();
                            showConfirmation("Success", "Challenge has been canceled", "OK", function () {
                                location.reload();
                            });
                        },
                        function (errorMessage) {
                            closeModal();
                            showServerError(errorMessage);
                        })
                }
            });
        }, null, function () {
            closeModal()
        });
}


function showSelectWinner(modal, title, yesButtonHandler) {
    var noButtonHandler = closeModal;
    $(modal + " .modalHeader .modalHeaderCenter h2").text(title);
    $(modal + " .modalBody, " + modal + " .modalHeader").addClass("confirmation");


    $(modal + " #registrants").html("");
    $(modal + " #registrants").append($('<option></option>').val(0).html("Please select the winner"));
    $.each(mainWidget.softwareCompetition.registrants, function (k,v) {
        $(modal + " #registrants").append($('<option></option>').val(v.id).html(v.name));
    });

    $(modal + " .modalCommandBox .defaultBtn").bind("click", yesButtonHandler);
    $(modal + " .modalCommandBox .noBtn").bind("click", noButtonHandler);
    adjustAndShow(modal);
    $(modal + " #registrants").resetSS();
}
