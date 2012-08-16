/**
 * AUTHOR: Blues, GreatKevin
 * VERSION: 1.4 (Release Assembly - TC Direct Cockpit Release Six)
 *
 * Version 1.0 (TC Cockpit Permission and Report Update One) change log:
 * - Change parameter name from projectIds to projectId.
 * - Change parameter name from billingAccountIds to billingAccount.
 * - Change parameter name from customerIds to customerId.
 * 
 * Version 1.1 (TC Accounting Tracking Invoiced Payments) change notes: Add logic to update the invoice record.
 *
 * Version 1.2 (TC Accounting Tracking Invoiced Payments Part 2) change notes: Add logic to create/update the invoice.
 *
 * Version 1.3 (Add ajax indicator for dropdown change and add group by and group values filter)
 *
 * Version 1.4 (Release Assembly - TC Direct Cockpit Release Six) changes:
 * - Add the handler for invoice number dropdown
 * - Update billing account dropdown handler to populate invoice numbers dropdown
 *
 * Submits the billing cost report form and trigger cost report excel download.
 */
function getBillingCostReportAsExcel() {
    $('#formDataExcel').val("true");
    document.dashboardBillingCostReportForm.submit();
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
        $('#dashboardBillingCostReportForm').submit();
        modalPreloader();
    });

    $('.dateRange a').click(function() {
        return false;
    });

    // show the filter panel by default
    $('a.fiterButton').css('background-position', 'top left');
    $('.filterArea,.container2,.tableResultFilter').show();

    // sort the project names
    sortDropDown("#formData\\.projectId");
    // sort the billing accounts names
    sortDropDown("#formData\\.billingAccountId")

    function loadOptionsByClientId(clientId) {

        showIndicator($("#formData\\.billingAccountId"));
        showIndicator($("#formData\\.projectId"));

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

                            $("#formData\\.invoiceNumberSelection").empty();

                            hideIndicator($("#formData\\.billingAccountId"));
                            hideIndicator($("#formData\\.projectId"));

                        },
                        function(errorMessage) {
                            $('#validationErrors').html(errorMessage);
                        });
            }
        });
    }


    $("#formData\\.customerId").change(function() {

        var customerId = $(this).val();

        if (customerId <= 0) {
            $("#formDatagroup option").each(function () {
                if ($(this).val() > 0) {
                    $(this).remove();
                }
            });

            $("#formDatagroup").attr('disabled', true);
            $("#formDatagroupValue").find(".multiSelectBox").empty();

        } else {
            $("#formDatagroupValue").find(".multiSelectBox").empty();
            loadGroupByOptions($("#formDatagroup"), customerId, function (result) {
                $("#formDatagroup").attr('disabled', false);
                $("#formDatagroup").val(-1);
                $("#formDatagroupValue").find(".multiSelectBox").empty();
            });
        }

        loadOptionsByClientId(customerId);
    });

    $("#formData\\.billingAccountId").change(function() {

        var billingId = $(this).val();

        if (billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#formData\\.customerId").val();

            loadOptionsByClientId(customerId);

            $("#formData\\.invoiceNumberSelection option").remove();

            return;
        }

        showIndicator($("#formData\\.projectId"));

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
                            hideIndicator($("#formData\\.projectId"));

                            if ($("#formDatagroup").val() > 0) {
                                $("#formData\\.projectId").attr('disabled', true);
                            }

                            $("#formDatagroup").attr('disabled', false);
                        },
                        function(errorMessage) {
                            $('#validationErrors').html(errorMessage);
                        });
            }
        });

        showIndicator($("#formData\\.invoiceNumberSelection"));

        $.ajax({
            type: 'POST',
            url:  "getInvoiceNumbersForBillingAccount",
            data: {'billingAccountId':billingId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        var invoiceNumbers = result;
                        var $invoice = $("#formData\\.invoiceNumberSelection");

                        $invoice.html("");
                        $invoice.append($('<option></option>').val("").html(""));
                        $.each(invoiceNumbers, function(index, value) {
                            $invoice.append($('<option></option>').val(value).html(value));
                        });

                        $invoice.val("");
                        hideIndicator($("#formData\\.invoiceNumberSelection"));
                    },
                    function(errorMessage) {
                        $('#validationErrors').html(errorMessage);
                    });
            }
        });

    });

    $("#formData\\.invoiceNumberSelection").change(function() {

        var invoiceNumber = $(this).val();
        var billingId = $("#formData\\.billingAccountId").val();

        if (!invoiceNumber || invoiceNumber == "") {
            // no invoice number is selected, use billing account
            $("#formData\\.billingAccountId").change();
            return;
        }

        showIndicator($("#formData\\.projectId"));

        $.ajax({
            type: 'POST',
            url:  "getProjectsForInvoiceNumberAndBillingAccount",
            data: {'billingAccountId':billingId, 'invoiceNumber':invoiceNumber},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        var projects = result;
                        var $project = $("#formData\\.projectId");

                        $project.html("");
                        $.each(projects, function(key, value) {
                            $project.append($('<option></option>').val(key).html(value));
                        });

                        // append the default "select all"
                        $project.append($('<option></option>').val(0).html("All Projects"));
                        $project.val(0);
                        sortDropDown("#formData\\.projectId");
                        hideIndicator($("#formData\\.projectId"));

                        if ($("#formDatagroup").val() > 0) {
                            $("#formData\\.projectId").attr('disabled', true);
                        }

                        $("#formDatagroup").attr('disabled', false);
                    },
                    function(errorMessage) {
                        $('#validationErrors').html(errorMessage);
                    });
            }
        });
    });

    $("#formData\\.projectId").change(function () {
        var id = $(this).val();
        if (id > 0) {
            // disable the
            $("#formDatagroup").attr('disabled', true);
            $("#formDatagroup").val(-1);
            $("#formDatagroupValue").find(".multiSelectBox").empty();
        } else {
            $("#formDatagroup").attr('disabled', false);
        }
    });

    $("#formDatagroup").change(function () {
        var groupById = $(this).val();

        if (groupById < 0) {
            $("#formDatagroupValue").find(".multiSelectBox").empty();
            $("#formDatagroupValue").find(".reportWarningMessage").hide();
            $("#formData\\.projectId").attr('disabled', false);
            $("#formData\\.projectId").val(0);
        } else {
            $("#formData\\.projectId").attr('disabled', true);
            $("#formData\\.projectId").val(0);
            loadGroupValuesForGroup($("#formDatagroupValue").find(".multiSelectBox"), groupById, function (result) {
            });
        }
    });

    // after loading, check the dropdown of project and group by

    if ($("#formDatagroup").val() > 0) {
        // disable project
        $("#formData\\.projectId").attr('disabled', true);
    } else if ($("#formData\\.projectId").val() > 0) {
        $("#formDatagroup").attr('disabled', true);
    }

    if ($("#formData\\.customerId").val() <= 0) {
        $("#formDatagroup").attr('disabled', true);
    }
    
    var contestIds = [];
    var paymentIds = [];
    var invoiceTypeNames = [];
    var invoiceAmounts = [];
    var processeds = [];
    var toUpdate = false;
    var updateSingle = false;
    var updateInvoiceId;
    var updateInvoiceRecordId;
    var updateRowChkbox;
    $("#invoiceNumber").keyup(function() {
        if ($.trim($(this).val()).length > 0) {
            $(".errorInfo", $(this).parent()).hide();
        }
    });


    
    /**
     * Format the date string from MM/dd/yy to yy-MM-dd
     * @param dateStr the date string to format
     * @return {String} the formatted date string
     */
    var formatDate = function(dateStr) {
        var date = new Date(dateStr);
        var paddingZero = function(number) {
            number = number + "";
            if (number.length == 1) return "0" + number;
            return number;
        };
        return date.getFullYear() + "-" + paddingZero(date.getMonth() + 1) + "-" + paddingZero(date.getDate());
    };
    
    /**
     * Update all the invoice record rows in the table which belongs to the same invoice.
     *
     * @param invoiceId the id of invoice to be updated.
     * @param invoiceNumber the new invoice number.
     * @param invoiceDate the new invoice date.
     */
    var updateExistsRows = function(invoiceId, invoiceNumber, invoiceDate) {
        var fd = formatDate(invoiceDate);
        $($.billingCostReportDataTable.fnGetNodes()).each(function() {
            var chkbox = $("input[name='invoiceRecordProcessed']", $(this));
            if (chkbox.attr("invoiceid") == invoiceId) {
                chkbox.attr("invoicenumber", invoiceNumber);
                chkbox.attr("invoicedate", invoiceDate);
                $("td.invoiceNumber", $(this)).html('<a href="#" class="updInvoiceDate">' + invoiceNumber + '</a>');
                $("td.invoiceDate", $(this)).html('<a href="#" class="updInvoiceDate">' + fd + '</a>');

                var data = $.billingCostReportDataTable.fnGetData(this);
                data[14] = '<a href="#" class="updInvoiceDate">' + invoiceNumber + '</a>';
                data[15] = '<a href="#" class="updInvoiceDate">' + fd + '</a>';
            }
        });
    };
    
    // The click handler of the SAVE button in the invoice popup
    $("#processInvoiceRecordModal .updateInvoice").click(function() {
        var invoiceNumber = $.trim($("#invoiceNumber").val());
        var invoiceDate = $("#invoiceDate").val();
        if (invoiceNumber.length == 0) {
            $(".errorInfo", $("#invoiceNumber").parent()).show();
            return;
        }
        if (toUpdate) {
            // update the existing invoice
            updateInvoice(updateInvoiceId, invoiceNumber, invoiceDate, updateSingle, updateInvoiceRecordId, true, function(result) {
                if (result.invoiceNumberExists) {
                    if (!updateSingle) {
                        // the new invoice number already exists, show error
                        $("#demoModal li").css('padding-top', '10px');
                        displayClientSideError("#demoModal", "Errors", ["The invoice number already exists"], false, function() {
                            closeModal();
                            modalLoad("#processInvoiceRecordModal");
                        });
                    } else {
                        // the invoice number already exists, display a warning popup first
                        modalLoad("#invoiceNumberDuplicatedModal");
                        setTimeout(function(){modalLoad("#invoiceNumberDuplicatedModal");}, 100);
                        $("#invoiceNumberDuplicatedModal .updateInvoice").unbind("click").click(function() {
                            updateInvoice(updateInvoiceId, invoiceNumber, invoiceDate, updateSingle, updateInvoiceRecordId, false, function(result) {
                                updateRowChkbox.attr("invoiceid", result.invoiceId);
                                updateExistsRows(result.invoiceId, invoiceNumber, invoiceDate);
                            }, function() {
                            });
                        });
                        $("#invoiceNumberDuplicatedModal .closeModal").unbind("click").click(function(event) {
                            event.stopPropagation();
                            modalAllClose();
                            modalLoad("#processInvoiceRecordModal");
                            $("#invoiceNumberDuplicatedModal .closeModal").unbind("click").click(function() {
                            });
                        });
                    }
                } else {
                    // ok
                    updateRowChkbox.attr("invoiceid", result.invoiceId);
                    updateExistsRows(result.invoiceId, invoiceNumber, invoiceDate);
                }
            }, function() {
            
            });
            return;
        }
        
        // create a new invoice for the selected item
        if (contestIds.length == 0) return;
        
        // the function to update the table rows when the invoice has been created successfully
        var updateFunc = function(result) {
            var invoiceId = result.invoiceId;
            updateExistsRows(invoiceId, invoiceNumber, invoiceDate);
            
            var chs = $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:checked:not(:disabled)");
            var ths = $("#billingCostReportSection .paginatedDataTable thead th").length - 1;
            
            var fd = formatDate(invoiceDate);
            chs.each(function() {
                $("td.invoiceAmount", $(this).parent().parent()).html("$" + parseFloat($(this).attr("invoiceamount")).formatMoney(2));
                if (ths != 19) {
                    $("td.invoiceDate", $(this).parent().parent()).html(fd);
                    $("td.invoiceNumber", $(this).parent().parent()).html(invoiceNumber);
                } else {
                    $("td.invoiceDate", $(this).parent().parent()).html('<a href="#" class="updInvoiceDate">' + fd + '</a>');
                    $("td.invoiceNumber", $(this).parent().parent()).html('<a href="#" class="updInvoiceDate">' + invoiceNumber + '</a>');
                }
                $(this).attr("invoiceid", invoiceId);
                $(this).attr("invoicenumber", invoiceNumber);
                $(this).attr("invoicedate", invoiceDate);
                
                var data = $.billingCostReportDataTable.fnGetData($(this).parent().parent()[0]);
                data[14] = $("td.invoiceNumber", $(this).parent().parent()).html();
                data[15] = $("td.invoiceNumber", $(this).parent().parent()).html();
            });
            chs.attr("disabled", "disabled");
            $("#billingCostReportSection .processBtn").attr("disabled", "disabled");
        };
        
        updateInvoiceRecords(contestIds, paymentIds, invoiceTypeNames, invoiceAmounts, processeds, invoiceNumber, invoiceDate, true, function(result) {
            if (result.invoiceNumberExists) {
            	// the invoice number already exists, display a warning popup first
                modalLoad("#invoiceNumberDuplicatedModal");
                setTimeout(function(){modalLoad("#invoiceNumberDuplicatedModal");}, 100);
                $("#invoiceNumberDuplicatedModal .updateInvoice").unbind("click").click(function() {
                    updateInvoiceRecords(contestIds, paymentIds, invoiceTypeNames, invoiceAmounts, processeds, invoiceNumber, invoiceDate, false, function(result) {
                        updateFunc(result);
                    }, function() {
                    
                    });
                });
            } else {
                updateFunc(result);
            }
        }, function() {
            
        });
    });
    
    // the click handler for the link of invoice number and invoice date
    $(".updInvoiceDate").live("click", function() {
        // update the invoice
        toUpdate = true;
        updateSingle = false;
        $("#processInvoiceRecordModal .title").html("Update Invoice Record");
        var chkbox = $("input[name='invoiceRecordProcessed']", $(this).parent().parent());
        $("#invoiceNumber").val(chkbox.attr("invoicenumber"));
        $("#invoiceDate").val(chkbox.attr("invoicedate")).trigger('change');
        $(".errorInfo", $("#invoiceNumber").parent()).hide();
        // the invoice id we need to update
        updateInvoiceId = chkbox.attr("invoiceid");
        updateRowChkbox = chkbox;
        modalLoad("#processInvoiceRecordModal");
        return false;
    });
    // the click handler for the link of invoice amount and invoice date
    $(".updInvoiceAmount").live("click", function() {
        // update the invoice
        toUpdate = true;
        updateSingle = true;
        $("#processInvoiceRecordModal .title").html("Update Single Invoice Record");
        var chkbox = $("input[name='invoiceRecordProcessed']", $(this).parent().parent());
        $("#invoiceNumber").val(chkbox.attr("invoicenumber"));
        $("#invoiceDate").val(chkbox.attr("invoicedate")).trigger('change');
        $(".errorInfo", $("#invoiceNumber").parent()).hide();
        // the invoice id we need to update
        updateInvoiceId = chkbox.attr("invoiceid");
        updateInvoiceRecordId = chkbox.attr("invoicerecordid");
        updateRowChkbox = chkbox;
        modalLoad("#processInvoiceRecordModal");
        return false;
    });
    
    var scrollDPTimeout = -1;
    $(document).scroll(function(){
        if ($("#processInvoiceRecordModal").is(":visible") && $("#dp-popup").is(":visible")) {
            clearTimeout(scrollDPTimeout);
            scrollDPTimeout = setTimeout(function() {
                $("a", $("#invoiceDate").parent()).click();
            }, 100);
        }
    });
    
    // the click handler for the checkbox in the process column
    $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']").click(function() {
        var chs = $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:checked:not(:disabled)");
        if (chs.length == 0) {
            $("#billingCostReportSection .processBtn").attr("disabled", "disabled");
        } else {
            $("#billingCostReportSection .paginatedDataTable .processBtn").attr("disabled", "");
        }
        
        if (chs.length == $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:not(:disabled)").length) {
            $("#checkAllInvoice").attr("checked", "checked");
        } else {
            $("#checkAllInvoice").removeAttr("checked");
        }
    });
    // the click handler for the "Invoice" button.
    $(".processBtn").click(function(event) {
        event.stopPropagation();
        contestIds = [];
        paymentIds = [];
        invoiceTypeNames = [];
        invoiceAmounts = [];
        processeds = [];
        $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']").each(function() {
            if (!$(this).is(":disabled") && $(this).is(":checked")) {
                contestIds.push($(this).attr("contestid"));
                paymentIds.push($(this).attr("paymentid"));
                invoiceTypeNames.push($(this).attr("invoicetype"));
                invoiceAmounts.push($(this).attr("invoiceamount"));
                processeds.push(true);
            }
        });
        if (contestIds.length == 0) {
            // no data to process
            showErrors("No record to process.");
            return;
        }
        
        toUpdate = false;
        $("#processInvoiceRecordModal .title").html("Process Invoice Record");
        $("#invoiceNumber").val("");
        $("#invoiceDate").val($("#currentDate").val()).trigger('change');
        $(".errorInfo", $("#invoiceNumber").parent()).hide();
        modalLoad("#processInvoiceRecordModal");
    });
    
    $("#checkAllInvoice").click(function() {
        if ($(this).is(":checked")) {
            $("#billingCostDetails tbody tr input[name='invoiceRecordProcessed']:not(:disabled)").attr("checked", "checked");
        } else {
            $("#billingCostDetails tbody tr input[name='invoiceRecordProcessed']:not(:disabled)").removeAttr("checked");
        }
        
        var chs = $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:checked:not(:disabled)");
        if (chs.length == 0) {
            $("#billingCostReportSection .processBtn").attr("disabled", "disabled");
        } else {
            $("#billingCostReportSection .paginatedDataTable .processBtn").attr("disabled", "");
        }
    });
    
});

/**
 * Update the invoice records.
 * 
 * @param contestIds the contest IDs of the invoice records.
 * @param paymentIds the payment IDs of the invoice records.
 * @param invoiceTypeNames the invoice type names of the invoice records.
 * @param invoiceAmounts the invoice amount of the invoice records.
 * @param processeds the processed flags of the invoice records.
 * @param invoiceNumber the invoice number of the invoice records.
 * @param invoiceDate the invoice date of the invoice records.
 * @param checkInvoiceNumber whether to check the invoice number exists
 * @param succCallback the callback function which will be called when AJAX completed.
 * @param errorCallback the callback function which will be called when AJAX failed.
 */
function updateInvoiceRecords(contestIds, paymentIds, invoiceTypeNames, invoiceAmounts, processeds, invoiceNumber, invoiceDate, checkInvoiceNumber, succCallback, errorCallback) {
    if (contestIds.length == 0) return;
    var data = {contestIds: contestIds, paymentIds: paymentIds, invoiceTypeNames: invoiceTypeNames, invoiceAmounts: invoiceAmounts, processeds: processeds,
                invoiceNumber: invoiceNumber, invoiceDate: invoiceDate, checkInvoiceNumber: checkInvoiceNumber};
    modalAllClose();
    $.ajax({
        type: 'POST',
        url:'updateInvoiceRecords',
        data: data,
        dataType: "json",
        cache:false,
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    if (succCallback) {
                        succCallback(result);
                    }
                },
                function(errorMessage) {
                    if (errorCallback) {
                        errorCallback();
                    }
                    showServerError(errorMessage);
                }
            )
        },
        beforeSend: function() {modalPreloader();},
        complete: function() {modalClose();}
    });
}

/**
 * Update the invoice.
 * 
 * @param invoiceId the id of the invoice
 * @param invoiceNumber the new invoice number
 * @param invoiceDate the new invoice date
 * @param succCallback the callback function which will be called when AJAX completed.
 * @param errorCallback the callback function which will be called when AJAX failed.
 */
function updateInvoice(invoiceId, invoiceNumber, invoiceDate, updateSingle, invoiceRecordId, checkInvoiceNumber, succCallback, errorCallback) {
    var data = {invoiceId: invoiceId, invoiceNumber: invoiceNumber, invoiceDate: invoiceDate, updateSingle: updateSingle,
                    invoiceRecordId: invoiceRecordId, checkInvoiceNumber: checkInvoiceNumber};
    modalAllClose();
    $.ajax({
        type: 'POST',
        url:'updateInvoice',
        data: data,
        dataType: "json",
        cache: false,
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    if (succCallback) {
                        succCallback(result);
                    }
                },
                function(errorMessage) {
                    if (errorCallback) {
                        errorCallback();
                    }
                    showServerError(errorMessage);
                }
            )
        },
        beforeSend: function() {modalPreloader();},
        complete: function() {modalClose();}
    });
}
