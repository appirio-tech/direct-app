/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss;

/**
 * <p>
 * Contract for all RSS Feed Transformer.
 * </p>
 *
 * @author pinoydream
 * @version 1.0
 */
public interface RSSFeedTransformer<T> {
	/**
	 * Transforms the raw data into RSSFeed object.
	 * @param rawData The raw data to be transformed.
	 * @return transformed data as RSSFeed.
	 */
	public RSSFeed transform(T rawData);
}
