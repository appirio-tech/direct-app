/**
 * The JS script for Pipeline report.
 *
 * Version 1.1 updates: Toggle report type selection. Two reports are supported: pipeline and cost report.
 *
 * Version 1.2 updates: Toggle report type selection. Three reports are supported: pipeline ,
 * cost report and billing cost report.
 *
 * Version 1.2.1 updates: Add js validation for start date and end date for pipeline report, end date must be larger than
 * end date.
 *
 * Version 1.3 (Release Assembly - TopCoder Cockpit Reports Filter Panels Revamp) updates
 * - Updates the filter panel of pipeline report to the new one.
 *
 * @author isv, Blues, GreatKevin, TCSASSEMBLER
 * @version 1.3
 * @since Direct Pipeline Stats Update Assembly
 */
var ctx = "/direct";

var draftsRatios = {};

$(document).ready(function(){

    $('#scheduledContestsViewType').change(function() {
        var viewType = $(this).val();
        $('.scData').hide();
        $('.' + viewType + 'ScheduledContests').show();
        if (!draftsRatios[viewType]) {
            getDraftsRatio(viewType);
        }
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
            $('#validationErrors').append('Numerical filter maximum value must be non-negative number<br/>');
        } else {
            v2 = parseFloat($('#numericalFilterMaxValue').val());
        }
        if (v1 > -1 && v2 > -1) {
            if (v2 < v1) {
                $('#validationErrors').append('Numerical filter maximum value must not be less than minimum value<br/>');
            }
        }
		var startDate = new Date($("#startDate").val());
        var endDate = new Date($("#endDate").val());

        if (startDate >= endDate) {
            $('#validationErrors').append("Start date should be smaller than end date<br/>");
        }
        if ($('#validationErrors').html() == '' || $('#validationErrors').html() == null) {
            var currentPage = $.trim($('.paginate_active').html());
            $('#pipelineDetailsPageNumber2').val(currentPage);
            $('#formDataExcel').val("false");
            document.DashboardSearchForm.submit();

            modalPreloader();
        }


        return false;
    });
});


function getDraftsRatio(viewType) {
    var request = {};
    request['formData.viewType'] = viewType;

    $.ajax({
       type: 'GET',
       url:  ctx + "/dashboardPipelineDraftsRatio",
       data: request,
       cache: false,
       dataType: 'json',
       success: function(jsonResult) {
           handleJsonResult(jsonResult,
                            function(result) {
                                draftsRatios[viewType] = true;
                                var count = result.length;
                                for (var i = 0; i < count; i++) {
                                    var o = result[i];
                                    var dcount = o['draftContestsCount'];
                                    var lcount = o['launchedContestsCount'];
                                    var lratio = o['launchedRatio'];
                                    var source = o['source'];
                                    $('.' + viewType + 'ScheduledContests').each(function(index) {
                                        var t = $.trim($(this).children().eq(0).html());
                                        if (t == source) {
                                            $(this).children().eq(4).html(lratio.toFixed(0) + '% (' + lcount + '/' + (dcount + lcount) +')');
                                        }
                                    });
                                }
                                $('.' + viewType + 'ScheduledContests').each(function(index) {
                                    var t = $.trim($(this).children().eq(4).html());
                                    if (t == 'Calculating...') {
                                        $(this).children().eq(4).html('');
                                    }
                                });
                            },
                            function(errorMessage) {
                                $('.' + viewType + 'ScheduledContests').each(function(index) {
                                    $(this).children().eq(4).html('Error');
                                });
                            });
       }
    });
}
