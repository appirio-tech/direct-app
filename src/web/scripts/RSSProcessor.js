/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
// build the "namespaces"
if (!js) {
    var js = {};
}
if (!js.topcoder) {
    js.topcoder = {};
}
if (!js.topcoder.rss) {
    js.topcoder.rss = {};
}
if (!js.topcoder.rss.template) { //build namespace:js.topcoder.rss.template
    js.topcoder.rss.template = {};
}
if (!js.topcoder.rss.template.tagprocessors) { //build namespace:js.topcoder.rss.template
    js.topcoder.rss.template.tagprocessors = {};
}
/**
 * @fileoverview
 *
 * <p>
 * The JavaScript RSS Template component will provide an easy way to take RSS
 * data output it graphically to HTML based on a template. The component will
 * take a user-specified template and for each record in the RSS given, will
 * output the string filled with the data from the RSS.
 * </p>
 *
 * <p>
 * <strong>Sample usage of this component:</strong>
 * <pre>
 * 1).Create RSSProcessor with data passed directly to component.
 * var rss = ".."; // HERE'S CONTENT OF RSS-FILE DEFINED ABOVE
 * var template = ".."; // HERE'S CONTENT OF TEMPLATE-FILE DEFINED ABOVE
 * var processor = new js.topcoder.rss.template.RSSProcessor(true, template);
 * document.write(processor.transformRSSText(rss));
 *
 *
 * 2).Create RSSProcessor with data loaded in AJAX request.
 * var rss = "http://somedomain.com/feed.rss";
 * var template = "http://somedomain.com/feed.template";
 * var processor = new js.topcoder.rss.template.RSSProcessor(false, template);
 * document.write(processor.transformRSSFeed(rss));
 * </pre>
 * </p>
 *
 * <p>
 * <strong> Thread safety:</strong>
 * The user can simply manually allow the
 * execution of this JavaScript (which is totally safe), or setup his/her
 * browser to allow scripts for concrete domains or always.
 * </p>
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
/*--------------------------------------------------Helper Class Separator -------------------------------------------*/
/**
 * Constructs a new js.topcoder.rss.template.UtilityClass instance.
 * @constructor
 *
 * <p> This class is just a Utility Class for this component which has common function used in
 * all the classes like argument validation etc.. As javascript doesn't support private classes,
 * this class is assumed to be private and this will be used only in this component.
 * @returns A new Utility Object.
 * </p>
 *
 * @namespace js.topcoder.rss.template
 * @class UtilityClass
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is thread safe as it is immutable.
 * </p>
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.UtilityClass = function(){
};
/**
 * <p>
 * Helper function to check whether the given obj is the excepted type.
 * </p>
 *
 * <p>
 * In this component, the 'function' parameter can be null, and the type of the
 * null is 'object', so when this condition occurs, just return.
 * </p>
 *
 * <p>
 * It can be used in a static manner.
 * </p>
 *
 * @param {Object} obj the obj to be checked.
 * @param {String} type the type excepted
 * @param {String} name the name of the obj.
 *
 * @throws IllegalArgumentException if the given obj is not excepted type( if the type is 'function' and the
 *           typeof(obj) =='object' and obj == null, just pass it).
 */
js.topcoder.rss.template.UtilityClass.checkType = function(obj, type, name){
    //if it is null
    if (type == "function" && typeof(obj) == "object" && obj == null) {
        return;
    }
    if (typeof(obj) != type) {
        throw new js.topcoder.rss.template.IllegalArgumentException("The [ " + name + " ]" + "should be type [ " + type + " ], but it's type is: " + typeof(obj));
    }
};
/**
 * <p>
 * Helper function to check whether the given obj is null or not, also check whether it is excepted type.
 * </p>
 *
 * <p>
 * It can be used in a static manner.
 * </p>
 *
 * @param {Object} obj the object to be checked.
 * @param {String} type the type excepted.
 * @param {String} name the name of the object.
 *
 * @throws IllegalArgumentException if the given obj is null or obj is not excepted type.
 */
js.topcoder.rss.template.UtilityClass.checkNull = function(obj, type, name){
    //check type
    js.topcoder.rss.template.UtilityClass.checkType(obj, type, name);

    //check null
    if (obj == null) {
        throw new js.topcoder.rss.template.IllegalArgumentException("The [ " + name + " ] can NOT be null.");
    }
};
/**
 * Trims the string.
 *
 * @param {String} str the string to be trimmed.
 * @returns the trimmed lowercase string.
 * @type String
 */
js.topcoder.rss.template.UtilityClass.trim = function(str){
    return str.replace(/^\s*|\s*$/g, "");
};
/**
 * <p>
 * Helper function to check whether the given String is null/empty or not, also check whether it is
 * 'string' type.
 * </p>
 *
 * <p>
 * It can be used in a static manner.
 * </p>
 *
 * @param {String} string the string to be checked.
 * @param {String} type the expected type
 * @param {String} name the name of the string.
 *
 * @throws IllegalArgumentException if the given string is null/empty or it is not 'string' type.
 */
js.topcoder.rss.template.UtilityClass.checkString = function(string, type, name){
    //check null and type
    js.topcoder.rss.template.UtilityClass.checkNull(string, type, name);

    //check empty
    if (js.topcoder.rss.template.UtilityClass.trim(string) == "") {
        throw new js.topcoder.rss.template.IllegalArgumentException("The [ " + name + " ] can NOT be empty.");
    }
};

/**
 * Check the separator [% and %]. If any of them do not exist, throw ProcessingException.
 *
 * @param {String} tag the template for checking
 * @throws ProcessingException f any of [%, %] do not exist
 */
js.topcoder.rss.template.UtilityClass.checkSaparator = function(tag){
    if (tag.indexOf("[%") < 0) {
        throw new js.topcoder.rss.template.ProcessingException("Can not find separator: [% in the template!");
    }
    if (tag.indexOf("%]", tag.indexOf("[%")) < 0) {
        throw new js.topcoder.rss.template.ProcessingException("Can not find separator: %] in the template!");
    }
}
/*--------------------------------------------------Helper Class Separator -------------------------------------------*/
/*--------------------------------------------------Exception Separator ----------------------------------------------*/
/**
 * Constructs a new IllegalArgumentException instance.
 * @constructor
 * @param {String} sReason a string describing the error.
 *
 * <p>
 * This is an exception for reporting problems with methods' arguments data.
 * This exception will be thrown
 * if any argument value of methods is invalid. For example, null or undefined if it is not allowed
 * type mismatch with the desired data type, etc. The external Javascript code will receive this exception
 * if it provided incorrect arguments to the methods of our component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is not thread safe as it mutable and base class is not thread safe.
 * This is a browser-based component, and so thread-safety is handled by the browser.
 * Thread safety is also not a requirement of this component.
 * </p>
 *
 * @namespace js.topcoder.rss.template
 * @class IllegalArgumentException
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.IllegalArgumentException = function(sReason){
    /**
     * This field represents the reason of which the exception caused. No
     * restrictions are for the value of this field. It is initialized in the
     * constructor and never changed later.
     *
     * Accessed: through corresponding getter.
     * @type {String}
     * @private
     */
    var reason;

    /**
     * This is a simple getter for this.reason field.
     *
     * @returns {String} the reason of the exception
     */
    this.getReason = function(){
        return reason;
    };

    /**
     * This constructor creates the exception with the given reason. No restrictions
     * are given for the value of the reason.
     */
    // constructor code - sets message field
    reason = ((typeof(sReason) != "string")) ? "" : sReason;
};
/*--------------------------------------------------Exception Separator ----------------------------------------------*/
/*--------------------------------------------------Exception Separator ----------------------------------------------*/
/**
 * Constructs a new AJAXFetchException instance.
 * @constructor
 * @param {String} sReason a string describing the error.
 *
 * <p>
 * This is an exception for reporting problems ajax data requests. This
 * exception will be thrown in ContentProvider.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is not thread safe as it mutable and base class is not thread safe.
 * This is a browser-based component, and so thread-safety is handled by the browser.
 * Thread safety is also not a requirement of this component.
 * </p>
 *
 * @namespace js.topcoder.rss.template
 * @class AJAXFetchException
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.AJAXFetchException = function(sReason){
    /**
     * This field represents the reason of which the exception caused. No
     * restrictions are for the value of this field. It is initialized in the
     * constructor and never changed later.
     *
     * Accessed: through corresponding getter.
     * @type {String}
     * @private
     */
    var reason;

    /**
     * This is a simple getter for this.reason field.
     *
     * @returns {String} the reason of the exception
     */
    this.getReason = function(){
        return reason;
    };

    /**
     * This constructor creates the exception with the given reason. No restrictions
     * are given for the value of the reason.
     */
    // constructor code - sets message field
    reason = ((typeof(sReason) != "string")) ? "" : sReason;
};
/*--------------------------------------------------Exception Separator ----------------------------------------------*/
/**
 * Constructs a new ProcessingException instance.
 * @constructor
 * @param {String} sReason a string describing the error.
 *
 * <p>
 * This is an exception for reporting problems with template format
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is not thread safe as it mutable and base class is not thread safe.
 * This is a browser-based component, and so thread-safety is handled by the browser.
 * Thread safety is also not a requirement of this component.
 * </p>
 *
 * @namespace js.topcoder.rss.template
 * @class ProcessingException
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.ProcessingException = function(sReason){
    /**
     * This field represents the reason of which the exception caused. No
     * restrictions are for the value of this field. It is initialized in the
     * constructor and never changed later.
     *
     * Accessed: through corresponding getter.
     * @type {String}
     * @private
     */
    var reason;

    /**
     * This is a simple getter for this.reason field.
     *
     * @returns {String} the reason of the exception
     */
    this.getReason = function(){
        return reason;
    };

    /**
     * This constructor creates the exception with the given reason. No restrictions
     * are given for the value of the reason.
     */
    // constructor code - sets message field
    reason = ((typeof(sReason) != "string")) ? "" : sReason;
};
/*------------------------------------------- Abstract Class Separator ------------------------------------------ ----*/
/**
 * Construct a new AbstractTagProcessor instance.
 *
 * @constructor
 * @param {String} tagText tag text to transform. Can't be undefined, empty, null.
 * @param {JSONObject} rss RSS data that is used to fill template. Can't be undefined, null.
 * @param {Number} item index of current feed item (if any). be undefined but cannot be null.
 *
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 *
 * <p>
 * This abstract class represents processor that transforms a tag using provided
 * rssData.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> This class is immutable and thread-safe.
 * Anyway, there're no thread synchronization features in JavaScript.
 * </p>
 *
 * @namespace js.topcoder.rss.template
 * @class AbstractTagProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.AbstractTagProcessor = function(tagText, rss, item){
    /**
     * Index of current feed item (if any). Is used when processing is inside
     * foreach block.
     *
     * Is set in constructor, accessed in process() method.
     *
     * @type {Number}
     * @private
     */
    var currentItem;

    /**
     * Text that needs to be transformed. Can't be undefined, null, empty.
     *
     * Is set in constructor, accessed in process() method.
     *
     * @type {String}
     * @private
     */
    var tag;

    /**
     * RSS data that is used to fill template. Can't be undefined, null.
     *
     * @type {JSONObject}
     * @private
     */
    var rssData;

    /**
     * Get the index of current feed item.
     *
     * @returns {Number} Index of current feed item.
     */
    this.getCurrentItem = function(){
        return currentItem;
    }

    /**
     * Get the text that needs to be transformed.
     *
     * @returns {String} Text that needs to be transformed.
     */
    this.getTag = function(){
        return tag;
    }

    /**
     * Get RSS data that is used to fill template.
     *
     * @returns {JSONObject} RSS data that is used to fill template.
     */
    this.getRssData = function(){
        return rssData;
    }

    /**
     * Implementation of this method will return transformed text.
     *
     * @returns {String} transformed text.
     * @throws ProcessingException if tag is not well-formed.
     *
     * @abstract
     */
    this.process = function(){
    }

    /**
     * Simple constructor that sets all attributes.
     *
     * @throws ProcessingException if tag is not well-formed.
     * Provide a meaningful reason, like "tag is not well-formed: " + tag.
     * @abstract
     */
    js.topcoder.rss.template.UtilityClass.checkString(tagText, "string", "tagText");
    js.topcoder.rss.template.UtilityClass.checkNull(rss, "object", "rss");
    if (item !== undefined) {
        js.topcoder.rss.template.UtilityClass.checkNull(item, "number", "item");
    }

    tag = tagText;
    rssData = rss;
    currentItem = item;
}
/*------------------------------------------- Abstract Class Separator ---------------------------------------------*/
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new TemplateProcessor instance.
 * @constructor
 *
 * <p>This helper class processes template text and fills it with rss data.</p>
 *
 * <p><strong>Thread-safety: </strong>This class is stateless and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template
 * @class TemplateProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.TemplateProcessor = function(){
}
/**
 * This static method fills template with rss data.
 *
 * @param {String} template template to fill. Can't be undefined, empty, null.
 * @param {JSONObject} rssData RSS data that is used to fill template. Can't be undefined,
 * null.
 * @param {Number} currentItem index of current feed item (if any). Can be undefined
 * but can not be null.
 * @returns {String} the processed RSS data by the given template.
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 * @throws ProcessingException if template is not well formatted. Provide a
 * meaningful exception like "Template is not well-formed".
 * @static
 */
js.topcoder.rss.template.TemplateProcessor.process = function(template, rssData, currentItem){
    js.topcoder.rss.template.UtilityClass.checkString(template, "string", "template");
    js.topcoder.rss.template.UtilityClass.checkNull(rssData, "object", "rssData");
    if (currentItem !== undefined) {
        js.topcoder.rss.template.UtilityClass.checkNull(currentItem, "number", "currentItem");
    }

    //Initialize result with "".
    var res = "";
    var firstTagPosition = template.indexOf("[%"); //check the index to see whether the tag exits
    while (firstTagPosition >= 0) {
        //indicate whether the tag is a for each tag
        var isForEachTag = (template.indexOf("[% foreach item %]", firstTagPosition) == firstTagPosition || 
			    template.indexOf("[% foreach item limit ", firstTagPosition) == firstTagPosition);
        //indicate whether the tag is an if tag
        var isConditionalTag = (template.indexOf("[% if", firstTagPosition) == firstTagPosition);
        var isDateTag; //indicate whether the tag is a date tag
        var endTagPosition; //the corresponding closing tag position
        if (isForEachTag) {
            endTagPosition = template.indexOf("[% /foreach %]", firstTagPosition);
            if (endTagPosition < 0) {
                throw new js.topcoder.rss.template.ProcessingException(
                    "Template is not well-formed, for there is no close tag for [% foreach item %]");
            }
        } else if (isConditionalTag) {
            endTagPosition = template.indexOf("[% /if %]", firstTagPosition);
            if (endTagPosition < 0) {
                throw new js.topcoder.rss.template.ProcessingException(
                    "Template is not well-formed, for there is no close tag for [% if {value}{>,<,=}'{some date}' %]");
            }
        } else {
            endTagPosition = template.indexOf("%]", firstTagPosition);
            if (endTagPosition < 0) {
                throw new js.topcoder.rss.template.ProcessingException(
                    "Template is not well-formed, for there is no close tag for [%= %]");
            }
            isDateTag = (template.slice(firstTagPosition, endTagPosition)).indexOf("format:") > 0;
        }

        //set the closing position to the end of "%]"
        endTagPosition = template.indexOf("%]", endTagPosition) + 2;

        //divide the template into three parts: before tag, tag, and after tag.
        var beforeTag = template.slice(0, firstTagPosition);
        var tag = template.slice(firstTagPosition, endTagPosition);

        var processor; //the processor to deal with the separator template
        if (isForEachTag) {
            processor = new js.topcoder.rss.template.tagprocessors.ForeachTagProcessor(tag, rssData, currentItem);
        } else if (isConditionalTag) {
            processor = new js.topcoder.rss.template.tagprocessors.ConditionTagProcessor(tag, rssData, currentItem);
        } else if (isDateTag) {
            processor = new js.topcoder.rss.template.tagprocessors.DateFormatTagProcessor(tag, rssData, currentItem);
        } else {
            processor = new js.topcoder.rss.template.tagprocessors.ValueTagProcessor(tag, rssData, currentItem);
        }

        res += beforeTag;
        res += processor.process();
        template = template.slice(endTagPosition);

        //reset the start position of a tag
        firstTagPosition = template.indexOf("[%");
    }

    return res + template;
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new ContentProvider instance.
 * @constructor
 *
 * <p>Simple helper class that gets content of file or feed by its URL. It
 * leverages the work to AJAXProcessor.</p>
 *
 * <p><strong>Thread-safety: </strong>This class is stateless and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template
 * @class ContentProvider
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.ContentProvider = function(){
}
/**
 * Static method that will be called outside the class. It returns file
 * content by its url in synchronous manner.
 *
 * @param {String} url URL to the file to get. Can't be empty, undefined or
 * null.
 * @returns {String} the gotten RSS data.
 * @throws IllegalArgumentException if url is empty, null or undefined;
 * @throws AJAXFetchException if processor request wasn't successful. Please,
 * provide a meaningful reason, like "Failure while loading file" + url.
 * @static
 */
js.topcoder.rss.template.ContentProvider.getURLContent = function(u){
    js.topcoder.rss.template.UtilityClass.checkString(u, "string", "url");

    var processor = new AJAXProcessor();
    try {
        processor.request({
            url: u,
            async: false,
			responseType:"text"
        });
    } catch (Error) {
        throw new js.topcoder.rss.template.AJAXFetchException("Failure while loading file: " + u + "Error:" + Error);
    }
    return processor.getResponseText();
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new SimpleRSSParser instance.
 * @constructor
 *
 * <p>This class is responsible for parsing rss data into JSONObject. The format of
 * resulting JSONObject must be as follows:
 * res = {'channel' : {"attribute1" : "attributeValue1", "attribute2" :
 * "attributeValue2", "attribute3" : "attributeValue3", ..."attributeX" :
 * "attributeValueX", },"items" : { { "attribute1" : "attributeValue1",
 * "attribute2" : "attributeValue2", "attribute3" : "attributeValue3",
 * ..."attributeX" : "attributeValueX", } ... {"attribute1" : "attributeValue1",
 * "attribute2" : "attributeValue2", "attribute3" : "attributeValue3",
 * ..."attributeX" : "attributeValueX", } }}
 *
 * Currently only simple elements are supported:
 * http://forums.topcoder.com/?module=Thread&threadID=619004&start=0.</p>
 *
 * <p><strong>Thread-safety: </strong>This class is stateless and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template
 * @class SimpleRSSParser
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.SimpleRSSParser = function(){
}
/**
 * This function will parse the whole rss feed text and return JSONObject.
 *
 * @param {String} rss Rss feed text. Can't be null, undefined, empty.
 * @returns {JSONObject} the specified Json Object relative to the RSS data.
 * @throws ProcessingException if channel tag is not found.
 * @throws IllegalArgumentException if parameters do not hold required values.
 */
js.topcoder.rss.template.SimpleRSSParser.parseRSS = function(rss){
    js.topcoder.rss.template.UtilityClass.checkString(rss, "string", "rss");

    //Get everything inside <channel> tag
    var channelStart = rss.indexOf("<channel>");
    if (channelStart < 0) {
        throw new js.topcoder.rss.template.ProcessingException(
            "Can not find start tag: <channel> for channel element.");
    }
    var channelEnd = rss.indexOf("</channel>", channelStart);
    if (channelEnd < 0) {
        throw new js.topcoder.rss.template.ProcessingException(
            "Can not find end tag: </channel> for channel element.");
    }

    return js.topcoder.rss.template.SimpleRSSParser.parseChannel(
        rss.slice(channelStart + "<channel>".length, channelEnd));
}

/**
 * This method parses contents of <channel> tag.
 *
 * @param {String} channel Content of channel tag. Can't be null or undefined.
 * @returns {JSONObject} parsed JSONObject.
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 */
js.topcoder.rss.template.SimpleRSSParser.parseChannel = function(channel){
    js.topcoder.rss.template.UtilityClass.checkNull(channel, "string", "channel");

    //create channel object.
    var channelJSON = new Array();
    //create items object
    var itemsJSON = new Array();

    var start = channel.indexOf("<"); //start position of a 'item' tag
    //find the 'item' blocks
    var block; // the strings of a tag
    var name; //property name
    var end; //end position of a 'item' tag
	var isEmptyTag; //holds whether the current tag is empty
	var emptyEnd; //End position of an empty tag (ends in />)
	
    while (start >= 0) {
        if (channel.indexOf(">", start) < 0) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The content of element: channel of RSS data has illegal format!");
        }
        name = channel.slice(start + 1, channel.indexOf(">", start)).split(" ")[0];
       	end = channel.indexOf("</" + name + ">", start);
	
	//isEmptyTag = channel[channel.indexOf(">", start)-1]=="/";
        isEmptyTag = channel.indexOf("/>", start) + 1 == channel.indexOf(">", start);
		
		//alert("End: " + end + " EmptyEnd: " + emptyEnd);
		//if(end< 0 || (emptyEnd > 0 && emptyEnd < end))
		if(isEmptyTag)
		{
			emptyEnd = channel.indexOf("/>", start);
			start=channel.indexOf("<", emptyEnd + ("/>").length);
		}
		else
		{
        	if (end < 0) {
            	throw new js.topcoder.rss.template.ProcessingException(
                	"The content of element: channel of RSS data has illegal format!");
        		}
        	block = channel.slice(start + name.length + 2, end);

        	if (name == "item") {
            	//add new element to items:
            	itemsJSON.push(js.topcoder.rss.template.SimpleRSSParser.parseChannelItem(block));
        	} else {
            	//add new element to channel
            	channelJSON[name] = block;
        	}
        	start = channel.indexOf("<", end + ("</" + name + ">").length); //reset start position
		}
    }
    return {
        "channel": channelJSON,
        "items": itemsJSON
    };
}

/**
 * This method parses contents of <item> tag.
 *
 * @param {String} channelItem Content of item tag. Can't be null or undefined.
 * @returns {JSONObject} parsed JSONObject.
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 */
js.topcoder.rss.template.SimpleRSSParser.parseChannelItem = function(channelItem){
    js.topcoder.rss.template.UtilityClass.checkNull(channelItem, "string", "channelItem");

    //create item object.
    var itemJSON = {};

    var start = channelItem.indexOf("<");
    var name;
    var end;
    var block;
    //each tag

    while (start >= 0) {
        name = channelItem.slice(start + 1, channelItem.indexOf(">", start)).split(" ")[0];
        end = channelItem.indexOf("</" + name + ">", start);
        if (end < 0) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The content of element of RSS data has illegal format!");
        }
        block = channelItem.slice(start + name.length + 2, end);

        //add new element to item
        itemJSON[name] = block;
        start = channelItem.indexOf("<", end + ("</" + name + ">").length);
    }

    return itemJSON;
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new RSSProcessor instance.
 * @constructor
 * @param plainText this value defines the format of data. If true, data
 * is a raw text. Otherwise, it is a link to file. This parameter was added
 * because JavaScript do not support function overloading. Can be true or false;
 * @param data this value holds template or link to template file depending on
 * plainText value. Can't be undefined, null or empty string.
 * @throws IllegalArgumentException if parameters do not hold required
 * values. Or rethrows from ContentProvider call.
 *
 * <p>This is main class in the component.
 * Multiple instances of this class can be created. It takes template in the
 * constructor and can process multiple rss feeds using provided methods. Both
 * RSS data and template can be provided as text and as link to file, which will
 * be received via content provider helper class.</p>
 *
 * <p><strong>Thread-safety: </strong>This class is immutable and thread-safe. Anyway, there're no
 * thread synchronization features in JavaScript.</p>
 *
 * @namespace js.topcoder.rss.template
 * @class RSSProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.RSSProcessor = function(plainText, data){
    /**
     * This string holds template. The format of template is defined in component
     * specification. The value is set in constructor. Is accessed in
     * transformRSSText and transformRSSUrl methods.
     *
     * @type {String}
     * @private
     */
    var template;

    /**
     * This is class constructor. It loads template.
     *
     */
    js.topcoder.rss.template.UtilityClass.checkNull(plainText, "boolean", "plainText");
    js.topcoder.rss.template.UtilityClass.checkString(data, "string", "data");

    if (plainText) {
        template = data;
    } else {
        template = js.topcoder.rss.template.ContentProvider.getURLContent(data);
    };

    /**
     * This method fills template with values provided in rss text. Can be called
     * outside the component or from transformRSSFeed method.
     *
     * @param {String} text text that contain RSS input. Can't be null, undefined or
     * empty.
     *
     * @throws IllegalArgumentException if parameters do not hold required
     * values.
     * @throws ProcessingException if text is not well formatted (described in
     * algorithm).
     */
    this.transformRSSText = function(text){
        js.topcoder.rss.template.UtilityClass.checkString(text, "string", "text");

        //parse JSON object from RSS text using SimpleRSSParser.
        var RSSJson = js.topcoder.rss.template.SimpleRSSParser.parseRSS(text);

        return js.topcoder.rss.template.TemplateProcessor.process(template, RSSJson);
    }

    /**
     * This method fills template with values provided in rss feed. Is called
     * outside the component.
     *
     * @param {String} feed URL to rss feed. Can't be null, undefined, empty.
     * @throws IllegalArgumentException if parameters do not hold required
     * values.
     * @throws AJAXFetchException this rethrows from ContentProvider.
     * @throws ProcessingException rethrows from #transformRSSText.
     */
    this.transformRSSFeed = function(feed){
        js.topcoder.rss.template.UtilityClass.checkString(feed, "string", "feed");

        var rss = js.topcoder.rss.template.ContentProvider.getURLContent(feed);
        return this.transformRSSText(rss);
    }
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new ConditionTagProcessor instance.
 *
 * @constructor
 * @param {String} tagText tag text to transform. Can't be undefined, empty, null.
 * @param {JSONObject} rss RSS data that is used to fill template. Can't be undefined, null.
 * @param {Number} item index of current feed item (if any). Can be undefined or null.
 *
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 *
 * <p>This class is responsible for creating conditions. Remember, that nested
 * conditions are not allowed. Currently, only simple comparisons with dates are
 * allowed:
 * [% if {value} {=, <, >} "{some date}" %] ..... [% /if %]
 * {value} can be an attribute of item or channel. {some date} must be a date
 * which can be parsed using javascript date object
 * (http://www.w3schools.com/jsref/jsref_parse.asp)
 *
 * For example: [% if pubDate<"Jul 8, 2008" %] <b>NEW</b> [% /if %]
 * </p>
 *
 * <p><strong>Thread-safety: </strong>This class is immutable and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template.tagprocessors
 * @class ConditionTagProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.tagprocessors.ConditionTagProcessor = function(tagText, rss, item){
    /**
     * Constructor, that will simply call base constructor.
     */
    this.base = js.topcoder.rss.template.AbstractTagProcessor;
    this.base(tagText, rss, item);

    /**
     * Processes tag attribute.
     *
     * @returns {String} transformed text.
     *
     * @throws ProcessingException if tag is not well-formed.
     */
    this.process = function(){
        var tag = this.getTag(); // get the tag from super-class.
        var currentItem = this.getCurrentItem(); // get the current item from super-class.
        var rssData = this.getRssData(); // get the rss data from super-class.

        //Check the separator [% and %]
        js.topcoder.rss.template.UtilityClass.checkSaparator(tag);

        //get tagValue inside the tag
        var tagValue = js.topcoder.rss.template.UtilityClass.trim(
            tag.slice(tag.indexOf("[%") + "[% if".length, tag.indexOf("%]")));
        var isInChannel = (tagValue.indexOf("channel.") == 0);

        //get compare sign
        var signs = ["=", "<", ">"];
        var comparisionSignIndex;

        for (var idx = 0; idx < signs.length; ++idx) {
            comparisionSignIndex = tagValue.indexOf(signs[idx]);
            if (comparisionSignIndex >= 0) {
                break;
            }
        }

        if (idx >= signs.length) {
            throw new js.topcoder.rss.template.ProcessingException(
                "tag is not well-formed: " + tag + "!Need comparision sign!");
        }
        var comparisionSign = signs[idx];

        //corresponding data in rss
        var val;

        //it has prefix "channel."
        if (isInChannel) {
            //get second part of the tagValue (for example, "title")
            var name = js.topcoder.rss.template.UtilityClass.trim(
                tagValue.slice("channel.".length, comparisionSignIndex));
            //get value from rssData
            val = rssData['channel'][name];
        } else {
            var name = js.topcoder.rss.template.UtilityClass.trim(tagValue.slice(0, comparisionSignIndex));
            val = rssData['items'][currentItem][name];
        }

        //rssData doesn't contain required attribute, return unchanged tag
        if (val == null) {
            return tag;
        }

        //Check the date segment
        if (tagValue.indexOf("\"") < 0 || tagValue.lastIndexOf("\"") < 0) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The condition tag should have the following format like: [% if pubDate> \"Jul 8, 2008\" %]!");
        }
        //Get date with which to compare inside the tag
        var date = tagValue.slice(tagValue.indexOf("\"") + 1, tagValue.lastIndexOf("\""));
        //Create date objects
        var d1 = new Date(val);
        var d2 = new Date(date);
        var comparisionResult = d1 - d2;
        //Compare dates according to sign
        if (comparisionSign == "=") {
            comparisionResult = (comparisionResult == 0);
        } else if (comparisionSign == "<") {
            comparisionResult = (comparisionResult < 0);
        } else {
            comparisionResult = (comparisionResult > 0);
        }
        if (comparisionResult) {
            return js.topcoder.rss.template.TemplateProcessor.process(
                tag.slice(tag.indexOf("%]") + "%]".length, tag.indexOf("[% /if %]")), rssData, currentItem);
            //body is contents between opening and closing tags([% if %] and [% /if %])
        } else {
            return "";
        }
    }
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new ForeachTagProcessor instance.
 *
 * @constructor
 * @param {String} tagText tag text to transform. Can't be undefined, empty, null.
 * @param {JSONObject} rss RSS data that is used to fill template. Can't be undefined, null.
 * @param {Number} item index of current feed item (if any). Can be undefined or null.
 *
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 *
 * <p>This class is responsible for creating repeated blocks filled with rss item
 * data. Remember, that nested foreach blocks are not allowed in current
 * version.
 *
 * Syntax: [% foreach item %] .... [% /foreach %]
 *
 * For example: [% foreach item %] <b>[%=link%]</b> [% /foreach %].</p>
 *
 *
 * <p><strong>Thread-safety: </strong>This class is immutable and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template.tagprocessors
 * @class ForeachTagProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.tagprocessors.ForeachTagProcessor = function(tagText, rss, item){
    /**
     * Constructor, that will simply call base constructor.
     */
    this.base = js.topcoder.rss.template.AbstractTagProcessor;
    this.base(tagText, rss, item);

    /**
     * Processes tag attribute.
     * Note: Here, currentItem parameter is useless.
     *
     * @returns {String} transformed text. For this method tag
     * attribute must have such format:
     *  [% foreach item %] .... [% /foreach %]
     *  @throws ProcessingException if tag is not well-formed.
     */
    this.process = function(){
        var tag = this.getTag(); // get the tag from super-class.
        var rssData = this.getRssData(); // get the rss data from super-class.
	var limit = -1; // normally there is no limit
	
        //Check the tag segment
	//Check if there is a limit set
	if (tag.indexOf("[% foreach item limit ") >= 0 && 
	    (tag.indexOf(" %]")+ " %]".length) < (tag.indexOf("[% /foreach %]") + "[% /foreach %]".length)) {
	    var limitText = tag.slice(tag.indexOf("[% foreach item limit ")+ "[% foreach item limit ".length,
				      tag.indexOf(" %]"));
	    if (limitText.match(/^\d+$/) && parseInt(limitText) > 0) {
		limit = parseInt(limitText)
	    } else {
		throw new js.topcoder.rss.template.ProcessingException(
		    "The limit specified in foreach tag should be a positive, integer number!");
	    }
	}else if (tag.indexOf("[% foreach item %]") < 0 || tag.indexOf("[% /foreach %]") < 0) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The foreach tag should have the following format like: [% foreach item %] some others [% /foreach %]" +
		" or [% foreach item limit some_number %] some others [% /foreach %]!");
        }
        //create res variable
        var res = "";
        //Get tag body inside [% foreach item %]....[% /foreach %] block
        var body = "";
	if(limit<0) {
	    body = tag.slice(tag.indexOf("[% foreach item %]") + "[% foreach item %]".length,
			     tag.indexOf("[% /foreach %]"));
	} else {
	    // The tag body starts after the " %]" succeeding the "[% foreach item limit " part
	    body = tag.slice(tag.indexOf(" %]",tag.indexOf("[% foreach item limit ")) + " %]".length,
			     tag.indexOf("[% /foreach %]"));
	}
        for (var i = 0; i < rssData["items"].length && (limit < 0 || i < limit); i++) {
            res += js.topcoder.rss.template.TemplateProcessor.process(body, rssData, i);
        }
        return res;
    }
}

/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new DateFormatTagProcessor instance.
 *
 * @constructor
 * @param {String} tagText tag text to transform. Can't be undefined, empty, null.
 * @param {JSONObject} rss RSS data that is used to fill template. Can't be undefined, null.
 * @param {Number} item index of current feed item (if any). Can be undefined or null.
 *
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 *
 * <p>This class is responsible for formatting dates. Syntax of corresponding tag
 * is:
 *
 * [%= {value} format: {format} %]
 *
 * {value} can be an attribute of item or channel. Format can contain: m - month
 * (as number) M - Month in format 'Jan', 'Feb', etc. MM - Full month name
 * ('January', 'February', etc.) Y - Year (as 4-digit number) y - Year (as
 * 2-digit number) D - day of month.
 *
 * For example: [%= pubDate format:M D, Y %]</p>
 *
 * <p><strong>Thread-safety: </strong>This class is immutable and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template.tagprocessors
 * @class DateFormatTagProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.tagprocessors.DateFormatTagProcessor = function(tagText, rss, item){
    /**
     * Constructor, that will simply call base constructor.
     */
    this.base = js.topcoder.rss.template.AbstractTagProcessor;
    this.base(tagText, rss, item);

    /**
     * Processes tag attribute.
     *
     * @returns {String} transformed text.
     * @throws ProcessingException if tag is not well-formed.
     */
    this.process = function(){
        var tag = this.getTag(); // get the tag from super-class.
        var currentItem = this.getCurrentItem(); // get the current item from super-class.
        var rssData = this.getRssData(); // get the rss data from super-class.

        //Check the separator [% and %]
        js.topcoder.rss.template.UtilityClass.checkSaparator(tag);

        //get tagValue inside the tag
        var tagValue = js.topcoder.rss.template.UtilityClass.trim(tag.slice("[%=".length, tag.indexOf("%]")));
        var isInChannel = (tagValue.indexOf("channel.") == 0);
        var val;

        var dateFormatIndex = tagValue.indexOf("format:");
        if (isInChannel) {
            //get second part of the tagValue (for example, "title")
            var name = js.topcoder.rss.template.UtilityClass.trim(
                tagValue.slice("channel.".length, dateFormatIndex));
            //get value from rssData
            val = rssData['channel'][name];
        } else {
            var name = js.topcoder.rss.template.UtilityClass.trim(tagValue.slice(0, dateFormatIndex));
            val = rssData['items'][currentItem][name];
        }
        if (val == null) {
            return tag;
        }
        var format = js.topcoder.rss.template.UtilityClass.trim(
            tagValue.slice(dateFormatIndex + "format:".length));
        //create Date object from val
        var date = new Date(val);

        //Check month returned is a number or not
        if (isNaN(date.getMonth())) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The month get from the date is not a number!");
        }
        //Check year returned is a number or not
        if (isNaN(date.getFullYear())) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The year get from the date is not a number!");
        }
        //Check date returned is a number or not
        if (isNaN(date.getDate())) {
            throw new js.topcoder.rss.template.ProcessingException(
                "The date get from the date is not a number!");
        }
        format = format.replace("m", date.getMonth() + 1);
        format = format.replace("Y", date.getFullYear());
        format = format.replace("D", date.getDate());
        format = format.replace("y", (date.getFullYear() + "").slice(2));
        var monthsShort = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        var months = ["January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"];
        format = format.replace("MM", months[date.getMonth()]);
        format = format.replace("M", monthsShort[date.getMonth()]);

        return format;
    }
}

/*------------------------------------------- Class Separator ------------------------------------------------------*/
/**
 * Construct a new ValueTagProcessor instance.
 *
 * @constructor
 * @param {String} tagText tag text to transform. Can't be undefined, empty, null.
 * @param {JSONObject} rss RSS data that is used to fill template. Can't be undefined, null.
 * @param {Number} item index of current feed item (if any). Can be undefined or null.
 *
 * @throws IllegalArgumentException if parameters do not hold required
 * values.
 *
 * <p>This class is responsible for replacing simple values. Syntax of
 * corresponding tag is:
 *
 * [%= {value} %]
 *
 * {value} can be an attribute of item or channel.
 *
 * For example: [%= channel.title %]</p>
 *
 * <p><strong>Thread-safety: </strong>This class is immutable and thread-safe.</p>
 *
 * @namespace js.topcoder.rss.template.tagprocessors
 * @class ValueTagProcessor
 *
 * @author abkqz, morehappiness
 * @version 1.0
 */
js.topcoder.rss.template.tagprocessors.ValueTagProcessor = function(tagText, rss, item){
    /**
     * Index of current feed item (if any). Is used when processing is inside
     * foreach block.
     *
     * Is set in constructor, accessed in process() method.
     *
     * @type {Number}
     * @private
     */
    var currentItem;

    /**
     * Text that needs to be transformed. Can't be undefined, null, empty.
     *
     * Is set in constructor, accessed in process() method.
     *
     * @type {String}
     * @private
     */
    var tag;

    /**
     * RSS data that is used to fill template. Can't be undefined, null.
     *
     * @type {JSONObject}
     * @private
     */
    var rssData;

    /**
     * Constructor, that will simply call base constructor.
     */
    this.base = js.topcoder.rss.template.AbstractTagProcessor;
    this.base(tagText, rss, item);
    currentItem = item;
    tag = tagText;
    rssData = rss;

    /**
     * Processes tag attribute.
     *
     * @returns {String}transformed text.
     * @throws ProcessingException if tag is not well-formed.
     */
    this.process = function(){
        var tag = this.getTag(); // get the tag from super-class.
        var currentItem = this.getCurrentItem(); // get the current item from super-class.
        var rssData = this.getRssData(); // get the rss data from super-class.

        //Check the separator [% and %]
        js.topcoder.rss.template.UtilityClass.checkSaparator(tag);

        //get tagValue inside the tag
        var tagValue = js.topcoder.rss.template.UtilityClass.trim(
            tag.slice(tag.indexOf("[%=") + "[%=".length, tag.indexOf("%]")));
        var isInChannel = (tagValue.indexOf("channel.") == 0);
        var val;

        if (isInChannel) {
            //get second part of the tagValue (for example, "title")
            var name = js.topcoder.rss.template.UtilityClass.trim(tagValue.slice("channel.".length));
            //get value from rssData
            val = rssData['channel'][name];
        } else {
            val = rssData['items'][currentItem][tagValue];
        }
        if (val == null) {
            return tag;
        } else {
            return val.replace("<![CDATA[", "").replace("]]>", ""); //Handle the CDATA  data in RSS
        }
    }
}
/*------------------------------------------- Class Separator ------------------------------------------------------*/

