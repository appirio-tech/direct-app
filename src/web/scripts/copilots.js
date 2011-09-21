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
 * @author TCSASSEMBLER
 * @version 1.3 (Release Assembly - TopCoder Cockpit Modal Windows Revamp)
 */

var currentDocument = {};
var exsitingDocuments = [];
var newDocuments = [];
var uploadedDocuments = [];
var prizes = [];
var projectId = -1;

$(document).ready(function() {

    initDialog('errorDialog', 500);

    var SelectOptions = {
        ddMaxHeight: '200px',
        yscroll: true
    };

    $('#projects2, #billingProjects2').sSelect(SelectOptions);
    
    $('#start2TimeInput').getSetSSValue(getTimePart($('#mainContent').data('p3')));
    

    /* init date-pack */
    if ($('.date-pick').length > 0) {
        $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
    }

    function makeMaxCharsTinyMCE(obj, maxChars) {
        tinyMCE.init({
            mode : "exact",
            elements : obj,
            plugins : "paste",
            theme : "advanced",
            theme_advanced_buttons1 : "bold,italic,underline,strikethrough,undo,redo,pasteword, bullist,numlist,link,unlink,code",
            theme_advanced_buttons2 : "",
            theme_advanced_buttons3 : "",
            init_instance_callback : function() {
                $('table.mceLayout').css('width', '100%');
            },
            valid_elements : tinyMCEValidElements,
            setup: function(ed) {setMaxCharsEventHandlerOnSetup(ed, maxChars);},
            handle_event_callback : maxCharsEventHandler(obj, maxChars)
        });
    }
    makeMaxCharsTinyMCE("publicCopilotPostingDescription2", 12000);
    makeMaxCharsTinyMCE("privateCopilotPostingDescription2", 4096);

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
        
        return false;
    });

    $('#saveContestInfo').click(function() {
        var errors = [];

        if ($('#projects2').val() == '-1') {
            errors.push('Project is not selected');
        }
        if (!checkRequired($('#contestNameInput2').val())) {
            errors.push('Contest name is empty');
        }
        if ($('#billingProjects2').val() == '0') {
            errors.push('Billing account is not selected');
        }

        if (errors.length > 0) {
            showErrors(errors);
        } else {
            hideEdit($(this));
            updateProjectGeneralInfo();
        }

        return false;
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
        }
        return false;
    });

    $('#cancelPublicDesc').click(function() {
        hideEdit($(this));
        tinyMCE.get('publicCopilotPostingDescription2').setContent($('#publicDescriptionText').html());
        return false;
    });

    $('#savePublicDesc').click(function() {
        hideEdit($(this));
        updatePublicDesc();
        return false;
    });

    $('#cancelPrivateDesc').click(function() {
        hideEdit($(this));
        tinyMCE.get('privateCopilotPostingDescription2').setContent($('#privateDescriptionText').html());
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
        data: {'projectName' : projectName, 'projectDescription' : projectDescription},
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
    var html = $.validator.format(template, doc['documentId'], doc['fileName'], doc['description']);
    $('.fileUpload dl').append(html);
}

function removeFileItem(documentId, docs) {
    $('#doc' + documentId).remove();
    $.each(docs, function(i, doc) {
        if (doc && doc.documentId == documentId) {
            docs.splice(i, 1);
        }
    });
}

function getDatePart(d) {
    if (d == null) {
        return null;
    }
    return d.toString("MM/dd/yyyy");
}

function getTimePart(d) {
    if (d == null) {
        return null;
    }
    return d.toString("HH:mm");
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
    // documents2 = [];
    var template = unescape($('#uploadedDocumentTemplate').html());
    $('#uploadedDocumentsTable').html('');
    $('#fileUpload2 dl').remove('.uploadedDocumentItem');
    for (var i = 0; i < exsitingDocuments.length; i++) {
        var doc = exsitingDocuments[i];
        var d = '<tr><td class="fileName"><span>' + (i + 1) + '.</span> <a href="javascript:">' + doc['fileName']
                + '</a></td> <td class="fileDesc">' + doc['description'] + '</td></tr>';
        $('#uploadedDocumentsTable').append(d);

        var html = $.validator.format(template, doc['documentId'], doc['fileName'], doc['description'], '2');
        $('#fileUpload2 dl').append(html);
        // documents2[i] = doc;
    }
}

function updateProjectGeneralInfo() {
    var contestName = $('#contestNameInput2').val();
    var projectName = $('#projects2 option:selected').text();
    var accountName = $('#billingProjects2 option:selected').text();
    var projectId = $('#projects2').val();
    var accountId = $('#billingProjects2').val();

    $('#contestNameTextLabel').html(contestName);
    $('#projectNameTextLabel').html(projectName);
    $('#billingProjectNameTextLabel').html(accountName);
    $('#projects').val(projectId);
    $('#contestNameInput').val(contestName);
    $('#billingProjects').val(accountId);
    
    sendSaveDraftRequestToServer();
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
    var publicDescription = tinyMCE.get('publicCopilotPostingDescription2').getContent();
    if (publicDescription.length > 12000) {
        showErrors('Public Description can haave at most 12000 characters.');
        return;
    }
    $('#publicDescriptionText').html(publicDescription);

    sendSaveDraftRequestToServer();
}

function updatePrivateDesc() {
    var privateDescription = tinyMCE.get('privateCopilotPostingDescription2').getContent();
    if (privateDescription.length > 4096) {
        showErrors('Private Description can haave at most 4096 characters.');
        return;
    }
    $('#privateDescriptionText').html(privateDescription);
    sendSaveDraftRequestToServer();
}

function updateProjectFiles() {
    // $('#uploadedDocumentsTable').html('');
    $('#fileUpload dl').html('');

    sendSaveDraftRequestToServer();

    for (var i = 0; i < newDocuments.length; i++) {
        var doc = newDocuments[i];

        var uploaded = false;

        for(var j = 0; j < uploadedDocuments.length; ++j) {
            var toCheck = uploadedDocuments[j];
            if (toCheck['documentId'] == doc['documentId']) {
                uploaded = true;
                break;
            }
        }

        if (uploaded == false) {
            var d = '<tr><td class="fileName"><span>' + (i + 1) + '.</span> <a href="javascript:">' + doc['fileName']
                    + '</a></td> <td class="fileDesc">' + doc['description'] + '</td></tr>';
            $('#uploadedDocumentsTable').append(d);
            uploadedDocuments.push(doc);
        }

    }
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
        errors.push('Contest name is empty');
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
    var request = saveAsDraftRequest();
    request['activationFlag'] = true;

    $.ajax({
        type: 'POST',
        url:  "saveDraftContest",
        data: request,
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


function saveAsDraftRequest() {
    var request = {};

    request['projectId'] = projectId;
    request['contestId'] = -1;
    request['tcDirectProjectId'] = $('#projects2').val();
    request['competitionType'] = 'SOFTWARE';

    request['assetDTO.name'] = $('#contestNameInput2').val();
    request['assetDTO.productionDate'] = $('#start2DateInput').datePicker().val() +"T" 
                                         + $('#start2TimeInput').val() + ":00";

    request['projectHeader.id'] = projectId;
    request['projectHeader.tcDirectProjectId'] = $('#projects2').val();
    request["projectHeader.properties['Billing Project']"] = $('#billingProjects2').val();
    request['projectHeader.projectSpec.detailedRequirements'] = tinyMCE.get('publicCopilotPostingDescription2').getContent();
    request['projectHeader.projectSpec.privateDescription'] = tinyMCE.get('privateCopilotPostingDescription2').getContent();

    request['docUploadIds'] = getDocumentIds();
    request['docCompIds'] = getExistingDocumentIds();
    request['projectHeader.prizes'] = prizes;

    return request;
}

function getDocumentIds() {
    return $.map(newDocuments, function(doc, i) {
        return doc.documentId;
    });
}

function getExistingDocumentIds() {
    return $.map(exsitingDocuments, function(doc, i) {
        return doc.documentId;
    });
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
        data: request,
        cache: false,
        dataType: 'json',
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


