/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Studio submission single view page.
 *
 * <p>
 * Version 1.0.1 (TC Direct Release Assembly 7) Change notes:
 * - Hide input fields if user has no write permission.
 * </p>
 *
 * <p>
 * Version 1.0.2 (TC Direct Contest Dashboard Update Assembly) Change notes:
 * - Apply to new prototype.
 * </p>
 *
 * <p>
 * Version 1.0.3 (Release Assembly - TopCoder Cockpit AJAX Revamp version 1.0) Change notes:
 * - Update the ajax loading indicator.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) Change notes:
 * - Updated to follow the new prototype.
 * </p>
 *
 * @author tangzx, GreatKevin, minhu
 * @version 1.1
 */
var listLikes = new Array();
var listDislikes = new Array();
var listExtra = new Array();
var listPrize = [0,0,0,0,0];
var arrPrize = ["firstPrize","secondPrize","thirdPrize","fourthPrize","fifthPrize"];
var arrSlot = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots"];
var cookieOptions = { path: '/', expires: 1 };
var COOKIE_NAME = "bankSelection";
var contestId;
var roundType;
var submissionId;

// Array Remove - By John Resig (MIT Licensed)
Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};

$(document).ready(function() {	
    if ($('.addFeedback').length==0){
        // adjust the action bar
        $('.submissionInfoFeedback').addClass('withoutFeedback');
        $('.submissionInfoFeedback span.tl,.submissionInfoFeedback span.bl').hide();
    }     
    
    $('.viewFeedbackContent').each(function() {
        var html=$(this).html().replace(/[\n]/g,'<br />')   
    });
        
	//if submission id already exist in the list than remove it
	function removeIfExist(id)
	{
		for(var i=0;i<listPrize.length;i++)
		{
			if (listPrize[i] == id)
			{
				listPrize[i]=0;
			}
		}
		for(var i=0;i<listExtra.length;i++)
		{
			if (listExtra[i] == id)
			{
				listExtra.remove(i,0);
			}
		}
		
		for(var i=0;i<listLikes.length;i++)
		{
			if(listLikes[i] == id)
			{
				listLikes.remove(i,0);
			}
		}
		for(var i=0;i<listDislikes.length;i++)
		{
			if(listDislikes[i] == id)
			{
				listDislikes.remove(i,0);
			}
		}
	}   
						   
	$("a.actButtonlike, a.actButtondislike, a.actButtonzoom, a.actButtondollar, a.actButtonstar").hover(function() { 
		var $this = $(this);	
		var $next = $(this).next(".dialog-mini-wrapper");								   
		$next.css("display", "block");
		$(this).children("span").addClass("hover");
		$next.hover(function() {
			$next.css("display", "block");
			$this.children("span").addClass("hover");
			},function() {
			$next.css("display", "none");
			$this.children("span").removeClass("hover");
		}); 
		},function() {
		var $next = $(this).next(".dialog-mini-wrapper");			
		$next.css("display", "none"); 
		$(this).children("span").removeClass("hover");
	});
	
	// contest id
	contestId = $("#contestId").val();
	// round type
	roundType = $("#roundType").val();
	// submission id of this single page
	submissionId = $("#submissionId").val();
	// get the url of a sumbission's single page
	function getSinglePage(submissionId) {
		return "studioSubmission?projectId=" + contestId + "&formData.submissionId=" + submissionId + "&formData.roundType=" + roundType;
	}

	// update the bank data to cookie
    var cookieInited = false;
	function getBankData() {
		var cdata = $.cookie(COOKIE_NAME);
		if (!cdata) {
			cdata = {};
		} else {
			cdata = jQuery.parseJSON(cdata);
		}
        if (!cdata[contestId]) {
            cdata[contestId] = {};
        }        
        if (!cdata[contestId][roundType]) {
            cdata[contestId][roundType] = {};
        }
		var jsonData = cdata[contestId][roundType];
		if (!jsonData["listLikes"] || cookieInited) {
		    jsonData["listLikes"] = listLikes;
		} else {
		    listLikes = jsonData["listLikes"];
		}
		if (!jsonData["listDislikes"] || cookieInited) {
		    jsonData["listDislikes"] = listDislikes;
		} else {
		    listDislikes = jsonData["listDislikes"];
		}
		if (!jsonData["listExtra"] || cookieInited) {
		    jsonData["listExtra"] = listExtra;
		} else {
		    listExtra = jsonData["listExtra"];
		}
		
		if (!cookieInited) {
		    // init the prize info
            for(var i=0;i<listPrize.length;i++) {
                listPrize[i]=jsonData[arrPrize[i]];
            }
		} else {
		    // save prize info
            for(var i=0;i<listPrize.length;i++) {
                jsonData[arrPrize[i]]=listPrize[i];
            }
		}
		$.cookie(COOKIE_NAME, JSON.stringify(cdata), cookieOptions);
		cookieInited = true;
	}

	// set class name to addToBank's children
	$(".addToBank").each(function() {
		$(this).children("a:not(:last-child)").each(function(index) {
			$(this).addClass(arrSlot[index]);
		});
	});

    var hasCheckout = $('#hasCheckout').val() == 'true';

    // set the place/thumb up/thumb down/extra indicator
	function setIndicator(cls) {
        $(".submissionActionGrid").children(".numIcon").removeClass().addClass("numIcon").addClass(cls);
        if (cls.indexOf('num')==0) {
            $('#popupRankText').html(cls.substr(3));
        } else {
            $('#popupRankText').html("not assigned");
        }
	}
    if (!hasCheckout) {
        //Add to Bank
        $(".firstSlots,.secondSlots,.thirdSlots,.fourthSlots,.fifthSlots").click(function() { 	
                var $itemlabel = $(this).attr("rel"); 
                removeIfExist($itemlabel);
                var i;
                for (i = 0; i < arrSlot.length; i++) {
                    if ($(this).hasClass(arrSlot[i])) {
                        break;
                    }
                }
                setIndicator("num" + (i+1));
                listPrize[i] = $itemlabel;
                getBankData();
                return false;
        }); 
    }

	//Add to Extra Purchase
	$(".extraSlot").click(function() { 		
			var $itemlabel = $(this).attr("rel");
			removeIfExist($itemlabel); 
            setIndicator("icoExtra");
			listExtra.push($itemlabel);
			getBankData();
			return false;
	}); 

	// remove an element obj from array, if no element was removed, -1 would be returned
	function arrayRemove(array, obj) {
			var index = -1;
			for (var i = 0; i < array.length; i++) {
				if (obj == array[i]) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				// not found
				return index;
			}
			array.splice(index, 1);
			return index;
	}
	//Remove From Extra Purchase
	$(".noExtraSlot").click(function() {
			var $itemlabel = $(this).attr("rel");
			if (arrayRemove(listExtra, $itemlabel) != -1) {
                setIndicator("icoNull");
				getBankData();
			}
	});
	
	//like bank
	$(".likeSubmission").click(function() { 
		var $item = $(this).attr("rel"); 
		removeIfExist($item);
        setIndicator("icoLike");
		listLikes.push($item);
		getBankData();
		return false;
	}); 
	
	//remove from like bank
	$(".noLikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listLikes, $item) != -1) {
            setIndicator("icoNull");
			getBankData();
		}
	});

	//dislike bank
	$(".dislikeSubmission").click(function() { 
		var $item = $(this).attr("rel");
		removeIfExist($item);
        setIndicator("icoDislike");
		listDislikes.push($item);
		getBankData();
		return false;
	});  
	
	//remove from dislike bank
	$(".noDislikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listDislikes, $item) != -1) {
            setIndicator("icoNull");
			getBankData();
		}
	});
	 

	// alert modal
	$('.addFeedbackButton').live('click',function(){
        modalLoad(milestoneReviewPhaseOpen ? "#alertModal2" : "#alertModalView");
		return false;
	});
	$("#closeViewBtn,#alertModalViewClose").live('click',function(){
		$("#alertModalView").css("display","none");
		modalClose();
		return false;
	});
	$("#cancelBtn,#alertModal2Close").live('click',function(){
		$("#alertModal2").css("display","none");
		modalClose();
		return false;
	});
	$("#alertModal3Close,#doneBtn").live('click',function(){	
		$('.addFeedbackButton').html("Edit Feedback");
        $("span.feedbackTitle").html("Edit Feedback"); 
        $("#alertModal3").find(".modalBodyBox").find(".hasAdded").html("Your feedback has been edited");
		$("#alertModal3").css("display","none");
		modalClose();
	});
	$('#editFeedbackBtn').live('click',function(){
		$("#alertModal3").css("display","none");
		$('.addFeedbackButton').html("Edit Feedback");
        $("span.feedbackTitle").html("Edit Feedback"); 
        $("#alertModal3").find(".modalBodyBox").find(".hasAdded").html("Your feedback has been edited");
        modalLoad("#alertModal2");
		return false;
	});
	//addFeedbackBox end
	
	//initDialog('msgDialog', 300);
	// block ui before ajax calls
	function beforeAjax() {
        modalPreloader();
	}
	// unblock ui after ajax calls
	function afterAjax() {
        //modalClose();
	}

	// save feedback text through ajax call
	$("#saveFeedbackBtn").click(function() {
		var feedback = $("#feedback").val();
		feedback = $.trim(feedback);
		if (feedback.length == 0) {
			ShowErrors("Feedback content can't be empty.");
			return false;
		}
        $("#alertModal2").css("display","none");
        modalClose();
		$.ajax({
			type: 'POST',
			url:  ctx + "/contest/updateSubmissionFeedback",
			data: {projectId : contestId, submissionId : submissionId, feedbackText : feedback, roundType : roundType},
			cache: false,
			dataType: 'json',
			async : false,
			success: function (jsonResult) {
                $("#alertModal3").find(".modalBodyBox").find("p").html(feedback.replace(/[\n]/g,'<br />'));
                modalLoad("#alertModal3");
			},
			beforeSend: beforeAjax,
			complete: afterAjax
		});
	});

	// restore bank ui using bank data
	getBankData();
    if (listLikes.indexOf(submissionId) >= 0) {
        setIndicator("icoLike");
    } else if (listDislikes.indexOf(submissionId) >= 0) {
        setIndicator("icoDislike");
    } else if (listExtra.indexOf(submissionId) >= 0) {
        setIndicator("icoExtra");
    } else if (listPrize.indexOf(submissionId) >= 0) {
        setIndicator("num" +(listPrize.indexOf(submissionId)+1));
    } else {
        setIndicator("icoNull");
    }
    
    if (!hasContestWritePermission) {
        $("#submissionFeedback").hide();
    }
}); 

