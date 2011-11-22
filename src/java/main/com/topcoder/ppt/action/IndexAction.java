/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>A <code>Struts 2</code> action used for handling requests for viewing home page.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class IndexAction extends ActionSupport {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3766186825305662710L;

    /**
     * Construct a new <code>IndexAction</code> instance.
     */
    public IndexAction() {
        
    }
    
    /**
     * Handles the incoming request.
     *
     * @return Action.SUCCESS
     */
    public String execute() {
        return SUCCESS;
    }
}
