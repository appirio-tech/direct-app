/**
 *
 * Common JS Script
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - Add some functions for modal pop ups
 *
 * Version 1.2 Direct Improvements Assembly Release 2 Assembly change note
 * - Add character limitation for the input fields and input areas when creating contests.
 *
 * Version 1.3 (Release Assembly - TopCoder Cockpit Modal Windows Revamp) change notes:
 * - Add methods to show different modal windows.
 *
 * Version 1.4 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) change notes:
 * - Add methods to setup cockpit tinyMCE editors.
 *
 * Version 1.5 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0)
 * - Add the tctip lib to display the tooltip
 *
 * Version 1.6 (Release Assembly - TopCoder Studio CCA Integration) change notes:
 * - Add methods to support place holder text for tinyMCE editors.
 *
 * Version 1.7 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 * - Add method to sort the dropdown by its option text
 *
 * Version 1.8 (Release Assembly - TopCoder Security Groups - Release 4)
 * - Add methods to support rendering at most configurable rows for an array content. The hidden 
 * - rows can be displayed through tooltip.
 * 
 * Version 1.8 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 *
 * Version 1.9 - (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
 * - Update method to fix a bug in Chrome.
 *
 * Version 2.0 - (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * - Update method sortDropDown
 *
 * Version 2.1 - (Release Assembly - TC Cockpit Tasks Management Release 2)
 * - Add method serializeObject to serialize a form into a json object
 *
 * Version 2.2 - (Module Assembly - TC Direct Struts 2 Upgrade)
 * - make calling of adjustImageRatio only if the page has specific image.
 *
 * Version 2.3 - 
 * - make calling of adjustImageRatio only if the page has specific image.
 *
 * Version 2.4 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * - Refactor jQuery.fn.outerHTML from projectMilestone.js to the common lib
 * 
 * @since Launch Contest Assembly - Studio
 */
$(document).ready(function() {
    adjustImageRatio();

    // setup the global AJAX timeout to 50 seconds
    jQuery.ajaxSetup({
        timeout: 50 * 60 * 1000
    });

    $(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
        modalClose();
        showGeneralError();
    });

    // initialize the dialogue to display response messages
    initDialog('msgDialog', 300, {
                Ok: function() {
                    $(this).dialog("close");
                }
            });

    // initialize the dialogue to display error messages
    initDialog('errorDialog', 300, {
                Ok: function() {
                    $(this).dialog("close");
                }
            });

    function limitFileDescriptionChars(maxChars) {
        var ori = "";
        var timeId = -1;
        return function(e) {
            var textArea = $(this);
            var content = textArea.val();
            if (content.length <= maxChars) {
                ori = content;
            }
            if (timeId != -1) {
                timeId = clearTimeout(timeId);
            }
            timeId = setTimeout(function() {
                timeId = -1;
                if (textArea.val().length > maxChars) {
                    showErrors("You can only input max " + maxChars
                        + " characters.");
                    textArea.val(ori);
                }
            }, 100);
            return true;
        };
    }

	  // limits the characters for text inputs and text editors
	  $("#contestName, #projectName, #newProjectName").bind('keydown keyup paste', limitContestProjectNameChars(200));
	  
	  $("#swFileDescription, #fileDescription, #fileDescription2").bind('keydown keyup paste', limitFileDescriptionChars(200));

      $("#newProjectDescription").bind('keydown keyup paste', limitFileDescriptionChars(2000));
});

var invalidCharsRegExp = /[^a-zA-Z0-9\$!\- ]+/mg;

/**
 * Limits the allowed chars to alphanumeric, $, and !
 * https://apps.topcoder.com/bugs/browse/TCCC-3091
 */
function limitContestProjectNameChars(maxChars) {
    var ori = "";
    var timeId = -1;
    return function(e) {
        var textArea = $(this);
        var content = textArea.val();
        var invalid = false;
        if (content.search(invalidCharsRegExp, '') > -1) {
            invalid = true;
        }
        if (content.length <= maxChars && !invalid) {
            ori = content;
        }
        if (timeId != -1) {
            timeId = clearTimeout(timeId);
        }
        timeId = setTimeout(function() {
            timeId = -1;
            if (invalid) {
                showErrors("Only alphanumeric, $, -, ! and space characters are allowed.");
                textArea.val(ori);
                return;
            }
            if (textArea.val().length > maxChars) {
                showErrors("You can only input max " + maxChars
                    + " characters.");
                textArea.val(ori);
            }
        }, 100);
        return true;
    };
}

Number.prototype.formatMoney = function(c, d, t){
    var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "," : d, t = t == undefined ? "." : t, s = n < 0 ? "-" : "", i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
    return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};

function isIntegerInput(input) {
    if(!input) return false;

    var number = parseInt(input, 10);
    if(input == "" || number != input) {
        return false;
    }

    return true;
}

/**
 * Modifies the jQuery param function so that it matches struts2 conversion.
 */
jQuery.extend({
	// Serialize an array of form elements or a set of
	// key/values into a query string
	param: function( a, traditional ) {
		var s = [];
		
		// Set traditional to true for jQuery <= 1.3.2 behavior.
		if ( traditional === undefined ) {
			traditional = jQuery.ajaxSettings.traditional;
		}
		
		// If an array was passed in, assume that it is an array of form elements.
		if ( jQuery.isArray(a) || a.jquery ) {
			// Serialize the form elements
			jQuery.each( a, function() {
				add( this.name, this.value );
			});
			
		} else {
			// If traditional, encode the "old" way (the way 1.3.2 or older
			// did it), otherwise encode params recursively.
			for ( var prefix in a ) {
				   buildParams( prefix, a[prefix] );
			}
		}

		// Return the resulting serialization
		return s.join("&").replace(/%20/g, "+");

		function buildParams( prefix, obj ) {
			if(typeof obj == 'function') {
				  return;
			}
			
      if (prefix.match(/.*properties$/)) {
				jQuery.each( obj, function( i, v ) {
					 buildParams( prefix  +  "['"+ i +"']"  , v );
				}); 
				return;
			}			
			
			if ( jQuery.isArray(obj) ) {
				// Serialize array item.
				jQuery.each( obj, function( i, v ) {
					if ( traditional ) {
						// Treat each array item as a scalar.
						add( prefix, v );
					} else {
						// If array item is non-scalar (array or object), encode its
						// numeric index to resolve deserialization ambiguity issues.
						// Note that rack (as of 1.0.0) can't currently deserialize
						// nested arrays properly, and attempting to do so may cause
						// a server error. Possible fixes are to modify rack's
						// deserialization algorithm or to provide an option or flag
						// to force array serialization to be shallow.
						buildParams( prefix  + ( typeof v === "object" || jQuery.isArray(v) ? "["+ i +"]" : "" ) , v );
						//keep adding
						//add( prefix, v );
					}
				});
					
			} else if ( !traditional && obj != null && typeof obj === "object" ) {
				// Serialize object item.
				jQuery.each( obj, function( k, v ) {
					//skip directjsXX properties
					if(!(k + "").match(/^directjs.*$/)) {
					  buildParams( prefix + "." + k + "", v );
				  }
				});
					
			} else {
				// Serialize scalar item.
				add( prefix, obj );
			}
		}

		function add( key, value ) {
			// If value is a function, invoke it and return its value
			value = jQuery.isFunction(value) ? value() : value;
			s[ s.length ] = encodeURIComponent(key) + "=" + encodeURIComponent(value);
		}
	}	
});

function guidGenerator() {
    var S4 = function() {
       return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}


/* TC tips JQuery plug-in. */
$.fn.tctip = function(s) {
    s = $.extend({
        title  : '',
        content: ''
    }, s || {});
    if ($(this).css('position') != 'absolute') {
        $(this).css('position', 'relative');
    }
    $(this).css('overflow', 'visible');
    var hover = $('<div class="tctips"><div class="triangle"></div><h2 class="tipsTitle">' + s.title + '</h2><p class="tipsContent">' + s.content + '</p></div>');
    $(this).append(hover);
    var top = $(this).outerHeight() + 11;
    
    if(navigator.userAgent.indexOf("Chrome") != -1) {
        hover.css({
            top: top + 'px',
            left: '0px'
        });
    } else {
        hover.css({
            top: top + 'px',
            left: '-28px'
        });
    }

    var leftNew, topNew;

    leftNew = hover.offset().left;
    topNew = hover.offset().top;
    var id = guidGenerator();
    hover.attr('id', id)
      .appendTo('body')
      .css({
        'position': 'absolute',
        'left': leftNew + 'px',
        'top': topNew + 'px'
      });

    hover.css('display', 'none');

    $(this).data('tipId', id);

    $(this).hover(
        function() {
            var h = $(this).data('tipId');
            $("#" + h).show();
        },
        function() {
            var h = $(this).data('tipId');
            $("#" + h).hide();
        }
    );

    return this;
};

/**
 * Defines the date format used for date picker.
 */
Date.format = 'mm/dd/yyyy';


/**
 * Global Functions/Objects
 */
/*
 * context path
 */ 
var ctx = "/direct";
 
/*
 * Initiate a dialog box using jQuery dialog.
 */
function initDialog(dialogDivId, width) {
    $("#" + dialogDivId).each(function() {
        $(this).dialog({
            bgiframe: true,
            width: width,
            height:'auto',
            modal: true,
            autoOpen: false,
            resizable: true,
            zIndex: 10 });

    });
}

function initDialog(dialogDivId, width, buttons) {
    $("#" + dialogDivId).each(function() {
        $(this).dialog({
            bgiframe: true,
            width: width,
            height:'auto',
            modal: true,
            autoOpen: false,
            resizable: true,
            zIndex: 10,
            buttons: buttons });

    });
}
 
 function closeDialog(dialogDivOrButton) {
   if(typeof dialogDivOrButton === "string") {
       $("#"+dialogDivOrButton).dialog('close');
   } else {
     //could be button object
     var button = dialogDivOrButton;
    $(button).parents("div.dialog-box:first").dialog("close");
   }
}

function clearDialog(dialogId) {
    $('#'+dialogId+ ' input[type!="checkbox"][type!="hidden"]').val("");
    $('#'+dialogId+ ' input[type="checkbox"]').attr("checked",false);
	$('#'+dialogId+ ' input[type="text"]').val("");
    $('#'+dialogId+ ' textarea').val("");
}

function checkRequired(value) {
    return $.trim(value).length > 0;
}

function checkNumber(value) {
    return optional(value) || /^\d+(\.\d+)?$/.test(value);
}

function checkFileType(value) {
	return checkRequired(value);
}

function optional(value) {
   return !checkRequired(value);
}

function isNotEmpty(value) {
  return checkRequired(value);
}


function isEmpty(value) {
  return !isNotEmpty(value);
}

function isDev() {
	return window.location.href.indexOf('cloud.topcoder.com') > -1;
}

function containTags(value) {
    return value.search(tagsRegExp) > 0;
}

/**
 * Formats the number in the money format.
 */
Number.prototype.formatMoney = function(c){
//for decimals
var d = '.';
//for thousands
var t = ',';
var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "," : d, t = t == undefined ? "." : t, s = n < 0 ? "-" : "", i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
   return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};

function sortSelectOptions(selectId) {
   var allOptions = $('#'+selectId+' option').get();
   allOptions.sort(function(a,b) {
      var compA = $(a).text().toUpperCase();
      var compB = $(b).text().toUpperCase();
      return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;   
   });
   $("#"+selectId).empty().append( allOptions );	
}

/**
 * Common function to handle JSON result.
 */
function handleJsonResult(jsonResult, successCallBack, failureCallBack) {
   modalClose(); // close the potentical preloading modal first
   if(jsonResult.result) {
       successCallBack(jsonResult.result['return']);
   } else {
       failureCallBack(jsonResult.error.errorMessage);
   }
}

function handleJsonResult2(jsonResult, successCallBack, failureCallBack) {
   if(jsonResult.result) {
       successCallBack(jsonResult.result['return']);
   } else {
       failureCallBack(jsonResult.error.errorMessage);
   }
}

/**
 * Functions to handle message/error messages.
 */
function showMessage(message) {
   $('#msgDialog p').html(message);
   $('#msgDialog').dialog('open');
}

/**
 * Shows the sucessful message after operation finished.
 *
 * @param message the message to show.
 * @since 1.3
 */
function showSuccessfulMessage(message) {
    displayOperationSuccess("#demoModal", "Message", message);
}

function showSuccessfulMessageWithOperation(message, text, handler) {
    displayOperationSuccess("#demoModal", "Message", message, text, handler);
}


/**
 * Shows the warning message after operation finishes
 *
 * @param message the message to show
 * @param buttonText the button text for confirming warning YES button
 * @param buttonEvent the button event for confirming warning YES button
 * @since 1.3
 */
function showWarningMessage(message, buttonText, buttonEvent) {
    displayWarning("#demoModal", "Warning", message, buttonText, buttonEvent);
}

/**
 * Shows the general error if an error happends
 */
function showGeneralError() {
   showServerError("Error occurred. Please retry the operation later.");
}

/**
 * Show client side validation errors.
 *
 * @param errors the error message array
 */
function showErrors(errors) {
   if(typeof errors == 'string') {
       var singleError = errors;
       errors = new Array();
       errors.push(singleError);
   }

    if(errors.length == 0) {
        errors.push("Error occurred. Please retry the operation later.")
    }

    if (errors.length == 1) {
        $("#demoModal li").css('padding-top', '10px');
    }

   displayClientSideError("#demoModal", "Errors", errors);
}

function showComingSoon(message) {
    displayComingSoonMessage("#demoModal", "Coming Soon", message);
}

/**
 * Shows the server error modal window when an error raised on server side.
 *
 * @param message the message to display
 * @since 1.3
 */
function showServerError(message) {
    if($.trim(message).length <= 0) {
        message = "Server Error occurred. Please retry the operation later."
    }
    displayServerError("#demoModal", "Server Error", message);
}

/**
 * Shows the confirmation modal window when needs user's confirmation.
 *
 * @param title the title of the modal window
 * @param message the message to display to user
 * @param yesText the button text for the confirmation YES
 * @param yesHandler the button event for the confirmation YES
 * @since 1.3
 */
function showConfirmation(title, message, yesText, yesHandler) {
    displayUserConfirmation("#demoModal", title, message, yesText, yesHandler);
}

/**
 * Clear the add new project modal window.
 *
 * @since 1.3
 */
function clearAddNewProjectForm() {
    $('#addNewProjectModal').find('input[name="newProjectName"]').val('');
    $('#addNewProjectModal').find('textarea[name="newProjectDescription"]').val('');
}

/*BUGR-4512*/
function adjustImageRatio() {
    if ($("a.thumbSingle img").length > 0) {
        var oldWidth = $("a.thumbSingle img").width();
        var oldHeight = $("a.thumbSingle img").height();
        var image = new Image();
        image.src = $("a.thumbSingle img").attr("src");
        setTimeout(function() {
            caculateImageRatio(image,oldWidth, oldHeight);
        },1000);
        //make sure the new width and new height is set
        image.onload = function(){
            if(parseInt($("a.thumbSingle img").width()) == 0 || parseInt($("a.thumbSingle img").height()) == 0) {
                $("a.thumbSingle img").attr("width",oldWidth);
                $("a.thumbSingle img").attr("height",oldHeight);
                caculateImageRatio(image,oldWidth, oldHeight);
            }
        }
    }
}
/*BUGR-4512*/
function caculateImageRatio(image,oldWidth, oldHeight) {
    var origWidth = image.width;
    var origHeight = image.height;
    var newWidth , newHeight;
    if(origWidth/oldWidth >= origHeight/oldHeight) {
        newHeight = (origHeight*oldWidth)/origWidth;
        $("a.thumbSingle img").css("paddingTop", (oldHeight-newHeight)/2);
        $("a.thumbSingle img").attr("height",newHeight);
    } else {
        newWidth = (origWidth*oldHeight)/origHeight;
        $("a.thumbSingle img").css("paddingLeft", (oldWidth-newWidth)/2);
        $("a.thumbSingle img").attr("width",newWidth);
    }
}

var removeMoneySymbolsReg = /[\$,]/g;

var allowedTags = [
/<a\s*(href\s*=\s*[^=|^>|^<]*)?>/mg,
/<(span|ul)\s*(style\s*=\s*[^=|^>|^<]*)?>/mg,
/<(annot|abbr|acronym|blockquote|b|br|em|i|li|ol|p|pre|s|strike|sub|sup|strong|table|td|tr|tt|u|ul)\s*>/mg, 
/<\/\s*(a|span|annot|abbr|acronym|blockquote|b|br|em|i|li|ol|p|pre|s|strike|sub|sup|strong|table|td|tr|tt|u|ul)\s*>/mg
];
var tagsRegExp = /<(\/)*[^<|^>|^\/]*>/mg;

function sortDropDown(dropDownId) {
    // alert('sort ' + dropDownId);
    // get the select
    var $dd = $(dropDownId);
    if ($dd.length > 0) { // make sure we found the select we were looking for

        // save the selected value
        var selectedVal = $dd.val();

        // get the options and loop through them
        var $options = $('option', $dd);
        var arrVals = [];
        $options.each(function() {
            // push each option value and text into an array
            arrVals.push({
                val: $(this).val(),
                text: $(this).text()
            });
        });

        // sort the array by the value (change val to text to sort by text instead)
        arrVals.sort(function(a, b) {
            if (a.val == 0 || a.val == 'All') {
                return -1;
            }
            if (b.val == 0 || b.val == 'All') {
                return 1;
            }

            if (a.text > b.text) {
                return 1;
            }
            else if (a.text == b.text) {
                return 0;
            }
            else {
                return -1;
            }
        });

        // loop through the sorted array and set the text/values to the options
        for (var i = 0, l = arrVals.length; i < l; i++) {
            $($options[i]).val(arrVals[i].val).text(arrVals[i].text);
        }

        // set the selected value back
        $dd.val(selectedVal);
    }
}

window.multiRowsDivID = 1;
/**
 * Build the HTML content to restrict the max rows of an array content.
 * @param rows the array containing the content.
 * @param maxrows the max rows.
 * @return the HTML content.
 */
function tooltipOnMultiRows(rows, maxrows) {
    if (!maxrows) maxrows = 4;
    if (rows.length <= maxrows) return rows.join('<br/>');
    var divId = 'multirows_tip_id_' + window.multiRowsDivID;
    var html = '<div class="multirowsTip" rel="' + window.multiRowsDivID + '">';
    var c = [];
    for (var i = 0; i < maxrows; i++) c.push(rows[i]);
    c.push('<strong>...</strong>');
    html += c.join('<br/>') + '</div>';
    html += '<div id="multirows_content_' + window.multiRowsDivID + '" style="display:none;">' + rows.join('<br/>') + '</div>';
    window.multiRowsDivID++;
    return html;
}
/**
 * Set up the mouseover event for the multi rows tooltip.
 */
function tooltipOnMultiRowsEvents() {
    var tips = $(".multirowsTip");
    if (tips.length == 0) return;
    tips.each(function() {
        $(this).tooltip({
            tip: '#namesRowsToolTip',
            delay:0,
            onBeforeShow: function(){
                this.getTip().find(".tooltipContent").html($("#multirows_content_" + $(this.getTrigger()).attr('rel')).html());
            }
        });
    });
}
/**
 * Build the multi rows HTML content and set up the tooltip mouseover event.
 */
function updateMultiRowsCell() {
    $(".multirowsCell").each(function() {
        var content = $(this).html().trim();
        var lines = content.split("<br>");
        if (lines.length < 2) lines = content.split("<br/>");
        if (lines.length < 2) lines = content.split("<BR/>");
        if (lines.length < 2) lines = content.split("<BR>");
        var rows = [];
        for (var i = 0; i < lines.length; i++) {
            var row = lines[i].trim();
            if (row.length > 0) rows.push(row);
        }
        if($(this).hasClass('alignCenter')) {
        	$(this).html(tooltipOnMultiRows(rows, 2));
        } else {
        	$(this).html(tooltipOnMultiRows(rows));
        }
    });
    tooltipOnMultiRowsEvents();
}

(function($){
    $.fn.serializeObject = function(){

        var self = this,
            json = {},
            push_counters = {},
            patterns = {
                "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)\])*$/,
                "key":      /[a-zA-Z0-9_]+|(?=\[\])/g,
                "push":     /^$/,
                "fixed":    /^\d+$/,
                "named":    /^[a-zA-Z0-9_]+$/
            };


        this.build = function(base, key, value){
            base[key] = value;
            return base;
        };

        this.push_counter = function(key){
            if(push_counters[key] === undefined){
                push_counters[key] = 0;
            }
            return push_counters[key]++;
        };

        $.each($(this).serializeArray(), function(){

            // skip invalid keys
            if(!patterns.validate.test(this.name)){
                return;
            }

            var k,
                keys = this.name.match(patterns.key),
                merge = this.value,
                reverse_key = this.name;

            while((k = keys.pop()) !== undefined){

                // adjust reverse_key
                reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                // push
                if(k.match(patterns.push)){
                    merge = self.build([], self.push_counter(reverse_key), merge);
                }

                // fixed
                else if(k.match(patterns.fixed)){
                    merge = self.build([], k, merge);
                }

                // named
                else if(k.match(patterns.named)){
                    merge = self.build({}, k, merge);
                }
            }

            json = $.extend(true, json, merge);
        });

        return json;
    };
})(jQuery);

jQuery.fn.outerHTML = function () {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

(function ($) {
    $.fn.ellipsis = function () {
        return this.each(function () {
            var el = $(this);

            if (el.css("overflow") == "hidden") {
                var text = el.html();
                var multiline = el.hasClass('multiline');
                var t = $(this.cloneNode(true))
                        .hide()
                        .css('position', 'absolute')
                        .css('overflow', 'visible')
                        .width(multiline ? el.width() : 'auto')
                        .height(multiline ? 'auto' : el.height())
                    ;

                el.after(t);

                function height() {
                    return t.height() > el.height();
                };
                function width() {
                    return t.width() > el.width();
                };

                var func = multiline ? height : width;

                while (text.length > 0 && func()) {
                    text = text.substr(0, text.length - 1);
                    t.html(text + "...");
                }

                el.html(t.html());
                t.remove();
            }
        });
    };
})(jQuery);
