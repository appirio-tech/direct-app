/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.result;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.topcoder.direct.services.view.action.ImageViewAction;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Custom result type for viewing an image file.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View Release 3)
 */
public class CustomImageBytesResult implements Result {

    /**
     * Handles the result type execution.
     *
     * @param invocation the action invocation.
     * @throws Exception if any error.
     */
    public void execute(ActionInvocation invocation) throws Exception {

        ImageViewAction action = (ImageViewAction) invocation.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType(action.getCustomContentType());
        response.getOutputStream().write(action.getCustomImageInBytes());
        response.getOutputStream().flush();

    }

}