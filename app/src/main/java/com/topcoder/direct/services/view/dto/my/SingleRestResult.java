/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;

/**
 * Model Direct REST API with single content
 *
 * @param <T>
 */
public class SingleRestResult<T> extends BaseRestResult {
    /**
     * A list of result returned of type T
     */
    private T content;

    /**
     * Gets the result.
     *
     * @return the result.
     */
    public T getContent() {
        return content;
    }

    /**
     * Sets the result.
     *
     * @param content the result.
     */
    public void setContent(T content) {
        this.content = content;
    }
}
