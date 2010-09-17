/**
 * Studio Submission Checkout Javascript
 *
 * @author flexme
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
// milestone prize
var milestonePrize;
// number of milestone submission that have been awarded
var milestoneAwardNumber;
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
	// milestone payment
	if (hasCheckout == "false") {
		if (milestoneAwardNumber > 0) {
			var totalMilestone = milestoneAwardNumber * milestonePrize;
			html += '<tr><td class="label">Milestone Prizes:</td><td>' + milestoneAwardNumber + '</td><td class="sum">' + fmoney(milestonePrize) + '</td><td class="sum">' + fmoney(totalMilestone) + '</td></tr>';
			total += parseFloat(totalMilestone);
		}
	}
	html += '<tr class="total"><td class="label">Total</td><td colspan="3" class="sum">' + fmoney(total) + '</td></tr>';
	$("#summary").html(html);
}

$(document).ready(function(){
	initDialog('msgDialog', 500);
	// block ui before ajax calls
	function beforeAjax() {
		$.blockUI({ message: '<div id=loading> loading.... </div>' });
	}
	// unblock ui after ajax calls
	function afterAjax() {
		$.unblockUI();
	}
	// set the timeout of ajax calls
	jQuery.ajaxSetup({
		timeout: 90000
	});
	
	// save feedback text
	$(".saveFeedback").click(function() {
		var submissionId = $(this).attr("rel");
		var feedback = $("#feedbackText" + submissionId).val();
		feedback = $.trim(feedback);
		if (feedback.length == 0) {
			$('#msgDialog p').html("Feedback content can't be empty.");
			$('#msgDialog').dialog('open');
			return false;
		}
		$.ajax({
			type: 'POST',
			url:  ctx + "/contest/updateSubmissionFeedback",
			data: {submissionId : submissionId, feedbackText : feedback},
			cache: false,
			dataType: 'json',
			async : false,
			success: function (jsonResult) {
				$("#submission-" + submissionId).find(".left").html(jsonResult.result['return'].feedbackText);
			},
			beforeSend: beforeAjax,
			complete: afterAjax
		});
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
			if (bankData[contestId]["FINAL"]) {
				listExtra = listExtra.concat(bankData[contestId]["FINAL"]["listExtra"]);
			}
			if (bankData[contestId]["MILESTONE"]) {
				listExtra = listExtra.concat(bankData[contestId]["MILESTONE"]["listExtra"]);
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
			bankData = bankData[contestId][roundType];
		} else {
			bankData = null;
		}
	}

	if (roundType == "MILESTONE" && hasCheckout == "true") {
		// already checkout
		$("#submissionList tr").each(function(index) {
			if (index > 0) {
				$(this).show();
			}
		});
		return;
	}
	if (bankData) {
		// add submissions which can be additional purchase to additional purchase section
		var html = "";
		var paidSubmissions = $("#paidSubmissions").val().split(",");
		var tempObj = {};
		for (var i = 0; i < paidSubmissions.length; i++) {
			tempObj[paidSubmissions[i]] = true;
		}
		var extraNumber = 0;
		for (var i = 0; i < listExtra.length; i++) {
			var label = listExtra[i];
			if (tempObj[label]) continue;
			extraNumber++;
			html += '<div class="additionalCol"><label>#' + label + '</label><input name="ch" type="checkbox" value=""'
				+ ' class="selectThumb" id="extra_' + label + '"/><a href="#" class="thumbList"><span></span><img src="http://www.topcoder.com/direct/cockpit/impersonation/cockpitStudio.do?&sbmid=' + label + '&sbt=small"'
				+ ' width="136" height="136"/></a></div>';
		}
		$("#purchasePreview").html(html);
		$(".selectThumb").click(updateSummary);
		if (extraNumber > 0) {
			$("#additionalPurchase").show();
		}
	}
	if (hasCheckout == "false") {
		// add placement icon to submissions and reorder the submissions
		var number = Math.min(submissionsNumber, prizeNumber);
		for (var i = 0; i < number; i++) {
			var label = bankData ? bankData[arrPrize[i]] : null;
			if (label) {
				$("#submission-" + label).find(".icoBankLocation").addClass(arrSlot[i]);
				$("#submission-" + label).appendTo($("#submissionList tbody")).show();
			} else {
				$('<tr><td></td><td></td><td></td><td class="left"><div class="warningMilestone">There is no selection for Slot '
				+ (i + 1) + '. You can go back to the <a href="submissions.action?contestId=' + contestId + '&formData.viewType=GRID&formData.roundType=' + roundType + '">submissions viewer</a> and add one / make necessary changes.</div></td></tr>').appendTo($("#submissionList tbody"));
			}
		}
	}

	// save milestone
	$(".saveMilestone").click(function() {
		var number = Math.min(submissionsNumber, prizeNumber);
		var ranks = "";
		
		var hasSelectedMilestone = false;
		
		for (var i = 0; i < number; i++) {
		    if (bankData && bankData[arrPrize[i]])
			    hasSelectedMilestone = true;
			if (i > 0) {
				ranks += ",";
			}
			ranks += bankData[arrPrize[i]];
		}
		if (!hasSelectedMilestone) {
			$('#msgDialog p').html("At least one submissions should be filled.");
			$('#msgDialog').dialog('open');
			return false;
		}		
		$("#ranks").val(ranks);
		$("#checkoutForm").submit();
		return false;
	});
	
	// checkout final submissions
	$(".checkout").click(function() {
		var number = Math.min(submissionsNumber, prizeNumber);
		var ranks = "";
		if (hasCheckout == "false") {
			for (var i = 0; i < number; i++) {
				if (!bankData || !bankData[arrPrize[i]]) {
					$('#msgDialog p').html("Prize slots must be filled.");
					$('#msgDialog').dialog('open');
					return false;
				}
				if (i > 0) {
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
		$("#additionalPurchases").val(additionalPurchases);
		$("#billingProjectId").val($("#slPaymentType").val());
		$("#checkoutForm").submit();
		return false;
	});

	milestonePrize = $("#milestonePrize").val();
	additionalPrize = $("#additionalPrize").val();
	milestoneAwardNumber = $("#milestoneAwardNumber").val();
	for (var i = 1; i <= prizeNumber; i++) {
		prizes[i] = $("#prize_" + i).val();
	}
	updateSummary();
});
