/**
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for new Enterprise Dashboard
 *
 * Version 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 *
 * Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
 * - Add js codes for the pipeline page and pipeline widget in the overview page.
 *
 * @author GreatKevin
 * @version 1.1
 */
var chart;
var financialTable;
var loadCurrentMonth;
var loadPreviousMonth;
var currentMonthLoaded;
var prevMonthLoaded;
var currentMonthTotal;
var prevMonthTotal;

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

function getEnterpriseDashboardRequest(pageSize, pageNumber, requireDate) {
    var formData = {};
    formData.clientId = $("#clientFilter").val();
    formData.directProjectId =  $("#projectFilter").val();
    formData.projectStatusId = $("#projectStatusFilter").val();
    formData.projectFilterId = $("#metaFilter").val();
    formData.projectFilterValue = $("#metaValueFilter").val();
    var startMonth = $(".timeLine .selectMonth:first span span").text();
    var endMonth = $(".timeLine .selectMonth:last span span").text();

    if(requireDate == true) {
        formData.startMonth = startMonth;
        formData.endMonth = endMonth;
    }
    formData.pageSize = pageSize;
    formData.pageNumber = pageNumber;

    // render header part
    $("#headerClient").text($("#clientFilter option:selected").text());
    $("#headerProject").text($("#projectFilter option:selected").text());
    $("#headerProjectStatus").text($("#projectStatusFilter option:selected").text());
    $("#headerFilter").text($("#metaFilter option:selected").text());
    $("#headerFilterValue").text($("#metaValueFilter option:selected").text());
    $("#headerDate").text(startMonth == endMonth ? startMonth : startMonth + ' - ' + endMonth);

    var currentMonth = countMonth(Date.today());
    var prevMonth = countMonth(Date.today().addMonths(-1));

    var startMonth = countMonth(Date.parse(startMonth));
    var endMonth = countMonth(Date.parse(endMonth));

    loadCurrentMonth = currentMonth < startMonth || currentMonth > endMonth;
    loadPreviousMonth = prevMonth < startMonth || prevMonth > endMonth;

    return formData;
}

function renderCurrentPrevMonthSpend() {
    if(currentMonthLoaded && $("#spendThisMonth").length > 0) {
        $("#spendThisMonth").text("$" + formatNum(currentMonthTotal));
    }

    if(prevMonthLoaded && $("#spendLastMonth").length > 0) {
        $("#spendLastMonth").text("$" + formatNum(prevMonthTotal));
    }
}

function renderTotalSpendWidget(resultJson) {
    var arrayMonth = new Array();
    var arrayTotalSpend = new Array();
    var arrayTrend = new Array();
    var max = -1;
    var area = 'overviewTotalSpend';
    if(resultJson.length) {
        $.each(resultJson,function(idx,item){
            arrayMonth.push(item.label);
            arrayTotalSpend.push(item.spend/1);

            if(parseInt(item.spend) > max) {
                max = parseInt(item.spend);
            }

            arrayTrend.push(item.average/1);
        });
    }

    var interval = 20000;
    var maxY = 160000;

    if (max > 0) {
        interval = Math.ceil(max/160000) * 20000;
        maxY = interval * 8;
    }

    max = max + max / 2;
    max = Math.round(max/1000) * 1000;

    if(resultJson.length){
        $("#" + area).empty();
        chart = new Highcharts.Chart({
            chart: {
                renderTo: area,
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
                headerFormat: '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>{point.key}</strong>',
                pointFormat: '<div>{series.name}: {point.y}</div>',
                footerFormat: '</div></div>',
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
                align: 'right',
                verticalAlign: 'top',
                y: -10,
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '10px',
                    color: '#898989'
                },
                symbolPadding: 5,
                symbolWidth: 12
            },
            plotOptions: {
                series: {
                    pointWidth: 9
                }
            },
            series: [{
                name: 'Total Spend',
                color: '#4f81bd',
                type: 'column',
                yAxis: 1,
                data: arrayTotalSpend,
                shadow: false,
                borderColor: '#325f96',
                states: {
                    hover: {
                        marker: {
                            fillColor: '#325f96'
                        }
                    }
                }
            }, {
                name: 'Trend',
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
    var arrayTrend = new Array();
    var max = -1;
    var area = 'chartTotalSpend';

    if(resultJson.length) {
        $.each(resultJson,function(idx,item){
            arrayMonth.push(item.label);
            arrayTotalSpend.push(item.spend/1);

            if(parseInt(item.spend) > max) {
                max = parseInt(item.spend);
            }

            arrayTrend.push(item.average/1);
        });
    }

    var interval = 20000;
    var maxY = 160000;

    if (max > 0) {
        interval = Math.ceil(max/160000) * 20000;
        maxY = interval * 8;
    }

    max = max + max / 2;
    max = Math.round(max/1000) * 1000;

    if(resultJson.length){
        $("#" + area).empty();
        chart = new Highcharts.Chart({
            chart: {
                renderTo: area,
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
                headerFormat: '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner"><strong>{point.key}</strong>',
                pointFormat: '<div>{series.name}: {point.y}</div>',
                footerFormat: '</div></div>',
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
            series: [{
                name: 'Total Spend',
                color: '#4572A7',
                type: 'column',
                yAxis: 1,
                data: arrayTotalSpend,
                shadow: false,
                borderColor: '#325f96',
                states: {
                    hover: {
                        marker: {
                            fillColor: '#325f96'
                        }
                    }
                }
            }, {
                name: 'Trend',
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
                "sDom":'<"pagePanel topPagePanel"i<"showPage"l><"pageNum"p>>t<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>'
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
    if (resultJson.length) {
        for (var i = 0; i < resultJson.length; i++) {
            xAxisCate.push(resultJson[i].date);
            finishedData.push(parseInt(resultJson[i].completedContests) == 0 ? null : parseInt(resultJson[i].completedContests));
            activeData.push(parseInt(resultJson[i].activeContests) == 0 ? null : parseInt(resultJson[i].activeContests));
            draftData.push(parseInt(resultJson[i].draftContests) == 0 ? null : parseInt(resultJson[i].draftContests));
            failedData.push(parseInt(resultJson[i].failedContests) == 0 ? null : parseInt(resultJson[i].failedContests));
            scheduledData.push(parseInt(resultJson[i].scheduledContests) == 0 ? null : parseInt(resultJson[i].scheduledContests));
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
                gridLineWidth:0
            },
            legend:{
                align:'left',
                verticalAlign:'top',
                borderWidth:0,
                borderRadius:0,
                y:-10,
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
                    name:'Failed Contests',
                    data:failedData,
                    color:'#e83308',
                    borderColor:'#a80b00'
                },
                {
                    name:'Draft Contests',
                    data:draftData,
                    color:'#939393',
                    borderColor:'#777777'
                },
                {
                    name:'Active Contests',
                    data:activeData,
                    color:'#2fcafa',
                    borderColor:'#0096c7'
                },
                {
                    name:'Scheduled Contests',
                    data:scheduledData,
                    color:'#FF850B',
                    borderColor:'#FF850B'
                },
                {
                    name:'Finished Contests',
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

    $('.pipelineSection ul').empty().append('<li class="finished">Total Finished Contests<strong>' + finishedDataTotal + '</strong></li><li class="active">Total Active Contests<strong>' + activeDataTotal + '</strong></li><li class="scheduled">Total Scheduled Contests<strong>' + scheduledDataTotal + '</strong></li><li class="draft">Total Draft Contests<strong>' + draftDataTotal + '</strong></li><li class="last failed">Total Failed Contests<strong>' + failedDataTotal + '</strong></li>');
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
                    pointWidth:48
                }
            },
            series:[
                {
                    name:'Failed Contests',
                    data:failedData,
                    color:'#e83308',
                    borderColor:'#a80b00'
                },
                {
                    name:'Draft Contests',
                    data:draftData,
                    color:'#939393',
                    borderColor:'#777777'

                },
                {
                    name:'Active Contests',
                    data:activeData,
                    color:'#2fcafa',
                    borderColor:'#0096c7'
                },
                {
                    name:'Scheduled Contests',
                    data:scheduledData,
                    color:'#FF850B',
                    borderColor:'#FF850B'
                },
                {
                    name:'Finished Contests',
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


function loadTotalSpend(resultHandler) {
    if($("#overviewTotalSpend").length > 0) {
        $("#overviewTotalSpend").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
    } else if ($("#chartTotalSpend").length > 0) {
        $("#chartTotalSpend").empty().html('<div class="ajaxTableLoader"><img alt="loading" src="/images/rss_loading.gif"></div>');
    }


    $.ajax({
        type: 'POST',
        url:  "getTotalSpend",
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
        $(".numberSectionInner ul").empty().html('<li class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></li>');
    }
    if($("#overviewPipeline").length > 0) {
        $("#overviewPipeline").empty().html('<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading" /></div>');
    }

    $.ajax({
        type: 'POST',
        url:  "getContestsPipeline",
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
        url:  "getFinancialStatistics",
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
            url:  "getTotalSpend",
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
    loadFinancial(renderOverviewFinancial);
    loadCurrentPrevMonthSpend();
    loadPipeline(renderPipelineWidget);
}

function renderFinancialTab() {
    currentMonthLoaded = false;
    prevMonthLoaded = false;
    loadTotalSpend(renderTotalSpendChart);
    loadFinancial(renderFinancial);
    loadCurrentPrevMonthSpend();
}


$(document).ready(function () {

    $("#clientFilter").attr("autocomplete","off");

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

    $("#clientFilter").change(function(){
        $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForClientAJAX",
            data: {'formData.customerIds':$("#clientFilter").val()},
            cache: false,
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
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });

        $.ajax({
            type:'POST',
            url:ctx + "/getGroupByOptionsForCustomer",
            data:{customerId:$("#clientFilter").val()},
            cache:false,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        var selector = $("#metaFilter");
                        selector.empty();

                        selector.append($("<option></option>").attr('value', 0).text("None"));

                        $.each(result, function (index, value) {
                            selector.append($("<option></option>").attr('value', value.id).text(value.name));
                        });

                        selector.val(0);
                        selector.trigger('change');
                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
            }
        });

    });

    $("#metaFilter").change(function () {
        $.ajax({
            type:'GET',
            url:ctx + "/getGroupValuesForGroupBy",
            data:{groupKeyId:$("#metaFilter").val()},
            cache:false,
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
        return RegExp.$1;
    }

    $('.blankPage').attr('target', '_blank');
    sortDropDown("#projectFilter");

    function initFilterDate() {
        $('.selectDate .monthList li').css('width', parseInt(($('.selectDate').width() - 84) / 12));
        $('.selectDate .monthList ul,.selectDate .monthList .timeLine').css('width', $('.selectDate .monthList li').width() * 12);
        moveMonth();
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
        $('.analyticsContainer .datePickerWrapper').css('width', ($('#mainSection').width() - $('.secondColumn').width() - $('.thirdColumn').width() - 144) / 2);
        $('.analyticsContainer .date-pick').css('width', ($('#mainSection').width() - $('.secondColumn').width() - $('.thirdColumn').width() - 220) / 2);
        $('.analyticsContainer .groupBy').css('width', ($('.analyticsContainer .firstColumn').width() - 190) / 2);
        $('.volumeView .tableData .ajaxTableLoader,.volumeView .tableData .noData').css({'margin-left':$('#mainSection').width() / 2 + 15, 'margin-top':'90px'});
        $('#calendarView .filter .filterContainer .filterValuesColumn').css('width', $('#mainSection').width() - $('#calendarView .filter .filterContainer .customerColumn').width() - $('#calendarView .filter .filterContainer .projectFiltersColumn').width() - $('#calendarView .filter .filterContainer .button').width() - 10);

    });

    // overview tab
    if ($(".overviewIcon").parents("li").hasClass("active")) {
        renderOverviewTab();

        $("#filterButton").click(function(){
            renderOverviewTab();
        });

    }

    if ($(".financialsIcon").parents("li").hasClass("active")) {
        renderFinancialTab();

        $("#filterButton").click(function(){
            renderFinancialTab();
        });
    }

    if ($(".pipelineIcon").parents("li").hasClass("active")) {
        loadPipeline(renderPipelinePage);

        $("#filterButton").click(function(){
            loadPipeline(renderPipelinePage);
        });
    }

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

    $('.filterForAnalytics .folder').live('click', function () {
        if ($(this).hasClass('unfolder')) {
            $(this).removeClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', '#bdbdbd solid 1px');
            $('.filterForAnalytics .filterContainer').show();
        } else {
            $(this).addClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', 'none');
            $('.filterForAnalytics .filterContainer').hide();
        }
    });

});
