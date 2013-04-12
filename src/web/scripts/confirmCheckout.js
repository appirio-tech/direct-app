/**
 * Studio Submission Checkout Confirmation Javascript
 *
 * @author FireIce
 * @version 1.0
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

    // add placement icon to submissions and reorder the submissions
    var number = Math.min(submissionsNumber, prizeNumber);
    for (var i = 0; i < number; i++) {
        var label = bankData ? bankData[arrPrize[i]] : null;
        if (label) {
            $("#submission-" + label).find(".icoBankLocation").addClass(arrSlot[i]);
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
