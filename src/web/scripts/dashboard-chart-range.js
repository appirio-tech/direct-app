/**
 * - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) Change Notes:
 * - Add the codes for the new summary panel and it's tips
 *
 * - Version 1.2 (Release Assembly - TC Cockpit Enterprise Dashboard Volume View Assembly) change notes:
 * - Add the support for the enterprise dashboard volume view
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
"23" :  ["Conceptulization", "Concept"],
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
"9" :  ["Bug Hunt" , "Bug Hunt"]
};


$(document).ready(function() {
    /* Multi Select */
    $('.graphArea .multiselect').multiSelect();


    /* Toggle Filter */
    $("a.fiterButton").click(function() {
        $(".filterContest .contestType a span").css({"width": "165px"});
        $(".filterContest .contestType div").css({"width": "188px"});
        if ($(this).parent().find(".filterArea").css('display') == 'none') {
            showFilter($(this));
        }
        else {
            hideFilter($(this));
        }
    });

    /* new add */
	$(".summaryPanel .tabs a").click(function(){
		$(".summaryPanel .tabs a.current").removeClass("current");
		$(".summaryPanel .tabs a.currentPrev").removeClass("currentPrev");
		$(this).parent().prev().children().addClass("currentPrev");
		$(this).addClass("current");
		switch($(this).parent().index()){
			case 0:
				$(".summaryPanel .tabs").removeClass("market").removeClass("compare").addClass("customer");
				$(".customerSummary").removeClass("hide");
				$(".marketSummary").addClass("hide");
				$(".compare").addClass("hide");
				break;
			case 1:
				$(".summaryPanel .tabs").addClass("market").removeClass("compare").removeClass("customer");
				$(".customerSummary").addClass("hide");
				$(".marketSummary").removeClass("hide");
				$(".compare").addClass("hide");
				break;
			case 2:
				$(".summaryPanel .tabs").removeClass("market").addClass("compare").removeClass("customer");
				$(".customerSummary").addClass("hide");
				$(".marketSummary").addClass("hide");
				$(".compare").removeClass("hide");
				break;
			default:
				break;
		}
	})

	$(".fieldName").hover(
		function(){
			$(this).find(".tooltipBox").css("display", "block");
		},
		function(){
			$(this).find(".tooltipBox").css("display", "none");
		}
	);

    /* Apply button action */
    $('a.applyButton').click(function(event) {
        //	doFilter();
        $('#chart_div iframe').width($(".visualization").width() - 5);
    });

    $('.dateRange a').click(function() {
        return false;
    });


    /**
     * The click function of volume view button in title bar.
     * @since 1.2
     */
    $('.btnView').click(function() {
        $('.btnArea a').removeClass('active');
        $(this).addClass('active');
        $('.chartCollapse a.expand').html('Volume View').removeClass("collapse");
        $(".chartContent").show();
        $(".volumeView a.fiterButton").css('background-position', 'bottom left');
        $(".summaryPanel, .comparisonView").hide();
        $(".graphArea, .filterArea, .tableView, #firstTableDataArea").hide();
        $(".volumeView .filterArea,.volumeView .filterLinkArea").show();
        $('.volumeView a.fiterButton').css('background-position', 'top left');
        $(".volumeView").show();
        $('.volumeView .multiselect').multiSelect();

        loadVolumeViewData();

    });

    /**
     * Truncate the volume view summary table header is the width is not enough.
     *
     * @param table the jquery table instance.
     * @since 1.2
     */
    function truncateVolumeViewSummary(table){
        var truncate = false;
        // the number of columns except total
        var cols = table.find("th").length - 1;

        var minFullColWidth = 150;
        var minShortColWidth = 80;
        var totalColumnWidth = 90;
        if(table.width() >= cols * minFullColWidth + totalColumnWidth){
            table.find("th span.full").show();
            table.find("th span.short").hide();
        }else{
            table.find("th span.full").hide();
            table.find("th span.short").show();
            truncate = true;
        }

        var width = '100%';

        if(table.parent().width() < cols * minShortColWidth + totalColumnWidth ){
            width =  cols * minShortColWidth + totalColumnWidth;
            $(".volumeView .summary .tabsContent").height('140');
        } else {
            width = $("div.tableScroll").width();
            $(".volumeView .summary .tabsContent").height('auto');
        }

        table.css({
            "width" : width
        });

        var colWidth = (width - 90) / cols;
        $("col.data").attr("width", colWidth + "px");

        $(".volumeView .summary .tableFixed table thead tr").css('height', $(".volumeView .summary .tableScroll table thead tr").height());
    }


    /**
     * Parse the the volume chart data returned from server and build the data
     * which can be consumed by the Google visualization column chart.
     *
     * @param volumeData the volume chart data return from server.
     * @param timeDimension the time dimension needed.
     * @since 1.2
     */
    function parseVolumeChartData(volumeData, timeDimension) {
        var data = new google.visualization.DataTable();
        var columnData = volumeData["columns"];
        var rowData = volumeData[timeDimension];

        $.each(columnData, function(index, item) {
            if (index == 0) {
                data.addColumn("string", item);
            } else {
                data.addColumn("number", item);
            }
        })
        data.addRows(rowData.length);
        $.each(rowData, function(index, item) {
            $.each(columnData, function(k, name) {
                data.setValue(index, k, item[name]);
            });
        });
        data.col = columnData.length - 1;
        data.row = rowData.length;
        return data;
    }

    function drawVolumeChart(data) {
        var minWidth = (data.row * 12 + 20) * data.col;    // control the min width for each row
        var chart = new google.visualization.ColumnChart(document.getElementById('viewChart'));
        var width = $(".volumeView .viewChartWrapper").width();
        if (width == minWidth || width < minWidth) {
            width = minWidth;
        }

        $("#viewChart").css({
            width:width + "px"
        })

        $("#viewChart iframe").remove();

        var option = {
            chartArea:{ left: 35, width: width - 35 },
            fontName: "Arial",
            fontSize: 11,
            gridlineColor: "#d4d5d6",
            width: width,
            height: 195,
            legend: "bottom",
            vAxis: { baselineColor: "#aaacae" }
        };
        chart.draw(data, option);

        $("#viewChart").parent().css({
            height: $("#viewChart").height() + 20 + "px"
        })
    }



    /**
     * Takes the summary data returned from server, and add into the summary table.
     *
     * @param summaryData the summary data returned from server.
     * @since 1.2
     */
    function addDataToVolumeSummary(summaryData) {
        // clear the table first
        $(".volumeView .summary .data, .volumeView .summary .total").remove();

        var table = $(".volumeView .tabsContent .tableScroll table");
        var col = $(".volumeView .tabsContent .tableScroll table colgroup");
        var thead = $(".volumeView .tabsContent .tableScroll table thead tr");
        var avgCompleted = $(".volumeView .tabsContent .tableScroll tbody tr:eq(0)");
        var avgFailed = $(".volumeView .tabsContent .tableScroll tbody tr:eq(1)");
        var totalCompleted = $(".volumeView .tabsContent .tableScroll tbody tr:eq(2)");
        var totalFailed = $(".volumeView .tabsContent .tableScroll tbody tr:eq(3)");
        var avgCompletedTotal = 0; var avgFailedTotal = 0; var totalCompletedTotal = 0; var totalFailedTotal = 0;

        $.each(summaryData, function(key, value) {

            col.append("<col width='100' class='data'/>");
            thead.append('<th class="data"><span class="full">' + shortNameMap[key][0] + '</span><span class="short">' + shortNameMap[key][1] + '</span></th>');

            avgCompleted.append("<td class='data'>" + (value["AvgCompleted"] == 0 ? 0 : value["AvgCompleted"].toFixed(1)) + "</td>");
            avgFailed.append("<td class='data'>" + (value["AvgFailed"] == 0 ? 0 : value["AvgFailed"].toFixed(1)) + "</td>");
            totalCompleted.append("<td class='data'>" + value["totalCompleted"] + "</td>");
            totalFailed.append("<td class='data'>" + value["totalFailed"] + "</td>");
            avgCompletedTotal += value["AvgCompleted"];
            avgFailedTotal += value["AvgFailed"];
            totalCompletedTotal += value["totalCompleted"];
            totalFailedTotal += value["totalFailed"];
        });

        col.append("<col class='total' width='90'/>");
        thead.append("<th class='total'>Total</th>");
        avgCompleted.append("<td class='data'>" + (avgCompletedTotal == 0 ? 0 : avgCompletedTotal.toFixed(1)) + "</td>");
        avgFailed.append("<td class='data'>" + (avgFailedTotal == 0 ? 0 : avgFailedTotal.toFixed(1)) + "</td>");
        totalCompleted.append("<td class='data'>" + totalCompletedTotal + "</td>");
        totalFailed.append("<td class='data'>" + totalFailedTotal + "</td>");
    }

    // the instance used to cache the volume chart
    // data returned from the server
    var chartDataCache;


    function checkVolumeViewRequestParameters(request) {
        var errors = [];

        if(request.indexOf("formData.contestStatus") == -1) {
            errors.push("The contest status is not chosen");
        } else if(request.indexOf("formData.billingAccountIds") == -1) {
            errors.push("The billing account is not chosen");
        } else if(request.indexOf("formData.customerIds") == -1) {
            errors.push("The customer is not chosen");
        } else if(request.indexOf("formData.projectCategoryIds") == -1) {
            errors.push("The project category is not chosen");
        } else if(request.indexOf("formData.startDate") == -1) {
            errors.push("The start date is not chosen");
        }  else if(request.indexOf("formData.endDate") == -1) {
            errors.push("The end date is not chosen");
        }

        if (errors.length > 0) {
            showErrors(errors);
            return false;
        }

        return true;
    }


    /**
     * Loads the volume view data by ajax and build the view.
     *
     * @param action the struts action to call by default is "dashboardEnterpriseVolumeView"
     * @since 1.2
     */
    function loadVolumeViewData(action) {

        if(action == undefined) {
            // not set, use the default one
            action = "dashboardEnterpriseVolumeView";
        }

        // get request
        var request = $("#volumeViewForm").serialize().replace(/volumeFormData/g, "formData").replace("&__multiselect_formData.projectCategoryIds=&", "&");

        if(!checkVolumeViewRequestParameters(request)) return;

        // show the ajax loading message
        $('#volumeZoomMessage').html('Loading...').css('color', 'red').css('font-weight', 'bold');

        if($("#volumeViewSummaryTable th").length <= 2) {
            // no data yet, hide the table when loading
            $("#volumeViewSummaryTable").hide();
        }

        $.ajax({
            type: 'POST',
            url: action,
            data: request,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                $('#volumeZoomMessage').html('');
                handleJsonResult(
                        jsonResult,
                        function(result){
                            var chartData = result.volumeChartData;
                            var summaryData = result.volumeSummaryData;

                            if (chartData == undefined || chartData == null) {
                                $('#volumeZoomMessage').html("Fail to load the volume view chart data");
                            }

                            if (summaryData == undefined || summaryData == null) {
                                $('#volumeZoomMessage').html("Fail to load the volume view summary data");
                            }

                            $("#volumeViewSummaryTable").show();

                            chartDataCache = chartData;
                            var startDateStr = result.startDate;
                            var endDateStr = result.endDate;
                            var summaryStart = new Date(startDateStr).toString("MMM dd,yyyy");
                            var summaryEnd = new Date(endDateStr).toString("MMM dd,yyyy");

                            // update the date labels in summary
                            $("#volumeStartDateLabel").html(summaryStart);
                            $("#volumeEndDateLabel").html(summaryEnd);

                            // update the date pickers
                            $("#startDateVolumeView").datePicker().val(startDateStr)
                                .trigger('change');
                            $("#endDateVolumeView").datePicker().val(endDateStr)
                                .trigger('change');

                            addDataToVolumeSummary(summaryData);
                            truncateVolumeViewSummary($(".volumeView .summary .tableScroll table"));
                            drawVolumeChart(parseVolumeChartData(chartData, $(".volumeView .timeDimension select").val()));

                        },
                        function(errorMessage) {
                            $('#volumeZoomMessage').html("Fail to load the volume view data");
                        });
            }
        });
    }



    // hide volume view by default
    $(".volumeView").hide();
    $(".volumeView .summary .tabsContent table tbody tr:nth-child(odd)").addClass("odd");

    // load different chart when the time dimension changed
    $(".volumeView .timeDimension select").change(function() {
        drawVolumeChart(parseVolumeChartData(chartDataCache, $(this).val()));
    });

    /**
     * Handles the apply button in the volume view.
     * @since 1.2
     */
    $(".volumeView .applyButton").click(function(){

        // validate the start date and end date first
        var d1 = new Date($('#startDateVolumeView').val());
        var d2 = new Date($('#endDateVolumeView').val());

        if (d1 > d2) {
            $('#volumeZoomMessage').html('Start date must not be after end date');
            return;
        }

        loadVolumeViewData();

        $("#volumeStartDateLabel").html(d1.toString("MMM dd,yyyy"));
        $("#volumeEndDateLabel").html(d2.toString("MMM dd,yyyy"));
    });


    /**
     * Handles the zoom links in the volume view.
     * @since 1.2
     */
    $('.volumeView .zoomLink').click(function() {
        var value = $(this).attr('href');
        $('#volumeViewZoomType').val(value);
        loadVolumeViewData("zoomEnterpriseDashboardVolumeView");
        return false;
    });

    $('a.fiterButton').css('background-position', 'bottom left');
    $('.filterArea,.container2,.tableResultFilter').hide();

    hideFilter = function(filter) {
        $(filter).parent().find('.filterArea').hide();
        $(filter).css('background-position', 'bottom left');
    };

    showFilter = function(filter) {
        $(filter).parent().find('.filterArea').show();
        $(filter).css('background-position', 'top left');
    }

     function sortDropDown(dropDownId) {
        // alert('sort ' + dropDownId);
        // get the select
        var $dd = $(dropDownId);
        if ($dd.length > 0) { // make sure we found the select we were looking for

            // save the selected value
            var selectedVal = $dd.val();

            // get the options and loop through them
            var $options = $('option', $dd);
            var arrVals = [];
            $options.each(function() {
                // push each option value and text into an array
                arrVals.push({
                    val: $(this).val(),
                    text: $(this).text()
                });
            });

            // sort the array by the value (change val to text to sort by text instead)
            arrVals.sort(function(a, b) {
                if(a.val == 0) {
                    return -1;
                }
                if (b.val == 0) {
                    return 1;
                }

                if (a.text > b.text) {
                    return 1;
                }
                else if (a.text == b.text) {
                    return 0;
                }
                else {
                    return -1;
                }
            });

            // loop through the sorted array and set the text/values to the options
            for (var i = 0, l = arrVals.length; i < l; i++) {
                $($options[i]).val(arrVals[i].val).text(arrVals[i].text);
            }

            // set the selected value back
            $dd.val(selectedVal);
        }
    }

    /**
     * Initializes the tooltip in the volume view summary table.
     * @since 1.2
     */
    $(".newTooltips").tooltip({
        tip:"#tooltipBox",
        position:"top left",
        offset:[-2,150],
        delay:0,
        onBeforeShow: function(){
            this.getTip().removeClass("greenToolTipBox");
            this.getTip().removeClass("orangeToolTipBox");
            if(this.getTrigger().hasClass("greenTips")){
                this.getTip().addClass("greenToolTipBox");
            }
            if(this.getTrigger().hasClass("orangeTips")){
                this.getTip().addClass("orangeToolTipBox");
            }
            this.getTip().find(".tooltipContent p").text(this.getTrigger().find(".tipC").text());
            this.getTip().find(".tooltipHeader h2").text(this.getTrigger().find(".tipT").text());
        }
    });

    if(isAdmin) {
        // insert "All Customers" to #volumeFormData\\.customerIds
        $("#volumeFormData\\.customerIds").append("<option value='0'>All Customers</option>");
        $("#volumeFormData\\.customerIds").val('0');

        loadOptionsByClientId(0, "volumeFormData");
    }


    sortDropDown("#formData\\.projectIds");
    sortDropDown("#volumeFormData\\.projectIds");
    sortDropDown("#formData\\.billingAccountIds");
    sortDropDown("#volumeFormData\\.billingAccountIds");
    sortDropDown("#volumeFormData\\.customerIds");

    function loadOptionsByClientId(clientId, formName) {

      $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForClientAJAX",
            data: {'formData.customerIds':clientId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                                function(result) {
                                    var billings = result.billings;
                                    var projects = result.projects;
                                    var $billing = $("#" + formName + "\\.billingAccountIds");
                                    var $project = $("#" + formName + "\\.projectIds");

                                    $billing.html("");
                                    $.each(billings, function(key, value){
                                        $billing.append($('<option></option>').val(key).html(value));
                                    });

                                    // append the default "select all"
                                    $billing.append($('<option></option>').val(0).html("All Billing Accounts"));
                                    $billing.val(0);

                                    $project.html("");
                                    $.each(projects, function(key, value){
                                        $project.append($('<option></option>').val(key).html(value));
                                    });

                                     // append the default "select all"
                                    $project.append($('<option></option>').val(0).html("All Projects"));
                                    $project.val(0);

                                    sortDropDown("#" + formName + "\\.projectIds");
                                    sortDropDown("#" + formName + "\\.billingAccountIds");

                                },
                                function(errorMessage) {
                                    $('#validationErrors').html(errorMessage);
                                });
            }
        });
    }

    var ieWidth = $(window).width();

    $(window).resize(function(){

        if(!$(".volumeView").is(":hidden")){

            var resize = false;

            if(!$.browser.msie) {
                resize = true;
            } else {
                var ieNewWidth = $(window).width();

                if(ieWidth != ieNewWidth) {
                    resize = true;
                    ieWidth = ieNewWidth;
                }
            }


            if(resize) {
                drawVolumeChart(parseVolumeChartData(chartDataCache, $(".volumeView .timeDimension select").val()));
                truncateVolumeViewSummary($(".volumeView .summary .tableScroll table"));
            }

        }
    })


    $("#formData\\.customerIds").change(function() {

        var customerId = $(this).val();

        loadOptionsByClientId(customerId, "formData");
    });

    $("#volumeFormData\\.customerIds").change(function() {

        var customerId = $(this).val();

        loadOptionsByClientId(customerId, "volumeFormData");
    });


    $("#formData\\.billingAccountIds").change(function() {

        var billingId = $(this).val();

        if(billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#formData\\.customerIds").val();

            loadOptionsByClientId(customerId, "formData");

            return;
        }

        $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForBillingAJAX",
            data: {'formData.billingAccountIds':billingId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                                function(result) {
                                    var projects = result.projects;
                                    var $project = $("#formData\\.projectIds");

                                    $project.html("");
                                    $.each(projects, function(key, value){
                                        $project.append($('<option></option>').val(key).html(value));
                                    });

                                    // append the default "select all"
                                    $project.append($('<option></option>').val(0).html("All Projects"));
                                    $project.val(0);
                                    sortDropDown("#formData\\.projectIds");

                                },
                                function(errorMessage) {
                                    $('#validationErrors').html(errorMessage);
                                });
            }
        });

    });
    
    $("#volumeFormData\\.billingAccountIds").change(function() {

        var billingId = $(this).val();

        if(billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#volumeFormData\\.customerIds").val();

            loadOptionsByClientId(customerId, "volumeFormData");

            return;
        }

        $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForBillingAJAX",
            data: {'formData.billingAccountIds':billingId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                                function(result) {
                                    var projects = result.projects;
                                    var $project = $("#volumeFormData\\.projectIds");

                                    $project.html("");
                                    $.each(projects, function(key, value){
                                        $project.append($('<option></option>').val(key).html(value));
                                    });

                                    // append the default "select all"
                                    $project.append($('<option></option>').val(0).html("All Projects"));
                                    $project.val(0);
                                    sortDropDown("#volumeFormData\\.projectIds");

                                },
                                function(errorMessage) {
                                    $('#validationErrors').html(errorMessage);
                                });
            }
        });

    });



});
