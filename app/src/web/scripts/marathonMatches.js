/**
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * The JavaScript code used by the Marathon Match contest views.
 *
 * - Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab
 * - Update for competitors' rating bar, pie graph.
 * - Update for submission history page.
 *
 * - Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab
 * - Update script for loading time line graph.
 * - Update script for loading submissions tab and handle the ajax call.
 *
 * - Version 1.3 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 * - Update script for loading final score ranking graph and final score vs provisional score graph.
 *
 * - Version 1.4 - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 * - Fix a bug of sorting direction.
 * - Add function to fix the height of pagination button and system tests table.
 *
 * @author Ghost_141
 * @version 1.4
 * @since 1.0
 */
// JavaScript Document
$(document).ready(function(){
	
	$('.registrantsAndSubmissions .registrantsContaner table#registrantsTable tr:even').addClass('even');
	$('.registrantsAndSubmissions .registrantsContaner table#listViewTable tr:even').addClass('even');

    var $systemTestEmptyBlock = $(".systemTestTable .emptyBlock");
    var $systemTestTable = $(".systemTestTable .dataTables_wrapper");
    // Fix the height of pagination button
    if($(".systemTestTable").length > 0) {
        var tableHeight = $systemTestTable.height();
        $systemTestEmptyBlock.height(tableHeight/2 - 77);
    }
    // Fix the width of table
    if($systemTestEmptyBlock.length == 0) {
        // if there is none pagination button.
        $systemTestTable.width("100%");
    } else if ($systemTestEmptyBlock.length == 1) {
        // if there is one pagination button.
        $systemTestTable.width("98%");
    }
    // if there is two pagination buttons the default css style will be used.

	//PIE for Registrants
	if($('#ratingPie').length){
        var ratingPie = [];
        $.each(pieData.rating,function(idx,item){
            ratingPie.push({
                name: item.handle,
                y: item.number/1,
                color: item.color,
                dataLabels: {
                    color: item.textColor
                }
            });
        });

        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'ratingPie',
                backgroundColor: null,
                margin: [0, 0, 0, 0]
            },
            credits: {
                text : ''
            },
            title: {
                text: null
            },
            tooltip: {
                enabled: false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    ignoreHiddenPoint: true,
                    cursor: 'pointer',
                    showInLegend: true,
                    size: '60%',
                    center: ['50%', '40%'],
                    borderColor: '#cccccc'
                }
            },
            legend: {
                margin: 0,
                float: false,
                borderWidth: 0,
                borderRadius: 0,
                padding: 20,
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#333333',
                    fontWeight:'bold'
                },
                itemMarginTop: 14,
                symbolWidth: 16,
                y: -10
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: ratingPie,
                dataLabels: {
                    distance: -20,
                    connectorWidth: 0,
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '12px',
                        fontWeight:'bold'
                    },
                    formatter: function() {
                        if(this.y != 0) {
                            return this.y;
                        }
                    }
                }
            }]
        });

	}
	
	//BAR for Registrants
	if($('#ratingBar').length){
        var ratingData = [];
        var ratingLabel = [];

        if(barData.rating.length != 0) {
            $.each(barData.rating, function(index, value) {
                ratingLabel.push(value.handle);
                ratingData.push({
                    y: value.number/1,
                    color: value.color
                });
            })
            var height = barData.rating.length * 20;
            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'ratingBar',
                    type: 'bar',
                    backgroundColor: null,
                    margin: [0, 0, 20, 100],
                    height: height
                },
                title: {
                    text: null
                },
                xAxis: {
                    categories: ratingLabel,
                    title: {
                        text: null
                    },
                    labels:{
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        }
                    },
                    tickLength: 0,
                    lineColor: '#c8c8c8'
                },
                yAxis: {
                    max:4300,
                    title: {
                        text: null
                    },
                    lineWidth: 1,
                    lineColor: '#c8c8c8',
                    gridLineColor: '#e4e4e4',
                    labels:{
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        formatter: function() {
                            return this.value;
                        }
                    },
                    showFirstLabel: false,
                    endOnTick: false
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    borderRadius: 1,
                    borderWidth: 1,
                    formatter: function() {
                        return this.y;
                    },
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '12px',
                        color: '#333333',
                        padding: '8px'
                    }
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: false
                        },
                        shadow: false
                    },
                    series: {
                        turboThreshold: 100000
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    data: ratingData
                }]
            });
        }

	}
	
	//CHART for Timeline
	if($('#timelineChart').length){
        var json = timeLineData;
        var score = [];
        var competitiors = [];
        var submissions = [];
        var plotBands = [];
        var legendColor, legendName;
        $.each(json.score,function(idx,item){
            if(item.name == 'Best Provisional Score'){
                legendColor = "#d29ed4";
                legendName = item.name;
                for(i=0;i<item.scoreDetail.length;i++){
                    if(item.scoreDetail[i].enabled == '1'){
                        // TODO: The dot line is disabled for now. It should be back in the future.
//                        if(item.scoreDetail[i].maxScore == '1'){
//                            score.push({
//                                y: item.scoreDetail[i].score/1000,
//                                dataLabels: {
//                                    enabled: true,
//                                    y: -12,
//                                    color: '#d29ed4',
//                                    formatter: function() {
//                                        return this.y * 1000;
//                                    }
//                                },
//                                marker: {
//                                    symbol: 'url(/images/circle-pink.png)'
//                                }
//                            });
//                        }else{
//                            score.push({
//                                y: item.scoreDetail[i].score/1000,
//                                dataLabels: {
//                                    enabled: true,
//                                    y: -12,
//                                    formatter: function() {
//                                        return this.y * 1000;
//                                    }
//                                },
//                                marker: {
//                                    symbol: 'url(/images/circle-pink.png)'
//                                }
//                            });
//                            plotBands.push({
//                                color: '#ffffff',
//                                width: 1,
//                                value: i,
//                                zIndex: 5,
//                                dashStyle: 'dot'
//                            });
//                        }
                        score.push({
                                y: item.scoreDetail[i].score/1000,
                                dataLabels: {
                                    enabled: true,
                                    y: -12,
                                    formatter: function() {
                                        return this.y * 1000;
                                    }
                                },
                                marker: {
                                    symbol: 'url(/images/circle-pink.png)'
                                }
                            });
                    }else{
                        score.push(item.scoreDetail[i].score?item.scoreDetail[i].score/1000:null);
                    }
                }
            } else if(item.name == 'Best Final Score') {
                legendColor = "#bdc800";
                legendName = item.name;
                for(i=0;i<item.scoreDetail.length;i++){
                    if(item.scoreDetail[i].enabled == '1'){
                        // TODO: The dot line is disabled now. It should be back in the future.
//                        if(item.scoreDetail[i].maxScore == '1' || i == item.scoreDetail.length - 1){
//                            score.push({
//                                y: item.scoreDetail[i].score/1000,
//                                dataLabels: {
//                                    enabled: true,
//                                    y: -12,
//                                    color: '#bdc800',
//                                    formatter: function() {
//                                        return this.y * 1000;
//                                    }
//                                },
//                                marker: {
//                                    symbol: 'url(/images/circle-completed.png)'
//                                }
//                            });
//                        }else{
//                            score.push({
//                                y: item.scoreDetail[i].score/1000,
//                                dataLabels: {
//                                    enabled: true,
//                                    y: -12,
//                                    formatter: function() {
//                                        return this.y * 1000;
//                                    }
//                                },
//                                marker: {
//                                    symbol: 'url(/images/circle-completed.png)'
//                                }
//                            });
//                            plotBands.push({
//                                color: '#ffffff',
//                                width: 1,
//                                value: i,
//                                zIndex: 5,
//                                dashStyle: 'dot'
//                            });
//                        }
                        score.push({
                            y: item.scoreDetail[i].score/1000,
                            dataLabels: {
                                enabled: true,
                                y: -12,
                                color: '#bdc800',
                                formatter: function() {
                                    return this.y * 1000;
                                }
                            },
                            marker: {
                                symbol: 'url(/images/circle-completed.png)'
                            }
                        });
                    }else{
                        score.push(item.scoreDetail[i].score?item.scoreDetail[i].score/1000:null);
                    }
                }
            }else if(item.name == 'Number of Competitors'){
                for(i=0;i<item.scoreDetail.length;i++){
                    if(item.scoreDetail[i].enabled == '1'){
                        competitiors.push({
                            y: item.scoreDetail[i].score/1,
                            dataLabels: {
                                enabled: true,
                                y: 4	,
                                x: 1
                            },
                            marker: {
                                symbol: 'url(/images/circle-yellow.png)'
                            }
                        });
                    }else{
                        competitiors.push(item.scoreDetail[i].score?item.scoreDetail[i].score/1:null);
                    }
                }
            }else{
                for(i=0;i<item.scoreDetail.length;i++){
                    if(item.scoreDetail[i].enabled == '1'){
                        submissions.push({
                            y: item.scoreDetail[i].score/1,
                            dataLabels: {
                                enabled: true,
                                y: 3,
                                x: 1
                            },
                            marker: {
                                symbol: 'url(/images/circle-blue.png)'
                            }
                        });
                    }else{
                        submissions.push(item.scoreDetail[i].score?item.scoreDetail[i].score/1:null);
                    }
                }
            }
        });
        var lastNumber = [];
        lastNumber.push({
            value: submissions[submissions.length - 1].y == undefined ? submissions[submissions.length - 1] : submissions[submissions.length - 1].y,
            name: "submissions"
        });
        lastNumber.push({
            value: competitiors[submissions.length - 1].y == undefined ? competitiors[submissions.length - 1] : competitiors[submissions.length - 1].y,
            name: "competitors"
        });
        lastNumber.push({
            value: score[submissions.length - 1].y == undefined ? score[submissions.length - 1] : score[submissions.length - 1].y,
            name: "score"
        });
        lastNumber.sort(function(a, b) {
            return b.value - a.value;
        });
        var scoreIndex, submissionsIndex, competitorsIndex;
        $.each(lastNumber, function(index, item) {
            if(item.name == "score") {
                scoreIndex = index;
            } else if(item.name == "competitors") {
                competitorsIndex = index;
            } else if(item.name == "submissions") {
                submissionsIndex = index;
            }
        });

        var xLength = json.xLength;
        var currentInterval = json.currentInterval;
        var submissionPhase = json.submissionPhase;
        var systemTesting = json.systemTesting;
        var active = json.active;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'timelineChart',
                type: 'area',
                margin: [25, 30, 50, 30]
            },
            title: {
                text: null
            },
            legend: {
                float: true,
                borderRadius: 0,
                borderColor: '#cccccc',
                backgroundColor: '#f5f5f5',
                padding: 8,
                verticalAlign: 'top',
                align: 'right',
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#333333'
                },
                y: -11,
                x: 11,
                symbolWidth: 16
            },
            xAxis: {
                min: 0,
                max: xLength - 1,
                plotBands: plotBands,
                startOnTick: true,
                tickInterval: 1,
                tickLength: 0,
                lineColor: '#999999',
                labels: {
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#000000',
                        lineHeight: '16px'
                    },
                    enabled: true,
                    useHTML: true,
                    formatter: function(){
                        if(this.value == '0'){
                            return '<span style="text-align:left; display:block; margin:0 0 0 128px; padding:12px 0 0 0; background:url(/images/icon-dot.png) no-repeat left top;">Submission Phase<br />Start: ' + submissionPhase + '</span>';
                        }else if(this.value == xLength - 1){
                            return '<span id="systemTestingPoint" style="text-align:right; display:block; margin:0 97px 0 0; padding:12px 0 0 0; background:url(/images/icon-dot.png) no-repeat right top;">System Testing<br />Start: ' + systemTesting + '</span>';
                        }else if(this.value == currentInterval){
                            return '<span style="display:block; position:absolute; z-index:10; *top:4px; left:-8px;"><img src="/images/icon-current.png" /></span>';
                        }else{
                            return null;
                        }
                    },
                    y: 8
                }
            },
            yAxis: {
                title: {
                    text: 'Score',
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#000000',
                        fontWeight: 'normal'
                    },
                    rotation: 0,
                    y: -70,
                    x: 10
                },
                labels: {
                    enabled: false
                },
                gridLineWidth: 0,
                lineWidth: 1,
                lineColor: '#999999'
            },
            plotOptions: {
                area: {
                    marker: {
                        radius: 0
                    },
                    lineWidth: 0,
                    fillOpacity: 1,
                    shadow: true,
                    dataLabels: {
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333',
                            fontWeight: 'bold'
                        }
                    },
                    enableMouseTracking: false
                }
            },
            tooltip: {
                enabled: false
            },
            credits: {
                enabled: false
            },
            series: [{
                name: legendName,
                data: score,
                color: legendColor,
                zIndex: scoreIndex
            },{
                name: 'Number of Competitors',
                data: competitiors,
                color: '#986a00',
                zIndex: competitorsIndex
            },{
                name: 'Number of Full Submissions',
                data: submissions,
                color: '#6dcff6',
                zIndex: submissionsIndex
            }]
        });

        // fix the system testing point position.
        var left = $("#systemTestingPoint").parent().css({
            left: function(index, value) {
                return parseFloat(value) - 16;
            }
        });
	}
	
	//COLUMN for Final Ranking
	function getRankingChart(){
		if($('#rankingChart').length){
            var ratingData = [];
            var ratingLabel = [];
            var rank = [];
            $.each(finalScoreRankingData.rating,function(idx,item){
                ratingLabel.push(item.handle);
                ratingData.push(item.number/1);
                rank.push(item.rank/1);
            });

            var height = ratingLabel.length * 40;

            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'rankingChart',
                    type: 'bar',
                    backgroundColor: null,
                    margin: [0, 10, 20, 100],
                    height: height
                },
                title: {
                    text: null
                },
                scrollbar: {
                    enabled: false
                },
                xAxis: {
                    categories: ratingLabel,
                    title: {
                        text: null
                    },
                    labels:{
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        }
                    },
                    tickLength: 0,
                    lineColor: '#c8c8c8'
                },
                yAxis: {
                    title: {
                        text: 'Score',
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        rotation: 0,
                        y: -210,
                        x: 45
                    },
                    lineWidth: 1,
                    lineColor: '#999999',
                    gridLineColor: '#e4e4e4',
                    labels:{
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        formatter: function() {
                            return this.value;
                        }
                    },
                    showFirstLabel: false,
                    endOnTick: false
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    borderRadius: 1,
                    borderWidth: 1,
                    formatter: function() {
                        return '<table style="width:150px; margin:0 5px;">'
                            +'<tr>'
                            +'<td style="width:70px;"><b>Handle</b></td>'
                            +'<td><b>: </b>'+ this.x +'</td>'
                            +'</tr>'
                            +'<tr>'
                            +'<td><b>Final Score:</b></td>'
                            +'<td><b>: </b>'+ this.y +'</td>'
                            +'</tr>'
                            +'<tr>'
                            +'<td><b>Ranking:</b></td>'
                            +'<td><b>: </b>'+ rank[this.point.x] +'</td>'
                            +'</tr>'
                            +'</table>';
                    },
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#333333',
                        lineHeight: '18px',
                        padding: '8px'
                    },
                    useHTML: true
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true,
                            formatter: function(){
                                return this.point.x;
                                if(this.y != 0) {
                                    return rank[this.point.x];
                                }
                            },
                            verticalAlign: 'top',
                            align: 'center',
                            style: {
                                fontFamily: 'Arial',
                                fontSize: '12px',
                                color: '#ffffff',
                                fontWeight: "bold"
                            },
                            x: -20,
                            y: -1
                        },
                        shadow: false
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Total Spend',
                    color: '#bdc800',
                    type: 'column',
                    data: ratingData,
                    shadow: false,
                    borderColor: '#bdc800'
                }]
            });
		}
	}
	ajaxTableLoader = setTimeout(function(){
		getRankingChart();
	},200);
	
	//COLUMN for Final Ranking
	function getSoreChart(){
		if($('#scoreChart').length){
            var finalScoreData = [];
            var provisionalScoreData = [];
            var ratingLabel = [];
            $.each(finalVsProvisionalScoreData.score,function(idx,item){
                ratingLabel.push(item.handle);
                finalScoreData.push(item.finalScore/1);
                provisionalScoreData.push(item.provisionalScore/1);
            });

            var height = finalScoreData.length * 60;

            chart = new Highcharts.Chart({
                chart: {
                    type: 'bar',
                    renderTo: 'scoreChart',
                    backgroundColor: null,
                    margin: [30, 20, 30, 100],
                    height: height
                },
                credits: {
                    text : ''
                },
                scrollbar: {
                    enabled: false
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                title: {
                    text: ''
                },
                xAxis: {
                    categories: ratingLabel,
                    min: 0,
                    tickLength: 0,
                    labels: {
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#000000'
                        }
                    },
                    lineColor: '#999999'
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Score',
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        rotation: 0,
                        y: -170,
                        x: 50
                    },
                    gridLineWidth: 1,
                    gridLineColor: '#e3e3e3',
                    lineColor: '#999999',
                    tickColor: '#999999',
                    lineWidth: 1,
                    endOnTick: false,
                    labels: {
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        formatter: function() {
                            return this.value;
                        }
                    }
                },
                tooltip: {
                    useHTML: true,
                    backgroundColor: "#FFFFFF",
                    borderRadius: 0,
                    borderWidth: 1,
                    headerFormat: '<table style="margin:0 5px;">',
                    pointFormat:
                        '<tr>' +
                            '<td style="color:{series.color}; padding-right: 10px;">{series.name}: </td>' +
                            '<td><b style="color: {series.color};">{point.y}</b></td>' +
                            '</tr>',
                    footerFormat: '</table>',
                    shared: true,
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        lineHeight: '16px',
                        padding: '8px'
                    }
                },
                legend: {
                    verticalAlign: 'top',
                    align: 'right',
                    borderColor: '#cccccc',
                    borderRadius: 0,
                    backgroundColor: '#f5f5f5',
                    padding: 10,
                    itemStyle: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#333333'
                    },
                    symbolWidth: 16,
                    symbolPadding: 8,
                    x: 12,
                    y: -11
                },
                plotOptions: {
                    series: {
                        marker: {
                            symbol: "circle"
                        }
                    }
                },
                series: [{
                    name: 'Final Score',
                    data: finalScoreData,
                    color: '#bdc800'
                }, {
                    name: 'Provisional Score',
                    data: provisionalScoreData,
                    color: '#d29ed4'
                }]
            });
		}
	}

    var chartTopSubmissions;
	//Top Submissions
	function loadTopSubmissions(json){
		if($('#chartTopSubmissions').length){

            var chartDataArray = new Array();
            var chartSeriesArray = [];
            chartSeriesArray.push({
                name: 'Full Submissions',
                data: null,
                type: 'scatter',
                color: '#494240',
                events: {
                    legendItemClick: function(event) {
                        return false;
                    }
                }
            });
            for(j=0;j<json.submissions.length;j++){
                for(i=0;i<json.submissions[j].changeLog.length;i++){
                    chartDataArray[i] = new Array();
                    chartDataArray[i].push(Date.UTC(json.submissions[j].changeLog[i].year,json.submissions[j].changeLog[i].month,json.submissions[j].changeLog[i].day,json.submissions[j].changeLog[i].hour,json.submissions[j].changeLog[i].minutes));
                    chartDataArray[i].push(json.submissions[j].changeLog[i].score/1);
                    chartDataArray[i].push(json.submissions[j].changeLog[i].number/1);
                    chartDataArray[i].push(json.submissions[j].changeLog[i].language);
                    chartDataArray[i].push(json.submissions[j].changeLog[i].time);
                }
                chartSeriesArray.push({
                    name: json.submissions[j].handle,
                    data: chartDataArray,
                    color: json.submissions[j].color
                });
                chartDataArray = [];
            }
            chartTopSubmissions = new Highcharts.Chart({
                chart: {
                    renderTo: 'chartTopSubmissions',
                    type: 'line',
                    backgroundColor: null,
                    margin: [50, 200, 50, 50]
                },
                title: {
                    text: null
                },
                credits: {
                    text : ''
                },
                xAxis: {
                    type: 'datetime',
                    startOnTick: true,
                    showFirstLabel: false,
                    maxPadding: 0.3,
                    lineColor: '#999999',
                    tickInterval: 24 * 3600 * 1000,
                    labels: {
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        }
                    },
                    gridLineWidth: 1,
                    gridLineColor: '#e3e3e3',
                    tickLength: 0,
                    dateTimeLabelFormats: {
                        day: '%m/%d/%Y'
                    }
                },
                yAxis: {
                    showFirstLabel: false,
                    lineColor: '#999999',
                    title: {
                        text: 'Score',
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        rotation: 0,
                        y: -186,
                        x: 45
                    },
                    labels: {
                        style: {
                            fontFamily: 'Arial',
                            fontSize: '11px',
                            color: '#333333'
                        },
                        formatter: function() {
                            return this.value;
                        },
                        x: -10,
                        y: 4
                    },
                    gridLineWidth: 0,
                    lineWidth: 1,
                    tickWidth: 1,
                    tickLength: 8,
                    endOnTick: false,
                    tickColor: '#999999'
                },
                tooltip: {
                    useHTML: true,
                    shadow: false,
                    backgroundColor : {
                        linearGradient : [ 0, 0, 0, 60 ],
                        stops : [ [ 0, '#FFFFFF' ], [ 1, '#e9e9e9' ] ]
                    },
                    formatter: function() {
                        return '<table style="border:#cccccc solid 1px; border-collapse:collapse; width:200px;">' +
                            '<tr>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;"><b>Submission #</b></td>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;">' + this.point.config[2] + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#f5f5f5;"><b>Submission Type</b></td>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#f5f5f5;">Full</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;"><b>Time Submitted</b></td>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;">' + this.point.config[4] + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#f5f5f5;"><b>Score</b></td>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#f5f5f5;">' + this.point.y + '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;"><b>Language</b></td>' +
                            '<td style="text-align:center; border:#cccccc solid 1px; background-color:#FFFFFF;">' + this.point.config[3] + '</td>' +
                            '</tr>' +
                            '</table>';
                    },
                    borderColor: '#cfcfcd',
                    borderRadius: 0,
                    borderWidth: 1,
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#333333',
                        lineHeight: '22px'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: false
                        },
                        marker: {
                            symbol: 'diamond',
                            fillColor: '#494240',
                            radius: 6
                        },
                        shadow: false
                    },
                    scatter: {
                        marker: {
                            symbol: 'diamond',
                            fillColor: '#494240',
                            radius: 6
                        }
                    }
                },
                legend: {
                    verticalAlign: 'top',
                    align: 'right',
                    borderColor: '#cccccc',
                    borderRadius: 0,
                    backgroundColor: '#f5f5f5',
                    padding: 10,
                    itemStyle: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#333333'
                    },
                    symbolWidth: 16,
                    symbolPadding: 8,
                    x: 12,
                    y: 40,
                    layout: 'vertical',
                    itemMarginTop: 8,
                    itemMarginBottom: 8,
                    labelFormatter: function() {
                        if(this.name != 'Full Submissions'){
                            return 'Provisional Rank '+(this.index)+': <b>'+this.name+ '</b>';
                        }else{
                            return this.name;
                        }
                    }
                },
                series: chartSeriesArray
            });
		}
        return chartTopSubmissions;
	}

    function fixTimeRange(chart, dateOffset) {
        chart.xAxis[0].setExtremes(
            Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7 * dateOffset, slStartDate.hour),
            Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7 * (dateOffset + 1), slStartDate.hour));
    }

    if($('#chartTopSubmissions').length){
        var dateOffset = 0;
        // Set up these two dates when first load this page.
        var slStartDate = submissionLineData.startDate;
        var slEndDate = submissionLineData.endDate;

        var c = loadTopSubmissions(submissionLineData);
        fixTimeRange(c, dateOffset);

        $('.chartTopSubmissions a.prev:not(.disable)').addClass('disable');
        if(Date.UTC(slEndDate.year, slEndDate.month, slEndDate.day, slEndDate.hour) < Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7, slStartDate.hour)) {
            $('.chartTopSubmissions a.next:not(.disable)').addClass('disable');
        } else {
            $('.chartTopSubmissions a.next.disable').removeClass('disable');
        }
    }

	$('.chartTopSubmissions a.prev:not(.disable)').live('click',function(){
        fixTimeRange(chartTopSubmissions, dateOffset - 1);
        dateOffset--;
        if(Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7 * dateOffset, slStartDate.hour) >
            Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day, slStartDate.hour)) {
            $(".chartTopSubmissions a.prev").removeClass('disable');
        } else {
            $('.chartTopSubmissions a.prev').addClass('disable');
        }
        $('.chartTopSubmissions a.next').removeClass('disable');
    });
	
	$('.chartTopSubmissions a.next:not(.disable)').live('click',function(){
        fixTimeRange(chartTopSubmissions, dateOffset + 1);
        dateOffset++;
        if(Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7 * dateOffset, slStartDate.hour) >
            Date.UTC(slEndDate.year, slEndDate.month, slEndDate.day, slEndDate.hour)) {
            $('.chartTopSubmissions a.next').removeClass('disable');
        } else {
            $('.chartTopSubmissions a.next').addClass('disable');
        }
		$('.chartTopSubmissions a.prev').removeClass('disable');
	});
	
	$('.viewTopSubmissions a').live('click',function(){
		var numberSubmions = $('#topSubmissionSelect').val();
        var submissoinStartInput = $("#submissionStart");
        var submissionStart = submissoinStartInput.val();
        if(submissionStart.length == 0) {
            showErrors("You have to input a start number of submissions");
            submissoinStartInput.val('');
            return false;
        }
        if(submissionStart <= 0) {
            showErrors("The submission start index should larger than 0");
            submissoinStartInput.val('');
            return false;
        }
        var regNumber = /^\d+$/;
        var numberTest = regNumber.test(submissionStart);

        if(!numberTest) {
            showErrors("The submission start index should be integer only.");
            submissoinStartInput.val('');
            return false;
        }

        var reg = new RegExp("(^|&)projectId=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        var projectId = r[2];
        $.ajax({
            type: "GET",
            url: "/direct/contest/mmSubmissions",
            data: {
                projectId: projectId,
                submissionStart: submissionStart,
                submissionSize: numberSubmions
            },
            dataType: "json",
            success: function(json){
                handleJsonResult(json,
                function(result) {
                    var c = loadTopSubmissions(JSON.parse(result.submissionLineData));
                    dateOffset = 0;
                    fixTimeRange(c, dateOffset);
                    $('.chartTopSubmissions a.prev:not(.disable)').addClass('disable');
                    if(Date.UTC(slEndDate.year, slEndDate.month, slEndDate.day, slEndDate.hour) < Date.UTC(slStartDate.year, slStartDate.month, slStartDate.day + 7, slStartDate.hour)) {
                        $('.chartTopSubmissions a.next:not(.disable)').addClass('disable');
                    } else {
                        $('.chartTopSubmissions a.next.disable').removeClass('disable');
                    }
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
            },
            beforeSend: function() {
                modalPreloader();
            },
            complete: function() {
                modalClose();
            }
        });
	});
	
	$('#socreTab li a').live('click',function(){
		$('#socreTab li').removeClass('active');
		$(this).parents('li').addClass('active');
		$('#scoreTabContainer .tabsContent').hide();
		var i = $('#socreTab li a').index(this);
		$('#scoreTabContainer .tabsContent').eq(i).show();
		$('#rankingChart,#scoreChart').empty();
		ajaxTableLoader = setTimeout(function(){
			getSoreChart();
			getRankingChart();
		},200);
	});
	
	//Person Score
	if($('#personScoreChart').length){
        var score = [];
        $.each(submissionHistory.personScore,function(idx,item){
            score[idx] = [];
            score[idx].push(Number(item.x));
            score[idx].push(Number(item.score));
            score[idx].push(item.date);
        });
        var X = submissionHistory.x;
        var startDate = submissionHistory.submissionStartTime;
        var endDate = submissionHistory.submissionEndTime;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'personScoreChart',
                type: 'scatter',
                margin: [55, 30, 50, 50],
                backgroundColor: null
            },
            title: {
                text: null
            },
            xAxis: {
                min: 0,
                max: X + 15,
                startOnTick: true,
                tickInterval: 1,
                tickLength: 0,
                lineColor: '#999999',
                labels: {
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#000000',
                        lineHeight: '16px'
                    },
                    enabled: true,
                    useHTML: true,
                    formatter: function(){
                        if(this.value == '0'){
                            return '<span style="text-align:left; display:block; margin:0 0 0 102px; padding:12px 0 0 0; background:url(/images/icon-dot.png) no-repeat left top;">Submission Phase<br />' + startDate + '</span>';
                        }else if(this.value == X){
                            return '<span style="text-align:right; display:block; margin:0 90px 0 0; padding:12px 0 0 0; background:url(/images/icon-dot.png) no-repeat right top;">System Testing<br />' + endDate + '</span>';
                        }else{
                            return null;
                        }
                    },
                    y: 8
                }
            },
            credits: {
                enabled: false
            },
            yAxis: {
                title: {
                    text: 'Provisional<br />Score',
                    style: {
                        fontFamily: 'Arial',
                        fontSize: '11px',
                        color: '#333333',
                        fontWeight: 'normal'
                    },
                    rotation: 0,
                    y: -105,
                    x: 10
                },
                gridLineWidth: 0,
                lineWidth: 1,
                lineColor: '#999999',
                showLastLabel: false,
                labels: {
                    enabled: false
                }
            },
            tooltip: {
                formatter: function() {
                    return "Submission Date: " + this.point.config[2] + "<br>Score: " + this.y;
                },
                style: {
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#d29ed4',
                    fontWeight: 'bold'
                }
            },
            legend: {
                verticalAlign: 'top',
                align: 'right',
                borderColor: '#cccccc',
                borderRadius: 0,
                backgroundColor: '#f5f5f5',
                padding: 8,
                itemStyle: {
                    fontFamily: 'Arial',
                    fontSize: '11px',
                    color: '#333333'
                },
                symbolWidth: 10,
                symbolPadding: 8,
                x: 11,
                y: -11,
                layout: 'vertical'
            },
            plotOptions: {
                scatter: {
                    marker: {
                        radius: 6,
                        symbol: 'diamond'
                    }
                }
            },
            series: [{
                name: 'Full Submissions',
                color: '#46403e',
                data: score
            }]
        });
	}
	
	$('.statusP .progress').css('width',$('.statusP .progress').text());
	
	window.onerror=function(){return true;};
	$('.registrantsAndSubmissions .chartWrapper .chartBar #ratingBarWrapper').css('overflow-x','hidden');
	if($.browser.msie&&($.browser.version == "7.0")){
		$('.resultDetails .resultTable .resultDetailsTable').css('width',$('.resultDetails .resultTable').width()-$('.resultDetails .resultTable .handleTable').width());
		$('.resultDetails .resultTable .resultDetailsTable .resultScrollBar').css('height',$('.resultDetails .resultTable .resultDetailsTable .resultScrollBar table').height()+16);
		window.onresize=function(){
			$('.resultDetails .resultTable .resultDetailsTable').css('width',$('.resultDetails .resultTable').width()-$('.resultDetails .resultTable .handleTable').width());
		};
        $(".systemTestTable .dataTables_wrapper .bottom1").remove();
        $(".systemTestTable .dataTables_wrapper .bottom2").remove();
	}

});