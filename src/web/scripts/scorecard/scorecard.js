/**
 * - Author: TCSASSEMBER
 * - Version: 1.1
 * - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 * 
 * This file contains all the client javascript code for the scorecard pages.
 * Version 1.1 (Release Assembly - TopCoder Scorecard Tool Ajax Loading and Static Files Sharing) Change notes:
 * - Add Clone scoreboard
 * - Change AJAX loading animiation.
 */

/**
 * will be called before ajax calls
 */
function beforeAjax() {
    modalPreloader();
}

/**
 * will be called after ajax calls
 */
function afterAjax() {
    modalClose();
}

/**
 * Common function to handle JSON result.
 */
function handleJsonResult(jsonResult, successCallBack, failureCallBack) {
   if(jsonResult.success) {
       successCallBack(jsonResult);
   } else {
       failureCallBack(jsonResult.message);
   }
}

/**
 * Show the error messages.
 *
 * @param errors the error messages
 */
function showErrors(errors) {
	$('#validation').dialog("open");
    $('#validation p.err').remove();
    $('#validation .body p:last').after("<p class='err'>" + errors + "</p>");
}

/**
 * Get the scorecard groups
 *
 * @param id the id of the scorecard
 */
function getGroups(id){
    $("#addMatch .body").load(ctx + "/scorecard/ajaxContent/" + id, function() {
        $('#addMatch').dialog("open");
        $('#addMatch').find("input").each(function(){
            $(this).attr('checked', false);
            $(this).removeClass("hover");
        });
        $('#addMatch').find(".card,tr.question,table.checks.section").each(function(){
            $(this).removeClass('hover');    
        });
		if ($('#cloneScorecard').val() != undefined){
			$('.button5.back').parent().hide();
		} else {
			$('.button5.back').parent().show();
		}
        afterAjax();
    });
}

var ctx = "";
// the json service root url
var jsonServiceUrlRoot = "";

var projectCategories;
var projectCategoryMap = [];
var scorecardTypeMap = [];
var scorecardStatusMap = [];

/**
 * Load the project categories data using ajax.
 * 
 * @param callback the handler which will be called after ajax data returned
 */
function loadProjectCategories(callback) {
    if (projectCategories) {
        callback();
        return;
    }
    $.ajax({
        type: 'GET',
        url: jsonServiceUrlRoot + "/project-category",
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    projectCategories = result.data;
                    for (var i = 0; i < projectCategories.length; i++) {
                        projectCategoryMap[projectCategories[i].id] = projectCategories[i].name;
                    }
                    callback();
                },
                showErrors
            );
        }
    });
}

/**
 * Search scorecard using ajax call.
 * 
 * @param page the page index
 * @param max the max result
 * @param callback the handler which will be called after ajax data returned
 */
function searchScorecard(page, max, callback) {
	var hasQuery = $.trim($("input[name='sn']").val()).length > 0;
	if (!hasQuery) {
		hasQuery = $("input[name='spt'],input[name='sc'],input[name='st'],input[name='ss']").is(":checked");
	}
	if (!hasQuery) {
		alert("At least one keyword should be entered before searching.");
		return;
	}
    loadProjectCategories(function() {
        $.ajax({
            type: 'GET',
            url: ctx + '/scorecard/search',
            data: $("#searchForm").serialize() + "&offset=" + (page * max) + "&max=" + max,
            dataType: 'json',
            success: function(jsonResult) {
                $(".searchResultHide").hide();
                $(".searchResultShow").show();
                $("#addScorecard").removeClass("roundedBox").addClass("scorecardSearch");
                $(".scorecardFind").appendTo("#searchResultForm");
                handleJsonResult(jsonResult,
                    function(result) {
                        $("#navTitle").html('Search Results');
                        $(".areaHeader .title").html('Search Results');
                        $("#searchResultCount").html('<strong>' + result.totalCount + '</strong>')
                        
                        var html = '';
                        for (var i = 0; i < result.count; i++) {
                            var so = result.data[i];
                            html = html + '<tr>';
                            html = html + '<td class="first"><a href="' + ctx + '/scorecard/show/' + so.id + '">' + so.scorecardName + ' v ' + so.scorecardVersion + '</a></td>';
                            html = html + '<td class="second">' + scorecardTypeMap[so.scorecardType] + '</td>';
                            html = html + '<td>' + projectCategoryMap[so.projectCategory] + '</td>';
                            html = html + '<td>' + scorecardStatusMap[so.scorecardStatus] + '</td>';
                            html = html + '<td class="action"><a href="' + ctx + '/scorecard/show/' + so.id + '" class="view">View&nbsp;|&nbsp;</a>';
                            if (so.editable){
                                html = html + '<a href="' + ctx + '/scorecard/edit/' + so.id + '" class="edit">Edit&nbsp;|&nbsp;</a>';
                            } else {
                                html = html + '<a href="javascript:;"' + so.id + '" class="edit"><span class="disabled">Edit</span>&nbsp;|&nbsp;</a>';
                            }
                            html = html + '<a href="' + ctx + '/scorecard/clone/' + so.id + '" ref="' + so.id + '" class="copy">Clone</a></td>';
                            html += '</tr>';
                        }
                        $("#searchResult tbody").html(html);
                        
                        // rendering paging control
                        html = '';
                        var maxPage = parseInt(result.totalCount / max);
                        if (max == 0) {
                            maxPage = 1;
                        }
                        if (result.totalCount % max > 0) {
                            maxPage++;
                        }
                        var css = "pageLink";
                        if (!window.initMultiselect) {
                            css = "pageLink2";
                        }
                        if (page == 0) {
                            html += '<span class="prev">Prev</span>';
                        } else {
                            html += '<a href="#" class="prev ' + css + '" rel="' + (page - 1) + '">Prev</a>';
                        }
                        for (var i = 0; i < maxPage; i++) {
                            if (page == i) {
                                html += '<a href="#" class="current ' + css + '" rel="' + i + '">' + (i + 1) + '</a>';
                            } else{
                                html += '<a class="' + css + '" href="#" rel="' + i + '">' + (i + 1) + '</a>';
                            }
                        }
                        if (page == maxPage - 1) {
                            html += '<span class="next">Next</span>';
                        } else {
                            html += '<a href="#" class="next ' + css + '" rel="' + (page + 1) + '">Next</span>';
                        }
                        $(".pages").html(html);
                        if (callback) {
                            callback();
                        }
                    },
                    showErrors
                );
            }
        });
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
    $(questionSRC).find('select.edit option').each(function() {
    	if ($.trim($(this).html()) == $.trim(selType)) {
    		$(this).attr('selected', 'selected');
    	}
    });    
    $(questionSRC).find('select.questionUploadDocument option').each(function() {
    	if ($.trim($(this).html()) == $.trim(upload)) {
    		$(this).attr('selected', 'selected');
    	}
    });    
    $(questionSRC).find("input.weightQ").val(weight);
    
    $(openQuestionHead).after(questionSRC);
}

/**
 * Calculate sum of question weights for every section.
 */
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

/**
 * Calculate sum of section for every group.
 */
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

/**
 * Calculate sum of group weights.
 */
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

/**
 * Gets the scorecard data which will be sending to server.
 */
function getScorecardData() {
    var scorecard = {
        id: parseInt($("#scorecard_id").val()),
        scorecardName: $("#scorecardName").val(),
        scorecardVersion: $("#scorecardVersion").val(),
        projectType: parseInt($("#projectType").val()),
        projectCategory: parseInt($('#projectCategory').val()),
        scorecardType: parseInt($('#scorecardType').val()),
        minimumScore: parseFloat($('#minimumScore').val()),
        maximumScore: parseFloat($('#maximumScore').val()),
        scorecardStatus: parseInt($('#scorecardStatus').val())
    };
    var scorecardGroups = [];
    $("#sortableGroups .biggroup").each(function(sortG) {
        var group = {sort: sortG + 1, scorecardSections: []};
        var obj = $(this);
        if (obj.attr('rel')) {
            group.id = parseInt(obj.attr('rel'));
        }
        group.groupName = obj.find("input[name='groupName']").val();
        group.weight = parseFloat(obj.find("input[name='groupWeight']").val());
        scorecardGroups.push(group);
        
        obj.find("table.scorecard.section").each(function(sortS) {
            var curSection = {sort: sortS + 1, scorecardQuestions: []};
            var trobj = $(this).find("tr.section");
            if (trobj.attr('rel')) {
                curSection.id = parseInt(trobj.attr('rel'));
            }
            curSection.sectionName = trobj.find("input[name='sectionName']").val();
            curSection.weight = parseFloat(trobj.find("input[name='sectionWeight']").val());
            group.scorecardSections.push(curSection);
            
            $(this).find("tr.question").each(function(sortQ) {
                var qobj = $(this);
                if (qobj.hasClass('hide')) {
                    return;
                }
                var question = {sort: sortQ + 1};
                if (qobj.attr('rel')) {
                    question.id = parseInt(qobj.attr('rel'));
                }
                question.description = qobj.find("textarea[name='questionDescription']").val();
                question.guideline = qobj.find("textarea[name='questionGuideline']").val();
                question.questionType = parseInt(qobj.find("select[name='questionType']").val());
                question.weight = parseFloat(qobj.find("input[name='questionWeight']").val());
                question.uploadDocument = qobj.find("select[name='questionUploadDocument']").val() == "true" ? true : false;
                curSection.scorecardQuestions.push(question);
            });
        });
    });
    scorecard.scorecardGroups = scorecardGroups;
    return scorecard;
}

/**
 * Flip object order.
 */
function flipObject(obj){
    var arr = [];
    $.each(obj, function(key, val){
        var temp = new Object;
        temp['key'] = key;
        temp['val'] = val;
        arr.push(temp);
        delete temp;
        delete obj[key];
    });
    arr.reverse();
    $.each(arr, function(key, val){
        obj[key] = val['val'];
    });
    
    return obj;
}

$(document).ready(function() {
    ctx = $("#ctx").val();
		jsonServiceUrlRoot = ctx + "/json";
    $("input[name='st']").each(function() {
        scorecardTypeMap[parseInt($(this).val())] = $(this).parent().text();
    });
    $("input[name='ss']").each(function() {
        scorecardStatusMap[parseInt($(this).val())] = $(this).parent().text();
    });
    if (window.initMultiselect) {
        $("#spt").multiSelect({}, function() {
            loadProjectCategories(function() {
                var os = $("input[name='spt']");
                var ptarray = [];
                var html = "<option></option>";
                for (var i = 0; i < os.length; i++) {
                    if ($(os[i]).is(":checked")) {
                        ptarray[parseInt($(os[i]).val())] = true;
                    }
                }
                for (var i = 0; i < projectCategories.length; i++) {
                    if (ptarray[projectCategories[i].projectType]) {
                        html = html + "<option value='" + projectCategories[i].id + "'>" + projectCategories[i].name + "</option>";
                    }
                }
                var prev = $("#sc").prev();
                $("#sc").remove();
                $('<select name="sc" class="combo" id="sc">' + html + '</select>').insertAfter(prev);
                $("#sc").multiSelect();
            });
        });
        $("#sc").multiSelect();
    }
    
    $.ajaxSetup({
        cache: false,
        beforeSend: beforeAjax,
        complete: afterAjax
    });
    $("#projectType").change(function() {
        var pt = $(this).val();
        loadProjectCategories(function() {
            var ptarray = [];
            var html = "";
            for (var i = 0; i < projectCategories.length; i++) {
                if (projectCategories[i].projectType == pt) {
                    html = html + "<option value='" + projectCategories[i].id + "'>" + projectCategories[i].name + "</option>";
                }
            }
            $("#projectCategory").html(html);
        });
    });
    
    $("input[name='sn']").keypress(function(event) {
        if(event.which == 13) {
            searchScorecard(0, $(".maxResult").val());
            
            return false;
        }        
        
    });
    $("#search, .apply").click(function() {
        searchScorecard(0, $(".maxResult").val());
    });
    $(".maxResult").change(function() {
		$(".maxResult").val($(this).val());
        searchScorecard(0, $(".maxResult").val());
    });
    $(".maxResult2").unbind("change").change(function() {
        $(".maxResult2").val($(this).val());
        $('#addSearch').dialog("close");
        searchScorecard(0, $(".maxResult").val(), function() {
            $('#addSearch').dialog("open");
            $('a.edit').hide();
            $('a.copy').show();
            $('a.copy').each(function(){
                $(this).attr('href', 'javascript:;');
                $(this).html('Copy');
            });
			$("a.view").attr("target","_blank");
			$("#searchResult td.first a").click(function() {
				$(this).parent().parent().find("a.copy").click();
				return false;
			});
        });
    });
    $(".pageLink").live("click", function() {
        searchScorecard(parseInt($(this).attr("rel")), $(".maxResult").val());
    });
    $(".pageLink2").live("click", function() {
        $('#addSearch').dialog("close");
        searchScorecard(parseInt($(this).attr("rel")), $(".maxResult").val(), function() {
            $('#addSearch').dialog("open");
            $('a.edit').hide();
            $('a.copy').show();
            $('a.copy').each(function(){
                $(this).attr('href', 'javascript:;');
                $(this).html('Copy');
            });
			$("a.view").attr("target","_blank");
			$("#searchResult td.first a").click(function() {
				$(this).parent().parent().find("a.copy").click();
				return false;
			});
        });
    });
    $(".apply2").click(function() {
        $('#addSearch').dialog("close");
        searchScorecard(0, $(".maxResult").val(), function() {
            $('#addSearch').dialog("open");
            $('a.edit').hide();
            $('a.copy').show();
            $('a.copy').each(function(){
                $(this).attr('href', 'javascript:;');
                $(this).html('Copy');
            });
			$("a.view").attr("target","_blank");
			$("#searchResult td.first a").click(function() {
				$(this).parent().parent().find("a.copy").click();
				return false;
			});
        });
    });
    $(".clearDataFind").click(function() {
        $("#searchForm input[name='spt']").attr("checked", false);
        $("#spt span").html("Select options");
        $("#sc span").html("Select options");
    });

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
        
        var scorecardData = getScorecardData();
        $.ajax({
            type: 'POST',
            url: ctx + '/scorecard/save/' + scorecardData.id,
            data: {scorecard: JSON.stringify(scorecardData)},
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        window.location.href = ctx + '/scorecard/show/' + jsonResult.id
                    },
                    showErrors
                );
            }
        });
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
        height: 372,
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
        $("#searchForm").appendTo("#addScorecard2 .scorecardFind");
        $('.addmenu').hide();
        $('#addScorecard2').dialog("open");
        openQuestionHead = $(this).parents(".questionHead");
        if (!$("#spt").is("a")) {
            $("#spt").multiSelect({}, function() {
                loadProjectCategories(function() {
                    var os = $("input[name='spt']");
                    var ptarray = [];
                    var html = "<option></option>";
                    for (var i = 0; i < os.length; i++) {
                        if ($(os[i]).is(":checked")) {
                            ptarray[parseInt($(os[i]).val())] = true;
                        }
                    }
                    for (var i = 0; i < projectCategories.length; i++) {
                        if (ptarray[projectCategories[i].projectType]) {
                            html = html + "<option value='" + projectCategories[i].id + "'>" + projectCategories[i].name + "</option>";
                        }
                    }
                    var prev = $("#sc").prev();
                    $("#sc").remove();
                    $('<select name="sc" class="combo" id="sc">' + html + '</select>').insertAfter(prev);
                    $("#sc").multiSelect();
                });
            });
            $("#sc").multiSelect();
        }
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
		$('#addSearch').dialog("close");
        return false;
    });    
    
    $('#addScorecard2 .searchBtn').live('click',function(){
        $('#addScorecard2').dialog("close");
        searchScorecard(0, $(".maxResult").val(), function() {
            $("#searchForm").appendTo("#addScorecard .scorecardFind");
            $('#addSearch').dialog("open");
            $('a.edit').hide();
            $('a.copy').show();
            $('a.copy').each(function(){
                $(this).attr('href', 'javascript:;');
                $(this).html('Copy');
            });
			$("a.view").attr("target","_blank");
			$("#searchResult td.first a").click(function() {
				$(this).parent().parent().find("a.copy").click();
				return false;
			});
        });
    });
    
    /* reuse html from scorecard-find.html */  
    //$('#addScorecard2 .body').load('scorecard-find.html .scorecardFind');
    //$('#addSearch .body').load('scorecard-search-result.html .scorecardSearch');

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
	$('#addMatch .closeDialog').click(function() {
		$('#addMatch').dialog('close');
	});

    
    $('#addSearch a.copy').live('click',function(){
        $('#addSearch').dialog("close");
        getGroups($(this).attr("ref"));
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
			if (openQuestionHead == undefined){
				openQuestionHead = $(".questionHead").first();
			}
            $(openQuestionHead).parents(".biggroup").before($(newGroup));
            
            var actualGroupTable = $(openQuestionHead).parents(".biggroup").prev().find("table.group").first();
            
            var sections = flipObject($(this).find("table.section.hover"));            
            sections.each(function(){
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
                
                var questions = flipObject($(this).find("tr.question.hover"));     
                questions.each(function(){
                    /*read question values*/
                    var questionText = $(this).find("span.questionText").html();
                    var questionWeight = $(this).find("span.questionWeight").html();
                    var questionType = $(this).find("span.questionType").html();
                    var questionUpload = $(this).find("span.questionUpload").html();
                    
                    var guideline = $(this).find(".ratings").text();
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
                        
                        var guideline = $(this).find(".ratings").text();
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
                    
                    var guideline = $(this).find(".ratings").text();
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
    
    calculateWeightG();
    calculateWeightS();
    calculateWeightQ();
    
	// Show the modal window when entering clong scorecard page
	if ($('#cloneScorecard').val() != undefined){
		getGroups($('#cloneScorecard').val());
		var version = $("#scorecardVersion").val();
		var sep = version.split(".");
		var last = parseInt(sep[sep.length - 1]) + 1;
		sep[sep.length - 1] = last.toString();
		$("#scorecardVersion").val(sep.join("."));
	}
});
