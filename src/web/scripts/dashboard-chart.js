/**
 * - Version 1.1 (TC Cockpit Enterprise Dashboard Update Cost Breakdown Assembly) Change Notes:
 * - Add support to the Contest Cost Breakdown and Market Cost Breakdown.
 *
 * - Version 1.2 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) Change Notes:
 * - Add the codes to fill in data for the new summary panel
 * - Add ajax loading indicator of the chart view drill-in operation
 * - Change the AJAX loading indicator for the contest breakdown and market breakdown view
 * - Version 1.2.1 (Release Assembly - TC Direct UI Improvement Assembly 3)
 * - Add codes to set the default 'Group By' to week when the user zooms to week
 */

// define google visualization charts
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    var data = new google.visualization.DataTable();
    var chart = new google.visualization.LineChart($("#chart_div")[0]);
    var width = $(".visualization").width() - 5;
    // a flag indicates whether we need to reload the contest cost break down data of customer
    var tableViewProjectIdsDirty = false;
    // a flag indicates whether we need to reload the contest cost break down data of market
    var allContestProjectIdsDirty = false;
    // a flag indicates whether we need to reload the market cost break down data
    var marketBreakDownDirty = false;
    var tableViewProjectIds = [];
    var allContestProjectIds = [];
    var oldProjectCategoryIds = [];
    var oldStartTime = "";
    var oldEndTime = "";
    var viewType = "";
    var timerange;
    // the contest break down data of market
    var allContestBreakDownData;
    // the contest break down data of customer
    var tableViewBreakDownData;

    // options for chart
    var options = {
        width: width,
        height: 240,
        legend:'bottom',
        colors:["#00aaff","#8cd81b"],
        pointSize: 7,
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

    var lastSelectedRow = -1; //for the last selected row number in the line chart
    var lastSelectedCol = -1; //or the last selected column number in the line chart
    var dirllTableDisplayed = false;//whether the drill-in table have been displayed.
    google.visualization.events.addListener(chart, 'select', function(){
        var selection = chart.getSelection();
        var row = selection[0].row;
        var column = selection[0].column;
        timeRange = chartData[displaying][timeDimension][row]["date"];
        var type = chartData[displaying]["column"][column].split(" ")[0];
        // if the coustomer data are the same with market data, then show the coustomer data
        if(chartData[displaying][timeDimension][row]["customer"] == chartData[displaying][timeDimension][row]["tc"]) {
            type = "Customer";
        }
        viewType = type;
        if(lastSelectedRow == row && lastSelectedCol == column) {
            if(dirllTableDisplayed) {
                $("#dynamicTableView").addClass("hide");
                dirllTableDisplayed = false;
            } else {
                $("#dynamicTableView").removeClass("hide");
                dirllTableDisplayed = true;
            }
        } else {
            $("#dynamicTableView").removeClass("hide");
            loadDrillTableData(timeRange, timeDimension, type);
            dirllTableDisplayed = true;
            lastSelectedRow = row;
            lastSelectedCol = column;
        }
        
    });
    function timeFilter(timeRange, time ,timeDim){
        var startEndDate = getStartEndDate(timeRange, timeDim);
        var startDayTime = Date.parse(startEndDate[0]);
        var endDayTime = Date.parse(startEndDate[1]);
        var specifiedTime = Date.parse(time);
        return specifiedTime >= startDayTime && specifiedTime <= endDayTime;
    }
    //get the date of nth day in a year
    function getDateOfDay(dayth, year) {
        var dayOfMonth = [31,28,31,30,31,30,31,31,30,31,30,31];
        var nameOfMonth = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
        if(year % 4 == 0){
            dayOfMonth[1] = 29;
        }
        var month = 1;
        var day = 0;
        for(var i=0; i<12; i++) {
            if(dayth > dayOfMonth[i]){
                month++;
                dayth -= dayOfMonth[i];
            } else {
                day = dayth;
                break;
            }
        }
        return nameOfMonth[month-1]+" "+day+", "+year;

    }
    function getStartEndDate(timeRange, timeDim) {
        var endDayOfMonth = {Jan:"31",Feb:"28",Mar:"31",Apr:"30",May:"31",Jun:"30",Jul:"31",Aug:"31",Sep:"30",Oct:"31",Nov:"30",Dec:"31"};
        var endDayOfQuarter = {Q1:"Mar 31",Q2:"Jun 30",Q3:"Sep 30",Q4:"Dec 31"};
        var startDayOfQuarter = {Q1:"Jan 1",Q2:"Apr 1",Q3:"Jul 1",Q4:"Oct 1"};
        var endDay="Jan 1,3000", startDay="Dec 31, 3000";
        if (timeDim == "week") {
            var year = timeRange.split(" ")[2];
            var weekth = parseInt(timeRange.split(" ")[0]);
            // week  day of the first day of the year, caculate this base on 2007.1.1 is monday.
            var weekDay = 1;
            for(var i=2008; i<=year; i++){
                if((i-1)%4 == 0){
                    weekDay += 2;
                } else {
                    weekDay++;
                }
            }
            weekDay = weekDay % 8 ;
            // day th of the year for specified week.
            var dayth = 0;
            var weeklen = 0;
            if(weekth == 1) {
                weeklen = 7-weekDay;
            } else {
                dayth = (7-weekDay+1) + (weekth-2)*7;
                weeklen = 6;
            }
            startDay = getDateOfDay(dayth+1, year);
            endDay = getDateOfDay(dayth+weeklen+1, year);
        } else if (timeDim == "month") {
            var month = timeRange.split(" ")[0];
            startDay = month + " 1, " + timeRange.split(" ")[1];
            endDay = month + " " + endDayOfMonth[month]+", " + timeRange.split(" ")[1];
        } else if (timeDim == "quarter") {
            var quarter = timeRange.split(" ")[0];
            startDay = startDayOfQuarter[quarter] + ", " + timeRange.split(" ")[1];
            endDay = endDayOfQuarter[quarter] + ", " + timeRange.split(" ")[1];
            } else if (timeDim == "year") {
            startDay = "Jan 1, " + timeRange;
            endDay = "Dec 31, " + timeRange;
        }
        return [startDay, endDay];
    }


    // chart the normal table view when ajax call is returned
    function chartNormalTableView() {
        var totalContestFullfilment = 0, totalMarketAvgFullfilment = 0;
        var totalContestCost = 0, totalMarketAvgCost = 0;
        var totalContestDuration = 0, totalMarketAvgDuration = 0;
        var reg1 = /\$/g,reg2 = /\,/g;
        var totalCompleted = 0;
        $("#firstDashboardTableBody table tbody tr").remove();
        $("#firstDashboardTableBody table tfoot").remove();
        tableViewProjectIds = [];
        viewType = "Customer";
        $(tableViewData).each(function(i){
            totalContestFullfilment += parseFloat(this.contestFullfilment);
            totalMarketAvgFullfilment += parseFloat(this.marketAvgFullfilment);
            if(parseFloat(this.contestFullfilment) != 0){
                totalCompleted++;
                totalContestCost += parseFloat(this.contestCost.replace(reg1,"").replace(reg2,""));
                totalContestDuration += parseFloat(this.contestDuration);
            }
            totalMarketAvgCost += parseFloat(this.marketAvgCost.replace(reg1,"").replace(reg2,""));
            totalMarketAvgDuration += parseFloat(this.marketAvgDuration);
            $("#firstDashboardTableBody table tbody").append(getOneRow(i, this, ""));
            tableViewProjectIds.push(this.projectId);
        });
        var totalNO = $(tableViewData).length;
        totalCompleted = totalCompleted == 0 ? 1: totalCompleted;
        if(totalNO > 0) {
            var totalTr = "<tfoot><tr><td colspan=5 class=\"alignLeft\">Average</td>";
            totalTr += "<td>"+ new Number(totalContestFullfilment/totalNO).toFixed(2) +"%</td>";
            totalTr += "<td class=\"fontGreen\">"+ new Number(totalMarketAvgFullfilment/totalNO).toFixed(2) +"%</td>";
            totalTr += "<td>$"+ new Number(totalContestCost/totalCompleted).toFixed(2) +"</td>";
            totalTr += "<td class=\"fontGreen\">$"+ new Number(totalMarketAvgCost/totalNO).toFixed(2) +"</td>";
            totalTr += "<td>"+ (totalContestDuration/totalCompleted).toFixed(2) +"</td>";
            totalTr += "<td class=\"fontGreen\">"+ (totalMarketAvgDuration/totalNO).toFixed(2) +"</td></tr></tfoot>";
            $("#firstDashboardTableBody table").append(totalTr);
        } else {
            var noNumTr = "<tr  style=\"height:378px;\"><td colspan=13>NO ENOUGH STATISTICS TO RENDER THE TABLE</td></tr>";
            $("#firstDashboardTableBody table tbody").append(noNumTr);
        }
        
        firstTablePagination.dataInit("#firstDashboardTableBody table", $("#firstDashboardTableFooter select").val(), "#firstDashboardTableFooter");
        $("#firstDashboardTableBody table").trigger("update");
    }

    // chart the table when click a point in the line chart
    function chartDrillTableView(timeRange, timeDim,type){
        var chartTableTbody = $(".tableViewChart table tbody");
        $(".tableViewChart table tbody tr").remove();
        $(".tableViewChart table tfoot").remove();
        var totalContestFullfilment = 0, totalMarketAvgFullfilment = 0;
        var totalContestCost = 0, totalMarketAvgCost = 0;
        var totalContestDuration = 0, totalMarketAvgDuration = 0;
        var reg1 = /\$/g,reg2 = /\,/g;
        var rowsNo = 0, colspan = 5, totalCompleted = 0;
        // data to render the drill-in table
        var data = tableViewData;
        if (type == "Market") {
            data = allContestData;
            allContestProjectIds = [];
            idsData = allContestProjectIds;
            if(!isAdmin) {
                if($("#secondDashboardTableHeader table colgroup col").length == 12) {
                    $($("#secondDashboardTableHeader table colgroup col")[1]).remove();
                    $($("#secondDashboardTableHeader table colgroup col")[1]).remove();
                    $($("#secondDashboardTableBody table colgroup col")[1]).remove();
                    $($("#secondDashboardTableBody table colgroup col")[1]).remove();
                    $($("#secondDashboardTableHeader table tbody tr td")[1]).remove();
                    $($("#secondDashboardTableHeader table tbody tr td")[1]).remove();
                }
                colspan = 3;
            }
        } else {
            tableViewProjectIds = [];
            idsData = tableViewProjectIds;
            if($("#secondDashboardTableHeader table colgroup col").length != 12) {
                $($("#secondDashboardTableHeader table colgroup col")[0]).after("<col width=\"10%\">");
                $($("#secondDashboardTableHeader table colgroup col")[1]).after("<col width=\"10%\">");
                $($("#secondDashboardTableBody table colgroup col")[0]).after("<col width=\"10%\">");
                $($("#secondDashboardTableBody table colgroup col")[1]).after("<col width=\"10%\">");
                $($("#secondDashboardTableHeader table tbody tr td")[0]).after("<td class=\"noBT\" rowspan=\"2\"><strong>Customer</strong></td>");
                $($("#secondDashboardTableHeader table tbody tr td")[1]).after("<td class=\"noBT\" rowspan=\"2\"><strong>Project</strong></td>");
            }
        }

        $(data).each(function(i){
            if(timeFilter(timeRange, this.date, timeDim)) {
                rowsNo++;
                totalContestFullfilment += parseFloat(this.contestFullfilment);
                totalMarketAvgFullfilment += parseFloat(this.marketAvgFullfilment);
                if(parseFloat(this.contestFullfilment) != 0 ) {
                    totalCompleted++;
                    totalContestCost += parseFloat(this.contestCost.replace(reg1,"").replace(reg2,""));
                    totalContestDuration += parseFloat(this.contestDuration);
                }
                totalMarketAvgCost += parseFloat(this.marketAvgCost.replace(reg1,"").replace(reg2,""));
                totalMarketAvgDuration += parseFloat(this.marketAvgDuration);
                $(chartTableTbody).append(getOneRow(i,this, type));
                idsData.push(this.projectId);
            }
        });
        if(rowsNo > 0) {
                totalCompleted = totalCompleted == 0 ? 1: totalCompleted;
                var totalTr = "<tfoot><tr><td colspan=" + colspan + " class=\"alignLeft\">Average</td>";
                totalTr += "<td>"+ new Number(totalContestFullfilment/rowsNo).toFixed(2) +"%</td>";
                totalTr += "<td class=\"fontGreen\">"+ new Number(totalMarketAvgFullfilment/rowsNo).toFixed(2) +"%</td>";
                totalTr += "<td>$"+ new Number(totalContestCost/totalCompleted).toFixed(2) +"</td>";
                totalTr += "<td class=\"fontGreen\">$"+ new Number(totalMarketAvgCost/rowsNo).toFixed(2) +"</td>";
                totalTr += "<td>"+ (totalContestDuration/totalCompleted).toFixed(2) +"</td>";
                totalTr += "<td class=\"fontGreen\">"+ (totalMarketAvgDuration/rowsNo).toFixed(2) +"</td></tr></tfoot>";
                $(".tableViewChart table").append(totalTr);
        } else {
            var noNumTr = "<tr  style=\"height:378px;\"><td colspan=13>NO ENOUGH STATISTICS TO RENDER THE TABLE</td></tr>";
            $(chartTableTbody).append(noNumTr);
        }
    }

    function getOneRow(index, elem ,type, firstClass, isCostBreakDown, breakDownMap) {
        if (!firstClass) {
            firstClass = "alignLeft";
        }
        var display = true , colspan="";
        var projectLink = "/direct/projectOverview.action?formData.projectId="+elem.directProjectId;
        var contestLink = "/direct/contest/detail.action?projectId="+elem.projectId;
        if(type == "Market" && !isAdmin) {
            display = false;
        }
        var tr="<tr ";
        if(index%2 == 0) {
            tr += "class=\"even\"";
        }
        tr += "><td class=\"" + firstClass + "\">"+ elem.date +"</td>";
        if(display) {
            tr += "<td class=\"alignLeft\"><span>"+ elem.customerName +"</span></td>";
            tr += "<td class=\"alignLeft\"><a href=\""+ projectLink +"\">"+ elem.projectName +"</a></td>";
        }
        tr += "<td class=\"alignLeft\"><a href=\""+ contestLink +"\">"+ elem.contestName +"</a></td>";
        tr += "<td class=\"alignLeft\"><span>"+ elem.contestType +"</span></td>";
        tr += "<td>"+ elem.contestFullfilment +"%</td>";
        tr += "<td class=\"fontGreen\">"+ elem.marketAvgFullfilment +"%</td>";
        tr += "<td>$"+ elem.contestCost +"</td>";
        tr += "<td class=\"fontGreen\">$"+ elem.marketAvgCost +"</td>";
        // render the cost break down data
        if (isCostBreakDown) {
            var breakdown = breakDownMap[parseInt(elem.projectId)];
            if (!breakdown) {
                breakdown = {contestFee : "0", prizes : "0.00", specReview : "0.00", review : "0.00", reliability : "0.00", digitalRun : "0.00",
                    copilot : "0.00", build : "0.00", bugs : "0.00", misc : "0.00"};
            }
            // tr += "<td>$" + breakdown.contestFee + "</td>";
            tr += "<td>$" + breakdown.prizes + "</td>";
            tr += "<td>$" + breakdown.specReview + "</td>";
            tr += "<td>$" + breakdown.review + "</td>";
            tr += "<td>$" + breakdown.reliability + "</td>";
            tr += "<td>$" + breakdown.digitalRun + "</td>";
            tr += "<td>$" + breakdown.copilot + "</td>";
            tr += "<td>$" + breakdown.build + "</td>";
            tr += "<td>$" + breakdown.bugs + "</td>";
            tr += "<td>$" + breakdown.misc + "</td>";
        }
        tr += "<td>"+ elem.contestDuration +"</td>";
        tr += "<td class=\"fontGreen\">"+ elem.marketAvgDuration +"</td></tr>";
        return tr;
    }
    

    // Time Dimension changed
    $("#timeDimension").change(function(){ 
        timeDimension = $("#timeDimension").val()==""?"month":$("#timeDimension").val();
        parseChartData();
        renderChart();
    });

    // cost, duration and fulfillment radio change
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

    // check whether the category ids have been changed, we need to reload the market
    // cost break down data if the category ids have been changed
    function checkCategoryIds() {
        var categoryIds = [];
        $("input[name='formData.projectCategoryIds']:checked").each(function() {
            categoryIds.push(parseInt(this.value));
        });
        var startTime = $("#startDateEnterprise").val();
        var endTime = $("#endDateEnterprise").val();
        if (oldProjectCategoryIds.toString() != categoryIds.toString()
            || oldStartTime != startTime || oldEndTime != endTime) {
            marketBreakDownDirty = true;
            oldProjectCategoryIds = categoryIds;
            oldStartTime = startTime;
            oldEndTime = endTime;
        }
    }

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

            if($("a.btnTable").hasClass("active")){
                loadNormalTableData("dashboardEnterpriseTableViewCall");
                $("a.fiterButton,.filterArea,.filterLinkArea").show();
            }

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
    $('a.btnTable').click(function() {
        $('.btnArea a').removeClass('active');
        $(this).addClass('active');
        $('.visualization').addClass('noBorder');
        $('#firstTableDataArea').show();
        $('.chartCollapse a.expand').html('Table View');
        $('.top,.chartWrapper,.tableResultFilter').hide();
        $("#dynamicTableView").addClass("hide");
        loadNormalTableData("dashboardEnterpriseTableViewCall");
        $('#firstDashboardTableBody table').css('width', $('#firstDashboardTableHeader table').width());
    });

    function loadNormalTableData(formActionUrl) {
        var formData = $('#EnterpriseDashboardForm').serialize();
        formData = formData.replace('&__multiselect_formData.projectIds=', '');
        formData = formData.replace('&__multiselect_formData.projectCategoryIds=', '');
        formData = formData.replace('&__multiselect_formData.customerIds=', '');
        formData = formData.replace('&__multiselect_formData.billingAccountIds=', '');
        $('#zoomMessage').html('Loading...').css('color', 'red').css('font-weight', 'bold');
        checkCategoryIds();
        $.ajax({
            type: 'get',
            url: formActionUrl,
            data: formData,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                $('#zoomMessage').html('');
                handleJsonResult(
                        jsonResult,
                        function(result){
                            tableViewData = result.contestStatus;
                            contestTypeAvgCost = result.contestTypeAvgCost;
                            tableViewProjectIdsDirty = true;
                            chartNormalTableView();
                        },
                        function(errorMessage) {
                            $('#zoomMessage').html(errorMessage);
                        });
            }
        });
    }
    //transform date format from "Aug 01, 2010" to "08/01/2010"
    function transformDate(date) {

        var dateMap = {Jan:"01",Feb:"02",Mar:"03",Apr:"04",May:"05",Jun:"06",Jul:"07",Aug:"08",Sep:"09",Oct:"10",Nov:"11",Dec:"12"};
        var month = date.split(",")[0].split(" ")[0];
        var day =  date.split(",")[0].split(" ")[1];
        var year = date.split(" ")[2];
        return dateMap[month]+"/"+day+"/"+year;
    }
    
    function isValidDrillInDate(date) {
        var day =  date.split(",")[0].split(" ")[1];
        var year = date.split(" ")[2];
        
        return !(isNaN(day) || isNaN(year));  
    }

    function loadDrillTableData(timeRange, timeDimension, type) {
        var formData = $('#EnterpriseDashboardForm').serialize();
        formData = formData.replace('&__multiselect_formData.projectIds=', '');
        formData = formData.replace('&__multiselect_formData.projectCategoryIds=', '');
        formData = formData.replace('&__multiselect_formData.customerIds=', '');
        formData = formData.replace('&__multiselect_formData.billingAccountIds=', '');
        var startEndDate = getStartEndDate(timeRange, timeDimension);
        formData += "&drillStartDate="+escape(transformDate(startEndDate[0]) + " 00:00:00");
        formData += "&drillEndDate="+escape(transformDate(startEndDate[1]) + " 23:59:59");
        
        if(!isValidDrillInDate(startEndDate[0]) || !isValidDrillInDate(startEndDate[1])) {
            $(".tableViewChart table tbody").append("<tr  style=\"height:378px;\"><td colspan=13>NO ENOUGH STATISTICS TO RENDER THE TABLE</td></tr>");
            
            // hide the vertical scrollbar
            $("#secondDashboardTableBody").css("overflow-y", "hidden");
            return;
        }
        
        $("#secondDashboardTableBody").css("overflow-y", "auto");

        var formActionUrl = "";
        if(type == "Market") {
            formActionUrl = "dashboardEnterpriseDrillTableCall";
        } else {
            formActionUrl = "dashboardEnterpriseTableViewCall";    
        }
        $('#zoomMessage').html('Loading...').css('color', 'red').css('font-weight', 'bold');
        showDrillInLoadingIndicator();
        checkCategoryIds();
        $.ajax({
            type: 'get',
            url: formActionUrl,
            data: formData,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                $('#zoomMessage').html('');
                $(".loadingIndicator").hide();
                handleJsonResult(
                        jsonResult,
                        function(result){
                            tableViewData = result.contestStatus;
                            allContestData = result.allContestStatus;
                            contestTypeAvgCost = result.contestTypeAvgCost;
                            tableViewProjectIdsDirty = true;
                            allContestProjectIdsDirty = true;
                            $("#secondDashboardTableBodyTable").show();
                            chartDrillTableView(timeRange, timeDimension, type);
                            $('#secondDashboardTableBody table').css('width', $('#secondDashboardTableHeader table').width());
                            secondTablePagination.dataInit("#secondDashboardTableBody table", $("#secondDashboardTableFooter select").val(), "#secondDashboardTableFooter");
                            //clear the click event first
                            $("#secondDashboardTableHeader table tr td").unbind("click");
                            attachTabeleSortEvent("second",type);
                            $("#secondDashboardTableBody table").trigger("update");
                        },
                        function(errorMessage) {
                            $('#zoomMessage').html(errorMessage);
                        });
            }
        });
    }

    /**
     * Show the AJAX loading indicator for the chart view drill-in.
     *
     * @since 1.2
     */
    function showDrillInLoadingIndicator() {
        $(".tableViewChart table tbody tr").remove();
        $(".tableViewChart table tfoot").remove();
        $(".tableViewChart table tfoot").hide();
        $("#secondDashboardTableBodyTable").hide();
        $(".loadingIndicator").css("margin-top", "100px");
        $(".loadingIndicator").css("margin-left", ($('#secondDashboardTableBody').width()/2 - 110) + 'px');
        $(".loadingIndicator").show();
    }

    function loadStats(formData, formActionUrl) {
        $('#zoomMessage').html('Loading...').css('color', 'red').css('font-weight', 'bold');
        checkCategoryIds();
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

                                            // display the customer summary data in the summary panel
                                            if(!result.hasCustomerSummary) {
                                                $("#customerSummaryData").hide();
                                                $("#customerSummaryNoData").show();
                                                $("#comparisonCustomerData").hide();
                                                $("#comparisonCustomerNoData").show();
                                            } else {
                                                $("#customerSummaryData").show();
                                                $("#customerSummaryNoData").hide();
                                                $("#comparisonCustomerData").show();
                                                $("#comparisonCustomerNoData").hide();

                                                // insert customer result data
                                                $("#customerAverageFulfillment, #customerAverageFulfillmentComparison").html(result.customerAverageFulfillment.toString() + '%');
                                                $("#customerAverageCost, #customerAverageCostComparison").html('$' + result.customerAverageCost.toString());
                                                $("#customerAverageDuration, #customerAverageDurationComparison").html(result.customerAverageDuration.toString() + ' days');
                                                $("#customerAverageVol, #customerAverageVolComparison").html(result.customerAverageVol.toString());
                                                $("#customerTotalVol, #customerTotalVolComparison").html(result.customerTotalVol.toString());
                                                $("#customerTotalCost, #customerTotalCostComparison").html('$' + result.customerTotalCost.toString());
                                                $("#customerMinMaxCost, #customerMinMaxCostComparison").html('$' + result.customerMinCost.toString() + " - $" + result.customerMaxCost.toString());
                                                $("#customerMinMaxDuration, #customerMinMaxDurationComparison").html(result.customerMinDuration.toString() + " - " + result.customerMaxDuration.toString() + ' days');
                                            }

                                            // display the market summary data in the summary panel
                                            if(!result.hasMarketSummary) {
                                                $("#marketSummaryData").hide();
                                                $("#marketSummaryNoData").show();
                                                $("#comparisonMarketData").hide();
                                                $("#comparisonMarketNoData").show();
                                            } else {
                                                $("#marketSummaryData").show();
                                                $("#marketSummaryNoData").hide();
                                                $("#comparisonMarketData").show();
                                                $("#comparisonMarketNoData").hide();

                                                // insert market result data
                                                $("#marketAverageFulfillment, #marketAverageFulfillmentComparison").html(result.marketAverageFulfillment.toString() + '%');
                                                $("#marketAverageCost, #marketAverageCostComparison").html('$' + result.marketAverageCost.toString());
                                                $("#marketAverageDuration, #marketAverageDurationComparison").html(result.marketAverageDuration.toString() + ' days');
                                                $("#marketAverageVol, #marketAverageVolComparison").html(result.marketAverageVol.toString());
                                                $("#marketTotalVol, #marketTotalVolComparison").html(result.marketTotalVol.toString());
                                                $("#marketTotalCost, #marketTotalCostComparison").html('$' + result.marketTotalCost.toString());
                                                $("#marketMinMaxCost, #marketMinMaxCostComparison").html('$' + result.marketMinCost.toString() + " - $" + result.marketMaxCost.toString());
                                                $("#marketMinMaxDuration, #marketMinMaxDurationComparison").html(result.marketMinDuration.toString() + " - " + result.marketMaxDuration.toString() + ' days');
                                            }

                                            // show the market cap
                                            if(result.hasMarketSummary && result.hasCustomerSummary) {
                                                 $("#marketCap").html(result.marketCap + "%");
                                                 $("#customerMarketCap").html(result.marketCap + "%");
                                            }

                                            $("#startDateEnterprise").datePicker().val(result.periodStartCalendar)
                                                    .trigger('change');
                                            $("#endDateEnterprise").datePicker().val(result.periodEndCalendar)
                                                    .trigger('change');

                                            chartData['contest'] = result.contest;
                                            chartData['cost'] = result.cost;
                                            chartData['fulfill'] = result.fulfill;
                                            contestTypeAvgCost = result.contestTypeAvgCost;
                                            parseChartData();
                                            renderChart();
                                            $('.chartSummary').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                            $('.chartWrapper,.graphArea .top').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                            $('#chart_div iframe').effect("highlight", {'color' : '#E1F2FF'}, 3000);
                                            if($("a.btnTable").hasClass("active")){
                                                $(".top,.chartWrapper").hide();
                                            }

											if(formActionUrl == 'dashboardEnterpriseZoom') {
                                                var vars = formData.split("&");
                                                for (var i = 0; i < vars.length; i++) {
                                                    var pair = vars[i].split("=");
                                                    if (pair[0] == 'zoomType' && pair[1] == 'WEEK') {
                                                        $("#timeDimension").val('week').change();
                                                    }
                                                }
                                            }
                                        },
                                        function(errorMessage) {
                                            $('#zoomMessage').html(errorMessage);
                                        });
                   }
               });

    }

    // expandView pop up
    $("#contestDViewMock").overlay({
        closeOnClick:false,
        mask: {
            color: '#000000',
            loadSpeed: 200,
            opacity: 0.6
        },
        top:"center",
        close :"#contestDViewClose",
        fixed : true,
        target : $("#contestDViewPopup"),
        onBeforeLoad : function(){
            var wWid = $(window).width()-40;
            var hHht = $(window).height() > 800 ? 750 :$(window).height()-50;
            $("#contestDViewPopup").css("width", "auto");
            $("#contestDViewPopup").css("max-width", wWid + "px");
            $("#contestDViewPopup").css("max-height", hHht + "px");
            $("#contestDViewPopup .dashboardTable").css("max-height", hHht-100 + "px"); 
        }
     });
    contestDViewApi =  $("#contestDViewMock").data("overlay");
    $("#marketDViewMock").overlay({
            closeOnClick:false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top:"center",
            close :"#marketDViewClose",
            fixed : true,
            target : $("#marketDViewPopup"),
            onBeforeLoad : function(){
                var wWid = $(window).width() > 1540?1500:$(window).width()-40;
                var hHht = $(window).height() > 800 ? 750 :$(window).height()-50;
                $("#marketDViewPopup").css("width", wWid + "px");
                $("#marketDViewPopup").css("max-height", hHht + "px");
                $(".marketTable").css("max-height", hHht-100 + "px");
            }
     });
    marketDViewApi =  $("#marketDViewMock").data("overlay");

    /**
     * Load contest break down data using ajax.
     *
     * @param projectIds the project ids of the contests
     * @param viewType the view type, can be Customer or Market
     */
    function loadContestBreakDownData(projectIds, viewType) {
        var data = {formData:{projectIds:projectIds}};
        modalPreloader();
        $.ajax({
            type: 'post',
            url: "dashboardGetCostBreakDownAJAX",
            data: data,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function(result){
                        modalClose();
                        if (viewType == "Market") {
                            allContestProjectIdsDirty = false;
                            allContestBreakDownData = result;
                        } else {
                            tableViewProjectIdsDirty = false;
                            tableViewBreakDownData = result;
                        }
                        renderContestBreakDown(viewType);
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    }

    /**
     * Loads market cost break down data using ajax.
     */
    function loadMarketBreakDownData() {
        var data = {formData:{projectCategoryIds:oldProjectCategoryIds, startDate:oldStartTime, endDate:oldEndTime}};
        modalPreloader();
        $.ajax({
            type: 'post',
            url: "dashboardGetCostBreakDownAJAX",
            data: data,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function(result){
                        modalClose();
                        marketBreakDownDirty = false;
                        marketBreakDownData = result;
                        renderMarketBreakDown();
                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    }

    function getNumber(price) {
        return parseFloat(price.replace(",", ""));
    }

    /**
     * Render the market cost break down data to the popup window.
     */
    function renderMarketBreakDown() {
        var breakDownMap = [];
        for (var i = 0; i < marketBreakDownData.length; i++) {
            breakDownMap[marketBreakDownData[i].id] = marketBreakDownData[i];
        }
        var chartTableTbody = $(".marketBreakDownChart table tbody");
        $(".marketBreakDownChart table tbody tr").remove();
        var rowsNo = 0;

        $("input[name='formData.projectCategoryIds']:checked").each(function(i) {
            var projectCategoryId = parseInt(this.value);
            var categoryName = $(this).parent().text();
            var breakdown = breakDownMap[projectCategoryId];
            if (!breakdown) {
                breakdown = {contestFee : "0", prizes : "0.00", specReview : "0.00", review : "0.00", reliability : "0.00", digitalRun : "0.00",
                    copilot : "0.00", build : "0.00", bugs : "0.00", misc : "0.00"};
            }
            var avgMemeberCost = getNumber(breakdown.prizes) + getNumber(breakdown.specReview) + getNumber(breakdown.review)
                            + getNumber(breakdown.reliability) + getNumber(breakdown.digitalRun) + getNumber(breakdown.copilot)
                            + getNumber(breakdown.build) + getNumber(breakdown.bugs) + getNumber(breakdown.misc);
            var tr = "<tr";
            if (rowsNo % 2 == 0) {
                tr += " class=\"even\"";
            }
            tr += ">";
            tr += "<td class='first'>" + categoryName + "</td>";
            tr += "<td class='fontGreen'>$" + avgMemeberCost.formatMoney(2) + "</td>";
            
            tr += "<td>$" + breakdown.prizes + "</td>";
            tr += "<td>$" + breakdown.specReview + "</td>";
            tr += "<td>$" + breakdown.review + "</td>";
            tr += "<td>$" + breakdown.reliability + "</td>";
            tr += "<td>$" + breakdown.digitalRun + "</td>";
            tr += "<td>$" + breakdown.copilot + "</td>";
            tr += "<td>$" + breakdown.build + "</td>";
            tr += "<td>$" + breakdown.bugs + "</td>";
            tr += "<td>$" + breakdown.misc + "</td></tr>";
            $(chartTableTbody).append(tr);
            
            rowsNo++;
        });
        if (rowsNo == 0) {
             var noNumTr = "<tr style=\"height:60px;\"><td colspan=\"12\" class=\"first\">NO ENOUGH STATISTICS TO RENDER THE TABLE</td></tr>";
            $(chartTableTbody).append(noNumTr);
        }
        attachTabeleSortEvent("fourth", "");
        $("#fourthDashboardTableBody table").trigger("update");
        marketDViewApi.load();
    }

    /**
     * Render the contests cost break down data to the popup window.
     *
     * @param viewType the view type, can be Customer or Market
     */
    function renderContestBreakDown(viewType) {
        var breakDownMap = [];
        if (viewType == "Market") {
            breakDownData = allContestBreakDownData;
        } else {
            breakDownData = tableViewBreakDownData;
        }
        for (var i = 0; i < breakDownData.length; i++) {
            breakDownMap[breakDownData[i].id] = breakDownData[i];
        }

        var chartTableTbody = $(".costBreakDownChart table tbody");
        $(".costBreakDownChart table tbody tr").remove();
        $(".costBreakDownChart table tfoot").remove();
        var rowsNo = 0;
        // data to render the cost breakdown table
        var data = tableViewData;
        var colspan = "5";
        if (viewType == "Market") {
            data = allContestData;
            if(!isAdmin) {
                if($("#thirdDashboardTableBody table colgroup col").length == 21) {

                    $($("#thirdDashboardTableBody table colgroup col")[1]).remove();
                    $($("#thirdDashboardTableBody table colgroup col")[1]).remove();
                    $($("#thirdDashboardTableBody table thead tr th")[1]).remove();
                    $($("#thirdDashboardTableBody table thead tr th")[1]).remove();
                }
                colspan = "3";
            }
        } else {
            if($("#thirdDashboardTableBody table colgroup col").length != 21) {
                $($("#thirdDashboardTableBody table colgroup col")[0]).after("<col width=\"94px\">");
                $($("#thirdDashboardTableBody table colgroup col")[1]).after("<col width=\"94px\">");
                $($("#thirdDashboardTableBody table thead tr th")[0]).after("<th class=\"noBT\" rowspan=\"2\"><strong>Customer</strong></th>");
                $($("#thirdDashboardTableBody table thead tr th")[1]).after("<th class=\"noBT\" rowspan=\"2\"><strong>Project</strong></th>");
            }
        }

        var totalContestFullfilment = 0, totalMarketAvgFullfilment = 0;
        var totalContestCost = 0, totalMarketAvgCost = 0;
        var totalContestDuration = 0, totalMarketAvgDuration = 0;
        var reg1 = /\$/g, reg2 = /\,/g;
        var totalCompleted = 0;
        var totalBreakdown = {contestFee : 0, prizes : 0.00, specReview : 0.00, review : 0.00, reliability : 0.00, digitalRun : 0.00,
                    copilot : 0.00, build : 0.00, bugs : 0.00, misc : 0.00};

        $(data).each(function(i){
            if($("a.btnTable").hasClass("active") || timeFilter(timeRange, this.date, timeDimension)) {
                rowsNo++;
                totalContestFullfilment += parseFloat(this.contestFullfilment);
                totalMarketAvgFullfilment += parseFloat(this.marketAvgFullfilment);
                if(parseFloat(this.contestFullfilment) != 0){
                    totalCompleted++;
                    totalContestCost += parseFloat(this.contestCost.replace(reg1,"").replace(reg2,""));
                    totalContestDuration += parseFloat(this.contestDuration);
                    for (var key in totalBreakdown) {
                        totalBreakdown[key] += parseFloat(breakDownMap[parseInt(this.projectId)][key].replace(reg1,"").replace(reg2,""));
                    }
                }
                totalMarketAvgCost += parseFloat(this.marketAvgCost.replace(reg1,"").replace(reg2,""));
                totalMarketAvgDuration += parseFloat(this.marketAvgDuration);
                $(chartTableTbody).append(getOneRow(i, this, viewType, "first", true, breakDownMap));
            }
        });
        totalCompleted = totalCompleted == 0 ? 1: totalCompleted;
        if(rowsNo > 0) {
            var totalTr = "<tfoot><tr><td colspan='" + colspan + "' class=\"alignLeft\">Average</td>";
            totalTr += "<td>"+ new Number(totalContestFullfilment/rowsNo).toFixed(2) +"%</td>";
            totalTr += "<td class=\"fontGreen\">"+ new Number(totalMarketAvgFullfilment/rowsNo).toFixed(2) +"%</td>";
            totalTr += "<td>$"+ new Number(totalContestCost/totalCompleted).toFixed(2) +"</td>";
            totalTr += "<td class=\"fontGreen\">$"+ new Number(totalMarketAvgCost/rowsNo).toFixed(2) +"</td>";

            // totalTr += "<td>$" + new Number(totalBreakdown.contestFee/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.prizes/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.specReview/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.review/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.reliability/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.digitalRun/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.copilot/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.build/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.bugs/totalCompleted).toFixed(2) + "</td>";
            totalTr += "<td>$" + new Number(totalBreakdown.misc/totalCompleted).toFixed(2) + "</td>";

            totalTr += "<td>"+ new Number(totalContestDuration/totalCompleted).toFixed(2) +"</td>";
            totalTr += "<td class=\"fontGreen\">"+ new Number(totalMarketAvgDuration/rowsNo).toFixed(2) +"</td></tr></tfoot>";
            $(".costBreakDownChart table").append(totalTr);
        } else {
            var noNumTr = "<tr style=\"height:60px;\"><td colspan=\"21\" class=\"first\">NO ENOUGH STATISTICS TO RENDER THE TABLE</td></tr>";
            $(chartTableTbody).append(noNumTr);
        }
        attachTabeleSortEvent("third", viewType);
        $("#thirdDashboardTableBody table").trigger("update");
        contestDViewApi.load();
    }

    /**
     * Event handler for the Contest Cost BreakDown button.
     */
    $("a.contestDlink").click(function(event) {
        event.stopPropagation();
        if (viewType == "Market") {
            dirty = allContestProjectIdsDirty;
            projectIds = allContestProjectIds;
        } else {
            dirty = tableViewProjectIdsDirty;
            projectIds = tableViewProjectIds;
        }
        if (dirty) {
            loadContestBreakDownData(projectIds, viewType);
        } else {
            renderContestBreakDown(viewType);
        }
        return false;
    });

    /**
     * Event handler for the Market Cost BreakDown button.
     */
    $("a.marketDlink").click(function(event) {
        event.stopPropagation();
        if (marketBreakDownDirty) {
            loadMarketBreakDownData();
        } else {
            renderMarketBreakDown();
        }
        return false;
    });
}


$(document).ready(function() {
    //add the table sort function
    attachTabeleSortEvent = function (order,type) {
        var paginationObj = order == "first" ? firstTablePagination : secondTablePagination;
        if (order == "third" || order == "fourth") {
            paginationObj = null;
        }
        var endtd = 5;
        if(!isAdmin && type == "Market") {
            endtd = 3;
        }
        if (order == "fourth") {
            endtd = 12;
        }
        var myTextExtraction = function(node)
        {
            return $.trim($(node).text());
        }

        if (order != "fourth") {
            $("#"+order+"DashboardTableBody table").tablesorter({
                textExtraction: myTextExtraction,
                headers :{
                    0: {
                        sorter: 'shortDate'
                    }
                }
            });
        } else {
            $("#"+order+"DashboardTableBody table").tablesorter({
                textExtraction: myTextExtraction,
                headers :{}
            });
        }

        var id = "#"+order+"DashboardTableHeader";
        if (order == "third" || order == "fourth") {
            id = "#"+order+"DashboardTableBody";
        }
        var firsel = id+" table tr:first-child td";
        if (order == "third" || order == "fourth") {
            firsel= id+" table thead tr:first-child th";
        }
        $(firsel).each(function(i){
            $(this).unbind('click');
            if(i<endtd) {
                var sortType = 0;
                $(this).click(function() {
                    var sorting = [[i, (sortType++)%2]];
                    $("#"+order+"DashboardTableBody table").trigger("sorton", [sorting]);
                    var rows =  $("#"+order+"DashboardTableBody table tbody tr");
                    rows.removeClass("even");
                    if (paginationObj) {
                        rows.addClass("hide");
                        for(var j=0 ;j<paginationObj.pagesize; j++){
                           $(rows[j]).removeClass("hide");
                        }
                    }
                    $("#"+order+"DashboardTableBody table tbody tr:even").addClass("even");
                });
            }
        });

        if (order != "fourth") {
            var firsel = id+" table tr:last-child td";
            if (order == "third") {
                firsel = id+" table thead tr:last-child th";
            }
            $(firsel).each(function(i){
                var sortType = 0;
                $(this).unbind('click');
                $(this).click(function() {
                    var sorting = [[i+endtd, (sortType++)%2]];
                    $("#"+order+"DashboardTableBody table").trigger("sorton", [sorting]);
                    var rows =  $("#"+order+"DashboardTableBody table tbody tr");
                    rows.removeClass("even");
                    if (paginationObj) {
                        rows.addClass("hide");
                        for(var j=0 ;j<paginationObj.pagesize; j++){
                           $(rows[j]).removeClass("hide");
                        }
                    }
                    $("#"+order+"DashboardTableBody table tbody tr:even").addClass("even");
                });
            });
        }
    }

    //add pagination function for the table
    function paginationforContest (tableid, pagesize, paginationId) {
        this.dataInit(tableid, pagesize, paginationId);
    }

    paginationforContest.prototype.page =  function(index){ // go to page 'index'
        if (index > this.maxPage) {
            return;
        }
        var start = this.pagesize*(index-1);
        var end =  this.pagesize*(index)-1;
        $(this.tableid + " tbody tr").each(function(i){
        if(i >= start && i <= end) {
            $(this).removeClass("hide");
            } else {
                $(this).addClass("hide");
            }
        });
        this.curPage = index;
        var tableSize = $(this.tableid + " tbody tr").length;
        var showStart = (this.curPage-1) * this.pagesize + 1;
        var showEnd = (showStart + this.pagesize -1 > tableSize)  ?  tableSize : (showStart + this.pagesize - 1);
        $(this.paginationId + " .panel3").text("Showing " + showStart +" to " + showEnd + " of "  + (showEnd-showStart+1) +" entries");

    }

    paginationforContest.prototype.next = function() { // go to next page
        if(this.curPage < this.maxPage) {
            this.page(++this.curPage);
            var pageLabel1 = $(this.paginationId +" .pages a")[1];
            var pageLabel2 = $(this.paginationId +" .pages a")[2];
            if($(pageLabel1).hasClass("current")) {
                $(pageLabel1).removeClass("current");
                $(pageLabel2).addClass("current");
            } else {
                $(pageLabel1).text(parseInt($(pageLabel1).text()) + 1);
                $(pageLabel2).text(parseInt($(pageLabel2).text()) + 1);
            }
        }

    }

    paginationforContest.prototype.prev = function() { // go to prev page
        if(this.curPage > 1) {
            this.page(--this.curPage);
            var pageLabel1 = $(this.paginationId +" .pages a")[1];
            var pageLabel2 = $(this.paginationId +" .pages a")[2];
            if($(pageLabel2).hasClass("current")) {
                $(pageLabel2).removeClass("current");
                $(pageLabel1).addClass("current");
            } else {
                $(pageLabel1).text(parseInt($(pageLabel1).text()) - 1);
                $(pageLabel2).text(parseInt($(pageLabel2).text()) - 1);
            }
        }
    }


    paginationforContest.prototype.dataInit = function(tableid, pagesize, paginationId) {
        var thisObj = this;
        var tableSize = $(tableid + " tbody tr").length;
        thisObj.tableid =  tableid; //the table which will add the pagination function
        if(pagesize == "All") {
            thisObj.pagesize =  tableSize; //diaplay all result in one page
            thisObj.maxPage = 1; // max page number is 1
        } else {
            thisObj.pagesize =  parseInt(pagesize);
            thisObj.maxPage = Math.ceil($(tableid + " tbody tr").length / this.pagesize);
        }
        $(paginationId + " .allPages").text(thisObj.maxPage + " Pages");
        var showingNumber = thisObj.pagesize;
        if(thisObj.pagesize > tableSize) {
            showingNumber = tableSize;    
        }
        if(thisObj.pagesize != 0) {
            $(paginationId + " .panel3").text("Showing 1 to " + showingNumber + " of "  + showingNumber +" entries");
        }
        thisObj.curPage =  1; //the default current page is 0
        thisObj.paginationId = paginationId;
        // if page number is 1 , inactive next, remove the second page item
        var pageLab1 = $(thisObj.paginationId + " .pages a")[1];
        var pageLab2 = $(thisObj.paginationId + " .pages a")[2];
        $(pageLab1).text("1");
        $(pageLab1).addClass("current");
        $(thisObj.paginationId + " .prev").addClass("prevInactive");
        if(thisObj.maxPage == 1){
            $(pageLab2).text("");
            $(thisObj.paginationId + " .next").addClass("nextInactive");
        } else {
            $(pageLab2).text("2");
            $(pageLab2).removeClass("current");
            $(thisObj.paginationId + " .next").removeClass("nextInactive");
        }

        $(thisObj.tableid + " tbody tr").removeClass("hide");
        var rows = $(thisObj.tableid + " tbody tr");
        for(var x=thisObj.pagesize ; x<rows.length; x++) {
            $(rows[x]).addClass("hide");
        }
    }

    
    paginationforContest.prototype.eventInit = function() {
        var thisObj = this;
        $(thisObj.paginationId + " .prev").click(function(){
            if(thisObj.curPage == thisObj.maxPage && thisObj.maxPage != 1){
                $(thisObj.paginationId + " .next").removeClass("nextInactive");
            }
            thisObj.prev();
            if(thisObj.curPage == 1){
                $(this).addClass("prevInactive");
            }
        });

        $(thisObj.paginationId + " .next").click(function(){
            if(thisObj.curPage == 1 && thisObj.maxPage != 1){
                $(thisObj.paginationId +" .prev").removeClass("prevInactive");
            }
            thisObj.next();
            if(thisObj.curPage == thisObj.maxPage){
                $(this).addClass("nextInactive");
            }
        });

        $(thisObj.paginationId + " .pages a").each(function(i){
            if(i == 1 || i == 2){
                $(this).click(function(){
                    $(thisObj.paginationId +" .pages a").removeClass("current");
                    $(this).addClass("current");
                    thisObj.curPage = $(this).text();
                    if(thisObj.curPage == 1) {
                        $(thisObj.paginationId + " .prev").addClass("prevInactive");
                    } else {
                        $(thisObj.paginationId + " .prev").removeClass("prevInactive");
                    }
                    if(thisObj.curPage == thisObj.maxPage) {
                        $(thisObj.paginationId + " .next").addClass("nextInactive");
                    } else {
                        $(thisObj.paginationId + " .next").removeClass("nextInactive");
                    }
                    thisObj.page($(this).text());

                });
            }
        });
    }



    firstTablePagination = new paginationforContest("#firstDashboardTableBody table", "All", "#firstDashboardTableFooter");
    firstTablePagination.eventInit();

    secondTablePagination = new paginationforContest("#secondDashboardTableBody table", "All", "#secondDashboardTableFooter");
    secondTablePagination.eventInit();

    //page size change function
    $("#firstDashboardTableFooter select").change(function(){
        firstTablePagination.dataInit("#firstDashboardTableBody table", $(this).val(), "#firstDashboardTableFooter");
    });

    $("#secondDashboardTableFooter select").change(function(){
        secondTablePagination.dataInit("#secondDashboardTableBody table",$(this).val(), "#secondDashboardTableFooter");
    });

    attachTabeleSortEvent("first","");

    var resizePopupId = -1;
    /**
     * Resize the popup windows to adapt for the new window size.
     */
    function resizePopup() {
        resizePopupId = -1;
        if (contestDViewApi && contestDViewApi.isOpened()) {
            contestDViewApi.close();
            setTimeout(function() {contestDViewApi.load();}, 300);
        }
        if (marketDViewApi && marketDViewApi.isOpened()) {
            marketDViewApi.close();
            setTimeout(function() {marketDViewApi.load();}, 300);
        }
    }
    $(window).resize(function(){
        $('#firstDashboardTableBody table').css('width', $('#firstDashboardTableHeader table').width());
        $('#secondDashboardTableBody table').css('width', $('#secondDashboardTableHeader table').width());
        if (resizePopupId != -1) {
            clearTimeout(resizePopupId);
        }
        resizePopupId = setTimeout(resizePopup, 100);
    });
});
