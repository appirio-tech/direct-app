/*
 * Copyright (C) 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> providing the details Term.</p>
 *
 * @author TCSCODER
 * @version 1.0 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES)
 */
public class TermOfUse implements Serializable {
    /**
     * Term Id
     */
    private long id;

    /**
     * Term title
     */
    private String title;

    /**
     * Term URL
     */
    private String url;

    /**
     * Getter for {@link #id}
      * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for {@link #id}
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for {@link #title}
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for {@lnik #title}
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for {@link #url}
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for {@link #url}
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
