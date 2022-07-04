/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for contests results export panel.
 *
 * @version 1.0
 *
 * @since Release Assembly - TopCoder Cockpit Project Contest Results Export Part 1 version 1.0
 * @author TCSASSEMBLER
 */

$(function() {

	// attach the export action to the button
	$("#exportBtn").live('click', function(){
		var startDateStr = $("#exportDateBegin").val();
		var endDateStr = $("#exportDateEnd").val();
		if (startDateStr != '' && /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/.exec(startDateStr) == null) {
			alert("start date is not valid");
			return;
		}
		if (endDateStr != '' && /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/.exec(endDateStr) == null) {
			alert("end date is not valid");
			return;
		}
		var startDate = Date.parse(startDateStr, "MM/dd/yyyy");
		var endDate = Date.parse(endDateStr, "MM/dd/yyyy");
		
		if (startDate > endDate) {
			alert("start date must be before end date.");
			return;
		}

		$("#exportProjectResultForm").submit();
	});

	$('#ExportContestsExpand').live('click', function() {
		if ($(this).hasClass('exportCollapse')) {
			$(this).removeClass('exportCollapse').addClass('exportExpand');
			$(this).find('img').attr("src", "/images/filter-panel/collapse_icon.png");
			$(this).parent().parent().parent().parent().parent().find('.exportContent').hide();
			$(this).parent().parent().parent().parent().parent().find('.exportBottom').hide();
			$(this).parent().parent().parent().parent().parent().find('.collapseBottom').show();
			$(this).parent().parent().parent().parent().css({"margin-bottom":"-2px","height":'30px'});
			$(this).parent().parent().parent().parent().find(".rightSide").css({"height":'30px'});
		} else {
			$(this).removeClass('exportExpand').addClass('exportCollapse');
			$(this).find('img').attr("src", "/images/filter-panel/expand_icon.png");
			$(this).parent().parent().parent().parent().parent().find('.exportContent').show();
			$(this).parent().parent().parent().parent().parent().find('.exportBottom').show();
			$(this).parent().parent().parent().parent().parent().find('.collapseBottom').hide();
			$(this).parent().parent().parent().parent().css({"margin-bottom":"0px","height":'32px'});
			$(this).parent().parent().parent().parent().find(".rightSide").css({"height":'32px'});
		}
    });
});