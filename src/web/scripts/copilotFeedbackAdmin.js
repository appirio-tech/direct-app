/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * <p>
 * The JS for the copilot feedback admin page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since (Module Assembly - TopCoder Copilot Feedback Integration)
 */

var currentRow;

$(document).ready(function () {

    $("a.approveButton").click(function () {
        updateCopilotFeedbackStatus($(this).parent().parent(), "Approved")
    })

    $("a.rejectButton").click(function () {
        updateCopilotFeedbackStatus($(this).parent().parent(), "Rejected");
    })

    $("a.adminFeedbackEdit").click(function(){
        var container = $(this).parents("tr");

        $("#editCopilotFeedbackModal textarea").val(container.find(".feedbackText").text());
        var feedbackAnswer = container.find(".feedbackAnswer").text().toLowerCase();
        $("#editCopilotFeedbackModal input[name=workAgain][value=" + feedbackAnswer + "]").attr('checked', 'checked');
        $("#editCopilotFeedbackModal .question p span").html(container.find("td.copilotHandle").html());
        currentRow = container;
        validateFeedbackModal('editCopilotFeedbackModal');
        modalLoad("#editCopilotFeedbackModal");
    });

    $("a.cancelButton").click(function(){
       modalAllClose();
    });

    $("a.editNewFeedback").click(function(){
        if(!validateFeedbackModal('editCopilotFeedbackModal')) {
            return;
        }

        var feedbackAnswer = $('input[name=workAgain]:checked', '#editCopilotFeedbackModal').val() == "yes";
        var feedbackText = $('.comment textarea', '#editCopilotFeedbackModal').val();
        var copilotProjectId = currentRow.find("input[name=copilotProjectId]").val();
        var feedbackAuthorId = currentRow.find("input[name=feedbackAuthorId]").val();
        var request = {};
        request.copilotProjectId = copilotProjectId;
        request.feedbackAuthorId = feedbackAuthorId;
        var feedback = {};
        feedback.answer = feedbackAnswer;
        feedback.text = feedbackText;
        request.feedback = feedback;

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'adminEditCopilotFeedback',
            cache : false,
            data : request,
            dataType : 'json',
            success : function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        if(result.result == 'success') {
                            currentRow.find("span.feedbackAnswer").text(feedbackAnswer ? "YES" : "NO");
                            currentRow.find("span.feedbackText").text(feedbackText);
                            showSuccessfulMessage("The feedback has been updated");
                        } else {
                            showServerError("Unknown error happened");
                        }

                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    })
            },
            error: function(result) {
                showErrors("Error when adding feedback for the copilot");
            }
        });

    });


});

function updateCopilotFeedbackStatus(tableRow, newStatus) {
    var copilotProjectId = tableRow.find("input[name=copilotProjectId]").val();
    var feedbackAuthorId = tableRow.find("input[name=feedbackAuthorId]").val();
    var request = {};
    request.copilotProjectId = copilotProjectId;
    request.feedbackAuthorId = feedbackAuthorId;
    request.status = newStatus;

    $.ajax({
        type:'post',
        url:'updateCopilotFeedbackStatus',
        cache:false,
        data:request,
        dataType:'json',
        success:function (jsonResult) {
            handleJsonResult(jsonResult,
                function (result) {
                    if (result.result == 'success') {
                        if (newStatus.toLowerCase() == 'approved') {
                            tableRow.find("a.button1").hide();
                        } else if (newStatus.toLowerCase() == 'rejected') {
                            tableRow.find("a.approveButton").show();
                            tableRow.find("a.rejectButton").hide();
                            tableRow.find("td.feedbackStatus").addClass("feedbackRejected");
                        }

                        tableRow.find("td.feedbackStatus").html(newStatus.toUpperCase());

                    } else {
                        showServerError("Unknown error happened");
                    }

                },
                function (errorMessage) {
                    showServerError(errorMessage);
                })
        },
        error:function (result) {
            showErrors("Error when updating feedback status");
        }
    });

}

function validateFeedbackModal(modalId) {
    var modal = $("#" + modalId);
    var passValidation = true;
    if(modal.find("input[name=workAgain]:checked").length == 0) {
        modal.find(".question span.errorMessage").text("Please choose an answer");
        passValidation = false;
    } else {
        modal.find(".question span.errorMessage").text('');
    }

    if($.trim(modal.find(".comment textarea").val()).length == 0) {
        modal.find(".comment span.errorMessage").text("Please enter your feedback here");
        passValidation = false;
        return false;
    } else {
        modal.find(".comment span.errorMessage").text('');
    }

    if($.trim(modal.find(".comment textarea").val()).length > 800) {
        modal.find(".comment span.errorMessage").text("Your feedback text cannot exceed 800 chars");
        passValidation = false;
    } else {
        modal.find(".comment span.errorMessage").text('');
    }

    return passValidation;
}