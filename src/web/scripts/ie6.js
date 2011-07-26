$(document).ready(function() {
						   
	DD_belatedPNG.fix('#navSingleSubmissionSlidePrev,  #navSingleSubmissionSlideNext');
	DD_belatedPNG.fix('#ie6-notification .tipBg');
	
	// bind hide function
	$("#ie6-notification A.close,#noAshAgain").click(function(){
		if($('#noAshAgain').attr('checked')){
			$.cookie("noIE6Notification", "yes", { expires: 90, path: '/', secure: false });	
		}
		hideIE6Notification();
	});
	
	$('#ie6-notification .alert strong').hover(function(){
		$(this).find('.tip').show();
		$(this).find('.tip').css('margin-top','10px');
	},function(){
		$(this).find('.tip').hide();
		$(this).find('.tip').css('margin-top','-9999px');
	});
	
	$(window).resize(function() {
		if(document.body.clientWidth <= 1000){
			$("#ie6-notification .content").css('width','1000px');
		}else{
			$("#ie6-notification .content").css('width','100%');	
		}
	});
	
	$('#ie6-notification a.downloadSubmission').attr('target','_blank');
	
});

var slide_during=500; //slide animation time during 500ms

// display ie6 banner function
showIE6Notification = function() {
	$('body').animate({paddingTop:'+=51'},slide_during);
	$('#ie6-notification').animate({top:'0'},slide_during);
}
// hide ie6 banner function 
hideIE6Notification = function() {
	$('body').animate({paddingTop:0},slide_during);
	$('#ie6-notification').animate({top:'-=51'},slide_during);
}

// check ie6
if(jQuery.browser.msie && (jQuery.browser.version == 6) && $.cookie("noIE6Notification") != "yes") {
	// display the ie6 notification
	setTimeout(showIE6Notification, 1000);
}