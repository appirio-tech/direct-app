/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * Studio submissions grid view page.
 *
 * <p>
 * Version 1.0.1 (TC Direct Release Assembly 7) Change notes:
 * - Hide input fields if user has no write permission.
 * </p>
 *
 * <p>
 * Version 1.0.2 (Direct Improvements Assembly Release 2) Change notes:
 * - Make drop submissions into the sink easy.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0.2
 */
 
var listLikes = new Array();
var listDislikes = new Array();
var listExtra = new Array();
var arrPrize = ["firstPrize","secondPrize","thirdPrize","fourthPrize","fifthPrize"];
var arrSlot = ["firstSlot","secondSlot","thirdSlot","fourthSlot","fifthSlot"];
var cookieOptions = { path: '/', expires: 1 };
var COOKIE_NAME = "bankSelection";
var contestId;
var roundType;

// Array Remove - By John Resig (MIT Licensed)
Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};

$(function(){

	//judge browser
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();	
	 
		$("#dropdownCountWrapper select").msDropDown(); 

	/**************************   fix browser    *************************************/
	//safari
	if(ua.match(/version\/([\d.]+).*safari/)!=null &&
	ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){		
		$("#dropdownCountWrapper  .dd").css('width','48px');
		 
	}
	if($.browser.msie && $.browser.version == 6.0){	  
		$("#dropdownCountWrapper  .dd").css({'width':'48px' });  
		$("#contest-sort label").css({'zoom':'1' });   
	}
	
	if($.browser.msie){	  
		$("#dropdownCountWrapper  .dd").css({'width':'48px' });   
	}
	
	if($.browser.mozilla){ 
		$("#dropdownCountWrapper  .dd").css({'width':'48px'});
		 
	}	
	 
});


$(document).ready(function(){
						    		   
						   
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
	// get the url of a sumbission's single page
	function getSinglePage(submissionId) {
		return "studioSubmission?projectId=" + contestId + "&formData.submissionId=" + submissionId + "&formData.roundType=" + roundType;
	}

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
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass($slotName);
				getBankData();

				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
					getBankData();
					return false;
				});
			}
		});
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

		if ($("#subFilterText").html() == "Submissions You Liked") {
			$("#likeSubmissionBtn").click();
		}
		if ($("#subFilterText").html() == "Submissions You Disliked") {
			$("#dislikeSubmissionBtn").click();
		}
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

    $("#bankSelectionItemList").disableSelection();
     

    if (hasContestWritePermission) {
        if (!hasCheckout) {
            $("#bankSelectionItemList").sortable({
                    items: 'li:not(#extraPrize)',
                    update:updateSortable
                });
        }
        
        // submission li init draggable
        $("#submissionGrid li:not(.prizedSubmission)").draggable({
            revert: true, 
            cursor: 'move',  
            cursorAt: { top: 0, left: 35 },
            helper: function(event) { 
                    return $('<div class="excerpt"><img width="60px" height="60px"  src="' + $(this).children(".statusSubmissionWrap").children("div").children("img.submissionIMG").attr("src") + '" /></div>');
                } 
        }); 

        // #clearSlots click function to clear all slots
        if (!hasCheckout) {
        $("#clearSlots").click(function() {
                $("#bankSelectionItemList li:not(:last-child)").html('<a href="#" class="thumb"><span></span></a>');
                listExtra = new Array();
                listLikes = new Array();
                listDislikes = new Array();
                $("#numExtra").html("(0)");
                $("#likeCount").html("0");
                $("#dislikeCount").html("0");

                $(".statusSubmission").removeClass("firstSlot");
                $(".statusSubmission").removeClass("secondSlot");
                $(".statusSubmission").removeClass("thirdSlot");
                $(".statusSubmission").removeClass("fourthSlot");
                $(".statusSubmission").removeClass("fifthSlot");

                $(".statusSubmission").removeClass("dollarSlot");
                $(".statusSubmission").removeClass("likeSlot");
                $(".statusSubmission").removeClass("dislikeSlot");
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
                        var $itemlabel = ui.draggable.children(".statusSubmissionWrap").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        listLikes.push($itemlabel);
                        $("#likeCount").html(listLikes.length);
                        $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("likeSlot"); 
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
                        var $itemlabel = ui.draggable.children(".statusSubmissionWrap").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        listDislikes.push($itemlabel);
                        $("#dislikeCount").html(listDislikes.length);
                        $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("dislikeSlot"); 
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
        

        if (!hasCheckout) {
            $("#bankSelectionItemList").children().each(function(index){
                $(this).droppable({
                    accept: '#submissionGrid li',
                    hoverClass: 'drophover',
                    drop: function(event, ui) {
                        var $item = ui.draggable.children(".statusSubmissionWrap").children("div").children("img.submissionIMG").attr("src");
                        var $itemlabel = ui.draggable.children(".statusSubmissionWrap").children("label").children(".submissionID").text();
                        removeIfExist($itemlabel);
                        var $slotId = $(this).attr('id');
                        var $slotName = arrSlot[arrPrize.indexOf($slotId)];
                        $(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
                        $(".statusSubmission").removeClass($slotName);
                        $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass($slotName);

                        $('#remove' + $slotId).click(function() {
                            $("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
                            $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
                            getBankData();
                            return false;
                        });
                        getBankData();
                    },
					tolerance:"touch"
                }).sortable({
                        items: 'li:not(#extraPrize)'
                    });
            });
        }

        // #extraPrize init droppable
        $("#extraPrize").droppable({
            hoverClass: 'drophover', 
            accept: '#submissionGrid li',	
            drop: function(event, ui) {
                var $item = ui.draggable.children(".statusSubmissionWrap").children("div").children("img.submissionIMG").attr("src");
                var $itemlabel = ui.draggable.children(".statusSubmissionWrap").children("label").children(".submissionID").text();
                removeIfExist($itemlabel);
                listExtra.push($itemlabel);
                $("#numExtra").html("(" + listExtra.length + ")");
                $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("dollarSlot");
                
                $('#removeExtraPrize').click(function() { 
                     $("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
                     $('#submission-'+$itemlabel).children(".statusSubmission").removeClass("dollarSlot");
                     getBankData();
                    return false;
                });
                getBankData();
            },	
            tolerance:"touch"
        });  
    }
    
    
	//Add to Bank number 1st
	$(".firstSlot:not(.disabledControl)").click(function() {
 			var $item = $(this).attr("title");
			var $itemlabel = $(this).attr("rel");
			removeIfExist($itemlabel);
			$(".statusSubmission").removeClass("firstSlot");
			$("#firstPrize").html('<a href="#" id="removeFirstPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("firstSlot");
			$('#removeFirstPrize').click(function() {
				$("#firstPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	});

	//Add to Bank number 2nd
	$(".secondSlot:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title");
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".statusSubmission").removeClass("secondSlot");
			$("#secondPrize").html('<a href="#" id="removeSecondPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("secondSlot");
			$('#removeSecondPrize').click(function() { 
				$("#secondPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 3rd
	$(".thirdSlot:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title");
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".statusSubmission").removeClass("thirdSlot");
			$("#thirdPrize").html('<a href="#" id="removeThirdPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("thirdSlot");
			$('#removeThirdPrize').click(function() { 
				$("#thirdPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 4th
	$(".fourthSlot:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title");
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".statusSubmission").removeClass("fourthSlot");
			$("#fourthPrize").html('<a href="#" id="removeFourthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("fourthSlot");
			$('#removeFourthPrize').click(function() { 
				$("#fourthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 5th
	$(".fifthSlot:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title");
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".statusSubmission").removeClass("fifthSlot");
			$("#fifthPrize").html('<a href="#" id="removeFifthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("fifthSlot");
			$('#removeFifthPrize').click(function() { 
				$("#fifthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Extra Purchase
	$(".extraSlot").click(function() { 	
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel");
			removeIfExist($itemlabel); 
			listExtra.push($itemlabel);
			$("#numExtra").html("(" + listExtra.length + ")");
			$("#submission-"+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("dollarSlot");
			$('#removeExtraPrize').click(function() { 
				$("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass("dollarSlot");
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
				$('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
				getBankData();
			}
	});

	//warning-box init
	$("#wb-like").hide();
	$("#wb-dislike").hide();  
	
	//like bank
	$(".likeSubmission").click(function() {
		//$("#wb-like").fadeTo("slow", 1.0);
		var $item = $(this).attr("rel"); 
		removeIfExist($item);
		listLikes.push($item);
		$("#likeCount").html(listLikes.length);
		$("#submission-"+$item).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("likeSlot");
		getBankData();
		return false;
	}); 
	//remove from like bank
	$(".noLikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listLikes, $item) != -1) {
			$("#likeCount").html(listLikes.length);
			$("#submission-"+$item).children(".statusSubmission").removeClass().addClass("statusSubmission");
			getBankData();
		}
	});

	
	//dislike bank
	$(".dislikeSubmission").click(function() {
		//$("#wb-dislike").fadeTo("slow", 1.0);
		var $item = $(this).attr("rel");
		removeIfExist($item);
		listDislikes.push($item);
		$("#dislikeCount").html(listDislikes.length);
		$("#submission-"+$item).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("dislikeSlot");
		getBankData();
		return false;
	});  
	
	//remove from dislike bank
	$(".noDislikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listDislikes, $item) != -1) {
			$("#dislikeCount").html(listDislikes.length);
			$("#submission-"+$item).children(".statusSubmission").removeClass().addClass("statusSubmission");
			getBankData();
		}
	});

	// show all submissions
	$("#allSubmissionBtn").click(function() {
		$("#subFilterText").html("All Submissions");
		$("#submissionGrid").children().eq(0).children().show();
		window.location.hash = "#all";
		return false;
	});
	// show like submissions
	$("#likeSubmissionBtn").click(function() {
		$("#subFilterText").html("Submissions You Liked");
		$("#submissionGrid").children().eq(0).children().show();
		$("#submissionGrid").children().eq(0).children().each(function() {
			if(!$(this).find(".statusSubmission").eq(0).hasClass("likeSlot")) {
				$(this).hide();
			}
		});
		window.location.hash = "#like";
		return false;
	});
	// show dislike submissions
	$("#dislikeSubmissionBtn").click(function() {
		$("#subFilterText").html("Submissions You Disliked");
		$("#submissionGrid").children().eq(0).children().show();
		$("#submissionGrid").children().eq(0).children().each(function() {
			if(!$(this).find(".statusSubmission").eq(0).hasClass("dislikeSlot")) {
				$(this).hide();
			}
		});
		window.location.hash = "#dislike";
		return false;
	});

    function initBanks(index, link) {
        var $item = link.attr("title");
        var $itemlabel = link.attr("rel");

        var f = arrPrize[index];
        var s = arrSlot[index];

        $('#submission-'+$itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass(s);
        if (link.hasClass('disabledControl')) {
            $("#" + f).html('<a href="' +
                            getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item +
                            '" alt="" /></a><label>' + $itemlabel + '</label>');
        } else {
            $("#" + f).html('<a href="#" id="remove' + f + '" class="btn_remove"></a><a href="' +
                            getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item +
                            '" alt="" /></a><label>' + $itemlabel + '</label>');
            $('#remove' + f).click(function() {
                $("#" + f).html('<a href="#" class="thumb"><span></span></a>');
                $('#submission-' + $itemlabel).children(".statusSubmission").removeClass().addClass("statusSubmission");
                getBankData();
                return false;
            });
        }
    }

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
	if (bankData) {
		// set prize bank data from cookie
		$("#bankSelectionItemList li:not(:last-child)").each(function(index) {
			var label = bankData[arrPrize[index]];
			if (label) {
				initBanks(index, $("#submission-" + label).find("." + arrSlot[index]));
			}
		});
		// set like submissions from cookie
		for (var i = 0; i < bankData.listLikes.length; i++) {
			var label = bankData.listLikes[i];
			$("#submission-" + label).find(".likeSubmission").click();
		}
		// set dislike submissions from cookie
		for (var i = 0; i < bankData.listDislikes.length; i++) {
			var label = bankData.listDislikes[i];
			$("#submission-" + label).find(".dislikeSubmission").click();
		}
		// set extra purchase from cookie
		for (var i = 0; i < bankData.listExtra.length; i++) {
			var label = bankData.listExtra[i];
			$("#submission-" + label).find(".extraSlot").click();
			if (hasCheckout) {
				$('#submission-' + label).children(".statusSubmission").removeClass().addClass("statusSubmission").addClass("dollarSlot");
			}
		}
		if (hasCheckout) {
			$("#numExtra").html("(" + bankData.listExtra.length + ")");
		}
	}
	// show like submissions
	if (window.location.hash == "#like") {
		$("#likeSubmissionBtn").click();
	}
	// show dislike submissions
	if (window.location.hash == "#dislike") {
		$("#dislikeSubmissionBtn").click();
	}


    if (!hasContestWritePermission) {
        $("#bankSelectionButton").hide();
        $(".btn_remove").hide();
    }
}); 
