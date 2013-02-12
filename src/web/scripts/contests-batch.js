// stores the original draft contests content
var batchEditBuffer;

var loadBatchContestsBuffer = function() {

    // reset the buffer
    batchEditBuffer = {};

    if ($("#contestTable.editTable").length > 0) {
        // iterate the draft edit table
        $("#contestTable.editTable tbody tr").each(function(){
            var contestId = $(this).find('input[name=contestId]').val();
            var contestName = $(this).find('input[name=contestName]').val();
            var contestTypeId = $(this).find('select[name=contestType]').val();

            // disable component design / development contest type
            if (contestTypeId == 1 || contestTypeId == 2) {
                $(this).find('select[name=contestType]').attr('disabled', 'disabled');
            }

            var billingAccountId = $(this).find('select[name=billingAccount]').val();
            var startDate = $(this).find('input[name=startDate]').val();
            var contestBuffer = {};
            contestBuffer.contestId = contestId;
            contestBuffer.contestName = contestName;
            contestBuffer.contestTypeId = contestTypeId;
            contestBuffer.billingAccountId = billingAccountId;
            contestBuffer.startDate = startDate;
            batchEditBuffer[contestId] = contestBuffer;
        });
    }
};

var disableUnaccesssibleBilling = function() {
    $("#contestTable.editTable tbody tr").each(function(){
        var disableBilling = (0 == $(this).find('select[name=billingAccount] option.enabled').length);
        if (disableBilling) {
            $(this).find('select[name=billingAccount]').hide();
            $(this).find('select[name=billingAccount]').parent().append("<span class='hiddenBilling'>Billing Account Invisible</span>");
        }
    });
};

$(document).ready(function () {

    loadBatchContestsBuffer();

    // hide the unaccessible billing account
    disableUnaccesssibleBilling();

    // disable auto complete
    $("#contestTable.editTable input").attr('autocomplete', 'off');
    $("#contestTable.editTable select").attr('autocomplete', 'off');

    /**
     * Handles the click of batch edit page check all checkbox
     * */
    $("#contestTable.editTable input.all").click(function () {
        if ($(this).is(":checked")) {
            $("#contestTable tbody input[type=checkbox]").attr('checked', 'checked');
            $("#contestTable tbody tr").addClass('selectRow');
        } else {
            $("#contestTable tbody input[type=checkbox]").removeAttr('checked');
            $("#contestTable tbody tr").each(function () {
                if (!isEditDraftRowModified($(this))) {
                    $(this).removeClass("selectRow");
                }
            })
        }
    });

    // edit draft page contest name check
    $("#contestTable.editTable input[name=contestName]").live('keyup', function () {
        if ($(this).val() != "") {
            $(this).removeClass("empty");
            $(this).siblings("span.validation").hide();

            // check if modified
            var row = $(this).parents("tr");
            editDraftChangesRowCheck(row);
        } else {
            $(this).addClass("empty");
            $(this).siblings("span.validation").show();
        }
    });

    // edit draft page contest type check
    $("#contestTable.editTable select[name=contestType]").live('change', function () {

        if ($(this).parent().parent().find("input[type=checkbox]").is(":checked")) {
            var value = $(this).val();
            // find all the checked rows
            var rows = $("#contestTable tbody tr input[type=checkbox]:checked").parent().parent();

            // set to the same value as current
            for (var i = 0; i < rows.length; i++) {

                var exists = 0 != $(rows[i]).find('select[name=contestType] option[value='+ value +']').length;

                if (exists) {
                    $(rows[i]).find("select[name=contestType]").val(value).attr('selected', true);
                    $(rows[i]).find("select[name=contestType]").removeClass("empty").siblings("span.validation").hide();
                }
            }
        }

        // check modification
        var row = $(this).parents("tr");
        editDraftChangesRowCheck(row);
    });

    // edit draft page billing account check
    $("#contestTable.editTable select[name=billingAccount]").live('change', function () {

        if ($(this).parent().parent().find("input[type=checkbox]").is(":checked")) {
            var value = $(this).val();
            var rows = $("#contestTable tbody tr input[type=checkbox]:checked").parent().parent();
            for (var i = 0; i < rows.length; i++) {
                $(rows[i]).find("select[name=billingAccount]").val(value).attr('selected', true);
            }
        }

        // check modification
        var row = $(this).parents("tr");
        editDraftChangesRowCheck(row);
    });

    // edit draft page start date check
    $("#contestTable.editTable input.date-pick").live('change', function () {

        if ($(this).closest("tr").find("input[type=checkbox]").is(":checked")) {
            var value = $(this).val();
            var rows = $("#contestTable tbody tr input[type=checkbox]:checked").closest("tr");
            for (var i = 0; i < rows.length; i++) {
                $(rows[i]).find("input.date-pick").val(value).removeClass("waterMark");
                $(rows[i]).find("input.date-pick").removeClass("empty").siblings("span.validation").hide();
            }
        }

        // check modification
        var row = $(this).parents("tr");
        editDraftChangesRowCheck(row);
    });


    // handle the row checkbox click
    $("#contestTable.editTable tbody input[type=checkbox]").click(function () {
        if ($(this).is(":checked")) {
            $(this).parent().parent().addClass("selectRow");
        } else {
            if(!isEditDraftRowModified($(this).parent().parent())) {
                $(this).parent().parent().removeClass("selectRow");
            }
        }
    });

    $("#contestTable input[type=checkbox]").removeAttr("checked");

    /**
     * Remove the draft contests.
     */
    $("#area1 .buttonBar a.remove").click(function () {
        if ($("#contestTable.editTable tbody input[type=checkbox]:checked").length > 0) {
            showConfirmation("REMOVE DRAFT CONTESTS",
                "Are you sure you want to remove selected Draft contests? <br/> You cannot undo after removal."
                , "DELETE", handleDraftContestsRemoval);
        } else {
            showErrors("Please select the draft contests to remove first");
        }
    });

    /**
     * Save all button.
     */
    $(".buttonBar .saveAll").click(function () {
        var passAllValidation = true;

        // validate first
        $("#contestTable.editTable tbody tr").each(function(){
            if (validateDraftRow($(this))) {
                $(this).find("input, select").removeClass("empty");
                $(this).find(".validation").hide();
            } else {
                passAllValidation = false;
            }
        });

        if (!passAllValidation) return;

        var contestsToUpdate = [];
        var formData = {projectId:$("#currentDirectProjectID").text()};

        // build the save request
        $("#contestTable.editTable tbody tr").each(function(){
            if(isEditDraftRowModified($(this))) {
                // modified, build the request
                var contest = {};
                contest.contestId = $(this).find('input[name=contestId]').val();
                contest.contestName = $(this).find('input[name=contestName]').val();
                contest.contestStartDate = $(this).find("input.date-pick").val();
                contest.contestTypeId = $(this).find('select[name=contestType]').val();
                contest.contestBillingAccountId = $(this).find('select[name=billingAccount]').val();

                contestsToUpdate.push(contest);
            }
        });



        if(contestsToUpdate.length <= 0) {
            showErrors("Contents of the draft contests are not changed.");
            return;
        }

        modalPreloader();

        $.ajax({
            type:'post',
            url:'updateDraftContests',
            data:{formData:formData, contestsToUpdate:contestsToUpdate},
            cache:false,
            timeout:400000,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function (result) {
                        $.each(result, function (index, value) {
                            $("#contestTable.editTable tbody tr").each(function () {
                                var contestId = $(this).find("input[name=contestId]").val();
                                if (contestId == value) {
                                    // remove the select highlight for the updated contest
                                    $(this).removeClass('selectRow');
                                }
                            });

                            loadBatchContestsBuffer();

                            showSuccessfulMessage("The draft contests have been updated.");
                        });
                    },
                    function (errorMessage) {
                        modalAllClose();
                        showServerError(errorMessage);
                    });
            }
        });

    });

    // pre-process the start date and end date duration
    $("#contestTable.editTable tbody tr").each(function () {
        var stDate = Date.parse($(this).find("input.date-pick").val());
        var endDate = Date.parse($(this).find("span.endDate").html());
        var dif = ((endDate.getTime() - stDate.getTime()));
        $(this).find("td:last-child").append("<span class='dif'>" + dif + "</span> ");
    });

    // update end date
    $("#contestTable.editTable tbody tr input.date-pick").change(function () {
        setTimeout(function () {
            updateEndDate();
        }, 100)
    });

    /**
     * Postpone button.
     */
    $("#area1 .buttonBar div.postpone a.postponeBtn").click(function () {
        $("span.warning").hide();
        $("span").removeClass("warningLocation");
        $("input.days").removeClass("empty");
        var rows = $("#contestTable tbody tr input[type=checkbox]:checked").parent().parent();
        var postpone = eval($("#area1 .buttonBar div.postpone input.days").val());
        if ($(rows).length == 0) {
            $("span.warning").hide();
            $("span.rowsEmpty").show();
        }
        if (typeof postpone != "undefined") {
            $(rows).each(function () {
                var stDate = Date.parse($(this).find("input.date-pick").val());
                var dif = eval($(this).find("span.dif").html());

                var postponeDate = new Date(stDate.getTime() + postpone * 1000 * 60 * 60 * 24);
                var endDate = new Date(stDate.getTime() + (postpone) * 1000 * 60 * 60 * 24 + dif);

                $(this).find("input.date-pick").val(postponeDate.toString("MM/dd/yyyy"));
                $(this).find("span.endDate").html(endDate.toString("MM/dd/yyyy"));
            });
        } else {
            $("span.warning").hide();
            $("span.daysEmpty").addClass("warningLocation");
            $("span.daysEmpty").show();
            $("input.days").addClass("empty");
        }
    });

    /*set postpone date as numeric*/
    $("#area1 .buttonBar div.postpone input.days").keyup(function () {
        this.value = this.value.replace(/[^0-9\.]/g, '');
        $("span.daysEmpty").hide();
        $("input.days").removeClass("empty");
    });

});

function handleDraftContestsRemoval() {
        closeModal();
        modalPreloader();

        var formData = {projectId:$("#currentDirectProjectID").text()};
        var contestsToDelete = [];

        $("#contestTable.editTable tbody input[type=checkbox]:checked").each(function () {
            var contestId = $(this).parent().parent().find("input[name=contestId]").val();
            var contestToDelete = {contestId:contestId};
            contestsToDelete.push(contestToDelete);
        });

        $.ajax({
            type:'post',
            url:'removeDraftContests',
            data:{formData:formData, contestsToUpdate:contestsToDelete},
            cache:false,
            timeout:40000,
            dataType:'json',
            success:function (jsonResult) {
                handleJsonResult(
                    jsonResult,
                    function (result) {
                        $.each(result, function (index, value) {
                            $("#contestTable.editTable tbody tr").each(function () {
                                var contestId = $(this).find("input[name=contestId]").val();
                                if (contestId == value) {
                                    // remove the row
                                    $(this).remove();
                                }
                            });
                        });
                    },
                    function (errorMessage) {
                        modalAllClose();
                        showServerError(errorMessage);
                    });
            }
        });
}


function updateEndDate() {
    var rows = $("#contestTable tbody tr");
    $(rows).each(function () {
        var stDate = Date.parse($(this).find("input.date-pick").val());
        var dif = eval($(this).find("span.dif").html());

        var endDate = new Date(stDate.getTime() + dif);
        $(this).find("span.endDate").html(endDate.toString("MM/dd/yyyy"));
    });
}

/**
 * Validate the contest row to see if there is any required field empty
 * @param row
 */
function validateDraftRow(row) {
    var contestName = row.find("input[name=contestName]");
    var contestType = row.find("select[name=contestType]");
    var contestStartDate = row.find("input[name=startDate]");

    var passValidation = true;

    if(contestName.val() == '') {
        contestName.addClass("empty").siblings("span.validation").show();
        passValidation = false;
    } else {
        contestName.removeClass("empty").siblings("span.validation").hide();
    }

    if(contestStartDate.val() == '' || contestStartDate.val() == 'mm/dd/yyyy') {
        contestStartDate.addClass("empty").parent().siblings("span.validation").show();
        passValidation = false;
    } else {
        contestStartDate.removeClass("empty").parent().siblings("span.validation").hide();
    }

    if (contestType.val() == 0) {
        contestType.addClass("empty").siblings("span.validation").show();
        passValidation = false;
    } else {
        contestType.removeClass("empty").siblings("span.validation").hide();
    }

    return passValidation;

}


function isEditDraftRowModified(tr) {
    var modified = false;
    var contestId = tr.find('input[name=contestId]').val();
    var contestBuffer = batchEditBuffer[contestId];

    // check contest name
    if (tr.find('input[name=contestName]').val() != contestBuffer.contestName) {
        modified = true;
    }
    // check contest type
    if(tr.find('select[name=contestType]').val() != contestBuffer.contestTypeId) {
        modified = true;
    }
    // check billing account
    if(tr.find('select[name=billingAccount]').val() != contestBuffer.billingAccountId) {
        modified = true;
    }
    // check start date
    if(tr.find('input[name=startDate]').val() != contestBuffer.startDate) {
        modified = true;
    }

    return modified;
}

function editDraftChangesCheck() {
    $("#contestTable.editTable tbody tr").each(function(){
        editDraftChangesRowCheck($(this));
    });
}

/**
 * Checks the row (one draft contest) in the draft edit page by comparing it current data with the original
 * contest data stored in the buffer. If not match, highlight row. If match and the checkbox is not checked,
 * remove the highlight.
 *
 * @param row the html row representing the draft contest.
 */
function editDraftChangesRowCheck(row) {
    if(isEditDraftRowModified(row)) {
        row.addClass("selectRow");
    } else {
        if(!row.find('input[type=checkbox]').is(":checked")) {
           row.removeClass("selectRow");
        }
    }
}
