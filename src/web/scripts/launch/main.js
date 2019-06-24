/**
 * Copyright (C) 2010 - 2019 TopCoder Inc., All Rights Reserved.
 *
 * Main Script. It contains the functions/variables shared for launch contest/edit contest.
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - move some functions for modal pop ups to main.js file
 *
 * Version 1.1.1 (TC Direct Release Assembly 7) Change notes:
 * - Allow set digital run field manually.
 *
 * Version 1.1.2 (TC Direct - Software Creation Update Assembly) Change notes:
 * - Add method getCopilotsByDirectProjectId to get copilots info via Ajax.
 * - Update method saveAsDraftRequestSoftware to add copilot id and name into request.
 * Version 1.2 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.1.3 (TC Direct - Release Bug Fix Assembly) Change notes:
 * - Change time zone from GMT-04 to UTC-05.
 * - Update removeDocument method to add "studio:true" parameter.
 * Version 1.3 TC Direct Replatforming Release 2 change notes:
 * - Add support to set checkpoint prizes for software contest.
 * - The studio contest can have specification review cost.
 * Version 1.4 TC Direct Replatforming Release 4 change notes:
 * - Add support to set Maximum Submissions for studio contest.
 *
 * Version 1.4.1 (Direct Improvements Assembly Release 2) Change notes:
 * - Change the previewContest function to preview the copilot contest correctly.
 *
 * Version 1.5 (Release Assembly - TC Direct UI Improvement Assembly 3) Change notes:
 * - Fix the styles of catalog selection dropdown.
 *
 * Version 1.6 TC Cockpit Post a Copilot Assembly  change notes:
 * - Add more checks.
 *
 * Version 1.7 (Release Assembly - TC Cockpit Contest Edit and Upload Update) Change notes:
 * - Fixed bug TCCC-3724. Updated logic for editing the contests.
 *
 * Version 1.8 (Release Assembly - TC Direct Cockpit Release One) change notes:
 * - Update fillPrizes(), always try loading billing contest fee for billingFees global object first
 *   because it's loaded via ajax and has the latest data.
 *
 * Version 1.9 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
 * - Update saveAsDraftRequestSoftware() to set the autoCreateBugHunt if contest type is assembly and autoCreateBugHunt
 * check box is checked
 *
 * Version 1.9.1 (BUGR-6609) changes:
 * - Update the submission end date and contest end date when the contest was updated.
 *
 * Version 2.0 (Release Assembly - TC Direct Cockpit Release Five) change notes:
 * - Fix the DR points, checkpoint prizes, contest fee percentage calculation etc.
 *
 * Version 2.1 (Release Assembly - TopCoder Cockpit Billing Account Project Association) change notes:
 * - Add method getBillingAccountsByDirectProjectId(directProjectId)
 *
 * Version 2.2 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 * - Always use the value of project info 49 as the copilot cost of the contest
 *
 * Version 2.3 (Release Assembly - TC Direct Cockpit Release Eight)
 * - Fix the review cost zero bug when the contest type is assembly and prize type is custom.
 *
 * Version 2.4 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match)
 * - Update to support launching mm contest.
 *
 * Version 2.5 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * - Fix the error that when user changes billing account, the prize gets reset.
 *
 * Version 2.6 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * - Update to support association between contest and project milestone
 *
 * Version 2.7 (Module Assembly - TC Cockpit Launch Code contest)
 * - Add support for the new contest type Code
 *
 * Version 2.8 (Module Assembly - TC Cockpit Launch F2F contest)
 * - Add support for the new contest type First2Finish
 *
 * Version 2.9 (Release Assembly - TC Cockpit Private Challenge Update)
 * - Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
 *
 * Version 3.0 (TC Cockpit Auto Assign Reviewer for First2Finish challenge)
 * - Add flag 'autoAssignReviewer' to the Save Contest ajax request for First2Finish challenge
 *
 * Version 3.1 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
 * - Add support for setting checkpoint end date and submission end date for software challenge
 *
 * Version 3.2 (F2F - TC Cockpit Update Bug Hunt type)
 * - Update to allow only setting 1st place prize for Bug Hunt challenge
 *
 * Version 3.3 (F2F - TC Cockpit Update Auto Assign Reviewer Flow)
 * - Add review type radios to choose 'community' or 'internal' review
 *
 * Version 3.4 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * - Handles new Design First2Finish contest
 *
 * Version 3.5 (Release Assembly - TC Direct Prize Section Update)
 * - Update prize section to support on the fly cost calculation for design challenge
 * - Add checkpoint prize for dev challenge prize section and update on the fly cost calculation
 *
 * Version 3.6 (TopCoder Direct - Review Cost Calculation Quick Updates) @author GreatKevin @challenge 30044580
 * - Updated codes related to the review cost calculation ajax.
 *
 * Version 3.7 (Topcoder Direct - Allow a user to link a marathon match round id to direct mm challenge)
 * @author Veve @channegeId 30046969
 * - Add the marathon round id project info save for marathon challenge
 *
 * Version 3.8 (TopCoder Direct - Design Challenge Track Studio Cup Point Flag)
 * - Add studio cup points checkbox.
 *
 * Version 3.9 (Provide Way To Pre_register members When Launching Challenge)
 * - Add support for pre-register member
 *
 * Version 3.10 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * - Add support for pick up challenge group.
 *
 * Version 4.0 (Topcoder - Ability To Set End Date For Registration Phase and Submission Phase)
 * - Updated saveAsDraftRequest to support regEndDate
 *
 * Version 4.1 (TOPCODER - SUPPORT CUSTOM COPILOT FEE FOR CHALLENGE IN DIRECT APP):
 * - Add support for custom copilot fee
 *
 * Version 4.2 (TOPCODER - SUPPORT TYPEAHEAD FOR TASK ASSIGNEES IN DIRECT APP):
 * - Move task assign member to use magicSuggest
 *
 * Version 4.3 (TOPCODER - IMPROVE USER MANAGEMENT BEHAVIOR FOR PROJECT PERMISSIONS & NOTIFICATIONS)
 * - Exclude copilot posting on #technologyAndPlatformSelectsChanged
 *
 * Version 4.4 (TOPCODER - IMPROVE TASK ASSIGNEE FILTERING FOR CHALLENGES WITH GROUPS)
 * - Use group member for list of task assign user
 *
 * Version 4.5 (Topcoder - Add Basic Marathon Match Creation And Update In Direct App)
 * - Update for Marathon match registration date
 *
 * Version 4.6 (Topcoder - Support Points Prize Type For Challenges)
 * - Add support for points prize type
 *
 * Version 4.7 (Topcoder - Add effort days field)
 * - Add enable effort days
 *
 * Version 4.8 (Topcoder - Integrate Direct with Groups V5)
 * - Refactor projectGroup to comply with v5</li>
 *
 * @author isv, GreatKevin, bugbuka, GreatKevin, Veve, TCSCODER, TCSASSEMBER
 * @version 4.8
 */

 /**
 * mainWidget
 */
var mainWidget = new com.topcoder.direct.MainWidget();

/**
 * documents
 */
var currentDocument = {};
var documents = [];

var originalPrizes;
var originalPoints;
var phaseOpen;
var isCompleted;
var isCancelled;

/**
 * Configurations
 */
var studioSubtypeOverviews = [];
var studioSubtypeFees = [];
var algorithmSubtypeFees = [];
var fileTypes = [];
var billingInfos = [];
var copilotFees = {};
//software
var softwareContestFees = {};
var originalSoftwareContestFees = {};

/**
 * Local cache for fees for certain billing project.
 *
 * The object key is the billing project id, the value is fee object array.
 */
var billingFees = {};

/**
 * Local cache for billingFeesPercentage;
 */
var billingFeesPercentage = {};

/**
 * Local cache for billingGroups
 */
var billingGroups = {};

/**
 * Local cache for copilots for direct project.
 */
var directProjectCopilots = {};

/**
 * Local cache for project resources for direct project
 *
 * @type {{}}
 */
var directProjectResources = {};

/**
 * software documents
 */
// current document for uploading
var swCurrentDocument = {};
// it contains all uploadDocument or compDocuments
// uses a field of "comp" of type boolean to indicate if it is comDocuments or not.
// It is added for edit handling where comDocument is handled differently
var swDocuments = [];

// represents project id of reporting contest type.
var REPORTING_ID = "36";

var groupCancel = false;
var members = [];
/**
 * Configuration/General Set up
 */
$(document).ready(function() {
//configured group input first
  if (typeof jQuery_1_11_1 !== 'undefined' && jQuery_1_11_1 !== null) {
    var ms_group = jQuery_1_11_1("#groups").magicSuggest({
                     placeholder: 'Type group name here',
                     allowFreeEntries: false,
                     data: [],
                     disabled: true
                     });
    jQuery_1_11_1(ms_group).on('selectionchange', function(e,m){
      if (groupCancel){
        return;
      }
      if (this.getValue().length > 0 && jQuery_1_11_1("#preRegisterUsers").magicSuggest().getValue().length > 0){
        displayWarning("#yesNoConfirmation", "Confirmation", "Changing group will remove all assigned members.\n" +
          "Do you want to proceed?", "OK", function(){
             jQuery_1_11_1("#preRegisterUsers").magicSuggest().clear();
             closeModal();
             }, "CANCEL", function(){
             jQuery_1_11_1("#groups").magicSuggest().clear();
             jQuery_1_11_1("#groups").magicSuggest().setValue(mainWidget.softwareCompetition.groups)
             closeModal();
             });
      }
    });
   }
   // loading some configuration data
   $.ajax({
      type: 'POST',
      url:  ctx+"/launch/getContestConfigs",
      data: {},
      cache: false,
      dataType: 'json',
      async: false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
            studioSubtypeOverviews = result.overview;
            studioSubtypeFees = result.studioContestFees;
            algorithmSubtypeFees = result.algorithmContestFees;
            fileTypes = result.fileTypes;
            softwareContestFees = result.softwareContestFees;
            originalSoftwareContestFees = $.extend(true,{},softwareContestFees);
            billingInfos = result.billingInfos;
            copilotFees = result.copilotFees;
              if (typeof jQuery_1_11_1 !== 'undefined' && jQuery_1_11_1 !== null) {
                  var platforms = result.platforms;
                  platforms.sort(sortByname);
                  jQuery_1_11_1("#platforms").magicSuggest({
                      placeholder: 'Type platform name here',
                      allowFreeEntries: false,
                      data: platforms
                  });
                  var technologies = result.technologies;
                  technologies.sort(sortByname);
                  jQuery_1_11_1("#technologies").magicSuggest({
                      placeholder: 'Type technology name here',
                      allowFreeEntries: false,
                      data: technologies
                  });
                  jQuery_1_11_1("#preRegisterUsers").magicSuggest({
                      placeholder: 'Type handle name here',
                      allowFreeEntries: false,
                      hideTrigger: true,
                      data: function (q) {
                          members=[];
                          var url, data;
                          if (jQuery_1_11_1("#groups").magicSuggest().getValue().length > 0){
                            url = group_member_api_url;
                            data = setupTokenRequest({groupIds: jQuery_1_11_1("#groups").magicSuggest().getValue()},
                                   getStruts2TokenName());
                          }else{
                            url = member_api_url;
                          }
                          if (typeof(q) === 'string' && q.length > 0) {
                              $.ajax({
                                  type: 'GET',
                                  url: url + q,
                                  data: data,
                                  cache: false,
                                  dataType: 'json',
                                  contentType: 'application/json; charset=utf-8',
                                  async: false,
                                  beforeSend: function (xhr) {
                                    xhr.setRequestHeader("Authorization", "Bearer " + $.cookie(jwtCookieName));
                                  },
                                  success: function (jsonResult) {
                                    if (jQuery_1_11_1("#groups").magicSuggest().getValue().length > 0){
                                      handleJsonResult(jsonResult, function(result){
                                        $.each(result, function (index, member) {
                                            members.push({'id': member['userId'].toString(), 'name': member['handle']});
                                        });
                                      }, function (errorMessage) {
                                           closeModal();
                                           showServerError(errorMessage);
                                      });
                                    } else {
                                      if (typeof(jsonResult['result']) !== 'undefined') {
                                          $.each(jsonResult['result']['content'], function (index, member) {
                                              members.push({
                                                  'id': member['userId'].toString(),
                                                  'name': member['handle']
                                              });
                                          });
                                      }
                                    }
                                  },
                                  error: function () {
                                      throw("Problem getting members");
                                  }
                              })
                          }
                          members.sort(sortByname);
                          return members;
                      }
                  });
              }
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      }
   });
    $.ajax({
          type: 'GET',
          url:  ctx+"/launch/getGroups",
          cache: false,
          dataType: 'json',
          contentType: 'application/json; charset=utf-8',
          success: function (jsonResult) {
              handleJsonResult(jsonResult,
              function(result) {
                if (typeof jQuery_1_11_1 !== 'undefined' && jQuery_1_11_1 !== null) {
                  result.sort(sortByname);
                  if (result.length>0){
                    jQuery_1_11_1("#groups").magicSuggest().setData(result);
                    jQuery_1_11_1("#groups").magicSuggest().enable();
                  }
                }
              },
              function(errorMessage) {
                showServerError(errorMessage);
              });
            }
          });
   // multiple prizes add for studio
   $('.prizesInner .studioAdd').click(function(){
     if($('#extraPrizes').is( ":hidden ")){
       $('#extraPrizes input').val('');
       $('#extraPrizes').show();
       $(this).hide();
     }
   }); //click

   // multiple prizes add for software
   $('.prizesInner .swAdd').click(function(){
        if($('#swExtraPrizes').is( ":hidden ")){
            $('#swExtraPrizes input').val('');
            $('#swExtraPrizes').show();
            $(this).hide();
        }
    });

   // multiple prizes add for algorithm
   $('.prizesInner .alAdd').click(function(){
     if($('#alExtraPrizes').is( ":hidden ")){
       $('#alExtraPrizes input').val('');
       $('#alExtraPrizes').show();
       $(this).hide();
     }
   }); //click

   // multiple points add
   $('.points .addPoint').click(function(){
     var extraPoints = $(this).parents('div.points').find('div.extraPoints');
     if(extraPoints.is( ":hidden ")){
       extraPoints.find('input').val('');
       extraPoints.show();
       $(this).hide();
     }
   }); //click


    $('.extraPoints .removePoint').click(function(){
        var extraPoints = $(this).parent('div.extraPoints');
        if(isExtraPrizesEmpty('#' + extraPoints.attr('id'))) {
            extraPoints.hide();
            $('.points .addPoint').show();
        } else {
            showErrors("There is point still set in this row.");
        }
    });//click


    $('.prizesInner .studioRemove').click(function(){
        if(isExtraPrizesEmpty("#extraPrizes")) {
            $('#extraPrizes').hide();
            $('.prizesInner .studioAdd').show();
        } else {
            showErrors("There is prize still set in this row.");
        }
    });//click

    $('.prizesInner .swRemove').click(function(){
        if(isExtraPrizesEmpty("#swExtraPrizes")) {
            $('#swExtraPrizes').hide();
            $('.prizesInner .swAdd').show();
        } else {
            showErrors("There is prize still set in this row.");
        }
    });//click

    $('.prizesInner .alRemove').click(function(){
        if(isExtraPrizesEmpty('#alExtraPrizes')) {
            $('#alExtraPrizes').hide();
            $('.prizesInner .alAdd').show();
        } else {
            showErrors("There is prize still set in this row.");
        }
    });//click

    $('.customRadio').click(function() {
       onFirstPlaceChangeKeyUp();
    });

    // digital run check box
    $('#DRCheckbox').click(function(){
        if($(this).is(":checked")) {
            $("#swDigitalRun").removeAttr('disabled');
            if($("#swDigitalRun").data('previousDR') != undefined) {
                $("#swDigitalRun").val($("#swDigitalRun").data('previousDR'));
            }
        } else {
            $("#swDigitalRun").data('previousDR', $("#swDigitalRun").val());
            $("#swDigitalRun").val(0);
            $("#swDigitalRun").attr('disabled', 'disabled');
        }
        fillPrizes();
    });

    if($("#overviewSoftwarePage").length > 0) {
        // if it's software contest launch flow, we make the DR flag default checked

    }

   //file types
   $('.fileType').click(function(){
       $('.deliverablesInner .checkInput').append('<div><input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" /></div>');
   });

  // Document uploader set up
  var uploader =
  new AjaxUpload(null, {
        action: ctx + '/launch/documentUpload',
        name : 'document',
        responseType : 'json',
        onSubmit : function(file , ext){
           //software document
           swCurrentDocument['fileName'] = file;

           uploader.setData(
           {
             studio:false,
             contestFileDescription:swCurrentDocument['description'],
             documentTypeId:swCurrentDocument['documentTypeId']
             }
           );

           modalPreloader();
        },
        onComplete : function(file, jsonResult){
                handleJsonResult(jsonResult,
                function(result) {
                  var documentId = result.documentId;
                  swCurrentDocument['documentId'] = documentId;
                  swDocuments.push(swCurrentDocument);

                  //only one requirement document
                  if(swCurrentDocument['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
                     $('.reqDocCheck').hide();
                  }

                  swCurrentDocument = {};
                  $('#swFileDescription, #documentList').val('');

                  swRefreshDocuments();

                  modalClose();
                  uploader._input = $("#uploadButtonDiv input[type='file']").get(0);
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                    modalClose();
                });
        }
      }, true);

  // Document uploader set up
  var alUploader =
  new AjaxUpload(null, {
        action: ctx + '/launch/documentUpload',
        name : 'document',
        responseType : 'json',
        onSubmit : function(file , ext){
           //software document
           swCurrentDocument['fileName'] = file;

           alUploader.setData(
           {
             studio:false,
             contestFileDescription:swCurrentDocument['description'],
             documentTypeId:swCurrentDocument['documentTypeId']
             }
           );

           modalPreloader();
        },
        onComplete : function(file, jsonResult){
                handleJsonResult(jsonResult,
                function(result) {
                  var documentId = result.documentId;
                  swCurrentDocument['documentId'] = documentId;
                  swDocuments.push(swCurrentDocument);

                  swCurrentDocument = {};
                  $('#alFileDescription, #aldocumentList').val('');

                  swRefreshDocuments();

                  modalClose();
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                    modalClose();
                });
        }
      }, false, true);

//Software uploader
  var swUploader =
  new AjaxUpload(null, {
    action: ctx + '/launch/documentUpload',
    name : 'document',
    responseType : 'json',
    onSubmit : function(file , ext){
       //software document
       swCurrentDocument['fileName'] = file;

       swUploader.setData(
       {
         studio:false,
         contestFileDescription:swCurrentDocument['description'],
         documentTypeId:swCurrentDocument['documentTypeId']
         }
       );

       modalPreloader();
    },
    onComplete : function(file, jsonResult){
            handleJsonResult(jsonResult,
            function(result) {
              var documentId = result.documentId;
              swCurrentDocument['documentId'] = documentId;
              swDocuments.push(swCurrentDocument);

              //only one requirement document
              if(swCurrentDocument['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
                 $('.reqDocCheck').hide();
              }

              swCurrentDocument = {};
              $('#swFileDescription, #documentList').val('');

              swRefreshDocuments();

              modalClose();
            },
            function(errorMessage) {
                showServerError(errorMessage);
                modalClose();
            });
    }
  }, false);

  $('#alFileUploadBtn').click(function(){
    var fileName = alUploader._input.value;
    var description = $('#alFileDescription').val();

    var errors = [];

    if(!checkRequired(fileName)) {
        errors.push('No file is selected.');
    }

    if(!checkRequired(description)) {
        errors.push('File description is empty.');
    }

    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }

    swCurrentDocument['description'] = description;
    if($('#specDoc').is(':visible:checked')) {
      swCurrentDocument['documentTypeId'] = REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
    } else {
      swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
    }

    alUploader.submit();
  });

  $('#fileUploadBtn').click(function(){
    var fileName = uploader._input.value;
    var description = $('#fileDescription').val();

    var errors = [];

    if(!checkRequired(fileName)) {
        errors.push('No file is selected.');
    }

    if(!checkRequired(description)) {
        errors.push('File description is empty.');
    }

    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }

    swCurrentDocument['description'] = description;
    if($('#specDoc').is(':visible:checked')) {
      swCurrentDocument['documentTypeId'] = REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
    } else {
      swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
    }

    uploader.submit();
  });

  $('#swFileUploadBtn').click(function(){
    var fileName = swUploader._input.value;
    var description = $('#swFileDescription').val();

    var errors = [];

    if(!checkRequired(fileName)) {
        errors.push('No file is selected.');
    } else {
        var fileNameToCheck = fileName.toLowerCase();
        if (fileNameToCheck.indexOf('fakepath') >= 0) {
            var p1 = fileNameToCheck.lastIndexOf("\\");
            var p2 = fileNameToCheck.lastIndexOf("/");
            var p = Math.max(p1, p2);
            fileNameToCheck = fileNameToCheck.substring(p + 1);
        }
        $('.fileInput').each(function(index, item) {
            if ($(item).html().toLowerCase() == fileNameToCheck) {
                errors.push('Such a file is already uploaded');
            }
        });
    }

    if(!checkRequired(description)) {
        errors.push('File description is empty.');
    }

    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }

    swCurrentDocument['description'] = description;
    if($('#swSpecDoc').is(':visible:checked')) {
      swCurrentDocument['documentTypeId'] = REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
    } else {
      swCurrentDocument['documentTypeId'] = SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID;
    }
    swUploader.submit();
  });

    $("input[name=privateProject]").click(function(){
        if ($(this).attr("checked") === true) {
            $(".preRegisterUsersRow").show();
            $("#preRegisterUsersEditDiv").show();
        }else{
            $(".preRegisterUsersRow").hide();
            $("#preRegisterUsersEditDiv").hide();
        }
    });
}); // end of initiation


function hasGroupSelected(){
    var selected = jQuery_1_11_1("#groups").magicSuggest().getSelection();
    return selected && selected.length > 0;
}

/**
 * <p>
 * Update contest administration fee when billing project or studio sub type is changed.
 * </p>
 */
function updateContestFee( ) {

    var isStudio = ('STUDIO' == getContestType(true)[0]);
    var isAlgorithm = ('ALGORITHM' == getContestType(true)[0])
    var contestTypeId = getContestType(true)[1];
    var billingProjectId = $('select#billingProjects').val();

    var billingContestFee = getBillingContestFee(billingProjectId, contestTypeId);

    if(isStudio || isAlgorithm) {
        //for studio or algorithm
        var contestFeePercentage = null;


        // has percentage fee
        if (billingFeesPercentage[billingProjectId] != null) {
            contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;

            if (contestFeePercentage != null) {
                var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
                var contestPrizesTotal = 0;
                $.each(prizes, function (i, prize) {
                    if (prize.prizeType.id != CONTEST_PRIZE_TYPE_ID) {
                        return;
                    }
                    var amount = prize.prizeAmount;
                    contestPrizesTotal += amount;
                });
                var isMultiRound = mainWidget.softwareCompetition.multiRound;
                //checkpoint prizes
                var checkpointPrizesTotal = 0;
                if (isMultiRound) {
                    var amount = prizes[prizes.length - 1].prizeAmount;
                    for (var i = 1; i <= prizes[prizes.length - 1].numberOfSubmissions; i++) {
                        checkpointPrizesTotal += amount;
                    }
                }
                var specificationReviewPayment = parseFloat(mainWidget.softwareCompetition.projectHeader.getSpecReviewCost());
                var reviewPayment = parseFloat(mainWidget.softwareCompetition.projectHeader.getReviewCost());
                var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
                var digitalRun = parseFloat(mainWidget.softwareCompetition.projectHeader.getDRPoints());

                var memberCost = contestPrizesTotal + checkpointPrizesTotal + (isNaN(specificationReviewPayment) ? 0 : specificationReviewPayment) + (isNaN(reviewPayment) ? 0 : reviewPayment) + copilotCost + (digitalRun || 0);
                /* + calculateStudioCupPoints() ; left to FF. */
                mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = contestFeePercentage * memberCost;
                mainWidget.softwareCompetition.adminFee = contestFeePercentage * memberCost;
                mainWidget.softwareCompetition.projectHeader.setAdminFee(mainWidget.softwareCompetition.projectHeader.contestAdministrationFee.toString());
                mainWidget.softwareCompetition.projectHeader.setContestFeePercentage(contestFeePercentage.toString());
                return;
            } else {
                mainWidget.softwareCompetition.projectHeader.setContestFeePercentage("0");
            }
        }

        // has fixed fee for the billing account
        if (billingContestFee >= 0) {
            // read from the billing account fee
            mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = billingContestFee;
            mainWidget.softwareCompetition.adminFee = billingContestFee;
        } else {

            // read from the default configuration file


            // studio contest
            if (isStudio) {
                // get studio contest fee from studio configuration
                $.each(studioSubtypeFees, function (i, feeItem) {
                    if (feeItem.id == contestTypeId) {
                        mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = feeItem.contestFee;
                        mainWidget.softwareCompetition.adminFee = feeItem.contestFee;
                    }
                });
            }

            // algorithm contest
            if (isAlgorithm) {
                // get algorithm contest fee from algorithm configuration
                $.each(algorithmSubtypeFees, function (i, feeItem) {
                    if (feeItem.id == contestTypeId) {
                        mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = feeItem.contestFee;
                        mainWidget.softwareCompetition.adminFee = feeItem.contestFee;
                    }
                });
            }
        }
        mainWidget.softwareCompetition.projectHeader.setAdminFee(mainWidget.softwareCompetition.projectHeader.contestAdministrationFee);
    } else {
        //for software

        // the fee object from the default configuration
        var feeObject = softwareContestFees[contestTypeId];


        if (billingContestFee >= 0) {
            //update corresponding contest fee
            if (feeObject) {
                // update the fee object from the configuration to the customized billing account contest fee
                feeObject.contestFee = billingContestFee;
            }
        } else {
            // rollback
            if (feeObject) {
                feeObject.contestFee = originalSoftwareContestFees[contestTypeId].contestFee;
            }
        }

        // resetSoftwarePrizes();
        $('.customRadio').show();
        // fill prizes - read value from cost object and do the render
        fillPrizes(billingProjectId);
    }
}

function updateBillingGroups() {

    var selectedBillingID = $("#billingProjects").val();

    if(selectedBillingID > 0 && billingGroups[selectedBillingID]) {
        if(billingGroups[selectedBillingID].length > 0) {
            $("#billingGroupCheckBox").show();

            var securityGroupId = mainWidget.softwareCompetition.projectHeader.securityGroupId;

            if(securityGroupId > 0) {
                $("#billingGroupCheckBox input[type=checkbox]").attr('checked', 'checked');
                $("#billingGroupCheckBox select").val(securityGroupId);
            }

            $("#billingGroupCheckBox input[type=checkbox]").trigger('change');

            if(securityGroupId > 0) {
                $("#billingGroupCheckBox select").val(securityGroupId);
                $("#securityGroupName").text($("#billingGroupCheckBox select option:selected").text());
                $("#securityGroupName").parents("tr").show();
            } else {
                $("#securityGroupName").text('');
                $("#securityGroupName").parents("tr").hide();
            }
        } else {
            $("#billingGroupCheckBox").hide();
            $("#billingGroupCheckBox select option").remove();
        }
    } else {
        $("#billingGroupCheckBox").hide();
        $("#billingGroupCheckBox select option").remove();
    }
}

/**
 * initiate contest fee in edit page
 */
function initContestFeeForEdit(isStudio, contestTypeId, billingProjectId) {
    var billingContestFee = getBillingContestFee(billingProjectId, contestTypeId);

    if(isStudio) {
          //for studio
          //nothing
    } else {
          //for software
        if(billingContestFee >= 0) {
             //update corresponding contest fee
             var feeObject = softwareContestFees[contestTypeId];
             if(feeObject) {
                 softwareContestFees[contestTypeId].contestFee = billingContestFee;
             }
        }
    }
}

function getBillingContestFee(billingProjectId, contestTypeId) {
    if(billingProjectId <=0 ) {
       return -1;
    }

      var fee = -1;

      var fees = getContestFeesForBillingProject(billingProjectId);

      $.each(fees, function(i,feeItem){
          // here studio contest is the same as software competition, they are both different with the original studio contest.
         if(feeItem.contestTypeId == contestTypeId) {
               fee = feeItem.contestFee;
         }
      });

      return fee;
}

/**
 * Gets contest fees for billing project.
 *
 * @param billingProjectId billing project id
 */
function getContestFeesForBillingProject(billingProjectId) {
      if(billingFees[billingProjectId] != null && billingGroups[billingProjectId]) {
         return billingFees[billingProjectId];
      }

      var fees = [];

      var percentage = {};

      var groups = [];

      var request = {billingProjectId:billingProjectId};

    $.ajax({
       type: 'POST',
       url:  ctx + "/launch/getBillingProjectContestFees",
       data: request,
       cache: false,
       async: false,
       dataType: 'json',
       success: function(jsonResult) {
           handleJsonResult(jsonResult,
           function(result) {
               if (result.percentage) {
                  // set percentage if not null
                  percentage = result.percentage;
               }
               if(result.fees) {
                  fees = result.fees;
               }
               if(result.groups) {
                  groups = result.groups;
               }
           },
           function(errorMessage) {
               showServerError(errorMessage);
           });
       }
    });

    billingFees[billingProjectId] = fees;
    billingFeesPercentage[billingProjectId] = percentage;
    billingGroups[billingProjectId] = groups;
    return fees;
}

/**
 * Gets copilots for direct project.
 *
 * @param directProjectId the direct project id
 */
function getCopilotsByDirectProjectId(directProjectId) {
    if(directProjectCopilots[directProjectId] != null) {
        return directProjectCopilots[directProjectId];
    }

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

    directProjectCopilots[directProjectId] = returnValue;
    return returnValue;
}

function getProjectResourcesByDirectProjectId(directProjectId) {
    if(directProjectResources[directProjectId] != null) {
        return directProjectResources[directProjectId];
    }

    var returnValue = [];

    var request = {formData: {projectId: directProjectId}};

    $.ajax({
        type: 'POST',
        url:  ctx + "/getProjectResponsiblePerson",
        data: request,
        cache: false,
        async: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    returnValue = result;
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });

    directProjectResources[directProjectId] = returnValue;

    return returnValue;
}

function getBillingAccountsByDirectProjectId(directProjectId) {
    var returnValue = {};
    var request = {directProjectId:directProjectId};
    $.ajax({
        type: 'POST',
        url:  ctx + "/launch/getBillingAccountsForProject",
        data: request,
        cache: false,
        async: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                  returnValue = result;
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });

    return returnValue;
}

function getReviewScorecards(typeId) {
    var returnValue = {};
    $.ajax({
        type: 'POST',
        data: {categoryId: typeId},
        url: ctx + '/launch/getReviewScorecards',
        cache: false,
        async: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    result.sort(function(sc1, sc2) {
                        var a = sc1.scorecardName.toLowerCase();
                        var b = sc2.scorecardName.toLowerCase();
                        return a < b ? -1 : ((a > b) ? 1 : 0);
                    })
                    returnValue = result;
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });
    return returnValue;
}
function getMilestonesByDirectProjectId(directProjectId) {
    var returnValue = {};
    var request = {directProjectId:directProjectId};

    $.ajax({
        type: 'POST',
        url:  ctx + "/launch/getDirectProjectMilestones",
        data: request,
        cache: false,
        async: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    returnValue = result;
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });

    return returnValue;
}

/**
 * Handles preview contest.
 */
function previewContest() {
  //http://studio.topcoder.com/?module=ViewContestDetails&ct=1001503
  if(!isContestSaved()) {
     showErrors("You must 'Save as Draft' before you can preview your challenge.");
  } else {
    if(mainWidget.isSoftwareContest()) {
        window.open('https://' + SERVER_CONFIG_SERVER_NAME + '/tc?module=ProjectDetail&pj='+mainWidget.softwareCompetition.projectHeader.id);
    } else if(mainWidget.isStudioContest()) {
        window.open('https://' + SERVER_CONFIG_STUDIO_SERVER_NAME + '/?module=ViewContestDetails&ct='+mainWidget.softwareCompetition.projectHeader.id);
    } else {
        // see thread http://apps.topcoder.com/forums/?module=Thread&threadID=782914&start=0, preview link is disabled.
    }
  }
}

function isContestSaved() {
   return (mainWidget.softwareCompetition.projectHeader.id > 0);
}

function disablePrizeAdjustment() {
    return (phaseOpen && !mainWidget.softwareCompetition.isPrivateProject())
}

/**
 * Handles cancel contest.
 */
function cancelContest() {
   if(window.confirm("Are you sure you want to cancel? Please save your work first if you want to keep this contest.")) {
       window.location.href = "home";
   }
}

function saveAsDraftRequest() {

    if ($("input[name=CMCTaskID]").length > 0 && $.trim($("input[name=CMCTaskID]").val()).length > 0) {
        mainWidget.softwareCompetition.projectHeader.properties['CloudSpokes CMC Task'] = $("input[name=CMCTaskID]").val();
    }

    if ($("input[name=CMCBillingID]").length > 0 && $.trim($("input[name=CMCBillingID]").val()).length > 0) {
        mainWidget.softwareCompetition.projectHeader.properties['CloudSpokes CMC Task'] = $("input[name=CMCTaskID]").val();
    }

    if ($("input[name=MatchRoundID]").length > 0) {
        if($.trim($("input[name=MatchRoundID]").val()).length > 0) {
            mainWidget.softwareCompetition.projectHeader.properties['Marathon Match Id'] = $("input[name=MatchRoundID]").val();
        } else {
            mainWidget.softwareCompetition.projectHeader.properties['Marathon Match Id'] = '';
        }
    }

    if (isF2F() || isDesignF2F()) {
        if ($("input[name=privateProject]:checked").length > 0){
            mainWidget.softwareCompetition.projectHeader.properties[TASK_FLAG] = "1";

            mainWidget.softwareCompetition.registrants = jQuery_1_11_1("#preRegisterUsers").magicSuggest().getSelection();
            var preRegisterUsers = $.map(mainWidget.softwareCompetition.registrants, function (val, i) {
                return val.name;
            });
            mainWidget.softwareCompetition.preRegisterUsers = preRegisterUsers.join(',');
        }else{
            mainWidget.softwareCompetition.projectHeader.properties[TASK_FLAG] = "0";
            mainWidget.softwareCompetition.preRegisterUsers = "";
        }
    }

    if ($("#billingGroupCheckBox input[type=checkbox]").is(":checked") && $("#billingGroupCheckBox select").val() > 0) {
        mainWidget.softwareCompetition.projectHeader.securityGroupId = $("#billingGroupCheckBox select").val();
    } else {
        mainWidget.softwareCompetition.projectHeader.securityGroupId = 0;
    }

    if ($("input[name=reviewType]:checked").length > 0
        || !mainWidget.softwareCompetition.projectHeader.properties['Review Type']) {

        var reviewType = isDesignType() ? "INTERNAL" : "COMMUNITY";

        if (isCode() || isF2F()) {
            // read from the choice
            if ('internal' == $("input[name=reviewType]:checked").val()) {
                reviewType = "INTERNAL";
            } else {
                reviewType = "COMMUNITY";
            }
        }

        mainWidget.softwareCompetition.projectHeader.properties['Review Type'] = reviewType;
    }

    if($(".effortEstimateRow").is(":visible")) {
      var effortDaysEstimateOffshore = $("input[name=effortDaysEstimateOffshore]").val().trim();
      var effortDaysEstimateOnsite = $("input[name=effortDaysEstimateOnsite]").val().trim();
      mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE] = effortDaysEstimateOffshore;
      mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE] = effortDaysEstimateOnsite;
    } else {
      mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_OFFSHORE] = '';
      mainWidget.softwareCompetition.projectHeader.properties[ESTIMATE_EFFORT_ONSITE] = '';
    }

/*
    if ($("#productName").val().trim().length > 0) {
        mainWidget.softwareCompetition.projectHeader.properties[PRODUCT_SKU] = $("#productName").val().trim();
    }else{
        mainWidget.softwareCompetition.projectHeader.properties[PRODUCT_SKU] = "";
    }
*/

    var request;

   if(mainWidget.isSoftwareContest()) {
       request =  saveAsDraftRequestSoftware();
   } else if(mainWidget.isStudioContest()){
       request = saveAsDraftRequestStudio();
   } else {
       request = saveAsDraftRequestAlgorithm();
   }

    if($("input[name=CMCBillingID]").length > 0 && $.trim($("input[name=CMCBillingID]").val()).length > 0) {
        request['cmcBillingId'] = $("input[name=CMCBillingID]").val();
    }

    var selectedGroups = jQuery_1_11_1("#groups").magicSuggest().getSelection();
    request['groups'] = $.map(selectedGroups, function (val, i) {
                                    return val.oldId.toString();
                        });

    var copilotCost = parseFloat(mainWidget.softwareCompetition.copilotCost);
    if(copilotCost > 0 && (copilotCost != parseFloat(copilotFees[getContestType(true)[1]]["copilotFee"]))){
        request["customCopilotFee"] = mainWidget.softwareCompetition.copilotCost;
    }

    request.projectHeader = $.extend(true, {}, request.projectHeader);
    // the points feature should only be available, when there are groups selected
    if (hasGroupSelected()) {
      // Concat points to prizes
      if (request.projectHeader.points && request.projectHeader.points.length) {
          var prizes = request.projectHeader.prizes.concat(request.projectHeader.points);
          // has point, no need to save contest prize 0
          prizes = prizes.filter(function(prize) {
            return !(prize.prizeAmount === 0 && prize.prizeType.id === CONTEST_PRIZE_TYPE_ID);
          });
          request.projectHeader.prizes = prizes;
      }
    }
    delete request.projectHeader.points;

    return request;
}

function saveAsDraftRequestSoftware() {
   var request = {};

   var thurgoodData = technologyAndPlatformSelectsChanged();

   if($("#swThurgoodDiv input").is(":checked")) {
       // thurgood checked
       if(thurgoodData['hasJavaTech'] == true) {
           mainWidget.softwareCompetition.projectHeader.properties['Thurgood Language'] = 'Java';
       } else {
           mainWidget.softwareCompetition.projectHeader.properties['Thurgood Language'] = '';
       }
       if(thurgoodData['hasSalesforcePlatform'] == true) {
           mainWidget.softwareCompetition.projectHeader.properties['Thurgood Platform'] = 'Salesforce';
       } else {
           mainWidget.softwareCompetition.projectHeader.properties['Thurgood Platform'] = '';
       }
   } else {
       mainWidget.softwareCompetition.projectHeader.properties['Thurgood Language'] = '';
       mainWidget.softwareCompetition.projectHeader.properties['Thurgood Platform'] = '';
   }

   delete mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE];

    if(isF2F() || isCode()) {
        // get the auto assign reviewer ID to F2F / CODE challenge
        mainWidget.softwareCompetition.projectHeader.autoAssignReviewerId =
            ('internal' == $("input[name=reviewType]:checked").val()) ? ($("#reviewer").val() ? $("#reviewer").val(): 0) : 0;

        if(mainWidget.softwareCompetition.projectHeader.autoAssignReviewerId > 0) {
            var reviewerId = $("#reviewer").val();
            var reviewHandle = $("#reviewer option:selected").text();
            mainWidget.softwareCompetition.reviewers = {};
            mainWidget.softwareCompetition.reviewers[reviewerId] = reviewHandle;
        }
    }

   request['projectId'] = mainWidget.softwareCompetition.projectHeader.id;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'SOFTWARE';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;
   request['directProjectMilestoneId'] = mainWidget.softwareCompetition.projectMilestoneId;
    request['preRegisterUsers'] = mainWidget.softwareCompetition.preRegisterUsers;

   if(mainWidget.softwareCompetition.subEndDate && formatDateForRequest(mainWidget.softwareCompetition.subEndDate)) {
       request['endDate'] = formatDateForRequest(mainWidget.softwareCompetition.subEndDate);
   }

   if(mainWidget.softwareCompetition.regEndDate && formatDateForRequest(mainWidget.softwareCompetition.regEndDate)) {
       request['regEndDate'] = formatDateForRequest(mainWidget.softwareCompetition.regEndDate);
   }

    //checkpoint
    if(mainWidget.softwareCompetition.multiRound) {
        request['checkpointDate'] = formatDateForRequest(mainWidget.softwareCompetition.checkpointDate);
    }



    if (!isNaN(mainWidget.softwareCompetition.copilotUserId)) {
        request['contestCopilotId'] = mainWidget.softwareCompetition.copilotUserId;
        request['contestCopilotName'] = mainWidget.softwareCompetition.copilotUserName;
    }

   if(isDevOrDesign()) {
      //only check for design
      if(isDesign() && mainWidget.softwareCompetition.projectHeader.id <= 0) {
          if(window.confirm("Would you like to create the corresponding Component Development contest?")) {
             request['autoCreateDev'] = true;
          } else {
             request['autoCreateDev'] = false;
          }
      }

      request['rootCategoryId'] = mainWidget.softwareCompetition.assetDTO.directjsRootCategoryId;
      request['categories'] = mainWidget.softwareCompetition.assetDTO.directjsCategories;
   }

    if(isAssembly()) {
        if(mainWidget.softwareCompetition.projectHeader.id <= 0) {
            if($("#bug_hunt_CheckBox").is(":checked")) {
                request['autoCreateBugHunt'] = true;
            } else {
                request['autoCreateBugHunt'] = false;
            }
        }
    }


   // update technologies
   if(isTechnologyContest()) {
      request['technologies'] = mainWidget.softwareCompetition.assetDTO.directjsTechnologies;
   }

    if (isPlatformContest()) {
        request['platforms'] = mainWidget.softwareCompetition.platforms;
    }

   // if dev is derived from selected design
   if(mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded) {
      request['selectedDesignId'] = mainWidget.softwareCompetition.assetDTO.directjsDesignId;
   }

   // the first page also gets some data

   updateSoftwarePrizes();

   // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

   //document uploads
   request['docUploadIds'] = getUploadDocumentIds();
   request['docCompIds'] = getCompDocumentIds();
   request['hasMulti'] = mainWidget.softwareCompetition.multiRound;

   return request;
}

function saveAsDraftRequestStudio() {
   var request = {};
   request['projectId'] = mainWidget.softwareCompetition.projectHeader.id;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'STUDIO';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;
   request['directProjectMilestoneId'] = mainWidget.softwareCompetition.projectMilestoneId;
    request['preRegisterUsers'] = mainWidget.softwareCompetition.preRegisterUsers;

    delete mainWidget.softwareCompetition.projectHeader.properties[MM_TYPE];

    if (!isNaN(mainWidget.softwareCompetition.copilotUserId)) {
        request['contestCopilotId'] = mainWidget.softwareCompetition.copilotUserId;
        request['contestCopilotName'] = mainWidget.softwareCompetition.copilotUserName;
    }

   updateStudioPrizes();
   // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

   //checkpoint
   if(mainWidget.softwareCompetition.multiRound) {
      request['checkpointDate'] = formatDateForRequest(mainWidget.softwareCompetition.checkpointDate);
   }
   // end date
   request['regEndDate'] = formatDateForRequest(mainWidget.softwareCompetition.regEndDate);
   request['endDate'] = formatDateForRequest(mainWidget.softwareCompetition.subEndDate);


   request['hasMulti'] = mainWidget.softwareCompetition.multiRound;

   //document uploads
   request['docUploadIds'] = getStudioDocumentIds();
   request['docCompIds'] = getCompDocumentIds();
   request['fileTypes'] = mainWidget.softwareCompetition.fileTypes;

   return request;
}

function saveAsDraftRequestAlgorithm() {
   var request = {};
   request['projectId'] = mainWidget.softwareCompetition.projectHeader.id;
   request['tcDirectProjectId'] = mainWidget.softwareCompetition.projectHeader.tcDirectProjectId;
   request['competitionType'] = 'ALGORITHM';
   request['assetDTO'] = mainWidget.softwareCompetition.assetDTO;
   request['projectHeader'] = mainWidget.softwareCompetition.projectHeader;
    request['preRegisterUsers'] = mainWidget.softwareCompetition.preRegisterUsers;

    if (!isNaN(mainWidget.softwareCompetition.copilotUserId)) {
        request['contestCopilotId'] = mainWidget.softwareCompetition.copilotUserId;
        request['contestCopilotName'] = mainWidget.softwareCompetition.copilotUserName;
    }

   updateAlgorithmPrizes();
   // add copilot cost into project header
   mainWidget.softwareCompetition.projectHeader.setCopilotCost(mainWidget.softwareCompetition.copilotCost);

   if(mainWidget.softwareCompetition.subEndDate && formatDateForRequest(mainWidget.softwareCompetition.subEndDate)) {
       request['endDate'] = formatDateForRequest(mainWidget.softwareCompetition.subEndDate);
   }

   if(mainWidget.softwareCompetition.regEndDate && formatDateForRequest(mainWidget.softwareCompetition.regEndDate)) {
       request['regEndDate'] = formatDateForRequest(mainWidget.softwareCompetition.regEndDate);
   }

   //request['hasMulti'] = mainWidget.softwareCompetition.multiRound;

   //document uploads
   request['docUploadIds'] = getStudioDocumentIds();
   request['docCompIds'] = getCompDocumentIds();
   request['fileTypes'] = mainWidget.softwareCompetition.fileTypes;

   return request;
}

function handleSaveAsDraftContestResult(jsonResult) {
   if(mainWidget.isSoftwareContest()) {
       handleSaveAsDraftContestResultSoftware(jsonResult);
   } else if(mainWidget.isStudioContest()){
       handleSaveAsDraftContestResultStudio(jsonResult);
   } else {
       handleSaveAsDraftContestResultAlgorithm(jsonResult);
   }
}

function handleFailedRegsiterUsers(failedUsers, projectId){
    messages = "These members fail to register:<br><br>";
    for (var i = 0;i < failedUsers.length; i++){
        var additionMsg = " "
        if (failedUsers[i].properties != null && failedUsers[i].properties.length > 0){
            for (var j = 0; j < failedUsers[i].properties.length; j++) {
                additionMsg += "<a href='" + failedUsers[i].properties[j].url + "'>[" + failedUsers[i].properties[j].title + "]</a>";
                console.log(additionMsg);
            }
        }
        messages += "<li><strong>" + failedUsers[i].handle + "</strong> - " + failedUsers[i].reason + additionMsg + "</li>";
        console.log(messages);
    }
    showWarningMessage(messages, "VIEW CHALLENGE", function(){window.open ('/direct/contest/detail?projectId=' + projectId,'_self',false);} )
}

function handleSaveAsDraftContestResultSoftware(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
          mainWidget.softwareCompetition.projectHeader.id = result.projectId;
          modalClose();
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else {
                    showSuccessfulMessageWithOperation("Software Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                        window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                    });
                }
            }else {
                showSuccessfulMessageWithOperation("Software Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                    window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                });
            }
        } else {
          modalClose();
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else {
                    showSuccessfulMessageWithOperation("Software Challenge <span class='messageContestName'>" + contestName + "</span> has been updated successfully.", "VIEW CHALLENGE", function () {
                        window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                    });
                }
            }else {
                showSuccessfulMessageWithOperation("Software Challenge <span class='messageContestName'>" + contestName + "</span> has been updated successfully.", "VIEW CHALLENGE", function () {
                    window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                });
            }
        }

        // update contest title display
        $(".areaHeader .contestTitle").text(contestName);

        //update endDate
        mainWidget.softwareCompetition.endDate = parseDate(result.endDate);
        mainWidget.softwareCompetition.subEndDate = parseDate(result.subEndDate);
        mainWidget.softwareCompetition.paidFee = result.paidFee;
        mainWidget.softwareCompetition.projectMilestoneId = result.projectMilestoneId;
        mainWidget.softwareCompetition.projectMilestoneName = result.projectMilestoneName;
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

function handleSaveAsDraftContestResultStudio(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        if (mainWidget.softwareCompetition.projectHeader.id < 0) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else {
                    showSuccessfulMessageWithOperation("Studio Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                        window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                    });
                }
            }else {
            showSuccessfulMessageWithOperation("Studio Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
            });
            }
        } else {
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else{
                    showSuccessfulMessageWithOperation("Studio Challenge <span class='messageContestName'>" + contestName + "</span> has been updated successfully.", "VIEW CHALLENGE", function () {
                        window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                    });}
            }else{
            showSuccessfulMessageWithOperation("Studio Challenge <span class='messageContestName'>" + contestName + "</span> has been updated successfully.", "VIEW CHALLENGE", function () {
                window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
            });}
        }

        // update contest title display
        $(".areaHeader .contestTitle").text(contestName);

        //update admin fee, to be fixed
        mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = result.paidFee;
        mainWidget.softwareCompetition.endDate = parseDate(result.endDate);

        mainWidget.softwareCompetition.projectMilestoneId = result.projectMilestoneId;
        mainWidget.softwareCompetition.projectMilestoneName = result.projectMilestoneName;

    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

function handleSaveAsDraftContestResultAlgorithm(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else{
                    showSuccessfulMessageWithOperation("Algorithm Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                        window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                    });
                }
            }else {
                showSuccessfulMessageWithOperation("Algorithm Challenge <span class='messageContestName'>" + contestName + "</span> has been saved successfully.", "VIEW CHALLENGE", function () {
                    window.open('/direct/contest/detail?projectId=' + result.projectId, '_self', false);
                });
            }
        } else {
            if (mainWidget.softwareCompetition.isPrivateProject()){
                if (result.failedRegisterUser != null && result.failedRegisterUser.length > 0) {
                    handleFailedRegsiterUsers(result.failedRegisterUser, result.projectId);
                }else{
                    showSuccessfulMessageWithOperation("Algorithm Challenge <span class='messageContestName'>" + contestName +"</span> has been updated successfully.", "VIEW CHALLENGE", function(){window.open ('/direct/contest/detail?projectId=' + result.projectId,'_self',false);});
                }
            }else{
                showSuccessfulMessageWithOperation("Algorithm Challenge <span class='messageContestName'>" + contestName +"</span> has been updated successfully.", "VIEW CHALLENGE", function(){window.open ('/direct/contest/detail?projectId=' + result.projectId,'_self',false);});
            }
        }

        // update contest title display
        $(".areaHeader .contestTitle").text(contestName);

        mainWidget.softwareCompetition.projectHeader.contestAdministrationFee = result.paidFee;
        mainWidget.softwareCompetition.endDate = parseDate(result.endDate);
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}


function showPage(pageId) {
    $('.launchpage').hide();

    // remove the 'Maximum Submissions' property and Allow stock art property
    if (pageId == "contestSelectionPage") {
        delete mainWidget.softwareCompetition.projectHeader.properties['Maximum Submissions'];
        delete mainWidget.softwareCompetition.projectHeader.properties['Allow Stock Art'];
    }

    if (pageId == "overviewSoftwarePage") {
        if (isTechnologyContest()) {
            $('#swTechnologyDiv').show();
        } else {
            $('#swTechnologyDiv').hide();
        }

        if (isPlatformContest()) {
            $('#swPlatformDiv').show();
        } else {
            $('#swPlatformDiv').hide();
        }


        if (isDevOrDesign()) {
            $('#swCatalogDiv').show();
        } else {
            $('#swCatalogDiv').hide();
        }
    }

    if (pageId == "reviewPage") {
        updateReviewStudio();
    }

    if (pageId == "reviewSoftwarePage") {
        updateReviewSoftware();
    }

    if (pageId == "reviewAlgorithmPage") {
        updateReviewAlgorithm();
    }

    if (pageId == "orderReviewPage") {
        updateOrderReviewStudio();
    }

    if (pageId == "orderReviewSoftwarePage") {
        updateOrderReviewSoftware();
    }

    if (pageId == "orderReviewAlgorithmPage") {
        updateOrderReviewAlgorithm();
    }

    $('#' + pageId).show();


    if(pageId == "overviewPage") {
        // studio challenge overview page
        if ($("#checkpointPrizeDiv").is(":visible") && !$("#checkpointPrizeDiv .numSelect select").data('customized')) {
            // for multiple round studio, setup the checkpoint prize section.
            $("#checkpointPrizeDiv .numSelect select").data('customized', true);
            $("#checkpointPrizeDiv .numSelect select").sSelect();
            $('#checkpointPrizeDiv div div div div').html('5');
            $('#checkpointSubmissionNumber').val('5');
            $('#checkpointPrizeDiv div div div ul li:eq(0) a').removeClass('hiLite');
            $('#checkpointPrizeDiv div div div ul li:eq(4) a').addClass('hiLite');
            $('#checkpointPrize').val('50');
        }

        if(isDesignF2F()) {
            // special handling for the "Design First2Finish" page
            $("#overviewPage .prizes .prizesInner").children(":gt(2)").hide();
            $("#overviewPage .prizes .prizesInner input:gt(0)").val('');
            $("#overviewPage .maxSubmissions input").val('');
            $("#overviewPage .maxSubmissions").hide();
        } else {
            $("#overviewPage .prizesInner").children().show();
            $("#overviewPage .maxSubmissions").show();
        }


        $("#studioCupPointsDiv").hide().find("input[type=checkbox]").removeAttr('checked');


        delay(studioPrizeChangeHandler(), 1000);
    }


    if (pageId == "overviewSoftwarePage") {

        if (isDevOrDesign()) {
            $('#catalogSelect').sSelect();
        }
    }

    if (pageId == "overviewSoftwarePage" && $("#swCheckpointPrizeDiv").is(":visible") && !$("#swCheckpointPrizeDiv .numSelect select").data('customized')) {
        $("#swCheckpointPrizeDiv .numSelect select").data('customized', true);
        $("#swCheckpointPrizeDiv .numSelect select").sSelect();
        $('#swCheckpointPrizeDiv div div div div').html('2');
        $('#swCheckpointSubmissionNumber').val('2');
        $('#swCheckpointPrizeDiv div div div ul li:eq(0) a').removeClass('hiLite');
        $('#swCheckpointPrizeDiv div div div ul li:eq(1) a').addClass('hiLite');
        $('#swCheckpointPrize').val('200');
    }

    $('html, body').animate({scrollTop: 0}, 'fast');
}



function isExtraPrizesEmpty(prizeSection) {
  var empty = true;

  $.each($(prizeSection + ' input'),function(i, element){
     if(isNotEmpty($(this).val())) {
        empty = false;
     }
  });

  return empty;
}

/**
 * Gets file types for each studio sub types
 */
function getStudioFileTypes(studioSubtypeId) {
  var types = [];

  $.each(fileTypes, function(i, fileType) {
      if(studioSubtypeId == fileType.id) {
         types = fileType.fileFormats;
      }
  });

  return types;
}

/**
 * Functions for handling documents.
 */
/**
 * Gets all studio document ids.
 *
 * @return studio document id array
 */
function getStudioDocumentIds() {
  return getDocumentIds(swDocuments);
}

/**
 * Gets uploadDocument ids for software competition.
 * It uses comp field to indicate if it is uploadDocument or compDocument
 *
 * @return upload document id array
 */
function getUploadDocumentIds() {
  var uploadDocuments = $.grep(swDocuments, function(doc,i) {
      return !doc['comp'];
  });
  return getDocumentIds(uploadDocuments);
}

/**
 * Gets compDocument ids.
 *
 * @return compDocument id array
 */
function getCompDocumentIds() {
  var compDocuments = $.grep(swDocuments, function(doc,i) {
      return doc['comp'];
  });
  return getDocumentIds(compDocuments);
}

/**
 * Gets document ids.
 *
 * @param docs the documents from which the ids will be extracted
 * @return document id array
 */
function getDocumentIds(docs) {
   return $.map(docs, function(doc, i){
      return doc.documentId;
    });
}

/**
 * Gets the Date object.
 *
 * @param datePart the format of MM/dd/yyyy
 * @param timePart  the format of HH:mm
 * @return Date object
 */
function getDate(datePart, timePart) {
    return Date.parse(datePart + ' ' + timePart, 'MM/dd/yyyy HH:mm EST');
}

function formatDateForRequest(d) {
   if(d == null) {
       return null;
   }
   //rfc3399 format
   return d.toString("yyyy-MM-ddTHH:mm:00");
}

function formatDateForReview(d) {
   if(d == null) {
      return null;
   }
    var dateTime = moment.tz(d.toString("MM/dd/yyyy HH:mm"), "MM/DD/YYYY HH:mm", "America/New_York");
    return dateTime.format('MM/DD/YYYY [at] HH:mm z');
}

function getDatePart(d) {
   if(d == null) {
      return null;
   }
   return d.toString("MM/dd/yyyy");
}

function getTimePart(d) {
   if(d == null) {
      return null;
   }
   return d.toString("HH:mm");
}

function getRoundedTime(d) {
    if(d == null) {
        return null;
    }
    return d.toString("HH:00");
}

function getServerTime() {
    var d = new Date();
    var utc = d.getTime() + (d.getTimezoneOffset() * 60000);
    return new Date(utc - 5 * 3600000); // TC time
}

/**
 * Gets the date object using id prefix.
 *
 * @param idPrefix the id prefix
 * @return Date object
 */
function getDateByIdPrefix(idPrefix) {
  return getDate($('#'+idPrefix+'Date').val(),$('#'+idPrefix+'Time').val());
}

/**
 * Parsing date object. It could be directly date string or the date field of the
 * passed object.
 *
 * @return the <code>Date</code> object from the date object
 */
function parseDate(dateObj) {
     if(!dateObj) {
         return null;
     }

     if(dateObj.date) {
        return Date.parse(dateObj.date,'MM/dd/yyyy HH:mm');
     } else {
         return Date.parse(dateObj,'MM/dd/yyyy HH:mm');
     }
}


/**
 * Gets contest type.
 *
 * @param ignoreTextCheck true if it doesn't need to check the selected text. it will be passed
 *        as true when the method is called from contestTypes change listener
 * @return a 2 element array. first element's value is "STUDIO" or "SOFTWARE"
 *         second element is the sub type id. both could be null if nothing is
 *         selected
 */
function getContestType(ignoreTextCheck) {
   if(!ignoreTextCheck && $('.selectDesing div.selectedTxt').html() == 'Select Challenge Type') {
       return [null,null];
   }
   var typeValues = $('#contestTypes').val().match(/^(STUDIO|SOFTWARE|ALGORITHM)(\d+)$/);
   return [typeValues[1],parseInt(typeValues[2])];
}

function swRefreshDocuments() {
   $('#swDocumentList, #documentList, #alDocumentList').html('');

   var html = "";
   var template = unescape($('#swFileTemplte').html());
   $.each(swDocuments, function(i,doc) {
       html += $.validator.format(template, doc.documentId,doc.fileName, doc.description);
   });

   $('#swDocumentList, #documentList, #alDocumentList').html(html);

   //show requirment document if any
   $.each(swDocuments, function(i,doc) {
       var docId = doc['documentId'];
       if(doc['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID) {
          $('.doc'+docId+'spec').show();
       } else {
         $('.doc'+docId+'spec').hide();
       }
   });

   if(!hasRequirementDocument()) {
        $('.reqDocCheck').show();
        $('#swSpecDoc, #specDoc').attr('checked',true);
   }
}

function hasRequirementDocument() {
   return $.grep(swDocuments, function(doc,i) {
       return doc['documentTypeId'] == REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID;
   }).length > 0;
}

function swRemoveDocument(documentId) {
   var doc = $.grep(swDocuments, function(doc,i){
       return doc.documentId == documentId;
   })[0];
   // if it is comp document, remove it directly
   if(doc['comp']) {
       removeSoftwareDocument(documentId);
       return;
   }

   $.ajax({
      type: 'POST',
      url:  ctx+"/launch/removeDocument",
      data: {
        documentId: documentId
      },
      cache: false,
      dataType: 'json',
      success: swHandleRemoveDocumentResult
   });

}

function swHandleRemoveDocumentResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
      var documentId = result.documentId;
      removeSoftwareDocument(documentId);
    },
    function(errorMessage) {
        showServerError(errorMessage);
    });
}

function removeSoftwareDocument(documentId) {
      $.each(swDocuments, function(i,doc) {
          if(doc && doc.documentId == documentId) {
            swDocuments.splice(i,1);
          }
      });

      swRefreshDocuments();

      if ($(".copilotFileUploadDiv").length > 0) {
        var p = $(".copilotFileUploadDiv input[type=hidden][value=" + documentId + "]").parent().parent();
        if ($(".copilotFileUploadDiv .rowItem p").length == 1) {
            p.find(".addButton").click();
        }
        p.remove();
    }
}

/**
 * Software Prize related function
 */
var customCosts = null;

/**
 * reset the prizes radios/items. It is called when contest type or billing project is changed.
 *
 * NOTE: the actual rendering is in validate or continue method where the category id is applied
 */
function resetSoftwarePrizes() {
     var billingProjectId = parseInt($('select#billingProjects').val());

     $('input[name="prizeRadio"]:nth(1)').attr('checked','checked');

     //reset custom costs
     //customCosts = null;

     /*
     if(isPrizeEditable(billingProjectId)) {
         $('.customRadio').show();
     } else {
         $('.customRadio').hide();
     }
     */
     $('.customRadio').show();
}


function fillStudioPrizes(billingProjectId) {
    if (!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
        return;
    }

    if (billingProjectId == null) {
        billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
    }

    var _PH = mainWidget.softwareCompetition.projectHeader;

    var totalCost = 0;

    var specReview = parseFloat(_PH.getSpecReviewCost());
    var copilotCost = parseFloat($(".copilotFee").val());
    var screeningCost = parseFloat(_PH.getReviewCost());
    var studioCupPoints = parseFloat(_PH.getDRPoints());
    if(_PH.isDrOn() == false) {
        studioCupPoints = 0;
        $("#studioCupPointsCheckBox").removeAttr('checked');
    } else {
        $("#studioCupPointsCheckBox").attr('checked', 'checked');
    }
    var adminFee = parseFloat(_PH.getAdminFee());

    var prizes = _PH.prizes;


    $("#rswSpecCost").text(specReview.formatMoney(2));
    $("#rswReviewCost").text(screeningCost.formatMoney(2));
    $("#rswDigitalRun").text(studioCupPoints.formatMoney(2));
    $("#rswCopilotFee").text(copilotCost);
    $("#rswContestFee").text(adminFee.formatMoney(2));

    totalCost = specReview + screeningCost + studioCupPoints + copilotCost + adminFee;

    $.each(prizes, function(index, p){
        if(p.prizeType.id == CHECKPOINT_PRIZE_TYPE_ID && mainWidget.softwareCompetition.multiRound == true) {
            // checkpoint prize
            totalCost += p.prizeAmount * p.numberOfSubmissions;
            $("#rMPrizesAmount").text("$" + p.prizeAmount.formatMoney(2));
            $("#rMPrizesNumberOfSubmissions").text(p.numberOfSubmissions);
        } else {
            // main prize
            $("#rPrize" + p.place).text("$" + p.prizeAmount.formatMoney(2));
            totalCost += p.prizeAmount;
        }
    });

    $("#rswTotal, span#studioTotal").text(totalCost.formatMoney(2));

}




/**
 * Render the prizes on the page depending on the contest type and prize type.
 * It is called when either contest type or prize type is changed. Therefore it is called in
 * validateFieldsContestSelectionSoftware (it is when contest type is changed) and listener function
 * when the prize radio button is changed.
 *
 * @param billingProjectId if billing project id is not specified, it will use the billing project ID in contest model object.
 *
 * @see updateSoftwarePrizes which is for persisting all changes
 */
function fillPrizes(billingProjectId) {

    if (!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
        return;
    }

    if (billingProjectId == null) {
        billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
    }
    var prizeType = $('input[name="prizeRadio"]:checked').val();
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";

    if(projectCategoryId == SOFTWARE_CATEGORY_ID_F2F
        || projectCategoryId == SOFTWARE_CATEGORY_ID_CODE
        || projectCategoryId == SOFTWARE_CATEGORY_ID_BUG_HUNT) {
        // always use custom prize type for First2Finish or CODE or BUG HUNT contest
        prizeType = 'custom';
    }

    var feeObject = softwareContestFees[projectCategoryId];

    if(!feeObject) {
        return;
    }


    if (!feeObject && mainWidget.isSoftwareContest()) {
        // does not check feeObject is the contest is not software contest
        showErrors('no fee found for project category ' + projectCategoryId);
        return;
    }


    var contestCost = getContestCost(feeObject, prizeType);

    if (contestCost == undefined) {
        return;
    }

    var copilotCost = parseFloat($("input.copilotFee:not([disabled])").val());
    copilotCost = isNaN(copilotCost) ? mainWidget.softwareCompetition.copilotCost : copilotCost;

    var firstPlaceAmount = contestCost.firstPlaceCost.formatMoney(2);
//    originalPrizes = [];
//    originalPrizes.push(contestCost.firstPlaceCost + '');
//    originalPrizes.push(contestCost.drCost + '');

    // prize data
    $('#swFirstPlace').val(firstPlaceAmount);
    $('#rswFirstPlace').html(firstPlaceAmount);

    $('#swSecondPlace,#rswSecondPlace').html(contestCost.secondPlaceCost.formatMoney(2));
    $(".prizesInner_software #prize2").val(contestCost.secondPlaceCost <= 0 ? '' : contestCost.secondPlaceCost);

    $("#rswCopilotFee").html(copilotCost);


    $(".contest_prize td.extraPrize").hide();

    if(contestCost.secondPlaceCost && contestCost.secondPlaceCost > 0 && projectCategoryId == SOFTWARE_CATEGORY_ID_CODE) {
        $('.contest_prize td.extraPrize:eq(0) span').html(contestCost.secondPlaceCost.formatMoney(2)).parent().show();
    }

    if(projectCategoryId != SOFTWARE_CATEGORY_ID_CODE) {
        contestCost.extraPrizes = [];
    }

    if(contestCost.extraPrizes && contestCost.extraPrizes.length > 0) {
        // there are extra prizes, display them
        var extraPrizesNumber = contestCost.extraPrizes.length;
        var hasExtraPrizeGTZero = false;
        for(var i = 0; i < extraPrizesNumber; ++i) {
            if(contestCost.extraPrizes[i] && contestCost.extraPrizes[i] > 0) {
                $(".prizesInner_software #prize" + (i + 3)).val(contestCost.extraPrizes[i]);
                $(".contest_prize td.extraPrize:eq(" + (i + 1) + ") span").html(contestCost.extraPrizes[i].formatMoney(2)).parent().show();
                hasExtraPrizeGTZero = true;
            }
        }

        if(extraPrizesNumber > 0 && hasExtraPrizeGTZero) {
            // click the add button instead of show #swExtraPrizes so the Add button is
            // hidden automatically after clicking
            $(".prizesInner_software .swAdd").click();
        }
    } else if (projectCategoryId != SOFTWARE_CATEGORY_ID_CODE) {
        $("#swExtraPrizes").hide();
        $(".prizesInner_software .swAdd").hide();
    }


    $('#swReviewCost,#rswReviewCost').html(contestCost.reviewBoardCost.formatMoney(2));
    $('#swReliabilityBonus,#rswReliabilityBonus').html(contestCost.reliabilityBonusCost.formatMoney(2));

    if($('#DRCheckbox').is(":checked")) {
        $('#rswDigitalRun').html(contestCost.drCost.formatMoney(2));
        $('#swDigitalRun').val(contestCost.drCost.formatMoney(2));
    } else {
        var noDR = 0;
        $('#rswDigitalRun').html(noDR.formatMoney(2));
        $('#swDigitalRun').val(noDR.formatMoney(2));
    }


    if(contestCost.specReviewCost != undefined) {
        $("#swSpecCost").text(contestCost.specReviewCost.formatMoney(2));
    }

    var contestBillingFee = -1;
    var contestFeePercentage = null;

    var isMultipleRound = mainWidget.softwareCompetition.multiRound;
    // no prize data filled into mainWidget.softwareCompetition
    var domOnly = mainWidget.softwareCompetition.projectHeader.id < 0;

    if (billingFees[billingProjectId] != null) {
        var fees = billingFees[billingProjectId];

        for(var i = 0; i < fees.length; ++i) {
            if(fees[i].contestTypeId == projectCategoryId) {
                contestBillingFee = fees[i].contestFee;
            }
        }
    }

    if (billingFeesPercentage[billingProjectId]!= null) {
        contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;

        if (contestFeePercentage != null) {
            contestBillingFee = (getContestTotal(feeObject, prizeType, domOnly, !isMultipleRound, 0) + copilotCost) * contestFeePercentage;
        }
    }

    if(contestBillingFee >= 0) {
        if (contestFeePercentage != null && contestFeePercentage > 0) {
            $('#rswContestFee').html(contestBillingFee.formatMoney(2) + ' (' + contestFeePercentage * 100 + '% markup)');
            $('#swContestFee').html(contestBillingFee.formatMoney(2));
            $("#swContestFeePercentage").text(' (' + (contestFeePercentage * 100).toFixed(2) + '% markup)');
        } else {
            $('#swContestFee,#rswContestFee').html(contestBillingFee.formatMoney(2));
            $("#swContestFeePercentage").text('');
        }
    } else {
        // no billing is loaded, use the default fee loaded from configuration
        $('#swContestFee,#rswContestFee').html(feeObject.contestFee.formatMoney(2));
        $("#swContestFeePercentage").text('');
    }

    //totals
    if (contestFeePercentage == null) {
        $('#swTotal,#rswTotal').html((getContestTotal(feeObject, prizeType, domOnly, !isMultipleRound, (contestBillingFee >= 0 ? contestBillingFee : null)) + copilotCost).formatMoney(2));
        $('#swPrize_low').html((getContestTotal(feeObject, 'low', domOnly, !isMultipleRound, (contestBillingFee >= 0 ? contestBillingFee : null)) + copilotCost).formatMoney(2));
        $('#swPrize_medium').html((getContestTotal(feeObject, 'medium', domOnly, !isMultipleRound, (contestBillingFee >= 0 ? contestBillingFee : null)) + copilotCost).formatMoney(2));
        $('#swPrize_high').html((getContestTotal(feeObject, 'high', domOnly, !isMultipleRound, (contestBillingFee >= 0 ? contestBillingFee : null)) + copilotCost).formatMoney(2));
    } else {
        $('#swTotal,#rswTotal').html(((getContestTotal(feeObject, prizeType, domOnly, !isMultipleRound, 0) + copilotCost) * (1 + contestFeePercentage)).formatMoney(2));
        $('#swPrize_low').html(((getContestTotal(feeObject, 'low', domOnly, !isMultipleRound, 0) + copilotCost) * (1 + contestFeePercentage)).formatMoney(2));
        $('#swPrize_medium').html(((getContestTotal(feeObject, 'medium', domOnly, !isMultipleRound, 0) + copilotCost) * (1 + contestFeePercentage)).formatMoney(2));
        $('#swPrize_high').html(((getContestTotal(feeObject, 'high', domOnly, !isMultipleRound, 0) + copilotCost) * (1 + contestFeePercentage)).formatMoney(2));
    }

    // spec cost
    if (contestCost.specReviewCost == undefined) {
        $('#swSpecCost,#rswSpecCost').html(feeObject.specReviewCost.formatMoney(2));
    } else {
        $('#swSpecCost,#rswSpecCost').html(contestCost.specReviewCost.formatMoney(2));
    }

   //if custom, make the first place editable
   if(prizeType == 'custom') {
      $('#swFirstPlace').attr('readonly',false);
      $('#swFirstPlace').val(contestCost.firstPlaceCost);

      $('#swDigitalRun').attr('readonly',false);
      $('#swDigitalRun').val(contestCost.drCost);
   } else {
      $('#swFirstPlace').attr('readonly',true);
      $('#swDigitalRun').attr('readonly',true);
   }
}

/**
 * Gets current total contest fee.
 */
function getCurrentContestTotal(useDomElem) {
   if (!mainWidget.softwareCompetition.projectHeader.projectCategory || mainWidget.softwareCompetition.projectHeader.projectCategory.id < 0) {
        return 0;
    }

    var prizeType = $('input[name="prizeRadio"]:checked').val();
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
    var feeObject;
    if (mainWidget.isStudioContest()) {
        for (var i = 0; i < studioSubtypeFees.length; i++) {
            if (studioSubtypeFees[i].id == projectCategoryId) {
                feeObject = studioSubtypeFees[i];
                break;
            }
        }
    } else if (mainWidget.isAlgorithmContest()) {
        for (var i = 0; i < algorithmSubtypeFees.length; i++) {
            if (algorithmSubtypeFees[i].id == projectCategoryId) {
                feeObject = algorithmSubtypeFees[i];
                break;
            }
        }
    } else {
        feeObject = softwareContestFees[projectCategoryId];
    }

    if (!feeObject) {
        showErrors('no fee found for project category' + projectCategoryId);
        return 0;
    }

    if (mainWidget.competitionType == "STUDIO" || mainWidget.competitionType == "ALGORITHM") {
        var total = parseFloat(mainWidget.softwareCompetition.adminFee);
        var prizeInputs = [];
        var lastPrizeIndex = -1;
        var stop = false;
        $.each($('div.prizes .prizesInput'), function (i, element) {
            var value = $.trim($(this).val());
            prizeInputs.push(value);
            if (isNotEmpty(value)) {
                if (!stop) {
                    lastPrizeIndex = i;
                }
            } else {
                stop = true;
            }
        });
        prizeInputs.splice(lastPrizeIndex + 1, 10);

        $.each(prizeInputs, function (i, value) {
            if (checkRequired(value) && checkNumber(value)) {
                total += parseFloat(value);
            }
        });
       if ($('#roundTypes').val() == 'multi') {
           total += parseFloat($('#checkpointPrize').val()) * parseFloat($('#checkpointSubmissionNumber').val());
       }
       // spec review cost
        if (feeObject.specReviewCost) {
            total += feeObject.specReviewCost;
        }
        return total;
   }

   return getContestTotal(feeObject, prizeType, useDomElem);
}

/**
 * It will fill and update the prizes in softwareCompetition object depending on the current contest type, prize type
 * Once you define the contest type and prize type, all values will be determined.
 * Billing will only affect if the custom will show or not.
 */
function updateSoftwarePrizes() {

    //update all fees
    var projectHeader = mainWidget.softwareCompetition.projectHeader;

    // input (1) - prize type
    var prizeType = $('input[name="prizeRadio"]:checked').val();

    // input (2) - contest type
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";

    // input (3) - billing project ID
    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();

    if (projectCategoryId == SOFTWARE_CATEGORY_ID_F2F ||
        projectCategoryId == SOFTWARE_CATEGORY_ID_CODE ||
        projectCategoryId == SOFTWARE_CATEGORY_ID_BUG_HUNT) {
        prizeType = 'custom';
    }

    // we will find out the contest fee
    // 1) check if there is customized contest fee for the billing account
    //  - 1.1 If exists fixed fee, set fixed fee
    //  - 1.2 If exists percentage fee, calculate the fee
    // 2) Otherwise, get from the default configuration - which is fixed fee configured in XML
    var contestFee = -1;


    // get the contest fee object from the default configuration for the project type
    var feeObject = softwareContestFees[projectCategoryId];

    // get contest cost object from the default configuration
    var contestCost = getContestCost(feeObject, prizeType);

    if (contestCost == undefined) {
        return;
    }

    var isMultipleRound = mainWidget.softwareCompetition.multiRound;


    // no prize data filled into mainWidget.softwareCompetition - representing this is in launch contest
    //
    var domOnly = mainWidget.softwareCompetition.projectHeader.id < 0;


    // billingFees and billingFeesPercentage are global objects
    // billingFees store the customized fixed fee for the billing account
    // billingFeesPercentage store the customized percentage fee for the billing account

    if (billingProjectId > 0) {


        if (billingFees != null && billingFees[billingProjectId] != null) {

            var fees = billingFees[billingProjectId];

            if (fees) {
                for (var i = 0; i < fees.length; ++i) {
                    if (fees[i].contestTypeId == projectCategoryId) {
                        contestFee = fees[i].contestFee;
                    }
                }
            }
        }

        if (billingFeesPercentage[billingProjectId] != null) {
            var contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;
            if (contestFeePercentage != null) {
                contestFee = (getContestTotal(feeObject, prizeType, domOnly, !isMultipleRound, 0) + mainWidget.softwareCompetition.copilotCost) * contestFeePercentage;
                projectHeader.setContestFeePercentage(contestFeePercentage);
            } else {
                projectHeader.setContestFeePercentage("0");
            }
        }
    } else {
        contestFee = softwareContestFees[projectCategoryId].contestFee;
    }

    if (contestFee < 0) {
        // still not get contest fee, use default
        contestFee = softwareContestFees[projectCategoryId].contestFee;
    }

    projectHeader.setFirstPlaceCost(contestCost.firstPlaceCost);
    projectHeader.setSecondPlaceCost(contestCost.secondPlaceCost);
    projectHeader.setReviewCost(contestCost.reviewBoardCost);
    projectHeader.setReliabilityBonusCost(contestCost.reliabilityBonusCost);
    projectHeader.setDRPoints(contestCost.drCost);


    if ($("#DRCheckbox").is(":checked")
        && projectCategoryId != SOFTWARE_CATEGORY_ID_F2F
        && projectCategoryId != SOFTWARE_CATEGORY_ID_CODE
        && projectCategoryId != SOFTWARE_CATEGORY_ID_BUG_HUNT) {
        projectHeader.properties['Digital Run Flag'] = 'On';
    } else {
        projectHeader.properties['Digital Run Flag'] = 'Off';
    }

    projectHeader.setCheckpointBonusCost(0);
    projectHeader.setAdminFee(contestFee);

    if (contestCost.specReviewCost == undefined) {
        projectHeader.setSpecReviewCost(feeObject.specReviewCost);
    } else {
        projectHeader.setSpecReviewCost(contestCost.specReviewCost);
    }

    var prizes = [];
    prizes.push(new com.topcoder.direct.Prize(1, contestCost.firstPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));

    var stopAddPrize = false;

    if(contestCost.secondPlaceCost > 0) {
        prizes.push(new com.topcoder.direct.Prize(2, contestCost.secondPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
    } else {
        stopAddPrize = true;
    }

    if(contestCost.extraPrizes && contestCost.extraPrizes.length > 0) {
        for(var i = 0; i < contestCost.extraPrizes.length; ++i) {

            if(!stopAddPrize && contestCost.extraPrizes[i] && contestCost.extraPrizes[i] > 0) {
                prizes.push(new com.topcoder.direct.Prize((i+3), contestCost.extraPrizes[i], CONTEST_PRIZE_TYPE_ID, 1));
            } else {
                stopAddPrize = true;
            }
        }
    }

    if (mainWidget.softwareCompetition.multiRound) {
        prizes.push(new com.topcoder.direct.Prize(1, parseFloat($('#swCheckpointPrize').val()), CHECKPOINT_PRIZE_TYPE_ID, parseInt($('#swCheckpointSubmissionNumber').val())));
    }
    mainWidget.softwareCompetition.projectHeader.prizes = prizes;

    projectHeader.setCostLevel(RADIOVALUE_COSTLEVEL_MAP[prizeType]);

}


/**
 * This method fills and updates the prizes in softwareCompetition object depending on the current Algorithm contest type.
 * Once contest type is defined, all values are determined.
 */
function updateAlgorithmPrizes() {
   //update all fees
   var projectHeader = mainWidget.softwareCompetition.projectHeader;
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = getAlgorithmContestCost(projectCategoryId);
   if (projectHeader.prizes.length == 0) {
       projectHeader.setFirstPlaceCost(feeObject.firstPlaceCost);
       projectHeader.setSecondPlaceCost(feeObject.secondPlaceCost);
       var prizes = [];
       prizes.push(new com.topcoder.direct.Prize(1, feeObject.firstPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
       prizes.push(new com.topcoder.direct.Prize(2, feeObject.secondPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
       prizes.push(new com.topcoder.direct.Prize(1, 0, CHECKPOINT_PRIZE_TYPE_ID, 1));
       projectHeader.prizes = prizes;
       // projectHeader.setDRPoints((feeObject.secondPlaceCost + feeObject.firstPlaceCost) * 0.25);
   }
   projectHeader.setDRPoints(0);
   projectHeader.properties['Digital Run Flag'] = 'off';
   projectHeader.setReviewCost(0);
   projectHeader.setSpecReviewCost(0);
}

/**
 * This method fills and updates the prizes in softwareCompetition object depending on the current Studio contest type.
 * Once contest type is defined, all values are determined.
 */
function updateStudioPrizes() {
    // update all prize cost
    var projectHeader = mainWidget.softwareCompetition.projectHeader;
    var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
    var feeObject = getStudioContestCost(projectCategoryId);
    if (projectHeader.prizes.length == 0) {
        projectHeader.setFirstPlaceCost(feeObject.firstPlaceCost);
        projectHeader.setSecondPlaceCost(feeObject.secondPlaceCost);
        var prizes = [];
        prizes.push(new com.topcoder.direct.Prize(1, feeObject.firstPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
        prizes.push(new com.topcoder.direct.Prize(2, feeObject.secondPlaceCost, CONTEST_PRIZE_TYPE_ID, 1));
        prizes.push(new com.topcoder.direct.Prize(1, 0, CHECKPOINT_PRIZE_TYPE_ID, 1));
        projectHeader.prizes = prizes;
        projectHeader.setDRPoints((feeObject.secondPlaceCost + feeObject.firstPlaceCost) * 0.25);
    }
    projectHeader.setReviewCost(feeObject.reviewCost);
    projectHeader.setSpecReviewCost(feeObject.specReviewCost);


    // do some special cases handling here
    // 1) if challenge is of type design first2finish, no DR points
    if (isDesignF2F() || isIdeaGeneration()) {
        projectHeader.setDRPoints(0);
    }


    // update the contest fee
    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();
    if (billingProjectId > 0) {
        if (billingFeesPercentage[billingProjectId] != null) {
            var contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;
            if (contestFeePercentage != null) {
                var total = 0;
                var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
                for (var i = 0; i < prizes.length; i++) {
                    total += prizes[i].prizeAmount * prizes[i].numberOfSubmissions;
                }
                total += parseFloat(projectHeader.getReviewCost());
                total += parseFloat(projectHeader.getSpecReviewCost());
                total += parseFloat(projectHeader.getDRPoints());
                var contestFee = (total + mainWidget.softwareCompetition.copilotCost) * contestFeePercentage;
                projectHeader.setAdminFee(contestFee);
                projectHeader.setContestFeePercentage(contestFeePercentage);
            } else {
                projectHeader.setContestFeePercentage("0");
            }
        }
    }
}

/**
 * determine if the prize is editable or not
 * if the billing project is not selected, then it is not editable
 */
function isPrizeEditable(billingProjectId) {
   var editable = false;

   $.each(billingInfos, function(i, billingInfo) {
        if(billingInfo.id == billingProjectId) {
           editable = billingInfo.manualPrizeSetting;
        }
    });

    return editable;
}

function onFirstPlaceChangeKeyUp() {
    onSoftwarePrizeInputChange($('#swFirstPlace'), "1st Place Prize", true);
}


/**
 * Handle digital run field key up event.
 */
function onDigitalRunChangeKeyUp() {
   var value = $('#swDigitalRun').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        return;
   }

   onDigitalRunChange();
}

/**
 * Handle checkpoint prize field key up event or checkpoint numberOfSubmission change event.
 */
function onCheckpointPrizeChangeKeyUp() {
    var value = $('#swCheckpointPrize').val();
    if(!checkRequired(value) || !checkNumber(value)) {
        return;
    }

    fillPrizes();
}

//TODO add a similar checking for multiple prizes
function getPrizesForSoftware() {
    var prizesFound = [];
    $(".prizesInner_software input[type=text].prizesInput:visible").each(function(){

        // skip DR
        if($(this).attr('id').indexOf("DigitalRun") != -1) return true

        // skip checkpoint prize
        if($(this).attr('id').indexOf("swCheckpointPrize") != -1) return true;

        if($.trim($(this).val()).length > 0) {
            prizesFound.push(parseFloat($(this).val()));
        } else {
            prizesFound.push(0);
        }
    });

    return prizesFound;
}


function onExtraPirzeChange(_input, _name) {
    var prizeType = $('input[name="prizeRadio"]:checked').val();

    if(prizeType != 'custom') {
        return;
    }

    var value = _input.val();
    if(!checkRequired(value) || !checkNumber(value)) {
        showErrors('The prize value is invalid.');
        return;
    }

    calcPrizes(getPrizesForSoftware());
}

var changeTimer = false;

function onSoftwarePrizeInputChange(_input, _name, required) {

    if(_input.attr('id') != 'swCheckpointPrize') {
        // do not check prize type for checkpoint prize
        var prizeType = $('input[name="prizeRadio"]:checked').val();

        // only apply to custom prize type
        if(prizeType != 'custom') {
            return;
        }
    }

    var value = _input.val();

    if(required == true && $.trim(value).length > 0) {
        if(!checkRequired(value)) {
            showErrors('The <b>' + _name + '</b> should be set');
        }
    }

    if(checkRequired(value) && !checkNumber(value)) {
        var floatValue = parseFloat(value);
        if(isNaN(floatValue)) {
            showErrors('The ' + _name + ' is an invalid prize.');
        }
        return;
    }

    if(changeTimer !== false) clearTimeout(changeTimer);

    changeTimer = setTimeout(function () {
        calcPrizes(getPrizesForSoftware());
    }, 500);
}

function onStudioPrizeInputChange(_input, _name, required) {
    var value = _input.val();

    if(required == true && $.trim(value).length > 0) {
        if(!checkRequired(value)) {
            showErrors('The <b>' + _name + '</b> should be set');
        }
    }

    if(checkRequired(value) && !checkNumber(value)) {
        var floatValue = parseFloat(value);
        if(isNaN(floatValue)) {
            showErrors('The ' + _name + ' is an invalid prize.');
        }
        return;
    }

    if(changeTimer !== false) clearTimeout(changeTimer);

    changeTimer = setTimeout(function () {
        onTheFlyCalculateStudioCosts();
    }, 500);
}

function onTheFlyCalculateStudioCosts() {

    var mainPrizesSum = 0;
    var checkpointPrizesSum = 0;

    $(".studioPrizes input[type=text].prizesInput").each(function(index, _prize) {
        mainPrizesSum += parseFloat($(this).val()) ? parseFloat($(this).val()) : 0;
    })

    if(mainWidget.softwareCompetition.multiRound == true) {
        checkpointPrizesSum = (parseFloat($("#checkpointPrize").val()) ? parseFloat($("#checkpointPrize").val()) : 0) * parseFloat($("#checkpointSubmissionNumber").val());
    }

    var studioCupPoints = (mainPrizesSum + checkpointPrizesSum) * 0.25;

    if($("#studioCupPointsCheckBox").is(":checked") == false) {
        studioCupPoints = 0;
    }

    if(isDesignF2F() || isIdeaGeneration()) {
        studioCupPoints = 0;
        $("#studioCupPointsCheckBox").removeAttr('checked').parent().hide();
    }

    var specReviewCost = 0;
    var screeningCost = 0;

    var copilotCost = parseFloat(mainWidget.softwareCompetition.projectHeader.properties['Copilot Cost']);

    if(!copilotCost) {
        copilotCost = mainWidget.softwareCompetition.copilotCost;
    }

    var billingProjectId = mainWidget.softwareCompetition.projectHeader.getBillingProject();

    var reviewType = isDesignType() ? "INTERNAL" : "COMMUNITY";

    $.ajax({
        type: 'POST',
        url: ctx + "/launch/getReviewCostAjax",
        data: {'projectCategoryId': mainWidget.softwareCompetition.projectHeader.projectCategory.id,
            'prize': mainPrizesSum, projectId : mainWidget.softwareCompetition.projectHeader.id,
            reviewType: reviewType},
        cache: false,
        dataType: 'json',
        async: true,
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
                function (result) {
                    if (result) {
                        if (result['Screening']) {
                            screeningCost += result['Screening'];
                        }
                        if (result['Review']) {
                            screeningCost += result['Review'];
                        }

                        if (result['Specification Review']) {
                            specReviewCost = result['Specification Review'];

                            if (typeof(contestHasSpecReview) !== 'undefined' && contestHasSpecReview == false) {
                                specReviewCost = 0;
                            }
                        }

                        $("#studioSpecReviewFee").text(specReviewCost.formatMoney(2));
                        $("#studioScreeningCost").text(screeningCost.formatMoney(2));
                        $("#studioCupPoints").text(studioCupPoints.formatMoney(2));
                        $("#studioCopilotFee").text(copilotCost.formatMoney(2));

                        var contestBillingFee;
                        var contestFeePercentage;
                        var feeObject;

                        for (var i = 0; i < studioSubtypeFees.length; i++) {
                            if (studioSubtypeFees[i].id == mainWidget.softwareCompetition.projectHeader.projectCategory.id) {
                                feeObject = studioSubtypeFees[i];
                                break;
                            }
                        }


                        if (billingFees[billingProjectId] != null) {
                            var fees = billingFees[billingProjectId];

                            for(var i = 0; i < fees.length; ++i) {
                                if(fees[i].contestTypeId == mainWidget.softwareCompetition.projectHeader.projectCategory.id) {
                                    contestBillingFee = fees[i].contestFee;
                                }
                            }
                        }

                        if (billingFeesPercentage[billingProjectId]!= null) {
                            contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;

                            if (contestFeePercentage != null) {
                                contestBillingFee = (mainPrizesSum + checkpointPrizesSum + studioCupPoints + specReviewCost + screeningCost + copilotCost) * contestFeePercentage;
                            }
                        }

                        if(contestBillingFee >= 0) {
                            if (contestFeePercentage != null && contestFeePercentage > 0) {
                                $('#studioAdminFee').html(contestBillingFee.formatMoney(2) + ' (' + contestFeePercentage * 100 + '% markup)');
                            } else {
                                $('#studioAdminFee').html(contestBillingFee.formatMoney(2));
                            }
                        } else {
                            // no billing is loaded, use the default fee loaded from configuration
                            $('#studioAdminFee').html(feeObject.contestFee.formatMoney(2));
                            contestBillingFee = feeObject.contestFee;
                        }

                        //totals
                        $('#studioTotal').html( (  mainPrizesSum + checkpointPrizesSum + studioCupPoints + specReviewCost + screeningCost + copilotCost + contestBillingFee ).formatMoney(2));
                    }
                },
                function (errorMessage) {
                    showServerError(errorMessage);
                })
        }
    });

}

/**
 * Calculates the second place cost, reliability cost, DR cost, spec review cost, review cost according to the given
 * first place prize. It only applies to software contests with custom prize type.
 *
 * The review prize is calcualted by calling getReviewCostAjax.
 *
 * @param firstPlacePrizeValue the first place prize
 */
function calcPrizes(prizes) {

   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";
   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data (populate the custom cost object in feeObject)
   var firstPlace = parseFloat(prizes[0]);
   var contestCost = getContestCost(feeObject, 'custom');
   var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
   contestCost.firstPlaceCost = firstPlace;
   if(prizes.length > 1) {
       // has second prize input
       contestCost.secondPlaceCost = parseFloat(prizes[1]);

       if(prizes.length > 2) {
           // there is extra prizes
           var extraPrizes = [];
           for(var k = 2; k < prizes.length; ++k) {
               extraPrizes.push(parseFloat(prizes[k]));
           }
           contestCost.extraPrizes = extraPrizes;
       }

   } else {
       contestCost.secondPlaceCost = calculateSecondPlacePrize(contestCost.firstPlaceCost);
   }


    if (projectCategoryId != REPORTING_ID && projectCategoryId != SOFTWARE_CATEGORY_ID_CODE
        && projectCategoryId != SOFTWARE_CATEGORY_ID_F2F && projectCategoryId != SOFTWARE_CATEGORY_ID_BUG_HUNT)
    {
        contestCost.reliabilityBonusCost = calculateReliabilityPrize(contestCost.firstPlaceCost,contestCost.secondPlaceCost,categoryId);
    } else {
        contestCost.reliabilityBonusCost = 0;
    }

   if (projectCategoryId != REPORTING_ID && projectCategoryId != SOFTWARE_CATEGORY_ID_CODE
       && projectCategoryId != SOFTWARE_CATEGORY_ID_F2F && projectCategoryId != SOFTWARE_CATEGORY_ID_BUG_HUNT)
   {
       contestCost.drCost = calculateDRPoint(contestCost.firstPlaceCost, contestCost.secondPlaceCost, contestCost.reliabilityBonusCost);
   } else {
       contestCost.drCost = 0;
   }

    if(projectCategoryId == SOFTWARE_CATEGORY_ID_CODE) {
        // Code contest does not have spec review
        contestCost.specReviewCost = 0;
    }

    if(projectCategoryId == SOFTWARE_CATEGORY_ID_F2F) {
        // First2Finish contest does not have spec review and second place, only has 1st place prize
        contestCost.secondPlaceCost = 0;
        contestCost.specReviewCost = 0;
    }

    if(projectCategoryId == SOFTWARE_CATEGORY_ID_BUG_HUNT) {
        // Bug hunt contest will only have 1st place prize
        contestCost.secondPlaceCost = 0;
    }

    // get review type
    var reviewType = isDesignType() ? "INTERNAL" : "COMMUNITY";

    if(isCode() || isF2F()) {
        // read from the choice
        if('internal' == $("input[name=reviewType]:checked").val()) {
            reviewType = "INTERNAL";
        } else {
            reviewType = "COMMUNITY";
        }
    }

    $.ajax({
        type: 'POST',
        url:  ctx+"/launch/getReviewCostAjax",
        data: {'projectCategoryId': categoryId, 'prize': contestCost.firstPlaceCost,
            reviewType: reviewType, projectId : mainWidget.softwareCompetition.projectHeader.id},
        cache: false,
        dataType: 'json',
        async : true,
        success: function (jsonResult) {
            handleJsonResult(jsonResult,
            function(result) {
                if(result) {
                    var reviewBoardCost = 0;

                    if (result['Specification Review']) {
                        contestCost.specReviewCost = result['Specification Review'];

                        if(typeof(contestHasSpecReview) !== 'undefined' && contestHasSpecReview == false) {
                            contestCost.specReviewCost = 0;
                        }
                    }

                    if (result['Screening']) {
                        reviewBoardCost += result['Screening'];
                    }
                    if (result['Review']) {
                        reviewBoardCost += result['Review'];
                    }
                    if (result['Aggregation']) {
                        reviewBoardCost += result['Aggregation'];
                    }
                    if (result['Final Review']) {
                        reviewBoardCost += result['Final Review'];
                    }
                    if (result['Iterative Review']) {
                        reviewBoardCost += result['Iterative Review'];
                    }


                    contestCost.reviewBoardCost = reviewBoardCost;

                    var billingProjectId = $('select#billingProjects').val();

                    // if the first prize value has been changed, this request should be ignored
                    if (contestCost.firstPlaceCost == prizes[0]) {
                        fillPrizes(billingProjectId);
                    }
                }
            },
            function(errorMessage) {
                showServerError(errorMessage);
            })
        }
   });
}

/**
 * Handle digital run field change event.
 */
function onDigitalRunChange() {
   var prizeType = $('input[name="prizeRadio"]:checked').val();
   if(prizeType != 'custom') {
       return;
   }

   var value = $('#swDigitalRun').val();
   if(!checkRequired(value) || !checkNumber(value)) {
        showErrors('digital run value is invalid.');
        return;
   }

    calcDR(value);
   fillPrizes();
}

function calcDR(drPoints){
   //fee object
   var projectCategoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id + "";

   var feeObject = softwareContestFees[projectCategoryId];

   //update custom cost data
   var contestCost = getContestCost(feeObject, 'custom');
   contestCost.drCost = parseFloat(drPoints);
}

function calculateSecondPlacePrize(firstPlaceCost) {
    return firstPlaceCost * 0.5;
}

function calculateReliabilityPrize(firstPlacePrize, secondPlacePrize, categoryId) {
    return (firstPlacePrize + secondPlacePrize) * 0.2;
}

function calculateDRPoint(firstPlacePrize, secondPlacePrize, reliabilityPrize) {
   return (firstPlacePrize + secondPlacePrize + reliabilityPrize) * 0.25;
}

function getContestTotal(feeObject, prizeType, useDomElem, noCheckpointCost, actualFee) {
    if(feeObject == undefined) {
        return;
    }
    var contestCost = getContestCost(feeObject, prizeType);
    var total = contestCost.firstPlaceCost + contestCost.secondPlaceCost + contestCost.reviewBoardCost
    + contestCost.reliabilityBonusCost + ($('#DRCheckbox').is(":checked") ? contestCost.drCost : 0) + (actualFee == null ? feeObject.contestFee : actualFee)
    + (contestCost.specReviewCost != undefined ? contestCost.specReviewCost : feeObject.specReviewCost);


    // check if there are multiple prizes
    if(contestCost.extraPrizes && contestCost.extraPrizes.length > 0) {
        for(var i = 0; i < contestCost.extraPrizes.length; ++i) {
            total += contestCost.extraPrizes[i];
        }
    }

    if (noCheckpointCost) {
        return total;
    }
    if (!useDomElem) {
        var prizes = mainWidget.softwareCompetition.projectHeader.prizes;
        for (var i = 0; i < prizes.length; i++) {
            if (prizes[i].prizeType.id == CHECKPOINT_PRIZE_TYPE_ID) {
                total += prizes[i].prizeAmount * prizes[i].numberOfSubmissions;
            }
        }
    } else if (mainWidget.softwareCompetition.multiRound) {

        var prize = parseFloat($("#swCheckpointPrize").val());
        if (!prize) {
            prize = 0;
        }
        total += prize * parseFloat($("#swCheckpointSubmissionNumber").val());
    }

    return total;
}

// get contest cost object from the default configuration

function getContestCost(feeObject, prizeType) {

    var contestCostObject;

    if (prizeType == 'custom') {
        //If custom costs is not set, use medium to initalize it
        customCosts = customCosts || $.extend({}, getContestCost(feeObject, 'medium'));
        contestCostObject = customCosts;
    } else {
        contestCostObject = $.grep(feeObject.contestCost.contestCostBillingLevels, function (cost, i) {
            return cost.id == prizeType;
        })[0];
    }

    if(mainWidget.softwareCompetition.multiRound == true && contestCostObject) {
        contestCostObject.checkpointCost = ( parseFloat($("#swCheckpointPrize").val()) ? parseFloat($("#swCheckpointPrize").val()) : 0 ) * parseFloat($("#swCheckpointSubmissionNumber").val());

    }

    return contestCostObject;

}

function getStudioContestCost(projectCategoryId) {
    for (var i = 0; i < studioSubtypeFees.length; i++) {
        if (studioSubtypeFees[i].id == projectCategoryId) {
            return studioSubtypeFees[i];
        }
    }
}

function getAlgorithmContestCost(projectCategoryId) {
    for (var i = 0; i < algorithmSubtypeFees.length; i++) {
        if (algorithmSubtypeFees[i].id == projectCategoryId) {
            return algorithmSubtypeFees[i];
        }
    }
}


/**
 * Software Technology/Category functions
 */
function sortCategorySelects() {
   sortSelectOptions('select1_categories');
   sortSelectOptions('select2_categories');
}

function updateCategories(callback) {
   var catalogId = $('#catalogSelect').val();

   // reset categories
   $('#select1_categories').children().remove();
   $('#select2_categories').children().remove();

   if(catalogId <= 0 ) {
       return;
   }

   // loading categories data for the given catalog id
   $.ajax({
      type: 'POST',
      url:  ctx+"/launch/getCategories",
      data: {'catalogId' : catalogId },
      cache: false,
      dataType: 'json',
      async : false,
      success: function (jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
             if(result) {
                $.each(result, function(i, category) {
                     $("<option/>").val(category.id).text(category.name).appendTo("#select1_categories");
                });

                if(typeof callback == 'function') {
                   callback();
                }
             }
          },
          function(errorMessage) {
              showServerError(errorMessage);
          })
      }
   });
}

function fillCategories() {
         $('#select1_categories').val(mainWidget.softwareCompetition.assetDTO.directjsCategories);
       $('#select1_categories option:selected').appendTo('#select2_categories');
       sortCategorySelects();
}


/**
 * Shared validation functions
 */
function validateContestName(contestName, errors) {
   if(!checkRequired(contestName)) {
       errors.push('Challenge name is empty.');
   }
}

function validateTcProject(tcProjectId, errors) {
   if(tcProjectId < 0) {
       errors.push('No project is selected.');
   }
}

function validateDirectProjectMilestone(milestoneId, errors) {

    if(milestoneId <= 0) {
        errors.push("Please associate this competition to a project milestone. If you don’t have any milestones, please create some now.");
    }
}

function validateCodePrizes(errors) {
    var prizeInputs = [];
    var lastPrizeIndex = -1;
    var errorsAdded = false;
    var stop = false;
    var $prizeElements;

    $prizeElements = $('div.prizes .prizesInner_software .prizesInput');

    $.each($prizeElements,function(i, element) {
        if($(this).attr('id') == 'swDigitalRun' || $(this).attr('id') == 'swCheckpointPrize') {
            return true;
        }

        var value = $.trim($(this).val());
        prizeInputs.push(value);
        if(isNotEmpty(value)) {
            if (!stop) {
                lastPrizeIndex = i;
            }
        } else {
            stop = true;
        }
    });

    // get the valid part of the prize array
    var invalidPart = prizeInputs.splice(lastPrizeIndex + 1, 10);

    // do the checking on the invalid part
    $.each(invalidPart, function(index, value){
        if(isNotEmpty(value)) {
            errors.push("<b>Prize #" + (lastPrizeIndex + index + 2) + "</b> should not be set when <b>Prize #" + (lastPrizeIndex + 2) + "</b> is not set");
            errorsAdded = true;
        }
    });

    // validation the prize number, there should be at least 1st place prize set
    if (prizeInputs.length < 1) {
        errors.push('At least first place prize should be set');
        errorsAdded = true;
    }

    var prizes = [];
    $.each(prizeInputs, function (i, value) {
        if (!checkRequired(value) || !checkNumber(value)) {
            errors.push('Prize ' + (i + 1) + ' is invalid.');
            errorsAdded = true;
        } else {
            prizes.push(new com.topcoder.direct.Prize((i + 1), parseFloat(value), CONTEST_PRIZE_TYPE_ID, 1));
        }
    });

    //check prize order
    mainWidget.softwareCompetition.projectHeader.setSecondPlaceCost(0);

    if (errors.length == 0) {
        var prevPrize = -1;
        $.each(prizes, function (i, value) {
            if (i != 0 && value.prizeAmount > prevPrize) {
                errors.push('Prize ' + (i + 1) + ' is too large, it should less or equal to previous prize: $' + prevPrize);
                errorsAdded = true;
            }
            if (value.prizeType.id == CONTEST_PRIZE_TYPE_ID) {
                if (value.place == 1) {
                    mainWidget.softwareCompetition.projectHeader.setFirstPlaceCost(value.prizeAmount);
                } else if (value.place == 2) {
                    mainWidget.softwareCompetition.projectHeader.setSecondPlaceCost(value.prizeAmount);
                }
            }

            prevPrize = value.prizeAmount;
        });

        // add prizes.length > 1 to handle the case that there is only one first place prize
        if (prizes.length > 1 && prizes[1].prizeAmount > 0 && (prizes[1].prizeAmount < 0.2 * prizes[0].prizeAmount)) {
            errors.push('Second place prize should at least be 20% of First place prize');
            errorsAdded = true;
        }
    }
    if (!errorsAdded) {
        if (phaseOpen) {
            var newPrizes = [];
            newPrizes.push($("#swFirstPlace").val());
            for (var i = 1; i <= 5; i++) {
                var value = $('.prizesInner_software #prize' + i).val();
                if ($.trim(value).length > 0) {
                    newPrizes.push(value);
                }
            }

            if (newPrizes.length < originalPrizes.prizes.length) {
                errors.push('The prizes can not be deleted');
            } else {
                var n = Math.min(newPrizes.length, originalPrizes.length);
                for (var i = 0; i < n; i++) {
                    if (parseFloat(newPrizes[i]) < parseFloat(originalPrizes[i])) {
                        errors.push('The prizes can not be decreased');
                        break;
                    }
                }
            }
        }
    }

    return prizes;
}

function validatePrizes(errors) {
    var prizeInputs = [];
    var lastPrizeIndex = -1;
    var errorsAdded = false;
    var $prizeElements;

    if (mainWidget.isStudioContest()) {
        $prizeElements = $('div.prizes .prizesInput');
    } else {
        $prizeElements = $('div.alPrizes .prizesInput');
    }

    $.each($prizeElements, function (i, element) {
        var value = $.trim($(this).val());
        if (isNotEmpty(value)) {
            prizeInputs.push(value);
        } else {
            return false;
        }
    });

    var requiredPrizeNumber = isDesignF2F() ? 1 : 2;

    //validation
    if (prizeInputs.length < requiredPrizeNumber) {
        errors.push('At least ' + (requiredPrizeNumber >= 2 ? 'First & Second place prizes' : 'First place prize') + ' should be set');
        errorsAdded = true;
    }

    var prizes = [];
    $.each(prizeInputs, function (i, value) {
        if (!checkRequired(value) || !checkNumber(value)) {
            errors.push('Prize ' + (i + 1) + ' is invalid.');
            errorsAdded = true;
        } else {
            prizes.push(new com.topcoder.direct.Prize((i + 1), parseFloat(value), CONTEST_PRIZE_TYPE_ID, 1));
        }
    });

    //check prize order
    if (errors.length == 0) {
        var prevPrize = -1;
        $.each(prizes, function (i, value) {
            if (i != 0 && value.prizeAmount > prevPrize) {
                errors.push('Prize ' + (i + 1) + ' is too large.');
                errorsAdded = true;
            }
            if (value.prizeType.id == CONTEST_PRIZE_TYPE_ID) {
                if (value.place == 1) {
                    mainWidget.softwareCompetition.projectHeader.setFirstPlaceCost(value.prizeAmount);
                } else if (value.place == 2) {
                    mainWidget.softwareCompetition.projectHeader.setSecondPlaceCost(value.prizeAmount);
                }
            }

            prevPrize = value.prizeAmount;
        });

        if (!isDesignF2F() &&
            prizes[1].prizeAmount < 0.2 * prizes[0].prizeAmount) {
            errors.push('Second place prize should at least be 20% of first place prize.');
            errorsAdded = true;
        }
    }
    if (!errorsAdded) {
        if (phaseOpen) {
            var newPrizes = [];
            for (var i = 1; i <= 5; i++) {
                var value = $('#prize' + i).val();
                if ($.trim(value).length > 0) {
                    newPrizes.push(value);
                }
            }
            ;

            if (newPrizes.length < originalPrizes.length) {
                errors.push('The prizes can not be deleted');
            } else {
                var n = Math.min(newPrizes.length, originalPrizes.length);
                for (var i = 0; i < n; i++) {
                    if (parseFloat(newPrizes[i]) < parseFloat(originalPrizes[i])) {
                        errors.push('The prizes can not be decreased');
                        break;
                    }
                }
            }
        }
    }

    return prizes;
}

/**
 * Validates points.
 * @param errors array of errors
 * @returns points
 */
function validatePoints(errors) {
    if (!hasGroupSelected()) {
        return [];
    }
    var pointInputs = [];
    var lastPointIndex = -1;
    var errorsAdded = false;
    var $pointElements;

    if (mainWidget.isStudioContest()) {
        $pointElements = $('#stPoints .pointsInput:visible');
    } else if (mainWidget.isSoftwareContest()) {
        $pointElements = $('#swPoints .pointsInput:visible');
    } else {
        $pointElements = $('#alPoints .pointsInput:visible');
    }

    $.each($pointElements,function(i, element) {
        var value = $.trim($(this).val());
        if (isEmpty(value)) {
            pointInputs.push(undefined);
        } else if(checkNumber(value)) {
            pointInputs.push(parseFloat(value));
        } else {
            pointInputs.push(NaN);
        }
    });

    // do the checking on the invalid part
    $.each(pointInputs, function(index, value){
        if (index > 0 && value !== undefined && pointInputs[index - 1] === undefined) {
            errors.push("<b>Point #" + (index + 1) + "</b> should not be set when <b>Point #" + (index) + "</b> is not set");
            errorsAdded = true;
        }
    });

    var points = [];
    $.each(pointInputs, function (i, value) {
        if (value !== undefined) {
            if (isNaN(value)) {
                errors.push('Point ' + (i + 1) + ' is invalid.');
                errorsAdded = true;
            } else {
                points.push(new com.topcoder.direct.Prize((i + 1), parseFloat(value), CHALLENGE_POINT_TYPE_ID, 1));
            }
        }
    });

    //check point order
    if (errors.length == 0) {
        var prevPoint = -1;
        $.each(points, function (i, value) {
            if (i != 0 && value.prizeAmount > prevPoint) {
                errors.push('Point ' + (i + 1) + ' is too large, it should less or equal to previous point: ' + prevPoint);
                errorsAdded = true;
            }

            prevPoint = value.prizeAmount;
        });

        // add points.length > 1 to handle the case that there is only one first place point
        if (points.length > 1 && points[1].prizeAmount > 0 && (points[1].prizeAmount < 0.2 * points[0].prizeAmount)) {
            errors.push('Second place point should at least be 20% of First place point');
            errorsAdded = true;
        }
    }

    return points;
}

function validateFileTypes(errors) {
    //file deliverables
    var fileTypes = [];
    $.each($('#deliverablesCheckboxs input.defaultFileType'),function(i, element) {
       if($(this).is(':checked')) {
           fileTypes.push($(this).val());
       }
    });

    var otherFileTypes = [];
    $.each($('#deliverablesCheckboxs input.fileInput'),function(i, element) {
       var value = $.trim($(this).val());
       if($(this).prev().is(':checked') && isNotEmpty(value)) {
           otherFileTypes.push(value);
       }
    });

   if(fileTypes.length ==0 && otherFileTypes.length == 0 ) {
       errors.push("No file deliverable type is selected.");
   }

    $.each(otherFileTypes,function(i, value) {
       if(!checkFileType(value)) {
            errors.push("File type "+ value +" is invalid.");
       }
    });

   return [fileTypes,otherFileTypes];
}

/**
 * Validates effort days estimate.
 * @param errors array of errors
 */
function validateEffortDaysEstimate(errors) {
  if (jQuery_1_11_1('.effortEstimateRow:visible').length > 0) {
    var effortOffshore = $("input[name=effortDaysEstimateOffshore]").val();
    var effortOnsite = $("input[name=effortDaysEstimateOnsite]").val();
    if (isNotEmpty(effortOffshore) && !checkNumber(effortOffshore) && !(effortOffshore > 0)) {
        errors.push("The estimate effort offshore should be a positive number");
    }
    if (isNotEmpty(effortOnsite) && !checkNumber(effortOnsite) && !(effortOnsite > 0)) {
        errors.push("The estimate effort onsite should be a positive number");
    }
    if (isEmpty(effortOffshore) && isEmpty(effortOnsite)) {
        errors.push("The estimate effort for offshore and/or onsite is required");
    }
  }
}

/**
 * Checks to see if the technology is needed for the contest
 */
function isTechnologyContest() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory || isDesignType()) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       //all except for concept, spec and content creation.
       return !((categoryId == SOFTWARE_CATEGORY_ID_CONCEPT) || (categoryId == SOFTWARE_CATEGORY_ID_SPEC)
                         || (categoryId == SOFTWARE_CATEGORY_ID_CONTENT) || (categoryId == ALGORITHM_CATEGORY_ID_MARATHON) );
   }
}

function isPlatformContest() {
    return isTechnologyContest();
}


function isDevOrDesign() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DESIGN) || (categoryId == SOFTWARE_CATEGORY_ID_DEVELOPMENT);
   }
}

function isDev() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DEVELOPMENT);
   }
}

function isDesign() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_DESIGN);
   }
}

/**
 * Checks whether the current contest to create is of type assembly
 * @since 1.9
 */
function isAssembly() {
   if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
       return false;
   } else {
       var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
       return (categoryId == SOFTWARE_CATEGORY_ID_ASSEMBLY);
   }
}


function isBugHunt() {
    if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
        return false;
    } else {
        var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        return (categoryId == SOFTWARE_CATEGORY_ID_BUG_HUNT);
    }
}

function isCode() {
    if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
        return false;
    } else {
        var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        return (categoryId == SOFTWARE_CATEGORY_ID_CODE);
    }
}

function isF2F() {
    if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
        return false;
    } else {
        var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        return (categoryId == SOFTWARE_CATEGORY_ID_F2F);
    }
}

function isDesignF2F() {
    if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
        return false;
    } else {
        var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        return (categoryId == STUDIO_CATEGORY_ID_DESIGN_F2F);
    }
}

function isIdeaGeneration() {
    if(!mainWidget.softwareCompetition.projectHeader.projectCategory) {
        return false;
    } else {
        var categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        return (categoryId == STUDIO_IDEA_GENERATION);
    }
}

function isDesignType(categoryId) {
    if(mainWidget.softwareCompetition.projectHeader.projectCategory) {
         categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
    }

    return (getProjectCategoryById(categoryId).typeName == 'Studio');
}

function isCopilotPosting(categoryId) {
    if(mainWidget.softwareCompetition.projectHeader.projectCategory) {
        categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
    }

    return (categoryId == COPILOT_POSTING);
}

function isDevelopmentType(categoryId) {
    if(mainWidget.softwareCompetition.projectHeader.projectCategory) {
        categoryId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
    }

    return (getProjectCategoryById(categoryId).typeName == 'Application'
        || getProjectCategoryById(categoryId).typeName == 'Component');
}

function beforeAjax() {
     modalPreloader();
}

function afterAjax() {
     modalClose();
}

function getDRTemplatesName(categoryId) {
    if (categoryId == 14) {
        return "assembly_detailed_requirements_templates";
    } else if (categoryId == 19) {
        return "ui_prototype_detailed_requirements_templates";
    } else if (categoryId == 7) {
        return "architecture_detailed_requirements_templates";
    } else if (categoryId == 23) {
        return "conceptualization_detailed_requirements_templates";
    } else if (categoryId == 6) {
        return "specification_detailed_requirements_templates";
    } else if (categoryId == 9) {
        return "bug_hunt_detailed_requirements_templates";
    } else if (categoryId == 26) {
        return "test_scenarios_detailed_requirements_templates";
    } else if (categoryId == 13) {
        return "test_suites_detailed_requirements_templates";
    } else {
        return "default_detailed_requirements_templates";
    }
}

function getSGTemplatesName(categoryId) {
    if (categoryId == 14) {
        return "assembly_software_guidelines_templates,salesforce_software_guidelines_templates";
    } else if (categoryId == 19) {
        return "ui_prototype_software_guidelines_templates";
    } else if (categoryId == 7) {
        return "architecture_software_guidelines_templates";
    } else if (categoryId == 23) {
        return "conceptualization_software_guidelines_templates";
    } else if (categoryId == 6) {
        return "specification_software_guidelines_templates";
    } else if (categoryId == 9) {
        return "bug_hunt_software_guidelines_templates";
    } else if (categoryId == 26) {
        return "test_scenarios_software_guidelines_templates";
    } else if (categoryId == 13) {
        return "test_suites_software_guidelines_templates";
    } else if (categoryId == 38 || categoryId == 39){
        return "default_software_guidelines_templates,salesforce_software_guidelines_templates";
    }else {
        return "default_software_guidelines_templates";
    }
}

function getStudioTemplatesName(categoryId) {
    if (categoryId == 32) {
        return "app_frontend_contest_spec_templates";
    } else if (categoryId == 16) {
        return "banner_or_icon_design_contest_spec_templates";
    } else if (categoryId == 31) {
        return "front-end_flash_design_contest_spec_templates";
    } else if (categoryId == 22) {
        return "ideagen_contest_spec_templates";
    } else if (categoryId == 20) {
        return "logo_design_contest_spec_templates";
    } else if (categoryId == 30) {
        return "widget_or_mobile_design_contest_spec_templates";
    } else if (categoryId == 21) {
        return "print_or_presentation_design_contest_spec_templates";
    } else if (categoryId == 34) {
        return "studio_other_design_contest_spec_templates";
    } else if (categoryId == 17) {
        return "web_design_contest_spec_templates";
    } else if (categoryId == 18) {
        return "wireframe_contest_spec_templates";
    } else {
        return "default_studio_contest_spec_templates";
    }
}

function showChallengeSaveConfiguration(saveFunction) {
    displayUserConfirmation("#saveChallengeConfirmation", "Save Challenge Confirmation",
        "Draft challenges scheduled to start in the future are visible in the upcoming challenge section of topcoder.com." +
        " Visible details include the challenge name, prizes, timeline, platform, and technologies. Change these details" +
        " as needed before saving the challenge as a draft.", "Save As Draft", saveFunction
        , "Cancel");

    $("#saveChallengeConfirmation .checkbox input").unbind('change');
    $("#saveChallengeConfirmation .checkbox input").bind('change', function() {
        var flag = !$(this).is(":checked");

        $.ajax({
            type: 'POST',
            url: "saveChallengeConfirmation",
            data: {flag: flag},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult2(jsonResult, function(result) {
                    showSaveChallengeConfirmation = (result.value == "true");
                }, function(errorMessage){
                    showErrors(errorMessage);
                })
            }
        });

    });
}

function validateCopilotFee(value, showError){
    var error = null;
    if (value == null || value == ""){
        value = 0;
        $(".copilotFee").val(value);
    }
    if (!checkNumber(value) || value < 0){
        error = "Copilot fee must be a valid number which greater or equal than 0 and up to 2 digits decimal.";
        if (showError) showErrors(error);
        return error;
    }
    var floatValue = parseFloat(value);
    if(isNaN(floatValue)) {
        error = "Invalid value of copilot fee";
        if (showError) showErrors(error);
        return error;
    }
    if(!/^\d+(\.\d{0,2})?$/.test(floatValue)){
        if (showError) showErrors("More than 2 digits decimal for copilot fee, will be truncated");
    }
    if ($("#contestCopilot").val() == 0 && floatValue !=0){
        floatValue = 0;
        $(".copilotFee").val(floatValue);
    }
    var fixFloat = floatValue.toFixed(2);
    $(".copilotFee").val(fixFloat);
    mainWidget.softwareCompetition.copilotCost = parseFloat(fixFloat);

    return error;
}

function sortByname(A, B){
    var a = A.name.toLowerCase();
    var b = B.name.toLowerCase();
    return a < b ? -1 : ((a > b) ? 1 : 0);
}

function technologyAndPlatformSelectsChanged() {
    var hasJavaTech = false;
    var hasSalesforcePlatform = false;
    if (isCopilotPosting()){
        return {hasJavaTech: hasJavaTech, hasSalesforcePlatform: hasSalesforcePlatform};
    }

    var selectedTechnologies = jQuery_1_11_1("#technologies").magicSuggest().getSelection();
    $(selectedTechnologies).each(function (val, i) {
        if (val.name == 'Java')
            hasJavaTech=true;
    });

    var selectedPlatforms = jQuery_1_11_1("#platforms").magicSuggest().getSelection();
    $(selectedPlatforms).each(function (val, i) {
        if (val.name == 'Salesforce.com')
            hasSalesforcePlatform=true;
    });

    if(hasJavaTech || hasSalesforcePlatform) {
        $("#swThurgoodDiv").show();
    } else {
        $("#swThurgoodDiv").hide();
    }

    return {hasJavaTech: hasJavaTech, hasSalesforcePlatform: hasSalesforcePlatform};
}
