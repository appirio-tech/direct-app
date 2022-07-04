/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

/**
 * <p>An interface for the actions which expose some data to views to be rendered.</p>
 *
 * @author isv
 * @version 1.0
 * @param <T> a type of the data exposed to views for displaying. 
 */
public interface ViewAction<T> extends TopCoderDirectAction {

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    T getViewData();
}
