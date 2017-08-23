/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;

/**
 * Base model Direct REST API
 */
public class BaseRestResult {
    /**
     * The metadata.
     */
    private Metadata metadata;

    /**
     * Gets the metadata.
     *
     * @return the metadata.
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Set the metadata.
     *
     * @param metadata the metadata.
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
