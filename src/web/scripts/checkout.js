/**
 * Studio Submission Checkout Javascript
 *
 * - Version 1.1 (TC Direct Replatforming Release 5) Change notes:
 * - - This file was refactoring to work for the new Studio contest type which work on the Online Review System.
 *
 * - Version 1.2 (Release Assembly - TopCoder Cockpit Modal Windows Revamp version) changes note:
 * -  Update the modal windows of success and error
 *
 * - Version 1.3 (Release Assembly - TC Direct Cockpit Release One) changes note
 * -  Update the check out checking logic to allow the number of chosen winner less than the predefined slots.
 *
 * @author flexme, TCSASSEMBLER
 * @version 1.3
 */
var arrPrize = ["firstPrize","secondPrize","thirdPrize","fourthPrize","fifthPrize"];
var arrSlot = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots"];
// position name
var position = ["1st", "2nd", "3rd", "4th", "5th"];
// the cookie to store bank data
var COOKIE_NAME = "bankSelection";
// contest id
var contestId;
// contest round type
var roundType;
// number of prize slots
var prizeNumber;
// prize payments
var prizes = new Array();
// submissions which can be additional purchase
var listExtra = new Array();
// prize of additional purchase
var additionalPrize;
// checkpoint prize
var checkpointPrize;
// number of checkpoint submission that have been awarded
var checkpointAwardNumber;
// the bank data
var bankData;
// a flag indicate whether the submissions have already been checked out
var hasCheckout;

// format the money to display
function fmoney(s) {
    var n = 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
    r = s.split(".")[1];
    t = "";
    for(i = 0; i < l.length; i ++ ) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return "$" + t.split("").reverse().join("") + "." + r;
}

// update the purchase summary section
function updateSummary() {
    var html = "";
    var total = 0;
    if (hasCheckout == "false") {
        // placement payment
        for (var i = 0; i < prizeNumber; i++) {
            if (bankData && bankData[arrPrize[i]]) {
                html += '<tr><td class="label">' + position[i] + ' Place Prize:</td><td>1</td><td class="sum">' + fmoney(prizes[i + 1]) + '</td><td class="sum">' + fmoney(prizes[i + 1]) + '</td></tr>';
                total += parseFloat(prizes[i + 1]);
            }
        }
    }

    // additional purchase
    var extraNumber = 0;
    for (var i = 0; i < listExtra.length; i++) {
        if ($("#extra_" + listExtra[i]).is(":checked")) {
            extraNumber++;
        }
    }
    if (extraNumber > 0) {
        var totalExtra = extraNumber * additionalPrize;
        html += '<tr><td class="label">Additional Purchases:</td><td>' + extraNumber + '</td><td class="sum">' + fmoney(additionalPrize) + '</td><td class="sum">' + fmoney(totalExtra) + '</td></tr>';
        total += parseFloat(totalExtra);
    }
    // checkpoint payment
    if (hasCheckout == "false") {
        if (checkpointAwardNumber > 0) {
            var totalCheckpoint = checkpointAwardNumber * checkpointPrize;
            html += '<tr><td class="label">Checkpoint Prizes:</td><td>' + checkpointAwardNumber + '</td><td class="sum">' + fmoney(checkpointPrize) + '</td><td class="sum">' + fmoney(totalCheckpoint) + '</td></tr>';
            total += parseFloat(totalCheckpoint);
        }
    }
    html += '<tr class="total"><td class="label">Total</td><td colspan="3" class="sum">' + fmoney(total) + '</td></tr>';
    $("#summary").html(html);
}

$(document).ready(function(){
    initDialog('msgDialog', 300);
    // block ui before ajax calls
    function beforeAjax() {
        modalPreloader();
    }
    // unblock ui after ajax calls
    function afterAjax() {
        modalClose();
    }
   

    // save feedback text
    $(".saveFeedback").click(function() {
        var submissionId = $(this).attr("rel");
        var feedback = $("#feedbackText" + submissionId).val();
        feedback = $.trim(feedback);
        if (feedback.length == 0) {
            showErrors("Feedback content can't be empty.");
            return false;
        }
        $.ajax({
            type: 'POST',
            url:  ctx + "/contest/updateSubmissionFeedback",
            data: setupTokenRequest({projectId : contestId, submissionId : submissionId, feedbackText : feedback, roundType : roundType}, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            async : false,
            success: function (jsonResult) {

                handleJsonResult2(jsonResult,
                    function (result) {
                        modalClose();
                        showSuccessfulMessage("Your feedback has been saved.");
                        $("#info" + submissionId).removeClass("errorinfo");
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });


            },
            beforeSend: beforeAjax,
            complete: afterAjax
        });
    });
    $("#checkAll").click(updateSummary);
    $("#checkNone").click(updateSummary);
    // save general feedback text
    $("#saveGeneralFeedback").click(function() {
        var contestId = $(this).attr("rel");
        var feedback = $("#feedbackTextCheckpointRound").val();
        feedback = $.trim(feedback);
        if (feedback.length == 0) {
            showErrors("Feedback content can't be empty.");
            return false;
        }
        $.ajax({
            type: 'POST',
            url:  ctx + "/contest/updateGeneralSubmissionsFeedback",
            data: setupTokenRequest({projectId : contestId, feedbackText : feedback}, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            async : false,
            success: function (jsonResult) {
                $("#ginfo").html("Feedback saved!");
                $("#ginfo").removeClass("errorinfo");
            },
            error: function (jsonResult) {
                showServerError("Error occurs when saving your feedback.");
            },
            beforeSend: beforeAjax,
            complete: afterAjax
        });
        return false;
    });

    // contest id
    contestId = $("#contestId").val();
    // round type
    roundType = $("#roundType").val();
    // number of prize slots
    prizeNumber = $("#prizeNumber").val();
    // number of submissions
    var submissionsNumber = $("#submissionList tr").length - 1;
    hasCheckout = $("#hasCheckout").val();

    // get bank data from cookie
    bankData = $.cookie(COOKIE_NAME);
    if (bankData) {
        bankData = jQuery.parseJSON(bankData);
        if (bankData && bankData[contestId]) {
            bankData = bankData[contestId][roundType];
            if (bankData) {
                listExtra = bankData["listExtra"];
            }
            var pre = "";
            var tempL = new Array();
            for (var i = 0; i < listExtra.length; i++) {
                if (listExtra[i] != pre) {
                    pre = listExtra[i];
                    tempL.push(pre);
                }
            }
            listExtra = tempL;
        } else {
            bankData = null;
        }
    }

    if (hasCheckout == "true") {
        // already checkout
        $("#submissionList tr").each(function(index) {
            if (index > 0) {
                $(this).show();
            }
        });

        checkpointPrize = $("#checkpointPrize").val();
        additionalPrize = $("#additionalPrize").val();
        checkpointAwardNumber = $("#checkpointAwardNumber").val();
        for (var i = 1; i <= prizeNumber; i++) {
            prizes[i] = $("#prize_" + i).val();
        }
        updateSummary();

        return;
    }
    if (bankData) {
        // add submissions which can be additional purchase to additional purchase section
        var html = "";
        var extraNumber = 0;
        for (var i = 0; i < listExtra.length; i++) {
            var label = listExtra[i];
            extraNumber++;
            html += '<div class="additionalCol"><label>#' + label + '</label><input name="ch" type="checkbox" value=""'
                + ' class="selectThumb" id="extra_' + label + '" onclick="updateSummary()"/><a href="#" class="thumbList"><span></span><img src="/direct/cockpit/impersonation/cockpitStudio.do?&sbmid=' + label + '&sbt=small"'
                + ' width="136" height="136"/></a></div>';
        }
        $("#purchasePreview").html(html);
        if (extraNumber > 0) {
            $("#additionalPurchase").show();
        }
    }
    // add placement icon to submissions and reorder the submissions
    var number = Math.min(submissionsNumber, prizeNumber);

    var finalRoundWinnerChosen = false;

    for (var i = 0; i < number; i++) {
        var label = bankData ? bankData[arrPrize[i]] : null;
        if (label || (roundType == "CHECKPOINT")) {

            if(roundType == 'FINAL') {
                finalRoundWinnerChosen = true;
            }

            $("#submission-" + label).find(".icoBankLocation").addClass(arrSlot[i]);
            $("#submission-" + label).appendTo($("#submissionList tbody")).show();
        }
    }

    if(roundType == "FINAL" && !finalRoundWinnerChosen ) {
        $('<tr><td></td><td></td><td></td><td class="left"><div class="warningCheckpoint">There is no selection winner for final round'
        + '. You can go back to the <a href="submissions.action?projectId=' + contestId + '&formData.viewType=GRID&formData.roundType=' + roundType + '">submissions viewer</a> and add one / make necessary changes.</div></td></tr>').appendTo($("#submissionList tbody"));
    }


    // set individual feedback text for final round.
    var subNumber = Math.min(submissionsNumber, prizeNumber);
    if (roundType == "FINAL") {
        for (var i = 0; i < subNumber; i++) {
            var label = bankData ? bankData[arrPrize[i]] : null;
            $("#feedbackText" + label).val("This feature will be implemented in future release");
        }
    }
    
    // save checkpoint or check out final submissions
    $(".saveCheckpoint, .checkout").click(function() {
        var number = Math.min(submissionsNumber, prizeNumber);

        var canSave = true;
        // check each submission's feedback text
        if (roundType == "CHECKPOINT") {
            for (var i = 0; i < number; i++) {
                var label = bankData ? bankData[arrPrize[i]] : null;
                if (label) {
                    $.ajax({
                        type: 'POST',
                        url:  ctx + "/contest/hasSubmissionFeedback",
                        data: {projectId : contestId, submissionId : label, roundType : roundType},
                        cache: false,
                        dataType: 'json',
                        async : false,
                        success: function (jsonResult) {
                            if (!jsonResult.result['return'].hasFeedback) {
                                 $("#info" + label).html("* Enter your feedback above");
                                 $("#info" + label).addClass("errorinfo");
                                 canSave = false;
                            }
                        },
                        beforeSend: beforeAjax,
                        complete: afterAjax
                    });
                }
            }
        }

        // check general feedback text
        $("#saveGeneralFeedback").each(function() {
            var contestId = $(this).attr("rel");
            $.ajax({
                type: 'POST',
                url:  ctx + "/contest/hasGeneralSubmissionsFeedback",
                data: {projectId : contestId},
                cache: false,
                dataType: 'json',
                async : false,
                success: function (jsonResult) {
                    if (!jsonResult.result['return'].hasFeedback) {
                         $("#ginfo").html("* Enter your feedback above");
                         $("#ginfo").addClass("errorinfo");
                         canSave = false;
                    }
                },
                beforeSend: beforeAjax,
                complete: afterAjax
            });
            return false;
        });

        // check action info fields.
        if (canSave) {
            var ranks = "";
            var prizesChosen = 0;
            var reachLast = false;
            for (var i = 0; i < number; i++) {
                if (!bankData || !bankData[arrPrize[i]]) {
                } else {
                    prizesChosen++;
                }
            }

            if(prizesChosen <= 0) {
                    
                var roundMessage = "checkpoint round";
                    
                if(roundType == 'FINAL') {
                    roundMessage = "final round";
                }


                showErrors("Please select at least one submission for " + roundMessage + ".");
                return false;
            }
            
            for (var i = 0; i < number; i++) {
                if (bankData && bankData[arrPrize[i]]) {
                    if (ranks.length > 0) {
                        ranks += ",";
                    }
                    ranks += bankData[arrPrize[i]];
                }
            }
            $("#ranks").val(ranks);

            var additionalPurchases = "";
            for (var i = 0; i < listExtra.length; i++) {
                if ($("#extra_" + listExtra[i]).is(":checked")) {
                    if (additionalPurchases != "") {
                        additionalPurchases += ",";
                    }
                    additionalPurchases += listExtra[i];
                }
            }

            if (prizesChosen < prizeNumber && additionalPurchases.length > 0) {
                showErrors("Prize slots must be filled before choosing extra purchases.");
                return false;
            }
            $("#additionalPurchases").val(additionalPurchases)
            $('<input />').attr('type', 'hidden').attr('name', "struts.token.name").attr('value', getStruts2TokenName()).appendTo('#checkoutForm');
            $('<input />').attr('type', 'hidden').attr('name', getStruts2TokenName()).attr('value', getStruts2Token()).appendTo('#checkoutForm');
            $("#checkoutForm").submit();
        }
        return false;
    });

    checkpointPrize = $("#checkpointPrize").val();
    additionalPrize = $("#additionalPrize").val();
    checkpointAwardNumber = $("#checkpointAwardNumber").val();
    for (var i = 1; i <= prizeNumber; i++) {
        prizes[i] = $("#prize_" + i).val();
    }
    updateSummary();
});
