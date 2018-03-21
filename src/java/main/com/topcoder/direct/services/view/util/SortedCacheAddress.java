/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.web.common.cache.MaxAge;
import com.topcoder.web.common.cache.address.jboss.JbossCacheAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class implement CacheAddress, taken list of key items, and then
 * return a sorted keys on {@link #getKey()}
 *
 * @version 1.0
 */
public class SortedCacheAddress implements JbossCacheAddress {
    /**
     * Prefix key
     */
    private Long prefix;

    private MaxAge maxAge;

    /**
     * Default prefix
     */
    private static final Long DEFAULT_PREFIX = 0L;

    /**
     * List of items for key
     */
    private List<Long> items = new ArrayList<Long>();

    public SortedCacheAddress() {
        this(DEFAULT_PREFIX);
    }

    public SortedCacheAddress(Long prefix) {
        this(prefix, MaxAge.HOUR);
    }

    public SortedCacheAddress(Long prefix, MaxAge maxAge) {
        this.prefix = prefix;
        this.maxAge = maxAge;
    }
    /**
     * Add item key
     *
     * @param item item to add
     */
    public void add(Long item) {
        items.add(item);
    }

    /**
     * Add list of items
     *
     * @param added list of item yo add
     */
    public void addAll(List<Long> added) {
        items.addAll(added);
    }

    /**
     * Remove item
     *
     * @param item item to be removed
     */
    public void remove (Long item) {
        items.remove(item);
    }

    /**
     * Get cache address key
     *
     * @return key
     */
    @Override
    public String getKey() {
        StringBuffer keyBuffer = new StringBuffer(String.valueOf(prefix));
        //sort it, so we'll get same key for same content
        Collections.sort(items);

        for (Long item : items) {
            keyBuffer.append("-");
            keyBuffer.append(String.valueOf(item));
        }
        return keyBuffer.toString();
    }

    @Override
    public String getFqn() {
        return new StringBuffer("/").append(maxAge.name()).append("/").append(getKey()).toString();
    }
}
