/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A base class for all <code>Struts</code> actions which will be visited by AJAX calls. The implementation can use
 * {@link #setResult(Object)} method to set the result which will be returned as JSON to browser.</p>
 *
 * @author flexme
 * @version 1.0
 */
public abstract class BaseAjaxAction extends ActionSupport {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5168550060666327305L;

    /**
     * Represents the logger.
     */
    protected static final Logger LOGGER = Logger.getLogger(BaseAjaxAction.class);
    
    /**
     * Represents the result which will be returned as JSON to browser.
     */
    private Object result;
    
    /**
     * The whole JSON data will will be returned as JSON to browser. 
     */
    private Map<String, Object> jsonData;
    
    /**
     * Gets the result which will be returned as JSON to browser.
     * 
     * @return the result which will be returned as JSON to browser.
     */
    protected Object getResult() {
        return result;
    }

    /**
     * Sets the result which will be returned as JSON to browser.
     * 
     * @param result the result which will be returned as JSON to browser.
     */
    protected void setResult(Object result) {
        this.result = result;
    }

    /**
     * Gets the whole JSON data will will be returned as JSON to browser. 
     * 
     * @return the whole JSON data will will be returned as JSON to browser. 
     */
    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    /**
     * Construct a new <code>BaseAjaxAction</code> instance.
     */
    public BaseAjaxAction() {
        jsonData = new HashMap<String, Object>();
    }
    
    /**
     * Handles the incoming request. It will wrap the necessary data into jsonData map which will be returned as JSON
     * to browser.
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     */
    public String execute() {
        try {
            executeAction();
            jsonData.put("success", Boolean.TRUE);
            if (result != null) {
                jsonData.put("result", result);
            }
            jsonData.put("fieldErrors", getFieldErrors());
        } catch (Throwable e) {
            jsonData.put("error", Boolean.TRUE);
            jsonData.put("message", e.getMessage());
            LOGGER.error("Error occurs when execute the action" + e, e);
        }
        return SUCCESS;
    }
    
    /**
     * This is the template method where the action logic will be performed by children classes.
     *
     * @throws Exception if any error occurs
     */
    protected abstract void executeAction() throws Exception;
}
