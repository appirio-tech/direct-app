/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
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
 * Changes in version 1.3 (	Release Assembly - TC Direct Cockpit Release Three version 1.0):
 * - Add the codes to sort the copilot jcarousel by the total contest descending
 *
 * @author tangzx, duxiaoyang, TCSASSEMBLER
 * @version 1.3
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
        
        resetCarousel();
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
		window.location.href="https://www.topcoder.com/help/template-video/?subject=startproject&catIdx=18";
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
    
    /* grayscale images */
    function grayscaleImageIE(imgObj) {
        imgObj.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(grayScale=1)';
    }

    function grayscaleImage(imgObj) {
        var canvas = document.createElement('canvas');
        var canvasContext = canvas.getContext('2d');

        var imgW = 60;//imgObj.width;
        var imgH = 72;//imgObj.height;
        canvas.width = imgW;
        canvas.height = imgH;
        
        canvasContext.drawImage(imgObj, 0, 0, imgW, imgH);
        var imgPixels = canvasContext.getImageData(0, 0, imgW, imgH);

        for ( var y = 0; y < imgPixels.height; y++) {
            for ( var x = 0; x < imgPixels.width; x++) {
                var i = (y * 4) * imgPixels.width + x * 4;
                var avg = (imgPixels.data[i] + imgPixels.data[i + 1] + imgPixels.data[i + 2]) / 3;
                imgPixels.data[i] = avg;
                imgPixels.data[i + 1] = avg;
                imgPixels.data[i + 2] = avg;
            }
        }

        canvasContext.putImageData(imgPixels, 0, 0, 0, 0, imgPixels.width, imgPixels.height);
        return canvas.toDataURL();
    }

    function grayscaleImg(object){
        if (!object.parent().parent().hasClass('current')) {
            if ($.browser.msie) {
                 grayscaleImageIE(object[0]);
            } else {
                object.attr('src', grayscaleImage(object[0]));
            }
        }
    }
    function resetNavThumb(){
        $('.navThumb ul img').each(function(i) {
            if (!$(this).parent().parent().hasClass('current')) {
                grayscaleImg($(this));
            }
        });
    }
    
    $.easing.easeinout = function(x, t, b, c, d) {
        if (t < d/2) return 2*c*t*t/(d*d) + b;
        var ts = t - d/2;
        return -2*c*ts*ts/(d*d) + 2*c*ts/d + c/2 + b;		
    };
    
    function carouselNavInit(){
        $(".ad-thumb-list").carouFredSel({
            prev: '.carouselNav .navBack',
            next: '.carouselNav .navForward',		
            scroll: {
                items: 1,
                easing: 'easeinout',
                duration: 400
            },
            auto: false
        }).find("li").click(function() {
            var deviation = $('.copilot-carousel').width()>950?-5:-3;
            $(".ad-thumb-list").trigger("slideTo", [$(this), deviation]);
        });	
    }

    function resetCarousel(){
        if($('.copilot-carousel').width()>950){
            $('.copilot-carousel').addClass('wider');
            $('.navThumb .caroufredsel_wrapper').css('width','880px');
            
        }else{
            $('.copilot-carousel').removeClass('wider');
            $('.navThumb .caroufredsel_wrapper').css('width','616px');
        }
        carouselNavInit();
    }

    // carousel thumb width
    function carouselInit() {
        resetCarousel();
        $('.navThumb .ad-thumb-list li a').each(function(i){
            $(this).attr('id','thumbNo-'+i);
        })
        var totalItems= $('.navThumb .ad-thumb-list').find('li').length;
        $('.foot-bar .currentThumb').html('<strong>1</strong> / '+ totalItems);						
    }
    
	if ($.browser.msie)
	{
		resetNavThumb();
	}
	else
	{
		$('.ad-thumb-list').addClass('loading');
		$(window).load(function(){
			resetNavThumb();			
			$('.ad-thumb-list').removeClass('loading');
		});
	}
	
    window.setTimeout(function(){
        carouselInit();
    } ,500);

    /* carousel nav thumb click */
    $('.navThumb a').live('click',
            function() {
                var idx = $(this).attr('id').split('-')[1];

                var a = $(this).parents('ul').find('.current a');

                var currentIdx = $(this).parents('ul').find('.current a').attr('id').split('-')[1];

                var totalItems= $('.foot-bar .currentThumb').text().split('/')[1];
                totalItems=parseInt(totalItems,10);
                $('#copilot-' + currentIdx).fadeTo('fast', 0, function() {
                    $('#copilot-' + currentIdx).hide();
                    $('#copilot-' + idx).show().css('opacity', '0').fadeTo('slow', '1');
                });
                $(this).parents('ul').find('.current').removeClass('current');
                $(this).parent().addClass('current');
                resetNavThumb();
                
                if ($.browser.msie) {
                    $(this).children('img:first').attr('style','filter: none');
                } else {
                    $(this).children('img:first').attr('src',$('#copilot-' + idx + ' img').attr('src'));
                }	
                
                $('.foot-bar .currentThumb').html('<strong>'+ (parseInt(idx,10)+1) +'</strong> / '+totalItems);
            });
});

var sortCopilot = function (x, y) {
    var Xindex = $(x).find("a").attr('id').split('-')[1];
    var yindex = $(y).find("a").attr('id').split('-')[1];

    var copilotX = $('#copilot-' + Xindex);
    var copilotY = $('#copilot-' + yindex);

    var totalX = copilotX.find(".colLeft .row:eq(2) span").text();
    var totalY = copilotY.find(".colLeft .row:eq(2) span").text();

    return totalX > totalY ? -1 : 1;
};

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

    window.setTimeout(function () {
           var temp = $(".ad-thumb-list li").sort(sortCopilot);
           $(".ad-thumb-list").empty();
        $(".ad-thumb-list").html(temp);

        var map = {};

        $(".ad-thumb-list li").each(function (index) {
            var Xindex = $(this).find("a").attr('id').split('-')[1];
            map[Xindex] = index;
            $(this).find("a").attr('id', 'thumbNo-' + index);
        })

        $(".copilot-details").each(function () {
            var currentIndex = $(this).attr('id').split('-')[1];
            var newIndex = map[currentIndex];
            $(this).attr('id', 'copilot-' + newIndex);
        });

        $(".ad-thumb-list li:eq(0) a").trigger('click');
    }, 500);

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
