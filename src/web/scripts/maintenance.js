// JavaScript Document
var allowMaintenance = true;

var switchMaintenance = true;
var switchIE = true;

$(function(){
	
	// bind hide function
	$("#topcoder-maintenance-notification A.close, #noAskMaintenanceAgain").click(function(){
		if($('#noAskMaintenanceAgain').attr('checked')){
			$.cookie('noMaintenanceCookies', 'yes', { expires: 90 }); 
		} 
		hideMaintenanceNotification(0);
	}); 
	
	$("#ie6-notification A.close, #noAshAgain").click(function(){
		if($('#noAshAgain').attr('checked')){
			$.cookie('ie6Cookie', 'yes', { expires: 90 }); 
		} 
		hideMaintenanceNotification(1);
	});
	
	$(window).resize(function() {
		if(document.body.clientWidth <= 1000){
			$("#topcoder-maintenance-notification .content").css('width','1000px');
		}else{
			$("#topcoder-maintenance-notification .content").css('width','100%');	
		}
	}); 
	if($.cookie("noMaintenanceCookies") != "yes") {
				switchMaintenance = true;
	} else {
        switchMaintenance = false;
  }
  if (!allowMaintenance) {
  		switchMaintenance = false;
  }
  if($.cookie("ie6Cookie") != "yes") {
				switchIE = true;
	} else {
        switchIE = false;
  }
	if(switchMaintenance) setTimeout(showMaintenanceNotification, 1000)
	
	
});
	function displayMessage(msg) {
        $('#message').html(msg).css({color: 'green'});
      } 
	var slide_during=500; //slide animation time during 500ms

	// display ie6 banner function
showMaintenanceNotification = function() {
    if($('#ie6-notification').offset().top == 0){
    	if (switchIE) {
         if (switchMaintenance) {
         	 $('body').animate({paddingTop:140},slide_during);
       		 $('#topcoder-maintenance-notification').animate({top:'51'},slide_during);
       	 } else {
       	 	 $('body').animate({paddingTop:51},slide_during);
       	 }
      }
    } else {    	
    	if (switchMaintenance) {
        $('body').animate({paddingTop:89},slide_during);
      	$('#topcoder-maintenance-notification').animate({top:'0'},slide_during);
      }
    }
}
	// hide maintainance banner function
	hideMaintenanceNotification = function(flag) {
   	if($('#ie6-notification').offset().top == 0){
   		if (flag == 0)  {
        $('body').animate({paddingTop:51},slide_during);
      } else {
      	if ($('#topcoder-maintenance-notification').offset().top == 51) {
      		$('body').animate({paddingTop:89},slide_during);
      	} else {
      		$('body').animate({paddingTop:0},slide_during);	
      	}
      }
    } else {
        $('body').animate({paddingTop:0},slide_during);
    }
    if (flag == 0) {
			$('#topcoder-maintenance-notification').animate({top:'-89'},slide_during);
		}
	}

	if($.cookie("ie6Cookie") != "yes") {
	// display the ie6 notification
		setTimeout(showMaintenanceNotification, 2000);
	}

