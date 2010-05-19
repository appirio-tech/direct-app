package com.topcoder.direct.services.view.action.contest.launch;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.Document;

public class DownloadDocumentAction extends ContestAction {
    private long documentId;

    private Document document;

    private byte[] content = new byte[] {};

    @Override
    protected void executeAction() throws Exception {
        if (documentId <= 0) {
            return;
        }

        document = getContestManager().getDocument(documentId);
        content = getContestManager().getDocumentContent(documentId);
    }

    public InputStream getInputStream() throws ContestManagementException {
        return new ByteArrayInputStream(content);
    }

    public String getContentDisposition() {
        return "attachment;filename=" + document.getOriginalFileName();
    }

    public Document getDocument() {
        return document;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }
}
