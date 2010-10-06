/**
 * The JS script for search.
 *
 *  Version 1.1 - Direct Pipeline Integration Assembly
 *  - Added onchange event listener to #scheduledContestsViewType drop-down
 *  - Added onclick event listeners to expand/collapse buttons on Pipeline Report screen
 *
 *
 * @author BeBetter, isv
 * @version 1.1 (Direct Search Assembly)
 */
$(document).ready(function() {
		var sStdMenu =
			'<strong>Show:  </strong><select size="1" name="dataTableLength">'+
				'<option value="5">5</option>'+
				'<option value="10">10</option>'+
				'<option value="25">25</option>'+
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
	$("#projectsResult .paginatedDataTable").dataTable({
		"iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" },
				{ "sType": "direct-projectNumber" }
			]

	});	
	$("#membersResult .paginatedDataTable").dataTable({
		"iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
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
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
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
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
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
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "date-direct" }, 
				{ "sType": "date-direct" }
			]

	});
	$("#activeContests .paginatedDataTable").dataTable({
		"iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
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
        "bFilter": false,
        "bSort": false,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
			] 
	});

        $("#pipelineReportArea .paginatedDataTable").dataTable({
        "iDisplayLength": 5,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp',
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

    $(".dataTables_info").addClass("hide");
    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");
    $(".dataTables_length").addClass("showPages");
    
    /* init date-pack */
    if($('.date-pick').length > 0){
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


    $("#pipelineSummary .expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        }else{
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineScheduledContests .expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $('.' + $('#scheduledContestsViewType').val() + 'ScheduledContests').show();
            $('.viewType').show();
            $(this).removeClass("collapse");
        }else{
            $(".scData").hide();
            $('.viewType').hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineDetails .expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        }else{
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
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
        return $('#validationErrors').html() == '';
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
