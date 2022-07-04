var listLikes = new Array();
var listDislikes = new Array();
var listExtra = new Array();
var arrPrize = ["firstPrize","secondPrize","thirdPrize","fourthPrize","fifthPrize"];
var arrSlot = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots"];

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
	
	// #firstPrize init droppable
	//$("#firstPrize").droppable({
	//$($("#bankSelectionItemList").children()[0]).droppable({
	$("#bankSelectionItemList").children().each(function(index){
		$(this).droppable({
			accept: '.carouselSingleItem',							   
			hoverClass: 'drophover', 
			drop: function(event, ui) {
				var $item = ui.draggable.children(".hidden").children("img.submissionIMG").attr("src");
				var $itemlabel = ui.draggable.children(".hidden").children("label").children(".submissionID").text();
				removeIfExist($itemlabel);
				var $slotId = $(this).attr('id');
				var $slotName = arrSlot[arrPrize.indexOf($slotId)];
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass($slotName);
				
				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass($slotName);
					return false;
				});
			}
		}).sortable({
				items: 'li:not(#extraPrize)'
			});
	});
	
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
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass($slotName);
				
				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass($slotName);
					return false;
				});
			}
		});
	}
	
	$("#bankSelectionItemList").sortable({
			items: 'li:not(#extraPrize)',
			update:updateSortable
		});
	$("#bankSelectionItemList").disableSelection(); 

	/*
	$(".column").sortable({
			connectWith: '.column'
		});
		*/
		
	// submission li init draggable
	$(".carouselSingleItem").draggable({
		revert: true, 
		cursor: 'move', 
		cursorAt: { top: 35, left: 35 },
		helper: function(event) {
				var $item = $(this).children(".hidden").children("img.submissionIMG").attr("src");
				return $('<div class="excerpt"><img src="' + $item + '" /></div>'); 
			} 
	}); 
	
	
	
	// #clearSlots click function to clear all slots
	$("#clearSlots").click(function() {  
			$("#bankSelectionItemList li:not(:last-child)").html('<a href="#" class="thumb"><span></span></a>');
			listExtra = new Array();
			$("#numExtra").html("(0)"); 
			$(".icoSingleSubmissionBankLocation").removeClass("firstSlots");
			$(".icoSingleSubmissionBankLocation").removeClass("secondSlots");
			$(".icoSingleSubmissionBankLocation").removeClass("thirdSlots");
			$(".icoSingleSubmissionBankLocation").removeClass("fourthSlots");
			$(".icoSingleSubmissionBankLocation").removeClass("fifthSlots");
			return false;
	});   
	
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
					$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("likeSlots"); 
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
					$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dislikeSlots"); 
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
			//$(this).html('<a href="#" id="removeExtraPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$(".icoSingleSubmissionBankLocation").removeClass("dollarSlots");
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dollarSlots");
			
			$('#removeExtraPrize').click(function() { 
				 $("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				 $('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("dollarSlots");
				return false;
			});
			
			
		}
	});  
	
	//Add to Bank number 1st
	$(".firstSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#firstPrize").html('<a href="#" id="removeFirstPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("firstSlots");
			$('#removeFirstPrize').click(function() { 
				$("#firstPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("firstSlots");
				return false;
			});
			return false;
	}); 
	
	
	//Add to Bank number 2nd
	$(".secondSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#secondPrize").html('<a href="#" id="removeSecondPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("secondSlots");
			$('#removeSecondPrize').click(function() { 
				$("#secondPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("secondSlots");
				return false;
			});
			return false;
	}); 
	
	//Add to Bank number 3rd
	$(".thirdSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#thirdPrize").html('<a href="#" id="removeThirdPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("thirdSlots");
			$('#removeThirdPrize').click(function() { 
				$("#thirdPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("thirdSlots");
				return false;
			});
			return false;
	}); 
	
	//Add to Bank number 4th
	$(".fourthSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#fourthPrize").html('<a href="#" id="removeFourthPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("fourthSlots");
			$('#removeFourthPrize').click(function() { 
				$("#fourthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("fourthSlots");
				return false;
			});
			return false;
	}); 
	
	//Add to Bank number 5th
	$(".fifthSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel"); 
			removeIfExist($itemlabel);
			$("#fifthPrize").html('<a href="#" id="removeFifthPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("fifthSlots");
			$('#removeFifthPrize').click(function() { 
				$("#fifthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("fifthSlots");
				return false;
			});
			return false;
	}); 
	
	//Add to Extra Purchase
	$(".extraSlot").click(function() { 					
			var $item = $(this).attr("title"); 
			var $itemlabel = $(this).attr("rel");
			removeIfExist($itemlabel); 
			listExtra.push($itemlabel);
			$("#numExtra").html("(" + listExtra.length + ")");
			//$("#extraPrize").html('<a href="#" id="removeExtraPrize" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
			$("#submission-"+$itemlabel).children(".statusSubmission").addClass("dollarSlot");
			$("#submission-"+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").addClass("dollarSlots");
			$('#removeExtraPrize').click(function() { 
				$("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass("dollarSlots");
				return false;
			});
			
			return false;
	}); 
	
	 
	
	//like bank
	$(".likeSubmission").click(function() { 
		var $item = $(this).attr("rel"); 
		removeIfExist($item);
		listLikes.push($item);
		$("#likeCount").html(listLikes.length);
		$("#submission-"+$item).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("likeSlots");
		return false;
	}); 
	
	//dislike bank
	$(".dislikeSubmission").click(function() { 
		var $item = $(this).attr("rel");
		removeIfExist($item);
		listDislikes.push($item);
		$("#dislikeCount").html(listDislikes.length);
		$("#submission-"+$item).children(".submissionAction").children(".icoSingleSubmissionBankLocation").removeClass().addClass("icoSingleSubmissionBankLocation").addClass("dislikeSlots");
		return false;
	});  
	 
}); 