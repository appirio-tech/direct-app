/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;

import java.util.List;

/**
 * <p>
 * The model for the response returned by the Direct REST API.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0
 */
public class RestResult<T> {
    /**
     * The metadata.
     */
    private Metadata metadata;

    /**
     * A list of result returned of type T
     */
    private List<T> content;

    /**
     * Gets the result.
     *
     * @return the result.
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Sets the result.
     *
     * @param content the result.
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * Gets the metadata.
     *
     * @return the metadata.
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Gets the metadata.
     *
     * @param metadata the metadata.
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
