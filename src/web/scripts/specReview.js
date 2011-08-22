/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file provide event handlers for specification review page.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
$(function() {
    showAddCommentDiv = function(qId, div_to_show, div_to_hide) {
        $("#add_your_comment" + qId + " textarea").val("Input your comment...");
        showHideDiv(div_to_show, div_to_hide);
    };

    /**
     * Show/Hide spec review question.
     * 
     * @param div_to_show the div to show
     * @param div_to_hide the div to hide
     */
    showHideDiv = function(div_to_show, div_to_hide){
        $('#'+div_to_show).show();
        $('#'+div_to_hide).hide();
    };
    
    /**
     * Change the comment according to the input text.
     */
    $('.textarea1 textarea').click(function(){
        if ($(this).val() == 'Input your comment...') {
            $(this).html('');
        }
    }).blur(function() {
        if ($(this).val() == '') {
            $(this).html('Input your comment...');
        }
    });

    /**
     * Set image button hover effect.
     */
    $("div.comment_specreview a").hover(function(){
        $("img.sr_editcomment", this).attr("src", "/images/edit_red.png");
    }, function() {
        $("img.sr_editcomment", this).attr("src", "/images/edit.png");
    });
    $("div.to_add_your_comment a").hover(function(){
        $("img.sr_addcomment", this).attr("src", "/images/add_your_comment_hover.png");
    }, function() {
        $("img.sr_addcomment", this).attr("src", "/images/add_your_comment.png");
    });
    
    /**
     * Render the specification review page.
     * 
     * @param items the items to show
     * @param userComments the user comments
     * @param guidelines the guidelines
     * @param contestId the contest id
     * @param userName the user name
     */
    renderSpecReviewPage = function(items, userComments, guidelines, contestId, userName) {
        // set reviewr's comment
        $.each(items, function(qId, item) {
            // set error if reviewer's answer is not "yes"
            var imgSrc = "/images/ico-success2.png";
            /*
            if (item.answer != "yes") {
                imgSrc = "/images/ico-error2.png";
                $("#caption_specreview" + qId).find("h2").addClass("red");
                $("#det_specreview" + qId).find("h2").addClass("red");
            }
            */
            $("#caption_specreview" + qId).find("img.icon_status").attr("src", imgSrc);
            $("#det_specreview" + qId).find("img.icon_status").attr("src", imgSrc);
            
            var oldComment = $("#reviewer_comment_specreview" + qId).find(".old_comment");
            if (item.comments.length > 0) {
                oldComment.remove();
            }
            // set comments
            $.each(item.comments, function(index, comment) {
                var c = oldComment.clone();
                c.find(".text_comment").html(comment.commentType);
                c.append($("<p>").append(comment.comment.replace(/&lt\;br\/&gt\;/g, "<br/>")));
                $("#reviewer_comment_specreview" + qId).append(c);
            });
        });
        
        // set div class
        $("div.no_details").each(function(index, item) {
            if (index % 2 == 0) {
                $(item).addClass("spec_review_odd_question");
            } else {
                $(item).addClass("spec_review_question");
            }
        });
        
        // set user comments
        $.each(userComments, function(qId, comments) {
            $.each(comments, function(index, comment) {
                updateCommentDiv(comment, qId, contestId, userName);
            });
            if (comments.length > 0) {
                $("#comment_specreview" + qId).show();
            }
        });
    };
    
    /**
     * Handle resubmit result.
     * 
     * @param jsonResult the json result
     */
    handleResubitResult = function(jsonResult) {
        if (jsonResult.result != null) {
            setPagePendingReview();
        }
    };
    
    /**
     * Change page style for waiting for fixes status.
     */
    setPageWaitingForFixes = function() {
        $(".container2Content_det span.status").html("Waiting For Fixes...");
        $("#resubmit").hide();
        $(".to_add_your_comment").hide();
        $(".sr_editcomment").hide();
        $(".add_your_comment").hide();
    };
    
    /**
     * Change page style for waiting for fixes status.
     */
    setPagePendingReview = function() {
        $(".container2Content_det span.status").html("Spec Review in Progress...");
        $("#resubmit").hide();
    };    
    
    /**
     * Handle add comment event or update comment event.
     * 
     * @param questionId the question id
     * @param contestId the contest id
     * @param userName the user name
     * @param action the action, "add" or "update"
     * @param commentId the comment id
     */
    handleAddOrUpdateCommentEvent = function(questionId, contestId, userName, action, commentId) {
        var comment = $("#add_your_comment" + questionId).find("textarea").val();

        sendRequest(questionId, contestId, userName, action, commentId, comment);
    };

    sendRequest = function(questionId, contestId, userName, action, commentId, comment) {
        var request = {
            "questionId" : questionId,
            "projectId" : contestId,
            "commentBy" : userName,
            "action" : action,
            "commentId" : commentId,
            "subject" : questionNames[questionId],
            "comment" : comment
        };
        
        request.comment = request.comment.replace(/\\/g, "\\\\");
        //request.comment = $("#xConvertor").text(request.comment).html();
        
        $.ajax({
            type: 'post',
            url: '../ajax/saveSpecReviewComment.action',
            data: request,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleCommentResult(jsonResult, questionId, contestId, userName);
            }
        });
    };

    /**
     * Update comment div, if it's not exist, then create one.
     * 
     * @param userComment the user comment object
     * @param questionId the question id
     * @param contestId the contest id
     * @param userName the user name
     */
    updateCommentDiv = function(userComment, questionId, contestId, userName) {
        var commentDivId = "commentDiv_" + userComment.commentId;
        var commentDiv = $("#" + commentDivId);
        if (commentDiv.length == 0) {
            commentDiv = $("<div class='comment_specreview'>");
            commentDiv.attr("id", commentDivId);
            var oldComment = $("<div>");
            oldComment.addClass("old_comment");
            oldComment.append("<a href='javascript:;'><img class='sr_editcomment' alt='Edit comment' src='/images/edit.png'></a>");
            oldComment.append("<p><span class='text_reviewer_handle'>Your comment: </span><span class='date_field'>(00/00/0000 00.00)</span></p>");
            oldComment.append("<p class='comment_field'");
            commentDiv.append(oldComment);
            $("#comment_area_" + questionId).append(commentDiv);

            var editDiv = $("<div class='add_your_comment'>");
            var textareaDiv = $("<div class='textarea1'>");
            textareaDiv.append("<textarea>");
            editDiv.append(textareaDiv);
            editDiv.append("<p class='add_comment'>");        
            editDiv.find("p").append("<a href='javascript:;' class='add_comment_cancel_text cancel_link'>Cancel</a>");
            editDiv.find("p").append("<a href='javascript:;' class='add_comment_cancel_text edit_link'>Edit</a>");
            $("#comment_area_" + questionId).append(editDiv);
            registerEditEvent();
        }
        
        $("#" + commentDivId).next().hide();
        
        $("#" + commentDivId + " .comment_field").html(userComment.comment);
        if (userComment.commentDate.month != null) {
            $("#" + commentDivId + " .date_field").html(getDateFormatString(userComment.commentDate));
        } else {
            $("#" + commentDivId + " .date_field").html(userComment.commentDate);
        }
        // use user name instead of "Your comment" and hide edit link if current user isn't the comment user
        if (userComment.commentBy != userName) {
            commentDiv.find(".text_reviewer_handle").html(userComment.commentBy + " comment: ");
            commentDiv.find(".sr_editcomment").hide();
        }
    };
    
    /**
     * Get formated string with specified date.
     *
     * @param date the date to format
     * @return formated string
     */
    getDateFormatString = function(date) {
        var ret = "(";
        if (date.month + 1 < 10) {
            ret += "0" + (date.month + 1);
        } else {
            ret += (date.month + 1);
        }
        ret += "/";
        if (date.date + 1 < 10) {
            ret += "0" + date.date;
        } else {
            ret += date.date;
        }
        ret += "/";
        ret += (date.year + 1900) + " ";
        if (date.hours < 10) {
            ret += "0" + date.hours;
        } else {
            ret += date.hours;
        }
        ret += ":";
        if (date.minutes < 10) {
            ret += "0" + date.minutes;
        } else {
            ret += date.minutes;
        }
        ret += ")";
        return ret;
    };
    
    /**
     * Handle add or update comment result.
     * 
     * @param jsonResult the json result
     * @param questionId the question id
     * @param contestId the contest id
     * @param userName the user name
     */
    handleCommentResult = function(jsonResult, questionId, contestId, userName) {
        if (jsonResult.result != null) {
            var data = jsonResult.result["return"];
            data.userComment.comment = data.userComment.comment.replace(/\\\\/g, "\\");
            updateCommentDiv(data.userComment, questionId, contestId, userName);
        }
    };

    registerEditEvent = function() {
        $(".comment_specreview a").unbind("click");
        $(".comment_specreview a").click(function(e) {
            showEditArea(e);
        });

        $(".add_your_comment .cancel_link").unbind("click");
        $(".add_your_comment .cancel_link").click(function(e) {
            $(e.target).parent().parent().hide();
        });

        $(".add_your_comment .edit_link").unbind("click");
        $(".add_your_comment .edit_link").click(function(e) {
            var editDiv = $(e.target).parent().parent();
            var commentDiv = editDiv.prev();
            var questionId = commentDiv.parent().attr("id").substring("comment_area_".length);
            var commentId = commentDiv.attr("id").substring("commentDiv_".length);
            var comment = editDiv.find("textarea").val();
    
            sendRequest(questionId, contestId, userName, "update", commentId, comment);
        });

    };

    showEditArea = function(e) {
        var commentDiv = $(e.target).parent().parent().find("a").parent().parent();
        commentDiv.next().show();

        commentDiv.next().find("textarea").val($.trim(commentDiv.find(".comment_field").html()));
    };
    
    // set resubmit click event
    $("#resubmit a").click(function() {
        $.ajax({
            type: 'post',
            url: '../ajax/resubmitSpecReview.action',
            data: {
                "content": "content",
                "projectId": contestId
            },
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleResubitResult(jsonResult);
            }
        });
    });
});
