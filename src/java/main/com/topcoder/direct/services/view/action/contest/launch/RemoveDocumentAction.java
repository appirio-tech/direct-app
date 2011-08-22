/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;

/**
 * <p>
 * Removes document.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note - Adds current user into the call
 * </p>
 * 
 * <p>
 * Version 1.2 - Cleanup obsolete Studio code:
 *   <ol>
 *     <li>Removed {@link #executeActionStudio()} method.
 *     <li>Removed {@link #executeActionSoftware()} method and moved its logic into {@link #executeAction()}.
 *   </ol> 
 * </p>
 *
 * @author BeBetter
 * @version 1.1
 */
public class RemoveDocumentAction extends ContestAction {

    /**
     * <p>
     * document id.
     * </p>
     */
    private long documentId;

    /**
     * <p>
     * Executes the action to remove the document.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        //Gets session file store
        SessionFileStore fileStore = new SessionFileStore(DirectUtils.getServletRequest().getSession(true));

        fileStore.removeFile(documentId);

        setResult(getDocumentResult(documentId));
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param document uploaded document
     * @return the result map
     */
    private Map<String, Object> getDocumentResult(long documentId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("documentId", documentId);
        return result;
    }

    /**
     * Represents getter for document id property. 
     * 
     * @return the document id.
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * Represents setter for document id.
     * 
     * @param documentId the document id.
     */
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }
}
