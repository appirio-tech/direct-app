/**
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for client invoices pages.
 *
 *  Version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
var sStdMenu =
    '<em>|</em><span>Show: </span><select size="1" name="dataTableLength" id="dataTableLength">' +
        '<option value="10">10</option>' +
        '<option value="25">25</option>' +
        '<option value="50">50</option>' +
        '<option value="-1">All</option>' +
        '</select>';

$(document).ready(function () {

    // add default option to customer selector
    if ($("#clientOption").length > 0) {
        $("#clientOption").prepend("<option value='0'>Select a customer</option>").val('0')
        $(".clientInvoiceManageRow:gt(0)").hide();
        $(".clientInvoiceManageRow:eq(0)").addClass('last');
    }

    $("#clientOption").change(function () {

        if($(this).val() == 0) {
            $(".clientInvoiceManageRow:gt(0)").hide();
            $(".clientInvoiceManageRow:eq(0)").addClass('last');
            return;
        } else {
            $(".clientInvoiceManageRow:gt(0)").show();
            $(".clientInvoiceManageRow:eq(0)").removeClass('last');
        }

        if ($("#dateBegin").val() != '' && $("#dateEnd").val() != '') {
            // re-filter the invoices because new records are inserted
            if ($("#clientOption").val() > 0) {
                $("#clientInvoicesFilter").trigger('click');
            } else {
                $(".clientInvoicesResult ol").empty();
            }

        }
    })

    var invoiceUploader =
        new AjaxUpload(null, {
            action: ctx + '/uploadClientInvoice',
            name: 'uploadInvoiceFile',
            responseType: 'json',
            onSubmit: function (file, ext) {

                var data = {
                    clientId: $("#clientOption").val(),
                        invoiceDate: $("#uploadDate").val(),
                    description: $("textarea[name=uploadInvoiceDescription]").val(),
                    fileName: file
                };

                data = setupTokenRequest(data, getStruts2TokenName());

                // set the form data for ajax upload form
                invoiceUploader.setData(
                    data
                );

                modalPreloader();
            },
            onComplete: function (file, jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {

                        // clear the text area
                        $("textarea[name=uploadInvoiceDescription]").val('');

                        // display the success upload response to user
                        showSuccessfulMessage("The invoice " + result.fileName + " has been uploaded");

                        modalClose();

                        if ($("#dateBegin").val() != '' && $("#dateEnd").val() != '') {
                            // re-filter the invoices because new records are inserted
                            $("#clientInvoicesFilter").trigger('click');
                        }

                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                        modalClose();
                    });
            }
        }, false);

    $("#uploadDate, input:file").live('change', function () {
        var fileSelected = validateInputControl($("input:file"));

        if (fileSelected) {
            // file selected, validation whether it's of type PDF
            if (!isPdfFile($("input:file").val())) {
                $("input:file").parent().find(".errorMessage2").show();
                return false;
            } else {
                $("input:file").parent().find(".errorMessage2").hide();
                return true;
            }
        } else {
            return false;
        }
    });

    $("#dateBegin, #dateEnd").change(function(){
        // check start date and end date values
        var startDv = new Date($("#dateBegin").val());
        var endDv = new Date($("#dateEnd").val());

        if (startDv > endDv) {
            $(".clientInvoiceManageRow:eq(1) .errorMessage2").show();
        } else {
            $(".clientInvoiceManageRow:eq(1) .errorMessage2").hide();
        }
    });

    $("#clientInvoicesFilter").click(function () {
        // do the validation first
        var validationResult = validateInputControl($("#dateBegin"))
            && validateInputControl($("#dateEnd")) && validateInputControl($("#clientOption"));

        if (!validationResult) {
            return;
        }

        // check start date and end date values
        var startDv = new Date($("#dateBegin").val());
        var endDv = new Date($("#dateEnd").val());

        if (startDv > endDv) {
            $(".clientInvoiceManageRow:eq(1) .errorMessage2").show();
            return;
        } else {
            $(".clientInvoiceManageRow:eq(1) .errorMessage2").hide();
        }

        var formData = {
            clientId: $("#clientOption").val(),
            startDate: $("#dateBegin").val(),
            endDate: $("#dateEnd").val()
        }

        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/filterClientInvoices",
            data: setupTokenRequest(formData, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        // empty the existing invoices list first
                        $(".clientInvoicesResult ol").empty();

                        $.each(result, function (index, value) {
                            var invoiceLink = $('<a class="invoiceLink"></a>').text(value.fileName)
                                .attr('href', ctx + "/downloadInvoiceUpload?invoiceUploadId=" + value.id);
                            var description = $('<span></span>').text(value.description);
                            var deleteLink = $("<a class='deleteLink' href='javascript:;'></a>").text('Delete');
                            var invoiceUploadId = $("<input type='hidden'/>").val(value.id);
                            $(".clientInvoicesResult ol").append($("<li></li>").append(invoiceLink)
                                .append(description).append(deleteLink).append(invoiceUploadId));
                        });
                    },
                    function (errorMessage) {
                        modalCloseAddNewProject();
                        showServerError(errorMessage);
                    });
            }
        });

    });

    $("#invoiceUploadButton").click(function () {
        invoiceUploader._input = $("input[name=uploadInvoiceFile]").get(0);

        if (!validateInvoiceUploadForm()) {
            return false;
        }

        invoiceUploader.submit();
        if ($(".uploadFileSection input[name=uploadInvoiceFile]").length == 0) {
            $('<input type="file" name="uploadInvoiceFile"/>').insertAfter(".uploadFileSection .fieldName2");
        }
    });


    $("a.deleteLink").live('click', function () {
        // retrieve the id of the invoice upload record to delete
        var $delete = $(this);
        var invoiceUploadId = $delete.parent().find("input[type=hidden]").val();

        var formData = {invoiceUploadId: invoiceUploadId};

        $.ajax({
            type: 'POST',
            url: ctx + "/deleteClientInvoice",
            data: setupTokenRequest(formData, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        $delete.parent().remove();
                    },
                    function (errorMessage) {
                        modalCloseAddNewProject();
                        showServerError(errorMessage);
                    });
            }
        });

    });

    if ($(".clientInvoice .tableData table").length > 0) {
        $('.clientInvoice .tableData table').dataTable({
            "iDisplayLength": 25,
            "bStateSave": false,
            "bFilter": false,
            "bSort": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sLengthMenu": sStdMenu,
                "oPaginate": {
                    "sFirst": "",
                    "sPrevious": "Prev",
                    "sNext": "Next",
                    "sLast": ""
                }
            },
            "aaSorting": [
                [0, 'desc']
            ],
            "aoColumns": [
                { "sType": "date-trimmed" },
                { "sType": "html" }
            ],
            "sPaginationType": "full_numbers",
            "sDom": 't<"pagePanel bottomPagePanel"i<"showPage"l><"pageNum"p>>'
        });
    }
});

function validateInputControl(input) {
    if (input.val() == '' || input.val() == 0) {
        input.parent().find(".errorMessage").show();
        return false;
    } else {
        input.parent().find(".errorMessage").hide();
        return true;
    }
}

function validateInvoiceUploadForm() {
    var result = validateInputControl($("#uploadDate"));
    result = validateInputControl($("#clientOption")) && result;

    var uploadDate = new Date($("#uploadDate").val());
    var currentDate = new Date();

    if (uploadDate > currentDate) {
        $("#uploadDate").parent().find(".errorMessage2").show();
        return false;
    } else {
        $("#uploadDate").parent().find(".errorMessage2").hide();
    }


    // validate description max length
    if ($("textarea[name=uploadInvoiceDescription]").val().length > 500) {
        $("textarea[name=uploadInvoiceDescription]").parent().find(".errorMessage").show();
        result = false;
    } else {
        $("textarea[name=uploadInvoiceDescription]").parent().find(".errorMessage").hide();
    }

    var fileSelected = validateInputControl($("input:file"));
    result = fileSelected && result;

    if (fileSelected) {
        // file selected, validation whether it's of type PDF
        if (!isPdfFile($("input:file").val())) {
            $("input:file").parent().find(".errorMessage2").show();
            return false;
        } else {
            $("input:file").parent().find(".errorMessage2").hide();
            return result;
        }
    } else {
        return false;
    }
}

function isPdfFile(fileName) {
    return fileName.match(/\.pdf$/) != null;
}
