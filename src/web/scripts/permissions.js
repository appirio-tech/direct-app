// JavaScript function for permissions.html
$(document).ready(function(){
	
	// customize the select
	//$('.display-perpage select').sSelect({ddMaxHeight: '200px'});
	
	// pop modal window
	$.each(["manageUserDialog","addUserDialog","addProjectDialog"],function(index,item){
		$('#' + item).dialog({
	        autoOpen: false,
	        height:450,
	        width: 580,
	        modal: true,
	        draggable:false,
	        resizable:false/*,
	        show:{effect:'fade',mode:'show',speed:300},
	        hide:{effect:'fade',mode:'hide',speed:300}*/
	    });
		
		$('#' + item + ' .closeDialog').click(function(){
			$('#' + item).dialog("close");
			return false;
		}) 
	})
	
	
	
	$('.addUser').click(function(){
		$('#manageUserDialog').dialog("open");
		return false;
	})
	
	$('.addProject').click(function(){
		$('#addProjectDialog').dialog("open");
		return false;
	})
	
	$('.addMoreUsers').click(function(){
		$('#addUserDialog').dialog("open");
		return false;
	}) 
	
	$(".permissions .firstItem").click(function(){
		$("#projects").show();
		$("#users").hide();
		$(this).addClass("on");
		$(".permissions .lastItem").removeClass("on");
	})
	
	$(".permissions .lastItem").click(function(){
		$("#users").show();
		$("#projects").hide();
		$(this).addClass("on");
		$(".permissions .firstItem").removeClass("on");
	})
	
	
	$(".ui-dialog .body .list .listItem").click(function(){
		$(this).toggleClass('active');
	})
	
	
})