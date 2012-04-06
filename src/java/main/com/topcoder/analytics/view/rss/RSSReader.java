/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss;

/**
 * <p>Contract for RSS Readers.</p>
 *
 * @author pinoydream
 * @version 1.0
 */
public interface RSSReader {
    /**
     * Reads an RSS feed from a URL.
     * @param url URL to read RSS from.
     * @return the RSS feed.
     */
    public RSSFeed read(String url);
}
