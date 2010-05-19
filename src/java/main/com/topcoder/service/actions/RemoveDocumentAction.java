/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Remove document.
 * </p>
 *
 * @author BeBetter
 */
public class RemoveDocumentAction extends ContestAction {
    private long contestId;

    private long documentId;

    @Override
    protected void executeAction() throws Exception {
        if (documentId <= 0) {
            return;
        }

        if (contestId < 0) {
            getContestServiceFacade().removeDocument(null, documentId);
        } else {
            UploadedDocument document = new UploadedDocument();
            document.setContestId(contestId);
            document.setDocumentId(documentId);
            getContestServiceFacade().removeDocumentFromContest(null, document);
        }

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


    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }
}
