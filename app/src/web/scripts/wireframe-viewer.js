/**
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * This is the script file for the validators and processors.
 *
 * -Version 1.1 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0) Change notes:
 * - - Make the wireframe viewer full screen.
 *
 * -Version 1.2 (Release Assembly - TopCoder Direct Wireframe Viewer Bug Fixes v1.0) Change notes:
 * - - Change the way to set the iframe's src attribute. Now first it will use AJAX to get the location
 * - - of the index page, then set the iframe's src attribute directly. This can avoid the rendering
 * - - BUG in FireFox.
 *
 * @author TCSASSEMBLER
 * @version 1.2
 * @since TC-Studio - Wireframe Viewer Modal Window Direct integration assembly v1.0
 */

// configuration of the white list for url redirection
var WHITE_LIST = ['http://localhost:8080'];
// configuration of the memory usage increase limit
var THRESHOLD_INCREASE_LIMIT = 0;
// configuration of the interval of the memory consumption checking, 5 seconds by default
var MEMORY_CHECK_INTERVAL = 5000; 

/**
 * The wireframe popup window class. 
 */
function WireFramePopupWindow() {
    // the wireframe url
    this.wireframeURL = "";
    // the validators
    this.validators = [];
    // the processors
    this.processors = [];
    // copyright
    this.copyrightComment = "";
}

/**
 * The show wireframe method. 
 */
WireFramePopupWindow.prototype.showWireframe = function() {
}
/**
 * The validate wireframe method. 
 */
WireFramePopupWindow.prototype.validateWireframe = function() {
}
/**
 * The process wireframe code method. 
 */
WireFramePopupWindow.prototype.processWireframeCode = function() {
}

/**
 * The wireframe processor base class. 
 */
function WireframeProcessor() {
}

/**
 * The process data method.
 * input:
 *      divID: the DOM element
 */
WireframeProcessor.prototype.processData = function(divID) {
}

/**
 * The cross site script checking processor class. 
 */
function CrossSiteScriptingProcessor() {
}
CrossSiteScriptingProcessor.prototype = new WireframeProcessor();
CrossSiteScriptingProcessor.prototype.constructor = CrossSiteScriptingProcessor;
/**
 * The process data method.
 * input:
 *      divID: the DOM element
 */
CrossSiteScriptingProcessor.prototype.processData = function(divID) {
    if(!divID) {
        return;
    }
    var nodes;
    if(divID.hasChildNodes())
    {
        nodes = divID.childNodes;
        this.loopNodeChildren(nodes);
    }
}
/**
 * Loop children element method.
 * input:
 *      nodes: the children DOM elements
 */
CrossSiteScriptingProcessor.prototype.loopNodeChildren = function(nodes) {
    if(!nodes) {
        return;
    }
    var node, content;
    for(var i = 0; i < nodes.length; i++)
    {
        node = nodes[i];
        content = '';
        if(node.nodeName === 'SCRIPT') {   // <script>... $UNTRUSTED DATA HERE$  </script>
            content = node.textContent || node.innerText;
            //console.log('[CrossSiteScriptingProcessor] Script: ' + content);
        } 
        else if (node.nodeType === 8)  // <!--... $UNTRUSTED DATA HERE$  -->
        {
            content = node.nodeValue;
            //console.log('[CrossSiteScriptingProcessor] Comment: ' + content);
        }
        else if(node.nodeType === 1)
        {
            if(node.nodeName === 'STYLE') {  // <style>... $UNTRUSTED DATA HERE$  </style>
                if(node.childNodes != null && node.childNodes[0] != undefined) {
                    content = node.childNodes[0].nodeValue;
                }                       
                //console.log('[CrossSiteScriptingProcessor] Style: ' + content);
            } else if(node.nodeName == 'DIV') { // <div>... $UNTRUSTED DATA HERE$  </div>
                content = node.innerText;
                //console.log('[CrossSiteScriptingProcessor] Div: ' + content);
            } else if(node.nodeName == 'BODY') { // <body> ... $UNTRUSTED DATA HERE$  </body>
                content = node.innerText;
                //console.log('[CrossSiteScriptingProcessor] Body: ' + content);
            }
            var attributes = node.attributes;  // <div... $UNTRUSTED DATA HERE$  =test /> in an attribute name
            for(var v = 0;  v < attributes.length; v++) {
                //console.log('[CrossSiteScriptingProcessor] Attribute name: ' + attributes.item(v).nodeName);
                this.checkContent(attributes.item(v).nodeName, node);
            }
            var tagName = node.tagName;  // <... $UNTRUSTED DATA HERE$  href="/test" /> in a tag name
            //console.log('[CrossSiteScriptingProcessor] Tag name: ' + tagName);
            if(tagName != undefined) {
                this.checkContent(tagName, node);
            }
        }
        
        this.checkContent(content, node);
        if(node.childNodes) {
            this.processData(node);
        }
    }
}
/**
 * The method to apply the real rules.
 * input:
 *      content: the content to check
 *      node: the node element that the content belongs to
 */
CrossSiteScriptingProcessor.prototype.checkContent = function(content, node) {
    if(!content || !node) {
        return;
    }
    var rules = ['eval', '.innerHTML', '.outerHTML', 'document.write', 'document.writeln'];
    for(var i = 0; i < rules.length; i++) {
        if(content.indexOf(rules[i]) != -1) {
            console.log('[CrossSiteScriptingProcessor] Untrusted according to rule: no ' 
                    + rules[i] + ' is allowed, node id: ' + node.id + ", content: " 
                    + content.substring(content.indexOf(rules[i]), content.indexOf(rules[i]) + 100));
        }
    }
    if(content.indexOf('<%') != -1 && content.indexOf('%>') != -1) {
        console.log('[CrossSiteScriptingProcessor] Untrusted according to rule: no <%...%> is allowed, node id: ' 
                    + node.id + ", content: " + content.substring(content.indexOf('<%'), content.indexOf('<%') + 100));
    }
}

/**
 * The wireframe source validator base class. 
 */
function WireframeSourceValidator() {
    // the error message
    this.errorMessage = "";
}

/**
 * The validate DOM base method.
 * input:
 *      divID: the DOM element
 */
WireframeSourceValidator.prototype.validateDOM = function(divID) {
}

/**
 * The URL redirection validator class. 
 */
function URLRedirectionValidator() {
    // the white list
    this.urlWhitelist = [];
}
URLRedirectionValidator.prototype = new WireframeSourceValidator();
URLRedirectionValidator.prototype.constructor = URLRedirectionValidator;
/**
 * The validate DOM method.
 * input:
 *      divID: the DOM element
 */
URLRedirectionValidator.prototype.validateDOM = function(divID) {
    if(!divID) {
        return;
    }
    var nodes;
    if(divID.hasChildNodes()) {
        nodes = divID.childNodes;
        this.loopNodeChildren(nodes);
    }
}
/**
 * Loop children element method.
 * input:
 *      nodes: the children DOM elements
 */
URLRedirectionValidator.prototype.loopNodeChildren = function(nodes) {
    if(!nodes) {
        return;
    }
    var node;
    for(var i = 0; i < nodes.length; i++) {
        node = nodes[i];
        // node.href, if there is no prefix "http" in the value of href, the node.href will 
        // return the value with the domain of the current application in front of it
        if(node.nodeType === 1 && node.nodeName === 'A' && node.href != "") {
            console.log("[URLRedirectionValidator] element: " + node.tagName + ", id: " + node.id + ", href: " 
                                + node.href + ", parent: " + node.parentNode.nodeName + ", id: " + node.parentNode.id);
            if(!this.checkWhitelist(node)) {
                this.errorMessage += "Detected unrecognized/disallowed link: " + node.href + " at location " 
                                + node.parentNode.nodeName + ", id: " + node.parentNode.id + "\n";
            }   
        }
        if(node.hasChildNodes()) {
            this.validateDOM(node);
        }
    }
}
/**
 * Check against the white list method.
 * input:
 *      node: the DOM element
 */
URLRedirectionValidator.prototype.checkWhitelist = function(node) {
    if(!node) {
        return;
    }
    // specially for href="javascript:;", this should not be an error
    if(node.href != "" && node.href.indexOf("http") == -1 && node.href.indexOf("https") == -1 
                        && node.href.indexOf("ftp") == -1 && node.href.indexOf("ftps") == -1) {
        return true;
    }
    if(this.urlWhitelist.length === 0) {// if no white list specified, then no href is allowed
        return false;
    }
    if(node.href != "") {
        for(var i = 0; i < this.urlWhitelist.length; i++) {
            var prefix = this.urlWhitelist[i];
            if(node.href.toLowerCase().indexOf(prefix.toLowerCase()) === 0) {
                return true;
            }
        }
        return false;
    } else {
        return true;
    }
}

/**
 * The wireframe resource validator class. 
 */
function WireFrameResourceValidator() {
    // the size snapshots array to store the checking history
    this.sizeSnapshots = [];
    // the threshold of the memory usage increase limit
    this.thresholdIncreaseLimit;
    // the size of the memory usage in current checking
    this.size = 0;
    // the unique id of the current checking
    this.uid;
}
WireFrameResourceValidator.prototype = new WireframeSourceValidator();
WireFrameResourceValidator.prototype.constructor = WireFrameResourceValidator;
/**
 * The validate DOM method.
 * input:
 *      divID: the DOM element
 */
WireFrameResourceValidator.prototype.validateDOM = function(obj) {
    if(!obj) {
        return;
    }
    this.uid = (new Date()).getTime();
    this.loopDOM(obj);
    this.sizeSnapshots.push(this.size);
    console.log('[WireFrameResourceValidator] Current snapshots array: ' + this.sizeSnapshots);
    console.log('[WireFrameResourceValidator] Memory consumption in this round: ' + this.size);
    this.size = 0;
}
/**
 * Loop DOM element method.
 * input:
 *      obj: the processing DOM element
 */
WireFrameResourceValidator.prototype.loopDOM = function(obj) {
    if(!obj || (typeof obj == 'function')) {
        return ;
    }

    // if obj is an array
    if(obj instanceof Array) {
        this.size += obj.length;
        for(var i=0; i < obj.length; i++) {
            if(!$.browser.msie) {
                if(obj[i].__leaks_checked == this.uid) {
                    continue;
                }
                obj[i].__leaks_checked = this.uid;
            }
            this.loopDOM(obj[i]);
        }
    }
    else if(typeof obj == 'object') { // if obj is an object
        var rval = [], prop;
        for(prop in obj) {
            rval.push(prop);
        }
        this.size += rval.length;
        for(var key in obj) {
            if(key.indexOf('selection') != -1) {
                return;
            }
            if(obj[key] == null || obj[key].__leaks_checked == this.uid) {
                return;
            }
            if(!$.browser.msie) {
                obj[key].__leaks_checked = this.uid;
            }
            this.loopDOM(obj[key])
        }
    }
}

/**
 * open the popup
 * input:
 *      string: the wireframe url
 */
function activate_modal(FRAME_URL, submissionId){ 
        // $('#mask').css({ 'display' : 'block', opacity : 0});
        // $('#mask').fadeTo(500,0.5);
        var wireframe = new WireFramePopupWindow();
        wireframe.showWireframe(FRAME_URL, submissionId);
}

function fixWindowSize() {
    $("#showWireframeModal").width($(window).width());
    $("#showWireframeModal").height($(window).height());
    $("#showWireframeModal .modalBody").height($(window).height() - 48);
}

/**
  * WireFramePopupWindow
  */
WireFramePopupWindow.prototype.showWireframe = function(FRAME_URL, submissionId){
    fixWindowSize();
    hasAlert = false;
    modalLoad("#showWireframeModal");
    //$('#frame_window').fadeIn(500);
    $('#submissionIdInTitle').html(submissionId);
    $('#frame_window').attr('src', FRAME_URL);
}
WireFramePopupWindow.prototype.validateWireframe = function(){
        // declare and initialize the local variables 
        var crossSiteScriptProcessor = new CrossSiteScriptingProcessor();

        var urlValidator = new URLRedirectionValidator();
        urlValidator.urlWhitelist = WHITE_LIST;
        urlValidator.errorMessage = "";

        var resourceValidator = new WireFrameResourceValidator();
        resourceValidator.sizeSnapshots = new Array();
        resourceValidator.thresholdIncreaseLimit = THRESHOLD_INCREASE_LIMIT;
        
        //urlValidator
        var display = $('#frame_window').css('display');
        if(display == 'none' || display == undefined) {
            alert("Please view the wireframe page first.");
            return;
        }
        var doc = window.frames["my_frame"].document;
        urlValidator.validateDOM(doc.getElementsByTagName('body')[0]);
        if(urlValidator.errorMessage.length != 0) {
            alert(urlValidator.errorMessage);
        } else {
            alert('No disallowed URL redirection found.');
        }
        urlValidator.errorMessage = '';
        
        //crossSiteProcessor
        var display = $('#frame_window').css('display');
        if(display == 'none' || display == undefined) {
            alert("Please view the wireframe page first.");
            return;
        }
        var doc = window.frames["my_frame"].document;
        crossSiteScriptProcessor.processData(doc.getElementsByTagName('html')[0]);
        
        //resValidator
        var display = $('#frame_window').css('display');
        if(display == 'none' || display == undefined) {
            alert("Please view the wireframe page first.");
            return;
        }
        var win = document.getElementById("frame_window").contentWindow;
        var myInterval = setInterval(function(){
            resourceValidator.validateDOM(win);
            var rounds = resourceValidator.sizeSnapshots.length;
            var snapshots = resourceValidator.sizeSnapshots;
            if(rounds > 0) {
                if(snapshots[rounds-1] - snapshots[0] > resourceValidator.thresholdIncreaseLimit) {
                    alert('The memory usage increase is beyond the limit. ');
                    clearInterval(myInterval);
                }
            }
        }, MEMORY_CHECK_INTERVAL);
}


/**
 * Load of the page.
 */
$(document).ready(function() {
    
    //disable the context menu 
    //$(window).bind('contextmenu', false);
    
        
    // close the popup
    /*$('.close_modal').click(function(){
        $('#mask').fadeOut(500);
        $('#frame_window').fadeOut(500);
    });*/

    $('#validator').click(function(){
        var wireframe = new WireFramePopupWindow();
        wireframe.validateWireframe();
    });
    
    $('.actButtonzoom').click(function(){
        var submissionId = $(this).attr("rel");
        var submissionUrl = "/direct/viewWireframeSubmission.action?submissionId=" + submissionId;
        $.ajax({
            type: 'GET',
            url: submissionUrl,
            dataType: 'json',
            cache:false,
            async:true,
            beforeSend: modalPreloader,
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        activate_modal("/direct" + result.indexPage, submissionId);
                    },
                    function(errorMessage) {
                        modalClose();
                        showServerError(errorMessage);
                    }
                );
            }
        });
        return false;
    });

    $(window).resize(function() {
        if ($("#showWireframeModal").is(":visible")) {
            fixWindowSize();
            modalLoad("#showWireframeModal");
        }
    });
});


    var alertFallback = true;
    if (typeof console === "undefined" || typeof console.log === "undefined") {
        console = {};
        if (alertFallback) {
            console.log = function(msg) {
                alert(msg);
            };
        } else {
            console.log = function() {};
        }
    }
