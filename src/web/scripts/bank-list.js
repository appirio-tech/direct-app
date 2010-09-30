var listLikes = new Array();
var listDislikes = new Array();
var listExtra = new Array();
var arrPrize = ["firstPrize","secondPrize","thirdPrize","fourthPrize","fifthPrize"];
var arrSlot = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots"];
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
	  
	//like bank
	$(".likeSubmission").click(function() { 
		var $item = $(this).attr("rel"); 
		removeIfExist($item);
		listLikes.push($item);
		$("#likeCount").html(listLikes.length);
		$("#submission-"+$item).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("likeSlots");
		getBankData();
		return false;
	}); 

	//remove from like bank
	$(".noLikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listLikes, $item) != -1) {
			$("#likeCount").html(listLikes.length);
			$("#submission-"+$item).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			getBankData();
		}
	});
	
	//dislike bank
	$(".dislikeSubmission").click(function() { 
		var $item = $(this).attr("rel");
		removeIfExist($item);
		listDislikes.push($item);
		$("#dislikeCount").html(listDislikes.length);
		$("#submission-"+$item).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dislikeSlots");
		getBankData();
		return false;
	}); 
	
	//remove from dislike bank
	$(".noDislikeSubmission").click(function() {
		var $item = $(this).attr("rel"); 
		if (arrayRemove(listDislikes, $item) != -1) {
			$("#dislikeCount").html(listDislikes.length);
			$("#submission-"+$item).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			getBankData();
		}
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

	// submission li init draggable
	$("a.thumbList:not(.prizedSubmission)").draggable({
		revert: true,
		cursor: 'move', 
		cursorAt: { top: 35, left: 35 },
		helper: function(event) {
				var $item = $(this).children("span.hidden").children("img.submissionIMG").attr("src");
				return $('<div class="excerpt"><img src="' + $item + '" /></div>');
			} 
	}); 
	
	// contest id
	contestId = $("#contestId").val();
	// round type
	roundType = $("#roundType").val();
	// get the url of a sumbission's single page
	function getSinglePage(submissionId) {
		return "studioSubmission?contestId=" + contestId + "&formData.submissionId=" + submissionId + "&formData.roundType=" + roundType;
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
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="'
                        + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item
                        + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$('#submission-'+$itemlabel).children("td").children("span.icoBankLocation").removeClass().addClass("icoBankLocation").addClass($slotName);
				getBankData();

				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children("td").children("span.icoBankLocation").removeClass().addClass("icoBankLocation");
					updateNullSlots();
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
    
    if (!hasCheckout) {
        $("#bankSelectionItemList").sortable({
            items: 'li:not(#extraPrize)',
            update:updateSortable
        });
        $("#bankSelectionItemList").disableSelection();
    }
	
	// update the icon status of submissions.
	function updateNullSlots() {
		$(".icoBankLocation").each(function() {
			var obj = $(this);
			var classes = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots","dollarSlots","likeSlots","dislikeSlots"];
			for (var i = 0; i < classes.length; i++) {
				if (obj.hasClass(classes[i])) {
					return;
				}
			}
			obj.removeClass().addClass("icoBankLocation").addClass("nullSlots");
		});
	}
	updateNullSlots();

	// #clearSlots click function to clear all slots
	$("#clearSlots").click(function() {  
        if (!hasCheckout) {
			$("#bankSelectionItemList li:not(:last-child)").html('<a href="#" class="thumb"><span></span></a>');
        }
			listExtra = new Array();
			listLikes = new Array();
			listDislikes = new Array();
			$("#numExtra").html("(0)");
			$("#likeCount").html("0");
			$("#dislikeCount").html("0");
        if (!hasCheckout) {
			$(".icoBankLocation").removeClass("firstSlots");
			$(".icoBankLocation").removeClass("secondSlots");
			$(".icoBankLocation").removeClass("thirdSlots");
			$(".icoBankLocation").removeClass("fourthSlots");
			$(".icoBankLocation").removeClass("fifthSlots");
        }
			$(".icoBankLocation").removeClass("dollarSlots");
			$(".icoBankLocation").removeClass("likeSlots");  
			$(".icoBankLocation").removeClass("dislikeSlots");  
			$(".icoBankLocation").addClass("nullSlots");
			getBankData();
			return false;
	}); 
	
	// #likeFolder init droppable
	$("#likeFolder a").droppable({ 
				drop: function(ev, ui) { 
					$(this).removeClass('drophover');
					var $count = $(this).children("span").text();  
					var $countAdd = parseInt($count) + 1; 
					//$(".statusSubmission").removeClass("likeSlot");
					var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").attr("title");
					removeIfExist($itemlabel);
					listLikes.push($itemlabel);
					$("#likeCount").html(listLikes.length);
					$("#submission-"+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("likeSlots");
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
					var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").attr("title");
					removeIfExist($itemlabel);
					listDislikes.push($itemlabel);
					$("#dislikeCount").html(listDislikes.length);
					$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dislikeSlots"); 
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
        $("#bankSelectionItemList").children().each(function(index) {
            $(this).droppable({
                accept: 'a.thumbList',
                hoverClass: 'drophover',
                drop: function(event, ui) {
                    var $item = ui.draggable.children("span.hidden").children("img.submissionIMG").attr("src");
                    var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").attr("title");
                    removeIfExist($itemlabel);
                    var $slotId = $(this).attr('id');
                    var $slotName = arrSlot[arrPrize.indexOf($slotId)];
                    $(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="' +
                                 getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item +
                                 '" alt="" /></a><label>' + $itemlabel + '</label>');
                    $(".statusSubmission").removeClass($slotName);
                    $('#submission-' +
                      $itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass($slotName);

                    $('#remove' + $slotId).click(function() {
                        $("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
                        $('#submission-' +
                          $itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
                        updateNullSlots();
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
	
	// #extraPrize init droppable
	$("#extraPrize").droppable({
		hoverClass: 'drophover', 
		accept: 'a.thumbList',	
		drop: function(event, ui) {
			var $item = ui.draggable.children("span.hidden").children("img.submissionIMG").attr("src");
			var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").attr("title");
			removeIfExist($itemlabel);
			listExtra.push($itemlabel);
			$("#numExtra").html("(" + listExtra.length + ")");
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dollarSlots");
			
			$('#removeExtraPrize').click(function() { 
				 $("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				 $('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("dollarSlots");
				 updateNullSlots();
				 getBankData();
				return false;
			});
			getBankData();
			
		}
	});   
	
	//Add to Bank number 1st
	$(".firstSlots:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			$("#firstPrize").html('<a href="#" id="removeFirstPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("firstSlots");
			$('#removeFirstPrize').click(function() { 
				$("#firstPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	
	//Add to Bank number 2nd
	$(".secondSlots:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			$("#secondPrize").html('<a href="#" id="removeSecondPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("secondSlots");
			$('#removeSecondPrize').click(function() { 
				$("#secondPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 3rd
	$(".thirdSlots:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			$("#thirdPrize").html('<a href="#" id="removeThirdPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("thirdSlots");
			$('#removeThirdPrize').click(function() { 
				$("#thirdPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 4th
	$(".fourthSlots:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			$("#fourthPrize").html('<a href="#" id="removeFourthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("fourthSlots");
			$('#removeFourthPrize').click(function() { 
				$("#fourthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
				getBankData();
				return false;
			});
			getBankData();
			return false;
	}); 
	
	//Add to Bank number 5th
	$(".fifthSlots:not(.disabledControl)").click(function() {
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$(".icoBankLocation").removeClass().addClass("icoBankLocation");
			updateNullSlots();
			$("#fifthPrize").html('<a href="#" id="removeFifthPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("fifthSlots");
			$('#removeFifthPrize').click(function() { 
				$("#fifthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
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
			//$("#extraPrize").html('<a href="#" id="removeExtraPrize" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$("#submission-"+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dollarSlots");
			$('#removeExtraPrize').click(function() { 
				$("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
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
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
				updateNullSlots();
				getBankData();
			}
	});

	// show all submissions
	$("#allSubmissionBtn").click(function() {
		$("#subFilterText").html("All Submissions");
		$("#submissionList").find("tr").show();
		return false;
	});

	// show like submissions
	$("#likeSubmissionBtn").click(function() {
		$("#subFilterText").html("Submissions You Liked");
		$("#submissionList").find("tr").show();
		$("#submissionList").find("tr").each(function() {
			var cld = $(this).find(".icoBankLocation");
			if(cld.size() > 0 && !cld.eq(0).hasClass("likeSlots")) {
				$(this).hide();
			}
		});
		return false;
	});

	// show dislike submissions
	$("#dislikeSubmissionBtn").click(function() {
		$("#subFilterText").html("Submissions You Disliked");
		$("#submissionList").find("tr").show();
		$("#submissionList").find("tr").each(function() {
			var cld = $(this).find(".icoBankLocation");
			if(cld.size() > 0 && !cld.eq(0).hasClass("dislikeSlots")) {
				$(this).hide();
			}
		});
		return false;
	});

    function initBanks(index, link) {
        var $item = link.attr("title");
        var $itemlabel = link.attr("rel");

        var f = arrPrize[index];
        var s = arrSlot[index];


//        $(".icoBankLocation").removeClass().addClass("icoBankLocation");
        $('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass(s);
        if (link.hasClass('disabledControl')) {
            $("#" + f).html('<a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
        } else {
            $("#" + f).html('<a href="#" id="remove' + f + '" class="btn_remove"></a><a href="' + getSinglePage($itemlabel) + '" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
            $('#remove' + f).click(function() {
                $("#" + f).html('<a href="#" class="thumb"><span></span></a>');
                $('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation");
                updateNullSlots();
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
//				$("#submission-" + label).find("." + arrSlot[index]).click();
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
		}
	}
	 
	 
}); 