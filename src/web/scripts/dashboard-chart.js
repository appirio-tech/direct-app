// javascript functions for dashboard view chart
                                       
// define google visualization charts
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    var data = new google.visualization.DataTable();
    var chart = new google.visualization.LineChart($(".visualization .chart")[0]);
    var width = $(".visualization").width();

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
    }
    var displaying = $(".visualization .displaying input:checked").val();
    var timeDimension = $("#timeDimension").val()==""?"month":$("#timeDimension").val();

    // create chart data 
    function parseChartData(){
        data = new google.visualization.DataTable();
        var columnData = chartData[displaying]["column"];            // chartData is defined as json format in data/dashboard-chart.json
        var rowData = chartData[displaying][timeDimension];
        $.each(columnData, function(index, item){
            if(index == 0){
                data.addColumn('string', item);    
            }else{
                data.addColumn('number', item);
            }
        })
        data.addRows(rowData.length);
        $.each(rowData,function(index,item){
            data.setValue(index, 0, item["date"]);            data.setValue(index, 1, item["customer"]);
            data.setValue(index, 2, item["tc"]);
        })
    }
    
    // Time Dimension changed
    $("#timeDimension").change(function(){ 
        timeDimension = $("#timeDimension").val()==""?"month":$("#timeDimension").val();
        parseChartData();
        chart.draw(data,options);
    });

    //  cost, duration and fulfillment radio change
    $(".visualization .displaying input").change(function(){
        displaying = $(this).val();
        parseChartData(); 
        chart.draw(data,options);
    });

    // window resize - redraw the chart to adjust the page size
    $(window).resize(function(){
        width = $(".visualization").width();
        options.width = $(".visualization").width();
        parseChartData();
        chart.draw(data,options);
    })

    $("#timeDimension").trigger("change"); 
  
}