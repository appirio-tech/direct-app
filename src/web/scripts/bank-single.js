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
 * @author tangzx, GreatKevin
 * @version 1.0.3
 */
var listLikes = new Array();
var listDislikes = new Array();
var listExtra = new Array();
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

$(document).ready(function(){
	
		$('#carouselSingle').cycle({ 
			fx:     'scrollHorz', 
			prev:   '.navSingleSubmissionSlidePrev', 
			next:   '.navSingleSubmissionSlideNext',  
			timeout:   0,
			pause:  1,
			speed: 1000
		});
		//$('#carouselSingle').css('height', '560px');
		/* new added for contest dashboard */
		if($(".SubmissionSlotTitle").length > 0){
			$(window).scrollTop($(".SubmissionSlotTitle").offset().top - 15);
		}
		/* end */
						   
$("a.actButtonlike, a.actButtondislike, a.actButtonzoom, a.actButtonzoom, a.actButtondollar, a.actButtonstar").hover(function() { 
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

if($.browser.msie && $.browser.version == 6.0){	   
		$("#contest-sort label").css({'zoom':'1' });   
	}

		$("a.thumbGrid").hover(function() {
			var $next = $(this).next(".submissionAction");								   
			$next.css("display", "block");
			$next.hover(function() { 
				$next.css("display", "block");
				},function() {
				$next.css("display", "none");
			}); 
			},function() {
			var $next = $(this).next(".submissionAction");			
			$next.css("display", "none"); 
		});
						   
	
	$('.TB_overlayBG').css("opacity", "0.6");
	$('#TB_overlaySingle').hide();
	$('#TB_closeWindowButtonSingle').click(function(){
		$('#TB_overlaySingle').hide();
		$('#TB_windowSingle').hide();										  
	});
	$(".actButtonzoom").click(function(){
		var img = document.createElement('img');
		img.src =  $(this).parent().parent().parent().parent().children("a.thumbSingle").children('img').attr('longdesc');
		$('#TB_ajaxContentSingle').empty().append(img);
//		TB_ajaxWindowTitleSingle
		$('#TB_overlaySingle').show();
		$('#TB_windowSingle').show();
		$('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
		var winH = $("body").height();
		var winW = $(window).width();
		$('#TB_windowSingle').css({
            'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_windowSingle").width() / 2)) + 'px',
			'margin-top' : ((winH/2)-($("#TB_windowSingle").height()/2)) +'px'
        });
		return false;
	});
								
	//if submission id already exist in the list than remove it
	function removeIfExist(id)
	{
		
		for(var i=0;i<arrPrize.length;i++)
		{
			if ( $("#"+arrPrize[i]).children().length == 3)
			{
				if ($("#"+arrPrize[i]).children().eq(2).text() == id)
				{
					$("#"+arrPrize[i]).html('<a href="#" class="thumb"><span></span></a>');
				}
			}
		}
		for(var i=0;i<listExtra.length;i++)
		{
			if (listExtra[i] == id)
			{
				listExtra.remove(i,0);
				$("#numExtra").html("(" + listExtra.length + ")");
			}
		}
		
		for(var i=0;i<listLikes.length;i++)
		{
			if(listLikes[i] == id)
			{
				listLikes.remove(i,0);
				$("#likeCount").html(listLikes.length);
			}
		}
		for(var i=0;i<listDislikes.length;i++)
		{
			if(listDislikes[i] == id)
			{
				listDislikes.remove(i,0);
				$("#dislikeCount").html(listDislikes.length);
			}
		}
	}
	
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
	function getBankData() {
		var jsonData = {};
		$("#bankSelectionItemList li:not(:last-child)").each(function() {
			jsonData[this.id] = $(this).children("label").eq(0).html();
		});
		jsonData["listLikes"] = listLikes;
		jsonData["listDislikes"] = listDislikes;
		jsonData["listExtra"] = listExtra;
		var cdata = $.cookie(COOKIE_NAME);
		if (!cdata) {
			cdata = {};
			cdata[contestId] = {};
		} else {
			cdata = jQuery.parseJSON(cdata);
			if (!cdata[contestId]) {
				cdata[contestId] = {};
				cdata[contestId][roundType] = {};
			}
		}
		cdata[contestId][roundType] = jsonData;
		$.cookie(COOKIE_NAME, JSON.stringify(cdata), cookieOptions);
	}

	// set id to bankSelectionItemList's children
	$("#bankSelectionItemList").children().each(function(index) {
		if(!this.id) {
			this.id = arrPrize[index];
		}
	});
	// set class name to addToBank's children
	$(".addToBank").each(function() {
		$(this).children("a:not(:last-child)").each(function(index) {
			$(this).addClass(arrSlot[index]);
		});
	});

    var hasCheckout = $('#hasCheckout').val() == 'true';
    
    	
	// update the order of the bank slots
	function updateSortable()
	{
		var result = $("#bankSelectionItemList").sortable('toArray');
		
		//reset slot prize number
		$("#bankSelectionItemList li:not(#extraPrize)").each(function(index){
			$(this).attr('id',arrPrize[index]);
		});
		
		$("#bankSelectionItemList li:not(#extraPrize)").each(function(index){
			if ($(this).children().length == 3)
			{
				var $item = $(this).children(".thumb").children("img").attr("src");
				var $itemlabel = $(this).children("label").text();
				var $slotId = arrPrize[index];
				var $slotName = arrSlot[index];
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass($slotName);
				getBankData();
				
				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
					getBankData();
					return false;
				});
			}
		});
	}

	$("#bankSelectionItemList").disableSelection(); 

    if (hasContestWritePermission) {
        if (!hasCheckout) {
            $("#bankSelectionItemList").children().each(function(index) {
                $(this).droppable({
                    accept: '.carouselSingleItem',
                    hoverClass: 'drophover',
                    drop: function(event, ui) {
                        var $item = ui.draggable.children(".hidden").children("img.submissionIMG").attr("src");
                        var $itemlabel = ui.draggable.children(".hidden").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        var $slotId = $(this).attr('id');
                        var $slotName = arrSlot[arrPrize.indexOf($slotId)];
                        $(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="' +
                                     getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item +
                                     '" alt="" /></a><label>' + $itemlabel + '</label>');
                        $(".statusSubmission").removeClass($slotName);
                        $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass($slotName);

                        $('#remove' + $slotId).click(function() {
                            $("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
                            $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
                            getBankData();
                            return false;
                        });
                        getBankData();
                    }
                }).sortable({
                    items: 'li:not(#extraPrize)'
                });
            });
        }
    
        if (!hasCheckout) {
            $("#bankSelectionItemList").sortable({
                items: 'li:not(#extraPrize)',
                update:updateSortable
            });
        }

        // submission li init draggable
        $(".carouselSingleItem:not(.prizedSubmission)").draggable({
            revert: true, 
            cursor: 'move', 
            cursorAt: { top: 35, left: 35 },
            helper: function(event) {
                    var $item = $(this).children(".hidden").children("img.submissionIMG").attr("src");
                    return $('<div class="excerpt"><img src="' + $item + '" /></div>'); 
                } 
        }); 
        
        
        // #clearSlots click function to clear all slots
        if (!hasCheckout) {
        $("#clearSlots").click(function() {
                listExtra = new Array();
                listLikes = new Array();
                listDislikes = new Array();
                $("#likeCount").html("0");
                $("#dislikeCount").html("0");
                $("#numExtra").html("(0)"); 
                $(".icoSingleSubmissionBankLocation").removeClass("firstSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("secondSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("thirdSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("fourthSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("fifthSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("dollarSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("likeSlots");
                $(".icoSingleSubmissionBankLocation").removeClass("dislikeSlots");
                $("#bankSelectionItemList li:not(:last-child)").html('<a href="#" class="thumb"><span></span></a>');
                getBankData();
                return false;
        });   
        }
        
        // #likeFolder init droppable
        $("#likeFolder a").droppable({ 
                    drop: function(ev, ui) { 
                        $(this).removeClass('drophover');
                        var $count = $(this).children("span").text();  
                        var $countAdd = parseInt($count) + 1; 
                        //$(".statusSubmission").removeClass("likeSlot");
                        var $itemlabel = ui.draggable.children(".hidden").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        listLikes.push($itemlabel);
                        $("#likeCount").html(listLikes.length);
                        $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("likeSlots"); 
                        getBankData();
                    },
                    over: function(){
                        $(this).addClass('drophover');
                    },
                    out : function(){
                        $(this).removeClass('drophover');
                    },	
                    tolerance:"pointer"
        });
        
        
        // #likeFolder init droppable
        $("#dislikeFolder a").droppable({ 
                    drop: function(ev, ui) { 
                        $(this).removeClass('drophover');
                        var $count = $(this).children("span").text();  
                        var $countAdd = parseInt($count) + 1; 
                        //$(".statusSubmission").removeClass("dislikeSlot");
                        var $itemlabel = ui.draggable.children(".hidden").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        listDislikes.push($itemlabel);
                        $("#dislikeCount").html(listDislikes.length);
                        $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dislikeSlots"); 
                        getBankData();
                    },
                    over: function(){
                        $(this).addClass('drophover');
                    },
                    out : function(){
                        $(this).removeClass('drophover');
                    },	
                    tolerance:"pointer"
        });
        
         
        // #extraPrize init droppable
        $("#extraPrize").droppable({
            hoverClass: 'drophover', 
            drop: function(event, ui) {
                var $item = ui.draggable.children(".hidden").children("img.submissionIMG").attr("src");
                var $itemlabel = ui.draggable.children(".hidden").children("label").children(".submissionID").text();
                removeIfExist($itemlabel);  
                listExtra.push($itemlabel);
                $("#numExtra").html("(" + listExtra.length + ")");
                $(".icoSingleSubmissionBankLocation").removeClass("dollarSlots");
                $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dollarSlots");
                
                $('#removeExtraPrize').click(function() { 
                     $("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
                     $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
                     getBankData();
                    return false;
                });
                getBankData();
                
            }
        });  
        
    }
	
    if (!hasCheckout) {
	//Add to Bank number 1st
	$(".firstSlots").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#firstPrize").html('<a href="#" id="removeFirstPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("firstSlots");
			$('#removeFirstPrize').click(function() { 
				$("#firstPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	
	//Add to Bank number 2nd
	$(".secondSlots").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#secondPrize").html('<a href="#" id="removeSecondPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("secondSlots");
			$('#removeSecondPrize').click(function() { 
				$("#secondPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 3rd
	$(".thirdSlots").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#thirdPrize").html('<a href="#" id="removeThirdPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("thirdSlots");
			$('#removeThirdPrize').click(function() { 
				$("#thirdPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 4th
	$(".fourthSlots").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#fourthPrize").html('<a href="#" id="removeFourthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("fourthSlots");
			$('#removeFourthPrize').click(function() { 
				$("#fourthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 5th
	$(".fifthSlots").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#fifthPrize").html('<a href="#" id="removeFifthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("fifthSlots");
			$('#removeFifthPrize').click(function() { 
				$("#fifthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
    }

	//Add to Extra Purchase
	$(".extraSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel");
			removeIfExist($itemlabel); 
			listExtra.push($itemlabel);
			$("#numExtra").html("(" + listExtra.length + ")");
			$(".statusSubmission").addClass("dollarSlot");
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").addClass("dollarSlots");
			$('#removeExtraPrize').click(function() { 
				$("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
				return false;
			});
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
				$("#numExtra").html("(" + listExtra.length + ")");
				$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
				getBankData();
			}
	});
	 
	
	//like bank
	$(".likeSubmission").click(function() { 
		var $item = $(this).attr("rel"); 
		removeIfExist($item);
		listLikes.push($item);
		$("#likeCount").html(listLikes.length);
		$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("likeSlots");
		getBankData();
		return false;
	}); 
	
	//remove from like bank
	$(".noLikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listLikes, $item) != -1) {
			$("#likeCount").html(listLikes.length);
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
			getBankData();
		}
	});

	//dislike bank
	$(".dislikeSubmission").click(function() { 
		var $item = $(this).attr("rel");
		removeIfExist($item);
		listDislikes.push($item);
		$("#dislikeCount").html(listDislikes.length);
		$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dislikeSlots");
		getBankData();
		return false;
	});  
	
	//remove from dislike bank
	$(".noDislikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listDislikes, $item) != -1) {
			$("#dislikeCount").html(listDislikes.length);
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
			getBankData();
		}
	});

	initDialog('msgDialog', 300);
	// block ui before ajax calls
	function beforeAjax() {
        modalPreloader();
	}
	// unblock ui after ajax calls
	function afterAjax() {
        modalClose();
	}

	// update feedback text through ajax call
	$("#updateFeedback").click(function() {
		var feedback = $("#feedback").val();
		feedback = $.trim(feedback);
		if (feedback.length == 0) {
			$('#msgDialog p').html("Feedback content can't be empty.");
			$('#msgDialog').dialog('open');
			return false;
		}
		$.ajax({
			type: 'POST',
			url:  ctx + "/contest/updateSubmissionFeedback",
			data: {projectId : contestId, submissionId : submissionId, feedbackText : feedback, roundType : roundType},
			cache: false,
			dataType: 'json',
			async : false,
			success: function (jsonResult) {
//				$("<tr><td class='label'>Feedback:</td><td>" + feedback + "</td></tr>").insertAfter($("#submissionData").find("tr").last());
//				$("#submissionFeedback").remove();
			},
			beforeSend: beforeAjax,
			complete: afterAjax
		});
	});
	// url of all submissions page
	var allSubs = "submissions?projectId=" + contestId + "&formData.viewType=GRID&formData.roundType=" + roundType;
	// show all submissions
	$("#allSubmissionBtn").click(function() {
		window.location.href = allSubs;
		return false;
	});
	// show like submissions
	$("#likeSubmissionBtn").click(function() {
		window.location.href = allSubs + "#like";
		return false;
	});
	// show dislike submissions
	$("#dislikeSubmissionBtn").click(function() {
		window.location.href = allSubs + "#dislike";
		return false;
	});

	// get bank data from cookie
	var bankData = $.cookie(COOKIE_NAME);
	if (bankData) {
		bankData = jQuery.parseJSON(bankData);
		if (bankData && bankData[contestId]) {
			bankData = bankData[contestId][roundType];
		} else {
			bankData = null;
		}
	}

    function initBanks(index, link) {
        var $item = link.attr("title");
        var $itemlabel = link.attr("rel");

        var f = arrPrize[index];
        var s = arrSlot[index];

        $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass(s);
        if (link.hasClass('disabledControl')) {
            $("#" + f).html('<a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
        } else {
            $("#" + f).html('<a href="#" id="remove' + f + '" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
            $('#remove' + f).click(function() {
                $("#" + f).html('<a href="#" class="thumb"><span></span></a>');
                $(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation");
                getBankData();
                return false;
            });
        }
    }

	// restore bank ui using bank data
	if (bankData) {
		listLikes = bankData.listLikes;
		listDislikes = bankData.listDislikes;
		listExtra = bankData.listExtra;
		// set prize bank data from cookie
		$("#bankSelectionItemList li:not(:last-child)").each(function(index) {
			var $itemlabel = bankData[arrPrize[index]];
			var $item = "https://www.topcoder.com/impersonation2/cockpitStudio.do?&sbmid=" + $itemlabel + "&sbt=thumb";
			var $slotId = arrPrize[index];
			if ($itemlabel) {
				if ($itemlabel != submissionId) {
                    if (hasCheckout) {
                        $(this).html('<a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' 
                                + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
                    } else {
                        $(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
                        $('#remove' + $slotId).click(function() {
                            $("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
                            getBankData();
                            return false;
                        });
                    }
				} else {
                    initBanks(index, $("." + arrSlot[index]));
				}
			}
		});
		// set the count of like submissions
		$("#likeCount").html(listLikes.length);
		// set the count of dislike submissions
		$("#dislikeCount").html(listDislikes.length);
		// set the count of extra purchases
		$("#numExtra").html("(" + listExtra.length + ")");
		if (listLikes.indexOf(submissionId) >= 0) {
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("likeSlots");
		}
		if (listDislikes.indexOf(submissionId) >= 0) {
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dislikeSlots");
		}
		if (listExtra.indexOf(submissionId) >= 0) {
			$(".submissionAction").children(".icoSingleSubmissionBankLocation").addClass("dollarSlots");
		}
	}
    
    if (!hasContestWritePermission) {
        $("#bankSelectionButton").hide();
        $("#submissionFeedback").hide();
        $(".btn_remove").hide();
    }
}); 
