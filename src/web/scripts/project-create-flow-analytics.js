/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript code related to analytics project creation flow.
 *
 * @author: minhu
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
 */

function initAnalyticsProjectFlow() {    
    initAnalyticsStep1();
    initAnalyticsStep3();
    initAnalyticsStep4();
    initAnalyticsStep5();
    initAnalyticsStep6();
    initAnalyticsStep7();
}

//-----------------------------------------------
// Step 1
//-----------------------------------------------
function initAnalyticsStep1() {
    /* slider */
    if($.fn.slider){
        $(".step2 .eBudget .slider").slider({
            range: "max",
            min: 0,
            max: 200,
            value: 0,
            step: 2,
            slide: function( event, ui ) { 
                $(".step2 .eBudget .slider").slider("widget").parent().find(".val input.text").val(ui.value);
                $(".step2 .eBudget .val").removeClass("error");
            }
        });
        $(".step2 .eTimeline .slider").slider({
            range: "max",
            min: 0,
            max: 400,
            value: 0,
            step: 5,
            slide: function( event, ui ) {
                $(".step2 .eTimeline .slider").slider("widget").parent().find(".val input.text").val(ui.value);
                $(".step2 .eTimeline .val").removeClass("error");
            }
        });
    }        
    $(".step2 .eBudget .val .text").change(function(){
        var val = parseInt($(this).val());
        if (!val || val<0)val=0;
        else if(val>200)val=200;
        $(this).val(val);        
        $(".step2 .eBudget .slider").slider("value",val); 
    });
    $(".step2 .eTimeline .val .text").change(function(){
        var val = parseInt($(this).val());
        if (!val || val<0)val=0;
        else if(val>400)val=400;
        $(this).val(val);        
        $(".step2 .eTimeline .slider").slider("value",val); 
    });    
    
    $(".step2 .val .text").focus(function(){
        $(this).parent().removeClass("error");
    })
    
    var days = $(".analyticsSteps .eTimeline .sliderCtl .val input");
    if($.fn.datePicker){
        var from = $(".step2 .eTimeline .dp input.from");
        var to = $(".step2 .eTimeline .dp input.to");
        var fromPicker = from.datePicker({startDate:'01/01/2001'});
        var toPicker = to.datePicker({startDate:'01/01/2001'});
        from.bind('dpClosed',function(e, selectedDates){
            var d1 = selectedDates[0];
            var d2 = to.dpGetSelected()[0]
            if(to.val() != ""){
                var d = d2.getTime() - d1.getTime();
                if (d<0) {
                    d=0;
                    fromPicker.val(d2.asString()).trigger('change');
                } else {
                    var d=d/(24*3600*1000);
                    if (d>400){
                        d=400;
                        var td=d2.addDays(-d);
                        fromPicker.val(td.asString()).trigger('change');
                    }
                }
                days.val(d);
            }
        });
        to.bind('dpClosed',function(e, selectedDates){
            var d1 = from.dpGetSelected()[0]    
            var d2 = selectedDates[0];
            var d = d2.getTime() - d1.getTime();
            days.val(0);
            if (d<0){
                d=0;
                toPicker.val(d1.asString()).trigger('change');
            }else{
                var d=d/(24*3600*1000);
                if (d>400){
                    d=400;
                    var td=d1.addDays(d);
                    toPicker.val(td.asString()).trigger('change');
                }
            }
            days.val(d);
        });
        $(".step2 .eTimeline input[name='eTimeline']").change(function(){ 
            $(".analyticsSteps .eTimeline .disableMask").remove();
            if($(this).val() == "dp"){
                $(".analyticsSteps .eTimeline .sliderCtl").append($("<div/>",{
                    "class":"disableMask"
                }));
                $(".step2 .eTimeline .val .text").attr("readonly","readonly");
                fromPicker.val(new Date().asString()).trigger('change');
                if(days.val() != "" && days.val() != 0){
                    toPicker.val((new Date()).addDays(days.val()).asString()).trigger('change');   
                }
            }else{
                $(".analyticsSteps .eTimeline .dp").append($("<div/>",{
                    "class":"disableMask"
                }));
                $(".step2 .eTimeline .val .text").removeAttr("readonly");
                if(days.val() != ""){
                    $(".step2 .eTimeline .slider").slider("value",days.val());           
                }
            }
        })       
    }

    /* validate step2  */
    $(".step2 .nextStepButton").click(function(){
        var valid = true;
        var budget = $(".eBudget .sliderCtl .val input").val();
        var timeline = $(".eTimeline .sliderCtl .val input").val();
        
        if(budget == "0" || budget == ""){
            $(".eBudget .sliderCtl .val").addClass("error");
            valid = false;
        }
        if(timeline == "0" || timeline == ""){
            $(".eTimeline .sliderCtl .val").addClass("error");
            valid = false;
        }
        if (valid)goAnalyticsProjectStep(2);
        return false;
    })

    $(".eTimeline input[type='radio']").change(function(){
        $(".eTimeline .error").removeClass("error");
    })    
}

//-----------------------------------------------
// Step 3
//-----------------------------------------------
function initAnalyticsStep3() {
    /* validate */
    $(".step4Container .nextStepButton").click(function(){
        if($.trim($(".step4 .row textarea").val()) == ""){
           $(".step4 .row").addClass("error");
            return false;
        }
        goAnalyticsProjectStep(4);
        return false;
    })
    $(".step4 .row textarea").keyup(function(){
        $(this).parent().removeClass("error");
    })

    $(".step4 input[name='metrics']").change(function(){
        if($(this).val() == "yes"){
            $(this).parents("td").find(".yesWrapper").show();
        }else{
            $(this).parents("td").find(".yesWrapper").hide();
        }
    })
    $(".step4 input[name='knowledge']").change(function(){
        if($(this).val() == "yes"){
            $(this).parents("td").find(".addKnowledge").show();
            $(this).parents("td").find(".currentSelect").show();
        }else{
            $(this).parents("td").find(".addKnowledge").hide();
            $(this).parents("td").find(".currentSelect").hide();
        }
    })
    $(".step4 .addKnowledge .field input.text").focus(function(){
        $(this).parent().removeClass("error");
    })
    $(".step4 .addKnowledge .field a.smallRedBtn").click(function(){
        var field = $(this).parent();
        if($.trim(field.find("input.text").val())==""){
            $(this).parent().addClass("error");
        }else{
            // check duplicate
            var ok=true;
            $(".step4 .currentSelect p .btnText").each(function(){
                if ($.trim($(this).text()) == $.trim(field.find("input.text").val())) {
                    ok=false;
                    return false;
                }
            });
            if (!ok) {
                showErrors("The field was already added.");
                return false;   
            }
            
            var btn = $('<span class="fieldBtn"><span class="btnMask"><span class="btnText"><a href="javascript:;" class="rm"></a></span></span></span>');
            btn.find(".btnText").append($("<i/>",{
                "text": $.trim(field.find("input.text").val())
            }));
            btn.appendTo($(".step4 .currentSelect p")); 
            field.find("input.text").val("");
            $(".step4 .addKnowledge span.remaning").html('<span class="num">30</span> characters remaining.');
        }
        return false;
    })
    $(".fieldBtn .btnText a.rm").live("click",function(){
        var row = $(this).parents(".row1")
        $(this).parents(".fieldBtn").remove(); 
        return false;   
    })  
    
    $(".step4 .addKnowledge input.text").limitedText({
        "max" : 30
    });
}

//-----------------------------------------------
// Step 4
//-----------------------------------------------
function initAnalyticsStep4() {    
    /* slider */
    if($.fn.slider){            
        $(".step5 .q1 .slider").slider({
            range: "max",
            min: 0,
            max: 4094,
            value: 0,
            step: 1,
            slide: function( event, ui ) {
                $(this).parent().find(".val input.text").val(ui.value);
                $(".step5 .q1 .val").removeClass("error");
            }
        });
        $(".step5 .q2 .slider").slider({
            range: "max",
            min: 0,
            max: 4094,
            value: 0,
            step: 1,
            slide: function( event, ui ) {
                $(this).parent().find(".val input.text").val(ui.value);
                $(".step5 .q2 .val").removeClass("error");
            }
        });
    }   
    $(".step5 .val .text").change(function(){
        var val = parseInt($(this).val());
        if (!val || val<0)val=0;
        else if(val>4094)val=4094;
        $(this).val(val);        
        $(this).parent().parent().find(".slider").slider("value",val); 
    });
    $(".step5 .textR .text").change(function(){
        var val = parseFloat($(this).val());
        if (!val || val<0)val=0;
        $(this).val(val);    
    });
    
    $(".step5 .val .text, .step5 .textR .text").focus(function(){
        $(this).parent().removeClass("error");
    })
    
    $(".step5 .row .left .radios input").change(function(){
        var row = $(this).parents(".row")
        row.find(".yesCondition,.noCondition,.unknowCondition").hide();
        row.find("." + $(this).val() + "Condition").show(); 
    })
    $(".step5Container .nextStepButton").click(function(){
        var valid=true;    
        $(".sliderCtl .val input").each(function(){
            if($(this).is(":visible") && $(this).val() == "0"){
                $(this).parent().addClass("error");
                valid=false;
            }
        })
        
        // execution time  
        $(".q3 .textR input").each(function(){
            if($(this).is(":visible") && $(this).val() == "0"){
                $(this).parent().addClass("error");
                valid=false;
            }
        })      
        
        if (valid)goAnalyticsProjectStep(5);
        return false;
    })
}

//-----------------------------------------------
// Step 5
//-----------------------------------------------
function initAnalyticsStep5() {
    $(".step6Container .nextStepButton").click(function(){
        
        if($.trim($(".step6 .otherDetails textarea").val()) == ""){
            $(".step6 .otherDetails").addClass("error");
            return false;
        }
        goAnalyticsProjectStep(6);
        return false;
    })
    
    $('.step6 .radio').attr('checked','');
    $('.step6 .isDirect textarea').css({resize:"none"});
    $(".step6 .radios input[name=document]:first").attr("checked","checked");    
    $('.step6 .browserWrapper .file').css({opacity:'0'});

    $(".step6 .otherDetails textarea").keyup(function(){
        $(this).parent().removeClass("error");
    })    
    $(".step6 .documents .text").keyup(function(){
        $(this).parents(".documents").removeClass("error");
    })
    
    $(".step6 .documents .radios input").change(function(){
        var docs = $(this).parents(".documents").removeClass("error");
        docs.find(".inputRow").hide();
        docs.find(("." + $(this).val())).show();
        
    })    
    
    //hover status for browse button
    $('.step6 .browserWrapper').hover(
        function() {
            $(this).find('.btn').addClass('hover');
        },
        function() {
            $(this).find('.btn').removeClass('hover');
        }
    );
    $('.step6 .action .addBtn').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
        }
    );
    
    //file upload function
    $('.step6 .textUploadPhoto, .step6 .browserWrapper .btn').click(function() {
        $('.step6 .browserWrapper .file').trigger('click');
    });
    $('.step6 .browserWrapper .file').change(function() {
        var fileName = getFileName($('.step6 .browserWrapper .file').val());
        $('.step6 .textUploadPhoto').val(fileName);
    }); 
}

//-----------------------------------------------
// Step 6
//-----------------------------------------------
function initAnalyticsStep6() {
    $('#newAnalyticsProjectStep6 .stepFifth .geryContent .nextStepButton').click(function() {
        var hasUserPermissionAdded = ($('#newAnalyticsProjectStep6 .stepFifth .checkPermissions .userRow').length>0);        
        if ($('.addUserPlan').is(":visible")) {
            if ((!hasUserPermissionAdded)) {
                addresscloseModal();
                addressLoadModal('#alertModal');
            } else {
                goAnalyticsProjectStep(7);
            }
        } else {
            addresscloseModal();
            addressLoadModal('#alertModal');
        }
    });
}

//-----------------------------------------------
// Step 7
//-----------------------------------------------
function initAnalyticsStep7() {
    $(".launchBtn").click(function(){
        $(".proceedMsg").show();
        $(this).hide(); 
        return false;
   });
    $(".proceedMsg a.no").click(function(){
        $(".proceedMsg").hide();
        $(".launchBtn").show();
        return false;
    })
    $(".proceedMsg a.yes").click(function(){
        createNewProject();
        return false;
    })
}

// go to step
var goAnalyticsProjectStep = function(stepNumber) {
    if (stepNumber < 0 || stepNumber > 8) {
        return;
    }
    if (stepNumber == 0) {
        activeProjectType = PROJECT_TYPE_CUSTOM;
        initStepBar('stepBarCustom');
        goCreateProjectStep(1);
        return;
    }
    
    $('.newProjectStep').hide();    
    updateStepBar(stepNumber);
    if (stepNumber == 8) {
        $('#newAnalyticsProjectStepConfirmation').show();
        return;
    }
    
    $('#newAnalyticsProjectStep' + stepNumber).show();
    if (stepNumber == 7) {
        updateAnalyticsProjectSummaryPage();
    }

    if (stepNumber == 2) {
        if(stepsChoices['require-copilot-step'] == 'chooseCopilot') {
            $("#newAnalyticsProjectStep2 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['require-copilot-step'] == 'createCopilot') {
            $("#newAnalyticsProjectStep2 .radioYes").attr("checked", "checked");
        } else if (stepsChoices['require-copilot-step'] == 'noCopilot') {
            $("#newAnalyticsProjectStep2 .radioNo").attr("checked", "checked");
        }
    }
};

// update the analytics summary page
function updateAnalyticsProjectSummaryPage() {
    // first page
    $('#reviewProjectName').html($('#newProjectName').val());
    $('#reviewProjectDesc').html($('#newProjectDescription').val());

    // step1
    $('#reviewBudget').html($('div.step2 .eBudget .val input').val() + " K US Dollar");
    $('#reviewTimeline').html($('div.step2 .eTimeline .val input').val() + " Days");

    // step2      
    $('#reviewCopilotsDiv').hide();
    $('#reviewCreateCopilotDiv').hide();
    if (stepsChoices['require-copilot-step'] == 'noCopilot') {
        $('#reviewCopilotYesNo').html("No");
    } else if(stepsChoices['require-copilot-step'] == 'createCopilot') {
        $('#reviewCopilotYesNo').html("Yes");
        $('#reviewCreateCopilotDiv').show();
    } else if(stepsChoices['require-copilot-step'] == 'chooseCopilot') {
        $('#reviewCopilotYesNo').html("Yes");
        $('#reviewCopilotsDiv').show();        
        var copilotsData = stepsChoices['copilots'];  
        var userIdsMap = widgetResult.userIdsMap;
        var copilotsList = "";
        var first = true;
        $.each(copilotsData, function(index, value){
            if (!first) copilotsList = copilotsList + ", "
            copilotsList = copilotsList + ('<span><a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr='
                + userIdsMap[value.copilotProfileId] + '">'+value.copilotHandle+'</a></span>');
            first = false;
        });
        $('#reviewCopilots').html(copilotsList);
    }
    
    // step3
    $('#reviewOverview').html($('.step4 .row textarea').val());
    var metrics = "N/A";
    if ($('.step4 table tr:eq(0) input[type="radio"]:checked').next().text()=="Yes"){
        metrics = $('.step4 table .yesWrapper textarea').val();
    }    
    $('#reviewMetrics').html(metrics);
    var fields = "N/A";
    if ($('.step4 table tr:eq(1) input[type="radio"]:checked').next().text()=="Yes"){
        fields = "";
        var first = true;
        $('.step4 .currentSelect .fieldBtn .btnText').each(function() {
            if (!first)fields = fields + ", " ;
            fields = fields + $(this).find("i").text();
            first = false;        
        });
    }
    $('#reviewFields').html(fields);
    
    // step4
    var testDataYesNo = $(".step5 .q1 input[type='radio']:checked").next().text();
    $('#reviewTestDataYesNo').html(testDataYesNo);
    $('#reviewTestDataSizeDiv').hide();
    $('#reviewTestDataDifficultDiv').hide();   
    if (testDataYesNo == 'Yes') {
        $('#reviewTestDataSizeDiv').show();
        $('#reviewTestDataSize').html($(".step5 .q1 .right .yesCondition .val input").val() + " MB");
    } else if (testDataYesNo == 'No') {
        $('#reviewTestDataDifficultDiv').show();
        $('#reviewTestDataDifficult').html($(".step5 .q1 .right .noCondition textarea").val());
    }
    
    var memoryYesNo = $(".step5 .q2 input[type='radio']:checked").next().text();
    $('#reviewMemoryYesNo').html(memoryYesNo);
    $('#reviewMemoryMaxDiv').hide();
    $('#reviewMemoryProperDiv').hide();   
    if (memoryYesNo == 'Yes') {
        $('#reviewMemoryMaxDiv').show();
        $('#reviewMemoryMax').html($(".step5 .q2 .right .yesCondition .val input").val() + " MB");
    } else if (memoryYesNo == 'No') {
        $('#reviewMemoryProperDiv').show();
        $('#reviewMemoryProper').html($(".step5 .q2 .right .noCondition .val input").val() + " MB");
    }
    
    var speedYesNo = $(".step5 .q3 input[type='radio']:checked").next().text();
    $('#reviewSpeedYesNo').html(speedYesNo);
    $('#reviewSpeedMaxDiv').hide();
    $('#reviewSpeedReasonableDiv').hide();   
    if (speedYesNo == 'Yes') {
        $('#reviewSpeedMaxDiv').show();
        $('#reviewSpeedMax').html($(".step5 .q3 .right .yesCondition .textR input").val() + " Seconds");
    } else if (speedYesNo == 'No') {
        $('#reviewSpeedReasonableDiv').show();
        $('#reviewSpeedReasonable').html($(".step5 .q3 .right .noCondition .textR input").val() + " Seconds");
    }    
    
    $('#reviewSolution').html($(".step5 .q4 .right input[type='radio']:checked").next().text());
    var languages = "";
    var first = true;
    $(".step5 .q5 .right input[type='checkbox']:checked").each(function(){
        if (!first)languages = languages + ", ";
        languages = languages + $(this).next().text();
        first = false;  
    });
    $('#reviewLanguages').html(languages);

    $('#reviewIntegrate').html($(".step5 .q6 .right input[type='radio']:checked").next().text());
       
    var currentYesNo = $(".step5 .q7 input[type='radio']:checked").next().text();
    $('#reviewCurrentYesNo').html(currentYesNo);
    $('#reviewCurrentInfoDiv').hide();   
    if (currentYesNo == 'Yes') {
        $('#reviewCurrentInfoDiv').show();
        $('#reviewCurrentInfo').html($(".step5 .q7 .right .yesCondition .textR textarea").val());
    }
    
    // step5
    $('#reviewOtherDetails').html($(".step6 .otherDetails textarea").val());
    $('#reviewDocuments tbody').empty();
    $('.step6 table tr:not(:last-child)').each(function(){
        var tr = $(this);
        $('#reviewDocuments tbody').append("<tr><td>"+
            tr.find("td:eq(0)").html()+"</td><td>"+
            tr.find("td:eq(1)").html()+"</td></tr>");    
    }); 
    
    // step6
    $('#reviewUsers tbody').empty();
    $('#newAnalyticsProjectStep6 .userRow').each(function(){
        var tr = $(this);
        var input1='<input class="selectUser" type="checkbox" disabled="disabled"/>';
        var input2='<input class="selectUser" type="checkbox" checked="checked" disabled="disabled"/>';
        $('#reviewUsers tbody').append("<tr><td><strong>"+
            tr.find("td:eq(0) div.group").html()+"</strong></td><td>"+
            (tr.find("td:eq(1) input:checked").length>0 ?input2:input1)+"</td><td>"+
            (tr.find("td:eq(2) input:checked").length>0 ?input2:input1)+"</td><td>"+
            (tr.find("td:eq(3) input:checked").length>0 ?input2:input1)+"</td></tr>");    
    });
}

// detail spec
function createCopilotContestDescription_AnalyticsProject() {
    var content = "";
    // first page
    content += '<p><strong>Project Name:</strong><br/>' + $('#reviewProjectName').html() + '</p>';
    content += '<p style="width:600px;word-wrap:break-word;"><strong>Project Description:</strong><br/>' +
        $('#reviewProjectDesc').html() + '</p>';
    content += '<br/>';    
    
    // step1
    content += '<p><strong>Budget:</strong><br/>' + $('#reviewBudget').html() + '</p>';
    content += '<p><strong>Timeline:</strong><br/>' + $('#reviewTimeline').html() + '</p>';
    content += '<br/>';  
    
    // step3 & step4 
    content += '<p style="width:600px;word-wrap:break-word;"><strong>A high level overview:</strong><br/>' +
                $('#reviewOverview').html() + '</p>';
    content += '<p style="width:600px;word-wrap:break-word;"><strong>The evaluation metrics of a solution:</strong><br/>' +
                $('#reviewMetrics').html() + '</p>';
    content += '<p style="width:600px;word-wrap:break-word;"><strong>The related fields of knowledge:</strong><br/>' +
                $('#reviewFields').html() + '</p>';
    
    // test data info 
    var testData = $('#reviewTestDataYesNo').html();
    if (testData == "Yes") {
        testData = "The test data is available with the expected size of " + $('#reviewTestDataSize').html() + " in total.";
    } else if (testData == "No") {
        testData = "It is difficult to get a proper test data. " + $('#reviewTestDataDifficult').html();
    } else {
        testData = "Please contact Topcoder PM for information.";   
    }    
    content += '<p><strong>Test data information:</strong><br/>' + testData + '</p>';
    
    // memory info 
    var memory = $('#reviewMemoryYesNo').html();
    if (memory == "Yes") {
        memory = "The maximum amount of memory that would be acceptable is " +
            $('#reviewMemoryMax').html() + ".";
    } else if (memory == "No") {
        memory = "The necessary amount of memory in order to obtain proper results is " +
            $('#reviewMemoryProper').html() + ".";
    } else {
        memory = "Please contact Topcoder PM for information.";   
    }    
    content += '<p><strong>Memory requirement:</strong><br/>' + memory + '</p>';
    
    // speed info 
    var speed = $('#reviewSpeedYesNo').html();
    if (speed == "Yes") {
        speed = "The maximum execution time that would be acceptable (assuming a single threaded solution and " +
            "average modern PC) is " + $('#reviewSpeedMax').html() + ".";
    } else if (speed == "No") {
        speed = "The execution time that is allowed for a solution in order to obtain reasonable results is " +
            $('#reviewSpeedReasonable').html() + ".";
    } else {
        speed = "Please contact Topcoder PM for information.";   
    }    
    content += '<p><strong>Runtime speed requirement:</strong><br/>' + speed + '</p>';
    
    content += '<p><strong>The appropriate thread model:</strong><br/>' + $('#reviewSolution').html() + '</p>';
    content += '<p><strong>The programming languages allowed:</strong><br/>' + $('#reviewLanguages').html() + '</p>';
    content += '<p><strong>Integration of winning solution:</strong><br/>' + 
            ($('#reviewIntegrate').html().substring(0, 1) =="I" ? "Done by client." : 
            "Done by TopCoder through a separate contest or a series of contests.") + '</p>';
    content += '<p><strong>Current solution information:</strong><br/>' + 
            ($('#reviewCurrentYesNo').html() == "Yes" ? $('#reviewCurrentInfo').html() : "Not available") + '</p>';    
    content += '<br/>';    
    
    // step5   
    content += '<p style="width:600px;word-wrap:break-word;"><strong>Other details:</strong><br/>' +
            $('#reviewOtherDetails').html() + '</p>';
    if ($('#reviewDocuments tbody tr').length > 0) {
        content += '<p style="width:600px;word-wrap:break-word;"><strong>Available documents:</strong>';
        $('#reviewDocuments tbody tr').each(function() {
            var docType = $(this).find("td:eq(0)").html();
            content += "<br/><span>" + docType + ": </span>" +
                (docType == "Upload" ? $(this).find("td:eq(1)").text() : $(this).find("td:eq(1)").html()); 
        });
        content += '</p>'; 
    }
    content += '<br/>';
    return content;
}
