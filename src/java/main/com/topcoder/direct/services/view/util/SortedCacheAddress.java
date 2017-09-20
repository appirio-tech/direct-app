/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.web.common.cache.address.CacheAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class implement CacheAddress, taken list of key items, and then
 * return a sorted keys on {@link #getKey()}
 *
 * @version 1.0
 */
public class SortedCacheAddress implements CacheAddress {
    /**
     * Prefix key
     */
    private Long prefix;

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
        this.prefix = prefix;
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
        Collections.sort(items, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o1);
            }
        });

        for (Long item : items) {
            keyBuffer.append("-");
            keyBuffer.append(String.valueOf(item));
        }
        return keyBuffer.toString();
    }

}
