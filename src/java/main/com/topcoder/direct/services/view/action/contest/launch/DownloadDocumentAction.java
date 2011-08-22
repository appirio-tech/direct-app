/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;

/**
 * <p>
 * This action will download the documents attached during contest creation or update.
 * </p>
 * 
 * @author morehappiness
 * @version 1.0
 */
public class DownloadDocumentAction extends ContestAction {
    /**
     * The document Url which is related to the uploaded root directory.
     */
    private String documentUrl;
    /**
     * The document id which is related to the uploaded root directory.
     */
    private String documentId;
    /**
     * The name of the document.
     */
    private String documentName;
    /**
     * The uploaded root directory of the file system.
     */
    private String uploadedFilesRootDir;
    
    /**
     * Gets the documentId field value.
     *
     * @return the documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the documentId field value.
     *
     * @param documentId the documentId to set
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the uploadedFilesRootDir field value.
     *
     * @return the uploadedFilesRootDir
     */
    public String getUploadedFilesRootDir() {
        return uploadedFilesRootDir;
    }

    /**
     * Sets the uploadedFilesRootDir field value.
     *
     * @param uploadedFilesRootDir the uploadedFilesRootDir to set
     */
    public void setUploadedFilesRootDir(String uploadedFilesRootDir) {
        this.uploadedFilesRootDir = uploadedFilesRootDir;
    }

    /**
     * Gets the documentName field value.
     * 
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the documentName field value.
     * 
     * @param documentName
     *            the documentName to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Updates the document url to the absolute path in the file system.
     */
    @Override
    protected void executeAction() throws Exception {
        if (documentId == null) {
            return;
        }
        documentUrl=DataProvider.getDocumentUrl(documentId);
        documentUrl = getRootDir() + documentUrl;
        this.documentName = this.documentUrl.substring(this.documentUrl.lastIndexOf("/")+ 1 );
    }
   
    /**
     * Gets the uploaded root directory.
     * 
     * @return the uploaded root directory
     */
    private String getRootDir() {
        String rootDir = uploadedFilesRootDir;
        if (!rootDir.endsWith("/")) {
            rootDir += "/";
        }
        
        return rootDir;
    }

    /**
     * Gets the input stream from the document.
     *
     * @return the input stream from the document
     * @throws FileNotFoundException if the document can not be found
     */
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(documentUrl);
    }

    /**
     * Gets the contents disposition for the document.
     * @return the contents disposition for the document
     */
    public String getContentDisposition() {
        String dis = "attachment;filename=" + documentName;

        return dis.replaceAll(" ", "%20");
    }
}
