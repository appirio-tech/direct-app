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
/*
		$('.TB_overlayBG').css("opacity", "0.6"); 
		$('#TB_overlaySingle').hide();
		
		$('.thumbSingle').click(function(){
			$('#TB_overlaySingle').show();
			$('#TB_windowSingle').show();
			$('#TB_overlaySingle').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
			$('#TB_windowSingle').css({
				'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_windowSingle").width() / 2)) + 'px'
			});
			$('#TB_windowSingle').css('top', 20);
			return false;
		});
		
		$('#TB_closeWindowButtonSingle').click(function(){
			$('#TB_overlaySingle').hide();
			$('#TB_windowSingle').hide();										  
		});
		
		$('#TB_overlaySingle').bgiframe(); 
		$('#TB_windowSingle').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});*/
		

});