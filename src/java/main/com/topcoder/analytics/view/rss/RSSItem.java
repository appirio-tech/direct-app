/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Simple POJO to hold RSS Item details.</p>
 *
 * @author pinoydream
 * @version 1.0
 */
public class RSSItem implements Serializable {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2526819328562822998L;
	/**
	 * Title.
	 */
	private String title;
	/**
	 * Link.
	 */
	private String link;
	/**
	 * Comments.
	 */
	private String comments;
	/**
	 * Published date.
	 */
	private String publishedDate;
	/**
	 * Published date as Date object.
	 */
	private Date publishedDateAsDateObject;
	/**
	 * Creator.
	 */
	private String creator;
	/**
	 * GUID.
	 */
	private String guid;
	/**
	 * Description.
	 */
	private String description;
	/**
	 * Comment RSS.
	 */
	private String commentRss;
	/**
	 * Default constructor.
	 */
	public RSSItem() {
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
	 * Gets the value of comments.
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * Sets the value for comments.
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * Gets the value of publishedDate.
	 * @return the publishedDate
	 */
	public String getPublishedDate() {
		return publishedDate;
	}
	/**
	 * Sets the value for publishedDate.
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	/**
	 * Gets the value of creator.
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * Sets the value for creator.
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * Gets the value of guid.
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * Sets the value for guid.
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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
	 * Gets the value of commentRss.
	 * @return the commentRss
	 */
	public String getCommentRss() {
		return commentRss;
	}
	/**
	 * Sets the value for commentRss.
	 * @param commentRss the commentRss to set
	 */
	public void setCommentRss(String commentRss) {
		this.commentRss = commentRss;
	}
	/**
	 * Gets the value of publishedDateAsDateObject.
	 * @return the publishedDateAsDateObject
	 */
	public Date getPublishedDateAsDateObject() {
		return publishedDateAsDateObject;
	}
	/**
	 * Sets the value for publishedDateAsDateObject.
	 * @param publishedDateAsDateObject the publishedDateAsDateObject to set
	 */
	public void setPublishedDateAsDateObject(Date publishedDateAsDateObject) {
		this.publishedDateAsDateObject = publishedDateAsDateObject;
	}
}
