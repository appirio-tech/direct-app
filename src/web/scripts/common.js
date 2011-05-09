/**
 * Common JS Script
 *
 * Version 1.1 Direct - Repost and New Version Assembly change note
 * - Add some functions for modal pop ups
 * Version 1.2 Direct Improvements Assembly Release 2 Assembly change note
 * - Add character limitation for the input fields and input areas when creating contests.
 *
 * @author TCSDEVELOPER
 * @version 1.2(Direct Improvements Assembly Release 2)
 * @since Launch Contest Assembly - Studio
 */
$(document).ready(function() {
       /*BUGR-4512*/
      adjustImageRatio();

	 function limitFileDescriptionChars(maxChars) {
			var ori="";
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
						alert("You can only input max " + maxChars
								+ " characters.");
						textArea.val(ori);
					}
				}, 100);
				return true;
			};
	}
		
	var invliadCharsRegExp = /[^a-zA-Z0-9\$!\- ]+/mg;
	/**
	 * Limits the allowed chars to alphanumeric, $, and !
	 * https://apps.topcoder.com/bugs/browse/TCCC-3091
	 */
	function limitContestProjectNameChars(maxChars) {
			var ori="";
			var timeId = -1;
			return function(e) {
				var textArea = $(this);
				var content = textArea.val();
				var invalid = false;
				if(content.search(invliadCharsRegExp, '') > -1) {
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
					if(invalid) {
						alert("Only alphanumeric, $, -, ! and space characters are allowed.");
						textArea.val(ori);
						return;
					}
					if (textArea.val().length > maxChars) {
						alert("You can only input max " + maxChars
								+ " characters.");
						textArea.val(ori);
					}
				}, 100);
				return true;
			};
		}

	  // limits the characters for text inputs and text editors
	  $("#contestName, #projectName").bind('keydown keyup paste', limitContestProjectNameChars(200));
	  
	  $("#swFileDescription, #fileDescription").bind('keydown keyup paste', limitFileDescriptionChars(200));
});


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
    $("#"+dialogDivId).each(function() {
        $(this).dialog({
          bgiframe: true,
          width: width,
          height:'auto',
          modal: true,
          autoOpen: false,
          resizable: false,
          zIndex: 10});
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
}

function checkRequired(value) {
    return $.trim(value).length > 0;
}

function checkNumber(value) {
    return optional(value) || /^\d+(\.\d+)?$/.test(value);
}

function checkFileType(value) {
	  value = $.trim(value);
    return optional(value) || /^\w+$/.test(value);
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
   if(jsonResult.result) {
       successCallBack(jsonResult.result['return']);
   } else {
       failureCallBack(jsonResult.error.errorMessage);
   }
}	

/**
 * Functions to hanle message/error messages.
 */
function showMessage(message) {
   $('#msgDialog p').html(message);
   $('#msgDialog').dialog('open');
}

function showGeneralError() {
   showErrors("Error occurred! Please retry it later.");
}

function showErrors(errors) {
   if(typeof errors == 'string') {
       var singleError = errors;
       errors = new Array();
       errors.push(singleError);
   }

   $('#errorDialog p').html('<ul></ul>');
   $.each(errors,function(i, error) {
        $('#errorDialog ul').append('<li>' + error + '</li>');
   });
   $('#errorDialog').dialog('open');
}

/*BUGR-4512*/
function adjustImageRatio() {
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

/**
 * Return the event handler used by tinyMCE to restrict the max characters.
 * 
 * @param obj the tinyMCE name
 * @param maxChars the max characters
 */
function maxCharsEventHandler(obj, maxChars) {
    var ori;
    var timeId = -1;
    return function(e) {
        var content = tinyMCE.get(obj).getContent();
        if (content.length <= maxChars) {
            ori = content;
        }
        if (timeId != -1) {
            timeId = clearTimeout(timeId);
        }
        timeId = setTimeout(function() {
            timeId = -1;
            if (tinyMCE.get(obj).getContent().length > maxChars) {
            	showErrors("You can only input max " + maxChars + " characters.");
                tinyMCE.get(obj).setContent(ori);
            }
        }, 100);
        return true;
    };
}

var allowedTags = [
/<a\s*(href\s*=\s*[^=|^>|^<]*)?>/mg,
/<img\s*((src|height|width)\s*=\s*[^=|^>|^<|^\s]*\s*)*>/mg, 
/<span\s*(style\s*=\s*[^=|^>|^<]*)?>/mg,
/<font\s*((color|size)\s*=\s*[^=|^>|^<|^\s]*\s*)*>/mg, 
/<(annot|abbr|acronym|blockquote|b|br|em|i|li|ol|p|pre|s|strike|sub|sup|strong|table|td|tr|tt|u|ul)\s*>/mg, 
/<\/\s*(a|img|span|font|annot|abbr|acronym|blockquote|b|br|em|i|li|ol|p|pre|s|strike|sub|sup|strong|table|td|tr|tt|u|ul)\s*>/mg
];
var tagsRegExp = /<(\/)*[^<|^>|^\/]*>/mg;
 /**
 * Return the event handler used by tinyMCE to restrict the max characters and the allowed tags.
 * 
 * @param obj the tinyMCE name
 * @param maxChars the max characters
 */
function maxCharsAndAllowedTagsEventHandler(obj, maxChars) {
    var ori='';
    var timeId = -1;
    return function(e) {
        var content = tinyMCE.get(obj).getContent();
		var invalid = false;
        if (content.length <= maxChars) {
			var c = content;
			c = c.replace(/<|>/mg, "").replace(/&lt;/mg, "<").replace(/&gt;/mg, ">").replace(/&amp;lt;/mg, "<").replace(/&amp;gt;/mg, ">");
			for(var i = 0; i < allowedTags.length; i++) {
				c = c.replace(allowedTags[i], '');
			}
			if(c.search(tagsRegExp) < 0) { // no invalid tags 
				ori = content;
			} else {
				invalid = true;
			}
        }
        if (timeId != -1) {
            timeId = clearTimeout(timeId);
        }
        timeId = setTimeout(function() {
            timeId = -1;
			if(invalid) {
				showErrors("You have inputted non-allowed tags or invalid attributes.");
                tinyMCE.get(obj).setContent(ori);
			}
            if (tinyMCE.get(obj).getContent().length > maxChars) {
            	showErrors("You can only input max " + maxChars + " characters.");
                tinyMCE.get(obj).setContent(ori);
            }
        }, 100);
        return true;
    };
}
