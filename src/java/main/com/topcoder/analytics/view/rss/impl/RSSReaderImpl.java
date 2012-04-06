/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.rss.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.topcoder.analytics.view.rss.RSSFeed;
import com.topcoder.analytics.view.rss.RSSFeedTransformer;
import com.topcoder.analytics.view.rss.RSSReader;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * Concrete implementation of RSSReader to retrieve RSS Feeds.
 * </p>
 * <p>
 * Changed notes in 1.1 Invalid character encoding has been fix<br/>
 * BUGR_6377 (TC Analytics Landing Page Assembly Enhancement)
 * </p>
 * @author pinoydream, TMALBONPH
 * @version 1.1
 */
public class RSSReaderImpl implements RSSReader {
    /**
     * Represents the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RSSReaderImpl.class);
    /**
     * RSS Feed transformer.
     */
    private RSSFeedTransformer<Document> rssFeedTransformer;

    /**
     * Reads RSS from a specified URL.
     *
     * @param url the News feed URL
     * @return the RSSFeed instance, otherwise <code>null</code> on error.
     */
    public RSSFeed read(String url) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            StringBuilder b = new StringBuilder(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                inputLine += "\n";
                b.append(inputLine);
            }
            in.close();
            return rssFeedTransformer.transform(builder.parse(new ByteArrayInputStream(b.toString().getBytes("UTF-8"))));
        } catch (ParserConfigurationException e) {
            LOGGER.error("An error occurred while trying to parse RSS feed", e);
        } catch (MalformedURLException e) {
            LOGGER.error("An error occurred while trying to use URL (" + url + ")", e);
        } catch (SAXException e) {
            LOGGER.error("An error occurred while trying to parse RSS feed", e);
        } catch (IOException e) {
            LOGGER.error("An error occurred while trying to parse RSS feed", e);
        }
        return null;
    }
    /**
     * Sets the value for rssFeedTransformer.
     * @param rssFeedTransformer the rssFeedTransformer to set
     */
    public void setRssFeedTransformer(RSSFeedTransformer<Document> rssFeedTransformer) {
        this.rssFeedTransformer = rssFeedTransformer;
    }
}
