/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action permits downloading of a document file from the data provided in the request. The class is
 * annotated with the annotation Result(name = ActionSupport.SUCCESS, type = "stream") to permit the Struts 2
 * framework to return a stream result.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 * <pre>
 * &lt;bean id="getDocumentFileContestAction"
 *     class="com.topcoder.service.actions.GetDocumentFileContestAction"&gt;
 *     &lt;property name="contestServiceFacade" ref="contestServiceFacade"&gt;&lt;/property&gt;
 *     &lt;property name="mimeTypeRetriever" ref="mimeTypeRetriever"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="getDocumentFileContestAction" class="getDocumentFileContestAction"&gt;
 *     &lt;interceptor-ref name="demoTCSStack" /&gt;
 *     &lt;result name="input"&gt;/getFile.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp?type=getFile&lt;/result&gt;
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
@Result(name = ActionSupport.SUCCESS, type = "stream")
public class GetDocumentFileContestAction extends GetDocumentsContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.getDocumentFileContestAction.";

    /**
     * <p>
     * Represents the document ID used to retrieve the file of the document.
     * </p>
     *
     * <p>
     * It will be set in the setter and returned by the getter. It will be greater than 0.
     * </p>
     */
    private long documentId;

    /**
     * <p>
     * Represents the input stream of the document file.
     * </p>
     *
     * <p>
     * It will be set in the execute method so it will be not null. It's used by the stream result.
     * The getters and setters, however, are always necessary for the Struts 2 framework.
     * </p>
     */
    private FileInputStream inputStream;

    /**
     * <p>
     * Represents the MIME type the document file.
     * </p>
     *
     * <p>
     * It will be set in the execute method so it will be not null.
     * It's used by the stream result. The getters and setters, however, are always necessary for the Struts 2
     * framework.
     * </p>
     */
    private String contentType;

    /**
     * <p>
     * Represents the content length of the document file.
     * </p>
     *
     * <p>
     * It will be set in the execute method so it will be not null.
     * It's used by the stream result. The getters and setters, however, are always necessary for the
     * Struts 2 framework.
     * </p>
     */
    private long contentLength;

    /**
     * <p>
     * Represents the content disposition of document file (an HTTP header that contains name of downloaded file).
     * </p>
     *
     * <p>
     * It will be set in the execute method so it will be not null. It's used by the stream result. The
     * getters and setters, however, are always necessary for the Struts 2 framework.
     * </p>
     */
    private String contentDisposition;

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
    public GetDocumentFileContestAction() {
    }

    /**
     * Finds the competition document that matches given document ID and sets the related fields (input stream,
     * content length, etc.) for this instance.
     *
     * @param competitionDocuments the list of competition documents to use for the operation
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>mimeTypeRetriever</code> hasn't been injected
     * @throws IllegalArgumentException if any non null element of <code>competitionDocuments</code> is not an
     *     <code>UploadedDocument</code> instance
     */
    @Override
    protected void performLogic(List<?> competitionDocuments) throws Exception {
        ActionHelper.checkInjectedFieldNull(mimeTypeRetriever, "mimeTypeRetriever");

        if (competitionDocuments == null || competitionDocuments.size() == 0) {
            // do nothing
            return;
        }

        // search for the document which matches the document ID
        UploadedDocument doc = null;
        for (int i = 0; i < competitionDocuments.size(); ++i) {
            Object obj = competitionDocuments.get(i);
            if (obj == null) {
                // just skip over null elements
                continue;
            }
            if (!(obj instanceof UploadedDocument)) {
                throw new IllegalArgumentException(
                    "The competition document elements must be of type UploadedDocument");
            }
            UploadedDocument candidateDoc = (UploadedDocument) competitionDocuments.get(i);

            // see if this document is the one we want
            if (candidateDoc.getDocumentId() == documentId) {
                doc = candidateDoc;
                break;
            }
        }

        if (doc == null) {
            // doc wasn't found, do nothing
            return;
        }

        // process file based on path
        String fileName = doc.getPath() + System.getProperty("file.separator") + doc.getFileName();
        contentDisposition = "attachment;filename=" + doc.getFileName();

        File file = new File(fileName);
        inputStream = new FileInputStream(file);
        contentLength = file.length();
        contentType = mimeTypeRetriever.getMimeTypeFromFileName(fileName);
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
     * Getter for the document ID.
     *
     * @return the document ID
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * Setter for the document ID. Struts 2 validation is used to check that the argument is greater 0.
     *
     * @param documentId the document ID
     */
    @FieldExpressionValidator(
        key = KEY_PREFIX + "documentIdGreaterThanZero", fieldName = "documentId",
        expression = "documentId > 0", message = ActionHelper.GREATER_THAN_ZERO)
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    /**
     * Getter for the input stream.
     *
     * @return the input stream
     */
    public FileInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Setter for the input stream.
     *
     * @param inputStream the input stream
     */
    public void setInputStream(FileInputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Getter for the content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Setter for the content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Getter for the content length.
     *
     * @return the content length
     */
    public long getContentLength() {
        return contentLength;
    }

    /**
     * Setter for the content length.
     *
     * @param contentLength the content length
     */
    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * Getter for the content disposition.
     *
     * @return the content disposition
     */
    public String getContentDisposition() {
        return contentDisposition;
    }

    /**
     * Setter for the content disposition.
     *
     * @param contentDisposition the content disposition
     */
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }
}
