/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.io.InputStream;

/**
 * <p>
 * This DTO consists of the submission download information
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is fully mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class MMDownloadSubmissionDTO {
    /**
     * The name of the file
     */
    private String fileName;

    /**
     * The stream containing the submission text.
     */
    private InputStream submission;

    /**
     * Default constructor.
     */
    public MMDownloadSubmissionDTO() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param fileName
     *            the name-sake field to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public InputStream getSubmission() {
        return submission;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submission
     *            the name-sake field to set
     */
    public void setSubmission(InputStream submission) {
        this.submission = submission;
    }
}
