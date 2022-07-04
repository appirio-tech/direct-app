// JavaScript Document for Get a Copilot
$(document).ready(function(){
	//z-index for step bar
	$('.stepBar li').each(function(){
		var totalStep = $('.stepBar li').length;
		var iNum = $('.stepBar li').index(this);
		$(this).css('z-index',totalStep-iNum);					   
	});
	
	//textarea
	$('.stepFirst textarea').css('width',$('.stepFirst .row').width()-272);
	
	//width for step bar 
	$('.stepBar li').css('width',($('.stepBar').width()/5)+16); 
	$('.stepBar li:first').css('width',($('.stepBar').width()/5)+14);
	$(window).resize(function(){
		$('.stepBar li').css('width',($('.stepBar').width()/5)+16);
		$('.stepBar li:first').css('width',($('.stepBar').width()/5)+14);
		$('.stepFirst textarea').css('width',$('.stepFirst .row').width()-272);
	});
	
	//modal
	addressPositionModal = function(){
		var wWidth  = window.innerWidth;
		var wHeight = window.innerHeight;

		if (wWidth==undefined) {
			wWidth  = document.documentElement.clientWidth;
			wHeight = document.documentElement.clientHeight;
		}

		var boxLeft = parseInt((wWidth / 2) - ( $("#new-modal").width() / 2 ));
		var boxTop  = parseInt((wHeight / 2) - ( $("#new-modal").height() / 2 ));

		// position modal
		$("#new-modal").css({
			'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
		});

		$("#modalBackground").css("opacity", "0.6");
		
		if ($("body").height() > $("#modalBackground").height()){
            $("#modalBackground").css("height", $("body").height() + "px");
		}
	}
	
	addressLoadModal = function(itemId) {
        $('#modalBackground').show();
		$(itemId).show();
		addressPositionModal();
    }
	
	addresscloseModal = function() {
        $('#modalBackground').hide();
        $('#errortModal,#detailModal,#gamePlanModal,#addUserModal,#deleteUserModal,#deleteConfirmModal,#alertModal,#copilotPopUp').hide();  
    }
	
	$(window).resize(function() {
		setModal();
	});
	
	function setModal(){
		addressPositionModal();
	}
	
	$('#deleteUserModal .yesbutton').click(function(){
		addresscloseModal();
		addressLoadModal('#deleteConfirmModal');
	});
	
	$(".closeModal,.closebutton").live('click', function() {
        addresscloseModal();
		return false;
    });
	
	
	
	$(".customUpload").css({filter:"alpha(opacity:0)",opacity:"0"});
	$(".customUpload").after('<input type="text" class="text uploadInput" readonly="readonly" /><a href="javascript:;" class="draft button6"><span class="left"><span class="right">BROWSE</span></span></a>');
	$(".customUpload").live("change",function(){
		$(this).siblings(".uploadInput").val($(this).val());
	});
	$(".get-a-copilot .customUploadWrap").hover( 
		function(){
			$(this).children(".draft").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right bottom");
		},
		function(){
			$(this).children(".draft").css("background-position","left top");
			$(this).children(".draft").children("span.left").css("background-position","left top");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right top");
		});
	
	
	/* add button */
	$('.uploadContent .addButton').live("click",function(){
		var inputLine = '<p><label>Select Document</label><span class="customUploadWrap"><input type="file" class="customUpload" /><input type="text" class="text uploadInput" readonly="readonly" /><a href="javascript:;" class="draft button6"><span class="left"><span class="right">BROWSE</span></span></a></span><a href="javascript:;" class="button6 uploadBtnRed"><span class="left"><span class="right">UPLOAD</span></span></a><a href="javascript:;" class="addButton"></a></p>'
		$(this).parent().after(inputLine);
		
		$(".customUpload").css({filter:"alpha(opacity:0)",opacity:"0"});	
		$(".get-a-copilot .customUploadWrap").hover( 
		function(){
			$(this).children(".draft").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right bottom");
		},
		function(){
			$(this).children(".draft").css("background-position","left top");
			$(this).children(".draft").children("span.left").css("background-position","left top");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right top");
		});
	});
	
	
	
	// validate
	$('.stepFirst .geryContent .nextStepButton').click(function(){
		if($('.stepFirst .text').val() == "" || $('.stepFirst textarea').val() == "" || $("#projectName").val() == "" ){
			addresscloseModal();
			addressLoadModal('#errortModal');
		}else{
			window.location.href = 'get-a-copilot-step-2.html';	
		}
	});
	
	Request = {
		QueryString : function(item){
			var svalue = location.search.match(new
			RegExp('[\?\&]' + item + '=([^\&]*)(\&?)','i'));
			return svalue ? svalue[1] : svalue;
		}
	}
	var key = Request.QueryString('id');
	
	$('.stepSecond .geryContent .nextStepButton').click(function(){
		var falgCheckbox = false;
		$('.stepSecond .checkbox').each(function(){
			if($(this).attr('checked')){
				falgCheckbox = true;
			}						  
		});
		if(!falgCheckbox){
			addresscloseModal();
			addressLoadModal('#errortModal');
		}else{
			{
				window.location.href = 'get-a-copilot-step-3.html';		
			}
		}
	});
	
	// step2 fluid
	
	function truncateType (){
		var width = $(window).width();
		
		if(width > 1100 ) {
			if ($(".type2").length < 1) {
				var middleUL = $('<ul class="type2"></ul>');
				$(".type1 li:eq(3)").appendTo(middleUL);
				$(".type1 li:eq(3)").appendTo(middleUL);
				$(".type3 li:eq(0)").appendTo(middleUL);
				$(".type1").after(middleUL);
			}
		}
		else if($(".type2").length > 0){
			$(".type2 li:eq(0)").appendTo(".type1");
			$(".type2 li:eq(0)").appendTo(".type1");
			$(".type2 li:eq(0)").prependTo(".type3");
			$(".type2").remove();
		}
	}
	
	
    $(window).resize(function(){
		truncateType();
    }) ;
	
	truncateType();
	
	
	
	$('.stepThird .geryContent .nextStepButton').click(function(){
		var falgRadio = false;
		$('.stepThird .radio').each(function(){
			if($(this).attr('checked')){
				falgRadio = true;
			}						  
		});
		if(!falgRadio){
			addresscloseModal();
			addressLoadModal('#errortModal');
		}else{
			{
				window.location.href = 'get-a-copilot-step-4.html';		
			}
		}
	});
	
	$(".radio:eq(0)").click(function() {
		$(this).siblings(".text").attr("disabled",false);
		
	})
	$(".radio:eq(1)").click(function() {
		$(".radio:eq(0)").siblings(".text").attr("disabled",true);
		
	})
	
	
	// step4 function
	$(".lineItem .red-button-big").click(function() {
		switch($(".lineItem .red-button-big").index(this)){
			case 0:
				$(".rowItem .red-button-big:eq(1)").addClass("disabled-button");
				$(".rowItem .red-button-big:eq(0)").removeClass("disabled-button");
				$(".postFrame").removeClass("hide");
				break;
			case 1:
				$(".rowItem .red-button-big:eq(1)").removeClass("disabled-button");
				$(".rowItem .red-button-big:eq(0)").addClass("disabled-button");
				$(".postFrame").addClass("hide");
				break;
			default:
			 	break;		
			
		}
		
	})
	
	
	$(".radio2:eq(0)").click(function() {
		$(".radio2:eq(1)").siblings(".text").attr("disabled",true);
		
	})
	$(".radio2:eq(1)").click(function() {
		$(this).siblings(".text").attr("disabled",false);
		
	})
	
	$('.stepForth .geryContent .nextStepButton').click(function(){
		var falgRadio = false;
		$('.stepForth .radio2').each(function(){
			if($(this).attr('checked')){
				falgRadio = true;
			}						  
		});
		if(!falgRadio){
			addresscloseModal();
			addressLoadModal('#errortModal');
		}else{
			{
				window.location.href = 'get-a-copilot-step-5.html';		
			}
		}
	});
	
	$('.stepFifth .geryContent .nextStepButton').click(function(){
		var falgRadio2 = false;
		$('.stepFifth #selectAccount option').each(function(){
			if($(this).attr('selected')){
				falgRadio2 = true;
			}						  
		});
		if(!falgRadio2){
			addresscloseModal();
			addressLoadModal('#errortModal');
		}else{
			{
				window.location.href = 'get-a-copilot-summary.html';		
			}
		}
	});
	
	// copilot
	$(".selectCopilot").live('click',function() {
		addressLoadModal('#copilotPopUp');
	});
	
	$("#copilotPopUp .submitBtn").live('click',function() {
		addresscloseModal();
		window.location.href="copilot-selected.html";
	});
	
	
	/*function  listType(){
        var width = $(window).width();
        if(width>1100){
			
		   $(".wideList").removeClass("hide");
		   $(".normalList").addClass("hide");
        } else{
			$(".normalList .projectItem").css("margin-right","50px"); 
		   $(".normalList").removeClass("hide");
		   $(".wideList").addClass("hide");
        } 
    }
	
	 $(window).resize(function(){ 
		listType();
    }) ;
	listType();*/
	
	function listType(num){
		var listWidth = $(".projectList").width();
		var itemWidth = 293;
		$(".projectItem").css("margin-right",(listWidth - itemWidth*num) / (num-1) + "px");
		$(".wideList .projectItem").each(function(i) {
			var number =$(this).index() + 1;
			if (number%num == 0) { 
				$(this).css("margin-right","0"); 
			}
		});
	}
	 if($(".projectList").width() > 1210){
		listType(4);
	}
	else{
		listType(3);
	}
	
	
	
	 $(window).resize(function(){
		 if($(".projectList").width() > 1210){
			listType(4);
		}
		else{
			listType(3);
		}
    });
	
	// get a copilot 
	$('.get-a-copilot .leftColumn').css('width',($('.get-a-copilot .mainContent').width()-330)-25);
	$(window).resize(function(){ 
		
		$('.get-a-copilot .leftColumn').css('width',($('.get-a-copilot .mainContent').width()-330)-25);
		
		
    }) ;
	
	function carouselSize() {
		var carouselWidth = $(".carousel-content").width();
		$(".carousel-content ul").css("left",( carouselWidth - 540 )/ 2 + "px");
	}
	
	carouselSize();
	
	 $(window).resize(function(){
		carouselSize();
    });
	
	// copilot list
	$(".copilotList table th").hover(
		function(){ 
			$(this).addClass("hover");
		},
		function(){
			$(this).removeClass("hover");
		}
	)
	
	$(".copilotList table tr:nth-child(even)").addClass("even");
	
	
	
	
	// video 
	$(".viewVideo").click(function() {
		$(".viewVideo").target = "_blank";
		window.location.href="https://www.topcoder.com/help/?page_id=15&subject=startproject&catIdx=5809";
	});
	
	
	
	
	$('.projectContainer').hover(
		function(){
			$('.projectContainer').removeClass('hover');
			$(this).addClass('hover');	
		},
		function() {
			$(this).removeClass('hover');
			
		}
	)
	
	
	
	
	 
	 /*------------------------------------------------ Carousel --*/
	var size = 5;
	var currentIndex = Math.round(size / 2);
	var edge = size - currentIndex;
	var data = {
		small: {
			width: "194px",
			height: "115px",
			marginTop: "-58px",
			left: "0",
			fontSize: "7px",
			infoWidth: "102px",
			photoWidth: "67px",
			infoMargin: "0",
			buttonWidth: "61px",
			zIndex: 1,
			padding_top: "9px",
			padding_bottom: "5px"
		},
		smallRight: {
			width: "194px",
			height: "115px",
			marginTop: "-58px",
			left: "343px",
			fontSize: "7px",
			infoWidth: "102px",
			photoWidth: "67px",
			infoMargin: "0",
			buttonWidth: "61px",
			zIndex: 1,
			padding_top: "9px",
			padding_bottom: "5px"
		},
		medium: {
			width: "240px",
			height: "142px",
			marginTop: "-72px",
			left: "57px",
			fontSize: "9px",
			infoWidth: "125px",
			photoWidth: "83px",
			infoMargin: "7px",
			buttonWidth: "77px",
			zIndex: 2,
			padding_top: "12px",
			padding_bottom: "9px"
		},
		mediumRight: {
			width: "240px",
			height: "142px",
			marginTop: "-72px",
			left: "242px",
			fontSize: "9px",
			infoWidth: "125px",
			photoWidth: "83px",
			infoMargin: "7px",
			buttonWidth: "77px",
			zIndex: 2,
			padding_top: "12px",
			padding_bottom: "9px"
		},
		current: {
			width: "283px",
			height: "163px",
			marginTop: "-85px",
			left: "126px",
			fontSize: "11px",
			infoWidth: "147px",
			photoWidth: "96px",
			infoMargin: "11px",
			buttonWidth: "90px",
			zIndex: 3,
			padding_top: "15px",
			padding_bottom: "12px"
		}
	}
	function styleItem (name, item){
		item.css("z-index", data[name]["zIndex"]);
		if(name == "small" || name == "smallRight"){
			item.css("left", data[name]["left"]);
			item.animate({ width: data[name]["width"], height: data[name]["height"], marginTop: data[name]["marginTop"]}, 300);
		}
		else{
			item.animate({ width: data[name]["width"], height: data[name]["height"], marginTop: data[name]["marginTop"], left: data[name]["left"] }, 300);
		}
		item.find(".photo").animate({ width: data[name]["photoWidth"] }, 300);
		item.find(".copilot-wrapper").css({ "padding-top": data[name]["padding_top"] });
		item.find(".copilot-wrapper").css({ "padding-bottom": data[name]["padding_bottom"] });
		item.find(".photo").find("img").animate({ width: data[name]["photoWidth"] }, 300);
		item.find(".photo").find(".middle").animate({ width: data[name]["buttonWidth"] }, 300);
		item.find(".info").animate({ fontSize: data[name]["fontSize"], width: data[name]["infoWidth"], marginLeft: data[name]["infoMargin"] }, 300);
	}
	function render() {
		$.each($(".carousel-content ul li"), function(index, value){
			var offset = Math.abs(currentIndex - (index + 1));
			$(".foot-bar strong").text($(".carousel-content ul li").length - offset);
			if(offset >= Math.round(size / 2)){
				$(value).css({ height: "115px", left: index < currentIndex ? - $(value).width() + "px" : "539px" }).hide();
			}
			else{
				$(value).show();
				if(offset == edge){
					styleItem(index < currentIndex ? "small" : "smallRight", $(value));
				}
				else if(offset == edge - 1){
					styleItem(index < currentIndex ? "medium" : "mediumRight", $(value));
				}
				else{
					styleItem("current", $(value));
				}
			}
		});
	}
	render();
	var lastAnimateTime = new Date().getTime();
	$(".foot-bar .prev").click(function(){
		var now = new Date().getTime();
		if(currentIndex > 1 && now - lastAnimateTime > 350){
			lastAnimateTime = new Date().getTime();
			currentIndex--;
			render();
		}
	});
	$(".foot-bar .next").click(function(){
		var now = new Date().getTime();
		if(currentIndex < $(".carousel-content ul li").length && now - lastAnimateTime > 350){
			lastAnimateTime = new Date().getTime();
			currentIndex++;
			render();
		}
	});
	
	 //fix browsers
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    // IE 6 
    if ($.browser.msie && $.browser.version == 6.0) {
    }

    // IE 7
    if ($.browser.msie && $.browser.version == 7.0) {
        $('.projectItem').css('zoom', '1');
		$('.copilot-wrapper').css('zoom', '1');
		
    }

    // IE 8 
    if ($.browser.msie && $.browser.version == 8.0) {
    }

    // FF 2 
    if (ua.match(/firefox\/([\d.]+)/) != null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0] < 3) {

    }

    // FF 3 
    if (ua.match(/firefox\/([\d.]+)/) != null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0] > 2) {
        $(".padding").css("padding-top", "0");
		$(".copilotInfo p").css("margin", "0");
    }


});

/* function to style the input file */
(function($) {
   $.fn.styleingInputFile = function(){
       this.each(function(){
           var fileInput = $(this);
           var parentWrap = fileInput.parents(".attachFile");
           var wrapOffset = parentWrap.offset();
           fileInput.attr("style","opacity: 0;-moz-opacity: 0;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);")
           parentWrap.mousemove(function(event){
               fileInput.blur();
               var relatedX = event.pageX - wrapOffset.left - fileInput.width() + 12;
               var relatedY = event.pageY - wrapOffset.top;
               fileInput.css("left",relatedX + "px");
               fileInput.css("top","0px");
           });
           fileInput.change(function(){
               $(this).blur();
               parentWrap.find(".fakeText input").val($(this).val());
           })
       })
   }
})(jQuery)