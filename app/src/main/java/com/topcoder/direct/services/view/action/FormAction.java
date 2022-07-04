/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

/**
 * <p>An interface for the actions which expose data for some web form submitted by user to for processing.</p>
 *
 * @author isv
 * @version 1.0
 * @param <T> a type of the form exposed to processors.
 */
public interface FormAction<T> extends TopCoderDirectAction {

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    T getFormData();
}
