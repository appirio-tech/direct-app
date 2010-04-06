/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This class is the central (and currently the single) class  
 * of the AJAX Processor component.</p>
 * <p>It provides the browser-independent implementation of HTTP requests handling.
 * The methods to send request and handle response to it are provided.</p>
 * <p>It can be used to simplify AJAX-enabled applications development,
 * as it takes implementation details away from application developers.</p>
 * 
 * <p>The constructor of the class creates an XMLHttpRequest isntance to be used to do the work.</p>
 *
 * @constructor
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
function AJAXProcessor()
{
	/**
	 * <p>This "private" variable stores XMLHttpRequest object used to do the work.</p>
	 * <p>It is initialized in constructor, null value means that there has
	 * been an error during initialization (probably because of lack of browser support).</p>
	 *
	 * @private
	 */
	this._httpRequest = null;

	/**
	 * <p>This "private" variable stores the id of HTML DOM element, 
	 * innerHTML of which will be updated with the response data.</p>
	 * <p>It is set using setInnerHTMLId method, null value means that
	 * no elemenent contents will be affected by the response received.</p>
	 *
	 * @private
	 */
	this._innerHTMLId = null;

	/*
	 * Start of constructor code. 
	 */

if (window.ActiveXObject) {
        	// This part works with ActiveX object : MSIE browser
            	try {
                	this._httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            	} catch (e) {
                	try {
                    		this._httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                	} catch (e) {
                		// Do nothing here, error will be detected later
                	}
		}
        }
	 else if (window.XMLHttpRequest) { 
        	// This part works with browsers which support 
        	// XMLHTMLRequest object: Mozilla, Safari, etc.
            	this._httpRequest = new XMLHttpRequest();
            	if (this._httpRequest.overrideMimeType) {
                	this._httpRequest.overrideMimeType('text/xml');
                	// See note below about this line
		}
        }


       	if (!this._httpRequest) {
		throw new InitializationException(
			"Error while creating XMLHttpRequest object, most likely the browser doesn't support AJAX");

	}
                                                        
        /*
         * End of constructor code.
         */


	/*
	 * <p>Sets the id of HTML DOM element, innerHTML of which 
	 * will be updated with the response data.</p>
	 * <p>Null id value means that no elemenent contents will be 
	 * affected by the response received.</p>
	 *
	 * @param innerHTMLId the id to be set, can be null
	 */
        this.setInnerHTMLId = setInnerHTMLId;
        function /* void */ setInnerHTMLId(/* String */ innerHTMLId) {
        	this._innerHTMLId = innerHTMLId; 
        }

        /*
	 * <p>Returns the id of HTML DOM element, innerHTML of which 
	 * will be updated with the response data.</p>
	 * <p>Null id value means that no elemenent contents will be 
	 * affected by the response received.</p>
	 *
	 * @return the id of HTML DOM element, innerHTML of which 
	 *	will be updated with the response data.
	 */
        this.getInnerHTMLId = getInnerHTMLId;
        function /* String */ getInnerHTMLId() {
        	return this._innerHTMLId; 
        }



	/*
	 * <p>Handles state change event.</p>
	 * <p>If specfied innerHTMLId value is not null, sets the innerHTML of 
	 * the HTML DOM element denoted by the specified id to the specified value,
	 * if the value itself also is not null.
	 * Otherwise does nothing (intended to be overriden by subclasses).</p>
	 * <p>This function gets called when the state of XMLHttpRequest object changes
	 * if the other event handler for it was not specified.</p>
	 * 
	 * @param innerHTMLId the id of HTML DOM element to update contents of, can be null
	 * @param responseValue the value(contents) of the response to be handled, can be null
	 * @throws IlegalArgumentException if there is no element with the specified id in the document 
	 */
	this.stateChange = stateChange;
	function /* void */ stateChange(/* String */ innerHTMLId, /* String or DOM Node */ responseValue) {
	        if (innerHTMLId  && responseValue) {
			// Find node to alter inner HTML of
			var node = document.getElementById(innerHTMLId);
			if (node == null) {
				throw new IllegalArgumentException("innerHTMLId", 
					"The element with the specified id was not found in the document");
			}
                
                	if (responseValue.nodeType) {
				// Remove all child nodes
				while (node.hasChildNodes()) {
					node.removeChild(node.lastChild);
				}
				// Add new contents as a child
				if (responseValue.documentElement && responseValue.documentElement.xml) {
					node.innerHTML = responseValue.documentElement.xml;
				} else {
					node.appendChild(responseValue.documentElement ? 
						responseValue.documentElement : responseValue);
				}
			} else {
				node.innerHTML = responseValue;
			} 							
		}
	}

	/**
	 * <p>Returns the state of the object:</p>
	 * <ul>
	 * <li>0 = uninitialized (XMLHttpRequest.open() has not been called yet) </li>
	 * <li>1 = loading (XMLHttpRequest.send() has not been called yet) </li>
	 * <li>2 = loaded (XMLHttpRequest.send() has been called, headers and status are available) </li>
	 * <li>3 = interactive (Downloading, XMLHttpRequest.responseText holds the partial data) </li>
	 * <li>4 = complete (Finished with all operations) </li>
	 * </ul>
	 * <p>The state of the object is taken from XMLHttpRequest.readyState property.</p>
	 * 
	 * @return the state of the object, as stated above
	 */
	this.getState = getState;
	/* int */ function getState() {
		return this._httpRequest.readyState;			
	}

	/**
	 * <p>Returns the response status for the HTTP request 
	 * as a number (e.g. 404 for "Not Found" or 200 for "OK").
	 * </p>
	 * <p>The response status is taken from XMLHttpRequest.status property.</p>
	 *
	 * @return the response status for the HTTP request, as a number
	 */
	this.getStatus = getStatus;
	/* int */ function getStatus() {
		return this._httpRequest.status;	
	}

	/**
	 * <p>Returns the response to the request as text, or null 
	 * if the request is unsuccessful or has not yet been sent.</p>
	 * <p>The response text is taken from XMLHttpRequest.responseText property.</p>
	 *
	 * @return the response to the request as text, or null 
	 * 		if the request is unsuccessful or has not yet been sent.
	 */	 	
	this.getResponseText = getResponseText;
	/* String */ function getResponseText() {
		return this._httpRequest.responseText;
	}

	/**
	 * <p>Returns the response to the request as XML DOM Document, or null 
	 * if the request is unsuccessful or has not yet been sent.</p>
	 * <p>The response XML is taken from XMLHttpRequest.responseXml property.</p>
	 *
	 * @return the response to the request as XML DOM Document, or null 
	 * 		if the request is unsuccessful or has not yet been sent.
	 */	 	
	this.getResponseXml = getResponseXml;
	/* Document */ function getResponseXml() {
		return this._httpRequest.responseXML; 
	}                                             

	/**
	 * <p>Sends an HTTP request to the server.</p>
	 *
	 * @param parameters a JSON-style object (it should not be null), which contains the parameters:
	 * <ul>
	 * <li><em>method</em> - a string, which specifies the request method, 
	 *	should be either "GET" or "POST", can be null, default is "GET"
	 * <li><em>url</em> - a string which specifies where to send the request to, 
	 *	should not be null or empty string
	 * <li><em>async</em> - true if the method needs to work asynchronously, false otherwise, default is true 
	 * <li><em>user></em> - a user name string, can be null should not be empty string,
	 *	null means that no user is authenticated
	 * <li><em>password</em> - a password string, can be null or empty string, 
	 * should be specified together with user
	 * <li><em>sendingText</em> - the text (string, which represets encoded form) or XML (DOM Document) to be sent, 
	 *	null if nothing is to be sent
	 * <li><em>responseType</em> - a desired type of response content, can be "xml" or "text"
	 *	"xml" means that DOM Node will be passed to stateChange function, 
	 *	"text" means that string will be passed to it, can be null, default is "text"
	 * <li><em>onStateChange</em> - a function to be executed on state change
	 * </ul> 
	 *
	 * @throws IllegalArgumentException if parameters argument is null or any of its fields has an illegal value
	 */
	this.request = request;
	/* void */ function request(/* JSON object */ parameters) {
		if (!parameters) {
			throw new IllegalArgumentException("parameters", 
				"A non-null parameter should be passed to the method");
		}

		if (!parameters.url || parameters.url.length == 0) {
			throw new IllegalArgumentException("parameters.url", 
				"The request URL should be specified");
		}

		if (parameters.method && parameters.method != "GET" && parameters.method != "POST") {
			throw new IllegalArgumentException("parameters.method",      
				"The request method should be specified as either \"GET\" or \"POST\"");
		}
		
		var _this = this;
		// Choose state change event handler
		if (parameters.onStateChange) {
			// Use custom state change handler
                	this._httpRequest.onreadystatechange = parameters.onStateChange; 
        	} else if (parameters.responseType == "xml") {
        	        // Use standard state change handler with XML response
                	this._httpRequest.onreadystatechange = function() { 
                		if (_this.getState() == 4 && (_this.getStatus() == 200 || _this.getStatus() == 0)) {
                			_this.stateChange(_this.getInnerHTMLId(), _this.getResponseXml()); 
        			}
        		};
        		// Override mime type
			//this._httpRequest.overrideMimeType("text/xml");
			if (window.XMLHttpRequest) { 
		        	// This part works with browsers which support 
		        	// XMLHTMLRequest object: Mozilla, Safari, etc.
		                this._httpRequest.overrideMimeType('text/xml');
			}
        	} else {
        		// Use standard state change handler with text response
                	this._httpRequest.onreadystatechange = function() { 
        			if (_this.getState() == 4 && (_this.getStatus() == 200 || _this.getStatus() == 0)) {
        				_this.stateChange(_this.getInnerHTMLId(), _this.getResponseText()); 
        			}
        		};        	              	
        	}

              	// Initialize a request and specify the method, URL, and authentication information	
		this._httpRequest.open((parameters.method ? parameters.method : "GET"),
					parameters.url, 
					(parameters.async != null ? parameters.async : true), 
					parameters.user, 
					parameters.password);
		// If request method is POST, set appropriate content type
		if (parameters.method == "POST") {
			if (parameters.sendingText.nodeType) {
				this._httpRequest.setRequestHeader("Content-Type", "text/xml");
			} else {
				this._httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			}
		}
		// Send an HTTP request to the server
        	this._httpRequest.send(parameters.method == "GET" ?  null : parameters.sendingText);
	}


	/**
	 * <p>This class defines an exception thrown in the case 
	 * when illegal argument is passed in to the function.</p>
	 * <p>This class is derived from the Error class</p>
	 *
	 * <p>The constructor takes in the illegal argument name,
	 * and a detailed error description message</p>
	 * @constructor
	 * @param argumentName the name of the illegal argument
	 * @oaram message the error message
	 *
	 */
        AJAXProcessor.IllegalArgumentException = IllegalArgumentException;
	function IllegalArgumentException(/* String */ argumentName, /* String */ message) {
		/*
		 * This variable stores the name of illegal argument.
		 * It is initialized in constructor.
		 */
		this.argumentName = null;
		
		/*
	 	 * Start of constructor code. 
	 	 */

		// Call superclass constructor
		Error.call(this, message);
		// Store argument name
		this.argumentName = argumentName;		

		/*
	 	 * End of constructor code. 
	 	 */
	}


        /**
	 * <p>This class defines an exception thrown in the case 
	 * when any error happens while AJAXProcessor is being initialized (constructed).</p>
	 * <p>This class is derived from the Error class</p>
	 * <p>The constructor takes in the detailed error description message</p>
	 * @constructor
	 * @param message the error message
	 *
	 */
        AJAXProcessor.InitializationException = InitializationException;
	function InitializationException(/* String */ message) {
		/*
		 * This variable stores the name of illegal argument.
		 * It is initialized in constructor.
		 */
		this.argumentName = null;
		
		/*
	 	 * Start of constructor code. 
	 	 */

		// Call superclass constructor
		Error.call(this, message);
		// Store argument name
		this.argumentName = argumentName;		

		/*
	 	 * End of constructor code. 
	 	 */
	}                        
}                                                      	

        
