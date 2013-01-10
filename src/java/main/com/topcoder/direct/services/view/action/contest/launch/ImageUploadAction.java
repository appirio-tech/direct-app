/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.struts2.ServletActionContext;

/**
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the actions
 * (different from Struts 1) are created for every request.
 * </p>
 *
 * @author TCASSEMBLER
 * @version 1.1
 */
public class ImageUploadAction extends ContestAction {
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
    private File upload;

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
    private String uploadFileName;

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
     * <p>A <code>String</code> providing the text of error message to be displayed to user in case uploaded file
     * exceeds the pre-defined limit.</p>
     * 
     * @since 1.1
     */
    private String fileSizeLimitExceededMessage;

    /**
     * Default constructor, creates new instance.
     */
    public ImageUploadAction() {
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
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String CKEditorFuncNum = request.getParameter("CKEditorFuncNum"); 
    	
    	String webRoot = request.getSession().getServletContext().getRealPath(""); 
    	
    	String exception = "";

    	try {
            String savePath = webRoot + "/upload/";
            File uploadFilePath = new File(savePath);
            if (uploadFilePath.exists() == false) {
                uploadFilePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(new File(savePath + uploadFileName));
            FileInputStream fis = new FileInputStream(upload);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            fis.close();
        } catch (Exception e) {
        	exception = e.toString();
        }
    	
    	PrintWriter printWriter = new PrintWriter(new FileWriter(webRoot + "/WEB-INF/image-upload.jsp"));
    	printWriter.write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("  
                + CKEditorFuncNum  
                + ", '"  
                + "/direct/upload/" + uploadFileName  
                + "' , '"  
                + ""  
                + "');</script>");
    	printWriter.flush();
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param document uploaded z
     * @return the result map
     */
    private Map<String, Object> getFileResult(long fileId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("documentId", fileId);
        return result;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
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

