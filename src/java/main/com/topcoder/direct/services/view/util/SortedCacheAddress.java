/*
 * Copyright (C) 2017 - 2019 TopCoder Inc., All Rights Reserved.
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
 * Version 1.1 (Topcoder - Integrate Direct with Groups V5)
 * <ul>
 *     <li>Change key use String</li>
 * </ul>
 * @version 1.1
 */
public class SortedCacheAddress implements JbossCacheAddress {
    /**
     * Prefix key
     */
    private String prefix;

    private MaxAge maxAge;

    /**
     * Default prefix
     */
    private static final String DEFAULT_PREFIX = "direct";

    /**
     * List of items for key
     */
    private List<String> items = new ArrayList<String>();

    public SortedCacheAddress() {
        this(DEFAULT_PREFIX);
    }

    public SortedCacheAddress(String prefix) {
        this(prefix, MaxAge.HOUR);
    }

    public SortedCacheAddress(String prefix, MaxAge maxAge) {
        this.prefix = prefix;
        this.maxAge = maxAge;
    }
    /**
     * Add item key
     *
     * @param item item to add
     */
    public void add(String item) {
        items.add(item);
        Collections.sort(items);
    }

    /**
     * Add list of items
     *
     * @param added list of item yo add
     */
    public void addAll(List<String> added) {
        items.addAll(added);
        Collections.sort(items);
    }

    /**
     * Remove item
     *
     * @param item item to be removed
     */
    public void remove (String item) {
        items.remove(item);
    }

    /**
     * Get cache address key
     *
     * @return key
     */
    @Override
    public String getKey() {
        StringBuffer keyBuffer = new StringBuffer(prefix);

        for (String item : items) {
            keyBuffer.append("-");
            keyBuffer.append(item);
        }
        return keyBuffer.toString();
    }

    @Override
    public String getFqn() {
        return new StringBuffer("/").append(maxAge.name()).append("/").append(getKey()).toString();
    }
}
