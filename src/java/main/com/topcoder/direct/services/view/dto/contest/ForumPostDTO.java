/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.UserDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A <code>DTO</code> providing the details for single forum post.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public class ForumPostDTO implements Serializable {

    /**
     * <p>A <code>UserDTO</code> providing the details on forum post author.</p>
     */
    private UserDTO author;

    /**
     * <p>A <code>Date</code> providing the time when the forum post was posted.</p>
     */
    private Date timestamp;

    /**
     * <p>A <code>String</code> providing the URL for forum post.</p>
     */
    private String url;

    /**
     * <p>Constructs new <code>ForumPostDTO</code> instance. This implementation does nothing.</p>
     */
    public ForumPostDTO() {
    }

    /**
     * <p>Gets the time when the forum post was posted.</p>
     *
     * @return a <code>Date</code> providing the time when the forum post was posted.
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    /**
     * <p>Sets the time when the forum post was posted.</p>
     *
     * @param timestamp a <code>Date</code> providing the time when the forum post was posted.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * <p>Gets the details on forum post author.</p>
     *
     * @return a <code>UserDTO</code> providing the details on forum post author.
     */
    public UserDTO getAuthor() {
        return this.author;
    }

    /**
     * <p>Sets the details on forum post author.</p>
     *
     * @param author a <code>UserDTO</code> providing the details on forum post author.
     */
    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    /**
     * <p>Gets the URL for forum post.</p>
     *
     * @return a <code>String</code> providing the URL for forum post.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * <p>Sets the URL for forum post.</p>
     *
     * @param url a <code>String</code> providing the URL for forum post.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
