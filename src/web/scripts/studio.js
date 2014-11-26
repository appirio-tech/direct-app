$(function(){

	//judge browser
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();	

    if($("#selectPaymentWrapper select").length > 0) {
        $("#selectPaymentWrapper select").msDropDown();
    }

 
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

$(document).ready(function () {
	
    $("a.actButtonlike, a.actButtondislike, a.actButtonzoom, a.actButtonzoom, a.actButtondollar, a.actButtonstar").hover(function () {
			var $this = $(this);	
			var $next = $(this).next(".dialog-mini-wrapper");								   
			$next.css("display", "block");
			$(this).children("span").addClass("hover");
        $next.hover(function () {
				$next.css("display", "block");
				$this.children("span").addClass("hover");
        }, function () {
				$next.css("display", "none");
				$this.children("span").removeClass("hover");
			}); 
    }, function () {
			var $next = $(this).next(".dialog-mini-wrapper");			
			$next.css("display", "none"); 
			$(this).children("span").removeClass("hover");
    });
 
 
    $("a.thumbGrid").hover(function () {
			var $next = $(this).next(".submissionAction");								   
			$next.css("display", "block");
        $next.hover(function () {
				$next.css("display", "block");
        }, function () {
				$next.css("display", "none");
			}); 
    }, function () {
			var $next = $(this).next(".submissionAction");			
			$next.css("display", "none"); 
		});  

    $("#checkAll").click(function () {
        $(".selectThumb").attr('checked', true);
			return false;			  
		}); 

    $("#checkNone").click(function () {
        $(".selectThumb").attr('checked', false);
			return false;			  
		}); 
  
    $(".checkpointConfirmation, .winnersLockIn").click(function () {
        if ($("ul#bankSelectionItemList li label").length <= 0) {
            showErrors("Please select at least one submission.");
            return false;
        }
        return true;
    })

    $("a.checkpointConfirmation, .winnersLockIn").one('click', function (e) {
        var originHref = $(this).attr('href');
        this.href = originHref + "&struts.token.name=" + getStruts2TokenName()
        + "&" + getStruts2TokenName() + "=" + getStruts2Token();
    });

}); 
