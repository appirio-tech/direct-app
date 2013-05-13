/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * This javascript handles the instant search and search all.
 *
 * Version 1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 * - Used to render payment overview page.
 *
 * @Author TCSASSEMBLER
 * @Version 1.0
 */
/*
 * The average total payment.
 */
var averageTotalPayment = '';

$(document).ready(function () {
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
    
    //table sort
    if ($("#topMembeBalancesTable").length > 0){
        $(".sortTop10").change(function (){
            if ($(this)[0].selectedIndex == "0"){
                $("#topMembeBalancesTable").tablesorter( {sortList: [[0,0]], widgets: ['zebra']} ); 
            }else {
                $("#topMembeBalancesTable").tablesorter( {sortList: [[0,1]], widgets: ['zebra']} ); 
            }
        });
    }

    //1 Payments
    if ( $("#memberPaymentsTable").length > 0){
        loadPaymentsTable();
    }
    
    //2 Potential Cash Outflow
    if ( $("#potentialCashOutflowTable").length > 0){
        loadPotentialCashOutflowTable();
    }
    
    //3 Potential Cash Outflow
    if ( $("#paymentsByMethodTable").length > 0){
        loadPaymentsByMethodTable();
    }

    //4 Average total  of payments
    if ( $("#averageTotalPayment").length > 0){
        $('.memberPaymentAverageTotalBlock .chartZoomLink').click(function() {
            var id = $(this).attr("id");
                
            $('.memberPaymentAverageTotalBlock .chartZoomLink.selected').removeClass('selected');
            $(this).addClass('selected');
            
            $('.memberPaymentAverageTotalBlock select').addClass('hide');
            $('#' + id + 'Select').removeClass('hide');
            
            loadPaymentAverageChart();
        });
        
        $('.memberPaymentAverageTotalBlock select').bind('change', function() {
            loadPaymentAverageChart();
        });
        
        loadPaymentAverageChart();
    }
    
    //legend click
    $('.memberPaymentAverageTotalBlock .chartLegend').live('click', function () {
        var thisLabelTxt = $.trim($(this).text());
        var thisLabel = $(this);
        thisLabel.toggleClass("isChecked");
        for (var i = 0; i < averageTotalPayment.series.length; i++) {
            var sName = averageTotalPayment.series[i].name;
            var sStack = averageTotalPayment.series[i].options.stack;
            if ((sName == thisLabelTxt) || (sStack == thisLabelTxt)){
                if (thisLabel.hasClass('isChecked')){
                    averageTotalPayment.series[i].hide();
                }else{
                    averageTotalPayment.series[i].show();
                }
            }
        }
        return false;
    });
    
    //5 Status of all payments
    if ( $("#statusOfPaymentsTable").length > 0){
        loadStatusOfPaymentsTable();
    }
    
    //6 Top 10 Member Balances
    if ( $(".memberPaymentTopTenBlock").length > 0){
        loadTopTenMemberBalance();
    }
    
    if ($('.paymentPrintVersion .timeStamp').length > 0) {
        $('.paymentPrintVersion .timeStamp').text(Date.now().toString('dd MMMM yyyy'));
    }
})

/**
 * The timeout value.
 */
var TIMEOUT = 100000;

/**
 * The base url.
 */
var baseURL = ctx + "/payments/";

/* 
 * Create an array with the values of payment method name in a column. 
 */
$.fn.dataTableExt.afnSortData['payment-method'] = function  ( oSettings, iColumn )
{
    var aData = [];
    $( 'td:eq('+iColumn+')', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
        var v;
        
        if ($(this).text().trim() == 'Paypal') {
            v = 1;
        } else if ($(this).text().trim() == 'Payoneer') {
            v = 2;
        } else if ($(this).text().trim() == 'Western Union') {
            v = 3;
        } else {
            v = 4;
        }
        
        aData.push(v);
    } );
    return aData;
}

/* 
 * Create an array with the values of all payment number in a column.
 */
$.fn.dataTableExt.afnSortData['payment-number'] = function  ( oSettings, iColumn )
{
    var aData = [];
    $( 'td:eq('+iColumn+')', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
        aData.push($(this).text().replace(' ', ''));
    } );
    return aData;
}

/**
 * Load payment average chart.
 */
function loadPaymentAverageChart() {
    var id = $('.memberPaymentBlockContainer .chartZoomLink.selected').attr('id');
    var type = $('#' + id + 'Input').val();
    var range = $('#' + id + 'Select').val();
    
    loadMemberPaymentAverageTotalChart(type, range);
}

/**
 * Get formatted number.
 *
 * @param number the number to format.
 * @return formatted number
 */
function getFormattedNumber(number) {
    var v = Math.round(number);
    var ret = '';
    
    while (v > 1000) {
        var tmp = '' + v % 1000;
        while (tmp.length < 3) {
            tmp = '0' + tmp;
        }
        
        ret = tmp + ' ' + ret;
        v /= 1000;
        v = ~~v;
    }
    if (v > 0) {
        ret = v + ' ' + ret;
    }
    ret = $.trim(ret);
    
    return "$" + (ret == '' ? '0' : ret).replace(' ', ',');
}

/**
 * Load payments pullable table.
 */
function loadPaymentsTable(){
    $.ajax({
        type: 'GET',
        url:  baseURL + "pullablePayments",
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (!result.hasOwnProperty("paymentBalance") || result['paymentBalance'].length == 0) {
                        $("#memberPaymentsTable tbody tr.loadRow").addClass('hide');
                        $("#memberPaymentsTable tbody tr.noDataRow").removeClass('hide');
                    } else {
                        // fill data
                        var paypalRow = $("#memberPaymentsTable tbody tr.paypalRow");
                        paypalRow.find('td:eq(1)').text(result['paymentBalance']['paypalBalance']);
                        paypalRow.find('td:eq(2)').text(result['pullablePayments']['paypalPayments']);
                        paypalRow.find('td:eq(3)').text(result['paymentDiff']['paypalDiffAmount']);
                        
                        var payoneerRow = $("#memberPaymentsTable tbody tr.payoneerRow");
                        payoneerRow.find('td:eq(1)').text(result['paymentBalance']['payoneerBalance']);
                        payoneerRow.find('td:eq(2)').text(result['pullablePayments']['payoneerPayments']);
                        payoneerRow.find('td:eq(3)').text(result['paymentDiff']['payoneerDiffAmount']);
                        
                        var westRow = $("#memberPaymentsTable tbody tr.westRow");
                        westRow.find('td:eq(1)').text(result['paymentBalance']['westernUnionBalance']);
                        westRow.find('td:eq(2)').text(result['pullablePayments']['westernUnionPayments']);
                        westRow.find('td:eq(3)').text(result['paymentDiff']['westernUnionDiffAmount']);
                        
                        var notSetRow = $("#memberPaymentsTable tbody tr.notSetRow");
                        notSetRow.find('td:eq(1)').text(result['paymentBalance']['notSetBalance']);
                        notSetRow.find('td:eq(2)').text(result['pullablePayments']['notSetPayments']);
                        notSetRow.find('td:eq(3)').text(result['paymentDiff']['notSetDiffAmount']);                        
                        
                        var totalRow = $("#memberPaymentsTable tfoot tr.totalRow");
                        
                        for (var i = 1; i < 5; i++) {
                            var total = 0;
                            
                            $("#memberPaymentsTable tbody tr.contentRow").each(function(index, row) {
                                var td = $(row).find("td:eq(" + i + ")");
                                var v = parseFloat(td.text());
                                total += v;
                                
                                if (i == 3) {
                                    if (v < 0) {
                                        v = -v;
                                        td.addClass('redTxt');
                                    } else {
                                        td.addClass('greenTxt');
                                    }
                                }
                                
                                td.text(getFormattedNumber(v));
                            });
                            
                            var tdClass;
                            if (i == 3) {
                                if (total < 0) {
                                    total = -total;
                                    tdClass = 'redTxt';
                                } else {
                                    tdClass = 'greenTxt';
                                }
                            }
                            
                            totalRow.find("td:eq(" + i + ")").text(getFormattedNumber(total)).addClass(tdClass);
                        }
                        
                        $("#memberPaymentsTable tbody tr.loadRow").remove();
                        $("#memberPaymentsTable tbody tr.noDataRow").remove();
                        $("#memberPaymentsTable tbody tr.contentRow").removeClass('hide');
                        $("#memberPaymentsTable tfoot tr.totalRow").removeClass('hide');
                        
                        $('#memberPaymentsTable').dataTable({    
                            "iDisplayLength": -1,    
                            "bFilter": false,    
                            "bSort": true,    
                            "sDom": '',    
                            "aoColumns": [        
                                { "sSortDataType": "payment-method" },     
                                { "sSortDataType": "payment-number", "sType": "numeric" },     
                                { "sSortDataType": "payment-number", "sType": "numeric" },     
                                { "sSortDataType": "payment-number", "sType": "numeric" }
                            ]
                        });
                    }
                    
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

/**
 * Load potential cash out flow table.
 */
function loadPotentialCashOutflowTable(){
    $.ajax({
        type: 'GET',
        url:  baseURL + "potentialCashOutFlow",
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (!result.hasOwnProperty("payments") || result['payments'].length == 0) {
                        $("#potentialCashOutflowTable tbody tr.loadRow").addClass('hide');
                        $("#potentialCashOutflowTable tbody tr.noDataRow").removeClass('hide');
                    } else {
                        // fill data
                        $.each(result['payments'], function(index, item) {
                            var row = $("<tr>");
                            row.addClass("contentRow hide");
                            
                            var td = $("<td class='alignLeft'>");
                            td.text(item['paymentMethodName']);
                            row.append(td);
                            
                            $.each(item['cashAmounts'], function(ii, amount) {
                                var td = $("<td class='alignRight'>").text(amount);
                                row.append(td);
                            });
                            
                            $("#potentialCashOutflowTable tbody").append(row);
                        });
                        
                        var length = result['days'].length;
                        var row = $("<tr class='totalRow hide'>");
                        
                        var td = $("<td class='alignLeft'>");
                        td.text('Total');
                        row.append(td);
                        
                        for (var i = 1; i <= length; i++) {
                            var td = $("<td class='alignRight'>");
                            var total = 0;
                            
                            $("#potentialCashOutflowTable tbody tr.contentRow").each(function(index, item) {
                                var ctd = $(item).find('td:eq(' + i + ')');
                                var v = parseFloat(ctd.text());
                                total += v;
                                
                                ctd.text(getFormattedNumber(v));
                            });
                            
                            td.text(getFormattedNumber(total));
                            row.append(td);
                        }
                        $("#potentialCashOutflowTable tfoot").append(row);
                        $("#potentialCashOutflowTable tbody tr:odd").addClass("odd");
                        
                        $("#potentialCashOutflowTable tbody tr.loadRow").remove();
                        $("#potentialCashOutflowTable tbody tr.noDataRow").remove();
                        $("#potentialCashOutflowTable tbody tr.contentRow").removeClass('hide');
                        $("#potentialCashOutflowTable tfoot tr.totalRow").removeClass('hide');
                        
                        var aoColumns = [{ "sSortDataType": "payment-method" }];
                        for (var i = 0; i< length; i++) {
                            aoColumns.push({ "sSortDataType": "payment-number", "sType": "numeric" });
                        }
                        
                        $('#potentialCashOutflowTable').dataTable({    
                            "iDisplayLength": -1,    
                            "bFilter": false,    
                            "bSort": true,    
                            "sDom": '',    
                            "aoColumns": aoColumns
                        });                        
                    }
                    
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });    
}

/**
 * Load the payments by method table.
 */
function loadPaymentsByMethodTable(){
    $.ajax({
        type: 'GET',
        url:  baseURL + "paymentsHistory",
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (!result.hasOwnProperty("columns") || result['columns'].length == 0) {
                        $("#paymentsByMethodTable tbody tr.loadRow").addClass('hide');
                        $("#paymentsByMethodTable tbody tr.noDataRow").removeClass('hide');
                    } else {
                        var length = result['columns'].length;
                        var thData = "<thead><tr>";
                        thData += '<th class="alignLeft">Payment method</th>';
                            
                        // fill datas
                        $.each(result['columns'], function(index, item) {
                            thData += '<th>' + item + '</th>';
                        });
                        thData += '</tr></thead>';
                        $("#paymentsByMethodTable tbody").before(thData);
                        
                        var columnNames = ['Paypal', 'Payoneer', 'Western Union', 'Not Set'];
                        $.each(columnNames, function(index, item) {
                            var row = $("<tr class='contentRow hide'>");
                            row.append($("<td class='alignLeft'>").text(item));
                            
                            for (var i = 0; i < length; i++) {
                                row.append($("<td class='alignRight'>").text('0'));
                            }
                            $("#paymentsByMethodTable tbody").append(row);
                        });
                        
                        var paymentNames = [ 'PAYPAL', 'PAYONEER', 'WESTERN_UNION', 'NOT_SET'];
                        $.each(paymentNames, function(index, item) {
                            var vRow = $("#paymentsByMethodTable tbody tr.contentRow:eq(" + index + ")");
                            $.each(result[item], function(ii, v) {
                                vRow.find("td.alignRight:eq(" + ii + ")").text(v);
                            });
                        });
                        
                        var row = $("<tr class='totalRow hide'>");
                        
                        var td = $("<td class='alignLeft'>");
                        td.text('Total');
                        row.append(td);
                        
                        for (var i = 1; i <= length; i++) {
                            var td = $("<td class='alignRight'>");
                            var total = 0;
                            
                            $("#paymentsByMethodTable tbody tr.contentRow").each(function(index, item) {
                                var ctd = $(item).find('td:eq(' + i + ')');
                                if ($.trim(ctd.text()).length > 0) {                                    
                                    var v = parseFloat(ctd.text());
                                    total += v;
                                    
                                    ctd.text(getFormattedNumber(v));
                                }
                            });
                            
                            td.text(getFormattedNumber(total));
                            row.append(td);
                        }
                        $("#paymentsByMethodTable tfoot").append(row);
                        
                        $("#paymentsByMethodTable tbody tr:odd").addClass("odd");
                        
                        $("#paymentsByMethodTable tbody tr.loadRow").remove();
                        $("#paymentsByMethodTable tbody tr.noDataRow").remove();
                        $("#paymentsByMethodTable tbody tr.contentRow").removeClass('hide');
                        $("#paymentsByMethodTable tfoot tr.totalRow").removeClass('hide');
                        
                        var aoColumns = [{ "sSortDataType": "payment-method" }];
                        for (var i = 0; i< length; i++) {
                            aoColumns.push({ "sSortDataType": "payment-number", "sType": "numeric" });
                        }
                        
                        $('#paymentsByMethodTable').dataTable({    
                            "iDisplayLength": -1,    
                            "bFilter": false,    
                            "bSort": true,    
                            "sDom": '',    
                            "aoColumns": aoColumns
                        });                          
                    }
                    
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });    
}

/**
 * Load the member payment average total chart.
 */
function loadMemberPaymentAverageTotalChart(zoomType, range){
    $(".memberPaymentAverageTotalBlock .chartLegendWrapper").css("visibility", "hidden");
    $(".memberPaymentAverageTotalBlock .zoomControl").css("visibility", "hidden");
    $("#averageTotalPayment").html('<div class="ajaxTableLoader"><img src="/images/rss_loading.gif" alt="loading"/></div>');
    
    $.ajax({
        type: 'GET',
        url:  baseURL + "memberPaymentStats",
        data: {
            dateType: zoomType,
            dateRange: range
        },
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (!result.hasOwnProperty("PAYPAL") || result['PAYPAL'].length == 0) {
                        $("#averageTotalPayment").empty().append('<div class="noData">No data available</div>');
                    } else {
                        renderChart(result);
                    }
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });    
}

/**
 * Render the member payment overage total chart.
 */
function renderChart(data) {
    var paypalData = data['PAYPAL'];
    var payoneerData = data['PAYONEER'];
    var wuData = data['WESTERN_UNION'];
        
    var catList = []
    var catTotalPaid = [];
    var paypalPaid = [];
    var paypalGap = [];
    var paypalCreated = [];
    var payoneerPaid = [];
    var payoneerGap = [];
    var payoneerCreated = [];
    var wuPaid = [];
    var wuGap = [];
    var wuCreated = [];
    var decoTop = [];
    var maxV = -1;
    
    for (var i = 0; i < paypalData.length; i++) {
        catList[i] = paypalData[i].timeStamp;
        catTotalPaid[i] = parseInt(paypalData[i].paid) + parseInt(payoneerData[i].paid) + parseInt(wuData[i].paid);
        paypalPaid[i] = parseInt(paypalData[i].paid);
        paypalCreated[i] =  parseInt(paypalData[i].created);
        paypalGap[i] = Math.max(0, (parseInt(paypalData[i].created) - parseInt(paypalData[i].paid)));
        payoneerPaid[i] = parseInt(payoneerData[i].paid);
        payoneerCreated[i] = parseInt(payoneerData[i].created);
        payoneerGap[i] = Math.max(0, (parseInt(payoneerData[i].created) - parseInt(payoneerData[i].paid)));
        wuPaid[i] = parseInt(wuData[i].paid);
        wuCreated[i] = parseInt(wuData[i].created);
        wuGap[i] = Math.max(0, (parseInt(wuData[i].created) - parseInt(wuData[i].paid)));
        
        maxV = Math.max(maxV, paypalData[i].created);
        maxV = Math.max(maxV, paypalData[i].paid);
        maxV = Math.max(maxV, payoneerData[i].created);
        maxV = Math.max(maxV, payoneerData[i].paid);
        maxV = Math.max(maxV, wuData[i].created);
        maxV = Math.max(maxV, wuData[i].paid);
    }
    hInterval = ~~ (maxV / 4);
    var barTop;
    if (hInterval > 10000) {
        hInterval -= hInterval % 10000;
        barTop = hInterval / 5;
    } else if (hInterval > 1000) {
        hInterval -= hInterval % 1000;
        barTop = hInterval / 5;
    } else if (hInterval > 100) {
        hInterval -= hInterval % 100;
        barTop = hInterval / 10;
    } else if (hInterval > 10) {
        hInterval -= hInterval % 10;
        barTop = hInterval / 15;
    }
    for (var i = 0; i < paypalData.length; i++) {
        decoTop[i] = barTop;
    }
    
    var withBar = paypalData.length <= 12;
    
    if (withBar) {
        $('.memberPaymentAverageTotalBlock .chartLegendWrapper .payMethodLegend .chartLegend').removeClass('hide');
        $('.memberPaymentAverageTotalBlock .chartLegendWrapper .paidByLabel').removeClass('hide');
    } else {
        $('.memberPaymentAverageTotalBlock .chartLegendWrapper .payMethodLegend .chartLegend').addClass('hide');
        $('.memberPaymentAverageTotalBlock .chartLegendWrapper .paidByLabel').addClass('hide');
    }
    
    var xAxisOption = {
        labels:{
            style:{
                fontFamily:'Arial',
                fontSize:'11px',
                fontWeight: 'normal',
                color:'#333333'
            }
        },
        lineWidth: 2,
        lineColor: '#808080',
        tickColor: '#ffffff'
    };
    if (withBar) {
        xAxisOption['categories'] = catList;
    }
    
    var seriesOption = [];
    
    if (withBar) {
        seriesOption = seriesOption.concat([
            {
                type: 'column',
                name: 'TopBorder',
                data: decoTop,
                stack: 'Paypal',
                color:'#00aeff'
            }
            
            , {
                type: 'column',
                name: 'CreatedSub',
                data: paypalCreated,
                stack: 'Paypal',
                color:'#ffffff'
            }, {
                type: 'column',
                name: 'PaidSub',
                data: paypalPaid,
                stack: 'Paypal',
                color:'#13487e'
            }
            
            , {
                type: 'column',
                name: 'TopBorder',
                data: decoTop,
                stack: 'Payoneer',
                color:'#00aeff'
            }, 
            
            {
                type: 'column',
                name: 'CreatedSub',
                data: payoneerCreated,
                stack: 'Payoneer',
                color:'#ffffff'
            }, {
                type: 'column',
                name: 'PaidSub',
                data: payoneerPaid,
                stack: 'Payoneer',
                color:'#d67002'
            }
            
            , {
                type: 'column',
                name: 'TopBorder',
                data: decoTop,
                stack: 'Western Union',
                color:'#00aeff'
            }, 
            
            {
                type: 'column',
                name: 'CreatedSub',
                data: wuCreated,
                stack: 'Western Union',
                color:'#ffffff'
            }, {
                type: 'column',

                name: 'PaidSub',
                data: wuPaid,
                stack: 'Western Union',
                color:'#edd400'
            }        
        ]);
    }
    seriesOption = seriesOption.concat([
         {
            type: 'line',
            name: 'Paid',
            data: catTotalPaid,
            marker: {
                lineWidth: 2,
                lineColor: '#85d524',
                fillColor: '#85d524',
                symbol: 'circle'
            },
            color:'#85d524'
        }
    ]);
    
    //build chart
    averageTotalPayment = new Highcharts.Chart({
        chart: { 
            renderTo: 'averageTotalPayment',
            marginTop: 4,
            marginBottom: 20
        },
        title: {
            text: ' '
        },
        xAxis: xAxisOption,
        yAxis: {
            labels:{
                style:{
                    fontFamily:'Arial',
                    fontSize:'11px',
                    color:'#333333'
                }
            },
            title:{
                text:''
            },
            min:0,
            tickInterval:hInterval,
            lineWidth: 0,
            gridLineWidth: 2,
            gridLineColor: '#e8e8e8'            
        },
        credits : {
            enabled : false
        },
        legend:{
            enabled: false
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
                if (this.series.type == "line"){
                    return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner">' +
                            '<strong>' + this.point.category + '</strong>' + 
                            '<div>' + this.series.name + ':' + this.point.y + '</div>' +
                        '</div></div>';
                }else{
                    if (this.series.name == "CreatedSub"){
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner">' +
                                    '<strong>' + this.series.options.stack + '<br/>' + this.point.category + '</strong>' + 
                                    '<div>Paid:' + parseInt(( this.point.stackTotal - barTop - this.y)) + '</div>' +
                                    '<div>Created:' +parseInt(this.y) + '</div>' +
                                '</div></div>';
                    }else if (this.series.name == "PaidSub"){
                        return '<div class="tooltip" style="border:#bdbdbd solid 1px;"><div class="tooltipInner">' +
                                    '<strong>' + this.series.options.stack + '<br/>' + this.point.category + '</strong>' + 
                                    '<div>Paid:' + parseInt(this.y) + '</div>' +
                                    '<div>Created:' + parseInt((this.point.stackTotal - barTop - this.y)) + '</div>' +
                                '</div></div>';
                    }else{
                        return '';    
                    }
                }
            }
        },
        plotOptions:{
            
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
                        return this.y == 0 ? null : this.y;
                    }
                },
                shadow:false,
                borderWidth: 0,
                groupPadding: 0.2,
                pointPadding: 0.07
            }, 
            
            line:{
                shadow:false
            },
            
            series: {
                marker: {
                    enabled: withBar,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }                    
                }
            }           
        },
        series: seriesOption
    });
    $('.memberPaymentAverageTotalBlock .chartLegend').removeClass("isChecked");
    $(".memberPaymentAverageTotalBlock .zoomControl").css("visibility", "visible");
    $(".memberPaymentAverageTotalBlock .chartLegendWrapper").css("visibility", "visible");
    //End build chart and show chart control/legend
}

/**
 * Load the status of payments table.
 */
function loadStatusOfPaymentsTable(){
    $.ajax({
        type: 'GET',
        url:  baseURL + "paymentStatus",
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (result.length == 0) {
                        $("#statusOfPaymentsTable tbody tr.loadRow").addClass('hide');
                        $("#statusOfPaymentsTable tbody tr.noDataRow").removeClass('hide');
                    } else {
                        $.each(result, function(index, item) {
                            if (!item['startDateStr']) {
                                var row = '<tr class="labelRow"><td class="alignLeft" colspan="5">Creation Date: <strong>' + 
                                    item['endDateStr'] + '+ days ago</strong></td></tr>';
                            } else {
                                var row = '<tr class="labelRow"><td class="alignLeft" colspan="5">Creation Date: <strong>' + 
                                    item['endDateStr'] + ' - ' + item['startDateStr'] + ' days ago</strong></td></tr>';
                            }
                            $("#statusOfPaymentsTable tbody").append(row);
                            
                            row = $("<tr class='contentRow hide'>");
                            row.append($("<td class='alignRight'>").text(item['owedOrAccruingPayments']));
                            row.append($("<td class='alignRight'>").text(item['onHoldPayments']));
                            row.append($("<td class='alignRight'>").text(item['paidPayments']));
                            row.append($("<td class='alignRight'>").text(item['paymentsEnteredIntoPaymentSystem']));
                            row.append($("<td class='alignRight'>").text(item['totalPayment']));
                            $("#statusOfPaymentsTable tbody").append(row);
                        });
                        
                        $("#statusOfPaymentsTable tbody").append('<tr class="labelRow"><td class="alignLeft" colspan="5">TOTAL</td></tr>');
                        var row = $("<tr class='totalRow hide'>");
                        for (var i = 0; i < 5; i++) {
                            var td = $("<td class='alignRight'>");
                            var total = 0;
                            
                            $("#statusOfPaymentsTable tbody tr.contentRow").each(function(index, item) {
                                var ctd = $(item).find('td:eq(' + i + ')');
                                if ($.trim(ctd.text()).length > 0) {                                    
                                    var v = parseFloat(ctd.text());
                                    total += v;
                                    
                                    ctd.text(getFormattedNumber(v));
                                }
                            });
                            
                            td.text(getFormattedNumber(total));
                            row.append(td);
                        }
                        $("#statusOfPaymentsTable tbody").append(row);
                        
                        $("#statusOfPaymentsTable tbody tr.loadRow").addClass('hide');
                        $("#statusOfPaymentsTable tbody tr.contentRow").removeClass('hide');
                        $("#statusOfPaymentsTable tbody tr.totalRow").removeClass('hide');
                    }
                    
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });    
}

/**
 * Load the top ten member table.
 */
function loadTopTenMemberBalance() {
    $.ajax({
        type: 'GET',
        url:  baseURL + "topMemberPayments",
        cache: false,
        timeout: TIMEOUT,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    if (result.length == 0) {
                        $("#topMembeBalancesTable tbody tr.loadRow").addClass('hide');
                        $("#topMembeBalancesTable tbody tr.noDataRow").removeClass('hide');
                    } else {
                        $("#topMembeBalancesTable tbody").empty();
                        
                        var total = 0;
                        $.each(result, function(i, item) {
                            var row = $("<tr class='contentRow hide'>");
                            row.append($('<td><span class="rowIdx">' + (i+1) + '</span></td>'));
                            row.append($("<td class='alignLeft'>").html(item['memberLink']));
                            var tmp = getFormattedNumber(item['amount']);
                            row.append($("<td class='alignRight amountCol'>").text(tmp));
                            
                            row.append($("<td>").text(item['paymentMethod']));
                            $("#topMembeBalancesTable tbody").append(row);
                            
                            total += item['amount'];
                        });
                        
                        var row = $("<tr class='totalRow hide'>");
                        row.append($('<td>'));
                        row.append($('<td class="alignLeft">').text('Total'));
                        
                        total = getFormattedNumber(total);
                        row.append($('<td class="alignRight">').text(total));
                        row.append($('<td>'));
                        
                        $("#topMembeBalancesTable").append($('<tfoot>').append(row));
                        
                        $("#topMembeBalancesTable tbody tr:odd").addClass("odd");
                        $("#topMembeBalancesTable tbody tr.loadRow").addClass('hide');
                        $("#topMembeBalancesTable tbody tr.contentRow").removeClass('hide');
                        $("#topMembeBalancesTable tr.totalRow").removeClass('hide');
                    }
                    
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });     
}

/**
 * Get date string with time.
 *
 * @param time the time
 * @return the date string
 */
function getDateString(time) {
    return new Date(time).toString('MM.dd.yyyy');
}


