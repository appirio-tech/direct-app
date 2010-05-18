/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action permits uploading and attaching a document to a contest. It uses the
 * <code>FileUploadInterceptor</code>: it will fill the proper fields in the UploadedDocument object required to
 * upload and attach the document to the contest. It will persist the related UploadedDocument.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 * <pre>
 * &lt;bean id="fileUploadAttachContestFileAction"
 *     class="com.topcoder.service.actions.FileUploadAttachContestFileAction"&gt;
 *     &lt;property name="contestServiceFacade" ref="contestServiceFacade"&gt;&lt;/property&gt;
 *     &lt;property name="mimeTypeRetriever" ref="mimeTypeRetriever"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="fileUploadAttachContestFileAction" class="fileUploadAttachContestFileAction"&gt;
 *     &lt;interceptor-ref name="demoTCSStack" /&gt;
 *     &lt;result name="input"&gt;/fileUpload.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp?type=fileUpload&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this
 * class will change based on the request parameters. It's not required to be thread safe because in Struts 2 the
 * actions (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class FileUploadAttachContestFileAction extends ContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.fileUploadAttachContestFileAction.";

    /**
     * <p>
     * Represents the uploaded file (the link to the temporary file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor (at the end of action logic it will be removed by the
     * interceptor). It will not be null when the logic is performed. It's changed by the setter and
     * returned by the getter.
     * </p>
     */
    private File contestFile;

    /**
     * <p>
     * Represents the name of uploaded file (the name of client's file, not the temporary file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor. It's changed by the setter and returned by the getter. It will
     * not be null or empty when the logic is performed.
     * </p>
     */
    private String contestFileName;

    /**
     * <p>
     * Represents the MIME type of the uploaded file (the MIME type of client's file).
     * </p>
     *
     * <p>
     * It's filled by the file upload interceptor. It's changed by the setter and returned by the getter. It will
     * not be null or empty when the logic is performed.
     * </p>
     *
     */
    private String contestFileContentType;

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
    public FileUploadAttachContestFileAction() {
    }

    /**
     * Executes the action. Saves the document and attaches it to the contest.
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> or <code>mimeTypeRetriever</code>
     *     haven't been injected
     */
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
        ActionHelper.checkInjectedFieldNull(mimeTypeRetriever, "mimeTypeRetriever");

        // populate the document information
        UploadedDocument uploadedDocument = new UploadedDocument();
        uploadedDocument.setDescription(contestFileDescription);
        uploadedDocument.setFileName(contestFileName);
        uploadedDocument.setContestId(contestId);
        uploadedDocument.setDocumentTypeId(documentTypeId);
        uploadedDocument.setMimeTypeId(mimeTypeRetriever.getMimeTypeIdFromMimeType(contestFileContentType));

        // get the file contents as byte array and load them to the uploadedDocument
        InputStream stream = null;
        try {
            stream = new FileInputStream(contestFile);
            uploadedDocument.setFile(IOUtils.toByteArray(stream));
        } finally {
            IOUtils.closeQuietly(stream);
        }

        // persist the document
        getContestServiceFacade().uploadDocumentForContest(null, uploadedDocument);
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
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdGreaterThanZero",
        fieldName = "contestId", expression = "contestId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Getter for the contest file.
     *
     * @return the contest file
     */
    public File getContestFile() {
        return contestFile;
    }

    /**
     * Setter for the contest file.
     *
     * @param contestFile the contest file
     */
    public void setContestFile(File contestFile) {
        this.contestFile = contestFile;
    }

    /**
     * Getter for the contest file name.
     *
     * @return the contest file name
     */
    public String getContestFileName() {
        return contestFileName;
    }

    /**
     * Setter for the contest file name.
     *
     * @param contestFileName the contest file name
     */
    public void setContestFileName(String contestFileName) {
        this.contestFileName = contestFileName;
    }

    /**
     * Getter for the contest file content type.
     *
     * @return the contest file contest type
     */
    public String getContestFileContentType() {
        return contestFileContentType;
    }

    /**
     * Setter for the contest file content type.
     *
     * @param contestFileContentType the contest file content type
     */
    public void setContestFileContentType(String contestFileContentType) {
        this.contestFileContentType = contestFileContentType;
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
    @FieldExpressionValidator(key = KEY_PREFIX + "contestFileDescriptionRange",
        fieldName = "contestFileDescription",
        expression = "contestFileDescription != null && contestFileDescription.trim().length() >= 1 && "
            + "contestFileDescription.trim().length() <= 4096",
        message = "contestFileDescription cannot be null and must be between 1 and 4096 characters")
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
     * Setter for the document type ID. Struts 2 validation is used to check that the argument is greater
     * than or equal to 0 and less than 25.
     *
     * @param documentTypeId the document type ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "documentTypeIdRange",
        fieldName = "documentTypeId",
        expression = "documentTypeId >= 0 && documentTypeId < 25",
        message = "documentTypeId must be >= 0 and < 25")
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
