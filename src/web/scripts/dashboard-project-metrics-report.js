/**
 * The JS script is for Project Metrics report.
 * 
 * AUTHOR: TCSASSEMBER
 * VERSION: 1.0 (TC Cockpit Project Metrics Report  )
 */
function getMetricsReportAsExcel() {
    $('#formDataExcel').val("true");
    document.dashboardProjectMetricsReportForm.submit();
}

$(document).ready(function() {

    // initialize the multiple checkboxes selection
    $('select.multiselect').each(function() {
        var obj = this;
        var name = $(obj).attr("name");
        var box = $("<div/>").addClass("multiSelectBox1").css("overflow-x", "hidden");
        var selectAllRow = $("<div/>").addClass("multiOptionRow");
        selectAllRow.html("<input type='checkbox'><label title='Select All'>Select All</label>");
        selectAllRow.appendTo(box);
        for (var i = 0; i < obj.options.length; i++) {
            var option = obj.options[i];
            var row = $("<div/>").addClass("multiOptionRow");
            var checkbox = $("<input type='checkbox' />");
            checkbox.attr("name", name);
            checkbox.attr("value", option.value);
            if (option.selected) {
                checkbox.attr("checked", true);
            }
            checkbox.appendTo(row);
            $("<label title='" + option.text + "'>" + option.text + "</label>").appendTo(row);
            row.appendTo(box);
        }
        $(box).insertAfter($(obj));
        // remove the original "select" element
        $(obj).remove();

        // update the status of checkboxes when one of the checkbox is clicked 
        function updateCheckboxs(checkbox, multiBox) {
            if (checkbox) {
                var checked = checkbox.is(":checked");
                if (!checkbox.attr("name")) {
                    $("input", multiBox).each(function() {
                        $(this).attr("checked", checked);
                    });
                }
            }
            
            var allchecked = true;
            $("input:not(:first)", multiBox).each(function() {
                if (!$(this).is(":checked")) {
                    allchecked = false;
                }
            });
            $("input:first", multiBox).attr("checked", allchecked);
            $("input", multiBox).each(function() {
                if (!$(this).is(":checked")) {
                    $(this).parent().removeClass("multiOptionRowChecked");
                } else {
                    $(this).parent().addClass("multiOptionRowChecked");
                }
            });
        }

        $("input", box).click(function() {
            updateCheckboxs($(this), box);
        });
        updateCheckboxs(null, box);
    });

    $('.filterTitle .expanded').click(function(){
        $(this).blur();
        var filterTitle = $(this).closest('.filterTitle');
        if(!$(this).hasClass('collapsed')){
            filterTitle.addClass('filterTitleCollapsed');
            $(this).addClass('collapsed');
            $('.filterContainer').hide();
        }else{
            filterTitle.removeClass('filterTitleCollapsed');
            $(this).removeClass('collapsed');
            $('.filterContainer').show();
        }
    });

    //Multi Select Area width
    function multiSelectAreaSet(){
        var width = $(window).width();
        if($('.filterContainer').length>0){
            if(width < 1380){
                $('.filterContainer').removeClass('filterContainer1400');
            }else{
                $('.filterContainer').addClass('filterContainer1400');
            }
            $('.rightFilterContent').width($('.filterContainer').width()-$('.leftFilterContent').width());
        }
    }
    
    //resize Multi Select Area width
    $(window).resize(function(){
        if($('.filterContainer').length>0){
            multiSelectAreaSet();
        }
    }) ;
    multiSelectAreaSet();

    $('#participationMetricsReportsSection .pipelineStats .tableTitle .expand').click(function(){
        $(this).blur();
        $me = $(this);
        if(!$me.hasClass('collapse')){
            $me.addClass('collapse');
            $meTable = $me.closest('table');
            $meTable.find('.subTheadRow').hide();
            $meTable.find('.viewType div').hide();
            $meTable.find('tbody').hide();
        }else{
            $me.removeClass('collapse');
            $meTable = $me.closest('table');
            $meTable.find('.subTheadRow').show();
            $meTable.find('.viewType div').show();
            $meTable.find('tbody').show();
        }
        return false;
    });

    $("#reportMetricsReportSubmit").click(function() {
        $('#formDataExcel').val("false");
        $("#dashboardProjectMetricsReportForm").submit();
        modalPreloader();
    });

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
    sortDropDown("#formData\\.projectId");
    // sort the billing accounts names
    sortDropDown("#formData\\.billingAccountId");

    // Load the billing projects and projects options when customer option is changed
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
                            var $billing = $("#formData\\.billingAccountId");
                            var $project = $("#formData\\.projectId");

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

                            sortDropDown("#formData\\.projectId");
                            sortDropDown("#formData\\.billingAccountId");

                        },
                        function(errorMessage) {
                            $('#validationErrors').html(errorMessage);
                        });
            }
        });
    }

    $("#formData\\.customerId").change(function() {
        var customerId = $(this).val();
        loadOptionsByClientId(customerId);
    });

    // Load the projects options when the billing project option is changed
    $("#formData\\.billingAccountId").change(function() {
        var billingId = $(this).val();

        if (billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#formData\\.customerId").val();
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
                            var $project = $("#formData\\.projectId");

                            $project.html("");
                            $.each(projects, function(key, value) {
                                $project.append($('<option></option>').val(key).html(value));
                            });

                            // append the default "select all"
                            $project.append($('<option></option>').val(0).html("All Projects"));
                            $project.val(0);
                            sortDropDown("#formData\\.projectId");
                        },
                        function(errorMessage) {
                            $('#validationErrors').html(errorMessage);
                        });
            }
        });
    });

    // Update the Aggregation Type
    $("#aggregationParticipationReportType").change(function() {
        var aggreType = $(this).val();
        var typeNamesMap = {"project" : "Project",
                            "billing" : "Billing Account",
                            "contestType" : "Challenge Type",
                            "status" : "Challenge Status"};
        $("tr.projectAggregationCostReport th:first").html(typeNamesMap[aggreType]);

        $("#participationMetricsReportAggregationArea tbody tr").hide();
        $("tr." + aggreType + "AggregationReport").show();
    });
});
