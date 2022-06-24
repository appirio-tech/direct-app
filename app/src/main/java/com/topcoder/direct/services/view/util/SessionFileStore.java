/*
 * Copyright (C) 2009 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.topcoder.catalog.entity.CompUploadedFile;

/**
 * <p>
 * Session file store. since there is no file persistence for software document, we use this for storing documents
 * temporarily. Since we don't expect a lot of user uploading in the same time so this approach should be applicable.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Change method {@link #getFileMap()} to public.</li>
 *   </ol>
 * </p>
 * 
 * @author BeBetter, TCSASSEMBER
 * @version 1.1
 */
public class SessionFileStore {

    private static final String SESSION_VAR_FILE_MAP = "SESSION_VAR_FILE_MAP";

    private final HttpSession session;

    public SessionFileStore(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("session should not be null.");
        }
        this.session = session;
    }

    public long addFile(CompUploadedFile file) {
        long fileTempId = new Date().getTime();
        getFileMap().put(fileTempId, file);
        return fileTempId;
    }

    public void removeFile(long fileTempId) {
        getFileMap().remove(fileTempId);
    }

    public CompUploadedFile getFile(long fileTempId) {
        return getFileMap().get(fileTempId);
    }

    public Map<Long, CompUploadedFile> getFileMap() {
        synchronized (session) {
            if (session.getAttribute(SESSION_VAR_FILE_MAP) == null) {
                session.setAttribute(SESSION_VAR_FILE_MAP, new HashMap<Long, CompUploadedFile>());
            }
        }

        return (Map<Long, CompUploadedFile>) session.getAttribute(SESSION_VAR_FILE_MAP);
    }
}
