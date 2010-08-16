/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * AJAX Bayeux Client Version 1.0
 */

// define the namespace
if (!js) {
    var js = {};
}
if (!js.topcoder) {
    js.topcoder = {};
}
if (!js.topcoder.ajax) {
    js.topcoder.ajax = {};
}

/**
 * <p>
 * This class is the central class of the AJAX Processor component. It provides the browser-independent implementation
 * of HTTP requests handling.
 * </p>
 * <p>
 * The methods to send request and handle response to it are provided.It can be used to simplify AJAX-enabled
 * applications development, as it takes implementation details away from application developers.
 * </p>
 * <p>
 * Changes in v2.0
 * <ul>
 * <li>1.The class name is renamed to AjaxProcessor.</li>
 * <li>2.The _innerHTMLId instance variable is renamed to _innerHtmlId. And the _httpRequest and _innerHtmlId instance
 * variables are made private. So you cannot access them with this._httpRequest or this._innerHtmlId anymore, you
 * should access them with _httpRequest and _innerHtmlId directly.</li>
 * <li>3.The request method now supports taking JSON object data as the sendingData, and it will be sent with the
 * text/json content type.(The sendingText in parameters JSON object is renamed to sendingData).
 * <li>4.The <code>IllegalArgumentException</code> and <code>InitializationException</code> classes are moved out of
 * this class into global scope.
 * <li>5.It contains a new method : getStatusText to return the status text, and the stateChange method is changed to
 * private scope.
 * </ul>
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here. This class is mutable though.
 * </p>
 * <p>
 * Example: (more examples, please refer to CS)
 * <pre>
 * var processor = new js.topcoder.ajax.AjaxProcessor();
 *
 * // set the id of affected HTML element
 * processor.setInnerHtmlId("testNode");
 *
 * // send a request asynchronously using GET method
 * processor.request({
 *     url: "http://localhost:8080/ajax_processor/bold.jsp?text=testContent",
 *     async: true,
 *     responseType: "xml"
 * });
 * <pre>
 * </p>
 * @constructor
 * @class js.topcoder.ajax.AjaxProcessor
 *
 * @author real_vg, Standlove, TCSDEVELOPER
 * @version 2.0
 */
js.topcoder.ajax.AjaxProcessor = function() {
    /**
     * <p>
     * This variable stores XMLHttpRequest object used to do the work.
     * </p>
     * <p>
     * It is initialized in constructor, null value means that there has been an error during initialization
     * (probably because of lack of browser support).
     * </p>
     * <p>
     * Changes in v2.0
     * It's made private in v2.0
     * </p>
     *
     * @private
     * @type XMLHttpRequest
     */
    var _httpRequest = null;

    /**
     * <p>
     * This variable stores the id of HTML DOM element, innerHTML of which will be updated with the response data.
     * </p>
     * <p>
     * It is set using setInnerHTMLId method, null value means that no element contents will be affected by the
     * response received.
     * </p>
     * <p>
     * Changes in v2.0
     * It's renamed from _innerHTMLId to _innerHtmlId.
     * It's made private in v2.0
     * </p>
     *
     * @private
     * @type String
     */
    var _innerHtmlId = null;

    /**
     * <p>
     * Sets the id of HTML DOM element, innerHTML of which will be updated with the response data.
     * </p>
     * <p>
     * Null id value means that no element contents will be affected by the response received.
     * </p>
     * <p>
     * Changes in v2.0
     * It's renamed from setInnerHTMLId to setInnerHtmlId. And it now use the _innerHtmlId private instance variable
     * instead.
     * </p>
     *
     * @param {String} innerHtmlId the id to be set, can be null.
     *
     * @public
     */
    this.setInnerHtmlId = function(innerHtmlId) {
        _innerHtmlId = innerHtmlId;
    }

    /**
     * <p>
     * Returns the id of HTML DOM element, innerHTML of which will be updated with the response data.
     * </p>
     * <p>
     * Null id value means that no element contents will be affected by the response received.
     * </p>
     * <p>
     * Changes in v2.0
     * It's renamed from getInnerHTMLId to getInnerHtmlId.and it now uses the _innerHtmlId private instance variable
     * instead.
     * </p>
     *
     * @return {String} the id of HTML DOM element, innerHTML of which will be updated with the response data.
     *
     * @public
     */
    this.getInnerHtmlId = function() {
        return _innerHtmlId;
    }

    /**
     * <p>
     * Handles state change event.
     * </p>
     * <p>
     * If specified innerHtmlId value is not null, sets the innerHTML of the HTML DOM element denoted by the specified
     * id to the specified value, if the value itself also is not null. Otherwise does nothing (intended to be
     * overridden by subclasses).
     * </p>
     * <p>
     * This function gets called when the state of XMLHttpRequest object changes if the other event handler for it
     * was not specified.
     * </p>
     * <p>
     * Changes in v2.0
     * It's changed to private scope.
     * </p>
     *
     * @param {String} innerHtmlId the id of HTML DOM element to update contents of, can be null.
     * @param {Object} responseValue the value(contents) of the response to be handled, can be null.
     * @throws js.topcoder.ajax.IlegalArgumentException if there is no element with the specified id in the document.
     *
     * @private
     */
    function stateChange(innerHtmlId, responseValue) {
        if (innerHtmlId && responseValue) {
            // find node to alter inner HTML of
            var node = document.getElementById(innerHtmlId);

            if (node == null) {
                throw new js.topcoder.ajax.IllegalArgumentException("innerHtmlId",
                    "The element with the specified id was not found in the document");
            }

            if (responseValue.nodeType) {
                // remove all child nodes
                while (node.hasChildNodes()) {
                    node.removeChild(node.lastChild);
                }

                // add new contents as a child
                if (responseValue.documentElement && responseValue.documentElement.xml) {
                    node.innerHTML = responseValue.documentElement.xml;
                } else {
                    node.appendChild(responseValue.documentElement ? responseValue.documentElement : responseValue);
                }
            } else {
                node.innerHTML = responseValue;
            }
        }
    }

    /**
     * <p>
     * Returns the state of the object:
     * </p>
     * <p>
     * <ul>
     * <li>0 = uninitialized (XMLHttpRequest.open() has not been called yet).</li>
     * <li>1 = loading (XMLHttpRequest.send() has not been called yet).</li>
     * <li>2 = loaded (XMLHttpRequest.send() has been called, headers and status are available).</li>
     * <li>3 = interactive (Downloading, XMLHttpRequest.responseText holds the partial data).</li>
     * <li>4 = complete (Finished with all operations).</li>
     * </ul>
     * </p>
     * <p>
     * Changes in v2.0
     * The _httpRequest instance variable is now private.
     * </p>
     *
     * @return {int} the state of the object, taken from XMLHttpRequest.readyState property.
     *
     * @public
     */
    this.getState = function() {
        return _httpRequest.readyState;
    }

    /**
     * <p>
     * Returns the response status for the HTTP request as a number (e.g. 404 for "Not Found" or 200 for "OK").
     * </p>
     * <p>
     * The response status is taken from XMLHttpRequest.status property.
     * </p>
     * <p>
     * Changes in v2.0
     * The _httpRequest instance variable is now private.
     * </p>
     *
     * @return {int} the response status for the HTTP request, as a number.
     *
     * @public
     */
    this.getStatus = function() {
        return _httpRequest.status;
    }

    /**
     * <p>
     * Returns the response status text for the HTTP request. The response status text is taken from XMLHttpRequest
     * statusText property.
     * </p>
     * <p>
     * Newly added in v2.0.
     * </p>
     *
     * @return {String} the response status Text for the HTTP request.
     *
     * @public
     */
    this.getStatusText = function() {
        return _httpRequest.statusText;
    }

    /**
     * <p>
     * Returns the response to the request as text, or null if the request is unsuccessful or has not yet been sent.
     * The response text is taken from XMLHttpRequest.responseText property.
     * </p>
     * <p>
     * Changes in v2.0
     * The _httpRequest instance variable is now private.
     * </p>
     *
     * @return {String} the response to the request as text, or null if the request is unsuccessful or has not yet
     * been sent.
     *
     * @public
     */
    this.getResponseText = function() {
        return _httpRequest.responseText;
    }

    /**
     * <p>
     * Returns the response to the request as XML DOM Document, or null if the request is unsuccessful or has not yet
     * been sent. The response XML is taken from XMLHttpRequest.responseXml property.
     * </p>
     * <p>
     * Changes in v2.0
     * The _httpRequest instance variable is now private.
     * </p>
     *
     * @return {Document} the response to the request as XML DOM Document, or null if the request is unsuccessful or
     * has not yet been sent.
     *
     * @public
     */
    this.getResponseXml = function() {
        return _httpRequest.responseXML;
    }

    /**
     * <p>
     * Sends an HTTP request to the server.
     * </p>
     * <p>
     * Change in v2.0
     * 1.It now uses the renamed _innerHtmlId instance variable (and its renamed getter & setter), and all instance
     * variables are now private. And the stateChange method is changed to private scope.
     * 2.The sendingText in the parameters JSON object is renamed to sendingData, and it now supports JSON data.
     * </p>
     *
     * @param parameters a JSON-style object (it should not be null), which contains the parameters:
     * <ul>
     * <li>method - a string, which specifies the request method, should be either "GET" or "POST", can be null,
     * default is "GET".</li>
     * <li>url - a string which specifies where to send the request to,  should not be null or empty string.</li>
     * <li>async - true if the method needs to work asynchronously, false otherwise, default is true.</li>
     * <li>user - a user name string, can be null should not be empty string, null means that no user is
     * authenticated.</li>
     * <li>password - a password string, can be null or empty string, should be specified together with user.</li>
     * <li>sendingData - the text (string, which represents encoded form) or XML (DOM Document)  or JSON object to be
     * sent, null if nothing is to be sent.</li>
     * <li>responseType - a desired type of response content, can be "xml" or "text""xml" means that DOM Node will be
     * passed to private stateChange function, "text" means that string will be passed to it, can be null, default is
     * "text".</li>
     * <li>onStateChange - a function to be executed on state change.</li>
     * </ul>
     *
     * @throws js.topcoder.ajax.IllegalArgumentException if parameters argument is null or any of its fields has an
     * illegal value.
     *
     * @public
     */
    this.request = function(parameters) {
        // check parameter
        js.topcoder.ajax.Helper.checkNotNull(parameters, "parameters");

        // check method
        if (parameters.method && (parameters.method != "GET") && (parameters.method != "POST")) {
            throw new js.topcoder.ajax.IllegalArgumentException("parameters.method",
                "The request method should be specified as either \"GET\" or \"POST\".");
        }

        // check url
        js.topcoder.ajax.Helper.checkString(parameters.url, "parameters.url");

        // check user
        if (js.topcoder.ajax.Helper.checkDefined(parameters.user)) {
            js.topcoder.ajax.Helper.checkString(parameters.user, "parameters.user");
        }

        // check password
        if (!js.topcoder.ajax.Helper.checkDefined(parameters.user) &&
            js.topcoder.ajax.Helper.checkDefined(parameters.password)) {
            throw new js.topcoder.ajax.IllegalArgumentException("parameters.password",
                "The parameters.password parameter must be specified together with user.");
        }

        // check responseType
        if (parameters.responseType && (parameters.responseType != "xml") && (parameters.responseType != "text")) {
            throw new js.topcoder.ajax.IllegalArgumentException("parameters.responseType",
                "The request method should be specified as either \"xml\" or \"text\".");
        }

        // initialize XMLHttpRequest
        initializeXMLHttpRequest();

        var thisObj = this;

        // choose state change event handler
        if (parameters.onStateChange) {
            // use custom state change handler
            _httpRequest.onreadystatechange = parameters.onStateChange;
        } else if (parameters.responseType == "xml") {
            // use standard state change handler with XML response
            _httpRequest.onreadystatechange = function() {
                if ((thisObj.getState() == 4) && ((thisObj.getStatus() == 200) || (thisObj.getStatus() == 0))) {
                    stateChange(thisObj.getInnerHtmlId(), thisObj.getResponseXml());
                }
            };

            if (window.XMLHttpRequest && _httpRequest.overrideMimeType) {
                // this part works with browsers which support XMLHTMLRequest object: FF, Safari, etc.
                _httpRequest.overrideMimeType('text/xml');
            }
        } else {
            // use standard state change handler with text response
            _httpRequest.onreadystatechange = function() {
                if ((thisObj.getState() == 4) && ((thisObj.getStatus() == 200) || (thisObj.getStatus() == 0))) {
                    stateChange(thisObj.getInnerHtmlId(), thisObj.getResponseText());
                }
            };
        }

        // initialize a request and specify the method, URL, and authentication information
        _httpRequest.open(parameters.method ? parameters.method : "GET",
            parameters.url,
            parameters.async != null ? parameters.async : true,
            parameters.user,
            parameters.password);

        var isJSON = false;
        var text = null;

        // check content type
        if (parameters.method == "POST") {
            if (parameters.sendingData && parameters.sendingData.nodeType) {
                _httpRequest.setRequestHeader("Content-Type", "text/xml");
            } else {
                text = JSON.stringify(parameters.sendingData);
                if ((text) && (((text.charAt(0) == '{') && (text.charAt(text.length - 1) == '}')) ||
                    ((text.charAt(0) == '[') && (text.charAt(text.length - 1) == ']')))) {
                    _httpRequest.setRequestHeader("Content-Type", "text/json");
                    isJSON = true;
                } else {
                    _httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                }
            }
        }

        // send the data
        if (isJSON) {
            _httpRequest.send(parameters.method == "GET" ? null : text);
        } else if (parameters.sendingData && parameters.sendingData.nodeType) {
            _httpRequest.send(
                parameters.method == "GET" ? null : js.topcoder.ajax.Helper.getXml(parameters.sendingData));
        } else {
            _httpRequest.send(parameters.method == "GET" ? null : parameters.sendingData);
        }
    }

    /**
     * <p>
     * A private method to initialize the XMLHttpRequest instance.
     * </p>
     *
     * @throws js.topcoder.ajax.InitializationException if the browser can't support XMLHttpRequest.
     *
     * @private
     */
    function initializeXMLHttpRequest() {
        // initialization codes
        if (window.XMLHttpRequest) {
            // this part works with browsers which support XMLHTMLRequest object: FF, Safari, etc.
            _httpRequest = new XMLHttpRequest();
            if (_httpRequest.overrideMimeType) {
                _httpRequest.overrideMimeType("text/xml");
            }
        } else if (window.ActiveXObject) {
            // this part works with ActiveX object : MSIE browser
            try {
                _httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e1) {
                try {
                    _httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e2) {
                    // do nothing here, error will be detected later
                }
            }
        }

        // fail to create the xml http request
        if (!_httpRequest) {
            throw new js.topcoder.ajax.InitializationException(
                "Error while creating XMLHttpRequest object, most likely the browser doesn't " +
                "support AJAX");
        }
    }

    // this is not necessary call, but just make sure which be compatible with v1.0
    initializeXMLHttpRequest();
}

/**
 * <p>
 * This class defines an exception thrown in the case when illegal argument is passed in to the function.
 * </p>
 * <p>
 * This class is derived from the Error class
 * </p>
 * <p>
 * Changes in v2.0
 * 1.Moved into global scope.
 * 2.the _argumentName instance variable is renamed from argumentName. And it's now private.
 * 3.A getArgumentName() method is added.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here.
 * </p>
 *
 * @constructor
 * @param argument the name of the illegal argument.
 * @param message the error message.
 * @class js.topcoder.ajax.IllegalArgumentException
 *
 * @author real_vg, Standlove, TCSDEVELOPER
 * @version 2.0
 */
js.topcoder.ajax.IllegalArgumentException = function(argument, message) {
    /**
     * <p>
     * This variable stores the name of illegal argument.
     * </p>
     * <p>
     * It is initialized in constructor.and never changed afterwards.
     * </p>
     * <p>
     * Changes in v2.0
     * renamed from argumentName to _argumentName made private.
     * </p>
     *
     * @private
     * @type String
     */
    var _argumentName;

    /**
     * <p>
     * The getter for the argumentName instance variable.
     * </p>
     *
     * @return {String} Return the argument name.
     *
     * @public
     */
    this.getArgumentName = function() {
        return _argumentName;
    }

    // initialize
    Error.call(this, message);
    _argumentName = argument;
}

/**
 * <p>
 * This class defines an exception thrown in the case when any error happens while AJAXProcessor is being initialized.
 * <p>
 * <p>
 * This class is derived from the Error class.
 * </p>
 * <p>
 * Changes in v2.0
 * 1.Moved into global scope.
 * 2.Removed the argumentName instance variable, as it's never used.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here.
 * </p>
 *
 * @constructor
 * @param message the error message.
 * @class js.topcoder.ajax.InitializationException
 *
 * @author real_vg, Standlove, TCSDEVELOPER
 * @version 2.0
 */
js.topcoder.ajax.InitializationException = function(message) {
    // initialize
    Error.call(this, message);
}

/**
 * <p>
 * This processor can be started and stopped. Once started, it will poll the server for response data in an endless
 * loop.
 * </p>
 * <p>
 * It allows only one AJAX request made to the server at any time. The component will provide getter and setter for
 * the text to send for each AJAX request.
 * </p>
 * <p>
 * The state change handler will behave as follows. If the state of the XMLHttpRequest is complete, and the status of
 * the HTTP response is 0 or 200, it will execute the configured onSuccess callback function. This callback function
 * will update the text to send of the processor. The processor will then make another AJAX request.This process
 * continues forever until the processor is stopped. The processor can wait for a certain time before making another
 * AJAX request.If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or 200,
 * the processor will attempt a number of retries. If the retry attempts are exhausted, the processor will invoke the
 * configured onRetryFailed callback function, but won't make another AJAX request again. Then the processor will stop
 * the polling process, until it is started again.
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here. This class is mutable though.
 * </p>
 * <p>
 * Example:
 * <pre>
 * // create LongPollingAjaxProcessor object
 * var processor = new js.topcoder.ajax.LongPollingAjaxProcessor({
 *     url: "http://localhost:8080/ajax_processor/jsonProcessor.jsp",
 *     async: true,
 *     waitInterval:1000,
 *     retryCount:3,
 *     sendingData:{"id":1, "name":"topcoder"},
 *     onSuccess: function(processor) {
 *         alert(processor.getResponseText());
 *         var data = processor.getSendingData();
 *         if (data.id == 3) {
 *             // stop the processor, it will stop sending the request.
 *             processor.stop();
 *             alert(processor.isStopped());
 *         } else {
 *             data.id = data.id + 1;
 *             processor.setSendingData(data);
 *         }
 *     },
 *     onRetryFailed: function(processor, statusCode, statusText) {
 *         // set retryCount & waitInterval.
 *         processor.setRetryCount(processor.getRetryCount() + 1);
 *         processor.setWaitInterval(processor.getWaitInterval() + 1000);
 *         alert(statusCode);
 *     }
 * });
 *
 * // start the processor, it will start to send the request to servlet
 * processor.start();
 *
 * // false should be returned indicating it¡¯s running.
 * var stopped = processor.isStopped();
 * </pre>
 * </p>
 *
 * @param parameters - a JSON-style object (it should not be null), which contains the parameters:
 * <ul>
 * <li>url - a string which specifies where to send the request to, should not be null or empty string.</li>
 * <li>user - a user name string, can be null should not be empty string, null means that no user is authenticated.
 * </li>
 * <li>password - a password string, can be null or empty string, should be specified together with user.</li>
 * <li>async - true if the method needs to work asynchronously, false otherwise, default is true.</li>
 * <li>sendingData - the data (string, which represents encoded form) or XML (DOM Document) or JSON object to be sent.
 * Required.</li>
 * <li>waitInterval - The processor can wait for a certain time before making another AJAX request. Default wait
 * interval is 0, meaning the AJAX request is sent immediately. It's measured in milliseconds. It must be a  long
 * value &gt;= 0.</li>
 * <li>retryCount - If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or
 * 200, the processor will attempt a number of retries. Default retry count is 2. It must be an int value &gt;= 0.</li>
 * <li>onSuccess - the user callback function to call if the response is received successfully. Required.</li>
 * <li>onRetryFailed - the user callback function to call if the retry attempts fail. Optional.</li>
 * <ul>
 *
 * @throws js.topcoder.ajax.IllegalArgumentException if the parameters is null or any of its fields has an illegal
 * value.
 * @throws js.topcoder.ajax.InitializationException if fail to create the AJAXProcessor (it's propagated).
 *
 * @constructor
 * @class js.topcoder.ajax.LongPollingAjaxProcessor
 *
 * @author Standlove, TCSDEVELOPER
 * @version 2.0
 * @since 2.0
 */
js.topcoder.ajax.LongPollingAjaxProcessor = function(parameters) {
    /**
     * <p>
     * An alias of 'this' instance used in the stateChange
     * </p>
     */
    var _thisObj = this;

    /**
     * A JSON-style object (it should not be null), which contains the parameters:
     * <ul>
     * <li>url - a string which specifies where to send the request to, should not be null or empty string.</li>
     * <li>user - a user name string, can be null should not be empty string, null means that no user is authenticated.
     * </li>
     * <li>password - a password string, can be null or empty string, should be specified together with user.</li>
     * <li>async - true if the method needs to work asynchronously, false otherwise, default is true.</li>
     * <li>sendingData - the data (string, which represents encoded form) or XML (DOM Document) or JSON object to be
     * sent.
     * Required.</li>
     * <li>waitInterval - The processor can wait for a certain time before making another AJAX request. Default wait
     * interval is 0, meaning the AJAX request is sent immediately. It's measured in milliseconds. It must be a  long
     * value &gt;= 0.</li>
     * <li>retryCount - If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or
     * 200, the processor will attempt a number of retries. Default retry count is 2. It must be an int value &gt;= 0.
     * </li>
     * <li>onSuccess - the user callback function to call if the response is received successfully. Required.</li>
     * <li>onRetryFailed - the user callback function to call if the retry attempts fail. Optional.</li>
     * <ul>
     * <p>
     * Initialized in constructor, and its reference is never changed afterwards.  Must be non-null.
     * </p>
     *
     * @private
     * @type JSONObject
     */
    var _parameters;

    /**
     * <p>
     * Represents the AjaxProcessor javascript object to send the AJAX request and receive AJAX response.
     * </p>
     * <p>
     * Initialized in constructor and its reference is never changed afterwards. Must be non-null.
     * </p>
     *
     * @private
     * @type js.topcoder.ajax.AjaxProcessor
     */
    var _ajaxProcessor;

    /**
     * <p>
     * Represents the processor is running or not.
     * </p>
     * <p>
     * It's set to false initially.It will set to true when start method is called. And it will be set to false when
     * stop method is called or the retry attempts fail. It can be accessed by user from the isStopped method.
     * </p>
     *
     * @private
     * @type boolean
     */
    var _running = false;

    /**
     * <p>
     * Represent the remaining number of retries the processor has to resend the request to sever.
     * </p>
     * <p>
     * It's set to retryCount constructor, and it will also be reset when the response is received successfully. It
     * will decrease by 1 if the state of the XMLHttpRequest is complete, but the status of the HTTP response is not
     * 0 or 200 in the stateChange method.
     * </p>
     * <p>
     * It must be &lt;= retryCount and &gt;= 0.
     * </p>
     *
     * @private
     * @type int
     */
    var _remainingRetries;

    /**
     * <p>
     * This function will be called if the state is changed inside the AjaxProcessor.
     * </p>
     * <p>.
     * It will call the onSuccess callback function if the response is successful, otherwise, it will retry the
     * request, and after all retry attempts fail, it will call the onRetryFailed callback function.
     * </p>
     *
     * @private
     */
    function stateChange() {
        // ignore other states
        if (_ajaxProcessor.getState() != 4) {
            return;
        }

        if ((_ajaxProcessor.getStatus() == 200) || (_ajaxProcessor.getStatus() == 0)) {
            _remainingRetries = _parameters.retryCount;

            // call the success callback function, which should set a new sendingData.
            _parameters.onSuccess(_thisObj);

            if (_running) {
                // wait a moment before sending the next request.
                setTimeout(function() {
                    sendRequest();
                }, _parameters.waitInterval);
            }
        } else {
            if (_remainingRetries == 0) {
                // stop the processor as all retries are exhausted, and call the retry callback function
                _running = false;
                if (_parameters.onRetryFailed) {
                    _parameters.onRetryFailed(_thisObj, _ajaxProcessor.getStatus(), _ajaxProcessor.getStatusText());
                }
            } else {
                if (_running) {
                    _remainingRetries = _remainingRetries - 1;

                    // wait a moment before retry the request.
                    setTimeout(function() {
                        sendRequest();
                    }, _parameters.waitInterval);
                }
            }
        }
    }

    /**
     * <p>
     * Send the request to server. It will also reset the _remainingRetries to the configured retryCount.
     * </p>
     *
     * @private
     */
    function sendRequest() {
        // reset _remainingRetries
        _remainingRetries = _parameters.retryCount;

        // call _ajaxProcessor.request
        _ajaxProcessor.request(_parameters);
    }

    /**
     * <p>
     * Start the polling, it will send the request to server if it's not running. And if it's running, it would simply
     * do nothing. It's ok to call this method multiple times.
     * </p>
     *
     * @public
     */
    this.start = function() {
        if (!_running) {
            _running = true;
            sendRequest();
        }
    }

    /**
     * <p>
     * Stop sending the request. It's ok to call this method multiple times.
     * </p>
     *
     * @public
     */
    this.stop = function() {
        _running = false;
    }

    /**
     * <p>
     * Indicate the processor is stopped or not.
     * </p>
     *
     * @return {boolean} a boolean value indicating the processor is stopped or not.
     *
     * @public
     */
    this.isStopped = function() {
        return !_running;
    }

    /**
     * <p>
     * Return the retry count.
     * </p>
     *
     * @return {int} the retry count.
     *
     * @public
     */
    this.getRetryCount = function() {
        return _parameters.retryCount;
    }

    /**
     * <p>
     * Set the retry count.
     * </p>
     *
     * @param retryCount the retry count.
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is not an int value or it's &lt; 0.
     *
     * @public
     */
    this.setRetryCount = function(retryCount) {
        // check parameter
        js.topcoder.ajax.Helper.checkNonNegativeInteger(retryCount, "retryCount");

        _parameters.retryCount = retryCount;
    }

    /**
     * <p>
     * Return the wait interval (in milliseconds).
     * </p>
     *
     * @return the wait interval (in milliseconds).
     */
    this.getWaitInterval = function() {
        return _parameters.waitInterval;
    }

    /**
     * <p>
     * Set the wait interval (in milliseconds).
     * </p>
     *
     * @param waitInterval wait interval (in milliseconds).
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is not an int value or it's &lt; 0.
     *
     * @public
     */
    this.setWaitInterval = function(waitInterval) {
        // check parameter
        js.topcoder.ajax.Helper.checkNonNegativeInteger(waitInterval, "waitInterval");

        _parameters.waitInterval = waitInterval;
    }

    /**
     * <p>
     * Set the data to send. The sending data can be the text (string, which represents encoded form data) or XML
     * (DOM Document) or JSON object to be sent.
     * </p>
     *
     * @param {Object} sendingData the data to send.
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is null.
     *
     * @public
     */
    this.setSendingData = function(sendingData) {
        // check parameter
        js.topcoder.ajax.Helper.checkNotNull(sendingData, "sendingData");

        _parameters.sendingData = sendingData;
    }

    /**
     * <p>
     * Get the data to send. The sending data can be the text (string, which represents encoded form data) or XML
     * (DOM Document) or JSON object to be sent.
     * </p>
     *
     * @return {Object} the data to send.
     *
     * @public
     */
    this.getSendingData = function() {
        return _parameters.sendingData;
    }

    /**
     * <p>
     * Returns the response to the request as text.
     * </p>
     *
     * @return {String} the response to the request as text.
     *
     * @public
     */
    this.getResponseText = function() {
        return _ajaxProcessor.getResponseText();
    }

    /**
     * <p>
     * Returns the response to the request as XML DOM Document.
     * </p>
     *
     * @return {Object} the response to the request as XML DOM Document.
     *
     * @public
     */
    this.getResponseXml = function() {
        return _ajaxProcessor.getResponseXml();
    }

    // check parameter
    js.topcoder.ajax.Helper.checkNotNull(parameters, "parameters");

    // check url
    js.topcoder.ajax.Helper.checkString(parameters.url, "parameters.url");

    // check user
    if (js.topcoder.ajax.Helper.checkDefined(parameters.user)) {
        js.topcoder.ajax.Helper.checkString(parameters.user, "parameters.user");
    }

    // check password
    if (!js.topcoder.ajax.Helper.checkDefined(parameters.user) &&
        js.topcoder.ajax.Helper.checkDefined(parameters.password)) {
        throw new js.topcoder.ajax.IllegalArgumentException("parameters.password",
            "The parameters.password parameter must be specified together with user.");
    }

    // check waitInterval
    if (js.topcoder.ajax.Helper.checkDefined(parameters.waitInterval)) {
        js.topcoder.ajax.Helper.checkNonNegativeInteger(parameters.waitInterval, "parameters.waitInterval");
    }

    // check retryCount
    if (js.topcoder.ajax.Helper.checkDefined(parameters.retryCount)) {
        js.topcoder.ajax.Helper.checkNonNegativeInteger(parameters.retryCount, "parameters.retryCount");
    }

    // check onSuccess
    js.topcoder.ajax.Helper.checkNotNull(parameters.onSuccess, "parameters.onSuccess");

    // initialize
    _ajaxProcessor = new js.topcoder.ajax.AjaxProcessor();

    // copy the parameters into instance variable
    try {
        _parameters = JSON.parse(JSON.stringify(parameters));
    } catch (e) {
        // xml dom can't be converted into json
        _parameters = parameters;
    }

    if (!js.topcoder.ajax.Helper.checkDefined(_parameters.waitInterval)) {
        _parameters.waitInterval = 0;
    }

    if (!js.topcoder.ajax.Helper.checkDefined(_parameters.retryCount)) {
        _parameters.retryCount = 2;
    }

    _parameters.onStateChange = stateChange;
    _parameters.method = "POST";
    _parameters.onSuccess = parameters.onSuccess;
    _parameters.onRetryFailed = parameters.onRetryFailed;
    _remainingRetries = _parameters.retryCount;
}

/**
 * <p>
 * This processor allows only one AJAX request made to the server at any time. User can add data to send to the
 * processor before the response for the previous AJAX request is completed. The processor will buffer the data.
 * </p>
 * <p>
 * If accumulative mode is on, the processor will send them all in a batch in the next AJAX request. In this mode,
 * the server is assumed to have the ability to consume the batch of sent data in one request. If accumulative mode
 * is off, each AJAX request will carry one piece of data. Requests will be made to the server, one at a time, until
 * the buffered data is consumed.
 * </p>
 * <p>
 * The state change handler will behave as follows. If the state of the XMLHttpRequest is complete, and the status of
 * the HTTP response is 0 or 200, it will execute the configured onSuccess callback function (optional). If there is
 * buffered data pending to be sent, the processor will make another AJAX request. When the accumulative mode is on,
 * all added data should be the same type (JSON, XML or form data text).
 * </p>
 * <p>
 * The processor can wait for a certain time before making another AJAX request.
 * </p>
 * <p>
 * If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or 200, the processor
 * will attempt a number of retries. If the retry attempts are exhausted, the processor will invoke the configured
 * onRetryFailed callback function, but won't make another AJAX request again. Then the processor will set the
 * suspended flag to off. User can later turn the flag on by calling the resume method, and the buffered processor
 * will resume sending the buffered data.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here. This class is mutable though.
 * </p>
 * <p>
 * Example: (more examples, please refer to CS)
 * <pre>
 * // create AjaxProcessor object
 * var processor = new js.topcoder.ajax.BufferedAjaxProcessor({
 *     url: "http://localhost:8080/ajax_processor/bufferedFormData.jsp",
 *     accumulative: false,
 *     onSuccess: function(processor) {
 *         alert(processor.getResponseText());
 *     }
 * });
 *
 * // add data, the data will be sent immediately as there is no buffered data right now
 * processor.addSendingData("message=a");
 *
 * // assume the response for the request sent above will be received after the following data are added. The
 * // following added data will be aggregated and buffered. The aggregated data is: message=b&message=c
 * processor.addSendingData("message=b");
 * processor.addSendingData("message=c");
 * </pre>
 * </p>
 *
 * @param {JSON} parameters a JSON-style object (it should not be null), which contains the parameters:
 * <ul>
 * <li>url - a string which specifies where to send the request to,  should not be null or empty string.</li>
 * <li>user - a user name string, can be null should not be empty string,  null means that no user is authenticated.
 * </li>
 * <li>password - a password string, can be null or empty string, should be specified together with user.</li>
 * <li>async - true if the method needs to work asynchronously, false otherwise, default is true.</li>
 * <li>sendingData - the text (string, which represents encoded form) or XML (DOM Document) or JSON object to be sent.
 * Can be null.</li>
 * <li>waitInterval - The processor can wait for a certain time before making another AJAX request. Default wait
 * interval is 0, meaning the AJAX request is sent immediately. It's measured in milliseconds. It must be a long value
 * &gt;= 0.</li>
 * <li>retryCount - If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or
 * 200, the processor will attempt a number of retries. Default retry count is 2. It must be an int value &gt;= 0.
 * </li>
 * <li>accumulative - If accumulative mode is on, the processor will send them all in a batch in the next AJAX request.
 * In this mode, the server is assumed to have the ability to consume the batch of sent data in one request. If
 * accumulative mode is off, each AJAX request will carry one piece of data. Requests will be made to the server, one
 * at a time, until the buffered data is consumed. Default to true.</li>
 * <li>onSuccess - the user callback function to call if the response is received successfully. Required.</li>
 * <li>onRetryFailed - the user callback function to call if the retry attempts fail. Optional.</li>
 * <li>onStateChange - The callback function to be called by the _ajaxProcessor when the state is changed, it is
 * always set to the private stateChange method.</li>
 * </ul>
 *
 * @throws js.topcoder.ajax.IllegalArgumentException if the parameters is null or any of its fields has an illegal
 * value.
 * @throws js.topcoder.ajax.InitializationException if fail to create the AJAXProcessor (it's propagated).
 *
 * @constructor
 * @class js.topcoder.ajax.BufferedAjaxProcessor
 *
 * @author Standlove, TCSDEVELOPER
 * @version 2.0
 * @since 2.0
 */
js.topcoder.ajax.BufferedAjaxProcessor = function(parameters) {
    /**
     * <p>
     * An alias of 'this' instance used in the stateChange
     * </p>
     */
    var _thisObj = this;

    /**
     * <p>
     * Represents the array holding the sending data.
     * </p>
     * <p>
     * It's used only if the accumulative mode is off.
     * 1. If accumulative mode is off, initialized to empty array in the constructor, and then its reference is never
     * changed. Can be empty. Must contain non-null element. Elements are added in the addSendingData method.
     * 2. If accumulative mode is on, it's left to null and not used.
     * </p>
     *
     * @private
     * @type Array
     */
    var _sendingDataArray;

    /**
     * <p>
     * Represents the object holding the aggregated sending data.
     * </p>
     * <p>
     * It's used when the accumulative mode is on.
     * 1. If accumulative mode is on: It can be null. It will be set in the addSendingData method. It can be text
     * (string, which represents encoded form data) or XML (DOM Document) or JSON object.
     * 2. if accumulative mode is off, it's null and not used.
     * </p>
     *
     * @private
     * @type Object
     */
    var _aggregatedSendingData;

    /**
     * <p>
     * Represents the data type of the sending data. It can be "json", "xml" or "formData" representing the sending
     * data is JSON object, XML DOM, or form data text.
     * </p>
     * <p>
     * Null initially, and it's set only once in the addSendingData method, and never changed afterwards. It's only
     * used when accumulative mode is on. It's left to null and not used if accumulative mode is off.
     * </p>
     *
     * @private
     * @type String
     */
    var _sendingDataType;

    /**
     * <p>
     * Represents xml root node name if the _sendingDataType is "xml" and the accumulative mode is on.
     * </p>
     * <p>
     * Null initially, and it's set only once in the addSendingData method, and never changed afterwards. It's only
     * used when accumulative mode is on. It's left to null and not used if accumulative mode is off.
     * </p>
     *
     * @private
     * @type String
     */
    var _xmlRootNodeName;

    /**
     * <p>
     * Represents the AjaxProcessor javascript object to send the AJAX request and receive AJAX response.
     * </p>
     * <p>
     * Initialized in constructor, and its reference is never changed afterwards. Must be non-null.
     * </p>
     *
     * @private
     * @type js.topcoder.ajax.AjaxProcessor
     */
    var _ajaxProcessor;

    /**
     * <p>
     * A JSON-style object (it should not be null), which contains the parameters:
     * </p>
     * <ul>
     * <li>url - a string which specifies where to send the request to,  should not be null or empty string.</li>
     * <li>user - a user name string, can be null should not be empty string,  null means that no user is authenticated.
     * </li>
     * <li>password - a password string, can be null or empty string, should be specified together with user.</li>
     * <li>async - true if the method needs to work asynchronously, false otherwise, default is true.</li>
     * <li>sendingData - the text (string, which represents encoded form) or XML (DOM Document) or JSON object to be
     * sent. Can be null.</li>
     * <li>waitInterval - The processor can wait for a certain time before making another AJAX request. Default wait
     * interval is 0, meaning the AJAX request is sent immediately. It's measured in milliseconds. It must be a long
     * value &gt;= 0.</li>
     * <li>retryCount - If the state of the XMLHttpRequest is complete, but the status of the HTTP response is not 0 or
     * 200, the processor will attempt a number of retries. Default retry count is 2. It must be an int value &gt;= 0.
     * </li>
     * <li>accumulative - If accumulative mode is on, the processor will send them all in a batch in the next AJAX
     * request. In this mode, the server is assumed to have the ability to consume the batch of sent data in one
     * request. If accumulative mode is off, each AJAX request will carry one piece of data. Requests will be made to
     * the server, one at a time, until the buffered data is consumed. Default to true.</li>
     * <li>onSuccess - the user callback function to call if the response is received successfully. Required.</li>
     * <li>onRetryFailed - the user callback function to call if the retry attempts fail. Optional.</li>
     * <li>onStateChange - The callback function to be called by the _ajaxProcessor when the state is changed, it is
     * always set to the private stateChange method.</li>
     * </ul>
     * <p>
     * Initialized in constructor, and its reference is never changed afterwards. Must be non-null.
     * </p>
     *
     * @private
     * @type JSONObject
     */
    var _parameters;

    /**
     * <p>
     * Represents the processor is suspended or not
     * </p>
     * The processor will be suspended when the retry attempts fail in the stateChange method. It's set to false
     * initially. It has isSuppended method to access its value.
     *
     * @private
     * @type boolean
     */
    var _suspended = false;

    /**
     * <p>
     * Represents the processor is running or not.
     * </p>
     * <p>
     * It will set to false if there is no data to send. And set to true when there is data to send. Set to false
     * initially.
     * </p>
     *
     * @private
     * @type boolean
     */
    var _running = false;

    /**
     * <p>
     * Represent the remaining number of retries the processor has to resend the request to sever.
     * </p>
     * <p>
     * It's set to retryCount constructor, and it will also be reset when the response is received successfully. It
     * will decrease by 1 if the state of the XMLHttpRequest is complete, but the status of the HTTP response is not
     * 0 or 200 in the stateChange method.
     * </p>
     * <p>
     * It must be &lt;= retryCount and &gt;= 0.
     * </p>
     *
     * @private
     * @type int
     */
    var _remainingRetries;

    /**
     * <p>
     * This function will be called if the state is changed inside the AjaxProcessor.
     * </p>
     * <p>
     * It will call the onSuccess callback function if the response is successful, otherwise, it will retry the
     * request, and after all retry attempts fail, it will call the onRetryFailed callback function.
     * </p>
     *
     * @private
     */
    function stateChange() {
        // ignore other state
        if (_ajaxProcessor.getState() != 4) {
            return;
        }

        if ((_ajaxProcessor.getStatus() == 200) || (_ajaxProcessor.getStatus() == 0)) {
            // reset the remaining retry
            _remainingRetries = _parameters.retryCount;

            // remove the current request data
            _parameters.sendingData = null;

            // call onSuccess if necessary
            if (_parameters.onSuccess) {
                _parameters.onSuccess(_thisObj);
            }

            // wait a moment before sending the next request
            setTimeout(function() {
                sendRequest();
            }, _parameters.waitInterval);
        } else {
            if (_remainingRetries == 0) {
                _suspended = true;
                // buffer the current request data
                _thisObj.addSendingData(_parameters.sendingData);
                _parameters.sendingData = null;

                // call onRetryFailed if necessary
                if (_parameters.onRetryFailed) {
                    _parameters.onRetryFailed(_thisObj, _ajaxProcessor.getStatus(), _ajaxProcessor.getStatusText());
                }
            } else {
                if (_running) {
                    _remainingRetries = _remainingRetries - 1;

                    if (_parameters.accumulative) {
                        // include the added data in the retry
                        _thisObj.addSendingData(_parameters.sendingData);
                    }

                    // retry
                    setTimeout(function() {
                        sendRequest();
                    }, _parameters.waitInterval);
                }
            }
        }
    }

    /**
     * <p>
     * Send the request to server.
     * </p>
     * <p>
     * If accumulative mode is on, it will send the aggregated data, and otherwise it will send one request data each
     * time.
     * </p>
     *
     * @private
     */
    function sendRequest() {
        // check if accumulative mode is on
        if (_parameters.accumulative == true) {
            if (_aggregatedSendingData) {
                _parameters.sendingData = _aggregatedSendingData;
                _aggregatedSendingData = null;
                _ajaxProcessor.request(_parameters);
            } else {
                _running = false;
            }
        } else {
            if (_remainingRetries < _parameters.retryCount) {
                // it's a retry request.
                _ajaxProcessor.request(_parameters);
            } else {
                // it's a normal request
                if (_sendingDataArray.length != 0) {
                    _parameters.sendingData = _sendingDataArray.pop();
                    _ajaxProcessor.request(_parameters);
                } else {
                    _running = false;
                }
            }
        }
    }

    /**
     * <p>
     * Add the data to send.
     * </p>
     * <p>
     * It will aggregate data if the accumulative mode is on, otherwise it will simply add the data into the array.
     * </p>
     *
     * @param sendingData the data to send.
     *
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is null, or the sendingData's data type
     * doesn't match the old ones when the accumulative mode is on, or the sendingData's xml root node name doesn't
     * match the old one when accumulative mode is on and data type is "xml".
     */
    this.addSendingData = function(sendingData) {
        // check parameter
        js.topcoder.ajax.Helper.checkNotNull(sendingData, "sendingData");

        // check if accumulative is on
        if (_parameters.accumulative == true) {
            var currentSendingDataType = null

            if (sendingData.nodeType) {
                currentSendingDataType = "xml";
            } else {
                // determine the data type of the sendingData.
                var text = JSON.stringify(sendingData);

                if ((text.charAt(0) == '{') && (text.charAt(text.length - 1) == '}')) {
                    currentSendingDataType = "jsonObject";
                } else if ((text.charAt(0) == '[') && (text.charAt(text.length - 1) == ']')) {
                    currentSendingDataType = "jsonArray";
                } else {
                    currentSendingDataType = "formData";
                }
            }

            if (_sendingDataType == null) {
                if ((currentSendingDataType == "jsonObject") || (currentSendingDataType == "jsonArray")) {
                    _sendingDataType = "json";
                } else {
                    _sendingDataType = currentSendingDataType;
                }

                if (_sendingDataType == "xml") {
                    _xmlRootNodeName = sendingData.tagName;
                }
            } else {
                // verify the sendingData is of the same type as the old ones.
                if (!((currentSendingDataType == _sendingDataType) ||
                    ((_sendingDataType == "json") &&
                      ((currentSendingDataType == "jsonObject") || (currentSendingDataType == "jsonArray"))))) {
                    throw new js.topcoder.ajax.IllegalArgumentException("sendingData",
                        "The data type of sending data doesn't match the old one.");
                }

                // verify the root node of the sending data.
                if (currentSendingDataType == "xml") {
                    if (_xmlRootNodeName != sendingData.tagName) {
                        throw new js.topcoder.ajax.IllegalArgumentException("sendingData",
                            "The sendingData's xml root node name doesn't match the old one.");
                    }
                }
            }

            // aggregate data.
            if (_aggregatedSendingData == null) {
                if (currentSendingDataType == "jsonObject") {
                    _aggregatedSendingData = [];  // initialize it to an JSON array.
                    _aggregatedSendingData[0] = sendingData;
                } else {
                    _aggregatedSendingData = sendingData;
                }
            } else {
                if (currentSendingDataType == "jsonObject") {
                    _aggregatedSendingData.push(sendingData);
                } else if (currentSendingDataType == "jsonArray") {
                    _aggregatedSendingData.push(sendingData);
                } else if (currentSendingDataType == "xml") {
                    for (var idx = 0; idx < sendingData.childNodes.length; idx++) {
                        _aggregatedSendingData.appendChild(sendingData.childNodes[idx]);
                    }
                } else {
                    _aggregatedSendingData = _aggregatedSendingData + "&" + sendingData;
                }
            }
        } else {
            // just put it into array if accumulative is off
            _sendingDataArray.push(sendingData);
        }

        // make the processor as running if necessary
        if (!_suspended && !_running) {
            _running = true;
            // try to send the request data.
            sendRequest();
        }
    }

    /**
     * <p>
     * Return the wait interval (in milliseconds).
     * </p>
     *
     * @return {int} the wait interval (in milliseconds).
     *
     * @public
     */
    this.getWaitInterval = function() {
        return _parameters.waitInterval;
    }

    /**
     * <p>
     * Set the wait interval (in milliseconds).
     * </p>
     *
     * @param {int} waitInterval wait interval (in milliseconds).
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is not a long value or it's &lt; 0.
     *
     * @public
     */
    this.setWaitInterval = function(waitInterval) {
        // check parameter
        js.topcoder.ajax.Helper.checkNonNegativeInteger(waitInterval, "waitInterval");

        _parameters.waitInterval = waitInterval;
    }

    /**
     * <p>
     * Return the retry count.
     * </p>
     *
     * @return {int} the retry count.
     *
     * @public
     */
    this.getRetryCount = function() {
        return _parameters.retryCount;
    }

    /**
     * <p>
     * Set the retry count.
     * </p>
     *
     * @param retryCount the retry count.
     * @throws js.topcoder.ajax.IllegalArgumentException if the argument is not an int value or it's &lt; 0
     *
     * @public
     */
    this.setRetryCount = function(retryCount) {
        // check parameter
        js.topcoder.ajax.Helper.checkNonNegativeInteger(retryCount, "retryCount");

        _parameters.retryCount = retryCount;
    }

    /**
     * <p>
     * resume the processor to continue to send the request.
     * </p>
     *
     * @public
     */
    this.resume = function() {
        _suspended = false;
        // call sendRequest again
        sendRequest();
    }

    /**
     * <p>
     * Indicate the process is suspended or not because of all the retry attempts fail.
     * </p>
     *
     * @return {boolean} a boolean value indicating the process is suspended or not.
     *
     * @public
     */
    this.isSuspended = function() {
        return _suspended;
    }

    /**
     * <p>
     * Returns the response to the request as text.
     * </p>
     *
     * @return {String} the response to the request as text.
     *
     * @public
     */
    this.getResponseText = function() {
        return _ajaxProcessor.getResponseText();
    }

    /**
     * <p>
     * Returns the response to the request as XML DOM Document
     * </p>
     *
     * @return {Document} the response to the request as XML DOM Document
     *
     * @public
     */
    this.getResponseXml = function() {
        return _ajaxProcessor.getResponseXml();
    }

    // check parameter
    js.topcoder.ajax.Helper.checkNotNull(parameters, "parameters");

    // check url
    js.topcoder.ajax.Helper.checkString(parameters.url, "parameters.url");

    // check user
    if (js.topcoder.ajax.Helper.checkDefined(parameters.user)) {
        js.topcoder.ajax.Helper.checkString(parameters.user, "parameters.user");
    }

    // check password
    if (!js.topcoder.ajax.Helper.checkDefined(parameters.user) &&
        js.topcoder.ajax.Helper.checkDefined(parameters.password)) {
        throw new js.topcoder.ajax.IllegalArgumentException("parameters.password",
            "The parameters.password parameter must be specified together with user.");
    }

    // check waitInterval
    if (js.topcoder.ajax.Helper.checkDefined(parameters.waitInterval)) {
        js.topcoder.ajax.Helper.checkNonNegativeInteger(parameters.waitInterval, "parameters.waitInterval");
    }

    // check retryCount
    if (js.topcoder.ajax.Helper.checkDefined(parameters.retryCount)) {
        js.topcoder.ajax.Helper.checkNonNegativeInteger(parameters.retryCount, "parameters.retryCount");
    }

    // initialize
    _ajaxProcessor = new js.topcoder.ajax.AjaxProcessor();

    // copy the parameters into instance variable;
    try {
        _parameters = JSON.parse(JSON.stringify(parameters));
    } catch (e) {
        // xml dom can't be converted into json
        _parameters = parameters;
    }

    if (!js.topcoder.ajax.Helper.checkDefined(_parameters.waitInterval)) {
        _parameters.waitInterval = 0;
    }

    if (!js.topcoder.ajax.Helper.checkDefined(_parameters.retryCount)) {
        _parameters.retryCount = 2;
    }

    if (!js.topcoder.ajax.Helper.checkDefined(_parameters.accumulative)) {
        _parameters.accumulative = true;
    }

    _parameters.onStateChange = stateChange;
    _parameters.method = "POST";
    _remainingRetries = _parameters.retryCount;
    _parameters.onSuccess = parameters.onSuccess;
    _parameters.onRetryFailed = parameters.onRetryFailed;

    if (_parameters.accumulative == false) {
        _sendingDataArray = new Array();
    }

    if (js.topcoder.ajax.Helper.checkDefined(_parameters.sendingData)) {
        this.addSendingData(_parameters.sendingData);
        _parameters.sendingData = null;
    }
}

/**
 * <p>
 * This class define the static utility methods.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>
 * Java Script will run in single-threaded mode, so it's not applicable here. This class is mutable though.
 * </p>
 *
 * @class js.topcoder.ajax.Helper
 *
 * @author TCSDEVELOPER
 * @version 2.0
 * @since 2.0
 */
js.topcoder.ajax.Helper = {
    /**
     * <p>
     * Checks if the given variable is defined, not <code>null</code> and is of the correct
     * type.
     * </p>
     *
     * @param {Object} value variable to check.
     * @param {String} name the name of the variable.
     * @throws js.topcoder.ajax.IllegalArgumentException if value is not defined, null.
     */
    checkNotNull : function(value, name) {
        // check undefined
        if (typeof(value) == "undefined") {
            throw new js.topcoder.ajax.IllegalArgumentException(name,
                "The " + name + " parameter can't be undefined.");
        }

        // check null
        if (value == null) {
            throw new js.topcoder.ajax.IllegalArgumentException(name,
                "The " + name + " parameter can't be null.");
        }
    },

    /**
     * <p>
     * Check if the given value is null (undefined), empty string.
     * </p>
     *
     * @param {String} value variable to check.
     * @param {String} name the name of the variable.
     * @throws js.topcoder.ajax.IllegalArgumentException if value is not defined, null or empty string.
     */
    checkString : function(value, name) {
        // check not null
        js.topcoder.ajax.Helper.checkNotNull(value, name);

        if (typeof(value) != "string") {
            throw new js.topcoder.ajax.IllegalArgumentException(name,
                "The " + name + " parameter must be a string.");
        }

        // check empty
        if (value.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
            throw new js.topcoder.ajax.IllegalArgumentException(name,
                "The " + name + " parameter can't be empty string.");
        }
    },

    /**
     * <p>
     * Check if the given value is a positive integer.
     * </p>
     *
     * @param {Int} value variable to check.
     * @param {String} name the name of the variable.
     * @throws js.topcoder.ajax.IllegalArgumentException if value is not positive integer.
     */
    checkNonNegativeInteger : function(value, name) {
        if ((typeof(value) != "number") || !new RegExp("(^0$)|(^[1-9][0-9]*$)").test(value)) {
            throw new js.topcoder.ajax.IllegalArgumentException(name,
                "The " + name + " parameter must be non negative integer.");
        }
    },

    /**
     * <p>
     * Return true if the value is not null/undefined, otherwise false.
     * </p>
     *
     * @param value the given value to check.
     * @returns true if the value is not null/undefined, otherwise false.
     */
    checkDefined : function(value) {
        return (typeof(value) != undefined) && (value != null);
    },

    /**
     * <p>
     * Get the xml string for the given xml dom.
     * </p>
     *
     * @param dom the givne xml dom.
     * @returns the xml string of the given xml dom.
     */
    getXml : function(dom) {
        // for IE
        if (dom.xml) {
            return dom.xml;
        } else {
            // for other browsers
            return new XMLSerializer().serializeToString(dom);
        }
    }
}