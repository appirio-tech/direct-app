/**
 * The JS script for search.
 *
 *  Version 1.1 - Direct Pipeline Integration Assembly
 *  - Added onchange event listener to #scheduledContestsViewType drop-down
 *  - Added onclick event listeners to expand/collapse buttons on Pipeline Report screen
 *
 *  Version 1.2 - Direct Pipeline Stats Update Assembly
 *  - Moved onchange event listener to #scheduledContestsViewType drop-down to dashboard-pipeline.js
 *  - Moved onclick event listeners to expand/collapse buttons on Pipeline Report screen to dashboard-pipeline.js
 *
 *  Version 1.3 - (TopCoder Cockpit - Cost Report Assembly)
 *  - Add DataTable pagination setting for aggregation cost report
 *  - Add two custom DataTable comparator for money and simple data.
 *  - Add collapse/expand event for aggregation cost report and cost details report.
 *
 * @author BeBetter, isv, Blues
 * @version 1.3
 */
var cookieOptions = { path: '/', expires: 1 };
var COOKIE_NAME = "pagination";

$(document).ready(function() {
    var sStdMenu =
            '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="5">5</option>' +
            '<option value="10">10</option>' +
            '<option value="25">25</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    var sLongStdMenu =
            '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="10">10</option>' +
            '<option value="25">25</option>' +
            '<option value="50">50</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    function trim(str) {
        str = str.replace(/^\s+/, '');
        for (var i = str.length - 1; i >= 0; i--) {
            if (/\S/.test(str.charAt(i))) {
                str = str.substring(0, i + 1);
                break;
            }
        }
        return str;
    }

    jQuery.fn.dataTableExt.oSort['direct-projectNumber-asc'] = function(a, b) {
        var num1 = a.split('/');
        var num2 = b.split('/');
        var x = num1[0] * 1;
        var y = num2[0] * 1;
        var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
        return z;
    };

    jQuery.fn.dataTableExt.oSort['direct-projectNumber-desc'] = function(a, b) {
        var num1 = a.split('/');
        var num2 = b.split('/');
        var x = num1[0] * 1;
        var y = num2[0] * 1;
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };
    jQuery.fn.dataTableExt.oSort['date-direct-asc'] = function(a, b) {
        if (trim(a) != '') {
            var frDatea = trim(a).split(' ');
            var frTimea = frDatea[1].split(':');
            var frDatea2 = frDatea[0].split('/');
            var x = (frDatea2[2] + frDatea2[0] + frDatea2[1] + frTimea[0] + frTimea[1]) * 1;
        } else {
            var x = 10000000000000; // = l'an 1000 ...
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split(' ');
			var frTimeb = frDateb[1].split(':');
			frDateb = frDateb[0].split('/');
			var y = (frDateb[2] + frDateb[0] + frDateb[1] + frTimeb[0] + frTimeb[1]) * 1;		                
		} else {
			var y = 10000000000000;		                
		}
		var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
		return z;
	};

	jQuery.fn.dataTableExt.oSort['date-direct-desc'] = function(a, b) {
		if (trim(a) != '') {
			var frDatea = trim(a).split(' ');
			var frTimea = frDatea[1].split(':');
			var frDatea2 = frDatea[0].split('/');
			var x = (frDatea2[2] + frDatea2[0] + frDatea2[1] + frTimea[0] + frTimea[1]) * 1;		                
		} else {
			var x = 10000000000000;		                
		}

        if (trim(b) != '') {
            var frDateb = trim(b).split(' ');
            var frTimeb = frDateb[1].split(':');
            frDateb = frDateb[0].split('/');
            var y = (frDateb[2] + frDateb[0] + frDateb[1] + frTimeb[0] + frTimeb[1]) * 1;
        } else {
            var y = 10000000000000;
        }
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };

    /** sort comparator for simple date "yyyy-MM-DD" **/
    jQuery.fn.dataTableExt.oSort['simple-date-asc'] = function(a, b) {
        if (trim(a) != '') {
            var frDatea = trim(a).split('-');
            var x = (parseFloat(frDatea[2]) + parseFloat(frDatea[1]) * 30 + parseFloat(frDatea[0]) * 365) * 1;
        } else {
            var x = 10000000000000;
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split('-');
            var y = (parseFloat(frDateb[2]) + parseFloat(frDateb[1]) * 30 + parseFloat(frDateb[0]) * 365) * 1;
		} else {
			var y = 10000000000000;
		}
		var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
		return z;
	};

	jQuery.fn.dataTableExt.oSort['simple-date-desc'] = function(a, b) {
		if (trim(a) != '') {
            var frDatea = trim(a).split('-');
            var x = (parseFloat(frDatea[2]) + parseFloat(frDatea[1]) * 30 + parseFloat(frDatea[0]) * 365) * 1;
        } else {
            var x = 10000000000000;
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split('-');
            var y = (parseFloat(frDateb[2]) + parseFloat(frDateb[1]) * 30 + parseFloat(frDateb[0]) * 365) * 1;
		} else {
			var y = 10000000000000;
		}
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };

    /***TCCC-2516*/
    jQuery.fn.dataTableExt.oSort['html-trimmed-asc'] = function (a, b) {
        var x = trim(a.replace(/<.*?>/g, "").toLowerCase());
        var y = trim(b.replace(/<.*?>/g, "").toLowerCase());
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['html-trimmed-desc'] = function (a, b) {
        var x = trim(a.replace(/<.*?>/g, "").toLowerCase());
        var y = trim(b.replace(/<.*?>/g, "").toLowerCase());
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['money-asc'] = function (a, b) {
        var x = parseFloat(trim(a.replace(/[^\d]/g, "")));
        var y = parseFloat(trim(b.replace(/[^\d]/g, "")));
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['money-desc'] = function (a, b) {
        var x = parseFloat(trim(a.replace(/[^\d]/g, "")));
        var y = parseFloat(trim(b.replace(/[^\d]/g, "")));
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };


    jQuery.fn.dataTableExt.oSort['reliability-asc'] = function (a, b) {
        var x = getReliability(trim(a));
        var y = getReliability(trim(b));
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['reliability-desc'] = function (a, b) {
        var x = getReliability(trim(a));
        var y = getReliability(trim(b));
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    function getReliability(text) {
        if (text == null || text.length == 0) {
            return 0;
        }
        text = text.replace(/,/g, '.');
        var f = parseFloat(text.substring(0, text.length - 1));
        if (isNaN(f)) {
            return 0;
        } else {
            return f;
        }
    }

    $("#projectsResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html-trimmed" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" },
                { "sType": "direct-projectNumber" }
			]

    });
    $("#membersResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
			]

    });
    $("#contestsResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "date-direct" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				null
			]

    });
    $("#ProjectContests .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "date-direct" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				null
			]

    });
    $("#ProjectRegistrants .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [
            [0,'asc']
        ],
        "aoColumns": [
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "reliability" },
            { "sType": "date-direct" },
            { "sType": "date-direct" }
        ]

    });
    $("#activeContests .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
                { "sType": "html" },
				{ "sType": "date-direct" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				null
			]

    });

    $("#notificationsContent .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": false,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
			] 
	});

    var pipelineDataTable = $("#pipelineReportArea .paginatedDataTable").dataTable({
        "iDisplayLength": 5,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                null
            ]

    });

    $("#MyCopilotPostings .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                null
            ]

    });

    $("#CopilotPostingRegistrants .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                null,
                { "sType": "date-direct" },
                { "sType": "date-direct" }
            ]

    });

    $("#CopilotPostingSubmissions .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                null,
                { "sType": "date-direct" },
                null,
                null
            ]

    });

    $("#costReportSection .paginatedDataTable").dataTable({
        "iDisplayLength": 25,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
                { "sType": "html" },
                { "sType": "simple-date" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" }
            ]

    });

    $("#billingCostReportSection .paginatedDataTable").dataTable({
        "iDisplayLength": 50,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sLongStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bcbottom1"l><"bcbottom2"fp',
        "aaSorting": [[4,'asc']],
        "aoColumns": [
                { "sType": "simple-date" },
                { "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "simple-date" },
                { "sType": "simple-date" },
                { "sType": "money" },
                { "sType": "html" },
                { "sType": "money" }
            ]

    });



    function formatDate(d) {
        var t1 = d.getDate();
        if (t1 < 10) {
            t1 = '0' + t1;
        }

        var t2 = d.getMonth() + 1;
        if (t2 < 10) {
            t2 = '0' + t2;
        }

        var t3 = d.getFullYear();

        return t2 + "/" + t1 + "/" + t3;
    }

    $('.summaryWeek').click(function() {
        var weekOf = $(this).attr('rel');

        var d1 = new Date();
        d1.setTime(parseInt(weekOf));
        d1.setHours(0);
        d1.setMinutes(0);
        d1.setSeconds(0);
        d1.setMilliseconds(0);

        var d2 = new Date();
        d2.setTime(d1.getTime() + 7 * 24 * 3600 * 1000 - 1000);

        $('#DashboardSearchForm input[name=formData.startDate]').val(formatDate(d1));
        $('#DashboardSearchForm input[name=formData.endDate]').val(formatDate(d2));
        $('#DashboardSearchForm').submit();
    });

    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");
    $(".dataTables_length").addClass("showPages");
    
    /* init date-pack */
    if($('.date-pick').length > 0){
        Date.firstDayOfWeek = 0;
    	$(".date-pick").datePicker({startDate:'01/01/2001'});
    }

    /* start/end only visible when contest type */
    $('#searchIn').bind("change",function() {
    	  if($('#searchIn').val() == 'CONTESTS') {
    	  	$('#datefilter').show();
    	  } else {
    	  	$('#datefilter').hide();
    	  }
    });
    $('#searchIn').trigger("change");

    $('#scheduledContestsViewType').change(function() {
        $('.scData').hide();
        $('.' + $(this).val() + 'ScheduledContests').show();
    });


    $("#pipelineSummary .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineScheduledContests .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $('.' + $('#scheduledContestsViewType').val() + 'ScheduledContests').show();
            $('.viewType').show();
            $(this).removeClass("collapse");
        } else {
            $(".scData").hide();
            $('.viewType').hide();
            $(this).addClass("collapse");
        }
    });

    $("#costReportAggregationArea .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $('.' + $('#aggregationCostReportType').val() + 'AggregationCostReport').show();
            $('.viewType').show();
            $(this).removeClass("collapse");
        } else {
            $(".scData").hide();
            $('.viewType').hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#costDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#billingCostDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $('.removeNumericalFilter').click(function() {
        $(this).parent().remove();
    });

    $('#submitPipelineForm').click(function() {
        var v1 = -1;
        var v2 = -1;
        $('#validationErrors').html('');
        if ($.trim($('#numericalFilterMinValue').val()) != '' && !isNumber($('#numericalFilterMinValue').val())) {
            $('#validationErrors').append('Numerical filter minimum value must be non-negative number<br/>');
        } else {
            v1 = parseFloat($('#numericalFilterMinValue').val());
        }
        if ($.trim($('#numericalFilterMaxValue').val()) != '' && !isNumber($('#numericalFilterMaxValue').val())) {
            $('#validationErrors').append('Numerical filter maximum value must be non-negative number');
        } else {
            v2 = parseFloat($('#numericalFilterMaxValue').val());
        }
        if (v1 > -1 && v2 > -1) {
            if (v2 < v1) {
                $('#validationErrors').append('Numerical filter maximum value must not be less than minimum value');
            }
        }
        if ($('#validationErrors').html() == '') {
            var currentPage = $.trim($('.paginate_active').html());
            $('#pipelineDetailsPageNumber2').val(currentPage);
            $('#formDataExcel').val("false");
            document.DashboardSearchForm.submit();
        }
        return false;
    });
});
   
/**
 * submits the search form.
 */    
function directSearch() {
	$('#formDataExcel').val("false");
	document.DashboardSearchForm.submit();
}    

/**
 * submits the search form and trigger excel download.
 */    
function directExcel() {
	$('#formDataExcel').val("true");
	document.DashboardSearchForm.submit();
}    

function isNumber(v) {
    var regExp1 = new RegExp('^[0-9]*\\.[0-9]*$');
    var regExp2 = new RegExp('^[0-9]*$');
    return regExp1.test(v) || regExp2.test(v);
}
