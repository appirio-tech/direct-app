// JavaScript Document
$(document).ready(function(){
						 
		$("#software").change(function(){
			var value = $(this).val();
			if(value == "Software Conceptualization" || value=="Software Specification" || value=="Architecture" || value=="Component Design"||value==
			   "Component Development" || value=="RIA Component"||value=="RIA Build"||value=="UI Prototype"||value=="Software Assembly"||
			   value=="Test Suites"||value=="Test Scenarios")
			{
				$("#milestone").css("display","none");
				$("#end").css("display","none");
				$("#type").css("display","none");
				$(".schedule").css("height","70px");
				$(".schedule").css("margin-bottom","105px");
			}
			else{
				$("#milestone").css("display","block");
				$("#end").css("display","block");
				$("#type").css("display","block");
				$(".schedule").css("height","165px");
				$(".schedule").css("margin-bottom","0px");
			}
								
		});
		
		$("#software").change(function(){
			var value = $(this).val();
			if(value == "Software Conceptualization" || value=="Software Specification" || value=="Architecture" || value=="Component Design"||value==
			   "Component Development" || value=="RIA Component"||value=="RIA Build"||value=="UI Prototype"||value=="Software Assembly"||
			   value=="Test Suites"||value=="Test Scenarios")
			{
				$("#cca").css("display","block");
			}
			else{
				$("#cca").css("display","none");
			}
		});
		
		$("#hide").click(function(){
			
			$("#info").css("display","none");
			$(".prizesInner_software").css("height","150px");
			
		});
		
		$('#add').click(function() {  
    		return !$('#select1 option:selected').remove().appendTo('#select2');  
    	});
		
		$('#remove').click(function() {  
 		return !$('#select2 option:selected').remove().appendTo('#select1');  
    	});
		
		$('#add_categories').click(function() {  
    		return !$('#select1_categories option:selected').remove().appendTo('#select2_categories');
    	});
		
		$('#remove_categories').click(function() {  
 		return !$('#select2_categories option:selected').remove().appendTo('#select1_categories');
    	});

		$('#catalogSelect').change(function() {
			if ($(this).val() == 'Net') {
				$('#select1_categories').children().remove().end().append('<option value="1">Analysis</option><option value="2">Application Management</option><option value="3">Communication</option><option value="4">Web</option><option value="5">Work Flow </option><option value="6">Other</option>');
				$('#select2_categories').children().remove();
			} else if ($(this).val() == 'Java') {
				$('#select1_categories').children().remove().end().append('<option value="1">Data Management</option><option value="2">Developer Tools</option><option value="3">Document Management</option><option value="4">Web</option><option value="5">Work Flow </option><option value="6">Other</option>');
				$('#select2_categories').children().remove();
			} else if ($(this).val() == 'Flex') {
				$('#select1_categories').children().remove().end().append('<option value="1">Financial</option><option value="2">Foundation</option><option value="3">Imaging</option><option value="4">Web</option><option value="5">Work Flow </option><option value="6">Other</option>');
				$('#select2_categories').children().remove();
			} else if ($(this).val() == 'C++') {
				$('#select1_categories').children().remove().end().append('<option value="1">Reporting</option><option value="2">Security</option><option value="4">Web</option><option value="5">Work Flow </option><option value="6">Other</option>');
				$('#select2_categories').children().remove();
			}
    	});
		
		$("#continue").click(function(){
			var value = $("#software").val();
			if(value == "Software Conceptualization" || value=="Software Specification" || value=="Architecture" || value=="Component Design"||value==
			   "Component Development" || value=="RIA Component"||value=="RIA Build"||value=="UI Prototype"||value=="Software Assembly"||
			   value=="Test Suites"||value=="Test Scenarios")
			{
				window.location = 'launch-contest-details-software.html';
			}
			else{
				window.location = 'launch-contest-details.html';
				
				
			}
								
		});			
		
		$(".contest_type_edit").css("display","none");
		$(".edit_type").click(function(){
			$(".contest_type").css("display","none");
			$(".contest_type_edit").css("display","block");							
		});
		
		$(".save_btn").click(function(){
			
			$(".contest_type").css("display","block");
			$(".contest_type_edit").css("display","none");
												
		});
		$(".cancel_text").click(function(){
			
			$(".contest_type").css("display","block");
			$(".contest_type_edit").css("display","none");
												
		});
		
		
		
		$(".contest_prize_edit").css("display","none");
		$(".edit_prize").click(function(){
			
			$(".contest_prize").css("display","none");
			$(".contest_prize_edit").css("display","block");
												
		});
		
		$(".save_btn_prize").click(function(){
			
			$(".contest_prize").css("display","block");
			$(".contest_prize_edit").css("display","none");
												
		});
		$(".cancel_text_prize").click(function(){
			
			$(".contest_prize").css("display","block");
			$(".contest_prize_edit").css("display","none");
												
		});
		
		
		
		$(".contest_round_edit").css("display","none");
		$(".edit_round").click(function(){
			
			$(".contest_round").css("display","none");
			$(".contest_round_edit").css("display","block");
												
		});
		
		$(".save_btn_round").click(function(){
			
			$(".contest_round").css("display","block");
			$(".contest_round_edit").css("display","none");
												
		});
		$(".cancel_text_round").click(function(){
			
			$(".contest_round").css("display","block");
			$(".contest_round_edit").css("display","none");
												
		});
		
		
		
		$(".contest_spec_edit").css("display","none");
		$(".edit_spec").click(function(){
			
			$(".contest_spec").css("display","none");
			$(".contest_spec_edit").css("display","block");
												
		});
		
		$(".save_btn_spec").click(function(){
			
			$(".contest_spec").css("display","block");
			$(".contest_spec_edit").css("display","none");
												
		});
		$(".cancel_text_spec").click(function(){
			
			$(".contest_spec").css("display","block");
			$(".contest_spec_edit").css("display","none");
												
		});
		
		
		$(".contest_files_edit").css("display","none");
		$(".edit_files").click(function(){
			
			$(".contest_files").css("display","none");
			$(".contest_files_edit").css("display","block");
												
		});
		
		$(".save_btn_files").click(function(){
			
			$(".contest_files").css("display","block");
			$(".contest_files_edit").css("display","none");
												
		});
		$(".cancel_text_files").click(function(){
			
			$(".contest_files").css("display","block");
			$(".contest_files_edit").css("display","none");
												
		});
		


		$("#single-multi").change(function(){
			var value=$(this).val();
			if(value == "Contest will be run in single-rounds")
			{
				$("#milestone").css("display","none");
				$(".schedule").css("height","150px");
			}
			else {
				$("#milestone").css("display","block");
				$(".schedule").css("height","165px");
			}
								
		});
		
		
						   // Drop Down Select Project
		$(".dropdown dt a").click(function() {
			$(".dropdown dd ul").toggle();
			return false;
		});
					
		$(".dropdown dd ul li a").click(function() {
			var text = $(this).html();
			$(".dropdown dt a span").html(text);
			$(".dropdown dd ul").hide();
			$("#result").html("Selected value is: " + getSelectedValue("sample"));
			return false;
		});
					
		function getSelectedValue(id) {
			return $("#" + id).find("dt a span.value").html();
		}

		$(document).bind('click', function(e) {
			var $clicked = $(e.target);
			if (! $clicked.parents().hasClass("dropdown"))
				$(".dropdown dd ul").hide();
		}); 
		
		// Drop Down Sort Contests
		$(".dropdown2 dt a").click(function() {
			$(".dropdown2 dd ul").toggle();
			return false;
		}); 
		
		$(".dropdown2 dd ul li a").click(function() {
			var text = $(this).html();
			$(".dropdown2 dt a span").html(text);
			$(".dropdown2 dd ul").hide();
			$("#result").html("Selected value is: " + getSelectedValue("sample2")); 
			$('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17});  
			return false;
		}); 
		
		function getSelectedValue(id) {
			return $("#" + id).find("dt a span.value2").html();
		}

		$(document).bind('click', function(e) {
			var $clicked = $(e.target);
			if (! $clicked.parents().hasClass("dropdown2"))
				$(".dropdown2 dd ul").hide();
		});  
		
		// Drop Down Search
		$(".dropdown3 dt a").click(function() {
			$(".dropdown3 dd ul").toggle();
			return false;
		});
					
		$(".dropdown3 dd ul li a").click(function() {
			var text = $(this).html();
			$(".dropdown3 dt a span").html(text);
			$(".dropdown3 dd ul").hide();
			$("#result").html("Selected value is: " + getSelectedValue("sample"));
			return false;
		});
					
		function getSelectedValue(id) {
			return $("#" + id).find("dt a span.value3").html();
		}

		$(document).bind('click', function(e) {
			var $clicked = $(e.target);
			if (! $clicked.parents().hasClass("dropdown3"))
				$(".dropdown3 dd ul").hide();
		}); 
		
		$("a.link-sort").click(function() { 
			var toLoad = $(this).attr('href'); 
			$('#scrollbar-wrapper div').hide('fast',loadContent);
			$('#load').remove();
			$('#scrollbar-wrapper div').append('<div id="load"></div>');
			$('#load').fadeIn('slow');
			window.location.hash = $(this).attr('href').substr(0,$(this).attr('href').length-5);
			function loadContent() {
				$('#scrollbar-wrapper div').load(toLoad,'',showNewContent()); 
			}
			function showNewContent() { 
				$('#scrollbar-wrapper div').show(0.001,hideLoader()); 
			}
			function hideLoader() {
				$('#load').fadeOut('slow');
			} 
		});  
			
	
	$("#expand-table").click(function() { 
			$("#collapse-table").show();  
			$(".row-hide").slideDown(0.0001);
			$(this).hide();  
			return false;
		}); 
	
	$("#collapse-table").click(function() { 
			$("#expand-table").show(); 
			$(".row-hide").slideToggle(0.0001);
			$(this).hide();
			return false;
		});
						   
	$('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17}); 
	$(".table-sidebar tr:odd").addClass("alt");
	$(".table-summary-content tr:even").addClass("alt");
	$("#collapse-table").hide();
	$(".row-hide").slideToggle(0.0001); 
	$("#wb-1").hide();
	$("#wb-2").hide();  
	
	$(".greentext").click(function() {
			$("#wb-1").fadeTo("slow", 1.0);
			return false;
		}); 
	
	$(".redtext").click(function() {
			$("#wb-2").fadeTo("slow", 1.0);
			return false;
		}); 
	
	$("a.btn-list").hover(function() {
			var $next = $(this).next(".dialog-mini-wrapper");								   
			$next.css("display", "block");
			$next.hover(function() {
				$next.css("display", "block");
				},function() {
				$next.css("display", "none");
			}); 
			},function() {
			var $next = $(this).next(".dialog-mini-wrapper");			
			$next.css("display", "none"); 
		}); 
	/* init select */
	if($('select').length > 0){
		$('.selectSoftware select,.selectDesign select,.catalogSelect select,.roundelect select,.startSelect select,.milestoneSelect select,.endSelect select,.milestoneEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect(); 
		
		$('.projectSelect select').sSelect({ddMaxHeight: '200px'});
		
		$('.billingSelect select').sSelect({ddMaxHeight: '200px'});

        $('.startEtSelect select,.endEtSelect select').sSelect({ddMaxHeight: '200px'});

        $('.selectDesign div.selectedTxt').html('Select Contest Type');
		
		$('.startEtSelect select').focus();
	}
	
	/* Optgroup 2 columns fix */
	if($('.selectDesign optgroup, .selectDesign .newListOptionTitle').length > 0){
		var optgroupMaxHeight = 0, num;
		
		$('.selectDesign optgroup').each(function(){
			num = $(this).children().length + 1;
			optgroupMaxHeight = num * 22 > optgroupMaxHeight ? num * 22 : optgroupMaxHeight;
		});
		
		$('.selectDesign .newList').css('height', optgroupMaxHeight + 'px');
		
		$(window).resize(function(){$('.selectDesign .newList').css('height', optgroupMaxHeight + 'px');});
		$(window).scroll(function(){$('.selectDesign .newList').css('height', optgroupMaxHeight + 'px');});
	}
	
	/* init date-pack */
	if($('.date-pick').length > 0){
		$(".date-pick").datePicker().val(new Date().asString()).trigger('change');
	}
	
	/* init tab **/
	$('#tab li').click(function(){
		
		$('#tab li a').removeClass('current');
		$(this).find('a').addClass('current');
		if($(this).attr('class') == 'top'){
			$('.selectDesign').show();
			$('.selectSoftware').addClass('visibility');
		}else{
			$('.selectDesign').hide();
			$('.selectSoftware').removeClass('visibility');	
		}
		
	});
	
	/* init pop */
	var prevPopup = null;
	showPopup = function(myLink,myPopupId){
		var myLinkLeft = myLinkTop  = 0;
		
		/* hide the previous popup */
		if( prevPopup )
			$(prevPopup).css("display","none");
			
		prevPopup = $('#'+myPopupId);
		
		/* get the position of the current link */
		do{
			myLinkLeft += myLink.offsetLeft;
			myLinkTop += myLink.offsetTop;
		}while( myLink = myLink.offsetParent );
		
		/* set the position of the popup */
		var popUpHeight2 = $('#'+myPopupId).height()/2;
		
		myLinkTop -= popUpHeight2;
	
		$('#'+myPopupId).css("top",(myLinkTop+4)+'px');
		$('#'+myPopupId).css("left",( myLinkLeft+22 )+'px');
		
		/* set the positio of the arrow inside the popup */
		$(".tooltipContainer SPAN.arrow").css("top",popUpHeight2+'px');
		
		/* show the popup */
		$('#'+myPopupId).css("display","block");
		
	}
	
	var prevPopups = null;
	showPopups = function(myLinks,myPopupIds){
		var myLinkLefts = myLinkTops  = 0;
		
		/* hide the previous popup */
		if( prevPopups )
			$(prevPopups).css("display","none");
			
		prevPopups = $('#'+myPopupIds);
		
		/* get the position of the current link */
		do{
			myLinkLefts += myLinks.offsetLeft;
			myLinkTops += myLinks.offsetTop;
		}while( myLinks = myLinks.offsetParent );
		
		/* set the position of the popup */
		var popUpHeight2s = $('#'+myPopupIds).height()/2;
		
		myLinkTops -= popUpHeight2s;
	
		$('#'+myPopupIds).css("top",(myLinkTops+4)+'px');
		$('#'+myPopupIds).css("left",( myLinkLefts+104 )+'px');
		
		/* set the positio of the arrow inside the popup */
		$(".tooltipContainer SPAN.arrow").css("top",popUpHeight2s+'px');
		
		/* show the popup */
		$('#'+myPopupIds).css("display","block");
		
	}
	
	$('.description .helpIcon').hover(function(){
		showPopup(this,'contestLaunch1');
	},function(){
		$('#contestLaunch1').hide();
	});
	


	$('.selectDesign .help,.selectSoftware .help').hover(function(){
		showPopups(this,'contestLaunch1');									
	},function(){
		$('#contestLaunch1').hide();
	});
	
	$('.mPrizes .helpIcon').hover(function(){
		showPopup(this,'contestLaunch2');
	},function(){
		$('#contestLaunch2').hide();	
	});
	
	$('.deliverables .helpIcon').hover(function(){
		showPopup(this,'contestLaunch3');
	},function(){
		$('#contestLaunch3').hide();	
	});
	
	$('.TB_overlayBG').css("opacity", "0.6");
	$('#TB_HideSelect').css("opacity", "0");
	$('#TB_overlay').hide();
	/* init help */
	$('.helloUser .help,.tabContest .moreLink,.help .helpIcon, .helpme').click(function(){
		$('#TB_overlay').show();
		$('#TB_window').show();
		$('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
		$('#TB_window').css({
            'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window").width() / 2)) + 'px'
        });
		$('#placeHolder').hide();
		$('#TB_ajaxContent').show();
	});
	
	$('.specrev-goto').click(function(){
		$('#TB_overlay').show();
		$('#TB_window_custom').show();
		$('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
		$('#TB_window_custom').css({
            //'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window_custom").width() / 2)) + 'px'
			'left' : $(window).width() / 2-$('#TB_window_custom').width() / 2,
			'top' : ($(window).height() / 2-$('#TB_window_custom').height() / 2) + $(window).scrollTop()
        });
	});
	$('#TB_window_custom').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window_custom").css('height')) / 2))});
	
	$('.conditions').click(function(){
		$('#TB_overlay').show();
		$('#TB_window').show();
		$('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
		$('#TB_window').css({
            'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window").width() / 2)) + 'px'
        });
		$('#placeHolder').show();
		$('#TB_ajaxContent').hide();
	});
	
	$('#TB_closeWindowButton').click(function(){
		$('#TB_overlay').hide();
		$('#TB_window').hide();										  
	});
	
	$('#addNewProject').click(function(){
		if($("#.projectSelect .newListSelected").is( ":hidden ")){
			$('.projectSelect .newListSelected').show();
			$('.projectSelect .text').hide();
			$(this).find('.right').text('ADD NEW');
		}else{
			$('.projectSelect .newListSelected').hide();
			$('.projectSelect .text').show();	
			$(this).find('.right').text('SELECT EXISTING');
		}
	});
	
	/* add button */
	$('.uploadInner .addButton').click(function(){
		
		$(this).parent().after('<div class="uploadInner" style="margin-top:12px;"><input type="text" class="text uploadInput" value="File Name" /><a href="javascript:;" class="button6"><span class="left"><span class="right">CHOOSE...</span></span></a><input type="text" class="text fileInput" value="File Description" /><a href="javascript:;" class="removeButton"><span class="hide">REMOVE</span></a><a href="javascript:;" class="addButton"><span class="hide">ADD</span></a></div>');						
	});
	
	$('.addButton').click(function(){
		var a = $(this).parent().parent().find('.uploadInner').length;
		a=a+1;
		
			
		$('<div class="uploadInner" style="margin-top:12px;"><strong class="left_align">'+a+'.&nbsp;&nbsp;&nbsp;</strong><input type="text" class="text uploadInput" value="File Name" /><a href="javascript:;" class="button6"><span class="left"><span class="right">CHOOSE...</span></span></a><input type="text" class="text fileInput" value="File Description" /> <img src="images/delete.png" alt="delete" class="deleteImg"  /><a class="delete" href="javascrip:;">Delete</a></div>').appendTo('.upload');						
	});
	
	$('.uploadInner .removeButton').click(function(){
		if($(this).parent().parent().find('.uploadInner').length > 1){
			$(this).parent().parent().find('.uploadInner:last').remove();
		}
	});
	
	$('.removeButton').click(function(){
		if($(this).parent().parent().find('.uploadInner').length > 1){
			$(this).parent().parent().find('.uploadInner:last').remove();
		}
	});
	
	$('.prizesInner .addButton').click(function(){
		if($(this).parent().parent().find('.prizesInner').eq(1).is( ":hidden ")){
			$(this).parent().parent().find('.prizesInner').eq(1).show();
			$(this).parent().parent().find('.prizesInner').eq(1).css('margin-top','12px');
		}else{
			$(this).parent().parent().find('.prizesInner').eq(2).show();
			$(this).parent().parent().find('.prizesInner').eq(2).css('margin-top','12px');
		}
	});
	
	
	$('.prizesInner .removeButton').click(function(){
		if($(this).parent().parent().find('.prizesInner').length > 0){
			$(this).parent().parent().find('.prizesInner:last').remove();
		}
	});
	
	$('.fileType').click(function(){
		if($('.deliverablesInner .fileInput').length < 3){
			$('.checkInput').append('<input type="checkbox" checked="checked" />&nbsp;&nbsp;<input type="text" class="text fileInput" />');
		}
	});

	
	/* bigframe */
	$('#TB_overlay').bgiframe();
	
	$('#TB_window').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});
	
	$(".uploadInner .button6").click(function(){$(".uploadInner .fileIn").trigger("click")});


     /* js functions for copilot pages */

     if($.fn.tooltip){
         $(".launchCopilotContest .tooltipLink").tooltip({
            tip:"#finalFixesTip",
            delay:0,
            position:"center right",
            offset:[-5,1]
        })
    }

     // styling file input
    $(".fileUpload .fileInput").styleingInputFile();

    function addFileItem(){
        var clone = $(".fileUpload .cloneit");
        var task = clone.clone();
        task.find(".fileInput").styleingInputFile();
        task.removeClass("cloneit").addClass("addFile");
        task.find("a.addButton").click(function(){
            addFileItem();    
        }) ;
        task.find("a.removeButton").click(function(){
            task.remove();
        })
        clone.before(task);
    }

    $(".fileUpload .addFile a.addButton").click(addFileItem);

   // edit function
    $(".editMask .editPanel").hide(); 
    $(".editMask .editLink").click(function(){
        var mask = $(this).parents(".editMask");
        mask.find(".editPanel").show();
        mask.find(".infoPanel").hide();
       /* $(".editMask .mceLayout").css("width","100%");*/
    });

    $(".editMask .cancel_text,.editMask .save_btn").click(function(){
        var mask = $(this).parents(".editMask");
        mask.find(".editPanel").hide();
        mask.find(".infoPanel").show(); 
    });

    // save as draft dialog
    $(".launchCopilotContest .draft").overlay({
            closeOnClick:false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top:"20%",
            close :"#saveAsDraftOK",
            fixed : true,
            target : $("#saveAsDraft")
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

	
	 
