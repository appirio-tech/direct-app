/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * JavaScript code related to mobile project creation flow.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
 *
 * @version 1.1 (Release Assembly - TopCoder Cockpit Start New Project Data Persistence) change notes:
 * 				added populate project answer for mobile project.
 *              added initialize project question for mobile project.
 * 
 * @author: KennyAlive, Ghost_141
 * @version 1.1
 */

function initMobileProjectFlow() {
	initProjectQuestions(PROJECT_TYPE_MOBILE);
    initMobileStep1();
    initMobileStep2();
    initMobileStep3();
    initMobileStep4();
    initMobileStep5();

    $("input[name=app]").attr("disabled","disabled");
    $('input[name=platform]:first').attr("checked","checked");
    $(".mobile p .errorText").hide(); 
    $(".mobile input[name=org]:first").attr("checked","checked");
    $("input[name=factorSelect], input[name=orientation], input[name=resolution]").removeAttr("checked");
    $("input[name=factor], input[name=ori], input[name=res]").removeAttr("disabled");
}

var goMobileProjectStep = function(stepNumber) {
    if (stepNumber < 1 || stepNumber > 5) {
        return;
    }

    if (stepNumber == 5) {
        updateMobileProjectSummaryPage();
    }
    
    if (stepNumber == 4) { // step4 jsp is shared between mobile and presentation flow.
        $('.newProjectStep').hide();
        $('#newSharedProjectStep4').show();
    } else {
        $('.newProjectStep').hide();
        $('#newMobileProjectStep' + stepNumber).show();
    }

    updateStepBar(stepNumber);
};

//-----------------------------------------------
// Step 1
//-----------------------------------------------
function initMobileStep1() {
    $('.mobile .stepFirst2 .nextStepButton').unbind("click").click(function(){
        var valid =true;
        if($('input[name=platform]:checked').val()=="cross"){
            if($(".mobile input[name=cross]:checked").val()=="other" && $(".mobile input[name=crossOther]").val()=="other"){
                $(".mobile input[name=crossOther]").addClass("error2");
                $(".mobile input[name=crossOther]").siblings("p").find("span").show();
                $(".mobile input[name=crossOther]").siblings("span.errorIcon").show();
                valid = false;
            }else{
                $(".mobile input[name=crossOther]").removeClass("error2");
                $(".mobile input[name=crossOther]").siblings("p").find("span").hide();
                $(".mobile input[name=crossOther]").siblings("span.errorIcon").hide();
            }
        }
        else if($('input[name=platform]:checked').val()=="native"){
            if($("#otherNative").is(':checked') && $(".mobile input[name=nativeOther]").val()=="other"){
                $(".mobile input[name=nativeOther]").addClass("error2");
                $(".mobile input[name=nativeOther]").siblings("p").find("span").show();
                $(".mobile input[name=nativeOther]").siblings("span.errorIcon").show();
                valid = false;
            }else{
                $(".mobile input[name=nativeOther]").removeClass("error2");
                $(".mobile input[name=nativeOther]").siblings("p").find("span").hide();
                $(".mobile input[name=nativeOther]").siblings("span.errorIcon").hide();
            }
            if(!($("input[name=native]:checked").length>0)){
                $("input[name=native]").parent().parent().parent().find("p:first").find(".errorText").show();
                valid = false;
            }else{
                $("input[name=native]").parent().parent().parent().find("p:first").find(".errorText").hide();
            }
        }

        if (valid) {
            goMobileProjectStep(2);
        }
    });

    $("input[name=platform]").change(function(){
        if($('input[name=platform]:checked').val()=="cross"){
            $(".stepFirst2 .content .desc, .stepFirst2 .content .desc .crossOption").show();
            $(".stepFirst2 .content .desc .nativeOption").hide();
        }
        else if($('input[name=platform]:checked').val()=="native"){
            $(".stepFirst2 .content .desc, .stepFirst2 .content .desc .nativeOption").show();
            $(".stepFirst2 .content .desc .crossOption").hide();
        }
        else{
            $(".stepFirst2 .content .desc, .stepFirst2 .content .desc .nativeOption, .stepFirst2 .content .desc .crossOption").hide();
        }
    });
}

//-----------------------------------------------
// Step 2
//-----------------------------------------------
function initMobileStep2() {
    $('.mobile .stepSecond2 .nextStepButton').unbind("click").click(function() {
        $("input[name=factorSelect]").parent().parent().find(".errorText").hide();
        $("input[name=factorSelect]").parent().parent().find(".errorIcon").hide();

        var valid1 = true, valid2 = true;
        if(!$("input[name=factorSelect]").is(":checked")){
            if($("input[name=factor]:last").is(":checked")){
                if($("input[name=factorOther]").val()=="other"){
                    $("input[name=factorOther]").addClass("error2");
                    $("input[name=factorOther]").siblings(".errorIcon").show();
                    $("input[name=factorOther]").siblings("p").find(".errorText").show();
                    valid1 = false;
                }else{
                    $("input[name=factorOther]").removeClass("error2");
                    $("input[name=factorOther]").siblings(".errorIcon").hide();
                    $("input[name=factorOther]").siblings("p").find(".errorText").hide();
                }
            }else if($("input[name=factor]:checked").length==0){
                $("input[name=factorSelect]").parent().parent().find(".errorText:first").show();
                $("input[name=factorSelect]").parent().parent().find(".errorIcon:first").show();
                valid1 = false;
            }
        }

        if ($('.mobile .stepSecond2 input[name="org"]:checked').val() == "yes") {
            if ($('.mobile .stepSecond2 input[name="app"]:checked').val() == "other") {
                var val = $.trim($('.mobile .stepSecond2 input[name="appOther"]').val());
                if (val == "" || val == "other") {
                    valid2 = false;
                }
            }
        }
        setValidationStatus2($('.mobile .stepSecond2 input[name="appOther"]'), valid2);

        if (valid1 && valid2) {
            goMobileProjectStep(3);
        }
    });

    $(".mobile .stepSecond2 input[name=org]").change(function(){
        if($(".mobile .stepSecond2 input[name=org]:checked").val()=="no"){
            $(this).parent().parent().siblings("div.left").addClass("disable");
            $(this).parent().parent().siblings("div.left").find("input").attr("disabled","disabled");
        }else{
            $(this).parent().parent().siblings("div.left").removeClass("disable");
            $(this).parent().parent().siblings("div.left").find("input").removeAttr("disabled");
        }
    });

    $("input[name=factorSelect]").click(function(){
        if($(this).is(":checked")){
            $("input[name=factor]").attr("disabled","disabled");
        }else{
            $("input[name=factor]").removeAttr("disabled");
        }
    });

    $("input[name=orientation]").click(function(){
        if($(this).is(":checked")){
            $("input[name=ori]").attr("disabled","disabled");
        }else{
            $("input[name=ori]").removeAttr("disabled");
        }
    });

    $("input[name=resolution]").click(function(){
        if($(this).is(":checked")){
            $("input[name=res]").attr("disabled","disabled");
        }else{
            $("input[name=res]").removeAttr("disabled");
        }
    });
}

//-----------------------------------------------
// Step 3
//-----------------------------------------------
function initMobileStep3() {
    $("input[name=motionSel], input[name=geoSel], input[name=cameraSel], input[name=aditionalSel]").removeAttr("checked");
    $("input[name=motion], input[name=geo], input[name=camera], input[name=aditional]").removeAttr("disabled");

    $('.mobile .stepThird2 .nextStepButton').unbind("click").click(function(){
        var valid=true;

        $(".errorText").hide();
        $(".errorIcon").hide();

        if(!$("input[name=motionSel]").is(":checked")){
            if(!($(".stepThird2 input[name=motion]:checked").length>0)){
                $("input[name=motionSel]").parent().parent().find(".errorIcon:first").show();
                $("input[name=motionSel]").parent().parent().find(".errorText:first").show();
                valid =false;
            }else{
                $("input[name=motionSel]").parent().parent().find(".errorIcon:first").hide();
                $("input[name=motionSel]").parent().parent().find(".errorText:first").hide();
            }
            if($(".stepThird2 input[name=motion].other").is(":checked")){
                if($("input[name=motionOtherText]").val()=="other"){
                    $("input[name=motionOtherText]").next().show();
                    $("input[name=motionOtherText]").parent().next().next().find(".errorText").show();
                    valid = false;
                }else{
                    $("input[name=motionOtherText]").next().hide();
                    $("input[name=motionOtherText]").parent().next().next().find(".errorText").hide();
                }
            }
        }else{
            $("input[name=motionSel]").parent().parent().find(".errorIcon").hide();
            $("input[name=motionSel]").parent().parent().find(".errorText").hide();
        }

        if(!$("input[name=geoSel]").is(":checked")){
            if(!($(".stepThird2 input[name=geo]:checked").length>0)){
                $("input[name=geo]").parent().parent().find(".errorIcon:first").show();
                $("input[name=geo]").parent().parent().find(".errorText:first").show();
                valid =false;
            }else{
                $("input[name=geo]").parent().parent().find(".errorIcon:first").hide();
                $("input[name=geo]").parent().parent().find(".errorText:first").hide();
            }
        }else{
            $("input[name=geo]").parent().parent().find(".errorIcon").hide();
            $("input[name=geo]").parent().parent().find(".errorText").hide();
        }

        if(!$("input[name=cameraSel]").is(":checked")){
            if(!($(".stepThird2 input[name=camera]:checked").length>0)){
                $("input[name=camera]").parent().parent().find(".errorIcon:first").show();
                $("input[name=camera]").parent().parent().find(".errorText:first").show();
                valid =false;
            }else{
                $("input[name=camera]").parent().parent().find(".errorIcon:first").hide();
                $("input[name=camera]").parent().parent().find(".errorText:first").hide();
            }
            if($(".stepThird2 input[name=camera].other").is(":checked")){
                if($("input[name=cameraOtherText]").val()=="other"){
                    $("input[name=cameraOtherText]").next().show();
                    $("input[name=cameraOtherText]").parent().next().next().find(".errorText").show();
                    valid = false;
                }else{
                    $("input[name=cameraOtherText]").next().hide();
                    $("input[name=cameraOtherText]").parent().next().next().find(".errorText").hide();
                }
            }
        }else{
            $("input[name=camera]").parent().parent().find(".errorIcon").hide();
            $("input[name=camera]").parent().parent().find(".errorText").hide();
        }

        if(!$("input[name=aditionalSel]").is(":checked")){
            if(!($(".stepThird2 input[name=aditional]:checked").length>0)){
                $("input[name=aditional]").parent().parent().find(".errorIcon:first").show();
                $("input[name=aditional]").parent().parent().find(".errorText:first").show();
                valid =false;
            }else{
                $("input[name=aditional]").parent().parent().find(".errorIcon:first").hide();
                $("input[name=aditional]").parent().parent().find(".errorText:first").hide();
            }

            $(".stepThird2 input[name=aditional].other").each(function() {
                if ($(this).is(":checked")) {
                    var span = $(this).parent().next();
                    var val = $.trim(span.find("input[name=aditionalOtherText]").val());
                    if (val == "" || val == "other") {
                        span.find("input[name=aditionalOtherText]").addClass("error2");
                        span.find("span.errorIcon").show();
                        span.next().next("p.message").find(".errorText").show();
                        valid = false;
                    } else {
                        span.find("input[name=aditionalOtherText]").removeClass("error2");
                        span.find("span.errorIcon").hide();
                        span.next().next("p.message").find(".errorText").hide();
                    }
                }
            });
        }else{
            $("input[name=aditional]").parent().parent().find(".errorIcon").hide();
            $("input[name=aditional]").parent().parent().find(".errorText").hide();
        }

        if (valid) {
            goMobileProjectStep(4);
        }
    });

    $("input[name=motionSel]").click(function(){
        if($(this).is(":checked")){
            $("input[name=motion]").attr("disabled","disabled");
        }else{
            $("input[name=motion]").removeAttr("disabled");
        }
    });
    $("input[name=geoSel]").click(function(){
        if($(this).is(":checked")){
            $("input[name=geo]").attr("disabled","disabled");
        }else{
            $("input[name=geo]").removeAttr("disabled");
        }
    });
    $("input[name=cameraSel]").click(function(){
        if($(this).is(":checked")){
            $("input[name=camera]").attr("disabled","disabled");
        }else{
            $("input[name=camera]").removeAttr("disabled");
        }
    });
    $("input[name=aditionalSel]").click(function(){
        if($(this).is(":checked")){
            $("input[name=aditional]").attr("disabled","disabled");
        }else{
            $("input[name=aditional]").removeAttr("disabled");
        }
    });

    $(".mobile .stepThird2 a.addField").click(function(){
        if($(".mobile input[name=aditionalSel]").is(":checked")){
            return false;
        }
        var html ="<label class='last'><input class='other' value='other' type='checkbox' name='aditional'/><span>&nbsp;</span></label>" +
            "<span class='input'><input disabled='disabled' name='aditionalOtherText' class='waterMark' title='other' type='text' value='other'/>" +
            "<span class='errorIcon'></span></span>" +
            "<div class='clear'></div>" +
            "<p class='message padding'><span class='errorText'>This field cannot be left empty.</span></p>";

        var elem = $(html);
        $(elem).find(".mobile input[type=checkbox]").attr("name",$(this).attr("id"));
        $(elem).find(".mobile input[type=text]").addClass($(this).attr("id"));
        $(this).parent().siblings("label:last").before(elem);
        $(this).parent().parent().find(".errorText").hide();
        $(this).parent().parent().find(".errorIcon").hide();
        $(this).parent().parent().find(".input").removeClass("error2");
    });

    $(".mobile .stepThird2 input[type=checkbox]").live("change",function(){
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
function initMobileStep4() {
    $('.stepSecond2 .radio,.stepFourth2 .radio').attr('checked','');
    $('.stepFourth2 .top .row.source textarea').css({resize:"none"});
    $(".stepFourth2 .top .row input.disable").attr("disabled","disabled");
    $(".stepFourth2 .top .row label input[name=source]:first").attr("checked","checked");
    $(".stepFourth2 .top .row label input[name=type]:first").attr("checked","checked");
    $('.stepFourth2 .browserWrapper .file').css({opacity:'0'});
    
    $(".stepFourth2 .top .row label input[name=type]").change(function(){
        if(($(this).val()=="other")){
            $(this).parent().parent().find("input.text").removeAttr("disabled");
        }else{
            $(this).parent().parent().find("input.text").val($(this).parent().parent().find("input.text").attr("title"));
            $(this).parent().parent().find("input.text").attr("disabled","disabled");
        }
        $("input[name=other]").removeClass("error2");
        $("input[name=other]").next().find(".errorText").hide();
        $("input[name=other]").parent().find(".errorIcon").hide();
    });
    
    $(".stepFourth2 .top .row label input[name=source]").change(function(){
        $(".stepFourth2 .inputs .row").hide();
        $(".stepFourth2 .inputs .row input.text, .stepFourth2 .inputs .row textarea").val("").removeClass("error2").siblings("p").find(".errorText").hide();
        $(".stepFourth2 .inputs .row input, .stepFourth2 .inputs .row textarea").val("");
        $(".stepFourth2 .inputs .row input.url").val($(".stepFourth2 .inputs .row input.url").attr("name"));
        var val = $(this).val();
        $(".stepFourth2 .inputs ."+val).show();
    });

    $(".stepFourth2 table.addedItem td a.remove").live("click", function(){
        $(this).parent().parent().remove();
        tableZebraEffect($(".stepFourth2 table.addedItem"));
    });
    
    //hover status for browse button
    $('.stepFourth2 .browserWrapper').hover(
        function() {
            $(this).find('.btn').addClass('hover');
        },
        function() {
            $(this).find('.btn').removeClass('hover');
        }
    );
    
    //file upload function
    $('.stepFourth2 .textUploadPhoto, .stepFourth2 .browserWrapper .btn').click(function() {
        $('.stepFourth2 .browserWrapper .file').trigger('click');
    });
    $('.stepFourth2 .browserWrapper .file').change(function() {
        var fileName = getFileName($('.stepFourth2 .browserWrapper .file').val());
        $('.stepFourth2 .textUploadPhoto').val(fileName);
    });

    $("#newSharedProjectStep4 .nextStepButton").click(function() {
        // step4 jsp is shared between mobile and presentation flow. 
        // TODO: put this code in neutral place, for example newCockpitProject.
        if (getProjectType() == PROJECT_TYPE_MOBILE) {
            goMobileProjectStep(5);
        } else if (getProjectType() == PROJECT_TYPE_PRESENTATION) {
            goCreatePresentationProjectStep(5);
        }
    });
}

//-----------------------------------------------
// Step 5
//-----------------------------------------------
function initMobileStep5() {
    $(".mobile .stepFifth2 .confirmButton a").click(function(){
        $(".mobile .stepFifth2 .proceed .message").show();
    });
    $(".mobile .stepFifth2 .message a.no").click(function(){
        $(".mobile .stepFifth2 .proceed .message").hide();
    });
    $(".mobile .stepFifth2 .message a.yes").click(function(){
        createNewProject();
    });
}

var mobileProjectOptions = {};

//-------------------------------------------------------------------
function createCopilotContestDescription_MobileProject() {
    var content = "";

    // 1. Development target
    var appType = htmlEncode($('.mobile .stepFirst2 .platform input:checked').val());
    var crossPlatformFramework;
    var targetOS;

    if (appType == 'cross') {
        appType = 'cross platform';
        var v = $('.mobile .stepFirst2 .crossOption input[name="cross"]:checked');
        if (v.val() == 'other') {
            crossPlatformFramework = '[other]: ' + htmlEncode($('.mobile .stepFirst2 .crossOption input[name="crossOther"]').val());
        } else {
            crossPlatformFramework = v.parent().text();
        }
    } else if (appType == 'native') {
        appType = 'native application';
        targetOS = "";
        $('.mobile .stepFirst2 .nativeOption input[name="native"]:checked').each(function(i) {
            if (i > 0) {
                targetOS += ', ';
            }
            if ($(this).val() == 'other') {
                targetOS += '[other]: ' + htmlEncode($('.mobile .stepFirst2 .nativeOption input[name="nativeOther"]').val());
            } else {
                targetOS += $(this).parent().text();
            }
        });
    } else if (appType == 'web') {
        appType = 'web application';
    } else if (appType == 'not') {
        appType = 'unsure, need help';
    }

    // 2. Application design
    var originalOrAdaptation = $('.mobile .stepSecond2 .original input[name="org"]:checked').val();
    var adaptedApplicationType;

    if (originalOrAdaptation == 'no') {
        originalOrAdaptation = 'original';
    } else if (originalOrAdaptation == 'yes') {
        originalOrAdaptation = 'adaptation';
        var v = $('.mobile .stepSecond2 .original input[name="app"]:checked');
        if (v.val() == 'other') {
            adaptedApplicationType = htmlEncode('[other]: ' + $('.mobile .stepSecond2 .original input[name="appOther"]').val());
        } else {
            adaptedApplicationType = htmlEncode(v.parent().text());
        }
    }

    var formFactor;
    var v = $('.mobile .stepSecond2 .products .left').eq(0);
    if (v.find('input[name="factorSelect"]').is(':checked')) {
        formFactor = 'unsure, need help';
    } else {
        formFactor = "";
        v.find('input[name="factor"]:checked').each(function(i) {
            if (i > 0) {
                formFactor += htmlEncode(', ');
            }
            if ($(this).val() == 'other') {
                formFactor += '[other]: ' + htmlEncode(v.find('input[name="factorOther"]').val());
            } else {
                formFactor += htmlEncode($(this).parent().text());
            }
        });
    }

    var screenOrientation;
    v = $('.mobile .stepSecond2 .products .left').eq(1);
    if (v.find('input[name="orientation"]').is(':checked')) {
        screenOrientation = htmlEncode('unsure, need help');
    } else {
        screenOrientation = htmlEncode(v.find('input[name="ori"]:checked').parent().text());
    }

    var screenResolution;
    v = $('.mobile .stepSecond2 .products .left').eq(2);
    if (v.find('input[name="resolution"]').is(':checked')) {
        screenResolution = htmlEncode('unsure, need help');
    } else {
        screenResolution = htmlEncode(v.find('input[name="res"]:checked').parent().text());
    }

    // 3. Hardware support
    var motionSensing;
    v = $('.mobile .stepThird2 .left').eq(0);
    if (v.find('input[name="motionSel"]').is(':checked')) {
        motionSensing = 'unsure, need help';
    } else {
        motionSensing = "";
        v.find('input[name="motion"]:checked').each(function(i) {
            if (i > 0) {
                motionSensing += htmlEncode(', ');
            }
            if ($(this).val() == 'other') {
                motionSensing += htmlEncode('[other]: ' + v.find('input[name="motionOtherText"]').val());
            } else {
                motionSensing += htmlEncode($(this).parent().text());
            }
        });
    }

    var geoLocation;
    v = $('.mobile .stepThird2 .left').eq(1);
    if (v.find('input[name="geoSel"]').is(':checked')) {
        geoLocation = 'unsure, need help';
    } else {
        geoLocation = "";
        v.find('input[name="geo"]:checked').each(function(i) {
            if (i > 0) {
                geoLocation += htmlEncode(', ');
            }
            geoLocation += htmlEncode($(this).parent().text());
        });
    }

    var camera;
    v = $('.mobile .stepThird2 .left').eq(2);
    if (v.find('input[name="cameraSel"]').is(':checked')) {
        camera = 'unsure, need help';
    } else {
        camera = "";
        v.find('input[name="camera"]:checked').each(function(i) {
            if (i > 0) {
                camera += htmlEncode(', ');
            }
            if ($(this).val() == 'other') {
                camera += htmlEncode('[other]: ' + v.find('input[name="cameraOtherText"]').val());
            } else {
                camera += htmlEncode($(this).parent().text());
            }
        });
    }

    var additionalFeatures;
    v = $('.mobile .stepThird2 .left').eq(3);
    if (v.find('input[name="aditionalSel"]').is(':checked')) {
        additionalFeatures = 'unsure, need help';
    } else {
        additionalFeatures = "";
        v.find('input[name="aditional"]:checked').each(function(i) {
            if (i > 0) {
                additionalFeatures += htmlEncode(', ');
            }
            if ($(this).val() == 'other') {
                additionalFeatures += htmlEncode('[other]: ' + $(this).parent().next().find('input[name="aditionalOtherText"]').val());
            } else {
                additionalFeatures += htmlEncode($(this).parent().text());
            }
        });
    }

    content += '<p><strong>Type of the app to build:</strong><br/>' + appType + '</p>';
    if (crossPlatformFramework != undefined) {
        content += '<p><strong>Cross-platform framework:</strong><br/>' + crossPlatformFramework + '</p>';
    }
    if (targetOS != undefined) {
        content += '<p><strong>Target operating system(s):</strong><br/>' + targetOS + '</p>';
    }

    content += '<p><strong>Original or adaptation status:</strong><br/>' + originalOrAdaptation + '</p>';
    if (originalOrAdaptation == 'adaptation') {
        content += '<p><strong>Type of the application being adapted:</strong><br/>' + adaptedApplicationType + '</p>';
    }
    content += '<p><strong>Form factor:</strong><br/>' + formFactor + '</p>';
    content += '<p><strong>Screen orientation:</strong><br/>' + screenOrientation + '</p>';
    content += '<p><strong>Screen resolution:</strong><br/>' + screenResolution + '</p>';
    content += '<p><strong>Motion sensing:</strong><br/>' + motionSensing + '</p>';
    content += '<p><strong>Geo location:</strong><br/>' + geoLocation + '</p>';
    content += '<p><strong>Camera:</strong><br/>' + camera + '</p>';
    content += '<p><strong>Additional features:</strong><br/>' + additionalFeatures + '</p>';

    var additionalRequirements = "";
    $(".stepFourth2 table.addedItem tbody").find("tr a.additionalRequirement").each(function() {
        var info = $(this).attr('rel');
        additionalRequirements += '<p style="width:600px;word-wrap: break-word;">' + info + '</p>';        
    });
    if (additionalRequirements != "") {
        content += '<p><strong>Additional requirements:</strong><br/></p>';
        content += additionalRequirements;
    }

    mobileProjectOptions.appType = appType;
    mobileProjectOptions.crossPlatformFramework = crossPlatformFramework;
    mobileProjectOptions.targetOS = targetOS;
    mobileProjectOptions.originalOrAdaptation = originalOrAdaptation;
    mobileProjectOptions.adaptedApplicationType = adaptedApplicationType;
    mobileProjectOptions.formFactor = formFactor;
    mobileProjectOptions.screenOrientation = screenOrientation;
    mobileProjectOptions.screenResolution = screenResolution;
    mobileProjectOptions.motionSensing = motionSensing;
    mobileProjectOptions.geoLocation = geoLocation;
    mobileProjectOptions.camera = camera;
    mobileProjectOptions.additionalFeatures = additionalFeatures;

    return content;
}

//-------------------------------------------------------------------
function updateMobileProjectSummaryPage() {
    createCopilotContestDescription_MobileProject();

    // 1. Development target
    var v = $('.mobile .stepFifth2 .content').eq(0);
    v.find('div.row').eq(0).find('.left').eq(1).html(mobileProjectOptions.appType);

    var v2 = v.find('div.row').eq(1);
    if (mobileProjectOptions.crossPlatformFramework != undefined) {
        v2.find('.left').eq(1).html(mobileProjectOptions.crossPlatformFramework);
        v2.show();
    } else {
        v2.hide();
    }

    v2 = v.find('div.row').eq(2);
    if (mobileProjectOptions.targetOS != undefined) {
        v2.find('.left').eq(1).html(mobileProjectOptions.targetOS);
        v2.show();
    } else {
        v2.hide();
    }

    // 2. Application design
    v = $('.mobile .stepFifth2 .content').eq(1);
    v.find('div.row').eq(0).find('.left').eq(1).html(mobileProjectOptions.originalOrAdaptation);

    v2 = v.find('div.row').eq(1);
    if (mobileProjectOptions.adaptedApplicationType != undefined) {
        v2.find('.left').eq(1).html(mobileProjectOptions.adaptedApplicationType);
        v2.show();
    } else {
        v2.hide();
    }

    v.find('div.row').eq(2).find('.left').eq(1).html(mobileProjectOptions.formFactor);
    v.find('div.row').eq(3).find('.left').eq(1).html(mobileProjectOptions.screenOrientation);
    v.find('div.row').eq(4).find('.left').eq(1).html(mobileProjectOptions.screenResolution);

    // 3. Hardware support
    v = $('.mobile .stepFifth2 .content').eq(2);

    v.find('div.row').eq(0).find('.left').eq(1).html(mobileProjectOptions.motionSensing);
    v.find('div.row').eq(1).find('.left').eq(1).html(mobileProjectOptions.geoLocation);
    v.find('div.row').eq(2).find('.left').eq(1).html(mobileProjectOptions.camera);
    v.find('div.row').eq(3).find('.left').eq(1).html(mobileProjectOptions.additionalFeatures);

    // 4. Detailed specifications. Right now use detailed requirements directly from step4.
    $('.mobile .stepFifth2 tbody').empty().append($('.stepFourth2 table.addedItem tbody').html());
    $('.mobile .stepFifth2 tbody').find('tr').each(function(i) {
        if (i > 0) {
            $(this).find('td').eq(3).remove();
        }
    });
}

function populateProjectAnswersForMobile() {
	// the result array.
	result = [];
	// prepare the project questions.
	mobileProjectQuestions = prepareProjectQuestions(PROJECT_TYPE_MOBILE);
	// define question index.
	var i = 0;
	// Development target
	answer = populateProjectAnswerFromProjectQuestion(mobileProjectQuestions[i++], result);
	if(answer.optionAnswers[0].answerHtmlValue == "cross") {
		// the app type is cross form.
		// get cross form framework
		populateProjectAnswerFromProjectQuestion(mobileProjectQuestions[i++], result);
		// escape the target os question
		i++;
	} else if(answer.optionAnswers[0].answerHtmlValue == "native") {
		// the app type is native.
		// get target os question
		i++;
		// get target os answer
		populateProjectAnswerFromProjectQuestion(mobileProjectQuestions[i++], result);
	} else {
		// the app type is web or not sure
		// escape above two questions
		i = i + 2;
	}
	// Application design
	answer = populateProjectAnswerFromProjectQuestion(mobileProjectQuestions[i++], result);
	if(answer.optionAnswers[0].answerHtmlValue == "no") {
		// Original design
		// escape next question
		i++;
	} else {
		// Adaptation design
		populateProjectAnswerFromProjectQuestion(mobileProjectQuestions[i++], result);
	}
	// Application design and Hardware support
	for(j=0; j<7; j++) {
		populateProjectAnswer_MobileType(mobileProjectQuestions[i++], result);
	}
	// get detailed specification
	getDetailedSpecifications(mobileProjectQuestions[i++], result);
	return result;
}

function populateProjectAnswer_MobileType(question, result) {
	answer = {};
	answer.optionAnswers = new Array();
	answer.projectQuestion = {};
	answer.projectQuestion.id = question.id;
	for(optionIndex in question.questionOptions) {
		var option = question.questionOptions[optionIndex];
		answerOption = {};
		answerOption.projectQuestionOption = {};
		answerOption.projectQuestionOption.id = option.id;
		// get the checkbox or radio
		answerNode = $("#" + option.answerHtmlId);
		if(answerNode.is(':checked')) {
			if(optionIndex == 0) {
				// user is unsure about this question. the rest options' answer will not be collected.
				answerOption.answerHtmlValue = answerNode.val();
				answer.optionAnswers.push(answerOption);
				break;
			}
			if(option.hasAssociatedTextbox) {
				answerOption.answerHtmlValue = $("#" + option.associatedTextboxHtmlId).val();
			}else {
				answerOption.answerHtmlValue = answerNode.val();
			}
			answer.optionAnswers.push(answerOption);
		}
	}
	answer.multipleAnswers = getMultipleAnswers(question.multipleAnswersHtmlXpath);
	result.push(answer);
	return answer;
}
