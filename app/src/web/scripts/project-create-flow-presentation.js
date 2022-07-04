/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript code related to presentation project creation flow.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
 * 
 * @version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence) change notes:
 * 				added populate project answer for presentation project.
 *              added initialize project question for presentation project.
 * 
 * @author: KennyAlive, Ghost_141
 * @version 1.1
 */

function initPresentationProjectFlow() {
	initProjectQuestions(PROJECT_TYPE_PRESENTATION);
    initPresentationStep1();
    initPresentationStep2();
    initPresentationStep3();
    initPresentationStep4();
    initPresentationStep5();

    $(".presentation .stepFirst2 .rightCnt input[name=part], .stepFirst2 .rightCnt textarea").attr("disabled","disabled");
    $(".presentation .stepFirst2 .leftCnt input[name=whole]").removeAttr("disabled");
    $(".presentation input[name=presentation]:first").attr("checked","checked");
    
}

//-----------------------------------------------
// Step 1
//-----------------------------------------------
function initPresentationStep1() {
    $("#newPresentationProjectStep1 textarea").css({width:"95%",resize:"none"});
    
    $('#newPresentationProjectStep1 .nextStepButton').click(function(){
        var valid = true;
        if($("#newPresentationProjectStep1 .rightCnt input[name=part]:checked").val()=="other" && $("#newPresentationProjectStep1 input[name=presentation]:checked").val()=="part"){
            if($("#newPresentationProjectStep1 .rightCnt textarea[name=part]").val()==""){
                $("#newPresentationProjectStep1 .textarea").removeClass("error2");
                $("#newPresentationProjectStep1 .rightCnt textarea[name=part]").parent().addClass("error2");
                $("#newPresentationProjectStep1 p.message, .stepFirst2 .textarea span").hide();
                $("#newPresentationProjectStep1 .rightCnt textarea[name=part]").siblings("span").show();
                $("#newPresentationProjectStep1 .rightCnt textarea[name=part]").parent().siblings("p").show();
                valid = false;
            }
        }
        if($("#newPresentationProjectStep1 .leftCnt input[name=whole]:checked").val()=="other" && $("#newPresentationProjectStep1 input[name=presentation]:checked").val()=="whole"){
            if($("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").val()==""){
                $("#newPresentationProjectStep1.textarea").removeClass("error2");
                $("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").parent().addClass("error2");
                $("#newPresentationProjectStep1 .leftCnt p.message, .stepFirst2 .rightCnt .textarea span").hide();
                $("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").siblings("span").show();
                $("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").parent().siblings("p").show();
                valid = false;
            }
        }
        if (valid) {
            $("#newPresentationProjectStep1 .textarea").removeClass("error2");
            $("#newPresentationProjectStep1 .rightCnt textarea[name=part]").siblings("span").hide();
            $("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").siblings("span").hide();
            $("#newPresentationProjectStep1 .leftCnt textarea[name=whole]").parent().siblings("p").hide();
            $("#newPresentationProjectStep1 .rightCnt textarea[name=part]").parent().siblings("p").hide();
            goCreatePresentationProjectStep(2);
        }
    });

    $("#newPresentationProjectStep1 input[name=presentation]").change(function(){
        if($(this).val()=="whole"){
            $("#newPresentationProjectStep1 input[name=whole]:first").attr("checked","checked");
            $("#newPresentationProjectStep1 .rightCnt").addClass("disable");
            $("#newPresentationProjectStep1 .rightCnt input[name=part], #newPresentationProjectStep1 .rightCnt textarea").attr("disabled","disabled");
            $("#newPresentationProjectStep1 .leftCnt").removeClass("disable");
            $("#newPresentationProjectStep1 .leftCnt input[name=whole], #newPresentationProjectStep12 .leftCnt textarea").removeAttr("disabled");
        }else{
            $("#newPresentationProjectStep1 input[name=part]:first").attr("checked","checked");
            $("#newPresentationProjectStep1 .rightCnt").removeClass("disable");
            $("#newPresentationProjectStep1 .rightCnt input[name=part], #newPresentationProjectStep1 .rightCnt textarea").removeAttr("disabled");
            $("#newPresentationProjectStep1 .leftCnt").addClass("disable");
            $("#newPresentationProjectStep1 .leftCnt input[name=whole], #newPresentationProjectStep1 .leftCnt textarea").attr("disabled","disabled");
        }
        $("#newPresentationProjectStep1 .leftCnt textarea, #newPresentationProjectStep1 .rightCnt textarea").removeClass("error");
    });
}

//-----------------------------------------------
// Step 2
//-----------------------------------------------
function initPresentationStep2() {
    $("#newPresentationProjectStep2 textarea").css({width:"95%",resize:"none"});

    $('.presentation input[name=format]:first').attr("checked","checked");
    $('.presentation input[name=visuals]:first').attr("checked","checked");

    $('.presentation .stepSecond2 .nextStepButton').click(function(){
        removeError2($(".presentation .stepSecond2 input[type=text], .presentation .stepSecond2 textarea"));
        var valid1 = validate2($(".presentation .stepSecond2 input.title, .presentation .stepSecond2 textarea"));

        var valid2=true;
        if($('.presentation input[name=format]:checked').val()=="other"){
            valid2 = validate2($(".presentation .stepSecond2 input.format"));
        }

        var valid3=true;
        if($('.presentation input[name=visuals]:checked').val()=="other"){
            valid3 = validate2($(".presentation .stepSecond2 input.visuals"));
        }

        if (valid1 && valid2 && valid3) {
            goCreatePresentationProjectStep(3);
        }
    });

    $(".presentation input[name=format]").change(function(){
        $(this).parent().siblings("p").find(".errorText2").hide();
        $(this).parent().siblings("span.input").find(".errorIcon2").hide();
        $(this).parent().siblings("span.input").removeClass("error2");
    });
    $(".presentation input[name=visuals]").change(function(){
        $(this).parent().siblings("p").find(".errorText").hide();
        $(this).parent().siblings("span.input").find(".errorIcon2").hide();
        $(this).parent().siblings("span.input").removeClass("error2");
    });
    
    $('.presentation input[name=format]').change(function(){
        if($('input[name=format]:checked').val()=="other"){
            $('input[name=format]:checked').parent().siblings("span.input").removeClass("disabled");
            $('input[name=format]:checked').parent().siblings("span.input").find("input").removeAttr("disabled");
        }else{
            $('input[name=format]:checked').parent().siblings("span.input").addClass("disabled");
            $('input[name=format]:checked').parent().siblings("span.input").find("input").attr("disabled","disabled");
        }
    });
    $('.presentation input[name=visuals]').change(function(){
        if($('input[name=visuals]:checked').val()=="other"){
            $('input[name=visuals]:checked').parent().siblings("span.input").removeClass("disabled");
            $('input[name=visuals]:checked').parent().siblings("span.input").find("input").removeAttr("disabled");
        }else{
            $('input[name=visuals]:checked').parent().siblings("span.input").addClass("disabled");
            $('input[name=visuals]:checked').parent().siblings("span.input").find("input").attr("disabled","disabled");
        }
    });

    $(".presentation .stepSecond2 .left .input input.limit").keydown(function(e){
       var str =$(this).val();
        if(str.length>= 30){
            if((e.keyCode==8 || e.keyCode== 46 || e.keyCode== 37 || e.keyCode== 39 || e.keyCode== 35 || e.keyCode== 36)){
                ;
            } else {
                return false;
            }
        }
    }).keyup(function(){
            $(this).parent().siblings("p").find(".remaning").find(".num").text(eval(30-$(this).val().length))
        }).keyup();
}

//-----------------------------------------------
// Step 3
//-----------------------------------------------
function initPresentationStep3() {
    $('.presentation .stepThird2 .nextStepButton').unbind("click").click(function(){
        var valid=true, valid1=true, valid2=true, valid3=true;

        removeError2($(".presentation input[type=text]"));
        if($('.presentation input[name=audience].other').is(":checked")){
            valid1 = validate2($(".presentation .stepThird2 input.audience"));
        }
        if($('.presentation input[name=purpose].other').is(":checked")){
            valid2 = validate2($(".presentation .stepThird2 input.purpose"));
        }
        if($('.presentation input[name=style].other').is(":checked")){
            valid3 = validate2($(".presentation .stepThird2 input.style"));
        }

        if($(".presentation .stepThird2 input[name=audience]:checked").length>0){
            $(".presentation span.label.audience").removeClass("errorText");
            $(".presentation span.label.audience").next().hide();
            $(".presentation span.label.audience").next().next().next().find(".errorText").hide();
        }else{
            $(".presentation span.label.audience").addClass("errorText");
            $(".presentation span.label.audience").next().show();
            $(".presentation span.label.audience").next().next().next().find(".errorText").show();
            valid =false;
        }
        if($(".presentation .stepThird2 input[name=purpose]:checked").length>0){
            $(".presentation span.label.purpose").removeClass("errorText");
            $(".presentation span.label.purpose").next().hide();
            $(".presentation span.label.purpose").next().next().next().find(".errorText").hide();
        }else{
            $(".presentation span.label.purpose").addClass("errorText");
            $(".presentation span.label.purpose").next().show();
            $(".presentation span.label.purpose").next().next().next().find(".errorText").show();
            valid =false;
        }
        if($(".presentation .stepThird2 input[name=style]:checked").length>0){
            $(".presentation span.label.style").removeClass("errorText");
            $(".presentation span.label.style").next().hide();
            $(".presentation span.label.style").next().next().next().find(".errorText").hide();
        }else{
            $(".presentation span.label.style").addClass("errorText");
            $(".presentation span.label.style").next().show();
            $(".presentation span.label.style").next().next().next().find(".errorText").show();
            valid =false;
        }
        if (valid && valid1 && valid2 && valid3) {
            goCreatePresentationProjectStep(4);
        }
    });

    $(".presentation .stepThird2 a.addField").click(function(){
        var id = this.id;
        var html ="<label class='last'><input class='other' value='other' type='checkbox' name='" + id + "'/><span>&nbsp;</span></label>" +
            "<span class='input'><input disabled='disabled' class='waterMark " + id  + "' title='other' type='text' value='other'/></span>" +
            "<div class='clear'></div> " +
            "<p class='message padding'><span class='errorText'>This field cannot be left empty.</span></p>"

        var elem = $(html);
        $(elem).find(".presentation input[type=checkbox]").attr("name",$(this).attr("id"));
        $(elem).find(".presentation input[type=text]").addClass($(this).attr("id"));
        $(this).parent().siblings("label:last").before(elem);
        $(this).parent().parent().find(".errorText").hide();
        $(this).parent().parent().find(".errorIcon").hide();
        $(this).parent().parent().find(".input").removeClass("error");
    });

    $(".presentation .stepThird2 input[type=checkbox]").live("change",function(){
        if($(this).is(":checked") && ($(this).val()=="other")){
            $(this).parent().next("span.input").removeClass("disabled");
            $(this).parent().next("span.input").find("input").removeAttr("disabled");
        }else if(!$(this).is(":checked") && ($(this).val()=="other")){
            $(this).parent().next("span.input").addClass("disabled");
            $(this).parent().next("span.input").find("input").attr("disabled","disabled");
        }
    });
}

//-----------------------------------------------
// Step 4
//-----------------------------------------------
function initPresentationStep4() {
    $("#newPresentationProjectStep4 .nextStepButton").click(function() {
        goCreatePresentationProjectStep(5);
    });
}

//-----------------------------------------------
// Step 5
//-----------------------------------------------
function initPresentationStep5() {
    $(".presentation .stepFifth2 .confirmButton a").click(function(){
        $(".presentation .stepFifth2 .proceed .message").show();
    });
    $(".presentation .stepFifth2 .message a.no").click(function(){
        $(".presentation .stepFifth2 .proceed .message").hide();
    });
    $(".presentation .stepFifth2 .message a.yes").click(function(){
        createNewProject();
    });
}

var goCreatePresentationProjectStep = function(stepNumber) {
    if (stepNumber < 1 || stepNumber > 5) {
        return;
    }

    if (stepNumber == 5) {
        updatePresentationProjectSummaryPage();
    }

    if (stepNumber == 4) { // step4 jsp is shared between mobile and presentation flow.
        $('.newProjectStep').hide();
        $('#newSharedProjectStep4').show();
    } else {
        $('.newProjectStep').hide();
        $('#newPresentationProjectStep' + stepNumber).show();
    }

    updateStepBar(stepNumber);
};

var presentationProjectOptions = {};

//-------------------------------------------------------------------
function createCopilotContestDescription_PresentationProject() {
    var content  = "";

    // 1. Deliverable
    var deliverableType = $('.presentation .stepFirst2 input[name="presentation"]:checked').val();
    var startingPoint;
    var componentToDeliver;

    if (deliverableType == 'whole') {
        deliverableType = 'A whole Presentation';
        var input = $('.presentation .stepFirst2 .leftCnt input[name="whole"]:checked');
        if (input.val() == 'other') {
            startingPoint = '[other]: ' + $('.presentation .stepFirst2 .leftCnt textarea').val();
        } else {
            startingPoint = input.parent().text();
        }
    } else if (deliverableType == 'part') {
        deliverableType = 'Part of a Presentation';
        var input = $('.presentation .stepFirst2 .rightCnt input[name="part"]:checked');
        if (input.val() == 'other') {
            componentToDeliver = '[other]: ' + $('.presentation .stepFirst2 .rightCnt textarea').val();
        } else {
            componentToDeliver = input.parent().text();
        }
    }

    // 2. Basics
    var title = $('#newPresentationProjectStep2 div.left.first input').val();
    var summary = $('#newPresentationProjectStep2 div.left.first textarea').val();

    var format;
    var input = $('#newPresentationProjectStep2 div.left.center input[name="format"]:checked');
    if (input.val() == 'other') {
        format = $('#newPresentationProjectStep2 div.left.center input[name="formatOther"]').val();
    } else {
        format = input.parent().text();
    }

    var visuals;
    input = $('#newPresentationProjectStep2 div.last input[name="visuals"]:checked');
    if (input.val() == 'other') {
        visuals = $('#newPresentationProjectStep2 div.last input[name="visualsOther"]').val();
    } else {
        visuals = input.parent().text();
    }

    // 3. Strategy
    var audience = "";
    $('#newPresentationProjectStep3 .left').eq(0).find('input[name="audience"]:checked').each(function(i) {
        if (i > 0) {
            audience += ', ';
        }
        if ($(this).val() == 'other') {
            audience += $(this).parent().next().find('input').val();
        } else {
            audience += $(this).parent().text();
        }
    });
    
    var purpose = "";
    $('#newPresentationProjectStep3 .left').eq(1).find('input[name="purpose"]:checked').each(function(i) {
        if (i > 0) {
            purpose += ', ';
        }
        if ($(this).val() == 'other') {
            purpose += $(this).parent().next().find('input').val();
        } else {
            purpose += $(this).parent().text();
        }
    });
    
    var style = "";
    $('#newPresentationProjectStep3 .left').eq(2).find('input[name="style"]:checked').each(function(i) {
        if (i > 0) {
            style += ', ';
        }
        if ($(this).val() == 'other') {
            style += $(this).parent().next().find('input').val();
        } else {
            style += $(this).parent().text();
        }
    });
 
    content += '<p><strong>Deliverable type:</strong><br/>' + deliverableType + '</p>';
    if (startingPoint != undefined)  {
        content += '<p><strong>Starting point:</strong><br/>' + startingPoint + '</p>';
    }
    if (componentToDeliver != undefined) {
        content += '<p><strong>Component to deliver:</strong><br/>' + componentToDeliver + '</p>';
    }
    content += '<p><strong>Title:</strong><br/>' + title + '</p>';
    content += '<p><strong>Summary:</strong><br/>' + summary + '</p>';
    content += '<p><strong>Format:</strong><br/>' + format + '</p>';
    content += '<p><strong>Visuals:</strong><br/>' + visuals + '</p>';
    content += '<p><strong>Audience:</strong><br/>' + audience + '</p>';
    content += '<p><strong>Purpose:</strong><br/>' + purpose + '</p>';
    content += '<p><strong>Style:</strong><br/>' + style + '</p>';
    
    var additionalRequirements = "";
    $(".stepFourth2 table.addedItem tbody").find("tr a.additionalRequirement").each(function() {
        var info = $(this).attr('rel');
        additionalRequirements += '<p style="width:600px;word-wrap: break-word;">' + info + '</p>';        
    });
    if (additionalRequirements != "") {
        content += '<p><strong>Additional requirements:</strong><br/></p>';
        content += additionalRequirements;
    }

    presentationProjectOptions.deliverableType = deliverableType;
    presentationProjectOptions.startingPoint = startingPoint;
    presentationProjectOptions.componentToDeliver = componentToDeliver;
    presentationProjectOptions.title = title;
    presentationProjectOptions.summary = summary;
    presentationProjectOptions.format = format;
    presentationProjectOptions.visuals = visuals;
    presentationProjectOptions.audience = audience;
    presentationProjectOptions.purpose = purpose;
    presentationProjectOptions.style = style;

    return content;
}

//-------------------------------------------------------------------
function updatePresentationProjectSummaryPage() {
    createCopilotContestDescription_PresentationProject();

    // 1. Deliverable
    var v = $('#newPresentationProjectStep5 .content').eq(0);
    v.find('div.row').eq(0).find('.left').eq(1).html(presentationProjectOptions.deliverableType);

    var v2 = v.find('div.row').eq(1);
    if (presentationProjectOptions.startingPoint != undefined) {
        v2.find('.left').eq(1).html(presentationProjectOptions.startingPoint);
        v2.show();
    } else {
        v2.hide();
    }

    v2 = v.find('div.row').eq(2);
    if (presentationProjectOptions.componentToDeliver != undefined) {
        v2.find('.left').eq(1).html(presentationProjectOptions.componentToDeliver);
        v2.show();
    } else {
        v2.hide();
    }

    // 2. Basics
    v = $('#newPresentationProjectStep5 .content').eq(1);
    v.find('div.row').eq(0).find('.left').eq(1).html(presentationProjectOptions.title);
    v.find('div.row').eq(1).find('.left').eq(1).html(presentationProjectOptions.summary);
    v.find('div.row').eq(2).find('.left').eq(1).html(presentationProjectOptions.format);
    v.find('div.row').eq(3).find('.left').eq(1).html(presentationProjectOptions.visuals);

    // 3. Strategy
    v = $('#newPresentationProjectStep5 .content').eq(2);
    v.find('div.row').eq(0).find('.left').eq(1).html(presentationProjectOptions.audience);
    v.find('div.row').eq(1).find('.left').eq(1).html(presentationProjectOptions.purpose);
    v.find('div.row').eq(2).find('.left').eq(1).html(presentationProjectOptions.style);

    // 4. Detailed specifications. Right now use detailed requirements directly from step4.
    $('.presentation .stepFifth2 tbody').empty().append($('.stepFourth2 table.addedItem tbody').html());
    $('.presentation .stepFifth2 tbody').find('tr').each(function(i) {
        if (i > 0) {
            $(this).find('td').eq(3).remove();
        }
    });
}

function populateProjectAnswersForPresentation() {
	// the result array.
	result = [];
	// prepare the project questions.
	presentationProjectQuestions = prepareProjectQuestions(PROJECT_TYPE_PRESENTATION);
	// define question index.
	var i = 0;
	populateFirstPresentationQuestion(presentationProjectQuestions[i++], result);
	
	for(var j = 0; j< 4; j++) {
		populateProjectAnswerFromProjectQuestion(presentationProjectQuestions[i++], result);
	}
	// get strategy answer
	for(var j = 0; j < 3; j++) {
		populateStrategyAnswers(presentationProjectQuestions[i++], result);
	}
	// get detailed specifications
	getDetailedSpecifications(presentationProjectQuestions[i++], result);
	return result;
}

function populateStrategyAnswers(question, result) {
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;
	
	$.each(question.questionOptions, function(index, content) {
		answerOption = {};
		answerOption.projectQuestionOption = {};
		answerOption.projectQuestionOption.id = content.id;
		// find the checked option(radio or checkbox)
		answerNode = $("#" + content.answerHtmlId);
		if(answerNode.is(':checked')) {
			answerOption.answerHtmlValue = answerNode.val();
			answer.optionAnswers.push(answerOption);
		}
	});
	answer.multipleAnswers = getMultipleAnswers(question.multipleAnswersHtmlXpath);
	result.push(answer);
	return answer;
}

function populateFirstPresentationQuestion(question, result) {
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;

	// question has multiple options
	node = $('#' + question.questionOptions[0].answerHtmlId);
	if(node.is(':checked')) {
		for(var i = 0; i < 5; i++) {
			var option = question.questionOptions[i];
			answerOption = {};
			answerOption.projectQuestionOption = {};
			answerOption.projectQuestionOption.id = option.id;
			// find the checked option(radio or checkbox)
			answerNode = $("#" + option.answerHtmlId);
			if(answerNode.is(':checked')) {
				if(option.hasAssociatedTextbox) {
					answerOption.answerHtmlValue = $("#" + option.associatedTextboxHtmlId).val();
				}else {
					answerOption.answerHtmlValue = answerNode.val();
				}
				answer.optionAnswers.push(answerOption);
			}
		}
	} else {
		for(var i = 5; i < 10; i++) {
			var option = question.questionOptions[i];
			answerOption = {};
			answerOption.projectQuestionOption = {};
			answerOption.projectQuestionOption.id = option.id;
			// find the checked option(radio or checkbox)
			answerNode = $("#" + option.answerHtmlId);
			if(answerNode.is(':checked')) {
				if(option.hasAssociatedTextbox) {
					answerOption.answerHtmlValue = $("#" + option.associatedTextboxHtmlId).val();
				}else {
					answerOption.answerHtmlValue = answerNode.val();
				}
				answer.optionAnswers.push(answerOption);
			}
		}
	}
	result.push(answer);
	return answer;
}