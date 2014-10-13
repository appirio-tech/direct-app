/**
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for new Enterprise Dashboard
 *
 * Version 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 *
 * Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
 * - Add js codes for the pipeline page and pipeline widget in the overview page.
 *
 * Version 1.2 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part)
 * - Add JS for the road map feature.
 *
 * Version 1.3 (Module Assembly - TC Cockpit Enterprise Dashboard New Active Contests)
 * - Add JS for the active contests
 *
 * Version 1.4 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 * - Add JS for the analysis tab
 *
 * Version 1.5 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 2)
 * - Add JS for analysis chart view to display contest duration and fulfillment data
 * - Add JS for loading volume view summary table
 * - Add JS for implementing table view in analysis page.
 *
 * Version 1.6 (Release Assembly - TopCoder Direct Cockpit Release Assembly Nine)
 * - use the correct sorting routine for the submission number in the active contests tab - active contests table
 *
 * Version 1.7 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * - Add JS for pipeline page - projects pipeline
 *
 * Version 1.8 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * - Add JS for enerprise dashboard total spend, contest / project pipeline drill-in.
 *
 * Version 1.9 （Release Assembly - TC Cockpit New Enterprise Dashboard Release 2）
 * - Fix all the issues in release 2
 * - Update filter synchronization logic
 * - Add push state and backward support for IE
 *
 * Version 2.0 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
 * - Add projected cost to total spend chart and total spend chart drill in
 * - Add project health page with financial health data
 *
 * Version 2.1 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
 * - Adjusts the empty row number projects widget in direct enterprise dashboard overview page
 *
 * @author GreatKevin, hanshuai, GreatKevin, TCSASSEMBLER
 * @version 2.1
 */
var shortNameMap = {
    "1" : ["Design" , "Design"],
    "2" :   ["Development", "Dev"],
    "6" :   ["Specification", "Spec"],
    "7" :   ["Architecture", "Archi"],
    "13" :  ["Test Suites", "Suites"],
    "14" :  ["Assembly  Competition", "Assembly"],
    "16" :  ["Banners/Icons","Banners/Icons"],
    "17" :  ["Web Design", "Web Design"],
    "18" :  ["Wireframes", "Wireframes"],
    "19" :  ["UI Prototypes", "Prototype"],
    "20" :  ["Logo Design" , "Logo"],
    "21" :  ["Print/Presentation", "Print"],
    "22" :  ["Idea Generation", "Idea"],
    "23" :  ["Conceptualization", "Concept"],
    "24" :  ["RIA Build" , "RIA Build"],
    "25" :  ["RIA Component" , "RIA component"],
    "26" :  ["Test Scenarios", "Scenarios"],
    "29" :  ["Copilot Posting" , "Copilot"],
    "30" :  ["Widget or Mobile Screen Design", "Widget"],
    "31" :  ["Front-End Flash" , "Flash"],
    "32" :  ["Application Front-End Design" , "App Design"],
    "34" :  ["Other" , "Other"],
    "35" :  ["Content Creation", "Content"],
    "36" :  ["Reporting" , "Reporting"],
    "37" :  ["Marathon Match" , "Marathon Match"],
    "9" :  ["Bug Hunt" , "Bug Hunt"],
    "38" :  ["First2Finish" , "First2Finish"],
    "39" :  ["Code" , "Code"],
    "40" : ["Design First2Finish", "Design First2Finish"]
};
var volumeTableLoaded;
var tableViewTable;
var tableViewTableTemplate;

var chart;
var financialTable;
var loadCurrentMonth;
var loadPreviousMonth;
var currentMonthLoaded;
var prevMonthLoaded;
var currentMonthTotal;
var prevMonthTotal;

// for filter panel synchronization
var projectToSync;
var projectFilterToSync;
var projectFilterValueToSync;

// for the analysis
var analysisData;

// the current selected customer name
var customerName;

var sStdMenu =
    '<em>|</em><span>Show: </span><select size="1" name="dataTableLength" id="dataTableLength">' +
        '<option value="10">10</option>' +
        '<option value="25">25</option>' +
        '<option value="50">50</option>' +
        '<option value="-1">All</option>' +
        '</select>';

var countMonth = function(d) {
    return d.getYear() * 12 + d.getMonth();
};

if (jQuery.fn.dataTableExt) {
    jQuery.fn.dataTableExt.oSort['financial-date-asc'] = function(a, b) {
        var x = Date.parse("01'" + a);
        var y = Date.parse("01'" + b);
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['financial-date-desc'] = function(a, b) {
        var x = Date.parse("01'" + a);
        var y = Date.parse("01'" + b);
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };
}


function getFilterSynParameters() {
    if($("#filterModal").length > 0) {
        var customer = $("#clientFilter").val();
        var project = $("#projectFilter").val();
        var status = $("#projectStatusFilter").val();
        var metaFilter = encodeURI($("#metaFilter").val());
        var metaValueFilter = encodeURI($("#metaValueFilter").val());
        var zoom = $("#zoomSelect").find("a.current").parent().attr('class');
        var startMonth = $(".timeLine .selectMonth:first span span").text();
        var endMonth = $(".timeLine .selectMonth:last span span").text();

        return $.param({'formData.clientId':customer, 'formData.directProjectId':project,'formData.projectStatusId':status,'formData.projectFilterId':metaFilter,'formData.projectFilterValue':metaValueFilter,'formData.zoom':zoom,'formData.startMonth':startMonth,'formData.endMonth':endMonth});
    }
}

function formatNum(strNum){
    if (strNum.length <= 3){
        return strNum;
    }
    if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)){
        return strNum;
    }
    var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
    var re = new RegExp();
    re.compile("(\\d)(\\d{3})(,|$)");
    while (re.test(b)){
        b = b.replace(re, "$1,$2$3");
    }
    return a + "" + b + "" + c;
}

if($.views) {
    $.views.helpers({
        formatMoney: formatMoney
    });
}


function formatMoney(strNum) {
    return '$' + formatNum(strNum);
}

function setScrollY(nTable, val) {
    var oSettings = nTable.fnSettings();
    oSettings.oScroll.sY = val;
    nTable.fnDraw(false);
}

function getFilterSelectorValue(filterName) {
    var filter = $("#" + filterName);
    if(filter.find("option").length == 0) {
        return 0;
    } else {
        return filter.val();
    }
}

function getFilterSelectorText(filterName) {
    var filter = $("#" + filterName);
    if(filter.find("option").length == 0) {
        return "None";
    } else {
        return filter.find("option:selected").text();
    }
}

function getEnterpriseDashboardRequest(pageSize, pageNumber, requireDate, noSync) {
    var formData = {};
    formData.clientId = getFilterSelectorValue('clientFilter');
    formData.directProjectId =  getFilterSelectorValue('projectFilter');
    formData.projectStatusId = getFilterSelectorValue('projectStatusFilter');
    formData.projectFilterId = getFilterSelectorValue('metaFilter');
    formData.projectFilterValue = $("#metaValueFilter").val();
    var startMonth = $(".timeLine .selectMonth:first span span").text();
    var endMonth = $(".timeLine .selectMonth:last span span").text();

    if(requireDate == true) {

    }

    if(requireDate == true) {
        formData.startMonth = startMonth;
        formData.endMonth = endMonth;
    }

    formData.pageSize = pageSize;
    formData.pageNumber = pageNumber;

    // render header part
    $("#headerClient").text(getFilterSelectorText('clientFilter'));
    $("#headerProject").html(getFilterSelectorText('projectFilter'));
    $("#headerProjectStatus").text(getFilterSelectorText('projectStatusFilter'));
    $("#headerFilter").html(getFilterSelectorText('metaFilter'));
    $("#headerFilterValue").html(getFilterSelectorText('metaValueFilter'));
    $("#headerDate").text(startMonth == "" ? "None" : (startMonth == endMonth ? startMonth : startMonth + ' - ' + endMonth));

    if(startMonth != "" && endMonth != "") {
        var currentMonth = countMonth(Date.today());
        var prevMonth = countMonth(Date.today().addMonths(-1));

        var startMonth = countMonth(Date.parse(startMonth));
        var endMonth = countMonth(Date.parse(endMonth));

        loadCurrentMonth = currentMonth < startMonth || currentMonth > endMonth;
        loadPreviousMonth = prevMonth < startMonth || prevMonth > endMonth;
    }

    if (!noSync) {
        var syncParameters = getFilterSynParameters();

        //history.pushState(syncParameters, "", getActionNameFromURL() + "?" + decodeURI(syncParameters));
        window.History.pushState(syncParameters, "TopCoder Cockpit - Enterprise Dashboard", "?" + decodeURI(syncParameters));

        // sync in left sidebar
        $("#silderBar a.filterSynEnabled, a.viewAllSync").each(function () {
            var url = $(this).attr("href").split("?")[0];
            if (history.pushState) {
                url = url + '?' + syncParameters;
            } else {
                url = url.split("#")[0]
                url = url + '#' + getActionNameFromURL($(this).attr("href")) + "?" + syncParameters;
            }

            $(this).attr("href", decodeURI(url));
        })


    }

    return formData;
}

function getActionNameFromURL(url) {
    var a = url.lastIndexOf('/');
    var b = url.indexOf('?');
    var actionName;
    if (b > 0) {
        actionName = url.substring(a+1, b);
    } else {
        actionName = url.substr(a + 1);
    }

    if(actionName.indexOf('#') != -1) {
        return actionName.split('#')[0];
    } else {
        return actionName;
    }
}

function renderCurrentPrevMonthSpend() {
    if(currentMonthLoaded && $("#spendThisMonth").length > 0) {
        $("#spendThisMonth").text("$ " + (currentMonthTotal ? formatNum(currentMonthTotal) : 0));
    }

    if(prevMonthLoaded && $("#spendLastMonth").length > 0) {
        $("#spendLastMonth").text("$ " + (prevMonthTotal ? formatNum(prevMonthTotal) : 0));
    }
}

function renderTotalSpendWidget(resultJson) {
    var arrayMonth = new Array();
    var arrayTotalSpend = new Array();
    var arrayProjected = new Array();
    var arrayTrend = new Array();
    var max = -1;
    var area = 'overviewTotalSpend';
    if(resultJson.length) {
        $.each(resultJson,function(idx,item){
            arrayMonth.push(item.label);
            arrayTotalSpend.push(item.spend/1);
            arrayProjected.push(item.projected/1);

            if((parseInt(item.spend) + parseInt(item.projected))  > max) {
                max = (parseInt(item.spend) + parseInt(item.projected));
            }

            arrayTrend.push(item.average/1);
        });
    }

    var interval = 2000;
    var maxY = 16000;

    if (max > 0) {
        interval = Math.ceil(max/16000) * 2000;
        maxY = interval * 9;
    }

    max = max + max / 2;
    max = Math.round(max/1000) * 1000;

    if(resultJson.length){
        $("#" + area).empty();
        chart = new Highcharts.Chart({
            chart: {
                renderTo: area,
                type:'column',
                zoomType: 'xy',
                marginTop: 25,
                marginLeft: 40,
                marginRight: 10,
                marginBottom: 20
            },
            credits: {
                text : ''
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: ''
            },
            xAxis: [{
                categories: arrayMonth,
                labels: {
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '10px',
                        color: '#898989'
                    }
                }
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    formatter: function() {
                        return this.value/1000 +'K';
                    },
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '10px',
                        color: '#898989'
                    }
                },
                title: {
                    text: ''
                },
                max: maxY,
                min: 0,
                tickInterval: interval,
                lineColor: '#cfcfcd',
                lineWidth: 1
            }, { // Secondary yAxis
                title: {
                    text: ''
                },
                labels: {
                    enabled: false,
                    style: {
                        color: '#4572A7'
                    }
                },
                opposite: false,
                max: maxY,
                min: 0,
                tickInterval: interval
            }],
            tooltip: {
                useHTML: true,
                backgroundColor: null,
                formatter:function () {
                    var s = '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                        this.point.category
                        + '</strong><div>' +
                        this.series.name
                        + ': $ ' +
                        formatNum(this.point.y);

                    if(this.point.stackTotal) {
                        s += '<br/>Projected Total: $ ' + formatNum(this.point.stackTotal);
                    }

                    s += '</div></div></div>';
                    return s;
                },
//                headerFormat: '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>{point.key}</strong>',
//                pointFormat: '<div>{series.name}: {point.y}</div>',
//                footerFormat: '</div></div>',
                borderRadius: 0,
                borderWidth: 0,
                shadow: false,
                valuePrefix: '$',
                style: {
                    margin: '0px',
                    padding: '0px',
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#333333'
                }
            },
            legend: {
                margin: 0,
                float: true,
                backgroundColor: '#ffffff',
                borderWidth: 0,
                borderRadius: 0,
                align: 'left',
                verticalAlign: 'top',
                y: -10,
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '10px',
                    color: '#898989'
                },
                symbolPadding: 5,
                symbolWidth: 12,
                width: '100%'
            },
            plotOptions: {
                column:{
                    stacking:'normal',
                    dataLabels:{
                        enabled:false,
                        color:'#ffffff',
                        style:{
                            fontFamily:'Arial',
                            fontSize:'11px',
                            fontWeight:'bold'
                        },
                        formatter:function () {
                            return null;
                        }
                    },
                    shadow:false,
                    borderColor:Highcharts.getOptions().borderColor
                },
                series: {
                    pointWidth: 9
                }
            },
            series: [{
                name: 'Total Spend',
                data: arrayTotalSpend
            }, {
                name: 'Projected Cost',
                data: arrayProjected
            }, {
                name: 'Moving Average (3 Months)',
                color: '#f30000',
                type: 'line',
                data: arrayTrend,
                marker: {
                    radius: 4,
                    fillColor: '#ff8585',
                    lineColor: 'ff0000'
                }
            }]
        });
    }else{
        $("#" + area).empty().append('<div class="noData">No data available</div>');
    }
};

function renderTotalSpendChart(resultJson) {
    var arrayMonth = new Array();
    var arrayTotalSpend = new Array();
    var arrayProjected = new Array();
    var arrayTrend = new Array();
    var max = -1;
    var area = 'chartTotalSpend';

    if(resultJson.length) {
        $.each(resultJson,function(idx,item){
            arrayMonth.push(item.label);
            arrayTotalSpend.push(item.spend/1);
            arrayProjected.push(item.projected/1);

            if((parseInt(item.spend) + parseInt(item.projected))  > max) {
                max = (parseInt(item.spend) + parseInt(item.projected));
            }

            arrayTrend.push(item.average/1);
        });
    }

    var interval = 2000;
    var maxY = 16000;

    if (max > 0) {
        interval = Math.ceil(max/16000) * 2000;
        maxY = interval * 9;
    }

    max = max + max / 2;
    max = Math.round(max/1000) * 1000;

    if(resultJson.length){
        $("#" + area).empty();
        chart = new Highcharts.Chart({
            chart: {
                renderTo: area,
                type:'column',
                zoomType: 'xy',
                marginTop: 20,
                marginLeft: 50,
                marginRight: 20,
                marginBottom: 55
            },
            credits: {
                text : ''
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: ''
            },
            xAxis: [{
                categories: arrayMonth,
                labels: {
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '12px',
                        color: '#898989'
                    }
                }
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    formatter: function() {
                        return this.value/1000 +'K';
                    },
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '12px',
                        color: '#898989'
                    }
                },
                title: {
                    text: ''
                },
                stackLabels:{
                    enabled:true,
                    formatter: function() {
                        return  this.total == 0 ? null : '$ ' + formatNum(this.total);
                    }
                },
                max: maxY,
                min: 0,
                tickInterval: interval,
                lineColor: '#cfcfcd',
                lineWidth: 1
            }, { // Secondary yAxis
                title: {
                    text: ''
                },
                labels: {
                    enabled: false,
                    style: {
                        color: '#4572A7'
                    }
                },
                opposite: false,
                max: maxY,
                min: 0,
                tickInterval: interval
            }],
            tooltip: {
                useHTML: true,
                backgroundColor: null,
                formatter:function () {
                    var s = '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                        this.point.category
                        + '</strong><div>' +
                        this.series.name
                        + ': $ ' +
                        formatNum(this.point.y);

                    if(this.point.stackTotal) {
                        s += '<br/>Projected Total: $ ' + formatNum(this.point.stackTotal);
                    }

                    s += '</div></div></div>';
                    return s;
                },
//                headerFormat: '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>{point.key}</strong>',
//                pointFormat: '<div>{series.name}: {point.y}<br/>Projected Total: $ {point.stackTotal}</div>',
//                footerFormat: '</div></div>',
                borderRadius: 0,
                borderWidth: 0,
                shadow: false,
                valuePrefix: '$',
                style: {
                    margin: '0px',
                    padding: '0px',
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#333333'
                }
            },
            legend: {
                margin: 0,
                float: true,
                backgroundColor: '#ebebeb',
                borderWidth: 0,
                borderRadius: 0,
                align: 'center',
                verticalAlign: 'bottom',
                y: 16,
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#898989'
                },
                symbolPadding: 5,
                symbolWidth: 12
            },
            plotOptions: {
                column:{
                    stacking:'normal',
                    dataLabels:{
                        enabled:true,
                        color:'#ffffff',
                        style:{
                            fontFamily:'Arial',
                            fontSize:'11px',
                            fontWeight:'bold'
                        },
                        formatter:function () {
                            return this.y == 0 ? null : '$ ' + formatNum(this.y);
                        }
                    },
                    shadow:false,
                    borderColor:Highcharts.getOptions().borderColor
                },
                series: {
                    events: {
                        click : function(event) {
                            if(!event.point.stackTotal) {
                                return;
                            }
                            $('#financialViewPopupMonth').text(event.point.category);
                            var requestData = getEnterpriseDashboardRequest(100000, 0, true);

                            // set start and end month to the drilled-in month
                            requestData.startMonth = event.point.category;
                            requestData.endMonth = event.point.category;

                            modalPreloader();

                            $.ajax({
                                type: 'POST',
                                url: ctx + "/enterpriseDashboard/getTotalSpendDrillIn",
                                data: {formData:requestData},
                                cache: false,
                                timeout:100000,
                                dataType: 'json',
                                success: function (jsonResult) {
                                    handleJsonResult(jsonResult,
                                        function (result) {
                                            if ($("#financialViewPopup .dataTables_wrapper").length > 0) {
                                                $("#financialDrillInTable").dataTable().fnDestroy();
                                            }
                                            $("#financialDrillInTable tbody").empty().html($("#financialDrillInTemplate").render(result));

                                            $("#financialViewPopup a.exportLink").attr('href', 'getTotalSpendDrillIn?' + $.param({formData: requestData, 'export': true})).text("Export to Excel (" + result.length + " records)");

                                            $("#financialViewHandler").data("overlay").load();
                                        },
                                        function (errorMessage) {
                                            showErrors(errorMessage);
                                        });
                                }
                            });

                        }
                    }
                }
            },
            series: [{
                name: 'Actual Cost',
                data: arrayTotalSpend
            }, {
                name: 'Projected Cost',
                data: arrayProjected
            }, {
                name: 'Moving Average (3 Months)',
                color: '#f30000',
                type: 'line',
                data: arrayTrend,
                marker: {
                    radius: 4,
                    fillColor: '#ff8585',
                    lineColor: 'ff0000'
                }
            }]
        });
    }else{
        $("#" + area).empty().append('<div class="noData">No data available</div>');
    }

    var totalSpend = 0;

    $.each(resultJson, function (idx, item) {
        totalSpend += parseInt(item.spend);
    });

    avarageMonthlySpend = totalSpend > 0 ? (totalSpend / resultJson.length).toFixed(1) : 0;
    var startMonth = $(".timeLine .selectMonth:first span span").text();
    var endMonth = $(".timeLine .selectMonth:last span span").text();
    var strDataCost = '';
    strDataCost += '<li>Spend This Month:<strong id="spendThisMonth">$ 0' + '</strong></li>';
    strDataCost += '<li>Spend Last Month:<strong id="spendLastMonth">$ 0' + '</strong></li>';
    strDataCost += '<li>Average Monthly Spend:<strong>$ ' + formatNum(avarageMonthlySpend) + '</strong></li>';
    strDataCost += '<li class="last">' + startMonth + ' - ' + endMonth  + ' Total Spend:<strong>$ ' + formatNum(totalSpend) + '</strong></li>';

    $(".totalSpendSection .numberSection ul").empty().append(strDataCost);

    if(!resultJson.length) {
        $("#financials tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></tr>');
        return;
    }
    var strData = '';
    var totalMemberCost = 0;
    var totalContestFee = 0;
    var totalSpend = 0;
    var totalProjected = 0;
    $.each(resultJson, function (idx, item) {
        strData += '<tr>';
        strData += '<td class="month">';
        strData += item.label;
        strData += '</td>';
        strData += '<td>$';
        strData += formatNum(item.memberCost)
        strData += '</td>';
        strData += '<td>$';
        strData += formatNum(item.contestFee);
        strData += '</td>';
        strData += '<td>$';
        strData += formatNum(item.spend);
        strData += '</td>';
        strData += '<td>$';
        strData += formatNum(parseFloat(item.spend) + parseFloat(item.projected));
        strData += '</td>';
        strData += '</tr>';
        totalMemberCost += parseFloat(item.memberCost);
        totalContestFee += parseFloat(item.contestFee);
        totalSpend += parseFloat(item.spend);
        totalProjected += (parseFloat(item.spend) + parseFloat(item.projected));
    });

    var totalData = '';

    totalData += '<tfoot><tr class="total">';
    totalData += '<td class="month">';
    totalData += "Total";
    totalData += '</td>';
    totalData += '<td>$';
    totalData += formatNum(totalMemberCost)
    totalData += '</td>';
    totalData += '<td>$';
    totalData += formatNum(totalContestFee);
    totalData += '</td>';
    totalData += '<td>$';
    totalData += formatNum(totalSpend);
    totalData += '</td>';
    totalData += '<td>$';
    totalData += formatNum(totalProjected);
    totalData += '</td>';
    totalData += '</tr></tfoot>';

    if (resultJson.length) {
        $("#financials").remove();
        var newTable = $($("#tableTemplate").html()).attr('id', 'financials');
        $(".tableData").empty().append(newTable);

        $("#financials tbody").empty().append(strData);
        $(totalData).insertAfter($("#financials tbody"));

        financialTable = $('#financials').dataTable({
            "iDisplayLength":-1,
            "bFilter":false,
            "bSort":true,
            "oLanguage":{
                "sLengthMenu":sStdMenu,
                "oPaginate":{
                    "sFirst":"",
                    "sPrevious":"Prev",
                    "sNext":"Next",
                    "sLast":""
                }
            },
            "sPaginationType":"full_numbers",
            "sDom":'',
            "aaSorting": [
                [0,'asc']
            ],
            "aoColumns": [
                { "sType": "financial-date" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" }
            ]
        });
        $("#financials").width('auto');
        //$("table tbody").append($(totalData)).insertAfter($("#financials"));
    } else {
        $("#financials tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></tr>');
    }
};

function renderOverviewFinancial(json) {

    if(!json.financials || !json.financials.length) {
        $('.overFinancialSection .ajaxTableLoader').hide();
        $('.overFinancialSection .dateSection').show();
        $(".financailSideBar ul li strong").removeClass('full');
        $("#overviewFinancailData tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></td></tr>');
        return;
    }

    var ajaxTableLoader, strData = '', strDataCost = '';
    var budgetedCostTotal = 0, actualCostTotal = 0, projectedCostTotal = 0, planedCostTotal = 0, monthCost = 0, lastMonthCost = 0, maxNum = 0;
    var maxLength = json.financials.length < 7 ? json.financials.length : 7;

    if (json.financials.length) {
        maxNum = parseInt(json.financials[0].totalBudget);

        for (i = 0; i < maxLength; i++) {
            if (parseInt(json.financials[i].totalBudget) > maxNum) {
                maxNum = parseInt(json.financials[i].totalBudget);
            }
        }

        for (i = 0; i < maxLength; i++) {

            var actualPrecent = 0;
            var projectPrecent = 0;
            var projectLink = "../projectOverview?formData.projectId=" + json.financials[i].projectId;
            strData += '<tr>';
            strData += '<td>';
            strData += '<a target="_blank" href="' + projectLink + '">' + json.financials[i].projectName + '</a>';
            strData += '</td>';

            // budget part
            if(json.financials[i].totalBudget > 0) {
                strData += '<td>';
                actualPrecent = json.financials[i].actualCost / json.financials[i].totalBudget;
                projectPrecent = json.financials[i].projectedCost / json.financials[i].totalBudget;
                if (json.financials[i].totalBudget / maxNum < 1) {
                    strData += '<div class="totalBudget" style="width:' + (json.financials[i].totalBudget / maxNum) * 100 + '%">';
                } else {
                    strData += '<div class="totalBudget">';
                }
                strData += '<div class="totalBudgetLeft">';
                strData += '<div class="totalBudgetRight">';
                if (projectPrecent >= 1) {
                    strData += '<div class="projectedCost red">';
                } else {
                    strData += '<div class="projectedCost">';
                }
                strData += '<div class="leftProjected">';
                strData += '<div class="rightProjected">';
                strData += '<div class="midProjected">' + projectPrecent + '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                if (actualPrecent < 0.7) {
                    strData += '<div class="actualCost green">';
                } else if (actualPrecent >= 0.7 && actualPrecent < 1) {
                    strData += '<div class="actualCost yellow">';
                } else {
                    strData += '<div class="actualCost red">';
                }
                strData += '<div class="leftActual">';
                strData += '<div class="rightActual">';
                strData += '<div class="midActual">' + actualPrecent + '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</td>';
            } else {
                strData += '<td class="alignCenter">';
                strData += '<i>project budget is not set</i>';
                strData += '</td>';
            }


            if(json.financials[i].totalBudget > 0) {
                strData += '<td class="alignCenter total"><strong>$' + formatNum(json.financials[i].totalBudget) + '</strong></td>';
            } else {
                var editProjectLink = "../editProject?formData.projectId=" + json.financials[i].projectId;
                strData += '<td class="alignCenter"><a href="' + editProjectLink + '">Set project budget</a></td>';
            }

            if (actualPrecent < 0.7) {
                strData += '<td class="alignCenter"><strong class="lessThan">$' + formatNum(json.financials[i].actualCost) + '</strong></td>';
            } else if (actualPrecent >= 0.7 && actualPrecent < 1) {
                strData += '<td class="alignCenter"><strong class="moreThan">$' + formatNum(json.financials[i].actualCost) + '</strong></td>';
            } else {
                strData += '<td class="alignCenter"><strong class="full">$' + formatNum(json.financials[i].actualCost) + '</strong></td>';
            }
            if (projectPrecent >= 1) {
                strData += '<td class="alignCenter lastTd"><strong class="full">$' + formatNum(json.financials[i].projectedCost) + '</strong></td>';
            } else {
                strData += '<td class="alignCenter lastTd"><strong>$' + formatNum(json.financials[i].projectedCost) + '</strong></td>';
            }
            strData += '</tr>';
        }
    }

    $.each(json.financials, function (idx, item) {
        budgetedCostTotal += parseInt(item.totalBudget);
        actualCostTotal += parseInt(item.actualCost);
        projectedCostTotal += parseInt(item.projectedCost);
        planedCostTotal += parseInt(item.plannedCost);
    });

    strDataCost += '<li>Budgeted Cost:<strong>$ ' + formatNum(budgetedCostTotal) + '</strong></li>';
    if (actualCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Actual Cost:<strong class="lessThan">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    } else if (actualCostTotal / budgetedCostTotal >= 0.7 && actualCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Actual Cost:<strong class="moreThan">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Actual Cost:<strong class="full">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    }
    if (projectedCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Projected Cost:<strong class="lessThan">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    } else if (projectedCostTotal / budgetedCostTotal >= 0.7 && projectedCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Projected Cost:<strong class="lessThan">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Projected Cost:<strong class="full">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    }
    if (planedCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Planned Cost:<strong class="lessThan">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    } else if (planedCostTotal / budgetedCostTotal >= 0.7 && planedCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Planned Cost:<strong class="lessThan">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Planned Cost:<strong class="full">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    }
    strDataCost += '<li>Spent Last Month:<strong class="lessThan" id="spendLastMonth">' + '</strong></li>';
    strDataCost += '<li class="last">Spent This Month:<strong class="lessThan" id="spendThisMonth">' + '</strong></li>';


    $(".financailSideBar ul").empty().append(strDataCost);
    $('.overFinancialSection .ajaxTableLoader').hide();
    $('.overFinancialSection .dateSection').show();
    if (json.financials.length) {
        $("#overviewFinancailData tbody").empty().append(strData);
        $("#overviewFinancailData tbody tr:last").addClass('last');
        $('#overviewFinancailData .midProjected').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#overviewFinancailData .midActual').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#overviewFinancailData tbody tr:odd').addClass('odd');
    } else {
        $(".financailSideBar ul li strong").removeClass('full');
        $("#overviewFinancailData tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></td></tr>');
    }
}

function renderFinancial(json) {

    if(!json.financials || !json.financials.length) {
        $("#financials tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></tr>');
        return;
    }

    var strData = '', strDataCost = '';
    var budgetedCostTotal = 0, actualCostTotal = 0, projectedCostTotal = 0, planedCostTotal = 0, monthCost = 0, lastMonthCost = 0, totalNumber = 0, maxNum = 0;
    if (json.financials.length) {

        $.each(json.financials, function (idx, item) {
            if (parseInt(item.totalBudget) > maxNum) {
                maxNum = parseInt(item.totalBudget);
            }
        });

        $.each(json.financials, function (idx, item) {
            var actualPrecent = 0;
            var projectPrecent = 0;
            var projectLink = "../projectOverview?formData.projectId=" + item.projectId;

            strData += '<tr>';
            strData += '<td>';
            strData += '<a target="_blank" href="' + projectLink + '">' + item.projectName + '</a>';
            strData += '</td>';

            if(item.totalBudget > 0) {

                actualPrecent = item.actualCost / item.totalBudget;
                projectPrecent = item.projectedCost / item.totalBudget;

                strData += '<td>';
                if (item.totalBudget / maxNum < 1) {
                    strData += '<div class="totalBudget" style="width:' + (item.totalBudget / maxNum) * 100 + '%">';
                } else {
                    strData += '<div class="totalBudget">';
                }
                strData += '<div class="totalBudgetLeft">';
                strData += '<div class="totalBudgetRight">';
                if (projectPrecent >= 1) {
                    strData += '<div class="projectedCost red">';
                } else {
                    strData += '<div class="projectedCost">';
                }
                strData += '<div class="leftProjected">';
                strData += '<div class="rightProjected">';
                strData += '<div class="midProjected">' + projectPrecent + '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                if (actualPrecent < 0.7) {
                    strData += '<div class="actualCost green">';
                } else if (actualPrecent >= 0.7 && actualPrecent < 1) {
                    strData += '<div class="actualCost yellow">';
                } else {
                    strData += '<div class="actualCost red">';
                }
                strData += '<div class="leftActual">';
                strData += '<div class="rightActual">';
                strData += '<div class="midActual">' + actualPrecent + '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</div>';
                strData += '</td>';
            } else {
                strData += '<td class="alignCenter">';
                strData += '<i>project budget is not set</i>';
                strData += '</td>';
            }

            if(item.totalBudget > 0) {
                strData += '<td class="alignCenter total">$' + formatNum(item.totalBudget) + '</td>';
            } else {
                var editProjectLink = "../editProject?formData.projectId=" + item.projectId;
                strData += '<td class="alignCenter"><a href="' + editProjectLink + '">Set project budget</a></td>';
            }

            if (actualPrecent < 0.7) {
                strData += '<td class="alignCenter"><strong class="lessThan">$' + formatNum(item.actualCost) + '</strong></td>';
            } else if (actualPrecent >= 0.7 && actualPrecent < 1) {
                strData += '<td class="alignCenter"><strong class="moreThan">$' + formatNum(item.actualCost) + '</strong></td>';
            } else {
                strData += '<td class="alignCenter"><strong class="full">$' + formatNum(item.actualCost) + '</strong></td>';
            }
            if (projectPrecent >= 1) {
                strData += '<td class="alignCenter lastTd"><strong class="full">$' + formatNum(item.projectedCost) + '</strong></td>';
            } else {
                strData += '<td class="alignCenter lastTd"><strong>$' + formatNum(item.projectedCost) + '</strong></td>';
            }
            strData += '</tr>';
        });
    }

    $.each(json.financials, function (idx, item) {
        budgetedCostTotal += parseInt(item.totalBudget);
        actualCostTotal += parseInt(item.actualCost);
        projectedCostTotal += parseInt(item.projectedCost);
        planedCostTotal += parseInt(item.plannedCost);
    });

    totalNumber = json.financials.length;

    strDataCost += '<li>Budgeted Cost:<strong>$ ' + formatNum(budgetedCostTotal) + '</strong></li>';
    if (actualCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Actual Cost:<strong class="lessThan">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    } else if (actualCostTotal / budgetedCostTotal >= 0.7 && actualCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Actual Cost:<strong class="moreThan">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Actual Cost:<strong class="full">$ ' + formatNum(actualCostTotal) + '</strong></li>';
    }
    if (projectedCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Projected Cost:<strong class="lessThan">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    } else if (projectedCostTotal / budgetedCostTotal >= 0.7 && projectedCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Projected Cost:<strong class="lessThan">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Projected Cost:<strong class="full">$ ' + formatNum(projectedCostTotal) + '</strong></li>';
    }
    if (planedCostTotal / budgetedCostTotal < 0.7) {
        strDataCost += '<li>Planned Cost:<strong class="lessThan">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    } else if (planedCostTotal / budgetedCostTotal >= 0.7 && planedCostTotal / budgetedCostTotal < 1) {
        strDataCost += '<li>Planned Cost:<strong class="lessThan">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    } else {
        strDataCost += '<li>Planned Cost:<strong class="full">$ ' + formatNum(planedCostTotal) + '</strong></li>';
    }
    strDataCost += '<li>Spent Last Month:<strong class="lessThan" id="spendLastMonth">' + '</strong></li>';
    strDataCost += '<li class="last">Spent This Month:<strong class="lessThan" id="spendThisMonth">' + '</strong></li>';

    $(".financialsSection .numberSection ul").empty().append(strDataCost);
    if (json.financials.length) {

        $("#financials tbody").empty().append(strData);
        $(".financialsSection .pagePanel .pageTotal span.totalNumber").text(totalNumber);
        $('#financials .midProjected').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#financials .midActual').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#financials tbody tr:odd').addClass('odd');

            financialTable = $('#financials').dataTable({
                "iDisplayLength":10,
                "bFilter":false,
                "bSort":false,
                "oLanguage":{
                    "sLengthMenu":sStdMenu,
                    "oPaginate":{
                        "sFirst":"",
                        "sPrevious":"Prev",
                        "sNext":"Next",
                        "sLast":""
                    }
                },
                "sPaginationType":"full_numbers",
                "sDom":'<"pagePanel topPagePanel"i<"showPage"l><"pageNum"p>>t<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>',
                "fnFooterCallback": function (nRow, aaData, iStart, iEnd, aiDisplay) {
                    var iTotalBudget = 0, iTotalActual = 0, iTotalProjected = 0;
                    for (var i = 0; i < aaData.length; i++) {
                        if(!isNaN(aaData[i][2].replace(removeMoneySymbolsReg, '') * 1)) {
                            iTotalBudget += aaData[i][2].replace(removeMoneySymbolsReg, '') * 1;
                        }

                        iTotalActual += aaData[i][3].replace(/(<([^>]+)>)/ig, "").replace(removeMoneySymbolsReg, '') * 1;
                        iTotalProjected += aaData[i][4].replace(/(<([^>]+)>)/ig, "").replace(removeMoneySymbolsReg, '') * 1;
                    }

                    /* Modify the footer row to match what we want */
                    var nCells = nRow.getElementsByTagName('td');
                    nCells[1].innerHTML = '$ ' + parseFloat(iTotalBudget).formatMoney(2);
                    nCells[2].innerHTML = '$ ' + parseFloat(iTotalActual).formatMoney(2);
                    nCells[3].innerHTML = '$ ' + parseFloat(iTotalProjected).formatMoney(2);


                }
            });
            $("#financials").width('auto');
    } else {
        $("#financials tbody").empty().append('<tr><td colspan="5"><div class="noData">No data available</div></td></tr>');
    }
};

function renderPipelineWidget(resultJson) {
    var xAxisCate = new Array();
    var finishedData = new Array();
    var activeData = new Array();
    var draftData = new Array();
    var failedData = new Array();
    var scheduledData = new Array();
    var max = 0;
    if (resultJson.length) {
        for (var i = 0; i < resultJson.length; i++) {
            xAxisCate.push(resultJson[i].date);
            finishedData.push(parseInt(resultJson[i].completedContests) == 0 ? null : parseInt(resultJson[i].completedContests));
            activeData.push(parseInt(resultJson[i].activeContests) == 0 ? null : parseInt(resultJson[i].activeContests));
            draftData.push(parseInt(resultJson[i].draftContests) == 0 ? null : parseInt(resultJson[i].draftContests));
            failedData.push(parseInt(resultJson[i].failedContests) == 0 ? null : parseInt(resultJson[i].failedContests));
            scheduledData.push(parseInt(resultJson[i].scheduledContests) == 0 ? null : parseInt(resultJson[i].scheduledContests));

            var tmp = parseInt(resultJson[i].completedContests) + parseInt(resultJson[i].activeContests)
                + parseInt(resultJson[i].draftContests) + parseInt(resultJson[i].failedContests) + parseInt(resultJson[i].scheduledContests);

            if (tmp > max) {
                max = tmp;
            }
        }
    }

    var pointWidth = 30;

    if(xAxisCate.length > 6 && xAxisCate.length < 10 ) {
        pointWidth = 25;
    } if (xAxisCate.length >= 10) {
        pointWidth = 20;
    }

    if (resultJson.length) {
        chart = new Highcharts.Chart({
            chart:{
                renderTo:'overviewPipeline',
                type:'column',
                marginTop:10,
                marginLeft:14,
                marginRight:14,
                marginBottom:20
            },
            credits:{
                text:''
            },
            navigation:{
                buttonOptions:{
                    enabled:false
                }
            },
            title:{
                text:null
            },
            xAxis:{
                categories:xAxisCate,
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'9px',
                        color:'#898989'
                    },
                    formatter:function () {
                        return this.value.substring(5, 8).toUpperCase() + '\'' + parseInt(this.value).toString().substring(2, 4);
                    }
                }
            },
            yAxis:{
                min:0,
                title:{
                    text:null
                },
                stackLabels:{
                    enabled:true
                },
                labels:{
                    enabled:false
                },
                gridLineWidth:0,
                max : max + max/3
            },
            legend:{
                align:'left',
                verticalAlign:'top',
                borderWidth:0,
                borderRadius:0,
                y:-10,
                x:-10,
                width:400,
                itemStyle:{
                    fontFamily:'Arial',
                    fontSize:'9px',
                    color:'#898989'
                },
                labelFormatter:function () {
                    return this.name.replace('Contests', '');
                },
                reversed:true
            },
            tooltip:{
                useHTML:true,
                backgroundColor:null,
                borderRadius:0,
                borderWidth:0,
                shadow:false,
                style:{
                    margin:'0px',
                    padding:'0px',
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#333333'
                },
                formatter:function () {
                    return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                        parseInt(this.point.category) + ' ' + this.point.category.substring(5)
                        + '</strong><div>' +
                        this.series.name
                        + ':' +
                        this.point.y
                        + '</div></div></div>';
                }
            },
            plotOptions:{
                column:{
                    stacking:'normal',
                    dataLabels:{
                        enabled:true,
                        color:'#ffffff',
                        style:{
                            fontFamily:'Arial',
                            fontSize:'11px',
                            fontWeight:'bold'
                        },
                        formatter:function () {
                            return this.y == 0 ? null : this.y;
                        }
                    },
                    shadow:false,
                    borderColor:Highcharts.getOptions().borderColor
                },
                series:{
                    pointWidth:pointWidth
                }
            },
            series:[
                {
                    name:'Failed Challenges',
                    data:failedData,
                    color:'#e83308',
                    borderColor:'#a80b00'
                },
                {
                    name:'Draft Challenges',
                    data:draftData,
                    color:'#939393',
                    borderColor:'#777777'
                },
                {
                    name:'Active Challenges',
                    data:activeData,
                    color:'#2fcafa',
                    borderColor:'#0096c7'
                },
                {
                    name:'Scheduled Challenges',
                    data:scheduledData,
                    color:'#FF850B',
                    borderColor:'#FF850B'
                },
                {
                    name:'Finished Challenges',
                    data:finishedData,
                    color:'#2f9c0d',
                    borderColor:'#187000'
                }
            ]
        });
    } else {
        $('#overviewPipeline').empty().append('<div class="noData">No data available</div>');
    }
}

function renderPipelinePage(resultJson) {
    var xAxisCate = new Array();
    var finishedData = new Array();
    var activeData = new Array();
    var draftData = new Array();
    var failedData = new Array();
    var scheduledData = new Array();
    var finishedDataTotal = 0, activeDataTotal = 0, draftDataTotal = 0, failedDataTotal = 0, scheduledDataTotal = 0;
    $.each(resultJson, function (idx, item) {
        finishedDataTotal += parseInt(item.completedContests);
        activeDataTotal += parseInt(item.activeContests);
        draftDataTotal += parseInt(item.draftContests);
        failedDataTotal += parseInt(item.failedContests);
        scheduledDataTotal += parseInt(item.scheduledContests);
    });

    if (resultJson.length) {
        $.each(resultJson, function (idx, item) {
            xAxisCate.push(item.date);
            finishedData.push(parseInt(item.completedContests) == 0 ? null : parseInt(item.completedContests));
            activeData.push(parseInt(item.activeContests) == 0 ? null : parseInt(item.activeContests));
            draftData.push(parseInt(item.draftContests) == 0 ? null : parseInt(item.draftContests));
            failedData.push(parseInt(item.failedContests) == 0 ? null : parseInt(item.failedContests));
            scheduledData.push(parseInt(item.scheduledContests) == 0 ? null : parseInt(item.scheduledContests));
        });
    }

    $('.pipelineSection .contestsPipeline ul').empty().append('<li class="finished">Total Finished Challenges<strong>' + finishedDataTotal + '</strong></li><li class="active">Total Active Challenges<strong>' + activeDataTotal + '</strong></li><li class="scheduled">Total Scheduled Challenges<strong>' + scheduledDataTotal + '</strong></li><li class="draft">Total Draft Challenges<strong>' + draftDataTotal + '</strong></li><li class="last failed">Total Failed Challenges<strong>' + failedDataTotal + '</strong></li>');
    if (resultJson.length) {
        chart = new Highcharts.Chart({
            chart:{
                renderTo:'pipelineChart',
                type:'column',
                marginBottom:60
            },
            credits:{
                text:''
            },
            navigation:{
                buttonOptions:{
                    enabled:false
                }
            },
            title:{
                text:null
            },
            xAxis:{
                categories:xAxisCate,
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    },
                    formatter:function () {
                        return this.value.substring(5, 8).toUpperCase() + '\'' + parseInt(this.value).toString().substring(2, 4);
                    }
                },
                tickLength:0
            },
            yAxis:{
                min:0,
                title:{
                    text:null
                },
                stackLabels:{
                    enabled:true
                },
                labels:{
                    enabled:false
                },
                gridLineWidth:0
            },
            legend:{
                align:'center',
                verticalAlign:'bottom',
                y:16,
                backgroundColor:'#ebebeb',
                borderColor:'#CCC',
                borderWidth:1,
                borderRadius:0,
                symbolPadding:6,
                itemWidth:150,
                itemStyle:{
                    fontFamily:'Arial',
                    fontWeight:'bold',
                    fontSize:'11px',
                    color:'#898989'
                },
                reversed:true
            },
            tooltip:{
                useHTML:true,
                backgroundColor:null,
                borderRadius:0,
                borderWidth:0,
                shadow:false,
                style:{
                    margin:'0px',
                    padding:'0px',
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#333333'
                },
                formatter:function () {
                    return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                        parseInt(this.point.category) + ' ' + this.point.category.substring(5)
                        + '</strong><div>' +
                        this.series.name
                        + ': ' +
                        this.point.y
                        + '</div></div></div>';
                }
            },
            plotOptions:{
                column:{
                    stacking:'normal',
                    dataLabels:{
                        enabled:true,
                        color:'#ffffff',
                        style:{
                            fontFamily:'Arial',
                            fontSize:'11px',
                            fontWeight:'bold'
                        },
                        formatter:function () {
                            return this.y == 0 ? null : this.y;
                        }
                    },
                    shadow:false,
                    borderColor:Highcharts.getOptions().borderColor
                },
                series:{
                    pointWidth:48,
                    events: {
                        click : function(event) {
                            $('#contestPipelineViewPopupMonth').text(event.point.category);
                            var requestData = getEnterpriseDashboardRequest(100000, 0, true);
                            // set start and end month to the drilled-in month
                            var currentMonth = event.point.category;
                            requestData.startMonth = currentMonth.substring(5, 8).toUpperCase() + '\'' + parseInt(currentMonth).toString().substring(2, 4);
                            requestData.endMonth = requestData.startMonth;

                            modalPreloader();

                            $.ajax({
                                type: 'POST',
                                url:  ctx + "/enterpriseDashboard/getContestsPipelineDrillIn",
                                data: {formData:requestData},
                                cache: false,
                                timeout:100000,
                                dataType: 'json',
                                success: function(jsonResult) {
                                    handleJsonResult(jsonResult,
                                        function(result) {
                                            if($("#contestPipelineViewPopup .dataTables_wrapper").length > 0) {
                                                $("#contestPipelineDrillInTable").dataTable().fnDestroy();
                                            }

                                            var tableData = [];

                                            $.each(result, function(index, value){
                                                var rowData = [];
                                                rowData.push('<a href="../projectOverview.action?formData.projectId=' + value.directProjectId + '" target="_blank">' + value.directProjectName + '</a>');
                                                rowData.push('<a href="../contest/detail.action?projectId=' + value.contestId + '" target="_blank">' + value.contestName + '</a>');
                                                rowData.push(value.contestStatus);
                                                rowData.push(value.startDate);
                                                rowData.push(value.endDate);
                                                rowData.push(value.copilot);
                                                tableData.push(rowData);
                                            })


                                            $("#contestPipelineDrillInTable tbody").empty().data('tableData', tableData);

                                            $("#contestPipelineViewPopup a.exportLink").attr('href', 'getContestsPipelineDrillIn?' + $.param({formData:requestData,'export':true})).text("Export to Excel (" + result.length + " records)");

                                            $("#contestPipelineViewHandler").data("overlay").load();
                                        },
                                        function(errorMessage) {
                                            showErrors(errorMessage);
                                        });
                                }
                            });
                        }
                    }
                }
            },
            series:[
                {
                    name:'Failed Challenges',
                    data:failedData,
                    color:'#e83308',
                    borderColor:'#a80b00'
                },
                {
                    name:'Draft Challenges',
                    data:draftData,
                    color:'#939393',
                    borderColor:'#777777'

                },
                {
                    name:'Active Challenges',
                    data:activeData,
                    color:'#2fcafa',
                    borderColor:'#0096c7'
                },
                {
                    name:'Scheduled Challenges',
                    data:scheduledData,
                    color:'#FF850B',
                    borderColor:'#FF850B'
                },
                {
                    name:'Finished Challenges',
                    data:finishedData,
                    color:'#2f9c0d',
                    borderColor:'#187000'
                }
            ]
        });
    } else {
        $('.pipelineSection #pipelineChart').empty().append('<div class="noData">No data available</div>');
    }
}

function renderProjectsPipeline(resultJson) {
    var xAxisCate = new Array();
    var completedData = new Array();
    var startedData = new Array();
    var completedDataTotal = 0, startedDataTotal = 0;
    $.each(resultJson, function (idx, item) {
        startedDataTotal += parseInt(item.startedProjectsNumber);
        completedDataTotal += parseInt(item.completedProjectsNumber);
    });

    if (resultJson.length) {
        $.each(resultJson, function (idx, item) {
            xAxisCate.push(item.date);
            completedData.push(parseInt(item.completedProjectsNumber) == 0 ? null : parseInt(item.completedProjectsNumber));
            startedData.push(parseInt(item.startedProjectsNumber) == 0 ? null : parseInt(item.startedProjectsNumber));
        });
    }

    $('.pipelineSection .projectsPipeline ul').empty().append('<li class="finished">Total Completed Projects<strong>' + completedDataTotal + '</strong></li><li class="active last">Total Started Projects<strong>' + startedDataTotal + '</strong></li>');
    if (resultJson.length) {
        chart = new Highcharts.Chart({
            chart:{
                renderTo:'projectPipelineChart',
                type:'column',
                marginBottom:60
            },
            credits:{
                text:''
            },
            navigation:{
                buttonOptions:{
                    enabled:false
                }
            },
            title:{
                text:null
            },
            xAxis:{
                categories:xAxisCate,
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    },
                    formatter:function () {
                        return this.value.substring(5, 8).toUpperCase() + '\'' + parseInt(this.value).toString().substring(2, 4);
                    }
                },
                tickLength:0
            },
            yAxis:{
                min:0,
                title:{
                    text:null
                },
                stackLabels:{
                    enabled:true
                },
                labels:{
                    enabled:false
                },
                gridLineWidth:0
            },
            legend:{
                align:'center',
                verticalAlign:'bottom',
                y:16,
                backgroundColor:'#ebebeb',
                borderColor:'#CCC',
                borderWidth:1,
                borderRadius:0,
                symbolPadding:6,
                itemWidth:150,
                itemStyle:{
                    fontFamily:'Arial',
                    fontWeight:'bold',
                    fontSize:'11px',
                    color:'#898989'
                },
                reversed:true
            },
            tooltip:{
                useHTML:true,
                backgroundColor:null,
                borderRadius:0,
                borderWidth:0,
                shadow:false,
                style:{
                    margin:'0px',
                    padding:'0px',
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#333333'
                },
                formatter:function () {
                    return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                        parseInt(this.point.category) + ' ' + this.point.category.substring(5)
                        + '</strong><div>' +
                        this.series.name
                        + ': ' +
                        this.point.y
                        + '</div></div></div>';
                }
            },
            plotOptions:{
                column:{
                    stacking:'normal',
                    dataLabels:{
                        enabled:true,
                        color:'#ffffff',
                        style:{
                            fontFamily:'Arial',
                            fontSize:'11px',
                            fontWeight:'bold'
                        },
                        formatter:function () {
                            return this.y == 0 ? null : this.y;
                        }
                    },
                    shadow:false,
                    borderColor:Highcharts.getOptions().borderColor
                },
                series:{
                    pointWidth:48,
                    events: {
                        click : function(event) {
                            $('#projectPipelineViewPopupMonth').text(event.point.category);
                            var requestData = getEnterpriseDashboardRequest(100000, 0, true);
                            // set start and end month to the drilled-in month
                            var currentMonth = event.point.category;
                            requestData.startMonth = currentMonth.substring(5, 8).toUpperCase() + '\'' + parseInt(currentMonth).toString().substring(2, 4);
                            requestData.endMonth = requestData.startMonth;

                            modalPreloader();

                            $.ajax({
                                type: 'POST',
                                url:  ctx + "/enterpriseDashboard/getProjectsPipelineDrillIn",
                                data: {formData:requestData},
                                cache: false,
                                timeout:100000,
                                dataType: 'json',
                                success: function(jsonResult) {
                                    handleJsonResult(jsonResult,
                                        function(result) {
                                            if($("#projectPipelineViewPopup .dataTables_wrapper").length > 0) {
                                                $("#projectPipelineDrillInTable").dataTable().fnDestroy();
                                            }
                                            $("#projectPipelineDrillInTable tbody").empty().html($("#projectPipelineDrillInTemplate").render(result));

                                            $("#projectPipelineViewPopup a.exportLink").attr('href', 'getProjectsPipelineDrillIn?' + $.param({formData:requestData,'export':true})).text("Export to Excel (" + result.length + " records)");

                                            $("#projectPipelineViewHandler").data("overlay").load();
                                        },
                                        function(errorMessage) {
                                            showErrors(errorMessage);
                                        });
                                }
                            });
                        }
                    }

                }
            },
            series:[
                {
                    name:'Started Projects',
                    data:startedData,
                    color:'#2fcafa',
                    borderColor:'#0096c7'
                },
                {
                    name:'Completed Projects',
                    data:completedData,
                    color:'#2f9c0d',
                    borderColor:'#187000'
                }
            ]
        });
    } else {
        $('.pipelineSection #projectPipelineChart').empty().append('<div class="noData">No data available</div>');
    }
    $(".filterPanelButton .xls").attr('href', 'getPipelineExport?' + $.param({formData: getEnterpriseDashboardRequest(10000, 0, true)}));
}

function renderOverviewProjects(resultJson) {
    var strData = '';
    var length;
    if (resultJson.length && resultJson.length > 0) {
        length = resultJson.length >= 5 ? 5 : resultJson.length;
        for (var i = 0; i < length; i++) {
            var projectName = resultJson[i].name;
            if(projectName.length > 45) {
                projectName = projectName.substr(0, 45) + '...';
            }
            var milestoneName;
            if (resultJson[i].milestoneName) {
                milestoneName = resultJson[i].milestoneName;
                if(milestoneName.length > 50) {
                    milestoneName = milestoneName.substr(0, 50) + '...';
                }
            }

            var projectLink = "/direct/projectOverview.action?formData.projectId=" +  resultJson[i].id;
            var milestoneLink = '../projectMilestoneView?formData.viewType=list&formData.projectId=' + resultJson[i].id;

            strData += '<tr>';
            strData += '<td>';
            strData += '<div class="showTip">';
            strData += '<a href="' + projectLink + '" class="projectName" target="_blank" title="' + resultJson[i].name + '">' + projectName + '</a>';
            strData += '</div>';
            strData += '</td>';
            strData += '<td class="alignCenter">';
            if (resultJson[i].milestoneName) {
                strData += '<a href="' + milestoneLink + '" target="_blank" title="'  + resultJson[i].milestoneName + '">' + milestoneName + '</a>';
            }
            strData += '</td></tr>';
        }


        if (length < 4) {
            for(var left = 1; left <= (5 - length); ++ left) {
                strData += '<tr style="border-left:1px solid #E7E7E7; border-right:1px solid #E7E7E7"></tr>';
            }
        }

        if (resultJson.length) {
            $("#overProjectsTableData tbody").empty().append(strData);
            $('#overProjectsTableData tbody tr:odd').addClass('odd');
            $("#overProjectsTableData .bar .barInner").each(function () {
                $(this).css('width', $(this).parent().find('span').text());
            });
        }
    } else {
        $("#overProjectsTableData tbody").empty().append('<tr><td colspan="2"><div class="noData">No data available</div></td></tr>');
    }
}

function renderOverviewRoadmap(resultJson) {
    var strDataOverDue = '', strDataUpcoming = '', strDataCompleted = '';
    var pattern = /^\w*/;
    if (resultJson.overdue && resultJson.overdue.length  && resultJson.overdue.length > 0) {
        var overdueLength = resultJson.overdue.length > 3 ? 3 : resultJson.overdue.length;
        for (i = 0; i < overdueLength; i++) {
            strDataOverDue += '<tr>';
            strDataOverDue += '<td>';
            strDataOverDue += '<h4><a target="_blank" href="' + '../projectMilestoneView?formData.viewType=list&formData.projectId=' + resultJson.overdue[i].projectId + '">' + htmlEncode(resultJson.overdue[i].projectName) + ":" + htmlEncode(resultJson.overdue[i].title) + '</a></h4>';
            strDataOverDue += '</td>';
            strDataOverDue += '<td class="alignCenter">';
            strDataOverDue += '<span class="date">' + pattern.exec(resultJson.overdue[i].date).toString().substr(0, 3) + resultJson.overdue[i].date.replace(pattern.exec(resultJson.overdue[i].date).toString(), '') + '</span>';
            strDataOverDue += '</td>';
            strDataOverDue += '</tr>';
        }

        $(".overRoadMapSection #overDueData tbody").empty().append(strDataOverDue);
        $('.overRoadMapSection #overDueData tbody tr:odd').addClass('odd');

    } else {
        $(".overRoadMapSection #overDueData tbody").empty().append('<tr><td colspan="2" class="alignCenter"><div class="noData">No data available</div></td></tr>');
    }

    if (resultJson.upcoming && resultJson.upcoming.length && resultJson.upcoming.length > 0) {
        var upcomingLength = resultJson.upcoming.length > 3 ? 3 : resultJson.upcoming.length;
        for (i = 0; i < upcomingLength; i++) {
            strDataUpcoming += '<tr>';
            strDataUpcoming += '<td>';
            strDataUpcoming += '<h4><a target="_blank" href="' + '../projectMilestoneView?formData.viewType=list&formData.projectId=' + resultJson.upcoming[i].projectId + '">' + htmlEncode(resultJson.upcoming[i].projectName) + ":" + htmlEncode(resultJson.upcoming[i].title)  + '</a></h4>';
            strDataUpcoming += '</td>';
            strDataUpcoming += '<td class="alignCenter">';
            strDataUpcoming += '<span class="date">' + pattern.exec(resultJson.upcoming[i].date).toString().substr(0, 3) + resultJson.upcoming[i].date.replace(pattern.exec(resultJson.upcoming[i].date).toString(), '') + '</span>';
            strDataUpcoming += '</td>';
            strDataUpcoming += '</tr>';
        }

        $(".overRoadMapSection #upcomingData tbody").empty().append(strDataUpcoming);
        $('.overRoadMapSection #upcomingData tbody tr:odd').addClass('odd');

    } else {
        $(".overRoadMapSection #upcomingData tbody").empty().append('<tr><td colspan="2" class="alignCenter"><div class="noData">No data available</div></td></tr>');
    }

    if (resultJson.completed && resultJson.completed.length && resultJson.completed.length > 0) {
        var completedLength = resultJson.completed.length > 3 ? 3 : resultJson.completed.length;

        for (i = 0; i < completedLength; i++) {
            strDataCompleted += '<tr>';
            strDataCompleted += '<td>';
            strDataCompleted += '<h4><a target="_blank" href="' + '../projectMilestoneView?formData.viewType=list&formData.projectId=' + resultJson.completed[i].projectId + '">' + htmlEncode(resultJson.completed[i].projectName) + ":" + htmlEncode(resultJson.completed[i].title)  + '</a></h4>';
            strDataCompleted += '</td>';
            strDataCompleted += '<td class="alignCenter">';
            strDataCompleted += '<span class="date">' + pattern.exec(resultJson.completed[i].date).toString().substr(0, 3) + resultJson.completed[i].date.replace(pattern.exec(resultJson.completed[i].date).toString(), '') + '</span>';
            strDataCompleted += '</td>';
            strDataCompleted += '</tr>';
        }

        $(".overRoadMapSection #completedData tbody").empty().append(strDataCompleted);
        $('.overRoadMapSection #completedData tbody tr:odd').addClass('odd');

    } else {
        $(".overRoadMapSection #completedData tbody").empty().append('<tr><td colspan="2" class="alignCenter"><div class="noData">No data available</div></td></tr>');
    }
}

function generateRoadMapRow(item) {
    var str = '';
    str += '<tr>';
    str += '<td>';
    str += '<h4><a target="_blank" href="' + '../projectMilestoneView?formData.viewType=list&formData.projectId=' + item.projectId + '">' + htmlEncode(item.title) + '</a></h4>';
    str += '<p>' + htmlEncode(item.description) + '</p>';
    str += '</td>';
    str += '<td>';
    str += '<h4 class="projectLink"><a target="_blank" href="' + 'projectOverview?formData.projectId=' + item.projectId + '">' + item.projectName + '</a></h4>';
    str += '</td>';
    str += '<td class="alignCenter">';
    str += '<span class="date">' + item.date + '</span>';
    str += '</td>';
    str += '</tr>';
    return str;
}

function setupRoadmapTable(table) {
    table.dataTable({
        "iDisplayLength":10,
        "bFilter":false,
        "bSort":false,
        "oLanguage":{
            "sLengthMenu":sStdMenu,
            "oPaginate":{
                "sFirst":"",
                "sPrevious":"Prev",
                "sNext":"Next",
                "sLast":""
            }
        },
        "sPaginationType":"full_numbers",
        "sDom":'<"pagePanel topPagePanel"i<"showPage"l><"pageNum"p>>t<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>'
    });
}

function renderRoadmapPage(json) {
    var strDataOverDue = '', strDataUpcoming = '', strDataCompleted = '', strDataTotal = '', strDataTab = '';
    var totalNumberOverDue = 0, totalNumberUpcoming = 0, totalNumberCompleted = 0;
    if (json.overdue && json.overdue.length) {
        $.each(json.overdue, function (idx, item) {
            strDataOverDue += generateRoadMapRow(item);
        });
    }
    if (json.upcoming && json.upcoming.length) {
        $.each(json.upcoming, function (idx, item) {
            strDataUpcoming += generateRoadMapRow(item);
        });
    }
    if (json.completed && json.completed.length) {
        $.each(json.completed, function (idx, item) {
            strDataCompleted += generateRoadMapRow(item);
        });
    }

    strDataTotal += '<li>Overdue:<strong class="red">' + json.overdue.length + '</strong></li>';
    strDataTotal += '<li>Upcoming<strong class="yellow">' + json.upcoming.length + '</strong></li>';
    strDataTotal += '<li class="last">Completed<strong class="green">' + json.completed.length + '</strong></li>';

    totalNumberOverDue = json.overdue.length;
    totalNumberUpcoming = json.upcoming.length;
    totalNumberCompleted = json.completed.length;

    $(".roadMapSection .roadMapNum ul").empty().append(strDataTotal);
    $(".roadMapSection .tabPanel ul li:eq(0) strong").text('(' + totalNumberOverDue + ')');
    $(".roadMapSection .tabPanel ul li:eq(1) strong").text('(' + totalNumberUpcoming + ')');
    $(".roadMapSection .tabPanel ul li:eq(2) strong").text('(' + totalNumberCompleted + ')');
    $(".roadMapSection .overDueTable .topPagePanel .pageTotal span.totalNumber").text(totalNumberOverDue);
    $(".roadMapSection .upcomingData .topPagePanel .pageTotal span.totalNumber").text(totalNumberUpcoming);
    $(".roadMapSection .completedData .topPagePanel .pageTotal span.totalNumber").text(totalNumberCompleted);
    $('.roadMapSection .tabPanel li a').removeClass('current');
    $('.roadMapSection .tabPanel li a').eq(0).addClass('current');
    if (json.overdue && json.overdue.length) {
        $(".roadMapSection #overDueData tbody").empty().append(strDataOverDue);
        $('.roadMapSection #overDueData tbody tr:odd').addClass('odd');
        setupRoadmapTable($('.roadMapSection .tabContainer table:eq(0)'));
    } else {
        $(".roadMapSection #overDueData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
    }
    if (json.upcoming && json.upcoming.length) {
        $(".roadMapSection #upcomingData tbody").empty().append(strDataUpcoming);
        $('.roadMapSection #upcomingData tbody tr:odd').addClass('odd');
        setupRoadmapTable($('.roadMapSection .tabContainer table:eq(1)'));
    } else {
        $(".roadMapSection #upcomingData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
    }
    if (json.completed && json.completed.length) {
        $(".roadMapSection #completedData tbody").empty().append(strDataCompleted);
        $('.roadMapSection #completedData tbody tr:odd').addClass('odd');
        setupRoadmapTable($('.roadMapSection .tabContainer table:eq(2)'));
    } else {
        $(".roadMapSection #completedData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
    }

    $('.roadMapSection .tabContainer table').width('100%');
}


function loadTotalSpend(resultHandler) {
    if($("#overviewTotalSpend").length > 0) {
        $("#overviewTotalSpend").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
    } else if ($("#chartTotalSpend").length > 0) {
        $("#chartTotalSpend").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
        $(".totalSpendSection .numberSectionInner ul").empty();
        $(".totalSpendSection .numberSectionInner ul").html('<li class="last ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></li>');
    }


    $.ajax({
        type: 'POST',
        url:  ctx + "/enterpriseDashboard/getTotalSpend",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);

                    var currentMonth = Date.today().toString("MMM'yy");
                    var prevMonth = Date.today().addMonths(-1).toString("MMM'yy");

                    $.each(result, function(index, item){
                        if(item.label == currentMonth) {
                            currentMonthLoaded = true;
                            currentMonthTotal = parseInt(item.spend);
                        }
                        if(item.label == prevMonth) {
                            prevMonthLoaded = true;
                            prevMonthTotal = parseInt(item.spend);
                        }
                    });
                    renderCurrentPrevMonthSpend();
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

function loadPipeline(resultHandler) {
    if($("#pipelineChartWrapper").length > 0) {
        $("#pipelineChart").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
        $(".contestsPipeline .numberSectionInner ul").empty().html('<li class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></li>');
    }
    if($("#overviewPipeline").length > 0) {
        $("#overviewPipeline").empty().html('<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>');
    }

    $.ajax({
        type: 'POST',
        url:  ctx + "/enterpriseDashboard/getContestsPipeline",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

function loadProjectPipeline(resultHandler) {
    $("#projectPipelineChart").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
    $(".projectsPipeline .numberSectionInner ul").empty().html('<li class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></li>');

    $.ajax({
        type: 'POST',
        url: ctx + "/enterpriseDashboard/getProjectsPipeline",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);
                },
                function(errorMessage) {
                    $(".projectsPipeline .numberSectionInner ul").empty();
                    $('.pipelineSection #projectPipelineChart').empty().append('<div class="noData">Error when loading projects pipeline</div>')
                });
        }
    });
}

function loadFinancial(resultHandler) {

    if($(".overFinancialSection").length > 0) {
        $(".overFinancialSection .mainSection .ajaxTableLoader").show();
        $(".overFinancialSection .mainSection .dateSection").hide();
    } else if($(".financialsSection").length > 0) {
        $(".financialsSection .numberSectionInner ul").empty();
        $(".financialsSection .numberSectionInner ul").html('<li class="last ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></li>');
        $("#financials").remove();
        var newTable = $($("#tableTemplate").html()).attr('id', 'financials');
        $(".tableData").empty().append(newTable);
        //append('<tr><td colspan="5"><div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div></td></tr>');
    }

    $.ajax({
        type: 'POST',
        url: ctx + "/enterpriseDashboard/getFinancialStatistics",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);
                    renderCurrentPrevMonthSpend();
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

function loadRoadmap(resultHandler) {

    if($(".overRoadMapSection").length > 0) {
       $(".overRoadMapSection .tabSection tbody").empty().html('<tr><td colspan="2" class="alignCenter"><div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div></td></tr>');
    } else if($(".roadMapSection").length > 0) {
        $(".roadMapSection .roadMapNumInner ul").empty().html('<li class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></li>');
        $(".roadMapSection .tabPanel li strong").empty().text('0');
        $(".roadMapSection .tabContainer .tabSection").remove();
        var newTable = $($("#tableTemplate").html());
        $(".roadMapSection .tabContainer").empty().append(newTable);
        //append('<tr><td colspan="5"><div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div></td></tr>');
    }


    $.ajax({
        type: 'POST',
        url:  ctx + "/enterpriseDashboard/getProjectsMilestones",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

function loadProjects(resultHandler) {

    if($(".overProjectsSection").length > 0) {
        $("#overProjectsTableData tbody").empty().html('<tr><td colspan="2" class="alignCenter"><div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div></td></tr>');
    }

    $.ajax({
        type: 'POST',
        url:  ctx + "/enterpriseDashboard/getProjectsWidget",
        data: {formData:getEnterpriseDashboardRequest(100000, 0, true)},
        cache: false,
        timeout:100000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    resultHandler(result);
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

function loadCurrentPrevMonthSpend() {
    if(loadCurrentMonth || loadPreviousMonth) {
        var request = getEnterpriseDashboardRequest(100000, 0, true);
        var currentMonth = Date.today().toString("MMM'yy");
        var prevMonth = Date.today().addMonths(-1).toString("MMM'yy");

        if(loadCurrentMonth && loadPreviousMonth) {
            request.startMonth = prevMonth;
            request.endMonth = currentMonth;
        } else if (!loadCurrentMonth && loadPreviousMonth) {
            request.startMonth = prevMonth;
            request.endMonth = prevMonth;
        } else {
            request.startMonth = currentMonth;
            request.endMonth = currentMonth;
        }

        $.ajax({
            type: 'POST',
            url:  ctx + "/enterpriseDashboard/getTotalSpend",
            data: {formData:request},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult2(jsonResult,
                    function(result) {
                        $.each(result, function(index, item){
                           if(item.label == currentMonth) {
                               currentMonthLoaded = true;
                               currentMonthTotal = parseInt(item.spend);
                           }
                           if(item.label == prevMonth) {
                                prevMonthLoaded = true;
                                prevMonthTotal = parseInt(item.spend);
                           }
                        });
                        renderCurrentPrevMonthSpend();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    }
}


function renderOverviewTab() {
    currentMonthLoaded = false;
    prevMonthLoaded = false;
    loadTotalSpend(renderTotalSpendWidget);
    //loadFinancial(renderOverviewFinancial);
    loadCurrentPrevMonthSpend();
    loadPipeline(renderPipelineWidget);
    loadRoadmap(renderOverviewRoadmap);
    loadProjects(renderOverviewProjects);
}

function renderFinancialTab() {
    currentMonthLoaded = false;
    prevMonthLoaded = false;
    loadTotalSpend(renderTotalSpendChart);
    // loadFinancial(renderFinancial);
    loadCurrentPrevMonthSpend();
    $(".filterPanelButton .xls").attr('href', 'getTotalSpendExport?' + $.param({formData: getEnterpriseDashboardRequest(10000, 0, true)}));
}

function renderHealthTab() {
    loadFinancial(renderFinancial);
    $(".filterPanelButton .xls").attr('href', 'getHealthExport?' + $.param({formData: getEnterpriseDashboardRequest(10000, 0, true)}));
}

function getRequestForAnalysis() {
    var request = {};
    request.customerIds = [getFilterSelectorValue('customer')];
    customerName = $("#customer option:selected").text();
    request.billingAccountIds = [getFilterSelectorValue('billingAccount')];
    request.projectIds = [getFilterSelectorValue('project')];
    var projectCategoryIds = [];
    $(".selectWrapper ul li:gt(0)").each(function(){
        if($(this).hasClass("selected")) {
            projectCategoryIds.push($(this).find('input').attr('value'));
        }
    })
    request.projectCategoryIds = projectCategoryIds;

    var groupType = $("#timeDimension").val();
    // get the start date and end date
    if (groupType == 'Week') {
        request.startDate = $("#endDateBegin").val();
        request.endDate = $("#endDateEnd").val();
    } else if (groupType == 'Month') {
        var startTime = Date.parse($("select[name=startYear]").val() + '-' + ($("select[name=startMonth]").val()/1 + 1));
        var endTime = Date.parse($("select[name=endYear]").val() + '-' + ($("select[name=endMonth]").val()/1 + 1));
        endTime = new Date(endTime.getFullYear(), endTime.getMonth() + 1, 0);
        request.startDate = $.datepicker.formatDate('mm/dd/yy', startTime);
        request.endDate =  $.datepicker.formatDate('mm/dd/yy', endTime);
    } else if (groupType == 'Quarter') {
        var startMonth = $("select[name=startQuarter]").val() * 3 - 2;
        var endMonth = $("select[name=endQuarter]").val() * 3;
        var startTime = Date.parse($("select[name=startYear]").val() + '-' + startMonth);
        var endTime = Date.parse($("select[name=endYear]").val() + '-' + endMonth);
        endTime = new Date(endTime.getFullYear(), endTime.getMonth() + 1, 0);
        request.startDate = $.datepicker.formatDate('mm/dd/yy', startTime);
        request.endDate =  $.datepicker.formatDate('mm/dd/yy', endTime);
    } else if (groupType == 'Year') {
        var startTime = Date.parse($("select[name=startYear]").val() + '-' + 1);
        var endTime = Date.parse($("select[name=endYear]").val() + '-' + 12);
        endTime = new Date(endTime.getFullYear(), endTime.getMonth() + 1, 0);
        request.startDate = $.datepicker.formatDate('mm/dd/yy', startTime);
        request.endDate =  $.datepicker.formatDate('mm/dd/yy', endTime);
    }

    var errorMessage = '';

    if(Date.parse(request.startDate) > Date.parse(request.endDate)) {
        errorMessage = "- Start time should not larger than the end time";
    }

    if(projectCategoryIds.length <= 0) {
        if(errorMessage.length > 0) {
            errorMessage += " <br/> ";
        }
        errorMessage += "- Please select at least one challenge type";
    }

    $(".button .errorMessage").html(errorMessage);

    if(errorMessage.length > 0) {
        return;
    }

    var data = {formData:request, groupByType:groupType, dashboardViewType: "newEnterpriseDashboard"};
    return data;
}


$(document).ready(function () {

    $(".filterContainer select").attr("autocomplete","off");

    $('.triggerModal').live('click', function () {
        modalLoad('#' + $(this).attr('name'));
        initFilterDate();
        return false;
    });

    //Filter Date
    var myDate = new Date();
    var months = new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
    var year = myDate.getFullYear();
    var month = myDate.getMonth();
    var currentFullDate = (months[month] + '\'' + year.toString().substring(2, 4));
    var step = 0;
    var currentMonth = 0;

    function moveMonth() {

        if (step == 0) {
            $('.selectDate .prevYear').addClass('disable');
            $('.selectDate .nextYear').removeClass('disable');
        } else if (step == $('.selectDate .monthList li').length - 12) {
            $('.selectDate .nextYear').addClass('disable');
            $('.selectDate .prevYear').removeClass('disable');
        } else {
            $('.selectDate .prevYear').removeClass('disable');
            $('.selectDate .nextYear').removeClass('disable');
        }
        $('.selectDate .monthList li').removeClass('firstMonth');
        $('.selectDate .monthList li').removeClass('lastMonth');
        $('.selectDate .monthList li').eq(step).addClass('firstMonth');
        $('.selectDate .monthList li').eq(step + 11).addClass('lastMonth');
        $('.selectDate .monthList li').hide();
        for (i = 0; i < 12; i++) {
            $('.selectDate .monthList li').eq(step).show();
            $('.selectDate .monthList li').eq(step + i).show();
        }
    }

    function getCurrent() {
        for (i = 0; i < $('.selectDate li').length; i++) {
            if (currentFullDate.match($('.selectDate li a').eq(i).text())) {
                currentMonth = i;
            }
        }
    }

    $('.selectDate .prevYear:not(.disable)').live('click', function () {
        step = step - 1;
        moveMonth();
    });
    $('.selectDate .nextYear:not(.disable)').live('click', function () {
        step = step + 1;
        moveMonth();
    });

    $('.selectDate li a').live('click', function () {
        if ($(this).hasClass('selectMonth')) {
            if (!$(this).parent('li').prev().find('a').hasClass('selectMonth') || !$(this).parent('li').next().find('a').hasClass('selectMonth')) {
                $(this).removeClass('selectMonth');
                $(this).parents('li').prev().find('a').removeClass('selectPrevMonth');
            }
        } else {
            if ($('.selectDate li a.selectMonth').length == 1) {
                if ($('.selectDate li a').index(this) > $('.selectDate li a').index($('.selectDate li a.selectMonth'))) {
                    for (i = $('.selectDate li a').index($('.selectDate li a.selectMonth')); i < $('.selectDate li a').index(this) + 1; i++) {
                        $('.selectDate li a').eq(i).addClass('selectMonth');
                        $('.selectDate li a').eq(i).parents('li').prev().find('a').addClass('selectPrevMonth');
                    }
                } else {
                    for (i = $('.selectDate li a').index($('.selectDate li a.selectMonth')); i > $('.selectDate li a').index(this) - 1; i--) {
                        $('.selectDate li a').eq(i).addClass('selectMonth');
                        $('.selectDate li a').eq(i).parents('li').prev().find('a').addClass('selectPrevMonth');
                    }
                }
            } else {
                if ($(this).parent('li').prev().find('a').hasClass('selectMonth') || $(this).parent('li').next().find('a').hasClass('selectMonth')) {
                    $(this).addClass('selectMonth');
                    $(this).parents('li').prev().find('a').addClass('selectPrevMonth');
                } else {
                    $('.selectDate li a').removeClass('selectMonth');
                    $('.selectDate li a').parents('li').prev().find('a').removeClass('selectPrevMonth');
                    $(this).addClass('selectMonth');
                    $(this).parents('li').prev().find('a').addClass('selectPrevMonth');
                }
            }
        }
        $('#zoomSelect ul li a').removeClass('current');
    });

    //select
    $('#zoomSelect ul li a').live('click', function () {
        if ($(this).hasClass('current')) {
            $('#zoomSelect ul li a').removeClass('current');
            $(this).removeClass('current');
        } else {
            $('#zoomSelect ul li a').removeClass('current');
            $(this).addClass('current');
        }
        $('.selectDate ul li a').removeClass('selectMonth');
        $('.selectDate ul li a').removeClass('selectPrevMonth');
    });

    $('#zoomSelect .currentMonth a').live('click', function () {
        if ($(this).hasClass('current')) {
            getCurrent();
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
            $('.selectDate ul li a').eq(currentMonth).addClass('selectMonth');
            $('.selectDate ul li a').eq(currentMonth - 1).addClass('selectPrevMonth');
            if (currentMonth > $('.selectDate ul li').length - 12) {
                step = $('.selectDate ul li').length - 12;
            } else {
                step = currentMonth;
            }
            moveMonth();
        } else {
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
        }
    });

    $('#zoomSelect .threeMonths a').live('click', function () {
        if ($(this).hasClass('current')) {
            getCurrent();
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
            for (i = 0; i < 3; i++) {
                $('.selectDate ul li a').eq(currentMonth - i).addClass('selectMonth');
                $('.selectDate ul li a').eq(currentMonth - i - 1).addClass('selectPrevMonth');
            }
            if (currentMonth - 2 > $('.selectDate ul li').length - 12) {
                step = $('.selectDate ul li').length - 12;
            } else {
                step = currentMonth - 2;
            }
            moveMonth();
        } else {
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
        }
    });

    $('#zoomSelect .sixMonths a').live('click', function () {
        if ($(this).hasClass('current')) {
            getCurrent();
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
            for (i = 0; i < 6; i++) {
                $('.selectDate ul li a').eq(currentMonth - i).addClass('selectMonth');
                $('.selectDate ul li a').eq(currentMonth - i - 1).addClass('selectPrevMonth');
            }
            if (currentMonth - 5 > $('.selectDate ul li').length - 12) {
                step = $('.selectDate ul li').length - 12;
            } else {
                step = currentMonth - 5;
            }
            moveMonth();
        } else {
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
        }
    });

    $('#zoomSelect .oneYear a').live('click', function () {
        if ($(this).hasClass('current')) {
            getCurrent();
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
            for (i = 0; i < 12; i++) {
                $('.selectDate ul li a').eq(currentMonth - i).addClass('selectMonth');
                $('.selectDate ul li a').eq(currentMonth - i - 1).addClass('selectPrevMonth');
            }
            if (currentMonth - 11 > $('.selectDate ul li').length - 12) {
                step = $('.selectDate ul li').length - 12;
            } else {
                step = currentMonth - 11;
            }
            moveMonth();
        } else {
            $('.selectDate ul li a').removeClass('selectMonth');
            $('.selectDate ul li a').removeClass('selectPrevMonth');
        }
    });

    $('#filterModal').css('width', $('#mainSection').width());

    initFilterDate();

    $('#zoomSelect .sixMonths a').trigger('click');

    sortDropDown("#projectStatusFilter");

    $("#clientFilter").change(function(){

        showIndicator($("#projectFilter"));
        showIndicator($("#metaFilter"));
        showIndicator($("#metaValueFilter"));
        var requestData = getEnterpriseDashboardRequest(10000, 0, true, true);
        requestData.projectFilterId = 0;
        requestData.projectFilterValue = "None";

        $.ajax({
            type: 'POST',
            url:  ctx + "/enterpriseDashboard/getOptionsForClientAJAX",
            data: {formData:requestData},
            cache: false,
            async: true,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult2(jsonResult,
                    function(result) {
                        var projects = result.projects;
                        var $project = $("#projectFilter");

                        $project.html("");
                        $project.append($('<option></option>').val(0).html("All Projects"));
                        $.each(projects, function(key, value) {
                            $project.append($('<option></option>').val(key).html(value));
                        });

                        $project.val(0);

                        sortDropDown("#projectFilter");

                        var $projectFilters = $("#metaFilter");
                        $projectFilters.empty();

                        $projectFilters.append($("<option></option>").attr('value', 0).text("None"));

                        $.each(result.projectFilters, function (key, value) {
                            $projectFilters.append($("<option></option>").attr('value', key).text(value));
                        });

                        $projectFilters.val(0);

                        var $projectFilterValues = $("#metaValueFilter");
                        $projectFilterValues.empty();

                        $projectFilterValues.append($("<option></option>").attr('value', 'None').text("None"));

                        $.each(result, function (index, value) {
                            $projectFilterValues.append($("<option></option>").attr('value', value).text(value));
                        });

                        $projectFilterValues.val('None');

                        hideIndicator($("#projectFilter"));
                        hideIndicator($("#metaFilter"));
                        hideIndicator($("#metaValueFilter"));
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    });

    $("#projectStatusFilter").change(function () {

        if($("#projectStatusFilter option").length == 0) return;

        showIndicator($("#projectFilter"));

        $.ajax({
            type:'POST',
            url: ctx + "/enterpriseDashboard/getFilteredProjects",
            data:{formData:getEnterpriseDashboardRequest(100000, 0, true, true)},
            cache:false,
            async: true,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        var projects = result;
                        var $project = $("#projectFilter");

                        $project.html("");
                        $project.append($('<option></option>').val(0).html("All Projects"));
                        $.each(projects, function(key, value) {
                            $project.append($('<option></option>').val(key).html(value));
                        });

                        $project.val(0);

                        if(projectToSync) {
                            $project.val(projectToSync);
                            projectToSync = null;
                        }

                        sortDropDown("#projectFilter");

                        hideIndicator($("#projectFilter"));
                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    });

    $("#metaFilter").change(function () {

        if($("#metaFilter option").length == 0) return;

        showIndicator($("#metaValueFilter"));

        $.ajax({
            type:'GET',
            url:ctx + "/getGroupValuesForGroupBy",
            data:{groupKeyId:$("#metaFilter").val()},
            cache:false,
            async: true,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        var selector = $("#metaValueFilter");
                        selector.empty();

                        selector.append($("<option></option>").attr('value', 'None').text("None"));

                        $.each(result, function (index, value) {
                            selector.append($("<option></option>").attr('value', value).text(value));
                        });

                        selector.val('None');

                        if(projectFilterValueToSync) {
                            selector.val(projectFilterValueToSync);
                            projectFilterValueToSync = null;
                        }

                        hideIndicator($("#metaValueFilter"));

                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    });

    $("#metaValueFilter").change(function () {

        if($("#metaValueFilter option").length == 0) return;
        var requestData = getEnterpriseDashboardRequest(100000, 0, true, true);

        if(requestData.projectFilterValue == 'None') {
            // if project filter value is None, do not filter by project filter
            requestData.projectFilterId = 0;
        }

        showIndicator($("#projectFilter"));

        $.ajax({
            type:'POST',
            url: ctx + "/enterpriseDashboard/getFilteredProjects",
            data:{formData:requestData},
            cache:false,
            async: true,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        var projects = result;
                        var $project = $("#projectFilter");

                        $project.html("");
                        $project.append($('<option></option>').val(0).html("All Projects"));
                        $.each(projects, function(key, value) {
                            $project.append($('<option></option>').val(key).html(value));
                        });

                        $project.val(0);

                        if(projectToSync) {
                            $project.val(projectToSync);
                            projectToSync = null;
                        }

                        sortDropDown("#projectFilter");

                        hideIndicator($("#projectFilter"));
                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });
    });

    //Get Location
    function getUrlPara(paraName) {
        var sUrl = location.href;
        var sReg = "(?:\\?|&){1}" + paraName + "=([^&]*)"
        var re = new RegExp(sReg, "gi");
        re.exec(sUrl);
        return decodeURI(RegExp.$1);
    }

    $('.blankPage').attr('target', '_blank');
    sortDropDown("#projectFilter");

    function initFilterDate() {
        $('.selectDate .monthList li').css('width', parseInt(($('.selectDate').width() - 84) / 12));
        $('.selectDate .monthList ul,.selectDate .monthList .timeLine').css('width', $('.selectDate .monthList li').width() * 12);
        moveMonth();
    }

    if ($('.expandViewPopup').length > 0) {
        $(window).resize(function () {
            var containerW = $(window).width();
            var containerH = $(window).height();
            var visibleOverlay;
            var wWid = containerW > 1540 ? 1500 : containerW - 40;
            var hHht = containerH > 800 ? 750 : containerH - 50;
            var curOverlayDataTable;

            if ($("#contestPipelineViewPopup").is(':visible')) {
                visibleOverlay = $("#contestPipelineViewPopup");
                curOverlayDataTable = $("#contestPipelineDrillInTable").dataTable();

                var rowHeight = 0;
                if($("#contestPipelineDrillInTable tbody tr").length > 0) {
                    rowHeight = $("#contestPipelineDrillInTable tbody tr:eq(0)").height();
                }

                var calHeight = 168 + rowHeight * $("#contestPipelineDrillInTable tbody tr").length;
                hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 60;
            }
            if ($("#projectPipelineViewPopup").is(':visible')) {
                visibleOverlay = $("#projectPipelineViewPopup");
                curOverlayDataTable = $("#projectPipelineDrillInTable").dataTable();
                var calHeight = 168 + 28 * $("#projectPipelineDrillInTable tbody tr").length;
                hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 60;
            }
            if ($("#financialViewPopup").is(':visible')) {
                visibleOverlay = $("#financialViewPopup");
                curOverlayDataTable = $("#financialDrillInTable").dataTable();
                var calHeight = 168 + 28 * $("#financialDrillInTable tbody tr").length;
                hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 60;
            }

            if (visibleOverlay) {
                visibleOverlay.css("width", wWid + "px");
                visibleOverlay.css("height", hHht + "px");
                var top = (containerH / 2) - (visibleOverlay.outerHeight() / 2);
                var left = (containerW / 2) - (visibleOverlay.outerWidth() / 2);
                visibleOverlay.css("top", top);
                visibleOverlay.css("left", left);
                hHht = $(window).height() > 800 ? 600 : $(window).height() - 136;
                setScrollY(curOverlayDataTable, hHht + "px");
                curOverlayDataTable.fnAdjustColumnSizing();
                curOverlayDataTable.fnDraw(false);
            }
        });
    }

    //window resize
    $(window).resize(function () {
        modalPosition();
        $('#filterModal').css('width', $('#mainSection').width());
        initFilterDate()
        var $width = $('#financials .totalBudget').width();
        var $widthHealth = $('#projectsHealthData .totalBudget').width();
        var $widthTotalSpend = $('#overviewFinancailData .totalBudget').width();
        $('#financials .midProjected').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#financials .midActual').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });

        $('#projectsHealthData .midProjected').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.processBar').width() - 12);
            } else {
                $(this).css('width', ($(this).parents('.processBar').width() - 12) * $(this).text());
            }
        });
        $('#projectsHealthData .midActual').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.processBar').width() - 12);
            } else {
                $(this).css('width', ($(this).parents('.processBar').width() - 12) * $(this).text());
            }
        });

        $('#overviewFinancailData .midProjected').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });
        $('#overviewFinancailData .midActual').each(function () {
            if ($(this).text() > 1) {
                $(this).css('width', $(this).parents('.totalBudget').width() - 7);
            } else {
                $(this).css('width', ($(this).parents('.totalBudget').width() - 7) * $(this).text());
            }
        });

        $('#mainSection .analyticsSection .lineViewContainer #lineView').css({'width':$('#mainSection').width() - 2, 'height':'360px'});
        $('#activeContest .filter .date-pick').css('width', ($('#activeContest .thirdCokumn').width() - 136) / 2);
        //$('.analyticsContainer .datePickerWrapper').css('width', ($('#mainSection').width() - $('.secondColumn').width() - $('.thirdColumn').width() - 144) / 2);
        //$('.analyticsContainer .date-pick').css('width', ($('#mainSection').width() - $('.secondColumn').width() - $('.thirdColumn').width() - 220) / 2);
        //$('.analyticsContainer .groupBy').css('width', ($('.analyticsContainer .firstColumn').width() - 190) / 2);
        $('.volumeView .tableData .ajaxTableLoader,.volumeView .tableData .noData').css({'margin-left':$('#mainSection').width() / 2 + 15, 'margin-top':'90px'});
        $('#calendarView .filter .filterContainer .filterValuesColumn').css('width', $('#mainSection').width() - $('#calendarView .filter .filterContainer .customerColumn').width() - $('#calendarView .filter .filterContainer .projectFiltersColumn').width() - $('#calendarView .filter .filterContainer .button').width() - 10);

    });


    //toggle
    $('.filter .folder').live('click', function () {
        if ($(this).hasClass('unfolder')) {
            $(this).removeClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', '#bdbdbd solid 1px');
            $('.filter .filterContainer').show();
        } else {
            $(this).addClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', 'none');
            $('.filter .filterContainer').hide();
        }
    });

    //tip for header
    $('.icon').hover(function () {
        var headerTip = '';
        headerTip += '<div class="headerTip"><div class="headerTipInner"><p>'
        headerTip += $(this).attr('rel');
        headerTip += '</p><div class="tipArrow"></div></div></div>';
        $('body').append(headerTip);
        $('.headerTip').css({'top':$(this).offset().top - $('.headerTip').height() - $(this).height() / 2, 'left':$(this).offset().left - $('.headerTip').width() / 2 + $(this).width() / 2});
    }, function () {
        $('.headerTip').remove();
    });

    $('#silderBar li a').hover(function () {
        var siderbarTip = '';
        if (!$(this).parents('li').hasClass('active')) {
            siderbarTip += '<div class="siderbarTip"><div class="siderbarTipInner"><h3>'
            siderbarTip += $(this).attr('title');
            siderbarTip += '</h3><p>';
            siderbarTip += $(this).attr('rel');
            siderbarTip += '</p><div class="tipArrow"></div></div></div>';
            $('body').append(siderbarTip);
            $('.siderbarTip').css({'top':$(this).offset().top - $('.siderbarTip').height() / 2 + $(this).height() / 2, 'left':$(this).width() + 10});
            $('.siderbarTip .tipArrow').css('top', ($('.siderbarTip').height() - 12) / 2);
        }
    }, function () {
        $('.siderbarTip').remove();
    });

    if ($('.date-pick').length > 0) {
        $(".date-pick").datePicker();
    }

    // filter sync
    if ($("#silderBar .active a.filterSynEnabled").length > 0 && $("#silderBar .active a.analyticsIcon").length <= 0 && getUrlPara('formData.clientId')) {
        var customer = getUrlPara('formData.clientId');
        var status = getUrlPara('formData.projectStatusId');

        var zoom = getUrlPara('formData.zoom');
        var startMonth = getUrlPara('formData.startMonth');
        var endMonth = getUrlPara('formData.endMonth');

        projectToSync = getUrlPara('formData.directProjectId');
        projectFilterToSync = getUrlPara('formData.projectFilterId');
        projectFilterValueToSync = getUrlPara('formData.projectFilterValue');

        $("#projectStatusFilter").val(status);
        $("#clientFilter").val(customer);
        var syncRequestData = getEnterpriseDashboardRequest(10000, 0, true, true);
        syncRequestData.projectFilterId = projectFilterToSync;
        syncRequestData.projectFilterValue = projectFilterValueToSync;

        if (zoom && zoom != 'null') {
            $("#zoomSelect").find("li a").removeClass("current");
            var zoomButton = $("#zoomSelect").find("li." + zoom).find("a");
            zoomButton.click();
        } else {
            $(".monthList .timeLine li").each(function () {
                if ($(this).find("span span").text() == startMonth.replace('%27', "'").toUpperCase()) {
                    step = $(this).index();
                    return false;
                }
            });

            moveMonth();
            $("#zoomSelect").find("li a.current").click();

            $(".monthList .timeLine li").each(function () {
                if ($(this).find("span span").text() == startMonth.replace('%27', "'").toUpperCase()) {
                    $(this).find("a").click();
                }
                if (startMonth != endMonth && $(this).find("span span").text() == endMonth.replace('%27', "'").toUpperCase()) {
                    $(this).find("a").click();
                }
            });
        }
        $("#headerClient").text(getFilterSelectorText('clientFilter'));
        $("#headerProject").html('<img src="/images/dots-white.gif"/>');
        $("#headerProjectStatus").text(getFilterSelectorText('projectStatusFilter'));
        $("#headerFilter").html('<img src="/images/dots-white.gif"/>');
        $("#headerFilterValue").html('<img src="/images/dots-white.gif"/>');
        $("#headerDate").text(startMonth == endMonth ? startMonth : startMonth + ' - ' + endMonth);

        // get options for the synchronized client
        $.ajax({
            type: 'POST',
            url: ctx + "/enterpriseDashboard/getOptionsForClientAJAX",
            data: {formData: syncRequestData},
            cache: false,
            async: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        var projects = result.projects;
                        var $project = $("#projectFilter");

                        $project.html("");
                        $project.append($('<option></option>').val(0).html("All Projects"));
                        $.each(projects, function (key, value) {
                            $project.append($('<option></option>').val(key).html(value));
                        });

                        $project.val(0);

                        if (projectToSync) {
                            $project.val(projectToSync);
                            projectToSync = null;
                        }

                        sortDropDown("#projectFilter");

                        var $projectFilters = $("#metaFilter");
                        $projectFilters.empty();

                        $projectFilters.append($("<option></option>").attr('value', 0).text("None"));

                        $.each(result.projectFilters, function (key, value) {
                            $projectFilters.append($("<option></option>").attr('value', key).text(value));
                        });

                        $projectFilters.val(0);

                        if (projectFilterToSync) {
                            $projectFilters.val(projectFilterToSync);
                            projectFilterToSync = null;
                        }

                        if (result.projectFilterValues) {
                            var $projectFilterValues = $("#metaValueFilter");
                            $projectFilterValues.empty();

                            $projectFilterValues.append($("<option></option>").attr('value', 'None').text("None"));

                            $.each(result.projectFilterValues, function (index, value) {
                                $projectFilterValues.append($("<option></option>").attr('value', value).text(value));
                            });

                            $projectFilterValues.val('None');

                            if (projectFilterValueToSync) {
                                $projectFilterValues.val(projectFilterValueToSync);
                                projectFilterValueToSync = null;
                            }
                        }
                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });

        // $("#projectStatusFilter").val(status).trigger('change');
    }


    // overview tab
    if ($(".overviewIcon").parents("li").hasClass("active")) {
        renderOverviewTab();

        $("#filterButton").click(function(){
            if(validateFilterPanel()) {
                modalAllClose();
                renderOverviewTab();
            }
        });

        $('.overRoadMapSection .tabPanel li a').live('click',function(){
            $('.overRoadMapSection .tabPanel li a').removeClass('current');
            $(this).addClass('current');
            $('.overRoadMapSection .tabSection').hide();
            $('.overRoadMapSection .tabSection').eq($('.overRoadMapSection .tabPanel li a').index(this)).show();
        });
    }

    // financial page
    if ($(".financialsIcon").parents("li").hasClass("active")) {
        $(".filterPanelButton .xls").show();
        renderFinancialTab();

        $("#filterButton").click(function(){
            if(validateFilterPanel()) {
                modalAllClose();
                renderFinancialTab();
            }
        });

        // setup drill-in for total spend chart in financial page
        $("#financialViewHandler").overlay({
            closeOnClick: false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top: "5%",
            close: "#financialViewClose",
            fixed: true,
            target: $("#financialViewPopup"),
            onBeforeLoad: function () {
                var wWid = $(window).width() > 1540 ? 1400 : $(window).width() - 50;
                var calHeight = 168 + 22 * $("#financialDrillInTable tbody tr").length;
                var hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 60;
                $("#financialViewPopup").css("width", wWid + "px");
                $("#financialViewPopup").css("height", hHht + "px");
            },
            onLoad: function () {
                var hHht = $(window).height() > 800 ? 600 : $(window).height() - 136;

                var table = $("#financialViewPopup").find('table').dataTable({
                    "bPaginate": false,
                    "bLengthChange": false,
                    "bFilter": false,
                    "bSort": true,
                    "bInfo": false,
                    "bAutoWidth": true,
                    "sScrollX": "100%",
                    "sScrollY": hHht + "px",
                    "bScrollCollapse": true,
                    "aoColumns": [
                        { "sType": "html" },
                        { "sType": "money" },
                        { "sType": "money" },
                        { "sType": "money" },
                        { "sType": "money" },
                        { "sType": "money" }
                    ]
                });
            }
        });
    }

    // project health page
    if ($(".projectHealthIcon").parents("li").hasClass("active")) {
        $(".filterPanelButton .xls").show();
        renderHealthTab();

        $("#filterButton").click(function(){
            if(validateFilterPanel()) {
                modalAllClose();
                renderHealthTab();
            }
        });
    }

    // pipeline
    if ($(".pipelineIcon").parents("li").hasClass("active")) {
        $(".filterPanelButton .xls").show();
        loadPipeline(renderPipelinePage);
        loadProjectPipeline(renderProjectsPipeline);

        $("#filterButton").click(function(){
            if(validateFilterPanel()) {
                modalAllClose();
                loadPipeline(renderPipelinePage);
                loadProjectPipeline(renderProjectsPipeline);
            }

        });

        $("#contestPipelineViewHandler").overlay({
            closeOnClick: false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top: "2%",
            close: "#contestPipelineViewClose",
            fixed: true,
            target: $("#contestPipelineViewPopup"),
            onBeforeLoad: function () {
                var wWid = $(window).width() > 1540 ? 1400 : $(window).width() - 50;
                var calHeight = 168 + 22 * $("#contestPipelineDrillInTable tbody").data("tableData").length;
                var hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 60;
                $("#contestPipelineViewPopup").css("width", wWid + "px");
                $("#contestPipelineViewPopup").css("height", hHht + "px");
            },
            onLoad: function () {
                var hHht = $(window).height() > 800 ? 600 : $(window).height() - 136;

                var table = $("#contestPipelineViewPopup").find('table').dataTable({
                    "aaData": $("#contestPipelineDrillInTable tbody").data("tableData"),
                    "bPaginate": false,
                    "bLengthChange": false,
                    "bFilter": false,
                    "bSort": true,
                    "bInfo": false,
                    "bAutoWidth": true,
                    "sScrollX": "100%",
                    "sScrollY": hHht + "px",
                    "bScrollCollapse": true
                });
            }
        });

        $("#projectPipelineViewHandler").overlay({
            closeOnClick: false,
            mask: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            top: "5%",
            close: "#projectPipelineViewClose",
            fixed: true,
            target: $("#projectPipelineViewPopup"),
            onBeforeLoad: function () {
                var wWid = $(window).width() > 1540 ? 1400 : $(window).width() - 50;
                var calHeight = 168 + 22 * $("#projectPipelineDrillInTable tbody tr").length;
                var hHht = $(window).height() > calHeight ? calHeight : $(window).height() - 150;
                $("#projectPipelineViewPopup").css("width", wWid + "px");
                $("#projectPipelineViewPopup").css("height", hHht + "px");
            },
            onLoad: function () {
                var hHht = $(window).height() > 800 ? 600 : $(window).height() - 136;

                var table = $("#projectPipelineViewPopup").find('table').dataTable({
                    "bPaginate": false,
                    "bLengthChange": false,
                    "bDeferRender": true,
                    "bFilter": false,
                    "bSort": false,
                    "bInfo": false,
                    "bAutoWidth": true,
                    "sScrollX": "100%",
                    "sScrollY": hHht + "px",
                    "bScrollCollapse": true
                });
            }
        });

    }


    // roadmap page
    if ($(".roadmapIcon").parents("li").hasClass("active")) {
        loadRoadmap(renderRoadmapPage);
        loadRoadmapCalendar();

        $("#filterButton").click(function(){
            if(validateFilterPanel()) {
                modalAllClose();
                loadRoadmap(renderRoadmapPage);
                loadRoadmapCalendar();
            }
        });

        $('.roadMapSection .tabPanel li a').live('click',function(){
            $('.roadMapSection .tabPanel li a').removeClass('current');
            $(this).addClass('current');
            $('.roadMapSection .tabSection').hide();
            $('.roadMapSection .tabSection').eq($('.roadMapSection .tabPanel li a').index(this)).show();
        });
    }

    // active contests
    if($('.dashboardPage #activeContest').size() != 0){
        $.dashboardActiveContestsDataTable = $('#activeContest .paginatedDataTable').dataTable({
            "iDisplayLength":10,
            "bStateSave": false,
            "bFilter":true,
            "bSort":true,
            "bAutoWidth": false,
            "oLanguage":{
                "sLengthMenu":sStdMenu,
                "oPaginate":{
                    "sFirst":"",
                    "sPrevious":"Prev",
                    "sNext":"Next",
                    "sLast":""
                }
            },
            "aaSorting": [
                [3,'desc']
            ],
            "aoColumns": [
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "date-direct" },
                { "sType": "date-direct" },
                { "sType": "link-number" },
                { "sType": "submission-number" },
                { "sType": "link-number" },
                { "sType": "html" },
                null,
                null
            ],
            "sPaginationType":"full_numbers",
            "sDom":'<"pagePanel topPagePanel"i<"showPage"l><"pageNum"p>>t<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>'
        });

        setTimeout(function () {
            $(".filterContainer select").each(function () {
                sortDropDown('#' + $(this).attr('id'));
            })
        }, 1000);


    }

    // analysis
    if($(".analyticsIcon").parents("li").hasClass("active")) {

        // setup codes
        $('.analyticsContainer .groupBy').css('width',($('.analyticsContainer .firstColumn').width()-190)/2);
        $('.analyticsContainer .datePickerWrapper').css('width',($('#mainSection').width()-$('.secondColumn').width()-$('.thirdColumn').width()-144)/2);
        $('.analyticsContainer .date-pick').css('width',($('#mainSection').width()-$('.secondColumn').width()-$('.thirdColumn').width()-220)/2);

        // setup filter panel - collapse / expand
        $('.filterForAnalytics .folder').live('click',function(){
            if($(this).hasClass('unfolder')){
                $(this).removeClass('unfolder');
                $(this).parents('.filterTitle').css('border-bottom','#bdbdbd solid 1px');
                $('.filterForAnalytics .filterContainer').show();
            }else{
                $(this).addClass('unfolder');
                $(this).parents('.filterTitle').css('border-bottom','none');
                $('.filterForAnalytics .filterContainer').hide();
            }
        });

        // setup chart view / table view toggles
        $('.analyticsView .viewTitle li a').live('click',function(){
            var showContainer = '.'+$(this).attr('rel');
            $('.analyticsView .viewTitle li').removeClass('active');
            $(this).parents('li').addClass('active');
            $('.analyticsView .analyticsViewTabContainer').hide();
            $('.analyticsView').find(showContainer).show();
            if($(this).text() == 'Table'){
                $('.analyticsView .viewTitle .folder').text('Table View');
                $("div.volumeView").hide();
                loadTableView();
            }else{
                $('.analyticsView .viewTitle .folder').text('Chart View');
                loadAnalysis();
            }
        });

        // setup chart view - collapse / expand
        $('.analyticsView .folder').live('click',function(){
            if($(this).hasClass('unfolder')){
                $(this).removeClass('unfolder');
                $('.analyticsView .viewContainer').show();
            }else{
                $(this).addClass('unfolder');
                $('.analyticsView .viewContainer').hide();
            }
        });


        ////////////// filter panel //////////////////////

        // customer selector - change event, reloads the billing accounts and projects
        $("#customer").change(function () {
            loadOptionsByClientId($(this).val());
        });

        // billing account selector - change event, reloads the projects
        $("#billingAccount").change(function() {

            var billingId = $(this).val();

            if (billingId == 0) {
                // select all again, load all the billings and projects for customer
                var customerId = $("#customer").val();

                loadOptionsByClientId(customerId);

                return;
            }

            $.ajax({
                type: 'POST',
                url:  ctx + "/enterpriseDashboard/dashboardGetOptionsForBillingAJAX",
                data: {'formData.billingAccountIds':billingId},
                cache: false,
                dataType: 'json',
                success: function(jsonResult) {
                    handleJsonResult(jsonResult,
                        function(result) {
                            var projects = result.projects;
                            var $project = $("#project");

                            $project.html("");
                            $.each(projects, function(key, value) {
                                $project.append($('<option></option>').val(key).html(value));
                            });

                            // append the default "select all"
                            $project.append($('<option></option>').val(0).html("All Projects"));
                            $project.val(0);
                            sortDropDown("#project");
                        },
                        function(errorMessage) {
                            // TODO
                            $('#validationErrors').html(errorMessage);
                        });
                }
            });
        });

        // contest type selector - select highlighting
        $('.filterForAnalytics .selectWrapper li input').live('click',function(){
            if($(this).attr('checked')){
                $(this).parents('li').addClass('selected');
            }else{
                $(this).parents('li').removeClass('selected');
            }
        });

        $('.filterForAnalytics .selectWrapper li input').each(function(){
            if($(this).attr('checked')){
                $(this).parents('li').addClass('selected');
            }else{
                $(this).parents('li').removeClass('selected');
            }
        });

        // contest type selector - label click event
        $('.filterForAnalytics .selectWrapper li span').live('click',function(){
            $(this).parents('li').find('input').trigger('click');
            if($(this).parents('li').find('input').attr('checked')){
                $(this).parents('li').addClass('selected');
            }else{
                $(this).parents('li').removeClass('selected');
            }
        });

        // contest type selector - Select all click event
        $('.filterForAnalytics .selectWrapper #checkAll').live('change',function(){
            if($(this).attr('checked')){
                $(this).parents('.selectWrapper').find('input').attr('checked',true);
                $(this).parents('.selectWrapper').find('li').addClass('selected');
            }else{
                $(this).parents('.selectWrapper').find('input').attr('checked',false);
                $(this).parents('.selectWrapper').find('li').removeClass('selected');
            }
        });

        // set the start date of the week to monday for date picker
        Date.firstDayOfWeek = 0;
        var monthsLookup = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

        function getMonday(d) {
            var day = d.getDay();
            var diff = d.getDate() - day + (day == 0 ? -7 : 0);
            var r = new Date(d.setDate(diff));
            return r;
        }

        function getQuarter(d) {
            var m = d.getMonth();
            return Math.floor( m/3 ) + 1;
        }

        function getYearOptions(endYear) {
            var yearOptions = '';
            for(var i = endYear; i >= 2005; i--) {
                yearOptions += '<option value="' + i + '">' + i + '</option>';
            }
            return yearOptions;
        }

        function getWeekRangeFromNow(weekNumber) {
            var currentDate = new Date();
            var endDateStr = $.datepicker.formatDate('mm/dd/yy', getMonday(currentDate));
            var startDate = new Date(currentDate.setDate(currentDate.getDate() - 7 * (weekNumber - 1)));
            var startDateStr = $.datepicker.formatDate('mm/dd/yy', getMonday(startDate));
            var result = {};
            result.startWeek = startDateStr;
            result.endWeek = endDateStr;
            return result;
        }

        function getMonthRangeFromNow(monthNumber) {
            var currentDate = new Date();
            var currentYear = currentDate.getFullYear();
            var currentMonth = currentDate.getMonth();
            var result = {};
            result.endYear = currentYear;
            result.endMonth = currentMonth;
            result.startYear = currentYear - ((currentMonth - monthNumber + 1) < 0 ? 1 : 0);
            result.startMonth = ((currentMonth - monthNumber + 1) + 12) % 12;
            return result;
        }

        function getQuarterRangeFromNow(quarterNumber) {
            var currentDate = new Date();
            var currentYear = currentDate.getFullYear();
            var currentQuarter = getQuarter(currentDate);
            var result = {};
            result.endYear = currentYear;
            result.endQuarter = currentQuarter;
            result.startYear = currentYear - ((currentQuarter - 4) < 0 ? 1 : 0);
            result.startQuarter = ((currentQuarter - 4) + 12) % 12;
            return result;
        }

        function getYearRangeFromNow(yearNumber) {
            var currentDate = new Date();
            var currentYear = currentDate.getFullYear();
            var result = {};
            result.endYear = currentYear;
            result.startYear = currentYear - yearNumber + 1;
            return result;
        }

        function updateWeekTime(weekNumber) {
            // eight weeks range by default
            var result = getWeekRangeFromNow(weekNumber);

            var newDate =
                '<label class="inline">Start:</label>' +
                    '<div class="datePickerWrapper"><input id="endDateBegin" type="text" class="date-pick" value="'+ result.startWeek + '" /></div>' +
                    '<label class="inline endLabel">End:</label>' +
                    '<div class="datePickerWrapper EndDatePickerWrapper"><input id="endDateEnd" type="text" class="date-pick" value="' + result.endWeek + '" /></div>';

            $(".dateRow>div").html(newDate);
            $(".dateRow>div>div>select").css({"width":"100px"});
            $(".dateRow>div>div").css({"float":"left", "padding-left":"0px"});
            $(".dateRow").css({"width":"800px"});
            if ($('.date-pick').length > 0) {
                $(".date-pick").datePicker({selectWeek:true, closeOnSelect:false, startDate:'01/01/2005'});
            }
        }

        function updateMonthTime(monthNumber) {
            var result = getMonthRangeFromNow(monthNumber);

            var yearOptions = getYearOptions(result.endYear);

            var newDate =
                '<div>' +
                    '<label class="inline">Year:</label>' +
                    '<select class="groupBy" name="startYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">Month:</label>' +
                    '<select class="groupBy" name="startMonth">' +
                    '<option value = "0">January</option>' +
                    '<option value = "1">February</option>' +
                    '<option value = "2">March</option>' +
                    '<option value = "3" >April</option>' +
                    '<option value = "4" selected="selected">May</option>' +
                    '<option value = "5">June</option>' +
                    '<option value = "6">July</option>' +
                    '<option value = "7">August</option>' +
                    '<option value = "8">September</option>' +
                    '<option value = "9">October</option>' +
                    '<option value = "10">November</option>' +
                    '<option value = "11">December</option>' +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">--</label>' +
                    '<label class="inline">Year:</label>' +
                    '<select  class="groupBy" name="endYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">Month:</label>' +
                    '<select  class="groupBy" name="endMonth">' +
                    '<option value = "0">January</option>' +
                    '<option value = "1">February</option>' +
                    '<option value = "2">March</option>' +
                    '<option value = "3">April</option>' +
                    '<option value = "4">May</option>' +
                    '<option value = "5" >June</option>' +
                    '<option value = "6">July</option>' +
                    '<option value = "7">August</option>' +
                    '<option value = "8">September</option>' +
                    '<option value = "9">October</option>' +
                    '<option value = "10">November</option>' +
                    '<option value = "11">December</option>' +
                    '</select>'
                    + '</div>';
            $(".dateRow>div").html(newDate);
            $(".dateRow>div>div>select").css({"width":"100px"});
            $(".dateRow>div>div").css({"float":"left", "padding-left":"0px"});
            $(".dateRow").css({"width":"800px"});
            $('select[name=endYear]').val(result.endYear);
            $('select[name=endMonth]').val(result.endMonth);
            $('select[name=startYear]').val(result.startYear);
            $('select[name=startMonth]').val(result.startMonth);
        }

        function updateQuarterTime(quarterNumber) {
            var result = getQuarterRangeFromNow(quarterNumber);

            var yearOptions = getYearOptions(result.endYear);

            var newDate =
                '<div>' +
                    '<label class="inline">Year:</label>' +
                    '<select class="groupBy" name="startYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">Quarter:</label>' +
                    '<select class="groupBy" name="startQuarter">' +
                    '<option value = "1">Q1</option>' +
                    '<option value = "2">Q2</option>' +
                    '<option value = "3">Q3</option>' +
                    '<option value = "4">Q4</option>' +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">--</label>' +
                    '<label class="inline">Year:</label>' +
                    '<select class="groupBy" name="endYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">Quarter:</label>' +
                    '<select  class="groupBy" name="endQuarter">' +
                    '<option value = "1">Q1</option>' +
                    '<option value = "2">Q2</option>' +
                    '<option value = "3">Q3</option>' +
                    '<option value = "4">Q4</option>' +
                    '</select>' +
                    '</div>';
            $(".dateRow>div").html(newDate);
            $(".dateRow>div>div>select").css({"width":"100px"});
            $(".dateRow>div>div").css({"float":"left", "padding-left":"0px"});
            $(".dateRow").css({"width":"800px"});
            $('select[name=endYear]').val(result.endYear);
            $('select[name=endQuarter]').val(result.endQuarter);
            $('select[name=startYear]').val(result.startYear);
            $('select[name=startQuarter]').val(result.startQuarter);
        }

        function updateYearTime(yearNumber) {
            var result = getYearRangeFromNow(yearNumber);
            var yearOptions = getYearOptions(result.endYear);

            var newDate =
                '<div>' +
                    '<label class="inline">Year:</label>' +
                    '<select class="groupBy" name="startYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>' +
                    '<div>' +
                    '<label class="inline">--</label>' +
                    '<label class="inline">Year:</label>' +
                    '<select  class="groupBy" name="endYear">' +
                    yearOptions +
                    '</select>' +
                    '</div>';
            $(".dateRow>div").html(newDate);
            $(".dateRow>div>div>select").css({"width":"100px"});
            $(".dateRow>div>div").css({"float":"left", "padding-left":"0px"});
            $(".dateRow").css({"width":"800px"});
            $("select[name=startYear]").val(result.startYear);
            $("select[name=endYear]").val(result.endYear);
        }

        // group by selector - change event
        $("#timeDimension").change(function () {

            // Week
            if ($(this).find("option:selected").text() == "Week") {
                var zoom =
                    '<li class="oneWeek firstSelectot zoom"><a href="javascript:;"><span>1 Week</span></a></li>' +
                        '<li class="twoWeek zoom"><a href="javascript:;"><span>2 Weeks</span></a></li>' +
                        '<li class="fourWeek zoom"><a href="javascript:;"><span>4 Weeks</span></a></li>' +
                        '<li class="eightWeek lastSelector zoom"><a href="javascript:;"><span>8 Weeks</span></a></li>';
                $(".zoomButton ul").html(zoom);

                updateWeekTime(8);
            }


            if ($(this).find("option:selected").text() == "Month") {
                var zoom =
                    '<li class="oneMonth firstSelectot zoom"><a href="javascript:;"><span>1 Month</span></a></li>' +
                        '<li class="twoMonth zoom"><a href="javascript:;"><span>2 Months</span></a></li>' +
                        '<li class="threeMonth zoom"><a href="javascript:;"><span>3 Months</span></a></li>' +
                        '<li class="sixMonth lastSelector zoom"><a href="javascript:;"><span>6 Months</span></a></li>';
                $(".zoomButton ul").html(zoom);

                updateMonthTime(6);
            }

            if ($(this).find("option:selected").text() == "Quarter") {
                var zoom =
                    '<li class="oneQuarter firstSelectot quarter zoom"><a href="javascript:;"><span>1 Quarter</span></a></li>' +
                        '<li class="twoQuarter quarter zoom"><a href="javascript:;"><span>2 Quarters</span></a></li>' +
                        '<li class="threeQuarter quarter zoom"><a href="javascript:;"><span>3 Quarters</span></a></li>' +
                        '<li class="fourQuarter lastSelector quarter zoom"><a href="javascript:;"><span>4 Quarters</span></a></li>';
                $(".zoomButton ul").html(zoom);

                updateQuarterTime(4);
            }

            // year
            if ($(this).find("option:selected").text() == "Year") {
                var zoom =
                    '<li class="oneYear firstSelectot zoom"><a href="javascript:;"><span>1 Year</span></a></li>' +
                        '<li class="twoYear zoom"><a href="javascript:;"><span>2 Years</span></a></li>' +
                        '<li class="threeYear zoom"><a href="javascript:;"><span>3 Years</span></a></li>';
                $(".zoomButton ul").html(zoom);

                updateYearTime(2);
            }
        });

        $("#timeDimension").val('Month').trigger('change');

        // summary tab - tab switch
        $('.analyticsView .customer a').live('click',function(){
            var showContainer = '.'+$(this).attr('rel');
            $('.analyticsView .customer a').removeClass('current');
            $(this).addClass('current');
            $('.analyticsView .customerTabContainer').hide();
            $('.analyticsView').find(showContainer).show();
        });

        // summary tab - on hover tips
        $('.tipToggle').hover(function () {
            switch ($(this).parents('.customerTabContainer').attr('id')) {
                case 'blue':
                    $('#tipForTab').addClass('blueTab');
                    break;
                case 'green':
                    $('#tipForTab').addClass('greenTab');
                    break;
                case 'orange':
                    $('#tipForTab').addClass('orangeTab');
                    break;
                default:
                    $('#tipForTab').addClass('blueTab');
                    break;
            }
            $('#tipForTab').show();
            $('#tipForTab .tooltipBox .tooltipHeader h2').text($(this).text().replace(':', ''));
            $('#tipForTab .tooltipBox .tooltipContent p').text($(this).attr('title'));
            $(this).attr('title', '');
            $('#tipForTab .tooltipBox').css({'left':$(this).offset().left - $('#tipForTab .tooltipBox').width() / 2 + $(this).width() / 2, 'top':$(this).offset().top - $('#tipForTab .tooltipBox').height() - 6});
        }, function () {
            $('#tipForTab').hide();
            $(this).attr('title', $('#tipForTab .tooltipBox .tooltipContent p').text());
            $('#tipForTab').removeClass('blueTab');
            $('#tipForTab').removeClass('greenTab');
            $('#tipForTab').removeClass('orangeTab');
        });


        function renderSummary(result) {
            $(".viewDate").show();
            $("#customerAverageFulfillment, #customerAverageFulfillmentComparison").html(result.customerAverageFulfillment.toString() + '%');
            $("#customerAverageCost, #customerAverageCostComparison").html('$' + result.customerAverageCost.toString());
            $("#customerAverageDuration, #customerAverageDurationComparison").html(result.customerAverageDuration.toString() + ' days');
            $("#customerAverageVol, #customerAverageVolComparison").html(result.customerAverageVol.toString());
            $("#customerTotalVol, #customerTotalVolComparison").html(result.customerTotalVol.toString());
            $("#customerTotalCost, #customerTotalCostComparison").html('$' + result.customerTotalCost.toString());
            $("#customerMinMaxCost, #customerMinMaxCostComparison").html('$' + result.customerMinCost.toString() + " - $" + result.customerMaxCost.toString());
            $("#customerMinMaxDuration, #customerMinMaxDurationComparison").html(result.customerMinDuration.toString() + " - " + result.customerMaxDuration.toString() + ' days');
            $("#marketAverageFulfillment, #marketAverageFulfillmentComparison").html(result.marketAverageFulfillment.toString() + '%');
            $("#marketAverageCost, #marketAverageCostComparison").html('$' + result.marketAverageCost.toString());
            $("#marketAverageDuration, #marketAverageDurationComparison").html(result.marketAverageDuration.toString() + ' days');
            $("#marketAverageVol, #marketAverageVolComparison").html(result.marketAverageVol.toString());
            $("#marketTotalVol, #marketTotalVolComparison").html(result.marketTotalVol.toString());
            $("#marketTotalCost, #marketTotalCostComparison").html('$' + result.marketTotalCost.toString());
            $("#marketMinMaxCost, #marketMinMaxCostComparison").html('$' + result.marketMinCost.toString() + " - $" + result.marketMaxCost.toString());
            $("#marketMinMaxDuration, #marketMinMaxDurationComparison").html(result.marketMinDuration.toString() + " - " + result.marketMaxDuration.toString() + ' days');
            if(result.hasMarketSummary && result.hasCustomerSummary) {
                $("#marketCap").html(result.marketCap + "%");
                $("#customerMarketCap").html(result.marketCap + "%");
            }
            $("td.customerReplacement").text(customerName);
            $("span.customerReplacement").text(customerName + ' Summary');
            $("h3.customerReplacement").text(customerName + ' Volume Summary');
        }

        $('.lineViewContainer .displayButton a').live('click', function () {

            if ($(this).find('input').is(':checked') == true) {
                $(this).find('input').attr('checked', false);
                $(this).removeClass('current');
            } else {
                $(this).find('input').attr('checked', true);
                $(this).addClass('current');
            }
            renderAnalysisByDisplayTypes();
        });

        function loadAnalysis() {
            var request = getRequestForAnalysis();
            if(!request) return false;

            var data = request.formData;
            volumeTableLoaded = false;
            $.ajax({
                type:'POST',
                url: ctx + '/dashboardEnterpriseAJAX',
                data:request,
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function(result) {
                            // update the chart viewer header
                            $(".filterPanelHeader h3").text(getFilterSelectorText('customer'));
                            $("#projectHeader").text(getFilterSelectorText('project'));
                            $("#billingHeader").text(getFilterSelectorText('billingAccount'));
                            $("#groupHeader").text(getFilterSelectorText('timeDimension'));
                            $("#timeHeader").text(data.startDate + ' to ' + data.endDate);
                            analysisData = result;

                            if(result.hasCustomerSummary == false) {
                                $(".viewDate").hide();
                                $('#lineView').empty().append('<div class="ajaxTableLoader"><span style="font-size:12px;color:grey;">Not Enough Data For Filters Selected</span></div>');
                                return;
                            }

                            // render the summary tabs
                            renderSummary(result);
                            renderAnalysisByDisplayTypes();
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        })
                }
            });
            return true;
        }

        function loadTableView() {
            var request = getRequestForAnalysis();

            if(!request) return false;

            $(".tableViewContainer tbody").empty().append('<tr><td colspan="11">' +
                    '<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading"/></div></td></tr>'
            );

            var data = request.formData;
            $.ajax({
                type:'POST',
                url: ctx + '/dashboardEnterpriseAJAX',
                data:request,
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function(result) {
                            // update the chart viewer header
                            $(".filterPanelHeader h3").text($("#customer option:selected").text());
                            $("#projectHeader").text($("#project option:selected").text());
                            $("#billingHeader").text($("#billingAccount option:selected").text());
                            $("#groupHeader").text($("#timeDimension option:selected").text());
                            $("#timeHeader").text(data.startDate + ' to ' + data.endDate);
                            analysisData = result;

                            if(result.hasCustomerSummary == false) {
                                $(".viewDate").hide();
                                $('#lineView').empty().append('<div class="ajaxTableLoader"><span style="font-size:12px;color:grey;">Not Enough Data For Filters Selected</span></div>');
                                return;
                            }
                            // render the summary tabs
                            renderSummary(result);
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        })
                }
            });

            delete request.dashboardViewType;
            delete request.groupByType;

            $.ajax({
                type:'POST',
                url: ctx + '/dashboardEnterpriseTableViewCall',
                data:request,
                cache:false,
                dataType:'json',
                success:function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function (result) {
                            var strData;
                            if (result.contestStatus.length) {
                                $.each(result.contestStatus, function (idx, item) {
                                    var projectLink = "/direct/projectOverview.action?formData.projectId=" + item.directProjectId;
                                    var contestLink = "/direct/contest/detail.action?projectId=" + item.projectId;

                                    if(item.contestType == 'Copilot Posting') {
                                        contestLink = "/direct/copilot/copilotContestDetails.action?projectId=" + item.projectId;
                                    }

                                    var date = $.datepicker.formatDate('yy-mm-dd', new Date(item.date));

                                    strData += '<tr>';
                                    strData += '<td>' + date + '</td>';
                                    strData += '<td>' + item.customerName + '</td>';
                                    strData += '<td class="alignLeft"><a target="_blank" href="' + projectLink +  '">' + item.projectName + '</a></td>';
                                    strData += '<td class="alignLeft"><a target="_blank" href="' + contestLink +  '">' + item.contestName + '</a></td>';
                                    strData += '<td class="alignLeft">' + item.contestType + '</td>';
                                    strData += '<td>' + item.contestFullfilment + '%</td>';
                                    strData += '<td><span class="marketNumber">' + item.marketAvgFullfilment + '%</span></td>';
                                    strData += '<td>$' + item.contestCost + '</td>';
                                    strData += '<td><span class="marketNumber">$' + item.marketAvgCost + '</span></td>';
                                    strData += '<td>' + item.contestDuration + '</td>';
                                    strData += '<td><span class="marketNumber">' + item.marketAvgDuration + '</span></td>';
                                    strData += '</tr>';
                                });

                                if(result.contestStatus.length){

                                    if (typeof tableViewTable == 'undefined') {
                                        tableViewTableTemplate = $(".tableViewContainer").html();
                                    } else {
                                        $(".tableViewContainer").html(tableViewTableTemplate);
                                    }

                                    $(".tableViewContainer tbody").empty().append(strData);
                                    $('.tableViewContainer .pagePanel .totalNumber').text(result.contestStatus.length);

                                    tableViewTable = $('.tableViewContainer table').dataTable({
                                        "iDisplayLength":10,
                                        "bFilter":false,
                                        "bSort":true,
                                        "oLanguage":{
                                            "sLengthMenu":sStdMenu,
                                            "oPaginate":{
                                                "sFirst":"",
                                                "sPrevious":"Prev",
                                                "sNext":"Next",
                                                "sLast":""
                                            }
                                        },
                                        "sPaginationType":"full_numbers",
                                        "sDom":'t<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>',
                                        "aaSorting": [
                                            [0,'asc']
                                        ],
                                        "aoColumns": [
                                            { "sType": "simple-date" },
                                            { "sType": "html" },
                                            { "sType": "html-trimmed" },
                                            { "sType": "html-trimmed" },
                                            { "sType": "html" },
                                            { "sType": "number-trimmed" },
                                            { "sType": "number-trimmed" },
                                            { "sType": "money"},
                                            { "sType": "money"},
                                            { "sType": "number-trimmed" },
                                            { "sType": "number-trimmed" }
                                        ]
                                    });


                                }
                            } else{
                                $(".tableViewContainer tbody").empty().append('<tr><td colspan="11"><div style="font-size:12px;color:grey;">No data available</div></td></tr>');
                            }
                        },
                        function (errorMessage) {
                            showServerError(errorMessage);
                        })
                }
            });

            return true;
        }

        $("#filterApply").click(function () {
            modalPreloader();

            var isInTableView = $(".analyticsView  .viewTitle li:eq(1)").hasClass("active");

            if(!isInTableView) {
                if(!loadAnalysis()) {
                    modalAllClose();
                }

            } else {
                if(!loadTableView()) {
                    modalAllClose();
                }
            }
        });

        if($("#silderBar .active a.filterSynEnabled").length > 0 && getUrlPara('customer')) {
            $("#customer").val(getUrlPara('customer')).trigger('change');
        }

        $('.lineViewContainer .zoomButton a').live('click', function () {
            $('.lineViewContainer .zoomButton a').removeClass('current');
            $(this).addClass('current');
            var timeNumber = $.trim($(this).find("span").text())[0] / 1;
            var timeDimension = $("#timeDimension").val();
            if(timeDimension == 'Week') {
                updateWeekTime(timeNumber);
            } else if(timeDimension == 'Month') {
                updateMonthTime(timeNumber);
            } else if(timeDimension == 'Quarter') {
                updateQuarterTime(timeNumber);
            } else if(timeDimension == 'Year') {
                updateYearTime(timeNumber);
            }
            $('#lineView').empty().append('<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>');
            loadAnalysis();
        });

        loadAnalysis();
    }
});

function loadOptionsByClientId(clientId) {
    $.ajax({
        type: 'POST',
        url:  ctx + "/dashboardGetOptionsForClientAJAX",
        data: {'formData.customerIds':clientId},
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    var billings = result.billings;
                    var projects = result.projects;
                    var $billing = $("#billingAccount");
                    var $project = $("#project");

                    $billing.html("");
                    $.each(billings, function(key, value) {
                        $billing.append($('<option></option>').val(key).html(value));
                    });

                    // append the default "select all"
                    $billing.append($('<option></option>').val(0).html("All Billing Accounts"));
                    $billing.val(0);

                    $project.html("");
                    $.each(projects, function(key, value) {
                        $project.append($('<option></option>').val(key).html(value));
                    });

                    // append the default "select all"
                    $project.append($('<option></option>').val(0).html("All Projects"));
                    $project.val(0);
                    if(projectToSync) {
                        $project.val(projectToSync);
                        projectToSync = null;
                    }
                    sortDropDown("#project");
                    sortDropDown("#billingAccount");
                },
                function(errorMessage) {
                    // TODO
                    $('#validationErrors').html(errorMessage);
                });
        }
    });
}

function displayChartView(contestData, contestDurationView, costView, fulfillView, volumeView, marketView) {

    if(volumeView) {
        renderVolumeSummaryTable(contestData.volumeSummaryData);
    } else {
        $("div.volumeView").hide();
    }

    var xAxisCate = new Array();
    var customerCost = new Array();
    var marketCost = new Array();
    var customerContestDuration = new Array();
    var marketContestDuration = new Array();
    var customerFulfillment = new Array();
    var marketFulfillment = new Array();
    var customerVolume = new Array();
    var marketVolume = new Array();
    var iRotation = 0;
    if ($('#lineView').length > 0) {

        var costData;
        var contestDurationData;
        var fulfillmentData;
        var groupType = $("#timeDimension").val();

        if(groupType == 'Week') {
            costData = contestData.cost.week;
            contestDurationData = contestData.contest.week;
            fulfillmentData = contestData.fulfill.week;
        } else if(groupType == 'Month') {
            costData = contestData.cost.month;
            contestDurationData = contestData.contest.month;
            fulfillmentData = contestData.fulfill.month;
        } else if(groupType == 'Quarter') {
            costData = contestData.cost.quarter;
            contestDurationData = contestData.contest.quarter;
            fulfillmentData = contestData.fulfill.quarter;
        } else if(groupType == 'Year') {
            costData = contestData.cost.year;
            contestDurationData = contestData.contest.year;
            fulfillmentData = contestData.fulfill.year;
        }

        var volumeData = contestData.volume;

        // setup cost data and X axis
        $.each(costData, function (idx, item) {
            xAxisCate.push(item.date);
            customerCost.push(item.customer / 1);
            marketCost.push(item.tc / 1);
        });

        // setup contest duration data
        $.each(contestDurationData, function (idx, item) {
            customerContestDuration.push(item.customer / 1);
            marketContestDuration.push(item.tc / 1);
        });

        // setup fulfillment data
        $.each(fulfillmentData, function (idx, item) {
            customerFulfillment.push(item.customer / 1);
            marketFulfillment.push(item.tc / 1);
        });

        // setup volume data
        $.each(volumeData, function (idx, item) {
            customerVolume.push(item.customer / 1);
            marketVolume.push(item.tc / 1);
        });

        var option = {
            chart:{
                renderTo:'lineView',
                type:'column',
                marginTop:30,
                marginLeft:100,
                marginRight:100,
                marginBottom:55
            },
            credits:{
                text:''
            },
            navigation:{
                buttonOptions:{
                    enabled:false
                }
            },
            title:{
                text:null
            },
            xAxis:{
                categories:xAxisCate,
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    },
                    rotation:iRotation
                },
                tickLength:0
            },
            yAxis:[],
            legend:{
                align:'right',
                verticalAlign:'top',
                y:-20,
                backgroundColor:'#ffffff',
                borderWidth:0,
                borderRadius:0,
                itemMarginTop:5,
                itemMarginBottom:5,
                itemStyle:{
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#898989'
                },
                reversed:true
            },
            tooltip:{
                useHTML:true,
                backgroundColor:null,
                borderRadius:0,
                borderWidth:0,
                shadow:false,
                style:{
                    margin:'0px',
                    padding:'0px',
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#333333'
                },
                formatter:function () {
                    var s = '';
                    if (this.series.name.indexOf('Fulfillment') != -1) {
                        s = ' %';
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                            this.point.category
                            + '</strong><div>' +
                            this.series.name
                            + ': ' +
                            this.point.y
                            + s
                            + '</div></div></div>';
                    } else if (this.series.name.indexOf('Duration') != -1) {
                        s = ' days';
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                            this.point.category
                            + '</strong><div>' +
                            this.series.name
                            + ': ' +
                            this.point.y
                            + s
                            + '</div></div></div>';
                    } else if (this.series.name.indexOf('Cost') != -1) {
                        s = '$ ';
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                            this.point.category
                            + '</strong><div>' +
                            this.series.name
                            + ': ' +
                            s +
                            this.point.y
                            + '</div></div></div>';
                    } else {
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>' +
                            this.point.category
                            + '</strong><div>' +
                            this.series.name
                            + ': ' +
                            this.point.y
                            + '</div></div></div>';
                    }
                }
            },
            series:[]
        };
        var axisNumber = 0;
        var costViewLegend = 'Average ' + customerName + ' Cost';
        var durationViewLegend = 'Average ' + customerName + ' Challenge Duration';
        var fulfillViewLegend = 'Average ' + customerName + ' Fulfillment';
        var volumeViewLegend = customerName + ' Volume';

        if (costView) {
            option.series.push({
                name:costViewLegend,
                color:'#8fd31a',
                yAxis:axisNumber,
                type:'line',
                data:customerCost,
                shadow:false
            });
            option.yAxis.push({ //For Cost
                min:0,
                title:{
                    text:'Cost',
                    style:{
                        fontFamily:'Arial',
                        fontWeight:'normal',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    }
                }
            });

            if (marketView) {
                option.series.push({
                    name:'Average Market Cost',
                    color:'#8fd31a',
                    yAxis:axisNumber,
                    type:'line',
                    dashStyle:'ShortDash',
                    showInLegend:false,
                    data:marketCost,
                    shadow:false
                });
            }


            axisNumber++;
        }

        if (volumeView) {
            option.series.push({
                name:volumeViewLegend,
                data:customerVolume,
                color:'#3465cc',
                yAxis:axisNumber,
                type:'line',
                borderColor:'#002e88'
            });
            option.yAxis.push({ //For Volume
                min:0,
                title:{
                    text:'Volume',
                    style:{
                        fontFamily:'Arial',
                        fontWeight:'normal',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                stackLabels:{
                    enabled:true,
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                opposite:true
            });
            if (marketView) {
                option.series.push({
                    name:'Market Volume',
                    data:marketVolume,
                    color:'#3465cc',
                    yAxis:axisNumber,
                    showInLegend:false,
                    type:'line',
                    dashStyle:'ShortDash',
                    borderColor:'#002e88'
                });
            }
            axisNumber++;
        }

        if(contestDurationView) {
            option.series.push({
                name: durationViewLegend,
                color: '#07b5fa',
                yAxis: axisNumber,
                type: 'line',
                data: customerContestDuration,
                shadow: false
            });

            option.yAxis.push( { //For Contest Duration
                min: 0,
                title: {
                    text: 'Challenge Duration',
                    style: {
                        fontFamily: 'Arial',
                        fontWeight: 'normal',
                        fontSize: '11px',
                        color: '#898989'
                    }
                },
                labels: {
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#898989'
                    }
                }
            });

            if(marketView) {
                option.series.push({
                    name: "Average Market Challenge Duration",
                    color: '#07b5fa',
                    yAxis: axisNumber,
                    type: 'line',
                    dashStyle: 'ShortDash',
                    showInLegend : false,
                    data: marketContestDuration,
                    shadow: false
                });
            }
            axisNumber++;
        }

        if (fulfillView) {
            option.series.push({
                name:fulfillViewLegend,
                color:'#dc3812',
                yAxis:axisNumber,
                type:'line',
                data:customerFulfillment,
                shadow:false
            });
            option.yAxis.push({ //For Fulfillment
                min:0,
                title:{
                    text:'Fulfillment',
                    style:{
                        fontFamily:'Arial',
                        fontWeight:'normal',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                labels:{
                    style:{
                        fontFamily:'Arial',
                        fontSize:'11px',
                        color:'#898989'
                    }
                },
                opposite:true
            });
            if (marketView) {
                option.series.push({
                    name:"Average Market Fulfillment",
                    color:'#dc3812',
                    yAxis:axisNumber,
                    type:'line',
                    dashStyle:'ShortDash',
                    showInLegend:false,
                    data:marketFulfillment,
                    shadow:false
                });
            }
            axisNumber++;
        }

        chart = new Highcharts.Chart(option);
    }
}

function renderAnalysisByDisplayTypes() {
    var contestDurationView = false;
    var costView = false;
    var fulfillmentView = false;
    var volumeView = false;
    var marketView = false;
    var all = false;

    $('#lineView').empty().append('<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>');
    $.each($('.lineViewContainer .displayButton a.current'), function (i, element) {
        switch ($.trim($(this).text())) {
            case 'Contest Duration':
                contestDurationView = true;
                break;
            case 'Cost':
                costView = true;
                break;
            case 'Fulfillment':
                fulfillmentView = true;
                break;
            case 'Volume View':
                volumeView = true;
                break;
            case 'Market':
                marketView = true;
                break;
            default:
                all = true;
                break;
        }
    });
    if (all) {
        displayChartView(analysisData, false, true, false, true, false);
    } else {
        displayChartView(analysisData, contestDurationView, costView, fulfillmentView, volumeView, marketView)
    }
}

function renderVolumeSummaryTable(volumeSummaryData) {

    if(volumeTableLoaded) {
        $("div.volumeView").show();
        return;
    }

    var volumeSummaryTable = $("#volumeSummaryTable");

    // clean the data first
    volumeSummaryTable.find("colgroup col").remove();
    volumeSummaryTable.find("thead tr th").remove();
    volumeSummaryTable.find("tbody tr td").remove();

    var first = true;
    var totalAvgFailed = 0;
    var totalAvgCompleted = 0;
    var totalFailed = 0;
    var totalCompleted = 0;

    $.each(volumeSummaryData, function(key, value){ 
        volumeSummaryTable.find("colgroup").append($("<col>").attr("width", "100"));
        var header = $("<th/>").text(shortNameMap[key][1]);
        if(first) {
            header.addClass("firstColumn");
        }
        volumeSummaryTable.find("thead tr").append(header);
        var row0 =$("<td/>").text(value['AvgCompleted'].toFixed(1));
        totalAvgCompleted += value['AvgCompleted'];
        if(first) {
            row0.addClass("firstColumn");
        }
        volumeSummaryTable.find("tbody tr:eq(0)").append(row0);

        var row1 =$("<td/>").text(value['AvgFailed'].toFixed(1));
        totalAvgFailed += value['AvgFailed'];
        if(first) {
            row1.addClass("firstColumn");
        }
        volumeSummaryTable.find("tbody tr:eq(1)").append(row1);

        var row2 =$("<td/>").text(value['totalCompleted'].toFixed(1));
        totalCompleted += value['totalCompleted'];
        if(first) {
            row2.addClass("firstColumn");
        }
        volumeSummaryTable.find("tbody tr:eq(2)").append(row2);

        var row3 =$("<td/>").text(value['totalFailed'].toFixed(1));
        totalFailed += value['totalFailed'];
        if(first) {
            row3.addClass("firstColumn");
        }
        volumeSummaryTable.find("tbody tr:eq(3)").append(row3);
        first = false;
    });

    volumeSummaryTable.find("colgroup").append($("<col>").attr("width", "100"));
    var header = $("<th/>").text("Total");
    volumeSummaryTable.find("thead tr").append(header);
    var row0 =$("<td/>").text(totalAvgCompleted.toFixed(1));
    volumeSummaryTable.find("tbody tr:eq(0)").append(row0);
    var row1 =$("<td/>").text(totalAvgFailed.toFixed(1));
    volumeSummaryTable.find("tbody tr:eq(1)").append(row1);
    var row2 =$("<td/>").text(totalCompleted.toFixed(1));
    volumeSummaryTable.find("tbody tr:eq(2)").append(row2);
    var row3 =$("<td/>").text(totalFailed.toFixed(1));
    volumeSummaryTable.find("tbody tr:eq(3)").append(row3);

    $("div.volumeView").show();
    volumeTableLoaded = true;
}

function loadRoadmapCalendar() {

    // modalPreloader();
    $(".roadmapViewArea").css('min-height', '600px');
    $(".roadmapViewArea .loading").css("opacity", "0.6");
    $(".roadmapViewArea .loading").show();

    isEnterpriseCalendarShown = false;


    $.ajax({
        type:'POST',
        url: ctx + '/enterpriseDashboard/getRoadmapCalendar',
        data:{formData:getEnterpriseDashboardRequest(100000, 0, true)},
        dataType:"html",
        cache:false,
        success:function (result) {

            if (result.indexOf("calendarData") != -1) {

                $(result).find("#allResponsiblePerson").insertAfter(".milestoneCalView");
                calendarData = jQuery.parseJSON($(result).find("#calendarData").html());

                if (calendarData != null) {
                    $('.milestoneCalView').empty();

                    // build the handle map
                    var userHandleColorMap = {};

                    $("#allResponsiblePerson a").each(function () {
                        var handle = $.trim($(this).text());
                        var color = $(this).attr('class');
                        var url = $(this).attr('href');

                        userHandleColorMap[handle] = {color:color, url:url};
                    });

                    // update handle color and link first
                    $.each(calendarData.events, function (index, item) {
                        if (item.person && item.person.name) {
                            if (userHandleColorMap[item.person.name] != null) {
                                item.person.color = userHandleColorMap[item.person.name].color;
                                item.person.url = userHandleColorMap[item.person.name].url;
                            }
                        }
                    });

                    $('.milestoneCalView').fullCalendar({
                        header:{
                            left:'prev',
                            center:'title',
                            right:'next'
                        },
                        editable:false,
                        dayNamesShort:['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                        //events:calendarData.events,
                        eventRender:function (event, element) {
                            var inner = element.find(".fc-event-inner");

                            inner.click(function(e){
                                e.preventDefault();
                                window.open('projectMilestoneView?formData.viewType=list&formData.projectId=' + event.projectId, '');
                            });

                            var milestoneNameClass = "upcoming";

                            switch (event["status"]) {
                                case 'completed':
                                    element.addClass("fc-milestone-completed");
                                    if (event.person) {
                                        $("<a/>", {
                                            "text":event.person.name,
                                            "href":event.person.url,
                                            "class":event.person.color
                                        }).appendTo(inner);
                                    }
                                    milestoneNameClass = "completed";
                                    break;
                                case 'upcoming':
                                    element.addClass("fc-milestone-upcoming");
                                    if (event.person) {
                                        $("<a/>", {
                                            "text":event.person.name,
                                            "href":event.person.url,
                                            "class":event.person.color
                                        }).appendTo(inner);
                                    }
                                    milestoneNameClass = "upcoming";
                                    break;
                                case 'overdue':
                                    element.addClass("fc-milestone-overdue");
                                    if (event.person) {
                                        $("<a/>", {
                                            "text":event.person.name,
                                            "href":event.person.url,
                                            "class":event.person.color
                                        }).appendTo(inner);
                                    }
                                    milestoneNameClass = "overdue";
                                    break;
                            }

                            var name = $("<span class='milestoneName'></span>").addClass(milestoneNameClass).text(event.title);
                            var project = $("<span></span>").text(event.projectName);

                            inner.find(".fc-event-title").empty().append(name).append(project);

                            var tcTip = $("<div/>", {
                                "class":"milestoneTips"
                            });
                            $("<div/>", {
                                "class":"triangle"
                            }).appendTo(tcTip);
                            $("<h2/>", {
                                "text":event.title,
                                "class":"tipsTitle"
                            }).appendTo(tcTip);
                            $("<h2/>", {
                                "text":event.projectName,
                                "class":"tipsTitle"
                            }).appendTo(tcTip);
                            $("<p/>", {
                                "text":event.description
                            }).appendTo(tcTip);
                            tcTip.appendTo(inner.parent());

                            inner.find(".fc-event-title").hover(function () {
                                tcTip.css("top", $(this).height() + 5 + "px");
                                inner.parent().css("z-index", 9);
                                tcTip.show();
                            }, function () {
                                tcTip.hide();
                                inner.parent().css("z-index", 8);
                            });
                        },
                        eventAfterRender:function (event, element) {
                            if (event["status"] == "overdue") {
                                $('.milestoneCalView .fc-content tbody td:eq(' + element.data("tdIndex") + ")").addClass("fc-overdue");
                            }
                        },
                        viewDisplay:function () {
                            $(".milestoneCalView .fc-today .fc-day-number").html("TODAY");
                            // this will be replaced with the ajax call in Assembly
                            if (!isEnterpriseCalendarShown) {
                                isEnterpriseCalendarShown = true;

                                $.each(calendarData.events, function (index, item) {
                                    $('.milestoneCalView').fullCalendar("renderEvent", item, true);
                                })
                            }
                        }
                    });
                }

                    // modalAllClose();
                $(".roadmapViewArea .loading").hide();
            } else {
                showErrors("Fail to load the milestone data");
            }
        }
    });
}

function validateFilterPanel() {
    // validate whether start month and end month
    if ($(".timeLine .selectMonth").length == 0) {
        $("#zoomSelect .validationMessage").show();
        return false;
    } else {
        $("#zoomSelect .validationMessage").hide();
        return true;
    }
}

