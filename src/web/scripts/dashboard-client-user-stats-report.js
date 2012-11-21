/**
 * The JS script is for dashboard Client User Stats report.
 *
 * AUTHOR: leo_lol
 * VERSION: 1.0 (Module Assembly - TC Client Users Stats Report Generation)
 */
$(document).ready(function(){
   sortDropDown("#currentClientId");
   $('#clientUserStatsReportsSection .pipelineStats .tableTitle .expand').click(function(){
        $(this).blur();
        $me = $(this);
        if(!$me.hasClass('collapse')){
            $me.addClass('collapse');
            $meTable = $me.closest('table');
            $meTable.find('.subTheadRow').hide();
            $meTable.find('.viewType div').hide();
            $meTable.find('tbody').hide();
        }else{
            $me.removeClass('collapse');
            $meTable = $me.closest('table');
            $meTable.find('.subTheadRow').show();
            $meTable.find('.viewType div').show();
            $meTable.find('tbody').show();
        }
        return false;
    });
   
   $("#clientIdSelect").change(function(){
        loadFilter();
   });
   $("#yearMonthSelect").change(function(){
      loadFilter();
   });
});

/**
 * Function used to export excel file.
 */
function getClientUserStatsReportAsExcel() {
   $("#formDataExcel").val(true);
   $("#clientUserStatsReportForm").submit();
}

/**
 * Synchronize content according the filtering criteria.
 */
function loadFilter() {
   var currentClient = $("#clientIdSelect").val();
   var currentYearMonth = $("#yearMonthSelect").val();
   var dT = $.clientUserStatsReportDataTable;
   
   updateYearMonth();
   if('All' == currentClient) {
      dT.fnFilter("", 0);
   } else {
       dT.fnFilter(currentClient, 0);
   }
   if('All' == currentYearMonth) {
      dT.fnFilter("", 1);
   } else {
      dT.fnFilter(currentYearMonth, 1);
      $("#yearMonthSelect").val(currentYearMonth);
   }
}

/**
  Function to update Year/Month filter, making only available choices selectable.
 */
function updateYearMonth() {
   $(".yearMonthItem").remove();
   var $select = $("#yearMonthSelect");
   var dT = $.clientUserStatsReportDataTable;
   var sData = dT.fnGetData();
   var currentClient = $("#clientIdSelect").val();
   var existing = "";
   var yearMonthArray = new Array();
   for(var i=0; i<sData.length; i++) {
      var items = (sData[i] + "").split(',');
      if(currentClient == items[0] || 'All' == currentClient) {
         if(-1 == existing.indexOf(items[1])) {
         	yearMonthArray.push(items[1]);
            existing = existing + ', ' + items[1];
         }
      }
   }
   yearMonthArray.sort();
   yearMonthArray.reverse();
   for(var i=0; i<yearMonthArray.length; i++) {
	   $select.append('<option class="yearMonthItem">' + yearMonthArray[i] + '</option>');
   }
}