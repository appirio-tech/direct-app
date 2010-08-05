$(function(){

	//judge browser
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();	
	 
		$("#dropdownWrapper select").msDropDown(); 

	/**************************   fix browser    *************************************/
	//safari
	if(ua.match(/version\/([\d.]+).*safari/)!=null &&
	ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){		
		$("#dropdownWrapper  .dd").css('width','176px');
		 
	}
	if($.browser.msie && $.browser.version == 6.0){	  
		$("#dropdownWrapper  .dd").css({'width':'176px' });  
		$("#contest-sort label").css({'zoom':'1' });   
	}
	
	if($.browser.msie){	  
		$("#dropdownWrapper  .dd").css({'width':'176px' });   
	}
	
	if($.browser.mozilla){ 
		$("#dropdownWrapper  .dd").css({'width':'176px'});
		 
	}	
	 
});

