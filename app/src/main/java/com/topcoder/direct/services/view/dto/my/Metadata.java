/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;

/**
 * <p>
 *   The challenge json results metadata.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0
 */
public class Metadata {
    /**
     * The total count of the result
     */
    private int totalCount;

    /**
     * Gets the total count.
     *
     * @return the total count.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the total count.
     *
     * @param totalCount the total count.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
