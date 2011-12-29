$(function(){

	//judge browser
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();	
	 
		$("#selectPaymentWrapper select").msDropDown(); 
 
	//safari
	if(ua.match(/version\/([\d.]+).*safari/)!=null &&
	ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){		
		$("#selectPaymentWrapper  .dd").css('width','286px');
		 
	}
	if($.browser.msie && $.browser.version == 6.0){	 
		$("#contest-sort label").css({'zoom':'1' });  
		$("#selectPaymentWrapper select").css({'width':'276px' });   
	}
	
	if($.browser.mozilla){ 
		$("#selectPaymentWrapper .dd").css({'width':'286px'});
		 
	}	
	 
});

$(document).ready(function(){ 		 	   
	
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

	$("#checkAll").click(function() {   
			$(".selectThumb").attr('checked' , true); 
			return false;			  
		}); 

	$("#checkNone").click(function() {   
			$(".selectThumb").attr('checked' , false); 
			return false;			  
		});

    /* js function for final fixed confirmation */
    $(".finalFixConfirm .noBtn").click(function(){
         $(".finalFixConfirm").hide();
         return false;
    })
    if($.fn.tooltip){
         $(".finalFixConfirm p a").tooltip({
            tip:"#finalFixesTip",
            delay:0,
            position:"center right",
            offset:[10,12]
        })
    }

    /* Carousel for final fix */
    // the Carouse visible number is changed by the widhow size 
    function getCarouselVisibleNum(){
        var num = 4;
        var width = $(".finalFixesCarousel").width(); 
        if(width < 621){
            num = 3;
        }else if( 620 < width && width < 781) {
           num = 4
        }else if( 780 < width && width < 1001) {
           num = 5
        }else if( 1000 <width && width < 1301){
           num = 6; 
        }else if( 1300 <width){
           num = 7;
        } 
        return num;

    }

    // initial carousel

    $(".finalFixesCarousel ul").jcarousel({
        scroll: 1,
        visible:getCarouselVisibleNum(),
        initCallback: function(carousel){ 

            $('a.carouselNext').bind('click', function() {
                carousel.next();
                return false;
            });

            $('a.carouselPrev').bind('click', function() {
                carousel.prev();
                return false;
            });

            $(window).resize(function(){
                if(carousel.options.visible == getCarouselVisibleNum()){
                    return false;
                }else{
                    //document.location.reload();
                    carousel.options.visible = $.jcarousel.intval(getCarouselVisibleNum());                    
                }
            });

        },
        itemLastInCallback : function(carousel, item, idx, state){
            if(idx == carousel.options.size){
                $('a.carouselNext').unbind("click");    
            }
        },
        itemLastOutCallback : function(carousel, item, idx, state){
            if(idx == carousel.options.size){
                $('a.carouselNext').bind('click', function() {
                    carousel.next();
                    return false;
                });
            } 
        },
        // This tells jCarousel NOT to autobuild prev/next buttons
        buttonNextHTML: null,
        buttonPrevHTML: null
        });
 
    // add more taskes
    $(".addTasks .addLink a").click(function(){
        // remember the number of the task
        var num =  $(this).data("number");
        if(!num){
            $(this).data("number",1);
            num = 1;
        }
         $(this).data("number",num+1);
        var clone = $(".addTasks .cloneit");
        var task = clone.clone();
        task.find("input").attr("name", "requestType_" + (num+1));
        task.removeClass("cloneit");
        clone.before(task);
    });

    // add more taskes
    $(".addFiles .addLink a").click(function(){
        // remember the number of the attachment
        var num =  $(this).data("number");
        if(!num){
            $(this).data("number",1);
            num = 1;
        }
         $(this).data("number",num+1);
        var clone = $(".addFiles .cloneit");
        var task = clone.clone();
        task.find(".fileInput").styleingInputFile();
        task.removeClass("cloneit");
        clone.before(task);
    });

    // styling file input
    $(".addFiles .fileInput").styleingInputFile();

    // fix tabs UI issue on safari and IE7
    function initTabs3(){
        $(".finalFixesMask").each(function(){
            var ulWidth = $(".finalFixesMask  #tabs3 ul").width();
            var leftWidth = 0;
            var lis = $(".finalFixesMask  #tabs3 ul li")
            $(lis).each(function(index){
                if(index < lis.length -1){
                    leftWidth += $(this).width();
                }
            })
            $(".finalFixesMask  #tabs3 ul li.lastItem").css("width",ulWidth - leftWidth); 
        })
    }
    if( ($.browser.msie && $.browser.version < 8.0) || $.browser.safari ){
        initTabs3();
        $(window).resize(initTabs3);
    } 

    // accept pop up
    $("#acceptBtnMock").overlay({
            closeOnClick:false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top:"20%",
            close :"#acceptPopupClose",
            fixed : true,
            target : $("#acceptPopup")
     });
    var acceptApi =  $("#acceptBtnMock").data("overlay");
    $("#acceptBtn").click(function(event){
        // If at least one request with type Required was marked as NOT fixed
        if($("input[name='desc1']:checked").val() == "0" || $("input[name='desc2']:checked").val() == "0" ||  $("input[name='desc3']:checked").val() == "0" ){
           acceptApi.load();
           return false; 
        }
    });

    // reject pop up
    $("#rejectBtnMock").overlay({
        closeOnClick:false,
        mask: {
            color: '#000000',
            loadSpeed: 200,
            opacity: 0.6
        },
        top:"20%",
        close :"#rejectPopupClose",
        fixed : true,
        target : $("#rejectPopup")
    });
    var rejectApi =  $("#rejectBtnMock").data("overlay");
    $("#rejectBtn").click(function(event){
        // if all requests with type Required were marked as fixed
        if($("input[name='desc1']:checked").val() == "1" && $("input[name='desc2']:checked").val() == "1" &&  $("input[name='desc3']:checked").val() == "1" ){
           rejectApi.load();
            return false;
        }
    }); 
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