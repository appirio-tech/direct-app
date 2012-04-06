/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.analytics.view.rss.RSSReader;

/**
 * <p>A <code>Struts 2</code> action used for handling requests for viewing home page.</p>
 *
 * @author flexme, pinoydream
 * @version 1.0
 */
public class IndexAction extends ActionSupport {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3766186825305662710L;
    /**
     * RSS reader.
     */
    private RSSReader rssReader;
    /**
     * RSS Feed url.
     */
    private String rssFeedUrl;

    /**
     * Construct a new <code>IndexAction</code> instance.
     */
    public IndexAction() {
    }

    /**
     * Handles the incoming request.
     *
     * @return Action.SUCCESS
     */
    public String execute() {
        // retrieve RSS for viewing in home page
        ServletActionContext.getRequest().setAttribute("rssFeed", rssReader.read(rssFeedUrl));
        return SUCCESS;
    }

    /**
     * Sets the value for rssReader.
     * @param rssReader the rssReader to set
     */
    public void setRssReader(RSSReader rssReader) {
        this.rssReader = rssReader;
    }

    /**
     * Sets the value for rssFeedUrl.
     * @param rssFeedUrl the rssFeedUrl to set
     */
    public void setRssFeedUrl(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }
}
