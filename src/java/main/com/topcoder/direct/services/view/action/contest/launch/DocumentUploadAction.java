/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.topcoder.security.TCSubject;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.util.errorhandling.ExceptionUtils;

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
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the actions
 * (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
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
    private int documentTypeId;

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
     * Default constructor, creates new instance.
     */
    public DocumentUploadAction() {
    }

    /**
     * Executes the action. Saves the document and attaches it to the contest.
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> or <code>mimeTypeRetriever</code> haven't
     *             been injected
     */
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
        ActionHelper.checkInjectedFieldNull(mimeTypeRetriever, "mimeTypeRetriever");

        // populate the document information
        UploadedDocument uploadedDocument = new UploadedDocument();
        uploadedDocument.setDescription(contestFileDescription);
        uploadedDocument.setFileName(documentFileName);
        uploadedDocument.setContestId(contestId);
        uploadedDocument.setDocumentTypeId(documentTypeId);
        uploadedDocument.setMimeTypeId(mimeTypeRetriever.getMimeTypeIdFromFileName(documentFileName));

        // get the file contents as byte array and load them to the uploadedDocument
        InputStream stream = null;
        try {
            stream = new FileInputStream(document);
            uploadedDocument.setFile(IOUtils.toByteArray(stream));
        } finally {
            IOUtils.closeQuietly(stream);
        }

        // get the TCSubject from session
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        // persist the document
        if (contestId < 0) {
            uploadedDocument = getContestServiceFacade().uploadDocument(tcSubject, uploadedDocument);
        } else {
            uploadedDocument = getContestServiceFacade().uploadDocumentForContest(tcSubject, uploadedDocument);
        }

        setResult(getDocumentResult(uploadedDocument));
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param document uploaded document
     * @return the result map
     */
    private Map<String, Object> getDocumentResult(UploadedDocument document) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("documentId", document.getDocumentId());
        return result;
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
    public int getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * Setter for the document type ID. Struts 2 validation is used to check that the argument is greater than or
     * equal to 0 and less than 25.
     *
     * @param documentTypeId the document type ID
     */
    public void setDocumentTypeId(int documentTypeId) {
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
}
