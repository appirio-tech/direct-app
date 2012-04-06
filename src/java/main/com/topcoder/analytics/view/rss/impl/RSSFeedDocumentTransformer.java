/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topcoder.analytics.view.rss.RSSFeed;
import com.topcoder.analytics.view.rss.RSSFeedTransformer;
import com.topcoder.analytics.view.rss.RSSItem;

/**
 * <p>
 * Concrete implementation for {@link RSSFeedTransformer} that transforms {@link Document} object to {@link RSSFeed}.
 * </p>
 *
 * @author pinoydream
 * @version 1.0
 */
public class RSSFeedDocumentTransformer implements RSSFeedTransformer<Document> {
	/**
	 * Item tag name.
	 */
	private static final String ITEM_TAG = "item";
	/**
	 * Title tag name.
	 */
	private static final String TITLE_TAG = "title";
	/**
	 * Link tag name.
	 */
	private static final String LINK_TAG = "link";
	/**
	 * Comment tag name.
	 */
	private static final String COMMENT_TAG = "comments";
	/**
	 * Comment RSS tag name.
	 */
	private static final String COMMENTRSS_TAG = "wfw:commentRss";
	/**
	 * Description tag name.
	 */
	private static final String DESCRIPTION_TAG = "description";
	/**
	 * Author tag name.
	 */
	private static final String AUTHOR_TAG = "dc:creator";
	/**
	 * Description tag name.
	 */
	private static final String PUBLISHED_DATE_TAG = "pubDate";
	/**
	 * GUID tag name.
	 */
	private static final String GUID_TAG = "guid";
	/**
	 * Date reader.
	 */
	private final SimpleDateFormat RSS_DATE_FORMATTER = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z");
	/**
	 * Date formatter.
	 */
	private final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MMMM dd yyyy");

	/**
	 * Default constructor.
	 */
	public RSSFeedDocumentTransformer() {
	}
	/**
	 * Transforms {@link Document} into {@link RSSFeed}.
	 * @return the RSS Feed.
	 */
	public RSSFeed transform(Document rawData) {
		RSSFeed feed = new RSSFeed();
		// get feed details in the future when needed
		// get each item
		feed.addAllItems(getItems(rawData));
		return feed;
	}
	/**
	 * Get the items.
	 * @param doc Source of items.
	 * @return the rss items.
	 */
	private List<RSSItem> getItems(Document doc) {
		List<RSSItem> items = new ArrayList<RSSItem>();
		NodeList nodes = doc.getElementsByTagName(ITEM_TAG);
		if (nodes != null && nodes.getLength() > 0) {
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				RSSItem item = new RSSItem();
				item.setTitle(getTitleFromItem(element));
				item.setLink(getLinkFromItem(element));
				item.setComments(getCommentsFromItem(element));
				item.setCommentRss(getCommentRssFromItem(element));
				item.setDescription(getDescriptionRssFromItem(element));
				item.setCreator(getAuthorRssFromItem(element));
				item.setGuid(getGuidRssFromItem(element));
				// published date
				String publishedDate = getPublishedDateRssFromItem(element);
				Date date = getDate(publishedDate);
				if (date != null) {
					publishedDate = getFormattedDate(date);
				}
				item.setPublishedDate(publishedDate);
				item.setPublishedDateAsDateObject(date);
				items.add(item);
			}
		}
		return items;
	}
	/**
	 * Gets the title of the Item.
	 * @param element
	 * @return
	 */
	private String getTitleFromItem(Element element) {
		return getValueOfElement(element, TITLE_TAG);
	}
	/**
	 * Gets the link of the Item.
	 * @param element
	 * @return
	 */
	private String getLinkFromItem(Element element) {
		return getValueOfElement(element, LINK_TAG);
	}
	/**
	 * Gets the comment RSS of the Item.
	 * @param element
	 * @return
	 */
	private String getCommentRssFromItem(Element element) {
		return getValueOfElement(element, COMMENTRSS_TAG);
	}
	/**
	 * Gets the comments of the Item.
	 * @param element
	 * @return
	 */
	private String getCommentsFromItem(Element element) {
		return getValueOfElement(element, COMMENT_TAG);
	}
	/**
	 * Gets the description of the Item.
	 * @param element
	 * @return
	 */
	private String getDescriptionRssFromItem(Element element) {
		return getValueOfElement(element, DESCRIPTION_TAG);
	}
	/**
	 * Gets the author of the Item.
	 * @param element
	 * @return
	 */
	private String getAuthorRssFromItem(Element element) {
		return getValueOfElement(element, AUTHOR_TAG);
	}
	/**
	 * Gets the published date of the Item.
	 * @param element
	 * @return
	 */
	private String getPublishedDateRssFromItem(Element element) {
		return getValueOfElement(element, PUBLISHED_DATE_TAG);
	}
	/**
	 * Gets the GUID of the Item.
	 * @param element
	 * @return
	 */
	private String getGuidRssFromItem(Element element) {
		return getValueOfElement(element, GUID_TAG);
	}
	/**
	 * Get date object.
	 * @param date String date
	 * @return date object
	 */
	private Date getDate(String date) {
		try {
			return RSS_DATE_FORMATTER.parse(date);
		} catch (ParseException e) {
			// it's ok
		}
		return null;
	}
	/**
	 * Get formatted date.
	 * @param date The date.
	 * @return formatted date
	 */
	public String getFormattedDate(Date date) {
		return DATE_FORMATTER.format(date);
	}
	/**
	 * Get value of element.
	 * @param element The element.
	 * @param elementName Name of element.
	 * @return the value of element.
	 */
	private String getValueOfElement(Element element, String elementName) {
		Element e = getFirstElement(element, elementName);
		if (e != null) {
			Node node = e.getFirstChild();
			return node != null ? node.getNodeValue() : null;
		}
		return null;
	}
	/**
	 * Gets the first element from a list of elements.
	 * @param element The element
	 * @param elementName The name of the element to look for.
	 * @return The element.
	 */
	private Element getFirstElement(Element element, String elementName) {
		NodeList nodes = element.getElementsByTagName(elementName);
		if (nodes != null && nodes.getLength() > 0) {
			return (Element) nodes.item(0);
		}
		return null;
	}
}
