/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Contains details for an RSS Feed.
 * </p>
 *
 * @author pinoydream
 * @version 1.0
 */
public class RSSFeed implements Serializable {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -6828519338139641575L;
	/**
	 * Title.
	 */
	private String title;
	/**
	 * Link.
	 */
	private String link;
	/**
	 * Generator.
	 */
	private String generator;
	/**
	 * Language.
	 */
	private String language;
	/**
	 * Description.
	 */
	private String description;
	/**
	 * Last build date.
	 */
	private String lastBuildDate;
	/**
	 * RSS Items.
	 */
	private List<RSSItem> items = new ArrayList<RSSItem>();
	/**
	 * Default constructor.
	 */
	public RSSFeed() {
	}
	/**
	 * Gets the value of title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Sets the value for title.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Gets the value of link.
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Sets the value for link.
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * Gets the value of generator.
	 * @return the generator
	 */
	public String getGenerator() {
		return generator;
	}
	/**
	 * Sets the value for generator.
	 * @param generator the generator to set
	 */
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	/**
	 * Gets the value of language.
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * Sets the value for language.
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * Gets the value of description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the value for description.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the value of lastBuildDate.
	 * @return the lastBuildDate
	 */
	public String getLastBuildDate() {
		return lastBuildDate;
	}
	/**
	 * Sets the value for lastBuildDate.
	 * @param lastBuildDate the lastBuildDate to set
	 */
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	/**
	 * Add an RSS item.
	 * @param item RSS item.
	 * @return true as specified by Collection.add()
	 */
	public boolean addItem(RSSItem item) {
		return items.add(item);
	}
	/**
	 * Clear all items.
	 */
	public void clearItems() {
		items.clear();
	}
	/**
	 * Add all RSS items from a collection.
	 * @param items collection of RSS Items.
	 * @return true or false as specified by Collection.addAll()
	 */
	public boolean addAllItems(Collection<RSSItem> colItems) {
		return items.addAll(colItems);
	}
	/**
	 * Removes an RSS item.
	 * @param item The item to be removed.
	 * @return true or false as specified by Collection.remove()
	 */
	public boolean removeItem(RSSItem item) {
		return items.remove(item);
	}
	/**
	 * Get the items. 
	 * @return
	 */
	public Collection<RSSItem> getItems() {
		return Collections.unmodifiableCollection(items);
	}	
}
