/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>A DTO for a single entry in TC Direct project forum.</p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0 (opCoder Cockpit Project Overview R2 Project Forum Backend Assembly)
 */
public class ProjectForumStatusDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID of the forum thread.</p>
     */
    private long threadID;

    /**
     * <p>A <code>long</code> providing the number of threads in forum.</p>
     */
    private long threadNumber;

    /**
     * <p>A <code>long</code> providing the number of messages in forum.</p>
     */
    private long messageNumber;

    /**
     * <p>A <code>String</code> providing the handle for author of last post in forum.</p>
     */
    private String lastPostHandle;

    /**
     * <p>A <code>String</code> providing the time of last post.</p>
     */
    private String lastPostTime;

    /**
     * <p>A <code>String</code> providing the title of the forum.</p>
     */
    private String threadTitle;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the forum has unread/unanswered messages.</p>
     */
    private boolean isRead;

    /**
     * <p>A <code>String</code> providing the summary for the forum.</p>
     */
    private String summary;

    /**
     * <p>Constructs new <code>ProjectForumStatusDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectForumStatusDTO() {
    }

    /**
     * <p>Gets the summary for the forum.</p>
     *
     * @return a <code>String</code> providing the summary for the forum.
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * <p>Sets the summary for the forum.</p>
     *
     * @param summary a <code>String</code> providing the summary for the forum.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * <p>Gets the flag indicating whether the forum has unread/unanswered messages.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the forum has unread/unanswered messages.
     */
    public boolean getIsRead() {
        return this.isRead;
    }

    /**
     * <p>Sets the flag indicating whether the forum has unread/unanswered messages.</p>
     *
     * @param isRead a <code>boolean</code> providing the flag indicating whether the forum has unread/unanswered
     *               messages.
     */
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * <p>Gets the title of the forum.</p>
     *
     * @return a <code>String</code> providing the title of the forum.
     */
    public String getThreadTitle() {
        return this.threadTitle;
    }

    /**
     * <p>Sets the title of the forum.</p>
     *
     * @param threadTitle a <code>String</code> providing the title of the forum.
     */
    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    /**
     * <p>Gets the time of last post.</p>
     *
     * @return a <code>String</code> providing the time of last post.
     */
    public String getLastPostTime() {
        return this.lastPostTime;
    }

    /**
     * <p>Sets the time of last post.</p>
     *
     * @param lastPostTime a <code>String</code> providing the time of last post.
     */
    public void setLastPostTime(String lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    /**
     * <p>Gets the handle for author of last post in forum.</p>
     *
     * @return a <code>String</code> providing the handle for author of last post in forum.
     */
    public String getLastPostHandle() {
        return this.lastPostHandle;
    }

    /**
     * <p>Sets the handle for author of last post in forum.</p>
     *
     * @param lastPostHandle a <code>String</code> providing the handle for author of last post in forum.
     */
    public void setLastPostHandle(String lastPostHandle) {
        this.lastPostHandle = lastPostHandle;
    }

    /**
     * <p>Gets the number of messages in forum.</p>
     *
     * @return a <code>long</code> providing the number of messages in forum.
     */
    public long getMessageNumber() {
        return this.messageNumber;
    }

    /**
     * <p>Sets the number of messages in forum.</p>
     *
     * @param messageNumber a <code>long</code> providing the number of messages in forum.
     */
    public void setMessageNumber(long messageNumber) {
        this.messageNumber = messageNumber;
    }

    /**
     * <p>Gets the number of threads in forum.</p>
     *
     * @return a <code>long</code> providing the number of threads in forum.
     */
    public long getThreadNumber() {
        return this.threadNumber;
    }

    /**
     * <p>Sets the number of threads in forum.</p>
     *
     * @param threadNumber a <code>long</code> providing the number of threads in forum.
     */
    public void setThreadNumber(long threadNumber) {
        this.threadNumber = threadNumber;
    }

    /**
     * <p>Gets the ID of the forum thread.</p>
     *
     * @return a <code>long</code> providing the ID of the forum thread.
     */
    public long getThreadID() {
        return this.threadID;
    }

    /**
     * <p>Sets the ID of the forum thread.</p>
     *
     * @param threadID a <code>long</code> providing the ID of the forum thread.
     */
    public void setThreadID(long threadID) {
        this.threadID = threadID;
    }
}
