/**
 * AUTHOR: Blues, flexme
 * VERSION: 1.2 (TopCoder Cockpit - Cost Report Assembly)
 *
 * Version 1.1 change: add toggle trigger for billing cost report.
 *
 * Version 1.2 (TC Cockpit Cost Report Update Cost Breakdown Assembly) change: add support for the cost breakdown
 *  report view.
 * 
 * Submits the cost report form and trigger cost report excel download.
 */
function getCostReportAsExcel(showBreakdown) {
    $('#formDataExcel').val("true");
    $('#formDataShowBreakdown').val(showBreakdown);
    document.dashboardCostReportForm.submit();
}

$(document).ready(function() {
    // initialize the multiple selection
    $('.multiselect').multiSelect();

    /** hide the filter panel **/
    hideFilter = function() {
        $('.filterArea').hide();
        $('a.fiterButton').css('background-position', 'bottom left');
    };

    /** show the filter panel **/
    showFilter = function() {
        $('.filterArea').show();
        $('a.fiterButton').css('background-position', 'top left');
    }

    // toggle the selection change for aggregation cost reports
    $('#aggregationCostReportType').change(function() {
        $('.scData').hide();
        $('.' + $(this).val() + 'AggregationCostReport').show();
    });

    /* Toggle Filter collapse/expand */
    $("a.fiterButton").click(function() {
        $(".filterContest .contestType a span").css({"width": "165px"});
        $(".filterContest .contestType div").css({"width": "188px"});
        if ($('.filterArea').css('display') == 'none') {
            showFilter();
        }
        else {
            hideFilter();
        }
    });

    /* Apply button action */
    $('a.applyButton').click(function(event) {
        $('#formDataExcel').val("false");
        $('#dashboardCostReportForm').submit();
        modalPreloader();
    });

    $('.dateRange a').click(function() {
        return false;
    });

    // show the filter panel by default
    $('a.fiterButton').css('background-position', 'top left');
    $('.filterArea,.container2,.tableResultFilter').show();

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
                if (a.val == 0) {
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

    // sort the project names
    sortDropDown("#formData\\.projectIds");
    // sort the billing accounts names
    sortDropDown("#formData\\.billingAccountIds")

    function loadOptionsByClientId(clientId) {

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
                            var $billing = $("#formData\\.billingAccountIds");
                            var $project = $("#formData\\.projectIds");

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

                            sortDropDown("#formData\\.projectIds");
                            sortDropDown("#formData\\.billingAccountIds");

                        },
                        function(errorMessage) {
                            $('#validationErrors').html(errorMessage);
                        });
            }
        });
    }


    $("#formData\\.customerIds").change(function() {

        var customerId = $(this).val();

        loadOptionsByClientId(customerId);
    });

    $("#formData\\.billingAccountIds").change(function() {

        var billingId = $(this).val();

        if (billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#formData\\.customerIds").val();

            loadOptionsByClientId(customerId);

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
                            $.each(projects, function(key, value) {
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
        },
        onClose : function() {
            $("#costDetailsViewType").val("default");
        }
     });
    contestDViewApi =  $("#contestDViewMock").data("overlay");
    $("#costDetailsViewType").val("default");
    // a flag indicates whether the cost break down data has been loaded
    var breakdownLoaded = false;

    /**
     * Loads the cost breakdown data for the specified projects using AJAX.
     * 
     * @param projectIds the id of the specified projects
     */
    function loadBreakdownData(projectIds) {
        var data = {formData:{projectIds:projectIds}};
        modalPreloader();
        $.ajax({
            type: 'POST',
            url: "dashboardGetCostBreakDownAJAX",
            data: data,
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function(result){
                        modalClose();
                        contestDViewApi.load();
                        breakdownLoaded = true;
                        var breakDownMap = [];
                        for (var i = 0; i < result.length; i++) {
                            breakDownMap[result[i].id] = result[i];
                        }
                        var totalCompleted = 0;
                        var totalCost = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                        var reg1 = /\$/g,reg2 = /\,/g;
                        $("#breakdownBody table tbody tr").each(function() {
                            var projectId = parseInt($(this).attr("rel"));
                            var breakdown = breakDownMap[projectId];
                            var fullfillment = 0;
                            if (!breakdown) {
                                breakdown = ["0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"];
                            } else {
                                if (breakdown.fullfillment > 0) {
                                    fullfillment = 1;
                                }
                                breakdown = [breakdown.prizes, breakdown.specReview, breakdown.review, breakdown.reliability,
                                    breakdown.digitalRun, breakdown.copilot, breakdown.build, breakdown.bugs, breakdown.misc];
                            }
                            totalCompleted += fullfillment;
                            $(this).children("td:not(:last)").each(function(i) {
                                if (i >= 10) {
                                    $(this).html("$" + breakdown[i - 10]);
                                }
                            });
                            if (fullfillment > 0) {
                                $(this).children("td").each(function(i) {
                                    if (i >= 7) {
                                        totalCost[i - 7] += parseFloat($(this).text().replace(reg1,"").replace(reg2,""));
                                    }
                                });
                            }
                        });
                        totalCompleted = totalCompleted == 0 ? 1 : totalCompleted;
                        $("#breakdownBody table tfoot tr td").each(function(i) {
                            if (i >= 1) {
                                $(this).html("$" + new Number(totalCost[i - 1] / totalCompleted).toFixed(2));
                            }
                        });

                        // bind sort event
                        var myTextExtraction = function(node) {
                            return $.trim($(node).text());
                        }
                        $("#breakdownBody table").tablesorter({
                            textExtraction: myTextExtraction,
                            headers :{
                                    6: {
                                        sorter: 'shortDate'
                                    }
                                }
                            });
                        $("#breakdownBody table th").each(function(i) {
                            var sortType = 0;
                            $(this).unbind('click');
                            $(this).click(function() {
                                var sorting = [[i, (sortType++)%2]];
                                $("#breakdownBody table").trigger("sorton", [sorting]);
                                var rows = $("#breakdownBody table tbody tr");
                                rows.removeClass("alt");
                                $("#breakdownBody table tbody tr:odd").addClass("alt");
                            });
                        });
                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    }

    // bind change event to the view type dorpdown
    $("#costDetailsViewType").change(function() {
        if ($(this).val() == "breakdown") {
            if (!breakdownLoaded) {
                // load the cost breakdown data
                var tbody = $("#breakdownBody table tbody");
                tbody.children("tr").remove();
                $("#breakdownBody table tfoot").remove();
                var oldval = $("#dataTableLength").val();
                $("#dataTableLength").val(-1);
                $("#dataTableLength").trigger("change");
                var trs = $("#costDetails tbody tr");
                var projectIds = [];
                trs.each(function(i) {
                    var projectId = parseInt($(this).attr("id").substr(8));
                    if (!isNaN(projectId)) {
                        projectIds.push(projectId);
                        var classes = "pipelineDetailsRow";
                        if (i % 2 == 1) {
                            classes = "pipelineDetailsRow alt";
                        }
                        var tr = $("<tr class='" + classes + "' rel='" + projectId + "'></tr>");
                        $(this).children("td:not(:last)").each(function() {
                            tr.append("<td>" + $(this).text() + "</td>");
                        });
                        for (var i = 0; i < 9; i++)
                        {
                            tr.append("<td></td>");
                        }
                        tr.append("<td>" + $(this).children("td:last").text() + "</td>");
                        tbody.append(tr);
                    }
                });
                if (projectIds.length == 0) {
                    tbody.append("<tr><td class='dataTables_empty' valign='top' colspan='20'>No matching records found</td></tr>");
                    contestDViewApi.load();
                }
                $("#breakdown_costDetails_info").html("Total " + projectIds.length + " entries");
                $("#dataTableLength").val(oldval);
                $("#dataTableLength").trigger("change");

                if (projectIds.length > 0) {
                    $("#breakdownBody table").append("<tfoot><tr><td colspan='7' style='text-align:left;padding:5px;' class='alignLeft'>Average</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></tfoot>");
                    loadBreakdownData(projectIds);
                }
            } else {
                contestDViewApi.load();
            }
        }
    });

    var resizePopupId = -1;
    /**
     * Dynamic resize the popup windows to adapt for the new window size.
     */
    function resizePopup() {
        resizePopupId = -1;
        if (contestDViewApi && contestDViewApi.isOpened()) {
            contestDViewApi.close();
            setTimeout(function() {contestDViewApi.load();}, 300);
        }
    }
    // bind resize event to window so that we can dynamic resize the popup windows.
    $(window).resize(function(){
        if (resizePopupId != -1) {
            clearTimeout(resizePopupId);
        }
        resizePopupId = setTimeout(resizePopup, 100);
    });
});
