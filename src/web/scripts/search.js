/**
 * The JS script for search.
 * 
 * @author BeBetter
 * @version 1.0 (Direct Search Assembly)
 */
$(document).ready(function() {
		var sStdMenu =
			'<select id=size="1" name="dataTableLength">'+
				'<option value="5">5</option>'+
				'<option value="10">10</option>'+
				'<option value="25">25</option>'+
			'</select>';
	
    $(".paginatedDataTable").dataTable({
    	  "iDisplayLength": 10,
        "bFilter": false,
        "bSort": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rt<"bottom1"il><"bottom2"fp'
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
