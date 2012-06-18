// JavaScript Document

var switchMaintenance = true;

$(function(){
	var slide_during=500; //slide animation time during 500ms

	// display ie6 banner function
	showMaintenanceNotification = function() {
		$('body').animate({paddingTop:'+=89'},slide_during);
		$('#topcoder-maintenance-notification').animate({top:'0'},slide_during);
	}
	// hide ie6 banner function 
	hideMaintenanceNotification = function() {
		$('body').animate({paddingTop:0},slide_during);
		$('#topcoder-maintenance-notification').animate({top:'-=89'},slide_during);
	}
	
	$(window).resize(function() {
		if(document.body.clientWidth <= 1000){
			$("#topcoder-maintenance-notification .content").css('width','1000px');
		}else{
			$("#topcoder-maintenance-notification .content").css('width','100%');	
		}
	});
	
	// bind hide function
	$("#topcoder-maintenance-notification A.close").click(function(){
		hideMaintenanceNotification();
	});
	
	if(switchMaintenance) setTimeout(showMaintenanceNotification, 1000);
	
});