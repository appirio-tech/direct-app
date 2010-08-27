var arrSlot = ["firstSlots","secondSlots","thirdSlots","fourthSlots","fifthSlots"];
// add placement icon to the placement submissions
$(document).ready(function() {
	var index = 0;
	$("#submissionList .icoBankLocation").each(function() {
		if (!$(this).hasClass("dollarSlots")) {
			$(this).addClass(arrSlot[index]);
			index++;
		}
	});
});
