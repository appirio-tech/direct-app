/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
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
 * @author BeBetter
 * @version 1.0
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

    private Map<Long, CompUploadedFile> getFileMap() {
        synchronized (session) {
            if (session.getAttribute(SESSION_VAR_FILE_MAP) == null) {
                session.setAttribute(SESSION_VAR_FILE_MAP, new HashMap<Long, CompUploadedFile>());
            }
        }

        return (Map<Long, CompUploadedFile>) session.getAttribute(SESSION_VAR_FILE_MAP);
    }
}
