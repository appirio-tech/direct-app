/**
 * The JavaScript code used by the Launch Copilot Posting Contest views.
 *
 * Changes in 1.1 (TCCC-2706):
 * - Update time zone from GMT-04 to UTC-05
 *
 * Changes in 1.2 (TCCC-2926 and TCCC-2900):
 * - Add support to restrict the max characters of private description and public description.
 * - Fix bug TCCC-2900.
 *
 * Version 1.3 (Release Assembly - TopCoder Cockpit Modal Windows Revamp) changes notes:
 * - Update to user the new "add new project" modal window
 *
 * Version 1.4 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) changes notes:
 * - Update to use the new cockpit tinyMCE editor
 * 
 * Version 1.5 (Release Assembly - TC Cockpit Contest Edit and Upload Update) Change notes:
 * - Fixed bug TCCC-3739. Fixed errors with proper maintaining the start date/time while editing the contest details
 * - and saving contest to server. The timeout for AJAX call to saveDraftContest is set to 3 minutes as the action
 * - may take a long time if there are large files added to contest.
 *
 * Version 1.5.1 (Release Assembly - TC Direct Cockpit Release One) changes:
 * - Fix the bug of not setting copilot posting payments property when saving.
 *
 * Version 1.5.2 (BUGR-6609) changes:
 * - Update the submission end date and contest end date when the contest was updated.
 *
 * Version 1.5.3 (Release Assembly - TC Direct Cockpit Release Four) changes:
 * - Change project name to a link to project overview page.
 * 
 * Version 1.5.4 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 *
 * Version 1.5.5 (Release Assembly - TC Cockpit Private Challenge Update)
 * - Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
 *
 * Version 1.6 (TopCoder Direct - EST/EDT switch in date picker)
 * - Setup event handler for auto changing date time timezone
 *
 *  @author GreatKevin, isv, GreatKevin, Veve
 * @version 1.6
 */

var currentDocument = {};
var exsitingDocuments = []; // Holds documents which are already assigned to contest before starting editing files
var newDocuments = []; // Holds documents which are uploaded to server but not yet assigned to contest while editing files
var removedExistingDocuments = []; // Holds documents which are already assigned to contest and are marked for deletion
                                   // while editing files
var prizes = [];
var projectId = -1;

var experiences = [];
var extraExperience = '';
var budget = '';

var copilotTypes = [];
var extraInfos = [];

var billingFees = {};

var billingFeesPercentage = {};

var billingGroups = {};

var contestBillingFee = 0;

var contestFeePercentage = 0;

var currentBillingAccount = 0;

var loadedBillingAccount = 0;

/**
 * Parse the date to be displayed in Schedule section.
 */
function parseDate(d) {
    return Date.parse(d,'MM/dd/yyyy HH:mm').toString("MM/dd/yyyy T HH:mm EST ").replace('T ','at ').replace('EST','EST (UTC-05)');
}


function setupCopilotDateTimeSuffix(idPrefix) {
    $('#' + idPrefix + 'DateInput, #' + idPrefix + 'TimeInput').bind('change', function () {
        var value = $('#' + idPrefix + 'DateInput').val() + ' ' + $('#' + idPrefix + 'TimeInput').val();
        $('#' + idPrefix + 'Timezone').text(
            moment.tz(
                value,
                "MM/DD/YYYY HH:mm",
                DEFAULT_TIMEZONE
            ).format('z')
        );
    });
}

    
$(document).ready(function() {

    getContestFeesForBillingProject($("#billingProjects2").val());

    currentBillingAccount = $("#billingProjects2").val();
    loadedBillingAccount =  $("#billingProjects2").val();

    $("#startDateLabel").html(parseDate($("#startDateLabel").text()));
    $("#subEndDateLabel").html(parseDate($("#subEndDateLabel").text()));
    $("#endDateLabel").html(parseDate($("#endDateLabel").text()));
    initDialog('errorDialog', 500);

    var SelectOptions = {
        ddMaxHeight: '200px',
        yscroll: true
    };

    $('#projects2, #billingProjects2, #start2TimeInput').sSelect(SelectOptions);
    
    CKEDITOR.replace( 'publicCopilotPostingDescription2' );
    CKEDITOR.replace( 'privateCopilotPostingDescription2' );

    /* init pop */
    var prevPopup = null;
    showPopup = function(myLink, myPopupId) {
        var myLinkLeft = myLinkTop = 0;

        /* hide the previous popup */
        if (prevPopup) {
            $(prevPopup).css("display", "none");
        }

        prevPopup = $('#' + myPopupId);

        /* get the position of the current link */
        do{
            myLinkLeft += myLink.offsetLeft;
            myLinkTop += myLink.offsetTop;
        } while (myLink = myLink.offsetParent);

        /* set the position of the popup */
        var popUpHeight2 = $('#' + myPopupId).height() / 2;

        myLinkTop -= popUpHeight2;

        $('#' + myPopupId).css("top", (myLinkTop + 4) + 'px');
        $('#' + myPopupId).css("left", ( myLinkLeft + 22 ) + 'px');

        /* set the positio of the arrow inside the popup */
        $(".tooltipContainer SPAN.arrow").css("top", popUpHeight2 + 'px');

        /* show the popup */
        $('#' + myPopupId).css("display", "block");
    };

    $('#CopilotPostingPublicDescHelpIcon').hover(function() {
        showPopup(this, 'copilotPostingPublicDescriptionToolTip');
    }, function() {
        $('#copilotPostingPublicDescriptionToolTip').hide();
    });

    $('#CopilotPostingPrivateDescHelpIcon').hover(function() {
        showPopup(this, 'copilotPostingPrivateDescriptionToolTip');
    }, function() {
        $('#copilotPostingPrivateDescriptionToolTip').hide();
    });

    $('#CopilotPostingCostsHelpIcon').hover(function() {
        showPopup(this, 'copilotPostingCostsToolTip');
    }, function() {
        $('#copilotPostingCostsToolTip').hide();
    });

    // styling file input
    $(".fileUpload .fileInput").styleingInputFile();

    // edit function
    $(".editMask .editPanel").hide();
    $(".editMask .editLink").click(function() {
        var mask = $(this).parents(".editMask");
        mask.find(".editPanel").show();
        mask.find(".infoPanel, .htmlDescription").hide();
    });

    $('#cancelContestInfo').click(function() {
        hideEdit($(this));
        
        $('#contestNameInput2').val($('#contestNameTextLabel').html());
        $('#projects2').getSetSSValue($('#mainContent').data('p1'));
        $('#billingProjects2').getSetSSValue($('#mainContent').data('p2'));
        
        $('.experienceDiv input[type=checkbox]').attr('checked', '');
        $.each(experiences, function(index, item) {
            $('.experienceDiv input[type=checkbox][value=' + item + ']').attr('checked', 'checked');
        });
        $('#otherExperience').val(extraExperience);
        
        if (budget == '') {
            $("#notHaveBudget").attr("checked", true);
        } else {
            $("#haveBudget").attr("checked", true);
        }
        $('#projectBudgetInput').val(budget);
        return false;
    });

    $('#saveContestInfo').click(function() {
        var errors = [];

        if ($('#projects2').val() == '-1') {
            errors.push('Project is not selected');
        }
        if (!checkRequired($('#contestNameInput2').val())) {
            errors.push('Challenge name is empty');
        }
        if ($('#billingProjects2').val() == '0') {
            errors.push('Billing account is not selected');
        }
        
        if (!checkNumber($('#projectBudgetInput').val())) {
            errors.push('Budget is not an valid number.');
        }

        if (errors.length > 0) {
            showErrors(errors);
        } else {
            hideEdit($(this));
            setupContestFee($("#billingProjects2").val(), false);
            updateProjectGeneralInfo();
            currentBillingAccount = $("#billingProjects2").val();
        }

        return false;
    });

    $("#billingGroupCheckBox input[type=checkbox]").change(function () {
        if ($(this).is(":checked")) {
            $("#billingGroupCheckBox select").show();
            $("#billingGroupCheckBox select option").remove();
            var selectedBillingID = $("#billingProjects2").val();
            if(selectedBillingID > 0 && billingGroups[selectedBillingID] && billingGroups[selectedBillingID].length > 0) {
                $.each(billingGroups[selectedBillingID], function(i, v){
                    $("#billingGroupCheckBox select").append($("<option/>").attr('value', v.id).text(v.name));
                })
            }
        } else {
            $("#billingGroupCheckBox select").hide();
        }
    });


    $('#cancelPrize').click(function() {
        hideEdit($(this));
        // restore
        $("input[name='firstPlacePrize']").val($("#rswFirstPlace").html());
        $("#swSecondPlace").html($("#rswSecondPlace").html());
        $("#swTotal").html($("#rswTotal").html());
        return false;
    });

    $('#savePrize').click(function() {
        hideEdit($(this));
        setupContestFee(currentBillingAccount, true);
        updatePrize();
    });

    $('#cancelDates').click(function() {
        hideEdit($(this));
        $('#start2DateInput').val(getDatePart($('#mainContent').data('p3')));
        $('#start2TimeInput').getSetSSValue(getTimePart($('#mainContent').data('p3')));
        return false;
    });

    $('#saveDates').click(function() {
        var errors = [];

        var startDate = getDateByIdPrefix('start2');

        if (errors.length > 0) {
            showErrors(errors);
        } else {
            hideEdit($(this));
            updateProjectDate();
            $('#mainContent').data('p3', getSelectedStartTime());
        }
        return false;
    });

    $('#cancelPublicDesc').click(function() {
        hideEdit($(this));
        CKEDITOR.instances.publicCopilotPostingDescription2.getData().setData($('#publicDescriptionText').html());
        return false;
    });

    $('#savePublicDesc').click(function() {
        hideEdit($(this));
        updatePublicDesc();
        return false;
    });

    $('#cancelPrivateDesc').click(function() {
        hideEdit($(this));
        CKEDITOR.instances.privateCopilotPostingDescription2.getData().setData($('#privateDescriptionText').html());
        return false;
    });

    $('#savePrivateDesc').click(function() {
        hideEdit($(this));
        updatePrivateDesc();
        return false;
    });

    $('#cancelFiles').click(function() {
        hideEdit($(this));
        restorePrevData();
        return false;
    });

    $('#saveFiles').click(function() {
        hideEdit($(this));
        updateProjectFiles();
        return false;
    });

    if($("#contestFeePercentageValue").length > 0) {
        var cp = parseFloat($("#contestFeePercentageValue").val());
        if (cp > 0) {
            $(".percentageInfo").text("( " + (cp * 100).toFixed(1) + "% markup )");
            contestFeePercentage = cp;
        }
    }

    $('input[name=firstPlacePrize]').keyup(function() {
        var amount = parseFloat($(this).val());

        if(isNaN(amount)) return;

        var second = ((amount) / 2).toFixed(1);
        var total = (amount + parseFloat(second));

        if($("#contestFeePercentageValue").length > 0) {
            var totalPrize = (amount + parseFloat(second));
            var cp = parseFloat($("#contestFeePercentageValue").val());
            if (cp > 0) {
                var contestFee = (totalPrize * cp).toFixed(1);
                $("#swContestFee").text(contestFee);
                $(".percentageInfo").text("( " + (cp * 100).toFixed(1) + "% markup )");
            }
        }

        total = (total + parseFloat($("#swContestFee").text())).toFixed(1);

        $("#swSecondPlace").text(second);
        $("#swTotal").text(total);
    });

    $("#billingProjects2").change(function(){
        var billingAccountId = $(this).val();
        if (billingAccountId > 0) {
            getContestFeesForBillingProject(billingAccountId);
        }
        setupContestFee(billingAccountId, false);
        updateBillingGroups();
    });

    $('#addNewProject2').click(function() {
        clearAddNewProjectForm();
        modalLoad("#addNewProjectModal");
        $('#addNewProjectModal').find('input[name="newProjectName"]').focus();
    });

    // Upload file
    // Document uploader set up - Screen 2
    var uploader2 =
            new AjaxUpload(null, {
                action: ctx + '/copilot/documentUpload',
                name : 'document',
                responseType : 'json',
                onSubmit : function(file, ext) {
                    currentDocument['fileName'] = file;
                    uploader2.setData({contestFileDescription : currentDocument['description'], documentTypeId : 24, studio: false});
                    modalPreloader();
                },
                onComplete : function(file, jsonResult) {
                    handleJsonResult(jsonResult,
                                     function(result) {
                                         currentDocument['documentId'] = result.documentId;
                                         newDocuments.push(currentDocument);
                                         addFileItem(currentDocument);
                                         currentDocument = {};
                                         $('#fileDescription2').val('');
                                         $('#fakeTextInput2').val('');
                                         modalClose();
                                     },
                                     function(errorMessage) {
                                         showServerError(errorMessage);
                                         modalClose();
                                     });
                }
            }, false);

    $('#fileUploadBtn2').click(function() {
        var fileName = uploader2._input.value;
        var description = $('#fileDescription2').val();

        var errors = [];

        if (!checkRequired(fileName)) {
            errors.push('No file is selected.');
        } else {
            var fileNameToCheck = fileName.toLowerCase();
            if (fileNameToCheck.indexOf('fakepath') >= 0) {
                var p1 = fileNameToCheck.lastIndexOf("\\");
                var p2 = fileNameToCheck.lastIndexOf("/");
                var p = Math.max(p1, p2);
                fileNameToCheck = fileNameToCheck.substring(p + 1);
            }
            $('.uploadedDocumentItemFileName').each(function(index, item) {
                if ($(item).html().toLowerCase() == fileNameToCheck) {
                    errors.push('Such a file is already uploaded');
                }
            });
        }

        if (!checkRequired(description)) {
            errors.push('File description is empty.');
        }


        if (errors.length > 0) {
            showErrors(errors);
            return false;
        }

        currentDocument['description'] = description;

        uploader2.submit();
    });
    
    $("#haveBudget").click(function() {
        //$("#projectBudgetInput").attr("disabled", "");
    });
    
    $("#notHaveBudget").click(function() {
        $("#projectBudgetInput").val("");
        //$("#projectBudgetInput").attr("disabled", "disabled");
    });
    
    $("#projectBudgetInput").click(function() {
        $("#haveBudget").attr("checked", "checked");
    });
    
    updateProjectGeneralInfo(true);
});

/* function to style the input file */
(function($) {
    $.fn.styleingInputFile = function() {
        this.each(function() {
            var fileInput = $(this);
            var parentWrap = fileInput.parents(".attachFile");
            var wrapOffset = parentWrap.offset();
            fileInput.attr("style",
                           "opacity: 0;-moz-opacity: 0;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);")
            parentWrap.mousemove(function(event) {
                fileInput.blur();
                var relatedX = event.pageX - wrapOffset.left - fileInput.width() + 12;
                var relatedY = event.pageY - wrapOffset.top;
                fileInput.css("left", relatedX + "px");
                fileInput.css("top", "0px");
            });
            fileInput.change(function() {
                $(this).blur();
                parentWrap.find(".fakeText input").val($(this).val());
            })
        })
    }
})(jQuery);

/**
 * Adds a new project.
 */
function addNewProject() {
    var projectName = $('#addNewProjectModal').find('input[name="newProjectName"]').val();
    var projectDescription = $('#addNewProjectModal').find('textarea[name="newProjectDescription"]').val();

    var errors = [];

    if (!checkRequired(projectName)) {
        errors.push('Project name is empty.');
    }

    if (!checkRequired(projectDescription)) {
        errors.push('Project description is empty.');
    }

    if (errors.length > 0) {
        showErrors(errors);
        $("#modal-background").hide();
        return;
    }

    $.ajax({
        type: 'POST',
        url:  "createProject",
        data: setupTokenRequest({'projectName' : projectName, 'projectDescription' : projectDescription}, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                             function(result) {
                                 var projectData = result;
                                 $("<option/>").val(result.projectId).text(result.name).appendTo("#projects");
                                 $('#projects').resetSS();
                                 $('#projects').getSetSSValue(result.projectId);

                                 $("<option/>").val(result.projectId).text(result.name).appendTo("#projects2");
                                 $('#projects2').resetSS();
                                 $('#projects2').getSetSSValue(result.projectId);

                                 modalCloseAddNewProject();
                                 showSuccessfulMessage('Project <span class="messageContestName">' + projectData.name + '</span> is created successfully.');

                             },
                             function(errorMessage) {
                                 modalCloseAddNewProject();
                                 showServerError(errorMessage);
                             });
        }
    });
}

function addFileItem(doc) {
    var template = unescape($('#uploadedDocumentTemplate').html());
    var html = $.validator.format(template, doc['documentId'], doc['fileName'], doc['description'].replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"));
    $('.fileUpload dl').append(html);
}

function removeFileItem(documentId, docs) {
    $('#doc' + documentId).remove();
    $.each(newDocuments, function(i, doc) {
        if (doc && doc.documentId == documentId) {
            newDocuments.splice(i, 1);
        }
    });
    $.each(exsitingDocuments, function(i, doc) {
        if (doc && doc.documentId == documentId) {
            removedExistingDocuments.push(doc);
        }
    });
}

function getDatePart(d) {
    if (d == null) {
        return null;
    }
    var year = d.substring(0, 4);
    var month = d.substring(5, 7);
    var day = d.substring(8, 10);
    return month + "/" + day + "/" + year;
}

function getTimePart(d) {
    if (d == null) {
        return null;
    }
    return d.substring(11, 16);
}

function getDate(datePart, timePart) {
    return Date.parse(datePart + ' ' + timePart + ' EST', 'MM/dd/yyyy HH:mm EST');
}

function getDateByIdPrefix(idPrefix) {
    return getDate($('#' + idPrefix + 'DateInput').val(), $('#' + idPrefix + 'TimeInput').val());
}

function hideEdit(cancelButton) {
    var mask = cancelButton.parents(".editMask");
    mask.find(".editPanel").hide();
    mask.find(".infoPanel,.htmlDescription").show();
}

function restorePrevData() {
    $('#uploadedDocumentsTable').html('');
    $('.fileUpload .uploadedDocumentItem').remove();
    for (var i = 0; i < exsitingDocuments.length; i++) {
        var doc = exsitingDocuments[i];
        var d = '<tr><td class="fileName"><span>' + (i + 1) + '.</span> <a href="' 
                        + ctx + '/launch/downloadDocument?documentId=' + doc['documentId'] + '">' + doc['fileName']
                + '</a></td> <td class="fileDesc">' + doc['description'] + '</td></tr>';
        $('#uploadedDocumentsTable').append(d);
        addFileItem(doc);
    }
    removedExistingDocuments = [];
    newDocuments = [];
}

function updateProjectGeneralInfo(notSendToServer) {
    var contestName = $('#contestNameInput2').val();
    var projectName = $('#projects2 option:selected').text();
    var accountName = $('#billingProjects2 option:selected').text();
    var projectId = $('#projects2').val();
    var accountId = $('#billingProjects2').val();
    
    copilotTypes = [];
    extraInfos = [];
    
    var exp = '';
    
    experiences = [];
    experiencesText = [];
    
    var tmpExp = [];
    $.each($('.experienceDiv input[type=checkbox]:checked'), function(index, item) {
        tmpExp.push($(item).next().html());
        experiences.push($(item).val());
        experiencesText.push($(item).parent().find("label").text());
        copilotTypes.push(new com.topcoder.direct.ProjectCopilotType($(this).val(), $(this).attr('id'), $(this).attr('id')));
    });
    
    removeCopilotExtraInfo(OTHER_EXTRA_INFO_TYPE);
    var other = $('#otherExperience').val();
    extraExperience = other;
    if (other.length > 0) {
        var infoType = new com.topcoder.direct.CopilotContestExtraInfo();
        infoType.type = OTHER_EXTRA_INFO_TYPE;
        infoType.value = other;
        extraInfos.push(infoType);
        
        tmpExp.push(other);
    }

    if(experiences.length > 0) {
        $(".requiredXperience span").html(experiencesText[0] + '&nbsp;<a class="more" href="javascript:;">More</a>');

        $(".moreFlyout ul li").remove(); // clear first

        $.each(experiencesText, function(index, item) {
            $("<li></li>").text('- ' + item).appendTo($(".moreFlyout ul"));
        });
    }

    if(other.length > 0) {
        $("<li></li>").text('- ' + other).appendTo($(".moreFlyout ul"));
    }

    if(experiences.length == 0 && other.length == 0) {
        $(".requiredXperience span").html("Not Required");
    }
    
    if (tmpExp.length > 0) {
        for (var i = 0; i < tmpExp.length; i++) {
            exp += tmpExp[i];
            if (i < tmpExp.length - 1) {
                exp += ", ";
            }
        }
    } else {
        exp = "Not set.";
    }
    
    var bud = $('#projectBudgetInput').val();
    budget = bud;

    removeCopilotExtraInfo(BUDGET_EXTRA_INFO_TYPE);
    if ($('#haveBudget:checked').length > 0) {
        var infoType = new com.topcoder.direct.CopilotContestExtraInfo();
        infoType.type = BUDGET_EXTRA_INFO_TYPE;
        infoType.value = bud;
        
        extraInfos.push(infoType);
        
        bud = "$ " + bud;

        $(".projectBudget span").text(bud);

    } else {
        bud = "Don't have a budget yet.";

        $(".projectBudget span").text("Not Indicated");
    }
            
    $('#contestNameTextLabel').html(htmlEncode(contestName));
    $('#projectNameTextLabel').html('<a href="../projectOverview?formData.projectId=' + projectId + '">' + projectName + '</a>');
    $('#billingProjectNameTextLabel').html(accountName);
    $('#projects').val(projectId);
    $('#contestNameInput').val(contestName);
    $('#billingProjects').val(accountId);
    
    $('#copilotTypesTextLabel').html(exp);
    $('#budgetTextLabel').html(bud);

    if($("input[name=CMCTaskID]").length > 0 && $.trim($("input[name=CMCTaskID]").val()).length > 0) {
        $("#rCMCTaskID").html($("input[name=CMCTaskID]").val());
    }
    
    if (typeof notSendToServer == "undefined" || !notSendToServer) {
        sendSaveDraftRequestToServer();
    }

    if ($("#billingGroupCheckBox input[type=checkbox]").is(":checked") && $("#billingGroupCheckBox select").val() > 0) {
        $("#securityGroupName").text($("#billingGroupCheckBox select option:selected").text());
        $("#securityGroupName").parents("li").show();
    } else {
        $("#securityGroupName").text('');
        $("#securityGroupName").parents("li").hide();
    }
}

function updateProjectDate() {
    var startDate = $('#start2DateInput').datePicker().val();
    var startTime = $('#start2TimeInput').val();

    $('#startDateLabel').html(startDate + ' at ' + startTime + ' EST (UTC-05)');
    $('#startDateInput').datePicker().val(startDate);
    $('#startTimeInput').val(startTime);
    
    sendSaveDraftRequestToServer();
    
}

function updatePublicDesc() {
    var publicDescription = CKEDITOR.instances.publicCopilotPostingDescription2.getData();
    
    $('#publicDescriptionText').html(publicDescription);

    sendSaveDraftRequestToServer();
}

function updatePrivateDesc() {
    var privateDescription = CKEDITOR.instances.privateCopilotPostingDescription2.getData();
	
    $('#privateDescriptionText').html(privateDescription);
    sendSaveDraftRequestToServer();
}

function updatePrize() {
    var amount = parseFloat($('input[name=firstPlacePrize]').val());
    if(isNaN(amount)) showErrors('Prize should be a number.');
    if(amount < 0 ) showErrors('Prize should be positive.');
    sendSaveDraftRequestToServer();

    $("#rswFirstPlace").html(amount.toFixed(1));
    $("#rswSecondPlace").html((amount/2).toFixed(1))
//
//    var total = parseFloat($("#rswFirstPlace").text()) + parseFloat($("#rswSecondPlace").text()) + parseFloat($("#rswContestFee").text());
//    $("#rswTotal").text(total.toFixed(1));
}

function updateProjectFiles() {
    sendSaveDraftRequestToServer();
}

function validateContestInput() {
    // Validate form data
    var contestName = $('#contestNameInput').val();
    var projectId = $('#projects').val();
    var accountId = $('#billingProjects').val();

    var errors = [];
    if (projectId == '-1') {
        errors.push('Project is not selected');
    }
    if (!checkRequired(contestName)) {
        errors.push('Challenge name is empty');
    }
    if (accountId == '0') {
        errors.push('Billing account is not selected');
    }

    var startDate = getDateByIdPrefix('start');
    var endDate = getDateByIdPrefix('end');

    if (startDate >= endDate) {
        errors.push('Start date should be before end date.');
    }
    return errors;
}


/**
 * Activate software contest.
 */
function activateContest() {
    setupContestFee(currentBillingAccount, false);
    var request = saveAsDraftRequest();
    request['activationFlag'] = true;

    $.ajax({
        type: 'POST',
        url:  "saveDraftContest",
        data: setupTokenRequest(request, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    showSuccessfulMessage("Your copilot posting <span class='messageContestName'>" +  request['assetDTO.name'] + "</span> has been activated.");
                    $("#resubmit").hide();
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        },
        beforeSend: beforeAjax,
        complete: afterAjax
    });
}

function getSelectedStartTime() {
    var dateValue = $('#start2DateInput').datePicker().val();
    var year = dateValue.substring(6, 10);
    var month = dateValue.substring(0, 2);
    var day = dateValue.substring(3, 5);
    return year + '-' + month + '-' + day +"T" + $('#start2TimeInput').val() + ":00";
}

function saveAsDraftRequest() {
    var request = {};

    request['projectId'] = projectId;
    request['contestId'] = -1;
    request['tcDirectProjectId'] = $('#projects2').val();
    request['competitionType'] = 'SOFTWARE';

    request['assetDTO.name'] = $('#contestNameInput2').val();

    request['assetDTO.productionDate'] = getSelectedStartTime();

    request['projectHeader.id'] = projectId;
    request['projectHeader.tcDirectProjectId'] = $('#projects2').val();
    request["projectHeader.properties['Billing Project']"] = $('#billingProjects2').val();
    request["projectHeader.properties['First Place Cost']"] = parseFloat($('input[name=firstPlacePrize]').val());
    request["projectHeader.properties['Second Place Cost']"] = (request["projectHeader.properties['First Place Cost']"] / 2).toFixed(1);
    request["projectHeader.properties['Payments']"] = parseFloat($('input[name=firstPlacePrize]').val());
    request["projectHeader.properties['Confidentiality Type']"] = 'public';
    request['projectHeader.projectSpec.detailedRequirements'] = CKEDITOR.instances.publicCopilotPostingDescription2.getData();
    request['projectHeader.projectSpec.privateDescription'] = CKEDITOR.instances.privateCopilotPostingDescription2.getData();

    
    request['docUploadIds'] = getDocumentIds();
    request['docCompIds'] = getExistingDocumentIds();

    $.each(prizes, function(index, value){
       if(value.place == 1) {
            value.prizeAmount = request["projectHeader.properties['First Place Cost']"];
       } else if (value.place ==2) {
             value.prizeAmount = request["projectHeader.properties['Second Place Cost']"];
       }
    });

    request['projectHeader.prizes'] = prizes;
    
    request['projectHeader.projectCopilotTypes'] = copilotTypes;
    request['projectHeader.copilotContestExtraInfos'] = extraInfos;

    request["projectHeader.properties['Contest Fee Percentage']"] = contestFeePercentage;

    var contestFee = parseFloat($("#swContestFee").text()).toFixed(1);
    request["projectHeader.properties['Admin Fee']"] = contestFee;

    if($("input[name=CMCTaskID]").length > 0 && $.trim($("input[name=CMCTaskID]").val()).length > 0) {
        request["projectHeader.properties['CloudSpokes CMC Task']"] = $("input[name=CMCTaskID]").val();
    }

    if($("#billingGroupCheckBox input[type=checkbox]").is(":checked") && $("#billingGroupCheckBox select").val() > 0) {
        request['projectHeader.securityGroupId'] = $("#billingGroupCheckBox select").val();
    } else {
        request['projectHeader.securityGroupId'] = 0;
    }
    
    return request;
}

function getDocumentIds() {
    return $.map(newDocuments, function(doc, i) {
        return doc.documentId;
    });
//    return $.map(uploadedDocuments, function(doc, i) {
//        return doc.documentId;
//    });
}

function getExistingDocumentIds() {
    var ddd = [];
    $.each(exsitingDocuments, function(i, doc) {
        var isRemoved = false;
        $.each(removedExistingDocuments, function(j, doc2) {
            if (doc && doc2 && doc.documentId == doc2.documentId) {
                isRemoved = true;
            }
        });
        if (!isRemoved) {
            ddd.push(doc.documentId);
        }
    });
    return ddd;
//    
//    return $.map(exsitingDocuments, function(doc, i) {
//        return doc.documentId;
//    });
}


/**
 * Handle contest activation result.
 *
 * @param jsonResult the json result
 */
function handleActivation(jsonResult) {
    handleJsonResult(jsonResult,
                     function(result) {
                         $('#paymentNumBox').html('No. ' + result.paymentNumber);
                         $('#contestNameReceipt').html($('#contestNameInput').val());
                         $('#projectNameReceipt').html($('#projects option:selected').text());
                         $('#paymentMethodReceipt').html(result.paymentMethodReceipt);
                         $('#startTimeReceipt').html(result.startTimeReceipt);
                         $('.launchpage').hide();
                         $('.orderReviewPage').show();
                     },
                     function(errorMessage) {
                         showServerError(errorMessage);
                     });
}

/**
 * Handle contest draft saving result.
 *
 * @param jsonResult the json result
 */
function handleDraftSaving(jsonResult) {
    handleJsonResult(jsonResult,
                     function(result) {
                         projectId = result.projectId;
                         newDocuments = [];
                         removedExistingDocuments = [];
                         exsitingDocuments = [];
                         $.each(result.documents, function(index, doc) {
                             var d = {};
                             var pos = doc.url.lastIndexOf('/');
                             d['fileName'] = doc.url.substring(pos + 1);
                             d['documentId'] = doc.id;
                             d['description'] = doc.documentName.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
                             exsitingDocuments.push(d);
                         });
                         restorePrevData();
                         $("#subEndDateLabel").html(parseDate(result.subEndDate));
                         $("#endDateLabel").html(parseDate(result.endDate));
                         showSuccessfulMessage("Your copilot posting has been saved successfully.");

                     },
                     function(errorMessage) {
                         showServerError(errorMessage);
                     });
}
function sendSaveDraftRequestToServer() {
    // Send request to server
    var request = saveAsDraftRequest();
    $.ajax({
        type: 'POST',
        url:  "saveDraftContest",
        data: setupTokenRequest(request, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        timeout: 180000,
        success: handleDraftSaving,
        beforeSend: function() {
            modalPreloader();
        },
        complete: function() {
            modalClose();
        }
    });
}

function beforeAjax() {
	 modalPreloader();
}

function afterAjax() {
	 modalClose();
}

function removeCopilotExtraInfo(infoType) {
	if (typeof infoType == "undefined" || typeof infoType.id == "undefined") {
		return;
	}
	var length = extraInfos.length;
	for (var i = 0; i < length; ++i) {
		var info = extraInfos.shift();
		if (info.type.id == infoType.id) {
			return;
		} else {
			extraInfos.push(info);
		}
	}
}

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

function setupContestFee(billingProjectId, updatePrize) {
    contestFeePercentage = 0;

    if (billingFees[billingProjectId] != null) {
        var fees = billingFees[billingProjectId];
        contestBillingFee = 0;
        for(var i = 0; i < fees.length; ++i) {
            if(fees[i].contestTypeId == 29) {
                contestBillingFee = fees[i].contestFee;
            }
        }
    }

    if (billingFeesPercentage[billingProjectId] != null && billingFeesPercentage[billingProjectId].contestFeePercentage != undefined) {
        contestFeePercentage = billingFeesPercentage[billingProjectId].contestFeePercentage;

        if (contestFeePercentage != null && contestFeePercentage > 0) {
            var readAmount = parseFloat($("#rswFirstPlace").text());
            var writeAmount = parseFloat($("input[name=firstPlacePrize]").val());
            var totalAmount = (updatePrize ? writeAmount : readAmount) * 1.5;

            contestBillingFee = (totalAmount * contestFeePercentage);
        }
    }

    var prize = parseFloat($('input[name=firstPlacePrize]').val());
    var total = prize * 1.5 + contestBillingFee;

    $("#rswContestFee").text(contestBillingFee.toFixed(1));
    $("#swContestFee").text(contestBillingFee.toFixed(1));

    if(billingProjectId == loadedBillingAccount) {
        if($("#contestFeePercentageValue").length > 0) {
            contestFeePercentage = parseFloat($("#contestFeePercentageValue").val());
        } else {
            contestFeePercentage = 0;
        }
    }

    if (contestFeePercentage > 0) {
        $(".percentageInfo").text("( " + (contestFeePercentage * 100).toFixed(1) + "% markup )");
    } else {
        $(".percentageInfo").text('');
    }

    $("#rswTotal").text(total.toFixed(1));
    $("#swTotal").text(total.toFixed(1));
}

function updateBillingGroups() {

    var selectedBillingID = $("#billingProjects2").val();

    if(selectedBillingID > 0 && billingGroups[selectedBillingID]) {
        if(billingGroups[selectedBillingID].length > 0) {
            $("#billingGroupCheckBox").show();

            var securityGroupId = $("#securityGroupId").val();

            if(securityGroupId > 0) {
                $("#billingGroupCheckBox input[type=checkbox]").attr('checked', 'checked');
                $("#billingGroupCheckBox select").val(securityGroupId);

            }

            $("#billingGroupCheckBox input[type=checkbox]").trigger('change');

            if (securityGroupId > 0) {
                $("#billingGroupCheckBox select").val(securityGroupId);

                $("#securityGroupName").text($("#billingGroupCheckBox select option:selected").text());
                $("#securityGroupName").parents("li").show();
            } else {
                $("#securityGroupName").text('');
                $("#securityGroupName").parents("li").hide();
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

