/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.topcoder.direct.services.view.action.contest.launch.AbstractAction;

/**
 * <p>
 * This abstract action provides the session injected by Struts built-in interceptor to the implementing
 * classes.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe and the implementations do not need be
 * thread safe. This should be fine since different instances of this class should be created to serve
 * different user requests and will not be shared across user threads (this will be taken care of by Struts 2
 * framework). So it can be considered as being used in a thread safe manner.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public abstract class SessionAwareAction extends AbstractAction implements SessionAware {

    /**
     * <p>
     * Represents the session in which the action is executing, injected by Struts built-in interceptors.
     * </p>
     *
     * <p>
     * Initialized to null, can be any value. It is set by setter.
     * </p>
     */
    private Map<String, Object> session;

    /**
     * Default constructor, it's protected to prevent external initialization.
     */
    protected SessionAwareAction() {
    }

    /**
     * Getter for session.
     *
     * @return the session
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * Setter for session.
     *
     * @param session the session
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
