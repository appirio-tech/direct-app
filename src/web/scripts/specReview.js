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
    /**
     * Show/Hide spec review question.
     * 
     * @param div_to_show the div to show
     * @param div_to_hide the div to hide
     */
    showHideDiv = function(div_to_show, div_to_hide){
        $('#'+div_to_show).show();
        $('#'+div_to_hide).hide();
    }
    
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
                c.append($("<p>").append(comment.comment));
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
        
        // set resubmit click event
        /*
        $("#resubmit a").click(function() {
            $.ajax({
                type: 'post',
                url: '../ajax/resubmitSpecReview.action',
                data: {
                    "content": "content",
                    "contestId": contestId
                },
                cache: false,
                dataType: 'json',
                success: function(jsonResult) {
                    handleResubitResult(jsonResult);
                }
            });
        });
        */
    };
    
    /**
     * Handle resubmit result.
     * 
     * @param jsonResult the json result
     */
    handleResubitResult = function(jsonResult) {
        if (jsonResult.result != null) {
            setPageWaitingForFixes();
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
     * Handle add comment event or update comment event.
     * 
     * @param questionId the question id
     * @param contestId the contest id
     * @param userName the user name
     * @param action the action, "add" or "update"
     * @param commentId the comment id
     */
    handleAddOrUpdateCommentEvent = function(questionId, contestId, userName, action, commentId) {
        var request = {
            "questionId" : questionId,
            "contestId" : contestId,
            "commentBy" : userName,
            "action" : action,
            "commentId" : commentId,
            "subject" : questionNames[questionId]
        };
        
        request.comment = $("#add_your_comment" + questionId).find("textarea").val();
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
            commentDiv = $("<div>");
            commentDiv.addClass("old_comment");
            commentDiv.attr("id", commentDivId);
            commentDiv.append("<a href='javascript:;'><img class='sr_editcomment' alt='Edit comment' src='/images/edit.png'></a>");
            commentDiv.append("<p><span class='text_reviewer_handle'>Your comment: </span><span id='" + commentDivId + "_time'>(00/00/0000 00.00)</span></p>");
            commentDiv.append("<p id='" + commentDivId + "_content'>");
            $("#comment_specreview" + questionId).append(commentDiv);
        }
        $("#" + commentDivId + "_content").html(userComment.comment);
        if (userComment.commentDate.month != null) {
            $("#" + commentDivId + "_time").html(getDateFormatString(userComment.commentDate));
        } else {
            $("#" + commentDivId + "_time").html(userComment.commentDate);
        }
        // use user name instead of "Your comment" and hide edit link if current user isn't the comment user
        if (userComment.commentBy != userName) {
            commentDiv.find(".text_reviewer_handle").html(userComment.commentBy + " comment: ");
            commentDiv.find(".sr_editcomment").hide();
        }
            
        commentDiv.find("a").unbind("click");
        commentDiv.find("a").click(function() {
            $($("#add_your_comment" + questionId).find("a")[1]).show();
            $($("#add_your_comment" + questionId).find("a")[2]).hide();
            $("#add_your_comment" + questionId).find("textarea").val($("#" + commentDivId + "_content").html());
            
            $("#add_your_comment" + questionId).show();
            $("#to_add_your_comment" + questionId).hide();
            
            $($("#add_your_comment" + questionId).find("a")[1]).unbind("click");
            $($("#add_your_comment" + questionId).find("a")[1]).click(function() {
                handleAddOrUpdateCommentEvent(questionId, contestId, userName, "update", userComment.commentId);
                $($("#add_your_comment" + questionId).find("a")[1]).hide();
                $($("#add_your_comment" + questionId).find("a")[2]).show();
                
                $("#add_your_comment" + questionId).hide();
                $("#to_add_your_comment" + questionId).show();
            });
            
            $($("#add_your_comment" + questionId).find("a")[0]).click(function() {
                $($("#add_your_comment" + questionId).find("a")[1]).hide();
                $($("#add_your_comment" + questionId).find("a")[2]).show();
            });
        });
    
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
});
