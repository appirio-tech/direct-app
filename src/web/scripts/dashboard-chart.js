// javascript functions for dashboard view chart
                                       
// define google visualization charts
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    var data = new google.visualization.DataTable();
    var chart = new google.visualization.LineChart($("#chart_div")[0]);
    var width = $(".visualization").width() - 5;

    // options for chart
    var options = {
        width: width,
        height: 240,
        legend:'bottom',
        colors:["#00aaff","#8cd81b"],
        vAxis:{
            minValue :0,
            textStyle : {color:"#333333",fontSize:11}

        },
        hAxis:{
            textStyle : {color:"#333333",fontSize:11}
        },
        legendTextStyle:{ color:"#000000",fontSize:11},
        tooltipTextStyle :  {color:"#333333",fontSize:11}    
    };
    
    var displaying = $(".visualization .displaying input:checked").val();
    var timeDimension = $("#timeDimension").val()==""?"month":$("#timeDimension").val();
   
    // Time Dimension changed
    $("#timeDimension").change(function(){ 
        timeDimension = $("#timeDimension").val()==""?"month":$("#timeDimension").val();
        parseChartData();
        renderChart();
    });

    //  cost, duration and fulfillment radio change
    $(".visualization .displaying input").change(function(){
        displaying = $(this).val();
        parseChartData(); 
        renderChart();
    });

    // window re-size - redraw the chart to adjust the page size
    $(window).resize(function(){
        width = $(".visualization").width();
        options.width = $(".visualization").width() - 5;
        parseChartData();
        renderChart();
    });

    $("#timeDimension").trigger("change");
    
    $('.zoomLink').click(function() {
        var value = $(this).attr('href');
        $('#zoomTypeInput').val(value);
        var formData = $('#EnterpriseDashboardForm').serialize();
        
        formData = formData.replace('&__multiselect_formData.projectIds=', '');
        formData = formData.replace('&__multiselect_formData.projectCategoryIds=', '');
        formData = formData.replace('&__multiselect_formData.customerIds=', '');
        formData = formData.replace('&__multiselect_formData.billingAccountIds=', '');
        
        loadStats(formData, 'dashboardEnterpriseZoom');
       
        return false;
    });
    
    $('#enterpriseDashboardSubmit').click(function() {
        // validate dates
        $('#validationErrors').html('');
        var d1 = new Date($('#startDateEnterprise').val());
        var d2 = new Date($('#endDateEnterprise').val());
        if (d1 > d2) {
            $('#validationErrors').html('Start date must not be after end date');
        } else {
            var formData = $('#EnterpriseDashboardForm').serialize();
        
            formData = formData.replace('&__multiselect_formData.projectIds=', '');
            formData = formData.replace('&__multiselect_formData.projectCategoryIds=', '');
            formData = formData.replace('&__multiselect_formData.customerIds=', '');
            formData = formData.replace('&__multiselect_formData.billingAccountIds=', '');
        
            loadStats(formData, 'dashboardEnterpriseAJAX');
            return false;
        }
    });

    
    // create chart data 
    function parseChartData() {
        data = new google.visualization.DataTable();
        var columnData = chartData[displaying]["column"]; // chartData is defined as json format in data/dashboard-chart.json
        $.each(columnData, function(index, item) {
            if (index == 0) {
                data.addColumn('string', item);
            } else {
                data.addColumn('number', item);
            }
        });
        if (chartData[displaying][timeDimension]) {
            var rowData = chartData[displaying][timeDimension];
            data.addRows(rowData.length);
            $.each(rowData, function(index, item) {
                data.setValue(index, 0, item["date"]);
                data.setValue(index, 1, item["customer"]);
                data.setValue(index, 2, item["tc"]);
            })
        }
    }
    
    function renderChart() {
        if (chartData['contest']['week'] && chartData['contest']['week'].length > 0) {
            $('#NoEnoughStats').hide();
            $('#chart_div').show();
            chart.draw(data, options);
        } else {
            $('#chart_div').hide();
            $('#NoEnoughStats').show();
        }
    }

    function loadStats(formData, formActionUrl) {
        $('#zoomMessage').html('Loading...').css('color', 'red').css('font-weight', 'bold');
        $.ajax({
                   type: 'get',
                   url:  formActionUrl,
                   data: formData,
                   cache: false,
                   dataType: 'json',
                   success: function(jsonResult) {
                       $('#zoomMessage').html('');
                       handleJsonResult(jsonResult,
                                        function(result) {
                                            $('#startDateLabel').html(result.periodStart);
                                            $('#endDateLabel').html(result.periodEnd);
                                            $('#avg1').html(result.avg1);
                                            $('#avg2').html(result.avg2);
                                            $('#avg3').html(result.avg3);

                                            $("#startDateEnterprise").datePicker().val(result.periodStartCalendar)
                                                    .trigger('change');
                                            $("#endDateEnterprise").datePicker().val(result.periodEndCalendar)
                                                    .trigger('change');

                                            chartData['contest'] = result.contest;
                                            chartData['cost'] = result.cost;
                                            chartData['fulfill'] = result.fulfill;
                                            parseChartData();
                                            renderChart();
                                            $('.chartSummary').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                            $('.chartWrapper,.graphArea .top').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                            $('#chart_div iframe').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                        },
                                        function(errorMessage) {
                                            $('#zoomMessage').html(errorMessage);
                                        });
                   }
               });

    }
}
