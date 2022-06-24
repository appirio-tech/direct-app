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
		return false;
	}); 
	
	//dislike bank
	$(".dislikeSubmission").click(function() { 
		var $item = $(this).attr("rel");
		removeIfExist($item);
		listDislikes.push($item);
		$("#dislikeCount").html(listDislikes.length);
		$("#submission-"+$item).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dislikeSlots");
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
 
	// submission li init draggable
	$("a.thumbList").draggable({
		revert: true, 
		cursor: 'move', 
		cursorAt: { top: 35, left: 35 },
		helper: function(event) {
				var $item = $(this).children("span.hidden").children("img.submissionIMG").attr("src");
				return $('<div class="excerpt"><img src="' + $item + '" /></div>');
			} 
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
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="studio-final-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$('#submission-'+$itemlabel).children("td").children("span.icoBankLocation").removeClass().addClass("icoBankLocation").addClass($slotName);
				
				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children("td").children("span.icoBankLocation").removeClass($slotName);
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
	
	// #clearSlots click function to clear all slots
	$("#clearSlots").click(function() {  
			$("#bankSelectionItemList li:not(:last-child)").html('<a href="#" class="thumb"><span></span></a>');
			listExtra = new Array();
			$("#numExtra").html("(0)");
			$("#likeCount").html("0");
			$("#dislikeCount").html("0");
			$(".icoBankLocation").removeClass("firstSlots");
			$(".icoBankLocation").removeClass("secondSlots");
			$(".icoBankLocation").removeClass("thirdSlots");
			$(".icoBankLocation").removeClass("fourthSlots");
			$(".icoBankLocation").removeClass("fifthSlots");
			$(".icoBankLocation").removeClass("dollarSlots");  
			return false;
	}); 
	
	// #likeFolder init droppable
	$("#likeFolder a").droppable({ 
				drop: function(ev, ui) { 
					$(this).removeClass('drophover');
					var $count = $(this).children("span").text();  
					var $countAdd = parseInt($count) + 1; 
					//$(".statusSubmission").removeClass("likeSlot");
					var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").text();
					removeIfExist($itemlabel);
					listLikes.push($itemlabel);
					$("#likeCount").html(listLikes.length);
					$("#submission-"+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("likeSlots");
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
					var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").text();
					removeIfExist($itemlabel);
					listDislikes.push($itemlabel);
					$("#dislikeCount").html(listDislikes.length);
					$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dislikeSlots"); 
				},
				over: function(){
					$(this).addClass('drophover');
				},
				out : function(){
					$(this).removeClass('drophover');
				},	
				tolerance:"pointer"
	});
	
	// #firstPrize init droppable
	//$("#firstPrize").droppable({
	//$($("#bankSelectionItemList").children()[0]).droppable({
	$("#bankSelectionItemList").children().each(function(index){
		$(this).droppable({
			accept: 'a.thumbList',							   
			hoverClass: 'drophover', 
			drop: function(event, ui) {
				var $item = ui.draggable.children("span.hidden").children("img.submissionIMG").attr("src");
				var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").text();
				removeIfExist($itemlabel);
				var $slotId = $(this).attr('id');
				var $slotName = arrSlot[arrPrize.indexOf($slotId)];
				$(this).html('<a href="#" id="remove' + $slotId + '" class="btn_remove"></a><a href="studio-milestone-single.html" class="thumb"><span></span><img src="' + $item + '" alt="" /></a><label>' + $itemlabel + '</label>');
				$(".statusSubmission").removeClass($slotName);
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass($slotName);
				
				$('#remove' + $slotId).click(function() { 
					$("#" + $slotId).html('<a href="#" class="thumb"><span></span></a>');
					$('#submission-'+$itemlabel).children(".statusSubmission").removeClass($slotName);
					return false;
				});
			}
		}).sortable({
				items: 'li:not(#extraPrize)'
			});
	});
	
	// #extraPrize init droppable
	$("#extraPrize").droppable({
		hoverClass: 'drophover', 
		accept: 'a.thumbList',	
		drop: function(event, ui) {
			var $item = ui.draggable.children("span.hidden").children("img.submissionIMG").attr("src");
			var $itemlabel = ui.draggable.parent("td").parent("tr").children("td.submissionID").text();
			removeIfExist($itemlabel);
			listExtra.push($itemlabel);
			$("#numExtra").html("(" + listExtra.length + ")");
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("dollarSlots");
			
			$('#removeExtraPrize').click(function() { 
				 $("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				 $('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("dollarSlots");
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
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("firstSlots");
			$('#removeFirstPrize').click(function() { 
				$("#firstPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("firstSlots");
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
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("secondSlots");
			$('#removeSecondPrize').click(function() { 
				$("#secondPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("secondSlots");
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
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("thirdSlots");
			$('#removeThirdPrize').click(function() { 
				$("#thirdPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("thirdSlots");
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
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("fourthSlots");
			$('#removeFourthPrize').click(function() { 
				$("#fourthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("fourthSlots");
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
			$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass().addClass("icoBankLocation").addClass("fifthSlots");
			$('#removeFifthPrize').click(function() { 
				$("#fifthPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("fifthSlots");
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
			$("#submission-"+$itemlabel).children("td").children(".icoBankLocation").addClass("dollarSlots");
			$('#removeExtraPrize').click(function() { 
				$("#extraPrize").html('<a href="#" class="thumb"><span></span></a>');
				$('#submission-'+$itemlabel).children("td").children(".icoBankLocation").removeClass("dollarSlots");
				return false;
			});
			
			return false;
	}); 
	
	
	 
	 
}); 