/**
 * Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for Platform Specialist Report
 *
 * Version 1.0 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
 *
 * Version 1.1 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
 * - Handle the case when there is no data returned
 *
 * @author TCSASSEMBLER
 * @version 1.1
 */

$(document).ready(function(){

    $("#platformSpecialist option").attr("selected", "selected");

    // setup the filter panel open event
    $('.triggerModal').live('click', function () {
        modalLoad('#' + $(this).attr('name'));
        initFilterDate();
        if ($("a#platformSpecialist").length == 0) {
            $("#platformSpecialist").multiSelect({
                selectAllText: "Select All Expert Services",
                oneOrMoreSelected: "% expert services selected"
            });
        }
        return false;
    });

    // setup the timeline controls
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

    // initialize the filter dates
    function initFilterDate() {
        $('.selectDate .monthList li').css('width', parseInt(($('.selectDate').width() - 84) / 12));
        $('.selectDate .monthList ul,.selectDate .monthList .timeLine').css('width', $('.selectDate .monthList li').width() * 12);
        moveMonth();
    }

    function getCurrent() {
        for (var i = 0; i < $('.selectDate li').length; i++) {
            if (currentFullDate.match($('.selectDate li a').eq(i).text())) {
                currentMonth = i;
            }
        }
    }

    // previous button of timeline
    $('.selectDate .prevYear:not(.disable)').live('click', function () {
        step = step - 1;
        moveMonth();
    });
    // next  button of timeline
    $('.selectDate .nextYear:not(.disable)').live('click', function () {
        step = step + 1;
        moveMonth();
    });

    // event of clicking a month
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

    //select the zoom button
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

    // current month select
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

    // 3 months select
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

    //  6 months select
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

    // 1 year select
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

    // setup the tooltip hover
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

    $('#filterModal').css('width', $('#mainSection').width());

    initFilterDate();

    $('#zoomSelect .sixMonths a').trigger('click');

    renderPlatformSpecialistsReport();

    // send request and show result when filter button is clicked
    $("#filterButton").click(function(){
        renderPlatformSpecialistsReport();
    });

    // resize
    $(window).resize(function () {
        modalPosition();
        $('#filterModal').css('width', $('#mainSection').width());
        initFilterDate();
    });
});

// send the request and render the data
function renderPlatformSpecialistsReport() {

    var requestData = getRequest();

    if($("div.tableData").data("dataTable")) {
        $("div.tableData").data("dataTable").fnDestroy();
        $("#platformSpecialistsReportTable").remove();
        $("div.tableData").removeData("dataTable");
    }

    if(requestData == null) {
        $(".ajaxTableLoader").hide();
        $("#loadingTable").hide();
        return;
    }

    $("#loadingTable").show();

    $.ajax({
        type: 'POST',
        url:  "getPlatformSpecialistsReportData",
        data: requestData,
        cache: false,
        timeout:720000,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult2(jsonResult,
                function(result) {
                    var thead = $("<thead><tr></tr></thead>");
                    var tbody = $("<tbody></tbody>");
                    var table = $("<table></table>").append(thead).append(tbody).attr('id', 'platformSpecialistsReportTable');

                    // build thead
                    var theadRow = thead.find("tr");
                    theadRow.append($("<th></th>").text("Platform Specialist"));
                    var categories = [];
                    var seriesData = [];
                    $("a.selectMonth").each(function(){
                        var monthText = $(this).text();
                        theadRow.append($("<th></th>").text(monthText));
                        categories.push(monthText);
                    })

                    var tableData = [];
                    var hasData = false;

                    $.each(result, function(key, value){
                        var row = [];
                        row.push(key);
                        $.each(value, function(i, v) {
                            row.push(formatMoney(v.toFixed(1)));
                        })
                        tableData.push(row);
                        hasData= true;
                    })

                    if (!hasData && ($("#platformSpecialist option").length > 0
                        || $("a#platformSpecialist").next('.multiSelectOptions').find('INPUT:checkbox:checked').not('.optGroup, .selectAll').length > 0)) {
                        // no response data and there is option selected

                        var rows;
                        if ($("#platformSpecialist option").length > 0) {
                            rows = $("#platformSpecialist option:selected");
                        } else {
                            rows = $("a#platformSpecialist").next('.multiSelectOptions').find('INPUT:checkbox:checked').not('.optGroup, .selectAll');
                        }

                        $.each(rows, function(index, value){
                            var emptyRow = [];
                            if($(value).is("option")) {
                                emptyRow.push($(value).text())
                            } else {
                                emptyRow.push($(value).parent().text());
                            }

                            for (var i = 0; i < $("a.selectMonth").length; ++i) {
                                emptyRow.push(formatMoney(0));
                            }

                            tableData.push(emptyRow);
                        });

                    }

                    $.each(tableData, function(index, dataRow){
                        var rowHTML = "<tr>"
                        var s = {data:[]};
                        for(var k = 0; k < dataRow.length; ++k) {
                            rowHTML = rowHTML + "<td>" + dataRow[k] + "</td>";
                            if (k == 0) {
                                s.name = dataRow[k];
                            } else {
                                s.data.push(parseFloat(dataRow[k].replace(",", "").replace("$", "")));
                            }
                        }
                        rowHTML += "</tr>";
                        tbody.append(rowHTML);
                        seriesData.push(s);
                    });

                    $("#loadingTable").hide();

                    $("div.tableData").append(table);

                    var aoColumns = [];
                    aoColumns.push({ "sType": "html-trimmed"});
                    for (var j = 0; j < tableData[0].length - 1; ++j) {
                        aoColumns.push({ "sType": "money" });
                    }


                    var dataTable = table.dataTable({
                        "bPaginate": false,
                        "bLengthChange": false,
                        "bFilter": false,
                        "bSort": true,
                        "bInfo": false,
                        "bAutoWidth": true,
                        "aoColumns":aoColumns
                    });

                    $("div.tableData").data("dataTable", dataTable);

                    var options = {
                        chart: {
                            renderTo: 'chartPlatformSpecialistsSpend',
                            type: 'line',
                            marginRight: 130,
                            marginBottom: 25
                        },
                        title: {
                            text: 'Expert Services Spend Per Month',
                            x: -20 //center
                        },
                        xAxis: {
                            categories: categories
                        },
                        yAxis: {
                            title: {
                                text: 'Member Spend'
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: '#808080'
                            }]
                        },
                        tooltip: {
                            formatter: function() {
                                return '<b>'+ this.series.name +'</b><br/>'+
                                    this.x +': '+ formatMoney(this.y);
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -10,
                            y: 100,
                            borderWidth: 0
                        },
                        series: seriesData
                    };

                    var chart = new Highcharts.Chart(options);
                },
                function(errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });
}

// gets the request for platform specialists report
function getRequest() {
    var request = {};
    var headerText = "";
    if($("a#platformSpecialist").length != 0) {
        request.userIds = $("#platformSpecialist").selectedValuesString();
        var options = $("a#platformSpecialist").next('.multiSelectOptions').find('INPUT:checkbox:checked').not('.optGroup, .selectAll');
        if (options.length >1) {
            headerText = options.length + " SELECTED";
        } else if (options.length == 1 ) {
            headerText = options.parent().text();
        } else {
            return null;
        }
    } else {
        request.userIds = $("#platformSpecialist").val();

        if(request.userIds && request.userIds.length > 1) {
            headerText = request.userIds.length + " SELECTED";
        } else if (request.userIds && request.userIds.length == 1 ) {
            headerText = $("#platformSpecialist option:selected").text();
        } else {
            return null;
        }
    }

    var startMonth = $(".timeLine .selectMonth:first span span").text();
    var endMonth = $(".timeLine .selectMonth:last span span").text();

    request.startMonth = startMonth;
    request.endMonth = endMonth;

    // render header part
    $("#headerPlatformSpecialist").text(headerText);
    $("#headerDate").text(startMonth == endMonth ? startMonth : startMonth + ' - ' + endMonth);


    if(startMonth == '' || endMonth == '') {
        return null;
    }

    return request;
}

/**
 * Formats a number to money format
 *
 * @param strNum the number
 * @return the formated string representing the money
 */
function formatMoney(strNum){
    strNum = parseFloat(strNum);
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
    return "$"+ a + "" + b + "" + c;
}


