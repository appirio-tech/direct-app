/**
 * The JS script is for dashboard Jira issues report.
 *
 * AUTHOR: TCSASSEMBLER
 * VERSION: 1.0 (Module Assembly - JIRA issues loading update and report creation)
 */
function getJiraIssuesReportAsExcel() {
    $('#formDataExcel').val("true");
    document.dashboardJiraIssuesReportForm.submit();
}

$(document).ready(function() {

    // initialize the multiple checkboxes selection
    $('select.multiselect').each(function() {
        var obj = this;
        var name = $(obj).attr("name");
        var box = $("<div/>").addClass("multiSelectBox").css("overflow-x", "hidden");
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

        $("#startDateJiraIssuesReport").datePicker().val($("#startDateJiraIssuesReport").val()).trigger('change');

        //console.log($("#startDateJiraIssuesReport").val());

    });

    //Multi Select Area width
    function multiSelectAreaSet(){
        var width = $(window).width();
        if($('.filterContainer').length>0){
            if(width < 1380){
                $('.filterContainer').removeClass('filterContainer1400');
            }else{
                $('.filterContainer').addClass('filterContainer1400');
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

    $('#jiraIssuesReportsSection .pipelineStats .tableTitle .expand').click(function(){
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

    $("#jiraIssuesReportSubmit").click(function() {
        $('#formDataExcel').val("false");

        var checked = false;

        $(".multiSelectBox input").each(function(){
            if($(this).is(":checked")) {
                checked = true;
            }
        })

        if(!checked) {
            showErrors("Please choose at least one status");
            return;
        }

        $("#dashboardJiraIssuesReportForm").submit();
        modalPreloader();
    });

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
                            $billing.append($('<option></option>').val(key).text(value));
                        });

                        // append the default "select all"
                        $billing.append($('<option></option>').val(0).text("All Billing Accounts"));
                        $billing.val(0);

                        $project.html("");
                        $.each(projects, function(key, value) {
                            $project.append($('<option></option>').val(key).text(value));
                        });

                        // append the default "select all"
                        $project.append($('<option></option>').val(0).text("All Projects"));
                        $project.val(0);

                        sortDropDown("#formData\\.projectId");
                        sortDropDown("#formData\\.billingAccountId");

                    },
                    function(errorMessage) {
                        $('#validationErrors').text(errorMessage);
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
                            $project.append($('<option></option>').val(key).text(value));
                        });

                        // append the default "select all"
                        $project.append($('<option></option>').val(0).text("All Projects"));
                        $project.val(0);
                        sortDropDown("#formData\\.projectId");
                    },
                    function(errorMessage) {
                        $('#validationErrors').html(errorMessage);
                    });
            }
        });
    });
});
