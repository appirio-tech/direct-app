/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file is used to render get a copilot page.
 * 
 * Version 1.0 TC Cockpit Post a Copilot Assembly 1 change note
 * - Apply to new prototype.
 *
 * Changes in version 1.1 (TC Cockpit Post a Copilot Assembly 2):
 * - Refined the function for step 3 fluid checkboxes.
 *
 * Changes in version 1.2 (Release Assembly - TC Direct Select From Copilot Pool Assembly):
 * - Move some codes out for handling select from copilot pool page request.
 * - Update the logic of Carousel.
 *
 * @author tangzx, duxiaoyang
 * @version 1.2
 */
$(document).ready(function(){
	//z-index for step bar
	$('.stepBar li').each(function(){
		var totalStep = $('.stepBar li').length;
		var iNum = $('.stepBar li').index(this);
		$(this).css('z-index',totalStep-iNum);					   
	});
	$('#budget').click(function(){
		$('#haveBudget').attr("checked", true);
	});
	$('#amountText1').click(function(){
		$('#useAmount1').attr("checked", true);
	});
	$('#amountText2').click(function(){
		$('#useAmount2').attr("checked", true);
	});
	//textarea
	$('.stepFirst textarea').css('min-width',$('.stepFirst .row').width()-272);
	
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
	
	Request = {
		QueryString : function(item){
			var svalue = location.search.match(new
			RegExp('[\?\&]' + item + '=([^\&]*)(\&?)','i'));
			return svalue ? svalue[1] : svalue;
		}
	}
	var key = Request.QueryString('id');

	
	// step2 fluid	
	function truncateType (){
		var width = $(window).width();
		
		if(width > 1100 ) {
			if ($(".type2").length < 1) {
				var middleUL = $('<ul class="type2"></ul>');
				var type1 = $('.type1 li').length;
				var type3 = $('.type3 li').length;
				
				for (var i = (type1 + type3) / 3 + 0.5; i < type1; i = i + 1) {
					$(".type1 li:eq(" + parseInt((type1 + type3) / 3 + 0.5) + ")").appendTo(middleUL);
				}
				
				for (var i = (type1 + type3) / 3; i < type3; i = i + 1) {
					$(".type3 li:eq(0)").appendTo(middleUL);
				}
				$(".type1").after(middleUL);
			}
		}
		else if($(".type2").length > 0) {
			var type2 = $('.type2 li').length;
			for (var i = 0; i < type2 / 2 + 0.5; i = i + 1) {
				$(".type2 li:eq(0)").appendTo(".type1");
			}
			while ($('.type2 li').length > 0) {
				$(".type2 li:eq(0)").prependTo(".type3");
			}
			$(".type2").remove();
		}
	}
	
    $(window).resize(function(){
		truncateType();
    }) ;
	
	truncateType();

	$(".radio:eq(0)").click(function() {
		$(this).siblings(".text").attr("disabled",false);
		
	})
	$(".radio:eq(1)").click(function() {
		$(".radio:eq(0)").siblings(".text").attr("disabled",false);
		
	})
	
    var hasIntiateStartDate = false;
	// step4 function
	$(".lineItem .red-button-big").click(function() {
		switch($(".lineItem .red-button-big").index(this)){
			case 0:
				$(".rowItem .red-button-big:eq(1)").addClass("disabled-button");
				$(".rowItem .red-button-big:eq(0)").removeClass("disabled-button");
				$(".lineItem.amountItem .postFrame").removeClass("hide");
                $(".lineItem.dateItem .postFrame").addClass("hide");
				break;
			case 1:
				$(".rowItem .red-button-big:eq(1)").removeClass("disabled-button");
				$(".rowItem .red-button-big:eq(0)").addClass("disabled-button");
                $(".lineItem.amountItem .postFrame").addClass("hide");
                $(".lineItem.dateItem .postFrame").removeClass("hide");
                
                if (!hasIntiateStartDate) {
                    $('.startEtSelect select').focus();
                    
                    var SelectOptions = {
                        ddMaxHeight: '220px',
                        yscroll: true
                    };
                    $('.startEtSelect select').sSelect(SelectOptions);
                    hasIntiateStartDate = true;
                }
                
				break;
			default:
			 	break;		
		}
        
        $(window).resize();
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
	
	// video 
	$(".viewVideo").click(function() {
		$(".viewVideo").target = "_blank";
		window.location.href="http://www.topcoder.com/help/template-video/?subject=startproject&catIdx=18";
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
    
    $('#copilotProfileCarouselDiv .info .handle a').attr("target", "_blank");
	
	 /*------------------------------------------------ Carousel ---------------------------------------------*/
	var size = 5;
	var currentIndex = Math.min(Math.round(size / 2), Math.round($(".carousel-content ul li").length / 2));
    var edge;
    
    if ($(".carousel-content ul li").length >= 5) {
        edge = 2;
    } else {
        edge = Math.floor(($(".carousel-content ul li").length - 1) / 2);
        size = edge * 2 + 1;
    }
    
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
			if(offset > edge){
				$(value).css({ height: "115px", left: index < currentIndex ? - $(value).width() + "px" : "539px" }).hide();
			}
			else{
				$(value).show();                
                if (offset == 1) {
                    styleItem(index < currentIndex ? "medium" : "mediumRight", $(value));
                } else if (offset == 2) {
                	styleItem(index < currentIndex ? "small" : "smallRight", $(value));
                } else {
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
		if(currentIndex == 1 && now - lastAnimateTime > 350){
			lastAnimateTime = new Date().getTime();
			currentIndex = $(".carousel-content ul li").length;
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
		if(currentIndex == $(".carousel-content ul li").length && now - lastAnimateTime > 350){
			lastAnimateTime = new Date().getTime();
			currentIndex = 1;
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

    //modalPreloader();
    $.ajax({
        type: 'POST',
        url:  "copilotStatistics",
        cache: true,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    //modalClose();
                    handleCopilotStatisticsResult(result);
                },
                function(errorMessage) {
                    //modalClose();
                    showServerError(errorMessage);
                });
        }
    });    
});

function handleCopilotStatisticsResult(result) {    
    //alert(result.length);
    $.each(result, function(index, item) {
        $(".field_" + index + "_totalProjects:not(input)").html(item.member.totalProjects);
        $(".field_" + index + "_totalContests:not(input)").html(item.member.totalContests);
        $(".field_" + index + "_totalRepostedContests:not(input)").html(item.member.totalRepostedContests);
        $(".field_" + index + "_totalFailedContests:not(input)").html(item.member.totalFailedContests);
        $(".field_" + index + "_totalBugRaces:not(input)").html(item.member.totalBugRaces);
        $(".field_" + index + "_currentProjects:not(input)").html(item.member.currentProjects);
        $(".field_" + index + "_currentContests:not(input)").html(item.member.currentContests);
        
        $(".field_" + index + "_fullfillment:not(input)").html(item.fullfillment);

        // set hidden input value
        $("input.field_" + index + "_totalProjects").val(item.member.totalProjects);
        $("input.field_" + index + "_totalContests").val(item.member.totalContests);
        $("input.field_" + index + "_totalRepostedContests").val(item.member.totalRepostedContests);
        $("input.field_" + index + "_totalFailedContests").val(item.member.totalFailedContests);
        $("input.field_" + index + "_totalBugRaces").val(item.member.totalBugRaces);
        $("input.field_" + index + "_currentProjects").val(item.member.currentProjects);
        $("input.field_" + index + "_currentContests").val(item.member.currentContests);
                
    });
}

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