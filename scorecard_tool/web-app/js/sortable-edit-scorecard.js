$(function() {
/* GROUPS */
	/* sortable */	
	$( "#sortableGroups" ).sortable({
		items: ".biggroup",
		handle: ".move.group",
		stop:  function(){$('.biggroup').removeClass("hover");}
	});
	
	/* hover */
	$(".move.group").live('mouseenter', function(){
		$(this).parents(".biggroup").addClass("hover");
    });
    $(".move.group").live('mouseleave', function(){
		$(this).parents(".biggroup").removeClass("hover");
    });
    
    /* add */
    $("th .addIcon").live('click', function() {
    	var group = $(".biggroup.example").clone().removeClass("hide example");
    	$(group).find("table.section").each(function() {
    		if(!$(this).hasClass('hide')){
				$(this).removeClass("example");
			}
		});
		$(this).parents(".biggroup").before($(group));
		calculateWeightG();
		calculateWeightS();
		calculateWeightQ();
		
		$($(this).parents(".biggroup").prev()).sortable({
			items: "table.section",
			connectWith: ".biggroup",
			handle: ".move.section.handle",
			stop:  function(){$('table.scorecard.view2.section').removeClass("hover");calculateWeightS();}
		});
		
		$($(group).find("table.section")).sortable({
			items: "tr.question",
			connectWith: "table.section",
			handle: ".move.question",
			helper: fixHelper,
			stop:  function(){$('tr.question').removeClass("hover");calculateWeightQ();}
		});
	});
	/* remove */
	$("th .remIcon").live('click', function() {
		if($("#sortableGroups table.group a.remIcon").size() != 1)
		{
			$(this).parents(".biggroup").fadeOut(800, function () {		
				$(this).remove();
				calculateWeightG();
				calculateWeightS();
				calculateWeightQ();
			});
		}
	});
	
	$("th .remIcon").live('mouseenter', function() {
		checkDisabledButton();
	});
/* SECTIONS */
	/* sortable */	
	$( ".biggroup" ).sortable({
		items: "table.section",
		connectWith: ".biggroup,.sections",
		handle: ".move.section.handle",
		stop:  function(){$('table.scorecard.view2.section').removeClass("hover");calculateWeightS();}
	});
	
	/* hover */
	$(".move.section").live('mouseenter', function(){
		if($(this).hasClass("handle"))
			$(this).parents("table.scorecard.view2.section").addClass("hover");
    });
    $(".move.section").live('mouseleave', function(){
		$(this).parents("table.scorecard.view2.section").removeClass("hover");
    });
    
	/* add */
	$("tr.section .addIcon").live('click', function() {
		$(this).parent().parent().parent().parent().before($(".biggroup.example table.section").first().clone().removeClass("hide example"));
		$($(this).parents(".biggroup")).find('table.section').sortable({
			items: "tr.question",
			connectWith: "table.section",
			handle: ".move.question",
			helper: fixHelper,
			stop:  function(){$('tr.question').removeClass("hover");calculateWeightQ();}
		});
		calculateWeightS();
	});
	
	/* remove */
	$("tr.section .remIcon").live('click', function() {
		if(!$(this).hasClass("onlyOne"))
		{
			$(this).parent().parent().parent().parent().fadeOut(800, function () {
				$(this).remove();
				calculateWeightG();
				calculateWeightS();
				calculateWeightQ();
			});
		}
	});


/* QUESTIONS */
	/* sortable */
	// Return a helper with preserved width of cells
	var fixHelper = function(e, ui) {
		ui.children().each(function() {
			$(this).width($(this).width());
		});
		return ui;
	};
	$( "table.section" ).sortable({
		items: "tr.question",
		connectWith: "table.section",
		handle: ".move.question",
		helper: fixHelper,
		stop:  function(){$('tr.question').removeClass("hover");calculateWeightQ();}
	});
	
	/* hover */
	$(".move.question").live('mouseenter', function(){
		$(this).parent().parent().parent().addClass("hover");
    });
    $(".move.question").live('mouseleave', function(){
		$(this).parent().parent().parent().removeClass("hover");
    });	
	
	/* remove */
	$("tr.question .remIcon").live('click', function() {
		$(this).parent().parent().fadeOut(800, function () {
			$(this).remove();
			calculateWeightG();
			calculateWeightS();
			calculateWeightQ();
		});
	});
	
	$('.saveChanges').live('click', function() {
		var errQ = calculateWeightQ();
		var errS = calculateWeightS();
		var errG = calculateWeightG();
		var err = errQ + errS + errG;

		if(err > 0){
			$('#validation').dialog("open");
			$('#validation p.err').remove();
			if(errQ)
			{
				$('#validation .body p:last').after("<p class='err'>Sum of question weights within a section must total 100!</p>");
			}
			if(errS)
			{
				$('#validation .body p:last').after("<p class='err'>Sum of section weights within a group must total 100!</p>");
			}
			if(errG)
			{
				$('#validation .body p:last').after("<p class='err'>Sum of all group weights must equal 100!</p>");
			}
			return false;
		}
	});

    //handler to trigger sum event
    $("input.weightQ").live('keyup', function() {
        calculateWeightQ();
    });
    
    //iterate through each textboxes and add keyup
    //handler to trigger sum event
    $("input.weightS").live('keyup', function() {
        calculateWeightS();
    });
    //handler to trigger sum event
    $("input.weightG").live('keyup', function() {
        calculateWeightG();
    });
	$("input.weightSumSections").attr("disabled", "disabled");
	$("input.weightSumQuestions").attr("disabled", "disabled");
	
	$("tr.questionHead a.addIcon").live('mouseenter', function(){
		var myLinkLeft = myLinkTop  = 0;
	
		myLinkLeft += $(this).position().left;
		myLinkTop += $(this).position().top;
	
		var menu = $(this).parent().find('.addmenu');
		$(menu).css("top",(myLinkTop+21)+'px');
		$(menu).css("left",myLinkLeft+'px');
		
		$(menu).show();
		$(this).addClass("hover");
	});
	$(".addmenu").live('mouseleave', function(){		
		$('.addmenu').hide();
		$('tr.questionHead a.addIcon').removeClass("hover");
	});
/* add validation */
// pop modal window
	$('#validation').dialog({
        autoOpen: false,
        width: 450,
        modal: true,
        draggable:false,
        resizable:false
    });
	
	$('#validation' + ' .closeDialog').click(function(){
		$('#validation').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});	

/* add manually */
// pop modal window
	$('#addManually').dialog({
        autoOpen: false,
        height: 415,
        width: 450,
        modal: true,
        draggable:false,
        resizable:false/*,
        show:{effect:'fade',mode:'show',speed:300},
        hide:{effect:'fade',mode:'hide',speed:300}*/
    });
	
	$('#addManually' + ' .closeDialog').click(function(){
		$('#addManually').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});	
	var openQuestionHead;
	$('.addManually').live('click',function(){
		$('.addmenu').hide();
		$('#addManually').dialog("open");
		$('#addManually textarea').each(function(){
			$(this).val("");
		});
		$('#addManually .body:first').nextAll(".body").remove();
		openQuestionHead = $(this).parents(".questionHead");
		return false;
	});
	
	/* more question link */
	$('#addManually a.moreQuestions').click(function(){
		$('#addManually .foot').before($(".addManually.body.example").clone().removeClass("addManually example"));
	});
	
	/* added question */
	$('#addManually .submitBtn').click( function(){
		$("#addManually .body").each(function() {
			var question = $(this).find('textarea.question').val();
			var guideline = $(this).find('textarea.guideline').val();
			var selType = $(this).find('select.type option:selected').val();
			addQuestion(openQuestionHead, question, guideline, selType);
		});
		$('#addManually').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});
	
	
/* add requirements */
// pop modal window
	$('#addReq').dialog({
        autoOpen: false,
        width: 450,
        modal: true,
        draggable:false,
        resizable:false/*,
        show:{effect:'fade',mode:'show',speed:300},
        hide:{effect:'fade',mode:'hide',speed:300}*/
    });
	
	$('#addReq' + ' .closeDialog').click(function(){
		$('#addReq').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});	
	
	$('.addReq').live('click',function(){
		$('.addmenu').hide();
		$('#addReq').dialog("open");
		$('#addReq textarea').each(function(){
			$(this).val("");
		});
		openQuestionHead = $(this).parents(".questionHead");
		return false;
	});
	
	/* added question */
	$('#addReq .submitBtn').click( function(){
		$("#addReq .body").each(function() {
			var questions = $(this).find('textarea.guideline').val();
			var selType = $(this).find('select option:selected').val();
			var lines = questions.split("\n");
			$.each(lines, function(n, elem) {
				addQuestion(openQuestionHead, elem, "", selType);
			});
		});
		$('#addReq').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});
/* add scorecard find */
// pop modal window
	$('#addScorecard2').dialog({
        autoOpen: false,
        height: 432,
        width: 750,
        modal: true,
        draggable:false,
        resizable:false/*,
        show:{effect:'fade',mode:'show',speed:300},
        hide:{effect:'fade',mode:'hide',speed:300}*/
    });
	
	$('#addScorecard2 .closeDialog').live('click',function(){
		$('#addScorecard2').dialog("close");
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		return false;
	});
	
	$('.addScorecard').live('click',function(){
		$('.addmenu').hide();
		$('#addScorecard2').dialog("open");
		openQuestionHead = $(this).parents(".questionHead");
		return false;
	});
	
	
	
/* add search results*/
	$('#addSearch').dialog({
        autoOpen: false,
        height: 530,
        width: 800,
        modal: true,
        draggable:false,
        resizable:false/*,
        show:{effect:'fade',mode:'show',speed:300},
        hide:{effect:'fade',mode:'hide',speed:300}*/
    });
	
	$('#addSearch .closeDialog').live('click',function(){
		$('#addScorecard2').dialog("close");
		return false;
	});	
	
	$('#addScorecard2 .searchBtn').live('click',function(){
		$('a.edit').hide();
		$('a.copy').show();
		$('#addSearch').dialog("open");
		$('#addScorecard2').dialog("close");
	});
	
	/* reuse html from scorecard-find.html */  
	$('#addScorecard2 .body').load('scorecard-find.html .scorecardFind');
	$('#addSearch .body').load('scorecard-search-result.html .scorecardSearch');

/* add Matching Scorecard Details */
	$('#addMatch').dialog({
        autoOpen: false,
        height: 530,
        width: 800,
        modal: true,
        draggable:false,
        resizable:false/*,
        show:{effect:'fade',mode:'show',speed:300},
        hide:{effect:'fade',mode:'hide',speed:300}*/
    });

	
	$('#addSearch a.copy').live('click',function(){
		$('#addMatch').dialog("open");
		$('#addMatch').find("input").each(function(){
			$(this).attr('checked', false);
			$(this).removeClass("hover");
		});
		$('#addMatch').find(".card,tr.question,table.checks.section").each(function(){
			$(this).removeClass('hover');	
		});
		
		$('#addSearch').dialog("close");
	});
	
	$('#addMatch a.back').live('click',function(){
		$('#addSearch').dialog("open");
		$('#addMatch').dialog("close");
	});

	/* check */
	$('#addMatch .checkMatch input').live('click',function(){
		$(this).parent().toggleClass('hover');
		if($(this).is(':checked')){
			if($(this).hasClass('group')){
				$(this).parents(".card").addClass('hover');
				$(this).parents("table.group").addClass('hover');

				$(this).parents(".card").find("tr.section .checkMatch input").each(function() {
					$(this).attr("checked", true);
					$(this).parents("table.checks.section").addClass('hover');
					$(this).parent().addClass('hover');
				});
				
				$(this).parents(".card").find("tr.question .checkMatch input").each(function() {
					$(this).attr("checked", true);
					$(this).parents("tr.question").addClass('hover');
					$(this).parent().addClass('hover');
				});
			}
			
			if($(this).hasClass('section')){
				$(this).parents("table.checks.section").addClass('hover');
				
				$(this).parents("table").find("tr.question .checkMatch input").each(function() {
					$(this).attr("checked", true);
					$(this).parents("tr.question").addClass('hover');
					$(this).parent().addClass('hover');
				});
			}
			
			if($(this).hasClass('question')){
				$(this).parents("tr.question").addClass('hover');
			}
	    }else{
			if($(this).hasClass('group')){
				$(this).parents(".card").removeClass('hover');
				$(this).parents("table.group").removeClass('hover');
				
				$(this).parents(".card").find("tr.section .checkMatch input").each(function() {
					$(this).attr("checked", false);
					$(this).parents("table.checks.section").removeClass('hover');
					$(this).parent().removeClass('hover');
				});
				
				$(this).parents(".card").find("tr.question .checkMatch input").each(function() {
					$(this).attr("checked", false);
					$(this).parents("tr.question").removeClass('hover');
					$(this).parent().removeClass('hover');
				});
			}
			if($(this).hasClass('section')){
				$(this).parents("table.checks.section").removeClass('hover');
				
				$(this).parents("table").find("tr.question .checkMatch input").each(function() {
					$(this).attr("checked", false);
					$(this).parents("tr.question").removeClass('hover');
					$(this).parent().removeClass('hover');
				});
			}
			
			if($(this).hasClass('question')){
				$(this).parents("tr.question").removeClass('hover');
			}
		}
	});
	
	/* copy scorecard from modal window "Matching Scorecard Details" */
	$('#addMatch a.copyScorecard').live('click',function(){
		$("#addMatch").find(".card.hover").each(function(){
			/*read group values*/
			var groupName = $(this).find("span.groupName").html();
			var groupWeight = $(this).find("span.groupWeight").html();
			/*create new group*/
			var newGroup = $(".biggroup.example").clone().removeClass("hide example");
			$(newGroup).find("table.section").each(function(){
				if(!$(this).hasClass('hide'))
					$(this).remove();
			});
			/*setting new values of group*/
			$(newGroup).find("input.groupName").val(groupName);
			$(newGroup).find("input.weightG").val(groupWeight);
			/*add new group*/
			$(openQuestionHead).parents(".biggroup").before($(newGroup));
			
			var actualGroupTable = $(openQuestionHead).parents(".biggroup").prev().find("table.group").first();
			
			$(this).find("table.section.hover").each(function(){
				/*read section values*/
				var sectionText = $(this).find("span.sectionText").html();
				var sectionWeight = $(this).find("span.sectionWeight").html();
				/*create new section*/
				var newSection = $(".biggroup.example table.section").first().clone().removeClass("hide example");
				/*setting new values of section*/
				$(newSection).find("input.sectionText").val(sectionText);
				$(newSection).find("input.weightS").val(sectionWeight);
				/*add new section*/
				$(actualGroupTable).next().prepend($(newSection));
				var actualSectionTable = $(actualGroupTable).next().find('table.section').first();
				
				$(this).find("tr.question.hover").each(function(){
					/*read question values*/
					var questionText = $(this).find("span.questionText").html();
					var questionWeight = $(this).find("span.questionWeight").html();
					var questionType = $(this).find("span.questionType").html();
					var questionUpload = $(this).find("span.questionUpload").html();
					
					var guideline = "";
					$(this).find(".rating").each(function(){
						guideline += $(this).find("span").html() + " - ";
						guideline += $(this).next().html() + "\n";
					});
 					/*create new question*/					
					addQuestion2($(actualSectionTable).find(".questionHead"), questionText, guideline, questionType, questionUpload, questionWeight)
				});
			});
		});
		
		$("#addMatch").find(".card").each(function(){
			if(!$(this).hasClass("hover"))
			{
				var actualGroupTable = $(openQuestionHead).parents(".biggroup").find("table.group").first();
				$(this).find("table.section.hover").each(function(){
					/*read section values*/
					var sectionText = $(this).find("span.sectionText").html();
					var sectionWeight = $(this).find("span.sectionWeight").html();
					/*create new section*/
					var newSection = $(".biggroup.example table.section").first().clone().removeClass("hide example");
					/*setting new values of section*/
					$(newSection).find("input.sectionText").val(sectionText);
					$(newSection).find("input.weightS").val(sectionWeight);
					/*add new section*/
					$(actualGroupTable).next().prepend($(newSection));
					
					var actualSectionTable = $(actualGroupTable).next().find('table.section').first();
					
					$(this).find("tr.question.hover").each(function(){
						/*read question values*/
						var questionText = $(this).find("span.questionText").html();
						var questionWeight = $(this).find("span.questionWeight").html();
						var questionType = $(this).find("span.questionType").html();
						var questionUpload = $(this).find("span.questionUpload").html();
						
						var guideline = "";
						$(this).find(".rating").each(function(){
							guideline += $(this).find("span").html() + " - ";
							guideline += $(this).next().html() + "\n";
						});
	 					/*create new question*/					
						addQuestion2($(actualSectionTable).find(".questionHead"), questionText, guideline, questionType, questionUpload, questionWeight)
					});
				});
			}
		});
		
		$("#addMatch").find("table.section").each(function(){
			
			if(!$(this).hasClass("hover"))
			{
				var actualSectionTable = $(openQuestionHead).parents("table.section");
				
				$(this).find("tr.question.hover").each(function(){
					/*read question values*/
					var questionText = $(this).find("span.questionText").html();
					var questionWeight = $(this).find("span.questionWeight").html();
					var questionType = $(this).find("span.questionType").html();
					var questionUpload = $(this).find("span.questionUpload").html();
					
					var guideline = "";
					$(this).find(".rating").each(function(){
						guideline += $(this).find("span").html() + " - ";
						guideline += $(this).next().html() + "\n";
					});
					/*create new question*/					
					addQuestion2($(actualSectionTable).find(".questionHead"), questionText, guideline, questionType, questionUpload, questionWeight)
				});
			}
		});
		calculateWeightQ();
		calculateWeightS();
		calculateWeightG();
		$('#addMatch').dialog("close");
		
		$('#sortableGroups').find(".biggroup").each(function(){
			$(this).sortable({
				items: "table.section",
				connectWith: ".biggroup,.sections",
				handle: ".move.section.handle",
				stop:  function(){$('table.scorecard.view2.section').removeClass("hover");calculateWeightS();}
			});
		});
		
		$('#sortableGroups').find("table.section").each(function(){
			$(this).sortable({
				items: "tr.question",
				connectWith: "table.section",
				handle: ".move.question",
				helper: fixHelper,
				stop:  function(){$('tr.question').removeClass("hover");calculateWeightQ();}
			});
		});
	});
	// if one group than group delete button is disabled
	// if one section of group than section delete button is disabled
	checkDisabledButton();
});


function calculateWeightQ() {
	var retQ = 0;
	$("table.scorecard.view2.section.edit").each(function() {
		if(!$(this).hasClass('example')){
	    	var sum = 0;
		    //iterate through each textboxes and add the values
		    $(this).find("input.weightQ").each(function() {
		        //add only if the value is number
		        if(!isNaN(this.value) && this.value.length!=0) {
		            sum += parseFloat(this.value);
		        }
		    });
		    //.toFixed() method will roundoff the final sum to 1 decimal places
		    $(this).find("input.weightSumQuestions").val(sum.toFixed(1));
		    if(sum.toFixed(1)!=100.0){
				$(this).find("input.weightSumQuestions").addClass("red").removeClass("green");
				retQ++;
			}else{
				$(this).find("input.weightSumQuestions").removeClass("red").addClass("green");
			}
		}		
	});
	
	return retQ;
}

function calculateWeightS() {
	var retS = 0;
    $(".biggroup").each(function() {
    	if(!$(this).hasClass('example')){
	    	var sum = 0;
		    //iterate through each textboxes and add the values
		    $(this).find("input.weightS").each(function() {
		
		        //add only if the value is number
		        if(!isNaN(this.value) && this.value.length!=0) {
		            sum += parseFloat(this.value);
		        }
		    });
		    //.toFixed() method will roundoff the final sum to 1 decimal places
		    $(this).find("input.weightSumSections").val(sum.toFixed(1));
		    if(sum.toFixed(1)!=100.0){
				$(this).find("input.weightSumSections").addClass("red").removeClass("green");
				retS++;
			}else{
				$(this).find("input.weightSumSections").removeClass("red").addClass("green");
			}
		}
	});
	checkDisabledButton();
	return retS;
}

function calculateWeightG() {
	var retG = 0;
  	var sum = 0;
  	
    //iterate through each textboxes and add the values
    $("#sortableGroups").find("input.weightG").each(function() {

        //add only if the value is number
        if(!isNaN(this.value) && this.value.length!=0) {
            sum += parseFloat(this.value);
        }
    });
    //.toFixed() method will roundoff the final sum to 1 decimal places
    $("#sortableGroups").find("input.weightSumGroups").val(sum.toFixed(1));
    if(sum.toFixed(1)!=100.0){
		$("#sortableGroups").find("input.weightSumGroups").addClass("red").removeClass("green");
		retG++;
	}else{
		$("#sortableGroups").find("input.weightSumGroups").removeClass("red").addClass("green");
	}
	checkDisabledButton();
	return retG;
}

// if one group than group delete button is disabled
// if one section of group than section delete button is disabled and disabled moving section
function checkDisabledButton()
{
	if($("#sortableGroups table.group a.remIcon").size() == 1)
	{
		$("#sortableGroups table.group a.remIcon").addClass("onlyOne");
	}else
	{
		$("#sortableGroups table.group a.remIcon").removeClass("onlyOne");
	}
	
	$("#sortableGroups .biggroup").each(function(){
		var count = 0;
		$(this).find("table.section").each(function(){count++;});
		if(count == 1)
		{
			$(this).find("table.section tr.section a.remIcon").addClass("onlyOne");
			$(this).find("tr.section .move").removeClass("handle");
		}else
		{
			$(this).find("table.section tr.section a.remIcon").removeClass("onlyOne");
			$(this).find("tr.section .move").addClass("handle");
		}	
	});
}

/* add question */
function addQuestion(openQuestionHead, question, guideline, selType){
	var questionSRC = $(".biggroup.example table.section tr.question").first().clone().removeClass('hide');
	$(questionSRC).find('textarea.question').val(question);
	$(questionSRC).find('textarea.guideline').val(guideline);
	$(questionSRC).find('select option[value='+selType+']').attr('selected', 'selected');
	
	$(openQuestionHead).after(questionSRC);
}

/* add question */
function addQuestion2(openQuestionHead, question, guideline, selType, upload, weight){
	var questionSRC = $(".biggroup.example table.section tr.question").first().clone().removeClass('hide');
	$(questionSRC).find('textarea.question').val(question);
	$(questionSRC).find('textarea.guideline').val(guideline);
	$(questionSRC).find('select option[value='+selType+']').attr('selected', 'selected');
	$(questionSRC).find("input.weightQ").val(weight);
	
	$(openQuestionHead).after(questionSRC);
}
