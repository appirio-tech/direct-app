/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 *
 * <p>
 * The JS for the copilot feedback admin page.
 * </p>
 *
 * Version 1.1 (Release Assembly - TopCoder Copilot Feedback Updates)
 * - Add 4 ratings to the copilot feedback
 *
 * @author GreatKevin
 * @version 1.1
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

    $(".ratingEdit span").live("mouseenter", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        wrapper.find("span").removeClass("active");
        wrapper.find("span:lt(" + (index + 1) + ")").addClass("active");
    });
    $(".ratingEdit span").live("mouseleave", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        var rating = wrapper.data("rating");
        wrapper.find("span").removeClass("active");
        wrapper.find("span:lt(" + rating + ")").addClass("active");
    });
    $(".ratingEdit span").live("click", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        wrapper.find("span:lt(" + index + ")").addClass("active");
        wrapper.data("rating", index + 1);
        return false;
    });

    $(".ratingEdit").each(function () {
        var _this = $(this);
        for (var i = 0; i < 5; i++) {
            var span = $("<span/>").appendTo(_this);
        }

        // check the value
        var item = $(this).parent();
        var value = item.find("input[name='rating']").val();

        if (value <= 0) {
            _this.data("rating", 0);
        } else {
            $(this).find("span:eq(" + (value - 1) + ")").trigger('mouseenter').trigger('click');
        }

    });

    // view the admin edit feedback modal
    $("a.adminFeedbackEdit").click(function(){
        var container = $(this).parents("tr");

        // populate feedback text
        $("#editCopilotFeedbackModal textarea").val(container.find(".feedbackText").text());

        // populate feedback answer
        var feedbackAnswer = container.find(".feedbackAnswer").text().toLowerCase();
        $("#editCopilotFeedbackModal input[name=workAgain][value=" + feedbackAnswer + "]").attr('checked', 'checked');

        // populate copilot handle
        $("#editCopilotFeedbackModal span.copilotHandleSpan").html(container.find("td.copilotHandle").html());

        // populate ratings
        $("#editCopilotFeedbackModal .rating .ratingEdit span").removeClass('active');
        var timelineRating = container.find("input[name=copilotFeedbackTimelineRating]").val();
        var qualityRating = container.find("input[name=copilotFeedbackQualityRating]").val();
        var communicationRating = container.find("input[name=copilotFeedbackCommunicationRating]").val();
        var managementRating = container.find("input[name=copilotFeedbackManagementRating]").val();
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(0) span:lt(" + timelineRating + ")").addClass('active').parents('.ratingEdit').data("rating", timelineRating);
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(1) span:lt(" + qualityRating + ")").addClass('active').parents('.ratingEdit').data("rating", qualityRating);;
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(2) span:lt(" + communicationRating + ")").addClass('active').parents('.ratingEdit').data("rating", communicationRating);;
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(3) span:lt(" + managementRating + ")").addClass('active').parents('.ratingEdit').data("rating", managementRating);;

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
        var timelineRating = $(".rating .ratingEdit:eq(0) span.active", '#editCopilotFeedbackModal').length;
        var qualityRating = $(".rating .ratingEdit:eq(1) span.active", '#editCopilotFeedbackModal').length;
        var communicationRating = $(".rating .ratingEdit:eq(2) span.active", '#editCopilotFeedbackModal').length;
        var managementRating = $(".rating .ratingEdit:eq(3) span.active", '#editCopilotFeedbackModal').length;
        var copilotProjectId = currentRow.find("input[name=copilotProjectId]").val();
        var feedbackAuthorId = currentRow.find("input[name=feedbackAuthorId]").val();
        var request = {};
        request.copilotProjectId = copilotProjectId;
        request.feedbackAuthorId = feedbackAuthorId;
        var feedback = {};
        feedback.answer = feedbackAnswer;
        feedback.text = feedbackText;
        feedback.timelineRating = timelineRating;
        feedback.qualityRating = qualityRating;
        feedback.communicationRating = communicationRating;
        feedback.managementRating = managementRating;
        request.feedback = feedback;

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'adminEditCopilotFeedback',
            cache : false,
            data : setupTokenRequest(request, getStruts2TokenName()),
            dataType : 'json',
            success : function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        if(result.result == 'success') {
                            currentRow.find("span.feedbackAnswer").text(feedbackAnswer ? "YES" : "NO");
                            currentRow.find("span.feedbackText").text(feedbackText);
                            currentRow.find(".copilotRating span:eq(0)").html("Timeline:" + timelineRating + "<br/>");
                            currentRow.find(".copilotRating span:eq(1)").html("Quality:" + qualityRating + "<br/>");
                            currentRow.find(".copilotRating span:eq(2)").html("Communication:" + communicationRating + "<br/>");
                            currentRow.find(".copilotRating span:eq(3)").html("Management:" + managementRating + "<br/>");
                            currentRow.find("input[name=copilotFeedbackTimelineRating]").val(timelineRating);
                            currentRow.find("input[name=copilotFeedbackQualityRating]").val(qualityRating);
                            currentRow.find("input[name=copilotFeedbackCommunicationRating]").val(communicationRating);
                            currentRow.find("input[name=copilotFeedbackManagementRating]").val(managementRating);

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
        data:setupTokenRequest(request, getStruts2TokenName()),
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

    // validate feedback ratings
    var passRatingValidation = true;
    modal.find(".rating .ratingEdit").each(function(){
        var ratingValue = getFeedbackRating($(this));
        if (ratingValue < 1) {
            // at least one star is needed for each rating
            passValidation = false;
            passRatingValidation = false;
        }
    })

    if(passRatingValidation) {
        modal.find(".rating span.errorMessage").text('');
    } else {
        modal.find(".rating span.errorMessage").text('Please give your ratings on each rating category, at least 1 star needs to give');
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


function getFeedbackRating(ratingControl) {
    return ratingControl.find("span.active").length;
}
