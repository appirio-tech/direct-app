// JavaScript Document
$(document).ready(function(){

	//text-shadow
	$('.solveProblem .inner h3,.stepBar li span.bg,.stepFifth .geryContent .downloadProfile .profileLeft').css('text-shadow','0px 1px 0px #ffffff');
	$('.stepBar li a').css('text-shadow','0px 1px 0px #6C871E');
	$('.stepBar li .complete a').css('text-shadow','0px 1px 0px #53661C');
	
	//z-index for step bar
	$('.stepBar li').each(function(){
		var totalStep = $('.stepBar li').length;
		var iNum = $('.stepBar li').index(this);
		$(this).css('z-index',totalStep-iNum);					   
	});
	
	$('.stepSecond .radio,.stepForth .radio').attr('checked','');
	
	//toolTip
	$('.toolTip').hover(function(){
		$(this).append('<div class="toolTipContainer"><div class="arrow"></div><p></p></div>');
		$(this).find('p').text($(this).attr('rel'));
	},function(){
		$(this).empty();
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
	
	//scroll 
	$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','scroll');
	
	//list selected
	$('#addUserModal .addUserForm .addUserList li').live('click',function(){
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');	
		}else{
			$(this).addClass('selected');		
		}															   
	});
	
	//select all
	$('#addUserModal .addUserForm .selectAll').click(function(){
		$('#addUserModal .addUserForm .addUserLeft ul li').addClass('selected');
	});
	
	//remove all
	$('#addUserModal .addUserForm .removeAll').click(function(){
		$('#addUserModal .addUserForm .addUserLeft ul').append($('#addUserModal .addUserForm .addUserRight ul').html());
		$('#addUserModal .addUserForm .addUserRight ul').empty();
		if($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186){
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','visible');	
		}
		if($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237){
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','visible');	
		}
	});
	
	//add item
	$('#addUserModal .addUserForm .addItem').live('click',function(){
		$('#addUserModal .addUserForm .addUserLeft ul li.selected').each(function(){
			$('#addUserModal .addUserForm .addUserRight ul').append('<li>'+$(this).html()+'</li>');	
			$(this).remove();																	  
		});
		if($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186){
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','visible');	
		}
		if($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237){
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','visible');	
		}
	});
	
	//remove item
	$('#addUserModal .addUserForm .removeItem').live('click',function(){
		$('#addUserModal .addUserForm .addUserRight ul li.selected').each(function(){
			$('#addUserModal .addUserForm .addUserLeft ul').append('<li>'+$(this).html()+'</li>');	
			$(this).remove();																	  
		});
		if($('#addUserModal .addUserForm .addUserLeft .addUserList ul').height() >= 186){
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserLeft .addUserList').css('overflow-y','visible');	
		}
		if($('#addUserModal .addUserForm .addUserRight .addUserList ul').height() >= 237){
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','scroll');
		}else{
			$('#addUserModal .addUserForm .addUserRight .addUserList').css('overflow-y','visible');	
		}
	});
	
	//add user
	$('.stepFifth .geryContent .addUserButton,.stepFifth .geryContent .addMoreUsers,.stepFifth .geryContent .folderIcon').click(function(){
		addresscloseModal();
		addressLoadModal('#addUserModal');
	});
	
	$('#addUserModal .saveButton').live('click',function(){
		addresscloseModal();									 
		$('.stepFifth .form').hide();
		$('.stepFifth .addUserPlan').show();
		return false;
	});
	
	//deleteConfirm
	$('.stepFifth .geryContent .deleteSelect').click(function(){
		addresscloseModal();
		addressLoadModal('#deleteUserModal');														  
	});
	
	$('.stepFifth .geryContent .deleteIcon').click(function(){
		addresscloseModal();
		addressLoadModal('#deleteConfirmModal');
	});
	
	$('.stepFifth .geryContent .nextStepButton').click(function(){
		var falgRadio2 = false;
		$('.stepFifth .selectUserCheck').each(function(){
			if($(this).attr('checked')){
				falgRadio2 = true;
			}						  
		});
		if($('.addUserPlan').is(":visible")){
			if((!falgRadio2)){
				addresscloseModal();
				addressLoadModal('#alertModal');
			}else{
				window.location.href = 'start-a-new-project-step-6.html';	
			}
		}else{
			addresscloseModal();
			addressLoadModal('#alertModal');	
		}
	});
	
	$('#alertModal .step6Button').click(function(){
		window.location.href = 'start-a-new-project-step-6.html';												 
	});
	
	
	
	if($.browser.msie&&($.browser.version == "7.0")){
		$('.stepForth .form dl.radioList dd .radio').css('margin-top','-4px');
		$('.stepForth .form dl.radioList dd').css('margin-left','28px');
		$('.stepSecond .radioBox').css('margin-left','0px');
		$('.stepSecond .radioBox .radio').css('margin-top','-3px');
		$('.selectUserCheck,.selectUser').css('margin-top','-3px');
	}
	
	//all
	$('.applyForAll td:eq(1) .selectUser').click(function(){
		if($(this).attr('checked')){
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(1) .selectUser').attr('checked','checked');										
			});	
		}else{
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(1) .selectUser').attr('checked','');										
			});	
		}
	});
	
	$('.applyForAll td:eq(2) .selectUser').click(function(){
		if($(this).attr('checked')){
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(2) .selectUser').attr('checked','checked');										
			});	
		}else{
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(2) .selectUser').attr('checked','');										
			});	
		}
	});
	
	$('.applyForAll td:eq(3) .selectUser').click(function(){
		if($(this).attr('checked')){
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(3) .selectUser').attr('checked','checked');										
			});	
		}else{
			$('.checkPermissions tr').each(function(){
				$(this).find('td:eq(3) .selectUser').attr('checked','');										
			});	
		}
	});
	
	/*--------------------------------------- Show/Hide group users (the table on the permissions tab ) --*/
	showHideGroup = function(myLink, rowClass){
		/*  myLink: the clicked link
			rowClass: the class name of the group */
		
		/* change the link ico */
		if( myLink.className.search("expand") != -1 ){
			$(myLink).removeClass("expand");
			$(myLink).addClass("collapse");
		}else{
			$(myLink).removeClass("collapse");
			$(myLink).addClass("expand");
		}
		
		
		$("."+rowClass).toggle();
	}
	
	/*--------------------------------------- Show/Hide group users (the table on the permissions tab ) --*/
	showHideGroup2 = function(myLink, rowClass){
		/*  myLink: the clicked link
			rowClass: the class name of the group */
		
		/* change the link ico */
		var $kid = $(myLink).children();
		if( $kid.hasClass("expand")){
			$kid.removeClass("expand");
			$kid.addClass("collapse");
		}else{
			$kid.removeClass("collapse");
			$kid.addClass("expand");
		}
				
		$("."+rowClass).toggle();
	}
	
	$('.stepForth .form dl.radioList dd span').click(function(){
		if($(this).parent().find('.radio').attr('checked')){
			$(this).parent().find('.radio').attr('checked','');	
		}else{
			$(this).parent().find('.radio').attr('checked','checked');	
		}													 
	});
	
	if($.browser.msie&&($.browser.version == "8.0")){
		$('#notifications .deleteSelect,#notifications .addMoreUsers').css('margin-top','3px');	
	}
	
	/*$('.projectContainer').click(function(){
		$('.projectContainer').removeClass('hover');
		$(this).addClass('hover');	
		$('.projectList').find('.radio').attr('checked','');
		$(this).parent().find('.radio').attr('checked','checked');
	});
	
	$('.projectItem label').click(function(){
		if($(this).parent().find('.radio').attr('checked')){
			$(this).parent().find('.radio').attr('checked','');	
		}else{
			$(this).parent().find('.radio').attr('checked','checked');	
		}
		$('.projectContainer').removeClass('hover');
		$(this).parent().parent().find('.projectContainer').addClass('hover');
	});*/
	
});