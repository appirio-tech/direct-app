/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import org.apache.commons.io.IOUtils;

import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;
import com.topcoder.util.errorhandling.ExceptionUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * This action permits uploading and attaching a document to a contest. It uses the <code>FileUploadInterceptor</code>
 * : it will fill the proper fields in the UploadedDocument object required to upload and attach the document to the
 * contest. It will persist the related UploadedDocument.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 * &lt;bean id=&quot;fileUploadAttachContestFileAction&quot;
 *     class=&quot;com.topcoder.service.actions.FileUploadAttachContestFileAction&quot;&gt;
 *     &lt;property name=&quot;contestServiceFacade&quot; ref=&quot;contestServiceFacade&quot;&gt;&lt;/property&gt;
 *     &lt;property name=&quot;mimeTypeRetriever&quot; ref=&quot;mimeTypeRetriever&quot;&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 *
 * <pre>
 * &lt;action name=&quot;fileUploadAttachContestFileAction&quot; class=&quot;fileUploadAttachContestFileAction&quot;&gt;
 *     &lt;interceptor-ref name=&quot;demoTCSStack&quot; /&gt;
 *     &lt;result name=&quot;input&quot;&gt;/fileUpload.jsp&lt;/result&gt;
 *     &lt;result name=&quot;success&quot;&gt;/success.jsp?type=fileUpload&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Contest Edit and Upload Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #fileSizeLimitExceededMessage} property.</li>
 *     <li>Added {@link #execute()} method.</li>
 *     <li>Added {@link #checkRequestError()} method.</li>
 *     <li>Updated {@link #executeAction()} method to remove the <code>result</code> from the data model.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the actions
 * (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, isv
 * @version 1.1
 */
public class DocumentUploadAction extends ContestAction {
    /**
     * <p>
     * Represents the uploaded file (the link to the temporary file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor (at the end of action logic it will be removed by the interceptor).
     * It will not be null when the logic is performed. It's changed by the setter and returned by the getter.
     * </p>
     */
    private File document;

    /**
     * <p>
     * Represents the name of uploaded file (the name of client's file, not the temporary file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor. It's changed by the setter and returned by the getter. It will not
     * be null or empty when the logic is performed.
     * </p>
     */
    private String documentFileName;

    /**
     * <p>
     * Represents the content type of uploaded file (the name of client's file, not the temporary file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor. It's changed by the setter and returned by the getter. It will not
     * be null or empty when the logic is performed.
     * </p>
     */
    private String documentContentType;

    /**
     * <p>
     * Represents the ID of the contest. It's used to attach the document to the contest.
     * </p>
     *
     * <p>
     * It must be greater than 0. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * Represents the description of the contest file.
     * </p>
     *
     * <p>
     * It can't be null or empty and has a max of 4096 chars. It's changed by the setter and returned by the getter.
     * </p>
     */
    private String contestFileDescription;

    /**
     * <p>
     * Represents the ID of the document type.
     * </p>
     *
     * <p>
     * It must be greater than or equal to 0 and less than 25. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long documentTypeId;

    /**
     * <p>
     * Represents the MIME type retriever used to retrieve the MIME types.
     * </p>
     *
     * <p>
     * It's injected by the setter and it will be not null when the logic of the action is performed.
     * </p>
     */
    private MimeTypeRetriever mimeTypeRetriever;

    /**
     * <p>
     * Indicates if it is studio document or software document. The default value is true.
     * </p>
     */
    private boolean studio = true;

    /**
     * <p>A <code>String</code> providing the text of error message to be displayed to user in case uploaded file
     * exceeds the pre-defined limit.</p>
     * 
     * @since 1.1
     */
    private String fileSizeLimitExceededMessage;

    /**
     * Default constructor, creates new instance.
     */
    public DocumentUploadAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @return a <code>String</code> referencing the next view to be displayed to user.
     * @throws Exception if an unexpected error occurs.
     * @since 1.1
     */
    @InputConfig(methodName = "checkRequestError")
    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    /**
     * Executes the action. Saves the document and attaches it to the contest.
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> or <code>mimeTypeRetriever</code> haven't
     *             been injected
     */
    public void executeAction() throws Exception {
        executeActionSoftware();
    }

    /**
     * <p>Handles the case when the request for uploading the document has failed, most likely due to uploaded file to
     * exceed the maximum file size limit.</p>
     * 
     * @return a <code>String</code> referencing the next view.  
     * @since 1.1
     */
    public String checkRequestError() {
        StringBuilder b = new StringBuilder();
        MultiPartRequestWrapper servletRequest = (MultiPartRequestWrapper) DirectUtils.getServletRequest();
        if (servletRequest.hasErrors()) {
            Collection<String> errors = servletRequest.getErrors();
            for (String error : errors) {
                error = error.toLowerCase();
                if (error.startsWith("the request was rejected because its size") 
                    && error.contains("exceeds the configured maximum")) {
                        setResult(new RuntimeException(getFileSizeLimitExceededMessage()));
                        return ERROR;
                } else {
                    b.append("\n").append(error);
                }
            }
        }
        if (b.length() == 0) {
            setResult(new RuntimeException("Failed to upload the document"));
        } else {
            setResult(new RuntimeException("Failed to upload the document due to following errors:" + b));
        }
        return ERROR;
    }

    /**
     * Executes the action. Saves the uploaded file to the file store.
     *
     * @throws Exception if some error occurs during method execution
     */
    private void executeActionSoftware() throws Exception {
        //Gets session file store
        SessionFileStore fileStore = new SessionFileStore(DirectUtils.getServletRequest().getSession(true));

        // populate the document information
        CompUploadedFile uploadedFile = new CompUploadedFile();
        uploadedFile.setUploadedFileDesc(contestFileDescription);
        uploadedFile.setUploadedFileName(documentFileName);
        //0 requirement document or 24 supporting document
        uploadedFile.setUploadedFileType(documentTypeId);

        // get the file contents as byte array and load them to the uploadedDocument
        InputStream stream = null;
        try {
            stream = new FileInputStream(document);
            uploadedFile.setFileData(IOUtils.toByteArray(stream));
        } finally {
            IOUtils.closeQuietly(stream);
        }

        setResult(getFileResult(fileStore.addFile(uploadedFile)));
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param document uploaded document
     * @return the result map
     */
    private Map<String, Object> getFileResult(long fileId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("documentId", fileId);
        return result;
    }

    /**
     * <p>
     * Gets the studio.
     * </p>
     *
     * @return the studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * <p>
     * Sets the studio value.
     * </p>
     *
     * @param studio the studio to set
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Getter for the contest ID.
     *
     * @return the contest ID
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for the contest ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param contestId the contest ID
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public File getDocument() {
        return document;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    /**
     * Getter for the contest file description.
     *
     * @return the contest file description
     */
    public String getContestFileDescription() {
        return contestFileDescription;
    }

    /**
     * Setter for the contest file description. Struts 2 validation is used to check that the argument is between 1
     * and 4096 characters.
     *
     * @param contestFileDescription the contest file description
     */
    public void setContestFileDescription(String contestFileDescription) {
        this.contestFileDescription = contestFileDescription;
    }

    /**
     * Getter for the document type ID.
     *
     * @return the document type ID
     */
    public long getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * Setter for the document type ID. Struts 2 validation is used to check that the argument is greater than or
     * equal to 0 and less than 25.
     *
     * @param documentTypeId the document type ID
     */
    public void setDocumentTypeId(long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * Getter for the MIME type retriever.
     *
     * @return the MIME type retriever
     */
    public MimeTypeRetriever getMimeTypeRetriever() {
        return mimeTypeRetriever;
    }

    /**
     * Setter for the MIME type retriever.
     *
     * @param mimeTypeRetriever the MIME type retriever
     *
     * @throws IllegalArgumentException if the argument is null
     */
    public void setMimeTypeRetriever(MimeTypeRetriever mimeTypeRetriever) {
        ExceptionUtils.checkNull(mimeTypeRetriever, null, null, "mimeTypeRetriever cannot be null");
        this.mimeTypeRetriever = mimeTypeRetriever;
    }

    /**
     * <p>Gets the text of error message to be displayed to user in case uploaded file exceeds the pre-defined
     * limit.</p>
     *
     * @return a <code>String</code> providing the text of error message to be displayed to user in case uploaded file
     *         exceeds the pre-defined limit.
     * @since 1.1
     */
    public String getFileSizeLimitExceededMessage() {
        return this.fileSizeLimitExceededMessage;
    }

    /**
     * <p>Sets the text of error message to be displayed to user in case uploaded file exceeds the pre-defined
     * limit.</p>
     *
     * @param fileSizeLimitExceededMessage a <code>String</code> providing the text of error message to be displayed to
     *                                     user in case uploaded file exceeds the pre-defined limit.
     * @since 1.1
     */
    public void setFileSizeLimitExceededMessage(String fileSizeLimitExceededMessage) {
        this.fileSizeLimitExceededMessage = fileSizeLimitExceededMessage;
    }
}
