/**
 * Close modal windows
 */
function closeModal() {
	$("#new-modal-window .outLay .modalBody .modalContent").removeClass("multiple");
	$("#new-modal-window .outLay .modalHeader").removeClass("confirmation");
	$("#new-modal-window .outLay .modalBody").removeClass("confirmation operationSuccess serverError warning");
	$("#new-modal-window .outLay .modalCommandBox .defaultBtn, .outLay .modalCommandBox .noBtn").unbind("click");
    $("#new-modal-window .large").removeClass("large");
	$("#modal-background, #new-modal-window .outLay").hide();
}

/**
 * Adjust the overlay background and model window and display them
 */
function adjustAndShow(modal) {
	$("#modal-background").css("height", document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
	$("#modal-background, " + modal).show();
	$("#new-modal-window").css("margin-left", - $("#new-modal-window").width() / 2 + "px").css("margin-top", - $("#new-modal-window").height() / 2 + "px");
	var height = 0;
	$(modal + " .outLay .modalBody .modalContent li").each(function(){
		height += $(this).height();
	});
	if(!$(modal+ " .outLay .modalBody .modalContent").hasClass("multiple")){
		$(modal+ " .outLay .modalBody .modalContent li:first").css("padding-top", ($(".outLay .modalBody .modalContent").height() - height) / 2 + "px");
	}

    if (jQuery.browser.msie && jQuery.browser.version == '9.0') {
        $(modal + " .modalBody").css("margin-top", "-2px");
    }
}

/**
 * Display client side error modal window
 *
 * modal : The id of the modal window
 * title : The title of the modal window
 * messageArray : The array of messages to be displayed, can be HTML code
 * buttonText : The text of the button(default: OK)
 * buttonHandler : The event handler of the button(default: closeModal)
 */
function displayClientSideError(modal, title, messageArray, buttonText, buttonHandler) {
	buttonText = buttonText || "OK";
	buttonHandler = buttonHandler || closeModal;
	$(modal + " .modalHeader .modalHeaderCenter h2").text(title);
	var isMultiple = messageArray.length != 1;
	if(isMultiple) {
		$(modal + " .modalBody .modalContent").addClass("multiple");
	}
	var html = "";
	for(var i = 0; i < messageArray.length; i++) {
		html += "<li>" + messageArray[i] + "</li>";
	}

	$(modal + " .modalBody .modalContent").html(html);
	$(modal + " .modalCommandBox .defaultBtn .btnC").text(buttonText);
	$(modal + " .modalCommandBox .defaultBtn").bind("click", buttonHandler);

    if (!isMultiple) {
        $(modal + " .modalBody .modalContent li").addClass("single");
    }

    adjustAndShow(modal);
}

/**
 * Display user confirmation modal window
 *
 * modal : The id of the modal window
 * title : The title of the modal window
 * message : The messages to be displayed, can be HTML code
 * yesButtonText : The text of the yes button(default: YES)
 * yesButtonHandler : The event handler of the yes button
 * noButtonText : The text of the no button(default: NO)
 * noButtonHandler : The event handler of the no button(default: closeModal)
 */
function displayUserConfirmation(modal, title, message, yesButtonText, yesButtonHandler, noButtonText, noButtonHandler) {
	yesButtonText = yesButtonText || "YES";
	noButtonText = noButtonText || "NO";
	noButtonHandler = noButtonHandler || closeModal;
	$(modal + " .modalHeader .modalHeaderCenter h2").text(title);
	$(modal + " .modalBody, " + modal + " .modalHeader").addClass("confirmation");

    // user large modal window if text exceeds 100 characters
    if (message.length >= 150) {
        $(modal).addClass("large");
    }

	$(modal + " .modalBody .modalContent").html("<li>" + message + "</li>");
	$(modal + " .modalCommandBox .defaultBtn .btnC").text(yesButtonText);
	$(modal + " .modalCommandBox .defaultBtn").bind("click", yesButtonHandler);
	$(modal + " .modalCommandBox .noBtn .btnC").text(noButtonText);
	$(modal + " .modalCommandBox .noBtn").bind("click", noButtonHandler);
	adjustAndShow(modal);
}

/**
 * Display operation success modal window
 *
 * modal : The id of the modal window
 * title : The title of the modal window
 * messageArray : The message to be displayed, can be HTML code
 * buttonText : The text of the button(default: OK)
 * buttonHandler : The event handler of the button(default: closeModal)
 */
function displayOperationSuccess(modal, title, message, buttonText, buttonHandler) {
	buttonText = buttonText || "OK";
	buttonHandler = buttonHandler || closeModal;
	$(modal + " .modalHeader .modalHeaderCenter h2").text(title);
	$(modal + " .modalBody").addClass("operationSuccess");
	$(modal + " .modalBody .modalContent").html("<li>" + message + "</li>");
	$(modal + " .modalCommandBox .defaultBtn .btnC").text(buttonText);
	$(modal + " .modalCommandBox .defaultBtn").bind("click", buttonHandler);
	adjustAndShow(modal);
}

/**
 * Display server error modal window
 *
 * modal : The id of the modal window
 * title : The title of the modal window
 * messageArray : The message to be displayed, can be HTML code
 * buttonText : The text of the button(default: OK)
 * buttonHandler : The event handler of the button(default: closeModal)
 */
function displayServerError(modal, title, message, buttonText, buttonHandler) {
	buttonText = buttonText || "OK";
	buttonHandler = buttonHandler || closeModal;
	$(modal + " .modalHeader .modalHeaderCenter h2").text(title);
	$(modal + " .modalBody").addClass("serverError");
	$(modal + " .modalBody .modalContent").html("<li>" + message + "</li>");
	$(modal + " .modalCommandBox .defaultBtn .btnC").text(buttonText);
	$(modal + " .modalCommandBox .defaultBtn").bind("click", buttonHandler);
	adjustAndShow(modal);
}

/**
 * Display warning modal window
 *
 * modal : The id of the modal window
 * title : The title of the modal window
 * message : The messages to be displayed, can be HTML code
 * yesButtonText : The text of the yes button(default: YES)
 * yesButtonHandler : The event handler of the yes button
 * noButtonText : The text of the no button(default: NO)
 * noButtonHandler : The event handler of the no button(default: closeModal)
 */
function displayWarning(modal, title, message, yesButtonText, yesButtonHandler, noButtonText, noButtonHandler) {
	yesButtonText = yesButtonText || "YES";
	noButtonText = noButtonText || "NO";
	noButtonHandler = noButtonHandler || closeModal;
	$(modal + " .modalHeader .modalHeaderCenter h2").text(title);
	$(modal + " .modalBody").addClass("warning");
	$(modal + " .modalBody .modalContent").html("<li>" + message + "</li>");
	$(modal + " .modalCommandBox .defaultBtn .btnC").text(yesButtonText);
	$(modal + " .modalCommandBox .defaultBtn").bind("click", yesButtonHandler);
	$(modal + " .modalCommandBox .noBtn .btnC").text(noButtonText);
	$(modal + " .modalCommandBox .noBtn").bind("click", noButtonHandler);
	adjustAndShow(modal);
}

$(function() {
	$("#modal-background").css("opacity", "0.6");
	$("#new-modal-window .closeModal").bind("click", closeModal);
	$("#new-modal-window").scrollFollow({
		offset : parseInt((document.documentElement.clientHeight / 2))
	});
});
