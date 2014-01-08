/**
 * Studio Submission Checkout Confirmation Javascript
 *
 * Version 1.1 Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly change note
 * - Added dialog for requesting the final fixes for winning submission
 *
 * Version 1.2 change note:
 * - Updated text for dialog for requesting final fixes from winner
 * - Moved the logic for requesting final fixes from winner to finalFixDecision.js
 * 
 * @author FireIce, isv
 * @version 1.2
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
	// placement payment
	for (var i = 0; i < prizeNumber; i++) {
		if (bankData && bankData[arrPrize[i]]) {
			html += '<tr><td class="label">' + position[i] + ' Place Prize:</td><td>1</td><td class="sum">' + fmoney(prizes[i + 1]) + '</td><td class="sum">' + fmoney(prizes[i + 1]) + '</td></tr>';
			total += parseFloat(prizes[i + 1]);
		}
	}

	// additional purchase
	var extraNumber = $(".dollarSlots").length;
	if (extraNumber > 0) {
		var totalExtra = extraNumber * additionalPrize;
		html += '<tr><td class="label">Additional Purchases:</td><td>' + extraNumber + '</td><td class="sum">' + fmoney(additionalPrize) + '</td><td class="sum">' + fmoney(totalExtra) + '</td></tr>';
		total += parseFloat(totalExtra);
	}
	// checkpoint payment
  if (checkpointAwardNumber > 0) {
	    var totalCheckpoint = checkpointAwardNumber * checkpointPrize;
	    html += '<tr><td class="label">Checkpoint Prizes:</td><td>' + checkpointAwardNumber + '</td><td class="sum">' + fmoney(checkpointPrize) + '</td><td class="sum">' + fmoney(totalCheckpoint) + '</td></tr>';
			total += parseFloat(totalCheckpoint);
  }	
	html += '<tr class="total"><td class="label">Total</td><td colspan="3" class="sum">' + fmoney(total) + '</td></tr>';
	$("#summary").html(html);
}


$(document).ready(function(){
    // contest id
    contestId = $("#contestId").val();
    // round type
    roundType = $("#roundType").val();
    // number of prize slots
    prizeNumber = $("#prizeNumber").val();
    // number of submissions
    var submissionsNumber = $("#submissionList tr").length - 1;

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

    $('.finalFixConfirm .noBtn').live('click', function () {
        sendNeedFinalFixesRequest(contestId, label, false);
    });

    $('.finalFixConfirm .yesBtn').live('click', function () {
        sendNeedFinalFixesRequest(contestId, label, true);
    });


    // add placement icon to submissions and reorder the submissions
    var number = Math.min(submissionsNumber, prizeNumber);
    for (var i = 0; i < number; i++) {
        var label = bankData ? bankData[arrPrize[i]] : null;
        if (label) {
            $("#submission-" + label).find(".icoBankLocation").addClass(arrSlot[i]);
            if (i == 0) { // Show Final Fix requesting form for winning submission
                var html = '';
                html += '<div class="finalFixConfirm">';
                html += '    <div class="p1">';
                html += '        <p>Would you like the winner to provide Final Fixes? <a target="_blank" href="http://community.topcoder.com/studio/help/final-fixes/faqs/">Final Fixes are explained here</a>. If you aren\'t sure, please ask your copilot before clicking "no".</p>';
                html += '        <a href="javascript:;" class="noBtn">NO</a>';
                html += '        <a href="javascript:;" class="yesBtn">YES</a>';
                html += '    </div>';
                html += '    <div class="p2 hide">';
                html += '        <p>You have chosen not to request Final Fixes from the winner and the challenge has now closed.</p>';
                html += '    </div>';
                html += '</div>';
                $("#submission-" + label + " .left").append(html);
            }
            $("#submission-" + label).appendTo($("#submissionList tbody")).show();
        }
    }

    $(".dollarSlots").parent().parent().parent().appendTo($("#submissionList tbody")).show(); 
    

    checkpointPrize = $("#checkpointPrize").val();
	  additionalPrize = $("#additionalPrize").val();
	  checkpointAwardNumber = $("#checkpointAwardNumber").val();
	  for (var i = 1; i <= prizeNumber; i++) {
	      prizes[i] = $("#prize_" + i).val();
	  }
	  updateSummary();
});
