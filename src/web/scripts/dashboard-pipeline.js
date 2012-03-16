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
 * Version 1.4 (TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement ) updates
  * - Add ajax indicator for dropdown change and add group by and group values filter
 *
 * @author isv, Blues, GreatKevin, TCSASSEMBLER
 * @version 1.4
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

    if ($.browser.msie) {
        $('.clientsSelection input:checkbox').click(function () {
            this.blur();
            this.focus();
            var box = $(this).parent().parent();
            var checkedNumber = box.find("input.optionItem:checked").length;

            $("#formDatagroupValue").find(".multiSelectBox").empty();

            if ($(this).hasClass('optionAll')) {

                if (box.find("input.optionItem").length > 1 || box.find("input.optionItem").length <= 0) {
                    $("#formDatagroup").val(-1).attr('disabled', true);
                    return;
                } else {
                    var customerId = $.trim(box.find("input.optionItem").parent().find(".clientIdHolder").text());

                    if (customerId <= 0) {
                        $("#formDatagroup").val(-1).attr('disabled', true);
                        return;
                    }

                    box.find("input").attr('disabled', true);

                    loadGroupByOptions($("#formDatagroup"), customerId, function (result) {
                        $(".clientsSelection input").attr('disabled', false);
                    });
                    return;
                }
            }

            if (checkedNumber == 1) {
                var customerId = $.trim(box.find("input.optionItem:checked").parent().find(".clientIdHolder").text());

                if (customerId <= 0) {
                    $("#formDatagroup").val(-1).attr('disabled', true);
                    return;
                }

                box.find("input").attr('disabled', true);

                loadGroupByOptions($("#formDatagroup"), customerId, function (result) {
                    $(".clientsSelection input").attr('disabled', false);
                });

            } else {
                $("#formDatagroup").val(-1).attr('disabled', true);
            }
        });
    } else {
        $(".clientsSelection input:checkbox").change(function () {
            var box = $(this).parent().parent();
            var checkedNumber = box.find("input.optionItem:checked").length;

            $("#formDatagroupValue").find(".multiSelectBox").empty();

            if (checkedNumber == 1) {
                var customerId = $.trim(box.find("input.optionItem:checked").parent().find(".clientIdHolder").text());

                if (customerId <= 0) {
                    $("#formDatagroup").val(-1).attr('disabled', true);
                    return;
                }

                box.find("input").attr('disabled', true);

                loadGroupByOptions($("#formDatagroup"), customerId, function (result) {
                    $(".clientsSelection input").attr('disabled', false);
                });

            } else {
                $("#formDatagroup").val(-1).attr('disabled', true);
            }
        });
    }

    if ($(".showJustForm").text() == 'true') {
        $(".clientsSelection input").trigger('change');
    }

    if($(".clientsSelection input.optionItem:checked").length > 1) {
        $("#formDatagroupValue").find(".multiSelectBox").empty();
        $("#formDatagroup").val(-1).attr('disabled', true);
    } else {
        $("#formDatagroup").attr('disabled', false);
    }

    $("#formDatagroup").change(function(){

        var groupById = $(this).val();

        if(groupById < 0) {
            $("#formDatagroupValue").find(".multiSelectBox").empty();
            $("#formDatagroupValue").find(".reportWarningMessage").hide();
        } else {
            loadGroupValuesForGroup($("#formDatagroupValue").find(".multiSelectBox"), groupById, function(result){});
        }

    });

    // add a listener to submit the client ids too when submitting the pipeline form

    $("#DashboardSearchForm").submit(function(){

        // get the selected client id
        $(".clientsSelection input.optionItem:checked").each(function(){
           var clientId = $(this).parent().find(".clientIdHolder").text();
            $('<input />').attr('type', 'hidden')
                    .attr('name', 'formData.clientIds')
                    .attr('value', clientId)
                    .appendTo("#DashboardSearchForm");

        });

        return true;
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

        // check group values & group id
        if($("#formDatagroup").val() > 0 && $("#formDatagroupValue input:checked").length == 0) {
            $('#validationErrors').append("No Group Value is chosen<br/>");
        }

        if($(".clientsSelection input.optionItem:checked").length == 0) {
            $('#validationErrors').append("No Client is chosen<br/>");
        }

        if ($('#validationErrors').html() == '' || $('#validationErrors').html() == null) {
            var currentPage = $.trim($('.paginate_active').html());
            $('#pipelineDetailsPageNumber2').val(currentPage);
            $('#formDataExcel').val("false");
            modalPreloader();
            $("#DashboardSearchForm").submit();
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
